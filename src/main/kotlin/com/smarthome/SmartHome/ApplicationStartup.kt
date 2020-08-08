package com.smarthome.SmartHome

import com.smarthome.SmartHome.service.FcmService
import com.smarthome.SmartHome.service.impl.fcm.builder.FcmPushDirector
import com.smarthome.SmartHome.service.impl.fcm.builder.HighCpuTemperatureFcmPushBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.util.*
import java.io.IOException
import java.io.BufferedReader
import java.io.InputStreamReader


@Component
class ApplicationStartup : ApplicationListener<ApplicationReadyEvent> {
    private var timer = Timer("temperatureTimer")
    @Autowired lateinit var fcmService: FcmService
    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        timer.scheduleAtFixedRate(getTemperatureTimerTask(), 0L, PERIOD)
    }

    private fun getTemperatureTimerTask() =
            object: TimerTask() {
                override fun run() {
                    try {
                        val proc = Runtime.getRuntime().exec("./temp.sh")
                        val stdInput = BufferedReader(InputStreamReader(proc.inputStream))
                        val temp = stdInput.readLine().substringAfterLast('=').substringBeforeLast('.').toIntOrNull() ?: -1
                        println("CPU temperature is: $temp")

                        if(temp > CRITICAL_TEMPERATURE){
                            sendPushNotificationAboutHighTemperature(temp)
                        }
                        proc.waitFor()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }

    private fun sendPushNotificationAboutHighTemperature(temperature: Int){
        val fcmPush = FcmPushDirector(HighCpuTemperatureFcmPushBuilder())
                .buildFcmPush(null, temperature)
        fcmService.sendPushNotificationsToUsers(fcmPush)
    }

    companion object {
        private const val PERIOD = 1000L * 60L * 5L // Once per 5 minutes
        private const val CRITICAL_TEMPERATURE = 70
    }

}
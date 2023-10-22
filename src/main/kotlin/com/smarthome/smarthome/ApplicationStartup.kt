package com.smarthome.smarthome

import com.smarthome.smarthome.service.BoilerScheduleService
import com.smarthome.smarthome.service.FcmService
import com.smarthome.smarthome.service.RaspberryService
import com.smarthome.smarthome.service.impl.fcm.builder.FcmPushDirector
import com.smarthome.smarthome.service.impl.fcm.builder.HighCpuTemperatureFcmPushBuilder
import org.slf4j.LoggerFactory
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
    @Autowired lateinit var boilerService: BoilerScheduleService
    @Autowired lateinit var raspberryService: RaspberryService
    private val log = LoggerFactory.getLogger("ApplicationStartup")

    /**
     * This event is executed as late as conceivably possible to indicate that
     * the application is ready to service requests.
     */
    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        timer.scheduleAtFixedRate(getTemperatureTimerTask(), 0L, PERIOD)
        boilerService.initRecurringJobForEnablingBoiler()
        raspberryService.restoreRozetkasAndLights()
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
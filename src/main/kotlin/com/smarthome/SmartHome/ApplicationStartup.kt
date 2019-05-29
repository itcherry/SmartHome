package com.smarthome.SmartHome

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
                        val temp = stdInput.readLine().substringAfterLast('=').substringBeforeLast('.').toIntOrNull()
                        println("CPU temperature is: $temp")

                        if((temp ?: 0) > 70){
                            // TODO send push notification
                        }
                        proc.waitFor()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }

    companion object {
        private const val PERIOD = 1000L * 60L * 10L // Each per 10 minutes
    }

}
package com.smarthome.SmartHome.service.impl.temperature_humidity

import com.pi4j.wiringpi.Gpio
import com.pi4j.wiringpi.GpioUtil

class DHT22DataHandler : Runnable {
    private val dht22_dat = intArrayOf(0, 0, 0, 0, 0)
    private var temperature = 9999f
    private var humidity = 9999f
    var shuttingDown = false

    init {
        // setup wiringPi
        if (Gpio.wiringPiSetup() == -1) {
            println(" ==>> GPIO SETUP FAILED")
        } else {
            GpioUtil.export(3, GpioUtil.DIRECTION_OUT)
        }
    }

    private fun pollDHT22(): Int {
        var lastState = Gpio.HIGH
        var j = 0
        dht22_dat[4] = 0
        dht22_dat[3] = dht22_dat[4]
        dht22_dat[2] = dht22_dat[3]
        dht22_dat[1] = dht22_dat[2]
        dht22_dat[0] = dht22_dat[1]
        val pinNumber = 16
        Gpio.pinMode(pinNumber, Gpio.OUTPUT)
        Gpio.digitalWrite(pinNumber, Gpio.LOW)
        Gpio.delay(18)
        Gpio.digitalWrite(pinNumber, Gpio.HIGH)
        Gpio.pinMode(pinNumber, Gpio.INPUT)
        for (i in 0 until maxTimings) {
            var counter = 0
            while (Gpio.digitalRead(pinNumber) == lastState) {
                counter++
                Gpio.delayMicroseconds(1)
                if (counter == 255) {
                    break
                }
            }
            lastState = Gpio.digitalRead(pinNumber)
            if (counter == 255) {
                break
            }

            /* ignore first 3 transitions */if (i >= 4 && i % 2 == 0) {
                /* shove each bit into the storage bytes */
                dht22_dat[j / 8] = dht22_dat[j / 8] shl 1
                if (counter > 16) {
                    dht22_dat[j / 8] = dht22_dat[j / 8] or 1
                }
                j++
            }
        }
        return j
    }

    private fun refreshData() {
        val pollDataCheck = pollDHT22()
        if (pollDataCheck >= 40 && checkParity()) {
            val newHumidity = ((dht22_dat[0] shl 8) + dht22_dat[1]).toFloat() / 10
            val newTemperature = ((dht22_dat[2] and 0x7F shl 8) + dht22_dat[3]).toFloat() / 10
            if (humidity == 9999f || newHumidity < humidity + 40 && newHumidity > humidity - 40) {
                humidity = newHumidity
                if (humidity > 100) {
                    humidity = dht22_dat[0].toFloat() // for DHT22
                }
            }
            if (temperature == 9999f || newTemperature < temperature + 40 && newTemperature > temperature - 40) {
                temperature = ((dht22_dat[2] and 0x7F shl 8) + dht22_dat[3]).toFloat() / 10
                if (temperature > 125) {
                    temperature = dht22_dat[2].toFloat() // for DHT22
                }
                if (dht22_dat[2] and 0x80 != 0) {
                    temperature = -temperature
                }
            }
        }
    }

    fun getHumidity(): Float {
        return if (humidity == 9999f) {
            0f
        } else {
            humidity
        }
    }

    fun getTemperature(): Float {
        return if (temperature == 9999f) {
            0f
        } else {
            temperature
        }
    }

    val temperatureInF: Float
        get() = if (temperature == 9999f) {
            32f
        } else {
            temperature * 1.8f + 32
        }

    private fun checkParity(): Boolean {
        return dht22_dat[4] == dht22_dat[0] + dht22_dat[1] + dht22_dat[2] + dht22_dat[3] and 0xFF
    }

    override fun run() {
        while (!shuttingDown) {
            refreshData()
        }
    }

    companion object {
        private const val maxTimings = 85
    }
}
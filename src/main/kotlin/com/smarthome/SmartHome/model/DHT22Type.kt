package com.smarthome.SmartHome.model

import com.pi4j.io.gpio.Pin
import com.pi4j.io.gpio.RaspiPin

enum class DHT22Type(val code: Int, val gpioPin: Pin) {
    SENSOR_KITCHEN(1, RaspiPin.GPIO_06),
    SENSOR_LIVING_ROOM(2, RaspiPin.GPIO_13),
    SENSOR_BEDROOM(3, RaspiPin.GPIO_19),
    SENSOR_OUTDOOR(4, RaspiPin.GPIO_26),
    UNDEFINED(-1, RaspiPin.GPIO_31);

    companion object {
        fun getTypeById(id: Int): DHT22Type {
            values().forEach {
                if (id == it.code) return it
            }
            return UNDEFINED
        }
    }
}
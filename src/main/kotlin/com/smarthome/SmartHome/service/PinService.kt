package com.smarthome.SmartHome.service

import com.smarthome.SmartHome.model.SensorToPin

interface PinService {
    fun setPin(pinId: Int, isEnabled: Boolean)

    fun setSensor(sensor: SensorToPin, isEnabled: Boolean)

    fun getSensor(sensor: SensorToPin): Boolean

    fun getPin(pinId: Int): Boolean

    fun getOutPin(pinId: Int): Boolean

    fun getOutSensor(sensor: SensorToPin): Boolean

    fun pulsePin(pinId: Int)
}
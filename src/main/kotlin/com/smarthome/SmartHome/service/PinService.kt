package com.smarthome.SmartHome.service

import com.smarthome.SmartHome.model.SensorToPin

interface PinService {
    fun setMultipurposePin(pinId: Int, isEnabled: Boolean)
    fun pulseMultipurposePin(pinId: Int)
    fun getMultipurposePin(pinId: Int): Boolean

    /* Only out sensors */
    fun setSensor(sensor: SensorToPin, isEnabled: Boolean)

    /* Only out sensors */
    fun getSensor(sensor: SensorToPin): Boolean

    /* Both OUT and IN sensors */
    fun getMultipurposeSensor(sensor: SensorToPin): Boolean

    /* Both OUT and IN sensors */
    fun setMultipurposeSensor(sensor: SensorToPin, isEnabled: Boolean)

}
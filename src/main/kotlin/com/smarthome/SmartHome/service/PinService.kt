package com.smarthome.SmartHome.service

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent
import com.smarthome.SmartHome.service.impl.pin.model.SensorToPin

interface PinService {
    fun setMultipurposePin(pinId: Int, isEnabled: Boolean)
    fun pulseMultipurposePin(pinId: Int)
    fun getMultipurposePin(pinId: Int): Boolean

    /* Only out sensors */
    fun setSensor(sensor: SensorToPin, isEnabled: Boolean)
    fun getSensor(sensor: SensorToPin): Boolean

    /* Both OUT and IN sensors */
    fun getMultipurposeSensor(sensor: SensorToPin): Boolean
    fun setMultipurposeSensor(sensor: SensorToPin, isEnabled: Boolean)
    fun pulseMultipurposeSensor(sensor: SensorToPin)

    /* Alarm listeners */
    fun setSecurityAlarmListener(listener: ((GpioPinDigitalStateChangeEvent) -> Unit)?)

    fun setNeptunAlarmListener(listener:(GpioPinDigitalStateChangeEvent) -> Unit)

    fun setFireAlarmListener(listener:(GpioPinDigitalStateChangeEvent) -> Unit)
}
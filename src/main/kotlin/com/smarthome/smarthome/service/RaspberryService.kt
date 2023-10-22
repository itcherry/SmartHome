package com.smarthome.smarthome.service

import com.smarthome.smarthome.service.impl.pin.model.SensorToPin

interface RaspberryService {
    fun enableSecurity()
    fun disableSecurity()
    fun isSecurityEnabled(): Boolean
    fun setRozetkaState(place: SensorToPin, isEnabled: Boolean)
    fun getRozetkaState(place: SensorToPin): Boolean
    fun pulseLightState(place: SensorToPin)
    fun getLightState(place: SensorToPin): Boolean
    fun restoreRozetkasAndLights()
}
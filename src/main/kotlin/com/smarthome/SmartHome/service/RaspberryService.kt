package com.smarthome.SmartHome.service

interface RaspberryService {
    fun enableSecurity()
    fun disableSecurity()
    fun isSecurityEnabled(): Boolean
}
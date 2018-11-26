package com.smarthome.SmartHome.service

interface PinService {
    fun setPin(pinId: Int, isEnabled: Boolean)

    fun getPin(pinId: Int): String

    fun pulsePin(pinId: Int)
}
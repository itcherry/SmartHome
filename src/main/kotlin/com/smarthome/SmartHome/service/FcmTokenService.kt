package com.smarthome.SmartHome.service

interface FcmTokenService {
    fun getAllTokens(): List<String>
    fun addToken(token: String?)
}
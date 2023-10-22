package com.smarthome.smarthome.service

interface FcmTokenService {
    fun getAllTokens(): List<String>
    fun addToken(token: String?)
}
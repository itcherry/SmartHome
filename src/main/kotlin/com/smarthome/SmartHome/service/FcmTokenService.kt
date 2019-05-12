package com.smarthome.SmartHome.service

import com.smarthome.SmartHome.entity.FcmToken

interface FcmTokenService {
    fun getAllTokens(): List<FcmToken>
    fun addToken(token: String?)
}
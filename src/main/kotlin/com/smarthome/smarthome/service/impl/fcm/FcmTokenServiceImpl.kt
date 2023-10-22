package com.smarthome.smarthome.service.impl.fcm

import com.smarthome.smarthome.error.FcmError
import com.smarthome.smarthome.exception.ExceptionFactory
import com.smarthome.smarthome.repository.entity.FcmToken
import com.smarthome.smarthome.repository.FcmTokenRepository
import com.smarthome.smarthome.service.FcmTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class FcmTokenServiceImpl @Autowired constructor(private val fcmTokenRepository: FcmTokenRepository) : FcmTokenService {

    @Transactional(readOnly = true)
    override fun getAllTokens(): List<String> = fcmTokenRepository.findAll().map { it.token }

    @Transactional
    override fun addToken(token: String?) {
        if(token.isNullOrBlank()){
            throw ExceptionFactory.create(FcmError.EMPTY_FCM_TOKEN)
        }

        fcmTokenRepository.save(FcmToken(token = token))
    }
}
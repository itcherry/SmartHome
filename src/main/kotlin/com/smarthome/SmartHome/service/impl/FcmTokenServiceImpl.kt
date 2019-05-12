package com.smarthome.SmartHome.service.impl

import com.smarthome.SmartHome.entity.FcmToken
import com.smarthome.SmartHome.entity.User
import com.smarthome.SmartHome.error.FcmError
import com.smarthome.SmartHome.exception.ExceptionFactory
import com.smarthome.SmartHome.repository.FcmTokenRepository
import com.smarthome.SmartHome.service.FcmTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FcmTokenServiceImpl @Autowired constructor(private val fcmTokenRepository: FcmTokenRepository) : FcmTokenService {

    @Transactional(readOnly = true)
    override fun getAllTokens(): List<FcmToken> = fcmTokenRepository.findAll()

    @Transactional
    override fun addToken(token: String?) {
        if(token.isNullOrBlank()){
            throw ExceptionFactory.create(FcmError.EMPTY_FCM_TOKEN)
        }

        fcmTokenRepository.save(FcmToken(token = token!!))
    }
}
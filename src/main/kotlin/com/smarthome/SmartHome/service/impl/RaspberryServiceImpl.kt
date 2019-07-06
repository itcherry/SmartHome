package com.smarthome.SmartHome.service.impl

import com.smarthome.SmartHome.repository.RaspberryRepository
import com.smarthome.SmartHome.repository.entity.Raspberry
import com.smarthome.SmartHome.service.RaspberryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class RaspberryServiceImpl @Autowired constructor(private val raspberryRepository: RaspberryRepository) : RaspberryService {

    @Transactional
    override fun enableSecurity() {
        val optionalRaspberry = raspberryRepository.findById(0L)

        val newRaspberry = optionalRaspberry.orElse(Raspberry(1L, true))
                .copy(isSecurityEnabled = true)
        raspberryRepository.save(newRaspberry)
    }

    @Transactional
    override fun disableSecurity() {
        val optionalRaspberry = raspberryRepository.findById(0L)
        val newRaspberry = optionalRaspberry.orElse(Raspberry(1L, false))
                .copy(isSecurityEnabled = false)
        raspberryRepository.save(newRaspberry)
    }

    @Transactional(readOnly = true)
    override fun isSecurityEnabled(): Boolean {
        return raspberryRepository.findById(1L).orElse(Raspberry(1L, false)).isSecurityEnabled
    }
}
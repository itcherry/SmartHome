package com.smarthome.SmartHome.service.impl

import com.smarthome.SmartHome.repository.RaspberryRepository
import com.smarthome.SmartHome.repository.entity.Raspberry
import com.smarthome.SmartHome.service.RaspberryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RaspberryServiceImpl @Autowired constructor(private val raspberryRepository: RaspberryRepository) : RaspberryService {

    @Transactional
    override fun enableSecurity() {
        val optionalRaspberry = raspberryRepository.findById(0L)
        optionalRaspberry.ifPresentOrElse({
            val newRaspberry = it.copy(isSecurityEnabled = true)
            raspberryRepository.save(newRaspberry)
        }, {
            raspberryRepository.save(Raspberry(isSecurityEnabled = true))
        })
    }

    @Transactional
    override fun disableSecurity() {
        val optionalRaspberry = raspberryRepository.findById(0L)
        optionalRaspberry.ifPresentOrElse({
            val newRaspberry = it.copy(isSecurityEnabled = false)
            raspberryRepository.save(newRaspberry)
        }, {
            raspberryRepository.save(Raspberry(isSecurityEnabled = false))
        })
    }

    @Transactional(readOnly = true)
    override fun isSecurityEnabled(): Boolean {
        return raspberryRepository.findById(0L).orElse(Raspberry(isSecurityEnabled = false)).isSecurityEnabled
    }
}
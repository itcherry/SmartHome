package com.smarthome.SmartHome.service.impl

import com.smarthome.SmartHome.repository.entity.User
import com.smarthome.SmartHome.repository.UserRepository
import com.smarthome.SmartHome.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl @Autowired constructor(private val userRepository: UserRepository) : UserService {

    @Transactional(readOnly = true)
    override fun getUserByLogin(login: String): User = userRepository.findUserByLogin(login)
}
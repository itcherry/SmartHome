package com.smarthome.smarthome.service.impl

import com.smarthome.smarthome.repository.entity.User
import com.smarthome.smarthome.repository.UserRepository
import com.smarthome.smarthome.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class UserServiceImpl @Autowired constructor(private val userRepository: UserRepository) : UserService {

    @Transactional(readOnly = true)
    override fun getUserByLogin(login: String): User = userRepository.findUserByLogin(login)
}
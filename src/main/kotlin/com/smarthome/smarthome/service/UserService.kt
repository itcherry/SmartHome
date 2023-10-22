package com.smarthome.smarthome.service

import com.smarthome.smarthome.repository.entity.User

interface UserService {
    fun getUserByLogin(login: String): User
}
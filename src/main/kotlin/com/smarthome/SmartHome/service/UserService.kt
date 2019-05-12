package com.smarthome.SmartHome.service

import com.smarthome.SmartHome.entity.User

interface UserService {
    fun getUserByLogin(login: String): User
}
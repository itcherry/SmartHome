package com.smarthome.smarthome.repository

import com.smarthome.smarthome.repository.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long?> {
    fun findUserByLogin(login: String?): User
}

package com.smarthome.SmartHome.repository

import com.smarthome.SmartHome.repository.entity.FcmToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FcmTokenRepository : JpaRepository<FcmToken, Long>
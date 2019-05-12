package com.smarthome.SmartHome.repository

import com.smarthome.SmartHome.entity.FcmToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FcmTokenRepository : JpaRepository<FcmToken, Long>
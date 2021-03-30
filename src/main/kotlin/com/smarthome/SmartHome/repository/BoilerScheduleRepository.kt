package com.smarthome.SmartHome.repository

import com.smarthome.SmartHome.repository.entity.BoilerSchedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoilerScheduleRepository : JpaRepository<BoilerSchedule, Long>
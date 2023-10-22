package com.smarthome.smarthome.repository

import com.smarthome.smarthome.repository.entity.BoilerSchedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoilerScheduleRepository : JpaRepository<BoilerSchedule, Long>
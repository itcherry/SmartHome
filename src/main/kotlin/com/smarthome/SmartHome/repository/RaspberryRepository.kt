package com.smarthome.SmartHome.repository

import com.smarthome.SmartHome.repository.entity.Raspberry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RaspberryRepository : JpaRepository<Raspberry, Long>
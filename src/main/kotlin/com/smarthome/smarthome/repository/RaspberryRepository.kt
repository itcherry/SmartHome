package com.smarthome.smarthome.repository

import com.smarthome.smarthome.repository.entity.Raspberry
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RaspberryRepository : JpaRepository<Raspberry, Long>
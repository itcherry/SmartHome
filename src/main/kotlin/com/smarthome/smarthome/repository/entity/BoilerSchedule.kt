package com.smarthome.smarthome.repository.entity

import javax.persistence.*

@Entity
@Table(name = "boiler_schedule")
data class BoilerSchedule(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "schedule_id") val id: Long = 0,
        @Column(name = "startMinute", nullable = false) val startMinute: Int = 0,
        @Column(name = "endMinute", nullable = false) val endMinute: Int = 0
)
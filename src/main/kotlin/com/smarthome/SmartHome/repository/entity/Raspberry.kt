package com.smarthome.SmartHome.repository.entity

import javax.persistence.*

@Entity
@Table(name = "raspberry")
data class Raspberry (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0L,
        @Column(name = "isSecurityEnabled", nullable = false) val isSecurityEnabled: Boolean = false
)
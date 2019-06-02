package com.smarthome.SmartHome.repository.entity

import javax.persistence.*

@Entity
@Table(name = "raspberry")
data class Raspberry (
        @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long = 0,
        @Column(name = "isSecurityEnabled", nullable = false) val isSecurityEnabled: Boolean
)
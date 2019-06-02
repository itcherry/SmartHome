package com.smarthome.SmartHome.repository.entity

import javax.persistence.*

@Entity
@Table(name = "fcm")
data class FcmToken(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0,
        @Column(name = "token", unique = true, nullable = false) val token: String
)
package com.smarthome.smarthome.repository.entity

import javax.persistence.*

@Entity
@Table(name = "fcm")
data class FcmToken(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id") val id: Long = 0L,
        @Column(name = "token", unique = true, nullable = false) val token: String = ""
)
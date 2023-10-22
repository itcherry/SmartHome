package com.smarthome.smarthome.repository.entity

import javax.persistence.*

@Entity
@Table(name = "user")
data class User(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) @Column(name = "user_id") val id: Long = 0,
        @Column(name = "login", unique = true, nullable = false) val login: String = "",
        @Column(name = "password", nullable = false) val password: String = ""
)
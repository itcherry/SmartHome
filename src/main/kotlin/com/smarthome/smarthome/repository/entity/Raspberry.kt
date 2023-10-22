package com.smarthome.smarthome.repository.entity

import javax.persistence.*

@Entity
@Table(name = "raspberry")
data class Raspberry(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long = 0L,
        @Column(name = "isSecurityEnabled", nullable = false) val isSecurityEnabled: Boolean = false,
        @Column(name = "isBoilerEnabled", nullable = false) val isBoilerEnabled: Boolean = false,
        @Column(name = "isBoilerAllDayEnabled", nullable = false) val isBoilerAllDayEnabled: Boolean = false,
        @Column(name = "isBedroomRozetkaEnabled", nullable = false) val isBedroomRozetkaEnabled: Boolean = false,
        @Column(name = "isBedroomLightsEnabled", nullable = false) val isBedroomLightsEnabled: Boolean = false,
        @Column(name = "isCorridorLightsEnabled", nullable = false) val isCorridorLightsEnabled: Boolean = false,
        @Column(name = "isKitchenLightsEnabled", nullable = false) val isKitchenLightsEnabled: Boolean = false,
        @Column(name = "isLivingRoomRozetkaEnabled", nullable = false) val isLivingRoomRozetkaEnabled: Boolean = false,
        @Column(name = "isAquariumRozetkaEnabled", nullable = false) val isAquariumRozetkaEnabled: Boolean = false,
        @Column(name = "isLivingRoomLightsEnabled", nullable = false) val isLivingRoomLightsEnabled: Boolean = false,
        @Column(name = "isAlarmEnabled", nullable = false) val isAlarmEnabled: Boolean = false
)
package com.smarthome.SmartHome

import com.pi4j.io.gpio.GpioFactory
import com.smarthome.SmartHome.dhtxx.DHT22
import com.smarthome.SmartHome.model.DHT22Type.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SmartHomeApplication {
    @Bean
    fun provideGpioFactory() = GpioFactory.getInstance()

    @Bean
    fun provideSensorsMap() = mapOf(
            SENSOR_KITCHEN to DHT22(SENSOR_KITCHEN.gpioPin),
            SENSOR_LIVING_ROOM to DHT22(SENSOR_LIVING_ROOM.gpioPin),
            SENSOR_BEDROOM to DHT22(SENSOR_BEDROOM.gpioPin),
            SENSOR_OUTDOOR to DHT22(SENSOR_OUTDOOR.gpioPin))
}

fun main(args: Array<String>) {
    runApplication<SmartHomeApplication>(*args)
}

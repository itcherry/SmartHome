package com.smarthome.smarthome

import com.corundumstudio.socketio.Configuration
import com.corundumstudio.socketio.SocketConfig
import com.corundumstudio.socketio.SocketIOServer
import com.google.gson.GsonBuilder
import com.pi4j.io.gpio.GpioController
import com.pi4j.io.gpio.GpioFactory
import com.smarthome.smarthome.converter.UserToUserDtoConverter
import com.smarthome.smarthome.service.impl.temperature_humidity.model.DHT22
import com.smarthome.smarthome.service.impl.temperature_humidity.model.DHT22Type.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.context.support.ConversionServiceFactoryBean
import org.springframework.core.convert.ConversionService
import org.springframework.core.convert.converter.Converter
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.*
import java.util.concurrent.Executor


@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
open class SmartHomeApplication {
    @Value("\${socketIO.hostname}")
    private val socketIOHostname: String? = null

    @Value("\${socketIO.port}")
    private val socketIOPort: Int? = null

    @Value("\${spring.mail.host}")
    private val smtpHost: String? = null

    @Value("\${spring.mail.username}")
    private val smtpUsername: String? = null

    @Value("\${spring.mail.password}")
    private val smtpPassword: String? = null

    @Value("\${spring.mail.port}")
    private val smtpPort: Int? = null

    @Value("\${spring.mail.properties.mail.smtp.auth}")
    private val smtpAuth: Boolean? = true

    @Value("\${spring.mail.properties.mail.smtp.starttls.enable}")
    private val smtpTtlsEnable: Boolean? = true

    @Value("\${spring.mail.protocol}")
    private val smtpProtocol: String? = null

    @Bean
    open fun socketIOServer(): SocketIOServer {
        val configuration = Configuration()
        configuration.hostname = socketIOHostname

        val socketConfig = SocketConfig()
        socketConfig.isReuseAddress = true
        configuration.pingInterval = 1000
        configuration.pingTimeout = 5000
        configuration.socketConfig = socketConfig

        configuration.port = socketIOPort!!.toInt()
        return SocketIOServer(configuration)
    }

    @Bean
    open fun userToUserDtoConverter(): UserToUserDtoConverter {
        return UserToUserDtoConverter()
    }

    @Bean(name = ["APIConversionService"])
    @Primary
    open fun getConversionService(): ConversionService? {
        val bean = ConversionServiceFactoryBean()
        val converters = HashSet<Converter<*, *>>()

        //add the converter
        converters.add(userToUserDtoConverter())

        bean.setConverters(converters)
        bean.afterPropertiesSet()
        return bean.getObject()
    }

    @Bean
    open fun provideGpioFactory(): GpioController {
        return GpioFactory.getInstance()
    }

    @Bean(name = ["threadPoolTaskExecutor"])
    open fun threadPoolTaskExecutor(): Executor {
        val threadPoolTaskExecutor = ThreadPoolTaskExecutor()
        threadPoolTaskExecutor.maxPoolSize = 100
        return ThreadPoolTaskExecutor()
    }

    @Bean
    open fun provideSensorsMap() = mapOf(
            SENSOR_KITCHEN to DHT22(SENSOR_KITCHEN.gpioPin),
            SENSOR_LIVING_ROOM to DHT22(SENSOR_LIVING_ROOM.gpioPin),
            SENSOR_BEDROOM to DHT22(SENSOR_BEDROOM.gpioPin),
            SENSOR_OUTDOOR to DHT22(SENSOR_OUTDOOR.gpioPin))

    @Bean
    open fun provideGSON() =
            GsonBuilder()
                    .setLenient()
                    .create()

    @Bean
    open fun provideJavaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl().apply {
            host = smtpHost
            port = smtpPort ?: 465
            username = smtpUsername
            password = smtpPassword
        }
        val props: Properties = mailSender.javaMailProperties
        props.put("mail.transport.protocol", smtpProtocol)
        props.put("mail.smtp.auth", smtpAuth.toString())
        props.put("mail.smtp.starttls.enable", smtpTtlsEnable.toString())
        props.put("mail.debug", "true")
        return mailSender
    }
}

fun main(args: Array<String>) {
    runApplication<SmartHomeApplication>(*args)
}

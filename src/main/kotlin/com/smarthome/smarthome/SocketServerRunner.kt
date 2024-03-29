package com.smarthome.smarthome

import com.corundumstudio.socketio.SocketIOServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class SocketServerRunner @Autowired constructor(private val server: SocketIOServer) : CommandLineRunner {
    @Throws(Exception::class)
    override fun run(vararg strings: String) {
        server.start()
    }
}

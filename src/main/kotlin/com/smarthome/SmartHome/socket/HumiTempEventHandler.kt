package com.smarthome.SmartHome.socket

import com.corundumstudio.socketio.SocketIONamespace
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.ConnectListener
import com.corundumstudio.socketio.listener.DataListener
import com.corundumstudio.socketio.listener.DisconnectListener
import com.google.gson.Gson
import com.pi4j.io.gpio.GpioController
import com.smarthome.SmartHome.controller.model.LightsDTO
import com.smarthome.SmartHome.model.DHT22Type
import com.smarthome.SmartHome.model.Pins
import com.smarthome.SmartHome.service.HumiTempService
import com.smarthome.SmartHome.service.PinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit


const val NAMESPACE = "/raspberry"
const val HUMIDITY_TEMPERATURE_EVENT = "tempHum"
const val LIGHTS_EVENT = "light"

@Component
class HumiTempEventHandler @Autowired constructor(socketIOServer: SocketIOServer,
                                                  private val pinService: PinService,
                                                  private val humiTempService: HumiTempService,
                                                  private val gson: Gson,
                                                  private val gpio: GpioController) {

    private val namespace: SocketIONamespace = socketIOServer.addNamespace(NAMESPACE)
    private var scheduler: ScheduledExecutorService? = null

    init {
        this.namespace.addConnectListener(onConnected())
        this.namespace.addDisconnectListener(onDisconnected())
        this.namespace.addEventListener(LIGHTS_EVENT, LightsDTO::class.java, setLightsListener())
    }

    private fun onConnected() = ConnectListener { client ->
        println("Client connected! " + client.handshakeData.address)
        if (namespace.allClients.size == 1) {
            scheduler = Executors.newScheduledThreadPool(1)
            scheduler?.scheduleAtFixedRate(getTemperatureRunnable(), 0, 2030, TimeUnit.MILLISECONDS)
            println("Temperature scheduler started")
        }
    }

    private fun onDisconnected() = DisconnectListener { client ->
        println("Client disconnected! " + client.handshakeData.address)
        if (namespace.allClients.isEmpty()) {
            scheduler?.shutdownNow()
            println("Temperature scheduler finished his work")
        }
    }

    private fun getTemperatureRunnable(): () -> Unit = {
        val data = gson.toJson(humiTempService.getDataFromSensor(DHT22Type.SENSOR_KITCHEN))
        namespace.broadcastOperations.sendEvent(HUMIDITY_TEMPERATURE_EVENT, data)
    }

    private fun setLightsListener() =
            DataListener<LightsDTO> { client, data, _ ->
                pinService.setPin(Pins.GPIO_05.pinId, data.isOn)
            }
}

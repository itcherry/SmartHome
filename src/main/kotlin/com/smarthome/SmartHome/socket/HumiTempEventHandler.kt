package com.smarthome.SmartHome.socket

import com.corundumstudio.socketio.SocketIONamespace
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.ConnectListener
import com.corundumstudio.socketio.listener.DisconnectListener
import com.google.gson.Gson
import com.smarthome.SmartHome.model.DHT22Type
import com.smarthome.SmartHome.service.HumiTempService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*


const val NAMESPACE = "/raspberry"
const val HUMIDITY_TEMPERATURE_EVENT = "tempHum"
const val LIGHTS_EVENT = "light"

@Component
class HumiTempEventHandler @Autowired constructor(socketIOServer: SocketIOServer,
                                                  private val humiTempService: HumiTempService,
                                                  private val gson: Gson) {

    private val namespace: SocketIONamespace = socketIOServer.addNamespace(NAMESPACE)
    private var timer: Timer? = null

    init {
        this.namespace.addConnectListener(onConnected())
        this.namespace.addDisconnectListener(onDisconnected())
    }

    private fun onConnected() = ConnectListener { client ->
        println("Client connected! " + client.handshakeData.address)
        if (namespace.allClients.size == 1) {
            timer = Timer("timer")
            timer?.scheduleAtFixedRate(getTemperatureTimerTask(), 0L, 500)
            println("Temperature scheduler started")
        }
    }

    private fun onDisconnected() = DisconnectListener { client ->
        println("Client disconnected! " + client.handshakeData.address)
        if (namespace.allClients.isEmpty()) {
            timer?.cancel()
            println("Temperature scheduler finished his work")
        }
    }

    private fun getTemperatureTimerTask() =
            object: TimerTask() {
                override fun run() {
                    val data = gson.toJson(humiTempService.getDataFromSensor(DHT22Type.SENSOR_KITCHEN))
                    namespace.broadcastOperations.sendEvent(HUMIDITY_TEMPERATURE_EVENT, data)
                }
            }
}

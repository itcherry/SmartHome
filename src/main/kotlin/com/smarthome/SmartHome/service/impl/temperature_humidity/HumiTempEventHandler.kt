package com.smarthome.SmartHome.service.impl.temperature_humidity

import com.corundumstudio.socketio.SocketIONamespace
import com.corundumstudio.socketio.SocketIOServer
import com.corundumstudio.socketio.listener.ConnectListener
import com.corundumstudio.socketio.listener.DisconnectListener
import com.google.gson.Gson
import com.smarthome.SmartHome.service.impl.temperature_humidity.model.DHT22Type
import com.smarthome.SmartHome.service.HumiTempService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*


const val NAMESPACE = "/raspberry"
const val TEMP_HUM_BEDROOM_EVENT = "tempHumBedroom"
const val TEMP_HUM_KITCHEN_EVENT = "tempHumKitchen"
const val TEMP_HUM_LIVING_ROOM_EVENT = "tempHumLivingRoom"
const val TEMP_HUM_OUTDOOR_EVENT = "tempHumOutdoor"

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
                    val dataKitchen = gson.toJson(humiTempService.getDataFromSensor(DHT22Type.SENSOR_KITCHEN))
                    namespace.broadcastOperations.sendEvent(TEMP_HUM_KITCHEN_EVENT, dataKitchen)

                    val dataBedroom = gson.toJson(humiTempService.getDataFromSensor(DHT22Type.SENSOR_BEDROOM))
                    namespace.broadcastOperations.sendEvent(TEMP_HUM_BEDROOM_EVENT, dataBedroom)

                    val dataLivingRoom = gson.toJson(humiTempService.getDataFromSensor(DHT22Type.SENSOR_LIVING_ROOM))
                    namespace.broadcastOperations.sendEvent(TEMP_HUM_LIVING_ROOM_EVENT, dataLivingRoom)

                    val dataOutdoor = gson.toJson(humiTempService.getDataFromSensor(DHT22Type.SENSOR_OUTDOOR))
                    namespace.broadcastOperations.sendEvent(TEMP_HUM_OUTDOOR_EVENT, dataOutdoor)
                }
            }
}

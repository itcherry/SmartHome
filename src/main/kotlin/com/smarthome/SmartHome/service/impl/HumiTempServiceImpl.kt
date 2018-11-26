package com.smarthome.SmartHome.service.impl

import com.smarthome.SmartHome.controller.model.SensorResult
import com.smarthome.SmartHome.dhtxx.DHT22
import com.smarthome.SmartHome.model.DHT22Type
import com.smarthome.SmartHome.service.HumiTempService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HumiTempServiceImpl @Autowired constructor(
        private val sensorsMap: Map<DHT22Type, DHT22>
) : HumiTempService {

    init {
        for (( _, sensor) in sensorsMap) {
            sensor.init()
        }
    }

    override fun getAllSensorsData(): List<SensorResult> {
        val result = mutableListOf<SensorResult>()

        for ((sensorType, sensor) in sensorsMap) {
            val sensorResult = SensorResult(dhtCode = sensorType.code)

            try {
                val dhtData = sensor.data
                sensorResult.humidity = dhtData.humidity
                sensorResult.temperature = dhtData.temperature
            } catch (e: Exception){
                println("An error occured when reading data from sensor: ${sensorType.name}")
            }

            result.add(sensorResult)
        }

        return result
    }
}
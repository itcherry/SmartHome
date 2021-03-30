package com.smarthome.SmartHome.service.impl.temperature_humidity

import com.smarthome.SmartHome.controller.model.SensorResult
import com.smarthome.SmartHome.service.impl.temperature_humidity.model.DHT22Type
import com.smarthome.SmartHome.controller.model.TemperatureHumidity
import com.smarthome.SmartHome.service.HumiTempService
import com.smarthome.SmartHome.service.impl.temperature_humidity.model.DHT22
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

    override fun getDataFromSensor(dhT22Type: DHT22Type): TemperatureHumidity {
        try {
            val dhtData = sensorsMap[dhT22Type]?.data
            return TemperatureHumidity(
                    humidity = dhtData?.humidity ?: 0.0,
                    temperature = dhtData?.temperature ?: 0.0
            )
        } catch (e: Exception){
            println("An error occurred when reading data from sensor: ${dhT22Type.name}. Message: ${e.message}")
        }
        return TemperatureHumidity(0.0, 0.0)
    }
}
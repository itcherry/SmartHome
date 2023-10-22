package com.smarthome.smarthome.service.impl.temperature_humidity

import com.pi4j.component.temperature.TemperatureSensor
import com.pi4j.io.w1.W1Master
import com.pi4j.temperature.TemperatureScale
import com.smarthome.smarthome.service.impl.temperature_humidity.model.DS18B20Type
import com.smarthome.smarthome.controller.model.SensorResult
import com.smarthome.smarthome.controller.model.Temperature
import com.smarthome.smarthome.service.HumiTempService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class HumiTempServiceImpl @Autowired constructor() : HumiTempService {

    private val log = LoggerFactory.getLogger("HumiTempService")

    override fun getAllSensorsData(): List<SensorResult> {
        val result = mutableListOf<SensorResult>()

        val w1Master = W1Master()

        for(device in w1Master.getDevices(TemperatureSensor::class.java)) {
            val sensor = DS18B20Type.getTypeByName(device.name)

            try {
                result.add(SensorResult(device.getTemperature(TemperatureScale.CELSIUS), sensor.code))
            } catch (e: Exception) {
                log.error("An error occurred when reading data from sensor: ${sensor.name}, ${sensor.sensorName}")
            }
        }

        return result
    }

    override fun getDataFromSensor(sensorType: DS18B20Type): Temperature {
        val w1Master = W1Master()
        val device = w1Master.getDevices(TemperatureSensor::class.java)
                .firstOrNull { sensorType.sensorName.trim().equals(it.name.trim(), true) }

        return if(device == null) {
            log.error("No temperature sensor with such code ${sensorType.sensorName}")
            Temperature(0.0)
        } else {
            Temperature(device.getTemperature(TemperatureScale.CELSIUS))
        }
    }
}
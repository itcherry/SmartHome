package com.smarthome.smarthome.service

import com.smarthome.smarthome.controller.model.SensorResult
import com.smarthome.smarthome.controller.model.Temperature
import com.smarthome.smarthome.service.impl.temperature_humidity.model.DS18B20Type

interface HumiTempService {
    fun getAllSensorsData(): List<SensorResult>
    fun getDataFromSensor(sensorType: DS18B20Type): Temperature
}
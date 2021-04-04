package com.smarthome.SmartHome.service

import com.smarthome.SmartHome.controller.model.SensorResult
import com.smarthome.SmartHome.controller.model.Temperature
import com.smarthome.SmartHome.service.impl.temperature_humidity.model.DHT22Type
import com.smarthome.SmartHome.controller.model.TemperatureHumidity
import com.smarthome.SmartHome.service.impl.temperature_humidity.model.DS18B20Type

interface HumiTempService {
    fun getAllSensorsData(): List<SensorResult>
    fun getDataFromSensor(sensorType: DS18B20Type): Temperature
}
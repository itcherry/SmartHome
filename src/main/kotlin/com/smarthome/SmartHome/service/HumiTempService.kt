package com.smarthome.SmartHome.service

import com.smarthome.SmartHome.controller.model.SensorResult
import com.smarthome.SmartHome.model.DHT22Type
import com.smarthome.SmartHome.model.TemperatureHumidity

interface HumiTempService {
    fun getAllSensorsData(): List<SensorResult>
    fun getDataFromSensor(dhT22Type: DHT22Type): TemperatureHumidity
}
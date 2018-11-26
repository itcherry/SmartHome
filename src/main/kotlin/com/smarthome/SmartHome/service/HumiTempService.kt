package com.smarthome.SmartHome.service

import com.smarthome.SmartHome.controller.model.SensorResult

interface HumiTempService {
    fun getAllSensorsData(): List<SensorResult>
}
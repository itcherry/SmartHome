package com.smarthome.SmartHome.controller.model

data class SensorResult(var temperature: Double? = null,
                        var humidity: Double? = null,
                        var dhtCode: Int)
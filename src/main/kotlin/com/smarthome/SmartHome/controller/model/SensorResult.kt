package com.smarthome.SmartHome.controller.model

import com.smarthome.SmartHome.dhtxx.DhtData

data class SensorResult(var temperature: Double? = null,
                        var humidity: Double? = null,
                        var dhtCode: Int)
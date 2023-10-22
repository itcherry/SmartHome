package com.smarthome.smarthome.controller.model

import com.google.gson.annotations.SerializedName

data class TemperatureHumidity(@SerializedName("temperature") val temperature: Double,
                               @SerializedName("humidity") val humidity: Double)
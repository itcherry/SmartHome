package com.smarthome.SmartHome.model

import com.google.gson.annotations.SerializedName

data class TemperatureHumidity(@SerializedName("temperature") val temperature: Double,
                                  @SerializedName("humidity") val humidity: Double)
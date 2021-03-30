package com.smarthome.SmartHome.controller.model

import com.google.gson.annotations.SerializedName

data class TimeRange(@SerializedName("startTime") val startTime: Int,
                     @SerializedName("endTime") val endTime: Int)
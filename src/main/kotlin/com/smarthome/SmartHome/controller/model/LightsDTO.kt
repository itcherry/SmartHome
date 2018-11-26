package com.smarthome.SmartHome.controller.model

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.google.gson.annotations.SerializedName
import com.smarthome.SmartHome.controller.model.deserializer.LightsDtoDeserializer

@JsonDeserialize(using = LightsDtoDeserializer::class)
data class LightsDTO(@SerializedName("isOn") val isOn: Boolean)
package com.smarthome.SmartHome.controller.model.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.google.gson.Gson
import com.smarthome.SmartHome.controller.model.LightsDTO


class LightsDtoDeserializer: JsonDeserializer<LightsDTO>(){
    override fun deserialize(parser: JsonParser, deserializationContext: DeserializationContext?): LightsDTO? {
        val node = parser.codec.readTree<JsonNode>(parser)
        val results = Gson().fromJson(node.asText(), Array<LightsDTO>::class.java)

        return if(results.isNotEmpty()){
            results[0]
        } else {
            null
        }
    }
}

package com.smarthome.SmartHome.controller

import com.smarthome.SmartHome.controller.model.ResponseBody
import com.smarthome.SmartHome.service.impl.pin.model.SensorToPin
import com.smarthome.SmartHome.service.PinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(CORRIDOR_VALUE)
class CorridorController @Autowired constructor(
        private val pinService: PinService
) {
    @RequestMapping(method = [(RequestMethod.PUT)], value = [LIGHT_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun pulseLightState() {
        pinService.pulseMultipurposeSensor(SensorToPin.CORRIDOR_LIGHT_OUTPUT)
    }

    @RequestMapping(method = [(RequestMethod.GET)], value = [LIGHT_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getLightState() = ResponseBody(ResponseBody.SUCCESS, null, !pinService.getSensor(SensorToPin.CORRIDOR_LIGHT_INPUT))
}
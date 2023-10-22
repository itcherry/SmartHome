package com.smarthome.smarthome.controller

import com.smarthome.smarthome.service.impl.pin.model.SensorToPin
import com.smarthome.smarthome.service.RaspberryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import com.smarthome.smarthome.controller.model.ResponseBody

@RestController
@RequestMapping(CORRIDOR_VALUE)
class CorridorController @Autowired constructor(
        private val raspberryService: RaspberryService
) {
    @RequestMapping(method = [(RequestMethod.PUT)], value = [LIGHT_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun pulseLightState() {
        raspberryService.pulseLightState(SensorToPin.CORRIDOR_LIGHT_OUTPUT)
    }

    @RequestMapping(method = [(RequestMethod.GET)], value = [LIGHT_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getLightState() = ResponseBody(
        ResponseBody.SUCCESS,
        null,
        raspberryService.getLightState(SensorToPin.CORRIDOR_LIGHT_INPUT)
    )
}
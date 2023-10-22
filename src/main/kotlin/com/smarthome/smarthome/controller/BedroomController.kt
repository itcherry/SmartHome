package com.smarthome.smarthome.controller

import com.smarthome.smarthome.service.impl.pin.model.SensorToPin
import com.smarthome.smarthome.service.RaspberryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import com.smarthome.smarthome.controller.model.ResponseBody

@RestController
@RequestMapping(BEDROOM_VALUE)
class BedroomController @Autowired constructor(
    private val raspberryService: RaspberryService
) {
    @RequestMapping(method = [(RequestMethod.PUT)], value = [ROZETKA_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun setRozetkaState(@RequestParam(IS_ENABLE_FIELD) isEnable: Boolean) {
        raspberryService.setRozetkaState(SensorToPin.BEDROOM_ROZETKA_OUTPUT, isEnable)
    }

    @RequestMapping(method = [(RequestMethod.GET)], value = [ROZETKA_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getRozetkaState() =
        ResponseBody(
            ResponseBody.SUCCESS,
            null,
            raspberryService.getRozetkaState(SensorToPin.BEDROOM_ROZETKA_OUTPUT)
        )

    @RequestMapping(method = [(RequestMethod.PUT)], value = [LIGHT_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun pulseLightState() {
        raspberryService.pulseLightState(SensorToPin.BEDROOM_LIGHT_OUTPUT)
    }

    @RequestMapping(method = [(RequestMethod.GET)], value = [LIGHT_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getLightState() =
        ResponseBody(
            ResponseBody.SUCCESS,
            null,
            raspberryService.getLightState(SensorToPin.BEDROOM_LIGHT_INPUT)
        )
}
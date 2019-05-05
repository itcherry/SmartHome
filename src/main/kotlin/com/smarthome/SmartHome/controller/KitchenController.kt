package com.smarthome.SmartHome.controller

import com.smarthome.SmartHome.controller.model.ResponseBody
import com.smarthome.SmartHome.model.SensorToPin
import com.smarthome.SmartHome.service.PinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(KITCHEN_VALUE)
class KitchenController @Autowired constructor(
        private val pinService: PinService
) {
    @RequestMapping(method = [(RequestMethod.PUT)], value = [LIGHT_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun setLightState(@RequestParam(IS_ENABLE_FIELD) isEnable: Boolean) {
        pinService.setSensor(SensorToPin.KITCHEN_LIGHT_OUTPUT, isEnable)
    }

    @RequestMapping(method = [(RequestMethod.GET)], value = [LIGHT_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getLightState() = ResponseBody(ResponseBody.SUCCESS, null, pinService.getSensor(SensorToPin.KITCHEN_LIGHT_INPUT))
}
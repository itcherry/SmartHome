package com.smarthome.SmartHome.controller

import com.smarthome.SmartHome.model.SensorToPin
import com.smarthome.SmartHome.service.PinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bedroom")
class BedroomController @Autowired constructor(
        private val pinService: PinService
){
    @RequestMapping(method = [(RequestMethod.PUT)],value = ["/rozetka"])
    @ResponseStatus(HttpStatus.OK)
    fun setRozetkaState(@RequestParam("isEnable") isEnable: Boolean){
        pinService.setSensor(SensorToPin.BEDROOM_ROZETKA_OUTPUT, isEnable)
    }

    @RequestMapping(method = [(RequestMethod.GET)],value = ["/rozetka"])
    @ResponseStatus(HttpStatus.OK)
    fun getRozetkaState() = pinService.getOutSensor(SensorToPin.BEDROOM_ROZETKA_OUTPUT)

    @RequestMapping(method = [(RequestMethod.PUT)],value = ["/light"])
    @ResponseStatus(HttpStatus.OK)
    fun setLightState(@RequestParam("isEnable") isEnable: Boolean){
        pinService.setSensor(SensorToPin.BEDROOM_LIGHT_OUTPUT, isEnable)
    }

    @RequestMapping(method = [(RequestMethod.GET)],value = ["/light"])
    @ResponseStatus(HttpStatus.OK)
    fun getLightState() = pinService.getSensor(SensorToPin.BEDROOM_LIGHT_INPUT)
}
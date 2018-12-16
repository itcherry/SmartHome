package com.smarthome.SmartHome.controller

import com.smarthome.SmartHome.model.SensorToPin
import com.smarthome.SmartHome.service.PinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/living-room")
class LivingRoomController @Autowired constructor(
        private val pinService: PinService
){
    @RequestMapping(method = [(RequestMethod.PUT)],value = ["/rozetka"])
    @ResponseStatus(HttpStatus.OK)
    fun setRozetkaState(@RequestParam("isEnable") isEnable: Boolean){
        pinService.setSensor(SensorToPin.LIVING_ROOM_ROZETKA_OUTPUT, isEnable)
    }

    @RequestMapping(method = [(RequestMethod.GET)],value = ["/rozetka"])
    @ResponseStatus(HttpStatus.OK)
    fun getRozetkaState() = pinService.getOutSensor(SensorToPin.LIVING_ROOM_ROZETKA_OUTPUT)
}
package com.smarthome.SmartHome.controller

import com.smarthome.SmartHome.model.SensorToPin
import com.smarthome.SmartHome.service.PinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/door")
class DoorController @Autowired constructor(
        private val pinService: PinService
){
    @RequestMapping(method = [(RequestMethod.PUT)])
    @ResponseStatus(HttpStatus.OK)
    fun setDoorState(@RequestParam("doOpen") doOpen: Boolean){
        pinService.setSensor(SensorToPin.DOOR_OUTPUT, doOpen)
    }

    @RequestMapping(method = [(RequestMethod.GET)])
    @ResponseStatus(HttpStatus.OK)
    fun getDoorState() = pinService.getOutSensor(SensorToPin.DOOR_OUTPUT)
}
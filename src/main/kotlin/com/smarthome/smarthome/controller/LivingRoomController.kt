package com.smarthome.smarthome.controller

import com.smarthome.smarthome.service.impl.pin.model.SensorToPin
import com.smarthome.smarthome.service.RaspberryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import com.smarthome.smarthome.controller.model.ResponseBody

@RestController
@RequestMapping(LIVING_ROOM_VALUE)
class LivingRoomController @Autowired constructor(
        private val raspberryService: RaspberryService
) {
    @RequestMapping(method = [(RequestMethod.PUT)], value = [ROZETKA_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun setRozetkaState(@RequestParam(IS_ENABLE_FIELD) isEnable: Boolean) {
        raspberryService.setRozetkaState(SensorToPin.LIVING_ROOM_ROZETKA_OUTPUT, isEnable)
    }

    @RequestMapping(method = [(RequestMethod.GET)], value = [ROZETKA_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getRozetkaState() = ResponseBody(
        ResponseBody.SUCCESS,
        null,
        raspberryService.getRozetkaState(SensorToPin.LIVING_ROOM_ROZETKA_OUTPUT)
    )

    @RequestMapping(method = [(RequestMethod.PUT)], value = [AQUARIUM_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun setAquariumState(@RequestParam(IS_ENABLE_FIELD) isEnable: Boolean) {
        raspberryService.setRozetkaState(SensorToPin.AQUARIUM_OUTPUT, isEnable)
    }

    @RequestMapping(method = [(RequestMethod.GET)], value = [AQUARIUM_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getAquariumState() = ResponseBody(
        ResponseBody.SUCCESS,
        null,
        raspberryService.getRozetkaState(SensorToPin.AQUARIUM_OUTPUT)
    )

    @RequestMapping(method = [(RequestMethod.PUT)], value = [LIGHT_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun pulseLightState() {
        raspberryService.pulseLightState(SensorToPin.LIVING_ROOM_LIGHT_OUTPUT)
    }

    @RequestMapping(method = [(RequestMethod.GET)], value = [LIGHT_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getLightState() = ResponseBody(
        ResponseBody.SUCCESS,
        null,
        raspberryService.getLightState(SensorToPin.LIVING_ROOM_LIGHT_INPUT)
    )
}
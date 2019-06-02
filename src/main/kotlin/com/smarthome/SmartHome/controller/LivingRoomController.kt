package com.smarthome.SmartHome.controller

import com.smarthome.SmartHome.controller.model.ResponseBody
import com.smarthome.SmartHome.service.impl.pin.model.SensorToPin
import com.smarthome.SmartHome.service.PinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(LIVING_ROOM_VALUE)
class LivingRoomController @Autowired constructor(
        private val pinService: PinService
) {
    @RequestMapping(method = [(RequestMethod.PUT)], value = [ROZETKA_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun setRozetkaState(@RequestParam(IS_ENABLE_FIELD) isEnable: Boolean) {
        pinService.setMultipurposeSensor(SensorToPin.LIVING_ROOM_ROZETKA_OUTPUT, isEnable)
    }

    @RequestMapping(method = [(RequestMethod.GET)], value = [ROZETKA_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getRozetkaState() = ResponseBody(ResponseBody.SUCCESS, null, pinService.getMultipurposeSensor(SensorToPin.LIVING_ROOM_ROZETKA_OUTPUT))

    @RequestMapping(method = [(RequestMethod.PUT)], value = [LIGHT_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun setLightState(@RequestParam(IS_ENABLE_FIELD) isEnable: Boolean) {
        pinService.setSensor(SensorToPin.LIVING_ROOM_LIGHT_OUTPUT, isEnable)
    }

    @RequestMapping(method = [(RequestMethod.GET)], value = [LIGHT_VALUE])
    @ResponseStatus(HttpStatus.OK)
    fun getLightState() = ResponseBody(ResponseBody.SUCCESS, null, pinService.getSensor(SensorToPin.LIVING_ROOM_LIGHT_INPUT))
}
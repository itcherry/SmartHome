package com.smarthome.smarthome.controller

import com.smarthome.smarthome.controller.model.ResponseBody
import com.smarthome.smarthome.service.impl.pin.model.SensorToPin
import com.smarthome.smarthome.service.RaspberryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(ALARM_VALUE)
class AlarmController @Autowired constructor(
    private val raspberryService: RaspberryService
) {
    @RequestMapping(method = [(RequestMethod.PUT)])
    @ResponseStatus(HttpStatus.OK)
    fun setAlarmState(@RequestParam(IS_ENABLE_FIELD) isEnable: Boolean) {
        raspberryService.setRozetkaState(SensorToPin.ALARM_OUTPUT, isEnable)
    }

    @RequestMapping(method = [(RequestMethod.GET)])
    @ResponseStatus(HttpStatus.OK)
    fun getAlarmState() =
        ResponseBody(
            ResponseBody.SUCCESS,
            null,
            raspberryService.getRozetkaState(SensorToPin.ALARM_OUTPUT)
        )
}
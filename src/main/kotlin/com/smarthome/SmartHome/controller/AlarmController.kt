package com.smarthome.SmartHome.controller

import com.smarthome.SmartHome.controller.model.ResponseBody
import com.smarthome.SmartHome.service.impl.pin.model.SensorToPin
import com.smarthome.SmartHome.service.PinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(ALARM_VALUE)
class AlarmController @Autowired constructor(
        private val pinService: PinService
) {
    @RequestMapping(method = [(RequestMethod.PUT)])
    @ResponseStatus(HttpStatus.OK)
    fun setAlarmState(@RequestParam(IS_ENABLE_FIELD) isEnable: Boolean) {
        pinService.setMultipurposeSensor(SensorToPin.ALARM_OUTPUT, isEnable)
    }

    @RequestMapping(method = [(RequestMethod.GET)])
    @ResponseStatus(HttpStatus.OK)
    fun getAlarmState() = ResponseBody(ResponseBody.SUCCESS, null, pinService.getMultipurposeSensor(SensorToPin.ALARM_OUTPUT))
}
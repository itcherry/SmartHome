package com.smarthome.SmartHome.controller

import com.smarthome.SmartHome.model.SensorToPin
import com.smarthome.SmartHome.service.PinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(ALARM_VALUE)
class AlarmController @Autowired constructor(
        private val pinService: PinService
){
    @RequestMapping(method = [(RequestMethod.PUT)])
    @ResponseStatus(HttpStatus.OK)
    fun setAlarmState(@RequestParam(IS_ENABLE_FIELD) isEnable: Boolean){
        pinService.setSensor(SensorToPin.ALARM_OUTPUT, isEnable)
    }

    @RequestMapping(method = [(RequestMethod.GET)])
    @ResponseStatus(HttpStatus.OK)
    fun getAlarmState() = pinService.getOutSensor(SensorToPin.ALARM_OUTPUT)
}
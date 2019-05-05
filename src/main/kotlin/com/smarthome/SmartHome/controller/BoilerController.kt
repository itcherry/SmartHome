package com.smarthome.SmartHome.controller

import com.smarthome.SmartHome.controller.model.ResponseBody
import com.smarthome.SmartHome.model.SensorToPin
import com.smarthome.SmartHome.service.PinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(BOILER_VALUE)
class BoilerController @Autowired constructor(
        private val pinService: PinService
){
    @RequestMapping(method = [(RequestMethod.PUT)])
    @ResponseStatus(HttpStatus.OK)
    fun setBoilerState(@RequestParam(IS_ENABLE_FIELD) isEnabled: Boolean){
        pinService.setSensor(SensorToPin.BOILER_OUTPUT, isEnabled)
    }

    @RequestMapping(method = [(RequestMethod.GET)])
    @ResponseStatus(HttpStatus.OK)
    fun getBoilerState() =  ResponseBody(ResponseBody.SUCCESS, null, pinService.getOutSensor(SensorToPin.BOILER_OUTPUT))
}
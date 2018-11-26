package com.smarthome.SmartHome.controller

import com.smarthome.SmartHome.controller.model.SensorResult
import com.smarthome.SmartHome.service.HumiTempService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/humidity-temperature")
class HumiTempController @Autowired constructor(
        private val humiTempService: HumiTempService
) {
    @RequestMapping(method = [(RequestMethod.GET)])
    @ResponseStatus(HttpStatus.OK)
    fun getSensorsValues(): List<SensorResult> = humiTempService.getAllSensorsData()
}
package com.smarthome.SmartHome.controller

import com.smarthome.SmartHome.controller.model.ResponseBody
import com.smarthome.SmartHome.service.PinService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pin")
class PinController @Autowired constructor(
        private val pinService: PinService
) {
    @RequestMapping(method = [(RequestMethod.PUT)], value = ["/{pinId}"])
    @ResponseStatus(HttpStatus.OK)
    fun setPin(@PathVariable("pinId") pinId: Int,
               @RequestParam("isEnable") isEnable: Boolean) {
        pinService.setPin(pinId, isEnable)
    }

    @RequestMapping(method = [(RequestMethod.PUT)], value = ["/pulse/{pinId}"])
    @ResponseStatus(HttpStatus.OK)
    fun pulsePin(@PathVariable("pinId") pinId: Int) {
        pinService.pulsePin(pinId)
    }

    @RequestMapping(method = [(RequestMethod.GET)], value = ["/{pinId}"])
    @ResponseStatus(HttpStatus.OK)
    fun getPin(@PathVariable("pinId") pinId: Int) = ResponseBody(ResponseBody.SUCCESS, null, pinService.getPin(pinId))

}
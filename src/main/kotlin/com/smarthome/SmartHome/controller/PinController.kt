package com.smarthome.SmartHome.controller

import com.smarthome.SmartHome.controller.model.ResponseBody
import com.smarthome.SmartHome.service.FcmService
import com.smarthome.SmartHome.service.PinService
import com.smarthome.SmartHome.service.impl.fcm.builder.FcmPushDirector
import com.smarthome.SmartHome.service.impl.fcm.builder.HighCpuTemperatureFcmPushBuilder
import com.smarthome.SmartHome.service.impl.fcm.builder.NeptunAlarmFcmPushBuilder
import com.smarthome.SmartHome.service.impl.fcm.builder.SecurityAlertFcmPushBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pin")
class PinController @Autowired constructor(
        private val pinService: PinService,
        fcmService: FcmService
) {
    init {
        pinService.setNeptunAlarmListener {
            fcmService.sendPushNotificationsToUsers(FcmPushDirector(NeptunAlarmFcmPushBuilder())
                    .buildFcmPush(null, null))
        }

        pinService.setSecurityAlarmListener {
            fcmService.sendPushNotificationsToUsers(FcmPushDirector(SecurityAlertFcmPushBuilder())
                    .buildFcmPush(null, null))
        }
    }

    @RequestMapping(method = [(RequestMethod.PUT)], value = ["/{pinId}"])
    @ResponseStatus(HttpStatus.OK)
    fun setPin(@PathVariable("pinId") pinId: Int,
               @RequestParam("isEnable") isEnable: Boolean) {
        pinService.setMultipurposePin(pinId, isEnable)
    }

    @RequestMapping(method = [(RequestMethod.PUT)], value = ["/pulse/{pinId}"])
    @ResponseStatus(HttpStatus.OK)
    fun pulsePin(@PathVariable("pinId") pinId: Int) {
        pinService.pulseMultipurposePin(pinId)
    }

    @RequestMapping(method = [(RequestMethod.GET)], value = ["/{pinId}"])
    @ResponseStatus(HttpStatus.OK)
    fun getPin(@PathVariable("pinId") pinId: Int) = ResponseBody(ResponseBody.SUCCESS, null, pinService.getMultipurposePin(pinId))

}
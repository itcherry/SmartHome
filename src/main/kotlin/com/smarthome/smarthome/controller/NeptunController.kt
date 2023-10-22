package com.smarthome.smarthome.controller

import com.smarthome.smarthome.service.FcmService
import com.smarthome.smarthome.service.impl.pin.model.SensorToPin
import com.smarthome.smarthome.service.PinService
import com.smarthome.smarthome.service.impl.fcm.builder.FcmPushDirector
import com.smarthome.smarthome.service.impl.fcm.builder.NeptunAlarmFcmPushBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import com.smarthome.smarthome.controller.model.ResponseBody

@RestController
@RequestMapping(NEPTUN_VALUE)
class NeptunController @Autowired constructor(
        private val pinService: PinService,
        private val fcmService: FcmService
) {

    init {
        println("Neptun alarm listener has been setted up")
        pinService.setNeptunAlarmListener {
            println("Neptun alarm!!!!")
            fcmService.sendPushNotificationsToUsers(FcmPushDirector(NeptunAlarmFcmPushBuilder())
                    .buildFcmPush(null, null))
        }
    }

    @RequestMapping(method = [(RequestMethod.GET)])
    @ResponseStatus(HttpStatus.OK)
    fun getNeptunState() = ResponseBody(
        ResponseBody.SUCCESS,
        null,
        !pinService.getSensor(SensorToPin.NEPTUN_INPUT)
    )
}
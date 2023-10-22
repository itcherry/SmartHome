package com.smarthome.smarthome.controller

import com.smarthome.smarthome.error.CustomError
import com.smarthome.smarthome.exception.ZetProException
import com.smarthome.smarthome.service.*
import com.smarthome.smarthome.service.impl.fcm.builder.FcmPushDirector
import com.smarthome.smarthome.service.impl.fcm.builder.SecurityAlertFcmPushBuilder
import com.smarthome.smarthome.service.impl.fcm.builder.SecurityEnabledFcmBushBuilder
import com.smarthome.smarthome.service.impl.pin.model.SensorToPin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import com.smarthome.smarthome.controller.model.ResponseBody

@RestController
@RequestMapping(SECURITY_VALUE)
class SecurityController @Autowired constructor(
        private val raspberryService: RaspberryService,
        private val fcmService: FcmService,
        private val pinService: PinService,
        private val mailSenderService: MailSenderService,
        private val zetProService: ZetProService
) {
    init { setSecurityAlarmListener() }

    @RequestMapping(method = [(RequestMethod.PUT)])
    @ResponseStatus(HttpStatus.OK)
    fun doEnable(@RequestParam("doEnable") doEnable: Boolean): ResponseBody<*> {

        try {
            zetProService.enableZetProSecurity(doEnable)
        } catch (e: ZetProException) {
            return ResponseBody(
                ResponseBody.ERROR,
                CustomError(e.code, e.message),
                null
            )
        }

        if (doEnable) {
            raspberryService.enableSecurity()
        } else {
            raspberryService.disableSecurity()
        }

        setSecurityAlarmListener()
        fcmService.sendPushNotificationsToUsers(FcmPushDirector(SecurityEnabledFcmBushBuilder())
                .buildFcmPush(null, doEnable))

        return ResponseBody(
            ResponseBody.SUCCESS,
            null,
            null
        )
    }

    @RequestMapping(method = [(RequestMethod.GET)])
    @ResponseStatus(HttpStatus.OK)
    fun isSecurityEnabled() = ResponseBody(
        ResponseBody.SUCCESS,
        null,
        raspberryService.isSecurityEnabled()
    )

    private fun setSecurityAlarmListener() {
        if (raspberryService.isSecurityEnabled()) {
            println("Security alarm listener has been setted up")
            pinService.setSecurityAlarmListener {
                println("Security alarm!!!! Pin state: ${it.state.value}")
                pinService.setMultipurposeSensor(SensorToPin.ALARM_OUTPUT, true)
                pinService.pulseMultipurposeSensor(SensorToPin.CORRIDOR_LIGHT_OUTPUT)
                pinService.pulseMultipurposeSensor(SensorToPin.KITCHEN_LIGHT_OUTPUT)
                pinService.pulseMultipurposeSensor(SensorToPin.BEDROOM_LIGHT_OUTPUT)
                pinService.pulseMultipurposeSensor(SensorToPin.LIVING_ROOM_LIGHT_OUTPUT)
                fcmService.sendPushNotificationsToUsers(FcmPushDirector(SecurityAlertFcmPushBuilder())
                        .buildFcmPush(null, null))
                mailSenderService.sendEmailWithPhotoFromCamera()
            }
        } else {
            println("Security alarm listeners removed")

            if(!pinService.getSensor(SensorToPin.CORRIDOR_LIGHT_INPUT)) {
                pinService.pulseMultipurposeSensor(SensorToPin.CORRIDOR_LIGHT_OUTPUT)
            }
            if(!pinService.getSensor(SensorToPin.KITCHEN_LIGHT_INPUT)) {
                pinService.pulseMultipurposeSensor(SensorToPin.KITCHEN_LIGHT_OUTPUT)
            }
            if(!pinService.getSensor(SensorToPin.BEDROOM_LIGHT_INPUT)) {
                pinService.pulseMultipurposeSensor(SensorToPin.BEDROOM_LIGHT_OUTPUT)
            }
            if(!pinService.getSensor(SensorToPin.LIVING_ROOM_LIGHT_INPUT)) {
                pinService.pulseMultipurposeSensor(SensorToPin.LIVING_ROOM_LIGHT_OUTPUT)
            }

            pinService.setMultipurposeSensor(SensorToPin.ALARM_OUTPUT, false)
            pinService.setSecurityAlarmListener(null)
        }
    }

}
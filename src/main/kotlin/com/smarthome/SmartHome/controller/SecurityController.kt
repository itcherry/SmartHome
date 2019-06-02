package com.smarthome.SmartHome.controller

import com.smarthome.SmartHome.controller.model.ResponseBody
import com.smarthome.SmartHome.service.FcmService
import com.smarthome.SmartHome.service.RaspberryService
import com.smarthome.SmartHome.service.impl.fcm.builder.FcmPushDirector
import com.smarthome.SmartHome.service.impl.fcm.builder.SecurityEnabledFcmBushBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(SECURITY_VALUE)
class SecurityController @Autowired constructor(
        private val raspberryService: RaspberryService,
        private val fcmService: FcmService
) {

    @RequestMapping(method = [(RequestMethod.PUT)])
    @ResponseStatus(HttpStatus.OK)
    fun doEnable(@RequestParam("doEnable") doEnable: Boolean): ResponseBody<*> {
        if(doEnable){
            raspberryService.enableSecurity()
        } else {
            raspberryService.disableSecurity()
        }

        fcmService.sendPushNotificationsToUsers(FcmPushDirector(SecurityEnabledFcmBushBuilder())
                .buildFcmPush(null, doEnable))

        return ResponseBody(ResponseBody.SUCCESS, null, null)
    }

    @RequestMapping(method = [(RequestMethod.GET)])
    @ResponseStatus(HttpStatus.OK)
    fun isSecurityEnabled() = ResponseBody(ResponseBody.SUCCESS, null, raspberryService.isSecurityEnabled())
}
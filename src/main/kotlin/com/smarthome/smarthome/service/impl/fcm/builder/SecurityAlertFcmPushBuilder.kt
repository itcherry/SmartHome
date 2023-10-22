package com.smarthome.smarthome.service.impl.fcm.builder

import com.smarthome.smarthome.controller.model.FcmPush
import com.smarthome.smarthome.controller.model.Notification

class SecurityAlertFcmPushBuilder : FcmPushBuilder() {
    override fun setNotification() {
        fcmPush.notification = Notification(
            "Someone in your home",
            "Catch that bastards as fast as you can"
        )
    }

    override fun setData(data: Any?) {
        fcmPush.data = FcmPush.FcmPushData(
                NotificationMessageType.SECURITY_ALARM.code,
                data)
    }

    override fun setPriority() {
        fcmPush.priority = "normal"
    }
}
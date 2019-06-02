package com.smarthome.SmartHome.service.impl.fcm.builder

import com.smarthome.SmartHome.controller.model.FcmPush
import com.smarthome.SmartHome.controller.model.Notification

class NeptunAlarmFcmPushBuilder : FcmPushBuilder() {
    override fun setNotification() {
        fcmPush.notification = Notification("Neptun is screaming", "You got too much water on your floor. Neptun closed all water engines, but fix it faster.")
    }

    override fun setData(data: Any) {
        fcmPush.data = FcmPush.FcmPushData(
                NotificationMessageType.NEPTUN_ALARM.code,
                data)
    }

    override fun setPriority() {
        fcmPush.priority = "normal"
    }
}
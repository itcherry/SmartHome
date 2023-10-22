package com.smarthome.smarthome.service.impl.fcm.builder


import com.smarthome.smarthome.controller.model.FcmPush
import com.smarthome.smarthome.controller.model.Notification

class FireAlarmFcmPushBuilder : FcmPushBuilder() {
    override fun setNotification() {
        fcmPush.notification = Notification(
            "There is fire at home",
            "Seems like something happened at home. There is fire."
        )
    }

    override fun setData(data: Any?) {
        fcmPush.data = FcmPush.FcmPushData(
                NotificationMessageType.FIRE_ALARM.code,
                data)
    }

    override fun setPriority() {
        fcmPush.priority = "normal"
    }
}
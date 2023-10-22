package com.smarthome.smarthome.service.impl.fcm.builder

import com.smarthome.smarthome.controller.model.FcmPush
import com.smarthome.smarthome.controller.model.Notification

class HighCpuTemperatureFcmPushBuilder : FcmPushBuilder() {
    override fun setNotification() {
        fcmPush.notification = Notification(
            "CPU temperature is critical",
            "Your raspberry is too hot. Check it and try to cool it."
        )
    }

    override fun setData(data: Any) {
        fcmPush.data = FcmPush.FcmPushData(
                NotificationMessageType.HIGH_CPU_TEMPERATURE.code,
                data)
    }

    override fun setPriority() {
        fcmPush.priority = "normal"
    }
}
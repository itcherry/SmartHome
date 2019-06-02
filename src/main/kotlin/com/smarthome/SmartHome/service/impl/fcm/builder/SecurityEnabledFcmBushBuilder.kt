package com.smarthome.SmartHome.service.impl.fcm.builder

import com.smarthome.SmartHome.controller.model.FcmPush
import com.smarthome.SmartHome.controller.model.Notification

class SecurityEnabledFcmBushBuilder : FcmPushBuilder() {
    override fun setNotification() {
        fcmPush.notification = Notification("Security state change", "Security has been enabled or disabled")
    }

    override fun setData(data: Any) {
        fcmPush.data = FcmPush.FcmPushData(
                NotificationMessageType.SECURITY_ENABLED.code,
                data)
    }

    override fun setPriority() {
        fcmPush.priority = "normal"
    }
}
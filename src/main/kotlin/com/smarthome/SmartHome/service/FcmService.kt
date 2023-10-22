package com.smarthome.smarthome.service

import com.smarthome.smarthome.controller.model.FcmPush

interface FcmService {
    fun sendPushNotificationsToUsers(push: FcmPush?)
}

package com.smarthome.SmartHome.service;

import com.smarthome.SmartHome.controller.model.FcmPush;

public interface FcmService {
    void sendPushNotificationsToUsers(FcmPush push);
}

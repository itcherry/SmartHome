package com.smarthome.smarthome.service.impl.fcm.builder;

import com.smarthome.smarthome.controller.model.FcmPush;

public class FcmPushDirector {
    private FcmPushBuilder fcmPushBuilder;
    public FcmPushDirector(FcmPushBuilder fcmPushBuilder) {
        this.fcmPushBuilder = fcmPushBuilder;
    }

    public FcmPush buildFcmPush(String to, Object dataToSend){
        fcmPushBuilder.createFcmPush();
        fcmPushBuilder.setTo(to);
        fcmPushBuilder.setData(dataToSend);
        fcmPushBuilder.setNotification();
        fcmPushBuilder.setPriority();
        return fcmPushBuilder.getFcmPush();
    }
}

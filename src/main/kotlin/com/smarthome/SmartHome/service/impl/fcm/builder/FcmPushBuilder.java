package com.smarthome.SmartHome.service.impl.fcm.builder;


import com.smarthome.SmartHome.controller.model.FcmPush;

public abstract class FcmPushBuilder {
    protected FcmPush fcmPush;
    public void createFcmPush() {
        fcmPush = new FcmPush();
    }

    public void setTo(String to){
        fcmPush.setTo(to);
    }

    public abstract void setNotification();

    public abstract void setData(Object data);

    public abstract void setPriority();

    public FcmPush getFcmPush() {
        return fcmPush;
    }
}

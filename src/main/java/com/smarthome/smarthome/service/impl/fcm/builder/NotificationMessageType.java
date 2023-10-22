package com.smarthome.smarthome.service.impl.fcm.builder;

public enum NotificationMessageType {
    HIGH_CPU_TEMPERATURE(1),
    NEPTUN_ALARM(2),
    SECURITY_ALARM(3),
    SECURITY_ENABLED(4),
    FIRE_ALARM(5);

    private int code;

    NotificationMessageType(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

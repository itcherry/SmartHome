package com.smarthome.SmartHome.error;

public enum FcmError implements ApiError  {
    CANT_SEND_MESSAGE_NOTIFICATION(151, ErrorMessages.CANT_SEND_MESSAGE_NOTIFICATION),
    EMPTY_FCM_TOKEN(152, ErrorMessages.EMPTY_FCM_TOKEN);

    private int errorCode;
    private String errorMessage;

    FcmError(int errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode(){
        return this.errorCode;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }
}

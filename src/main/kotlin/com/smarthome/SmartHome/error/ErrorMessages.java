package com.smarthome.SmartHome.error;

public interface ErrorMessages {
    String EMPTY_FCM_TOKEN = "Empty fcm token";

    String INCORRECT_PASSWORD = "Invalid password!";
    String INCORRECT_CREDENTIALS = "Incorrect credentials";

    String CANT_SEND_MESSAGE_NOTIFICATION = "Can't send message push notification!";

    String EMPTY_JWT_TOKEN = "JWT claims string is empty";
    String UNSUPPORTED_JWT_TOKEN = "Unsupported JWT token";
    String EXPIRED_JWT_TOKEN = "Expired JWT token";
    String INVALID_JWT_TOKEN = "Invalid JWT token";
    String ACCESS_DENIED = "You are not allowed to do this!";

    String NO_SUCH_USER = "No such user!!!";

    String ZET_PRO_UNAUTHORISED = "ZetPro unauthorised";
    String ZET_PRO_NOT_SUCCESSFULL = "ZetPro not successful request";
}

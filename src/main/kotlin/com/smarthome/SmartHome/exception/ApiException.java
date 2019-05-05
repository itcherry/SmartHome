package com.smarthome.SmartHome.exception;

import com.smarthome.SmartHome.error.ApiError;

public interface ApiException {

    int getCode();

    String getMessage();

    ApiError getError();
}

package com.smarthome.smarthome.exception;

import com.smarthome.smarthome.error.ApiError;

public interface ApiException {

    int getCode();

    String getMessage();

    ApiError getError();
}

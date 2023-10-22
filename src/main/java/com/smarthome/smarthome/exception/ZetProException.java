package com.smarthome.smarthome.exception;


import com.smarthome.smarthome.error.ZetProError;

import java.text.MessageFormat;

public class ZetProException extends RuntimeException implements ApiException {
    private final ZetProError error;

    public ZetProException(ZetProError error, Object... messageArguments) {
        super(MessageFormat.format(error.getErrorMessage(), messageArguments));
        this.error = error;
    }

    public ZetProException(ZetProError error, final Throwable cause, Object... messageArguments) {
        super(MessageFormat.format(error.getErrorMessage(), messageArguments), cause);
        this.error = error;
    }

    public int getCode() {
        return error.getErrorCode();
    }

    public String getMessage(){
        return error.getErrorMessage();
    }

    public ZetProError getError(){
        return error;
    }
}
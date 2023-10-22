package com.smarthome.smarthome.exception;

import com.smarthome.smarthome.error.AuthenticationError;
import com.smarthome.smarthome.error.FcmError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class ExceptionFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionFactory.class);

    public static FcmException create(final Throwable cause, final FcmError error, final Object... messageArguments) {
        LOG.error(MessageFormat.format(error.getErrorMessage(), messageArguments), cause);
        return new FcmException (error, cause, messageArguments);
    }

    public static FcmException create(final FcmError error, final Object... messageArguments) {
        LOG.error(MessageFormat.format(error.getErrorMessage(), messageArguments));
        return new FcmException(error, messageArguments);
    }

    public static AuthenticationException create(final Throwable cause, final AuthenticationError error, final Object... messageArguments) {
        LOG.error(MessageFormat.format(error.getErrorMessage(), messageArguments), cause);
        return new AuthenticationException (error, cause, messageArguments);
    }

    public static AuthenticationException create(final AuthenticationError error, final Object... messageArguments) {
        LOG.error(MessageFormat.format(error.getErrorMessage(), messageArguments));
        return new AuthenticationException(error, messageArguments);
    }
}

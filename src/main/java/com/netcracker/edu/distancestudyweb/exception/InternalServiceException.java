package com.netcracker.edu.distancestudyweb.exception;

public class InternalServiceException extends ServiceException {
    public InternalServiceException() {
    }

    public InternalServiceException(String message) {
        super(message);
    }

    public InternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServiceException(Throwable cause) {
        super(cause);
    }

    public InternalServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

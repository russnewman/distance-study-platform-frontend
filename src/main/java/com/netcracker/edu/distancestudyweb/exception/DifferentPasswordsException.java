package com.netcracker.edu.distancestudyweb.exception;

public class DifferentPasswordsException extends Exception {
    public DifferentPasswordsException() {
    }

    public DifferentPasswordsException(String message) {
        super(message);
    }

    public DifferentPasswordsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DifferentPasswordsException(Throwable cause) {
        super(cause);
    }

    public DifferentPasswordsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

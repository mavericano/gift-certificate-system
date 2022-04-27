package com.epam.esm.core.exception;

public class KeyHolderException extends RuntimeException {
    public KeyHolderException() {
        super();
    }

    public KeyHolderException(String message) {
        super(message);
    }

    public KeyHolderException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyHolderException(Throwable cause) {
        super(cause);
    }

    protected KeyHolderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

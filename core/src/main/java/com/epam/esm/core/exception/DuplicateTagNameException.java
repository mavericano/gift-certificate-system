package com.epam.esm.core.exception;

public class DuplicateTagNameException extends RuntimeException {
    public DuplicateTagNameException() {
        super();
    }

    public DuplicateTagNameException(String message) {
        super(message);
    }

    public DuplicateTagNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateTagNameException(Throwable cause) {
        super(cause);
    }

    protected DuplicateTagNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

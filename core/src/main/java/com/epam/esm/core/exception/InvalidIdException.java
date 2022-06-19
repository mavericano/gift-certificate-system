package com.epam.esm.core.exception;

import org.springframework.context.i18n.LocaleContextHolder;

public class InvalidIdException extends RuntimeException {

    @Override
    public String getLocalizedMessage() {
        String messageKey = "invalidIdExceptionMessage";
        return ExceptionMessageHandler.getMessage(messageKey, LocaleContextHolder.getLocale());
    }

    public InvalidIdException() {
        super();
    }

    public InvalidIdException(String message) {
        super(message);
    }

    public InvalidIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidIdException(Throwable cause) {
        super(cause);
    }

    protected InvalidIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

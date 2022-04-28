package com.epam.esm.core.exception;

import org.springframework.context.i18n.LocaleContextHolder;

public class KeyHolderException extends RuntimeException {
    private final String messageKey = "keyHolderExceptionMessage";

    @Override
    public String getLocalizedMessage() {
        return ExceptionMessageHandler.getMessage(messageKey, LocaleContextHolder.getLocale());
    }

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

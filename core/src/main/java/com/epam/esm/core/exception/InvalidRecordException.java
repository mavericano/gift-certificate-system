package com.epam.esm.core.exception;

import org.springframework.context.i18n.LocaleContextHolder;

public class InvalidRecordException extends RuntimeException {
    private String messageKey;

    @Override
    public String getLocalizedMessage() {
        return ExceptionMessageHandler.getMessage(messageKey, LocaleContextHolder.getLocale());
    }

    public InvalidRecordException() {
        super();
    }

    public InvalidRecordException(String messageKey) {
        this.messageKey = messageKey;
    }

    public InvalidRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRecordException(Throwable cause) {
        super(cause);
    }

    protected InvalidRecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

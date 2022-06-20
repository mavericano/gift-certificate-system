package com.epam.esm.core.exception;

import org.springframework.context.i18n.LocaleContextHolder;

public class NoSuchRecordException extends RuntimeException {

    @Override
    public String getLocalizedMessage() {
        String messageKey = "noSuchRecordExceptionMessage";
        return ExceptionMessageHandler.getMessage(messageKey, LocaleContextHolder.getLocale());
    }
    public NoSuchRecordException() {
        super();
    }

    public NoSuchRecordException(String message) {
        super(message);
    }

    public NoSuchRecordException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchRecordException(Throwable cause) {
        super(cause);
    }

    protected NoSuchRecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

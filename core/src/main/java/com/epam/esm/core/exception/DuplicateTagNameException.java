package com.epam.esm.core.exception;

import org.springframework.context.i18n.LocaleContextHolder;

public class DuplicateTagNameException extends RuntimeException {
    private final String messageKey = "duplicateTagNameExceptionMessage";

    @Override
    public String getLocalizedMessage() {
        return ExceptionMessageHandler.getMessage(messageKey, LocaleContextHolder.getLocale());
    }

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

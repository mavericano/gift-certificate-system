package com.epam.esm.core.exception;

import org.springframework.context.i18n.LocaleContextHolder;

public class InvalidPageSizeException extends RuntimeException {

    private final String messageKey;

    @Override
    public String getLocalizedMessage() {
        return ExceptionMessageHandler.getMessage(messageKey, LocaleContextHolder.getLocale());
    }

    public InvalidPageSizeException(String messageKey) {
        this.messageKey = messageKey;
    }
}

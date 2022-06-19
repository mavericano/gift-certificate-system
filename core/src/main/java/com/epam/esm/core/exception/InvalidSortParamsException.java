package com.epam.esm.core.exception;

import org.springframework.context.i18n.LocaleContextHolder;

public class InvalidSortParamsException extends RuntimeException {

    private final String messageKey;

    public InvalidSortParamsException(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getLocalizedMessage() {
        return ExceptionMessageHandler.getMessage(messageKey, LocaleContextHolder.getLocale());
    }
}

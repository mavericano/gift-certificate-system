package com.epam.esm.core.exception;

import org.springframework.context.i18n.LocaleContextHolder;

public class CredsMissingException extends RuntimeException {

    @Override
    public String getLocalizedMessage() {
        String messageKey = "credsMissingExceptionMessage";
        return ExceptionMessageHandler.getMessage(messageKey, LocaleContextHolder.getLocale());
    }

    public CredsMissingException() {
        super();
    }
}

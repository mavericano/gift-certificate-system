package com.epam.esm.core.exception;

import org.springframework.context.i18n.LocaleContextHolder;

public class DuplicateUsernameException extends RuntimeException {
    @Override
    public String getLocalizedMessage() {
        String messageKey = "duplicateUsernameExceptionMessage";
        return ExceptionMessageHandler.getMessage(messageKey, LocaleContextHolder.getLocale());
    }

    public DuplicateUsernameException() {
        super();
    }
}

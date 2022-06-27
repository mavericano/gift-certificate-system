package com.epam.esm.core.exception;

import org.springframework.context.i18n.LocaleContextHolder;

public class MismatchingCustomerException extends RuntimeException {

    @Override
    public String getLocalizedMessage() {
        String messageKey = "mismatchingCustomerExceptionMessage";
        return ExceptionMessageHandler.getMessage(messageKey, LocaleContextHolder.getLocale());
    }

}

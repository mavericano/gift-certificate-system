package com.epam.esm.core.exception;

import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class ExceptionMessageHandler {

    private final static String BASENAME = "locales/error-messages";

    public static String getMessage(String key, Locale locale) {
        return ResourceBundle.getBundle(BASENAME, locale).getString(key);
    }
}

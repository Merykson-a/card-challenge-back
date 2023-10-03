package com.card.challenge.domain.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class Message {
    private static MessageSource messageSource;

    public Message(MessageSource messageSource) {
        Message.messageSource = messageSource;
    }

    public static String toLocale(String msgCode) {
        return toLocale(msgCode, (Object) null);
    }

    public static String toLocale(String msgCode, Object... parameters) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource == null ? msgCode : messageSource.getMessage(msgCode, parameters, locale);
    }
}


package com.coworking.coworking_technical_test.exceptions;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessageResolver {

    private final MessageSource messageSource;

    public String get(MessageKey key, Object... args) {
        return messageSource.getMessage(key.key(), args, Locale.getDefault());
    }
}

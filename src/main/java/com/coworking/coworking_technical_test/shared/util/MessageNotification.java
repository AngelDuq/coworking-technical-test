package com.coworking.coworking_technical_test.shared.util;


import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageNotification {
    
    private final MessageSource messageSource;
    
    public MessageSource getMessageSource(){
        return messageSource;
    }

    public String getMessage(String message, Object[] obj, Locale locale){
        return getMessageSource().getMessage(message,obj,locale);
    }

}

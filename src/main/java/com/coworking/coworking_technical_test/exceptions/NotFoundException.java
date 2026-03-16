package com.coworking.coworking_technical_test.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(MessageKey messageKey) {
        super(messageKey.key());
    }

}

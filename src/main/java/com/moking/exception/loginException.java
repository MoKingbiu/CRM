package com.moking.exception;

import org.springframework.stereotype.Component;

@Component
public class loginException extends exception {
    public loginException() {
    }

    public loginException(String message) {
        super(message);
    }
}

package com.moking.exception;

import org.springframework.stereotype.Component;

@Component
public class exception extends java.lang.RuntimeException {
    public exception(String message) {
        super(message);
    }

    public exception() {
    }
}

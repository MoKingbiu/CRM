package com.moking.exception;

import org.springframework.stereotype.Component;

@Component
public class activityDeleteException extends exception{
    public activityDeleteException() {
    }

    public activityDeleteException(String message) {
        super(message);
    }
}

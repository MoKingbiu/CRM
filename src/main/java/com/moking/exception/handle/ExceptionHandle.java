package com.moking.exception.handle;

import com.moking.exception.loginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(loginException.class)
    public void loginException(Exception e){

    }

    @ExceptionHandler
    public void weizhiException(Exception e){
        
    }
}

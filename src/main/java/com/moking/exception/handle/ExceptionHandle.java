package com.moking.exception.handle;

import com.moking.exception.activityDeleteException;
import com.moking.exception.clueTranException;
import com.moking.exception.loginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(loginException.class)
    public void loginException(Exception e){

    }

    @ExceptionHandler(activityDeleteException.class)
    public void activityDeleteException(Exception e){

    }

    @ExceptionHandler(clueTranException.class)
    public void clueTranException(Exception e){

    }

    @ExceptionHandler
    public void weizhiException(Exception e){
        
    }
}

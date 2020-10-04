package com.moking.settings.service;

import com.moking.exception.exception;
import com.moking.settings.domain.User;

public interface UserService {
    User login(String loginAct,String loginPwd,String ip) throws exception;
}

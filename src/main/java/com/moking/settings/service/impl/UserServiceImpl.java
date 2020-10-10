package com.moking.settings.service.impl;

import com.moking.exception.exception;
import com.moking.exception.loginException;
import com.moking.settings.dao.UserDao;
import com.moking.settings.domain.User;
import com.moking.settings.service.UserService;
import com.moking.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws exception {
        User u=new User();
        u.setLoginAct(loginAct);
        u.setLoginPwd(loginPwd);
        User user=userDao.login(u);

        if(user==null){
            throw new loginException("账号密码错误");
        }

        String userTime=user.getExpireTime();
        String newTime= DateTimeUtil.getSysTime();
        if(userTime.compareTo(newTime)<0){
            throw new loginException("账号已过期");
        }

        if("0".equals(user.getLockState())){
            throw new loginException("账号已被封锁");
        }

        if(!user.getAllowIps().contains(ip)){
            throw new loginException("ip地址受限");
        }
        return user;
    }

    @Override
    public List<User> getUserList() {
        List<User> list=null;
        list=userDao.getUserList();
        return list;
    }
}

package com.moking.settings.dao;

import com.moking.settings.domain.User;
import java.util.List;

public interface UserDao {
    User login(User user);
    List<User> getUserList();
}

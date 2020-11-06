package com.moking.workbench.service;

import com.moking.exception.exception;
import com.moking.settings.domain.User;
import com.moking.workbench.domain.Tran;
import java.util.List;

public interface TranService {

     List<User> add();

     void save(Tran t, String customerName) throws exception;

    Tran detail(String id);
}

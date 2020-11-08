package com.moking.workbench.service;

import com.moking.exception.exception;
import com.moking.settings.domain.User;
import com.moking.workbench.domain.Tran;
import com.moking.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranService {

     List<User> add();

     void save(Tran t, String customerName) throws exception;

    Tran detail(String id);

    List<TranHistory> showHistoryList(String tranId);

    boolean changeStage(Tran t);
}

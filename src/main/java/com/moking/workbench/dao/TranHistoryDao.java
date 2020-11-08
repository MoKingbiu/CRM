package com.moking.workbench.dao;

import com.moking.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int save(TranHistory th);

    List<TranHistory> showHistoryList(String tranId);
}

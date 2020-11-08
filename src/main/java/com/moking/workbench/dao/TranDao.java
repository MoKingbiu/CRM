package com.moking.workbench.dao;

import com.moking.workbench.domain.Tran;

public interface TranDao {

    int save(Tran t);

    Tran detail(String id);

    int changeStage(Tran t);
}

package com.moking.workbench.dao;

import com.moking.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> getList(String cid);

    int delete(String cid);
}

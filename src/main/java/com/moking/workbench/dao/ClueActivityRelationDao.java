package com.moking.workbench.dao;


import com.moking.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {
    List<ClueActivityRelation> getListById(String cid);

    int delete(String cid);
}

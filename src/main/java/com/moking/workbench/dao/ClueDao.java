package com.moking.workbench.dao;


import com.moking.workbench.domain.Clue;
import com.moking.workbench.domain.ClueActivityRelation;
import com.moking.workbench.domain.ClueRemark;

import java.util.List;
import java.util.Map;

public interface ClueDao {

    int saveClue(Clue clue);
    Clue getClue(String id);
    int unbund(String id);
    int bund(ClueActivityRelation car);

    Clue getClueById(String cid);

    int delete(String cid);
}

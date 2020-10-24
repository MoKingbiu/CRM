package com.moking.workbench.dao;


import com.moking.workbench.domain.Clue;
import com.moking.workbench.domain.ClueActivityRelation;

import java.util.Map;

public interface ClueDao {

    int saveClue(Clue clue);
    Clue getClue(String id);
    int unbund(String id);
    int bund(ClueActivityRelation car);
}

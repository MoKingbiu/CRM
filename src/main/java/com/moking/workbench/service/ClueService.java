package com.moking.workbench.service;

import com.moking.workbench.domain.Clue;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface ClueService {
    Map<String, Object> getUserList();
    Map<String, Object> saveClue(Clue clue, HttpSession session);
    Clue getClue(String id);
    int unbund(String id);
    boolean bund(String cid,String[] aids);
}

package com.moking.workbench.web.controller;

import com.moking.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {
    @Autowired
    private ClueService clueServiceImpl;

    @RequestMapping("/getUserList.do")
    @ResponseBody
    public Map<String,Object> getUserList(){
        Map<String,Object> map=clueServiceImpl.getUserList();
        return map;
    }

}

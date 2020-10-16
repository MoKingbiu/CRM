package com.moking.vo;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class pageListVo<T> {
    private int total;
    private List<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public pageListVo() {
    }
}

package com.moking.settings.dao;

import com.moking.settings.domain.DicValue;
import java.util.List;

public interface DicValueDao {
    List<DicValue> getDicValueList(String code);
}

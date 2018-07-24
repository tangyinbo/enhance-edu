package com.cowboy.excel.imports;


import com.cowboy.excel.domain.BaseModel;

import java.util.List;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/4/20 16:43
 * @Description:
 */
public abstract class ImportResult<E> extends BaseModel {
    public abstract List<E> getResult();

    public abstract String getResMsg();
}

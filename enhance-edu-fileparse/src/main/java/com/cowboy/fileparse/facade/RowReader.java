package com.cowboy.fileparse.facade;

import com.cowboy.fileparse.RowData;

import java.util.List;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/5/30 15:50
 * @Description:
 */
public interface RowReader<T> {
    /**
     * 业务逻辑实现方法
     *
     * @param sheetIndex
     * @param curRow
     * @param rowlist
     */
    public RowData<T> getRows(int sheetIndex, int curRow, List<T> rowlist);
}

package com.cowboy.fileparse;


import com.cowboy.fileparse.facade.RowFileter;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/5/31 18:44
 * @Description:
 */
public class TestRowFilter implements RowFileter<String> {
    @Override
    public boolean filter(RowData rowData) {
        rowData.setValue(rowData.getValue() + ">>>hello tangyinbo");
        return true;
    }
}

package com.cowboy.fileparse;


import com.cowboy.fileparse.facade.RowDataProcess;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/5/31 18:10
 * @Description:
 */
public class PrintRowDataProcess implements RowDataProcess<String> {
    @Override
    public void process(RowData<String> rowDataStr) {
        System.out.println(rowDataStr);
    }

    @Override
    public void finish() {
        System.out.println("=======finish=========");
    }
}

package com.cowboy.fileparse.facade;


import com.cowboy.fileparse.RowData;

/**
 * 行数据处理
 * @Auther: Tangyinbo
 * @Date: 2018/5/31 17:08
 * @Description:
 */
public interface RowDataProcess<T> {
   /* *//**
     * 行数据消费
     * @param consumer
     *//*
    public void setConsumer(Consumer<String> consumer);*/
    /**
     * 处理数据
     * @param rowData 行数据
     */
    public void process(RowData<T> rowData);

    /**
     * 通知处理完成钩子
     */
    public void finish();
}

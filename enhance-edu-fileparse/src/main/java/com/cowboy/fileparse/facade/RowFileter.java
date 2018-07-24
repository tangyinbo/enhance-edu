package com.cowboy.fileparse.facade;


import com.cowboy.fileparse.RowData;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/5/31 17:07
 * @Description:
 */
public interface RowFileter<T> {
    /**
     * 行过滤器
     *
     * @param rowData 过滤的数据
     * @return false 终止后面的过滤器以及行数据处理, true 继续后面的操作
     */
    boolean filter(RowData<T> rowData);
}

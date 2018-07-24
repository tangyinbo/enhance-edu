package com.cowboy.fileparse.facade;

import java.io.IOException;
import java.util.List;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/5/31 15:41
 * @Description:
 */
public interface KYFileParse{
    /**
     * 处理文件
     * @param fileName
     */
    public void process(String fileName) throws IOException, Exception;

    /**
     * 设置行读取器
     * @param rowReader
     */
    public void setRowReader(RowReader rowReader);

    /**
     * 设置行数据过滤器
     * @param rowFilters
     */
    public void setRowFilters(List<RowFileter> rowFilters);

    /**
     * 添加行过滤器
     * @param rowFileter
     */
    public void addRowFilter(RowFileter rowFileter);

    /**
     *
     * @param rowDataProcess
     */
    public void setRowDataProcess(RowDataProcess rowDataProcess);
}

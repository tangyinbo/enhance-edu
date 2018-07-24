package com.cowboy.excel.imports;


import java.io.InputStream;
import java.rmi.server.ExportException;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/4/20 16:55
 * @Description:
 */
public interface FileImportor {
    /**
     * 解析导入文件结果
     * @param metaData
     * @param inputStream
     * @return
     * @throws
     */
    public abstract ImportResult getImportResult(ImportMetaData metaData, InputStream inputStream) throws ExportException, Exception;
}

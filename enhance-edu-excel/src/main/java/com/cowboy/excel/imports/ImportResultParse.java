package com.cowboy.excel.imports;

import java.io.InputStream;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/4/20 16:48
 * @Description:
 */
public class ImportResultParse {
    /**
     * 解析配置导入文件
     * @param metaData
     * @param inputStream
     * @return
     */
    public static ImportResult parseResult(ImportMetaData metaData, InputStream inputStream) throws Exception {
        return new ExcelFileImportor().getImportResult(metaData,inputStream);
    }
}

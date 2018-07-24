package com.cowboy.excel.export;


import com.cowboy.excel.exception.KyFileExportException;
import com.cowboy.excel.export.domain.ExportType;

import java.io.OutputStream;

/**
 * 文件导出结果类
 */
public abstract class ExportResult {
    /**
     * 导出文件名
     */
    private String fileName;

    /**
     * 導出文件對象
     *
     * @return
     */
    public abstract Object getResult();

    /**
     * 输出文件到输出流
     *
     * @param outputStream
     * @throws KyFileExportException
     */
    public abstract void export(OutputStream outputStream) throws KyFileExportException;

    /**
     * 获取文件导出类型
     *
     * @return
     */
    public abstract ExportType getExportType();

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}

package com.cowboy.excel.exception;

/**
 * 文件导出异常定义
 */
public class KyFileExportException extends Exception {

    public KyFileExportException(String message){
        super(message);
    }

    public KyFileExportException(Throwable throwable, String message){
        super(message, throwable);
    }

    public KyFileExportException(Throwable throwable){
        super(throwable);
    }
}

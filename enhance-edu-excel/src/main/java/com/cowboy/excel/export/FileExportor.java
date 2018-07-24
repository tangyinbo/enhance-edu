package com.cowboy.excel.export;


import com.cowboy.excel.exception.KyFileExportException;
import com.cowboy.excel.export.domain.ExportCell;

import java.util.List;

/**
 * 文件导出解析
 */
public interface FileExportor {
    /**
     * 解析数据和文件定义,封装为导出对象
     *
     * @param data        即将导出数据
     * @param exportCells 导出数据格式定义
     * @return 导出对象
     * @throws KyFileExportException if any exception occur
     */
    public Object getExportResult(List<?> data, List<ExportCell> exportCells) throws KyFileExportException;
}

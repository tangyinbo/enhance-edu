package com.cowboy.excel.export;



import com.cowboy.excel.domain.BaseModel;
import com.cowboy.excel.export.domain.ExportCell;
import com.cowboy.excel.export.domain.ExportType;

import java.util.List;

/**
 * Excel 文件导出元数据
 */
public class ExportMetaData extends BaseModel {
    private String fileName;//输出的文件名
    private ExportType exportType;//0 表示 excel, 1 表示txt
    private List<ExportCell> exportCells; //单元格定义

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<ExportCell> getExportCells() {
        return exportCells;
    }

    public void setExportCells(List<ExportCell> exportCells) {
        this.exportCells = exportCells;
    }

    public ExportType getExportType() {
        return exportType;
    }

    public void setExportType(ExportType exportType) {
        this.exportType = exportType;
    }
}

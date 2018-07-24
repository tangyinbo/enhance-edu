package com.cowboy.excel.imports;

import com.cowboy.excel.imports.domain.ImportCell;

import java.util.List;

/**
 * 导入Excel解析的元数据
 * @Auther: Tangyinbo
 * @Date: 2018/4/20 16:23
 * @Description:
 */
public class ImportMetaData {
    //文件类型,后缀名,如:xls,xlsx 主要用于区分到时候使用哪种poi解析
    private String fileType;
    private Integer startRowNo;//读取的起始行 起始为0
    private List<ImportCell> importCells;

    public Integer getStartRowNo() {
        return startRowNo;
    }

    public void setStartRowNo(Integer startRowNo) {
        this.startRowNo = startRowNo;
    }

    public List<ImportCell> getImportCells() {
        return importCells;
    }

    public void setImportCells(List<ImportCell> importCells) {
        this.importCells = importCells;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}

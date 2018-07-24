package com.cowboy.excel.export;

import com.cowboy.excel.exception.KyFileExportException;
import com.cowboy.excel.export.domain.ExportType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by stark.zhang on 2015/11/6.
 */
public class ExcelExportResult extends ExportResult {
    private Workbook workbook;

    /**
     * 导出数据元配置
     */
    private ExportMetaData exportMetaData;

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public Object getResult() {
        return workbook;
    }

    /**
     * 导出文件到指定输出流
     *
     * @param outputStream
     * @throws KyFileExportException
     */
    public void export(OutputStream outputStream) throws KyFileExportException {
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            throw new KyFileExportException("Error occurred while export excel msg is " + e);
        }finally {
            try {
                outputStream.close();
                if(workbook instanceof SXSSFWorkbook){
                    // dispose of temporary files backing this workbook on disk
                    ((SXSSFWorkbook)workbook).dispose();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ExportMetaData getExportMetaData() {
        return exportMetaData;
    }

    public void setExportMetaData(ExportMetaData exportMetaData) {
        this.exportMetaData = exportMetaData;
    }

    @Override
    public ExportType getExportType() {
        return ExportType.EXCEL2007;
    }
}

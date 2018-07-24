package com.cowboy.excel.export;

import com.cowboy.excel.exception.KyFileExportException;
import com.cowboy.excel.export.domain.ExportType;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.function.Function;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/4/19 13:55
 * @Description:
 */
public class ExportResultParse {

    /**
     * @param exportConfig
     * @param data
     * @return
     * @throws KyFileExportException
     */
    public static ExportResult parse(ExportMetaData exportConfig, List<?> data) throws KyFileExportException {
        ExportType exportType = exportConfig.getExportType();
        switch (exportType) {
            case EXCEL2007:
                Workbook workbook = new ExcelExportor().getExportResult(data, exportConfig.getExportCells());
                ExcelExportResult exportExcelResult = new ExcelExportResult();
                exportExcelResult.setWorkbook(workbook);
                exportExcelResult.setExportMetaData(exportConfig);
                exportExcelResult.setFileName(exportConfig.getFileName());
                return exportExcelResult;
            default:
                throw new KyFileExportException("暂时不支持的导出文件类型");
        }
    }

    /**
     *
     * @param exportConfig  导出配置
     * @param function 数据提供者
     * @param scalar 每次提取数据量
     * @return 导出结果
     * @throws KyFileExportException
     */
    public static ExportResult parse(ExportMetaData exportConfig, Function<Integer,List<?>> function, int scalar) throws KyFileExportException {
        ExportType exportType = exportConfig.getExportType();
        switch (exportType) {
            case EXCEL2007:
                Workbook workbook = new ExcelExportor().getExportResult(function, exportConfig.getExportCells(),scalar);
                ExcelExportResult exportExcelResult = new ExcelExportResult();
                exportExcelResult.setWorkbook(workbook);
                exportExcelResult.setExportMetaData(exportConfig);
                exportExcelResult.setFileName(exportConfig.getFileName());
                return exportExcelResult;
            default:
                throw new KyFileExportException("暂时不支持的导出文件类型");
        }
    }

    /**
     *
     * @param exportConfig  导出配置
     * @param function 数据提供者
     * @return 导出结果
     * @throws KyFileExportException
     */
    public static ExportResult parseLowMemory(ExportMetaData exportConfig, Function<Integer,List<?>> function) throws KyFileExportException {
        //内存一万条数据
        int scalar = 10000;
        ExportType exportType = exportConfig.getExportType();
        switch (exportType) {
            case EXCEL2007:
                Workbook workbook = new ExcelExportor().getExportResultLowMemory(function, exportConfig.getExportCells(),scalar);
                ExcelExportResult exportExcelResult = new ExcelExportResult();
                exportExcelResult.setWorkbook(workbook);
                exportExcelResult.setExportMetaData(exportConfig);
                exportExcelResult.setFileName(exportConfig.getFileName());
                return exportExcelResult;
            default:
                throw new KyFileExportException("暂时不支持的导出文件类型");
        }
    }
}

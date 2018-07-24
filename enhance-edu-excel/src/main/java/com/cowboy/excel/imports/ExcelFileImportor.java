package com.cowboy.excel.imports;

import com.cowboy.excel.exception.KyFileExportException;
import com.cowboy.excel.imports.domain.ImportCell;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * excel文件解析
 *
 * @Auther: Tangyinbo
 * @Date: 2018/4/20 16:57
 * @Description:
 */
public class ExcelFileImportor implements FileImportor {
    private static final String EXCEL_2003 = "xls";
    private static final String EXCEL_2007 = "xlsx";

    @Override
    public ImportResult getImportResult(ImportMetaData metaData, InputStream inputStream) throws Exception {
        //异常结果信息
        StringBuilder stringbuilder = new StringBuilder();
        //工作簿对象
        Workbook workbook = createWorkbook(metaData, inputStream);
        List<Map> result = readExcel(workbook, metaData, stringbuilder);
        ImportMapResult importResult = new ImportMapResult();
        importResult.setResMsg(stringbuilder.toString());
        importResult.setResult(result);
        return importResult;
    }

    /**
     * 解析Excel对象
     *
     * @param workbook
     * @param metaData
     * @param sb
     * @return
     * @throws KyFileExportException
     */
    private List<Map> readExcel(Workbook workbook, ImportMetaData metaData, StringBuilder sb) throws KyFileExportException {
        //选择第一个sheet
        Sheet sheet = workbook.getSheetAt(0);
        //获取解析开始的行号
        int startRow = metaData.getStartRowNo();
        //获取每个cell解析配置
        List<ImportCell> lists = metaData.getImportCells();
        //获取excel sheet 的数据行数
        int phyRow = sheet.getPhysicalNumberOfRows();
        List<Map> results = Lists.newLinkedList();
        //解析每行数据
        for (int t = startRow; t < phyRow; t++) {
            Row row = sheet.getRow(t);
            if (row == null) {
                continue;
            }
            //poi获取正确行数很难。这里约定，前三个值都为空时，自动放弃该行
            if (isCellEmpty(row.getCell(0)) && isCellEmpty(row.getCell(1)) && isCellEmpty(row.getCell(2))) {
                continue;
            }
            Map<String, Object> maps = Maps.newLinkedHashMap();
            maps.put(ImportMapResult.IS_LINE_LEGAL_KEY, true);
            for (ImportCell importCell : lists) {
                setValue(maps, importCell, row, sb, t, startRow);
            }
            results.add(maps);
        }
        return results;
    }

    /**
     * 设置值
     *
     * @param maps       结果集
     * @param importCell 当前cell解析
     * @param row        当前行
     * @param sb         异常嘻嘻
     * @param line       当前行号
     * @param startRow   开始行
     * @throws KyFileExportException
     */
    private void setValue(Map<String, Object> maps, ImportCell importCell, Row row, StringBuilder sb, int line, int startRow) throws KyFileExportException {
        //解析的单元格列索引
        int num = importCell.getNumber();
        //当前行
        int showLine = line + startRow;
        //列索引
        int showColumn = num+1;
        maps.put(ImportMapResult.LINE_NUM_KEY, showLine);
        ImportCell.CellType cellType = importCell.getCellType();
        ImportCell.NullAble nullable = importCell.getNullAble();
        //错误消息
        String errMsg = null;
        String key = importCell.getKey();
        Cell cell = row.getCell(num);
        int rawCellType = Cell.CELL_TYPE_BLANK;
        if (cell != null) {
            rawCellType = cell.getCellType();
        }
        if (rawCellType == Cell.CELL_TYPE_BLANK || cell == null || rawCellType == Cell.CELL_TYPE_STRING && StringUtils.isEmpty(cell.getStringCellValue())) {
            if (nullable == ImportCell.NullAble.NULL_ALLOWED) {
                maps.put(key, null);
            } else {
                errMsg = String.format("行:%d,列:%d 为空,但是不允许为空! \n", showLine, showColumn);
                setErrMsg(errMsg, maps, sb);
            }
        } else {
            switch (cellType) {
                case INT:
                    if (rawCellType == Cell.CELL_TYPE_STRING) {
                        String temp = cell.getStringCellValue();
                        if (!StringUtils.isNumeric(temp)) {
                            errMsg = String.format("行:%d,列:%d 不是数字 \n", showLine, showColumn);
                            setErrMsg(errMsg, maps, sb);
                        }
                        maps.put(key, Integer.valueOf(temp));
                    }
                    if (rawCellType == Cell.CELL_TYPE_NUMERIC) {
                        Double temp = cell.getNumericCellValue();
                        maps.put(key, temp.intValue());
                    }
                    break;

                case STRING:
                    String temp = null;
                    if (rawCellType == Cell.CELL_TYPE_NUMERIC) {
                        temp = String.valueOf(cell.getNumericCellValue());
                        maps.put(key, temp);
                        break;
                    }
                    if (rawCellType == Cell.CELL_TYPE_STRING) {
                        temp = cell.getStringCellValue();
                        maps.put(key, temp);
                        break;
                    }

                    errMsg = String.format("行:%d,列:%d 不是字符串 \n", showLine, showColumn);
                    setErrMsg(errMsg, maps, sb);
                    break;

                case FLOAT:
                    if (rawCellType == Cell.CELL_TYPE_NUMERIC) {
                        Double temp1 = cell.getNumericCellValue();
                        maps.put(key, temp1.floatValue());
                    } else {
                        errMsg = String.format("行:%d,列:%d 不是浮点型 \n", showLine, showColumn);
                        setErrMsg(errMsg, maps, sb);
                    }
                    break;

                case DATE:
                    if (rawCellType == Cell.CELL_TYPE_NUMERIC) {
                        Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
                        maps.put(key, date);
                    } else {
                        errMsg = String.format("行:%d,列:%d 不是日期 \n", showLine, showColumn);
                        setErrMsg(errMsg, maps, sb);
                    }
                    break;
                case BIGDECIMAL:
                    if (rawCellType == Cell.CELL_TYPE_NUMERIC) {
                        Double temp1 = cell.getNumericCellValue();
                        maps.put(key, BigDecimal.valueOf(temp1));
                    } else {
                        errMsg = String.format("行:%d,列:%d 不是BigDecimal \n", showLine, showColumn);
                        setErrMsg(errMsg, maps, sb);
                    }
                    break;
                case DOUBLE:
                    if (rawCellType == Cell.CELL_TYPE_NUMERIC) {
                        Double temp1 = cell.getNumericCellValue();
                        maps.put(key, temp1);
                    } else {
                        errMsg = String.format("行:%d,列:%d 不是Double \n", showLine, showColumn);
                        setErrMsg(errMsg, maps, sb);
                    }
                    break;
            }
        }
    }

    /**
     * 设置异常信息
     *
     * @param errMsg
     * @param maps
     * @param sb
     */
    private void setErrMsg(String errMsg, Map<String, Object> maps, StringBuilder sb) {
        sb.append(errMsg);
        maps.put(ImportMapResult.IS_LINE_LEGAL_KEY, false);
    }

    /**
     * 判断cell值是否为空
     *
     * @param cell
     * @return
     */
    private boolean isCellEmpty(Cell cell) {
        if (cell == null) {
            return true;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return true;
        }
        if (cell.getCellType() == Cell.CELL_TYPE_STRING && StringUtils.isEmpty(cell.getStringCellValue())) {
            return true;
        }
        return false;
    }


    /**
     * 获取工作簿对象
     *
     * @param metaData
     * @param inputStream
     * @return
     * @throws KyFileExportException
     * @throws IOException
     */
    private Workbook createWorkbook(ImportMetaData metaData, InputStream inputStream) throws KyFileExportException, IOException {
        Workbook workbook = null;
        if (EXCEL_2003.equals(metaData.getFileType())) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (EXCEL_2007.equals(metaData.getFileType())) {
            workbook = new XSSFWorkbook(inputStream);
        } else {
            throw new KyFileExportException("上传Excel,文件类型不支持! 暂时只支持:[" + EXCEL_2003 + "," + EXCEL_2007 + "],传入的文件类型为:" + metaData.getFileType());
        }
        return workbook;
    }
}

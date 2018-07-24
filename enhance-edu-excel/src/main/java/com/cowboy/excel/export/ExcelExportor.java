package com.cowboy.excel.export;

import com.cowboy.excel.exception.KyFileExportException;
import com.cowboy.excel.export.domain.ExportCell;
import com.cowboy.excel.utils.KyDateUtils;
import com.cowboy.excel.utils.ReflectionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Excel导出
 */
public class ExcelExportor implements FileExportor {
    @Override
    public Workbook getExportResult(List<?> data, List<ExportCell> exportCells) throws KyFileExportException {
        Workbook workbook = new XSSFWorkbook();
        //创建sheet页
        Sheet sheet = workbook.createSheet();
        //创建标题行
        Row titleRow = sheet.createRow(0);
        //填充标题行
        createTitleRow(workbook, titleRow, exportCells, sheet);
        //解析内容行
        if (List.class.isAssignableFrom(data.getClass())) {
            if (!data.isEmpty()) {
                //Map 数据类型
                if (data.get(0) instanceof Map) {
                    createContentRowsByMap(workbook, (List<Map>) data, exportCells, sheet);
                } else {
                    //对象类型
                    createContentRowsByBean(workbook, (List<Object>) data, exportCells, sheet);
                }
            }
        } else {
            throw new KyFileExportException("传入的data数据格式有误，请检查是否属于list");
        }
        return workbook;
    }

    /**
     * 解析数据行如果传入的数据类型是Map
     *
     * @param workbook
     * @param dataList
     * @param exportCells
     * @param sheet
     * @return
     * @throws KyFileExportException
     */
    private Workbook createContentRowsByMap(Workbook workbook, List<Map> dataList, List<ExportCell> exportCells, Sheet sheet) throws KyFileExportException {
        if (!dataList.isEmpty()) {
            int rowNum =  sheet.getLastRowNum();
            rowNum = (rowNum == -1)?0:rowNum+1;
            CellStyle cellStyle = createCellStyle(workbook);
            for (Map map : dataList) {
                Row row = sheet.createRow(rowNum);
                //row.setHeightInPoints(23.0F);
                for (int colNum = 0; colNum < exportCells.size(); colNum++) {
                    Cell cell = row.createCell(colNum);
                    cell.setCellStyle(cellStyle);
                    ExportCell exportCell = exportCells.get(colNum);
                    Object obj = null;
                    obj = map.get(exportCell.getAlias());
                    setCellValue(obj, cell);
                }
                rowNum++;
            }
        }
        return workbook;
    }

    /**
     * 解析excel内容,如果导出的数据是对象
     *
     * @param workbook
     * @param dataList
     * @param exportCells
     * @param sheet
     * @throws KyFileExportException
     */
    private static void createContentRowsByBean(Workbook workbook, List<Object> dataList, List<ExportCell> exportCells, Sheet sheet) throws KyFileExportException {
        int rowNum =  sheet.getLastRowNum();
        rowNum = (rowNum == -1)?0:rowNum+1;
        if (!dataList.isEmpty()) {
            CellStyle cellStyle = createCellStyle(workbook);
            for (Object t : dataList) {
                Row row = sheet.createRow(rowNum);
                //row.setHeightInPoints(23.0F);
                for (int colNum = 0; colNum < exportCells.size(); colNum++) {
                    Cell cell = row.createCell(colNum);
                    cell.setCellStyle(cellStyle);
                    ExportCell exportCell = exportCells.get(colNum);
                    Object obj = null;
                    try {
                        obj = ReflectionUtils.executeMethod(t, ReflectionUtils.returnGetMethodName(exportCell.getAlias()));
                    } catch (Exception e) {
                        throw new KyFileExportException("执行executeMethod  出错 Alias is " + exportCell.getAlias() + " at " + e.getMessage());
                    }
                    setCellValue(obj, cell);
                }
                ++rowNum;
            }

        }
    }


    private static void setCellValue(Object obj, Cell cell) throws KyFileExportException {
        if (obj != null) {
            BigDecimal bigDecimal = null;
            String classType = obj.getClass().getName();
            if (classType.endsWith("String"))
                cell.setCellValue((String) obj);
            else if (("int".equals(classType)) || (classType.equals("java.lang.Integer")))
                cell.setCellValue(((Integer) obj).intValue());
            else if (("double".equals(classType)) || (classType.equals("java.lang.Double"))) {
                bigDecimal = new BigDecimal(((Double) obj).doubleValue());
                cell.setCellValue(bigDecimal.doubleValue());
            } else if (("float".equals(classType)) || (classType.equals("java.lang.Float"))) {
                bigDecimal = new BigDecimal(((Float) obj).floatValue());
                cell.setCellValue(bigDecimal.doubleValue());
            } else if ((classType.equals("java.util.Date")) || (classType.endsWith("Date")))
                cell.setCellValue(KyDateUtils.formatDate((Date) obj, "yyyy-MM-dd HH:mm:ss"));
            else if (classType.equals("java.util.Calendar"))
                cell.setCellValue((Calendar) obj);
            else if (("char".equals(classType)) || (classType.equals("java.lang.Character")))
                cell.setCellValue(obj.toString());
            else if (("long".equals(classType)) || (classType.equals("java.lang.Long")))
                cell.setCellValue(((Long) obj).longValue());
            else if (("short".equals(classType)) || (classType.equals("java.lang.Short")))
                cell.setCellValue(((Short) obj).shortValue());
            else if (classType.equals("java.math.BigDecimal")) {
                bigDecimal = (BigDecimal) obj;
                bigDecimal = new BigDecimal(bigDecimal.doubleValue());
                cell.setCellValue(bigDecimal.doubleValue());
            } else {
                throw new KyFileExportException("data type error !  obj is " + obj);
            }
        }
    }

    /**
     * 创建标题行
     *
     * @param workbook    excel poi 对象
     * @param row         excel 标题行
     * @param exportCells 导出配置
     * @param sheet       sheet页
     */
    private static void createTitleRow(Workbook workbook, Row row, List<ExportCell> exportCells, Sheet sheet) {
        CellStyle style = createHeadStyle(workbook);
        row.setHeightInPoints(25.0F);
        Font font = workbook.createFont();
        font.setColor((short) 12);
//        font.setBoldweight((short) 700);
        style.setFont(font);
        style.setFillBackgroundColor((short) 13);
        int i = 0;
        for (ExportCell exportCell : exportCells) {
            sheet.setColumnWidth(i, 3200);
            Cell cell = row.createCell(i);
            cell.setCellValue(exportCell.getTitle());
            cell.setCellStyle(style);
            ++i;
        }
    }

    /**
     * 创建title的样式 默认就行
     *
     * @param workbook
     * @return
     */
    private static CellStyle createHeadStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
//        style.setAlignment((short) 2);
//        style.setVerticalAlignment((short) 1);
//        style.setFillForegroundColor((short) 55);
//        style.setFillPattern((short) 1);
//        style.setBorderBottom((short) 1);
//        style.setBorderLeft((short) 1);
//        style.setBorderRight((short) 1);
//        style.setBorderTop((short) 1);
//        style.setWrapText(true);
        return style;
    }

    /**
     * 创建正文的样式
     *
     * @param workbook
     * @return
     */
    private static CellStyle createCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
//        style.setAlignment((short) 2);
//        style.setVerticalAlignment((short) 1);
//        style.setFillForegroundColor((short) 9);
//        Font font = workbook.createFont();
//        font.setColor((short) 8);
//        font.setFontHeightInPoints((short) 12);
//        style.setWrapText(true);
        return style;
    }

    /**
     * 导出结果
     * @param function
     * @param exportCells
     * @param scalar
     * @return
     */
    public Workbook getExportResult(Function<Integer,List<?>> function, List<ExportCell> exportCells, int scalar) throws KyFileExportException {
        Workbook workbook = new XSSFWorkbook();
        //创建sheet页
        Sheet sheet = workbook.createSheet();
        //创建标题行
        Row titleRow = sheet.createRow(0);
        //填充标题行
        createTitleRow(workbook, titleRow, exportCells, sheet);

        while(true){
            List<?> data = function.apply(scalar);
            if(data != null){
                //解析内容行
                if (List.class.isAssignableFrom(data.getClass())) {
                    if (!data.isEmpty()) {
                        //Map 数据类型
                        if (data.get(0) instanceof Map) {
                            createContentRowsByMap(workbook, (List<Map>) data, exportCells, sheet);
                        } else {
                            //对象类型
                            createContentRowsByBean(workbook, (List<Object>) data, exportCells, sheet);
                        }
                    }
                } else {
                    throw new KyFileExportException("传入的data数据格式有误，请检查是否属于list");
                }
            }else{
                break;
            }
            //如果检索行小于约定每次查询行结束查询
            if(data.size() < scalar){
                break;
            }
        }
        return workbook;
    }

    /**
     * 导出结果
     * @param function
     * @param exportCells
     * @param scalar
     * @return
     */
    public Workbook getExportResultLowMemory(Function<Integer,List<?>> function, List<ExportCell> exportCells, int scalar) throws KyFileExportException {
        SXSSFWorkbook workbook = new SXSSFWorkbook (scalar);
        //创建sheet页
        Sheet sheet = workbook.createSheet();
        //创建标题行
        Row titleRow = sheet.createRow(0);
        //填充标题行
        createTitleRow(workbook, titleRow, exportCells, sheet);

        while(true){
            List<?> data = function.apply(scalar);
            if(data != null){
                //解析内容行
                if (List.class.isAssignableFrom(data.getClass())) {
                    if (!data.isEmpty()) {
                        //Map 数据类型
                        if (data.get(0) instanceof Map) {
                            createContentRowsByMap(workbook, (List<Map>) data, exportCells, sheet);
                        } else {
                            //对象类型
                            createContentRowsByBean(workbook, (List<Object>) data, exportCells, sheet);
                        }
                    }
                } else {
                    throw new KyFileExportException("传入的data数据格式有误，请检查是否属于list");
                }
            }else{
                break;
            }
            //如果检索行小于约定每次查询行结束查询
            if(data.size() < scalar){
                break;
            }
        }
        return workbook;
    }
}

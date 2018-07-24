package com.cowboy.excel;

import com.cowboy.excel.exception.KyFileExportException;
import com.cowboy.excel.export.ExportMetaData;
import com.cowboy.excel.export.ExportMetaDataFactory;
import com.cowboy.excel.export.ExportResult;
import com.cowboy.excel.export.ExportResultParse;
import com.cowboy.excel.imports.ImportMetaData;
import com.cowboy.excel.imports.ImportMetaDataFactory;
import com.cowboy.excel.imports.ImportResult;
import com.cowboy.excel.imports.ImportResultParse;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest {
    public static void main(String[] args) throws Exception {

        long timeMillis = System.currentTimeMillis();
        parseMap(400000);
        //parseObject(200000);
        System.out.println("共消耗时间:"+ (System.currentTimeMillis() - timeMillis)/1000 +"s");
        //importTest();
        //1W object 共消耗时间:2s  map 1S
        //10W object 共消耗时间:7s  map 7S
        //20W object 共消耗时间:13s  map 11S
        return;
    }

    public static void importTest() throws Exception {
        ImportMetaData importMetaData = ImportMetaDataFactory.getImportMetaData(AppTest.class.getClassLoader().getResourceAsStream("import/config.xml"));

        ImportResult importResult = ImportResultParse.parseResult(importMetaData,AppTest.class.getClassLoader().getResourceAsStream("import/test.xlsx"));

        System.out.println(importResult);

    }

    public static Object parseObject(int row) throws KyFileExportException, FileNotFoundException {
        ExportMetaData exportConfig = ExportMetaDataFactory.getExportMetaData(AppTest.class.getClassLoader().getResourceAsStream("export/exportconfig.xml"));
        ExportEntity exportEntity = null;
        List<ExportEntity> exportEntities = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            exportEntity = new ExportEntity();
            exportEntity.setIndex(i);
            exportEntity.setDate(new Date());
            exportEntity.setGreet("hi" + i);
            exportEntity.setFloats(Float.valueOf(i));
            exportEntity.setDd(BigDecimal.valueOf(i));
            exportEntities.add(exportEntity);
        }
        ExportResult exportResult = ExportResultParse.parse(exportConfig, exportEntities);
        //系统mac
        OutputStream outputStream = new FileOutputStream("E:\\test\\output4.xlsx");
        exportResult.export(outputStream);
        return null;
    }

    private static void parseMap(int row) throws KyFileExportException, FileNotFoundException {
        ExportMetaData exportConfig = ExportMetaDataFactory.getExportMetaData(AppTest.class.getClassLoader().getResourceAsStream("export/exportconfig.xml"));
        //map也可以换成一个实体类
        List<Map> lists = new LinkedList<>();
        for (int i = 0; i < row; i++) {
            Map<String, Object> maps = new HashMap<>();
            maps.put("index", i);
            maps.put("date", new Date());
            maps.put("greet", "hi" + i);
            maps.put("float", Float.valueOf(i));
            maps.put("bigdecimal", BigDecimal.valueOf(i));
            lists.add(maps);
        }
        ExportResult exportResult = ExportResultParse.parse(exportConfig, lists);
        //系统mac
        OutputStream outputStream = new FileOutputStream("E:\\test\\output2.xlsx");
        exportResult.export(outputStream);
    }
}

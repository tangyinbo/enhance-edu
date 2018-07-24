package com.cowboy.fileparse;

import com.cowboy.fileparse.facade.RowDataProcess;
import com.cowboy.fileparse.facade.RowFileter;
import com.cowboy.fileparse.facade.RowReader;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/5/30 15:51
 * @Description:
 */
public class TT {
    public static void main(String[] args) throws Exception {
        RowReader rowReader = new ListJoinSymbolToStrRowReaderd();
        RowDataProcess rowDataProcess = new PrintRowDataProcess();

     /* Excel2003Parse excel03 = new Excel2003Parse();
        excel03.setRowReader(rowReader);
        excel03.setRowDataProcess(rowDataProcess);
        List<RowFileter> filters = new ArrayList<>();
        filters.add(new TestRowFilter());
        excel03.setRowFilters(filters);
        excel03.process("D:\\company\\env\\template\\tt.xls");*/


        //excel03.process("D:\\company\\env\\template\\tt.xls");
        //excel03.process("D:\\company\\channel\\recon_template\\059.xls");


        Excel2007Parse excel07 = new Excel2007Parse();
        excel07.setRowReader(rowReader);
        excel07.setRowDataProcess(rowDataProcess);
        List<RowFileter> filters = new ArrayList<>();
        filters.add(new TestRowFilter());
        excel07.setRowFilters(filters);
        excel07.process("D:\\company\\channel\\recon_template\\003.xlsx");
        //excel07.process("C:\\Users\\t2\\Desktop\\dd.xlsx");
        //excel07.process("D:\\company\\channel\\recon_template\\003.xlsx");

        //excel07.process("2018.5.29-078.xlsx");
//        excel07.process("D:\\company\\channel\\recon_template\\");
//        excel07.process("D:\\company\\channel\\recon_template\\");
//        excel07.process("D:\\company\\channel\\recon_template\\");
//        excel07.process("D:\\company\\channel\\recon_template\\");
//        excel07.process("D:\\company\\channel\\recon_template\\");

      /*  rowReader = new ListJoinSymbolToStrTxtRowReaderd();
        KYFileParse csvParse = new CsvParse();
        csvParse.setRowReader(rowReader);
        csvParse.setRowDataProcess(rowDataProcess);
        List<RowFileter> filters = new ArrayList<>();
        //filters.add(new TestRowFilter());
        filters.add(new ValueFormulaRowFilter("$TRIM,$TRIM_SYMBOL@\":\"\",$TRIM_SYMBOL@`:\"\""));
        csvParse.setRowFilters(filters);
        csvParse.process("D:\\company\\channel\\recon_template\\072.csv");*/
    }
}

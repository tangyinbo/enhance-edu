package com.cowboy.fileparse;

import com.cowboy.fileparse.facade.KYFileParse;
import com.cowboy.fileparse.facade.RowDataProcess;
import com.cowboy.fileparse.facade.RowFileter;
import com.cowboy.fileparse.facade.RowReader;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/5/31 15:38
 * @Description:
 */
public class CsvParse implements KYFileParse {
    /**
     * 行阅读
     */
    private RowReader<String> rowReader;

    /**
     * 行数据过滤器
     */
    private List<RowFileter> rowFilters;

    /**
     * 行数据处理器
     */
    private RowDataProcess rowDataProcess;

    public void setRowReader(RowReader rowReader){
        this.rowReader = rowReader;
    }

    @Override
    public void setRowFilters(List<RowFileter> rowFilters) {
        this.rowFilters = rowFilters;
    }

    @Override
    public void addRowFilter(RowFileter rowFileter) {
        if (this.rowFilters != null) {
            this.rowFilters.add(rowFileter);
        } else {
            synchronized (this) {
                if (this.rowFilters == null) {
                    this.rowFilters = new ArrayList<>();
                    rowFilters.add(rowFileter);
                }
            }
        }
    }

    @Override
    public void setRowDataProcess(RowDataProcess rowDataProcess) {
        this.rowDataProcess = rowDataProcess;
    }

    @Override
    public void process(String fileName) throws IOException {
        File file = new File(fileName);

        String fileEncode = KYFileUtils.CHARSET;
        try {
            fileEncode = KYFileUtils.getFileEncode(file);
        } catch (Exception e) {
            e.printStackTrace();
            //
        }
        ImmutableList<String> lines = Files.asCharSource(new File(fileName), Charset.forName(fileEncode)).readLines();
        int rowIdx = 0;
        for (String line : lines) {
            // 每行结束时， 调用getRows() 方法
            RowData<String> rowData = rowReader.getRows(0,rowIdx++, Arrays.asList(line));

            //过滤器处理结果,默认true
            boolean filterResult = true;
            if(this.rowFilters != null){
                for(RowFileter fileter:rowFilters){
                    if(fileter == null){
                        continue;
                    }
                    //过滤数据
                    filterResult = fileter.filter(rowData);
                    //如果返回false不继续后面的处理
                    if(!filterResult){
                        filterResult = false;
                        break;
                    }
                }
            }
            //处理数据
            if(rowDataProcess != null && filterResult){
                rowDataProcess.process(rowData);
            }
        }
        if(rowDataProcess != null) {
            rowDataProcess.finish();
        }
    }
}

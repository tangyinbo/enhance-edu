package com.cowboy.fileparse;

import com.cowboy.fileparse.facade.RowReader;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/5/30 15:51
 * @Description:
 */
public class ListJoinSymbolToStrTxtRowReaderd implements RowReader<String> {
    private static final String DEFAULT_SPLIT_SYMBOL = ",";
    private static final String DEFAULT_JOIN_SYMBOL = "|";
    /**
     * 分隔符
     */
    private String originJoinSymbol = DEFAULT_SPLIT_SYMBOL;
    /**
     * 连接符,默认空格
     */
    private String joinSymbol = DEFAULT_JOIN_SYMBOL;


    public ListJoinSymbolToStrTxtRowReaderd() {
    }

    /**
     * @param originJoinSymbol 原文件使用的分隔符
     * @param joinSymbol       解析后使用的分隔符
     */
    public ListJoinSymbolToStrTxtRowReaderd(String originJoinSymbol, String joinSymbol) {
        this.originJoinSymbol = originJoinSymbol;
        this.joinSymbol = joinSymbol;
    }

    /* 业务逻辑实现方法
     * @see com.eprosun.util.excel.IRowReader#getRows(int, int, java.util.List)
     */
    public RowData<String> getRows(int sheetIndex, int curRow, List<String> rowlist) {
        String value = rowlist.get(0);
        if (StringUtils.isEmpty(value)) {
            return RowData.instance("");
        } else {
            value = value.replaceAll(originJoinSymbol, joinSymbol);
            return RowData.instance(value);
        }
    }

}
package com.cowboy.fileparse;

import com.cowboy.fileparse.facade.RowReader;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/5/30 15:51
 * @Description:
 */
public class ListJoinSymbolToStrRowReaderd implements RowReader<String> {
    /**
     * 连接符,默认空格
     */
    private String joinSymbol = "|";

    public ListJoinSymbolToStrRowReaderd() {
    }

    public ListJoinSymbolToStrRowReaderd(String joinSymbol) {
        this.joinSymbol = joinSymbol;
    }

    /* 业务逻辑实现方法
     * @see com.eprosun.util.excel.IRowReader#getRows(int, int, java.util.List)
     */
    public RowData<String> getRows(int sheetIndex, int curRow, List<String> rowlist) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rowlist.size(); i++) {
            String value = rowlist.get(i);
            if(StringUtils.isEmpty(value)){
                value = "";
            }else{
                value = value.trim();
            }
            sb.append(value);
            //不是最后一个
            if (i != rowlist.size() - 1) {
                sb.append(this.joinSymbol);
            }
        }
        return RowData.instance(sb.toString());
    }

}
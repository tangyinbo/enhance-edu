package com.cowboy.excel.imports;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/4/20 16:45
 * @Description:
 */
public class ImportMapResult extends ImportResult<Map> {
    private String resMsg;//返回的信息
    /**
     * 行号
     */
    public final static String LINE_NUM_KEY = "lineNum";
    /**
     *是否合法行
     */
    public final static String IS_LINE_LEGAL_KEY = "isLineLegal";
    private List<Map> result;

    @Override
    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    @Override
    public List<Map> getResult() {
        return result;
    }

    public void setResult(List<Map> result) {
        this.result = result;
    }
}

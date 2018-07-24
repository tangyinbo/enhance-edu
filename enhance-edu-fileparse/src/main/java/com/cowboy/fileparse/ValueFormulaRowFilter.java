package com.cowboy.fileparse;

import com.cowboy.fileparse.common.KYStringUtils;
import com.cowboy.fileparse.facade.RowFileter;
import com.cowboy.fileparse.formula.StringProcessEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * 公式处理过滤器
 *
 * @Auther: Tangyinbo
 * @Date: 2018/5/31 20:25
 * @Description:
 */
public class ValueFormulaRowFilter implements RowFileter<String> {
    /**
     * 公式分隔符
     */
    private static final String formula_split = ",";
    /**
     * 公式开头
     */
    private static final String formula_prefix = "$";
    /**
     * 公式和参数分割符
     */
    private static final String formula_param_prefix = "@";
    /**
     * 公式参数分隔符
     */
    private static final String formula_param_split = ":";
    /**
     * 公式
     */
    private String formula;
    /**
     * 连接符,默认空格
     */
    private String joinSymbol = "|";

    public ValueFormulaRowFilter(String formula) {
        this.formula = formula;
    }

    public void setJoinSymbol(String joinSymbol) {
        this.joinSymbol = joinSymbol;
    }

    @Override
    public boolean filter(RowData<String> rowData) {
        String rowValueStr = rowData.getValue();
        String result = KYStringUtils.processStrBySplitStr(rowValueStr,this.joinSymbol,(r)->{
            //公式不为空
            if(!StringUtils.isEmpty(formula)){
                //获取公式列表
                String[] forms = formula.split(formula_split);
                for(String f:forms){
                    if(f.startsWith(formula_prefix)){
                        String formulaParam = f.substring(1);
                        //分割公式和参数部分
                        String[] formulaParams = formulaParam.split(formula_param_prefix);
                        //公式
                        String realFormula = formulaParams[0];
                        String[] args = null;
                        if(formulaParams.length==2){
                            args = formulaParams[1].split(formula_param_split);
                        }
                        try{
                            StringProcessEnum pro = StringProcessEnum.valueOf(realFormula);
                            //获取参数
                            r = pro.process(r,args);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            return r;
        });
        rowData.setValue(result);
        return true;
    }
}

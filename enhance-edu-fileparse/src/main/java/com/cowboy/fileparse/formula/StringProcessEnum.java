package com.cowboy.fileparse.formula;

import org.apache.commons.lang3.StringUtils;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/5/31 20:44
 * @Description:
 */
public enum StringProcessEnum {
    TRIM() {
        @Override
        public String process(String value,String[] args) {
            if(StringUtils.isEmpty(value)){
                return value;
            }
            return value.trim();
        }
    },
    APPEND_STR() {
        @Override
        public String process(String value, String[] args) {
            if(StringUtils.isEmpty(value) || args == null || args.length== 0){
                return value;
            }
            return value.concat(args[0]);
        }
    },
    TRIM_SYMBOL() {
        @Override
        public String process(String value, String[] args) {
            if(StringUtils.isEmpty(value) || args == null || args.length== 0){
                return value;
            }
            String replaceStr = args[0];
            return value.replaceAll("(^"+replaceStr+"*|"+replaceStr+"*$)","");
        }
    };
    public abstract String process(String value,String[] args);
}

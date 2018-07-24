package com.cowboy.fileparse.common;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/5/31 19:54
 * @Description:
 */
public class KYStringUtils {
    /**
     * 处理字符串根据指定分隔符
     *
     * @param str
     * @param splitStr
     */
    public static String processStrBySplitStr(String str, String splitStr, Function<String, String> process) {
        StringBuilder sb = new StringBuilder();
        //分隔符长度
        int splitStrLen = splitStr.length();
        int valueIdx = 0;
        //开始处理角标
        int startIdx = 0;
        //
        int idx = 0;
        while ((idx = str.indexOf(splitStr, idx)) != -1) {
            String value = str.substring(startIdx, idx);
            if (!StringUtils.isEmpty(value)) {
                value = process.apply(value);
            }
            sb.append(value);
            sb.append(splitStr);
            idx += splitStrLen;
            startIdx = idx;
        }
        //上次找到分隔符的位置小于字符串长度
        if (startIdx - splitStrLen < str.length()) {
            String value = str.substring(startIdx);
            if (!StringUtils.isEmpty(value)) {
                value = process.apply(value);
            }
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * 获取指定分隔符分割的字符串的第几次出现的字符, 如: |a|b|c|  第一次出现的字符是空, 第一次是 a ,第三次是c,第四次是空
     * @param str      原Str
     * @param splitStr 分隔符
     * @param times    第几次出现
     * @return 字符串位置,如果为找到返回空串
     */
    public static String getAssignTimesAppearStr(String str, String splitStr, int times) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        //分隔符长度
        int splitLen = splitStr.length();
        //开始查找分隔符角标
        int fromIdx = 0;
        //上次找到角标
        int prefindIdx = 0;
        //当前找到角标
        int findIdx = 0;
        //查找到的次数
        int findTimes = 0;
        while ((findIdx = str.indexOf(splitStr, fromIdx)) != -1) {
            //如果找到
            if (findTimes == times) {
                //如果查找第0个并且第0个就是分隔符
                if (findIdx == 0 && times == 0) {
                    return "";
                }

                int startIdx = prefindIdx + (findTimes == 0 ? 0 : splitLen);
                String findStr = str.substring(startIdx, findIdx);
                return findStr;
            }
            //下次开始查找的位置
            fromIdx = findIdx + splitLen;
            prefindIdx = findIdx;
            findTimes++;
        }
        //
        if (fromIdx < str.length() && findTimes == times) {
            return str.substring(fromIdx);
        }
        return "";
    }

    public static void main(String[] args) {
        String result = getAssignTimesAppearStr("2018-06-03 23:54:54|\"050恒协固码D0\"|212201806032354537555|P77772018060316547234|50.0|0.85|消费|支付宝-支付宝T0WAP支付|交易成功|", "|", 8);
        System.out.println(result.length() + "--" + result);
    }
}

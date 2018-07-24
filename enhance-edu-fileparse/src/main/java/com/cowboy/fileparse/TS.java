package com.cowboy.fileparse;

import java.io.IOException;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/5/31 15:57
 * @Description:
 */
public class TS {
    public static void main(String[] args) throws IOException {
        String s = "a|b|abc||c";
        //System.out.println(s.substring(7));
        rowProcessSplitValue(s,"|");
        // InputStream inputStream = new FileInputStream("D:\\company\\channel\\recon_template\\072.csv");
       // System.out.println(KYFileUtils.getCsvFileEncode(new File("D:\\company\\channel\\recon_template\\072.csv")));
        //System.out.println(KYFileUtils.getCsvFileEncode(new File("C:\\Users\\t2\\Desktop\\ss.txt")));
    }

    /**
     * 处理字符串根据指定分隔符
     * @param str
     * @param splitStr
     */
    public static void rowProcessSplitValue(String str,String splitStr){
        StringBuilder sb = new StringBuilder();
        //分隔符长度
        int splitStrLen = splitStr.length();
        int valueIdx = 0;
        //开始处理角标
        int startIdx = 0;
        //
        int idx=0;
        while((idx = str.indexOf(splitStr,idx)) != -1){
            String value = str.substring(startIdx,idx);
            System.out.println(valueIdx+":"+value);
            valueIdx++;
            sb.append(value);
            sb.append(splitStr);
            idx+=splitStrLen;
            startIdx = idx;
        }
        //上次找到分隔符的位置小于字符串长度
        if(startIdx - splitStrLen < str.length()){
            String value = str.substring(startIdx);
            System.out.println(valueIdx+":"+value);
            sb.append(value);
        }
        //sb.append(str.substring(startIdx-splitStrLen));
        System.out.println(sb.toString());
    }
}

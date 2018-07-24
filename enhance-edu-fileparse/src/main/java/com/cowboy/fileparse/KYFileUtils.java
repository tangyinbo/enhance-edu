package com.cowboy.fileparse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件工具类
 *
 * @Auther: Tangyinbo
 * @Date: 2018/5/31 15:59
 * @Description:
 */
public class KYFileUtils {
    public static final String CHARSET = "UTF-8";

    /**
     * 获取csv文件夹编码
     *
     * @return
     */
    public static String getFileEncode(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        int p = (inputStream.read() << 8) + inputStream.read();
        String code = null;
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        return code;
    }
}

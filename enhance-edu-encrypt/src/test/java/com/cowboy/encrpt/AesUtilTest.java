package com.cowboy.encrpt;

import com.cowboy.encrypt.utils.AESUtil;
import org.junit.Test;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/5/21 11:26
 * @Description:
 */
public class AesUtilTest {
    @Test
    public void test1(){
        String hexKey = AESUtil.generateHexKey("aaa");
        System.out.println("hexKey:"+hexKey);
        String encodePwd = AESUtil.encrytAes("qazwsxfsfsfsfsfsfsffsfsedcrfv",hexKey);
        System.out.println("encodePwd:"+encodePwd);
        String pwd = AESUtil.decryptAes(encodePwd,hexKey);
        System.out.println("pwd:"+pwd);
    }
}

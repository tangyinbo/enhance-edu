package com.cowboy.http.exception;

import com.cowboy.http.consts.HttpDealCodeEnum;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/8/15 17:30
 * @Description:
 */
public class MyHttpClientException extends RuntimeException {
    /**
     * 异常编码
     */
    private String code;
    /**
     * 用户异常信息(展示给用户)
     */
    private String userMsg;
    /**
     * 系统异常信息,打印系统定位问题
     */
    private String sysMsg;

    public MyHttpClientException(HttpDealCodeEnum dealCodeEnum, Throwable cause) {
        super(dealCodeEnum.getSysMsg(), cause);
        this.code = dealCodeEnum.getCode();
        this.userMsg = dealCodeEnum.getUserMsg();
        this.sysMsg = dealCodeEnum.getSysMsg();
    }

    public MyHttpClientException(HttpDealCodeEnum dealCodeEnum, String msgDetail) {
        super(dealCodeEnum.getSysMsg() + ":" + msgDetail);
        this.code = dealCodeEnum.getCode();
        this.userMsg = dealCodeEnum.getUserMsg();
        this.sysMsg = dealCodeEnum.getSysMsg() + ":" + msgDetail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }

    public String getSysMsg() {
        return sysMsg;
    }

    public void setSysMsg(String sysMsg) {
        this.sysMsg = sysMsg;
    }
}

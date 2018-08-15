package com.cowboy.http.consts;

/**
 * @Auther: Tangyinbo
 * @Date: 2018/8/15 17:33
 * @Description:
 */
public enum HttpDealCodeEnum {
    PARAM_VALIDATE_ERROR("1001", "参数校失败!");

    HttpDealCodeEnum(String code, String desc) {
        this.code = code;
        this.userMsg = desc;
        this.sysMsg = desc;
    }

    private String code;
    private String userMsg;
    private String sysMsg;

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

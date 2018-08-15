package com.cowboy.http;

import com.cowboy.http.consts.HttpDealCodeEnum;
import com.cowboy.http.consts.HttpMethodEnum;
import com.cowboy.http.exception.MyHttpClientException;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * http请求参数定义
 *
 * @Auther: Tangyinbo
 * @Date: 2018/8/15 17:22
 * @Description:
 */
public class HttpReqDefine {
    /**
     * 表单提交URL
     */
    private String url;
    /**
     * 表单提交方法
     */
    private HttpMethodEnum method;
    /**
     * 获取连接超时时间,单位毫秒
     */
    private int connectionRequestTimeout;
    /**
     * 连接超时,单位毫秒
     */
    private int connectTimeout;
    /**
     * 获取数据超时,单位毫秒
     */
    private int socketTimeout;
    /**
     * 请求参数头
     */
    private List<Header> headers;
    /**
     * 参数
     */
    private List<NameValuePair> params;

    public String getUrl() {
        return url;
    }

    public HttpMethodEnum getMethod() {
        return method;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public List<NameValuePair> getParams() {
        return params;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(HttpMethodEnum method) {
        this.method = method;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public void setParams(List<NameValuePair> params) {
        this.params = params;
    }
}

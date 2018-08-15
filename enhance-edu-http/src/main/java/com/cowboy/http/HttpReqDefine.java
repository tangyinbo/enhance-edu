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

    /**
     * 获取构建者
     *
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * 表单构造者
     */
    public static class Builder {
        private String url;
        private HttpMethodEnum method;
        private int connectionRequestTimeout;
        private int connectTimeout;
        private int socketTimeout;
        private List<NameValuePair> params;
        private List<Header> headers;
        private String keywords;

        /**
         * 设置请求头参数
         *
         * @param headers
         * @return
         */
        public Builder setHeaders(List<Header> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * 添加请求头
         *
         * @param name
         * @param value
         * @return
         */
        public Builder addHeaders(String name, String value) {
            if (this.headers == null) {
                this.headers = new ArrayList<>();
            }
            Header header = new BasicHeader(name, value);
            this.headers.add(header);
            return this;
        }


        /**
         * 设置请求参数
         *
         * @param params 请求参数
         * @return
         */
        public Builder setParams(List<NameValuePair> params) {
            this.params = params;
            return this;
        }

        /**
         * 添加表单参数
         *
         * @param paramName
         * @param paramValue
         * @return
         */
        public Builder addParam(String paramName, String paramValue) {
            NameValuePair nameValuePair = new BasicNameValuePair(paramName, paramValue);
            if (params == null) {
                params = new ArrayList<>();
            }
            params.add(nameValuePair);
            return this;
        }

        /**
         * 设置请求URL
         *
         * @param url
         * @return
         */
        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * 设置请求方法,{@link HttpMethodEnum}
         *
         * @param method 请求方法
         * @return
         */
        public Builder setMethod(HttpMethodEnum method) {
            this.method = method;
            return this;
        }

        /**
         * 获取http连接超时时间
         *
         * @param connectionRequestTimeout
         * @return
         */
        public Builder setConnectionRequestTimeout(int connectionRequestTimeout) {
            this.connectionRequestTimeout = connectionRequestTimeout;
            return this;
        }

        /**
         * 连接服务超时时间
         *
         * @param connectTimeout
         * @return
         */
        public Builder setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * 获取数据超时时间
         *
         * @param socketTimeout
         * @return
         */
        public Builder setSocketTimeoutt(int socketTimeout) {
            this.socketTimeout = socketTimeout;
            return this;
        }

        /**
         * 关键字,用户生成日志记录供以后检索日志使用
         *
         * @param keywords
         * @return
         */
        public Builder setKeyWords(String keywords) {
            this.keywords = keywords;
            return this;
        }

        public void paramCheck() {
            StringBuilder sb = new StringBuilder();
            if (isEmpty(this.url)) {
                sb.append("URL不能为空!;");
            }
            if (this.method == null) {
                sb.append("请求方法不能为空!;");
            }
            if (this.method == HttpMethodEnum.POST) {
                if (this.params == null || this.params.size() == 0) {
                    sb.append("请求参数不能为空!;");
                }
            }
            if (sb.length() != 0) {
                //参数校验失败
                throw new MyHttpClientException(HttpDealCodeEnum.PARAM_VALIDATE_ERROR, sb.toString());
            }
        }

        /**
         * 构造表单提交
         *
         * @return
         */
        public HttpReqDefine build() {
            //参数验证
            paramCheck();
            HttpReqDefine form = new HttpReqDefine();
            form.setUrl(this.url);
            form.setMethod(this.method);
            form.setParams(this.params);
            form.setConnectionRequestTimeout(this.connectionRequestTimeout);
            form.setConnectTimeout(this.connectTimeout);
            form.setSocketTimeout(this.socketTimeout);
            form.setHeaders(this.headers);
            return form;
        }

        private boolean isEmpty(String str) {
            return str == null || str.length() == 0;
        }
    }

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

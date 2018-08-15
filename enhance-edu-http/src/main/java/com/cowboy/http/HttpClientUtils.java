package com.cowboy.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * Http 客户端工具类
 *
 * @Auther: Tangyinbo
 * @Date: 2018/8/15 16:44
 * @Description:
 */
public class HttpClientUtils {

    private static final String WORK_PATH = "";

    /**
     * 建立连接后获取数据的默认时间</p>
     * Defines the socket timeout (SO_TIMEOUT) in milliseconds,
     * which is the timeout for waiting for data or, put differently, a maximum period inactivity between two consecutive data packets)
     */
    private static final int DEFAULT_SOCKET_TIMEOUT = 30000;
    /**
     * 默认完成三次握手，建立tcp链接所用的时间
     */
    private static final int DEFAULT_CONNECT_TIMEOUT = 4000;

    /**
     * 默认编码
     */
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * @param url    :url
     * @param params :参数
     * @return 返回的字符串
     * @Description 处理http请求的post方法
     */
    public static String post(String url, Hashtable<String, String> params) throws Exception {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = "";
        try {
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(DEFAULT_SOCKET_TIMEOUT).setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).build();
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            List<NameValuePair> ps = new ArrayList<NameValuePair>();
            for (String pKey : params.keySet()) {
                ps.add(new BasicNameValuePair(pKey, params.get(pKey)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(ps, DEFAULT_CHARSET));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity, DEFAULT_CHARSET);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                // result = "";
            }
        }
        return result;
    }

    /**
     * @param url    :url
     * @param params :参数
     * @return 返回的字符串
     * @Description 处理http请求的get方法
     */
    public static String get(String url, Hashtable<String, String> params) throws IOException {
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;

        String result = "";
        try {
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(DEFAULT_SOCKET_TIMEOUT).setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).build();
            String ps = "";
            for (String pKey : params.keySet()) {
                if (!"".equals(ps)) {
                    ps = ps + "&";
                }
                // 处理特殊字符，%替换成%25，空格替换为%20，#替换为%23
                String pValue = params.get(pKey).replace("%", "%25").replace(" ", "%20").replace("#", "%23");
                ps += pKey + "=" + pValue;
            }
            if (!"".equals(ps)) {
                url = url + "?" + ps;
            }
            httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity, DEFAULT_CHARSET);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (httpGet != null) {
                    httpGet.releaseConnection();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                //result = "";
            }
        }
        return result;
    }

    /**
     * @param url    :url
     * @param params :参数
     * @return 返回的字符串
     * @Description 处理https请求的post方法
     */
    public static String postSSL(String url, Hashtable<String, String> params) throws Exception {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = "";
        try {
            httpClient = (CloseableHttpClient) wrapClient();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(DEFAULT_SOCKET_TIMEOUT).setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).build();
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            List<NameValuePair> ps = new ArrayList<NameValuePair>();
            for (String pKey : params.keySet()) {
                ps.add(new BasicNameValuePair(pKey, params.get(pKey)));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(ps, DEFAULT_CHARSET));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();

            result = EntityUtils.toString(httpEntity, DEFAULT_CHARSET);

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                //result = "";
            }
        }
        return result;
    }

    /**
     * @param url    :url
     * @param params :参数
     * @return 返回的字符串
     * @Description 处理https请求的get方法
     */
    public static String getSSL(String url, Hashtable<String, String> params) throws IOException {
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;

        String result = "";
        try {
            httpClient = (CloseableHttpClient) wrapClient();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT).setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).build();
            String ps = "";
            for (String pKey : params.keySet()) {
                if (!"".equals(ps)) {
                    ps = ps + "&";
                }
                // 处理特殊字符，%替换成%25，空格替换为%20，#替换为%23
                String pValue = params.get(pKey).replace("%", "%25")
                        .replace(" ", "%20").replace("#", "%23");
                ps += pKey + "=" + pValue;
            }
            if (!"".equals(ps)) {
                url = url + "?" + ps;
            }
            httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity, DEFAULT_CHARSET);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (httpGet != null) {
                    httpGet.releaseConnection();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                // result = "";
            }
        }
        return result;
    }

    /**
     * @param url    :url
     * @param params :参数
     * @return 返回的字符串
     * @Description 处理http请求的post方法（含有大数据的参数）
     */
    public static String postMultipart(String url, Map<String, String> params) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = "";
        String fileName = "";
        try {
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(4000).setConnectTimeout(4000).build();
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);

            List<NameValuePair> ps = new ArrayList<NameValuePair>();
            for (String pKey : params.keySet()) {
                ps.add(new BasicNameValuePair(pKey, params.get(pKey)));
            }

            Hashtable<String, File> files = new Hashtable<String, File>();
            fileName = WORK_PATH + params.get("FileName") + ".txt";
            String content = params.get("Report");

            //FileIOMethod.SaveTextFile(fileName, content, DEFAULT_CHARSET);
            File file = new File(fileName);
            files.put("Report", file);
            HttpEntity entity = makeMultipartEntity(ps, files);
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity, DEFAULT_CHARSET);
        } catch (ClientProtocolException e) {
            result = "";
        } catch (IOException e) {
            result = "";
        } finally {
            //FileIOMethod.DelFile(fileName);
            try {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                result = "";
            }
        }
        return result;
    }

    /**
     * @param url    :url
     * @param params :参数
     * @return 返回的字符串
     * @Description 处理https请求的post方法（含有大数据的参数）
     */
    public static String postMultipartSSL(String url,
                                          Hashtable<String, String> params) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = "";
        String fileName = "";
        try {
            httpClient = (CloseableHttpClient) wrapClient();
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(4000).setConnectTimeout(4000).build();
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);

            List<NameValuePair> ps = new ArrayList<NameValuePair>();
            for (String pKey : params.keySet()) {
                ps.add(new BasicNameValuePair(pKey, params.get(pKey)));
            }

            Hashtable<String, File> files = new Hashtable<String, File>();
            fileName = WORK_PATH + params.get("FileName") + ".txt";
            String content = params.get("Report");
            //FileIOMethod.SaveTextFile(fileName, content, DEFAULT_CHARSET);
            File file = new File(fileName);
            files.put("Report", file);
            HttpEntity entity = makeMultipartEntity(ps, files);
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity, DEFAULT_CHARSET);

        } catch (ClientProtocolException e) {
            result = "";
        } catch (IOException e) {
            result = "";
        } finally {
            //FileIOMethod.DelFile(fileName);
            try {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                result = "";
            }
        }
        return result;
    }

    /**
     * @return HttpClient
     * @Description 创建一个不进行正式验证的请求客户端对象 不用导入SSL证书
     */
    public static HttpClient wrapClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] arg0,
                                               String arg1) throws CertificateException {
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(ssf).build();
            return httpclient;
        } catch (Exception e) {
            return HttpClients.createDefault();
        }
    }

    /**
     * @param params :字符串的入参
     * @param files  :大数据的入参
     * @return HttpClient
     * @Description 创建一个HTTPEntity对象
     */
    public static HttpEntity makeMultipartEntity(List<NameValuePair> params,
                                                 Map<String, File> files) {

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE); // 如果有SocketTimeoutException等情况，可修改这个枚举
        if (params != null && params.size() > 0) {
            for (NameValuePair p : params) {
                builder.addTextBody(p.getName(), p.getValue(),
                        ContentType.TEXT_PLAIN.withCharset(DEFAULT_CHARSET));
            }
        }

        if (files != null && files.size() > 0) {
            Set<Map.Entry<String, File>> entries = files.entrySet();
            for (Map.Entry<String, File> entry : entries) {
                builder.addPart(entry.getKey(), new FileBody(entry.getValue()));
            }
        }

        return builder.build();

    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String url = "";
        Hashtable<String, String> params = new Hashtable<String, String>();
        params.put("username", "123");
        params.put("password", "123");
        String a = postSSL(url, params);
        System.out.println("值为：" + a);


    }

}

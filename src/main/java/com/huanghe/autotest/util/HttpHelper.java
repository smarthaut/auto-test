package com.huanghe.autotest.util;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.log4j.Logger;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;

/**
 * Based on HttpClient4.x
 */
public class HttpHelper {
    public  static  Logger log = Logger.getLogger(HttpHelper.class);

    public static String get(String requestUrl){
        return get(requestUrl,null,null);
    }

    public static String get(String requestUrl, HttpClientContext context,CookieStore cookieStore ){
        CloseableHttpClient httpClient;
        if (cookieStore == null) {
             httpClient = HttpClients.createDefault();
        }
        else {
             httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();;
        }

        try {
            HttpGet httpGet = new HttpGet(requestUrl);
            HttpResponse response = httpClient.execute(httpGet,context);
            checkStatus(response.getStatusLine(),requestUrl);
            String responseStr = EntityUtils.toString(response.getEntity());
            httpGet.releaseConnection();
            return responseStr;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("get", e);
            throw ExceptionBuilder.create(e.getMessage());
        }finally {
            close(httpClient);
        }
    }


    private static void close(CloseableHttpClient httpClient) {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String post(String requestUrl, String requestbody, HttpClientContext context,CookieStore cookieStore) {
        return post(requestUrl, requestbody,"utf-8",context,cookieStore);
    }
    public static String post(String requestUrl, String requestbody) {
        return post(requestUrl, requestbody,"utf-8",null,null);
    }

    private static String post(String requestUrl, String requestbody, String encode, HttpClientContext context,CookieStore cookieStore){
        CloseableHttpClient httpClient;
        if (cookieStore == null) {
            httpClient = HttpClients.createDefault();
        }
        else {
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();;
        }
        if( requestUrl==null || requestUrl.trim().length()==0 ) {
            throw ExceptionBuilder.create("请求地址为空");
        }
        try {
            HttpPost post = new HttpPost(requestUrl);
            //目前都是application/x-www-form-urlencoded，后续可以放到请求参数中
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");
            StringEntity entity = new StringEntity(requestbody, encode);
            post.setEntity(entity);
            HttpResponse response = httpClient.execute(post,context);
            checkStatus(response.getStatusLine(),requestUrl);
            String responseStr = EntityUtils.toString(response.getEntity());
            post.releaseConnection();
            return responseStr;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("post", e);
            throw ExceptionBuilder.create(e.getMessage());
        }finally {
            close(httpClient);
        }
    }


    private static void checkStatus(StatusLine statusLine, String url) {
        int  status = statusLine.getStatusCode();
        if (status == HttpStatus.SC_BAD_REQUEST || status == SC_NOT_FOUND){
            
            throw ExceptionBuilder.create("网络地址错误,url:"+url);
        }
        if (status == HttpStatus.SC_INTERNAL_SERVER_ERROR){
            throw ExceptionBuilder.create("服务器响应错误,url:"+url);
        }
    }


}

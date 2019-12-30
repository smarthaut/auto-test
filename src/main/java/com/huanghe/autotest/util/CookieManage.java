package com.huanghe.autotest.util;
import	java.io.FileReader;
import	java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import	java.io.File;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.log4j.Logger;

import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Time : 2019-12-26 10:48
 * @Author : huanghe
 * @File : CookieManage
 * @Software: IntelliJ IDEA
 */
public class CookieManage {
    private static final String ATTRIBS = "attribs";
    private static Logger logger = Logger.getLogger(CookieManage.class);

    public static String toJSONString(CookieStore cookieStore) {
        List<JSONObject> list = cookieStore.getCookies().stream().map(c -> {
            JSONObject j = (JSONObject) JSON.toJSON(c);
            try {
                BasicClientCookie cookie = (BasicClientCookie) c;
                Field field = BasicClientCookie.class.getDeclaredField(ATTRIBS);
                field.setAccessible(true);
                j.put(ATTRIBS, field.get(cookie));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return j;
        }).collect(Collectors.toList());
        return JSON.toJSONString(list);
    }
    /**

     * @ param     :

     * @ return    :

     * @ Description:获取cookie

     * @ Date       :2019-12-26

    */

    public static CookieStore fromFile(String filename) {
        String cs = null;
        try {
            File file = new File(filename);
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(file));
            cs = reader.readLine();
            reader.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = JSON.parseArray(cs);
        BasicCookieStore cookieStore = new BasicCookieStore();
        for (Object o : jsonArray) {
            JSONObject json = (JSONObject) o;
            String name = json.getString("name");
            String value = json.getString("value");

            BasicClientCookie cookie = new BasicClientCookie(name, value);
            Long d = json.getLong("expiryDate");
            if (d != null) {
                cookie.setExpiryDate(new Date(d));
            }
            cookie.setPath(json.getString("path"));
            cookie.setDomain(json.getString("domain"));
            cookie.setSecure(json.getBooleanValue("secure"));
            cookie.setVersion(json.getIntValue("version"));
            JSONObject attribs = json.getJSONObject(ATTRIBS);
            if (attribs != null) {
                try {
                    Field field = BasicClientCookie.class.getDeclaredField(ATTRIBS);
                    field.setAccessible(true);
                    field.set(cookie, attribs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            cookieStore.addCookie(cookie);
        }

        return cookieStore;
    }
    /**

     * @ param     :

     * @ return    :

     * @ Description:存储cookie

     * @ Date       :2019-12-26

    */

    public static void storageCookie(){
        try {
            Map map = new HashMap();
            map.put("mobile","17621100841");
            map.put("pwd","89a5028ef7f4421b6b929e40a50d1bd577a45b06450120342a895ddbd23f6e28");
            map.put("password" , "f4d6f864d8f4eae109f17be472847cbd");
            CookieStore cookieStore = new BasicCookieStore();
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();;
            String url = "https://www.zhbbroker.cn/yiiapp/user-pwd/user-pwd-login"+
                    Map2URL.getUrlParamsByMap(map);
            HttpPost httpPost = new HttpPost(url);
            HttpClientContext context= null;
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            HttpResponse response = httpClient.execute(httpPost,context);
            String cookieValue = toJSONString(cookieStore);
            File file = new File("cookie.txt");
            if (!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(cookieValue);
            logger.info("cookie存储"+cookieValue);
            bw.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

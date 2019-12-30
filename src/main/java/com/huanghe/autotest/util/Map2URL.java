package com.huanghe.autotest.util;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @Time : 2019-12-26 10:15
 * @Author : huanghe
 * @File : Map2URL
 * @Software: IntelliJ IDEA
 */
public class Map2URL {
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        String s = "";
        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                sb.append(entry.getKey() + "=" + URLEncoder.encode(entry.getValue().toString(),"UTF-8"));
                sb.append("&");
            }
            s = sb.toString();
            if (s.endsWith("&")) {
                s = StringUtils.substringBeforeLast(s, "&");
            }
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        return "?"+s;
    }

}

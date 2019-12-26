package com.huanghe.autotest.util;

import org.apache.commons.lang.StringUtils;

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
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return "?"+s;
    }

}

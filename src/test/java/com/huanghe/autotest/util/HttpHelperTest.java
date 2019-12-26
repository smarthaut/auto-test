package com.huanghe.autotest.util;

import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * @Time : 2019-12-26 09:36
 * @Author : huanghe
 * @File : HttpHelperTest
 * @Software: IntelliJ IDEA
 */
public class HttpHelperTest {

    @Test
    public void testGet() {
    }

    @Test
    public void testGet1() {
    }

    @Test
    public void testPost() {
        Map map = new HashMap();
        map.put("mobile","17621100841");
        map.put("pwd","89a5028ef7f4421b6b929e40a50d1bd577a45b06450120342a895ddbd23f6e28");
        map.put("password" , "f4d6f864d8f4eae109f17be472847cbd");
        System.out.println(HttpHelper.post("https://www.zhbbroker.cn/yiiapp/user-pwd/user-pwd-login"+
                Map2URL.getUrlParamsByMap(map),""));
    }

    @Test
    public void testPost1() {
    }
}
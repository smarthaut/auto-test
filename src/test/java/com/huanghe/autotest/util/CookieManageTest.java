package com.huanghe.autotest.util;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.testng.annotations.Test;

import java.util.List;


/**
 * @Time : 2019-12-26 14:28
 * @Author : huanghe
 * @File : CookieManageTest
 * @Software: IntelliJ IDEA
 */
public class CookieManageTest {

    @Test
    public void testToJSONString() {
    }

    @Test
    public void testFromFile() {
        CookieStore cookieStore = CookieManage.fromFile("cookie.txt");
        List<Cookie> data = cookieStore.getCookies();
        System.out.println(data);
    }

    @Test
    public void testStorageCookie() {
    }
}
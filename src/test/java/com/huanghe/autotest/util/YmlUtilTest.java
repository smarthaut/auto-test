package com.huanghe.autotest.util;

import org.testng.annotations.Test;


/**
 * @Time : 2019-12-18 18:17
 * @Author : huanghe
 * @File : YmlUtilTest
 * @Software: IntelliJ IDEA
 */
public class YmlUtilTest {

    @Test
    public void testGetYmlByFileName() {
        System.out.println(YmlUtil.getValue("mysql.host"));

    }
}
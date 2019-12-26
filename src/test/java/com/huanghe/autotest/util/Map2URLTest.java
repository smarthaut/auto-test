package com.huanghe.autotest.util;

import org.testng.annotations.Test;

import java.util.Map;

/**
 * @Time : 2019-12-26 10:18
 * @Author : huanghe
 * @File : Map2URLTest
 * @Software: IntelliJ IDEA
 */
public class Map2URLTest {

    @Test
    public void testGetUrlParamsByMap() {
        Map map = XmlUtil.readXml("test01.xml");
        System.out.println(Map2URL.getUrlParamsByMap(map));
    }
}
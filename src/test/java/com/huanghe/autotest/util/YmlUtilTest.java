package com.huanghe.autotest.util;

import org.testng.annotations.Test;

import java.util.Map;


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
        Map yamlData = YmlUtil.getYmlByFileName("config.yml");
        Map insuredData = (Map) yamlData.get("insured");
        System.out.println(insuredData.get("department"));
        System.out.println(yamlData);
    }
}
package com.huanghe.autotest.data;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @Time : 2019-12-25 10:02
 * @Author : huanghe
 * @File : DataBuilderTest
 * @Software: IntelliJ IDEA
 */
public class DataBuilderTest {

    @Test
    public void testGenerateXML() {
        DataBuilder.generateXML("黑龙江_哈尔滨.xml","2018-01-01");
    }
}
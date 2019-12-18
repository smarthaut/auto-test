package com.huanghe.autotest.common;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @Time : 2019-12-16 16:14
 * @Author : huanghe
 * @File : RunCaseJsonTest
 * @Software: IntelliJ IDEA
 */
public class RunCaseJsonTest {

    @Test
    public void testRunCase() {
        System.out.println(new RunCaseJson().runCase("https://api.apiopen.top/getAllUrl",
                "","get").asString());
        Assert.assertEquals(new RunCaseJson().runCase("https://api.apiopen.top/getAllUrl",
                "","get").statusCode(),200);
    }
}
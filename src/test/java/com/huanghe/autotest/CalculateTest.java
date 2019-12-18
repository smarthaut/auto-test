package com.huanghe.autotest;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @Time : 2019-12-16 15:26
 * @Author : huanghe
 * @File : CalculateTest
 * @Software: IntelliJ IDEA
 */
public class CalculateTest {

    @Test
    public void testAdd() {
        Assert.assertEquals(new Calculate().add(1,2),3);
        System.out.println("testAdd");
    }
}
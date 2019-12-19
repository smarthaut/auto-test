package com.huanghe.autotest.util;

import org.testng.annotations.Test;

import javax.sound.midi.Soundbank;

import static org.testng.Assert.*;

/**
 * @Time : 2019-12-19 14:39
 * @Author : huanghe
 * @File : XlsUtilTest
 * @Software: IntelliJ IDEA
 */
public class XlsUtilTest {

    @Test
    public void testReadExcel() {
        System.out.println(XlsUtil.readExcel("appcasedate.xlsx","newduojia"));
    }

    @Test
    public void testGetWorkbok() {
    }
}
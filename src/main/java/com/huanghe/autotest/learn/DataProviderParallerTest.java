package com.huanghe.autotest.learn;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @Time : 2019-12-31 09:07
 * @Author : huanghe
 * @File : DataProviderParallerTest
 * @Software: IntelliJ IDEA
 */
public class DataProviderParallerTest {

    @Test(dataProvider = "test2")
    public void test2(String param1,String param2){
        long id = Thread.currentThread().getId();
        System.out.println(param1+"     "+param2 + " thread id:"+id);
    }


    @Test(dataProvider = "test1")
    public void test1(String param1,String param2){
        long id = Thread.currentThread().getId();
        System.out.println(param1+"     "+param2 + " thread id:"+id);
    }

    @DataProvider(name = "test2")
    public static Object[][] parallel2Test(){
        return new Object[][]{
                {"a","b"},
                {"c","d"}};
    }

    @DataProvider(name = "test1")
    public static Object[][] parallel1Test(){
        return new Object[][]{
                {"dataprovider test1 data1","dataprovider test1 data1"},
                {"dataprovider test1 data2","dataprovider test1 data2"}};
    }
}

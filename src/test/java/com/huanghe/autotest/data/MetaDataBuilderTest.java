package com.huanghe.autotest.data;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @Time : 2019-12-31 09:46
 * @Author : huanghe
 * @File : MetaDataBuilderTest
 * @Software: IntelliJ IDEA
 */
public class MetaDataBuilderTest {

    @Test
    public void testMetaData() {
        List<String> fileNames=new ArrayList<>();
        fileNames.add("吉林_长春.xml");
        fileNames.add("黑龙江_哈尔滨.xml");
        System.out.println(MetaDataBuilder.metaData(fileNames));
    }
}
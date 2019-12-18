package com.huanghe.autotest.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @Time : 2019-12-17 14:38
 * @Author : huanghe
 * @File : XmlUtilTest
 * @Software: IntelliJ IDEA
 */
public class XmlUtilTest {

    @Test
    public void testRead() throws DocumentException {
        XmlUtil xmlUtil = new XmlUtil();
        Document document = xmlUtil.read("test.xml");
        System.out.println(document.asXML());
    }

    @Test
    public void testGetElement()throws DocumentException {
        Document document = new XmlUtil().read("test.xml");
        Element element = document.getRootElement();
        System.out.println(element.getName());
    }

    @Test
    public void testGetAllIterator() {


    }

    @Test
    public void testGetFooIterator() {
    }

    @Test
    public void testGetAttributeIterator() {
    }

    @Test
    public void testTreeWalk() {
    }

    @Test
    public void testGetStringXml() {
    }

    @Test
    public void testGetDocumentXml() {
    }

    @Test
    public void testSimpleWrite() {
    }

    @Test
    public void testFormatWrite() {
    }
}
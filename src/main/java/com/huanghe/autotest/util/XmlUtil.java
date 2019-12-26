package com.huanghe.autotest.util;
import java.util.*;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import java.io.File;
import java.io.FileWriter;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
 * @Time : 2019-12-16 17:39
 * @Author : huanghe
 * @File : XmlUtil
 * @Software: IntelliJ IDEA
 */
public class XmlUtil {
    /**
     * 建立一个XML文档,文档名由输入属性决定
     *
     * @param filename
     *            需建立的文件名
     * @return 返回操作结果, 0表失败, 1表成功
     */
    public static int createXMLFile(Map dataMap,String filename) {/** 返回操作结果, 0表失败, 1表成功 */
        int returnValue = 0;
        /** 建立document对象 */
        Document document = DocumentHelper.createDocument();
        /** 建立XML文档的根 */
        Element datasElement = document.addElement("datas");
        /** 加入第一个节点 */
        Set<Map.Entry<String,Object>> mapdata = dataMap.entrySet();
        Iterator<Map.Entry < String, Object>> iter = mapdata.iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();
            Element dataElement = datasElement.addElement("data");
            /** 加入show属性内容 */
            dataElement.addAttribute("name", entry.getKey());
            if ((""+entry.getValue()).equals("null")) {
                dataElement.addAttribute("value", "");
            } else {
                dataElement.addAttribute("value", entry.getValue()+"");
            }
        }
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("utf-8");
            /** 将document中的内容写入文件中 */
            XMLWriter writer = new XMLWriter(new FileWriter(new File(filename)),format);
            writer.write(document);
            writer.close();
            /** 执行成功,需返回1 */
            returnValue = 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnValue;
    }
    public static Map<String,String> readXml(String filename){
        Map<String,String> dataMap = new HashMap();
        try {
            //创建事件解析器
            SAXReader sr = new SAXReader();
            //读取XML文件并创建文本容器
            Document doc = sr.read(filename);
            //获取XML根节点
            Element root = doc.getRootElement();
            //遍历根节点
            Iterator<Element> iterator = root.elementIterator();
            while (iterator.hasNext()){
                Element dataelement = iterator.next();
                String name = dataelement.attributeValue("name");
                String value = dataelement.attributeValue("value");
                dataMap.put(name,value);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
        return dataMap;

    }
/**

 * @ param     :attr filename

 * @ return    : value

 * @ Description:根据节点名字获取value

 * @ Date       :2019-12-26

*/

    public static  String getValueByattr(String attr,String filename){
        Map dataMap = readXml(filename);
        String value = dataMap.get(attr).toString();
        return value;
    }

    public static void main(String[] args) {
        System.out.println(readXml("test01.xml"));
        System.out.println(getValueByattr("insured_certificates_valid_date","test01.xml"));
    }



}

package com.huanghe.autotest.util;
import java.util.Iterator;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;


/**
 * @Time : 2019-12-16 17:39
 * @Author : huanghe
 * @File : XmlUtil
 * @Software: IntelliJ IDEA
 */
public class XmlUtil {




        /**
         * 其中,reader的read方法是重载的,可以从InputStream,File,Url等多种不同的源来读取.
         * 得到的Document对象就代表了整个XML. 读取的字符编码是按照XML文件头定义的编码来转换的.
         * 如果遇到乱码问题,注意要把各处的编码名称保持一致即可.
         *
         * @param fileName
         * @return
         * @decription
         * @throws DocumentException
         */
        public static  Document read(String fileName) throws DocumentException {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new File(fileName));
            return document;
        }

        /**
         * 读取后的第二步就是得到Root节点, 熟悉XML的人都知道,一切XML分析都是从Root元素开始的.
         *
         * @param doc
         * @return
         */
        public Element getElement(Document doc) {
            return doc.getRootElement();
        }

        /**
         * 1) 枚举(Iterator) 获取所有子节点的迭代
         *
         * @param doc
         */
        public void getAllIterator(Document doc) {
            Element root = this.getElement(doc);
            // 枚举所有子节点
            for (Iterator i = root.elementIterator(); i.hasNext();) {
                Element element = (Element) i.next();
                String text = element.getText();
                System.out.println(text);
                // do something
            }
        }

        /**
         * 1) 枚举(Iterator) 获取制定节点的迭代
         *
         * @param doc
         * @param fooName
         */
        public void getFooIterator(Document doc, String fooName) {
            Element root = this.getElement(doc);
            // 枚举名称为foo的节点
            for (Iterator i = root.elementIterator(fooName); i.hasNext();) {
                Element foo = (Element) i.next();
                String text = foo.getText();
                System.out.println(text);
                // do something
            }
        }

        /**
         * 1) 枚举(Iterator) 获取某节点的所有属性的迭代
         *
         * @param doc
         */
        public void getAttributeIterator(Document doc) {
            Element root = this.getElement(doc);
            // 枚举属性
            for (Iterator i = root.attributeIterator(); i.hasNext();) {
                Attribute attribute = (Attribute) i.next();
                String text = attribute.getText();
                System.out.println(text);
                // do something
            }
        }

        /**
         * 2)递归 递归也可以采用Iterator作为枚举手段，但文档中提供了另外的做法
         *
         * @param doc
         */
        public void treeWalk(Document doc) {
            Element root = this.getElement(doc);
            treeWalk(root);
        }

        private void treeWalk(Element element) {
            for (int i = 0, size = element.nodeCount(); i < size; i++) {
                Node node = element.node(i);
                if (node instanceof Element) {
                    treeWalk((Element) node);
                } else {
                    String text = node.getText();
                    System.out.println(text);
                    // do something
                }
            }
        }

        /**
         *
         * @Title: getStringXml
         * @Description:XML转字符串
         * @param doc
         * @return String
         * @author NoureWang
         * @date Oct 13, 2011
         */
        public String getStringXml(Document doc){
            return doc.asXML();
        }
        /**
         * 字符串转XML
         * @param xml
         * @return
         * @throws DocumentException
         */
        public Document getDocumentXml(String xml) throws DocumentException {
            return DocumentHelper.parseText(xml);
        }
        /**
         * 一个简单的输出方法是将一个Document或任何的Node通过write方法输出
         * @param doc
         * @param xml
         * @throws IOException
         */
        public void simpleWrite(Document doc,String xml) throws IOException {
            FileWriter out = new FileWriter(xml);
            doc.write(out);
        }
        /**
         * 如果你想改变输出的格式，比如美化输出或缩减格式，可以用XMLWriter类
         * @param doc
         * @throws IOException
         */
        public void formatWrite(Document doc, String filename) throws IOException {
            // 指定文件
            XMLWriter writer = new XMLWriter(new FileWriter(filename));
            writer.write(doc);
            writer.close();
            // 美化格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            writer = new XMLWriter(System.out, format);
            writer.write(doc);
            // 缩减格式
            format = OutputFormat.createCompactFormat();
            writer = new XMLWriter(System.out, format);
            writer.write(doc);
        }
        /**
         * 建立一个XML文档,文档名由输入属性决定
         *
         * @param filename
         *            需建立的文件名
         * @return 返回操作结果, 0表失败, 1表成功
         */
        public int createXMLFile(String filename) {
            /** 返回操作结果, 0表失败, 1表成功 */
            int returnValue = 0;
            /** 建立document对象 */
            Document document = DocumentHelper.createDocument();
            /** 建立XML文档的根books */
            Element booksElement = document.addElement("books");
            /** 加入一行注释 */
            booksElement.addComment("This is a test for dom4j, holen, 2004.9.11");
            /** 加入第一个book节点 */
            Element bookElement = booksElement.addElement("book");
            /** 加入show属性内容 */
            bookElement.addAttribute("show", "yes");
            /** 加入title节点 */
            Element titleElement = bookElement.addElement("title");
            /** 为title设置内容 */
            titleElement.setText("Dom4j Tutorials");
            /** 类似的完成后两个book */
            bookElement = booksElement.addElement("book");
            bookElement.addAttribute("show", "yes");
            titleElement = bookElement.addElement("title");
            titleElement.setText("Lucene Studing");
            bookElement = booksElement.addElement("book");
            bookElement.addAttribute("show", "no");
            titleElement = bookElement.addElement("title");
            titleElement.setText("Lucene in Action");
            /** 加入owner节点 */
            Element ownerElement = booksElement.addElement("owner");
            ownerElement.setText("O'Reilly");
            try {
                OutputFormat format = OutputFormat.createPrettyPrint();
                format.setEncoding("GBK");
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

        public static void main(String[] args){
            XmlUtil domu = new XmlUtil();
            domu.createXMLFile("test.xml");
        }


}

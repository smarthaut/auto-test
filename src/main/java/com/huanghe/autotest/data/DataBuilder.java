package com.huanghe.autotest.data;

import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.huanghe.autotest.util.*;


/**
 * @Time : 2019-12-24 17:41
 * @Author : huanghe
 * @File : DataBuilder
 * @Software: IntelliJ IDEA
 */
public class DataBuilder {
    private static Map<String, String> orderData;
    private static Map<String, String> insuredData;
    private static Map<String, Object> allData;
    private static Set<String> orderDateKey;
    private static Set<String> insuredDataKey;
    private static Set<String> allKey;

    /**
     * @ param     :filename:xml文件名 province:省份  date:签单日期
     * @ return    :
     * @ Description:生成对应的请求文件
     * @ Date       :2019-12-26
     */

    public static void generateXML(String filename, String date) {
        try {
            orderDateKey = new HashSet<>();
            insuredDataKey = new HashSet<>();
            allKey = new HashSet<>();
            allData = new HashMap<>();
            String province = filename.split("_")[0];
            String orderId = DBconn.QueryOrderids(province, date);
            orderData = (Map<String, String>) DBconn.QueryAllDataByOrderId(orderId).toArray()[0];
            insuredData = (Map<String, String>) YmlUtil.getYmlByFileName("config.yml").get("insured");
            orderDateKey = orderData.keySet();
            insuredDataKey = insuredData.keySet();
            allKey.addAll(orderDateKey);
            allKey.addAll(insuredDataKey);
            for (String key : allKey) {
                if (orderData.containsKey(key)) {
                    allData.put(key, orderData.get(key));
                } else {
                    allData.put(key, insuredData.get(key));
                }

            }
            //初始化一些数据
            allData.put("insured_mobile","17621100888");
            allData.put("applicant_mobile" , "17621100888");
            allData.put("owner_mobile" , "17621100888");
            allData.put("version",YmlUtil.getValue("version"));
            XmlUtil.createXMLFile(allData, filename);
            //TODO 异常处理
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @ param     :
     * @ return    :
     * @ Description:获取可报价的"省市.xml"
     * @ Date       :2019-12-26
     */

    public static List<String> generaXmlName() {
        List<String> xmlName = new ArrayList<>();
        CookieManage.storageCookie();
        String data = HttpHelper.get("https://www.zhbbroker.cn/chc-shop/v1/car/get-area-config",
                null, CookieManage.fromFile("cookie.txt"));
        JSONObject obj = JSON.parseObject(data);
        for (Map.Entry<String, Object> s : obj.entrySet()) {
            if (s.getKey() == "data") {
                List<Map<String, Object>> o = (List<Map<String, Object>>) s.getValue();
                for (int i = 0; i < o.size(); i++) {
                    String province = (String) o.get(i).get("n");
                    List<Map<String, Object>> c = (List<Map<String, Object>>) o.get(i).get("c");
                    for (int j = 0; j < c.size(); j++) {
                        String city = (String) c.get(j).get("n");
                        String name = province + "_" + city + ".xml";
                        xmlName.add(name);
                    }
                    //TODO  暂未实现指定省市获取可报价xml

                }
            }
        }
        return xmlName;
    }

    /**
     * @ param     :
     * @ return    :
     * @ Description:读取json--》string
     * @ Date       :2019-12-26
     */

    public static void readJsonData() throws IOException {
        String jsonStr = "";
        try {
            File jsonFile = new File("ProvinceCity.json");
            FileReader fileReader = new FileReader(jsonFile);

            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), "utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
            System.out.println(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List xmlNameList = generaXmlName();
        for (int i =0 ; i < xmlNameList.size();i++){
                String xmlnName = (String) xmlNameList.get(i);
                generateXML(xmlnName,"2018-01-01");
        }
    }
}

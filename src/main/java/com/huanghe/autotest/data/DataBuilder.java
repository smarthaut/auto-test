package com.huanghe.autotest.data;
import	java.util.HashMap;
import com.huanghe.autotest.util.DBconn;
import com.huanghe.autotest.util.YmlUtil;
import com.huanghe.autotest.util.XmlUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Time : 2019-12-24 17:41
 * @Author : huanghe
 * @File : DataBuilder
 * @Software: IntelliJ IDEA
 */
public class DataBuilder {
    private  static Map<String,String> orderData;
    private  static Map<String, String> insuredData;
    private  static Map<String, Object> allData;
    private static Set<String> orderDateKey;
    private static Set<String> insuredDataKey;
    private static  Set<String > allKey;
    /**

     * @ param     :filename:xml文件名 province:省份  date:签单日期

     * @ return    :

     * @ Description:生成对应的请求文件

     * @ Date       :2019-12-26

    */

    public static  void generateXML(String filename,String province,String date){
        try {
            orderDateKey = new HashSet<>();
            insuredDataKey = new HashSet<>();
            allKey = new HashSet<>();
            allData = new HashMap<> ();
            String orderId = DBconn.QueryOrderids(province,date);
            orderData = (Map<String, String>) DBconn.QueryAllDataByOrderId(orderId).toArray()[0];
            insuredData = (Map<String, String>) YmlUtil.getYmlByFileName("config.yml").get("insured");
            orderDateKey = orderData.keySet();
            insuredDataKey = insuredData.keySet();
            allKey.addAll(orderDateKey);
            allKey.addAll(insuredDataKey);
            for (String key:allKey){
                if (orderData.containsKey(key)){
                    allData.put(key,orderData.get(key));
                }else {
                    allData.put( key,insuredData.get(key));
                }

            }
            XmlUtil.createXMLFile(allData,filename);
            //TODO 异常处理
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

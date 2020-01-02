package com.huanghe.autotest.zuihuibao;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.huanghe.autotest.data.DataBuilder;
import com.huanghe.autotest.data.MetaDataBuilder;
import com.huanghe.autotest.util.*;
import io.qameta.allure.Description;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Time : 2019-12-31 14:42
 * @Author : huanghe
 * @File : RecordPriceTest
 * @Software: IntelliJ IDEA
 */
public class RecordPriceTest {
    private static Logger logger =  Logger.getLogger(RecordPriceTest.class);
    private static String host = YmlUtil.getValue("env");

    @DataProvider(name="getMetaData")
    public static  Object[][] getMetaData() {
        CookieManage.storageCookie();
        //生成指定的xml文件
        List<String> xmlNameList = DataBuilder.generaXmlName("吉林", null);
        for (String xmlName:xmlNameList){
            //根据xml生成真实的xml文件
            DataBuilder.generateXML(xmlName,"2018-01-01");
        }
        //数据初始化
        List<Map<String, String>> metaData = MetaDataBuilder.metaData(xmlNameList);
        System.out.println(metaData);
        Object[][] array = new Object[metaData.size()][7];
        for (int i = 0; i < metaData.size(); i++) {
            array[i] = metaData.get(i).values().toArray();
        }
        return array;
    }


    @Test(dataProvider = "getMetaData")
    @Description("报价")
    public void testRecord(String area,String filename,String province,String city,String provider,String district,
                           String com_id,String insurance_company){
        //获取报价单号
        Map dataMap = new HashMap();
        String paramDatas = XlsUtil.getParamsFromCasename("test_get_price_party_id");
        Map<String, String> paramMapDatas = JSONObject.parseObject(paramDatas, new TypeReference<Map<String, String>>(){});
        for (String entry : paramMapDatas.keySet()){
            dataMap.put(entry, XmlUtil.getValueByattr(entry,filename));
        }
        logger.info("获取报价单号请求参数"+dataMap);
        String response = HttpHelper.post(host+"/yiiapp/car-ins/get-price-parity-id"+ Map2URL.getUrlParamsByMap(dataMap),
                "",null, CookieManage.fromFile("cookie.txt"));
        logger.info("获取报价单号返回"+response);
        JSONObject parityJson = JSONObject.parseObject(response);
        while (parityJson.get("return_code").equals("-1001")){
            try {
                Thread.sleep(2000);
                response = HttpHelper.post(host+"/yiiapp/car-ins/get-price-parity-id"+ Map2URL.getUrlParamsByMap(dataMap),
                        "",null, CookieManage.fromFile("cookie.txt"));
                logger.info("获取报价单号返回"+response);
                parityJson = JSONObject.parseObject(response);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        String priceParityId = parityJson.getJSONObject("data").get("price_parity_id").toString();
        XmlUtil.SetValueByAttr("price_parity_id",priceParityId,filename);
        XmlUtil.SetValueByAttr("insurance_company" , insurance_company, filename);
        XmlUtil.SetValueByAttr("com_id" , com_id, filename);
        XmlUtil.SetValueByAttr("district" , district, filename);
        XmlUtil.SetValueByAttr("provider" , provider, filename);
        XmlUtil.SetValueByAttr("province" , province, filename);
        XmlUtil.SetValueByAttr("city" , city, filename);
        XmlUtil.SetValueByAttr("area",area,filename);
        //报价
        Map priceDataMap = new HashMap();
        String priceParamDatas = XlsUtil.getParamsFromCasename("test_record_price_info_merg");
        Map<String, String> priceParamMap = JSONObject.parseObject(priceParamDatas, new TypeReference<Map<String, String>>(){});
        for (String entry : priceParamMap.keySet()){
            priceDataMap.put(entry, XmlUtil.getValueByattr(entry,filename));
        }
        logger.info("报价请求参数"+dataMap);
        String priceResponse = HttpHelper.post(host+"/yiiapp/car-ins/price-policy"+ Map2URL.getUrlParamsByMap(priceDataMap),
                "",null, CookieManage.fromFile("cookie.txt"));
        logger.info("报价返回"+priceResponse);
    }
}

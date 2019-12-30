package com.huanghe.autotest;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.huanghe.autotest.util.*;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @Time : 2019-12-27 10:03
 * @Author : huanghe
 * @File : RecordPriceTest
 * @Software: IntelliJ IDEA
 */
public class RecordPriceTest  {
    public static String fileName = "吉林_长春.xml";
    private static Logger logger =  Logger.getLogger(GetFileMes.class);

    @BeforeMethod
    public void setUp() {
        CookieManage.storageCookie();
    }

    @AfterMethod
    public void tearDown() {
    }


    @Test()
    public void testQueryCarModel(){
        Map dataMap = new HashMap();
        CookieManage.storageCookie();
        String paramDatas = XlsUtil.getParamsFromCasename("test_get_car_model_no_info");
        Map<String, String> paramMapDatas = JSONObject.parseObject(paramDatas, new TypeReference<Map<String, String>>(){});
        for (String entry : paramMapDatas.keySet()){
            dataMap.put(entry,XmlUtil.getValueByattr(entry,fileName));
        }
        logger.info("车辆查询请求参数"+dataMap);
        String response = HttpHelper.post("https://www.zhbbroker.cn/yiiapp/car-info/get-car-model-no-info"+ Map2URL.getUrlParamsByMap(dataMap),
                "",null,CookieManage.fromFile("cookie.txt"));
        logger.info("查询车型返回"+response);
        Map<String,String> carModelDetail = JSONObject.parseObject(response,new TypeReference<Map<String, String>>(){});
        List<Map<String, Object>> ina = JSONObject.parseObject(carModelDetail.get("data"),
                new TypeReference<List<Map<String, Object>>>(){});
        if (ina.size() ==0){
            logger.info("根据该品牌型号未查询到相关车辆信息");
            return;
        }
        //查询车型返回最接近price的车型
        List differ = new ArrayList();
        for (Map<String, Object> data:ina){
            Number purchasePrice = (Number) data.get("price");
            Double differValue = Math.abs(purchasePrice.doubleValue()/10000-Double.valueOf(XmlUtil.getValueByattr("price",fileName)));
            differ.add(differValue);
        }
        int index = differ.indexOf(Collections.min(differ));
        String specialCarDetail = JSONObject.toJSONString(ina.get(index));
        XmlUtil.SetValueByAttr("selected_car_model_detail",specialCarDetail,fileName);

    }
    @Test(dependsOnMethods = "queryCarModel")
    public void testReplenishCarInfo(){
        Map dataMap = new HashMap();
        String paramDatas = XlsUtil.getParamsFromCasename("test_replenish_info_merge");
        Map<String, String> paramMapDatas = JSONObject.parseObject(paramDatas, new TypeReference<Map<String, String>>(){});
        for (String entry : paramMapDatas.keySet()){
            dataMap.put(entry,XmlUtil.getValueByattr(entry,fileName));
        }
        logger.info("保存车辆信息请求参数"+dataMap);
        String response = HttpHelper.post("https://www.zhbbroker.cn/yiiapp/car-info/replenish-car-info"+ Map2URL.getUrlParamsByMap(dataMap),
                "",null,CookieManage.fromFile("cookie.txt"));
        logger.info("保存车辆信息"+response);
    }
    //获取可报价保险公司
    @Test()
    public void testPriceConfiguration(){
        Map dataMap = new HashMap();
        String paramDatas = XlsUtil.getParamsFromCasename("test_price_config");
        Map<String, String> paramMapDatas = JSONObject.parseObject(paramDatas, new TypeReference<Map<String, String>>(){});
        for (String entry : paramMapDatas.keySet()){
            dataMap.put(entry,XmlUtil.getValueByattr(entry,fileName));
        }
        logger.info("获取可报价保险公司接口请求参数"+dataMap);
        String response = HttpHelper.post("https://www.zhbbroker.cn/yiiapp/car-ins/price-configuration"+
                        Map2URL.getUrlParamsByMap(dataMap),
                "",null,CookieManage.fromFile("cookie.txt"));
        logger.info("获取可报价保险公司接口返回结果"+response);
//        Map<String,String> detail = JSONObject.parseObject(response,new TypeReference<Map<String, String>>(){});
//        List<Map<String, String>> insuranceDetail = JSONObject.parseObject(detail.get("data"),new TypeReference<List<Map<String, String> > > () {
//        });
//        logger.info(insuranceDetail);
        JSONObject insuranceJson = JSONObject.parseObject(response);
        JSONArray data = insuranceJson.getJSONObject("data").getJSONArray("ins_companies");
        Iterator<Object> it = data.iterator();// 使用Iterator迭代器
        while (it.hasNext()){
            JSONObject arrayObj = (JSONObject) it.next();
            System.out.println(arrayObj.get("com_id"));
        }
        }
        //获取报价单号
        @Test
        public void testGetPriceParityId(){
            Map dataMap = new HashMap();
            String paramDatas = XlsUtil.getParamsFromCasename("test_get_price_party_id");
            Map<String, String> paramMapDatas = JSONObject.parseObject(paramDatas, new TypeReference<Map<String, String>>(){});
            for (String entry : paramMapDatas.keySet()){
                dataMap.put(entry,XmlUtil.getValueByattr(entry,fileName));
            }
            logger.info("获取可报价单号请求参数"+dataMap);
            String response = HttpHelper.post("https://www.zhbbroker.cn/yiiapp/car-ins/get-price-parity-id"+
                            Map2URL.getUrlParamsByMap(dataMap),
                    "",null,CookieManage.fromFile("cookie.txt"));
            logger.info("获取报价单号接口返回结果"+response);
            JSONObject responseData = JSONObject.parseObject(response);
            String priceParityId = responseData.getJSONObject("data").getString("price_parity_id");
            System.out.println(priceParityId);

        }
}

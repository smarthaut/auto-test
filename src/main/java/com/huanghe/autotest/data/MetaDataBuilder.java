package com.huanghe.autotest.data;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.huanghe.autotest.util.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * @Time : 2019-12-31 09:29
 * @Author : huanghe
 * @File : MetaDataBuilder
 * @Software: IntelliJ IDEA
 * @desc: 为批量报价提供原始数据
 */
public class MetaDataBuilder {
    private static org.apache.log4j.Logger logger =  Logger.getLogger(MetaDataBuilder.class);
    private static String host = YmlUtil.getValue("env");

    //；
    public static List<Map<String,String>> metaData(List<String> fileNames){
        List < Map<String, String>> data = new ArrayList<Map<String, String> > ();
        for (String fileName : fileNames){
            queryCarModel(fileName);
            replanishCarInfo(fileName);
            List < Map<String, String>> innerData = insuranceDistrict(fileName);
            data.addAll(innerData);
        }

        return data;
    }

    //车型查询
    private static void queryCarModel(String fileName){
        Map dataMap = new HashMap();
        CookieManage.storageCookie();
        String paramDatas = XlsUtil.getParamsFromCasename("test_get_car_model_no_info");
        Map<String, String> paramMapDatas = JSONObject.parseObject(paramDatas, new TypeReference<Map<String, String>>(){});
        for (String entry : paramMapDatas.keySet()){
            dataMap.put(entry,XmlUtil.getValueByattr(entry,fileName));
        }
        if (dataMap.get("car_model_no").toString().equals("")){
            dataMap.put("car_model_no", "别克SGM7150");
        }
        logger.info("车辆查询请求参数"+dataMap);
        String response = HttpHelper.post(host+"/yiiapp/car-info/get-car-model-no-info"+ Map2URL.getUrlParamsByMap(dataMap),
                "",null,CookieManage.fromFile("cookie.txt"));
        logger.info("查询车型返回"+response);
        Map<String,String> carModelDetail = JSONObject.parseObject(response,new TypeReference<Map<String, String>>(){});
        List<Map<String, Object>> ina = JSONObject.parseObject(carModelDetail.get("data"),
                new TypeReference<List<Map<String, Object>>>(){});
        if (ina.size() ==0){
            logger.info("根据该品牌型号未查询到相关车辆信息");
            XmlUtil.SetValueByAttr("selected_car_model_detail","{\"codeSet\":[\"BTYPKZUC0042\",\"BKAALD0090\"],\"extendInfo\":{\"com\":" +
                    "\"taipingyangfx\",\"guid\":\"null_别克SGM7150\"},\"brand_name\":\"别克SGM7150LMAB轿车\",\"gearbox_name\":\"手动档\",\"market_date\"" +
                    ":\"201504\",\"price\":66900,\"seat\":5,\"curb_weight\":1210,\"displacement\":1.485,\"engine_desc\":\"1.5L\",\"power\":83,\"fuel_type\":" +
                    "\"燃油\",\"standard_name\":\"别克SGM7150LMAB轿车\",\"industry_model_code\":\"BTYPKZUC0042\"," +
                    "\"model_code\":\"BKAALD0090\",\"parent_veh_name\":\"三厢 手动档 经典型 国Ⅴ\",\"description\":\"别克SGM7150LMAB轿车 三厢 手动档 经典型 国Ⅴ 1.5L 手动档 5座 201504款 参考价：￥66900.0\"}",fileName);
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
        JSONObject specialCar = JSONObject.parseObject(specialCarDetail);
        XmlUtil.SetValueByAttr("selected_car_model_detail",specialCarDetail,fileName);
        String price = (((Number)specialCar.get("price")).doubleValue()/10000)+"";
        XmlUtil.SetValueByAttr("price",price,fileName);
        XmlUtil.SetValueByAttr("seat_num",specialCar.getString("seat"),fileName);
    }

    //保存车辆信息
    private static void replanishCarInfo(String fileName){
        Map dataMap = new HashMap();
        String paramDatas = XlsUtil.getParamsFromCasename("test_replenish_info_merge");
        Map<String, String> paramMapDatas = JSONObject.parseObject(paramDatas, new TypeReference<Map<String, String>>(){});
        for (String entry : paramMapDatas.keySet()){
            dataMap.put(entry,XmlUtil.getValueByattr(entry,fileName));
        }
        logger.info("保存车辆信息请求参数"+dataMap);
        String response = HttpHelper.post(host+"/yiiapp/car-info/replenish-car-info"+ Map2URL.getUrlParamsByMap(dataMap),
                "",null,CookieManage.fromFile("cookie.txt"));
        logger.info("保存车辆信息"+response);
        JSONObject responseJson = JSONObject.parseObject(response);
        if (responseJson.get("return_code").equals("0")){
            String carId = responseJson.getJSONObject("data").getString("car_id");
            XmlUtil.SetValueByAttr("car_id",carId,fileName);
        }

    }

    //获取可报价机构
    private static List<Map<String,String>> insuranceDistrict(String fileName) {
        List<Map<String, String> > metadata = new ArrayList<Map<String, String> > ();
        String province = fileName.split("_")[0];
        String city = fileName.split("_")[1].split("\\.")[0];
        Map dataMap = new HashMap();
        String paramDatas = XlsUtil.getParamsFromCasename("test_price_config");
        Map<String, String> paramMapDatas = JSONObject.parseObject(paramDatas, new TypeReference<Map<String, String>>(){});
        for (String entry : paramMapDatas.keySet()){
            dataMap.put(entry,XmlUtil.getValueByattr(entry,fileName));
        }
        dataMap.put("province",province);
        dataMap.put("city",city);
        dataMap.put("area","");
        logger.info("获取可报价保险公司接口请求参数"+dataMap);
        String response = HttpHelper.post(host+"/yiiapp/car-ins/price-configuration"+
                        Map2URL.getUrlParamsByMap(dataMap),
                "",null,CookieManage.fromFile("cookie.txt"));
        logger.info("获取可报价保险公司接口返回结果"+response);
        JSONObject insuranceJson = JSONObject.parseObject(response);
        JSONArray data = insuranceJson.getJSONObject("data").getJSONArray("ins_companies");
        Iterator<Object> it = data.iterator();// 使用Iterator迭代器
        while (it.hasNext()){
            JSONObject arrayObj = (JSONObject) it.next();
            Map inerMap = new HashMap();
            if (arrayObj.get("is_open").equals(1)){
                inerMap.put("filename",fileName);
                inerMap.put("province",arrayObj.get("province"));
                inerMap.put("city" , arrayObj.get("city"));
                inerMap.put("area" , arrayObj.get("area"));
                inerMap.put("district" , arrayObj.get("district"));
                inerMap.put("com_id",arrayObj.get("com_id"));
                inerMap.put("provider" , arrayObj.get("provider"));
                inerMap.put("insurance_company" , arrayObj.get("insurance_company"));
            }
            metadata.add(inerMap);
        }
        return metadata;
    }


}

package com.huanghe.autotest.common;
import io.restassured.response.Response;
import static  io.restassured.RestAssured.given;

/**
 * @Time : 2019-12-16 15:35
 * @Author : huanghe
 * @File : RunCaseJson
 * @Software: IntelliJ IDEA
 */
public class RunCaseJson {
    /*
     * post或get方式请求,返回响应报文（json格式）
     * url 接口
     * bodyString:json格式的请求报文体
     * para:requestType post或get
     */
    public static Response runCase(String url,String bodyString, String requestType) {
        Response response = null;
        if (requestType.toLowerCase().equals("get"))
            response = given().contentType("application/json;charset=UTF-8")
                    .request().body(bodyString).get(url);
        else response = given().contentType("application/json;charset=UTF-8")
                .request().body(bodyString).post();

        return response;
    }
}

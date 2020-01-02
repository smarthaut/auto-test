package com.huanghe.autotest.learn;
import javax.sound.midi.Soundbank;
import java.util.HashSet;
import	java.util.Set;

import java.util.HashMap;
import java.util.Map;

/**
 * @Time : 2019-12-19 09:06
 * @Author : huanghe
 * @File : Demo
 * @Software: IntelliJ IDEA
 */
public class Demo {
    public static Map<String, String> result = new HashMap<String, String>();
    public  static Map<String, String> demo(){
        result.put("name","huanghe");
        while (result.size() ==1)
            setage();
        return result;
    }

    private static void setage() {

        result.put("age" , "18");
    }

    public static void main(String[] args) {
        Map data = new HashMap();
        data.put("1","apple");
        data.put("w" , "people");
        System.out.println(data.entrySet());
        System.out.println(data);
        String city = "长春.xml";
        System.out.println(city.split("\\.")[0]);
    }

}

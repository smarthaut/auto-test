package com.huanghe.autotest.learn;
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
        Set<String> a = new HashSet<>();
        Set<String> b = new HashSet<> ();
        a.add("apple");
        a.add("pear");
        b.add("banana");
        b.add("watermelon");
        b.add("apple");
        a.addAll(b);
        System.out.println(a);
    }

}

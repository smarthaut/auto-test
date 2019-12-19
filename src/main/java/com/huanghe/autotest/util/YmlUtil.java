package com.huanghe.autotest.util;
import org.apache.commons.lang.StringUtils;
import org.yaml.snakeyaml.Yaml;
import	java.io.InputStream;
import	java.util.HashMap;
import java.util.Map;

/**
 * @Time : 2019-12-18 17:18
 * @Author : huanghe
 * @File : YalUtil
 * @Software: IntelliJ IDEA
 */
public class YmlUtil {
    private static  String config_file = "config.yml";
    private static Map<String, String> result = new HashMap<String, String> ();
    public static  Map<String, String> getYmlByFileName(String filename){
        result = new HashMap<>();
        if(filename == null)
            filename = config_file;
        InputStream is = YmlUtil.class.getClassLoader().getResourceAsStream(filename);
        Yaml yaml =new Yaml();
        Object obj = yaml.loadAs(is,Map.class);
//        System.out.println(obj);
        Map<String, Object> param = (Map<String, Object>) obj;
        for(Map.Entry<String,Object> entry:param.entrySet()){
            String key = entry.getKey();
            Object val = entry.getValue();

            if(val instanceof Map){
                forEachYaml(key,(Map<String, Object>) val);
            }else{
                result.put(key,val.toString());
            }
        }
        return result;
    }
    /**
     * 根据key获取值
     * @param key
     * @return
     */
    public static String getValue(String key){
        Map<String,String> map = getYmlByFileName(null);
        if(map==null)return null;
        return map.get(key);
    }
    /**
     * 遍历yml文件，获取map集合
     * @param key_str
     * @param obj
     * @return
     */
    public static void forEachYaml(String key_str,Map<String, Object> obj){
        for(Map.Entry<String,Object> entry:obj.entrySet()){
            String key = entry.getKey();
            Object val = entry.getValue();
            String str_new = "";
            if(StringUtils.isNotBlank(key_str)){
                str_new = key_str+ "."+key;
            }else{
                str_new = key;
            }
            if(val instanceof Map){
                forEachYaml(str_new,(Map<String, Object>) val);
            }else{
                result.put(str_new,val.toString());
            }
        }
    }

}

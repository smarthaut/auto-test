package com.huanghe.autotest.util;


import org.apache.log4j.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @Time : 2019-12-17 16:34
 * @Author : huanghe
 * @File : GetFileMes
 * @Software: IntelliJ IDEA
 */
public class GetFileMes {
    private static Properties properties;
    Logger logger =  Logger.getLogger(GetFileMes.class);
    /**

     * @ param     :key propertiesFileName

     * @ return    : value

     * @ Description: 根据key filename 取具体的值

     * @ Date       :2019-12-18

    */

    public String getValue(String key,String propertiesFileName) throws IOException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream(propertiesFileName);
        properties = new Properties();
        properties.load(stream);
        String value = properties.getProperty(key);
        return value;
    }
    /**

     * @ param     :

     * @ return    :

     * @ Description:获取文件路径

     * @ Date       :2019-12-18

    */

    public String getFilePath(String directory,String fileName) {

        try{
            URL resource = this.getClass().getClassLoader().getResource(directory+"/"+fileName);
            String filePath = resource.toURI().getPath();
            logger.info("filePath："+filePath);
            return filePath;
        }catch (Exception e){
            logger.info(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            GetFileMes file = new GetFileMes();
            System.out.println(file.getValue("DB_host","config.properties"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}


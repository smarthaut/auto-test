package com.huanghe.autotest.util;
import	java.util.ArrayList;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Time : 2019-12-24 14:11
 * @Author : huanghe
 * @File : DBconn
 * @Software: IntelliJ IDEA
 */

public class DBconn {

    public static PreparedStatement stmt = null;
    public static Connection conn = null;

    public static void connetDatabse() throws SQLException,
            ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://172.17.1.20:3306/dbzhb?useSSL=false";
        String user = "dev";
        String password = "54dFgd%s";
        if (conn != null) {
            conn.close();
            conn = null;
        }
        conn = DriverManager.getConnection(url, user, password);
        System.out.println("Connection is Successful");

    }

    public static void closeConnet() throws SQLException {
        conn.close();
    }

    public static boolean isconnect() {

        if ( conn == null) {
            System.out.println("MySQL Connet Fail");
            return false;
        } else {
            return true;
        }
    }
/**

 * @ param     :peovince，date

 * @ return    :order_id

 * @ Description:根据省以及签单日期获取相应的订单

 * @ Date       :2019-12-24

*/

    public static String QueryOrderids(String province,String date)throws SQLException,ClassNotFoundException{
        connetDatabse();
        if (isconnect()){
            String sql = "SELECT \n" +
                    "                    order_id \n" +
                    "                    FROM user_order \n" +
                    "                    WHERE underwrite_time > ? \n" +
                    "                    AND STATUS =5\n" +
                    "                    AND province = ? \n" +
                    "                    LIMIT ?,1;";
            PreparedStatement ps = conn.prepareStatement(sql);
            int num= (int)(Math.random() * 100);
            ps.setString(1,date);
            ps.setString(2,province);
            ps.setInt(3,num);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                return rs.getString("order_id");
            }
            rs.close();
            ps.close();
            closeConnet();
        }
        //查询不到随便默认个订单
        return "81515471314424523";
    }

/**

 * @ param     :orderId

 * @ return    :

 * @ Description:

 * @ Date       :2019-12-24

*/

    public static List QueryAllDataByOrderId(String orderId) throws SQLException,ClassNotFoundException {
        connetDatabse();
        List<String> orderData = new ArrayList<>();
        if (isconnect()){
            String sql ="SELECT\n" +
                    "                b.province,\n" +
                    "        \t    b.city,\n" +
                    "        \t    b.insurance_company,\n" +
                    "            \tb.frame_no,\n" +
                    "            \tb.license_no,\n" +
                    "            \tb.car_model_no,\n" +
                    "            \tb.is_new_car,\n" +
                    "            \tb.engine_no,\n" +
                    "            \tb.seat_num,\n" +
                    "            \tb.reg_date,\n" +
                    "            \tb.owner_name,\n" +
                    "            \tb.owner_id_no,\n" +
                    "            \tb.owner_mobile,\n" +
                    "            \tb.insured_name,\n" +
                    "            \tb.insured_id_no,\n" +
                    "            \tb.insured_mobile,\n" +
                    "            \tb.applicant_name,\n" +
                    "            \tb.applicant_id_no,\n" +
                    "            \tb.applicant_mobile,\n" +
                    "            \tb.is_special_car,\n" +
                    "            \tb.is_loaned,\n" +
                    "            \tb.beneficiary,\n" +
                    "            \tb.price,\n" +
                    "            \tb.selected_car_model_detail\n" +
                    "            FROM\n" +
                    "            user_order_detail b  \n" +
                    "            WHERE\n" +
                    "                b.order_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,orderId);
            ResultSet rs = ps.executeQuery();
            orderData = convertList(rs);
            rs.close();
            ps.close();
            closeConnet();
            return orderData;

        }
        return orderData;
    }
    private static List convertList(ResultSet rs) throws SQLException{
        List list = new ArrayList();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取行的数量
        while (rs.next()) {
            Map rowData = new HashMap();//声明Map
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
            }
            list.add(rowData);
        }
        return list;
    }

    public static void main(String[] args) {
        try {
            connetDatabse();
            String orderId = QueryOrderids("黑龙江","2018-01-01");
            System.out.println(QueryAllDataByOrderId(orderId));
            closeConnet();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
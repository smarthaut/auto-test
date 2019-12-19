package com.huanghe.autotest.util;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Time : 2019-12-19 09:46
 * @Author : huanghe
 * @File : XlsUtil
 * @Software: IntelliJ IDEA
 */
public class XlsUtil {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";
    public List readExcel(String file,String sheetName) {
        try {
            // 创建输入流，读取Excel
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(file);
            // jxl提供的Workbook类
            Workbook wb = getWorkbok(file);
            Sheet sheet = wb.getSheet(sheetName);
            for (int index =0;index<sheet.getLastRowNum();index++){

            }
//            // Excel的页签数量
//            int sheet_size = wb.getNumberOfSheets();
//            for (int index = 0; index < sheet_size; index++) {
//                List<List> outerList=new ArrayList<List>();
//                // 每个页签创建一个Sheet对象
//                Sheet sheet = wb.getSheet(String.valueOf(index));
//                for (int i = 0; i < (sheet.getLastRowNum()+1); i++) {
//                    List innerList=new ArrayList();
//                    // sheet.getColumns()返回该页的总列数
//                    for (int j = 0; j < sheet.getColumns(); j++) {
//                        String cellinfo = sheet.getCell(j, i).getContents();
//                        if(cellinfo.isEmpty()){
//                            continue;
//                        }
//                        innerList.add(cellinfo);
//                        System.out.print(cellinfo);
//                    }
//                    outerList.add(i, innerList);
//                    System.out.println();
//                }
//                return outerList;
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (BiffException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 判断Excel的版本,获取Workbook
     * @param in
     * @param filename
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(String file) throws IOException{
        Workbook wb = null;
        InputStream in = XmlUtil.class.getClassLoader().getResourceAsStream(file);
        if(file.endsWith(EXCEL_XLS)){     //Excel&nbsp;2003
            wb = new HSSFWorkbook(in);
        }else if(file.endsWith(EXCEL_XLSX)){    // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }
}

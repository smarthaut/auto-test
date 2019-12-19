package com.huanghe.autotest.util;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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
    private  static HashMap<String,Object> dataMap;
    private static ArrayList innerList;
    private static ArrayList titleList;

    public static List readExcel(String file,String sheetName) {
        try {
            // 创建输入流，读取Excel
            InputStream is = XlsUtil.class.getClassLoader().getResourceAsStream(file);
            // jxl提供的Workbook类
            Workbook wb = getWorkbok(file);
            Sheet sheet = wb.getSheet(sheetName);
            innerList = new ArrayList();
            titleList = new ArrayList();
            dataMap = new HashMap();
            //获取标题
            for (int cellnum = 0; cellnum < sheet.getRow(0).getLastCellNum();cellnum++) {
                Cell cells = sheet.getRow(0).getCell(cellnum);
                titleList.add(cells.getRichStringCellValue().getString());
            }
            System.out.println("title"+titleList);

            for (int i =1;i <= sheet.getLastRowNum();i++){
                //第一行为标题，去除
                for (int j = 0; j <= sheet.getRow(i).getLastCellNum();j++){
                    Cell cell = sheet.getRow(i).getCell(j);
                    if (cell == null){
                        innerList.add("");
                        continue;
                    }
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            dataMap.put(titleList.get(j).toString(),cell.getRichStringCellValue().getString());
                            innerList.add(cell.getRichStringCellValue().getString());
                            break;case Cell.CELL_TYPE_NUMERIC: if (DateUtil.isCellDateFormatted(cell)) {
                                dataMap.put(titleList.get(j).toString(), cell.getDateCellValue());
                                innerList.add(cell.getDateCellValue());
                            } else {
                                dataMap.put(titleList.get(j).toString(), cell.getNumericCellValue());
                                innerList.add(cell.getNumericCellValue());
                            }
                            break;case Cell.CELL_TYPE_BOOLEAN: dataMap.put(titleList.get(j).toString(), cell.getBooleanCellValue());
                            innerList.add(cell.getBooleanCellValue());
                            break;case Cell.CELL_TYPE_FORMULA: dataMap.put(titleList.get(j).toString(), cell.getCellFormula());
                            innerList.add(cell.getCellFormula());
                            break;default: dataMap.put(titleList.get(j).toString(), "");
                            innerList.add("");
                            break;
                        }

                    }

                }

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("datamap"+dataMap);
        return innerList;
    }
    /**
     * 判断Excel的版本,获取Workbook
     * @param
     * @param
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

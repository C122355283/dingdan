package com.xie.dingdan.Tool;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);
    public static List<Map> excelToShopIdList(Workbook workbook) throws InvalidFormatException {
        String s;
        List<Map> list1 = new ArrayList<>();
        try {
            //工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //总行数
            int rowLength = sheet.getLastRowNum();
            System.out.println("总行数有多少行"+rowLength);
            //工作表的列
            Row row = sheet.getRow(0);

            //总列数
            int colLength = row.getLastCellNum();
            System.out.println("总列数有多少列"+colLength);
            //得到指定的单元格
            Cell cell = row.getCell(0);
            if(!String.valueOf(cell).equals("电话")){
                throw new IllegalArgumentException("表格内容有误，请确认后重试");
            }else if(!String.valueOf(row.getCell(1)).equals("昵称")){
                throw new IllegalArgumentException("表格内容有误，请确认后重试");
            }

            for (int i = 1; i <= rowLength; i++) {
                row = sheet.getRow(i);
                Map<String,String> map = new HashMap<>();
                for (int j = 0; j < colLength; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        if(!String.valueOf(cell).equals("")){
                            if(j==0){
                                String data = "";
//                                System.out.println(cell.getCellTypeEnum());
                                if(cell.getCellType() == CellType.NUMERIC){
                                    BigDecimal phon = new BigDecimal(cell.getNumericCellValue());
                                    data = String.valueOf(phon);
                                }else {
                                    data = cell.getStringCellValue();
                                }
                                data = data.trim();
                                map.put("电话",data);
                            }else if(j==1){
                                String data = "";
//                                System.out.println(cell.getCellTypeEnum());
                                if(cell.getCellType() == CellType.NUMERIC){
                                    BigDecimal phon = new BigDecimal(cell.getNumericCellValue());
                                    data = String.valueOf(phon);
                                }else {
                                    data = cell.getStringCellValue();
                                }
                                data = data.trim();
                                map.put("昵称",data);
                            }
                        }
                    }
                }
                if(map.size()!=0){
                    list1.add(map);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list1 ;
    }


//    public static Workbook createworkbook(InputStream inp) throws IOException,InvalidFormatException {
//        if (!inp.markSupported()) {
//            inp = new PushbackInputStream(inp, 8);
//        }
//        if (POIFSFileSystem.hasPOIFSHeader(inp)) {
//            return new HSSFWorkbook(inp);
//        }
//        if (POIXMLDocument.hasOOXMLHeader(inp)) {
//            return new XSSFWorkbook(OPCPackage.open(inp));
//        }
//            throw new IllegalArgumentException("你的excel版本目前解析不了,请更换表格重试，请不要使用导出的表格导入");
//    }

}

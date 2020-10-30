package com.office;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

//import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

//import com.itcast.poi.util.NumberUtils;


/**
 * use for :
 *
 * @author zoukh
 * Created in:  2019/1/4 14:37
 * @version 1.0
 * @Modified By:
 * @used in: WorkTest
 */
public class ExcelUtils {
    SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
    Map<String, CellStyle> styleMap = new HashMap<String, CellStyle>();
    public static final short DEFAULT_SHEET_INDEX = 1;



    public String toLetterString(int number) {
        if (number < 1) {// 出错了
            return null;
        }
        if (number < 27) {
            return String.valueOf((char) ('A' + number - 1));
        }
        if (number % 26 == 0) {
            return toLetterString(number / 26 - 1) + "Z";
        }
        return toLetterString(number / 26) + String.valueOf((char) ('A' + number % 26 - 1));
    }


    /**
     * 判断是否是数字
     */
    public boolean isNumeric(String str) {
        if (str.contains("-") || str.contains("(") || str.contains(")")) {
            str = str.replace("-", "").replace("(", "").replace(")", "");
        }
        String value = "";
        boolean b = true;
        if (str.contains("..")) {
            return false;
        } else if (str.substring(str.length() - 1).equals(".")) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            value = str.substring(i, i + 1);
            if (!value.equals(".")) {
                java.text.DecimalFormat nf = new java.text.DecimalFormat("00.00");
                try {
                    nf.parse(value);
                } catch (ParseException e1) {
                    b = false;
                    return b;
                }
            }
        }
        return b;
    }


    /*
     *   将字符串转为整数
     */
    public int toNum_new(String str) {
        char[] ch = str.toCharArray();
        int ret = 0;
        for (int i = 0; i < ch.length; i++) {
            ret *= 26;
            ret += ch2int(ch[i]);
        }
        return ret;
    }

    /*
     *   A~Z/a~z   转为1~26
     */
    public int ch2int(char ch) {
        if (ch >= 'a' && ch <= 'z')
            return ch - 'a' + 1;
        if (ch >= 'A' && ch <= 'Z')
            return ch - 'A' + 1;
        throw new java.lang.IllegalArgumentException();
    }

    public String int2Str(int i) {
       // int
        return "";
    }


    /**
     * 读excel
     *
     * @param filePath excel路径
     */
    public void readExcelAll(String filePath) {
        Workbook book = null;
        try {
            book = getExcelWorkbook(filePath);
            int sheetNum = book.getNumberOfSheets();
            for (int sheetItem = 0; sheetItem < sheetNum; sheetItem++) {
                Sheet sheet = getSheetByNum(book, sheetItem + 1);
                System.out.println("sheetName：" + sheet.getSheetName());
                readSheetAll(sheet);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * use for : 遍历sheet
     *
     * @author zoukh
     * @Created in:  2019/1/5 13:05
     * @Modified By:
     * @version 1.0
     * @used in: ExcelUtils
     */
    private void readSheetAll(Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();
        Row row = null;
        for (int j = 0; j <= lastRowNum; j++) {

            List <String> list= getSheetRow(sheet, j);
            if (null!=list&&list.size()>0){
                for(int i = 0;i < list.size(); i ++) {
                    System.out.printf("%10s", i+1);
                }
                System.out.println();
                for(String tmp:list) {
                    System.out.printf("%10s", tmp);
                }
            }
        }
    }

    /**
     * use for : 读一行
     *
     * @author zoukh
     * @Created in:  2019/1/5 13:15
     * @Modified By:
     * @version 1.0
     * @used in: ExcelUtils
     */
    public List<String> getSheetRow(Sheet sheet, int rowIndex) {
        List<String> list = new ArrayList<String>();
        int lastRowNum = sheet.getLastRowNum();
        Row row = null;
        row = sheet.getRow(rowIndex - 1);
        if (row != null) {
            int lastCellNum = row.getLastCellNum();
            Cell cell = null;
          //  StringBuilder sb = null;
            for (int j = 0; j < lastCellNum; j++) {
                cell = row.getCell(j);
                if (cell != null) {
                    list.add(changeCellToString(cell));
                    //System.out.printf("%10s", changeCellToString(cell));
                }else{
                    list.add("");
                }
            }
            //System.out.println();
        }
        return list;
    }


    /**
     * use for : 读一个单元格
     *
     * @author zoukh
     * @Created in:  2019/1/5 13:15
     * @Modified By:
     * @version 1.0
     * @used in: ExcelUtils
     */
    private void readSheetRowCol(Sheet sheet, int rowIndex, int colIndex) {
            System.out.println(changeCellToString(getSheetRowCol(sheet,rowIndex,colIndex)));
    }

    public String getSheetRowColString(Sheet sheet, int rowIndex, int colIndex) {
        return changeCellToString(getSheetRowCol(sheet,rowIndex,colIndex));
    }


    public Cell getSheetRowCol(Sheet sheet, int rowIndex, int colIndex) {
        int lastRowNum = sheet.getLastRowNum();
        Row row = null;
        Cell cell = null;
        row = sheet.getRow(rowIndex - 1);
        if (row != null) {
            int lastCellNum = row.getLastCellNum();

            StringBuilder sb = null;

            cell = row.getCell(colIndex - 1);
        }
        return cell;

    }



    /**
     * 读excel
     *
     * @param filePath  excel路径
     * @param sheetName sheet名字
     */
    public void readExcelSheetAll(String filePath, String sheetName) {
        Workbook book = null;
        try {
            book = getExcelWorkbook(filePath);

            Sheet sheet = book.getSheet(sheetName);

           // System.out.println("sheetName：" + sheet.getSheetName());
            readSheetAll(sheet);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读excel
     *
     * @param filePath   excel路径
     * @param sheetIndex sheet名字
     */
    public void readExcelSheetAll(String filePath, int sheetIndex) {
        Workbook book = null;
        try {
            book = getExcelWorkbook(filePath);
            Sheet sheet = getSheetByNum(book, sheetIndex);
            System.out.println("sheetName：" + sheet.getSheetName());
            readSheetAll(sheet);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读excel
     *
     * @param filePath   excel路径
     * @param sheetIndex sheet名字
     */
    public void readExcelSheetRow(String filePath, int sheetIndex, int rowIndex) {
        Workbook book = null;
        try {
            book = getExcelWorkbook(filePath);
            Sheet sheet = getSheetByNum(book, sheetIndex);
            List <String> list= getSheetRow(sheet, rowIndex);
            if (null!=list&&list.size()>0){
                for(int i = 0;i < list.size(); i ++) {
                    System.out.printf("%10s", i+1);
                }
                System.out.println();
                for(String tmp:list) {
                    System.out.printf("%10s", tmp);
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readExcelSheetRow(String filePath, int rowIndex) {
        readExcelSheetRow(filePath, DEFAULT_SHEET_INDEX, rowIndex);
    }

    /**
     * use for : 读一列
     *
     * @author zoukh
     * @Created in:  2019/1/5 13:15
     * @Modified By:
     * @version 1.0
     * @used in: ExcelUtils
     */

    public void readExcelSheetCol(String filePath, int sheetIndex, int colIndex) {
        Workbook book = null;
        try {
            book = getExcelWorkbook(filePath);
            Sheet sheet = getSheetByNum(book, sheetIndex);
            readSheetCol(sheet, colIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readExcelSheetCol(String filePath, int colIndex) {
        readExcelSheetCol(filePath, DEFAULT_SHEET_INDEX, colIndex);
    }


    public void readExcelSheetCol(String filePath, int sheetIndex, String colName) {
        Workbook book = null;
        int colIndex = 0;
        char charCol[] = colName.toCharArray();
        for (int i=charCol.length -1;i>=0;i--) {
            int pow=(int)Math.pow(26,i);
            colIndex =colIndex+ pow*ch2int(charCol[i]);
        }
        try {
            book = getExcelWorkbook(filePath);
            Sheet sheet = getSheetByNum(book, sheetIndex);
            readSheetCol(sheet, colIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readExcelSheetCol(String filePath, String colName) {
        readExcelSheetCol(filePath, DEFAULT_SHEET_INDEX, colName);
    }


    public void readExcelSheetRowCol(String filePath, int sheetIndex, int rowIndex, String colName) {
        Workbook book = null;
        int colIndex = 0;
        char charCol[] = colName.toCharArray();
        for (int i=charCol.length -1;i>=0;i--) {
            int pow=(int)Math.pow(26,i);
            colIndex =colIndex+ pow*ch2int(charCol[i]);
        }
        try {
            book = getExcelWorkbook(filePath);
            Sheet sheet = getSheetByNum(book, sheetIndex);
            //      System.out.println("sheetName：" + sheet.getSheetName());
            readSheetRowCol(sheet, rowIndex, colIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readExcelSheetRowCol(String filePath, int rowIndex, String colName) {
        readExcelSheetRowCol(filePath, DEFAULT_SHEET_INDEX, rowIndex, colName);

    }


    public void readSheetCol(Sheet sheet, int colIndex) {
        List <String> list= getSheetCol(sheet, colIndex);
        if (null!=list&&list.size()>0){
            for(int i = 0;i < list.size(); i ++) {
                System.out.println(i+1+": "+list.get(i));
            }
        }
    }


    public List<String> getSheetCol(Sheet sheet, int colIndex) {
        int totalRows = sheet.getLastRowNum();
        List<String> list = new ArrayList<String>();
        for (int r = 0; r <= totalRows; r++) {
            Row row = sheet.getRow(r);
            if (row == null) {
                list.add("");
                continue;
            }
            Cell cell = row.getCell(colIndex - 1);
            if (null != cell) {
                list.add((changeCellToString(cell)));
            } else {
                list.add("");
            }

        }
        return list;
    }

    /**
     * use for : cell转换成String
     *
     * @author zoukh
     * @Created in:  2019/1/5 14:37
     * @Modified By:
     * @version 1.0
     * @used in: ExcelUtils
     */
    private String changeCellToString(Cell cell) {
        // String resule="";
        String type_cn = null;
        if(null==cell){
            return "";
        }
        String type_style = cell.getCellStyle().getDataFormatString().toUpperCase();
        String type_style_cn = getCellStyleByChinese(type_style);
        int type = cell.getCellType();
        String value = "";
        switch (type) {
            case 0:
                if (DateUtil.isCellDateFormatted(cell)) {
                    type_cn = "NUMBER-DATE";
                    Date date = cell.getDateCellValue();
                    value = sFormat.format(date);
                } else {
                    type_cn = "NUMBER";
                    double tempValue = cell.getNumericCellValue();
                    if (Math.round(tempValue) == tempValue) {
                        value = String.valueOf(Math.round(tempValue));
                    } else {
                        value = String.valueOf(tempValue);
                    }
                }
                break;
            case 1:
                type_cn = "STRING";
                value = cell.getStringCellValue();
                break;
            case 2:
                type_cn = "FORMULA";
                value = cell.getCellFormula();
                break;
            case 3:
                type_cn = "BLANK";
                value = cell.getStringCellValue();
                break;
            case 4:
                type_cn = "BOOLEAN";
                boolean tempValue = cell.getBooleanCellValue();
                value = String.valueOf(tempValue);
                break;
            case 5:
                type_cn = "ERROR";
                byte b = cell.getErrorCellValue();
                value = String.valueOf(b);
            default:
                break;
        }
        // sb.append(value + ",内容类型是：" + type_cn + ",单元格的格式是：" + type_style_cn);
        // System.out.printf("%10s", value);
        // System.out.println(sb.toString());
        return value;
    }


    /**
     * 根据单元格的格式 返回单元格的格式中文
     *
     * @param type_style
     * @return
     */
    private String getCellStyleByChinese(String type_style) {
        String cell_style_cn = "";
        if (type_style.contains("GENERAL")) {
            cell_style_cn = "常规";
        } else if (type_style.equals("_ * #,##0.00_ ;_ * \\-#,##0.00_ ;_ * \"-\"??_ ;_ @_ ")) {
            cell_style_cn = "会计专用";
        } else if (type_style.equals("0")) {
            cell_style_cn = "整数";
        } else if (type_style.contains("YYYY/MM") || type_style.contains("YYYY\\-MM")) {
            cell_style_cn = "日期";
        } else if (type_style.equals("0.00%")) {
            cell_style_cn = "百分比";
        } else {
            cell_style_cn = "不符合规定格式类型:" + type_style;
//			cell_style_cn = type_style;
        }
        return cell_style_cn;
    }


    /**
     * 写内容到excel中
     *
     * @throws IOException
     */
    public void testWrite(String srcFilePath, String tarFilePath) {
        FileOutputStream out = null;
        try {
            Workbook book = getExcelWorkbook(srcFilePath);
            Sheet sheet = getSheetByNum(book, 1);

            Map<String, String> map = new HashMap<String, String>();
            List<Map<String, String>> list = new LinkedList<Map<String, String>>();
            map.put("A", "4,INT");
            map.put("B", "小红,GENERAL");
            map.put("C", "18,INT");
            map.put("D", "1990-03-10,DATE");
            map.put("E", "0.056,PERCENT");
            map.put("F", "4800,DOUBLE");
            //list.add(map);

            int startRow = 6;
            boolean result = writeToExcel(list, sheet, startRow);
            if (result) {
                out = new FileOutputStream(tarFilePath);
                book.write(out);
                System.out.println("文件写入完成！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将传入的内容写入到excel中sheet里
     *
     * @param list
     */
    public boolean writeToExcel(List<Map<String, String>> list, Sheet sheet, int startRow) {
        boolean result = false;
        try {
            Map<String, String> map = null;
            Row row = null;
            for (int i = 0; i < list.size(); i++) {
                map = list.get(i);
                row = sheet.getRow(startRow - 1);
                if (row == null) {
                    row = sheet.createRow(startRow - 1);
                }
                startRow++;
                Cell cell = null;

                BigDecimal db = null;
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    String key = entry.getKey();
                    int colNum = toNum_new(key) - 1;

                    String value_type = entry.getValue();
                    String value = value_type.split(",")[0];
                    String style = value_type.split(",")[1];

                    cell = row.getCell(colNum);
                    if (cell == null) {
                        cell = row.createCell(colNum);
                    }
                    if (style.equals("GENERAL")) {
                        cell.setCellValue(value);
                    } else {
                        if (style.equals("DOUBLE") || style.equals("INT")) {
                            db = new BigDecimal(value, java.math.MathContext.UNLIMITED);
                            cell.setCellValue(db.doubleValue());
                        } else if (style.equals("PERCENT")) {
                            db = new BigDecimal(value, java.math.MathContext.UNLIMITED);
                            cell.setCellValue(db.doubleValue());
                        } else if (style.equals("DATE")) {
                            java.util.Date date = sFormat.parse(value);
                            cell.setCellValue(date);
                        }
                        cell.setCellStyle(styleMap.get(style));
                    }
                }
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    /**
     * 获取excel的Workbook
     *
     * @throws IOException
     */
    public Workbook getExcelWorkbook(String filePath) throws IOException {
        Workbook book = null;
        File file = null;
        FileInputStream fis = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                throw new RuntimeException("文件不存在");
            } else {
          /*      FileInputStream in = new FileInputStream(file);
                Workbook wk = StreamingReader.builder()
                        .rowCacheSize(10)  //缓存到内存中的行数，默认是10
                        .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
                        .open(in);
                initStyleMap(wk);*/

                fis = new FileInputStream(file);
                book = WorkbookFactory.create(fis);
                initStyleMap(book);

            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return book;
    }

    /**
     * 根据索引 返回Sheet
     *
     * @param number
     */
    public Sheet getSheetByNum(Workbook book, int number) {
        Sheet sheet = null;
        try {
            sheet = book.getSheetAt(number - 1);
//			if(sheet == null){
//				sheet = book.createSheet("Sheet"+number);
//			}
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return sheet;
    }

    /**
     * 根据索引 返回Sheet
     *
     * @param number
     */
    public Sheet getSheetByNum(String fileName, int number) throws IOException {
        Sheet sheet = null;
        Workbook book = getExcelWorkbook(fileName);
        try {
            sheet = book.getSheetAt(number - 1);
//			if(sheet == null){
//				sheet = book.createSheet("Sheet"+number);
//			}
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return sheet;
    }

    public int getLastRowNum(Sheet sheet) {
        int lastRowNum = sheet.getLastRowNum();

        return lastRowNum;
    }



    /**
     * 初始化格式Map
     */


    public void initStyleMap(Workbook book) {
        DataFormat hssfDF = book.createDataFormat();

        CellStyle doubleStyle = book.createCellStyle(); //会计专用
        doubleStyle.setDataFormat(hssfDF.getFormat("_ * #,##0.00_ ;_ * \\-#,##0.00_ ;_ * \"-\"??_ ;_ @_ ")); //poi写入后为会计专用
        styleMap.put("DOUBLE", doubleStyle);

        CellStyle intStyle = book.createCellStyle(); //会计专用
        intStyle.setDataFormat(hssfDF.getFormat("0")); //poi写入后为会计专用
        styleMap.put("INT", intStyle);

        CellStyle yyyyMMddStyle = book.createCellStyle();//日期yyyyMMdd
        yyyyMMddStyle.setDataFormat(hssfDF.getFormat("yyyy-MM-dd"));
        styleMap.put("DATE", yyyyMMddStyle);

        CellStyle percentStyle = book.createCellStyle();//百分比
        percentStyle.setDataFormat(hssfDF.getFormat("0.00%"));
        styleMap.put("PERCENT", percentStyle);
    }
}





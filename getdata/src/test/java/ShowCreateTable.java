package com.office;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * use for :
 *
 * @author zoukh
 * Created in:  2019/2/26 15:32
 * @version 1.0
 * @Modified By:
 * @used in: WorkTest
 */
public class ShowCreateTable {
    static ExcelUtils excelUtils=new ExcelUtils();

    public static void main(String[] args) throws Exception {
        String filePath= "F:\\git\\WhyiseeBench\\springStart\\doc\\模型与接口.xlsx";
        //第几个sheet页
        Sheet sheet = excelUtils.getSheetByNum(filePath,5);
        genCreateTable(sheet,"mysql");



    }

    public static void genCreateTable(Sheet sheet,String dbtype){
        int rowNums = excelUtils.getLastRowNum(sheet);
        StringBuffer sb = new StringBuffer();
        //生成建表语句
        sb.append("\n"+"CREATE TABLE "+excelUtils.getSheetRowColString(sheet,3,1)+"\n");
        sb.append(" ( "+"\n");

        if (dbtype.equals("oracle")) {
            sb.append(" "+excelUtils.getSheetRowColString(sheet,5,2)+" "+excelUtils.getSheetRowColString(sheet,5,3)+"\n");
            for (int r = 6; r <= rowNums + 1; r++) {
                sb.append("," + excelUtils.getSheetRowColString(sheet, r, 2) + " " + excelUtils.getSheetRowColString(sheet, r, 3) + "\n");

            }
            sb.append(" ); " + "\n");

            //生成注释
            sb.append("comment on table  " + excelUtils.getSheetRowColString(sheet, 3, 1)
                    + " is '" + excelUtils.getSheetRowColString(sheet, 1, 2) + "'; " + "\n");
            for (int r = 5; r <= rowNums + 1; r++) {
                // excelUtils.readExcelSheetRow(filePath,r);
                sb.append("comment on column " + excelUtils.getSheetRowColString(sheet, 3, 1)
                        + "." + excelUtils.getSheetRowColString(sheet, r, 2)
                        + " is '" + excelUtils.getSheetRowColString(sheet, r, 1)
                        + excelUtils.getSheetRowColString(sheet, r, 4) + "'; " + "\n");
            }
        }else if (dbtype.equals("mysql")){
            sb.append(" "+excelUtils.getSheetRowColString(sheet,5,2)+" "
                    +excelUtils.getSheetRowColString(sheet,5,3)
                    +" comment '"+excelUtils.getSheetRowColString(sheet, 5, 1)
                    + excelUtils.getSheetRowColString(sheet, 5, 4) + "'\n");
            for (int r = 6; r <= rowNums + 1; r++) {
                sb.append("," + excelUtils.getSheetRowColString(sheet, r, 2) + " " + excelUtils.getSheetRowColString(sheet, r, 3) +" comment '"+
                        excelUtils.getSheetRowColString(sheet, r, 1)
                        + excelUtils.getSheetRowColString(sheet, r, 4) + "'\n");

            }
            sb.append(" ) DEFAULT CHARSET=utf8, comment=' "+ excelUtils.getSheetRowColString(sheet, 1, 2)+ "';\n");
        }

        System.out.println(sb);

    }
}

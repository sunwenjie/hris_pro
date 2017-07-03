package com.kan.base.util.poi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadAndWriteExcel
{

   public static void main( String[] args ) throws InvalidFormatException, IOException
   {

      //目标 文件初始化  
      XSSFWorkbook wb2 = new XSSFWorkbook();
      XSSFSheet  sheet2 = wb2.createSheet( "考勤加班" );

      //源文件
      InputStream ins = new FileInputStream( "D:\\考勤报表导出.xlsx" );
      Workbook wb1 = WorkbookFactory.create( ins );
      Sheet sheet1 = wb1.getSheetAt( 0 );
      for ( int rowNum = 0; rowNum <= sheet1.getLastRowNum(); rowNum++ )
      {
         Row row = sheet1.getRow( rowNum );
         if ( row == null )
         {
            continue;
         }
         createRow( wb2, sheet2, row, rowNum );

      }
      try
      {
         OutputStream os = new FileOutputStream( "D:/workbook.xlsx" );
         wb2.write( os );
         os.close();
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }

   }

   public static void createRow( XSSFWorkbook wb, XSSFSheet sheet, Row source, int rowNum )
   {

      // 在第一行第一个单元格，插入下拉框  
      XSSFRow  row = sheet.createRow( rowNum );
      XSSFCell cell0 = row.createCell( 0 );
      XSSFCell cell1 = row.createCell( 1 );
      XSSFCell cell2 = row.createCell( 2 );
      XSSFCell cell3 = row.createCell( 3 );
      XSSFCell cell4 = row.createCell( 4 );
      XSSFCell cell5 = row.createCell( 5 );

      //TODO cell赋值

      cell0.setCellValue( "1" );
      cell1.setCellValue( "1" );
      cell2.setCellValue( "1" );
      cell3.setCellValue( "1" );
      cell4.setCellValue( "1" );
      cell5.setCellValue( "1" );
      
      
      String[] textList = { "OT1.5", "OT2", "OT3" };
      sheet.addValidationData( setDataValidation( sheet, textList, 1, 5, 3, 3 ) );

   }

   public static DataValidation setDataValidation( Sheet sheet, String[] textList, int firstRow, int endRow, int firstCol, int endCol )
   {

      DataValidationHelper helper = sheet.getDataValidationHelper();
      // 加载下拉列表内容    
      DataValidationConstraint constraint = helper.createExplicitListConstraint( textList );
      // DVConstraint constraint = new DVConstraint();    
      constraint.setExplicitListValues( textList );

      // 设置数据有效性加载在哪个单元格上。    
      // 四个参数分别是：起始行、终止行、起始列、终止列    
      CellRangeAddressList regions = new CellRangeAddressList( ( short ) firstRow, ( short ) endRow, ( short ) firstCol, ( short ) endCol );

      // 数据有效性对象    
      DataValidation data_validation = helper.createValidation( constraint, regions );
      //DataValidation data_validation = new DataValidation(regions, constraint);    

      return data_validation;
   }

   public class Model
   {
      private String name;
      private String startTime;
      private String endTime;

      public String getName()
      {
         return name;
      }

      public void setName( String name )
      {
         this.name = name;
      }

      public String getStartTime()
      {
         return startTime;
      }

      public void setStartTime( String startTime )
      {
         this.startTime = startTime;
      }

      public String getEndTime()
      {
         return endTime;
      }

      public void setEndTime( String endTime )
      {
         this.endTime = endTime;
      }

   }

}

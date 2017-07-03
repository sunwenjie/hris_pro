package com.kan.base.util.poi;

import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kan.base.util.KANUtil;

public class POIUtil
{
   public static String REG_EX_DATE = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

   public static int EXPORT_BATCH_SIZE = 100;

   /** 
    * 方法名称：SetDataValidation 
    * 内容摘要：设置数据有效性 
    * @param firstRow 
    * @param firstCol 
    * @param endRow 
    * @param endCol 
    */
   public static HSSFDataValidation SetDataValidation( final String[] textList, int firstRow, int firstCol, int endRow, int endCol )
   {

      //加载下拉列表内容 
      DVConstraint constraint = DVConstraint.createExplicitListConstraint( textList );

      //设置数据有效性加载在哪个单元格上。 

      //四个参数分别是：起始行、终止行、起始列、终止列 

      CellRangeAddressList regions = new CellRangeAddressList( firstRow, endRow, firstCol, endCol );

      //数据有效性对象 
      HSSFDataValidation data_validation = new HSSFDataValidation( regions, constraint );

      // 添加到Sheet
      // aSheet.addValidationData(data_validation); 
      return data_validation;

   }

   /** 
    * 方法名称：SetDataValidation 
    * 内容摘要：设置数据有效性 
    * @param firstRow 
    * @param firstCol 
    * @param endRow 
    * @param endCol 
    */
   public static void SetDataValidation( final XSSFWorkbook workbook, final String[] textList, int firstRow, int firstCol, int endRow, int endCol, final String sourceListTitle )
   {
      if ( textList != null && textList.length > 0 )
      {
         final XSSFSheet mainSheet = workbook.getSheetAt( 0 );
         // 初始化DataValidationHelper
         final DataValidationHelper helper = mainSheet.getDataValidationHelper();
         // 加载下拉列表内容    
         final DataValidationConstraint constraint = helper.createExplicitListConstraint( textList );
         // 如果数据有效性长度大于excel数据有效性来源的最大限制
         if ( ArrayUtils.toString( textList ).length() >= 255 )
         {
            // 资源表的个数
            final int currSubSheetIndex = workbook.getNumberOfSheets() + 1;
            // 获取XSSFWorkbook子表
            final XSSFSheet subSheet = workbook.createSheet( "Sheet" + currSubSheetIndex );
            // 给第一行、第一列添加title
            subSheet.createRow( 0 ).createCell( 0 ).setCellValue( sourceListTitle );
            // 给row行、第tempColumnIndex添加数据
            for ( int row = 0; row < textList.length; row++ )
            {
               subSheet.createRow( row + 1 ).createCell( 0 ).setCellValue( textList[ row ] );
            }
            // 数据来源引用Sheet1
            constraint.setFormula1( "Sheet" + currSubSheetIndex + "!$A$2:$A$" + ( textList.length + 1 ) );
         }
         else
         {
            // 数据来源引用字符数据
            constraint.setExplicitListValues( textList );
         }

         // 设置数据有效性加载在哪个单元格上， 四个参数分别是：起始行、终止行、起始列、终止列    
         final CellRangeAddressList regions = new CellRangeAddressList( ( short ) firstRow, ( short ) endRow, ( short ) firstCol, ( short ) endCol );
         // 创建数据有效性对象    
         final DataValidation data_validation = helper.createValidation( constraint, regions );
         mainSheet.addValidationData( data_validation );
      }
   }

   public static void addValidationData( final XSSFWorkbook workbook, final String[] textList, int firstRow, int firstCol, int endRow, int endCol, final String formula )
   {
      // 初始化DataValidationHelper
      final DataValidationHelper helper = workbook.getSheetAt( 0 ).getDataValidationHelper();
      // 加载下拉列表内容    
      final DataValidationConstraint constraint = helper.createExplicitListConstraint( textList );
      if ( KANUtil.filterEmpty( formula ) != null )
      {
         constraint.setFormula1( formula );
      }
      else
      {
         // 数据来源引用字符数据
         constraint.setExplicitListValues( textList );
      }
      // 设置数据有效性加载在哪个单元格上， 四个参数分别是：起始行、终止行、起始列、终止列    
      final CellRangeAddressList regions = new CellRangeAddressList( ( short ) firstRow, ( short ) endRow, ( short ) firstCol, ( short ) endCol );
      // 创建数据有效性对象    
      final DataValidation data_validation = helper.createValidation( constraint, regions );
      workbook.getSheetAt( 0 ).addValidationData( data_validation );
   }

   public static String getCellValue( Cell cell )
   {
      String ret;
      switch ( cell.getCellType() )
      {
         case Cell.CELL_TYPE_BLANK:
            ret = "";
            break;
         case Cell.CELL_TYPE_BOOLEAN:
            ret = String.valueOf( cell.getBooleanCellValue() );
            break;
         case Cell.CELL_TYPE_ERROR:
            ret = null;
            break;
         case Cell.CELL_TYPE_FORMULA:
            Workbook wb = cell.getSheet().getWorkbook();
            CreationHelper crateHelper = wb.getCreationHelper();
            FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();
            ret = getCellValue( evaluator.evaluateInCell( cell ) );
            break;
         case Cell.CELL_TYPE_NUMERIC:
            if ( DateUtil.isCellDateFormatted( cell ) )
            {
               Date theDate = cell.getDateCellValue();
               ret = KANUtil.formatDate( theDate, "yyyy-MM-dd", true );
            }
            else
            {
               ret = NumberToTextConverter.toText( cell.getNumericCellValue() );
            }
            break;
         case Cell.CELL_TYPE_STRING:
            ret = cell.getRichStringCellValue().getString();
            break;
         default:
            ret = null;
      }

      return ret; //有必要自行trim  
   }

   /** 
    * 复制一个单元格样式到目的单元格样式 
    * @param fromCell 
    * @param toCell 
    */
   public static void copyCellStyle( Cell fromCell, Cell toCell, XSSFWorkbook workbook )
   {
      if ( fromCell == null || toCell == null )
         return;

      final XSSFCellStyle cellStyle = workbook.createCellStyle();

      cellStyle.setAlignment( fromCell.getCellStyle().getAlignment() );
      //边框和边框颜色  
      cellStyle.setBorderBottom( fromCell.getCellStyle().getBorderBottom() );
      cellStyle.setBorderLeft( fromCell.getCellStyle().getBorderLeft() );
      cellStyle.setBorderRight( fromCell.getCellStyle().getBorderRight() );
      cellStyle.setBorderTop( fromCell.getCellStyle().getBorderTop() );
      cellStyle.setTopBorderColor( fromCell.getCellStyle().getTopBorderColor() );
      cellStyle.setBottomBorderColor( fromCell.getCellStyle().getBottomBorderColor() );
      cellStyle.setRightBorderColor( fromCell.getCellStyle().getRightBorderColor() );
      cellStyle.setLeftBorderColor( fromCell.getCellStyle().getLeftBorderColor() );

      //背景和前景  
      cellStyle.setFillBackgroundColor( fromCell.getCellStyle().getFillBackgroundColor() );
      cellStyle.setFillForegroundColor( fromCell.getCellStyle().getFillForegroundColor() );

      System.out.println( "背景" + fromCell.getCellStyle().getFillBackgroundColor() );
      System.out.println( "前景" + fromCell.getCellStyle().getFillForegroundColor() );

      cellStyle.setDataFormat( fromCell.getCellStyle().getDataFormat() );
      cellStyle.setFillPattern( fromCell.getCellStyle().getFillPattern() );
      //     toCell.setFont(fromCell.getFont(null));  

      cellStyle.setHidden( fromCell.getCellStyle().getHidden() );
      cellStyle.setIndention( fromCell.getCellStyle().getIndention() );//首行缩进  
      cellStyle.setLocked( fromCell.getCellStyle().getLocked() );
      cellStyle.setRotation( fromCell.getCellStyle().getRotation() );//旋转  
      cellStyle.setVerticalAlignment( fromCell.getCellStyle().getVerticalAlignment() );
      cellStyle.setWrapText( fromCell.getCellStyle().getWrapText() );

      toCell.setCellStyle( cellStyle );
   }

}

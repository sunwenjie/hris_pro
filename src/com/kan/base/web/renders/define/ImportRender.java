package com.kan.base.web.renders.define;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ColumnVO;
import com.kan.base.domain.define.ImportDTO;
import com.kan.base.domain.define.ImportDetailVO;
import com.kan.base.domain.define.ImportHeaderVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.security.PositionGradeVO;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.poi.POIUtil;
import com.kan.base.web.action.BaseAction;

public class ImportRender
{

   public static String getImportDetailManageHtml( final HttpServletRequest request, final ColumnVO columnVO ) throws KANException
   {
      // 初始化StringBuffer对象
      final StringBuffer rs = new StringBuffer();
      // 初始化ImportDetailVO对象
      final ImportDetailVO importDetailVO = ( ImportDetailVO ) request.getAttribute( "importDetailForm" );

      if ( importDetailVO != null )
      {
         // 如果ImportHeaderId不为空，添加隐藏字段
         if ( importDetailVO.getImportHeaderId() != null && !"".equals( importDetailVO.getImportHeaderId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"importHeaderId\" name=\"importHeaderId\" value=\"" + importDetailVO.getImportHeaderId() + "\"/>" );
         }
         // 如果ListDetailId不为空，添加隐藏字段
         if ( importDetailVO.getEncodedId() != null && !"".equals( importDetailVO.getEncodedId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"importDetailId\" name=\"importDetailId\" value=\"" + importDetailVO.getEncodedId() + "\"/>" );
         }
         // 如果ColumnId不为空，添加隐藏字段
         if ( importDetailVO.getColumnId() != null && !"".equals( importDetailVO.getColumnId() ) )
         {
            rs.append( "<input type=\"hidden\" id=\"columnId\" name=\"columnId\" value=\"" + importDetailVO.getColumnId() + "\"/>" );
         }

         rs.append( "<ol class=\"auto\">" );

         // 如果当前字段对象不为空
         if ( columnVO != null && columnVO.getValueType() != null && !columnVO.getValueType().equals( "" ) )
         {
            // 如果选择字段是数值类型
            if ( columnVO.getValueType().equals( "1" ) )
            {
               rs.append( "<ol class=\"auto\">" );
               // 精度
               rs.append( "<li>" );
               rs.append( "<label>精度 <img src=\"images/tips.png\" title=\"保留小数位数\" /></label>" );
               rs.append( KANUtil.getSelectHTML( importDetailVO.getAccuracys(), "accuracy", "manageImportDetail_accuracy", importDetailVO.getAccuracy(), null, null ) );
               rs.append( "</li>" );

               // 取舍
               rs.append( "<li>" );
               rs.append( "<label>取舍 <img src=\"images/tips.png\" title=\"小数位数保留方式，截取 - 直接抹去待截取小数位，向上进位 - 正数进位、负数截取\" /></label>" );
               rs.append( KANUtil.getSelectHTML( importDetailVO.getRounds(), "round", "manageImportDetail_round", importDetailVO.getRound(), null, null ) );
               rs.append( "</li>" );
               rs.append( "</ol>" );
            }
            // 如果选择字段是日期类型
            if ( columnVO.getValueType().equals( "3" ) )
            {
               rs.append( "<ol class=\"auto\">" );
               rs.append( "<li>" );
               rs.append( "<label>日期格式</label>" );
               rs.append( KANUtil.getSelectHTML( importDetailVO.getDatetimeFormats(), "datetimeFormat", "manageImportDetail_datetimeFormat", importDetailVO.getDatetimeFormat(), null, null ) );
               rs.append( "</li>" );
               rs.append( "</ol>" );
            }
            rs.append( "<input type=\"hidden\" id=\"valueType\" id=\"valueType\" value=\"" + columnVO.getValueType() + "\"/>" );
         }

         // 字段名 - 中文
         rs.append( "<li>" );
         rs.append( "<label>字段名称（中文）<em> *</em></label>" );
         rs.append( "<input type=\"text\" id=\"nameZH\" name=\"nameZH\" maxlength=\"100\" class=\"manageImportDetail_nameZH\" value=\""
               + ( importDetailVO.getNameEN() == null ? "" : importDetailVO.getNameZH() ) + "\"/>" );
         rs.append( "</li>" );

         // 字段名 - 英文
         rs.append( "<li>" );
         rs.append( "<label>字段名称（英文） <em> *</em></label>" );
         rs.append( "<input type=\"text\" id=\"nameEN\" name=\"nameEN\" maxlength=\"100\" class=\"manageImportDetail_nameEN\" value=\""
               + ( importDetailVO.getNameEN() == null ? "" : importDetailVO.getNameEN() ) + "\"/>" );
         rs.append( "</li>" );

         // 字段宽度
         rs.append( "<li>" );
         rs.append( "<label>字段宽度 <img src=\"images/tips.png\" title=\"导出模板字段的字段显示的宽度，可设置百分比或固定象素\" /></label> " );
         rs.append( "<input type=\"text\" id=\"columnWidth\" name=\"columnWidth\" maxlength=\"3\" class=\"manageImportDetail_columnWidth \" value=\""
               + ( importDetailVO.getColumnWidth() == null ? "" : importDetailVO.getColumnWidth() ) + "\"/>" );
         rs.append( "</li>" );

         // 排列顺序
         rs.append( "<li>" );
         rs.append( "<label>字段顺序 <img src=\"images/tips.png\" title=\"导出模板字段的字段排列的顺序，一般为数值，例如：1、2、3、4、5...\" /><em> *</em></label> " );
         rs.append( "<input type=\"text\" id=\"columnIndex\" name=\"columnIndex\" maxlength=\"2\" class=\"manageImportDetail_columnIndex\" value=\""
               + ( importDetailVO.getColumnIndex() == null ? "0" : importDetailVO.getColumnIndex() ) + "\"/>" );
         rs.append( "</li>" );

         // 字体大小
         rs.append( "<li>" );
         rs.append( "<label>字体大小（Size） <img src=\"images/tips.png\" title=\"导出模板字段的字体的大小\" /></label> " );
         rs.append( KANUtil.getSelectHTML( importDetailVO.getFontSizes(), "fontSize", "manageImportDetail_fontSize", importDetailVO.getFontSize(), null, null ) );
         rs.append( "</li>" );

         // 示例值
         rs.append( "<li>" );
         rs.append( "<label>示例值<img src=\"images/tips.png\" title=\"导出模板时该字段的默认值\" /></label> " );
         rs.append( "<input type=\"text\" id=\"tempValue\" name=\"tempValue\" maxlength=\"500\" class=\"manageImportDetail_tempValue\" value=\""
               + ( importDetailVO.getTempValue() == null ? "" : importDetailVO.getTempValue() ) + "\"/>" );
         rs.append( "</li>" );

         // 转译
         rs.append( "<li>" );
         rs.append( "<label>转译</label>" );
         rs.append( KANUtil.getSelectHTML( importDetailVO.getFlags(), "isDecoded", "manageImportDetail_isDecoded", importDetailVO.getIsDecoded(), null, null ) );
         rs.append( "</li>" );
         rs.append( "</ol>" );

         // 对齐
         rs.append( "<ol class=\"auto\">" );
         rs.append( "<li>" );
         rs.append( "<label>对齐</label>" );
         rs.append( KANUtil.getSelectHTML( importDetailVO.getAligns(), "align", "manageImportDetail_align", importDetailVO.getAlign(), null, null ) );
         rs.append( "</li>" );

         // 状态
         rs.append( "<li>" );
         rs.append( "<label>状态  <em>*</em></label> " );
         rs.append( KANUtil.getSelectHTML( importDetailVO.getStatuses(), "status", "manageImportDetail_status", importDetailVO.getStatus(), null, null ) );
         rs.append( "</li>" );

         // 描述
         rs.append( "<li>" );
         rs.append( "<label>描述</label> " );
         rs.append( "<textarea name=\"description\" class=\"manageImportDetail_description\">" + ( importDetailVO.getDescription() == null ? "" : importDetailVO.getDescription() )
               + "</textarea>" );
         rs.append( "</li>" );
         rs.append( "</ol>" );
      }

      return rs.toString();
   }

   public static String getPositionGradeMultipleChoice( final HttpServletRequest request, final ImportHeaderVO importHeaderVO ) throws KANException
   {
      final List< PositionGradeVO > positionGraderVOs = KANConstants.getKANAccountConstants( importHeaderVO.getAccountId() ).POSITION_GRADE_VO;
      final StringBuffer rs = new StringBuffer();

      if ( positionGraderVOs != null && positionGraderVOs.size() > 0 )
      {
         for ( PositionGradeVO positionGradeVO : positionGraderVOs )
         {
            String gradeName = "";

            // 按照语言设置取Title
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               gradeName = positionGradeVO.getGradeNameZH();
            }
            else
            {
               gradeName = positionGradeVO.getGradeNameEN();
            }

            rs.append( "<li>" );
            rs.append( "<input type=\"checkbox\" id=\"positionGradeIdArray\" name=\"positionGradeIdArray\" value=\"" + positionGradeVO.getPositionGradeId() + "\" "
                  + ( checked( positionGradeVO.getPositionGradeId(), importHeaderVO.getPositionGradeIds() ) ? "checked" : "" ) + "/>" + gradeName );
            rs.append( "</li>" );
         }
      }

      return rs.toString();
   }

   // 判断当前节点是否需要选中
   private static boolean checked( final String positionGradeId, final String positionGradeIds )
   {
      if ( KANUtil.filterEmpty( positionGradeIds ) != null )
      {
         final String[] positionGradeIdArray = KANUtil.jasonArrayToStringArray( positionGradeIds );
         for ( String temp : positionGradeIdArray )
         {
            if ( temp != null && temp.trim().equals( positionGradeId ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   public static XSSFWorkbook generateImportTemplateExcel( final HttpServletRequest request, final String importHeadId ) throws KANException
   {

      final String accountId = BaseAction.getAccountId( request, null );
      final String corpId = KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) );
      final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( accountId );
      final ImportDTO importDTO = accountConstants.getImportDTOByImportHeadId( importHeadId, corpId );

      final boolean isZHLanguage = request.getLocale().getLanguage().equalsIgnoreCase( "ZH" );

      if ( importDTO != null )
      {
         final ImportHeaderVO importHeaderVO = importDTO.getImportHeaderVO();
         if ( importHeaderVO != null && importHeaderVO.getTableId() != null )
         {
            final TableDTO tableDTO = accountConstants.getTableDTOByTableId( importHeaderVO.getTableId() );

            final List< ImportDetailVO > actualImportDetailVOs = new ArrayList< ImportDetailVO >();
            actualImportDetailVOs.addAll( importDTO.getImportDetailVOs() );

            //拼接从表字段
            if ( importDTO.getSubImportDTOs() != null && importDTO.getSubImportDTOs().size() > 0 )
            {
               for ( ImportDTO subImportDTO : importDTO.getSubImportDTOs() )
               {
                  if ( subImportDTO.getImportDetailVOs() != null )
                  {
                     actualImportDetailVOs.addAll( subImportDTO.getImportDetailVOs() );
                  }
               }
            }

            // 初始化工作薄
            final XSSFWorkbook workbook = new XSSFWorkbook();

            String xlsxSheetName = isZHLanguage ? importHeaderVO.getNameZH() : importHeaderVO.getNameEN();
            if ( KANUtil.filterEmpty( xlsxSheetName ) == null )
            {
               xlsxSheetName = isZHLanguage ? "新建 Excel 工作表" : "Sheet1";
            }
            // 创建表格，这个Sheet放置模板主要内容
            final XSSFSheet sheet = workbook.createSheet( xlsxSheetName );

            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth( 15 );

            // 生成Excel Header Row
            final XSSFRow rowHeader = sheet.createRow( 0 );
            final XSSFRow rowFirst = sheet.createRow( 1 );
            final XSSFRow rowLast = sheet.createRow( 4 );

            final XSSFCellStyle firstCellStyle = workbook.createCellStyle();
            XSSFFont fontTemp = workbook.createFont();
            fontTemp.setFontName( "Calibri" );
            short size = 16;
            XSSFFont fontFroRemark = workbook.createFont();
            fontTemp.setFontName( "Calibri" );
            fontFroRemark.setFontHeightInPoints( ( short ) 10 );
            String lastheadId = "0";
            int fillForegroundColor = 0;
            String[] colorString = { "#FFFFFF", "#B8CCE4", "#D7E4BC", "#CCC0DA", "#B2A1C7", "#66CCCC", };

            for ( int i = 0; i < actualImportDetailVOs.size(); i++ )
            {
               final XSSFCellStyle headerCellStyle = workbook.createCellStyle();
               final XSSFCellStyle lastCellStyle = workbook.createCellStyle();
               final ImportDetailVO importDetailVO = actualImportDetailVOs.get( i );
               if ( !"0".equals( lastheadId ) && !importDetailVO.getImportHeaderId().equals( lastheadId ) )
               {
                  fillForegroundColor = fillForegroundColor + 1 > colorString.length ? 1 : fillForegroundColor + 1;
               }
               headerCellStyle.setFillForegroundColor( new XSSFColor( Color.decode( colorString[ fillForegroundColor ] ) ) );
               headerCellStyle.setFillPattern( XSSFCellStyle.SOLID_FOREGROUND );
               lastCellStyle.setFont( fontTemp );
               lastCellStyle.setFillForegroundColor( new XSSFColor( Color.decode( fillForegroundColor == 0 ? "#C2D69A" : colorString[ fillForegroundColor ] ) ) );
               lastCellStyle.setFillPattern( XSSFCellStyle.SOLID_FOREGROUND );
               lastheadId = importDetailVO.getImportHeaderId();
               final String colunmName = isZHLanguage ? importDetailVO.getNameZH() : importDetailVO.getNameEN();

               final XSSFCell cellHeader = rowHeader.createCell( i );
               final XSSFRichTextString text = new XSSFRichTextString( colunmName );
               //文本类型
               XSSFDataFormat textFormat = workbook.createDataFormat();

               cellHeader.setCellValue( text );

               //在第五行增加描述
               final XSSFCell cellLast = rowLast.createCell( i );

               if ( KANUtil.filterEmpty( importDetailVO.getDescription() ) != null )
               {
                  cellLast.setCellValue( new XSSFRichTextString( "*" + importDetailVO.getDescription() ) );
               }

               lastCellStyle.setFont( fontFroRemark );
               cellLast.setCellStyle( lastCellStyle );
               final XSSFCell cellFirst = rowFirst.createCell( i );

               ColumnVO columnVO = tableDTO.getColumnVOByColumnId( importDetailVO.getColumnId() );

               // 如果是从表字段
               if ( columnVO == null && importDTO.getSubImportDTOs() != null && importDTO.getSubImportDTOs().size() > 0 )
               {
                  for ( ImportDTO subImportDTO : importDTO.getSubImportDTOs() )
                  {
                     final TableDTO subTableDTO = accountConstants.getTableDTOByTableId( subImportDTO.getImportHeaderVO().getTableId() );
                     columnVO = subTableDTO.getColumnVOByColumnId( importDetailVO.getColumnId() );

                     if ( columnVO != null )
                     {
                        break;
                     }
                  }
               }

               // 字段为下拉框
               if ( columnVO != null && columnVO.getInputType() != null
                     && ( columnVO.getInputType().trim().equals( "2" ) || columnVO.getInputType().trim().equals( "3" ) || columnVO.getInputType().trim().equals( "4" ) ) )
               {
                  // 如果是部门的话就不要 数据有效性验证   ---mark临时的解决方案
                  if ( !"branch".equals( columnVO.getNameDB() ) )
                  {
                     // 获取下拉框对应的值
                     final List< MappingVO > mappingVOs = KANUtil.getColumnOptionValues( request.getLocale(), columnVO, accountId, corpId );
                     if ( mappingVOs != null && mappingVOs.size() > 0 )
                     {
                        final String tempValue = importDetailVO.getTempValue();
                        List< String > matchTextList = new ArrayList< String >();
                        String defoutValue = null;
                        // 数据有效性设置
                        for ( int j = 0; j < mappingVOs.size(); j++ )
                        {
                           MappingVO mappingVO = mappingVOs.get( j );
                           if ( !"0".equals( mappingVO.getMappingId() ) )
                           {
                              matchTextList.add( mappingVO.getMappingValue() );
                              if ( tempValue != null && tempValue.equals( mappingVO.getMappingValue() ) )
                              {
                                 defoutValue = tempValue;
                              }
                           }
                        }
                        if ( defoutValue == null && matchTextList.size() > 0 )
                        {
                           defoutValue = matchTextList.get( 0 );
                        }
                        // 设置默认值
                        final XSSFRichTextString text2 = new XSSFRichTextString( defoutValue );
                        cellFirst.setCellValue( text2 );
                        // to array
                        String[] matchTextArray = new String[ matchTextList.size() ];
                        for ( int j = 0; j < matchTextArray.length; j++ )
                        {
                           matchTextArray[ j ] = matchTextList.get( j );
                        }

                        // 设置该sheet的数据有效性
                        POIUtil.SetDataValidation( workbook, matchTextArray, 1, i, 1000, i, ( isZHLanguage ? columnVO.getNameZH() : columnVO.getNameEN() ) );
                     }
                  }
               }
               else
               {
                  final XSSFRichTextString text2 = new XSSFRichTextString( importDetailVO.getTempValue() );
                  cellFirst.setCellValue( text2 );
               }

               // 设置字体
               int columnWidth = 3500;
               try
               {
                  columnWidth = new BigDecimal( Integer.valueOf( importDetailVO.getColumnWidth() ) * 256 ).setScale( 0, BigDecimal.ROUND_HALF_UP ).intValue();
                  size = Short.parseShort( importDetailVO.getFontSize() );
               }
               catch ( NumberFormatException e )
               {
               }
               // 设置列宽
               sheet.setColumnWidth( i, columnWidth );
               // 设置字体大小
               fontTemp.setFontHeightInPoints( size );

               firstCellStyle.setFont( fontTemp );
               // 设置对其方式
               if ( "1".equals( importDetailVO.getAlign() ) )
               {
                  firstCellStyle.setAlignment( XSSFCellStyle.ALIGN_LEFT );
               }
               else if ( "2".equals( importDetailVO.getAlign() ) )
               {
                  firstCellStyle.setAlignment( XSSFCellStyle.ALIGN_CENTER );
               }
               else if ( "3".equals( importDetailVO.getAlign() ) )
               {
                  firstCellStyle.setAlignment( XSSFCellStyle.ALIGN_RIGHT );
               }
               else
               {
                  firstCellStyle.setAlignment( XSSFCellStyle.ALIGN_LEFT );
               }

               //文本
               firstCellStyle.setDataFormat( textFormat.getFormat( "@" ) );
               cellFirst.setCellStyle( firstCellStyle );

               //判断是否是必填项 更改背景色
               headerCellStyle.setFont( fontTemp );
               headerCellStyle.setAlignment( firstCellStyle.getAlignment() );

               if ( columnVO != null && "1".equals( importDetailVO.getIsIgnoreDefaultValidate() ) )
               {
                  if ( KANUtil.filterEmpty( importDTO.getImportHeaderVO().getTableId() ) != null && !importDTO.getImportHeaderVO().getTableId().equals( columnVO.getTableId() ) )
                  {
                     headerCellStyle.setFillForegroundColor( new HSSFColor.BLUE().getIndex() );
                     headerCellStyle.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );
                  }
                  else
                  {
                     headerCellStyle.setFillForegroundColor( new HSSFColor.RED().getIndex() );
                     headerCellStyle.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );
                  }
               }

               if ( columnVO != null && "1".equals( columnVO.getIsRequired() ) )
               {
                  if ( KANUtil.filterEmpty( importDTO.getImportHeaderVO().getTableId() ) != null && !importDTO.getImportHeaderVO().getTableId().equals( columnVO.getTableId() ) )
                  {
                     headerCellStyle.setFillForegroundColor( new HSSFColor.BLUE().getIndex() );
                     headerCellStyle.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );
                  }
                  else
                  {
                     headerCellStyle.setFillForegroundColor( new HSSFColor.RED().getIndex() );
                     headerCellStyle.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );
                  }
               }

               //文本
               headerCellStyle.setDataFormat( textFormat.getFormat( "@" ) );
               cellHeader.setCellStyle( headerCellStyle );
            }

            return workbook;
         }
      }

      return null;
   }
}

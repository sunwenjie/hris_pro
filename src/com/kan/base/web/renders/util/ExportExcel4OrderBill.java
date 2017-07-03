package com.kan.base.web.renders.util;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kan.base.core.ServiceLocator;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.SocialBenefitSolutionDetailService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.domain.biz.sb.SpecialDTO;
import com.kan.hro.domain.biz.settlement.OrderBillHeaderView;
import com.kan.hro.domain.biz.settlement.OrderDetailVO;
import com.kan.hro.domain.biz.settlement.SettlementDTO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;
import com.kan.hro.service.inf.biz.sb.SBHeaderService;

public class ExportExcel4OrderBill
{

   public static XSSFWorkbook generateListExcelForOrderBillHeaderView( final HttpServletRequest request, final String javaObjectName ) throws KANException
   {
      try
      {
         // 初始化corpId
         final String corpId = KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) );
         final String accountId = BaseAction.getAccountId( request, null );

         ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, corpId );

         // 初始化PagedListHolder
         final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );
         final ClientVO clientVO = ( ClientVO ) request.getAttribute( "clientVO" );
         final OrderBillHeaderView orderBillHeaderView = ( OrderBillHeaderView ) pagedListHolder.getObject();
         final String[] monthly = orderBillHeaderView.getMonthly().split( "/" );
         final String header = clientVO.getNameZH() + monthly[ 0 ] + "年" + monthly[ 1 ] + "月结算清单";
         String sub_header = "制表时间：" + KANUtil.formatDate( new Date(), "yyyy.MM.dd" ) + "          ";
         sub_header += "制表人：" + BaseAction.getUsername( request, null ) + "            ";
         sub_header += "审核人：";

         // 初始化工作薄
         final XSSFWorkbook workbook = new XSSFWorkbook();
         final List< XSSFCellStyle > cellStyles = new ArrayList< XSSFCellStyle >();
         if ( listDTO != null && pagedListHolder != null )
         {

            // 创建字体
            final XSSFFont font = workbook.createFont();
            font.setFontName( "新宋体" );
            font.setFontHeightInPoints( ( short ) 10 );

            // 创建字体(一级标题字体)
            final XSSFFont font_t1 = workbook.createFont();
            font_t1.setFontName( "新宋体" );
            font_t1.setFontHeightInPoints( ( short ) 14 );
            font_t1.setBold( true );

            // 创建字体(二级标题字体)
            final XSSFFont font_t2 = workbook.createFont();
            font_t2.setFontName( "新宋体" );
            font_t2.setFontHeightInPoints( ( short ) 11 );
            font_t2.setBold( true );

            // 创建字体(三级标题字体)
            final XSSFFont font_t3 = workbook.createFont();
            font_t3.setFontName( "新宋体" );
            font_t3.setFontHeightInPoints( ( short ) 9 );
            font_t3.setBold( true );

            // 创建字体 bule
            final XSSFFont font_blue = workbook.createFont();
            font_blue.setFontName( "新宋体" );
            font_blue.setFontHeightInPoints( ( short ) 9 );
            font_blue.setBold( true );
            font_blue.setItalic( true );

            // 创建单元格样式
            final XSSFCellStyle cellStyleLeft = workbook.createCellStyle();
            cellStyleLeft.setFont( font );
            cellStyleLeft.setAlignment( XSSFCellStyle.ALIGN_LEFT );
            cellStyleLeft.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyles.add( cellStyleLeft );

            // 创建单元格样式
            final XSSFCellStyle cellStyleCenter = workbook.createCellStyle();
            cellStyleCenter.setFont( font );
            cellStyleCenter.setAlignment( XSSFCellStyle.ALIGN_CENTER );
            cellStyleCenter.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyles.add( cellStyleCenter );

            // 创建单元格样式
            final XSSFCellStyle cellStyleRight = workbook.createCellStyle();
            cellStyleRight.setFont( font );
            cellStyleRight.setAlignment( XSSFCellStyle.ALIGN_RIGHT );
            cellStyleRight.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyles.add( cellStyleRight );

            final XSSFCellStyle cellStyleRight_dataFormat = workbook.createCellStyle();
            cellStyleRight_dataFormat.setFont( font );
            cellStyleRight_dataFormat.setAlignment( XSSFCellStyle.ALIGN_RIGHT );
            cellStyleRight_dataFormat.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyleRight_dataFormat.setDataFormat( workbook.createDataFormat().getFormat( "##0.00" ) );
            cellStyles.add( cellStyleRight_dataFormat );

            // 创建单元格样式(一级标题样式)
            final XSSFCellStyle cellStyle_t1 = workbook.createCellStyle();
            cellStyle_t1.setFont( font_t1 );
            cellStyle_t1.setAlignment( XSSFCellStyle.ALIGN_CENTER );
            cellStyle_t1.setVerticalAlignment( XSSFCellStyle.VERTICAL_CENTER );
            cellStyle_t1.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyles.add( cellStyle_t1 );

            // 创建单元格样式(二级标题样式)
            final XSSFCellStyle cellStyle_t2 = workbook.createCellStyle();
            cellStyle_t2.setFont( font_t2 );
            cellStyle_t2.setAlignment( XSSFCellStyle.ALIGN_CENTER );
            cellStyle_t2.setVerticalAlignment( XSSFCellStyle.VERTICAL_CENTER );
            cellStyle_t2.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyles.add( cellStyle_t2 );

            // 创建单元格样式(三级标题样式)
            final XSSFCellStyle cellStyle_t3 = workbook.createCellStyle();
            cellStyle_t3.setFont( font_t3 );
            cellStyle_t3.setAlignment( XSSFCellStyle.ALIGN_CENTER );
            cellStyle_t3.setDataFormat( workbook.createDataFormat().getFormat( "##0.00" ) );
            cellStyle_t3.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyles.add( cellStyle_t3 );

            // 创建单元格样式  蓝色
            final XSSFCellStyle cellStyle_blue = workbook.createCellStyle();
            cellStyle_blue.setFont( font_blue );
            cellStyle_blue.setAlignment( XSSFCellStyle.ALIGN_CENTER );
            cellStyle_blue.setFillForegroundColor( new XSSFColor( new Color( 204, 255, 255 ) ) );
            cellStyle_blue.setFillPattern( XSSFCellStyle.SOLID_FOREGROUND );
            cellStyle_blue.setDataFormat( workbook.createDataFormat().getFormat( "##0.00" ) );
            cellStyle_blue.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyles.add( cellStyle_blue );

            // 灰色
            final XSSFCellStyle cellStyle_gray = workbook.createCellStyle();
            cellStyle_gray.setFont( font_t3 );
            cellStyle_gray.setAlignment( XSSFCellStyle.ALIGN_CENTER );
            cellStyle_gray.setFillForegroundColor( new XSSFColor( new Color( 192, 192, 192 ) ) );
            cellStyle_gray.setFillPattern( XSSFCellStyle.SOLID_FOREGROUND );
            cellStyle_gray.setDataFormat( workbook.createDataFormat().getFormat( "##0.00" ) );
            cellStyle_gray.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyles.add( cellStyle_gray );

            // 添加边框
            /*for ( int i = 0; i < cellStyles.size(); i++ )
            {
               cellStyles.get( i ).setBorderLeft( XSSFCellStyle.BORDER_THIN );
               cellStyles.get( i ).setBorderBottom( XSSFCellStyle.BORDER_THIN );
               cellStyles.get( i ).setBorderRight( XSSFCellStyle.BORDER_THIN );
               cellStyles.get( i ).setBorderTop( XSSFCellStyle.BORDER_THIN );
            }*/

            // 创建表格
            final XSSFSheet sheet = workbook.createSheet( listDTO.getListName( request ) );
            // 设置表格默认列宽度为10个字节
            sheet.setDefaultColumnWidth( 10 );

            // 初始化（合并主从ListDetailVO）ListDetailVO列表
            final List< ListDetailVO > mergeListDetailVOs = new ArrayList< ListDetailVO >();

            // 存在列表字段
            if ( listDTO.getListDetailVOs() != null && listDTO.getListDetailVOs().size() > 0 )
            {

               // 如果存在从ListDetailVO
               if ( listDTO.getSubListDTOs() != null && listDTO.getSubListDTOs().size() > 0 )
               {
                  // 初始化Sub ListDetailVO
                  final List< ListDetailVO > subListDetailVOs = mergeListDetailVO( request, listDTO.getSubListDTOs() );

                  if ( subListDetailVOs != null && subListDetailVOs.size() > 0 )
                  {
                     mergeListDetailVOs.addAll( subListDetailVOs );
                  }
               }

               // 排序
               if ( mergeListDetailVOs.size() > 0 )
               {
                  Collections.sort( mergeListDetailVOs, new ComparatorListDetail() );
               }
               // 调整列,并返回社保科目的集合
               final List< ListDetailVO > sbItemDetailVOList = fixListDetailVOs( mergeListDetailVOs );
               // 是否有住房公积金 67
               final boolean isItem_housingExist = isItemExist( sbItemDetailVOList, "67" );
               // 总科目数量、
               final int TOTAL_ITEM_LENGTH = mergeListDetailVOs.size();
               // 社保科目数量
               final int TOTAL_SB_ITEM_LENGTH = sbItemDetailVOList.size();
               // 普通科目数量
               final int TOTAL_COMMON_ITEM_LENGTH = TOTAL_ITEM_LENGTH - TOTAL_SB_ITEM_LENGTH;
               // 排除公积金社保科目数量
               final int TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH = isItem_housingExist ? TOTAL_SB_ITEM_LENGTH - 1 : TOTAL_SB_ITEM_LENGTH;
               // 住房公积金数量
               final int TOTAL_ITEM_67_LENGTH = isItem_housingExist ? 3 : 0;
               // 是否只交社保
               final boolean isSBOnly = isSBOnly( mergeListDetailVOs );
               // 创建头
               final XSSFRow rowHeader = sheet.createRow( 0 );
               rowHeader.setHeightInPoints( 50 );
               // 头合并
               final int rowHeaderLength = 3 + TOTAL_COMMON_ITEM_LENGTH + 1 + ( TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH + 1 ) * 2 + TOTAL_ITEM_67_LENGTH;
               sheet.addMergedRegion( new CellRangeAddress( 0, 0, 0, rowHeaderLength - 1 ) );
               final XSSFCell cellHeader = rowHeader.createCell( 0 );
               final XSSFRichTextString text = new XSSFRichTextString( header );
               cellHeader.setCellValue( text );
               cellHeader.setCellStyle( cellStyle_t1 );
               // 第二行，合并2行单元格
               final XSSFRow rowTitle = sheet.createRow( 1 );
               rowTitle.setHeightInPoints( 20 );
               sheet.addMergedRegion( new CellRangeAddress( 1, 2, 0, rowHeaderLength - 1 ) );
               final XSSFCell cellTitle = rowTitle.createCell( 0 );
               cellTitle.setCellValue( new XSSFRichTextString( sub_header ) );
               cellTitle.setCellStyle( cellStyle_t2 );
               // 表头
               final XSSFRow rowTableHeader3 = sheet.createRow( 3 );
               final XSSFRow rowTableHeader4 = sheet.createRow( 4 );
               final XSSFRow rowTableHeader5 = sheet.createRow( 5 );
               // rowNumber 序号
               sheet.addMergedRegion( new CellRangeAddress( 3, 5, 0, 0 ) );
               final XSSFCell cellNumber = rowTableHeader3.createCell( 0 );
               cellNumber.setCellValue( new XSSFRichTextString( "序号" ) );
               cellNumber.setCellStyle( cellStyle_t3 );
               // rowEmployeeName 姓名
               sheet.addMergedRegion( new CellRangeAddress( 3, 5, 1, 1 ) );
               final XSSFCell cellEmployeeName = rowTableHeader3.createCell( 1 );
               cellEmployeeName.setCellValue( new XSSFRichTextString( "姓名" ) );
               cellEmployeeName.setCellStyle( cellStyle_t3 );
               // 社保月份
               sheet.addMergedRegion( new CellRangeAddress( 3, 5, 2, 2 ) );
               final XSSFCell cellSBMonthly = rowTableHeader3.createCell( 2 );
               cellSBMonthly.setCellValue( new XSSFRichTextString( "社保月份" ) );
               cellSBMonthly.setCellStyle( cellStyle_t3 );
               // 处理科目
               final List< String > sbItemIds = getSBItemIdsBySBItemDetailVOList( sbItemDetailVOList );
               int itemColumnIndex = 3;
               for ( ListDetailVO itemDetailVO : mergeListDetailVOs )
               {
                  // 社保科目特殊处理，
                  if ( sbItemIds.contains( itemDetailVO.getItemId() ) )
                  {
                     // 如果是 社保科目，跳过
                     continue;
                  }
                  sheet.addMergedRegion( new CellRangeAddress( 3, 5, itemColumnIndex, itemColumnIndex ) );
                  final XSSFCell cellItemName = rowTableHeader3.createCell( itemColumnIndex );
                  cellItemName.setCellValue( new XSSFRichTextString( getColumnName( request, itemDetailVO ) ) );
                  cellItemName.setCellStyle( cellStyle_t3 );

                  // 初始化Excel列宽，针对用户定义固定列宽的情况
                  if ( itemDetailVO.getColumnWidthType() != null && itemDetailVO.getColumnWidthType().trim().equals( "2" ) && itemDetailVO.getColumnWidth() != null
                        && itemDetailVO.getColumnWidth().trim().matches( "[0-9]*" ) )
                  {
                     // 换算Excel列宽并取整
                     final BigDecimal columnWidth = new BigDecimal( Integer.valueOf( itemDetailVO.getColumnWidth() ) * 30 ).setScale( 0, BigDecimal.ROUND_HALF_UP );
                     // 设置Excel列宽
                     sheet.setColumnWidth( itemColumnIndex, columnWidth.intValue() );
                  }

                  itemColumnIndex++;
               }
               // 处理基数,(空白一格，基数两格)
               final XSSFCell cellBaseTopBlank = rowTableHeader3.createCell( itemColumnIndex );
               cellBaseTopBlank.setCellValue( new XSSFRichTextString( "" ) );
               cellBaseTopBlank.setCellStyle( cellStyleCenter );
               // 基数
               sheet.addMergedRegion( new CellRangeAddress( 4, 5, itemColumnIndex, itemColumnIndex ) );
               final XSSFCell cellBase = rowTableHeader4.createCell( itemColumnIndex );
               cellBase.setCellValue( new XSSFRichTextString( "基数" ) );
               cellBase.setCellStyle( cellStyle_t3 );
               itemColumnIndex++;
               // 社会保险
               sheet.addMergedRegion( new CellRangeAddress( 3, 3, itemColumnIndex, itemColumnIndex + ( TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH + 1 ) * 2 - 1 ) );
               final XSSFCell cellSocialBenefit = rowTableHeader3.createCell( itemColumnIndex );
               final String[] capFloorArr = getSBRange( pagedListHolder, false );
               String capFloorArrStr = "";
               if ( !"0".equals( capFloorArr[ 0 ] ) && !"0".equals( capFloorArr[ 1 ] ) )
               {
                  capFloorArrStr = "(" + capFloorArr[ 0 ] + " - " + capFloorArr[ 1 ] + ")";
               }
               cellSocialBenefit.setCellValue( new XSSFRichTextString( "社会保险" + capFloorArrStr ) );
               cellSocialBenefit.setCellStyle( cellStyle_t3 );
               // 单位
               sheet.addMergedRegion( new CellRangeAddress( 4, 4, itemColumnIndex, itemColumnIndex + TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH ) );
               final XSSFCell cellSBCompany = rowTableHeader4.createCell( itemColumnIndex );
               Double ratesCompany = 0.00d;
               for ( String itemId : sbItemIds )
               {
                  if ( "67".equals( itemId ) )
                  {
                     continue;
                  }
                  String tempRate = KANUtil.filterEmpty( getRate( pagedListHolder, itemId, true ) );
                  ratesCompany += tempRate == null ? 0 : Double.parseDouble( tempRate );
               }
               cellSBCompany.setCellValue( new XSSFRichTextString( "单位(" + ratesCompany + "%)" ) );
               cellSBCompany.setCellStyle( cellStyle_t3 );
               // 个人
               sheet.addMergedRegion( new CellRangeAddress( 4, 4, itemColumnIndex + TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH + 1, itemColumnIndex
                     + ( TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH + 1 ) * 2 - 1 ) );
               final XSSFCell cellSBPersonal = rowTableHeader4.createCell( itemColumnIndex + TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH + 1 );

               Double ratesPersonal = 0.00d;
               for ( String itemId : sbItemIds )
               {
                  if ( "67".equals( itemId ) )
                  {
                     continue;
                  }
                  String tempRate = KANUtil.filterEmpty( getRate( pagedListHolder, itemId, false ) );
                  ratesPersonal += tempRate == null ? 0 : Double.parseDouble( tempRate );
               }

               cellSBPersonal.setCellValue( new XSSFRichTextString( "个人（" + ratesPersonal + "%）" ) );
               cellSBPersonal.setCellStyle( cellStyle_t3 );
               // 单位科目（社保科目名称）
               for ( ListDetailVO listDetailVO : sbItemDetailVOList )
               {
                  // 排除住房公积金
                  if ( !"67".equals( listDetailVO.getItemId() ) )
                  {
                     final XSSFCell cellCompanyItemTemp = rowTableHeader5.createCell( itemColumnIndex );
                     String rate = KANUtil.filterEmpty( getRate( pagedListHolder, listDetailVO.getItemId(), true ) );
                     rate = ( rate == null ? "" : ( rate + "%" ) );
                     cellCompanyItemTemp.setCellValue( new XSSFRichTextString( listDetailVO.getNameZH() + rate ) );
                     cellCompanyItemTemp.setCellStyle( cellStyleCenter );
                     itemColumnIndex++;
                  }
               }
               // 添加小计1
               final XSSFCell cellCompanyItemTempXJ1 = rowTableHeader5.createCell( itemColumnIndex );
               cellCompanyItemTempXJ1.setCellValue( "小计1" );
               cellCompanyItemTempXJ1.setCellStyle( cellStyleCenter );
               itemColumnIndex++;

               // 个人科目
               for ( ListDetailVO listDetailVO : sbItemDetailVOList )
               {
                  // 排除住房公积金
                  if ( !"67".equals( listDetailVO.getItemId() ) )
                  {
                     final XSSFCell cellCompanyItemTemp = rowTableHeader5.createCell( itemColumnIndex );
                     String rate = KANUtil.filterEmpty( getRate( pagedListHolder, listDetailVO.getItemId(), false ) );
                     rate = ( rate == null ? "" : ( rate + "%" ) );
                     cellCompanyItemTemp.setCellValue( new XSSFRichTextString( listDetailVO.getNameZH() + rate ) );
                     cellCompanyItemTemp.setCellStyle( cellStyleCenter );
                     itemColumnIndex++;
                  }
               }

               // 添加小计2
               final XSSFCell cellCompanyItemTempXJ2 = rowTableHeader5.createCell( itemColumnIndex );
               cellCompanyItemTempXJ2.setCellValue( "小计2" );
               cellCompanyItemTempXJ2.setCellStyle( cellStyleCenter );
               itemColumnIndex++;

               // 住房公积金,如果存在
               if ( isItem_housingExist )
               {
                  sheet.addMergedRegion( new CellRangeAddress( 3, 3, itemColumnIndex, itemColumnIndex + 2 ) );
                  final XSSFCell cellZFGJJ = rowTableHeader3.createCell( itemColumnIndex );
                  cellZFGJJ.setCellValue( new XSSFRichTextString( "住房公积金" ) );
                  cellZFGJJ.setCellStyle( cellStyle_t3 );
                  // 住房公积金第二层
                  sheet.addMergedRegion( new CellRangeAddress( 4, 4, itemColumnIndex, itemColumnIndex + 2 ) );
                  final XSSFCell cellZFGJJ2 = rowTableHeader4.createCell( itemColumnIndex );
                  final String[] capFloorArrHousing = getSBRange( pagedListHolder, true );
                  capFloorArrStr = "";
                  if ( !"0".equals( capFloorArrHousing[ 0 ] ) && !"0".equals( capFloorArrHousing[ 1 ] ) )
                  {
                     capFloorArrStr = "(" + capFloorArrHousing[ 0 ] + " - " + capFloorArrHousing[ 1 ] + ")";
                  }
                  cellZFGJJ2.setCellValue( new XSSFRichTextString( capFloorArrStr ) );
                  cellZFGJJ2.setCellStyle( cellStyle_t3 );
                  //住房公积金 - 基数
                  sheet.addMergedRegion( new CellRangeAddress( 5, 5, itemColumnIndex, itemColumnIndex ) );
                  final XSSFCell cellBaseZFGJJ = rowTableHeader5.createCell( itemColumnIndex );
                  cellBaseZFGJJ.setCellValue( new XSSFRichTextString( "基数" ) );
                  cellBaseZFGJJ.setCellStyle( cellStyle_t3 );
                  itemColumnIndex++;
                  // 住房公积金 - 10% 2列
                  for ( int i = 0; i < 2; i++ )
                  {
                     String rate = "";
                     final XSSFCell tempCell = rowTableHeader5.createCell( itemColumnIndex );
                     if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
                     {
                        SettlementDTO tempVO = ( SettlementDTO ) pagedListHolder.getSource().get( 0 );
                        if ( tempVO.getDetailVOs() != null && tempVO.getDetailVOs().size() > 0 )
                        {
                           rate = ( i == 0 ? tempVO.getOrderDetailVOs().get( 0 ).getBillRateCompany() : tempVO.getOrderDetailVOs().get( 0 ).getBillRatePersonal() );
                           if ( i == 0 )
                           {
                              rate = getRate( pagedListHolder, "67", true );
                           }
                           else
                           {
                              // 个人
                              rate = getRate( pagedListHolder, "67", false );
                           }
                        }
                     }

                     tempCell.setCellValue( new XSSFRichTextString( KANUtil.filterEmpty( rate ) == null ? "" : rate + "%" ) );
                     tempCell.setCellStyle( cellStyle_t3 );
                     itemColumnIndex++;
                  }
               }

               //--------------------------------------------------------------
               // 用以标识Body行序号
               int bodyRowIndex = 6;
               int pageListHolderSize = 0;
               if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
               {
                  // 遍历行
                  for ( Object object : pagedListHolder.getSource() )
                  {
                     final SettlementDTO settlementDTO = ( SettlementDTO ) object;
                     // 生成Excel Body Row
                     final XSSFRow rowBody = sheet.createRow( bodyRowIndex );

                     // 用以标识Body列序号
                     int bodyColumnIndex = 0;

                     // 序号值
                     final XSSFCell cellBodyIndex = rowBody.createCell( bodyColumnIndex );
                     cellBodyIndex.setCellValue( bodyRowIndex - 5 );
                     cellBodyIndex.setCellStyle( cellStyleCenter );
                     bodyColumnIndex++;
                     //姓名
                     final XSSFCell cellBodyEmployeeName = rowBody.createCell( bodyColumnIndex );
                     cellBodyEmployeeName.setCellValue( getEmployeeName( request, settlementDTO ) );
                     cellBodyEmployeeName.setCellStyle( cellStyleCenter );
                     bodyColumnIndex++;
                     //社保月份
                     final XSSFCell cellBodySBMonthly = rowBody.createCell( bodyColumnIndex );
                     cellBodySBMonthly.setCellValue( "1".equals( settlementDTO.getIsAdjustment() ) ? settlementDTO.getServiceContractVO().getMonthly()
                           : settlementDTO.getServiceContractVO().getSbMonthly() );
                     cellBodySBMonthly.setCellStyle( cellStyleCenter );
                     bodyColumnIndex++;
                     // 科目
                     for ( ListDetailVO itemDetailVO : mergeListDetailVOs )
                     {
                        // 社保特殊处理，
                        if ( sbItemIds.contains( itemDetailVO.getItemId() ) )
                        {
                           // 如果是 社保，跳过
                           continue;
                        }
                        final XSSFCell cellBodyItem = rowBody.createCell( bodyColumnIndex );
                        cellBodyItem.setCellValue( getSettlementValueByItemId( settlementDTO, itemDetailVO ) );
                        cellBodyItem.setCellStyle( cellStyleRight_dataFormat );
                        bodyColumnIndex++;
                     }
                     // 基数、
                     final XSSFCell cellBodyBase = rowBody.createCell( bodyColumnIndex );
                     Double sbBaseCompany = 0.00d;
                     if ( settlementDTO.getOrderDetailVOs() != null && settlementDTO.getOrderDetailVOs().size() > 0 )
                     {
                        if ( KANUtil.filterEmpty( settlementDTO.getOrderDetailVOs().get( 0 ).getSbBaseCompany() ) != null )
                        {
                           for ( OrderDetailVO tempVO : settlementDTO.getOrderDetailVOs() )
                           {
                              if ( "7".equals( getItemTypeByItemId( tempVO.getItemId(), accountId ) ) )
                              {
                                 sbBaseCompany = Double.parseDouble( tempVO.getSbBaseCompany() );
                                 break;
                              }
                           }
                        }

                     }
                     cellBodyBase.setCellValue( sbBaseCompany );
                     cellBodyBase.setCellStyle( cellStyleRight_dataFormat );
                     bodyColumnIndex++;
                     // 社保科目,公司。eg.养老20%，失业1.5%，生育0.8%，工伤0.5%，医疗9%，小计1
                     List< Double > companySB = getCompanySB( settlementDTO, sbItemIds );
                     for ( Double cSB : companySB )
                     {
                        final XSSFCell cellBodyCSB = rowBody.createCell( bodyColumnIndex );
                        cellBodyCSB.setCellValue( cSB );
                        cellBodyCSB.setCellStyle( cellStyleRight_dataFormat );
                        bodyColumnIndex++;
                     }
                     // 社保科目,个人，eg. 养老8%，失业0.5%，医疗2%，大病，小计2
                     List< Double > personalSB = getPersonalSB( settlementDTO, sbItemIds );
                     for ( Double pSB : personalSB )
                     {
                        final XSSFCell cellBodyCSB = rowBody.createCell( bodyColumnIndex );
                        cellBodyCSB.setCellValue( pSB );
                        cellBodyCSB.setCellStyle( cellStyleRight_dataFormat );
                        bodyColumnIndex++;
                     }

                     // 住房公积金，基数，公司，个人
                     if ( isItem_housingExist )
                     {
                        List< Double > itemHousing = getItemHousing( settlementDTO );
                        for ( Double ih : itemHousing )
                        {
                           final XSSFCell cellBodyCSB = rowBody.createCell( bodyColumnIndex );
                           cellBodyCSB.setCellValue( ih );
                           cellBodyCSB.setCellStyle( cellStyleRight_dataFormat );
                           bodyColumnIndex++;
                        }
                     }
                     // 换行，统计数据长度
                     bodyRowIndex++;
                     pageListHolderSize++;
                  }

               }
               // --------------------------------------------------------------------
               // bodytotalcolumn
               int bodyMinTotalColumnIndex = 0;
               final XSSFRow rowBodyTotalMin = sheet.createRow( bodyRowIndex );
               // 小计
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, bodyMinTotalColumnIndex, bodyMinTotalColumnIndex + 2 ) );
               final XSSFCell cellBodyTotalMin_minSum = rowBodyTotalMin.createCell( bodyMinTotalColumnIndex );
               cellBodyTotalMin_minSum.setCellValue( "小计" );
               cellBodyTotalMin_minSum.setCellStyle( cellStyle_t3 );
               bodyMinTotalColumnIndex = bodyMinTotalColumnIndex + 3;

               // 小计值
               for ( int i = bodyMinTotalColumnIndex; i < rowHeaderLength; i++ )
               {
                  final XSSFCell cellBodyTotalItem = rowBodyTotalMin.createCell( bodyMinTotalColumnIndex );
                  final int rowStart = 6;
                  final int rowEnd = pageListHolderSize + 6;
                  final int columnStart = bodyMinTotalColumnIndex;
                  cellBodyTotalItem.setCellValue( getSumMulitCellValues( sheet, rowStart, rowEnd, columnStart ) );
                  cellBodyTotalItem.setCellStyle( cellStyleRight_dataFormat );
                  bodyMinTotalColumnIndex++;
               }
               //----------------------------------------------------------------------
               //  切换到合计行
               bodyRowIndex++;
               final XSSFRow rowBodyTotalMax = sheet.createRow( bodyRowIndex );
               int bodyMaxTotalColumnIndex = 0;
               // 合计
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, bodyMaxTotalColumnIndex, bodyMaxTotalColumnIndex + 1 ) );
               final XSSFCell rowBodyTotalMax_companySum = rowBodyTotalMax.createCell( bodyMaxTotalColumnIndex );
               rowBodyTotalMax_companySum.setCellValue( "合计" );
               rowBodyTotalMax_companySum.setCellStyle( cellStyle_t3 );
               bodyMaxTotalColumnIndex = bodyMaxTotalColumnIndex + 2;
               // 单位应缴
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, bodyMaxTotalColumnIndex, bodyMaxTotalColumnIndex + 1 ) );
               final XSSFCell rowBodyTotalMax_companyBillSum = rowBodyTotalMax.createCell( bodyMaxTotalColumnIndex );
               rowBodyTotalMax_companyBillSum.setCellValue( "单位应缴" );
               rowBodyTotalMax_companyBillSum.setCellStyle( cellStyle_t3 );
               bodyMaxTotalColumnIndex = bodyMaxTotalColumnIndex + 2;
               //  用于显示位置
               bodyMaxTotalColumnIndex += TOTAL_COMMON_ITEM_LENGTH;
               // 单位应缴值
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, bodyMaxTotalColumnIndex, bodyMaxTotalColumnIndex + 1 ) );
               final XSSFCell cellBodyTotalMax_companyBillSumValue = rowBodyTotalMax.createCell( bodyMaxTotalColumnIndex );
               // 所有科目合计+小计1+公积金公司部分
               int rowStart = bodyRowIndex - 1;
               int rowEnd = rowStart;
               int columnStart = 3;
               int columnEnd = 3 + TOTAL_COMMON_ITEM_LENGTH;
               Double allItemsMinSum = 0d;
               // 没有普通科目
               if ( TOTAL_COMMON_ITEM_LENGTH == 0 )
               {
                  allItemsMinSum = 0d;
               }
               else
               {
                  allItemsMinSum = getSumMulitCellValues( sheet, rowStart, rowEnd, columnStart, columnEnd - 1 );
               }
               // 小计1
               final Double totalMinCount1 = getCellValue( sheet, pageListHolderSize + 6, 3 + TOTAL_COMMON_ITEM_LENGTH + 1 + TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH );
               // 公司住房公积金
               Double item_housing_company = 0.00d;
               if ( isItem_housingExist )
               {
                  item_housing_company = getCellValue( sheet, pageListHolderSize + 6, 3 + TOTAL_COMMON_ITEM_LENGTH + 1 + ( TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH + 1 ) * 2
                        + TOTAL_ITEM_67_LENGTH - 2 );
               }
               cellBodyTotalMax_companyBillSumValue.setCellValue( allItemsMinSum + totalMinCount1 + item_housing_company );
               cellBodyTotalMax_companyBillSumValue.setCellStyle( cellStyle_gray );
               bodyMaxTotalColumnIndex = bodyMaxTotalColumnIndex + 2;
               // 元/月
               final XSSFCell rowBodyTotalMax_yuan_month = rowBodyTotalMax.createCell( bodyMaxTotalColumnIndex );
               rowBodyTotalMax_yuan_month.setCellValue( "元/月" );
               rowBodyTotalMax_yuan_month.setCellStyle( cellStyleCenter );
               bodyMaxTotalColumnIndex = bodyMaxTotalColumnIndex + 2;
               // 个人应缴
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, bodyMaxTotalColumnIndex, bodyMaxTotalColumnIndex + 1 ) );
               final XSSFCell rowBodyTotalMax_personalBillSum = rowBodyTotalMax.createCell( bodyMaxTotalColumnIndex );
               rowBodyTotalMax_personalBillSum.setCellValue( "个人应缴" );
               rowBodyTotalMax_personalBillSum.setCellStyle( cellStyle_t3 );
               bodyMaxTotalColumnIndex = bodyMaxTotalColumnIndex + 2;
               // 个人应缴值(小计2+住房公积金个人)
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, bodyMaxTotalColumnIndex, bodyMaxTotalColumnIndex + 1 ) );
               final XSSFCell cellBodyTotalMax_personalBillSumValue = rowBodyTotalMax.createCell( bodyMaxTotalColumnIndex );
               // 小计2
               final Double totalMinCount2 = getCellValue( sheet, pageListHolderSize + 6, 3 + TOTAL_COMMON_ITEM_LENGTH + 1 + ( TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH + 1 ) * 2 - 1 );
               // 住房公积金 个人
               Double item_housing_personal = 0.00d;
               if ( isItem_housingExist )
               {
                  item_housing_personal = getCellValue( sheet, pageListHolderSize + 6, 3 + TOTAL_COMMON_ITEM_LENGTH + 1 + ( TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH + 1 ) * 2
                        + TOTAL_ITEM_67_LENGTH - 1 );
               }
               cellBodyTotalMax_personalBillSumValue.setCellValue( totalMinCount2 + item_housing_personal );
               cellBodyTotalMax_personalBillSumValue.setCellStyle( cellStyle_blue );
               bodyMaxTotalColumnIndex = bodyMaxTotalColumnIndex + 2;
               // 元/月
               final XSSFCell rowBodyTotalMax_yuan_month2 = rowBodyTotalMax.createCell( bodyMaxTotalColumnIndex );
               rowBodyTotalMax_yuan_month2.setCellValue( "元/月" );
               rowBodyTotalMax_yuan_month2.setCellStyle( cellStyleCenter );
               bodyMaxTotalColumnIndex = bodyMaxTotalColumnIndex + 2;
               // 换行
               bodyRowIndex++;
               //--------------------------------------------------------------------
               // 结尾统计
               final XSSFRow row_description = sheet.createRow( bodyRowIndex );
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 0, 1 ) );
               final XSSFCell cell_description = row_description.createCell( 0 );
               cell_description.setCellValue( "备注：" );
               cell_description.setCellStyle( cellStyle_t3 );
               bodyRowIndex++;
               // 管理费
               final XSSFRow row_mgtFee = sheet.createRow( bodyRowIndex );
               final XSSFCell cell_mgtFee = row_mgtFee.createCell( 1 );
               cell_mgtFee.setCellValue( "服务费：" );
               cell_mgtFee.setCellStyle( cellStyle_t3 );
               // 管理费值    
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 2, 3 ) );
               final XSSFCell cell_mgtFee_value = row_mgtFee.createCell( 2 );
               final Double sumMgtFee = sumItemValue( pagedListHolder.getSource(), new String[] { "121", "122" } );
               cell_mgtFee_value.setCellValue( sumMgtFee );
               cell_mgtFee_value.setCellStyle( cellStyle_t3 );
               final XSSFCell cell_mgtFee_yuan = row_mgtFee.createCell( 4 );
               cell_mgtFee_yuan.setCellValue( "元" );
               cell_mgtFee_yuan.setCellStyle( cellStyle_t3 );
               bodyRowIndex++;
               // 代发工资
               final XSSFRow row_agentSalary = sheet.createRow( bodyRowIndex );
               final XSSFCell cell_agentSalary = row_agentSalary.createCell( 1 );
               cell_agentSalary.setCellValue( "代发工资：" );
               cell_agentSalary.setCellStyle( cellStyle_t3 );
               // 代发工资值
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 2, 3 ) );
               final XSSFCell cell_agentSalary_value = row_agentSalary.createCell( 2 );
               final List< String > exceptItemTypes = new ArrayList< String >();
               // 7社保 8商保 9管理 
               exceptItemTypes.add( "7" );
               exceptItemTypes.add( "8" );
               exceptItemTypes.add( "9" );
               final Double sumAgentSalary = sumExceptItemType( pagedListHolder.getSource(), exceptItemTypes, accountId );
               cell_agentSalary_value.setCellValue( sumAgentSalary );
               cell_agentSalary_value.setCellStyle( cellStyle_t3 );
               final XSSFCell cell_agentSalary_yuan = row_agentSalary.createCell( 4 );
               cell_agentSalary_yuan.setCellValue( "元" );
               cell_agentSalary_yuan.setCellStyle( cellStyle_t3 );
               bodyRowIndex++;
               // 市民卡
               final XSSFRow row_personalCard = sheet.createRow( bodyRowIndex );
               final XSSFCell cell_personalCard = row_personalCard.createCell( 1 );
               cell_personalCard.setCellValue( "市民卡：" );
               cell_personalCard.setCellStyle( cellStyle_t3 );
               // 
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 2, 3 ) );
               final XSSFCell cell_cell_personalCard_value = row_personalCard.createCell( 2 );
               cell_cell_personalCard_value.setCellValue( "" );
               cell_cell_personalCard_value.setCellStyle( cellStyle_t3 );
               bodyRowIndex++;
               // 社保
               final XSSFRow row_sb = sheet.createRow( bodyRowIndex );
               final XSSFCell cell_sb = row_sb.createCell( 1 );
               cell_sb.setCellValue( "社保：" );
               cell_sb.setCellStyle( cellStyle_t3 );
               // 社保值
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 2, 3 ) );
               final XSSFCell cell_sb_value = row_sb.createCell( 2 );
               //final Double sumSB = getCellValue( sheet, bodyRowIndex - 6, 2 + TOTAL_COMMON_ITEM_LENGTH + 1 + TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH );
               final Double sumSBCommon = getSumSBAllValue( pagedListHolder, accountId, true, isSBOnly );
               cell_sb_value.setCellValue( sumSBCommon );
               cell_sb_value.setCellStyle( cellStyle_t3 );
               final XSSFCell cell_sb_yuan = row_sb.createCell( 4 );
               cell_sb_yuan.setCellValue( "元" );
               cell_sb_yuan.setCellStyle( cellStyle_t3 );
               bodyRowIndex++;
               // 商保
               final XSSFRow row_cb = sheet.createRow( bodyRowIndex );
               final XSSFCell cell_cb = row_cb.createCell( 1 );
               cell_cb.setCellValue( "商保：" );
               cell_cb.setCellStyle( cellStyle_t3 );
               // 社保值
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 2, 3 ) );
               final XSSFCell cell_cb_value = row_cb.createCell( 2 );
               //final Double sumSB = getCellValue( sheet, bodyRowIndex - 6, 2 + TOTAL_COMMON_ITEM_LENGTH + 1 + TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH );
               final Double sumCBCommon = getSumCBAllValue( pagedListHolder, accountId );
               cell_cb_value.setCellValue( sumCBCommon );
               cell_cb_value.setCellStyle( cellStyle_t3 );
               final XSSFCell cell_cb_yuan = row_sb.createCell( 4 );
               cell_cb_yuan.setCellValue( "元" );
               cell_cb_yuan.setCellStyle( cellStyle_t3 );
               bodyRowIndex++;
               // 公积金
               final XSSFRow row_housing = sheet.createRow( bodyRowIndex );
               final XSSFCell cell_housing = row_housing.createCell( 1 );
               cell_housing.setCellValue( "公积金：" );
               cell_housing.setCellStyle( cellStyle_t3 );
               // 公积金值
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 2, 3 ) );
               final XSSFCell cell_housing_value = row_housing.createCell( 2 );
               /*final Double sumHousing = !isItem_housingExist ? 0.00d : getCellValue( sheet, bodyRowIndex - 7, 2 + TOTAL_COMMON_ITEM_LENGTH + 1
                     + ( TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH + 1 ) * 2 + 1 );*/
               final Double sumSBHousing = getSumSBAllValue( pagedListHolder, accountId, false, isSBOnly );
               cell_housing_value.setCellValue( sumSBHousing );
               cell_housing_value.setCellStyle( cellStyle_t3 );
               final XSSFCell cell_housing_yuan = row_housing.createCell( 4 );
               cell_housing_yuan.setCellValue( "元" );
               cell_housing_yuan.setCellStyle( cellStyle_t3 );
               bodyRowIndex++;
               // 再空一行
               bodyRowIndex++;
               // 合计
               final XSSFRow row_amount = sheet.createRow( bodyRowIndex );
               final XSSFCell cell_amount = row_amount.createCell( 1 );
               cell_amount.setCellValue( "合计：" );
               cell_amount.setCellStyle( cellStyle_t3 );
               // 合计值
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 2, 3 ) );
               final XSSFCell cell_amount_value = row_amount.createCell( 2 );

               cell_amount_value.setCellValue( sumMgtFee + sumAgentSalary + sumSBCommon + sumSBHousing );
               cell_amount_value.setCellStyle( cellStyle_t3 );
               final XSSFCell cell_amount_yuan = row_amount.createCell( 4 );
               cell_amount_yuan.setCellValue( "元" );
               cell_amount_yuan.setCellStyle( cellStyle_t3 );
               bodyRowIndex++;

            }
         }

         return workbook;
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   private static boolean isSBOnly( List< ListDetailVO > mergeListDetailVOs )
   {
      // 判断是否有工资科目 item = 1
      boolean sbOnly = true;
      for ( ListDetailVO detailVO : mergeListDetailVOs )
      {
         if ( "1".equals( detailVO.getItemId() ) )
         {
            sbOnly = false;
            break;
         }
      }
      return sbOnly;
   }

   /**
    * 
    * @param pagedListHolder
    * @param isCommonSB
    * @param isSBOnly  只交社保（没有基本工资的情况）
    * @return
    */
   private static Double getSumSBAllValue( final PagedListHolder pagedListHolder, final String accountId, final boolean isCommonSB, final boolean isSBOnly )
   {
      Double sum = 0d;
      final List< ItemVO > itemVOs = KANConstants.getKANAccountConstants( accountId ).ITEM_VO;
      if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         List< Object > objects = pagedListHolder.getSource();
         for ( int i = 0; i < objects.size(); i++ )
         {
            final Object obj = objects.get( i );
            final SettlementDTO settlementDTO = ( SettlementDTO ) obj;
            List< OrderDetailVO > orderDetailVOs = settlementDTO.getOrderDetailVOs();
            if ( orderDetailVOs != null && orderDetailVOs.size() > 0 )
            {
               for ( int j = 0; j < orderDetailVOs.size(); j++ )
               {
                  final OrderDetailVO orderDetailVO = orderDetailVOs.get( j );
                  for ( ItemVO itemVO : itemVOs )
                  {
                     if ( itemVO.getItemId().equals( orderDetailVO.getItemId() ) )
                     {
                        // 如果是查询普通社保
                        if ( isCommonSB && "7".equals( itemVO.getItemType() ) && !"67".equals( orderDetailVO.getItemId() ) )
                        {
                           // 如果只交社保。则社保=公司+个人
                           // 如果发工资，公司代缴个人，则社保=公司+公司承担个人
                           // 如果发工资,公司不承担个人，则社保=公司
                           final String tempC = KANUtil.filterEmpty( orderDetailVO.getSbBillAmountCompany() ) == null ? "0" : orderDetailVO.getSbBillAmountCompany();
                           String tempP = "0";
                           if ( isSBOnly )
                           {
                              tempP = KANUtil.filterEmpty( orderDetailVO.getSbCostAmountPersonal() ) == null ? "0" : orderDetailVO.getSbCostAmountPersonal();
                           }
                           else
                           {
                              // PersonalSBBurdenValues 个人社保公司代缴的金额。一个人可能有多分社保。其中有的是公司承担，有的不是。所以在service里面就把公司承担统计的单独放到这个字段.数据取自于sbCostAmountPersonal
                              tempP = KANUtil.filterEmpty( orderDetailVO.getPersonalSBBurdenValues() ) == null ? "0" : orderDetailVO.getPersonalSBBurdenValues();
                           }
                           if ( KANUtil.filterEmpty( orderDetailVO.getSbMonthly() ) == null )
                           {
                              // 补缴的社保
                              sum += Double.parseDouble( KANUtil.filterEmpty( orderDetailVO.getBillAmountCompany() ) == null ? "0" : orderDetailVO.getBillAmountCompany() )
                                    + Double.parseDouble( KANUtil.filterEmpty( orderDetailVO.getCostAmountPersonal() ) == null ? "0" : orderDetailVO.getCostAmountPersonal() );
                           }
                           else
                           {
                              sum += Double.parseDouble( tempC ) + Double.parseDouble( tempP );
                           }
                           break;
                        }

                        // 如果是查询公积金
                        else if ( !isCommonSB && "7".equals( itemVO.getItemType() ) && "67".equals( orderDetailVO.getItemId() ) )
                        {
                           final String tempC = KANUtil.filterEmpty( orderDetailVO.getSbBillAmountCompany() ) == null ? "0" : orderDetailVO.getSbBillAmountCompany();
                           String tempP = "0";
                           if ( isSBOnly )
                           {
                              tempP = KANUtil.filterEmpty( orderDetailVO.getSbCostAmountPersonal() ) == null ? "0" : orderDetailVO.getSbCostAmountPersonal();
                           }
                           else
                           {
                              tempP = KANUtil.filterEmpty( orderDetailVO.getPersonalSBBurdenValues() ) == null ? "0" : orderDetailVO.getPersonalSBBurdenValues();
                           }
                           
                           if ( KANUtil.filterEmpty( orderDetailVO.getSbMonthly() ) == null )
                           {
                              // 补缴的社保
                              sum += Double.parseDouble( KANUtil.filterEmpty( orderDetailVO.getBillAmountCompany() ) == null ? "0" : orderDetailVO.getBillAmountCompany() )
                                    + Double.parseDouble( KANUtil.filterEmpty( orderDetailVO.getCostAmountPersonal() ) == null ? "0" : orderDetailVO.getCostAmountPersonal() );
                           }
                           else
                           {
                              sum += Double.parseDouble( tempC ) + Double.parseDouble( tempP );
                           }
                           break;
                        }
                     }
                  }
               }
            }
         }
      }

      return sum;
   }

   private static Double getSumCBAllValue( final PagedListHolder pagedListHolder, final String accountId )
   {
      Double sum = 0d;
      final List< ItemVO > itemVOs = KANConstants.getKANAccountConstants( accountId ).ITEM_VO;
      if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         List< Object > objects = pagedListHolder.getSource();
         for ( int i = 0; i < objects.size(); i++ )
         {
            final Object obj = objects.get( i );
            final SettlementDTO settlementDTO = ( SettlementDTO ) obj;
            List< OrderDetailVO > orderDetailVOs = settlementDTO.getOrderDetailVOs();
            if ( orderDetailVOs != null && orderDetailVOs.size() > 0 )
            {
               for ( int j = 0; j < orderDetailVOs.size(); j++ )
               {
                  final OrderDetailVO orderDetailVO = orderDetailVOs.get( j );
                  for ( ItemVO itemVO : itemVOs )
                  {
                     if ( itemVO.getItemId().equals( orderDetailVO.getItemId() ) )
                     {
                        // 如果是商保
                        if ( "8".equals( itemVO.getItemType() ) )
                        {
                           if ( KANUtil.filterEmpty( orderDetailVO.getBillAmountPersonal() ) != null )
                           {
                              sum += Double.parseDouble( orderDetailVO.getBillAmountPersonal() );
                           }
                           if ( KANUtil.filterEmpty( orderDetailVO.getBillAmountCompany() ) != null )
                           {
                              sum += Double.parseDouble( orderDetailVO.getBillAmountCompany() );
                           }
                           break;
                        }
                     }
                  }
               }
            }
         }
      }

      return sum;
   }

   // 获取社保上下限
   private static String[] getSBRange( PagedListHolder pagedListHolder, boolean isHousing ) throws KANException
   {
      String[] arr = { "0", "0" };
      if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         List< Object > objects = pagedListHolder.getSource();
         String sbHeaderId = "";
         for ( Object obj : objects )
         {
            final SettlementDTO settlementDTO = ( SettlementDTO ) obj;
            List< OrderDetailVO > orderDetailVOs = settlementDTO.getOrderDetailVOs();
            if ( orderDetailVOs != null && orderDetailVOs.size() > 0 )
            {
               for ( OrderDetailVO orderDetailVO : orderDetailVOs )
               {
                  sbHeaderId = orderDetailVO.getSbHeaderId();
                  break;
               }
            }
            if ( KANUtil.filterEmpty( sbHeaderId ) != null )
            {
               break;
            }
         }
         if ( KANUtil.filterEmpty( sbHeaderId ) != null )
         {
            final SBHeaderService sbHeaderService = ( SBHeaderService ) ServiceLocator.getService( "sbHeaderService" );
            final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) ServiceLocator.getService( "employeeContractSBService" );
            final SocialBenefitSolutionDetailService socialBenefitSolutionDetailService = ( SocialBenefitSolutionDetailService ) ServiceLocator.getService( "socialBenefitSolutionDetailService" );

            final SBHeaderVO sbHeaderVO = sbHeaderService.getSBHeaderVOByHeaderId( sbHeaderId );
            final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( sbHeaderVO.getEmployeeSBId() );
            List< Object > socialBenefitSolutionDetailVOObjects = socialBenefitSolutionDetailService.getSocialBenefitSolutionDetailVOsByHeaderId( employeeContractSBVO.getSbSolutionId() );
            if ( socialBenefitSolutionDetailVOObjects != null && socialBenefitSolutionDetailVOObjects.size() > 0 )
            {
               List< ItemVO > itemVOs = KANConstants.ITEM_VO;
               boolean findBenefitSolution = false;
               for ( ItemVO itemVO : itemVOs )
               {
                  // 如果不是社保科目
                  if ( !"7".equals( itemVO.getItemType() ) )
                  {
                     continue;
                  }
                  //如果是住房公积金
                  if ( isHousing )
                  {
                     if ( !"67".equals( itemVO.getItemId() ) )
                     {
                        continue;
                     }
                  }
                  else
                  {
                     if ( "67".equals( itemVO.getItemId() ) )
                     {
                        continue;
                     }
                  }

                  for ( Object object : socialBenefitSolutionDetailVOObjects )
                  {
                     final SocialBenefitSolutionDetailVO benefitSolutionDetailVO = ( SocialBenefitSolutionDetailVO ) object;
                     arr[ 0 ] = benefitSolutionDetailVO.getCompanyFloor();
                     arr[ 1 ] = benefitSolutionDetailVO.getCompanyCap();
                     findBenefitSolution = true;
                     break;
                  }
                  //如果找到
                  if ( findBenefitSolution )
                  {
                     break;
                  }
               }
            }
         }

      }
      return arr;

   }

   private static String getRate( PagedListHolder pagedListHolder, String itemId, boolean isCompany )
   {
      String rate = "";
      List< Object > objects = pagedListHolder.getSource();
      if ( objects != null && objects.size() > 0 )
      {
         boolean flag = false;
         for ( Object obj : objects )
         {
            final SettlementDTO settlementDTO = ( SettlementDTO ) obj;
            if ( settlementDTO != null && settlementDTO.getOrderDetailVOs().size() > 0 )
            {
               for ( OrderDetailVO orderDetailVO : settlementDTO.getOrderDetailVOs() )
               {
                  if ( itemId.equals( orderDetailVO.getItemId() ) )
                  {
                     // 如果是查公司的
                     if ( isCompany )
                     {
                        rate = orderDetailVO.getBillRateCompany();
                     }
                     // 查个人
                     else
                     {
                        rate = orderDetailVO.getBillRatePersonal();
                     }

                     flag = true;
                     break;

                  }
               }
            }
            if ( flag )
            {
               break;
            }
         }
      }
      return KANUtil.filterEmpty( rate ) == null ? "" : rate;
   }

   private static Double sumExceptItemType( final List< Object > source, final List< String > exceptItemTypes, final String accountId )
   {
      Double sum = 0.00d;
      for ( Object obj : source )
      {
         final SettlementDTO settlementDTO = ( SettlementDTO ) obj;
         List< OrderDetailVO > orderDetailVOs = settlementDTO.getOrderDetailVOs();
         for ( OrderDetailVO orderDetailVO : orderDetailVOs )
         {
            if ( !exceptItemTypes.contains( getItemTypeByItemId( orderDetailVO.getItemId(), accountId ) ) )
            {
               sum += Double.parseDouble( orderDetailVO.getBillAmountCompany() );
            }
         }
      }
      return sum;
   }

   private static Double sumItemValue( List< Object > list, String[] itemIds )
   {
      Double sum = 0.00d;
      for ( Object obj : list )
      {
         final SettlementDTO settlementDTO = ( SettlementDTO ) obj;
         List< OrderDetailVO > orderDetailVOs = settlementDTO.getOrderDetailVOs();
         for ( OrderDetailVO orderDetailVO : orderDetailVOs )
         {
            for ( String itemId : itemIds )
            {
               if ( itemId.equals( orderDetailVO.getItemId() ) )
               {
                  sum += Double.parseDouble( orderDetailVO.getBillAmountCompany() );
               }
            }
         }
      }
      return sum;
   }

   // 获取所有的社保科目ID。（用于遍历普通科目）
   private static List< String > getSBItemIdsBySBItemDetailVOList( List< ListDetailVO > sbItemDetailVOList )
   {
      List< String > sbItemIds = new ArrayList< String >();
      for ( ListDetailVO listDetailVO : sbItemDetailVOList )
      {
         sbItemIds.add( listDetailVO.getItemId() );
      }
      return sbItemIds;
   }

   //判断科目是否存在（用于判断住房公积金）
   private static boolean isItemExist( List< ListDetailVO > sbItemDetailVOList, String targetItemId )
   {
      boolean exist = false;
      for ( ListDetailVO tempVO : sbItemDetailVOList )
      {
         if ( targetItemId.equals( tempVO.getItemId() ) )
         {
            exist = true;
            break;
         }
      }
      return exist;
   }

   private static Double getCellValue( XSSFSheet sheet, int row, int column )
   {
      return sheet.getRow( row ) != null ? sheet.getRow( row ).getCell( column ).getNumericCellValue() : 0.00d;
   }

   /**
    * @param sheet 
    * @param rowStart 开始行
    * @param rowEnd  结束行
    * @param columnStart  开始列
    * @return
    */
   private static Double getSumMulitCellValues( XSSFSheet sheet, int rowStart, int rowEnd, int columnStart )
   {
      return getSumMulitCellValues( sheet, rowStart, rowEnd, columnStart, columnStart );
   }

   private static Double getSumMulitCellValues( XSSFSheet sheet, int rowStart, int rowEnd, int columnStart, int columnEnd )
   {
      Double sum = 0.00d;
      for ( int i = rowStart; i <= rowEnd; i++ )
      {
         for ( int j = columnStart; j <= columnEnd; j++ )
         {
            XSSFRow row = sheet.getRow( i );
            XSSFCell cell = row.getCell( j );
            if ( cell != null )
            {
               sum += cell.getNumericCellValue();
            }
         }
      }
      return sum;
   }

   // 67 住房公积金,基数，公司个人
   private static List< Double > getItemHousing( SettlementDTO settlementDTO )
   {
      List< Double > sbValues = new ArrayList< Double >();

      // 如果是住房公积金
      for ( OrderDetailVO vo : settlementDTO.getOrderDetailVOs() )
      {
         //
         if ( "67".equals( vo.getItemId() ) )
         {
            if ( KANUtil.filterEmpty( vo.getSbMonthly() ) == null )
            {
               sbValues.add( Double.parseDouble( KANUtil.formatNumberNull2Zero( vo.getSbBaseCompany() ) ) );
               sbValues.add( Double.parseDouble( KANUtil.formatNumberNull2Zero( vo.getBillAmountCompany() ) ) );
               sbValues.add( Double.parseDouble( KANUtil.formatNumberNull2Zero( vo.getCostAmountPersonal() ) ) );
            }
            else
            {
               sbValues.add( Double.parseDouble( KANUtil.formatNumberNull2Zero( vo.getSbBaseCompany() ) ) );
               sbValues.add( Double.parseDouble( KANUtil.formatNumberNull2Zero( vo.getSbBillAmountCompany() ) ) );
               sbValues.add( Double.parseDouble( KANUtil.formatNumberNull2Zero( vo.getSbCostAmountPersonal() ) ) );
            }
            break;
         }
      }

      return sbValues;
   }

   // 养老8% 61，失业0.5% 63，医疗2% 62，大病 73，小计2
   // 排除 住房公积金，67
   private static List< Double > getPersonalSB( SettlementDTO settlementDTO, List< String > sbItemIds )
   {
      List< Double > sbValues = new ArrayList< Double >();
      Double sum = 0d;
      for ( String sbItem : sbItemIds )
      {
         // 如果是住房公积金
         if ( "67".equals( sbItem ) )
         {
            continue;
         }

         boolean flag = false;

         for ( OrderDetailVO vo : settlementDTO.getOrderDetailVOs() )
         {
            //
            if ( sbItem.equals( vo.getItemId() ) )
            {
               Double tempValue = Double.parseDouble( KANUtil.formatNumberNull2Zero( vo.getSbCostAmountPersonal() ) );
               if ( KANUtil.filterEmpty( vo.getSbMonthly() ) == null )
               {
                  tempValue = Double.parseDouble( KANUtil.formatNumberNull2Zero( vo.getCostAmountPersonal() ) );
               }
               else
               {
                  tempValue = Double.parseDouble( KANUtil.formatNumberNull2Zero( vo.getSbCostAmountPersonal() ) );
               }
               sbValues.add( tempValue );
               sum += tempValue;
               flag = true;
               break;
            }
         }
         // 如果没有当前科目
         if ( !flag )
         {
            sbValues.add( 0.00d );
         }
      }
      sbValues.add( sum );
      return sbValues;
   }

   // 获取公司的五险一金值
   // 养老20% 61，失业1.5% 63，生育0.8% 65，工伤0.5% 64，医疗9% 62，小计1
   // 排除 住房公积金，67
   private static List< Double > getCompanySB( SettlementDTO settlementDTO, List< String > sbItemIds )
   {
      List< Double > sbValues = new ArrayList< Double >();
      Double sum = 0d;
      for ( String sbItem : sbItemIds )
      {
         // 如果是住房公积金
         if ( "67".equals( sbItem ) )
         {
            continue;
         }

         boolean flag = false;

         for ( OrderDetailVO vo : settlementDTO.getOrderDetailVOs() )
         {

            if ( sbItem.equals( vo.getItemId() ) )
            {
               Double tempValue = 0.0d;
               // 如果是当月的代缴。则取billAmountCompany
               if ( KANUtil.filterEmpty( vo.getSbMonthly() ) == null )
               {
                  tempValue = Double.parseDouble( KANUtil.formatNumberNull2Zero( vo.getBillAmountCompany() ) );
               }
               else
               {
                  tempValue = Double.parseDouble( KANUtil.formatNumberNull2Zero( vo.getSbBillAmountCompany() ) );
               }
               sbValues.add( tempValue );
               sum += tempValue;
               flag = true;
               break;
            }
         }
         // 如果没有当前科目
         if ( !flag )
         {
            sbValues.add( 0.00d );
         }
      }
      sbValues.add( sum );
      return sbValues;
   }

   //
   private static Double getSettlementValueByItemId( SettlementDTO settlementDTO, ListDetailVO itemDetailVO )
   {
      String value = "0.00";
      List< OrderDetailVO > orderDetailVOs = settlementDTO.getOrderDetailVOs();
      for ( OrderDetailVO detailVO : orderDetailVOs )
      {
         if ( ( detailVO.getItemId() ).equals( itemDetailVO.getItemId() ) )
         {
            value = KANUtil.formatNumberNull2Zero( detailVO.getBillAmountCompany() );
            break;
         }
      }
      return Double.parseDouble( value );
   }

   /**
    * 调整列，把社保列单独拿出来
    * (宽度为集合中所有的科目，必加五险一金（14列），加2列个人信息)
    * 61 养老  ；63 失业；65 生育；64 工伤；62 医疗；73 大病;67住房公积金。
    * @param mergeListDetailVOs
    * @return
    */
   private static List< ListDetailVO > fixListDetailVOs( List< ListDetailVO > mergeListDetailVOs )
   {
      List< ItemVO > itemVOs = KANConstants.ITEM_VO;
      // 系统社保科目的ID
      List< String > sbItemIds = new ArrayList< String >();
      // 当前的社保科目ListDetailVOs
      List< ListDetailVO > resultSBListDetailVO = new ArrayList< ListDetailVO >();
      List< ListDetailVO > tempListDetailVO = new ArrayList< ListDetailVO >();
      for ( ItemVO tempVO : itemVOs )
      {
         if ( "7".equals( tempVO.getItemType() ) )
         {
            sbItemIds.add( tempVO.getItemId() );
         }
      }
      // 删除item_999
      for ( ListDetailVO tempVO : mergeListDetailVOs )
      {
         if ( !"999".equals( tempVO.getItemId() ) )
         {
            tempListDetailVO.add( tempVO );
         }
      }
      mergeListDetailVOs.clear();
      mergeListDetailVOs.addAll( tempListDetailVO );
      // 添加社保
      for ( ListDetailVO tempVO : mergeListDetailVOs )
      {
         // 如果是社保科目
         if ( sbItemIds.contains( tempVO.getItemId() ) )
         {
            resultSBListDetailVO.add( tempVO );
         }
      }
      return resultSBListDetailVO;
   }

   private static String getEmployeeName( final HttpServletRequest request, final SettlementDTO settlementDTO ) throws KANException
   {
      // 列表字段显示名称
      String name = "";

      if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
      {
         name = settlementDTO.getServiceContractVO().getEmployeeNameZH();
      }
      else
      {
         name = settlementDTO.getServiceContractVO().getEmployeeNameEN();
      }

      return name;
   }

   private static String getColumnName( final HttpServletRequest request, final ListDetailVO listDetailVO ) throws KANException
   {
      // 列表字段显示名称
      String columnName = "";

      if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
      {
         columnName = listDetailVO.getNameZH();
      }
      else
      {
         columnName = listDetailVO.getNameEN();
      }

      return columnName;
   }

   // 合并从表DetailVO
   @SuppressWarnings("unchecked")
   private static List< ListDetailVO > mergeListDetailVO( final HttpServletRequest request, final List< ListDTO > listDTOs ) throws KANException
   {
      try
      {
         // 初始化PagedListHolder
         final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );
         // 初始化所有科目
         final List< ItemVO > items = ( List< ItemVO > ) ( ( PagedListHolder ) request.getAttribute( "pagedListHolder" ) ).getAdditionalObject();

         if ( items != null && items.size() > 0 )
         {
            return fetchListDetailVOByItemVO( items, listDTOs );
         }
         else
         {
            // 存在PagedListHolder
            if ( pagedListHolder != null && pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
            {
               for ( Object objectDTO : pagedListHolder.getSource() )
               {
                  if ( objectDTO instanceof SpecialDTO )
                  {
                     final SpecialDTO< Object, List< ? > > specialDTO = ( SpecialDTO< Object, List< ? >> ) objectDTO;
                     return fetchListDetailVO( specialDTO, listDTOs );
                  }
               }
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   private static List< ListDetailVO > fetchListDetailVOByItemVO( final List< ItemVO > items, final List< ListDTO > listDTOs )
   {
      // 初始化ListDetailVO List
      final List< ListDetailVO > listDetailVOs = new ArrayList< ListDetailVO >();

      if ( items != null && items.size() > 0 )
      {
         for ( ItemVO itemVO : items )
         {
            for ( ListDTO listDTO : listDTOs )
            {
               if ( listDTO != null && listDTO.getListDetailVOs().size() > 0 )
               {
                  int count = 0;
                  for ( ListDetailVO listDetailVO : listDTO.getListDetailVOs() )
                  {
                     if ( KANUtil.filterEmpty( listDetailVO.getPropertyName() ) != null && listDetailVO.getPropertyName().split( "_" )[ 1 ].equals( itemVO.getItemId() ) )
                     {
                        count++;
                        listDetailVOs.add( listDetailVO );
                     }

                     if ( itemVO.getItemType() != null )
                     {
                        if ( ( itemVO.getItemType().equals( "7" ) && count == 2 ) || ( !itemVO.getItemType().trim().equals( "7" ) && count == 1 ) )
                        {
                           break;
                        }
                     }
                  }
               }
            }
         }
      }

      return listDetailVOs;
   }

   @SuppressWarnings("unchecked")
   private static List< ListDetailVO > fetchListDetailVO( final SpecialDTO< Object, List< ? > > specialDTO, final List< ListDTO > listDTOs ) throws KANException
   {
      // 初始化ListDetailVO List
      final List< ListDetailVO > listDetailVOs = new ArrayList< ListDetailVO >();

      for ( Object object : specialDTO.getDetailVOs() )
      {
         // 获取科目ID
         final String itemId = ( String ) KANUtil.getValue( object, "itemId" );
         boolean flag = true;

         if ( specialDTO.getFlags() != null )
         {
            Map< String, Integer > flags = ( Map< String, Integer > ) specialDTO.getFlags();
            if ( flags != null && flags.get( itemId ) == 2 )
            {
               flag = false;
            }
         }

         // 获取科目类型
         final String itemType = ( String ) KANUtil.getValue( object, "itemType" );

         for ( ListDTO listDTO : listDTOs )
         {
            if ( listDTO != null && listDTO.getListDetailVOs().size() > 0 )
            {
               int count = 0;
               for ( ListDetailVO listDetailVO : listDTO.getListDetailVOs() )
               {
                  if ( flag )
                  {
                     if ( KANUtil.filterEmpty( listDetailVO.getPropertyName() ) != null && listDetailVO.getPropertyName().split( "_" )[ 1 ].equals( itemId ) )
                     {
                        count++;
                        listDetailVOs.add( listDetailVO );
                     }

                     if ( KANUtil.filterEmpty( itemType ) != null )
                     {
                        if ( ( itemType.equals( "7" ) && count == 2 ) || ( !itemType.trim().equals( "7" ) && count == 1 ) )
                        {
                           break;
                        }
                     }
                  }
               }
            }
         }
      }

      return listDetailVOs;
   }

   // 内部类 - 排序columnIndex
   private static class ComparatorListDetail implements Comparator< ListDetailVO >
   {
      @Override
      public int compare( final ListDetailVO o1, final ListDetailVO o2 )
      {
         if ( KANUtil.filterEmpty( o1.getColumnIndex() ) == null || KANUtil.filterEmpty( o2.getColumnIndex() ) == null )
         {
            return 1;
         }

         return Integer.valueOf( o1.getColumnIndex() ) - Integer.valueOf( o2.getColumnIndex() );
      }
   }

   private static String getItemTypeByItemId( final String itemId, final String accountId )
   {
      String itemType = "";
      try
      {
         final ItemVO itemVO = KANConstants.getKANAccountConstants( accountId ).getItemVOByItemId( itemId );
         if ( itemVO != null )
         {
            itemType = itemVO.getItemType();
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return itemType;
   }
}

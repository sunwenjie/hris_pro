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
         // ��ʼ��corpId
         final String corpId = KANUtil.filterEmpty( BaseAction.getCorpId( request, null ) );
         final String accountId = BaseAction.getAccountId( request, null );

         ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, corpId );

         // ��ʼ��PagedListHolder
         final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );
         final ClientVO clientVO = ( ClientVO ) request.getAttribute( "clientVO" );
         final OrderBillHeaderView orderBillHeaderView = ( OrderBillHeaderView ) pagedListHolder.getObject();
         final String[] monthly = orderBillHeaderView.getMonthly().split( "/" );
         final String header = clientVO.getNameZH() + monthly[ 0 ] + "��" + monthly[ 1 ] + "�½����嵥";
         String sub_header = "�Ʊ�ʱ�䣺" + KANUtil.formatDate( new Date(), "yyyy.MM.dd" ) + "          ";
         sub_header += "�Ʊ��ˣ�" + BaseAction.getUsername( request, null ) + "            ";
         sub_header += "����ˣ�";

         // ��ʼ��������
         final XSSFWorkbook workbook = new XSSFWorkbook();
         final List< XSSFCellStyle > cellStyles = new ArrayList< XSSFCellStyle >();
         if ( listDTO != null && pagedListHolder != null )
         {

            // ��������
            final XSSFFont font = workbook.createFont();
            font.setFontName( "������" );
            font.setFontHeightInPoints( ( short ) 10 );

            // ��������(һ����������)
            final XSSFFont font_t1 = workbook.createFont();
            font_t1.setFontName( "������" );
            font_t1.setFontHeightInPoints( ( short ) 14 );
            font_t1.setBold( true );

            // ��������(������������)
            final XSSFFont font_t2 = workbook.createFont();
            font_t2.setFontName( "������" );
            font_t2.setFontHeightInPoints( ( short ) 11 );
            font_t2.setBold( true );

            // ��������(������������)
            final XSSFFont font_t3 = workbook.createFont();
            font_t3.setFontName( "������" );
            font_t3.setFontHeightInPoints( ( short ) 9 );
            font_t3.setBold( true );

            // �������� bule
            final XSSFFont font_blue = workbook.createFont();
            font_blue.setFontName( "������" );
            font_blue.setFontHeightInPoints( ( short ) 9 );
            font_blue.setBold( true );
            font_blue.setItalic( true );

            // ������Ԫ����ʽ
            final XSSFCellStyle cellStyleLeft = workbook.createCellStyle();
            cellStyleLeft.setFont( font );
            cellStyleLeft.setAlignment( XSSFCellStyle.ALIGN_LEFT );
            cellStyleLeft.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyles.add( cellStyleLeft );

            // ������Ԫ����ʽ
            final XSSFCellStyle cellStyleCenter = workbook.createCellStyle();
            cellStyleCenter.setFont( font );
            cellStyleCenter.setAlignment( XSSFCellStyle.ALIGN_CENTER );
            cellStyleCenter.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyles.add( cellStyleCenter );

            // ������Ԫ����ʽ
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

            // ������Ԫ����ʽ(һ��������ʽ)
            final XSSFCellStyle cellStyle_t1 = workbook.createCellStyle();
            cellStyle_t1.setFont( font_t1 );
            cellStyle_t1.setAlignment( XSSFCellStyle.ALIGN_CENTER );
            cellStyle_t1.setVerticalAlignment( XSSFCellStyle.VERTICAL_CENTER );
            cellStyle_t1.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyles.add( cellStyle_t1 );

            // ������Ԫ����ʽ(����������ʽ)
            final XSSFCellStyle cellStyle_t2 = workbook.createCellStyle();
            cellStyle_t2.setFont( font_t2 );
            cellStyle_t2.setAlignment( XSSFCellStyle.ALIGN_CENTER );
            cellStyle_t2.setVerticalAlignment( XSSFCellStyle.VERTICAL_CENTER );
            cellStyle_t2.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyles.add( cellStyle_t2 );

            // ������Ԫ����ʽ(����������ʽ)
            final XSSFCellStyle cellStyle_t3 = workbook.createCellStyle();
            cellStyle_t3.setFont( font_t3 );
            cellStyle_t3.setAlignment( XSSFCellStyle.ALIGN_CENTER );
            cellStyle_t3.setDataFormat( workbook.createDataFormat().getFormat( "##0.00" ) );
            cellStyle_t3.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyles.add( cellStyle_t3 );

            // ������Ԫ����ʽ  ��ɫ
            final XSSFCellStyle cellStyle_blue = workbook.createCellStyle();
            cellStyle_blue.setFont( font_blue );
            cellStyle_blue.setAlignment( XSSFCellStyle.ALIGN_CENTER );
            cellStyle_blue.setFillForegroundColor( new XSSFColor( new Color( 204, 255, 255 ) ) );
            cellStyle_blue.setFillPattern( XSSFCellStyle.SOLID_FOREGROUND );
            cellStyle_blue.setDataFormat( workbook.createDataFormat().getFormat( "##0.00" ) );
            cellStyle_blue.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyles.add( cellStyle_blue );

            // ��ɫ
            final XSSFCellStyle cellStyle_gray = workbook.createCellStyle();
            cellStyle_gray.setFont( font_t3 );
            cellStyle_gray.setAlignment( XSSFCellStyle.ALIGN_CENTER );
            cellStyle_gray.setFillForegroundColor( new XSSFColor( new Color( 192, 192, 192 ) ) );
            cellStyle_gray.setFillPattern( XSSFCellStyle.SOLID_FOREGROUND );
            cellStyle_gray.setDataFormat( workbook.createDataFormat().getFormat( "##0.00" ) );
            cellStyle_gray.setBottomBorderColor( new XSSFColor( new Color( 0, 0, 0 ) ) );
            cellStyles.add( cellStyle_gray );

            // ��ӱ߿�
            /*for ( int i = 0; i < cellStyles.size(); i++ )
            {
               cellStyles.get( i ).setBorderLeft( XSSFCellStyle.BORDER_THIN );
               cellStyles.get( i ).setBorderBottom( XSSFCellStyle.BORDER_THIN );
               cellStyles.get( i ).setBorderRight( XSSFCellStyle.BORDER_THIN );
               cellStyles.get( i ).setBorderTop( XSSFCellStyle.BORDER_THIN );
            }*/

            // �������
            final XSSFSheet sheet = workbook.createSheet( listDTO.getListName( request ) );
            // ���ñ��Ĭ���п��Ϊ10���ֽ�
            sheet.setDefaultColumnWidth( 10 );

            // ��ʼ�����ϲ�����ListDetailVO��ListDetailVO�б�
            final List< ListDetailVO > mergeListDetailVOs = new ArrayList< ListDetailVO >();

            // �����б��ֶ�
            if ( listDTO.getListDetailVOs() != null && listDTO.getListDetailVOs().size() > 0 )
            {

               // ������ڴ�ListDetailVO
               if ( listDTO.getSubListDTOs() != null && listDTO.getSubListDTOs().size() > 0 )
               {
                  // ��ʼ��Sub ListDetailVO
                  final List< ListDetailVO > subListDetailVOs = mergeListDetailVO( request, listDTO.getSubListDTOs() );

                  if ( subListDetailVOs != null && subListDetailVOs.size() > 0 )
                  {
                     mergeListDetailVOs.addAll( subListDetailVOs );
                  }
               }

               // ����
               if ( mergeListDetailVOs.size() > 0 )
               {
                  Collections.sort( mergeListDetailVOs, new ComparatorListDetail() );
               }
               // ������,�������籣��Ŀ�ļ���
               final List< ListDetailVO > sbItemDetailVOList = fixListDetailVOs( mergeListDetailVOs );
               // �Ƿ���ס�������� 67
               final boolean isItem_housingExist = isItemExist( sbItemDetailVOList, "67" );
               // �ܿ�Ŀ������
               final int TOTAL_ITEM_LENGTH = mergeListDetailVOs.size();
               // �籣��Ŀ����
               final int TOTAL_SB_ITEM_LENGTH = sbItemDetailVOList.size();
               // ��ͨ��Ŀ����
               final int TOTAL_COMMON_ITEM_LENGTH = TOTAL_ITEM_LENGTH - TOTAL_SB_ITEM_LENGTH;
               // �ų��������籣��Ŀ����
               final int TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH = isItem_housingExist ? TOTAL_SB_ITEM_LENGTH - 1 : TOTAL_SB_ITEM_LENGTH;
               // ס������������
               final int TOTAL_ITEM_67_LENGTH = isItem_housingExist ? 3 : 0;
               // �Ƿ�ֻ���籣
               final boolean isSBOnly = isSBOnly( mergeListDetailVOs );
               // ����ͷ
               final XSSFRow rowHeader = sheet.createRow( 0 );
               rowHeader.setHeightInPoints( 50 );
               // ͷ�ϲ�
               final int rowHeaderLength = 3 + TOTAL_COMMON_ITEM_LENGTH + 1 + ( TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH + 1 ) * 2 + TOTAL_ITEM_67_LENGTH;
               sheet.addMergedRegion( new CellRangeAddress( 0, 0, 0, rowHeaderLength - 1 ) );
               final XSSFCell cellHeader = rowHeader.createCell( 0 );
               final XSSFRichTextString text = new XSSFRichTextString( header );
               cellHeader.setCellValue( text );
               cellHeader.setCellStyle( cellStyle_t1 );
               // �ڶ��У��ϲ�2�е�Ԫ��
               final XSSFRow rowTitle = sheet.createRow( 1 );
               rowTitle.setHeightInPoints( 20 );
               sheet.addMergedRegion( new CellRangeAddress( 1, 2, 0, rowHeaderLength - 1 ) );
               final XSSFCell cellTitle = rowTitle.createCell( 0 );
               cellTitle.setCellValue( new XSSFRichTextString( sub_header ) );
               cellTitle.setCellStyle( cellStyle_t2 );
               // ��ͷ
               final XSSFRow rowTableHeader3 = sheet.createRow( 3 );
               final XSSFRow rowTableHeader4 = sheet.createRow( 4 );
               final XSSFRow rowTableHeader5 = sheet.createRow( 5 );
               // rowNumber ���
               sheet.addMergedRegion( new CellRangeAddress( 3, 5, 0, 0 ) );
               final XSSFCell cellNumber = rowTableHeader3.createCell( 0 );
               cellNumber.setCellValue( new XSSFRichTextString( "���" ) );
               cellNumber.setCellStyle( cellStyle_t3 );
               // rowEmployeeName ����
               sheet.addMergedRegion( new CellRangeAddress( 3, 5, 1, 1 ) );
               final XSSFCell cellEmployeeName = rowTableHeader3.createCell( 1 );
               cellEmployeeName.setCellValue( new XSSFRichTextString( "����" ) );
               cellEmployeeName.setCellStyle( cellStyle_t3 );
               // �籣�·�
               sheet.addMergedRegion( new CellRangeAddress( 3, 5, 2, 2 ) );
               final XSSFCell cellSBMonthly = rowTableHeader3.createCell( 2 );
               cellSBMonthly.setCellValue( new XSSFRichTextString( "�籣�·�" ) );
               cellSBMonthly.setCellStyle( cellStyle_t3 );
               // �����Ŀ
               final List< String > sbItemIds = getSBItemIdsBySBItemDetailVOList( sbItemDetailVOList );
               int itemColumnIndex = 3;
               for ( ListDetailVO itemDetailVO : mergeListDetailVOs )
               {
                  // �籣��Ŀ���⴦��
                  if ( sbItemIds.contains( itemDetailVO.getItemId() ) )
                  {
                     // ����� �籣��Ŀ������
                     continue;
                  }
                  sheet.addMergedRegion( new CellRangeAddress( 3, 5, itemColumnIndex, itemColumnIndex ) );
                  final XSSFCell cellItemName = rowTableHeader3.createCell( itemColumnIndex );
                  cellItemName.setCellValue( new XSSFRichTextString( getColumnName( request, itemDetailVO ) ) );
                  cellItemName.setCellStyle( cellStyle_t3 );

                  // ��ʼ��Excel�п�����û�����̶��п�����
                  if ( itemDetailVO.getColumnWidthType() != null && itemDetailVO.getColumnWidthType().trim().equals( "2" ) && itemDetailVO.getColumnWidth() != null
                        && itemDetailVO.getColumnWidth().trim().matches( "[0-9]*" ) )
                  {
                     // ����Excel�п�ȡ��
                     final BigDecimal columnWidth = new BigDecimal( Integer.valueOf( itemDetailVO.getColumnWidth() ) * 30 ).setScale( 0, BigDecimal.ROUND_HALF_UP );
                     // ����Excel�п�
                     sheet.setColumnWidth( itemColumnIndex, columnWidth.intValue() );
                  }

                  itemColumnIndex++;
               }
               // �������,(�հ�һ�񣬻�������)
               final XSSFCell cellBaseTopBlank = rowTableHeader3.createCell( itemColumnIndex );
               cellBaseTopBlank.setCellValue( new XSSFRichTextString( "" ) );
               cellBaseTopBlank.setCellStyle( cellStyleCenter );
               // ����
               sheet.addMergedRegion( new CellRangeAddress( 4, 5, itemColumnIndex, itemColumnIndex ) );
               final XSSFCell cellBase = rowTableHeader4.createCell( itemColumnIndex );
               cellBase.setCellValue( new XSSFRichTextString( "����" ) );
               cellBase.setCellStyle( cellStyle_t3 );
               itemColumnIndex++;
               // ��ᱣ��
               sheet.addMergedRegion( new CellRangeAddress( 3, 3, itemColumnIndex, itemColumnIndex + ( TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH + 1 ) * 2 - 1 ) );
               final XSSFCell cellSocialBenefit = rowTableHeader3.createCell( itemColumnIndex );
               final String[] capFloorArr = getSBRange( pagedListHolder, false );
               String capFloorArrStr = "";
               if ( !"0".equals( capFloorArr[ 0 ] ) && !"0".equals( capFloorArr[ 1 ] ) )
               {
                  capFloorArrStr = "(" + capFloorArr[ 0 ] + " - " + capFloorArr[ 1 ] + ")";
               }
               cellSocialBenefit.setCellValue( new XSSFRichTextString( "��ᱣ��" + capFloorArrStr ) );
               cellSocialBenefit.setCellStyle( cellStyle_t3 );
               // ��λ
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
               cellSBCompany.setCellValue( new XSSFRichTextString( "��λ(" + ratesCompany + "%)" ) );
               cellSBCompany.setCellStyle( cellStyle_t3 );
               // ����
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

               cellSBPersonal.setCellValue( new XSSFRichTextString( "���ˣ�" + ratesPersonal + "%��" ) );
               cellSBPersonal.setCellStyle( cellStyle_t3 );
               // ��λ��Ŀ���籣��Ŀ���ƣ�
               for ( ListDetailVO listDetailVO : sbItemDetailVOList )
               {
                  // �ų�ס��������
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
               // ���С��1
               final XSSFCell cellCompanyItemTempXJ1 = rowTableHeader5.createCell( itemColumnIndex );
               cellCompanyItemTempXJ1.setCellValue( "С��1" );
               cellCompanyItemTempXJ1.setCellStyle( cellStyleCenter );
               itemColumnIndex++;

               // ���˿�Ŀ
               for ( ListDetailVO listDetailVO : sbItemDetailVOList )
               {
                  // �ų�ס��������
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

               // ���С��2
               final XSSFCell cellCompanyItemTempXJ2 = rowTableHeader5.createCell( itemColumnIndex );
               cellCompanyItemTempXJ2.setCellValue( "С��2" );
               cellCompanyItemTempXJ2.setCellStyle( cellStyleCenter );
               itemColumnIndex++;

               // ס��������,�������
               if ( isItem_housingExist )
               {
                  sheet.addMergedRegion( new CellRangeAddress( 3, 3, itemColumnIndex, itemColumnIndex + 2 ) );
                  final XSSFCell cellZFGJJ = rowTableHeader3.createCell( itemColumnIndex );
                  cellZFGJJ.setCellValue( new XSSFRichTextString( "ס��������" ) );
                  cellZFGJJ.setCellStyle( cellStyle_t3 );
                  // ס��������ڶ���
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
                  //ס�������� - ����
                  sheet.addMergedRegion( new CellRangeAddress( 5, 5, itemColumnIndex, itemColumnIndex ) );
                  final XSSFCell cellBaseZFGJJ = rowTableHeader5.createCell( itemColumnIndex );
                  cellBaseZFGJJ.setCellValue( new XSSFRichTextString( "����" ) );
                  cellBaseZFGJJ.setCellStyle( cellStyle_t3 );
                  itemColumnIndex++;
                  // ס�������� - 10% 2��
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
                              // ����
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
               // ���Ա�ʶBody�����
               int bodyRowIndex = 6;
               int pageListHolderSize = 0;
               if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
               {
                  // ������
                  for ( Object object : pagedListHolder.getSource() )
                  {
                     final SettlementDTO settlementDTO = ( SettlementDTO ) object;
                     // ����Excel Body Row
                     final XSSFRow rowBody = sheet.createRow( bodyRowIndex );

                     // ���Ա�ʶBody�����
                     int bodyColumnIndex = 0;

                     // ���ֵ
                     final XSSFCell cellBodyIndex = rowBody.createCell( bodyColumnIndex );
                     cellBodyIndex.setCellValue( bodyRowIndex - 5 );
                     cellBodyIndex.setCellStyle( cellStyleCenter );
                     bodyColumnIndex++;
                     //����
                     final XSSFCell cellBodyEmployeeName = rowBody.createCell( bodyColumnIndex );
                     cellBodyEmployeeName.setCellValue( getEmployeeName( request, settlementDTO ) );
                     cellBodyEmployeeName.setCellStyle( cellStyleCenter );
                     bodyColumnIndex++;
                     //�籣�·�
                     final XSSFCell cellBodySBMonthly = rowBody.createCell( bodyColumnIndex );
                     cellBodySBMonthly.setCellValue( "1".equals( settlementDTO.getIsAdjustment() ) ? settlementDTO.getServiceContractVO().getMonthly()
                           : settlementDTO.getServiceContractVO().getSbMonthly() );
                     cellBodySBMonthly.setCellStyle( cellStyleCenter );
                     bodyColumnIndex++;
                     // ��Ŀ
                     for ( ListDetailVO itemDetailVO : mergeListDetailVOs )
                     {
                        // �籣���⴦��
                        if ( sbItemIds.contains( itemDetailVO.getItemId() ) )
                        {
                           // ����� �籣������
                           continue;
                        }
                        final XSSFCell cellBodyItem = rowBody.createCell( bodyColumnIndex );
                        cellBodyItem.setCellValue( getSettlementValueByItemId( settlementDTO, itemDetailVO ) );
                        cellBodyItem.setCellStyle( cellStyleRight_dataFormat );
                        bodyColumnIndex++;
                     }
                     // ������
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
                     // �籣��Ŀ,��˾��eg.����20%��ʧҵ1.5%������0.8%������0.5%��ҽ��9%��С��1
                     List< Double > companySB = getCompanySB( settlementDTO, sbItemIds );
                     for ( Double cSB : companySB )
                     {
                        final XSSFCell cellBodyCSB = rowBody.createCell( bodyColumnIndex );
                        cellBodyCSB.setCellValue( cSB );
                        cellBodyCSB.setCellStyle( cellStyleRight_dataFormat );
                        bodyColumnIndex++;
                     }
                     // �籣��Ŀ,���ˣ�eg. ����8%��ʧҵ0.5%��ҽ��2%���󲡣�С��2
                     List< Double > personalSB = getPersonalSB( settlementDTO, sbItemIds );
                     for ( Double pSB : personalSB )
                     {
                        final XSSFCell cellBodyCSB = rowBody.createCell( bodyColumnIndex );
                        cellBodyCSB.setCellValue( pSB );
                        cellBodyCSB.setCellStyle( cellStyleRight_dataFormat );
                        bodyColumnIndex++;
                     }

                     // ס�������𣬻�������˾������
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
                     // ���У�ͳ�����ݳ���
                     bodyRowIndex++;
                     pageListHolderSize++;
                  }

               }
               // --------------------------------------------------------------------
               // bodytotalcolumn
               int bodyMinTotalColumnIndex = 0;
               final XSSFRow rowBodyTotalMin = sheet.createRow( bodyRowIndex );
               // С��
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, bodyMinTotalColumnIndex, bodyMinTotalColumnIndex + 2 ) );
               final XSSFCell cellBodyTotalMin_minSum = rowBodyTotalMin.createCell( bodyMinTotalColumnIndex );
               cellBodyTotalMin_minSum.setCellValue( "С��" );
               cellBodyTotalMin_minSum.setCellStyle( cellStyle_t3 );
               bodyMinTotalColumnIndex = bodyMinTotalColumnIndex + 3;

               // С��ֵ
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
               //  �л����ϼ���
               bodyRowIndex++;
               final XSSFRow rowBodyTotalMax = sheet.createRow( bodyRowIndex );
               int bodyMaxTotalColumnIndex = 0;
               // �ϼ�
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, bodyMaxTotalColumnIndex, bodyMaxTotalColumnIndex + 1 ) );
               final XSSFCell rowBodyTotalMax_companySum = rowBodyTotalMax.createCell( bodyMaxTotalColumnIndex );
               rowBodyTotalMax_companySum.setCellValue( "�ϼ�" );
               rowBodyTotalMax_companySum.setCellStyle( cellStyle_t3 );
               bodyMaxTotalColumnIndex = bodyMaxTotalColumnIndex + 2;
               // ��λӦ��
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, bodyMaxTotalColumnIndex, bodyMaxTotalColumnIndex + 1 ) );
               final XSSFCell rowBodyTotalMax_companyBillSum = rowBodyTotalMax.createCell( bodyMaxTotalColumnIndex );
               rowBodyTotalMax_companyBillSum.setCellValue( "��λӦ��" );
               rowBodyTotalMax_companyBillSum.setCellStyle( cellStyle_t3 );
               bodyMaxTotalColumnIndex = bodyMaxTotalColumnIndex + 2;
               //  ������ʾλ��
               bodyMaxTotalColumnIndex += TOTAL_COMMON_ITEM_LENGTH;
               // ��λӦ��ֵ
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, bodyMaxTotalColumnIndex, bodyMaxTotalColumnIndex + 1 ) );
               final XSSFCell cellBodyTotalMax_companyBillSumValue = rowBodyTotalMax.createCell( bodyMaxTotalColumnIndex );
               // ���п�Ŀ�ϼ�+С��1+������˾����
               int rowStart = bodyRowIndex - 1;
               int rowEnd = rowStart;
               int columnStart = 3;
               int columnEnd = 3 + TOTAL_COMMON_ITEM_LENGTH;
               Double allItemsMinSum = 0d;
               // û����ͨ��Ŀ
               if ( TOTAL_COMMON_ITEM_LENGTH == 0 )
               {
                  allItemsMinSum = 0d;
               }
               else
               {
                  allItemsMinSum = getSumMulitCellValues( sheet, rowStart, rowEnd, columnStart, columnEnd - 1 );
               }
               // С��1
               final Double totalMinCount1 = getCellValue( sheet, pageListHolderSize + 6, 3 + TOTAL_COMMON_ITEM_LENGTH + 1 + TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH );
               // ��˾ס��������
               Double item_housing_company = 0.00d;
               if ( isItem_housingExist )
               {
                  item_housing_company = getCellValue( sheet, pageListHolderSize + 6, 3 + TOTAL_COMMON_ITEM_LENGTH + 1 + ( TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH + 1 ) * 2
                        + TOTAL_ITEM_67_LENGTH - 2 );
               }
               cellBodyTotalMax_companyBillSumValue.setCellValue( allItemsMinSum + totalMinCount1 + item_housing_company );
               cellBodyTotalMax_companyBillSumValue.setCellStyle( cellStyle_gray );
               bodyMaxTotalColumnIndex = bodyMaxTotalColumnIndex + 2;
               // Ԫ/��
               final XSSFCell rowBodyTotalMax_yuan_month = rowBodyTotalMax.createCell( bodyMaxTotalColumnIndex );
               rowBodyTotalMax_yuan_month.setCellValue( "Ԫ/��" );
               rowBodyTotalMax_yuan_month.setCellStyle( cellStyleCenter );
               bodyMaxTotalColumnIndex = bodyMaxTotalColumnIndex + 2;
               // ����Ӧ��
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, bodyMaxTotalColumnIndex, bodyMaxTotalColumnIndex + 1 ) );
               final XSSFCell rowBodyTotalMax_personalBillSum = rowBodyTotalMax.createCell( bodyMaxTotalColumnIndex );
               rowBodyTotalMax_personalBillSum.setCellValue( "����Ӧ��" );
               rowBodyTotalMax_personalBillSum.setCellStyle( cellStyle_t3 );
               bodyMaxTotalColumnIndex = bodyMaxTotalColumnIndex + 2;
               // ����Ӧ��ֵ(С��2+ס�����������)
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, bodyMaxTotalColumnIndex, bodyMaxTotalColumnIndex + 1 ) );
               final XSSFCell cellBodyTotalMax_personalBillSumValue = rowBodyTotalMax.createCell( bodyMaxTotalColumnIndex );
               // С��2
               final Double totalMinCount2 = getCellValue( sheet, pageListHolderSize + 6, 3 + TOTAL_COMMON_ITEM_LENGTH + 1 + ( TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH + 1 ) * 2 - 1 );
               // ס�������� ����
               Double item_housing_personal = 0.00d;
               if ( isItem_housingExist )
               {
                  item_housing_personal = getCellValue( sheet, pageListHolderSize + 6, 3 + TOTAL_COMMON_ITEM_LENGTH + 1 + ( TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH + 1 ) * 2
                        + TOTAL_ITEM_67_LENGTH - 1 );
               }
               cellBodyTotalMax_personalBillSumValue.setCellValue( totalMinCount2 + item_housing_personal );
               cellBodyTotalMax_personalBillSumValue.setCellStyle( cellStyle_blue );
               bodyMaxTotalColumnIndex = bodyMaxTotalColumnIndex + 2;
               // Ԫ/��
               final XSSFCell rowBodyTotalMax_yuan_month2 = rowBodyTotalMax.createCell( bodyMaxTotalColumnIndex );
               rowBodyTotalMax_yuan_month2.setCellValue( "Ԫ/��" );
               rowBodyTotalMax_yuan_month2.setCellStyle( cellStyleCenter );
               bodyMaxTotalColumnIndex = bodyMaxTotalColumnIndex + 2;
               // ����
               bodyRowIndex++;
               //--------------------------------------------------------------------
               // ��βͳ��
               final XSSFRow row_description = sheet.createRow( bodyRowIndex );
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 0, 1 ) );
               final XSSFCell cell_description = row_description.createCell( 0 );
               cell_description.setCellValue( "��ע��" );
               cell_description.setCellStyle( cellStyle_t3 );
               bodyRowIndex++;
               // �����
               final XSSFRow row_mgtFee = sheet.createRow( bodyRowIndex );
               final XSSFCell cell_mgtFee = row_mgtFee.createCell( 1 );
               cell_mgtFee.setCellValue( "����ѣ�" );
               cell_mgtFee.setCellStyle( cellStyle_t3 );
               // �����ֵ    
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 2, 3 ) );
               final XSSFCell cell_mgtFee_value = row_mgtFee.createCell( 2 );
               final Double sumMgtFee = sumItemValue( pagedListHolder.getSource(), new String[] { "121", "122" } );
               cell_mgtFee_value.setCellValue( sumMgtFee );
               cell_mgtFee_value.setCellStyle( cellStyle_t3 );
               final XSSFCell cell_mgtFee_yuan = row_mgtFee.createCell( 4 );
               cell_mgtFee_yuan.setCellValue( "Ԫ" );
               cell_mgtFee_yuan.setCellStyle( cellStyle_t3 );
               bodyRowIndex++;
               // ��������
               final XSSFRow row_agentSalary = sheet.createRow( bodyRowIndex );
               final XSSFCell cell_agentSalary = row_agentSalary.createCell( 1 );
               cell_agentSalary.setCellValue( "�������ʣ�" );
               cell_agentSalary.setCellStyle( cellStyle_t3 );
               // ��������ֵ
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 2, 3 ) );
               final XSSFCell cell_agentSalary_value = row_agentSalary.createCell( 2 );
               final List< String > exceptItemTypes = new ArrayList< String >();
               // 7�籣 8�̱� 9���� 
               exceptItemTypes.add( "7" );
               exceptItemTypes.add( "8" );
               exceptItemTypes.add( "9" );
               final Double sumAgentSalary = sumExceptItemType( pagedListHolder.getSource(), exceptItemTypes, accountId );
               cell_agentSalary_value.setCellValue( sumAgentSalary );
               cell_agentSalary_value.setCellStyle( cellStyle_t3 );
               final XSSFCell cell_agentSalary_yuan = row_agentSalary.createCell( 4 );
               cell_agentSalary_yuan.setCellValue( "Ԫ" );
               cell_agentSalary_yuan.setCellStyle( cellStyle_t3 );
               bodyRowIndex++;
               // ����
               final XSSFRow row_personalCard = sheet.createRow( bodyRowIndex );
               final XSSFCell cell_personalCard = row_personalCard.createCell( 1 );
               cell_personalCard.setCellValue( "���񿨣�" );
               cell_personalCard.setCellStyle( cellStyle_t3 );
               // 
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 2, 3 ) );
               final XSSFCell cell_cell_personalCard_value = row_personalCard.createCell( 2 );
               cell_cell_personalCard_value.setCellValue( "" );
               cell_cell_personalCard_value.setCellStyle( cellStyle_t3 );
               bodyRowIndex++;
               // �籣
               final XSSFRow row_sb = sheet.createRow( bodyRowIndex );
               final XSSFCell cell_sb = row_sb.createCell( 1 );
               cell_sb.setCellValue( "�籣��" );
               cell_sb.setCellStyle( cellStyle_t3 );
               // �籣ֵ
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 2, 3 ) );
               final XSSFCell cell_sb_value = row_sb.createCell( 2 );
               //final Double sumSB = getCellValue( sheet, bodyRowIndex - 6, 2 + TOTAL_COMMON_ITEM_LENGTH + 1 + TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH );
               final Double sumSBCommon = getSumSBAllValue( pagedListHolder, accountId, true, isSBOnly );
               cell_sb_value.setCellValue( sumSBCommon );
               cell_sb_value.setCellStyle( cellStyle_t3 );
               final XSSFCell cell_sb_yuan = row_sb.createCell( 4 );
               cell_sb_yuan.setCellValue( "Ԫ" );
               cell_sb_yuan.setCellStyle( cellStyle_t3 );
               bodyRowIndex++;
               // �̱�
               final XSSFRow row_cb = sheet.createRow( bodyRowIndex );
               final XSSFCell cell_cb = row_cb.createCell( 1 );
               cell_cb.setCellValue( "�̱���" );
               cell_cb.setCellStyle( cellStyle_t3 );
               // �籣ֵ
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 2, 3 ) );
               final XSSFCell cell_cb_value = row_cb.createCell( 2 );
               //final Double sumSB = getCellValue( sheet, bodyRowIndex - 6, 2 + TOTAL_COMMON_ITEM_LENGTH + 1 + TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH );
               final Double sumCBCommon = getSumCBAllValue( pagedListHolder, accountId );
               cell_cb_value.setCellValue( sumCBCommon );
               cell_cb_value.setCellStyle( cellStyle_t3 );
               final XSSFCell cell_cb_yuan = row_sb.createCell( 4 );
               cell_cb_yuan.setCellValue( "Ԫ" );
               cell_cb_yuan.setCellStyle( cellStyle_t3 );
               bodyRowIndex++;
               // ������
               final XSSFRow row_housing = sheet.createRow( bodyRowIndex );
               final XSSFCell cell_housing = row_housing.createCell( 1 );
               cell_housing.setCellValue( "������" );
               cell_housing.setCellStyle( cellStyle_t3 );
               // ������ֵ
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 2, 3 ) );
               final XSSFCell cell_housing_value = row_housing.createCell( 2 );
               /*final Double sumHousing = !isItem_housingExist ? 0.00d : getCellValue( sheet, bodyRowIndex - 7, 2 + TOTAL_COMMON_ITEM_LENGTH + 1
                     + ( TOTAL_SB_ITEM_EXCEPT_HOUSING_LENGTH + 1 ) * 2 + 1 );*/
               final Double sumSBHousing = getSumSBAllValue( pagedListHolder, accountId, false, isSBOnly );
               cell_housing_value.setCellValue( sumSBHousing );
               cell_housing_value.setCellStyle( cellStyle_t3 );
               final XSSFCell cell_housing_yuan = row_housing.createCell( 4 );
               cell_housing_yuan.setCellValue( "Ԫ" );
               cell_housing_yuan.setCellStyle( cellStyle_t3 );
               bodyRowIndex++;
               // �ٿ�һ��
               bodyRowIndex++;
               // �ϼ�
               final XSSFRow row_amount = sheet.createRow( bodyRowIndex );
               final XSSFCell cell_amount = row_amount.createCell( 1 );
               cell_amount.setCellValue( "�ϼƣ�" );
               cell_amount.setCellStyle( cellStyle_t3 );
               // �ϼ�ֵ
               sheet.addMergedRegion( new CellRangeAddress( bodyRowIndex, bodyRowIndex, 2, 3 ) );
               final XSSFCell cell_amount_value = row_amount.createCell( 2 );

               cell_amount_value.setCellValue( sumMgtFee + sumAgentSalary + sumSBCommon + sumSBHousing );
               cell_amount_value.setCellStyle( cellStyle_t3 );
               final XSSFCell cell_amount_yuan = row_amount.createCell( 4 );
               cell_amount_yuan.setCellValue( "Ԫ" );
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
      // �ж��Ƿ��й��ʿ�Ŀ item = 1
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
    * @param isSBOnly  ֻ���籣��û�л������ʵ������
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
                        // ����ǲ�ѯ��ͨ�籣
                        if ( isCommonSB && "7".equals( itemVO.getItemType() ) && !"67".equals( orderDetailVO.getItemId() ) )
                        {
                           // ���ֻ���籣�����籣=��˾+����
                           // ��������ʣ���˾���ɸ��ˣ����籣=��˾+��˾�е�����
                           // ���������,��˾���е����ˣ����籣=��˾
                           final String tempC = KANUtil.filterEmpty( orderDetailVO.getSbBillAmountCompany() ) == null ? "0" : orderDetailVO.getSbBillAmountCompany();
                           String tempP = "0";
                           if ( isSBOnly )
                           {
                              tempP = KANUtil.filterEmpty( orderDetailVO.getSbCostAmountPersonal() ) == null ? "0" : orderDetailVO.getSbCostAmountPersonal();
                           }
                           else
                           {
                              // PersonalSBBurdenValues �����籣��˾���ɵĽ�һ���˿����ж���籣�������е��ǹ�˾�е����еĲ��ǡ�������service����Ͱѹ�˾�е�ͳ�Ƶĵ����ŵ�����ֶ�.����ȡ����sbCostAmountPersonal
                              tempP = KANUtil.filterEmpty( orderDetailVO.getPersonalSBBurdenValues() ) == null ? "0" : orderDetailVO.getPersonalSBBurdenValues();
                           }
                           if ( KANUtil.filterEmpty( orderDetailVO.getSbMonthly() ) == null )
                           {
                              // ���ɵ��籣
                              sum += Double.parseDouble( KANUtil.filterEmpty( orderDetailVO.getBillAmountCompany() ) == null ? "0" : orderDetailVO.getBillAmountCompany() )
                                    + Double.parseDouble( KANUtil.filterEmpty( orderDetailVO.getCostAmountPersonal() ) == null ? "0" : orderDetailVO.getCostAmountPersonal() );
                           }
                           else
                           {
                              sum += Double.parseDouble( tempC ) + Double.parseDouble( tempP );
                           }
                           break;
                        }

                        // ����ǲ�ѯ������
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
                              // ���ɵ��籣
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
                        // ������̱�
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

   // ��ȡ�籣������
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
                  // ��������籣��Ŀ
                  if ( !"7".equals( itemVO.getItemType() ) )
                  {
                     continue;
                  }
                  //�����ס��������
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
                  //����ҵ�
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
                     // ����ǲ鹫˾��
                     if ( isCompany )
                     {
                        rate = orderDetailVO.getBillRateCompany();
                     }
                     // �����
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

   // ��ȡ���е��籣��ĿID�������ڱ�����ͨ��Ŀ��
   private static List< String > getSBItemIdsBySBItemDetailVOList( List< ListDetailVO > sbItemDetailVOList )
   {
      List< String > sbItemIds = new ArrayList< String >();
      for ( ListDetailVO listDetailVO : sbItemDetailVOList )
      {
         sbItemIds.add( listDetailVO.getItemId() );
      }
      return sbItemIds;
   }

   //�жϿ�Ŀ�Ƿ���ڣ������ж�ס��������
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
    * @param rowStart ��ʼ��
    * @param rowEnd  ������
    * @param columnStart  ��ʼ��
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

   // 67 ס��������,��������˾����
   private static List< Double > getItemHousing( SettlementDTO settlementDTO )
   {
      List< Double > sbValues = new ArrayList< Double >();

      // �����ס��������
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

   // ����8% 61��ʧҵ0.5% 63��ҽ��2% 62���� 73��С��2
   // �ų� ס��������67
   private static List< Double > getPersonalSB( SettlementDTO settlementDTO, List< String > sbItemIds )
   {
      List< Double > sbValues = new ArrayList< Double >();
      Double sum = 0d;
      for ( String sbItem : sbItemIds )
      {
         // �����ס��������
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
         // ���û�е�ǰ��Ŀ
         if ( !flag )
         {
            sbValues.add( 0.00d );
         }
      }
      sbValues.add( sum );
      return sbValues;
   }

   // ��ȡ��˾������һ��ֵ
   // ����20% 61��ʧҵ1.5% 63������0.8% 65������0.5% 64��ҽ��9% 62��С��1
   // �ų� ס��������67
   private static List< Double > getCompanySB( SettlementDTO settlementDTO, List< String > sbItemIds )
   {
      List< Double > sbValues = new ArrayList< Double >();
      Double sum = 0d;
      for ( String sbItem : sbItemIds )
      {
         // �����ס��������
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
               // ����ǵ��µĴ��ɡ���ȡbillAmountCompany
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
         // ���û�е�ǰ��Ŀ
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
    * �����У����籣�е����ó���
    * (���Ϊ���������еĿ�Ŀ���ؼ�����һ��14�У�����2�и�����Ϣ)
    * 61 ����  ��63 ʧҵ��65 ������64 ���ˣ�62 ҽ�ƣ�73 ��;67ס��������
    * @param mergeListDetailVOs
    * @return
    */
   private static List< ListDetailVO > fixListDetailVOs( List< ListDetailVO > mergeListDetailVOs )
   {
      List< ItemVO > itemVOs = KANConstants.ITEM_VO;
      // ϵͳ�籣��Ŀ��ID
      List< String > sbItemIds = new ArrayList< String >();
      // ��ǰ���籣��ĿListDetailVOs
      List< ListDetailVO > resultSBListDetailVO = new ArrayList< ListDetailVO >();
      List< ListDetailVO > tempListDetailVO = new ArrayList< ListDetailVO >();
      for ( ItemVO tempVO : itemVOs )
      {
         if ( "7".equals( tempVO.getItemType() ) )
         {
            sbItemIds.add( tempVO.getItemId() );
         }
      }
      // ɾ��item_999
      for ( ListDetailVO tempVO : mergeListDetailVOs )
      {
         if ( !"999".equals( tempVO.getItemId() ) )
         {
            tempListDetailVO.add( tempVO );
         }
      }
      mergeListDetailVOs.clear();
      mergeListDetailVOs.addAll( tempListDetailVO );
      // ����籣
      for ( ListDetailVO tempVO : mergeListDetailVOs )
      {
         // ������籣��Ŀ
         if ( sbItemIds.contains( tempVO.getItemId() ) )
         {
            resultSBListDetailVO.add( tempVO );
         }
      }
      return resultSBListDetailVO;
   }

   private static String getEmployeeName( final HttpServletRequest request, final SettlementDTO settlementDTO ) throws KANException
   {
      // �б��ֶ���ʾ����
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
      // �б��ֶ���ʾ����
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

   // �ϲ��ӱ�DetailVO
   @SuppressWarnings("unchecked")
   private static List< ListDetailVO > mergeListDetailVO( final HttpServletRequest request, final List< ListDTO > listDTOs ) throws KANException
   {
      try
      {
         // ��ʼ��PagedListHolder
         final PagedListHolder pagedListHolder = ( PagedListHolder ) request.getAttribute( "pagedListHolder" );
         // ��ʼ�����п�Ŀ
         final List< ItemVO > items = ( List< ItemVO > ) ( ( PagedListHolder ) request.getAttribute( "pagedListHolder" ) ).getAdditionalObject();

         if ( items != null && items.size() > 0 )
         {
            return fetchListDetailVOByItemVO( items, listDTOs );
         }
         else
         {
            // ����PagedListHolder
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
      // ��ʼ��ListDetailVO List
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
      // ��ʼ��ListDetailVO List
      final List< ListDetailVO > listDetailVOs = new ArrayList< ListDetailVO >();

      for ( Object object : specialDTO.getDetailVOs() )
      {
         // ��ȡ��ĿID
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

         // ��ȡ��Ŀ����
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

   // �ڲ��� - ����columnIndex
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

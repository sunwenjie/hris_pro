package com.kan.hro.service.impl.biz.cb;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.poi.POIUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.cb.CBBillDao;
import com.kan.hro.dao.inf.biz.cb.CBDetailDao;
import com.kan.hro.domain.biz.cb.CBBillVO;
import com.kan.hro.service.inf.biz.cb.CBBillService;

public class CBBillServiceImpl extends ContextService implements CBBillService
{
   private CBDetailDao cbDetailDao;

   @Override
   public PagedListHolder getCBBillVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final CBBillDao cbBillDao = ( CBBillDao ) getDao();
      pagedListHolder.setHolderSize( cbBillDao.countCBBillVOsByCondition( ( CBBillVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( cbBillDao.getCBBillVOsByCondition( ( CBBillVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( cbBillDao.getCBBillVOsByCondition( ( CBBillVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public SXSSFWorkbook cbBillReport( final List< String > itemNameList, final PagedListHolder pagedListHolder, final CBBillVO cbBillVOPage, final HttpServletRequest request )
         throws KANException
   {
      final SXSSFWorkbook workbook = new SXSSFWorkbook( POIUtil.EXPORT_BATCH_SIZE );
      workbook.setCompressTempFiles( true );
      try
      {
         // ��������
         final Font font = workbook.createFont();
         font.setFontName( "Calibri" );
         font.setFontHeightInPoints( ( short ) 11 );

         // ������Ԫ����ʽ
         final CellStyle cellStyleLeft = workbook.createCellStyle();
         cellStyleLeft.setFont( font );
         cellStyleLeft.setAlignment( CellStyle.ALIGN_LEFT );

         // ������Ԫ����ʽ
         final CellStyle cellStyleCenter = workbook.createCellStyle();
         cellStyleCenter.setFont( font );
         cellStyleCenter.setAlignment( CellStyle.ALIGN_CENTER );

         // ������Ԫ����ʽ
         final CellStyle cellStyleRight = workbook.createCellStyle();
         cellStyleRight.setFont( font );
         cellStyleRight.setAlignment( CellStyle.ALIGN_RIGHT );

         // ������Ԫ����ʽ(��ɫ)
         final Font fontRed = workbook.createFont();
         fontRed.setFontName( "Calibri" );
         fontRed.setFontHeightInPoints( ( short ) 11 );
         fontRed.setColor( Font.COLOR_RED );
         final CellStyle cellStyleCenterRed = workbook.createCellStyle();
         cellStyleCenterRed.setFont( fontRed );
         cellStyleCenterRed.setAlignment( CellStyle.ALIGN_CENTER );

         // �������
         final Sheet sheet = workbook.createSheet( "�̱���" );
         // ���ñ��Ĭ���п��Ϊ15���ֽ�
         sheet.setDefaultColumnWidth( 15 );

         // ����Excel Header Row
         final Row rowHeader = sheet.createRow( 0 );
         final Row rowHeadertwo = sheet.createRow( 1 );

         final boolean isHeaderShow = itemNameList != null && itemNameList.size() > 0;
         final boolean isShowByRole = BaseAction.getRole( request, null ).equals( KANConstants.ROLE_CLIENT );
         int indexDetail = 12;
         // ���Ա�ʶHeader�����
         int headerColumnIndex = 0;
         String[] headerColumnName = { "�ͻ�ID", "�������", "�ͻ�����", "��ԱID", "��Ա����", "֤������", "����Э��ID", "����״̬ ", "�·�", "�̱�����", "�̱�״̬", "������", "��˾Ӫ�գ��ϼƣ�", "���ѳɱ����ϼƣ�", "״̬" };
         if ( isShowByRole )
         {
            headerColumnName = new String[] { "��ԱID", "��Ա����", "֤������", "����Э��ID", "����״̬ ", "�·�", "�̱�����", "�̱�״̬", "������", "��˾Ӫ�գ��ϼƣ�", "״̬" };
            indexDetail = 9;
         }
         List< String > headerColumnNames = new ArrayList< String >();
         for ( int i = 0; i < headerColumnName.length; i++ )
         {
            if ( i == indexDetail )
            {
               final Cell cell = rowHeader.createCell( headerColumnIndex );
               cell.setCellStyle( cellStyleCenter );
               cell.setCellValue( "��Ŀ����" );
               if ( isHeaderShow )
               {
                  sheet.addMergedRegion( new CellRangeAddress( 0, 0, headerColumnIndex, headerColumnIndex + itemNameList.size() - 1 ) );
                  for ( String headerName : itemNameList )
                  {
                     final Cell cellTwo = rowHeadertwo.createCell( headerColumnIndex );
                     cellTwo.setCellValue( headerName );
                     headerColumnIndex++;
                  }
               }
               else
               {
                  headerColumnIndex++;
               }
            }
            final Cell cell = rowHeader.createCell( headerColumnIndex );
            cell.setCellValue( headerColumnName[ i ] );
            cell.setCellStyle( cellStyleLeft );
            if ( isHeaderShow )
            {
               sheet.addMergedRegion( new CellRangeAddress( 0, 1, headerColumnIndex, headerColumnIndex ) );
            }
            headerColumnIndex++;
            headerColumnNames.add( headerColumnName[ i ] );
         }
         // ��������Excel Body
         if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
         {
            // ���Ա�ʶBody�����
            int bodyRowIndex = 2;

            // ������
            for ( Object object : pagedListHolder.getSource() )
            {
               CBBillVO cbBillVO = ( CBBillVO ) object;
               // ����Excel Body Row
               final Row rowBody = sheet.createRow( bodyRowIndex );

               // ���Ա�ʶBody�����
               int bodyColumnIndex = 0;
               if ( !isShowByRole )
               {
                  rowBody.createCell( bodyColumnIndex ).setCellValue( cbBillVO.getClientId() );
                  bodyColumnIndex++;
                  rowBody.createCell( bodyColumnIndex ).setCellValue( cbBillVO.getCbNumber() );
                  bodyColumnIndex++;
                  rowBody.createCell( bodyColumnIndex ).setCellValue( cbBillVO.getClientName() );
                  bodyColumnIndex++;
               }
               rowBody.createCell( bodyColumnIndex ).setCellValue( cbBillVO.getEmployeeId() );
               bodyColumnIndex++;
               rowBody.createCell( bodyColumnIndex ).setCellValue( cbBillVO.getEmployeeName() );
               bodyColumnIndex++;
               rowBody.createCell( bodyColumnIndex ).setCellValue( cbBillVO.getCertificateNumber() );
               bodyColumnIndex++;
               rowBody.createCell( bodyColumnIndex ).setCellValue( cbBillVO.getContractId() );
               bodyColumnIndex++;
               rowBody.createCell( bodyColumnIndex ).setCellValue( cbBillVO.decodeField( cbBillVO.getContractStatus(), cbBillVOPage.getContractStatuses() ) );
               bodyColumnIndex++;
               rowBody.createCell( bodyColumnIndex ).setCellValue( cbBillVO.getMonthly() );
               bodyColumnIndex++;
               rowBody.createCell( bodyColumnIndex ).setCellValue( cbBillVO.getEmployeeCBName() );
               bodyColumnIndex++;
               rowBody.createCell( bodyColumnIndex ).setCellValue( cbBillVO.decodeField( cbBillVO.getCbStatus(), cbBillVOPage.getCbStatuses() ) );
               bodyColumnIndex++;
               rowBody.createCell( bodyColumnIndex ).setCellValue( cbBillVO.getCbNumber() );
               bodyColumnIndex++;
               for ( Object detailObject : ( ( CBBillVO ) object ).getDetailList() )
               {
                  CBBillVO detail = ( CBBillVO ) detailObject;
                  Cell detailCell = rowBody.createCell( bodyColumnIndex );
                  detailCell.setCellStyle( cellStyleRight );
                  detailCell.setCellValue( detail.getAmountPurchaseCost() );
                  bodyColumnIndex++;
               }
               Cell costPriceCell = rowBody.createCell( bodyColumnIndex );
               costPriceCell.setCellStyle( cellStyleRight );
               costPriceCell.setCellValue( cbBillVO.getDecodeDetailAmountPurchaseCosts() );
               bodyColumnIndex++;
               if ( !isShowByRole )
               {
                  Cell costCell = rowBody.createCell( bodyColumnIndex );
                  costCell.setCellStyle( cellStyleRight );
                  costCell.setCellValue( cbBillVO.getDecodeDetailAmountPurchaseCosts() );
                  bodyColumnIndex++;
               }
               rowBody.createCell( bodyColumnIndex ).setCellValue( cbBillVO.decodeField( cbBillVO.getAdditionalStatus(), cbBillVOPage.getAdditionalStatuses() ) );
               bodyColumnIndex++;
               bodyRowIndex++;
            }
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
      return workbook;
   }

   @Override
   public List< Object > getCBBillDetailByHeaderIds( final List< String > list ) throws KANException
   {
      final CBBillDao cbBillDao = ( CBBillDao ) getDao();
      return cbBillDao.getCBBillDetailByHeaderIds( list );
   }

   public CBDetailDao getCbDetailDao()
   {
      return cbDetailDao;
   }

   public void setCbDetailDao( CBDetailDao cbDetailDao )
   {
      this.cbDetailDao = cbDetailDao;
   }

   @Override
   public List< Object > getCBBillDetailByHeaderId( String headerId ) throws KANException
   {
      final List< String > headerIds = new ArrayList< String >();
      headerIds.add( headerId );
      return this.getCBBillDetailByHeaderIds( headerIds );
   }
}

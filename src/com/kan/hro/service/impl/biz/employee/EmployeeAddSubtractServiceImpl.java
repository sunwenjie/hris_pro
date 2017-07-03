package com.kan.hro.service.impl.biz.employee;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.poi.POIUtil;
import com.kan.hro.dao.inf.biz.employee.EmployeeAddSubtractDao;
import com.kan.hro.domain.biz.employee.EmployeeAddSubtract;
import com.kan.hro.service.inf.biz.employee.EmployeeAddSubtractService;

public class EmployeeAddSubtractServiceImpl extends ContextService implements EmployeeAddSubtractService
{

   @Override
   public PagedListHolder getEmployeeAddSubtractsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final EmployeeAddSubtractDao employeeAddSubtractDao = ( EmployeeAddSubtractDao ) getDao();
      pagedListHolder.setHolderSize( employeeAddSubtractDao.countEmployeeAddSubtractsByCondition( ( EmployeeAddSubtract ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( employeeAddSubtractDao.getEmployeeAddSubtractsByCondition( ( EmployeeAddSubtract ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( employeeAddSubtractDao.getEmployeeAddSubtractsByCondition( ( EmployeeAddSubtract ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public SXSSFWorkbook employeeAddSubtractReport( PagedListHolder employeeAddSubtractHolder )
   {
      // ��ʼ��������
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
         final Sheet sheet = workbook.createSheet( "����Ա����" );
         // ���ñ��Ĭ���п��Ϊ15���ֽ�
         sheet.setDefaultColumnWidth( 15 );

         // ����Excel Header Row
         final Row rowHeader = sheet.createRow( 0 );

         // ���Ա�ʶHeader�����
         int headerColumnIndex = 0;
         String[] headerColumnName = {  "��ǲЭ����", "��ͬ��ʼʱ��", "��ͬ����ʱ��", "��ְ����", "��������", "�����������", "����ͣ��������",
               "����","Ա������", "�Ա�", "���֤��", "�Ͷ����Ͽ���", "��������", "������ַ", "����", "ѧ��", "ְλ", "�������", "�ͻ�����", "��ע" };
         List< String > headerColumnNames = new ArrayList< String >();
         for ( int i = 0; i < headerColumnName.length; i++ )
         {

            final Cell cell = rowHeader.createCell( headerColumnIndex );
            cell.setCellValue( headerColumnName[ i ] );
            cell.setCellStyle( cellStyleLeft );
            headerColumnIndex++;
            headerColumnNames.add( headerColumnName[ i ] );

         }

         // ��������Excel Body
         if ( employeeAddSubtractHolder.getSource() != null && employeeAddSubtractHolder.getSource().size() > 0 )
         {
            // ���Ա�ʶBody�����
            int bodyRowIndex = 1;

            EmployeeAddSubtract employeeAddSubtractObject = ( EmployeeAddSubtract ) employeeAddSubtractHolder.getObject();

            // ������
            for ( Object object : employeeAddSubtractHolder.getSource() )
            {
               EmployeeAddSubtract employeeAddSubtract = ( EmployeeAddSubtract ) object;
               // ����Excel Body Row
               final Row rowBody = sheet.createRow( bodyRowIndex );

               // ���Ա�ʶBody�����
               int bodyColumnIndex = 0;
               
               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getContractId() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getStartDate() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getEndDate() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getResignDate() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getSbName() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getPlanStartDate() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getPlanEndDate() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getBase() );
               bodyColumnIndex++;


               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getEmployeeName() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtractObject.decodeField( employeeAddSubtract.getSalutation(), employeeAddSubtractObject.getSalutations() ) );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getCertificateNumber() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getSbNumber() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getDecodeResidencyType() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getResidencyAddress() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getCityNameZH() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtractObject.decodeField( employeeAddSubtract.getHighestEducation(), employeeAddSubtractObject.getHighestEducations() ) );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getAdditionalPosition() );
               bodyColumnIndex++;

              
               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getNumber() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getClientName() );
               bodyColumnIndex++;

               rowBody.createCell( bodyColumnIndex ).setCellValue( employeeAddSubtract.getDescription() );
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

}

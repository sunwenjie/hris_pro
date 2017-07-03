package com.kan.hro.web.actions.biz.payment;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.MoneyToChinese;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.payment.IncomeTaxYearView;
import com.kan.hro.service.inf.biz.payment.IncomeTaxYearViewService;

public class IncomeTaxYearViewAction extends BaseAction
{
   public static String ACCESSACTION = "JAVA_OBJECT_INCOME_TAX_YEAR";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ��ʼ��Service�ӿ�
         final IncomeTaxYearViewService incomeTaxYearViewService = ( IncomeTaxYearViewService ) getService( "incomeTaxYearViewService" );
         // ���Action Form
         final IncomeTaxYearView incomeTaxYearView = ( IncomeTaxYearView ) form;

         //��������Ȩ��
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), ACCESSACTION, incomeTaxYearView );
            setDataAuth( request, response, incomeTaxYearView );
         }

         if ( incomeTaxYearView.getTaxAmountPersonalMin() != null && incomeTaxYearView.getTaxAmountPersonalMin().equals( "" ) )
         {
            incomeTaxYearView.setTaxAmountPersonalMin( "0" );
         }
         
         // ����form
         decodedObject( incomeTaxYearView );

         // Ĭ�ϲ�ѯ��ǰ���
         if ( KANUtil.filterEmpty( incomeTaxYearView.getYear() ) == null )
         {
            incomeTaxYearView.setYear( KANUtil.getYear( new Date() ) );
         }

         String frontTaxAmountPersonalMin = incomeTaxYearView.getTaxAmountPersonalMin();
         String frontTaxAmountPersonalMax = incomeTaxYearView.getTaxAmountPersonalMax();

         // ��˰������
         if ( KANUtil.filterEmpty( incomeTaxYearView.getTaxAmountPersonalMin() ) == null )
         {
            frontTaxAmountPersonalMin = "120000";
            incomeTaxYearView.setTaxAmountPersonalMin( frontTaxAmountPersonalMin );
         }

         // ��˰������
         if ( KANUtil.filterEmpty( incomeTaxYearView.getTaxAmountPersonalMax() ) == null )
         {
            incomeTaxYearView.setTaxAmountPersonalMax( String.valueOf( Integer.MAX_VALUE ) );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder incomeTaxYearViewHolder = new PagedListHolder();
         // ���뵱ǰҳ
         incomeTaxYearViewHolder.setPage( page );
         // ���뵱ǰֵ����
         incomeTaxYearViewHolder.setObject( incomeTaxYearView );
         // ����ҳ���¼����
         incomeTaxYearViewHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         incomeTaxYearViewService.getIncomeTaxYearViewsByCondition( incomeTaxYearViewHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( incomeTaxYearViewHolder, request );

         // Holder��д��Request����
         request.setAttribute( "incomeTaxYearViewHolder", incomeTaxYearViewHolder );

         incomeTaxYearView.setTaxAmountPersonalMin( frontTaxAmountPersonalMin.equals( "0" ) ? "" : frontTaxAmountPersonalMin );
         incomeTaxYearView.setTaxAmountPersonalMax( frontTaxAmountPersonalMax );

         // Ajax���ã�ֱ�Ӵ�ֵ��table jspҳ��
         if ( new Boolean( getAjax( request ) ) )
         {
            request.setAttribute( "role", BaseAction.getRole( request, null ) );
            return mapping.findForward( "listIncomeTaxYearViewTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listIncomeTaxYearView" );
   }

   // ����excel
   public ActionForward genreate_excel( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡcontractId
         final String contractId = request.getParameter( "contractId" );

         // ��ȡyear
         final String year = request.getParameter( "year" );

         // ��ʼ��Service
         final IncomeTaxYearViewService incomeTaxYearViewService = ( IncomeTaxYearViewService ) getService( "incomeTaxYearViewService" );

         // ��ȡActionForm
         IncomeTaxYearView incomeTaxYearView = ( IncomeTaxYearView ) form;
         incomeTaxYearView.setYear( year );
         incomeTaxYearView.setContractId( contractId );

         // ��ȡIncomeTaxYearView
         incomeTaxYearView = incomeTaxYearViewService.getIncomeTaxYearViewByCondition( incomeTaxYearView );
         incomeTaxYearView.reset( null, request );

         // ��ʼ��excel��
         String excelName = incomeTaxYearView.getEmployeeNameZH() + "�ĸ�˰�걨����ȣ�.xls";

         // ��ȡ��˰�걨�걨��ģ��·��
         final String path = KANUtil.basePath + "/12w.xls";

         // ����File
         final File file = new File( path );

         // ����ģ��·��
         if ( file.exists() )
         {
            // ��ʼ��������
            final HSSFWorkbook workbook = new HSSFWorkbook( new FileInputStream( file ) );

            // ��ȡ������
            final HSSFSheet sheet = workbook.getSheetAt( 0 );

            // ������
            for ( int i = 1; i < sheet.getPhysicalNumberOfRows(); i++ )
            {
               // ��ȡ���϶˵�Ԫ��
               final HSSFRow row = sheet.getRow( i );

               // �в�Ϊ��
               if ( row != null )
               {
                  // ��ȡ��Excel�ļ��е����е���
                  int cells = row.getPhysicalNumberOfCells();

                  // ������
                  for ( int j = 0; j < cells; j++ )
                  {
                     // ��ȡ���е�ֵ
                     HSSFCell cell = row.getCell( j );
                     if ( cell != null )
                     {
                        String field = "";
                        String value = "";
                        String cellValue = "";
                        if ( cell.getCellType() == Cell.CELL_TYPE_NUMERIC )
                        {
                           cellValue = String.valueOf( cell.getNumericCellValue() );
                        }
                        else if ( cell.getCellType() == Cell.CELL_TYPE_STRING )
                        {
                           cellValue = cell.getStringCellValue();
                        }

                        // ���֤�պ���
                        if ( i == 4 && j == 7 )
                        {
                           cellValue = "${certificateNumber}";
                        }

                        // ��ϵ��ַ�ʱ�
                        if ( i == 6 && j == 7 )
                        {
                           cellValue = "${personalPostcode}";
                        }

                        if ( !cellValue.equals( "" ) && cellValue.contains( "$" ) )
                        {
                           field = KANUtil.getSubString( cellValue, "{", "}" );

                           // �����Ҫת�����Ľ��${chineseMoney}        
                           if ( field.equals( "chineseMoney" ) )
                           {
                              value = ( String ) KANUtil.getValue( incomeTaxYearView, "beforeTaxSalary" );
                              if ( KANUtil.filterEmpty( value ) != null )
                              {
                                 value = MoneyToChinese.getMoneyString( Double.valueOf( value ) );
                              }
                           }
                           else
                           {
                              value = ( String ) KANUtil.getValue( incomeTaxYearView, field );
                           }

                           // ��������֤��||�ʱ����
                           if ( field.equals( "certificateNumber" ) || field.equals( "personalPostcode" ) )
                           {
                              if ( KANUtil.filterEmpty( value ) != null )
                              {
                                 for ( int k = 0; k < value.length(); k++ )
                                 {
                                    j++;
                                    row.getCell( j ).setCellValue( String.valueOf( value.charAt( k ) ) );
                                 }
                              }

                              continue;
                           }

                           cell.setCellValue( cellValue.replace( "${" + field + "}", value == null ? "" : value ) );
                        }
                     }
                  }
               }
            }

            // ���ͻ��˴����ļ���
            // ��ʼ��OutputStream
            final OutputStream os = response.getOutputStream();

            // ���÷����ļ�����
            response.setContentType( "application/x-msdownload" );

            // ����ļ���������������
            response.setHeader( "Content-Disposition", "attachment;filename=" + new String( URLDecoder.decode( excelName, "UTF-8" ).getBytes(), "iso-8859-1" ) );

            // Excel�ļ�д��OutputStream
            workbook.write( os );

            // ���OutputStream
            os.flush();
            //�ر���  
            os.close();
         }

      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   public static void main( String[] args )
   {
      String s = "320382198808175519";

      for ( int i = 0; i < s.length(); i++ )
      {
         System.out.println( String.valueOf( s.charAt( i ) ) );
      }
   }
}
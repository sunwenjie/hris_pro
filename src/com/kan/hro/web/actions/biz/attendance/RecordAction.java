package com.kan.hro.web.actions.biz.attendance;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.RecordVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.attendance.RecordService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

public class RecordAction extends BaseAction
{

   // accessAction
   public final static String ACCESS_ACTION = "HRO_BIZ_ATTENDANCE_RECORD";

   public final static String[] RECORD_COLUMN = new String[] { "��Ա���", "�ǼǺ���", "ˢ������", "ˢ��ʱ��", "ǩ����ʽ", "�豸���", "��ע" };

   public final static String[] RECORD_COLUMN_NUM = new String[] { "0", "1", "4", "5", "6", "7", "11" };

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final RecordService recordService = ( RecordService ) getService( "recordService" );
         // ���Action Form
         final RecordVO recordVO = ( RecordVO ) form;

         // ����subAction
         dealSubAction( recordVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder recordHolder = new PagedListHolder();
         // ���뵱ǰҳ
         recordHolder.setPage( page );
         // ���뵱ǰֵ����
         recordHolder.setObject( recordVO );
         // ����ҳ���¼����
         recordHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         recordService.getRecordVOsByCondition( recordHolder, true );

         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( recordHolder, request );
         // Holder��д��Request����
         request.setAttribute( "recordHolder", recordHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Item JSP
            request.setAttribute( "role", getRole( request, null ) );
            return mapping.findForward( "listRecordTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listRecord" );
   }

   // ����ģ��
   public void downloadTemplate( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡ��·��
         final String path = KANUtil.basePath + "/signRecord.xlsx";

         // ����File
         final File file = new File( path );

         // ����ģ��·��
         if ( file.exists() )
         {
            // ��ʼ��������
            final XSSFWorkbook workbook = new XSSFWorkbook( new FileInputStream( file ) );

            // ��ʼ��OutputStream
            final OutputStream os = response.getOutputStream();

            // ���÷����ļ�����
            response.setContentType( "application/x-msdownload" );

            // ����ļ���������������
            response.setHeader( "Content-Disposition", "attachment;filename=" + new String( URLDecoder.decode( "�򿨼�¼�ǼǱ�.xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) );

            // Excel�ļ�д��OutputStream
            workbook.write( os );

            // ���OutputStream
            os.flush();
            //�ر���  
            os.close();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // �ϴ��ļ�
   public void uploadFile( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service
         final RecordService recordService = ( RecordService ) getService( "recordService" );
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );

         // ��ȡ��·��
         final String path = KANUtil.basePath + "/signRecord.xlsx";

         // ����File
         final File file = new File( path );

         final List< Object > records = new ArrayList< Object >();

         // ����ģ��·��
         if ( file.exists() )
         {
            // ��ʼ��������
            final XSSFWorkbook workbook = new XSSFWorkbook( new FileInputStream( file ) );

            final XSSFSheet sheet = workbook.getSheetAt( 0 );

            // ������
            for ( int i = 1; i < sheet.getPhysicalNumberOfRows(); i++ )
            {
               final XSSFRow row = sheet.getRow( i );

               final RecordVO recordVO = new RecordVO();

               // �в�Ϊ��
               if ( row != null )
               {
                  // ��ȡ��Excel�ļ��е����е���
                  int cells = row.getPhysicalNumberOfCells();

                  // ������
                  for ( int j = 0; j < cells; j++ )
                  {
                     // ��ȡ���е�ֵ
                     final XSSFCell cell = row.getCell( j );
                     final String cellValue = cell.getStringCellValue();
                     // { "0", "1", "4", "5", "6", "7", "11" };
                     if ( j == 0 )
                     {
                        final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( cellValue );
                        if ( employeeVO != null )
                        {
                           recordVO.setEmployeeNameZH( employeeVO.getNameZH() );
                           recordVO.setEmployeeNameEN( employeeVO.getNameEN() );
                        }
                        recordVO.setEmployeeId( cellValue );
                     }
                     else if ( j == 1 )
                     {
                        recordVO.setEmployeeNo( cellValue );
                     }
                     else if ( j == 4 )
                     {
                        recordVO.setSignDate( cellValue );
                     }
                     else if ( j == 5 )
                     {
                        recordVO.setSignTime( cellValue );
                     }
                     else if ( j == 6 )
                     {
                        recordVO.setSignType( cellValue );
                     }
                     else if ( j == 7 )
                     {
                        recordVO.setMachineNo( cellValue );
                     }
                     else if ( j == 11 )
                     {
                        recordVO.setDescription( cellValue );
                     }
                  }

                  recordVO.setStatus( "1" );
                  recordVO.setCreateBy( getUserId( request, null ) );
                  recordVO.setCreateDate( new Date() );
                  recordVO.setModifyBy( getUserId( request, null ) );
                  recordVO.setModifyDate( new Date() );
               }

               records.add( recordVO );
            }
         }

         recordService.insertRecordVOs( records );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

}

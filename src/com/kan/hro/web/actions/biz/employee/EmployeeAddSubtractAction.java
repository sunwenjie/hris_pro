package com.kan.hro.web.actions.biz.employee;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeAddSubtract;
import com.kan.hro.service.inf.biz.employee.EmployeeAddSubtractService;

public class EmployeeAddSubtractAction extends BaseAction
{
   // ��ǰAction��Ӧ��Access Action
   public static final String accessAction = "EMPLOYEE_ADDSUBTRACT_REPORTS";

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
         final EmployeeAddSubtractService employeeAddSubtractService = ( EmployeeAddSubtractService ) getService( "employeeAddSubtractService" );

         // ���Action Form
         final EmployeeAddSubtract employeeAddSubtract = ( EmployeeAddSubtract ) form;

         //Ĭ�ϲ�����Ա
         if ( org.apache.commons.lang3.StringUtils.isBlank( employeeAddSubtract.getOpType() ) )
         {
            employeeAddSubtract.setOpType( "1" );
         }
         //Ĭ�ϲ���Ա������Э��
         if ( org.apache.commons.lang3.StringUtils.isBlank( employeeAddSubtract.getType() ) )
         {
            employeeAddSubtract.setType( "5" );
         }

         // ���û��ָ��������Ĭ�ϰ� batchId����
         if ( employeeAddSubtract.getSortColumn() == null || employeeAddSubtract.getSortColumn().isEmpty() )
         {
            employeeAddSubtract.setSortOrder( "desc" );
            employeeAddSubtract.setSortColumn( "a.contractId" );
         }
         
         String nowDate= new SimpleDateFormat( "yyyy/MM" ).format( new Date() );
         
         request.setAttribute( "nowDate", nowDate );
         
         if ( employeeAddSubtract.getMonth() == null  )
         {
            employeeAddSubtract.setMonth( nowDate );
         }

         // ����subAction
         dealSubAction( employeeAddSubtract, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeAddSubtract );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeAddSubtractService.getEmployeeAddSubtractsByCondition( pagedListHolder, true );

         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "employeeAddSubtractHolder", pagedListHolder );

         // Ajax���ã�ֱ�ӷ���tableҳ��
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listEmployeeAddSubtractTable" );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listEmployeeAddSubtract" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }

   /**
    * ��������ģ��
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException 
    */
   public ActionForward exportReport( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeAddSubtractService employeeAddSubtractService = ( EmployeeAddSubtractService ) getService( "employeeAddSubtractService" );

         // ��ʼ��PagedListHolder
         final PagedListHolder employeeAddSubtractHolder = new PagedListHolder();

         // ��ʼ�����������ѯ����
         final EmployeeAddSubtract employeeAddSubtract = ( EmployeeAddSubtract ) form;
         
         // ����
         decodedObject( employeeAddSubtract );

         if ( null == employeeAddSubtract.getOpType() )
         {
            employeeAddSubtract.setOpType( "1" );
         }

         if ( null == employeeAddSubtract.getType() )
         {
            employeeAddSubtract.setType( "5" );
         }

         employeeAddSubtractHolder.setObject( employeeAddSubtract );
         employeeAddSubtractService.getEmployeeAddSubtractsByCondition( employeeAddSubtractHolder, false );
         employeeAddSubtract.reset( mapping, request );
         if ( employeeAddSubtractHolder.getSource() != null )
         {
            // ˢ�¹��ʻ�
            refreshHolder( employeeAddSubtractHolder, request );
            final SXSSFWorkbook workbook = employeeAddSubtractService.employeeAddSubtractReport( employeeAddSubtractHolder );
            // ��ʼ��OutputStream
            final OutputStream os = response.getOutputStream();

            // ���÷����ļ�����
            response.setContentType( "application/x-msdownload" );

            // ����ļ���������������
            response.setHeader( "Content-Disposition", "attachment;filename=" + new String( URLDecoder.decode( "����Ա��.xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) );

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
      return mapping.findForward( "" );

   }
}

/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeLogVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeLogService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**
 * @author Kevin Jin
 */

public class EmployeeLogAction extends BaseAction
{

   public static final String accessAction = "HRO_BIZ_EMPLOYEE_LOG";

   /**
    * List employeeLog
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );
         // ��ʼ��Service�ӿ�
         final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
         // ���Action Form
         final EmployeeLogVO employeeLogVO = ( EmployeeLogVO ) form;

         dealSubAction( employeeLogVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeLogVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeLogService.getEmployeeLogVOsByCondition( pagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // ��ת���б����
         return dealReturn( accessAction, "listEmployeeLog", mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   /**
    * to_objectNew
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      final String employeeId = KANUtil.decodeString( request.getParameter( "employeeId" ) );
      final EmployeeLogVO employeeLogVO = ( EmployeeLogVO ) form;
      employeeLogVO.setEmployeeId( employeeId );

      // ���ع�Ա����
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
      employeeLogVO.setEmployeeName( employeeVO.getName() );

      // ����Ĭ�Ϲ���״̬Ϊ��ְ
      employeeLogVO.setStatus( "1" );

      // ����Sub Action
      employeeLogVO.setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageEmployeeLog" );
   }

   /**
    * to_objectModify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
         // ��õ�ǰ����
         String id = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( KANUtil.filterEmpty( id ) == null )
         {
            id = ( ( EmployeeLogVO ) form ).getEmployeeLogId();
         }

         // ���������Ӧ����
         final EmployeeLogVO employeeLogVO = employeeLogService.getEmployeeLogVOByEmployeeLogId( id );

         //���ع�Ա����
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeLogVO.getEmployeeId() );
         employeeLogVO.setEmployeeName( employeeVO.getName() );

         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         employeeLogVO.reset( null, request );

         employeeLogVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeLogForm", employeeLogVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageEmployeeLog" );
   }

   /**
    * Add employeeLog
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
            // ���ActionForm
            final EmployeeLogVO employeeLogVO = ( EmployeeLogVO ) form;

            employeeLogVO.setAccountId( getAccountId( request, response ) );
            employeeLogVO.setCreateBy( getUserId( request, response ) );
            employeeLogVO.setModifyBy( getUserId( request, response ) );
            employeeLogVO.setType( "1" );
            employeeLogVO.setStatus( "1" );

            // �½�����
            employeeLogService.insertEmployeeLog( employeeLogVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_ADD );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeLog
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
            // ��õ�ǰ����
            String employeeLogId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final EmployeeLogVO employeeLogVO = employeeLogService.getEmployeeLogVOByEmployeeLogId( employeeLogId );
            // ��ȡ��¼�û�
            employeeLogVO.update( ( EmployeeLogVO ) form );
            employeeLogVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            employeeLogService.updateEmployeeLog( employeeLogVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }
         ( ( EmployeeLogVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete employeeLog
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
         final EmployeeLogVO employeeLogVO = new EmployeeLogVO();
         // ��õ�ǰ����
         String employeeLogId = request.getParameter( "employeeLogId" );

         // ɾ��������Ӧ����
         employeeLogVO.setEmployeeLogId( KANUtil.decodeStringFromAjax( employeeLogId ) );
         employeeLogVO.setModifyBy( getUserId( request, response ) );
         employeeLogService.deleteEmployeeLog( employeeLogVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeLog list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );
         // ���Action Form
         EmployeeLogVO employeeLogVO = ( EmployeeLogVO ) form;
         // ����ѡ�е�ID
         if ( employeeLogVO.getSelectedIds() != null && !employeeLogVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeLogVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeeLogVO.setEmployeeLogId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeLogVO.setModifyBy( getUserId( request, response ) );
               employeeLogService.deleteEmployeeLog( employeeLogVO );
            }
         }
         // ���Selected IDs����Action
         employeeLogVO.setSelectedIds( "" );
         employeeLogVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Log by Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Siuvan at 2014-11-20
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeLogService employeeLogService = ( EmployeeLogService ) getService( "employeeLogService" );

         // ��õ�ǰ����
         final String employeeLogId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeLogId" ) );

         // ��ʼ��EmployeeLogVO
         final EmployeeLogVO employeeLogVO = employeeLogService.getEmployeeLogVOByEmployeeLogId( employeeLogId );
         employeeLogVO.setModifyBy( getUserId( request, response ) );

         // ����ɾ���ӿ�
         final long rows = employeeLogService.deleteEmployeeLog( employeeLogVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
         }
         else
         {
            deleteFailedAjax( out, null );
         }

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

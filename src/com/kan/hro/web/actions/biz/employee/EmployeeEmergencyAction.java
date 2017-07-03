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
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeEmergencyVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeEmergencyService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�EmployeeEmergencyAction  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-10-8 ����01:46:52  
* �޸��ˣ�Jixiang  
* �޸�ʱ�䣺2013-10-8 ����01:46:52  
* �޸ı�ע��  
* @version   
*   
*/
public class EmployeeEmergencyAction extends BaseAction
{
   public final static String accessAction = "HRO_BIZ_EMPLOYEE_EMERGENCY";

   /**
    * List employeeEmergency
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
         final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );
         // ���Action Form
         final EmployeeEmergencyVO employeeEmergencyVO = ( EmployeeEmergencyVO ) form;

         dealSubAction( employeeEmergencyVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // ���뵱ǰҳ
         pagedListHolder.setPage( page );

         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeEmergencyVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( getPageSize( request, accessAction ) );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeEmergencyService.getEmployeeEmergencyVOsByCondition( pagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // ��ת���б����
         return dealReturn( accessAction, "listEmployeeEmergency", mapping, form, request, response );
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
      EmployeeEmergencyVO employeeEmergencyVO = ( EmployeeEmergencyVO ) form;
      employeeEmergencyVO.setEmployeeId( employeeId );
      // ����Sub Action
      employeeEmergencyVO.setSubAction( CREATE_OBJECT );

      //���ع�Ա����
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeEmergencyVO.getEmployeeId() );
      employeeEmergencyVO.setEmployeeName( employeeVO.getName() );

      // ��ת���½�����
      return mapping.findForward( "manageEmployeeEmergency" );
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
         final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );
         // ��õ�ǰ����
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( id == null || "".equals( id ) )
         {
            id = ( ( EmployeeEmergencyVO ) form ).getEmployeeEmergencyId();
         }
         // ���������Ӧ����
         final EmployeeEmergencyVO employeeEmergencyVO = employeeEmergencyService.getEmployeeEmergencyVOByEmployeeEmergencyId( id );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         employeeEmergencyVO.reset( null, request );

         //���ع�Ա����
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeEmergencyVO.getEmployeeId() );
         employeeEmergencyVO.setEmployeeName( employeeVO.getName() );

         employeeEmergencyVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeEmergencyForm", employeeEmergencyVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageEmployeeEmergency" );
   }

   /**
    * Add employeeEmergency
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
            final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );
            // ���ActionForm
            final EmployeeEmergencyVO employeeEmergencyVO = ( EmployeeEmergencyVO ) form;

            employeeEmergencyVO.setAccountId( getAccountId( request, response ) );
            employeeEmergencyVO.setCreateBy( getUserId( request, response ) );
            employeeEmergencyVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column###
            employeeEmergencyVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_EMERGENCY" ) );

            // �½�����
            employeeEmergencyService.insertEmployeeEmergency( employeeEmergencyVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeEmergencyVO, Operate.ADD, employeeEmergencyVO.getEmployeeEmergencyId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeEmergency
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
            final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );
            // ��õ�ǰ����
            String employeeEmergencyId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final EmployeeEmergencyVO employeeEmergencyVO = employeeEmergencyService.getEmployeeEmergencyVOByEmployeeEmergencyId( employeeEmergencyId );

            // ��ȡ��¼�û�
            employeeEmergencyVO.update( ( EmployeeEmergencyVO ) form );

            employeeEmergencyVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            employeeEmergencyVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_EMERGENCY" ) );

            // �޸Ķ���
            employeeEmergencyService.updateEmployeeEmergency( employeeEmergencyVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeEmergencyVO, Operate.MODIFY, employeeEmergencyVO.getEmployeeEmergencyId(), null );
         }
         ( ( EmployeeEmergencyVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete employeeEmergency
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
         final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );
         final EmployeeEmergencyVO employeeEmergencyVO = new EmployeeEmergencyVO();
         // ��õ�ǰ����
         String employeeEmergencyId = request.getParameter( "employeeEmergencyId" );

         // ɾ��������Ӧ����
         employeeEmergencyVO.setEmployeeEmergencyId( KANUtil.decodeStringFromAjax( employeeEmergencyId ) );
         employeeEmergencyVO.setModifyBy( getUserId( request, response ) );
         employeeEmergencyService.deleteEmployeeEmergency( employeeEmergencyVO );
         insertlog( request, employeeEmergencyVO, Operate.DELETE, employeeEmergencyId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeEmergency list
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
         final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );
         // ���Action Form
         EmployeeEmergencyVO employeeEmergencyVO = ( EmployeeEmergencyVO ) form;
         // ����ѡ�е�ID
         if ( employeeEmergencyVO.getSelectedIds() != null && !employeeEmergencyVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeEmergencyVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeeEmergencyVO.setEmployeeEmergencyId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeEmergencyVO.setModifyBy( getUserId( request, response ) );
               employeeEmergencyService.deleteEmployeeEmergency( employeeEmergencyVO );
            }

            insertlog( request, employeeEmergencyVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeEmergencyVO.getSelectedIds() ) );
         }
         // ���Selected IDs����Action
         employeeEmergencyVO.setSelectedIds( "" );
         employeeEmergencyVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Emergency by Ajax
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
         final EmployeeEmergencyService employeeEmergencyService = ( EmployeeEmergencyService ) getService( "employeeEmergencyService" );

         // ��õ�ǰ����
         final String employeeEmergencyId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeEmergencyId" ) );

         // ��ʼ��EmployeeEmergencyVO
         final EmployeeEmergencyVO employeeEmergencyVO = employeeEmergencyService.getEmployeeEmergencyVOByEmployeeEmergencyId( employeeEmergencyId );
         employeeEmergencyVO.setModifyBy( getUserId( request, response ) );

         // ����ɾ���ӿ�
         final long rows = employeeEmergencyService.deleteEmployeeEmergency( employeeEmergencyVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeEmergencyVO, Operate.DELETE, employeeEmergencyId, "delete_object_ajax" );
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

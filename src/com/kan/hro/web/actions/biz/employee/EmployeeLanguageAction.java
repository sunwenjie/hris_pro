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
import com.kan.hro.domain.biz.employee.EmployeeLanguageVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeLanguageService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�EmployeeLanguageAction  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-9-18 ����05:08:49  
* �޸��ˣ�Jixiang  
* �޸�ʱ�䣺2013-9-18 ����05:08:49  
* �޸ı�ע��  
* @version   
*   
*/
public class EmployeeLanguageAction extends BaseAction
{

   public static final String accessAction = "HRO_BIZ_EMPLOYEE_LANGUAGE";

   /**
    * List employeeLanguage
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
         final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );
         // ���Action Form
         final EmployeeLanguageVO employeeLanguageVO = ( EmployeeLanguageVO ) form;

         dealSubAction( employeeLanguageVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // ���뵱ǰҳ
         pagedListHolder.setPage( page );

         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeLanguageVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( getPageSize( request, accessAction ) );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeLanguageService.getEmployeeLanguageVOsByCondition( pagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // ��ת���б����
         return dealReturn( accessAction, "listEmployeeLanguage", mapping, form, request, response );
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
      EmployeeLanguageVO employeeLanguageVO = ( EmployeeLanguageVO ) form;
      employeeLanguageVO.setEmployeeId( employeeId );
      // ����Sub Action
      employeeLanguageVO.setSubAction( CREATE_OBJECT );

      //���ع�Ա����
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
      employeeLanguageVO.setEmployeeName( employeeVO.getName() );

      // ��ת���½�����
      return mapping.findForward( "manageEmployeeLanguage" );
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
         final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );

         // ��õ�ǰ����
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeLanguageVO ) form ).getEmployeeLanguageId();
         }
         // ���������Ӧ����
         final EmployeeLanguageVO employeeLanguageVO = employeeLanguageService.getEmployeeLanguageVOByEmployeeLanguageId( id );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         employeeLanguageVO.reset( null, request );

         //���ع�Ա����
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeLanguageVO.getEmployeeId() );
         employeeLanguageVO.setEmployeeName( employeeVO.getName() );

         employeeLanguageVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeLanguageForm", employeeLanguageVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageEmployeeLanguage" );
   }

   /**
    * Add employeeLanguage
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
            final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );
            // ���ActionForm
            final EmployeeLanguageVO employeeLanguageVO = ( EmployeeLanguageVO ) form;

            employeeLanguageVO.setAccountId( getAccountId( request, response ) );
            employeeLanguageVO.setCreateBy( getUserId( request, response ) );
            employeeLanguageVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column###
            employeeLanguageVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_LANGUAGE" ) );

            // �½�����
            employeeLanguageService.insertEmployeeLanguage( employeeLanguageVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeLanguageVO, Operate.ADD, employeeLanguageVO.getEmployeeLanguageId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeLanguage
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
            final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );
            // ��õ�ǰ����
            String employeeLanguageId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final EmployeeLanguageVO employeeLanguageVO = employeeLanguageService.getEmployeeLanguageVOByEmployeeLanguageId( employeeLanguageId );

            // ��ȡ��¼�û�
            employeeLanguageVO.update( ( EmployeeLanguageVO ) form );

            employeeLanguageVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            employeeLanguageVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_LANGUAGE" ) );

            // �޸Ķ���
            employeeLanguageService.updateEmployeeLanguage( employeeLanguageVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeLanguageVO, Operate.MODIFY, employeeLanguageVO.getEmployeeLanguageId(), null );
         }
         // ���Form����
         ( ( EmployeeLanguageVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete employeeLanguage
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
         final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );
         final EmployeeLanguageVO employeeLanguageVO = new EmployeeLanguageVO();
         // ��õ�ǰ����
         String employeeLanguageId = request.getParameter( "employeeLanguageId" );

         // ɾ��������Ӧ����
         employeeLanguageVO.setEmployeeLanguageId( KANUtil.decodeStringFromAjax( employeeLanguageId ) );
         employeeLanguageVO.setModifyBy( getUserId( request, response ) );
         employeeLanguageService.deleteEmployeeLanguage( employeeLanguageVO );
         insertlog( request, employeeLanguageVO, Operate.DELETE, employeeLanguageId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeLanguage list
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
         final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );
         // ���Action Form
         EmployeeLanguageVO employeeLanguageVO = ( EmployeeLanguageVO ) form;
         // ����ѡ�е�ID
         if ( employeeLanguageVO.getSelectedIds() != null && !employeeLanguageVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeLanguageVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeeLanguageVO.setEmployeeLanguageId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeLanguageVO.setModifyBy( getUserId( request, response ) );
               employeeLanguageService.deleteEmployeeLanguage( employeeLanguageVO );
            }

            insertlog( request, employeeLanguageVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeLanguageVO.getSelectedIds() ) );
         }
         // ���Selected IDs����Action
         employeeLanguageVO.setSelectedIds( "" );
         employeeLanguageVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Language by Ajax
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
         final EmployeeLanguageService employeeLanguageService = ( EmployeeLanguageService ) getService( "employeeLanguageService" );

         // ��õ�ǰ����
         final String employeeLanguageId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeLanguageId" ) );

         // ��ʼ��EmployeeLanguageVO
         final EmployeeLanguageVO employeeLanguageVO = employeeLanguageService.getEmployeeLanguageVOByEmployeeLanguageId( employeeLanguageId );
         employeeLanguageVO.setModifyBy( getUserId( request, response ) );

         // ����ɾ���ӿ�
         final long rows = employeeLanguageService.deleteEmployeeLanguage( employeeLanguageVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeLanguageVO, Operate.DELETE, employeeLanguageId, null );
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

/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;

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
import com.kan.hro.domain.biz.employee.EmployeeSkillVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.service.inf.biz.employee.EmployeeSkillService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�EmployeeSkillAction  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-9-18 ����05:08:49  
* �޸��ˣ�Jixiang  
* �޸�ʱ�䣺2013-9-18 ����05:08:49  
* �޸ı�ע��  
* @version   
*   
*/
public class EmployeeSkillAction extends BaseAction
{

   public static final String accessAction = "HRO_BIZ_EMPLOYEE_SKILL";

   /**
    * List employeeSkill
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
         final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );
         // ���Action Form
         final EmployeeSkillVO employeeSkillVO = ( EmployeeSkillVO ) form;

         dealSubAction( employeeSkillVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // ���뵱ǰҳ
         pagedListHolder.setPage( page );

         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeSkillVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeSkillService.getEmployeeSkillVOsByCondition( pagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // ��ת���б����
         return dealReturn( accessAction, "listEmployeeSkill", mapping, form, request, response );
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
      EmployeeSkillVO employeeSkillVO = ( EmployeeSkillVO ) form;
      employeeSkillVO.setEmployeeId( employeeId );
      // ����Sub Action
      employeeSkillVO.setSubAction( CREATE_OBJECT );

      //���ع�Ա����
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
      employeeSkillVO.setEmployeeName( employeeVO.getName() );

      // ��ת���½�����
      return mapping.findForward( "manageEmployeeSkill" );
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
         final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );
         // ��õ�ǰ����
         final String id = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // ���������Ӧ����
         final EmployeeSkillVO employeeSkillVO = employeeSkillService.getEmployeeSkillVOByEmployeeSkillId( id );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         employeeSkillVO.reset( null, request );

         //���ع�Ա����
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeSkillVO.getEmployeeId() );
         employeeSkillVO.setEmployeeName( employeeVO.getName() );

         employeeSkillVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeSkillForm", employeeSkillVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageEmployeeSkill" );
   }

   /**
    * Add employeeSkill
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
            final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );
            // ���ActionForm
            final EmployeeSkillVO employeeSkillVO = ( EmployeeSkillVO ) form;

            employeeSkillVO.setAccountId( getAccountId( request, response ) );
            employeeSkillVO.setCreateBy( getUserId( request, response ) );
            employeeSkillVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column###
            employeeSkillVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_SKILL" ) );

            // �½�����
            employeeSkillService.insertEmployeeSkill( employeeSkillVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_ADD );
            insertlog( request, employeeSkillVO, Operate.ADD, employeeSkillVO.getEmployeeSkillId(), null );
            final String employeeId = request.getParameter( "employeeId" );
            final String id = URLEncoder.encode( Cryptogram.encodeString( employeeId ), "UTF-8" );

            // ��ת����Ա��Ϣ�޸�ҳ��
            response.sendRedirect( request.getContextPath() + "/employeeAction.do?proc=to_objectModify&id=" + id );
         }
         // ���Form����
         ( ( EmployeeSkillVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      //return list_object( mapping, form, request, response );
      return null;
   }

   /**
    * Modify employeeSkill
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
            final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );
            // ��õ�ǰ����
            String employeeSkillId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final EmployeeSkillVO employeeSkillVO = employeeSkillService.getEmployeeSkillVOByEmployeeSkillId( employeeSkillId );

            // ��ȡ��¼�û�
            employeeSkillVO.update( ( EmployeeSkillVO ) form );

            employeeSkillVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            employeeSkillVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_SKILL" ) );

            // �޸Ķ���
            employeeSkillService.updateEmployeeSkill( employeeSkillVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeSkillVO, Operate.MODIFY, employeeSkillVO.getEmployeeSkillId(), null );
         }
         // ���Form����
         ( ( EmployeeSkillVO ) form ).reset();
         final String employeeId = request.getParameter( "employeeId" );
         final String id = URLEncoder.encode( Cryptogram.encodeString( employeeId ), "UTF-8" );
         // ��ת����Ա��Ϣ�޸�ҳ��
         response.sendRedirect( request.getContextPath() + "/employeeAction.do?proc=to_objectModify&id=" + id );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      //return list_object( mapping, form, request, response );
      return null;
   }

   /**
    * Delete employeeSkill
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
         final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );
         final EmployeeSkillVO employeeSkillVO = new EmployeeSkillVO();
         // ��õ�ǰ����
         String employeeSkillId = request.getParameter( "employeeSkillId" );

         // ɾ��������Ӧ����
         employeeSkillVO.setEmployeeSkillId( KANUtil.decodeStringFromAjax( employeeSkillId ) );
         employeeSkillVO.setModifyBy( getUserId( request, response ) );
         employeeSkillService.deleteEmployeeSkill( employeeSkillVO );
         insertlog( request, employeeSkillVO, Operate.DELETE, employeeSkillId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeSkill list
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
         final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );
         // ���Action Form
         EmployeeSkillVO employeeSkillVO = ( EmployeeSkillVO ) form;
         // ����ѡ�е�ID
         if ( employeeSkillVO.getSelectedIds() != null && !employeeSkillVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeSkillVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeeSkillVO.setEmployeeSkillId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeSkillVO.setModifyBy( getUserId( request, response ) );
               employeeSkillService.deleteEmployeeSkill( employeeSkillVO );
            }

            insertlog( request, employeeSkillVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeSkillVO.getSelectedIds() ) );
         }
         // ���Selected IDs����Action
         employeeSkillVO.setSelectedIds( "" );
         employeeSkillVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Skill by Ajax
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
         final EmployeeSkillService employeeSkillService = ( EmployeeSkillService ) getService( "employeeSkillService" );

         // ��õ�ǰ����
         final String employeeSkillId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeSkillId" ) );

         // ��ʼ��EmployeeSkillVO
         final EmployeeSkillVO employeeSkillVO = employeeSkillService.getEmployeeSkillVOByEmployeeSkillId( employeeSkillId );
         employeeSkillVO.setModifyBy( getUserId( request, response ) );

         // ����ɾ���ӿ�
         final long rows = employeeSkillService.deleteEmployeeSkill( employeeSkillVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeSkillVO, Operate.DELETE, employeeSkillId, "delete_object_ajax" );
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

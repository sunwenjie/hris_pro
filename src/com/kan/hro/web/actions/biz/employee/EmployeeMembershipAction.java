/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeMembershipVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeMembershipService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�EmployeeMembershipAction  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-9-18 ����05:08:07  
* �޸��ˣ�Jixiang  
* �޸�ʱ�䣺2013-9-18 ����05:08:07  
* �޸ı�ע��  
* @version   
*   
*/
public class EmployeeMembershipAction extends BaseAction
{
   public static final String accessAction = "HRO_BIZ_EMPLOYEE_MEMBERSHIP";

   /**
    * List employeeMembership
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
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
         // ���Action Form
         final EmployeeMembershipVO employeeMembershipVO = ( EmployeeMembershipVO ) form;

         dealSubAction( employeeMembershipVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // ���뵱ǰҳ
         pagedListHolder.setPage( page );

         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeMembershipVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( getPageSize( request, accessAction ) );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeMembershipService.getEmployeeMembershipVOsByCondition( pagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // ��ת���б����
         return dealReturn( accessAction, "listEmployeeMembership", mapping, form, request, response );
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
      EmployeeMembershipVO employeeMembershipVO = ( EmployeeMembershipVO ) form;
      employeeMembershipVO.setEmployeeId( employeeId );
      // ����Sub Action
      ( ( EmployeeMembershipVO ) form ).setSubAction( CREATE_OBJECT );

      //���ع�Ա����
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeMembershipVO.getEmployeeId() );
      employeeMembershipVO.setEmployeeName( employeeVO.getName() );

      // ��ת���½�����
      return mapping.findForward( "manageEmployeeMembership" );
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
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
         // ��õ�ǰ����
         String id = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeMembershipVO ) form ).getEmployeeMembershipId();
         }
         // ���������Ӧ����
         final EmployeeMembershipVO employeeMembershipVO = employeeMembershipService.getEmployeeMembershipVOByEmployeeMembershipId( id );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         employeeMembershipVO.reset( null, request );

         //���ع�Ա����
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeMembershipVO.getEmployeeId() );
         employeeMembershipVO.setEmployeeName( employeeVO.getName() );

         employeeMembershipVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeMembershipForm", employeeMembershipVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageEmployeeMembership" );
   }

   /**
    * Add employeeMembership
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
            final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
            // ���ActionForm
            final EmployeeMembershipVO employeeMembershipVO = ( EmployeeMembershipVO ) form;

            employeeMembershipVO.setAccountId( getAccountId( request, response ) );
            employeeMembershipVO.setCreateBy( getUserId( request, response ) );
            employeeMembershipVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column###
            employeeMembershipVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_MEMBERSHIP" ) );

            // �½�����
            employeeMembershipService.insertEmployeeMembership( employeeMembershipVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_ADD );
            insertlog( request, employeeMembershipVO, Operate.ADD, employeeMembershipVO.getEmployeeMembershipId(), null );
         }
         ( ( EmployeeMembershipVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeMembership
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
            final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
            // ��õ�ǰ����
            String employeeMembershipId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final EmployeeMembershipVO employeeMembershipVO = employeeMembershipService.getEmployeeMembershipVOByEmployeeMembershipId( employeeMembershipId );

            // ��ȡ��¼�û�
            employeeMembershipVO.update( ( EmployeeMembershipVO ) form );

            employeeMembershipVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            employeeMembershipVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_MEMBERSHIP" ) );

            // �޸Ķ���
            employeeMembershipService.updateEmployeeMembership( employeeMembershipVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeMembershipVO, Operate.MODIFY, employeeMembershipVO.getEmployeeMembershipId(), null );
         }
         // ���Form����
         ( ( EmployeeMembershipVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete employeeMembership
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
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
         final EmployeeMembershipVO employeeMembershipVO = new EmployeeMembershipVO();
         // ��õ�ǰ����
         String employeeMembershipId = request.getParameter( "employeeMembershipId" );

         // ɾ��������Ӧ����
         employeeMembershipVO.setEmployeeMembershipId( KANUtil.decodeStringFromAjax( employeeMembershipId ) );
         employeeMembershipVO.setModifyBy( getUserId( request, response ) );
         employeeMembershipService.deleteEmployeeMembership( employeeMembershipVO );
         insertlog( request, employeeMembershipVO, Operate.DELETE, employeeMembershipId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeMembership list
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
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
         // ���Action Form
         EmployeeMembershipVO employeeMembershipVO = ( EmployeeMembershipVO ) form;
         // ����ѡ�е�ID
         if ( employeeMembershipVO.getSelectedIds() != null && !employeeMembershipVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeMembershipVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeeMembershipVO.setEmployeeMembershipId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeMembershipVO.setModifyBy( getUserId( request, response ) );
               employeeMembershipService.deleteEmployeeMembership( employeeMembershipVO );
            }

            insertlog( request, employeeMembershipVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeMembershipVO.getSelectedIds() ) );
         }
         // ���Selected IDs����Action
         employeeMembershipVO.setSelectedIds( "" );
         employeeMembershipVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Membership by Ajax
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
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );

         // ��õ�ǰ����
         final String employeeMembershipId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeMembershipId" ) );

         // ��ʼ��EmployeeMembershipVO
         final EmployeeMembershipVO employeeMembershipVO = employeeMembershipService.getEmployeeMembershipVOByEmployeeMembershipId( employeeMembershipId );
         employeeMembershipVO.setModifyBy( getUserId( request, response ) );

         // ����ɾ���ӿ�
         final long rows = employeeMembershipService.deleteEmployeeMembership( employeeMembershipVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeMembershipVO, Operate.DELETE, employeeMembershipId, "delete_object_ajax" );
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

   /**
    * ��֤����Ƿ����
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward is_employeeMembership_exist( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final String employeeId = request.getParameter( "employeeId" );
         //��ʼ���ӿ�
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
         //��ȡ��ǰ��Ա���������
         List< Object > employeeMembershipVOs = employeeMembershipService.getEmployeeMembershipVOsByEmployeeId( employeeId );
         //��ȡ��ǰѡ������
         final String membershipId = request.getParameter( "membershipId" );
         boolean flag = false;
         //�жϵ�ǰѡ�������Ƿ�����ڵ�ǰ��Ա���еĻ
         EmployeeMembershipVO m = null;
         EmployeeMembershipVO theExistEmployeeMembershipVO = null;
         for ( Object o : employeeMembershipVOs )
         {
            m = ( EmployeeMembershipVO ) o;
            if ( m.getMembershipId().equals( membershipId ) )
            {//�������
               theExistEmployeeMembershipVO = m;
               flag = true;
            }
         }

         if ( flag )
         {
            out.print( theExistEmployeeMembershipVO.encodedField( theExistEmployeeMembershipVO.getEmployeeMembershipId() ) );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   /**
    * ��ȡ�Ѵ��ڵ���������ڵĻ����ɫ���
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward get_exist_employeeMemberships( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         //��ʼ���ӿ�
         final EmployeeMembershipService employeeMembershipService = ( EmployeeMembershipService ) getService( "employeeMembershipService" );
         //��ȡ��Աid
         final String employeeId = request.getParameter( "employeeId" );
         //��ȡ���е����
         final List< MappingVO > membershipMappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getMemberships( request.getLocale().getLanguage() );
         //��ȡ��ǰ��Ա���������
         List< Object > employeeMembershipVOs = employeeMembershipService.getEmployeeMembershipVOsByEmployeeId( employeeId );

         MappingVO mappingVO = null;
         EmployeeMembershipVO employeeMembershipVO = null;
         List< MappingVO > existMappingVO = new ArrayList< MappingVO >();
         for ( Object o : membershipMappingVOs )
         {
            mappingVO = ( MappingVO ) o;
            for ( Object emo : employeeMembershipVOs )
            {
               employeeMembershipVO = ( EmployeeMembershipVO ) emo;
               //�������
               if ( employeeMembershipVO.getMembershipId().equals( mappingVO.getMappingId() ) )
               {
                  existMappingVO.add( mappingVO );
               }
            }
         }

         StringBuffer result = new StringBuffer();
         //�����û��������
         for ( MappingVO m : existMappingVO )
         {
            result.append( m.getMappingId() ).append( ":" ).append( m.getMappingValue() ).append( "##" );
         }
         out.print( result.length() >= 2 ? result.toString().substring( 0, result.length() - 2 ) : "" );
         existMappingVO = null;

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

}

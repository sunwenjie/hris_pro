/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeEducationVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeEducationService;
import com.kan.hro.service.inf.biz.employee.EmployeeService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�EmployeeEducationAction  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-9-18 ����05:05:52  
* �޸��ˣ�Jixiang  
* �޸�ʱ�䣺2013-9-18 ����05:05:52  
* �޸ı�ע��  
* @version   
*   
*/
public class EmployeeEducationAction extends BaseAction
{

   public static final String accessAction = "HRO_BIZ_EMPLOYEE_EDUCATION";

   /**
    * List employeeEducation
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
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );
         // ���Action Form
         final EmployeeEducationVO employeeEducationVO = ( EmployeeEducationVO ) form;

         dealSubAction( accessAction, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // ���뵱ǰҳ
         pagedListHolder.setPage( page );

         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeEducationVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( getPageSize( request, accessAction ) );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeEducationService.getEmployeeEducationVOsByCondition( pagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // ��ת���б����
         return dealReturn( accessAction, "listEmployeeEducation", mapping, form, request, response );
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
      EmployeeEducationVO employeeEducationVO = ( EmployeeEducationVO ) form;
      employeeEducationVO.setEmployeeId( employeeId );

      //���ع�Ա����
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeEducationVO.getEmployeeId() );
      employeeEducationVO.setEmployeeName( employeeVO.getName() );

      // ����Sub Action
      employeeEducationVO.setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageEmployeeEducation" );
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
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );
         // ��õ�ǰ����
         String id = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeEducationVO ) form ).getEmployeeEducationId();
         }
         // ���������Ӧ����
         final EmployeeEducationVO employeeEducationVO = employeeEducationService.getEmployeeEducationVOByEmployeeEducationId( id );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         employeeEducationVO.reset( null, request );

         //���ع�Ա����
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeEducationVO.getEmployeeId() );
         employeeEducationVO.setEmployeeName( employeeVO.getName() );

         employeeEducationVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeEducationForm", employeeEducationVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageEmployeeEducation" );
   }

   /**
    * Add employeeEducation
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
            final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
            // ���ActionForm
            final EmployeeEducationVO employeeEducationVO = ( EmployeeEducationVO ) form;

            employeeEducationVO.setAccountId( getAccountId( request, response ) );
            employeeEducationVO.setCreateBy( getUserId( request, response ) );
            employeeEducationVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column###
            employeeEducationVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_EDUCATION" ) );

            // �½�����
            employeeEducationService.insertEmployeeEducation( employeeEducationVO );

            List< Object > listEducation = employeeEducationService.getEmployeeEducationVOsByEmployeeId( employeeEducationVO.getEmployeeId() );
            if ( listEducation != null && listEducation.size() != 0 )
            {
               EmployeeEducationVO educationVO = ( EmployeeEducationVO ) listEducation.get( 0 );
               EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeEducationVO.getEmployeeId() );
               employeeVO.setHighestEducation( educationVO.getEducationId() );
               employeeVO.setGraduationDate( educationVO.getEndDate() );
               employeeService.updateEmployee( employeeVO );
            }
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_ADD );
            insertlog( request, employeeEducationVO, Operate.ADD, employeeEducationVO.getEmployeeEducationId(), null );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeEducation
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
            final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );
            // ��õ�ǰ����
            String employeeEducationId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final EmployeeEducationVO employeeEducationVO = employeeEducationService.getEmployeeEducationVOByEmployeeEducationId( employeeEducationId );
            final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
            // ��ȡ��¼�û�
            employeeEducationVO.update( ( EmployeeEducationVO ) form );

            employeeEducationVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            employeeEducationVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_EDUCATION" ) );

            // �޸Ķ���
            employeeEducationService.updateEmployeeEducation( employeeEducationVO );

            List< Object > listEducation = employeeEducationService.getEmployeeEducationVOsByEmployeeId( employeeEducationVO.getEmployeeId() );
            if ( listEducation != null && listEducation.size() != 0 )
            {
               EmployeeEducationVO educationVO = ( EmployeeEducationVO ) listEducation.get( 0 );
               EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeEducationVO.getEmployeeId() );
               employeeVO.setHighestEducation( educationVO.getEducationId() );
               employeeVO.setGraduationDate( educationVO.getEndDate() );
               employeeService.updateEmployee( employeeVO );
            }

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
            insertlog( request, employeeEducationVO, Operate.MODIFY, employeeEducationVO.getEmployeeEducationId(), null );
         }
         ( ( EmployeeEducationVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete employeeEducation
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
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );
         final EmployeeEducationVO employeeEducationVO = new EmployeeEducationVO();
         // ��õ�ǰ����
         String employeeEducationId = request.getParameter( "employeeEducationId" );

         // ɾ��������Ӧ����
         employeeEducationVO.setEmployeeEducationId( KANUtil.decodeStringFromAjax( employeeEducationId ) );
         employeeEducationVO.setModifyBy( getUserId( request, response ) );
         employeeEducationService.deleteEmployeeEducation( employeeEducationVO );
         insertlog( request, employeeEducationVO, Operate.DELETE, employeeEducationId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeEducation list
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
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );
         // ���Action Form
         EmployeeEducationVO employeeEducationVO = ( EmployeeEducationVO ) form;
         // ����ѡ�е�ID
         if ( employeeEducationVO.getSelectedIds() != null && !employeeEducationVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeEducationVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeeEducationVO.setEmployeeEducationId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeEducationVO.setModifyBy( getUserId( request, response ) );
               employeeEducationService.deleteEmployeeEducation( employeeEducationVO );
            }

            insertlog( request, employeeEducationVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeEducationVO.getSelectedIds() ) );
         }
         // ���Selected IDs����Action
         employeeEducationVO.setSelectedIds( "" );
         employeeEducationVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Education by Ajax
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
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );

         // ��õ�ǰ����
         final String employeeEducationId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeEducationId" ) );

         // ��ʼ��EmployeeEducationVO
         final EmployeeEducationVO employeeEducationVO = employeeEducationService.getEmployeeEducationVOByEmployeeEducationId( employeeEducationId );
         employeeEducationVO.setModifyBy( getUserId( request, response ) );

         // ����ɾ���ӿ�
         final long rows = employeeEducationService.deleteEmployeeEducation( employeeEducationVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeEducationVO, Operate.DELETE, employeeEducationId, "delete_object_ajax" );
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

   public ActionForward list_schoolName_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���Ҫ���������  �����ǹ̶��ġ�q�� ����URL���ܹ�
         final String q = URLDecoder.decode( request.getParameter( "q" ), "UTF-8" );

         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );

         // ��ʼ�� JSONArray ###
         final JSONArray array = new JSONArray();

         array.addAll( employeeEducationService.getSchoolNameBySchoolName( q ) );

         // Send to client
         out.println( array.toString() );
         System.out.println( "response:" + array.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "" );
   }

   public ActionForward list_major_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���Ҫ���������  �����ǹ̶��ġ�q�� ����URL���ܹ�
         final String q = URLDecoder.decode( request.getParameter( "q" ), "UTF-8" );

         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final EmployeeEducationService employeeEducationService = ( EmployeeEducationService ) getService( "employeeEducationService" );

         // ��ʼ�� JSONArray ###
         final JSONArray array = new JSONArray();

         array.addAll( employeeEducationService.getMajorByMajor( q ) );

         // Send to client
         out.println( array.toString() );
         System.out.println( "response:" + array.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "" );
   }

}

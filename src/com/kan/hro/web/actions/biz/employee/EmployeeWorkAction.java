/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.net.URLDecoder;

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
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.employee.EmployeeWorkVO;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.service.inf.biz.employee.EmployeeWorkService;

/**
 * @author Kevin Jin
 */

public class EmployeeWorkAction extends BaseAction
{

   public static final String accessAction = "HRO_BIZ_EMPLOYEE_WORK";

   /**
    * List employeeWork
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
         final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );
         // ���Action Form
         final EmployeeWorkVO employeeWorkVO = ( EmployeeWorkVO ) form;

         dealSubAction( employeeWorkVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // ���뵱ǰҳ
         pagedListHolder.setPage( page );

         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeWorkVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( getPageSize( request, accessAction ) );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeWorkService.getEmployeeWorkVOsByCondition( pagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );

         // ��ת���б����
         return dealReturn( accessAction, "listEmployeeWork", mapping, form, request, response );
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
      EmployeeWorkVO employeeWorkVO = ( EmployeeWorkVO ) form;
      employeeWorkVO.setEmployeeId( employeeId );

      //���ع�Ա����
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );
      employeeWorkVO.setEmployeeName( employeeVO.getName() );

      // ����Ĭ�Ϲ���״̬Ϊ��ְ
      employeeWorkVO.setStatus( "3" );

      // ����Sub Action
      employeeWorkVO.setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageEmployeeWork" );
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
         final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );
         // ��õ�ǰ����
         String id = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( KANUtil.filterEmpty( id ) == null )
         {
            id = ( ( EmployeeWorkVO ) form ).getEmployeeWorkId();
         }

         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeWorkVO ) form ).getEmployeeWorkId();
         }
         // ���������Ӧ����
         final EmployeeWorkVO employeeWorkVO = employeeWorkService.getEmployeeWorkVOByEmployeeWorkId( id );

         //���ع�Ա����
         final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
         final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeWorkVO.getEmployeeId() );
         employeeWorkVO.setEmployeeName( employeeVO.getName() );

         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         employeeWorkVO.reset( null, request );

         employeeWorkVO.setSubAction( VIEW_OBJECT );
         request.setAttribute( "employeeWorkForm", employeeWorkVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageEmployeeWork" );
   }

   /**
    * Add employeeWork
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
            final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );
            // ���ActionForm
            final EmployeeWorkVO employeeWorkVO = ( EmployeeWorkVO ) form;

            employeeWorkVO.setAccountId( getAccountId( request, response ) );
            employeeWorkVO.setCreateBy( getUserId( request, response ) );
            employeeWorkVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column###
            employeeWorkVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_WORK" ) );

            // �½�����
            employeeWorkService.insertEmployeeWork( employeeWorkVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeWorkVO, Operate.ADD, employeeWorkVO.getEmployeeWorkId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeWork
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
            final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );
            // ��õ�ǰ����
            String employeeWorkId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final EmployeeWorkVO employeeWorkVO = employeeWorkService.getEmployeeWorkVOByEmployeeWorkId( employeeWorkId );

            // ��ȡ��¼�û�
            employeeWorkVO.update( ( EmployeeWorkVO ) form );

            employeeWorkVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            employeeWorkVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_WORK" ) );

            // �޸Ķ���
            employeeWorkService.updateEmployeeWork( employeeWorkVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeWorkVO, Operate.MODIFY, employeeWorkVO.getEmployeeWorkId(), null );
         }
         ( ( EmployeeWorkVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete employeeWork
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
         final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );
         final EmployeeWorkVO employeeWorkVO = new EmployeeWorkVO();
         // ��õ�ǰ����
         String employeeWorkId = request.getParameter( "employeeWorkId" );

         // ɾ��������Ӧ����
         employeeWorkVO.setEmployeeWorkId( KANUtil.decodeStringFromAjax( employeeWorkId ) );
         employeeWorkVO.setModifyBy( getUserId( request, response ) );
         employeeWorkService.deleteEmployeeWork( employeeWorkVO );
         insertlog( request, employeeWorkVO, Operate.DELETE, employeeWorkVO.getEmployeeWorkId(), null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeWork list
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
         final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );
         // ���Action Form
         EmployeeWorkVO employeeWorkVO = ( EmployeeWorkVO ) form;
         // ����ѡ�е�ID
         if ( employeeWorkVO.getSelectedIds() != null && !employeeWorkVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeWorkVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeeWorkVO.setEmployeeWorkId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeWorkVO.setModifyBy( getUserId( request, response ) );
               employeeWorkService.deleteEmployeeWork( employeeWorkVO );
            }

            insertlog( request, employeeWorkVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeWorkVO.getSelectedIds() ) );
         }
         // ���Selected IDs����Action
         employeeWorkVO.setSelectedIds( "" );
         employeeWorkVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Work by Ajax
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
         final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );

         // ��õ�ǰ����
         final String employeeWorkId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeWorkId" ) );

         // ��ʼ��EmployeeWorkVO
         final EmployeeWorkVO employeeWorkVO = employeeWorkService.getEmployeeWorkVOByEmployeeWorkId( employeeWorkId );
         employeeWorkVO.setModifyBy( getUserId( request, response ) );

         // ����ɾ���ӿ�
         final long rows = employeeWorkService.deleteEmployeeWork( employeeWorkVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeWorkVO, Operate.DELETE, employeeWorkId, "delete_object_ajax" );
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

   public ActionForward list_companyName_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
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
         final EmployeeWorkService employeeWorkService = ( EmployeeWorkService ) getService( "employeeWorkService" );

         // ��ʼ�� JSONArray ###
         final JSONArray array = new JSONArray();

         array.addAll( employeeWorkService.getCompanyNameByName( q ) );

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

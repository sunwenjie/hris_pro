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
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.employee.EmployeeContractOTVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOTService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�EmployeeContractOTAction  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-8-23 ����11:01:46  
*   
*/
public class EmployeeContractOTAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_OT";

   /**
    * List employeeContractOT
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
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );
         // ���Action Form
         final EmployeeContractOTVO employeeContractOTVO = ( EmployeeContractOTVO ) form;

         // ����Զ�����������######
         // employeeContractOTVO.setRemark1( generateDefineListSearches( request, "HRO_BIZ_EMPLOYEE_CONTRACT_OT" ) );

         // �����SubAction��Ϊ��
         if ( employeeContractOTVO.getSubAction() != null && !employeeContractOTVO.getSubAction().trim().equals( "" ) )
         {
            // �����SubAction��ɾ���б����
            if ( employeeContractOTVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
            {
               // ����ɾ���б��SubAction
               delete_objectList( mapping, form, request, response );
            }
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( employeeContractOTVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // ���뵱ǰҳ
         pagedListHolder.setPage( page );

         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeContractOTVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeContractOTService.getEmployeeContractOTVOsByCondition( pagedListHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( pagedListHolder, request );

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Config the response
            response.setContentType( "text/html" );
            response.setCharacterEncoding( "UTF-8" );
            // ��ʼ��PrintWrite����
            final PrintWriter out = response.getWriter();

            // Send to client
            out.println( ListRender.generateListTable( request, "HRO_BIZ_EMPLOYEE_CONTRACT_OT" ) );
            out.flush();
            out.close();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listEmployeeContractOT" );
   }

   /**
    * To Object New
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ��ʼ��Service
      final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

      // ���ContractId
      final String contractId = KANUtil.decodeString( request.getParameter( "contractId" ) );

      // ���EmployeeContractVO
      final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
      employeeContractVO.reset( null, request );

      // Ĭ��ֵ����
      final EmployeeContractOTVO employeeContractOTVO = ( EmployeeContractOTVO ) form;
      employeeContractOTVO.setSubAction( CREATE_OBJECT );
      employeeContractOTVO.setContractId( contractId );
      employeeContractOTVO.setBase( "0.00" );
      employeeContractOTVO.setDiscount( "100.00" );
      employeeContractOTVO.setMultiple( "1" );
      employeeContractOTVO.setResultCap( "0.00" );
      employeeContractOTVO.setResultFloor( "0.00" );
      employeeContractOTVO.setStartDate( employeeContractVO.getStartDate() );
      employeeContractOTVO.setEndDate( employeeContractVO.getEndDate() );
      employeeContractOTVO.setStatus( "1" );
      employeeContractOTVO.setDescription( "" );

      // ����Attribute
      request.setAttribute( "employeeContractVO", employeeContractVO );

      // ��ת���½�����
      return mapping.findForward( "manageEmployeeContractOT" );
   }

   /**
    * To Object Modify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��õ�ǰ����
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeContractOTVO ) form ).getEmployeeOTId();
         }

         // ���EmployeeContractOTVO
         final EmployeeContractOTVO employeeContractOTVO = employeeContractOTService.getEmployeeContractOTVOByEmployeeOTId( id );
         employeeContractOTVO.reset( null, request );
         employeeContractOTVO.setSubAction( VIEW_OBJECT );

         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractOTVO.getContractId() );
         employeeContractVO.reset( null, request );

         // ����Attribute
         request.setAttribute( "employeeContractOTForm", employeeContractOTVO );
         request.setAttribute( "employeeContractVO", employeeContractVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageEmployeeContractOT" );
   }

   /**
    * Add Employee Contract OT
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );

            // ���ActionForm
            final EmployeeContractOTVO employeeContractOTVO = ( EmployeeContractOTVO ) form;
            employeeContractOTVO.setCreateBy( getUserId( request, response ) );
            employeeContractOTVO.setModifyBy( getUserId( request, response ) );

            // �����Զ���Column
            employeeContractOTVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SALARY" ) );

            // �½�����
            employeeContractOTService.insertEmployeeContractOT( employeeContractOTVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeContractOTVO, Operate.ADD, employeeContractOTVO.getEmployeeOTId(), null );
         }
         // �ظ��ύ���� 
         else
         {
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setContractId( ( ( EmployeeContractOTVO ) form ).getContractId() );
            return new EmployeeContractAction().to_objectModify( mapping, employeeContractVO, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Employee Contract OT
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );

            // ��õ�ǰ����
            final String employeeOTId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // ���EmployeeContractOTVO
            final EmployeeContractOTVO employeeContractOTVO = employeeContractOTService.getEmployeeContractOTVOByEmployeeOTId( employeeOTId );
            employeeContractOTVO.update( ( EmployeeContractOTVO ) form );
            employeeContractOTVO.setModifyBy( getUserId( request, response ) );

            // �����Զ���Column
            employeeContractOTVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_OT" ) );

            // �޸Ķ���
            employeeContractOTService.updateEmployeeContractOT( employeeContractOTVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeContractOTVO, Operate.MODIFY, employeeContractOTVO.getEmployeeOTId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   /**
    * Delete Employee Contract OT list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );

         // ���Action Form
         final EmployeeContractOTVO employeeContractOTVO = ( EmployeeContractOTVO ) form;

         // ����ѡ�е�ID
         if ( employeeContractOTVO.getSelectedIds() != null && !employeeContractOTVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeContractOTVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeeContractOTVO.setEmployeeOTId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeContractOTVO.setModifyBy( getUserId( request, response ) );
               employeeContractOTService.deleteEmployeeContractOT( employeeContractOTVO );
            }

            insertlog( request, employeeContractOTVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeContractOTVO.getSelectedIds() ) );
         }

         // ���Selected IDs����Action
         employeeContractOTVO.setSelectedIds( "" );
         employeeContractOTVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Contract OT by Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractOTService employeeContractOTService = ( EmployeeContractOTService ) getService( "employeeContractOTService" );

         // ��õ�ǰ����
         String employeeOTId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeOTId" ) );

         // ��ʼ��EmployeeContractOTVO
         final EmployeeContractOTVO employeeContractOTVO = employeeContractOTService.getEmployeeContractOTVOByEmployeeOTId( employeeOTId );
         employeeContractOTVO.setEmployeeOTId( employeeOTId );
         employeeContractOTVO.setModifyBy( getUserId( request, response ) );

         // ����ɾ���ӿ�
         final long rows = employeeContractOTService.deleteEmployeeContractOT( employeeContractOTVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeContractOTVO, Operate.DELETE, employeeOTId, null );
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

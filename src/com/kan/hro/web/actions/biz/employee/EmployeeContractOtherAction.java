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
import com.kan.hro.domain.biz.employee.EmployeeContractOtherVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOtherService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�EmployeeContractOtherAction  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-10-10 ����10:02:20   
*   
*/
public class EmployeeContractOtherAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_OTHER";

   /**
    * List Employee Contract Other
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
         final EmployeeContractOtherService employeeContractOtherService = ( EmployeeContractOtherService ) getService( "employeeContractOtherService" );
         // ���Action Form
         final EmployeeContractOtherVO employeeContractOtherVO = ( EmployeeContractOtherVO ) form;

         // ����Զ�����������######
         // employeeContractOtherVO.setRemark1( generateDefineListSearches(
         // request, "HRO_BIZ_EMPLOYEE_CONTRACT_Other" ) );

         // �����SubAction��Ϊ��
         if ( employeeContractOtherVO.getSubAction() != null && !employeeContractOtherVO.getSubAction().trim().equals( "" ) )
         {
            // �����SubAction��ɾ���б����
            if ( employeeContractOtherVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
            {
               // ����ɾ���б��SubAction
               delete_objectList( mapping, form, request, response );
            }
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( employeeContractOtherVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // ���뵱ǰҳ
         pagedListHolder.setPage( page );

         // ���뵱ǰֵ����
         pagedListHolder.setObject( employeeContractOtherVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeContractOtherService.getEmployeeContractOtherVOsByCondition( pagedListHolder, true );
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
            out.println( ListRender.generateListTable( request, "HRO_BIZ_EMPLOYEE_CONTRACT_OTHER" ) );
            out.flush();
            out.close();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listEmployeeContractOther" );
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
   // Reviewed by Kevin Jin at 2013-11-17
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
      final EmployeeContractOtherVO employeeContractOtherVO = ( EmployeeContractOtherVO ) form;
      employeeContractOtherVO.setSubAction( CREATE_OBJECT );
      employeeContractOtherVO.setContractId( contractId );
      employeeContractOtherVO.setBase( "0.00" );
      employeeContractOtherVO.setResultCap( "0.00" );
      employeeContractOtherVO.setResultFloor( "0.00" );
      employeeContractOtherVO.setCycle( "1" );
      employeeContractOtherVO.setStartDate( employeeContractVO.getStartDate() );
      employeeContractOtherVO.setEndDate( employeeContractVO.getEndDate() );
      employeeContractOtherVO.setStatus( "1" );
      employeeContractOtherVO.setDescription( "" );

      // ����Attribute
      request.setAttribute( "employeeContractVO", employeeContractVO );

      // ��ת���½�����
      return mapping.findForward( "manageEmployeeContractOther" );
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
   // Reviewed by Kevin Jin at 2013-11-17
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final EmployeeContractOtherService employeeContractOtherService = ( EmployeeContractOtherService ) getService( "employeeContractOtherService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��õ�ǰ����
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeContractOtherVO ) form ).getEmployeeOtherId();
         }

         // ���EmployeeContractOtherVO
         final EmployeeContractOtherVO employeeContractOtherVO = employeeContractOtherService.getEmployeeContractOtherVOByEmployeeOtherId( id );
         employeeContractOtherVO.reset( null, request );
         employeeContractOtherVO.setSubAction( VIEW_OBJECT );

         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractOtherVO.getContractId() );
         employeeContractVO.reset( null, request );

         // ����Attribute
         request.setAttribute( "employeeContractOtherForm", employeeContractOtherVO );
         request.setAttribute( "employeeContractVO", employeeContractVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageEmployeeContractOther" );
   }

   /**
    * Add Employee Contract Other
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-17
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeContractOtherService employeeContractOtherService = ( EmployeeContractOtherService ) getService( "employeeContractOtherService" );
            // ���ActionForm
            final EmployeeContractOtherVO employeeContractOtherVO = ( EmployeeContractOtherVO ) form;

            employeeContractOtherVO.setCreateBy( getUserId( request, response ) );
            employeeContractOtherVO.setModifyBy( getUserId( request, response ) );

            // �����Զ����ֶ�
            employeeContractOtherVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_OTHER" ) );

            // �½�����
            employeeContractOtherService.insertEmployeeContractOther( employeeContractOtherVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeContractOtherVO, Operate.ADD, employeeContractOtherVO.getEmployeeOtherId(), null );
         }
         // �ظ��ύ���� 
         else
         {
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setContractId( ( ( EmployeeContractOtherVO ) form ).getContractId() );
            return new EmployeeContractAction().to_objectModify( mapping, employeeContractVO, request, response );
         }

         // ���Form����
         ( ( EmployeeContractOtherVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Employee Contract Other
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-17
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeContractOtherService employeeContractOtherService = ( EmployeeContractOtherService ) getService( "employeeContractOtherService" );

            // ��õ�ǰ����
            String employeeContractOtherId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���EmployeeContractOtherVO
            final EmployeeContractOtherVO employeeContractOtherVO = employeeContractOtherService.getEmployeeContractOtherVOByEmployeeOtherId( employeeContractOtherId );
            employeeContractOtherVO.update( ( EmployeeContractOtherVO ) form );
            employeeContractOtherVO.setModifyBy( getUserId( request, response ) );

            // �����Զ���Column
            employeeContractOtherVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_Other" ) );

            // �޸Ķ���
            employeeContractOtherService.updateEmployeeContractOther( employeeContractOtherVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, employeeContractOtherVO, Operate.MODIFY, employeeContractOtherVO.getEmployeeOtherId(), null );
         }

         // ���Form����
         ( ( EmployeeContractOtherVO ) form ).reset();
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
    * Delete Employee Contract Other list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-17
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractOtherService employeeContractOtherService = ( EmployeeContractOtherService ) getService( "employeeContractOtherService" );

         // ���Action Form
         final EmployeeContractOtherVO employeeContractOtherVO = ( EmployeeContractOtherVO ) form;
         // ����ѡ�е�ID
         if ( employeeContractOtherVO.getSelectedIds() != null && !employeeContractOtherVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeContractOtherVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeeContractOtherVO.setEmployeeOtherId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeContractOtherVO.setModifyBy( getUserId( request, response ) );
               employeeContractOtherService.deleteEmployeeContractOther( employeeContractOtherVO );
            }

            insertlog( request, employeeContractOtherVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeContractOtherVO.getSelectedIds() ) );
         }

         // ���Selected IDs����Action
         employeeContractOtherVO.setSelectedIds( "" );
         employeeContractOtherVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Contract Other by Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-17
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractOtherService employeeContractOtherService = ( EmployeeContractOtherService ) getService( "employeeContractOtherService" );

         // ��õ�ǰ����
         final String employeeOtherId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeOtherId" ) );

         final EmployeeContractOtherVO employeeContractOtherVO = employeeContractOtherService.getEmployeeContractOtherVOByEmployeeOtherId( employeeOtherId );
         employeeContractOtherVO.setEmployeeOtherId( employeeOtherId );
         employeeContractOtherVO.setModifyBy( getUserId( request, response ) );

         // ɾ��������Ӧ����
         final long rows = employeeContractOtherService.deleteEmployeeContractOther( employeeContractOtherVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeContractOtherVO, Operate.DELETE, employeeOtherId, "delete_object_ajax" );
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

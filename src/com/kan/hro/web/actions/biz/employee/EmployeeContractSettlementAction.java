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

import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeContractSettlementVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSettlementService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-10-10 ����10:02:20   
*   
*/
public class EmployeeContractSettlementAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_SETTLEMENT";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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
      final EmployeeContractSettlementVO employeeContractSettlementVO = ( EmployeeContractSettlementVO ) form;
      employeeContractSettlementVO.setSubAction( CREATE_OBJECT );
      employeeContractSettlementVO.setContractId( contractId );
      employeeContractSettlementVO.setResultCap( "0.00" );
      employeeContractSettlementVO.setResultFloor( "0.00" );
      employeeContractSettlementVO.setStatus( "1" );

      // ����Attribute
      request.setAttribute( "employeeContractVO", employeeContractVO );

      // ��ת���½�����
      return mapping.findForward( "manageEmployeeContractSettlement" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeContractSettlementService employeeContractSettlementService = ( EmployeeContractSettlementService ) getService( "employeeContractSettlementService" );
            // ���ActionForm
            final EmployeeContractSettlementVO employeeContractSettlementVO = ( EmployeeContractSettlementVO ) form;

            employeeContractSettlementVO.setCreateBy( getUserId( request, response ) );
            employeeContractSettlementVO.setModifyBy( getUserId( request, response ) );

            // �����Զ����ֶ�
            employeeContractSettlementVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SETTLEMENT" ) );

            // �½�����
            employeeContractSettlementService.insertEmployeeContractSettlement( employeeContractSettlementVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_ADD );
         }

         // ���Form����
         ( ( EmployeeContractSettlementVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final EmployeeContractSettlementService employeeContractSettlementService = ( EmployeeContractSettlementService ) getService( "employeeContractSettlementService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��õ�ǰ����
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeContractSettlementVO ) form ).getEmployeeSettlementId();
         }

         final EmployeeContractSettlementVO employeeContractSettlementVO = employeeContractSettlementService.getEmployeeContractSettlementVOByEmployeeSettlementId( id );
         employeeContractSettlementVO.reset( null, request );
         employeeContractSettlementVO.setSubAction( VIEW_OBJECT );

         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractSettlementVO.getContractId() );
         employeeContractVO.reset( null, request );

         // ����Attribute
         request.setAttribute( "employeeContractSettlementForm", employeeContractSettlementVO );
         request.setAttribute( "employeeContractVO", employeeContractVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageEmployeeContractSettlement" );
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeContractSettlementService employeeContractSettlementService = ( EmployeeContractSettlementService ) getService( "employeeContractSettlementService" );

            // ��õ�ǰ����
            String employeeContractSettlementId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���VO
            final EmployeeContractSettlementVO employeeContractSettlementVO = employeeContractSettlementService.getEmployeeContractSettlementVOByEmployeeSettlementId( employeeContractSettlementId );
            employeeContractSettlementVO.update( ( EmployeeContractSettlementVO ) form );
            employeeContractSettlementVO.setModifyBy( getUserId( request, response ) );

            // �����Զ���Column
            employeeContractSettlementVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SETTLEMENT" ) );

            // �޸Ķ���
            employeeContractSettlementService.updateEmployeeContractSettlement( employeeContractSettlementVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // ���Form����
         ( ( EmployeeContractSettlementVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   /**
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
         final EmployeeContractSettlementService employeeContractSettlementService = ( EmployeeContractSettlementService ) getService( "employeeContractSettlementService" );

         // ��õ�ǰ����
         final String employeeSettlementId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeSettlementId" ) );

         final EmployeeContractSettlementVO employeeContractSettlementVO = employeeContractSettlementService.getEmployeeContractSettlementVOByEmployeeSettlementId( employeeSettlementId );
         employeeContractSettlementVO.setEmployeeSettlementId( employeeSettlementId );
         employeeContractSettlementVO.setModifyBy( getUserId( request, response ) );

         // ɾ��������Ӧ����
         final long rows = employeeContractSettlementService.deleteEmployeeContractSettlement( employeeContractSettlementVO );

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

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractSettlementService employeeContractSettlementService = ( EmployeeContractSettlementService ) getService( "employeeContractSettlementService" );

         // ���Action Form
         final EmployeeContractSettlementVO employeeContractSettlementVO = ( EmployeeContractSettlementVO ) form;
         // ����ѡ�е�ID
         if ( employeeContractSettlementVO.getSelectedIds() != null && !employeeContractSettlementVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeContractSettlementVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeeContractSettlementVO.setEmployeeSettlementId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeContractSettlementVO.setModifyBy( getUserId( request, response ) );
               employeeContractSettlementService.deleteEmployeeContractSettlement( employeeContractSettlementVO );
            }
         }

         // ���Selected IDs����Action
         employeeContractSettlementVO.setSelectedIds( "" );
         employeeContractSettlementVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

}

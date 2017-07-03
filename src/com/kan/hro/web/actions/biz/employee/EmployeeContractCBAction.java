/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.CommercialBenefitSolutionDTO;
import com.kan.base.domain.management.CommercialBenefitSolutionHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.CachedUtil;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientOrderCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractCBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.client.ClientOrderCBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractCBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�EmployeeContractCBAction  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-10-10 ����10:01:14  
* �޸��ˣ�Jixiang  
* �޸�ʱ�䣺2013-10-10 ����10:01:14  
* �޸ı�ע��  
* @version   
*   
*/
public class EmployeeContractCBAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_CB";

   /**
    * List Object
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
         // �洢Token
         saveToken( request );
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         // ���Action Form
         final EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) form;

         // ���á�������Ȩ��
         request.setAttribute( "isExportExcel", "1" );

         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, employeeContractCBVO );
         setDataAuth( request, response, employeeContractCBVO );

         employeeContractCBVO.setOrderId( KANUtil.filterEmpty( employeeContractCBVO.getOrderId(), "0" ) );

         // ����ɾ������
         if ( employeeContractCBVO.getSubAction() != null && employeeContractCBVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // Ajax�ύ������������Ҫ���롣
         decodedObject( employeeContractCBVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder employeeContractCBHolder = new PagedListHolder();
         // ���뵱ǰҳ
         employeeContractCBHolder.setPage( page );

         // ���û��ָ��������Ĭ�ϰ� AdjustmentHeaderId����
         if ( employeeContractCBVO.getSortColumn() == null || employeeContractCBVO.getSortColumn().isEmpty() )
         {
            employeeContractCBVO.setSortColumn( "a.status,a.contractId" );
            employeeContractCBVO.setSortOrder( "desc" );
         }

         // ���In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            employeeContractCBVO.setCorpId( getCorpId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }

         // ���뵱ǰֵ����
         employeeContractCBHolder.setObject( employeeContractCBVO );
         // ����ҳ���¼����
         employeeContractCBHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeContractCBService.getFullEmployeeContractCBVOsByCondition( employeeContractCBHolder, getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( employeeContractCBHolder, request );

         // Holder��д��Request����
         request.setAttribute( "employeeContractCBHolder", employeeContractCBHolder );

         // �����Ajax����
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               request.setAttribute( "holderName", "employeeContractCBHolder" );
               request.setAttribute( "fileName", "�̱�������" );
               request.setAttribute( "nameZHArray", getNameZHArray( request, response ) );
               request.setAttribute( "nameSysArray", getNameSysArray( request, response ) );
               // �����ļ�
               return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
            }
            else
            {
               // д��Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listEmployeeContractCBTable" );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��ת���б����
      return mapping.findForward( "listEmployeeContractCB" );
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

      // ��ʼ��Service
      final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

      // ���ContractId
      final String contractId = KANUtil.decodeString( request.getParameter( "contractId" ) );

      // ���EmployeeContractVO
      final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
      employeeContractVO.reset( null, request );

      // Ĭ��ֵ����
      EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) form;
      employeeContractCBVO.setContractId( contractId );
      employeeContractCBVO.setSubAction( CREATE_OBJECT );
      employeeContractCBVO.setStartDate( employeeContractVO.getStartDate() );
      employeeContractCBVO.setEndDate( "" );
      employeeContractCBVO.setFreeShortOfMonth( "2" );
      employeeContractCBVO.setChargeFullMonth( "2" );
      employeeContractCBVO.setStatus( "0" );
      employeeContractCBVO.setDescription( "" );

      // ����Attribute
      request.setAttribute( "employeeContractVO", employeeContractVO );

      // ��ת���½�����
      return mapping.findForward( "manageEmployeeContractCB" );
   }

   /**  
    * Get Months Options Ajax
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward get_object_json_manage( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �洢Token
         saveToken( request );
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ�� Service
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );

         // ���ContractId
         final String contractId = request.getParameter( "contractId" );

         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         employeeContractVO.reset( null, request );

         // ��ʼ��EmployeeContractCBVO
         EmployeeContractCBVO employeeContractCBVO = new EmployeeContractCBVO();
         employeeContractCBVO.setContractId( contractId );
         employeeContractCBVO.setSubAction( CREATE_OBJECT );
         employeeContractCBVO.setStartDate( employeeContractVO.getStartDate() );
         employeeContractCBVO.setFreeShortOfMonth( "2" );
         employeeContractCBVO.setChargeFullMonth( "2" );

         // ����̱�����solutionId
         final String solutionId = request.getParameter( "solutionId" );

         // ����̱�����ID��Ϊ��
         if ( KANUtil.filterEmpty( solutionId, "0" ) != null )
         {
            // ����SolutionId
            employeeContractCBVO.setSolutionId( solutionId );
            // ���Э��ID ContractId��Ӧ���̱���������
            final List< Object > clientOrderCBVOObjects = clientOrderCBService.getClientOrderCBVOsByEmployeeContractId( contractId );
            for ( Object clientOrderCBVOObject : clientOrderCBVOObjects )
            {
               ClientOrderCBVO clientOrderCBVO = ( ClientOrderCBVO ) clientOrderCBVOObject;

               if ( solutionId.equals( clientOrderCBVO.getCbSolutionId() ) )
               {
                  // �������������
                  if ( KANUtil.filterEmpty( clientOrderCBVO.getFreeShortOfMonth(), "0" ) != null )
                  {
                     employeeContractCBVO.setFreeShortOfMonth( clientOrderCBVO.getFreeShortOfMonth() );
                  }
                  else
                  {
                     // ����̱�����DTO
                     final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getCommercialBenefitSolutionDTOByHeaderId( solutionId );
                     final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO();

                     // �������������
                     if ( KANUtil.filterEmpty( commercialBenefitSolutionHeaderVO.getFreeShortOfMonth() ) != null )
                     {
                        employeeContractCBVO.setFreeShortOfMonth( commercialBenefitSolutionHeaderVO.getFreeShortOfMonth() );
                     }
                     else
                     {
                        employeeContractCBVO.setFreeShortOfMonth( "2" );
                     }

                  }

                  // �������������
                  if ( KANUtil.filterEmpty( clientOrderCBVO.getChargeFullMonth(), "0" ) != null )
                  {
                     employeeContractCBVO.setChargeFullMonth( clientOrderCBVO.getChargeFullMonth() );
                  }
                  else
                  {
                     // ����̱�����DTO
                     final CommercialBenefitSolutionDTO commercialBenefitSolutionDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getCommercialBenefitSolutionDTOByHeaderId( solutionId );
                     final CommercialBenefitSolutionHeaderVO commercialBenefitSolutionHeaderVO = commercialBenefitSolutionDTO.getCommercialBenefitSolutionHeaderVO();

                     // �������������
                     if ( KANUtil.filterEmpty( commercialBenefitSolutionHeaderVO.getChargeFullMonth() ) != null )
                     {
                        employeeContractCBVO.setChargeFullMonth( commercialBenefitSolutionHeaderVO.getChargeFullMonth() );
                     }
                     else
                     {
                        employeeContractCBVO.setChargeFullMonth( "2" );
                     }

                  }
                  break;
               }

            }
         }

         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();
         employeeContractCBVO.reset( mapping, request );
         jsonObject = JSONObject.fromObject( employeeContractCBVO );

         // ����Token
         request.setAttribute( "token", CachedUtil.get( request, getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME ) );

         // Send to front
         out.println( jsonObject.toString() );
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

   /**  
    * Get Object Json
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward get_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �洢Token
         saveToken( request );
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ�� Service
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );

         // �������employeeCBId
         final String employeeCBId = request.getParameter( "employeeCBId" );
         // ���������ӦVO
         final EmployeeContractCBVO employeeContractCBVO = employeeContractCBService.getFullEmployeeContractCBVOByEmployeeCBId( employeeCBId );

         if ( employeeContractCBVO != null )
         {
            employeeContractCBVO.setSubAction( VIEW_OBJECT );
         }

         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();

         if ( employeeContractCBVO != null )
         {
            employeeContractCBVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( employeeContractCBVO );
            jsonObject.put( "success", "true" );

            // ����Token
            request.setAttribute( "token", CachedUtil.get( request, getUserToken( request, null ) + "_" + BaseAction.TOKEN_NAME ) );
         }
         else
         {
            jsonObject.put( "success", "false" );
         }

         // Send to front
         out.println( jsonObject.toString() );
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
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��õ�ǰ����
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( KANUtil.filterEmpty( id ) == null )
         {
            id = ( ( EmployeeContractCBVO ) form ).getEmployeeCBId();
         }

         // ���������Ӧ����
         final EmployeeContractCBVO employeeContractCBVO = employeeContractCBService.getEmployeeContractCBVOByEmployeeCBId( id );
         employeeContractCBVO.reset( null, request );
         employeeContractCBVO.setSubAction( VIEW_OBJECT );

         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractCBVO.getContractId() );
         employeeContractVO.reset( null, request );

         request.setAttribute( "employeeContractCBForm", employeeContractCBVO );
         request.setAttribute( "employeeContractVO", employeeContractVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageEmployeeContractCB" );
   }

   /**
    * Add employeeContractCB
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
            final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            // ���ActionForm
            final EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) form;

            // ��ȡEmployeeContractVO
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractCBVO.getContractId() );
            if ( employeeContractVO != null )
            {
               employeeContractCBVO.setEmployeeNameZH( employeeContractVO.getEmployeeNameZH() );
               employeeContractCBVO.setEmployeeNameEN( employeeContractVO.getEmployeeNameEN() );
            }

            employeeContractCBVO.setAccountId( getAccountId( request, response ) );
            employeeContractCBVO.setCorpId( getCorpId( request, response ) );
            employeeContractCBVO.setCreateBy( getUserId( request, response ) );
            employeeContractCBVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column###
            employeeContractCBVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // �½�����
            if ( employeeContractCBService.insertEmployeeContractCB( employeeContractCBVO ) == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeeContractCBVO, Operate.SUBMIT, employeeContractCBVO.getEmployeeCBId(), null );
            }
            else
            {
               success( request, MESSAGE_TYPE_ADD );
               insertlog( request, employeeContractCBVO, Operate.ADD, employeeContractCBVO.getEmployeeCBId(), null );
            }
         }
         // �ظ��ύ���� 
         else
         {
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setContractId( ( ( EmployeeContractCBVO ) form ).getContractId() );
            return new EmployeeContractAction().to_objectModify( mapping, employeeContractVO, request, response );
         }

         // ���Form����
         ( ( EmployeeContractCBVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify employeeContractCB
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

            // ��ȡSubAction
            final String subAction = request.getParameter( "subAction" );

            // ��ʼ��Service�ӿ�
            final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
            // ��õ�ǰ����
            String employeeContractCBId = KANUtil.decodeString( request.getParameter( "id" ) );

            // ���EmployeeContractCBVO����
            final EmployeeContractCBVO employeeContractCBVO = employeeContractCBService.getEmployeeContractCBVOByEmployeeCBId( employeeContractCBId );
            employeeContractCBVO.reset( mapping, request );
            employeeContractCBVO.update( ( EmployeeContractCBVO ) form );
            employeeContractCBVO.setModifyDate( new Date() );
            employeeContractCBVO.setModifyBy( getUserId( request, response ) );
            // �����Զ���Column
            employeeContractCBVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // ������̱������ύ
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               if ( employeeContractCBService.submitEmployeeContractCB( employeeContractCBVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, employeeContractCBVO, Operate.SUBMIT, employeeContractCBVO.getEmployeeCBId(), null );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, employeeContractCBVO, Operate.MODIFY, employeeContractCBVO.getEmployeeCBId(), null );
               }
            }
            else
            {
               employeeContractCBService.updateEmployeeContractCB( employeeContractCBVO );
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeeContractCBVO, Operate.MODIFY, employeeContractCBVO.getEmployeeCBId(), null );
            }

         }

         // ���Form����
         ( ( EmployeeContractCBVO ) form ).reset();

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   /**  
    * Modify Object Popup
    *	ģ̬�����޸�
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Add By Jack at 2013-12-29
   public ActionForward modify_object_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         // ���ActionForm
         final EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) form;
         employeeContractCBVO.setModifyBy( getUserId( request, response ) );

         // ģ̬���޸��̱�����
         final String actFlag = employeeContractCBService.modifyEmployeeContractCBVO( employeeContractCBVO );

         // �����������¼
         if ( actFlag.equals( "addObject" ) )
         {
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
            insertlog( request, employeeContractCBVO, Operate.ADD, employeeContractCBVO.getEmployeeCBId(), null );
         }
         else if ( actFlag.equals( "updateObject" ) )
         {
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
            insertlog( request, employeeContractCBVO, Operate.MODIFY, employeeContractCBVO.getEmployeeCBId(), null );
         }
         else if ( actFlag.equals( "submitObject" ) )
         {
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_SUBMIT, null, MESSAGE_HEADER );
            insertlog( request, employeeContractCBVO, Operate.SUBMIT, employeeContractCBVO.getEmployeeCBId(), null );
         }

         // ���form
         ( ( EmployeeContractCBVO ) form ).reset();
         ( ( EmployeeContractCBVO ) form ).setEmployeeCBId( "" );
         ( ( EmployeeContractCBVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractCBVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return list_object( mapping, form, request, response );
   }

   /**  
    * Modify Objects Popup
    *	ģ̬����������޸�
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Add By Jack at 2013-12-29
   public ActionForward modify_objects_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );

         final EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) form;
         employeeContractCBVO.setModifyBy( getUserId( request, response ) );
         // �޸�
         employeeContractCBService.modifyEmployeeContractCBVOs( employeeContractCBVO );
         // ���ر༭�ɹ����
         success( request, null, "�����ɹ�", MESSAGE_HEADER );
         insertlog( request, employeeContractCBVO, Operate.MODIFY, employeeContractCBVO.getEmployeeCBId(), null );
         // ���form
         ( ( EmployeeContractCBVO ) form ).reset();
         ( ( EmployeeContractCBVO ) form ).setEmployeeCBId( "" );
         ( ( EmployeeContractCBVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractCBVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete employeeContractCB
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
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );

         // ��õ�ǰ����
         final String employeeCBId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeCBId" ) );

         // ɾ��������Ӧ����
         final EmployeeContractCBVO employeeContractCBVO = employeeContractCBService.getEmployeeContractCBVOByEmployeeCBId( employeeCBId );
         employeeContractCBVO.setModifyBy( getUserId( request, response ) );
         employeeContractCBVO.setModifyDate( new Date() );

         employeeContractCBService.deleteEmployeeContractCB( employeeContractCBVO );
         insertlog( request, employeeContractCBVO, Operate.DELETE, employeeCBId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete employeeContractCB list
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
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );
         // ���Action Form
         EmployeeContractCBVO employeeContractCBVO = ( EmployeeContractCBVO ) form;
         // ����ѡ�е�ID
         if ( employeeContractCBVO.getSelectedIds() != null && !employeeContractCBVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeContractCBVO.getSelectedIds().split( "," ) )
            {
               // ɾ��������Ӧ����
               final EmployeeContractCBVO tempEmployeeContractCBVO = employeeContractCBService.getEmployeeContractCBVOByEmployeeCBId( KANUtil.decodeStringFromAjax( selectedId ) );
               tempEmployeeContractCBVO.setModifyBy( getUserId( request, response ) );
               tempEmployeeContractCBVO.setModifyDate( new Date() );
               employeeContractCBService.deleteEmployeeContractCB( tempEmployeeContractCBVO );
            }

            insertlog( request, employeeContractCBVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeContractCBVO.getSelectedIds() ) );
         }
         // ���form
         ( ( EmployeeContractCBVO ) form ).reset();
         ( ( EmployeeContractCBVO ) form ).setEmployeeCBId( "" );
         ( ( EmployeeContractCBVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractCBVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Contract Salary by Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );

         // ��õ�ǰ����
         final String employeeCBId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeCBId" ) );

         // ��ʼ��EmployeeContractSalaryVO
         final EmployeeContractCBVO employeeContractCBVO = employeeContractCBService.getEmployeeContractCBVOByEmployeeCBId( employeeCBId );
         employeeContractCBVO.setEmployeeCBId( employeeCBId );
         employeeContractCBVO.setModifyBy( getUserId( request, response ) );

         // ����ɾ���ӿ�
         final long rows = employeeContractCBService.deleteEmployeeContractCB( employeeContractCBVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeContractCBVO, Operate.DELETE, employeeCBId, "delete_object_ajax" );
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
    * List Object Options Manage Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_options_manage_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final EmployeeContractCBService employeeContractCBService = ( EmployeeContractCBService ) getService( "employeeContractCBService" );

         // ��÷���Э��ID
         String contractId = request.getParameter( "contractId" );
         final String solutionId = request.getParameter( "solutionId" );

         if ( KANUtil.filterEmpty( contractId ) != null )
         {
            contractId = KANUtil.decodeStringFromAjax( contractId );
         }

         // ���ContractId�����籣
         final List< Object > employeeContractCBVOs = employeeContractCBService.getEmployeeContractCBVOsByContractId( contractId );

         // ��������籣
         final List< MappingVO > allMappingVOs = new ArrayList< MappingVO >();

         // �����In House��¼
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            allMappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getCommercialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) ) );
         }
         // �����Hr Service��¼
         else
         {
            allMappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getCommercialBenefitSolutions( request.getLocale().getLanguage() ) );
         }

         //���super��
         //allMappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getCommercialBenefitSolutions( request.getLocale().getLanguage() ) );

         // ��ȡ�ظ��ġ�
         final List< MappingVO > existMappingVOs = new ArrayList< MappingVO >();
         EmployeeContractCBVO employeeContractCBVO = null;
         for ( int i = 0; i < employeeContractCBVOs.size(); i++ )
         {
            for ( int j = 0; j < allMappingVOs.size(); j++ )
            {
               employeeContractCBVO = ( EmployeeContractCBVO ) employeeContractCBVOs.get( i );
               if ( employeeContractCBVO.getSolutionId().equals( ( allMappingVOs.get( j ) ).getMappingId() ) )
               {
                  existMappingVOs.add( allMappingVOs.get( j ) );
               }
            }
         }

         allMappingVOs.removeAll( existMappingVOs );
         allMappingVOs.add( 0, ( ( EmployeeContractCBVO ) form ).getEmptyMappingVO() );
         out.println( KANUtil.getOptionHTML( allMappingVOs, "solutionId", ( KANUtil.filterEmpty( solutionId, "0" ) != null ) ? solutionId
               : ( KANUtil.filterEmpty( solutionId, "0" ) ) ) );

         // Send to client
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   // ������ͷ
   private String[] getNameZHArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameZHs = new ArrayList< String >();
      final String role = getRole( request, response );

      nameZHs.add( "���ID" );
      nameZHs.add( ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "ID" );
      nameZHs.add( ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "�������У�" );
      nameZHs.add( ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "������Ӣ��" );
      nameZHs.add( "��ע" );
      if ( role.equals( "1" ) )
      {
         nameZHs.add( "�ͻ�ID" );
      }
      nameZHs.add( ( role.equals( "1" ) ? "����" : "�������" ) + "ID" );
      nameZHs.add( ( role.equals( "1" ) ? "����Э��" : "�Ͷ���ͬ" ) + "ID" );
      nameZHs.add( "��ͬ״̬" );

      return KANUtil.stringListToArray( nameZHs );
   }

   // ��������
   private String[] getNameSysArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameSyses = new ArrayList< String >();
      final String role = getRole( request, response );

      nameSyses.add( "employeeCBId" );
      nameSyses.add( "employeeId" );
      nameSyses.add( "employeeNameZH" );
      nameSyses.add( "employeeNameEN" );
      nameSyses.add( "decodeRemark" );
      if ( role.equals( "1" ) )
      {
         nameSyses.add( "clientId" );
      }
      nameSyses.add( "orderId" );
      nameSyses.add( "contractId" );
      nameSyses.add( "decodeContractStatus" );

      return KANUtil.stringListToArray( nameSyses );
   }
}

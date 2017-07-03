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
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.management.SocialBenefitSolutionDTO;
import com.kan.base.domain.management.SocialBenefitSolutionDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.CachedUtil;
import com.kan.base.util.KANAccountConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBDetailVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSBVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderSBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBDetailService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�EmployeeContractSBAction  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-8-23 ����11:01:46   
*   
*/
public class EmployeeContractSBAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_SB";

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
         this.saveToken( request );
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ��ʼ��Service�ӿ�
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         // ���Action Form
         final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) form;

         employeeContractSBVO.setOrderId( KANUtil.filterEmpty( employeeContractSBVO.getOrderId(), "0" ) );

         // ���á�������Ȩ��
         request.setAttribute( "isExportExcel", "1" );

         // ��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, employeeContractSBVO );
         setDataAuth( request, response, employeeContractSBVO );

         // ����ɾ������
         if ( employeeContractSBVO.getSubAction() != null && employeeContractSBVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // Ajax�ύ������������Ҫ���롣
         decodedObject( employeeContractSBVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder employeeContractSBHolder = new PagedListHolder();
         // ���뵱ǰҳ
         employeeContractSBHolder.setPage( page );

         // ���û��ָ��������Ĭ�ϰ� AdjustmentHeaderId����
         if ( employeeContractSBVO.getSortColumn() == null || employeeContractSBVO.getSortColumn().isEmpty() )
         {
            employeeContractSBVO.setSortColumn( "a.status,a.contractId" );
            employeeContractSBVO.setSortOrder( "desc" );
         }

         // ���In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            employeeContractSBVO.setCorpId( getCorpId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }

         // ��ѡ���籣״̬����
         if ( employeeContractSBVO.getStatusArray() != null && employeeContractSBVO.getStatusArray().length > 0 )
         {
            employeeContractSBVO.setStatus( KANUtil.toJasonArray( employeeContractSBVO.getStatusArray(), "," ).replace( "{", "" ).replace( "}", "" ) );
         }

         // ���뵱ǰֵ����
         employeeContractSBHolder.setObject( employeeContractSBVO );
         // ����ҳ���¼����
         employeeContractSBHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeContractSBService.getFullEmployeeContractSBVOsByCondition( employeeContractSBHolder, getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( employeeContractSBHolder, request );

         // Holder��д��Request����
         request.setAttribute( "employeeContractSBHolder", employeeContractSBHolder );

         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( getAjax( request ) ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               request.setAttribute( "holderName", "employeeContractSBHolder" );
               request.setAttribute( "fileName", "�籣����������" );
               request.setAttribute( "nameZHArray", getNameZHArray( request, response ) );
               request.setAttribute( "nameSysArray", getNameSysArray( request, response ) );
               // �����ļ�
               return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
            }
            else
            {
               // д��Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listEmployeeContractSBTable" );
            }
         }

         // ��ת���б����
         return mapping.findForward( "listEmployeeContractSB" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * List Object Vendor
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_object_vendor( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
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
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         // ���Action Form
         final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) form;

         employeeContractSBVO.setOrderId( KANUtil.filterEmpty( employeeContractSBVO.getOrderId(), "0" ) );

         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, employeeContractSBVO );
         setDataAuth( request, response, employeeContractSBVO );

         // ����ɾ������
         if ( employeeContractSBVO.getSubAction() != null && employeeContractSBVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // Ajax�ύ������������Ҫ���롣
         decodedObject( employeeContractSBVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder employeeContractSBHolder = new PagedListHolder();
         // ���뵱ǰҳ
         employeeContractSBHolder.setPage( page );

         // ���û��ָ��������Ĭ�ϰ� AdjustmentHeaderId����
         if ( employeeContractSBVO.getSortColumn() == null || employeeContractSBVO.getSortColumn().isEmpty() )
         {
            employeeContractSBVO.setSortColumn( "a.status,a.contractId" );
            employeeContractSBVO.setSortOrder( "desc" );
         }

         // ���In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            employeeContractSBVO.setCorpId( getCorpId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }

         // ���뵱ǰֵ����
         employeeContractSBHolder.setObject( employeeContractSBVO );
         // ����ҳ���¼����
         employeeContractSBHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeContractSBService.getVendorEmployeeContractSBVOsByCondition( employeeContractSBHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( employeeContractSBHolder, request );

         // Holder��д��Request����
         request.setAttribute( "employeeContractSBHolder", employeeContractSBHolder );

         // �����Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listVendorSBTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��ת���б����
      return mapping.findForward( "listVendorSB" );
   }

   /**  
    * Get Object Json
    *	ajax��ȡEmployeeContractSBVO
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
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

         // �������employeeSBId
         final String employeeSBId = request.getParameter( "employeeSBId" );
         // ���������ӦVO
         final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getFullEmployeeContractSBVOByEmployeeSBId( employeeSBId );

         if ( employeeContractSBVO != null )
         {
            employeeContractSBVO.setSubAction( VIEW_OBJECT );
         }

         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();

         if ( employeeContractSBVO != null )
         {
            employeeContractSBVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( employeeContractSBVO );
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
      final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) form;
      employeeContractSBVO.setSubAction( CREATE_OBJECT );
      employeeContractSBVO.setContractId( contractId );
      employeeContractSBVO.setStartDate( employeeContractVO.getStartDate() );
      employeeContractSBVO.setEndDate( "" );
      employeeContractSBVO.setNeedSBCard( "2" );
      employeeContractSBVO.setNeedMedicalCard( "2" );
      employeeContractSBVO.setStatus( "0" );
      employeeContractSBVO.setDescription( "" );

      // ����Attribute
      request.setAttribute( "employeeContractVO", employeeContractVO );

      // ��ת���½�����
      return mapping.findForward( "manageEmployeeContractSB" );
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
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��õ�ǰ����
         String id = KANUtil.decodeString( request.getParameter( "id" ) );

         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeContractSBVO ) form ).getEmployeeSBId();
         }

         // ���EmployeeContractSBVO
         final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( id );
         employeeContractSBVO.reset( null, request );
         employeeContractSBVO.setSubAction( VIEW_OBJECT );

         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractSBVO.getContractId() );
         employeeContractVO.reset( null, request );

         // ����Attribute
         request.setAttribute( "employeeContractSBForm", employeeContractSBVO );
         request.setAttribute( "employeeContractVO", employeeContractVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageEmployeeContractSB" );
   }

   /**
    * Add Employee Contract SB
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
            final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

            // ���ActionForm
            final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) form;
            employeeContractSBVO.setCreateBy( getUserId( request, response ) );
            employeeContractSBVO.setModifyBy( getUserId( request, response ) );

            // ��ȡEmployeeContractVO
            final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractSBVO.getContractId() );
            if ( employeeContractVO != null )
            {
               employeeContractSBVO.setEmployeeNameZH( employeeContractVO.getEmployeeNameZH() );
               employeeContractSBVO.setEmployeeNameEN( employeeContractVO.getEmployeeNameEN() );
            }

            // �����Զ���Column
            employeeContractSBVO.setRemark1( saveDefineColumns( request, accessAction ) );

            if ( KANUtil.filterEmpty( employeeContractSBVO.getVendorId(), "0" ) == null )
            {
               employeeContractSBVO.setVendorServiceId( null );
            }
            // �������
            if ( employeeContractSBService.insertEmployeeContractSB( employeeContractSBVO ) == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeeContractSBVO, Operate.SUBMIT, employeeContractSBVO.getEmployeeSBId(), null );
            }
            else
            {
               success( request, MESSAGE_TYPE_ADD );
               insertlog( request, employeeContractSBVO, Operate.ADD, employeeContractSBVO.getEmployeeSBId(), null );
            }
         }
         // �ظ��ύ���� 
         else
         {
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setContractId( ( ( EmployeeContractSBVO ) form ).getContractId() );
            return new EmployeeContractAction().to_objectModify( mapping, employeeContractVO, request, response );
         }

         // ���Form����
         ( ( EmployeeContractSBVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Employee Contract SB
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
            final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

            // ��ȡSubAction
            final String subAction = request.getParameter( "subAction" );

            // ��õ�ǰ����
            final String employeeSBId = KANUtil.decodeString( request.getParameter( "id" ) );

            // ���EmployeeContractSBVO
            final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( employeeSBId );
            employeeContractSBVO.reset( mapping, request );
            employeeContractSBVO.update( ( EmployeeContractSBVO ) form );
            employeeContractSBVO.setModifyDate( new Date() );
            employeeContractSBVO.setModifyBy( getUserId( request, response ) );

            // �����Զ���Column
            employeeContractSBVO.setRemark1( saveDefineColumns( request, accessAction ) );

            if ( KANUtil.filterEmpty( employeeContractSBVO.getVendorId(), "0" ) == null )
            {
               employeeContractSBVO.setVendorServiceId( null );
            }

            // ������籣�����ύ
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               if ( employeeContractSBService.submitEmployeeContractSB( employeeContractSBVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, employeeContractSBVO, Operate.SUBMIT, employeeContractSBVO.getEmployeeSBId(), null );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, employeeContractSBVO, Operate.MODIFY, employeeContractSBVO.getEmployeeSBId(), null );
               }
            }
            else
            {
               employeeContractSBService.updateEmployeeContractSB( employeeContractSBVO );
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeeContractSBVO, Operate.MODIFY, employeeContractSBVO.getEmployeeSBId(), null );
            }
         }

         // ���form
         ( ( EmployeeContractSBVO ) form ).reset();
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
    *	ģ̬���޸�
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
         // �����ظ��ύ
         //         if ( this.isTokenValid( request, true ) )
         //         {
         // ��ʼ��Service�ӿ�
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );
         // ���ActionForm
         final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) form;
         employeeContractSBVO.setModifyBy( getUserId( request, response ) );

         // ģ̬���޸��籣����
         final String actFlag = employeeContractSBService.modifyEmployeeContractSBVO( employeeContractSBVO );

         // �����������¼
         if ( actFlag.equals( "addObject" ) )
         {
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
            insertlog( request, employeeContractSBVO, Operate.ADD, employeeContractSBVO.getEmployeeSBId(), null );
         }
         else if ( actFlag.equals( "updateObject" ) )
         {
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
            insertlog( request, employeeContractSBVO, Operate.MODIFY, employeeContractSBVO.getEmployeeSBId(), null );
         }
         else if ( actFlag.equals( "submitObject" ) )
         {
            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_SUBMIT, null, MESSAGE_HEADER );
            insertlog( request, employeeContractSBVO, Operate.SUBMIT, employeeContractSBVO.getEmployeeSBId(), null );
         }

         // ���form
         ( ( EmployeeContractSBVO ) form ).reset();
         ( ( EmployeeContractSBVO ) form ).setEmployeeSBId( "" );
         ( ( EmployeeContractSBVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractSBVO ) form ).setSubAction( "" );
         ( ( EmployeeContractSBVO ) form ).setStatus( "" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      final String pageFlag = ( String ) request.getParameter( "pageFlag" );
      if ( pageFlag != null && pageFlag.equals( "vendor" ) )
      {
         // ��ת������Ӧ��ѡ�񡱽���
         return list_object_vendor( mapping, form, request, response );
      }
      else
      {
         // ��ת������Ա�Ӽ���������
         return list_object( mapping, form, request, response );
      }
   }

   /**  
    * Modify Objects Popup
    * ģ̬����������޸�
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Add By Jack at 2013-12-29
   public ActionForward modify_objects_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

         final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) form;
         employeeContractSBVO.setModifyBy( getUserId( request, response ) );
         // �޸�
         employeeContractSBService.modifyEmployeeContractSBVOs( employeeContractSBVO );
         // ���ر༭�ɹ����
         success( request, null, "�����ɹ�", MESSAGE_HEADER );
         insertlog( request, employeeContractSBVO, Operate.MODIFY, employeeContractSBVO.getEmployeeSBId(), "modify_objects_popup" );
         // ���form
         ( ( EmployeeContractSBVO ) form ).reset();
         ( ( EmployeeContractSBVO ) form ).setEmployeeSBId( "" );
         ( ( EmployeeContractSBVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractSBVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���鿴����
      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   /**
    * Delete Employee Contract SB list
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
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

         // ���Action Form
         final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) form;

         // ����ѡ�е�ID
         if ( employeeContractSBVO.getSelectedIds() != null && !employeeContractSBVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeContractSBVO.getSelectedIds().split( "," ) )
            {
               // ɾ��������Ӧ����
               final EmployeeContractSBVO tempEmployeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( KANUtil.decodeStringFromAjax( selectedId ) );
               tempEmployeeContractSBVO.setModifyBy( getUserId( request, response ) );
               tempEmployeeContractSBVO.setModifyDate( new Date() );
               employeeContractSBService.deleteEmployeeContractSB( tempEmployeeContractSBVO );
            }

            insertlog( request, employeeContractSBVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeContractSBVO.getSelectedIds() ) );
         }

         // ���form
         ( ( EmployeeContractSBVO ) form ).reset();
         ( ( EmployeeContractSBVO ) form ).setEmployeeSBId( "" );
         ( ( EmployeeContractSBVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractSBVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Employee Contract SB Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-22
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

         // ��õ�ǰ����
         final String employeeSBId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeSBId" ) );

         // ɾ��������Ӧ����
         final EmployeeContractSBVO employeeContractSBVO = employeeContractSBService.getEmployeeContractSBVOByEmployeeSBId( employeeSBId );
         employeeContractSBVO.setModifyBy( getUserId( request, response ) );
         employeeContractSBVO.setModifyDate( new Date() );

         // ����ɾ���ӿ�
         final long rows = employeeContractSBService.deleteEmployeeContractSB( employeeContractSBVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeContractSBVO, Operate.DELETE, employeeSBId, "delete_object_ajax" );
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
    *  List Special Info Html
    *  
    *  @param mapping
    *  @param form
    *  @param request
    *  @param response
    *  @return
    *  @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-19
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡ�Ƿ�Ҫtab
         final String noTab = request.getParameter( "noTab" );

         // ��õ�ǰ����
         final String employeeSBId = request.getParameter( "employeeSBId" );

         // ����籣����
         final String sbSolutionId = request.getParameter( "sbSolutionId" );

         // ��ʼ��KANAccountConstants
         final KANAccountConstants accountConstants = KANConstants.getKANAccountConstants( getAccountId( request, response ) );

         // Used for return
         final List< EmployeeContractSBDetailVO > tempEmployeeContractSBDetailVOs = new ArrayList< EmployeeContractSBDetailVO >();

         // �ӻ����ȡ�籣����DTO
         final SocialBenefitSolutionDTO socialBenefitSolutionDTO = accountConstants.getSocialBenefitSolutionDTOByHeaderId( sbSolutionId );

         if ( socialBenefitSolutionDTO != null && socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs() != null
               && socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs().size() > 0 )
         {
            // ��ʼ��Service�ӿ�
            final EmployeeContractSBDetailService employeeContractSBDetailService = ( EmployeeContractSBDetailService ) getService( "employeeContractSBDetailService" );
            final List< Object > employeeContractSBDetailVOs = employeeContractSBDetailService.getEmployeeContractSBDetailVOsByEmployeeSBId( employeeSBId );

            // ѭ��������ϸ
            for ( SocialBenefitSolutionDetailVO socialBenefitSolutionDetailVO : socialBenefitSolutionDTO.getSocialBenefitSolutionDetailVOs() )
            {
               // �籣����ItemVO
               final ItemVO itemVO = accountConstants.getItemVOByItemId( socialBenefitSolutionDetailVO.getItemId() );

               final EmployeeContractSBDetailVO employeeContractSBDetailVO = new EmployeeContractSBDetailVO();
               employeeContractSBDetailVO.setItemId( itemVO.getItemId() );
               employeeContractSBDetailVO.setItemNo( itemVO.getItemNo() );
               employeeContractSBDetailVO.setNameZH( itemVO.getNameZH() );
               employeeContractSBDetailVO.setNameEN( itemVO.getNameZH() );
               employeeContractSBDetailVO.setSolutionDetailId( socialBenefitSolutionDetailVO.getDetailId() );
               employeeContractSBDetailVO.setCompanyCap( socialBenefitSolutionDetailVO.getCompanyCap() );
               employeeContractSBDetailVO.setCompanyFloor( socialBenefitSolutionDetailVO.getCompanyFloor() );
               employeeContractSBDetailVO.setPersonalCap( socialBenefitSolutionDetailVO.getPersonalCap() );
               employeeContractSBDetailVO.setPersonalFloor( socialBenefitSolutionDetailVO.getPersonalFloor() );
               employeeContractSBDetailVO.setCompanyPercent( socialBenefitSolutionDetailVO.getCompanyPercent() );
               employeeContractSBDetailVO.setPersonalPercent( socialBenefitSolutionDetailVO.getPersonalPercent() );
               employeeContractSBDetailVO.setCompanyFixAmount( socialBenefitSolutionDetailVO.getCompanyFixAmount() );
               employeeContractSBDetailVO.setPersonalFixAmount( socialBenefitSolutionDetailVO.getPersonalFixAmount() );
               employeeContractSBDetailVO.setStartDateLimit( socialBenefitSolutionDetailVO.getStartDateLimit() );
               employeeContractSBDetailVO.setEndDateLimit( socialBenefitSolutionDetailVO.getEndDateLimit() );

               // �Ѿ������̱���ϸ
               if ( employeeContractSBDetailVOs != null && employeeContractSBDetailVOs.size() > 0 )
               {
                  for ( Object employeeContractSBDetailVOObject : employeeContractSBDetailVOs )
                  {
                     if ( socialBenefitSolutionDetailVO.getDetailId().equals( ( ( EmployeeContractSBDetailVO ) employeeContractSBDetailVOObject ).getSolutionDetailId() ) )
                     {
                        // �������ù�˾�͸��˻���
                        employeeContractSBDetailVO.setBaseCompany( ( ( EmployeeContractSBDetailVO ) employeeContractSBDetailVOObject ).getBaseCompany() );
                        employeeContractSBDetailVO.setBasePersonal( ( ( EmployeeContractSBDetailVO ) employeeContractSBDetailVOObject ).getBasePersonal() );
                     }
                  }
               }
               else
               {
                  // ��ʼ��EmployeeContractSalaryService�ӿ�
                  final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

                  String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );

                  final List< Object > employeeContractSalaryVOs = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractId( contractId );

                  if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
                  {
                     // ����ͬһ����Э���µĹ��ʷ���
                     for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
                     {
                        final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject;

                        // ����ǻ������ʣ���н������ʼ����˾�͸����籣����
                        if ( employeeContractSalaryVO.getItemId() != null && employeeContractSalaryVO.getItemId().equals( "1" )
                              && KANUtil.filterEmpty( employeeContractSalaryVO.getBase() ) != null )
                        {
                           double baseCompany = Double.valueOf( employeeContractSalaryVO.getBase() );
                           double basePersonal = Double.valueOf( employeeContractSalaryVO.getBase() );

                           if ( KANUtil.filterEmpty( employeeContractSBDetailVO.getCompanyCap() ) != null
                                 && baseCompany > Double.valueOf( employeeContractSBDetailVO.getCompanyCap() ) )
                           {
                              baseCompany = Double.valueOf( employeeContractSBDetailVO.getCompanyCap() );
                           }

                           if ( KANUtil.filterEmpty( employeeContractSBDetailVO.getCompanyFloor() ) != null
                                 && baseCompany < Double.valueOf( employeeContractSBDetailVO.getCompanyFloor() ) )
                           {
                              baseCompany = Double.valueOf( employeeContractSBDetailVO.getCompanyFloor() );
                           }
                           employeeContractSBDetailVO.setBaseCompany( String.valueOf( baseCompany ) );

                           if ( KANUtil.filterEmpty( employeeContractSBDetailVO.getPersonalCap() ) != null
                                 && basePersonal > Double.valueOf( employeeContractSBDetailVO.getPersonalCap() ) )
                           {
                              basePersonal = Double.valueOf( employeeContractSBDetailVO.getPersonalCap() );
                           }

                           if ( KANUtil.filterEmpty( employeeContractSBDetailVO.getPersonalFloor() ) != null
                                 && basePersonal < Double.valueOf( employeeContractSBDetailVO.getPersonalFloor() ) )
                           {
                              basePersonal = Double.valueOf( employeeContractSBDetailVO.getPersonalFloor() );
                           }

                           employeeContractSBDetailVO.setBasePersonal( String.valueOf( basePersonal ) );
                        }
                     }
                  }

                  // �����˾�͸����籣����Ϊ����Ĭ������Ϊ��Сֵ
                  if ( KANUtil.filterEmpty( employeeContractSBDetailVO.getBaseCompany() ) == null
                        || KANUtil.filterEmpty( employeeContractSBDetailVO.getBaseCompany() ).equals( "0" ) )
                  {
                     employeeContractSBDetailVO.setBaseCompany( employeeContractSBDetailVO.getCompanyFloor() );
                  }
                  if ( KANUtil.filterEmpty( employeeContractSBDetailVO.getBasePersonal() ) == null
                        || KANUtil.filterEmpty( employeeContractSBDetailVO.getBasePersonal() ).equals( "0" ) )
                  {
                     employeeContractSBDetailVO.setBasePersonal( employeeContractSBDetailVO.getPersonalFloor() );
                  }

               }

               tempEmployeeContractSBDetailVOs.add( employeeContractSBDetailVO );
            }
         }
         // ���տ�ĿID����
         for ( int i = 0; i < tempEmployeeContractSBDetailVOs.size(); i++ )
         {
            for ( int j = i + 1; j < tempEmployeeContractSBDetailVOs.size(); j++ )
            {
               if ( Integer.parseInt( tempEmployeeContractSBDetailVOs.get( i ).getItemId() ) > Integer.parseInt( tempEmployeeContractSBDetailVOs.get( j ).getItemId() ) )
               {
                  final EmployeeContractSBDetailVO tempVO = tempEmployeeContractSBDetailVOs.get( i );
                  tempEmployeeContractSBDetailVOs.set( i, tempEmployeeContractSBDetailVOs.get( j ) );
                  tempEmployeeContractSBDetailVOs.set( j, tempVO );
               }
            }
         }

         // Attribute����
         request.setAttribute( "employeeContractSBDetailVOs", tempEmployeeContractSBDetailVOs );

         request.setAttribute( "countEmployeeContractSBDetailVOs", tempEmployeeContractSBDetailVOs.size() );

         if ( KANUtil.filterEmpty( noTab ) != null && KANUtil.filterEmpty( noTab ).equals( "true" ) )
         {
            return mapping.findForward( "listEmployeeContractSBDetailTable" );
         }

         return mapping.findForward( "listEmployeeContractSBDetail" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * list_object_options_ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ������ѡ��
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // ��ʼ��Service�ӿ�
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

         // ��÷���Э��ID
         final String contractId = request.getParameter( "contractId" );

         // ���EmployeeContractSBVO�б�
         final List< Object > employeeContractSBVOs = employeeContractSBService.getEmployeeContractSBVOsByContractId( contractId );

         String employeeSBId = "";
         if ( employeeContractSBVOs != null && employeeContractSBVOs.size() > 0 )
         {
            // ����
            for ( Object employeeContractSBVOObject : employeeContractSBVOs )
            {
               final EmployeeContractSBVO employeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOObject;
               employeeContractSBVO.reset( null, request );
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( employeeContractSBVO.getEmployeeSBId() );
               mappingVO.setMappingValue( employeeContractSBVO.getDecodeSbSolutionId() );
               mappingVOs.add( mappingVO );
            }

            // ���ֻ��һ��ѡ����Ĭ�ϱ�ѡ��
            if ( employeeContractSBVOs.size() == 1 )
            {
               employeeSBId = ( ( EmployeeContractSBVO ) employeeContractSBVOs.get( 0 ) ).getEmployeeSBId();
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "employeeSBId", employeeSBId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "" );
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
         final EmployeeContractSBService employeeContractSBService = ( EmployeeContractSBService ) getService( "employeeContractSBService" );

         // ��÷���Э��ID
         String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );

         // ��������籣
         final List< MappingVO > allMappingVOs = new ArrayList< MappingVO >();

         ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );

         // �����Ͷ���ͬID�õ���Ӧ����������籣
         List< Object > clientOrderSBVOs = clientOrderSBService.getClientOrderSBVOsByEmployeeContractId( contractId );
         // ���ContractId�����籣
         final List< Object > employeeContractSBVOs = employeeContractSBService.getEmployeeContractSBVOsByContractId( contractId );

         if ( clientOrderSBVOs.size() > 0 )
         {
            boolean isZH = request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" );
            for ( Object o : clientOrderSBVOs )
            {
               ClientOrderSBVO clientOrderSBVO = ( ClientOrderSBVO ) o;
               MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( clientOrderSBVO.getSbSolutionId() );
               if ( isZH )
               {
                  mappingVO.setMappingValue( clientOrderSBVO.getSbSolutionNameZH() );
               }
               else
               {
                  mappingVO.setMappingValue( clientOrderSBVO.getSbSolutionNameEN() );
               }
               allMappingVOs.add( mappingVO );
            }
         }
         else
         {

            // �����In House��¼
            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               allMappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) ) );
            }
            // �����Hr Service��¼
            else
            {
               allMappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage() ) );
            }

            // ���super��
            allMappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getSocialBenefitSolutions( request.getLocale().getLanguage() ) );
         }
         // ��ȡ�ظ��ġ�
         final List< MappingVO > existMappingVOs = new ArrayList< MappingVO >();
         EmployeeContractSBVO employeeContractSBVO = null;
         for ( int i = 0; i < employeeContractSBVOs.size(); i++ )
         {
            for ( int j = 0; j < allMappingVOs.size(); j++ )
            {
               employeeContractSBVO = ( EmployeeContractSBVO ) employeeContractSBVOs.get( i );
               if ( employeeContractSBVO.getSbSolutionId().equals( ( allMappingVOs.get( j ) ).getMappingId() ) )
               {

                  // ������޸�����
                  if ( KANUtil.filterEmpty( request.getParameter( "sbSolutionId" ) ) != null )
                  {
                     if ( KANUtil.filterEmpty( request.getParameter( "sbSolutionId" ) ).equals( ( allMappingVOs.get( j ) ).getMappingId() ) )
                     {
                        continue;
                     }
                  }

                  existMappingVOs.add( allMappingVOs.get( j ) );
               }
            }
         }

         allMappingVOs.removeAll( existMappingVOs );
         allMappingVOs.add( 0, ( ( EmployeeContractSBVO ) form ).getEmptyMappingVO() );

         out.println( KANUtil.getOptionHTML( allMappingVOs, "sbSolutionId", KANUtil.filterEmpty( request.getParameter( "sbSolutionId" ) ) ) );

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

   public void sbSolution_change_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ����˾�Ƿ�е��籣
         String personalSBBurden = "2";

         // ��ȡ����ID
         final String orderHeaderId = request.getParameter( "orderHeaderId" );

         // ��÷���Э��ID
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );

         // ����籣����ID
         final String sbSolutionId = request.getParameter( "sbSolutionId" );

         // ��ʼ��Service
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );

         // �����Ͷ���ͬID�õ���Ӧ����������籣
         final List< Object > clientOrderSBVOs = clientOrderSBService.getClientOrderSBVOsByEmployeeContractId( contractId );

         // �������clientOrderSBVOs
         if ( clientOrderSBVOs != null && clientOrderSBVOs.size() > 0 )
         {
            for ( Object clientOrderSBVOObject : clientOrderSBVOs )
            {
               final ClientOrderSBVO clientOrderSBVO = ( ClientOrderSBVO ) clientOrderSBVOObject;
               if ( KANUtil.filterEmpty( clientOrderSBVO.getSbSolutionId() ) != null && KANUtil.filterEmpty( clientOrderSBVO.getSbSolutionId() ).equals( sbSolutionId ) )
               {
                  personalSBBurden = KANUtil.filterEmpty( clientOrderSBVO.getPersonalSBBurden() ) == null ? personalSBBurden
                        : KANUtil.filterEmpty( clientOrderSBVO.getPersonalSBBurden() );
                  break;
               }
            }
         }
         else
         {
            final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );
            if ( clientOrderHeaderVO != null )
            {
               personalSBBurden = KANUtil.filterEmpty( clientOrderHeaderVO.getPersonalSBBurden() ) == null ? personalSBBurden
                     : KANUtil.filterEmpty( clientOrderHeaderVO.getPersonalSBBurden() );
            }
         }

         out.println( personalSBBurden.trim() );
         // Send to client
         out.flush();
         out.close();

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
      nameZHs.add( "�籣�����𷽰�" );
      nameZHs.add( "�ӱ�����" );
      nameZHs.add( "�˱�����" );
      nameZHs.add( "�籣������״̬" );
      nameZHs.add( ( role.equals( "1" ) ? "����" : "�������" ) + "����" );
      nameZHs.add( "��ͬ״̬" );
      nameZHs.add( "���֤����" );
      if ( role.equals( "1" ) )
      {
         nameZHs.add( "�ͻ�ID" );
      }

      return KANUtil.stringListToArray( nameZHs );
   }

   // ��������
   private String[] getNameSysArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameSyses = new ArrayList< String >();
      final String role = getRole( request, response );

      nameSyses.add( "employeeSBId" );
      nameSyses.add( "employeeId" );
      nameSyses.add( "employeeNameZH" );
      nameSyses.add( "employeeNameEN" );
      nameSyses.add( "decodeSbSolutionId" );
      nameSyses.add( "startDate" );
      nameSyses.add( "endDate" );
      nameSyses.add( "decodeStatus" );
      nameSyses.add( "orderDescription" );
      nameSyses.add( "decodeContractStatus" );
      nameSyses.add( "certificateNumber" );
      if ( role.equals( "1" ) )
      {
         nameSyses.add( "clientId" );
      }
      return KANUtil.stringListToArray( nameSyses );
   }
}

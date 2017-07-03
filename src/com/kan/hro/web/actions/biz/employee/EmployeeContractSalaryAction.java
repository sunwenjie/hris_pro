/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.HistoryService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�EmployeeContractSalaryAction  
* ��������  
* �����ˣ�Jixiang  
* ����ʱ�䣺2013-8-23 ����11:01:31  
*   
*/
public class EmployeeContractSalaryAction extends BaseAction
{

   public static String accessAction = "HRO_BIZ_EMPLOYEE_CONTRACT_SALARY";
   //���ʻ�������accessAction
   public static String accessActionForEmployeeSalary = "HRO_BIZ_EMPLOYEE_SALARY";

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
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         // ���Action Form
         final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;
         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, employeeContractSalaryVO );
         setDataAuth( request, response, employeeContractSalaryVO );

         employeeContractSalaryVO.setOrderId( KANUtil.filterEmpty( employeeContractSalaryVO.getOrderId(), "0" ) );

         // Ajax�ύ������������Ҫ���롣
         decodedObject( employeeContractSalaryVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder employeeContractSalaryHolder = new PagedListHolder();
         // ���뵱ǰҳ
         employeeContractSalaryHolder.setPage( page );

         // ���û��ָ��������Ĭ�ϰ� AdjustmentHeaderId����
         if ( employeeContractSalaryVO.getSortColumn() == null || employeeContractSalaryVO.getSortColumn().isEmpty() )
         {
            employeeContractSalaryVO.setSortColumn( "a.status,a.contractId,a.modifyDate" );
            employeeContractSalaryVO.setSortOrder( "desc" );
         }

         // ���In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            employeeContractSalaryVO.setCorpId( getCorpId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }

         // ���뵱ǰֵ����
         employeeContractSalaryHolder.setObject( employeeContractSalaryVO );
         // ����ҳ���¼����
         employeeContractSalaryHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeContractSalaryService.getEmployeeContractSalaryVOsByCondition( employeeContractSalaryHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
               : true ) );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( employeeContractSalaryHolder, request );

         // Holder��д��Request����
         request.setAttribute( "employeeContractSalaryHolder", employeeContractSalaryHolder );

         // �����Ajax����
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               request.setAttribute( "holderName", "employeeContractSalaryHolder" );
               request.setAttribute( "fileName", "���ʻ�����" );
               request.setAttribute( "nameZHArray", getNameZHArray( request, response ) );
               request.setAttribute( "nameSysArray", getNameSysArray( request, response ) );

               // �����ļ�
               return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
            }

            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listEmployeeContractSalaryTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��ת���б����
      return mapping.findForward( "listEmployeeContractSalary" );
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
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // �������employeeSalaryId
         final String employeeSalaryId = request.getParameter( "employeeSalaryId" );
         // ���������ӦVO
         final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( employeeSalaryId );
         // ��ʼ�� JSONObject
         JSONObject jsonObject = new JSONObject();

         if ( employeeContractSalaryVO != null )
         {
            employeeContractSalaryVO.reset( mapping, request );
            employeeContractSalaryVO.setSubAction( MODIFY_OBJECT );
            jsonObject = JSONObject.fromObject( employeeContractSalaryVO );
            jsonObject.put( "success", "true" );
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
    * Get Object Ajax Popup
    * popup��ȡ����
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward get_object_ajax_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // ��ȡContractId
         final String contractId = request.getParameter( "contractId" );
         EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;

         // ��ȡEmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         employeeContractVO.reset( null, request );
         // ���OrderId
         final String orderId = employeeContractVO.getOrderId();
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderId );
         // ��ö�����SalaryType
         final String salaryType = clientOrderHeaderVO.getSalaryType();
         final String divideType = clientOrderHeaderVO.getDivideType();

         // ����Ĭ��ֵ����ʼʱ�䡢����ʱ�䡢��н��ʽ�����㷽ʽ��
         employeeContractSalaryVO.setContractId( contractId );
         employeeContractSalaryVO.setBase( "0.00" );
         employeeContractSalaryVO.setResultCap( "0.00" );
         employeeContractSalaryVO.setResultFloor( "0.00" );
         employeeContractSalaryVO.setCycle( "1" );
         employeeContractSalaryVO.setShowToTS( "2" );
         employeeContractSalaryVO.setStatus( "1" );
         employeeContractSalaryVO.setStartDate( employeeContractVO.getStartDate() );
         employeeContractSalaryVO.setEndDate( employeeContractVO.getEndDate() );

         if ( salaryType != null && !"0".equals( salaryType ) )
         {
            employeeContractSalaryVO.setSalaryType( salaryType );
         }
         else
         {
            employeeContractSalaryVO.setSalaryType( "1" );
         }
         if ( divideType != null && !"0".equals( divideType ) )
         {
            employeeContractSalaryVO.setDivideType( divideType );
         }
         else
         {
            employeeContractSalaryVO.setDivideType( "2" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "manageEmployeeContractSalaryPopup" );
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
      final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

      // ���ContractId
      String contractId = ( String ) request.getAttribute( "contractId" );
      if ( contractId == null )
      {
         contractId = KANUtil.decodeString( request.getParameter( "contractId" ) );
      }

      // ���EmployeeContractVO
      final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
      employeeContractVO.reset( null, request );

      // ���OrderId
      final String orderId = employeeContractVO.getOrderId();
      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderId );
      // ��ö�����SalaryType
      final String salaryType = clientOrderHeaderVO.getSalaryType();
      final String divideType = clientOrderHeaderVO.getDivideType();

      // Ĭ��ֵ����
      final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;
      employeeContractSalaryVO.setSubAction( CREATE_OBJECT );
      employeeContractSalaryVO.setContractId( contractId );
      employeeContractSalaryVO.setBase( "0.00" );
      employeeContractSalaryVO.setResultCap( "0.00" );
      employeeContractSalaryVO.setResultFloor( "0.00" );
      employeeContractSalaryVO.setCycle( "1" );
      employeeContractSalaryVO.setStartDate( employeeContractVO.getStartDate() );
      employeeContractSalaryVO.setEndDate( employeeContractVO.getEndDate() );
      employeeContractSalaryVO.setShowToTS( "2" );
      employeeContractSalaryVO.setProbationUsing( "2" );
      employeeContractSalaryVO.setDescription( "" );

      // ����Ͷ���ͬ�Ƿ��½�״̬����Ҫ�߹�������״̬��ʼ��Ϊͣ��
      if ( employeeContractVO != null && ( employeeContractVO.getStatus().equals( "1" ) || employeeContractVO.getStatus().equals( "4" ) ) )
      {
         employeeContractSalaryVO.setStatus( "1" );
      }
      else
      {
         employeeContractSalaryVO.setStatus( "2" );
      }

      if ( salaryType != null && !"0".equals( salaryType ) )
      {
         employeeContractSalaryVO.setSalaryType( salaryType );
      }
      else
      {
         employeeContractSalaryVO.setSalaryType( "1" );
      }
      if ( divideType != null && !"0".equals( divideType ) )
      {
         employeeContractSalaryVO.setDivideType( divideType );
      }
      else
      {
         employeeContractSalaryVO.setDivideType( "2" );
      }
      // ����Attribute
      request.setAttribute( "employeeContractVO", employeeContractVO );
      request.setAttribute( "clientOrderHeaderVO", clientOrderHeaderVO );
      request.setAttribute( "ItemType", 0 );

      // ��ת���½�����
      return mapping.findForward( "manageEmployeeContractSalary" );
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
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // ��õ�ǰ����
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( id == null || id.trim().isEmpty() )
         {
            id = ( ( EmployeeContractSalaryVO ) form ).getEmployeeSalaryId();
         }

         // ���EmployeeContractSalaryVO
         final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( id );
         employeeContractSalaryVO.reset( null, request );
         employeeContractSalaryVO.setSubAction( VIEW_OBJECT );

         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractSalaryVO.getContractId() );
         employeeContractVO.reset( null, request );

         // ����Attribute
         request.setAttribute( "employeeContractSalaryForm", employeeContractSalaryVO );
         request.setAttribute( "employeeContractVO", employeeContractVO );

         ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( employeeContractSalaryVO.getItemId() );
         request.setAttribute( "ItemType", itemVO.getItemType() );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageEmployeeContractSalary" );
   }

   /**
    * Add Employee Contract Salary
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
            final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

            // ���ActionForm
            final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;
            employeeContractSalaryVO.setCreateBy( getUserId( request, response ) );
            employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );
            // ��֤�����Ƿ��ظ�
            if ( employeeContractSalaryService.hasConflictContractSalaryInOneItem( employeeContractSalaryVO ) )
            {
               error( request, null, "ʱ������Ѵ��ڸÿ�Ŀ��н�귽����" );
               request.setAttribute( "contractId", employeeContractSalaryVO.getContractId() );
               return to_objectNew( mapping, form, request, response );
            }
            // �������Ŀ
            final String[] excludeDivideItemIds = request.getParameterValues( "checkBox_excludeDivideItemIds" );
            if ( excludeDivideItemIds != null && excludeDivideItemIds.length > 0 )
            {
               employeeContractSalaryVO.setExcludeDivideItemIds( KANUtil.toJasonArray( excludeDivideItemIds, "," ) );
            }
            // �����Զ���Column
            employeeContractSalaryVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SALARY" ) );

            // �½�����
            employeeContractSalaryService.insertEmployeeContractSalary( employeeContractSalaryVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, employeeContractSalaryVO, Operate.ADD, employeeContractSalaryVO.getEmployeeSalaryId(), null );
         }
         // �ظ��ύ���� 
         else
         {
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setContractId( ( ( EmployeeContractSalaryVO ) form ).getContractId() );
            return new EmployeeContractAction().to_objectModify( mapping, employeeContractVO, request, response );
         }

         // ���Form����
         ( ( EmployeeContractSalaryVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת����ѯ����
      return to_objectModify( mapping, form, request, response );
   }

   /**  
    * Add Object Popup
    *	ģ̬�����н�귽��_���ٲ���
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward add_object_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // ���ActionForm
         final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;
         employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );

         // �½�����
         employeeContractSalaryService.insertEmployeeContractSalaryPopup( employeeContractSalaryVO );
         // ������ӳɹ����
         success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

         insertlog( request, employeeContractSalaryVO, Operate.ADD, employeeContractSalaryVO.getEmployeeSalaryId(), "add_object_popup" );
         // ���Form����
         ( ( EmployeeContractSalaryVO ) form ).reset();
         ( ( EmployeeContractSalaryVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractSalaryVO ) form ).setEmployeeSalaryId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת����ѯ����
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify Employee Contract Salary
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-18
   // Modify by siuvan.xia at 2014-07-02
   /* (non-Javadoc)
    * @see com.kan.base.web.action.BaseAction#modify_object(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
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
            final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
            // ��õ�ǰ����
            final String employeeSalaryId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // ��ȡSubAction
            final String subAction = request.getParameter( "subAction" );

            // ���EmployeeContractSalaryVO
            final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( employeeSalaryId );
            employeeContractSalaryVO.update( ( EmployeeContractSalaryVO ) form );
            employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );
            //�����ȫ�ڽ������û��ѡ����Ĭ��Ϊ��������е�
            if ( "0".equals( employeeContractSalaryVO.getDivideType() ) )
            {
               EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( employeeContractSalaryVO.getContractId() );
               if ( employeeContractVO != null && employeeContractVO.getOrderId() != null && !"".equals( employeeContractVO.getOrderId() ) )
               {
                  ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
                  employeeContractSalaryVO.setDivideType( clientOrderHeaderVO.getDivideType() );
               }
            }
            // �����Զ���Column
            employeeContractSalaryVO.setRemark1( saveDefineColumns( request, "HRO_BIZ_EMPLOYEE_CONTRACT_SALARY" ) );
            // �������Ŀ
            final String[] excludeDivideItemIds = request.getParameterValues( "checkBox_excludeDivideItemIds" );
            if ( excludeDivideItemIds != null && excludeDivideItemIds.length > 0 )
            {
               employeeContractSalaryVO.setExcludeDivideItemIds( KANUtil.toJasonArray( excludeDivideItemIds, "," ) );
            }

            // �޸Ķ���
            employeeContractSalaryService.updateEmployeeContractSalary( employeeContractSalaryVO );

            // ����ǿͻ��ύ
            if ( KANUtil.filterEmpty( subAction ) != null && KANUtil.filterEmpty( subAction ).equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               employeeContractSalaryVO.reset( null, request );
               // ״̬��Ϊ����
               employeeContractSalaryVO.setStatus( "1" );
               employeeContractSalaryVO.setRole( getRole( request, response ) );
               employeeContractSalaryVO.setIp( getIPAddress( request ) );
               employeeContractSalaryService.generateHistoryVOForWorkflow( employeeContractSalaryVO );
               employeeContractSalaryService.submitEmployeeContractSalary( employeeContractSalaryVO );
               // �����ύ�ɹ��ı��
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeeContractSalaryVO, Operate.SUBMIT, employeeContractSalaryVO.getEmployeeSalaryId(), null );
            }
            else
            {
               // ���ر༭�ɹ����
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeeContractSalaryVO, Operate.MODIFY, employeeContractSalaryVO.getEmployeeSalaryId(), null );
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת����ѯ����
      return to_objectModify( mapping, form, request, response );
   }

   /**  
    * modify_object_popup
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward modify_object_popup( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         //         if ( this.isTokenValid( request, true ) )
         //         {
         // ��ʼ��Service�ӿ�
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // ��õ�ǰ����
         final String employeeSalaryId = ( ( EmployeeContractSalaryVO ) form ).getEmployeeSalaryId();

         // ���EmployeeContractSalaryVO
         final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( employeeSalaryId );
         employeeContractSalaryVO.update( ( EmployeeContractSalaryVO ) form );
         employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );

         // �޸Ķ���
         employeeContractSalaryService.updateEmployeeContractSalary( employeeContractSalaryVO );
         // ���Form����
         ( ( EmployeeContractSalaryVO ) form ).reset();
         ( ( EmployeeContractSalaryVO ) form ).setEmployeeSalaryId( "" );

         // ���ر༭�ɹ����
         success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
         insertlog( request, employeeContractSalaryVO, Operate.MODIFY, employeeSalaryId, "modify_object_popup" );
         //         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת����ѯ����
      return list_object( mapping, form, request, response );
   }

   /**  
    * Enable Object
    *	�޸�״̬Ϊ����
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward enable_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         // ��õ�ǰ����
         final String employeeSalaryId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeSalaryId" ) );
         // ���EmployeeContractSalaryVO
         final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( employeeSalaryId );
         employeeContractSalaryVO.setStatus( EmployeeContractSalaryVO.TRUE );
         employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );
         // �޸Ķ���
         employeeContractSalaryService.updateEmployeeContractSalary( employeeContractSalaryVO );
         // ���Selected IDs����Action
         ( ( EmployeeContractSalaryVO ) form ).setSubAction( "" );
         ( ( EmployeeContractSalaryVO ) form ).reset();
         ( ( EmployeeContractSalaryVO ) form ).setEmployeeSalaryId( "" );
         // ���ر༭�ɹ����
         success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
         insertlog( request, employeeContractSalaryVO, Operate.MODIFY, employeeSalaryId, "enable_object" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��ת����ѯ����
      return list_object( mapping, form, request, response );
   }

   /**  
    * Disable Object
    *	�޸�״̬Ϊͣ��
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward disable_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         // ��õ�ǰ����
         final String employeeSalaryId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeSalaryId" ) );
         // ���EmployeeContractSalaryVO
         final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( employeeSalaryId );
         employeeContractSalaryVO.setStatus( EmployeeContractSalaryVO.FALSE );
         employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );
         // �޸Ķ���
         employeeContractSalaryService.updateEmployeeContractSalary( employeeContractSalaryVO );
         // ���Selected IDs����Action
         ( ( EmployeeContractSalaryVO ) form ).setSubAction( "" );
         ( ( EmployeeContractSalaryVO ) form ).reset();
         ( ( EmployeeContractSalaryVO ) form ).setEmployeeSalaryId( "" );
         // ���ر༭�ɹ����
         success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
         insertlog( request, employeeContractSalaryVO, Operate.MODIFY, employeeSalaryId, "disable_object" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��ת����ѯ����
      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   /**
    * Delete Employee Contract Salary list
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
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // ���Action Form
         final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;
         // ����ѡ�е�ID
         if ( employeeContractSalaryVO.getSelectedIds() != null && !employeeContractSalaryVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : employeeContractSalaryVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               employeeContractSalaryVO.setEmployeeSalaryId( KANUtil.decodeStringFromAjax( selectedId ) );
               employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );
               employeeContractSalaryService.deleteEmployeeContractSalary( employeeContractSalaryVO );
            }

            insertlog( request, employeeContractSalaryVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( employeeContractSalaryVO.getSelectedIds() ) );
         }

         // ���Selected IDs����Action
         employeeContractSalaryVO.setSelectedIds( "" );
         employeeContractSalaryVO.setSubAction( "" );
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
   // Reviewed by Kevin Jin at 2013-11-18
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // ��õ�ǰ����
         final String employeeSalaryId = KANUtil.decodeStringFromAjax( request.getParameter( "employeeSalaryId" ) );

         // ��ʼ��EmployeeContractSalaryVO
         final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( employeeSalaryId );
         employeeContractSalaryVO.setEmployeeSalaryId( employeeSalaryId );
         employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );

         // ����ɾ���ӿ�
         final long rows = employeeContractSalaryService.deleteEmployeeContractSalary( employeeContractSalaryVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ����״̬��Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, employeeContractSalaryVO, Operate.DELETE, employeeSalaryId, "delete_object_ajax" );
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

   public ActionForward checkHasConflictContractSalaryInOneItem( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {

      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         final PrintWriter out = response.getWriter();
         // ��ʼ�� Service�ӿ�
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;
         // ��õ�ǰ����
         String id = KANUtil.decodeString( request.getParameter( "id" ) );
         employeeContractSalaryVO.setEmployeeSalaryId( id );
         if ( employeeContractSalaryService.hasConflictContractSalaryInOneItem( ( EmployeeContractSalaryVO ) form ) )
         {
            out.print( "1" );
         }
         else
         {
            out.print( "0" );
         }
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   public void getEmployeeContractSalaryByContractId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         response.setContentType( "application/json;charset=UTF-8" );
         response.setCharacterEncoding( "UTF-8" );
         String contractId = request.getParameter( "contractId" );
         List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );
         List< Object > employeeVOList = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractId( contractId );
         for ( Object object : employeeVOList )
         {
            EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) object;
            Map< String, String > mapReturn = new HashMap< String, String >();
            mapReturn.put( "itemId", employeeContractSalaryVO.getItemId() );
            listReturn.add( mapReturn );
         }
         JSONArray json = JSONArray.fromObject( listReturn );
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ȥ���ҳ�湤����
   public ActionForward list_object_workflow_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����������ID
         String historyId = request.getParameter( "historyId" );

         // ��ʼ��Service�ӿ�
         final HistoryService historyService = ( HistoryService ) getService( "historyService" );
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         HistoryVO historyVO = historyService.getHistoryVOByHistoryId( historyId );

         final String objectClassStr = historyVO.getObjectClass();
         Class< ? > objectClass = Class.forName( objectClassStr );

         String passObjStr = historyVO.getPassObject();
         final EmployeeContractSalaryVO tempEmployeeContractSalaryVO = ( EmployeeContractSalaryVO ) JSONObject.toBean( JSONObject.fromObject( passObjStr ), objectClass );
         if ( tempEmployeeContractSalaryVO != null )
         {
            final EmployeeContractSalaryVO employeeContractSalaryVO = employeeContractSalaryService.getEmployeeContractSalaryVOByEmployeeSalaryId( tempEmployeeContractSalaryVO.getEmployeeSalaryId() );
            // ˢ�¶��󣬳�ʼ�������б����ʻ�
            employeeContractSalaryVO.reset( null, request );
            employeeContractSalaryVO.setSubAction( VIEW_OBJECT );
            request.setAttribute( "employeeContractSalaryForm", employeeContractSalaryVO );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // Ajax����
      return mapping.findForward( "manageEmployeeContractSalaryWorkflow" );
   }

   // ������ͷ
   private String[] getNameZHArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameZHs = new ArrayList< String >();
      final String role = getRole( request, response );
      nameZHs.add( "���ID" );
      nameZHs.add( ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "ID" );
      nameZHs.add( ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "���������ģ�" );
      nameZHs.add( ( role.equals( "1" ) ? "��Ա" : "Ա��" ) + "������Ӣ�ģ�" );
      nameZHs.add( "��ע" );
      nameZHs.add( "н�귽��" );
      if ( role.equals( "1" ) )
      {
         nameZHs.add( "�ͻ�ID" );
      }
      nameZHs.add( ( role.equals( "1" ) ? "����" : "�������" ) + "����" );
      nameZHs.add( ( role.equals( "1" ) ? "Э��" : "��ͬ" ) + "��ʼʱ��" );
      nameZHs.add( ( role.equals( "1" ) ? "Э��" : "��ͬ" ) + "����ʱ��" );
      nameZHs.add( ( role.equals( "1" ) ? "Э��" : "��ͬ" ) + "״̬" );

      return KANUtil.stringListToArray( nameZHs );
   }

   // ��������
   private String[] getNameSysArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final List< String > nameSyses = new ArrayList< String >();
      final String role = getRole( request, response );
      nameSyses.add( "employeeSalaryId" );
      nameSyses.add( "employeeId" );
      nameSyses.add( "employeeNameZH" );
      nameSyses.add( "employeeNameEN" );
      nameSyses.add( "remark" );
      nameSyses.add( "decodeItemId" );
      if ( role.equals( "1" ) )
      {
         nameSyses.add( "clientId" );
      }
      nameSyses.add( "orderName" );
      nameSyses.add( "contractStartDate" );
      nameSyses.add( "contractEndDate" );
      nameSyses.add( "decodeStatus" );

      return KANUtil.stringListToArray( nameSyses );
   }

   /***
    * ��ʾ���н�귽������(��н�����õ�) show_addEmployeeSalaryPage_ajax
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @throws KANException
    */
   public ActionForward show_addEmployeeSalaryPage_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         //this.saveToken( request );

         // ���ContractId
         final String contractId = request.getParameter( "contractId" );
         // ��ʼ��Service
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // ��ȡActionForm
         final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;

         String salaryType = "1";
         String divideType = "2";
         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );
         if ( employeeContractVO != null )
         {
            employeeContractSalaryVO.setContractId( contractId );
            employeeContractSalaryVO.setEmployeeId( employeeContractVO.getEmployeeId() );
            employeeContractSalaryVO.setStartDate( employeeContractVO.getStartDate() );
            employeeContractSalaryVO.setEndDate( employeeContractVO.getEndDate() );
            // ��ȡClientOrderHeaderVO
            final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() );
            if ( clientOrderHeaderVO != null )
            {
               if ( clientOrderHeaderVO.getSalaryType() != null && !"0".equals( clientOrderHeaderVO.getSalaryType() ) )
               {
                  salaryType = clientOrderHeaderVO.getSalaryType();
               }

               if ( clientOrderHeaderVO.getDivideType() != null && !"0".equals( clientOrderHeaderVO.getDivideType() ) )
               {
                  divideType = clientOrderHeaderVO.getDivideType();
               }

               employeeContractSalaryVO.setExcludeDivideItemIds( clientOrderHeaderVO.getExcludeDivideItemIds() );
            }
         }

         employeeContractSalaryVO.setSubAction( CREATE_OBJECT );
         employeeContractSalaryVO.setSalaryType( salaryType );
         employeeContractSalaryVO.setDivideType( divideType );
         employeeContractSalaryVO.setBase( "0.00" );
         employeeContractSalaryVO.setResultCap( "0.00" );
         employeeContractSalaryVO.setResultFloor( "0.00" );
         employeeContractSalaryVO.setCycle( "1" );
         employeeContractSalaryVO.setShowToTS( "2" );
         employeeContractSalaryVO.setProbationUsing( "2" );
         employeeContractSalaryVO.setDescription( "" );
         employeeContractSalaryVO.setStatus( "1" );

         // ��ת���½�����
         return mapping.findForward( "manageEmployeeContractSalaryForAdjustment" );
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

   }

   /*** 
    * add_object_popup_ajax(��н�õ�) ģ̬�����н�귽��_���ٲ������Ϊ��0��
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void add_object_popup_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // ���ActionForm
         final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) form;
         employeeContractSalaryVO.setModifyBy( getUserId( request, response ) );

         // �½�����
         employeeContractSalaryService.insertEmployeeContractSalaryPopup( employeeContractSalaryVO );
         // ������ӳɹ����
         success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

         insertlog( request, employeeContractSalaryVO, Operate.ADD, employeeContractSalaryVO.getEmployeeSalaryId(), "add_object_popup" );
         // ���Form����
         ( ( EmployeeContractSalaryVO ) form ).reset();
         ( ( EmployeeContractSalaryVO ) form ).setSelectedIds( "" );
         ( ( EmployeeContractSalaryVO ) form ).setEmployeeSalaryId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}

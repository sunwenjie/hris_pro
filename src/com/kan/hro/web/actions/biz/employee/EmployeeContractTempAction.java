/*
 * Created on 2013-04-11
 */

package com.kan.hro.web.actions.biz.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeSalaryAdjustmentVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractCBTempService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractLeaveTempService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOTTempService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractOtherTempService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSBTempService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryTempService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractTempService;
import com.kan.hro.service.inf.biz.employee.EmployeeSalaryAdjustmentService;

/**  
*   
* ��Ŀ���ƣ�HRO_V1  
* �����ƣ�EmployeeContractAction  
* ��������  
* �����ˣ�Jixiang   
*   
*/
public class EmployeeContractTempAction extends BaseAction
{
   // �Ͷ���ͬ
   public static String accessActionLabor = "HRO_BIZ_EMPLOYEE_LABOR_CONTRACT";

   // ����Э��
   public static String ACCESS_ACTION_SERVICE = "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT";

   // �Ͷ���ͬ��In House��
   public static String ACCESS_ACTION_SERVICE_IN_HOUSE = "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_IN_HOUSE";

   // ��ǰAction��Ӧ��Access Action
   public static String getAccessAction( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         return ACCESS_ACTION_SERVICE_IN_HOUSE;
      }
      else
      {
         return ACCESS_ACTION_SERVICE;
      }
   }

   /**
    * List Employee Contract
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
         final String accessAction = getAccessAction( request, response );

         // ��õ�ǰҳ
         final String page = getPage( request );

         // ��ʼ��Service�ӿ�
         final EmployeeContractTempService employeeContractService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // ���Action Form
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         // ����OrderId��������
         employeeContractVO.setOrderId( KANUtil.filterEmpty( employeeContractVO.getOrderId(), "0" ) );
         // ���SubAction
         final String subAction = getSubAction( form );
         // ���û��ָ��������Ĭ�ϰ�employeeId����
         if ( employeeContractVO.getSortColumn() == null || employeeContractVO.getSortColumn().isEmpty() )
         {
            employeeContractVO.setSortColumn( "tempStatus,contractId" );
            employeeContractVO.setSortOrder( "desc" );
         }

         // SubAction����
         dealSubAction( employeeContractVO, mapping, form, request, response );

         employeeContractVO.setFlag( "2" );
         request.setAttribute( "flag", "2" );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ���뵱ǰҳ
            pagedListHolder.setPage( page );
            // ����Object
            pagedListHolder.setObject( employeeContractVO );
            // ����ҳ���¼����
            pagedListHolder.setPageSize( getPageSize( request, accessAction ) );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            employeeContractService.getEmployeeContractTempVOsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
                  : isPaged( request, accessAction ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( pagedListHolder, request );
         }
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "role", getRole( request, response ) );

         if ( new Boolean( getAjax( request ) ) )
         {
            if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               return mapping.findForward( "listEmployeeContractTempTableInHouse" );
            }
            return mapping.findForward( "listEmployeeContractTempTable" );
         }
         // �����In House��¼
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            // ��ת���б����
            return mapping.findForward( "listEmployeeContractTempInHouse" );
         }
         else
         {
            return mapping.findForward( "listEmployeeContractTemp" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * ���£�ֱ��update��ʽ����Ľ��
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_temp( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         // ��ʼ��Service�ӿ�
         final EmployeeContractTempService employeeContractTempService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // ��ù�ѡID
         final String contractIds = employeeContractVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( contractIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = contractIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( int i = 0; i < selectedIdArray.length; i++ )
            {
               selectedIdArray[ i ] = KANUtil.decodeStringFromAjax( selectedIdArray[ i ] );
            }

            employeeContractTempService.updateByTempContractIds( selectedIdArray, getIPAddress( request ) );

            if ( rows < 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeeContractVO, Operate.SUBMIT, null, "submit_temp:" + KANUtil.decodeSelectedIds( contractIds ) );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, employeeContractVO, Operate.MODIFY, null, "submit_temp:" + KANUtil.decodeSelectedIds( contractIds ) );
            }

         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   /**
    * �˻�,����ɾ��temp��
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward rollback_temp( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         final EmployeeContractTempService employeeContractTempService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // ��ù�ѡID
         final String batchIds = employeeContractVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( int i = 0; i < selectedIdArray.length; i++ )
            {
               selectedIdArray[ i ] = KANUtil.decodeStringFromAjax( selectedIdArray[ i ] );
            }

            employeeContractTempService.rollbackByTempContractIds( selectedIdArray );

            if ( rows < 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, employeeContractVO, Operate.ROllBACK, null, "rollback_temp:" + KANUtil.decodeSelectedIds( batchIds ) );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
            }
         }
         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final EmployeeContractTempService employeeContractTempService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // ��õ�ǰ����
         String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( contractId == null || contractId.trim().isEmpty() )
         {
            contractId = ( ( EmployeeContractVO ) form ).getContractId();
         }

         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractTempService.getEmployeeContractTempVOByContractId( contractId );
         employeeContractVO.setSubAction( VIEW_OBJECT );
         employeeContractVO.reset( null, request );
         employeeContractVO.setComeFrom( ( ( EmployeeContractVO ) form ).getComeFrom() );

         request.setAttribute( "employeeContractTempForm", employeeContractVO );

         final String flag = employeeContractVO.getFlag();

         if ( flag != null && "2".equals( flag ) && !KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
         {
            // ��ת������Э���½�����
            return mapping.findForward( "manageEmployeeContractTemp" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
      {
         // ��ת�� �Ͷ���ͬ IN HOUSE
         return mapping.findForward( "manageEmployeeContractTempInHouse" );
      }
      else
      {
         // ��ת���Ͷ���ͬ�½�����
         return mapping.findForward( "manageEmployeeContractTemp" );
      }

   }

   public ActionForward to_objectModify_update( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final EmployeeContractTempService employeeContractTempService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // ��õ�ǰ����
         String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( contractId == null || contractId.trim().isEmpty() )
         {
            contractId = ( ( EmployeeContractVO ) form ).getContractId();
         }

         // ���EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractTempService.getEmployeeContractTempVOByContractId( contractId );
         employeeContractVO.setSubAction( VIEW_OBJECT );
         employeeContractVO.reset( null, request );
         employeeContractVO.setComeFrom( ( ( EmployeeContractVO ) form ).getComeFrom() );

         request.setAttribute( "employeeContractTempForm", employeeContractVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���Ͷ���ͬ�½�����
      return mapping.findForward( "manageEmployeeContractTempUpdateInHouse" );
   }

   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��ContractId
         final String contractId = KANUtil.decodeString( request.getParameter( "contractId" ) );

         //  ����н�귽���б�
         final EmployeeContractSalaryTempService employeeContractSalaryTemppService = ( EmployeeContractSalaryTempService ) getService( "employeeContractSalaryTempService" );
         final List< Object > employeeContractSalaryVOs = employeeContractSalaryTemppService.getEmployeeContractSalaryTempVOsByContractId( contractId );

         if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
         {
            for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
            {
               ( ( ActionForm ) employeeContractSalaryVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractSalaryVOs", employeeContractSalaryVOs );
         request.setAttribute( "numberOfContractSalary", employeeContractSalaryVOs == null ? 0 : employeeContractSalaryVOs.size() );

         //  �����籣�����б�
         final EmployeeContractSBTempService employeeContractSBService = ( EmployeeContractSBTempService ) getService( "employeeContractSBTempService" );
         final List< Object > employeeContractSBVOs = employeeContractSBService.getEmployeeContractSBTempVOsByContractId( contractId );

         if ( employeeContractSBVOs != null && employeeContractSBVOs.size() > 0 )
         {
            for ( Object employeeContractSBVOObject : employeeContractSBVOs )
            {
               ( ( ActionForm ) employeeContractSBVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractSBVOs", employeeContractSBVOs );
         request.setAttribute( "numberOfContractSB", employeeContractSBVOs == null ? 0 : employeeContractSBVOs.size() );

         //  �������̱����б�
         final EmployeeContractCBTempService employeeContractCBService = ( EmployeeContractCBTempService ) getService( "employeeContractCBTempService" );
         final List< Object > employeeContractCBVOs = employeeContractCBService.getEmployeeContractCBTempVOsByContractId( contractId );

         if ( employeeContractCBVOs != null && employeeContractCBVOs.size() > 0 )
         {
            for ( Object employeeContractCBVOObject : employeeContractCBVOs )
            {
               ( ( ActionForm ) employeeContractCBVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractCBVOs", employeeContractCBVOs );
         request.setAttribute( "numberOfContractCB", employeeContractCBVOs == null ? 0 : employeeContractCBVOs.size() );

         //  �����ݼٷ����б�
         final EmployeeContractLeaveTempService employeeContractLeaveTempService = ( EmployeeContractLeaveTempService ) getService( "employeeContractLeaveTempService" );
         final List< Object > employeeContractLeaveVOs = employeeContractLeaveTempService.getEmployeeContractLeaveTempVOsByContractId( contractId );

         if ( employeeContractLeaveVOs != null && employeeContractLeaveVOs.size() > 0 )
         {
            for ( Object employeeContractLeaveVOObject : employeeContractLeaveVOs )
            {
               ( ( ActionForm ) employeeContractLeaveVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractLeaveVOs", employeeContractLeaveVOs );
         request.setAttribute( "numberOfContractLeave", employeeContractLeaveVOs == null ? 0 : employeeContractLeaveVOs.size() );

         //  ���ؼӰ෽���б�
         final EmployeeContractOTTempService employeeContractOTService = ( EmployeeContractOTTempService ) getService( "employeeContractOTTempService" );
         final List< Object > employeeContractOTVOs = employeeContractOTService.getEmployeeContractOTTempVOsByContractId( contractId );

         if ( employeeContractOTVOs != null && employeeContractOTVOs.size() > 0 )
         {
            for ( Object employeeContractOTVOObject : employeeContractOTVOs )
            {
               ( ( ActionForm ) employeeContractOTVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractOTVOs", employeeContractOTVOs );
         request.setAttribute( "numberOfContractOT", employeeContractOTVOs == null ? 0 : employeeContractOTVOs.size() );

         // ���ع�Ա���������б�
         final EmployeeContractOtherTempService employeeContractOtherTempService = ( EmployeeContractOtherTempService ) getService( "employeeContractOtherTempService" );
         final List< Object > employeeContractOtherVOs = employeeContractOtherTempService.getEmployeeContractOtherTempVOsByContractId( contractId );

         if ( employeeContractOtherVOs != null && employeeContractOtherVOs.size() > 0 )
         {
            for ( Object employeeContractOtherVOObject : employeeContractOtherVOs )
            {
               ( ( ActionForm ) employeeContractOtherVOObject ).reset( null, request );
            }
         }

         request.setAttribute( "employeeContractOtherVOs", employeeContractOtherVOs );
         request.setAttribute( "numberOfContractOther", employeeContractOtherVOs == null ? 0 : employeeContractOtherVOs.size() );

         // ���ع�Ա�ɱ�
         request.setAttribute( "employeeContractSettlementVOs", null );
         request.setAttribute( "numberOfContractSettlement", "0" );

         // ��ʼ��Service�ӿ�
         final EmployeeContractTempService employeeContractTempService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );
         EmployeeContractVO employeeContractVO = employeeContractTempService.getEmployeeContractTempVOByContractId( contractId );

         //���ظ����б�
         if ( employeeContractVO == null )
         {
            employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setFlag( request.getParameter( "flag" ) );
         }

         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         employeeContractVO.reset( null, request );

         request.setAttribute( "employeeContractForm", employeeContractVO );
         request.setAttribute( "numberOfContractAttachment", employeeContractVO.getAttachmentArray() != null ? employeeContractVO.getAttachmentArray().length : 0 );

         // ��������������б�
         final String comeFrom = request.getParameter( "comeFrom" );

         //���ص�н��Ϣ
         final EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService = ( EmployeeSalaryAdjustmentService ) getService( "employeeSalaryAdjustmentService" );
         EmployeeSalaryAdjustmentVO salaryAdjustmentVO = new EmployeeSalaryAdjustmentVO();
         salaryAdjustmentVO.setLocale( getLocale( request ) );
         salaryAdjustmentVO.setAccountId( getAccountId( request, response ) );
         salaryAdjustmentVO.setClientId( getClientId( request, response ) );
         salaryAdjustmentVO.setCorpId( getCorpId( request, response ) );
         salaryAdjustmentVO.setContractId( contractId );
         salaryAdjustmentVO.setStatus( "5" );

         final PagedListHolder salaryAdjustmentHolder = new PagedListHolder();
         // ���뵱ǰֵ����
         salaryAdjustmentHolder.setObject( salaryAdjustmentVO );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         employeeSalaryAdjustmentService.getSalaryAdjustmentVOsByCondition( salaryAdjustmentHolder, false );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( salaryAdjustmentHolder, request );
         // Holder��д��Request����
         request.setAttribute( "salaryAdjustmentHolder", salaryAdjustmentHolder );

         if ( "workflow".equals( comeFrom ) )
         {
            return mapping.findForward( "workflowEmployeeContractSpecialInfo" );
         }

         return mapping.findForward( "manageEmployeeContractSpecialInfo" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   public ActionForward list_detail( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         final String accessAction = getAccessAction( request, response );

         // ��õ�ǰҳ
         final String page = getPage( request );

         // ��ʼ��Service�ӿ�
         final EmployeeContractTempService employeeContractService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // ���Action Form
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         // ����OrderId��������
         employeeContractVO.setOrderId( KANUtil.filterEmpty( employeeContractVO.getOrderId(), "0" ) );
         // ���SubAction
         final String subAction = getSubAction( form );
         // ���û��ָ��������Ĭ�ϰ�employeeId����
         if ( employeeContractVO.getSortColumn() == null || employeeContractVO.getSortColumn().isEmpty() )
         {
            employeeContractVO.setSortColumn( "tempStatus,contractId" );
            employeeContractVO.setSortOrder( "desc" );
         }

         // SubAction����
         dealSubAction( employeeContractVO, mapping, form, request, response );

         employeeContractVO.setFlag( "2" );
         request.setAttribute( "flag", "2" );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // ������������ȣ���ôSubAction������Search Object��Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ���뵱ǰҳ
            pagedListHolder.setPage( page );
            // ����Object
            pagedListHolder.setObject( employeeContractVO );
            // ����ҳ���¼����
            pagedListHolder.setPageSize( getPageSize( request, accessAction ) );
            // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
            employeeContractService.getEmployeeContractTempVOsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
                  : isPaged( request, accessAction ) );
            // ˢ��Holder�����ʻ���ֵ
            refreshHolder( pagedListHolder, request );
         }
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "role", getRole( request, response ) );

         if ( new Boolean( getAjax( request ) ) )
         {
            return mapping.findForward( "listEmployeeContractTempUpdateTableInHouse" );
         }

         // ��ת���б����
         return mapping.findForward( "listEmployeeContractTempUpdateInHouse" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}

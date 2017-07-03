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
* 项目名称：HRO_V1  
* 类名称：EmployeeContractAction  
* 类描述：  
* 创建人：Jixiang   
*   
*/
public class EmployeeContractTempAction extends BaseAction
{
   // 劳动合同
   public static String accessActionLabor = "HRO_BIZ_EMPLOYEE_LABOR_CONTRACT";

   // 服务协议
   public static String ACCESS_ACTION_SERVICE = "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT";

   // 劳动合同（In House）
   public static String ACCESS_ACTION_SERVICE_IN_HOUSE = "HRO_BIZ_EMPLOYEE_SERVICE_CONTRACT_IN_HOUSE";

   // 当前Action对应的Access Action
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

         // 获得当前页
         final String page = getPage( request );

         // 初始化Service接口
         final EmployeeContractTempService employeeContractService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // 获得Action Form
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         // 过滤OrderId搜索条件
         employeeContractVO.setOrderId( KANUtil.filterEmpty( employeeContractVO.getOrderId(), "0" ) );
         // 获得SubAction
         final String subAction = getSubAction( form );
         // 如果没有指定排序则默认按employeeId排序
         if ( employeeContractVO.getSortColumn() == null || employeeContractVO.getSortColumn().isEmpty() )
         {
            employeeContractVO.setSortColumn( "tempStatus,contractId" );
            employeeContractVO.setSortOrder( "desc" );
         }

         // SubAction处理
         dealSubAction( employeeContractVO, mapping, form, request, response );

         employeeContractVO.setFlag( "2" );
         request.setAttribute( "flag", "2" );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 传入当前页
            pagedListHolder.setPage( page );
            // 设置Object
            pagedListHolder.setObject( employeeContractVO );
            // 设置页面记录条数
            pagedListHolder.setPageSize( getPageSize( request, accessAction ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            employeeContractService.getEmployeeContractTempVOsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
                  : isPaged( request, accessAction ) );
            // 刷新Holder，国际化传值
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
         // 如果是In House登录
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            // 跳转到列表界面
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
    * 更新，直接update正式表里的金额
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
         // 获取ActionForm
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         // 初始化Service接口
         final EmployeeContractTempService employeeContractTempService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // 获得勾选ID
         final String contractIds = employeeContractVO.getSelectedIds();

         // 存在勾选ID
         if ( KANUtil.filterEmpty( contractIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = contractIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
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
    * 退回,物理删除temp表
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
         // 获取ActionForm
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         final EmployeeContractTempService employeeContractTempService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // 获得勾选ID
         final String batchIds = employeeContractVO.getSelectedIds();

         // 存在勾选ID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
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
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final EmployeeContractTempService employeeContractTempService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // 获得当前主键
         String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( contractId == null || contractId.trim().isEmpty() )
         {
            contractId = ( ( EmployeeContractVO ) form ).getContractId();
         }

         // 获得EmployeeContractVO
         final EmployeeContractVO employeeContractVO = employeeContractTempService.getEmployeeContractTempVOByContractId( contractId );
         employeeContractVO.setSubAction( VIEW_OBJECT );
         employeeContractVO.reset( null, request );
         employeeContractVO.setComeFrom( ( ( EmployeeContractVO ) form ).getComeFrom() );

         request.setAttribute( "employeeContractTempForm", employeeContractVO );

         final String flag = employeeContractVO.getFlag();

         if ( flag != null && "2".equals( flag ) && !KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
         {
            // 跳转到服务协议新建界面
            return mapping.findForward( "manageEmployeeContractTemp" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      if ( KANConstants.ROLE_IN_HOUSE.equalsIgnoreCase( getRole( request, null ) ) )
      {
         // 跳转到 劳动合同 IN HOUSE
         return mapping.findForward( "manageEmployeeContractTempInHouse" );
      }
      else
      {
         // 跳转到劳动合同新建界面
         return mapping.findForward( "manageEmployeeContractTemp" );
      }

   }

   public ActionForward to_objectModify_update( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final EmployeeContractTempService employeeContractTempService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // 获得当前主键
         String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( contractId == null || contractId.trim().isEmpty() )
         {
            contractId = ( ( EmployeeContractVO ) form ).getContractId();
         }

         // 获得EmployeeContractVO
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

      // 跳转到劳动合同新建界面
      return mapping.findForward( "manageEmployeeContractTempUpdateInHouse" );
   }

   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化ContractId
         final String contractId = KANUtil.decodeString( request.getParameter( "contractId" ) );

         //  加载薪酬方案列表
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

         //  加载社保方案列表
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

         //  加载社商报案列表
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

         //  加载休假方案列表
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

         //  加载加班方案列表
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

         // 加载雇员其他设置列表
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

         // 加载雇员成本
         request.setAttribute( "employeeContractSettlementVOs", null );
         request.setAttribute( "numberOfContractSettlement", "0" );

         // 初始化Service接口
         final EmployeeContractTempService employeeContractTempService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );
         EmployeeContractVO employeeContractVO = employeeContractTempService.getEmployeeContractTempVOByContractId( contractId );

         //加载附件列表
         if ( employeeContractVO == null )
         {
            employeeContractVO = new EmployeeContractVO();
            employeeContractVO.setFlag( request.getParameter( "flag" ) );
         }

         // 刷新对象，初始化对象列表及国际化
         employeeContractVO.reset( null, request );

         request.setAttribute( "employeeContractForm", employeeContractVO );
         request.setAttribute( "numberOfContractAttachment", employeeContractVO.getAttachmentArray() != null ? employeeContractVO.getAttachmentArray().length : 0 );

         // 工作流审批里的列表
         final String comeFrom = request.getParameter( "comeFrom" );

         //加载调薪信息
         final EmployeeSalaryAdjustmentService employeeSalaryAdjustmentService = ( EmployeeSalaryAdjustmentService ) getService( "employeeSalaryAdjustmentService" );
         EmployeeSalaryAdjustmentVO salaryAdjustmentVO = new EmployeeSalaryAdjustmentVO();
         salaryAdjustmentVO.setLocale( getLocale( request ) );
         salaryAdjustmentVO.setAccountId( getAccountId( request, response ) );
         salaryAdjustmentVO.setClientId( getClientId( request, response ) );
         salaryAdjustmentVO.setCorpId( getCorpId( request, response ) );
         salaryAdjustmentVO.setContractId( contractId );
         salaryAdjustmentVO.setStatus( "5" );

         final PagedListHolder salaryAdjustmentHolder = new PagedListHolder();
         // 传入当前值对象
         salaryAdjustmentHolder.setObject( salaryAdjustmentVO );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeSalaryAdjustmentService.getSalaryAdjustmentVOsByCondition( salaryAdjustmentHolder, false );
         // 刷新Holder，国际化传值
         refreshHolder( salaryAdjustmentHolder, request );
         // Holder需写入Request对象
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

         // 获得当前页
         final String page = getPage( request );

         // 初始化Service接口
         final EmployeeContractTempService employeeContractService = ( EmployeeContractTempService ) getService( "employeeContractTempService" );

         // 获得Action Form
         final EmployeeContractVO employeeContractVO = ( EmployeeContractVO ) form;

         // 过滤OrderId搜索条件
         employeeContractVO.setOrderId( KANUtil.filterEmpty( employeeContractVO.getOrderId(), "0" ) );
         // 获得SubAction
         final String subAction = getSubAction( form );
         // 如果没有指定排序则默认按employeeId排序
         if ( employeeContractVO.getSortColumn() == null || employeeContractVO.getSortColumn().isEmpty() )
         {
            employeeContractVO.setSortColumn( "tempStatus,contractId" );
            employeeContractVO.setSortOrder( "desc" );
         }

         // SubAction处理
         dealSubAction( employeeContractVO, mapping, form, request, response );

         employeeContractVO.setFlag( "2" );
         request.setAttribute( "flag", "2" );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pagedListHolder = new PagedListHolder();

         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 传入当前页
            pagedListHolder.setPage( page );
            // 设置Object
            pagedListHolder.setObject( employeeContractVO );
            // 设置页面记录条数
            pagedListHolder.setPageSize( getPageSize( request, accessAction ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            employeeContractService.getEmployeeContractTempVOsByCondition( pagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
                  : isPaged( request, accessAction ) );
            // 刷新Holder，国际化传值
            refreshHolder( pagedListHolder, request );
         }
         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "role", getRole( request, response ) );

         if ( new Boolean( getAjax( request ) ) )
         {
            return mapping.findForward( "listEmployeeContractTempUpdateTableInHouse" );
         }

         // 跳转到列表界面
         return mapping.findForward( "listEmployeeContractTempUpdateInHouse" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}

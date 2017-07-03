package com.kan.hro.web.actions.biz.settlement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.TaxVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientOrderDTO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.settlement.BatchTempVO;
import com.kan.hro.domain.biz.settlement.BatchVO;
import com.kan.hro.domain.biz.settlement.OrderDetailTempVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderTempVO;
import com.kan.hro.domain.biz.settlement.ServiceContractTempVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.settlement.BatchService;
import com.kan.hro.service.inf.biz.settlement.BatchTempService;
import com.kan.hro.service.inf.biz.settlement.OrderDetailTempService;
import com.kan.hro.service.inf.biz.settlement.OrderHeaderTempService;
import com.kan.hro.service.inf.biz.settlement.ServiceContractTempService;

/**   
 * 类名称：SettlementAction  
 * 类描述：结算操作
 * 创建人：Kevin  
 * 创建时间：2013-9-13  
 */
public class SettlementTempAction extends BaseAction
{

   // 当前Action对应的Access Action
   public final static String accessAction = "HRO_SETTLE_ORDER_BATCH_TEMP";

   /**
    * List Estimation
    * 
    *	显示预算批次列表，操作TEMP
    * 结算人员可以按批次或服务协议退回，任何退回物理删除TEMP表数据
    * TEMP表中对应的服务协议不能被修改（锁定服务协议，只能查看）
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-23
   public ActionForward list_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );

         // 获得Action Form
         final BatchTempVO batchTempVO = ( BatchTempVO ) form;
         //处理数据权限
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, batchTempVO );
         
         // 设置相关属性
         batchTempVO.setPageFlag( BatchTempService.BATCH );
         // 需要设置当前用户AccountId
         batchTempVO.setAccountId( getAccountId( request, response ) );
         // 获得SubAction
         final String subAction = getSubAction( form );
         // 添加自定义搜索内容
         batchTempVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         decodedObject( batchTempVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder batchTempHolder = new PagedListHolder();
         // 传入当前页
         batchTempHolder.setPage( page );

         // 如果没有指定排序则默认按 BatchId排序
         if ( batchTempVO.getSortColumn() == null || batchTempVO.getSortColumn().isEmpty() )
         {
            batchTempVO.setSortColumn( "batchId" );
            batchTempVO.setSortOrder( "desc" );
         }

         // 传入当前值对象
         batchTempHolder.setObject( batchTempVO );
         // 设置页面记录条数
         batchTempHolder.setPageSize( listPageSize_medium );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         batchTempService.getBatchTempVOsByCondition( batchTempHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // 刷新Holder，国际化传值
         refreshHolder( batchTempHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "batchTempHolder", batchTempHolder );
         // 写入pageFlag
         request.setAttribute( "pageFlag", BatchTempService.BATCH );

         // 如果是ajax请求
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listBatchTempTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listBatchTemp" );
   }

   /**
    * To Estimation Creation Page
    * 
    * 转向创建预算批次界面，操作TEMP
    * 选择条件参考Batch
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-23
   public ActionForward to_estimationNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 初始化Service接口
      final BatchService batchService = ( BatchService ) getService( "batchService" );
      final BatchVO BatchVO = batchService.getLastestBatchVOByAccountId( getAccountId( request, response ) );

      // 设置Account Period
      if ( BatchVO != null && BatchVO.getAccountPeriod() != null )
      {
         ( ( BatchTempVO ) form ).setAccountPeriod( BatchVO.getAccountPeriod() );
      }

      // 设置Sub Action
      ( ( BatchTempVO ) form ).setSubAction( CREATE_OBJECT );

      // 标记是批次新增页面
      request.setAttribute( "pageFlag", "none" );

      // 跳转到新建界面
      return mapping.findForward( "manageBatchTemp" );
   }

   /**
    * Add Estimation
    * 
    * 创建预算批次，操作TEMP
    * 结算人员可以按批次或服务协议退回，任何退回物理删除TEMP表数据
    * TEMP表中对应的服务协议不能被修改（锁定服务协议，只能查看）
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-23
   public ActionForward add_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            final List< String > flags = new ArrayList< String >();

            // 初始化Service接口
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
            final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );

            // 获得ActionForm
            final BatchTempVO batchTempVO = ( BatchTempVO ) form;
            batchTempVO.setCreateBy( getUserId( request, response ) );
            batchTempVO.setModifyBy( getUserId( request, response ) );

            // 批次运算开始时间设置
            batchTempVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

            // 保存自定义Column
            batchTempVO.setRemark1( saveDefineColumns( request, "" ) );

            // 按照界面搜索条件获取符合的订单DTO
            final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
            clientOrderHeaderVO.setAccountId( batchTempVO.getAccountId() );
            clientOrderHeaderVO.setEntityId( batchTempVO.getEntityId() );
            clientOrderHeaderVO.setBusinessTypeId( batchTempVO.getBusinessTypeId() );
            clientOrderHeaderVO.setClientId( batchTempVO.getClientId() );
            clientOrderHeaderVO.setOrderHeaderId( batchTempVO.getOrderId() );
            clientOrderHeaderVO.setEmployeeContractId( batchTempVO.getContractId() );
            clientOrderHeaderVO.setMonthly( batchTempVO.getMonthly() );
            // 判断结算是否包含工资
            if ( batchTempVO.getContainSalary() != null && batchTempVO.getContainSalary().trim().equals( "1" ) )
            {
               flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_SALARY );
            }
            // 判断结算是否包含社保
            if ( batchTempVO.getContainSB() != null && batchTempVO.getContainSB().trim().equals( "1" ) )
            {
               flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_SB );
            }
            // 判断结算是否包含商保
            if ( batchTempVO.getContainCB() != null && batchTempVO.getContainCB().trim().equals( "1" ) )
            {
               flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_CB );
            }
            // 判断结算是否包含其他
            if ( batchTempVO.getContainOther() != null && batchTempVO.getContainOther().trim().equals( "1" ) )
            {
               flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_OTHER );
            }
            // 判断结算是否包含服务费
            if ( batchTempVO.getContainServiceFee() != null && batchTempVO.getContainServiceFee().trim().equals( "1" ) )
            {
               flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_SERVICE_FEE );
            }
            clientOrderHeaderVO.setSettlementFlags( flags );
            
            //处理数据权限
            setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, clientOrderHeaderVO );
            

            final List< ClientOrderDTO > clientOrderDTOs = clientOrderHeaderService.getClientOrderDTOsByCondition( clientOrderHeaderVO );

            // 遍历并逐个计算订单
            if ( clientOrderDTOs != null && clientOrderDTOs.size() > 0 )
            {
               for ( ClientOrderDTO clientOrderDTO : clientOrderDTOs )
               {
                  clientOrderDTO.calculateSettlement( flags );
               }

               // 批次运算结束时间设置（预设）
               batchTempVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

               // 调用Service方法存储数据（需要考虑Transaction）
               final int rows = batchTempService.insertBatchTemp( batchTempVO, clientOrderDTOs );

               if ( rows > 0 )
               {
                  // 返回添加成功标记
                  success( request, null, "成功创建批次 " + batchTempVO.getBatchId() + " ！" );
               }
               else
               {
                  // 返回警告标记
                  warning( request, null, "批次未创建。" + ( getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) ? "雇员" : "员工" ) + "已被处理或数据不完整！" );
               }
            }
            else
            {
               // 返回警告标记
               warning( request, null, "批次未创建。" + ( getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) ? "雇员" : "员工" ) + "已被处理或数据不完整！" );
            }
         }
         else
         {
            // 返回失败标记
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
         }

         // 清空Form条件
         ( ( BatchTempVO ) form ).reset();
         ( ( BatchTempVO ) form ).setBatchId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_estimation( mapping, form, request, response );
   }

   /**
    * Submit Estimation
    * 
    * 提交预算批次，TEMP表数据被复制到永久表并且TEMP数据被删除
    * 提交后数据无法被修改，任何差错只能调整
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {

         // 获取的 BatchTempVO
         final BatchTempVO batchTempVO = ( BatchTempVO ) form;
         batchTempVO.setCreateBy( getUserId( request, response ) );
         batchTempVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );

         // 提交
         batchTempService.submitBatchTemp( batchTempVO );

         // 清除Selected IDs和子Action
         ( ( BatchTempVO ) form ).setSelectedIds( "" );

         // 根据pageFlag 跳转
         return forward( batchTempVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Rollback Estimation
    * 
    * 退回预算批次或服务协议 - TEMP表数据被物理清除
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-30
   public ActionForward rollback_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得Action Form
         final BatchTempVO batchTempVO = ( BatchTempVO ) form;
         batchTempVO.setCreateBy( getUserId( request, response ) );
         batchTempVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );

         // 退回
         batchTempService.rollbackBatchTemp( batchTempVO );

         // 清除Selected IDs和子Action
         ( ( BatchTempVO ) form ).setSelectedIds( "" );

         // 根据pageFlag 跳转
         return forward( batchTempVO.getPageFlag(), mapping, form, request, response );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Forward
    *
    * 根据pageFlag 跳转
    * @param pageFlag
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-29
   private ActionForward forward( final String pageFlag, final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 重设排序
      ( ( BatchTempVO ) form ).setSortColumn( null );

      if ( pageFlag.equalsIgnoreCase( BatchTempService.HEADER ) )
      {
         OrderHeaderTempVO orderHeaderTemp = new OrderHeaderTempVO();
         orderHeaderTemp.reset();
         orderHeaderTemp.setBatchId( "" );
         return new SettlementHeaderTempAction().list_object( mapping, orderHeaderTemp, request, response );
      }
      else if ( pageFlag.equalsIgnoreCase( BatchTempService.CONTRACT ) )
      {
         ServiceContractTempVO serviceContractTemp = new ServiceContractTempVO();
         serviceContractTemp.reset();
         serviceContractTemp.setBatchId( "" );
         return new SettlementContractTempAction().list_object( mapping, serviceContractTemp, request, response );
      }
      else if ( pageFlag.equalsIgnoreCase( BatchTempService.DETAIL ) )
      {
         return to_contractDetail( mapping, form, request, response );
      }
      else
      {
         return list_estimation( mapping, form, request, response );
      }
   }

   /**
    * To Batch Detail
    * 
    * 显示批次及订单列表
    * 需要显示当前批次的汇总数
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-23
   public ActionForward to_batchDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderTempService orderHeaderTempService = ( OrderHeaderTempService ) getService( "orderHeaderTempService" );

         // 获得当前主键（批次信息）
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得当前主键对象
         final BatchTempVO batchTempVO = batchTempService.getBatchTempVOByBatchId( batchId );

         if ( batchTempVO != null )
         {
            // 设置页面的PageFlag
            batchTempVO.setPageFlag( BatchTempService.HEADER );
            // 刷新VO对象，初始化对象列表及国际化
            batchTempVO.reset( null, request );
            request.setAttribute( "batchTempForm", batchTempVO );

            // 如果批次是按客户结算的，装载客户对象
            if ( batchTempVO.getClientId() != null && !batchTempVO.getClientId().trim().equals( "" ) )
            {
               final ClientVO clientVO = clientService.getClientVOByClientId( batchTempVO.getClientId() );
               clientVO.reset( null, request );
               request.setAttribute( "clientVO", clientVO );
            }
         }

         /**
          * 获取订单列表
          */
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder headerTempListHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 传入当前页
         headerTempListHolder.setPage( page );
         // 初始化查询对象
         OrderHeaderTempVO orderHeaderTempVO = new OrderHeaderTempVO();
         // 设置相关属性值
         orderHeaderTempVO.setBatchId( batchId );
         orderHeaderTempVO.setAccountId( getAccountId( request, response ) );
         // 传入排序相关字段
         orderHeaderTempVO.setSortColumn( request.getParameter( "sortColumn" ) );
         orderHeaderTempVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 默认按 BatchId排序
         if ( orderHeaderTempVO.getSortColumn() == null || orderHeaderTempVO.getSortColumn().trim().equals( "" ) )
         {
            orderHeaderTempVO.setSortColumn( "batchId" );
         }

         // 传入当前值对象
         headerTempListHolder.setObject( orderHeaderTempVO );
         // 设置页面记录条数
         headerTempListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         orderHeaderTempService.getOrderHeaderTempVOsByCondition( headerTempListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( headerTempListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "headerTempListHolder", headerTempListHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listHeaderTempTable" );
         }

         if ( headerTempListHolder == null || headerTempListHolder.getHolderSize() == 0 )
         {
            ( ( BatchTempVO ) form ).reset();
            ( ( BatchTempVO ) form ).setBatchId( "" );
            return list_estimation( mapping, form, request, response );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "listHeaderTemp" );
   }

   /**
    * To Header Detail
    * 
    * 显示批次、订单相关信息及服务协议列表
    * 需要显示当前服订单的汇总数
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-23
   public ActionForward to_headerDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderTempService orderHeaderTempService = ( OrderHeaderTempService ) getService( "orderHeaderTempService" );
         final ServiceContractTempService serviceContractTempService = ( ServiceContractTempService ) getService( "serviceContractTempService" );

         // 获得批次ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得批次对象
         final BatchTempVO batchTempVO = batchTempService.getBatchTempVOByBatchId( batchId );

         // 设置页面的PageFlag

         batchTempVO.setPageFlag( BatchTempService.CONTRACT );
         // 初始化对象及国际化
         batchTempVO.reset( null, request );
         request.setAttribute( "batchTempForm", batchTempVO );

         // 如果批次是按客户结算的，装载客户对象
         if ( batchTempVO.getClientId() != null && !batchTempVO.getClientId().trim().equals( "" ) )
         {
            final ClientVO clientVO = clientService.getClientVOByClientId( batchTempVO.getClientId() );
            clientVO.reset( null, request );
            request.setAttribute( "clientVO", clientVO );
         }

         // 获得订单流水ID
         final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderHeaderId" ) );
         // 获得订单流水对象
         final OrderHeaderTempVO orderHeaderTempVO = orderHeaderTempService.getOrderHeaderTempVOByOrderHeaderId( orderHeaderId );

         if ( orderHeaderTempVO != null )
         {
            // 初始化对象及国际化
            orderHeaderTempVO.reset( null, request );
            request.setAttribute( "orderHeaderTempVO", orderHeaderTempVO );
            // 如果订单存在TaxId，装载Tax对象
            if ( orderHeaderTempVO.getTaxId() != null && !orderHeaderTempVO.getTaxId().trim().equals( "" ) )
            {
               final TaxVO taxVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxVOByTaxId( orderHeaderTempVO.getTaxId() );
               taxVO.reset( null, request );
               request.setAttribute( "taxVO", taxVO );
            }
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder contractTempHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 传入当前页
         contractTempHolder.setPage( page );
         // 初始化查询对象
         final ServiceContractTempVO serviceContractTempVO = new ServiceContractTempVO();
         // 设置相关属性值
         serviceContractTempVO.setOrderHeaderId( orderHeaderId );
         // 传入排序相关字段
         serviceContractTempVO.setSortColumn( request.getParameter( "sortColumn" ) );
         serviceContractTempVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 默认按 订单流水ID排序
         if ( serviceContractTempVO.getSortColumn() == null || serviceContractTempVO.getSortColumn().trim().equals( "" ) )
         {
            serviceContractTempVO.setSortColumn( "orderHeaderId" );
            serviceContractTempVO.setSortOrder( "desc" );
         }

         // 传入当前值对象
         contractTempHolder.setObject( serviceContractTempVO );
         // 设置页面记录条数
         contractTempHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         serviceContractTempVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         serviceContractTempService.getServiceContractTempVOsByCondition( contractTempHolder, true );
         refreshHolder( contractTempHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "contractTempHolder", contractTempHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listContractTempTable" );
         }

         if ( contractTempHolder == null || contractTempHolder.getHolderSize() == 0 )
         {
            ServiceContractTempVO serviceContractTemp = new ServiceContractTempVO();
            serviceContractTemp.reset();
            serviceContractTemp.setBatchId( "" );
            return new SettlementContractTempAction().list_object( mapping, serviceContractTemp, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "listContractTemp" );
   }

   /**
    * To Contract Detail
    * 
    * 显示批次、订单和服务协议相关信息及明细列表
    * 需要显示当前服务协议的汇总数
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-23
   public ActionForward to_contractDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderTempService orderHeaderTempService = ( OrderHeaderTempService ) getService( "orderHeaderTempService" );
         final ServiceContractTempService serviceContractTempService = ( ServiceContractTempService ) getService( "serviceContractTempService" );
         final OrderDetailTempService orderDetailTempService = ( OrderDetailTempService ) getService( "orderDetailTempService" );

         // 获得批次ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得批次对象
         final BatchTempVO batchTempVO = batchTempService.getBatchTempVOByBatchId( batchId );

         if ( batchTempVO != null )
         {
            // 设置页面的PageFlag
            batchTempVO.setPageFlag( BatchTempService.DETAIL );
            // 初始化对象及国际化
            batchTempVO.reset( null, request );
            request.setAttribute( "batchTempForm", batchTempVO );

            // 如果批次是按客户结算的，装载客户对象
            if ( batchTempVO.getClientId() != null && !batchTempVO.getClientId().trim().equals( "" ) )
            {
               final ClientVO clientVO = clientService.getClientVOByClientId( batchTempVO.getClientId() );
               clientVO.reset( null, request );
               request.setAttribute( "clientVO", clientVO );
            }
         }

         // 获得订单流水ID
         final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderHeaderId" ) );
         // 获得订单流水对象
         final OrderHeaderTempVO orderHeaderTempVO = orderHeaderTempService.getOrderHeaderTempVOByOrderHeaderId( orderHeaderId );

         if ( orderHeaderTempVO != null )
         {
            // 初始化对象及国际化
            orderHeaderTempVO.reset( null, request );
            request.setAttribute( "orderHeaderTempVO", orderHeaderTempVO );

            // 如果订单存在TaxId，装载Tax对象
            if ( orderHeaderTempVO.getTaxId() != null && !orderHeaderTempVO.getTaxId().trim().equals( "" ) )
            {
               final TaxVO taxVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxVOByTaxId( orderHeaderTempVO.getTaxId() );
               taxVO.reset( null, request );
               request.setAttribute( "taxVO", taxVO );
            }
         }

         // 获得服务协议流水ID
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );
         // 获得服务协议流水对象
         final ServiceContractTempVO serviceContractTempVO = serviceContractTempService.getServiceContractTempVOByContractId( contractId );

         if ( serviceContractTempVO != null )
         {
            // 初始化对象及国际化
            serviceContractTempVO.reset( null, request );
            request.setAttribute( "serviceContractTempVO", serviceContractTempVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder orderDetailTempHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 传入当前页
         orderDetailTempHolder.setPage( page );
         // 初始化查询对象
         final OrderDetailTempVO orderDetailTempVO = new OrderDetailTempVO();
         // 设置相关属性值
         orderDetailTempVO.setContractId( contractId );
         // 传入排序相关字段
         orderDetailTempVO.setSortColumn( request.getParameter( "sortColumn" ) );
         orderDetailTempVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 默认按服务协议流水ID排序
         if ( orderDetailTempVO.getSortColumn() == null || orderDetailTempVO.getSortColumn().trim().equals( "" ) )
         {
            orderDetailTempVO.setSortColumn( "itemId" );
         }

         // 传入当前值对象
         orderDetailTempHolder.setObject( orderDetailTempVO );
         // 设置页面记录条数
         orderDetailTempHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         orderDetailTempVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         orderDetailTempService.getOrderDetailTempVOsByCondition( orderDetailTempHolder, true );
         refreshHolder( orderDetailTempHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "orderDetailTempHolder", orderDetailTempHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listDetailTempTable" );
         }

         if ( orderDetailTempHolder == null || orderDetailTempHolder.getHolderSize() == 0 )
         {
            ServiceContractTempVO serviceContractTemp = new ServiceContractTempVO();
            serviceContractTemp.reset();
            serviceContractTemp.setBatchId( "" );
            return new SettlementContractTempAction().list_object( mapping, serviceContractTemp, request, response );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "listDetailTemp" );
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

}

package com.kan.hro.web.actions.biz.settlement;

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
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.settlement.BatchVO;
import com.kan.hro.domain.biz.settlement.OrderDetailVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.settlement.BatchService;
import com.kan.hro.service.inf.biz.settlement.BatchTempService;
import com.kan.hro.service.inf.biz.settlement.OrderDetailService;
import com.kan.hro.service.inf.biz.settlement.OrderHeaderService;
import com.kan.hro.service.inf.biz.settlement.ServiceContractService;

/**   
 * 类名称：SettlementAction  
 * 类描述：结算操作
 * 创建人：Kevin  
 * 创建时间：2013-9-13  
 */
public class SettlementAction extends BaseAction
{

   // 当前Action对应的Access Action
   public final static String accessAction = "HRO_SETTLE_ORDER_BATCH";

   /**
    * list_estimation
    * 
    *	显示预算批次列表
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final BatchService batchService = ( BatchService ) getService( "batchService" );

         // 获得Action Form
         final BatchVO batchVO = ( BatchVO ) form;
         //处理数据权限
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, batchVO );
         // 设置相关属性
         batchVO.setPageFlag( BatchTempService.BATCH );
         // 需要设置当前用户AccountId
         batchVO.setAccountId( getAccountId( request, response ) );
         // 获得SubAction
         final String subAction = getSubAction( form );
         // 添加自定义搜索内容
         batchVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         decodedObject( batchVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder batchHolder = new PagedListHolder();
         // 传入当前页
         batchHolder.setPage( page );

         // 如果没有指定排序则默认按 batchId排序
         if ( batchVO.getSortColumn() == null || batchVO.getSortColumn().trim().equals( "" ) )
         {
            batchVO.setSortColumn( "batchId" );
            batchVO.setSortOrder( "desc" );
         }

         // 传入当前值对象
         batchHolder.setObject( batchVO );
         // 设置页面记录条数
         batchHolder.setPageSize( listPageSize_medium );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         batchService.getBatchVOsByCondition( batchHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // 刷新Holder，国际化传值
         refreshHolder( batchHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "batchHolder", batchHolder );
         // 写入pageFlag
         request.setAttribute( "pageFlag", BatchTempService.BATCH );

         // 如果是ajax请求
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listBatch" );
   }

   /**
    * to_estimationNew
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
   public ActionForward to_estimationNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no user
      return null;
   }

   /**
    * add_estimation
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
   public ActionForward add_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no user
      return null;
   }

   /**
    * submit_estimation
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
      // no use
      return null;
   }

   /**
    * rollback_estimation
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
   public ActionForward rollback_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
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
         final BatchService batchService = ( BatchService ) getService( "batchService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderService orderHeaderService = ( OrderHeaderService ) getService( "orderHeaderService" );

         // 获得当前主键（批次信息）
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得当前主键对象
         final BatchVO batchVO = batchService.getBatchVOByBatchId( batchId );

         // 设置页面的PageFlag
         batchVO.setPageFlag( BatchTempService.HEADER );

         // 刷新VO对象，初始化对象列表及国际化
         batchVO.reset( null, request );
         request.setAttribute( "batchForm", batchVO );

         // 如果批次是按客户结算的，装载客户对象
         if ( batchVO.getClientId() != null && !batchVO.getClientId().trim().equals( "" ) )
         {
            final ClientVO clientVO = clientService.getClientVOByClientId( batchVO.getCorpId() );
            clientVO.reset( null, request );
            request.setAttribute( "clientVO", clientVO );
         }

         /**
          * 获取订单列表
          */
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder headerListHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 传入当前页
         headerListHolder.setPage( page );
         // 初始化查询对象
         OrderHeaderVO orderHeaderVO = new OrderHeaderVO();
         // 设置相关属性值
         orderHeaderVO.setBatchId( batchId );
         orderHeaderVO.setAccountId( getAccountId( request, response ) );
         // 传入排序相关字段
         orderHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
         orderHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 默认按 BatchId排序
         if ( orderHeaderVO.getSortColumn() == null || orderHeaderVO.getSortColumn().trim().equals( "" ) )
         {
            orderHeaderVO.setSortColumn( "batchId" );
         }

         // 传入当前值对象
         headerListHolder.setObject( orderHeaderVO );
         // 设置页面记录条数
         headerListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         orderHeaderService.getOrderHeaderVOsByCondition( headerListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( headerListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "headerListHolder", headerListHolder );
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "listHeader" );
   }

   /**
    * to_HeaderDetail
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
         final BatchService batchService = ( BatchService ) getService( "batchService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderService orderHeaderService = ( OrderHeaderService ) getService( "orderHeaderService" );
         final ServiceContractService serviceContractService = ( ServiceContractService ) getService( "serviceContractService" );

         // 获得批次ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得批次对象
         final BatchVO batchVO = batchService.getBatchVOByBatchId( batchId );

         // 设置页面的PageFlag
         batchVO.setPageFlag( BatchTempService.CONTRACT );
         // 初始化对象及国际化
         batchVO.reset( null, request );
         request.setAttribute( "batchForm", batchVO );

         // 如果批次是按客户结算的，装载客户对象
         if ( batchVO.getClientId() != null && !batchVO.getClientId().trim().equals( "" ) )
         {
            final ClientVO clientVO = clientService.getClientVOByClientId( batchVO.getClientId() );
            clientVO.reset( null, request );
            request.setAttribute( "clientVO", clientVO );
         }

         // 获得订单流水ID
         final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderHeaderId" ) );
         // 获得订单流水对象
         final OrderHeaderVO orderHeaderVO = orderHeaderService.getOrderHeaderVOByOrderHeaderId( orderHeaderId );
         // 初始化对象及国际化
         orderHeaderVO.reset( null, request );
         request.setAttribute( "orderHeaderVO", orderHeaderVO );

         // 如果订单存在TaxId，装载Tax对象
         if ( orderHeaderVO.getTaxId() != null && !orderHeaderVO.getTaxId().trim().equals( "" ) )
         {
            final TaxVO taxVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxVOByTaxId( orderHeaderVO.getTaxId() );
            taxVO.reset( null, request );
            request.setAttribute( "taxVO", taxVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder contractHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 传入当前页
         contractHolder.setPage( page );
         // 初始化查询对象
         final ServiceContractVO serviceContractVO = new ServiceContractVO();
         // 设置相关属性值
         serviceContractVO.setOrderHeaderId( orderHeaderId );
         // 传入排序相关字段
         serviceContractVO.setSortColumn( request.getParameter( "sortColumn" ) );
         serviceContractVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 默认按 订单流水ID排序
         if ( serviceContractVO.getSortColumn() == null || serviceContractVO.getSortColumn().trim().equals( "" ) )
         {
            serviceContractVO.setSortColumn( "orderHeaderId" );
            serviceContractVO.setSortOrder( "desc" );
         }

         // 传入当前值对象
         contractHolder.setObject( serviceContractVO );
         // 设置页面记录条数
         contractHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         serviceContractVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         serviceContractService.getServiceContractVOsByCondition( contractHolder, true );
         refreshHolder( contractHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "contractHolder", contractHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listContractTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "listContract" );
   }

   /**
    * to_contractDetail
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
         final BatchService batchService = ( BatchService ) getService( "batchService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderService orderHeaderService = ( OrderHeaderService ) getService( "orderHeaderService" );
         final ServiceContractService serviceContractService = ( ServiceContractService ) getService( "serviceContractService" );
         final OrderDetailService orderDetailService = ( OrderDetailService ) getService( "orderDetailService" );

         // 获得批次ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得批次对象
         final BatchVO batchVO = batchService.getBatchVOByBatchId( batchId );

         // 设置页面的PageFlag
         batchVO.setPageFlag( BatchTempService.DETAIL );
         // 初始化对象及国际化
         batchVO.reset( null, request );
         request.setAttribute( "batchForm", batchVO );

         // 如果批次是按客户结算的，装载客户对象
         if ( batchVO.getClientId() != null && !batchVO.getClientId().trim().equals( "" ) )
         {
            final ClientVO clientVO = clientService.getClientVOByClientId( batchVO.getCorpId() );
            clientVO.reset( null, request );
            request.setAttribute( "clientVO", clientVO );
         }

         // 获得订单流水ID
         final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderHeaderId" ) );
         // 获得订单流水对象
         final OrderHeaderVO orderHeaderVO = orderHeaderService.getOrderHeaderVOByOrderHeaderId( orderHeaderId );
         // 初始化对象及国际化
         orderHeaderVO.reset( null, request );
         request.setAttribute( "orderHeaderVO", orderHeaderVO );

         // 如果订单存在TaxId，装载Tax对象
         if ( orderHeaderVO.getTaxId() != null && !orderHeaderVO.getTaxId().trim().equals( "" ) )
         {
            final TaxVO taxVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxVOByTaxId( orderHeaderVO.getTaxId() );
            taxVO.reset( null, request );
            request.setAttribute( "taxVO", taxVO );
         }

         // 获得服务协议流水ID
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );
         // 获得服务协议流水对象
         final ServiceContractVO serviceContractVO = serviceContractService.getServiceContractVOByContractId( contractId );
         // 初始化对象及国际化
         serviceContractVO.reset( null, request );
         request.setAttribute( "serviceContractVO", serviceContractVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder orderDetailHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 传入当前页
         orderDetailHolder.setPage( page );
         // 初始化查询对象
         final OrderDetailVO orderDetailVO = new OrderDetailVO();
         // 设置相关属性值
         orderDetailVO.setContractId( contractId );
         // 传入排序相关字段
         orderDetailVO.setSortColumn( request.getParameter( "sortColumn" ) );
         orderDetailVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 默认按服务协议流水ID排序
         if ( orderDetailVO.getSortColumn() == null || orderDetailVO.getSortColumn().trim().equals( "" ) )
         {
            orderDetailVO.setSortColumn( "itemId" );
         }

         // 传入当前值对象
         orderDetailHolder.setObject( orderDetailVO );
         // 设置页面记录条数
         orderDetailHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         orderDetailVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         orderDetailService.getOrderDetailVOsByCondition( orderDetailHolder, true );
         refreshHolder( orderDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "orderDetailHolder", orderDetailHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "listDetail" );
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

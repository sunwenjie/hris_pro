package com.kan.hro.web.actions.biz.payment;

import java.util.Date;
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
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.payment.PaymentBatchVO;
import com.kan.hro.domain.biz.payment.PaymentDTO;
import com.kan.hro.domain.biz.payment.PaymentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.domain.biz.settlement.BatchTempVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.service.inf.biz.payment.PaymentBatchService;
import com.kan.hro.service.inf.biz.payment.PaymentHeaderService;

public class PaymentAction extends BaseAction
{

   // 特殊的java对象名
   public static final String javaObjectName = "com.kan.hro.domain.biz.payment.PayslipDTO";

   // Access Action
   public static final String ACCESS_ACTION = "HRO_PAYMENT_BATCH";

   public ActionForward export_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final PaymentBatchService paymentBatchService = ( PaymentBatchService ) getService( "paymentBatchService" );

         // 获得Action Form
         final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) form;

         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), ACCESS_ACTION, paymentBatchVO );
         setDataAuth( request, response, paymentBatchVO );

         paymentBatchVO.setOrderId( KANUtil.filterEmpty( paymentBatchVO.getOrderId(), "0" ) );
         // 设置Status值（按照StatusFlag）
         paymentBatchVO.setStatus( getStatusesByStatusFlag( paymentBatchVO.getStatusFlag() ) );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         pagedListHolder.setObject( paymentBatchVO );
         paymentBatchService.getPaymentDTOsByCondition( pagedListHolder );

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );

         return new DownloadFileAction().specialExportList( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * List Object
    *	 获得批次列表
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = getPage( request );
         // 获得是否Ajax调用
         final String ajax = getAjax( request );
         // 初始化Service接口
         final PaymentBatchService paymentBatchService = ( PaymentBatchService ) getService( "paymentBatchService" );

         // 获取页面标记（“预览” - preview；“台账 - ”confirm；“发放确认 ” - submit）
         String statusFlag = request.getParameter( "statusFlag" );
         if ( statusFlag == null )
         {
            statusFlag = PaymentBatchService.STATUS_FLAG_PREVIEW;
         }

         // 设置PageFlag和StatusFlag
         ( ( PaymentBatchVO ) form ).setStatusFlag( statusFlag );
         ( ( PaymentBatchVO ) form ).setPageFlag( PaymentBatchService.PAGE_FLAG_BATCH );

         // 获得Action Form
         final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) form;

         String accessAction = "HRO_PAYMENT_BATCH";

         if ( statusFlag.equals( PaymentBatchService.STATUS_FLAG_SUBMIT ) )
         {
            accessAction = "HRO_PAYMENT_BATCH_SUBMIT";
         }
         else if ( statusFlag.equals( PaymentBatchService.STATUS_FLAG_ISSUE ) )
         {
            accessAction = "HRO_PAYMENT_BATCH_ISSUE";
         }
         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, paymentBatchVO );
         setDataAuth( request, response, paymentBatchVO );

         request.setAttribute( "authAccessAction", accessAction );

         paymentBatchVO.setOrderId( KANUtil.filterEmpty( paymentBatchVO.getOrderId(), "0" ) );
         // 设置Status值（按照StatusFlag）
         paymentBatchVO.setStatus( getStatusesByStatusFlag( paymentBatchVO.getStatusFlag() ) );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder paymentBatchHolder = new PagedListHolder();

         // 传入当前页
         paymentBatchHolder.setPage( page );

         // 如果没有指定排序则默认按 BatchId排序
         if ( paymentBatchVO.getSortColumn() == null || paymentBatchVO.getSortColumn().isEmpty() )
         {
            paymentBatchVO.setSortColumn( "batchId" );
            paymentBatchVO.setSortOrder( "desc" );
         }

         // 传入当前值对象
         paymentBatchHolder.setObject( paymentBatchVO );
         // 设置页面记录条数
         paymentBatchHolder.setPageSize( listPageSize_medium );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         paymentBatchService.getPaymentBatchVOsByCondition( paymentBatchHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( paymentBatchHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "paymentBatchHolder", paymentBatchHolder );
         // 写入statusFlag
         request.setAttribute( "statusFlag", statusFlag );
         // 写入pageFlag
         request.setAttribute( "pageFlag", PaymentBatchService.PAGE_FLAG_BATCH );

         // 如果是In House登录，设置帐套数据
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // 显示导出按钮
         request.setAttribute( "javaObjectName", javaObjectName );
         showExportButton( mapping, form, request, response );

         // 设定权限
         setHRFunctionRole( mapping, form, request, response );

         // 如果是Ajax请求
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPaymentBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listPaymentBatch" );
   }

   /**
    * 查看批次详情
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_batchDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 初始化Service接口
      final PaymentBatchService paymentBatchService = ( PaymentBatchService ) getService( "paymentBatchService" );
      // 获得批次主键ID
      final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

      // 初始化PaymentBatchVO
      PaymentBatchVO paymentBatchVO = new PaymentBatchVO();
      paymentBatchVO.setBatchId( batchId );
      paymentBatchVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
      paymentBatchVO.setAccountId( getAccountId( request, response ) );

      final List< Object > paymentBatchVOs = paymentBatchService.getPaymentBatchVOsByCondition( paymentBatchVO );

      if ( paymentBatchVOs != null && paymentBatchVOs.size() > 0 )
      {
         paymentBatchVO = ( PaymentBatchVO ) paymentBatchVOs.get( 0 );
      }

      paymentBatchVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
      paymentBatchVO.reset( null, request );

      request.setAttribute( "paymentBatchForm", paymentBatchVO );
      request.setAttribute( "statusFlag", request.getParameter( "statusFlag" ) );
      request.setAttribute( "pageFlag", PaymentBatchService.PAGE_FLAG_HEADER );

      /**
       * PaymentHeaderDTO集合
       */
      // 初始化PagedListHolder
      PagedListHolder paymentHeaderDTOHolder = new PagedListHolder();

      // 初始化Service接口
      final PaymentHeaderService paymentHeaderService = ( PaymentHeaderService ) getService( "paymentHeaderService" );

      // 初始化PaymentHeaderVO
      final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
      paymentHeaderVO.setAccountId( getAccountId( request, response ) );
      paymentHeaderVO.setBatchId( batchId );
      paymentHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );

      // 排列
      paymentHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
      paymentHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

      // 默认排序
      if ( request.getParameter( "sortColumn" ) == null || request.getParameter( "sortColumn" ).trim().isEmpty() )
      {
         paymentHeaderVO.setSortColumn( "paymentHeaderId" );
         paymentHeaderVO.setSortOrder( "desc" );
      }

      // 设置页面
      paymentHeaderDTOHolder.setPage( getPage( request ) );
      paymentHeaderDTOHolder.setObject( paymentHeaderVO );
      // 设置页面大小
      paymentHeaderDTOHolder.setPageSize( listPageSize );

      paymentHeaderService.getPaymentHeaderDTOsByCondition( paymentHeaderDTOHolder, true );

      // Reset PaymentHeaderDTOHolder
      if ( paymentHeaderDTOHolder != null && paymentHeaderDTOHolder.getHolderSize() > 0 )
      {
         final List< Object > paymentHeaderDTOOjbects = paymentHeaderDTOHolder.getSource();

         if ( paymentHeaderDTOOjbects != null && paymentHeaderDTOOjbects.size() > 0 )
         {
            for ( Object paymentHeaderDTOOjbect : paymentHeaderDTOOjbects )
            {
               final PaymentDTO tempPaymentHeaderDTO = ( PaymentDTO ) paymentHeaderDTOOjbect;

               // Reset PaymentHeaderVO
               final PaymentHeaderVO tempPaymentHeaderVO = tempPaymentHeaderDTO.getPaymentHeaderVO();

               if ( tempPaymentHeaderVO != null )
               {
                  tempPaymentHeaderVO.reset( mapping, request );
               }

               // Reset PaymentDetailVO
               final List< PaymentDetailVO > paymentDetailVOs = tempPaymentHeaderDTO.getPaymentDetailVOs();

               if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
               {
                  for ( PaymentDetailVO tempPaymentDetailVO : paymentDetailVOs )
                  {
                     tempPaymentDetailVO.reset( mapping, request );
                  }
               }
            }
         }
      }

      request.setAttribute( "paymentHeaderDTOHolder", paymentHeaderDTOHolder );

      // 获得是否Ajax调用
      final String ajax = request.getParameter( "ajax" );

      // 显示导出按钮
      request.setAttribute( "javaObjectName", javaObjectName );
      showExportButton( mapping, form, request, response );

      // 设定权限
      setHRFunctionRole( mapping, form, request, response );

      // 如果是ajax请求或者删除操作则跳转到Table公共部分对应的jsp
      if ( new Boolean( ajax ) )
      {
         // 写入Role
         request.setAttribute( "role", getRole( request, response ) );
         return mapping.findForward( "listPaymentHeaderTable" );
      }

      // 如果全部方案状态都改变了，则跳转到上一层
      if ( paymentHeaderDTOHolder == null || paymentHeaderDTOHolder.getHolderSize() == 0 )
      {
         ( ( PaymentBatchVO ) form ).reset();
         ( ( PaymentBatchVO ) form ).setBatchId( "" );
         request.setAttribute( "paymentBatchForm", ( PaymentBatchVO ) form );
         ( ( PaymentBatchVO ) form ).setSortColumn( null );
         return list_object( mapping, form, request, response );
      }

      return mapping.findForward( "listPaymentHeader" );
   }

   /**  
    * To ObjectNew
    *	 新建批次
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( PaymentBatchVO ) form ).setSubAction( CREATE_OBJECT );

      // 如果是In House登录，设置帐套数据
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         passClientOrders( request, response );
      }

      // 跳转到新建界面
      return mapping.findForward( "managePaymentBatch" );
   }

   /**  
    * Add Object
    *	 添加批次
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2013-12-04
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( true )
         {
            // 初始化Service接口
            final PaymentBatchService paymentBatchService = ( PaymentBatchService ) getService( "paymentBatchService" );

            // 初始化批次数
            int rows = 0;

            // 获取Form
            final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) form;
            paymentBatchVO.setCreateBy( getUserId( request, response ) );
            paymentBatchVO.setModifyBy( getUserId( request, response ) );
            paymentBatchVO.setOrderId( KANUtil.filterEmpty( paymentBatchVO.getOrderId(), "0" ) );
            // 批次执行开始时间
            paymentBatchVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

            // 获取符合的结算信息
            final ServiceContractVO serviceContractVO = new ServiceContractVO();
            serviceContractVO.setAccountId( paymentBatchVO.getAccountId() );
            serviceContractVO.setEntityId( paymentBatchVO.getEntityId() );
            serviceContractVO.setBusinessTypeId( paymentBatchVO.getBusinessTypeId() );
            serviceContractVO.setClientId( paymentBatchVO.getClientId() );
            serviceContractVO.setCorpId( paymentBatchVO.getCorpId() );
            serviceContractVO.setOrderId( KANUtil.filterEmpty( paymentBatchVO.getOrderId(), "0" ) );
            serviceContractVO.setEmployeeContractId( paymentBatchVO.getContractId() );
            serviceContractVO.setEmployeeId( paymentBatchVO.getEmployeeId() );
            serviceContractVO.setMonthly( paymentBatchVO.getMonthly() );

            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               // 初始化BatchTempVO
               final BatchTempVO batchTempVO = new BatchTempVO();
               batchTempVO.reset( mapping, request );
               batchTempVO.setEntityId( paymentBatchVO.getEntityId() );
               batchTempVO.setBusinessTypeId( paymentBatchVO.getBusinessTypeId() );
               batchTempVO.setOrderId( KANUtil.filterEmpty( paymentBatchVO.getOrderId(), "0" ) );
               batchTempVO.setContractId( paymentBatchVO.getContractId() );
               batchTempVO.setMonthly( paymentBatchVO.getMonthly() );
               batchTempVO.setAccountPeriod( KANUtil.formatDate( new Date(), "yyyy-MM-dd" ) );
               batchTempVO.setContainSalary( BatchTempVO.TRUE );
               batchTempVO.setContainSB( BatchTempVO.TRUE );
               batchTempVO.setContainCB( BatchTempVO.TRUE );
               batchTempVO.setContainOther( BatchTempVO.TRUE );
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
               clientOrderHeaderVO.setCorpId( batchTempVO.getCorpId() );
               clientOrderHeaderVO.setOrderHeaderId( KANUtil.filterEmpty( paymentBatchVO.getOrderId(), "0" ) );
               clientOrderHeaderVO.setEmployeeContractId( batchTempVO.getContractId() );
               clientOrderHeaderVO.setEmployeeId( paymentBatchVO.getEmployeeId() );
               clientOrderHeaderVO.setMonthly( batchTempVO.getMonthly() );

               //处理数据权限
               //String accessAction = "HRO_PAYMENT_BATCH";
               //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, clientOrderHeaderVO );
               setDataAuth( request, response, clientOrderHeaderVO );

               // 调用Service方法存储数据（需要考虑Transaction）
               rows = paymentBatchService.insertPaymentBatchInHouse( paymentBatchVO, batchTempVO, clientOrderHeaderVO, serviceContractVO );
            }
            else
            {
               //处理数据权限
               //String accessAction = "HRO_PAYMENT_BATCH";
               //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, serviceContractVO );
               // 调用Service方法存储数据（需要考虑Transaction）
               rows = paymentBatchService.insertPaymentBatch( paymentBatchVO, serviceContractVO );
            }

            if ( rows > 0 )
            {
               // 返回添加成功标记
               success( request, null, "成功创建批次 " + paymentBatchVO.getBatchId() + " ！" );
               insertlog( request, paymentBatchVO, Operate.ADD, paymentBatchVO.getBatchId(), "工资结算创建" );
            }
            else
            {
               // 返回警告标记
               warning( request, null, "批次未创建。" + ( getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) ? "雇员" : "员工" ) + "已被处理，考勤表未提交，工资未导入或"
                     + ( getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) ? "派送协议" : "劳动合同" ) + "信息不完整！" );
            }
         }
         else
         {
            // 返回失败标记
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
         }

         // 清空Form条件
         ( ( PaymentBatchVO ) form ).reset();
         ( ( PaymentBatchVO ) form ).setBatchId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**  
    * Submit Estimation
    *	薪资提交
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward submit_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取的 BatchTempVO
         final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) form;
         paymentBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final PaymentBatchService paymentBatchService = ( PaymentBatchService ) getService( "paymentBatchService" );

         // 设置权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), ACCESS_ACTION, paymentBatchVO );
         setDataAuth( request, response, paymentBatchVO );

         // 提交
         final int returnBatchFlag = paymentBatchService.submit( paymentBatchVO );
         insertlog( request, paymentBatchVO, Operate.SUBMIT, paymentBatchVO.getBatchId(), "submit_estimation" );

         // 如果该批次方案均修改
         if ( returnBatchFlag == 0 )
         {
            // 重置查询条件
            ( ( PaymentBatchVO ) form ).setBatchId( null );
            ( ( PaymentBatchVO ) form ).setSortColumn( null );
            paymentBatchVO.setPageFlag( PaymentBatchService.PAGE_FLAG_BATCH );
         }

         // 清除Selected IDs和子Action
         ( ( PaymentBatchVO ) form ).setSelectedIds( "" );

         // 根据pageFlag 跳转
         return forward( paymentBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Issue Estimation
    *	薪资发放
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward issue_Actual( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {

         // 获取的 BatchTempVO
         final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) form;
         paymentBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final PaymentBatchService paymentBatchService = ( PaymentBatchService ) getService( "paymentBatchService" );
         // 设置权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), ACCESS_ACTION, paymentBatchVO );
         setDataAuth( request, response, paymentBatchVO );

         // 提交
         final int returnBatchFlag = paymentBatchService.submit( paymentBatchVO );
         insertlog( request, paymentBatchVO, Operate.SUBMIT, paymentBatchVO.getBatchId(), "issue_Actual" );

         // 如果该批次方案均修改
         if ( returnBatchFlag == 0 )
         {
            // 重置查询条件
            ( ( PaymentBatchVO ) form ).setBatchId( null );
            ( ( PaymentBatchVO ) form ).setSortColumn( null );
            paymentBatchVO.setPageFlag( PaymentBatchService.PAGE_FLAG_BATCH );
         }

         // 清除Selected IDs和子Action
         ( ( PaymentBatchVO ) form ).setSelectedIds( "" );

         // 根据pageFlag 跳转
         return forward( paymentBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Rollback Estimation
    *	退回
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward rollback_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取的 BatchTempVO
         final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) form;
         paymentBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final PaymentBatchService paymentBatchService = ( PaymentBatchService ) getService( "paymentBatchService" );

         // 退回
         final int rollbackBatchFlag = paymentBatchService.rollback( paymentBatchVO, getRole( request, response ) );
         insertlog( request, paymentBatchVO, Operate.ROllBACK, paymentBatchVO.getBatchId(), "rollback_estimation" );

         // 如果该批次方案均退回了
         if ( rollbackBatchFlag == 0 )
         {
            // 重置查询条件
            ( ( PaymentBatchVO ) form ).setBatchId( null );
            ( ( PaymentBatchVO ) form ).setSortColumn( null );
            paymentBatchVO.setPageFlag( PaymentBatchService.PAGE_FLAG_BATCH );
         }

         // 清除Selected IDs和子Action
         ( ( PaymentBatchVO ) form ).setSelectedIds( "" );

         // 根据pageFlag 跳转
         return forward( paymentBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Forward
    *	页面跳转控制
    *	@param pageFlag
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   private ActionForward forward( String pageFlag, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      if ( PaymentBatchService.PAGE_FLAG_HEADER.equalsIgnoreCase( pageFlag ) )
      {
         PaymentHeaderVO paymentHeader = new PaymentHeaderVO();
         paymentHeader.reset();
         paymentHeader.setBatchId( "" );
         paymentHeader.setStatus( getStatusesByStatusFlag( ( ( PaymentBatchVO ) form ).getStatusFlag() ) );
         return new PaymentHeaderAction().list_object( mapping, paymentHeader, request, response );
      }
      else
      {
         return list_object( mapping, form, request, response );
      }

   }

   // 根据StatusFlag获得状态
   private String getStatusesByStatusFlag( final String statusFlag )
   {
      // 初始化，默认为“预览”
      String status = "1";

      if ( statusFlag != null && !statusFlag.isEmpty() )
      {
         //工资台账
         if ( statusFlag.equalsIgnoreCase( PaymentBatchService.STATUS_FLAG_SUBMIT ) )
         {
            return "2";
         }
         // 发放确认
         else if ( statusFlag.equals( PaymentBatchService.STATUS_FLAG_ISSUE ) )
         {
            return "3";
         }
      }

      return status;
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

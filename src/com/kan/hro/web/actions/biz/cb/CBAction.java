package com.kan.hro.web.actions.biz.cb;

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
import com.kan.hro.domain.biz.cb.CBBatchVO;
import com.kan.hro.domain.biz.cb.CBDetailVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.ServiceContractDTO;
import com.kan.hro.service.inf.biz.cb.CBBatchService;
import com.kan.hro.service.inf.biz.cb.CBDetailService;
import com.kan.hro.service.inf.biz.cb.CBHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;

/**   
 * 类名称：CBAction  
 * 类描述：商保操作
 * 创建人：Kevin  
 * 创建时间：2013-9-13  
 */
public class CBAction extends BaseAction
{
   public static String accessAction = "HRO_CB_BATCH_PREVIEW";
   // 当前Action对应的JavaObjectName
   public static String javaObjectName = "com.kan.hro.domain.biz.cb.CBDTO";

   // 根据StatusFlag获取状态值Status
   private String getStatusesByStatusFlag( final String statusFlag )
   {
      // 初始化，默认为“预览”
      String status = "1";

      if ( statusFlag != null && !statusFlag.isEmpty() )
      {
         if ( statusFlag.equalsIgnoreCase( CBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            return "2";
         }
         else if ( statusFlag.equals( CBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            return "3,4,5";
         }
      }

      return status;
   }

   /**
    * list_estimation
    * 
    * 显示预算批次列表
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
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
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );

         /**
          * 页面显示CBBatchVO List(根据"statusFlag")
          */
         // 获取页面list标记（“预览”preview；“确认”confirm；“提交”submit）
         String statusFlag = request.getParameter( "statusFlag" );
         // 如果statusFlag 为null,默认为预览页面
         if ( statusFlag == null )
         {
            statusFlag = CBBatchService.STATUS_FLAG_PREVIEW;
         }

         // 设置Form的PageFlag和StatusFlag
         ( ( CBBatchVO ) form ).setStatusFlag( statusFlag );
         ( ( CBBatchVO ) form ).setPageFlag( CBBatchService.PAGE_FLAG_BATCH );

         // 获得Action Form
         final CBBatchVO cbBatchVO = ( CBBatchVO ) form;

         String accessAction = "HRO_CB_BATCH_PREVIEW";
         if ( statusFlag.equals( CBBatchService.STATUS_FLAG_APPROVE ) )
         {
            accessAction = "HRO_CB_BATCH_PREVIEW";
         }
         else if ( statusFlag.equals( CBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            accessAction = "HRO_CB_BATCH_CONFIRM";
         }
         else if ( statusFlag.equals( CBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            accessAction = "HRO_CB_BATCH_SUBMIT";
         }
         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, cbBatchVO );
         setDataAuth( request, response, cbBatchVO );

         cbBatchVO.setOrderId( KANUtil.filterEmpty( cbBatchVO.getOrderId(), "0" ) );
         // 根据statusFlag设置CBBatchVO的status值（1:新建，2:批准，3:确认，4:提交）-用来搜索对应状态的  CBBatchVO List
         cbBatchVO.setStatus( getStatusesByStatusFlag( cbBatchVO.getStatusFlag() ) );

         // 需要设置当前用户AccountId
         cbBatchVO.setAccountId( getAccountId( request, response ) );

         decodedObject( cbBatchVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder cbBatchHolder = new PagedListHolder();

         // 如果没有指定排序则默认按 批次流水号排序
         if ( cbBatchVO.getSortColumn() == null || cbBatchVO.getSortColumn().trim().equals( "" ) )
         {
            cbBatchVO.setSortColumn( "batchId" );
            cbBatchVO.setSortOrder( "desc" );
         }

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            cbBatchVO.setCorpId( getCorpId( request, response ) );
            // 发送帐套列表
            passClientOrders( request, response );
         }

         // 传入当前页
         cbBatchHolder.setPage( page );
         // 传入当前值对象
         cbBatchHolder.setObject( cbBatchVO );
         // 设置页面记录条数
         cbBatchHolder.setPageSize( listPageSize_medium );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         cbBatchService.getCBBatchVOsByCondition( cbBatchHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( cbBatchHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "cbBatchHolder", cbBatchHolder );
         // 写入pageFlag
         request.setAttribute( "pageFlag", CBBatchService.PAGE_FLAG_BATCH );

         // 是否显示导出按钮
         request.setAttribute( "javaObjectName", javaObjectName );
         showExportButton( mapping, form, request, response );

         /**
          * 页面转向控制
          */
         // 申报预览列表
         if ( statusFlag.equals( CBBatchService.STATUS_FLAG_PREVIEW ) )
         {
            request.setAttribute( "statusFlag", CBBatchService.STATUS_FLAG_PREVIEW );
            // 如果是ajax请求
            if ( new Boolean( ajax ) )
            {
               // 写入Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listCBBatchTablePreview" );
            }
            return mapping.findForward( "listCBBatchPreview" );
         }
         // 申报确认列表
         else if ( statusFlag.equals( CBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            request.setAttribute( "statusFlag", CBBatchService.STATUS_FLAG_CONFIRM );
            // 如果是ajax请求
            if ( new Boolean( ajax ) )
            {
               // 写入Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listCBBatchTableConfirm" );
            }
            return mapping.findForward( "listCBBatchConfirm" );
         }
         // 提交结算列表
         else if ( statusFlag.equals( CBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            request.setAttribute( "statusFlag", CBBatchService.STATUS_FLAG_SUBMIT );
            // 如果是ajax请求
            if ( new Boolean( ajax ) )
            {
               // 写入Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listCBBatchTableSubmit" );
            }
            return mapping.findForward( "listCBBatchSubmit" );
         }
         else
         {
            return mapping.findForward( "" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * to_estimationNew
    * 
    * 转向创建预算批次界面
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
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( CBBatchVO ) form ).setSubAction( CREATE_OBJECT );
      // 设置状态默认值
      ( ( CBBatchVO ) form ).setStatus( "1" );

      // 如果是从客户端登录
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         ( ( CBBatchVO ) form ).setCorpId( getCorpId( request, null ) );
         // 发送帐套列表
         passClientOrders( request, response );
      }

      // 标记是批次新增页面
      request.setAttribute( "pageFlag", "none" );

      // 跳转到新建界面
      return mapping.findForward( "manageCB" );
   }

   /**
    * Add Estimation
    * 
    * 创建预算批次，状态为新建
    * 新建后业务人员可以按批次或服务协议退回
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-09
   public ActionForward add_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 避免重复提交
      if ( this.isTokenValid( request, true ) )
      {
         // 初始化Service接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );

         // 获取Form
         final CBBatchVO cbBatchVO = ( CBBatchVO ) form;
         cbBatchVO.setAccountId( getAccountId( request, response ) );
         cbBatchVO.setCreateBy( getUserId( request, response ) );
         cbBatchVO.setModifyBy( getUserId( request, response ) );

         // 保存自定义Column
         cbBatchVO.setRemark1( saveDefineColumns( request, "" ) );
         // 批次执行开始时间
         cbBatchVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

         // 按照界面搜索条件获取符合的服务协议DTO
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         employeeContractVO.setAccountId( getAccountId( request, null ) );
         employeeContractVO.setEntityId( cbBatchVO.getEntityId() );
         employeeContractVO.setBusinessTypeId( cbBatchVO.getBusinessTypeId() );
         employeeContractVO.setCbId( cbBatchVO.getCbId() );
         employeeContractVO.setClientId( cbBatchVO.getClientId() );
         employeeContractVO.setCorpId( cbBatchVO.getCorpId() );
         employeeContractVO.setOrderId( KANUtil.filterEmpty( cbBatchVO.getOrderId(), "0" ) );
         employeeContractVO.setContractId( cbBatchVO.getContractId() );
         // EmployeeId暂时界面未用
         employeeContractVO.setEmployeeId( null );
         employeeContractVO.setMonthly( cbBatchVO.getMonthly() );
         employeeContractVO.setCbStartDate( KANUtil.formatDate( KANUtil.getLastDate( cbBatchVO.getMonthly() ), "yyyy-MM-dd" ) );
         employeeContractVO.setCbEndDate( KANUtil.formatDate( KANUtil.getFirstDate( cbBatchVO.getMonthly() ), "yyyy-MM-dd" ) );

         //处理数据权限
         //String accessAction = "HRO_CB_BATCH_PREVIEW";
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, employeeContractVO );
         setDataAuth( request, response, employeeContractVO );
         final List< ServiceContractDTO > serviceContractDTOs = employeeContractService.getServiceContractDTOsByCondition( employeeContractVO, EmployeeContractService.FLAG_CB );

         // 遍历并逐个计算服务协议
         if ( serviceContractDTOs != null && serviceContractDTOs.size() > 0 )
         {
            for ( ServiceContractDTO serviceContractDTO : serviceContractDTOs )
            {
               serviceContractDTO.calculateCB( request );
            }

            // 批次执行结束时间
            cbBatchVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

            // 调用Service方法存储商保数据（需要考虑Transaction）
            final int rows = cbBatchService.insertCBBatch( cbBatchVO, serviceContractDTOs );

            if ( rows > 0 )
            {
               // 返回添加成功标记
               success( request, null, "成功创建批次 " + cbBatchVO.getBatchId() + " ！" );
               insertlog( request, cbBatchVO, Operate.ADD, cbBatchVO.getBatchId(), null );
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
      ( ( CBBatchVO ) form ).reset();
      ( ( CBBatchVO ) form ).setBatchId( "" );

      return list_estimation( mapping, form, request, response );
   }

   /**
    * submit_estimation
    * 
    * 提交预算批次，状态为批准（通常批准有业务人员操作）
    * 批准后商保结算人员可以按批次或服务协议退回
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
         // 获取的 BatchVO
         final CBBatchVO cbBatchVO = ( CBBatchVO ) form;
         cbBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );

         // 提交至批准
         cbBatchService.submit( cbBatchVO );

         insertlog( request, cbBatchVO, Operate.SUBMIT, null, "submit_estimation:" + KANUtil.decodeSelectedIds( cbBatchVO.getSelectedIds() ) );

         // 根据pageFlag 跳转
         return forward( cbBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * doReturnActByPageFlag
    * 根据pageFlag 跳转
    * @param pageFlag
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   private ActionForward forward( String pageFlag, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      if ( pageFlag.equalsIgnoreCase( CBBatchService.PAGE_FLAG_HEADER ) )
      {
         CBHeaderVO cbHeader = new CBHeaderVO();
         cbHeader.reset();
         cbHeader.setBatchId( "" );
         cbHeader.setStatus( getStatusesByStatusFlag( ( ( CBBatchVO ) form ).getStatusFlag() ) );
         return new CBHeaderAction().list_object( mapping, cbHeader, request, response );
      }
      else if ( pageFlag.equalsIgnoreCase( CBBatchService.PAGE_FLAG_DETAIL ) )
      {
         return to_cbDetail( mapping, form, request, response );
      }
      else
      {
         return list_estimation( mapping, form, request, response );
      }
   }

   /**
    * rollback
    * 
    * 退回预算批次或服务协议 - 只有新建和批准状态才有退回功能
    * 退回记录 - 商保操作数据物理删除，服务协议中的商保状态改为“无商保”
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward rollback( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );
         // 获得Action Form
         final CBBatchVO cbBatchVO = ( CBBatchVO ) form;
         cbBatchVO.setStatus( getStatusesByStatusFlag( cbBatchVO.getStatusFlag() ) );

         // 退回
         cbBatchService.rollback( cbBatchVO );

         insertlog( request, cbBatchVO, Operate.ROllBACK, null, KANUtil.decodeSelectedIds( cbBatchVO.getSelectedIds() ) );

         // 清除Selected IDs和子Action
         ( ( CBBatchVO ) form ).setSelectedIds( "" );

         // 根据pageFlag 跳转
         return forward( cbBatchVO.getPageFlag(), mapping, form, request, response );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * submit_confirmation
    * 
    * 提交商保批次 - 至商保机构，状态为确认
    * 提交后无法退回，存在问题或商保机构反馈有偏差，调整处理
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_confirmation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取的 BatchVO
         final CBBatchVO cbBatchVO = ( CBBatchVO ) form;
         cbBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );

         // 提交至批准
         cbBatchService.submit( cbBatchVO );

         insertlog( request, cbBatchVO, Operate.SUBMIT, null, "submit_confirmation:" + KANUtil.decodeSelectedIds( cbBatchVO.getSelectedIds() ) );

         // 根据pageFlag 跳转
         return forward( cbBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * submit_settlement
    * 
    * 提交商保批次 - 至结算，状态为提交
    * 提交后结算模块可见
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward submit_settlement( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取的 BatchVO
         final CBBatchVO cbBatchVO = ( CBBatchVO ) form;
         cbBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );

         // 提交至批准
         cbBatchService.submit( cbBatchVO );

         insertlog( request, cbBatchVO, Operate.SUBMIT, null, "submit_settlement:" + KANUtil.decodeSelectedIds( cbBatchVO.getSelectedIds() ) );

         // 根据pageFlag 跳转
         return forward( cbBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * to_cbDetail
    * 
    * 显示批次、服务协议、商保方案相关信息及商保方案明细列表
    * 需要显示当前商保方案的汇总数
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_cbDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         /**
          * 获取批次、服务协议、商保方案相关信息
          */
         // 初始化Service接口
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );
         final CBHeaderService cbHeaderService = ( CBHeaderService ) getService( "cbHeaderService" );
         final CBDetailService cbDetailService = ( CBDetailService ) getService( "cbDetailService" );

         // 获得当前批次主键ID
         final String cbBatchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得服务协议主键ID
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );
         // 获得商保方案主键HeaderID
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) );

         // 初始化CBHeaderVO
         CBHeaderVO cbHeaderVO = new CBHeaderVO();
         cbHeaderVO.setBatchId( cbBatchId );
         cbHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         cbHeaderVO.setContractId( contractId );
         cbHeaderVO.setAccountId( getAccountId( request, response ) );

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            cbHeaderVO.setCorpId( getCorpId( request, response ) );
         }

         // 设置HeaderId
         cbHeaderVO.setHeaderId( headerId );

         // 提取商保方案
         final List< Object > cbHeaderVOs = cbHeaderService.getCBHeaderVOsByCondition( cbHeaderVO );

         if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
         {
            cbHeaderVO = ( CBHeaderVO ) cbHeaderVOs.get( 0 );
         }

         // 刷新VO对象，初始化对象列表及国际化
         cbHeaderVO.reset( null, request );

         // 初始化CBBatchVO
         CBBatchVO cbBatchVO = new CBBatchVO();
         cbBatchVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         cbBatchVO.setBatchId( cbBatchId );
         cbBatchVO.setAccountId( getAccountId( request, response ) );

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            cbBatchVO.setCorpId( getCorpId( request, response ) );
         }
         final List< Object > cbBatchVOs = cbBatchService.getCBBatchVOsByCondition( cbBatchVO );

         if ( cbBatchVOs != null && cbBatchVOs.size() > 0 )
         {
            cbBatchVO = ( CBBatchVO ) cbBatchVOs.get( 0 );
         }

         cbBatchVO.setPageFlag( CBBatchService.PAGE_FLAG_DETAIL );
         cbBatchVO.setStatusFlag( request.getParameter( "statusFlag" ) );
         cbBatchVO.reset( null, request );

         String accessAction = "HRO_CB_BATCH_PREVIEW";
         if ( request.getParameter( "statusFlag" ).equals( CBBatchService.STATUS_FLAG_APPROVE ) )
         {
            accessAction = "HRO_CB_BATCH_PREVIEW";
         }
         else if ( request.getParameter( "statusFlag" ).equals( CBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            accessAction = "HRO_CB_BATCH_CONFIRM";
         }
         else if ( request.getParameter( "statusFlag" ).equals( CBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            accessAction = "HRO_CB_BATCH_SUBMIT";
         }
         request.setAttribute( "authAccessAction", accessAction );
         request.setAttribute( "cbBatchForm", cbBatchVO );
         request.setAttribute( "cbHeaderVO", cbHeaderVO );

         /**
          * 获取商保方案明细列表
          */
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder cbDetailHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 传入当前页
         cbDetailHolder.setPage( page );

         // 新建CBDetailVO用于查询
         final CBDetailVO cbDetailVO = new CBDetailVO();
         cbDetailVO.setHeaderId( headerId );
         cbDetailVO.setAccountId( getAccountId( request, response ) );
         cbDetailVO.setStatus( getStatusesByStatusFlag( cbBatchVO.getStatusFlag() ) );

         // 传入排序相关字段
         cbDetailVO.setSortColumn( request.getParameter( "sortColumn" ) );
         cbDetailVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 如果没有指定排序则默认按 商保方案明细流水号排序
         if ( cbDetailVO.getSortColumn() == null || cbDetailVO.getSortColumn().trim().equals( "" ) )
         {
            cbDetailVO.setSortColumn( "itemId" );
         }

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            cbDetailVO.setCorpId( getCorpId( request, response ) );
         }

         // 传入当前值对象
         cbDetailHolder.setObject( cbDetailVO );
         // 设置页面记录条数
         cbDetailHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         cbDetailVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         cbDetailService.getCBDetailVOsByCondition( cbDetailHolder, true );
         refreshHolder( cbDetailHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "cbDetailHolder", cbDetailHolder );
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listDetailTable" );
         }

         // 如果不存在商保明细数据
         if ( cbDetailHolder == null || cbDetailHolder.getHolderSize() == 0 )
         {
            CBHeaderVO cbHeader = new CBHeaderVO();
            cbHeader.reset();
            cbHeader.setBatchId( "" );
            return new CBHeaderAction().list_object( mapping, cbHeader, request, response );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // Ajax调用
      return mapping.findForward( "listDetail" );
   }

   /**  
    * Export Object
    * 导出
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward export_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final CBBatchService cbBatchService = ( CBBatchService ) getService( "cbBatchService" );
         // 获得Action Form
         final CBBatchVO cbBatchVO = ( CBBatchVO ) form;
         // 设置状态值
         cbBatchVO.setStatus( getStatusesByStatusFlag( cbBatchVO.getStatusFlag() ) );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前值对象
         pagedListHolder.setObject( cbBatchVO );
         // 调用Service方法，引用对象返回
         cbBatchService.getCBDTOsByCondition( pagedListHolder );
         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pagedListHolder );

         return new DownloadFileAction().specialExportList( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

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

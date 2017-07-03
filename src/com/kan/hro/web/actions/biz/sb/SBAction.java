package com.kan.hro.web.actions.biz.sb;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.ServiceContractDTO;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.sb.SBBatchService;
import com.kan.hro.service.inf.biz.sb.SBDetailService;
import com.kan.hro.service.inf.biz.sb.SBHeaderService;
import com.kan.hro.service.inf.biz.vendor.VendorService;
import com.kan.hro.web.actions.biz.vendor.VendorAction;

/**   
 * 类名称：SBAction  
 * 类描述：社保操作
 * 创建人：Kevin  
 * 创建时间：2013-9-13  
 */
public class SBAction extends BaseAction
{
   // Access Action of Preview
   public static String ACCESS_ACTION_PREVIEW = "HRO_SB_BATCH_PREVIEW";
   // Access Action of Confirm
   public static String ACCESS_ACTION_CONFIRM = "HRO_SB_BATCH_CONFIRM";
   // Access Action of Submit
   public static String ACCESS_ACTION_SUBMIT = "HRO_SB_BATCH_SUBMIT";
   // 当前Action对应的JavaObjectName
   public static String javaObjectName = "com.kan.hro.domain.biz.sb.SBDTO";

   /**
    * List Estimation
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
   // Reviewed by Kevin Jin at 2013-10-31
   public ActionForward list_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, KANUtil.filterEmpty( getCorpId( request, null ) ) );

         // 判断列表是否需要添加导出功能
         if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getExportExcel() != null
               && listDTO.getListHeaderVO().getExportExcel().trim().equals( "1" ) )
         {
            request.setAttribute( "isExportExcel", "1" );
         }

         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );

         // 获取页面标记（“预览” - preview；“确认 - ”confirm；“提交 ” - submit）
         String statusFlag = request.getParameter( "statusFlag" );

         if ( statusFlag == null )
         {
            statusFlag = SBBatchService.STATUS_FLAG_PREVIEW;
         }

         // 设置PageFlag和StatusFlag
         ( ( SBBatchVO ) form ).setStatusFlag( statusFlag );
         ( ( SBBatchVO ) form ).setPageFlag( SBBatchService.PAGE_FLAG_BATCH );

         // 获得Action Form
         final SBBatchVO sbBatchVO = ( SBBatchVO ) form;

         String accessAction = "HRO_SB_BATCH_PREVIEW";
         if ( statusFlag.equals( SBBatchService.STATUS_FLAG_PREVIEW ) )
         {
            accessAction = "HRO_SB_BATCH_PREVIEW";
         }
         else if ( statusFlag.equals( SBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            accessAction = "HRO_SB_BATCH_CONFIRM";
         }
         else if ( statusFlag.equals( SBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            accessAction = "HRO_SB_BATCH_SUBMIT";
            final Iterator< MappingVO > iterators = sbBatchVO.getStatuses().iterator();
            //提交结算查询3,4,5 状态
            while ( iterators.hasNext() )
            {
               MappingVO mappingVO = iterators.next();
               if ( !mappingVO.getMappingId().equals( "0" ) && !mappingVO.getMappingId().equals( "3" ) && !mappingVO.getMappingId().equals( "4" )
                     && !mappingVO.getMappingId().equals( "5" ) )
               {
                  iterators.remove();
               }

            }

         }
         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, sbBatchVO );
         setDataAuth( request, response, sbBatchVO );
         sbBatchVO.setOrderId( KANUtil.filterEmpty( sbBatchVO.getOrderId(), "0" ) );

         if ( KANUtil.filterEmpty( sbBatchVO.getStatus(), "0" ) == null )
         {
            // 设置Status值（按照StatusFlag）
            sbBatchVO.setStatus( getStatusesByStatusFlag( sbBatchVO.getStatusFlag() ) );
         }

         // 设置当前用户AccountId
         sbBatchVO.setAccountId( getAccountId( request, response ) );

         decodedObject( sbBatchVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sbBatchHolder = new PagedListHolder();

         // 传入当前页
         sbBatchHolder.setPage( page );

         // 如果没有指定排序则默认按 BatchId排序
         if ( sbBatchVO.getSortColumn() == null || sbBatchVO.getSortColumn().isEmpty() )
         {
            sbBatchVO.setSortColumn( "batchId" );
            sbBatchVO.setSortOrder( "desc" );
         }

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbBatchVO.setCorpId( getCorpId( request, response ) );
            // 发送帐套列表
            passClientOrders( request, response );
         }

         // 添加供应商下拉框
         sbBatchVO.setVendors( new VendorAction().list_option( mapping, form, request, response ) );

         // 传入当前值对象
         sbBatchHolder.setObject( sbBatchVO );
         // 设置页面记录条数
         sbBatchHolder.setPageSize( listPageSize_medium );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         sbBatchService.getSBBatchVOsByCondition( sbBatchHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( sbBatchHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "sbBatchHolder", sbBatchHolder );
         // 写入pageFlag
         request.setAttribute( "pageFlag", SBBatchService.PAGE_FLAG_BATCH );

         /**
          * 页面转向处理
          */
         // 申报预览列表  
         if ( statusFlag.equals( SBBatchService.STATUS_FLAG_PREVIEW ) )
         {
            request.setAttribute( "statusFlag", SBBatchService.STATUS_FLAG_PREVIEW );

            // 如果是Ajax请求
            if ( new Boolean( ajax ) )
            {
               // 写入Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listSBBatchTablePreview" );
            }

            return mapping.findForward( "listSBBatchPreview" );
         }
         // 申报确认列表
         else if ( statusFlag.equals( SBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            request.setAttribute( "statusFlag", SBBatchService.STATUS_FLAG_CONFIRM );

            // 如果是Ajax请求
            if ( new Boolean( ajax ) )
            {
               // 写入Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listSBBatchTableConfirm" );
            }

            return mapping.findForward( "listSBBatchConfirm" );
         }
         // 提交结算列表
         else if ( statusFlag.equals( SBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            request.setAttribute( "statusFlag", SBBatchService.STATUS_FLAG_SUBMIT );

            // 如果是Ajax请求
            if ( new Boolean( ajax ) )
            {
               // 写入Role
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listSBBatchTableSubmit" );
            }

            return mapping.findForward( "listSBBatchSubmit" );
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
    * To Estimation New
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
   // Reviewed by Kevin Jin at 2013-10-09
   public ActionForward to_estimationNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      SBBatchVO sbBatchVO = ( SBBatchVO ) form;
      // 设置Sub Action
      sbBatchVO.setSubAction( CREATE_OBJECT );
      // 设置状态默认值
      sbBatchVO.setStatus( "1" );

      // 如果是从客户端登录
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         ( ( SBBatchVO ) form ).setCorpId( getCorpId( request, null ) );

         // 发送帐套列表
         passClientOrders( request, response );
      }

      // 标记是批次新增页面
      request.setAttribute( "pageFlag", "none" );

      // 跳转到新建界面
      return mapping.findForward( "manageSB" );
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
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );

         // 初始化输入值错误
         request.setAttribute( "errorCount", 0 );

         // 检查页面输入值（如果输入值不为空不需要验证）
         if ( KANUtil.filterEmpty( ( ( SBBatchVO ) form ).getClientId(), "0" ) != null )
         {
            checkClientId( mapping, form, request, response );
         }
         if ( KANUtil.filterEmpty( ( ( SBBatchVO ) form ).getOrderId(), "0" ) != null )
         {
            checkOrderId( mapping, form, request, response );
         }
         if ( KANUtil.filterEmpty( ( ( SBBatchVO ) form ).getContractId(), "0" ) != null )
         {
            checkEmployeeContractId( mapping, form, request, response );
         }

         // 根据是否有错误跳转页面
         if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
         {
            return to_estimationNew( mapping, form, request, response );
         }

         // 获取Form
         final SBBatchVO sbBatchVO = ( SBBatchVO ) form;

         sbBatchVO.setOrderId( KANUtil.filterEmpty( sbBatchVO.getOrderId(), "0" ) );
         sbBatchVO.setCreateBy( getUserId( request, response ) );
         sbBatchVO.setModifyBy( getUserId( request, response ) );

         // 批次执行开始时间
         sbBatchVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

         // 按照界面搜索条件获取符合的服务协议DTO
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         employeeContractVO.setAccountId( getAccountId( request, null ) );
         employeeContractVO.setEntityId( sbBatchVO.getEntityId() );
         employeeContractVO.setBusinessTypeId( sbBatchVO.getBusinessTypeId() );
         employeeContractVO.setSbCityId( sbBatchVO.getCityId() );
         employeeContractVO.setClientId( sbBatchVO.getClientId() );
         employeeContractVO.setCorpId( sbBatchVO.getCorpId() );
         employeeContractVO.setOrderId( sbBatchVO.getOrderId() );
         employeeContractVO.setContractId( sbBatchVO.getContractId() );
         // EmployeeId暂时界面未用
         employeeContractVO.setEmployeeId( null );
         employeeContractVO.setMonthly( sbBatchVO.getMonthly() );
         employeeContractVO.setSbStartDate( KANUtil.formatDate( KANUtil.getLastDate( sbBatchVO.getMonthly() ), "yyyy-MM-dd" ) );
         employeeContractVO.setSbEndDate( KANUtil.formatDate( KANUtil.getFirstDate( sbBatchVO.getMonthly() ), "yyyy-MM-dd" ) );

         //社保方案类型
         employeeContractVO.setSbType( sbBatchVO.getSbType() );
         //员工社保状态
         if ( sbBatchVO.getSbStatusArray() != null && sbBatchVO.getSbStatusArray().length > 0 )
         {
            employeeContractVO.setSbStatusArray( sbBatchVO.getSbStatusArray() );
         }
         else
         {
            employeeContractVO.setSbStatusArray( null );
         }

         //处理数据权限
         //String accessAction = "HRO_SB_BATCH_PREVIEW";
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, employeeContractVO );
         setDataAuth( request, response, employeeContractVO );

         final List< ServiceContractDTO > serviceContractDTOs = employeeContractService.getServiceContractDTOsByCondition( employeeContractVO, EmployeeContractService.FLAG_SB );

         // 遍历并逐个计算服务协议
         if ( serviceContractDTOs != null && serviceContractDTOs.size() > 0 )
         {
            for ( ServiceContractDTO serviceContractDTO : serviceContractDTOs )
            {
               serviceContractDTO.calculateSB( request );
            }

            // 批次执行结束时间
            sbBatchVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

            // 调用Service方法存储社保数据（需要考虑Transaction）
            final int rows = sbBatchService.insertSBBatch( sbBatchVO, serviceContractDTOs );

            if ( rows > 0 )
            {
               // 返回添加成功标记
               success( request, null, "成功创建批次 " + sbBatchVO.getBatchId() + " ！" );
               insertlog( request, sbBatchVO, Operate.ADD, sbBatchVO.getBatchId(), "申报预览" );
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
      ( ( SBBatchVO ) form ).reset();
      ( ( SBBatchVO ) form ).setBatchId( "" );

      return list_estimation( mapping, form, request, response );
   }

   /**
    * Submit Estimation
    * 
    * 提交预算批次，状态为批准（通常批准有业务人员操作）
    * 批准后社保结算人员可以按批次或服务协议退回
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-31
   public ActionForward submit_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取的 BatchVO
         final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
         sbBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );

         // 提交至批准
         sbBatchService.submit( sbBatchVO );

         insertlog( request, sbBatchVO, Operate.SUBMIT, null, "submit_estimation:" + KANUtil.decodeSelectedIds( sbBatchVO.getSelectedIds() ) );

         // 根据pageFlag 跳转
         return forward( sbBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * submit_confirmation
    * 
    * 提交社保批次 - 至社保机构，状态为确认
    * 提交后无法退回，存在问题或社保机构反馈有偏差，调整处理
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-31
   public ActionForward submit_confirmation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取的 BatchVO
         final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
         sbBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );
         //生成工作流需要的参数
         sbBatchService.generateHistoryVOForWorkflow( sbBatchVO );
         // 提交至批准
         sbBatchService.submit( sbBatchVO );

         insertlog( request, sbBatchVO, Operate.SUBMIT, null, "submit_confirmation:" + KANUtil.decodeSelectedIds( sbBatchVO.getSelectedIds() ) );

         if ( getRole( request, response ).equals( KANConstants.ROLE_VENDOR ) )
         {
            // 如果是供应商登录
            return to_sbDetail_inVendor( mapping, form, request, response );
         }

         // 根据pageFlag 跳转
         return forward( sbBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * submit_settlement
    * 
    * 提交社保批次 - 至结算，状态为提交
    * 提交后结算模块可见
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-31
   public ActionForward submit_settlement( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取的 BatchVO
         final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
         sbBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );

         // 提交至批准
         sbBatchService.submit( sbBatchVO );

         insertlog( request, sbBatchVO, Operate.SUBMIT, null, "submit_settlement:" + KANUtil.decodeSelectedIds( sbBatchVO.getSelectedIds() ) );

         // 根据pageFlag 跳转
         return forward( sbBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Rollback
    * 
    * 退回预算批次或服务协议 - 只有新建和批准状态才有退回功能
    * 退回记录 - 社保操作数据物理删除，服务协议中的社保状态改为“无社保”
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
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );
         // 获得Action Form
         final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
         sbBatchVO.setStatus( getStatusesByStatusFlag( sbBatchVO.getStatusFlag() ) );

         Map< String, String > statusMap = new HashMap< String, String >();
         statusMap.put( "statusAdd", request.getParameter( "statusAddHidden" ) );
         statusMap.put( "statusBack", request.getParameter( "statusBackHidden" ) );

         // 删除对应项
         sbBatchService.rollback( sbBatchVO, statusMap );

         insertlog( request, sbBatchVO, Operate.ROllBACK, null, "rollback" + KANUtil.decodeSelectedIds( sbBatchVO.getSelectedIds() ) );

         // 清除Selected IDs和子Action
         ( ( SBBatchVO ) form ).setSelectedIds( "" );

         // 根据pageFlag 跳转
         return forward( sbBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Forward
    * 根据Page Flag跳转
    *
    * @param pageFlag
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-31
   private ActionForward forward( String pageFlag, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_HEADER ) )
      {
         SBHeaderVO sbHeader = new SBHeaderVO();
         sbHeader.setBatchId( "" );
         sbHeader.setStatus( getStatusesByStatusFlag( ( ( SBBatchVO ) form ).getStatusFlag() ) );
         return new SBHeaderAction().list_object( mapping, sbHeader, request, response );
      }
      else if ( pageFlag.equalsIgnoreCase( SBBatchService.PAGE_FLAG_DETAIL ) )
      {
         return to_sbDetail( mapping, form, request, response );
      }
      else
      {
         return list_estimation( mapping, form, request, response );
      }
   }

   /**
    * To SBDetail
    * 
    * 显示批次、服务协议、社保方案相关信息及社保方案明细列表
    * 需要显示当前社保方案的汇总数
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-31
   public ActionForward to_sbDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, KANUtil.filterEmpty( getCorpId( request, null ) ) );

         // 判断列表是否需要添加导出功能
         if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getExportExcel() != null
               && listDTO.getListHeaderVO().getExportExcel().trim().equals( "1" ) )
         {
            request.setAttribute( "isExportExcel", "1" );
         }

         // 初始化Service接口
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         final SBDetailService sbDetailService = ( SBDetailService ) getService( "sbDetailService" );

         // 获得当次主键ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得服务协议主键ID
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );
         // 获得社保方案主键ID
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) );

         // 初始化SBHeaderVO
         SBHeaderVO sbHeaderVO = new SBHeaderVO();
         sbHeaderVO.setBatchId( batchId );
         sbHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         sbHeaderVO.setContractId( contractId );
         sbHeaderVO.setAccountId( getAccountId( request, response ) );

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
         }

         // 设置HeaderId
         sbHeaderVO.setHeaderId( headerId );

         final List< Object > sbHeaderVOs = sbHeaderService.getSBHeaderVOsByCondition( sbHeaderVO );

         if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
         {
            sbHeaderVO = ( SBHeaderVO ) sbHeaderVOs.get( 0 );
         }

         // 刷新VO对象，初始化对象列表及国际化
         sbHeaderVO.reset( null, request );

         // 初始化SBBatchVO
         SBBatchVO sbBatchVO = new SBBatchVO();
         sbBatchVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         sbBatchVO.setBatchId( batchId );
         sbBatchVO.setAccountId( getAccountId( request, response ) );

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbBatchVO.setCorpId( getCorpId( request, response ) );
         }
         final List< Object > sbBatchVOs = sbBatchService.getSBBatchVOsByCondition( sbBatchVO );

         if ( sbBatchVOs != null && sbBatchVOs.size() > 0 )
         {
            sbBatchVO = ( SBBatchVO ) sbBatchVOs.get( 0 );
         }

         sbBatchVO.setPageFlag( SBBatchService.PAGE_FLAG_DETAIL );
         sbBatchVO.setStatusFlag( request.getParameter( "statusFlag" ) );
         sbBatchVO.reset( null, request );
         request.setAttribute( "sbBatchForm", sbBatchVO );
         request.setAttribute( "sbHeaderVO", sbHeaderVO );

         String accessAction = "HRO_SB_BATCH_PREVIEW";
         if ( request.getParameter( "statusFlag" ).equals( SBBatchService.STATUS_FLAG_PREVIEW ) )
         {
            accessAction = "HRO_SB_BATCH_PREVIEW";
         }
         else if ( request.getParameter( "statusFlag" ).equals( SBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            accessAction = "HRO_SB_BATCH_CONFIRM";
         }
         else if ( request.getParameter( "statusFlag" ).equals( SBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            accessAction = "HRO_SB_BATCH_SUBMIT";
         }
         request.setAttribute( "authAccessAction", accessAction );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sbDetailHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 传入当前页
         sbDetailHolder.setPage( page );

         // 初始化SBDetailVO
         final SBDetailVO sbDetailVO = new SBDetailVO();
         sbDetailVO.setHeaderId( headerId );
         sbDetailVO.setAccountId( getAccountId( request, response ) );
         sbDetailVO.setStatus( getStatusesByStatusFlag( sbBatchVO.getStatusFlag() ) );

         // 传入排序相关字段
         sbDetailVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbDetailVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 如果没有指定排序则默认按科目ID排序
         if ( sbDetailVO.getSortColumn() == null || sbDetailVO.getSortColumn().isEmpty() )
         {
            sbDetailVO.setSortColumn( "itemId" );
         }

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbDetailVO.setCorpId( getCorpId( request, response ) );
         }

         // 传入当前值对象
         sbDetailHolder.setObject( sbDetailVO );
         // 设置页面记录条数
         sbDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         sbDetailService.getSBDetailVOsByCondition( sbDetailHolder, true );
         refreshHolder( sbDetailHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "sbDetailHolder", sbDetailHolder );

         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listDetailTable" );
         }

         // 如果不存在社保明细数据
         if ( sbDetailHolder == null || sbDetailHolder.getHolderSize() == 0 )
         {
            SBHeaderVO sbHeader = new SBHeaderVO();
            sbHeader.reset();
            sbHeader.setBatchId( "" );
            return new SBHeaderAction().list_object( mapping, sbHeader, request, response );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listDetail" );
   }

   /**  
    * To SBDetail InVendor
    * 供应商登录查看SBDetail信息
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_sbDetail_inVendor( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         final SBDetailService sbDetailService = ( SBDetailService ) getService( "sbDetailService" );
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );

         // 获得社保方案主键ID sbHeaderId
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) );
         // 获得供应商ID
         final String vendorId = KANUtil.decodeStringFromAjax( request.getParameter( "vendorId" ) );
         // 获得状态标示
         final String additionalStatus = request.getParameter( "additionalStatus" );

         // 获得供应商
         final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
         request.setAttribute( "vendorVO", vendorVO );

         // 初始化SBHeaderVO
         SBHeaderVO sbHeaderVO = new SBHeaderVO();
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         sbHeaderVO.setHeaderId( headerId );
         sbHeaderVO.setStatus( additionalStatus );

         // 提取社保方案
         final List< Object > sbHeaderVOs = sbHeaderService.getSBHeaderVOsByCondition( sbHeaderVO );

         if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
         {
            sbHeaderVO = ( SBHeaderVO ) sbHeaderVOs.get( 0 );
         }

         // 刷新VO对象，初始化对象列表及国际化
         sbHeaderVO.reset( null, request );

         // 初始化SBBatchVO
         final String batchId = sbHeaderVO.getBatchId();
         SBBatchVO sbBatchVO = new SBBatchVO();
         sbBatchVO.setStatus( additionalStatus );
         sbBatchVO.setBatchId( batchId );
         sbBatchVO.setAccountId( getAccountId( request, response ) );

         final List< Object > sbBatchVOs = sbBatchService.getSBBatchVOsByCondition( sbBatchVO );

         if ( sbBatchVOs != null && sbBatchVOs.size() > 0 )
         {
            sbBatchVO = ( SBBatchVO ) sbBatchVOs.get( 0 );
         }

         sbBatchVO.setPageFlag( SBBatchService.PAGE_FLAG_DETAIL );
         sbBatchVO.setStatusFlag( SBBatchService.STATUS_FLAG_CONFIRM );
         sbBatchVO.setStatus( additionalStatus );
         sbBatchVO.reset( null, request );
         request.setAttribute( "sbBatchForm", sbBatchVO );
         request.setAttribute( "sbHeaderVO", sbHeaderVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sbDetailHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 传入当前页
         sbDetailHolder.setPage( page );

         // 初始化SBDetailVO
         final SBDetailVO sbDetailVO = new SBDetailVO();
         sbDetailVO.setHeaderId( headerId );
         sbDetailVO.setAccountId( getAccountId( request, response ) );
         sbDetailVO.setStatus( additionalStatus );

         // 传入排序相关字段
         sbDetailVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbDetailVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 如果没有指定排序则默认按科目ID排序
         if ( sbDetailVO.getSortColumn() == null || sbDetailVO.getSortColumn().isEmpty() )
         {
            sbDetailVO.setSortColumn( "itemId" );
         }

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbDetailVO.setCorpId( getCorpId( request, response ) );
         }

         // 传入当前值对象
         sbDetailHolder.setObject( sbDetailVO );
         // 设置页面记录条数
         sbDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         sbDetailService.getSBDetailVOsByCondition( sbDetailHolder, true );
         refreshHolder( sbDetailHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "sbDetailHolder", sbDetailHolder );

         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listDetailTable" );
         }

         // 如果不存在社保明细数据
         //         if ( sbDetailHolder == null || sbDetailHolder.getHolderSize() == 0 )
         //         {
         //            SBHeaderVO sbHeader = new SBHeaderVO();
         //            sbHeader.reset();
         //            sbHeader.setBatchId( "" );
         //            return new SBHeaderAction().list_object( mapping, sbHeader, request, response );
         //         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listDetail" );
   }

   // 根据StatusFlag获取状态值Status
   // Reviewed by Kevin Jin at 2013-10-31
   private String getStatusesByStatusFlag( final String statusFlag )
   {
      // 初始化，默认为“预览”
      String status = "1";

      if ( statusFlag != null && !statusFlag.isEmpty() )
      {
         if ( statusFlag.equalsIgnoreCase( SBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            return "2";
         }
         else if ( statusFlag.equals( SBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            return "3,4,5";
         }
      }

      return status;
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
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );
         // 获得Action Form
         final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
         setDataAuth( request, response, sbBatchVO );
         // 设置状态
         sbBatchVO.setStatus( getStatusesByStatusFlag( sbBatchVO.getStatusFlag() ) );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前值对象
         pagedListHolder.setObject( sbBatchVO );
         // 调用Service方法，引用对象返回
         sbBatchService.getSBDTOsByCondition( pagedListHolder );
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

   /**  
    * 检查输入ClientId是否有效
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkClientId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 初始化Service接口
      final ClientService clientService = ( ClientService ) getService( "clientService" );
      // 获取Form
      final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
      // 获得ClientId
      final String clientId = KANUtil.filterEmpty( sbBatchVO.getClientId() );

      final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

      if ( clientVO == null )
      {
         request.setAttribute( "clientIdError", "客户ID输入无效！" );
         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );
      }

   }

   /**  
    * 检查输入OrderId是否有效
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkOrderId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 初始化Service接口
      final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
      // 获取Form
      final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
      // 获得OrderId
      final String clientOrderHeaderId = KANUtil.filterEmpty( sbBatchVO.getOrderId() );

      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( clientOrderHeaderId );

      if ( clientOrderHeaderVO == null )
      {
         request.setAttribute( "orderIdError", "订单ID输入无效！" );
         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );
      }

   }

   /**  
    * 检查输入EmployeeContractId是否有效
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkEmployeeContractId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 初始化Service接口
      final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
      // 获取Form
      final SBBatchVO sbBatchVO = ( SBBatchVO ) form;
      // 获得ContractId
      final String contractId = KANUtil.filterEmpty( sbBatchVO.getContractId() );

      final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( contractId );

      if ( employeeContractVO == null )
      {

         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            request.setAttribute( "contractIdError", "劳动合同ID输入无效！" );
         }
         else
         {
            request.setAttribute( "contractIdError", "派送协议ID输入无效！" );
         }

         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );

      }

   }

   public void checkSBStatus( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );
         final PrintWriter out = response.getWriter();
         String addCount = "0";
         String backCount = "0";
         String selectedIds = request.getParameter( "selectedIds" );

         if ( StringUtils.isNotEmpty( selectedIds ) )
         {
            String[] ids = selectedIds.split( "," );
            String[] batchId = new String[ ids.length ];

            for ( int i = 0; i < batchId.length; i++ )
            {
               batchId[ i ] = KANUtil.decodeStringFromAjax( ids[ i ] );
            }

            addCount = sbBatchService.getSBToApplyForMoreStatusCountByBatchIds( batchId );
            backCount = sbBatchService.getSBToApplyForResigningStatusCountByBatchIds( batchId );
         }

         final JSONObject jsonObject = new JSONObject();
         jsonObject.put( "addCount", addCount );
         jsonObject.put( "backCount", backCount );
         out.println( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * To SBDetail
    * 
    * 显示批次、服务协议、社保方案相关信息及社保方案明细列表
    * 需要显示当前社保方案的汇总数
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-31
   public ActionForward to_sbDetail_workflow( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {

         // 初始化Service接口
         final SBBatchService sbBatchService = ( SBBatchService ) getService( "sbBatchService" );
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         final SBDetailService sbDetailService = ( SBDetailService ) getService( "sbDetailService" );

         // 获得当次主键ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得服务协议主键ID
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );
         // 获得社保方案主键ID
         final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) );

         // 初始化SBHeaderVO
         SBHeaderVO sbHeaderVO = new SBHeaderVO();
         sbHeaderVO.setBatchId( batchId );
         sbHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         sbHeaderVO.setContractId( contractId );
         sbHeaderVO.setAccountId( getAccountId( request, response ) );

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
         }

         // 设置HeaderId
         sbHeaderVO.setHeaderId( headerId );

         final List< Object > sbHeaderVOs = sbHeaderService.getSBHeaderVOsByCondition( sbHeaderVO );

         if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
         {
            sbHeaderVO = ( SBHeaderVO ) sbHeaderVOs.get( 0 );
         }

         // 刷新VO对象，初始化对象列表及国际化
         sbHeaderVO.reset( null, request );

         // 初始化SBBatchVO
         SBBatchVO sbBatchVO = new SBBatchVO();
         sbBatchVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         sbBatchVO.setBatchId( batchId );
         sbBatchVO.setAccountId( getAccountId( request, response ) );

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbBatchVO.setCorpId( getCorpId( request, response ) );
         }
         final List< Object > sbBatchVOs = sbBatchService.getSBBatchVOsByCondition( sbBatchVO );

         if ( sbBatchVOs != null && sbBatchVOs.size() > 0 )
         {
            sbBatchVO = ( SBBatchVO ) sbBatchVOs.get( 0 );
         }

         sbBatchVO.setPageFlag( SBBatchService.PAGE_FLAG_DETAIL );
         sbBatchVO.setStatusFlag( request.getParameter( "statusFlag" ) );
         sbBatchVO.reset( null, request );
         request.setAttribute( "sbBatchForm", sbBatchVO );
         request.setAttribute( "sbHeaderVO", sbHeaderVO );

         String accessAction = "HRO_SB_BATCH_PREVIEW";
         if ( request.getParameter( "statusFlag" ).equals( SBBatchService.STATUS_FLAG_PREVIEW ) )
         {
            accessAction = "HRO_SB_BATCH_PREVIEW";
         }
         else if ( request.getParameter( "statusFlag" ).equals( SBBatchService.STATUS_FLAG_CONFIRM ) )
         {
            accessAction = "HRO_SB_BATCH_CONFIRM";
         }
         else if ( request.getParameter( "statusFlag" ).equals( SBBatchService.STATUS_FLAG_SUBMIT ) )
         {
            accessAction = "HRO_SB_BATCH_SUBMIT";
         }
         request.setAttribute( "authAccessAction", accessAction );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sbDetailHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 传入当前页
         sbDetailHolder.setPage( page );

         // 初始化SBDetailVO
         final SBDetailVO sbDetailVO = new SBDetailVO();
         sbDetailVO.setHeaderId( headerId );
         sbDetailVO.setAccountId( getAccountId( request, response ) );
         sbDetailVO.setStatus( getStatusesByStatusFlag( sbBatchVO.getStatusFlag() ) );

         // 传入排序相关字段
         sbDetailVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbDetailVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 如果没有指定排序则默认按科目ID排序
         if ( sbDetailVO.getSortColumn() == null || sbDetailVO.getSortColumn().isEmpty() )
         {
            sbDetailVO.setSortColumn( "itemId" );
         }

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbDetailVO.setCorpId( getCorpId( request, response ) );
         }

         // 传入当前值对象
         sbDetailHolder.setObject( sbDetailVO );
         // 设置页面记录条数
         sbDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         sbDetailService.getSBDetailVOsByCondition( sbDetailHolder, true );
         refreshHolder( sbDetailHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "sbDetailHolder", sbDetailHolder );

         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listDetailTable" );
         }

         // 如果不存在社保明细数据
         if ( sbDetailHolder == null || sbDetailHolder.getHolderSize() == 0 )
         {
            SBHeaderVO sbHeader = new SBHeaderVO();
            sbHeader.reset();
            sbHeader.setBatchId( "" );
            return new SBHeaderAction().list_object( mapping, sbHeader, request, response );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listDetailWorkflow" );
   }
}

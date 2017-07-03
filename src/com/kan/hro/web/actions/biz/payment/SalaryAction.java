package com.kan.hro.web.actions.biz.payment;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.tag.AuthConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.payment.CommonBatchVO;
import com.kan.hro.domain.biz.payment.PaymentBatchVO;
import com.kan.hro.domain.biz.payment.SalaryDTO;
import com.kan.hro.domain.biz.payment.SalaryDetailVO;
import com.kan.hro.domain.biz.payment.SalaryHeaderVO;
import com.kan.hro.domain.biz.settlement.BatchTempVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.service.inf.biz.payment.CommonBatchService;
import com.kan.hro.service.inf.biz.payment.PaymentBatchService;
import com.kan.hro.service.inf.biz.payment.SalaryHeaderService;

public class SalaryAction extends BaseAction
{
   public final static String accessAction = "HRO_SALARY_HEADER";

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
         final CommonBatchService salaryBatchService = ( CommonBatchService ) getService( "salaryBatchService" );

         final SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) form;
         final CommonBatchVO commonBatchVO = new CommonBatchVO();
         commonBatchVO.setSortColumn( salaryHeaderVO.getSortColumn() );
         commonBatchVO.setSortOrder( salaryHeaderVO.getSortOrder() );
         commonBatchVO.setSelectedIds( salaryHeaderVO.getSelectedIds() );
         commonBatchVO.setSubAction( salaryHeaderVO.getSubAction() );
         commonBatchVO.setBatchId( salaryHeaderVO.getBatchId() );
         commonBatchVO.setImportExcelName( salaryHeaderVO.getImportExcelName() );
         commonBatchVO.setStatus( salaryHeaderVO.getStatus() );

         // 设置当前用户AccountId
         commonBatchVO.setAccountId( getAccountId( request, response ) );

         // 设置当前的clientId
         commonBatchVO.setClientId( getClientId( request, response ) );
         commonBatchVO.setCorpId( getCorpId( request, response ) );

         // 处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, commonBatchVO );
         //commonBatchVO.setInList( KANUtil.convertStringToList( commonBatchVO.getHasIn(), "," ) );
         //commonBatchVO.setNotInList( KANUtil.convertStringToList( commonBatchVO.getNotIn(), "," ) );
         //setDataAuth( request, response, commonBatchVO );
         
         Set< String > rulePositionIds = new HashSet< String >();
         rulePositionIds.add( getPositionId( request, null ) );
         commonBatchVO.setRulePositionIds( rulePositionIds );
         commonBatchVO.setRulePublic( AuthConstants.RULE_UN_PUBLIC );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder salaryBatchHolder = new PagedListHolder();

         // 传入当前页
         salaryBatchHolder.setPage( page );

         // 如果没有指定排序则默认按 BatchId排序
         if ( commonBatchVO.getSortColumn() == null || commonBatchVO.getSortColumn().isEmpty() )
         {
            commonBatchVO.setSortColumn( "batchId" );
            commonBatchVO.setSortOrder( "desc" );
         }

         // 传入当前值对象
         salaryBatchHolder.setObject( commonBatchVO );
         // 设置页面记录条数
         salaryBatchHolder.setPageSize( listPageSize_medium );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         salaryBatchService.getSalaryBatchVOsByCondition( salaryBatchHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( salaryBatchHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "salaryBatchHolder", salaryBatchHolder );
         // 如果是Ajax请求
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listSalaryBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listSalaryBatchBody" );
   }

   public ActionForward to_salaryHeader( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 获得批次主键ID
      final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

      // 初始化Service接口
      final SalaryHeaderService salaryHeaderService = ( SalaryHeaderService ) getService( "salaryHeaderService" );
      final CommonBatchService salaryBatchService = ( CommonBatchService ) getService( "salaryBatchService" );

      final CommonBatchVO commonBatchVO = salaryBatchService.getCommonBatchVOByBatchId( batchId );

      commonBatchVO.reset( null, request );
      request.setAttribute( "commonBatchVO", commonBatchVO );

      // 获得当前页
      final String page = getPage( request );
      // 获得是否Ajax调用
      final String ajax = getAjax( request );
      final SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) form;
      salaryHeaderVO.setBatchId( batchId );

      // 设置当前用户AccountId
      salaryHeaderVO.setAccountId( getAccountId( request, response ) );

      // 设置当前的clientId
      salaryHeaderVO.setClientId( getClientId( request, response ) );
      salaryHeaderVO.setCorpId( getCorpId( request, response ) );

      // 初始化PagedListHolder，用于引用方式调用Service
      final PagedListHolder salaryHeaderHolder = new PagedListHolder();

      // 传入当前页
      salaryHeaderHolder.setPage( page );

      // 如果没有指定排序则默认按 BatchId排序
      if ( salaryHeaderVO.getSortColumn() == null || salaryHeaderVO.getSortColumn().isEmpty() )
      {
         salaryHeaderVO.setSortColumn( "salaryHeaderId" );
         salaryHeaderVO.setSortOrder( "desc" );
      }

      // 传入当前值对象
      salaryHeaderHolder.setObject( salaryHeaderVO );
      // 设置页面记录条数
      salaryHeaderHolder.setPageSize( listPageSize_medium );
      // 调用Service方法，引用对象返回，第二个参数说明是否分页
      salaryHeaderService.getSalaryHeaderVOsByCondition( salaryHeaderHolder, true );
      // 刷新Holder，国际化传值
      refreshHolder( salaryHeaderHolder, request );

      salaryHeaderService.getSalaryDTOsByCondition( salaryHeaderHolder, true );

      // Reset PaymentHeaderDTOHolder
      if ( salaryHeaderHolder != null && salaryHeaderHolder.getHolderSize() > 0 )
      {
         final List< Object > salaryDTOOjbects = salaryHeaderHolder.getSource();

         if ( salaryDTOOjbects != null && salaryDTOOjbects.size() > 0 )
         {
            for ( Object salaryDTOOjbect : salaryDTOOjbects )
            {
               final SalaryDTO tempSalaryDTO = ( SalaryDTO ) salaryDTOOjbect;

               // Reset PaymentHeaderVO
               final SalaryHeaderVO tempSalaryHeaderVO = tempSalaryDTO.getSalaryHeaderVO();

               if ( tempSalaryHeaderVO != null )
               {
                  tempSalaryHeaderVO.reset( mapping, request );
               }

               // Reset PaymentDetailVO
               final List< SalaryDetailVO > salaryDetailVOs = tempSalaryDTO.getSalaryDetailVOs();

               if ( salaryDetailVOs != null && salaryDetailVOs.size() > 0 )
               {
                  for ( SalaryDetailVO tempSalaryDetailVO : salaryDetailVOs )
                  {
                     tempSalaryDetailVO.reset( mapping, request );
                     if ( KANUtil.filterEmpty( tempSalaryDetailVO.getItemType() ) == null )
                     {
                        tempSalaryDetailVO.setItemType( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( tempSalaryDetailVO.getItemId() ).getItemType() );
                     }
                  }
               }

            }
         }

      }

      request.setAttribute( "salaryDTOHolder", salaryHeaderHolder );

      // 如果全部方案状态都改变了，则跳转到上一层
      if ( salaryHeaderHolder == null || salaryHeaderHolder.getHolderSize() == 0 )
      {
         ( ( SalaryHeaderVO ) form ).setSortColumn( null );
         return list_object( mapping, form, request, response );
      }

      // Holder需写入Request对象
      request.setAttribute( "salaryHeaderHolder", salaryHeaderHolder );
      if ( new Boolean( ajax ) )
      {
         // 写入Role
         request.setAttribute( "role", getRole( request, response ) );
         return mapping.findForward( "listSalaryDetailTable" );
      }

      return mapping.findForward( "listSalaryHeadBody" );
   }

   /**
    * 查看详情
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_salaryDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 获得批次主键ID
      final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) );

      // 初始化Service接口
      final SalaryHeaderService salaryHeaderService = ( SalaryHeaderService ) getService( "salaryHeaderService" );
      final SalaryHeaderVO salaryHeaderVO = salaryHeaderService.getSalaryHeaderVOByHeaderId( headerId );
      salaryHeaderVO.reset( null, request );
      request.setAttribute( "salaryHeaderForm", salaryHeaderVO );
      // 初始化PagedListHolder
      PagedListHolder salaryDTOHolder = new PagedListHolder();

      // 排列
      salaryHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
      salaryHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

      // 默认排序
      if ( request.getParameter( "sortColumn" ) == null || request.getParameter( "sortColumn" ).trim().isEmpty() )
      {
         salaryHeaderVO.setSortColumn( "salaryHeaderId" );
         salaryHeaderVO.setSortOrder( "desc" );
      }

      // 设置页面
      salaryDTOHolder.setPage( getPage( request ) );
      salaryDTOHolder.setObject( salaryHeaderVO );
      // 设置页面大小
      salaryDTOHolder.setPageSize( listPageSize );

      salaryHeaderService.getSalaryDTOsByCondition( salaryDTOHolder, true );

      // Reset PaymentHeaderDTOHolder
      if ( salaryDTOHolder != null && salaryDTOHolder.getHolderSize() > 0 )
      {
         final List< Object > salaryDTOOjbects = salaryDTOHolder.getSource();

         if ( salaryDTOOjbects != null && salaryDTOOjbects.size() > 0 )
         {
            for ( Object salaryDTOOjbect : salaryDTOOjbects )
            {
               final SalaryDTO tempSalaryDTO = ( SalaryDTO ) salaryDTOOjbect;

               // Reset PaymentHeaderVO
               final SalaryHeaderVO tempSalaryHeaderVO = tempSalaryDTO.getSalaryHeaderVO();

               if ( tempSalaryHeaderVO != null )
               {
                  tempSalaryHeaderVO.reset( mapping, request );
               }

               // Reset PaymentDetailVO
               final List< SalaryDetailVO > salaryDetailVOs = tempSalaryDTO.getSalaryDetailVOs();

               if ( salaryDetailVOs != null && salaryDetailVOs.size() > 0 )
               {
                  for ( SalaryDetailVO tempSalaryDetailVO : salaryDetailVOs )
                  {
                     tempSalaryDetailVO.reset( mapping, request );
                     if ( KANUtil.filterEmpty( tempSalaryDetailVO.getItemType() ) == null )
                     {
                        tempSalaryDetailVO.setItemType( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( tempSalaryDetailVO.getItemId() ).getItemType() );
                     }
                  }
               }

            }
         }
      }

      request.setAttribute( "salaryDTOHolder", salaryDTOHolder );

      // 获得是否Ajax调用
      final String ajax = request.getParameter( "ajax" );

      // 如果是ajax请求或者删除操作则跳转到Table公共部分对应的jsp
      if ( new Boolean( ajax ) )
      {
         // 写入Role
         request.setAttribute( "role", getRole( request, response ) );
         return mapping.findForward( "listSalaryDetailTable" );
      }

      // 如果全部方案状态都改变了，则跳转到上一层
      if ( salaryDTOHolder == null || salaryDTOHolder.getHolderSize() == 0 )
      {
         ( ( SalaryHeaderVO ) form ).setSortColumn( null );
         return list_object( mapping, form, request, response );
      }

      return mapping.findForward( "listSalaryDetail" );
   }

   /**
    * To ObjectNew 新建批次
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
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
    * Add Object 添加批次
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2013-12-04
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
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
               clientOrderHeaderVO.setMonthly( batchTempVO.getMonthly() );

               // 调用Service方法存储数据（需要考虑Transaction）
               rows = paymentBatchService.insertPaymentBatchInHouse( paymentBatchVO, batchTempVO, clientOrderHeaderVO, serviceContractVO );
            }
            else
            {
               // 调用Service方法存储数据（需要考虑Transaction）
               rows = paymentBatchService.insertPaymentBatch( paymentBatchVO, serviceContractVO );
            }

            if ( rows > 0 )
            {
               // 返回添加成功标记
               success( request, null, "成功创建批次 " + paymentBatchVO.getBatchId() + " ！" );
            }
            else
            {
               // 返回警告标记
               warning( request, null, "批次未创建。没有符合条件的数据！" );
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
    * submit_salary
    * 
    * 提交，状态为批准
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2014-05-20
   public ActionForward submit_salary( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取的 BatchVO
         final SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) form;

         // 初始化Service接口
         final SalaryHeaderService salaryHeaderService = ( SalaryHeaderService ) getService( "salaryHeaderService" );

         // 获得批次主键ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

         final CommonBatchVO commonBatchVO = new CommonBatchVO();

         if ( KANUtil.filterEmpty( batchId ) != null )
         {
            // 初始化CommonBatchVO
            commonBatchVO.setBatchId( batchId );
         }

         commonBatchVO.setSelectedIds( salaryHeaderVO.getSelectedIds() );
         commonBatchVO.setPageFlag( request.getParameter( "pageFlag" ) );
         commonBatchVO.setSubAction( request.getParameter( "subAction" ) );

         // 提交至批准
         int row = salaryHeaderService.submit( commonBatchVO );
         insertlog( request, commonBatchVO, Operate.SUBMIT, commonBatchVO.getBatchId(), "submit_salary" );
         ( ( SalaryHeaderVO ) form ).setSelectedIds( "" );

         // 根据pageFlag 跳转
         if ( KANUtil.filterEmpty( batchId ) != null && commonBatchVO.getPageFlag().equalsIgnoreCase( SalaryHeaderService.PAGE_FLAG_HEADER ) && row != 1 )
         {
            return to_salaryHeader( mapping, form, request, response );
         }

         // 清空Form条件
         ( ( SalaryHeaderVO ) form ).reset();
         ( ( SalaryHeaderVO ) form ).setBatchId( "" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**
    * rollback_salary
    * 
    * 退回，直接删除
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward rollback_salary( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取的 BatchVO
         final SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) form;

         // 初始化Service接口
         final SalaryHeaderService salaryHeaderService = ( SalaryHeaderService ) getService( "salaryHeaderService" );

         // 获得批次主键ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

         final CommonBatchVO commonBatchVO = new CommonBatchVO();
         if ( KANUtil.filterEmpty( batchId ) != null )
         {
            // 初始化CommonBatchVO
            commonBatchVO.setBatchId( batchId );
         }
         commonBatchVO.setSelectedIds( salaryHeaderVO.getSelectedIds() );
         commonBatchVO.setPageFlag( request.getParameter( "pageFlag" ) );
         commonBatchVO.setSubAction( request.getParameter( "subAction" ) );

         // 退回
         int row = salaryHeaderService.rollback( commonBatchVO );
         insertlog( request, commonBatchVO, Operate.ROllBACK, commonBatchVO.getBatchId(), "rollback_salary" );
         ( ( SalaryHeaderVO ) form ).setSelectedIds( "" );
         // 根据pageFlag 跳转
         if ( KANUtil.filterEmpty( batchId ) != null && commonBatchVO.getPageFlag().equalsIgnoreCase( SalaryHeaderService.PAGE_FLAG_HEADER ) && row != 1 )
         {
            return to_salaryHeader( mapping, form, request, response );
         }

         // 清空Form条件
         ( ( SalaryHeaderVO ) form ).reset();
         ( ( SalaryHeaderVO ) form ).setBatchId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
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

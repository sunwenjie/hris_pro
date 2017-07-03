package com.kan.hro.web.actions.biz.payment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentHeaderService;
import com.kan.hro.service.inf.biz.payment.PaymentHeaderService;
import com.kan.hro.service.inf.biz.vendor.VendorService;

public class PaymentAdjustmentHeaderAction extends BaseAction
{

   public static final String accessAction = "HRO_PAYMENT_ADJUSTMENT";

   /**  
    * List_object
    *	 显示所有状态的工资调整列表
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2014-01-01
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = getPage( request );

         // 初始化Service接口
         final PaymentAdjustmentHeaderService paymentBatchService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // 获得Action Form
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;

         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, paymentAdjustmentHeaderVO );
         setDataAuth( request, response, paymentAdjustmentHeaderVO );

         paymentAdjustmentHeaderVO.setPageFlag( PaymentAdjustmentHeaderService.PAGE_FLAG_HEADER );
         paymentAdjustmentHeaderVO.setOrderId( KANUtil.filterEmpty( paymentAdjustmentHeaderVO.getOrderId(), "0" ) );

         if ( paymentAdjustmentHeaderVO.getSubAction() != null )
         {
            // 调用删除方法
            if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
            {
               delete_objectList( mapping, form, request, response );
            }
            else if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( SUBMIT_OBJECTS ) || paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( ISSUE_OBJECTS ) )
            {
               modify_objectListStatus( mapping, form, request, response );
            }
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( paymentAdjustmentHeaderVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder paymentAdjustmentHeaderHolder = new PagedListHolder();

         // 传入当前页
         paymentAdjustmentHeaderHolder.setPage( page );

         // 如果没有指定排序则默认按 AdjustmentHeaderId排序
         if ( paymentAdjustmentHeaderVO.getSortColumn() == null || paymentAdjustmentHeaderVO.getSortColumn().isEmpty() )
         {
            paymentAdjustmentHeaderVO.setSortColumn( "adjustmentHeaderId" );
            paymentAdjustmentHeaderVO.setSortOrder( "desc" );
         }

         // 如果In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            paymentAdjustmentHeaderVO.setCorpId( getCorpId( request, response ) );
            paymentAdjustmentHeaderVO.setClientId( getClientId( request, response ) );
            // 发送帐套列表
            passClientOrders( request, response );
         }

         // 传入当前值对象
         paymentAdjustmentHeaderHolder.setObject( paymentAdjustmentHeaderVO );
         // 设置页面记录条数
         paymentAdjustmentHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         paymentBatchService.getPaymentAdjustmentHeaderVOsByCondition( paymentAdjustmentHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( paymentAdjustmentHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "paymentAdjustmentHeaderHolder", paymentAdjustmentHeaderHolder );
         // 写入pageFlag
         request.setAttribute( "pageFlag", PaymentAdjustmentHeaderService.PAGE_FLAG_HEADER );

         /**
          * 页面转向处理
          */
         // 如果是Ajax请求
         if ( new Boolean( request.getParameter( "ajax" ) ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPaymentAdjustmentHeaderTable" );
         }

         return mapping.findForward( "listPaymentAdjustmentHeader" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * List Object Confirm
    *	显示状态为已审核的工资调整列表
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_object_confirm( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = getPage( request );
         // 初始化Service接口
         final PaymentAdjustmentHeaderService paymentBatchService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // 设置PageFlag
         ( ( PaymentAdjustmentHeaderVO ) form ).setPageFlag( PaymentAdjustmentHeaderService.PAGE_FLAG_HEADER );

         // 获得Action Form
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;
         paymentAdjustmentHeaderVO.setOrderId( KANUtil.filterEmpty( paymentAdjustmentHeaderVO.getOrderId(), "0" ) );
         // 设置Status值（只能为“已审核”状态）
         paymentAdjustmentHeaderVO.setStatus( "2" );

         // 设置当前用户AccountId
         paymentAdjustmentHeaderVO.setAccountId( getAccountId( request, response ) );

         decodedObject( paymentAdjustmentHeaderVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder paymentAdjustmentHeaderHolder = new PagedListHolder();

         //String accessAction = "HRO_PAYMENT_ADJUSTMENT_CONFIRM";
         //处理数据权限
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, paymentAdjustmentHeaderVO );
         setDataAuth( request, response, paymentAdjustmentHeaderVO );

         // 传入当前页
         paymentAdjustmentHeaderHolder.setPage( page );

         // 如果没有指定排序则默认按 AdjustmentHeaderId排序
         if ( paymentAdjustmentHeaderVO.getSortColumn() == null || paymentAdjustmentHeaderVO.getSortColumn().isEmpty() )
         {
            paymentAdjustmentHeaderVO.setSortColumn( "adjustmentHeaderId" );
            paymentAdjustmentHeaderVO.setSortOrder( "desc" );
         }

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            paymentAdjustmentHeaderVO.setClientId( getClientId( request, response ) );
            paymentAdjustmentHeaderVO.setCorpId( getCorpId( request, response ) );
            // 发送帐套列表
            passClientOrders( request, response );
         }

         // 传入当前值对象
         paymentAdjustmentHeaderHolder.setObject( paymentAdjustmentHeaderVO );
         // 设置页面记录条数
         paymentAdjustmentHeaderHolder.setPageSize( listPageSize_medium );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         paymentBatchService.getPaymentAdjustmentHeaderVOsByCondition( paymentAdjustmentHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( paymentAdjustmentHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "paymentAdjustmentHeaderHolder", paymentAdjustmentHeaderHolder );
         // 写入pageFlag
         request.setAttribute( "pageFlag", PaymentAdjustmentHeaderService.PAGE_FLAG_HEADER );

         /**
          * 页面转向处理
          */
         // 如果是Ajax请求
         if ( new Boolean( request.getParameter( "ajax" ) ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPaymentAdjustmentHeaderConfirmTable" );
         }

         return mapping.findForward( "listPaymentAdjustmentHeaderConfirm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * To ObjectNew
    *	 
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

      // 初始化PaymentAdjustmentHeaderVO
      final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;

      // 设置Sub Action
      paymentAdjustmentHeaderVO.setSubAction( CREATE_OBJECT );
      // 设置默认值
      paymentAdjustmentHeaderVO.setStatus( "1" );

      // 如果是从客户端登录
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         // 设置客户ID
         paymentAdjustmentHeaderVO.setClientId( getClientId( request, null ) );
         paymentAdjustmentHeaderVO.setCorpId( getCorpId( request, null ) );

         // 初始化帐套集合
         final List< MappingVO > clientOrderHeaderMappingVOs = new ArrayList< MappingVO >();

         // 初始化Service接口
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         // 初始化ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
         clientOrderHeaderVO.setCorpId( getCorpId( request, null ) );
         clientOrderHeaderVO.setClientId( getClientId( request, null ) );
         clientOrderHeaderVO.setAccountId( getAccountId( request, response ) );
         clientOrderHeaderVO.setStatus( "3, 5" );

         // 获得登录客户对应的所有帐套信息
         final List< Object > clientOrderHeaderVOs = clientOrderHeaderService.getClientOrderHeaderVOsByCondition( clientOrderHeaderVO );

         // 生成帐套 MappingVO集合
         if ( clientOrderHeaderVOs != null && clientOrderHeaderVOs.size() > 0 )
         {
            for ( Object clientOrderHeaderVOObject : clientOrderHeaderVOs )
            {
               final MappingVO mappingVO = new MappingVO();
               final ClientOrderHeaderVO clientOrderHeaderVOTemp = ( ClientOrderHeaderVO ) clientOrderHeaderVOObject;
               mappingVO.setMappingId( clientOrderHeaderVOTemp.getOrderHeaderId() );
               mappingVO.setMappingValue( clientOrderHeaderVOTemp.getOrderHeaderId() );

               if ( this.getLocale( request ).getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  if ( KANUtil.filterEmpty( clientOrderHeaderVOTemp.getNameZH() ) != null )
                  {
                     mappingVO.setMappingValue( mappingVO.getMappingValue() + " - " + clientOrderHeaderVOTemp.getNameZH() );
                  }
               }
               else
               {
                  if ( KANUtil.filterEmpty( clientOrderHeaderVOTemp.getNameEN() ) != null )
                  {
                     mappingVO.setMappingValue( mappingVO.getMappingValue() + " - " + clientOrderHeaderVOTemp.getNameEN() );
                  }
               }

               clientOrderHeaderMappingVOs.add( mappingVO );
            }
         }

         clientOrderHeaderMappingVOs.add( 0, KANUtil.getEmptyMappingVO( getLocale( request ) ) );
         request.setAttribute( "clientOrderHeaderMappingVOs", clientOrderHeaderMappingVOs );
      }

      return mapping.findForward( "listPaymentAdjustmentDetail" );
   }

   /**  
    * Add Object
    *	 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2014-04-26
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化PaymentAdjustmentDetailVO
         final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO = new PaymentAdjustmentDetailVO();

         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service
            final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );
            final PaymentHeaderService paymentHeaderService = ( PaymentHeaderService ) getService( "paymentHeaderService" );
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
            final VendorService vendorService = ( VendorService ) getService( "vendorService" );

            // 初始化PaymentAdjustmentHeaderVO
            final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;

            // 搜索工资结算数据
            final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
            paymentHeaderVO.setAccountId( paymentAdjustmentHeaderVO.getAccountId() );
            paymentHeaderVO.setCorpId( paymentAdjustmentHeaderVO.getCorpId() );
            paymentHeaderVO.setEmployeeId( paymentAdjustmentHeaderVO.getEmployeeId() );
            paymentHeaderVO.setMonthly( paymentAdjustmentHeaderVO.getMonthly() );
            // 新建状态数据
            paymentHeaderVO.setStatus( "1" );
            final List< Object > paymentHeaderVOs = paymentHeaderService.getPaymentHeaderVOsByCondition( paymentHeaderVO );

            if ( paymentHeaderVOs == null || paymentHeaderVOs.size() == 0 )
            {
               // 获取EmployeeContractVO
               if ( KANUtil.filterEmpty( paymentAdjustmentHeaderVO.getContractId() ) != null )
               {
                  // 初始化EmployeeContractVO
                  final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( paymentAdjustmentHeaderVO.getContractId() );

                  // 初始化SalaryVendorId
                  String salaryVendorId = employeeContractVO.getSalaryVendorId();

                  if ( KANUtil.filterEmpty( salaryVendorId, "0" ) == null )
                  {
                     salaryVendorId = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() ).getSalaryVendorId();
                  }

                  // 初始化VendorVO
                  VendorVO vendorVO = null;

                  if ( KANUtil.filterEmpty( salaryVendorId, "0" ) != null )
                  {
                     vendorVO = vendorService.getVendorVOByVendorId( salaryVendorId );
                  }

                  paymentAdjustmentHeaderVO.setOrderId( employeeContractVO.getOrderId() );
                  paymentAdjustmentHeaderVO.setBusinessTypeId( employeeContractVO.getBusinessTypeId() );
                  paymentAdjustmentHeaderVO.setEntityId( employeeContractVO.getEntityId() );
                  paymentAdjustmentHeaderVO.setBranch( employeeContractVO.getBranch() );
                  paymentAdjustmentHeaderVO.setOwner( employeeContractVO.getOwner() );
                  paymentAdjustmentHeaderVO.setVendorId( vendorVO != null ? vendorVO.getVendorId() : "0" );
                  paymentAdjustmentHeaderVO.setVendorNameZH( vendorVO != null ? vendorVO.getNameZH() : "" );
                  paymentAdjustmentHeaderVO.setVendorNameEN( vendorVO != null ? vendorVO.getNameEN() : "" );
               }

               paymentAdjustmentHeaderVO.setAccountId( getAccountId( request, response ) );
               paymentAdjustmentHeaderVO.setCreateBy( getUserId( request, response ) );
               paymentAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
               paymentAdjustmentHeaderService.insertPaymentAdjustmentHeader( paymentAdjustmentHeaderVO );

               paymentAdjustmentDetailVO.setAdjustmentHeaderId( paymentAdjustmentHeaderVO.getAdjustmentHeaderId() );

               // 返回添加成功标记
               success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
               insertlog( request, paymentAdjustmentHeaderVO, Operate.ADD, paymentAdjustmentHeaderVO.getAdjustmentHeaderId(), null );

               ( ( PaymentAdjustmentHeaderVO ) form ).setAccountId( getAccountId( request, response ) );

               // 清空Form条件
               ( ( PaymentAdjustmentHeaderVO ) form ).reset();

               return new PaymentAdjustmentDetailAction().list_object( mapping, paymentAdjustmentDetailVO, request, response );
            }
            else
            {
               // 返回警告标记
               warning( request, null, "工资调整未创建。请先提交" + paymentAdjustmentHeaderVO.getEmployeeNameZH() + "的工资结算数据！", MESSAGE_HEADER );
            }
         }
         else
         {
            // 返回失败标记
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ), MESSAGE_HEADER );
         }

         // 清空Form条件
         ( ( PaymentAdjustmentHeaderVO ) form ).reset();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return to_objectNew( mapping, form, request, response );
   }

   /**  
    * To ObjectModify
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   /**  
    * Modify Object
    *	 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2014-01-03
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化接口
            final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

            // 获得当前主键
            String adjustmentHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "adjustmentHeaderId" ) );

            // 初始化PaymentAdjustmentHeaderVO
            final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );
            paymentAdjustmentHeaderVO.update( ( PaymentAdjustmentHeaderVO ) form );
            paymentAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );

            // 获取EmployeeContractVO
            if ( KANUtil.filterEmpty( paymentAdjustmentHeaderVO.getContractId() ) != null )
            {
               final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( paymentAdjustmentHeaderVO.getContractId() );

               paymentAdjustmentHeaderVO.setOrderId( employeeContractVO.getOrderId() );
               paymentAdjustmentHeaderVO.setBusinessTypeId( employeeContractVO.getBusinessTypeId() );
               paymentAdjustmentHeaderVO.setEntityId( employeeContractVO.getEntityId() );
               paymentAdjustmentHeaderVO.setBranch( employeeContractVO.getBranch() );
               paymentAdjustmentHeaderVO.setOwner( employeeContractVO.getOwner() );
            }

            // 修改对象
            paymentAdjustmentHeaderService.updatePaymentAdjustmentHeader( paymentAdjustmentHeaderVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, paymentAdjustmentHeaderVO, Operate.MODIFY, paymentAdjustmentHeaderVO.getAdjustmentHeaderId(), null );
         }

         return new PaymentAdjustmentDetailAction().list_object( mapping, new PaymentAdjustmentDetailVO(), request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Submit Object
    *	提交，修改调整状态为待审核
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward submit_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // 获得主键ID
         final String adjustmentHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "adjustmentHeaderId" ) );

         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );

         // 设置属性值
         paymentAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         paymentAdjustmentHeaderVO.setModifyDate( new Date() );
         paymentAdjustmentHeaderVO.setStatus( "2" );
         paymentAdjustmentHeaderService.updatePaymentAdjustmentHeader( paymentAdjustmentHeaderVO );

         // 返回提交成功标记
         success( request, MESSAGE_TYPE_SUBMIT, null, MESSAGE_HEADER );

         insertlog( request, paymentAdjustmentHeaderVO, Operate.SUBMIT, adjustmentHeaderId, null );

         // 清除Selected IDs和子Action
         ( ( PaymentAdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).reset();
         ( ( PaymentAdjustmentHeaderVO ) form ).setAdjustmentHeaderId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**  
    * Approve Object
    *	修改调整状态为批准'
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward approve_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // 获得Action Form
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;

         // 存在选中的ID
         if ( paymentAdjustmentHeaderVO.getSelectedIds() != null && KANUtil.filterEmpty( paymentAdjustmentHeaderVO.getSelectedIds() ) != null )
         {
            // 分割
            for ( String selectedId : paymentAdjustmentHeaderVO.getSelectedIds().split( "," ) )
            {
               // 初始化PaymentAdjustmentHeaderVO
               final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVOTemp = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );

               // 调用接口
               paymentAdjustmentHeaderVOTemp.setModifyBy( getUserId( request, response ) );
               paymentAdjustmentHeaderVOTemp.setModifyDate( new Date() );
               paymentAdjustmentHeaderVOTemp.setStatus( "3" );
               paymentAdjustmentHeaderService.updatePaymentAdjustmentHeader( paymentAdjustmentHeaderVOTemp );
            }

            insertlog( request, paymentAdjustmentHeaderVO, Operate.SUBMIT, null, "approve_object:" + KANUtil.decodeSelectedIds( paymentAdjustmentHeaderVO.getSelectedIds() ) );
         }

         // 返回“批准”成功标记
         success( request, null, "确认成功", MESSAGE_HEADER );

         // 清除Selected IDs和子Action
         ( ( PaymentAdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object_confirm( mapping, form, request, response );
   }

   /**  
    * Rollback Object
    *	修改调整状态为退回
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward rollback_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // 获得Action Form
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;

         // 存在选中的ID
         if ( paymentAdjustmentHeaderVO.getSelectedIds() != null && !paymentAdjustmentHeaderVO.getSelectedIds().trim().isEmpty() )
         {
            // 分割
            for ( String selectedId : paymentAdjustmentHeaderVO.getSelectedIds().split( "," ) )
            {
               // 初始化PaymentAdjustmentHeaderVO
               final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVOTemp = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );

               // 调用接口
               paymentAdjustmentHeaderVOTemp.setModifyBy( getUserId( request, response ) );
               paymentAdjustmentHeaderVOTemp.setModifyDate( new Date() );
               paymentAdjustmentHeaderVOTemp.setStatus( "4" );
               paymentAdjustmentHeaderService.updatePaymentAdjustmentHeader( paymentAdjustmentHeaderVOTemp );
            }

            insertlog( request, paymentAdjustmentHeaderVO, Operate.ROllBACK, null, "rollback_object:" + KANUtil.decodeSelectedIds( paymentAdjustmentHeaderVO.getSelectedIds() ) );
         }

         // 清除Selected IDs和子Action
         ( ( PaymentAdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).reset();

         // 返回“退回”成功标记
         success( request, null, "退回成功", MESSAGE_HEADER );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object_confirm( mapping, form, request, response );
   }

   /**  
    * Issue Object
    *	修改调整状态为发放
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward issue_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // 获得主键ID
         final String adjustmentHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "adjustmentHeaderId" ) );

         // 初始化PaymentAdjustmentHeaderVO
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );

         // 设置属性值
         paymentAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         paymentAdjustmentHeaderVO.setModifyDate( new Date() );
         paymentAdjustmentHeaderVO.setStatus( "5" );
         paymentAdjustmentHeaderService.updatePaymentAdjustmentHeader( paymentAdjustmentHeaderVO );

         // 清除Selected IDs和子Action
         ( ( PaymentAdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).reset();

         // 返回“发放”成功标记
         success( request, null, "发放成功", MESSAGE_HEADER );

         insertlog( request, paymentAdjustmentHeaderVO, Operate.SUBMIT, null, "issue_object" + paymentAdjustmentHeaderVO.getSelectedIds() );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**  
    * Delete Object
    *	 标记删除薪资
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@throws KANException
    */
   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
   }

   /**  
    * Delete ObjectList
    *	 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@throws KANException
    */
   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );
         // 获得Action Form
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;
         // 初始化删除记录数
         int deleteCounts = 0;

         // 存在选中的ID
         if ( paymentAdjustmentHeaderVO.getSelectedIds() != null && !paymentAdjustmentHeaderVO.getSelectedIds().trim().isEmpty() )
         {
            // 分割
            for ( String selectedId : paymentAdjustmentHeaderVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // 初始化ClientVO
                  final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVOTemp = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );

                  // 状态为“新建”、“退回”状态才可删除
                  if ( "1".equals( paymentAdjustmentHeaderVOTemp.getStatus() ) || "4".equals( paymentAdjustmentHeaderVOTemp.getStatus() ) )
                  {
                     deleteCounts++;
                     // 调用接口
                     paymentAdjustmentHeaderVOTemp.setModifyBy( getUserId( request, response ) );
                     paymentAdjustmentHeaderVOTemp.setModifyDate( new Date() );
                     paymentAdjustmentHeaderService.deletePaymentAdjustmentHeader( paymentAdjustmentHeaderVOTemp );
                  }
               }
            }
         }

         // 清除Selected IDs和子Action
         ( ( PaymentAdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).reset();
         ( ( PaymentAdjustmentHeaderVO ) form ).setAdjustmentHeaderId( "" );

         if ( deleteCounts > 0 )
         {
            // 返回“删除”成功标记
            success( request, null, "成功删除 " + deleteCounts + " 条记录", MESSAGE_HEADER );
            insertlog( request, paymentAdjustmentHeaderVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( paymentAdjustmentHeaderVO.getSelectedIds() ) );
         }
         else
         {
            // 返回“删除”成功标记
            warning( request, null, "不存在符合修改条件记录（只有“新建”或者“退回”数据才能被删除）！", MESSAGE_HEADER );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Modify ObjectListStatus
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@throws KANException
    */
   private void modify_objectListStatus( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );
         // 获得Action Form
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;
         // 初始化操作调整数据序号数量和影响的雇员集合
         int countHeaderId = 0;
         List< EmployeeVO > employees = new ArrayList< EmployeeVO >();

         // 存在选中的ID
         if ( paymentAdjustmentHeaderVO.getSelectedIds() != null && !paymentAdjustmentHeaderVO.getSelectedIds().trim().isEmpty() )
         {
            // 分割
            for ( String selectedId : paymentAdjustmentHeaderVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // 初始化ClientVO
                  final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVOTemp = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );

                  // 调用接口
                  paymentAdjustmentHeaderVOTemp.setModifyBy( getUserId( request, response ) );
                  paymentAdjustmentHeaderVOTemp.setModifyDate( new Date() );

                  // “提交”调整数据
                  if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( SUBMIT_OBJECTS ) )
                  {
                     // 新建 和 退回 状态可以 提交
                     if ( paymentAdjustmentHeaderVOTemp.getStatus().equals( "1" ) || paymentAdjustmentHeaderVOTemp.getStatus().equals( "4" ) )
                     {
                        countHeaderId++;
                        paymentAdjustmentHeaderVOTemp.setStatus( "2" );
                        paymentAdjustmentHeaderService.updatePaymentAdjustmentHeader( paymentAdjustmentHeaderVOTemp );
                        fectchEmployeeList( paymentAdjustmentHeaderVOTemp, employees );
                     }
                  }
                  // “发放”调整数据
                  else if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( ISSUE_OBJECTS ) )
                  {
                     // 批准状态可以发放
                     if ( paymentAdjustmentHeaderVOTemp.getStatus().equals( "3" ) )
                     {
                        countHeaderId++;
                        paymentAdjustmentHeaderVOTemp.setStatus( "5" );
                        paymentAdjustmentHeaderService.updatePaymentAdjustmentHeader( paymentAdjustmentHeaderVOTemp );
                        fectchEmployeeList( paymentAdjustmentHeaderVOTemp, employees );
                     }
                  }
               }
            }
         }

         // 返回标记
         if ( countHeaderId == 0 )
         {
            warning( request, null, "不存在符合修改条件记录，操作失败！", MESSAGE_HEADER );
         }
         else
         {
            // 初始化雇员姓名集合
            String employeeNamesString = "";

            // 雇员集合按姓名排序
            if ( employees != null && employees.size() > 1 )
            {
               sortEmployees( employees, request );
            }

            // 如果影响雇员数量不超过3条
            if ( employees.size() < 3 )
            {
               // 如果是提交
               if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( SUBMIT_OBJECTS ) )
               {
                  employeeNamesString = getEmployeeNameArray( employees );
                  success( request, null, employeeNamesString.toString() + " （合计：" + employees.size() + " 人）；" + countHeaderId + "条调整数据提交成功！", MESSAGE_HEADER );
               }
               // 如果是发放
               else if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( ISSUE_OBJECTS ) )
               {
                  employeeNamesString = getEmployeeNameArray( employees );
                  success( request, null, employeeNamesString.toString() + " （合计：" + employees.size() + " 人）；" + countHeaderId + "条调整数据发放成功！", MESSAGE_HEADER );
               }
            }
            else
            {
               // 如果是提交
               if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( SUBMIT_OBJECTS ) )
               {
                  // 获得前三个员工的集合
                  final List< EmployeeVO > top3Employees = getTop3EmployeeVOs( employees );
                  employeeNamesString = getEmployeeNameArray( top3Employees );
                  success( request, null, String.valueOf( countHeaderId ) + employeeNamesString.toString() + " （...等人，合计：" + employees.size() + " 人）；" + countHeaderId
                        + "条调整数据提交成功！", MESSAGE_HEADER );
               }
               // 如果是发放
               else if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( ISSUE_OBJECTS ) )
               {
                  // 获得前三个员工的集合
                  final List< EmployeeVO > top3Employees = getTop3EmployeeVOs( employees );
                  employeeNamesString = getEmployeeNameArray( top3Employees );
                  success( request, null, String.valueOf( countHeaderId ) + employeeNamesString.toString() + " （...等人，合计：" + employees.size() + " 人）；" + countHeaderId
                        + "条调整数据修改成功！", MESSAGE_HEADER );
               }
            }
         }

         // 清除Selected IDs和子Action
         ( ( PaymentAdjustmentHeaderVO ) form ).reset();
         ( ( PaymentAdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).setAdjustmentHeaderId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * GetTop3EmployeeVOs
    *	获得前三个员工的集合
    *	@param employees
    *	@return
    */
   private List< EmployeeVO > getTop3EmployeeVOs( final List< EmployeeVO > employees )
   {
      List< EmployeeVO > top3Employees = new ArrayList< EmployeeVO >();

      for ( int i = 0; i < 3; i++ )
      {
         top3Employees.add( employees.get( i ) );
      }

      return top3Employees;
   }

   /**  
    * GetEmployeeNameArray
    *	获得员工姓名集合
    *	@param employees
    *	@return
    */
   private String getEmployeeNameArray( final List< EmployeeVO > employees )
   {
      // 初始化员工姓名集合
      StringBuilder employeeNames = new StringBuilder();

      for ( int i = 0; i < employees.size(); i++ )
      {
         if ( i < ( employees.size() - 1 ) )
         {
            // 如果存在英文名
            if ( KANUtil.filterEmpty( employees.get( i ).getNameEN() ) != null )
            {

               employeeNames.append( employees.get( i ).getNameZH() + "(" + employees.get( i ).getNameEN() + ")， " );
            }
            else
            {
               employeeNames.append( employees.get( i ).getNameZH() + "， " );
            }
         }
         else
         {
            // 如果存在英文名
            if ( KANUtil.filterEmpty( employees.get( i ).getNameEN() ) != null )
            {

               employeeNames.append( employees.get( i ).getNameZH() + "(" + employees.get( i ).getNameEN() + ") " );
            }
            else
            {
               employeeNames.append( employees.get( i ).getNameZH() );
            }
         }
      }

      return employeeNames.toString();
   }

   /**  
    * SortEmployees
    *	雇员集合按姓名排序
    *	@param employees
    *	@param request
    */
   private void sortEmployees( final List< EmployeeVO > employees, final HttpServletRequest request )
   {
      Collections.sort( employees, new Comparator< EmployeeVO >()
      {
         @Override
         public int compare( EmployeeVO o1, EmployeeVO o2 )
         {
            // 中文名称排序
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               return o1.getNameZH().compareTo( o2.getNameZH() );
            }
            // 英文排序
            else
            {
               return o1.getNameEN().compareTo( o2.getNameEN() );
            }
         }
      } );
   }

   /**  
    * FectchEmployeeList
    *	生成员工集合
    *	@param paymentAdjustmentHeaderVOTemp
    *	@param employees
    */
   private void fectchEmployeeList( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVOTemp, final List< EmployeeVO > employees )
   {
      if ( !isEmployeeExist( paymentAdjustmentHeaderVOTemp, employees ) )
      {
         final EmployeeVO tempEmployeeVO = new EmployeeVO();
         tempEmployeeVO.setEmployeeId( paymentAdjustmentHeaderVOTemp.getEmployeeId() );
         tempEmployeeVO.setNameEN( paymentAdjustmentHeaderVOTemp.getEmployeeNameEN() );
         tempEmployeeVO.setNameZH( paymentAdjustmentHeaderVOTemp.getEmployeeNameZH() );
         employees.add( tempEmployeeVO );
      }
   }

   private boolean isEmployeeExist( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVOTemp, final List< EmployeeVO > employees )
   {
      if ( employees != null && employees.size() > 0 )
      {
         for ( EmployeeVO employeeVO : employees )
         {
            if ( employeeVO.getEmployeeId().equals( paymentAdjustmentHeaderVOTemp.getEmployeeId() ) )
            {
               return true;
            }
         }
         return false;
      }
      return false;
   }
}

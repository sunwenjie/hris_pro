package com.kan.hro.web.actions.biz.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentDetailService;
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentHeaderService;

public class PaymentAdjustmentDetailAction extends BaseAction
{
   /**  
    * List Object
    *	 薪资调整列表
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
         // 添加页面Token
         this.saveToken( request );

         // 获得当前页
         final String page = getPage( request );

         // 获得Action Form
         final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO = ( PaymentAdjustmentDetailVO ) form;

         // 初始化Service接口
         final PaymentAdjustmentDetailService paymentAdjustmentDetailService = ( PaymentAdjustmentDetailService ) getService( "paymentAdjustmentDetailService" );
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // 获取主表ID
         String adjustmentHeaderId = request.getParameter( "adjustmentHeaderId" );
         if ( KANUtil.filterEmpty( adjustmentHeaderId ) == null )
         {
            adjustmentHeaderId = paymentAdjustmentDetailVO.getAdjustmentHeaderId();
         }
         else
         {
            adjustmentHeaderId = KANUtil.decodeStringFromAjax( adjustmentHeaderId );
         }

         paymentAdjustmentDetailVO.setAdjustmentHeaderId( adjustmentHeaderId );

         // 如果子SubAction是删除列表操作deleteObject
         if ( getSubAction( form ).equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除列表的SubAction
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序、翻页或导出操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( paymentAdjustmentDetailVO );
         }

         // 获得主键对应对象
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder paymentAdjustmentDetailHolder = new PagedListHolder();

         // 传入当前页
         paymentAdjustmentDetailHolder.setPage( page );

         // 如果没有指定排序则默认按adjustmentDetailId排序
         if ( paymentAdjustmentDetailVO.getSortColumn() == null || paymentAdjustmentDetailVO.getSortColumn().isEmpty() )
         {
            paymentAdjustmentDetailVO.setSortColumn( "adjustmentDetailId" );
            paymentAdjustmentDetailVO.setSortOrder( "desc" );
         }

         // 传入当前值对象
         paymentAdjustmentDetailHolder.setObject( paymentAdjustmentDetailVO );
         // 设置页面记录条数
         paymentAdjustmentDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         paymentAdjustmentDetailService.getPaymentAdjustmentDetailVOsByCondition( paymentAdjustmentDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( paymentAdjustmentDetailHolder, request );
         paymentAdjustmentHeaderVO.reset( mapping, request );
         // Holder需写入Request对象
         request.setAttribute( "paymentAdjustmentHeaderForm", paymentAdjustmentHeaderVO );
         request.setAttribute( "paymentAdjustmentDetailHolder", paymentAdjustmentDetailHolder );

         // 如果是Ajax请求
         if ( new Boolean( this.getAjax( request ) ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPaymentAdjustmentDetailTable" );
         }

         return mapping.findForward( "listPaymentAdjustmentDetail" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * List Object Confirm
    *  薪资调整列表
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2014-01-03
   public ActionForward list_object_confirm( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 获得当前页
         final String page = getPage( request );

         // 初始化Service接口
         final PaymentAdjustmentDetailService paymentAdjustmentDetailService = ( PaymentAdjustmentDetailService ) getService( "paymentAdjustmentDetailService" );
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // 获取主表ID
         final String adjustmentHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "adjustmentHeaderId" ) );

         // 获得Action Form
         final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO = ( PaymentAdjustmentDetailVO ) form;
         paymentAdjustmentDetailVO.setAdjustmentHeaderId( adjustmentHeaderId );

         // 获得主键对应对象
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder paymentAdjustmentDetailHolder = new PagedListHolder();

         // 传入当前页
         paymentAdjustmentDetailHolder.setPage( page );

         // 如果没有指定排序则默认按adjustmentDetailId排序
         if ( paymentAdjustmentDetailVO.getSortColumn() == null || paymentAdjustmentDetailVO.getSortColumn().isEmpty() )
         {
            paymentAdjustmentDetailVO.setSortColumn( "adjustmentDetailId" );
            paymentAdjustmentDetailVO.setSortOrder( "desc" );
         }

         // 传入当前值对象
         paymentAdjustmentDetailHolder.setObject( paymentAdjustmentDetailVO );
         // 设置页面记录条数
         paymentAdjustmentDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         paymentAdjustmentDetailService.getPaymentAdjustmentDetailVOsByCondition( paymentAdjustmentDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( paymentAdjustmentDetailHolder, request );
         paymentAdjustmentHeaderVO.reset( mapping, request );
         // Holder需写入Request对象
         request.setAttribute( "paymentAdjustmentHeaderForm", paymentAdjustmentHeaderVO );
         request.setAttribute( "paymentAdjustmentDetailHolder", paymentAdjustmentDetailHolder );

         return mapping.findForward( "listPaymentAdjustmentDetailConfrim" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   // Added by Kevin Jin 2014-01-02
   public ActionForward to_objectNew_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );

         // 设置SubAction
         ( ( PaymentAdjustmentDetailVO ) form ).setAdjustmentHeaderId( KANUtil.decodeString( ( ( PaymentAdjustmentDetailVO ) form ).getAdjustmentHeaderId() ) );
         ( ( PaymentAdjustmentDetailVO ) form ).setStatus( "1" );
         ( ( PaymentAdjustmentDetailVO ) form ).setSubAction( CREATE_OBJECT );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageAdjustmentDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Add Object
    *	添加PaymentAdjustmentDetailVO
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      if ( this.isTokenValid( request, true ) )
      {
         try
         {
            // 初始化接口
            final PaymentAdjustmentDetailService paymentAdjustmentDetailService = ( PaymentAdjustmentDetailService ) getService( "paymentAdjustmentDetailService" );

            // 初始化PaymentAdjustmentDetailVO
            final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO = ( PaymentAdjustmentDetailVO ) form;

            // 初始化ItemVO
            final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( paymentAdjustmentDetailVO.getItemId() );
            paymentAdjustmentDetailVO.setAdjustmentHeaderId( KANUtil.decodeString( paymentAdjustmentDetailVO.getAdjustmentHeaderId() ) );
            paymentAdjustmentDetailVO.setItemNo( itemVO.getItemNo() );
            paymentAdjustmentDetailVO.setNameZH( itemVO.getNameZH() );
            paymentAdjustmentDetailVO.setNameEN( itemVO.getNameEN() );

            if ( KANUtil.filterEmpty( itemVO.getPersonalTax() ) != null && itemVO.getPersonalTax().equals( "1" ) )
            {
               paymentAdjustmentDetailVO.setAddtionalBillAmountPersonal( paymentAdjustmentDetailVO.getBillAmountPersonal() );
            }

            paymentAdjustmentDetailVO.setAccountId( getAccountId( request, response ) );
            paymentAdjustmentDetailVO.setCreateBy( getUserId( request, response ) );
            paymentAdjustmentDetailVO.setModifyBy( getUserId( request, response ) );

            // 添加对象
            paymentAdjustmentDetailService.insertPaymentAdjustmentDetail( paymentAdjustmentDetailVO );

            // 返回“添加”成功的标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, paymentAdjustmentDetailVO, Operate.ADD, paymentAdjustmentDetailVO.getAdjustmentDetailId(), null );

            ( ( PaymentAdjustmentDetailVO ) form ).reset();
         }
         catch ( Exception e )
         {
            throw new KANException( e );
         }
      }

      return list_object( mapping, form, request, response );
   }

   /**  
    * To ObjectModify
    *	 跳转到修改
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
    * Ajax 跳转修改科目
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );

         // 初始化Service接口
         final PaymentAdjustmentDetailService paymentAdjustmentDetailService = ( PaymentAdjustmentDetailService ) getService( "paymentAdjustmentDetailService" );

         // 从表ID
         final String adjustmentDetailId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );

         // 获得AdjustmentDetailVO对象
         final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO = paymentAdjustmentDetailService.getPaymentAdjustmentDetailVOByAdjustmentDetailId( adjustmentDetailId );

         // 国际化传值
         paymentAdjustmentDetailVO.reset( mapping, request );

         // 设置SubAction
         paymentAdjustmentDetailVO.setSubAction( VIEW_OBJECT );

         // 传入request对象
         request.setAttribute( "paymentAdjustmentDetailForm", paymentAdjustmentDetailVO );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageAdjustmentDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Modify Object
    *	 修改科目
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化接口
            final PaymentAdjustmentDetailService paymentAdjustmentDetailService = ( PaymentAdjustmentDetailService ) getService( "paymentAdjustmentDetailService" );

            // 获得当前主键和主表主键ID
            final String adjustmentDetailId = KANUtil.decodeString( request.getParameter( "adjustmentDetailId" ) );

            // 获得 PaymentAdjustmentDetailVO
            final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO = paymentAdjustmentDetailService.getPaymentAdjustmentDetailVOByAdjustmentDetailId( adjustmentDetailId );
            paymentAdjustmentDetailVO.update( ( PaymentAdjustmentDetailVO ) form );

            // 初始化ItemVO
            final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( paymentAdjustmentDetailVO.getItemId() );
            paymentAdjustmentDetailVO.setAdjustmentHeaderId( KANUtil.decodeString( paymentAdjustmentDetailVO.getAdjustmentHeaderId() ) );
            paymentAdjustmentDetailVO.setItemNo( itemVO.getItemNo() );
            paymentAdjustmentDetailVO.setNameZH( itemVO.getNameZH() );
            paymentAdjustmentDetailVO.setNameEN( itemVO.getNameEN() );
            paymentAdjustmentDetailVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            paymentAdjustmentDetailService.updatePaymentAdjustmentDetail( paymentAdjustmentDetailVO );

            // 返回“编辑”成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, paymentAdjustmentDetailVO, Operate.MODIFY, paymentAdjustmentDetailVO.getAdjustmentDetailId(), null );

            // 清空Form条件
            ( ( PaymentAdjustmentDetailVO ) form ).reset();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
   }

   /**
    * 删除detail
    */
   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final PaymentAdjustmentDetailService paymentAdjustmentDetailService = ( PaymentAdjustmentDetailService ) getService( "paymentAdjustmentDetailService" );
         // 获得Action Form
         final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO = ( PaymentAdjustmentDetailVO ) form;

         // 存在选中的ID
         if ( paymentAdjustmentDetailVO.getSelectedIds() != null && !paymentAdjustmentDetailVO.getSelectedIds().equals( "" ) )
         {
            for ( String selectedId : paymentAdjustmentDetailVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               final PaymentAdjustmentDetailVO paymentAdjustmentDetailVOTemp = paymentAdjustmentDetailService.getPaymentAdjustmentDetailVOByAdjustmentDetailId( KANUtil.decodeStringFromAjax( selectedId ) );
               paymentAdjustmentDetailVOTemp.setModifyBy( getUserId( request, response ) );
               paymentAdjustmentDetailService.deletePaymentAdjustmentDetail( paymentAdjustmentDetailVOTemp );
            }

            insertlog( request, paymentAdjustmentDetailVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( paymentAdjustmentDetailVO.getSelectedIds() ) );
         }

         // 清除Selected IDs和子Action
         paymentAdjustmentDetailVO.setSelectedIds( "" );
         paymentAdjustmentDetailVO.setSubAction( "" );

         // 返回“删除”成功标记
         success( request, MESSAGE_TYPE_DELETE, null, MESSAGE_DETAIL );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

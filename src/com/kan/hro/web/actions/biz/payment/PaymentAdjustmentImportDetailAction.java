package com.kan.hro.web.actions.biz.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportDetailVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportHeaderVO;
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentImportDetailService;
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentImportHeaderService;

public class PaymentAdjustmentImportDetailAction extends BaseAction
{

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final PaymentAdjustmentImportDetailService paymentAdjustmentImportDetailService = ( PaymentAdjustmentImportDetailService ) getService( "paymentAdjustmentImportDetailService" );
         final PaymentAdjustmentImportHeaderService paymentAdjustmentImportHeaderService = ( PaymentAdjustmentImportHeaderService ) getService( "paymentAdjustmentImportHeaderService" );
         // 获得Action Form
         final PaymentAdjustmentImportDetailVO paymentAdjustmentImportDetailVO = ( PaymentAdjustmentImportDetailVO ) form;
         paymentAdjustmentImportDetailVO.setAdjustmentHeaderId( KANUtil.decodeStringFromAjax( request.getParameter( "adjustmentHeaderId" ) ) );

         // 如果没有指定排序则默认按 adjustmentHeaderId排序
         if ( paymentAdjustmentImportDetailVO.getSortColumn() == null || paymentAdjustmentImportDetailVO.getSortColumn().isEmpty() )
         {
            paymentAdjustmentImportDetailVO.setSortColumn( "a.adjustmentDetailId" );
            paymentAdjustmentImportDetailVO.setSortOrder( "desc" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder paymentAdjustmentImprotDetailHolder = new PagedListHolder();
         // 传入当前页
         paymentAdjustmentImprotDetailHolder.setPage( page );
         // 传入当前值对象
         paymentAdjustmentImprotDetailHolder.setObject( paymentAdjustmentImportDetailVO );
         // 设置页面记录条数
         paymentAdjustmentImprotDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         paymentAdjustmentImportDetailService.getPaymentAdjustmentImportDetailVOsByCondition( paymentAdjustmentImprotDetailHolder, true );
         refreshHolder( paymentAdjustmentImprotDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "paymentAdjustmentImportDetailHolder", paymentAdjustmentImprotDetailHolder );
         PaymentAdjustmentImportHeaderVO paymentAdjustmentImportHeaderVO = paymentAdjustmentImportHeaderService.getPaymentAdjustmentImportHeaderVOsById( KANUtil.decodeStringFromAjax( request.getParameter( "adjustmentHeaderId" ) ) ,getAccountId( request, response ));
         paymentAdjustmentImportHeaderVO.reset( mapping, request );
         request.setAttribute( "paymentAdjustmentImportHeaderForm", paymentAdjustmentImportHeaderVO );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPaymentAdjustmentImportDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listPaymentAdjustmentImportDetail" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }

}

package com.kan.hro.web.actions.biz.payment;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportBatchVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportHeaderVO;
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentImportBatchService;
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentImportHeaderService;

public class PaymentAdjustmentImportHeaderAction extends BaseAction
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
         final PaymentAdjustmentImportHeaderService paymentAdjustmentImportHeaderService = ( PaymentAdjustmentImportHeaderService ) getService( "paymentAdjustmentImportHeaderService" );
         final PaymentAdjustmentImportBatchService paymentAdjustmentImportBatchService = ( PaymentAdjustmentImportBatchService ) getService( "paymentAdjustmentImportBatchService" );

         // 获得Action Form
         final PaymentAdjustmentImportHeaderVO paymentAdjustmentImportHeaderVO = ( PaymentAdjustmentImportHeaderVO ) form;
         paymentAdjustmentImportHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) ) );

         // 如果没有指定排序则默认按 adjustmentHeaderId排序
         if ( paymentAdjustmentImportHeaderVO.getSortColumn() == null || paymentAdjustmentImportHeaderVO.getSortColumn().isEmpty() )
         {
            paymentAdjustmentImportHeaderVO.setSortColumn( "a.adjustmentHeaderId" );
            paymentAdjustmentImportHeaderVO.setSortOrder( "desc" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder paymentAdjustmentImprotBatchHolder = new PagedListHolder();
         // 传入当前页
         paymentAdjustmentImprotBatchHolder.setPage( page );
         // 传入当前值对象
         paymentAdjustmentImprotBatchHolder.setObject( paymentAdjustmentImportHeaderVO );
         // 设置页面记录条数
         paymentAdjustmentImprotBatchHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         paymentAdjustmentImportHeaderService.getPaymentAdjustmentImportHeaderVOsByCondition( paymentAdjustmentImprotBatchHolder, true );
         refreshHolder( paymentAdjustmentImprotBatchHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "paymentAdjustmentImportHeaderHolder", paymentAdjustmentImprotBatchHolder );

         PaymentAdjustmentImportBatchVO paymentAdjustmentImportBatchVO = paymentAdjustmentImportBatchService.getPaymentAdjustmentImportBatchById( KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) ) );
         request.setAttribute( "paymentAdjustmentImportBatchVO", paymentAdjustmentImportBatchVO );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPaymentAdjustmentImportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listPaymentAdjustmentImportHeader" );
   }

   public ActionForward backUpRecord( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      final PaymentAdjustmentImportHeaderService paymentAdjustmentImportHeaderService = ( PaymentAdjustmentImportHeaderService ) getService( "paymentAdjustmentImportHeaderService" );
      String selectedId= request.getParameter( "selectedIds" );
      String[] ids =null;
      if(StringUtils.isNotBlank( selectedId )){
         ids=selectedId.split( "," );
      }
      
      String batchId = request.getParameter( "batchId" );
      int count = 0;
      if(ids!=null && ids.length>0){
          count = paymentAdjustmentImportHeaderService.backUpRecord( ids, KANUtil.decodeStringFromAjax( batchId ) );
      }
      
      if ( count == 0 )
      {
         PaymentAdjustmentImportBatchVO payAdjustmentImportBatchVO = new PaymentAdjustmentImportBatchVO();
         payAdjustmentImportBatchVO.reset( mapping, request );
         return new PaymentAdjustmentImportBatchAction().list_object( mapping, payAdjustmentImportBatchVO, request, response );
      }
      else
      {
         final PaymentAdjustmentImportHeaderVO paymentAdjustmentImportHeaderVO = ( PaymentAdjustmentImportHeaderVO ) form;
         paymentAdjustmentImportHeaderVO.setBatchId( batchId );
         return list_object( mapping, paymentAdjustmentImportHeaderVO, request, response );
      }
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

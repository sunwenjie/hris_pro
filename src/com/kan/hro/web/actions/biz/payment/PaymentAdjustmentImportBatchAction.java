package com.kan.hro.web.actions.biz.payment;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentImportBatchVO;
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentImportBatchService;

public class PaymentAdjustmentImportBatchAction extends BaseAction
{
   public static String accessAction = "HRO_PAYMENT_ADJUSTMENT_IMPORT_BATCH";

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
         final PaymentAdjustmentImportBatchService paymentAdjustmentImportBatchService = ( PaymentAdjustmentImportBatchService ) getService( "paymentAdjustmentImportBatchService" );

         // 获得Action Form
         final PaymentAdjustmentImportBatchVO paymentAdjustmentImportBatchVO = ( PaymentAdjustmentImportBatchVO ) form;

         // 如果没有指定排序则默认按 adjustmentHeaderId排序
         if ( paymentAdjustmentImportBatchVO.getSortColumn() == null || paymentAdjustmentImportBatchVO.getSortColumn().isEmpty() )
         {
            paymentAdjustmentImportBatchVO.setSortColumn( "a.batchId" );
            paymentAdjustmentImportBatchVO.setSortOrder( "desc" );
         }

         //处理数据权限
         //         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, PaymentAdjustmentImportBatchVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder paymentAdjustmentImprotBatchHolder = new PagedListHolder();
         // 传入当前页
         paymentAdjustmentImprotBatchHolder.setPage( page );
         // 传入当前值对象
         paymentAdjustmentImprotBatchHolder.setObject( paymentAdjustmentImportBatchVO );
         // 设置页面记录条数
         paymentAdjustmentImprotBatchHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         paymentAdjustmentImportBatchService.getPaymentAdjustmentImportBatchVOsByCondition( paymentAdjustmentImprotBatchHolder, true );
         refreshHolder( paymentAdjustmentImprotBatchHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "paymentAdjustmentImportBatchHolder", paymentAdjustmentImprotBatchHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPaymentAdjustmentImportBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listPaymentAdjustmentImportBatch" );
   }

   public ActionForward submit_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ActionForm
         final PaymentAdjustmentImportBatchVO paymentAdjustmentImportBatchVO = ( PaymentAdjustmentImportBatchVO ) form;
         paymentAdjustmentImportBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final PaymentAdjustmentImportBatchService paymentAdjustmentImportBatchService = ( PaymentAdjustmentImportBatchService ) getService( "paymentAdjustmentImportBatchService" );

         // 获得勾选ID
         final String batchIds = paymentAdjustmentImportBatchVO.getSelectedIds();

         // 存在勾选ID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( String encodedSelectId : selectedIdArray )
            {
               final PaymentAdjustmentImportBatchVO submitObject = paymentAdjustmentImportBatchService.getPaymentAdjustmentImportBatchById( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );
               submitObject.setModifyDate( new Date() );
               rows = rows + paymentAdjustmentImportBatchService.updatePaymentAdjustmentImportBatch( submitObject );
            }

            if ( rows == 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
            }

         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   /**
    * 退回,物理删除temp表
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward back_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取ActionForm
         final PaymentAdjustmentImportBatchVO paymentAdjustmentImportBatchVO = ( PaymentAdjustmentImportBatchVO ) form;
         paymentAdjustmentImportBatchVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final PaymentAdjustmentImportBatchService paymentAdjustmentImportBatchService = ( PaymentAdjustmentImportBatchService ) getService( "paymentAdjustmentImportBatchService" );

         // 获得勾选ID
         final String batchIds = paymentAdjustmentImportBatchVO.getSelectedIds();

         // 存在勾选ID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // 分割选择项
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // 遍历selectedIds 以做修改
            for ( String encodedSelectId : selectedIdArray )
            {
               final PaymentAdjustmentImportBatchVO submitObject = paymentAdjustmentImportBatchService.getPaymentAdjustmentImportBatchById( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );

               rows = rows + paymentAdjustmentImportBatchService.backBatch( submitObject );
            }

            if ( rows < 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE, "退回成功!" );
            }
         }
         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
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

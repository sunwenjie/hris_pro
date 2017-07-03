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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentImportBatchService paymentAdjustmentImportBatchService = ( PaymentAdjustmentImportBatchService ) getService( "paymentAdjustmentImportBatchService" );

         // ���Action Form
         final PaymentAdjustmentImportBatchVO paymentAdjustmentImportBatchVO = ( PaymentAdjustmentImportBatchVO ) form;

         // ���û��ָ��������Ĭ�ϰ� adjustmentHeaderId����
         if ( paymentAdjustmentImportBatchVO.getSortColumn() == null || paymentAdjustmentImportBatchVO.getSortColumn().isEmpty() )
         {
            paymentAdjustmentImportBatchVO.setSortColumn( "a.batchId" );
            paymentAdjustmentImportBatchVO.setSortOrder( "desc" );
         }

         //��������Ȩ��
         //         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, PaymentAdjustmentImportBatchVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder paymentAdjustmentImprotBatchHolder = new PagedListHolder();
         // ���뵱ǰҳ
         paymentAdjustmentImprotBatchHolder.setPage( page );
         // ���뵱ǰֵ����
         paymentAdjustmentImprotBatchHolder.setObject( paymentAdjustmentImportBatchVO );
         // ����ҳ���¼����
         paymentAdjustmentImprotBatchHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         paymentAdjustmentImportBatchService.getPaymentAdjustmentImportBatchVOsByCondition( paymentAdjustmentImprotBatchHolder, true );
         refreshHolder( paymentAdjustmentImprotBatchHolder, request );
         // Holder��д��Request����
         request.setAttribute( "paymentAdjustmentImportBatchHolder", paymentAdjustmentImprotBatchHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPaymentAdjustmentImportBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listPaymentAdjustmentImportBatch" );
   }

   public ActionForward submit_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final PaymentAdjustmentImportBatchVO paymentAdjustmentImportBatchVO = ( PaymentAdjustmentImportBatchVO ) form;
         paymentAdjustmentImportBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentImportBatchService paymentAdjustmentImportBatchService = ( PaymentAdjustmentImportBatchService ) getService( "paymentAdjustmentImportBatchService" );

         // ��ù�ѡID
         final String batchIds = paymentAdjustmentImportBatchVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
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
    * �˻�,����ɾ��temp��
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
         // ��ȡActionForm
         final PaymentAdjustmentImportBatchVO paymentAdjustmentImportBatchVO = ( PaymentAdjustmentImportBatchVO ) form;
         paymentAdjustmentImportBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentImportBatchService paymentAdjustmentImportBatchService = ( PaymentAdjustmentImportBatchService ) getService( "paymentAdjustmentImportBatchService" );

         // ��ù�ѡID
         final String batchIds = paymentAdjustmentImportBatchVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
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
               success( request, MESSAGE_TYPE_UPDATE, "�˻سɹ�!" );
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

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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentImportHeaderService paymentAdjustmentImportHeaderService = ( PaymentAdjustmentImportHeaderService ) getService( "paymentAdjustmentImportHeaderService" );
         final PaymentAdjustmentImportBatchService paymentAdjustmentImportBatchService = ( PaymentAdjustmentImportBatchService ) getService( "paymentAdjustmentImportBatchService" );

         // ���Action Form
         final PaymentAdjustmentImportHeaderVO paymentAdjustmentImportHeaderVO = ( PaymentAdjustmentImportHeaderVO ) form;
         paymentAdjustmentImportHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) ) );

         // ���û��ָ��������Ĭ�ϰ� adjustmentHeaderId����
         if ( paymentAdjustmentImportHeaderVO.getSortColumn() == null || paymentAdjustmentImportHeaderVO.getSortColumn().isEmpty() )
         {
            paymentAdjustmentImportHeaderVO.setSortColumn( "a.adjustmentHeaderId" );
            paymentAdjustmentImportHeaderVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder paymentAdjustmentImprotBatchHolder = new PagedListHolder();
         // ���뵱ǰҳ
         paymentAdjustmentImprotBatchHolder.setPage( page );
         // ���뵱ǰֵ����
         paymentAdjustmentImprotBatchHolder.setObject( paymentAdjustmentImportHeaderVO );
         // ����ҳ���¼����
         paymentAdjustmentImprotBatchHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         paymentAdjustmentImportHeaderService.getPaymentAdjustmentImportHeaderVOsByCondition( paymentAdjustmentImprotBatchHolder, true );
         refreshHolder( paymentAdjustmentImprotBatchHolder, request );
         // Holder��д��Request����
         request.setAttribute( "paymentAdjustmentImportHeaderHolder", paymentAdjustmentImprotBatchHolder );

         PaymentAdjustmentImportBatchVO paymentAdjustmentImportBatchVO = paymentAdjustmentImportBatchService.getPaymentAdjustmentImportBatchById( KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) ) );
         request.setAttribute( "paymentAdjustmentImportBatchVO", paymentAdjustmentImportBatchVO );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPaymentAdjustmentImportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
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

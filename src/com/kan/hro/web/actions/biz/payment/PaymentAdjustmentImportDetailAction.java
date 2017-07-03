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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentImportDetailService paymentAdjustmentImportDetailService = ( PaymentAdjustmentImportDetailService ) getService( "paymentAdjustmentImportDetailService" );
         final PaymentAdjustmentImportHeaderService paymentAdjustmentImportHeaderService = ( PaymentAdjustmentImportHeaderService ) getService( "paymentAdjustmentImportHeaderService" );
         // ���Action Form
         final PaymentAdjustmentImportDetailVO paymentAdjustmentImportDetailVO = ( PaymentAdjustmentImportDetailVO ) form;
         paymentAdjustmentImportDetailVO.setAdjustmentHeaderId( KANUtil.decodeStringFromAjax( request.getParameter( "adjustmentHeaderId" ) ) );

         // ���û��ָ��������Ĭ�ϰ� adjustmentHeaderId����
         if ( paymentAdjustmentImportDetailVO.getSortColumn() == null || paymentAdjustmentImportDetailVO.getSortColumn().isEmpty() )
         {
            paymentAdjustmentImportDetailVO.setSortColumn( "a.adjustmentDetailId" );
            paymentAdjustmentImportDetailVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder paymentAdjustmentImprotDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         paymentAdjustmentImprotDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         paymentAdjustmentImprotDetailHolder.setObject( paymentAdjustmentImportDetailVO );
         // ����ҳ���¼����
         paymentAdjustmentImprotDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         paymentAdjustmentImportDetailService.getPaymentAdjustmentImportDetailVOsByCondition( paymentAdjustmentImprotDetailHolder, true );
         refreshHolder( paymentAdjustmentImprotDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "paymentAdjustmentImportDetailHolder", paymentAdjustmentImprotDetailHolder );
         PaymentAdjustmentImportHeaderVO paymentAdjustmentImportHeaderVO = paymentAdjustmentImportHeaderService.getPaymentAdjustmentImportHeaderVOsById( KANUtil.decodeStringFromAjax( request.getParameter( "adjustmentHeaderId" ) ) ,getAccountId( request, response ));
         paymentAdjustmentImportHeaderVO.reset( mapping, request );
         request.setAttribute( "paymentAdjustmentImportHeaderForm", paymentAdjustmentImportHeaderVO );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPaymentAdjustmentImportDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
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

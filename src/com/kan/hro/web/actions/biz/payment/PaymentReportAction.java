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
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.payment.PaymentReportVO;
import com.kan.hro.service.inf.biz.payment.PaymentReportService;

public class PaymentReportAction extends BaseAction
{
   // Module AccessAction
   public static final String ACCESS_ACTION = "HRO_SALARY_PAYMENT_AVG_REPORT";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���Action Form
         final PaymentReportVO paymentReportVO = ( PaymentReportVO ) form;
         if ( KANUtil.filterEmpty( paymentReportVO.getMonthlyBegin() ) == null || KANUtil.filterEmpty( paymentReportVO.getMonthlyEnd() ) == null )
         {
            paymentReportVO.setMonthlyBegin( KANUtil.getMonthly( new Date() ) );
            paymentReportVO.setMonthlyEnd( KANUtil.getMonthly( new Date() ) );
         }
         // ��ʼ��Service�ӿ�
         final PaymentReportService paymentReportService = ( PaymentReportService ) getService( "paymentReportService" );
         // ����������Ҫ���롣
         decodedObject( paymentReportVO );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder paymentReportHolder = new PagedListHolder();
         // ���뵱ǰҳ
         paymentReportHolder.setPage( page );
         // ���뵱ǰֵ����
         paymentReportHolder.setObject( paymentReportVO );
         // ����ҳ���¼����
         paymentReportHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         paymentReportService.getAVGPaymentReportVOsByCondition( paymentReportHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( paymentReportHolder, request );
         // Holder��д��Request����
         request.setAttribute( "paymentReportHolder", paymentReportHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               request.setAttribute( "holderName", "paymentReportHolder" );
               request.setAttribute( "fileName", request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "ƽ�����ʱ���" : "AVG Payment Report" );
               request.setAttribute( "nameZHArray", paymentReportVO.getTitleNameList().split( "," ) );
               request.setAttribute( "nameSysArray", paymentReportVO.getTitleIdList().split( "," ) );
               // �����ļ�
               return new DownloadFileAction().commonExportList( mapping, form, request, response, true );
            }
            else
            {
               request.setAttribute( "role", BaseAction.getRole( request, response ) );
               // Ajax Table���ã�ֱ�Ӵ���Item JSP
               return mapping.findForward( "listAVGPaymentReportTable" );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listAVGPaymentReport" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // TODO Auto-generated method stub

   }

}

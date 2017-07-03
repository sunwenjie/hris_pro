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
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final PaymentReportVO paymentReportVO = ( PaymentReportVO ) form;
         if ( KANUtil.filterEmpty( paymentReportVO.getMonthlyBegin() ) == null || KANUtil.filterEmpty( paymentReportVO.getMonthlyEnd() ) == null )
         {
            paymentReportVO.setMonthlyBegin( KANUtil.getMonthly( new Date() ) );
            paymentReportVO.setMonthlyEnd( KANUtil.getMonthly( new Date() ) );
         }
         // 初始化Service接口
         final PaymentReportService paymentReportService = ( PaymentReportService ) getService( "paymentReportService" );
         // 搜索内容需要解码。
         decodedObject( paymentReportVO );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder paymentReportHolder = new PagedListHolder();
         // 传入当前页
         paymentReportHolder.setPage( page );
         // 传入当前值对象
         paymentReportHolder.setObject( paymentReportVO );
         // 设置页面记录条数
         paymentReportHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         paymentReportService.getAVGPaymentReportVOsByCondition( paymentReportHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         // 刷新Holder，国际化传值
         refreshHolder( paymentReportHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "paymentReportHolder", paymentReportHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               request.setAttribute( "holderName", "paymentReportHolder" );
               request.setAttribute( "fileName", request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "平均工资报表" : "AVG Payment Report" );
               request.setAttribute( "nameZHArray", paymentReportVO.getTitleNameList().split( "," ) );
               request.setAttribute( "nameSysArray", paymentReportVO.getTitleIdList().split( "," ) );
               // 导出文件
               return new DownloadFileAction().commonExportList( mapping, form, request, response, true );
            }
            else
            {
               request.setAttribute( "role", BaseAction.getRole( request, response ) );
               // Ajax Table调用，直接传回Item JSP
               return mapping.findForward( "listAVGPaymentReportTable" );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
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

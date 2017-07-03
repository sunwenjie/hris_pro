package com.kan.hro.web.actions.biz.payment;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.payment.PaymentBatchVO;
import com.kan.hro.domain.biz.payment.PaymentDTO;
import com.kan.hro.domain.biz.payment.PaymentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.domain.biz.payment.PayslipDTO;
import com.kan.hro.domain.biz.payment.PayslipDetailView;
import com.kan.hro.service.inf.biz.employee.EmployeeService;
import com.kan.hro.service.inf.biz.payment.PaymentBatchService;
import com.kan.hro.service.inf.biz.payment.PaymentHeaderService;
import com.kan.hro.service.inf.biz.payment.PayslipDetailViewService;

public class PaymentHeaderAction extends BaseAction
{
   // �����java������
   private static String javaObjectName = "com.kan.hro.domain.biz.payment.PayslipDTO";

   // Access Action
   public static final String ACCESS_ACTION = "HRO_PAYMENT_BATCH";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ����Ƿ�Ajax����
      final String ajax = request.getParameter( "ajax" );
      // ��ʼ��Service�ӿ�
      final PaymentBatchService paymentBatchService = ( PaymentBatchService ) getService( "paymentBatchService" );
      // �����������ID
      final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

      // ��ʼ��PaymentBatchVO
      PaymentBatchVO paymentBatchVO = new PaymentBatchVO();
      paymentBatchVO.setBatchId( batchId );
      paymentBatchVO.setCorpId( getCorpId( request, response ) );
      paymentBatchVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
      paymentBatchVO.setAccountId( getAccountId( request, response ) );

      if ( StringUtils.equals( request.getParameter( "statusFlag" ), PaymentBatchService.STATUS_FLAG_SUBMIT ) )
      {
         paymentBatchVO.setStatus( "2" );
      }
      //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), ACCESS_ACTION, paymentBatchVO );
      setDataAuth( request, response, paymentBatchVO );
      final List< Object > paymentBatchVOs = paymentBatchService.getPaymentBatchVOsByCondition( paymentBatchVO );

      if ( paymentBatchVOs != null && paymentBatchVOs.size() > 0 )
      {
         paymentBatchVO = ( PaymentBatchVO ) paymentBatchVOs.get( 0 );
      }

      paymentBatchVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
      paymentBatchVO.reset( null, request );

      request.setAttribute( "paymentBatchForm", paymentBatchVO );
      request.setAttribute( "statusFlag", request.getParameter( "statusFlag" ) );
      request.setAttribute( "pageFlag", PaymentBatchService.PAGE_FLAG_HEADER );

      /**
       * PaymentHeaderDTO����
       */
      // ��ʼ��PagedListHolder
      PagedListHolder paymentHeaderDTOHolder = new PagedListHolder();

      // ��ʼ��Service�ӿ�
      final PaymentHeaderService paymentHeaderService = ( PaymentHeaderService ) getService( "paymentHeaderService" );

      // ��ʼ��PaymentHeaderVO
      final PaymentHeaderVO paymentHeaderVO = ( PaymentHeaderVO ) form;

      //��������Ȩ��
      //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), ACCESS_ACTION, paymentHeaderVO );
      setDataAuth( request, response, paymentHeaderVO );
      if ( ajax != null && ajax.equals( "true" ) )
      {
         decodedObject( paymentHeaderVO );
      }

      paymentHeaderVO.setAccountId( getAccountId( request, response ) );
      paymentHeaderVO.setCorpId( getCorpId( request, response ) );
      paymentHeaderVO.setBatchId( batchId );
      // ����
      paymentHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
      paymentHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

      if ( KANUtil.filterEmpty( paymentHeaderVO.getStatus() ) == null )
      {
         if ( request.getParameter( "statusFlag" ) != null )
         {
            paymentHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
         }
      }

      // Ĭ������
      if ( request.getParameter( "sortColumn" ) == null || request.getParameter( "sortColumn" ).trim().isEmpty() )
      {
         paymentHeaderVO.setSortColumn( "paymentHeaderId" );
         paymentHeaderVO.setSortOrder( "desc" );
      }

      // �����In House��¼��������������
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         passClientOrders( request, response );

      }

      // ����ҳ��
      paymentHeaderDTOHolder.setPage( getPage( request ) );
      paymentHeaderDTOHolder.setObject( paymentHeaderVO );
      // ����ҳ���С
      paymentHeaderDTOHolder.setPageSize( listPageSize );

      paymentHeaderService.getPaymentHeaderDTOsByCondition( paymentHeaderDTOHolder, true );

      // Reset PaymentHeaderDTOHolder
      if ( paymentHeaderDTOHolder != null && paymentHeaderDTOHolder.getHolderSize() > 0 )
      {
         final List< Object > paymentHeaderDTOOjbects = paymentHeaderDTOHolder.getSource();

         if ( paymentHeaderDTOOjbects != null && paymentHeaderDTOOjbects.size() > 0 )
         {
            for ( Object paymentHeaderDTOOjbect : paymentHeaderDTOOjbects )
            {
               final PaymentDTO tempPaymentHeaderDTO = ( PaymentDTO ) paymentHeaderDTOOjbect;

               // Reset PaymentHeaderVO
               final PaymentHeaderVO tempPaymentHeaderVO = tempPaymentHeaderDTO.getPaymentHeaderVO();

               if ( tempPaymentHeaderVO != null )
               {
                  tempPaymentHeaderVO.reset( mapping, request );
               }

               // Reset PaymentDetailVO
               final List< PaymentDetailVO > paymentDetailVOs = tempPaymentHeaderDTO.getPaymentDetailVOs();

               if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
               {
                  for ( PaymentDetailVO tempPaymentDetailVO : paymentDetailVOs )
                  {
                     tempPaymentDetailVO.reset( mapping, request );
                  }
               }
            }
         }
      }

      request.setAttribute( "paymentDTOHolder", paymentHeaderDTOHolder );
      String accessAction = "HRO_PAYMENT_BATCH";

      if ( request.getParameter( "statusFlag" ).equals( PaymentBatchService.STATUS_FLAG_SUBMIT ) )
      {
         accessAction = "HRO_PAYMENT_BATCH_SUBMIT";
      }
      else if ( request.getParameter( "statusFlag" ).equals( PaymentBatchService.STATUS_FLAG_ISSUE ) )
      {
         accessAction = "HRO_PAYMENT_BATCH_ISSUE";
      }

      request.setAttribute( "authAccessAction", accessAction );

      // ��ʾ������ť
      request.setAttribute( "javaObjectName", javaObjectName );
      showExportButton( mapping, form, request, response );

      // �趨Ȩ��
      setHRFunctionRole( mapping, form, request, response );

      // �����ajax�������ɾ����������ת��Table�������ֶ�Ӧ��jsp
      if ( new Boolean( ajax ) )
      {
         // д��Role
         request.setAttribute( "role", getRole( request, response ) );
         return mapping.findForward( "listPaymentHeaderTable" );
      }
      final String boolSearchHeader = request.getParameter( "searchHeader" );
      // ���ȫ������״̬���ı��ˣ�����ת����һ��
      if ( paymentHeaderDTOHolder == null || paymentHeaderDTOHolder.getHolderSize() == 0 )
      {
         if ( boolSearchHeader == null || !boolSearchHeader.equals( "true" ) )
         {
            PaymentBatchVO paymentBatch = new PaymentBatchVO();
            paymentBatch.reset( null, request );
            paymentBatch.setBatchId( "" );
            request.setAttribute( "paymentBatchForm", paymentBatch );
            request.setAttribute( "messageInfo", true );
            return new PaymentAction().list_object( mapping, paymentBatch, request, response );
         }
      }
      return mapping.findForward( "listPaymentHeader" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use

   }

   // ����StatusFlag���״̬
   private String getStatusesByStatusFlag( final String statusFlag )
   {
      // ��ʼ����Ĭ��Ϊ��Ԥ����
      String status = "1";

      if ( statusFlag != null && !statusFlag.isEmpty() )
      {
         //����̨��
         if ( statusFlag.equalsIgnoreCase( PaymentBatchService.STATUS_FLAG_SUBMIT ) )
         {
            return "2";
         }
         // ����ȷ��
         else if ( statusFlag.equals( PaymentBatchService.STATUS_FLAG_ISSUE ) )
         {
            return "3";
         }
      }

      return status;
   }

   public ActionForward list_object_mobile( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         PaymentHeaderService paymentHeaderService = ( PaymentHeaderService ) getService( "paymentHeaderService" );
         final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
         paymentHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         String monthly = request.getParameter( "monthly" );
         if ( KANUtil.filterEmpty( monthly ) != null && !StringUtils.isNumeric( monthly ) )
         {
            monthly = "";
         }
         if ( KANUtil.filterEmpty( monthly ) != null && StringUtils.isNumeric( KANUtil.filterEmpty( monthly ) ) )
         {
            paymentHeaderVO.setMonthly( monthly );
         }
         List< Object > paymentHeaderVOs = paymentHeaderService.getMonthliesByPaymentHeaderVO( paymentHeaderVO );
         request.setAttribute( "paymentHeaderVOs", paymentHeaderVOs );
         return mapping.findForward( "paymentMonthlyList" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward to_batchDetail_mobile( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ��ʼ��Service�ӿ�
      final PayslipDetailViewService payslipViewService = ( PayslipDetailViewService ) getService( "payslipDetailViewService" );
      final EmployeeService employeeService = ( EmployeeService ) getService( "employeeService" );
      final String employeeId = getEmployeeId( request, response );
      final EmployeeVO employeeVO = employeeService.getEmployeeVOByEmployeeId( employeeId );

      final String monthly = request.getParameter( "monthly" );
      // ��ʼ��PagedListHolder
      PagedListHolder payslipHolder = new PagedListHolder();

      final PayslipDetailView payslipDetailView = new PayslipDetailView();
      payslipDetailView.setMonthly( monthly );
      payslipDetailView.setStatus( "3" );
      payslipDetailView.setAccountId( getAccountId( request, response ) );
      payslipDetailView.setEmployeeId( employeeId );
      payslipDetailView.setClientId( getClientId( request, response ) );
      payslipDetailView.setCorpId( getCorpId( request, response ) );
      payslipDetailView.setMonthlyBegin( monthly );
      payslipDetailView.setMonthlyEnd( monthly );

      payslipHolder.setObject( payslipDetailView );

      payslipViewService.getPayslipDTOsByCondition( payslipHolder, false );
      for ( Object obj : payslipHolder.getSource() )
      {
         ( ( PayslipDTO ) obj ).getPayslipHeaderView().setAccountId( getAccountId( request, response ) );
      }
      request.setAttribute( "payslipHolder", payslipHolder );
      request.setAttribute( "employeeVO", employeeVO );
      request.setAttribute( "monthly", monthly );

      return mapping.findForward( "paymentDetailMobile" );
   }

   public ActionForward list_object_nativeAPP( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         final PaymentHeaderService paymentHeaderService = ( PaymentHeaderService ) getService( "paymentHeaderService" );
         final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
         paymentHeaderVO.setEmployeeId( getEmployeeId( request, response ) );
         String monthly = request.getParameter( "monthly" );
         if ( KANUtil.filterEmpty( monthly ) != null )
         {
            paymentHeaderVO.setMonthly( monthly );
         }
         final List< Object > paymentHeaderVOs = paymentHeaderService.getMonthliesByPaymentHeaderVO( paymentHeaderVO );
         final JSONArray jsonArray = new JSONArray();
         for ( Object obj : paymentHeaderVOs )
         {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put( "monthly", ( ( PaymentHeaderVO ) obj ).getMonthly() );
            jsonObject.put( "afterTaxSalary", ( ( PaymentHeaderVO ) obj ).getAfterTaxSalary() );
            jsonArray.add( jsonObject );
         }

         out.print( jsonArray.toString() );
         out.flush();
         out.close();
         return null;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward to_batchDetail_nativeAPP( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         // ��ʼ��Service�ӿ�
         final PaymentHeaderService paymentHeaderService = ( PaymentHeaderService ) getService( "paymentHeaderService" );
         final String employeeId = getEmployeeId( request, response );

         final String monthly = request.getParameter( "monthly" );
         /**
          * PaymentHeaderDTO����
          */
         // ��ʼ��PagedListHolder
         PagedListHolder paymentHeaderDTOHolder = new PagedListHolder();

         // ��ʼ��PaymentHeaderVO
         final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
         paymentHeaderVO.setMonthly( monthly );
         paymentHeaderVO.setStatus( "3" );
         paymentHeaderVO.setAccountId( getAccountId( request, response ) );
         paymentHeaderVO.setEmployeeId( employeeId );
         paymentHeaderVO.setClientId( getClientId( request, response ) );
         paymentHeaderVO.setCorpId( getCorpId( request, response ) );

         paymentHeaderDTOHolder.setObject( paymentHeaderVO );

         paymentHeaderService.getPaymentHeaderDTOsByCondition( paymentHeaderDTOHolder, false );

         if ( paymentHeaderDTOHolder != null && paymentHeaderDTOHolder.getSource() != null && paymentHeaderDTOHolder.getSource().size() > 0 )
         {
            final List< Object > paymentHeaderDTOObject = paymentHeaderDTOHolder.getSource();
            final List< MappingVO > items = ( List< MappingVO > ) paymentHeaderDTOHolder.getAdditionalObject();
            final JSONObject jsonObject = new JSONObject();
            final JSONArray itemArray = new JSONArray();
            final JSONArray amountPersonalArray = new JSONArray();
            for ( MappingVO item : items )
            {
               Double amountSum = 0d;
               for ( Object dtoObject : paymentHeaderDTOObject )
               {
                  final PaymentDTO paymentDTO = ( PaymentDTO ) dtoObject;
                  final List< PaymentDetailVO > paymentDetailVOs = paymentDTO.getPaymentDetailVOs();
                  for ( PaymentDetailVO detailVO : paymentDetailVOs )
                  {
                     // �������ͬ��Ŀ
                     if ( item.getMappingId().equals( detailVO.getItemId() ) )
                     {
                        amountSum += Double.parseDouble( detailVO.getAmountPersonal() );
                     }
                  }
               }
               itemArray.add( item.getMappingValue() );
               amountPersonalArray.add( KANUtil.formatNumber( String.valueOf( amountSum ), getAccountId( request, null ) ) );
            }
            // Ӧ��
            Double addtionalBillAmountPersonal = 0d;
            // ��˰
            Double taxAmountPersonal = 0d;
            // ʵ��
            Double afterTaxSalary = 0d;
            for ( Object obj : paymentHeaderDTOObject )
            {
               final PaymentDTO paymentDTO = ( PaymentDTO ) obj;
               final PaymentHeaderVO headerVO = paymentDTO.getPaymentHeaderVO();
               addtionalBillAmountPersonal += Double.parseDouble( headerVO.getAddtionalBillAmountPersonal() );
               taxAmountPersonal += Double.parseDouble( headerVO.getTaxAmountPersonal() );
               afterTaxSalary += Double.parseDouble( headerVO.getAfterTaxSalary() );
            }
            itemArray.add( "Ӧ������" );
            itemArray.add( "��˰" );
            itemArray.add( "ʵ������" );
            amountPersonalArray.add( KANUtil.formatNumber( String.valueOf( addtionalBillAmountPersonal ), getAccountId( request, null ) ) );
            amountPersonalArray.add( KANUtil.formatNumber( String.valueOf( taxAmountPersonal ), getAccountId( request, null ) ) );
            amountPersonalArray.add( KANUtil.formatNumber( String.valueOf( afterTaxSalary ), getAccountId( request, null ) ) );
            jsonObject.put( "itemArray", itemArray );
            jsonObject.put( "amountPersonalArray", amountPersonalArray );
            out.print( jsonObject.toString() );
            out.flush();
            out.close();
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }
}

package com.kan.hro.web.actions.biz.payment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentAdjustmentHeaderVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.payment.PaymentAdjustmentHeaderService;
import com.kan.hro.service.inf.biz.payment.PaymentHeaderService;
import com.kan.hro.service.inf.biz.vendor.VendorService;

public class PaymentAdjustmentHeaderAction extends BaseAction
{

   public static final String accessAction = "HRO_PAYMENT_ADJUSTMENT";

   /**  
    * List_object
    *	 ��ʾ����״̬�Ĺ��ʵ����б�
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
         // ��õ�ǰҳ
         final String page = getPage( request );

         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentHeaderService paymentBatchService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // ���Action Form
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;

         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, paymentAdjustmentHeaderVO );
         setDataAuth( request, response, paymentAdjustmentHeaderVO );

         paymentAdjustmentHeaderVO.setPageFlag( PaymentAdjustmentHeaderService.PAGE_FLAG_HEADER );
         paymentAdjustmentHeaderVO.setOrderId( KANUtil.filterEmpty( paymentAdjustmentHeaderVO.getOrderId(), "0" ) );

         if ( paymentAdjustmentHeaderVO.getSubAction() != null )
         {
            // ����ɾ������
            if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
            {
               delete_objectList( mapping, form, request, response );
            }
            else if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( SUBMIT_OBJECTS ) || paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( ISSUE_OBJECTS ) )
            {
               modify_objectListStatus( mapping, form, request, response );
            }
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( paymentAdjustmentHeaderVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder paymentAdjustmentHeaderHolder = new PagedListHolder();

         // ���뵱ǰҳ
         paymentAdjustmentHeaderHolder.setPage( page );

         // ���û��ָ��������Ĭ�ϰ� AdjustmentHeaderId����
         if ( paymentAdjustmentHeaderVO.getSortColumn() == null || paymentAdjustmentHeaderVO.getSortColumn().isEmpty() )
         {
            paymentAdjustmentHeaderVO.setSortColumn( "adjustmentHeaderId" );
            paymentAdjustmentHeaderVO.setSortOrder( "desc" );
         }

         // ���In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            paymentAdjustmentHeaderVO.setCorpId( getCorpId( request, response ) );
            paymentAdjustmentHeaderVO.setClientId( getClientId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }

         // ���뵱ǰֵ����
         paymentAdjustmentHeaderHolder.setObject( paymentAdjustmentHeaderVO );
         // ����ҳ���¼����
         paymentAdjustmentHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         paymentBatchService.getPaymentAdjustmentHeaderVOsByCondition( paymentAdjustmentHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( paymentAdjustmentHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "paymentAdjustmentHeaderHolder", paymentAdjustmentHeaderHolder );
         // д��pageFlag
         request.setAttribute( "pageFlag", PaymentAdjustmentHeaderService.PAGE_FLAG_HEADER );

         /**
          * ҳ��ת����
          */
         // �����Ajax����
         if ( new Boolean( request.getParameter( "ajax" ) ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPaymentAdjustmentHeaderTable" );
         }

         return mapping.findForward( "listPaymentAdjustmentHeader" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * List Object Confirm
    *	��ʾ״̬Ϊ����˵Ĺ��ʵ����б�
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_object_confirm( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );
         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentHeaderService paymentBatchService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // ����PageFlag
         ( ( PaymentAdjustmentHeaderVO ) form ).setPageFlag( PaymentAdjustmentHeaderService.PAGE_FLAG_HEADER );

         // ���Action Form
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;
         paymentAdjustmentHeaderVO.setOrderId( KANUtil.filterEmpty( paymentAdjustmentHeaderVO.getOrderId(), "0" ) );
         // ����Statusֵ��ֻ��Ϊ������ˡ�״̬��
         paymentAdjustmentHeaderVO.setStatus( "2" );

         // ���õ�ǰ�û�AccountId
         paymentAdjustmentHeaderVO.setAccountId( getAccountId( request, response ) );

         decodedObject( paymentAdjustmentHeaderVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder paymentAdjustmentHeaderHolder = new PagedListHolder();

         //String accessAction = "HRO_PAYMENT_ADJUSTMENT_CONFIRM";
         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, paymentAdjustmentHeaderVO );
         setDataAuth( request, response, paymentAdjustmentHeaderVO );

         // ���뵱ǰҳ
         paymentAdjustmentHeaderHolder.setPage( page );

         // ���û��ָ��������Ĭ�ϰ� AdjustmentHeaderId����
         if ( paymentAdjustmentHeaderVO.getSortColumn() == null || paymentAdjustmentHeaderVO.getSortColumn().isEmpty() )
         {
            paymentAdjustmentHeaderVO.setSortColumn( "adjustmentHeaderId" );
            paymentAdjustmentHeaderVO.setSortOrder( "desc" );
         }

         // �����In House��¼����Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            paymentAdjustmentHeaderVO.setClientId( getClientId( request, response ) );
            paymentAdjustmentHeaderVO.setCorpId( getCorpId( request, response ) );
            // ���������б�
            passClientOrders( request, response );
         }

         // ���뵱ǰֵ����
         paymentAdjustmentHeaderHolder.setObject( paymentAdjustmentHeaderVO );
         // ����ҳ���¼����
         paymentAdjustmentHeaderHolder.setPageSize( listPageSize_medium );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         paymentBatchService.getPaymentAdjustmentHeaderVOsByCondition( paymentAdjustmentHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( paymentAdjustmentHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "paymentAdjustmentHeaderHolder", paymentAdjustmentHeaderHolder );
         // д��pageFlag
         request.setAttribute( "pageFlag", PaymentAdjustmentHeaderService.PAGE_FLAG_HEADER );

         /**
          * ҳ��ת����
          */
         // �����Ajax����
         if ( new Boolean( request.getParameter( "ajax" ) ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPaymentAdjustmentHeaderConfirmTable" );
         }

         return mapping.findForward( "listPaymentAdjustmentHeaderConfirm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * To ObjectNew
    *	 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ��ʼ��PaymentAdjustmentHeaderVO
      final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;

      // ����Sub Action
      paymentAdjustmentHeaderVO.setSubAction( CREATE_OBJECT );
      // ����Ĭ��ֵ
      paymentAdjustmentHeaderVO.setStatus( "1" );

      // ����Ǵӿͻ��˵�¼
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         // ���ÿͻ�ID
         paymentAdjustmentHeaderVO.setClientId( getClientId( request, null ) );
         paymentAdjustmentHeaderVO.setCorpId( getCorpId( request, null ) );

         // ��ʼ�����׼���
         final List< MappingVO > clientOrderHeaderMappingVOs = new ArrayList< MappingVO >();

         // ��ʼ��Service�ӿ�
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         // ��ʼ��ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
         clientOrderHeaderVO.setCorpId( getCorpId( request, null ) );
         clientOrderHeaderVO.setClientId( getClientId( request, null ) );
         clientOrderHeaderVO.setAccountId( getAccountId( request, response ) );
         clientOrderHeaderVO.setStatus( "3, 5" );

         // ��õ�¼�ͻ���Ӧ������������Ϣ
         final List< Object > clientOrderHeaderVOs = clientOrderHeaderService.getClientOrderHeaderVOsByCondition( clientOrderHeaderVO );

         // �������� MappingVO����
         if ( clientOrderHeaderVOs != null && clientOrderHeaderVOs.size() > 0 )
         {
            for ( Object clientOrderHeaderVOObject : clientOrderHeaderVOs )
            {
               final MappingVO mappingVO = new MappingVO();
               final ClientOrderHeaderVO clientOrderHeaderVOTemp = ( ClientOrderHeaderVO ) clientOrderHeaderVOObject;
               mappingVO.setMappingId( clientOrderHeaderVOTemp.getOrderHeaderId() );
               mappingVO.setMappingValue( clientOrderHeaderVOTemp.getOrderHeaderId() );

               if ( this.getLocale( request ).getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  if ( KANUtil.filterEmpty( clientOrderHeaderVOTemp.getNameZH() ) != null )
                  {
                     mappingVO.setMappingValue( mappingVO.getMappingValue() + " - " + clientOrderHeaderVOTemp.getNameZH() );
                  }
               }
               else
               {
                  if ( KANUtil.filterEmpty( clientOrderHeaderVOTemp.getNameEN() ) != null )
                  {
                     mappingVO.setMappingValue( mappingVO.getMappingValue() + " - " + clientOrderHeaderVOTemp.getNameEN() );
                  }
               }

               clientOrderHeaderMappingVOs.add( mappingVO );
            }
         }

         clientOrderHeaderMappingVOs.add( 0, KANUtil.getEmptyMappingVO( getLocale( request ) ) );
         request.setAttribute( "clientOrderHeaderMappingVOs", clientOrderHeaderMappingVOs );
      }

      return mapping.findForward( "listPaymentAdjustmentDetail" );
   }

   /**  
    * Add Object
    *	 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2014-04-26
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��PaymentAdjustmentDetailVO
         final PaymentAdjustmentDetailVO paymentAdjustmentDetailVO = new PaymentAdjustmentDetailVO();

         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service
            final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );
            final PaymentHeaderService paymentHeaderService = ( PaymentHeaderService ) getService( "paymentHeaderService" );
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
            final VendorService vendorService = ( VendorService ) getService( "vendorService" );

            // ��ʼ��PaymentAdjustmentHeaderVO
            final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;

            // �������ʽ�������
            final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
            paymentHeaderVO.setAccountId( paymentAdjustmentHeaderVO.getAccountId() );
            paymentHeaderVO.setCorpId( paymentAdjustmentHeaderVO.getCorpId() );
            paymentHeaderVO.setEmployeeId( paymentAdjustmentHeaderVO.getEmployeeId() );
            paymentHeaderVO.setMonthly( paymentAdjustmentHeaderVO.getMonthly() );
            // �½�״̬����
            paymentHeaderVO.setStatus( "1" );
            final List< Object > paymentHeaderVOs = paymentHeaderService.getPaymentHeaderVOsByCondition( paymentHeaderVO );

            if ( paymentHeaderVOs == null || paymentHeaderVOs.size() == 0 )
            {
               // ��ȡEmployeeContractVO
               if ( KANUtil.filterEmpty( paymentAdjustmentHeaderVO.getContractId() ) != null )
               {
                  // ��ʼ��EmployeeContractVO
                  final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( paymentAdjustmentHeaderVO.getContractId() );

                  // ��ʼ��SalaryVendorId
                  String salaryVendorId = employeeContractVO.getSalaryVendorId();

                  if ( KANUtil.filterEmpty( salaryVendorId, "0" ) == null )
                  {
                     salaryVendorId = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( employeeContractVO.getOrderId() ).getSalaryVendorId();
                  }

                  // ��ʼ��VendorVO
                  VendorVO vendorVO = null;

                  if ( KANUtil.filterEmpty( salaryVendorId, "0" ) != null )
                  {
                     vendorVO = vendorService.getVendorVOByVendorId( salaryVendorId );
                  }

                  paymentAdjustmentHeaderVO.setOrderId( employeeContractVO.getOrderId() );
                  paymentAdjustmentHeaderVO.setBusinessTypeId( employeeContractVO.getBusinessTypeId() );
                  paymentAdjustmentHeaderVO.setEntityId( employeeContractVO.getEntityId() );
                  paymentAdjustmentHeaderVO.setBranch( employeeContractVO.getBranch() );
                  paymentAdjustmentHeaderVO.setOwner( employeeContractVO.getOwner() );
                  paymentAdjustmentHeaderVO.setVendorId( vendorVO != null ? vendorVO.getVendorId() : "0" );
                  paymentAdjustmentHeaderVO.setVendorNameZH( vendorVO != null ? vendorVO.getNameZH() : "" );
                  paymentAdjustmentHeaderVO.setVendorNameEN( vendorVO != null ? vendorVO.getNameEN() : "" );
               }

               paymentAdjustmentHeaderVO.setAccountId( getAccountId( request, response ) );
               paymentAdjustmentHeaderVO.setCreateBy( getUserId( request, response ) );
               paymentAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
               paymentAdjustmentHeaderService.insertPaymentAdjustmentHeader( paymentAdjustmentHeaderVO );

               paymentAdjustmentDetailVO.setAdjustmentHeaderId( paymentAdjustmentHeaderVO.getAdjustmentHeaderId() );

               // ������ӳɹ����
               success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
               insertlog( request, paymentAdjustmentHeaderVO, Operate.ADD, paymentAdjustmentHeaderVO.getAdjustmentHeaderId(), null );

               ( ( PaymentAdjustmentHeaderVO ) form ).setAccountId( getAccountId( request, response ) );

               // ���Form����
               ( ( PaymentAdjustmentHeaderVO ) form ).reset();

               return new PaymentAdjustmentDetailAction().list_object( mapping, paymentAdjustmentDetailVO, request, response );
            }
            else
            {
               // ���ؾ�����
               warning( request, null, "���ʵ���δ�����������ύ" + paymentAdjustmentHeaderVO.getEmployeeNameZH() + "�Ĺ��ʽ������ݣ�", MESSAGE_HEADER );
            }
         }
         else
         {
            // ����ʧ�ܱ��
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ), MESSAGE_HEADER );
         }

         // ���Form����
         ( ( PaymentAdjustmentHeaderVO ) form ).reset();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }

      return to_objectNew( mapping, form, request, response );
   }

   /**  
    * To ObjectModify
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
    * Modify Object
    *	 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2014-01-03
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ���ӿ�
            final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );
            final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

            // ��õ�ǰ����
            String adjustmentHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "adjustmentHeaderId" ) );

            // ��ʼ��PaymentAdjustmentHeaderVO
            final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );
            paymentAdjustmentHeaderVO.update( ( PaymentAdjustmentHeaderVO ) form );
            paymentAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );

            // ��ȡEmployeeContractVO
            if ( KANUtil.filterEmpty( paymentAdjustmentHeaderVO.getContractId() ) != null )
            {
               final EmployeeContractVO employeeContractVO = employeeContractService.getEmployeeContractVOByContractId( paymentAdjustmentHeaderVO.getContractId() );

               paymentAdjustmentHeaderVO.setOrderId( employeeContractVO.getOrderId() );
               paymentAdjustmentHeaderVO.setBusinessTypeId( employeeContractVO.getBusinessTypeId() );
               paymentAdjustmentHeaderVO.setEntityId( employeeContractVO.getEntityId() );
               paymentAdjustmentHeaderVO.setBranch( employeeContractVO.getBranch() );
               paymentAdjustmentHeaderVO.setOwner( employeeContractVO.getOwner() );
            }

            // �޸Ķ���
            paymentAdjustmentHeaderService.updatePaymentAdjustmentHeader( paymentAdjustmentHeaderVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, paymentAdjustmentHeaderVO, Operate.MODIFY, paymentAdjustmentHeaderVO.getAdjustmentHeaderId(), null );
         }

         return new PaymentAdjustmentDetailAction().list_object( mapping, new PaymentAdjustmentDetailVO(), request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Submit Object
    *	�ύ���޸ĵ���״̬Ϊ�����
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward submit_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // �������ID
         final String adjustmentHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "adjustmentHeaderId" ) );

         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );

         // ��������ֵ
         paymentAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         paymentAdjustmentHeaderVO.setModifyDate( new Date() );
         paymentAdjustmentHeaderVO.setStatus( "2" );
         paymentAdjustmentHeaderService.updatePaymentAdjustmentHeader( paymentAdjustmentHeaderVO );

         // �����ύ�ɹ����
         success( request, MESSAGE_TYPE_SUBMIT, null, MESSAGE_HEADER );

         insertlog( request, paymentAdjustmentHeaderVO, Operate.SUBMIT, adjustmentHeaderId, null );

         // ���Selected IDs����Action
         ( ( PaymentAdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).reset();
         ( ( PaymentAdjustmentHeaderVO ) form ).setAdjustmentHeaderId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**  
    * Approve Object
    *	�޸ĵ���״̬Ϊ��׼'
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward approve_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // ���Action Form
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;

         // ����ѡ�е�ID
         if ( paymentAdjustmentHeaderVO.getSelectedIds() != null && KANUtil.filterEmpty( paymentAdjustmentHeaderVO.getSelectedIds() ) != null )
         {
            // �ָ�
            for ( String selectedId : paymentAdjustmentHeaderVO.getSelectedIds().split( "," ) )
            {
               // ��ʼ��PaymentAdjustmentHeaderVO
               final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVOTemp = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );

               // ���ýӿ�
               paymentAdjustmentHeaderVOTemp.setModifyBy( getUserId( request, response ) );
               paymentAdjustmentHeaderVOTemp.setModifyDate( new Date() );
               paymentAdjustmentHeaderVOTemp.setStatus( "3" );
               paymentAdjustmentHeaderService.updatePaymentAdjustmentHeader( paymentAdjustmentHeaderVOTemp );
            }

            insertlog( request, paymentAdjustmentHeaderVO, Operate.SUBMIT, null, "approve_object:" + KANUtil.decodeSelectedIds( paymentAdjustmentHeaderVO.getSelectedIds() ) );
         }

         // ���ء���׼���ɹ����
         success( request, null, "ȷ�ϳɹ�", MESSAGE_HEADER );

         // ���Selected IDs����Action
         ( ( PaymentAdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object_confirm( mapping, form, request, response );
   }

   /**  
    * Rollback Object
    *	�޸ĵ���״̬Ϊ�˻�
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward rollback_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // ���Action Form
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;

         // ����ѡ�е�ID
         if ( paymentAdjustmentHeaderVO.getSelectedIds() != null && !paymentAdjustmentHeaderVO.getSelectedIds().trim().isEmpty() )
         {
            // �ָ�
            for ( String selectedId : paymentAdjustmentHeaderVO.getSelectedIds().split( "," ) )
            {
               // ��ʼ��PaymentAdjustmentHeaderVO
               final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVOTemp = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );

               // ���ýӿ�
               paymentAdjustmentHeaderVOTemp.setModifyBy( getUserId( request, response ) );
               paymentAdjustmentHeaderVOTemp.setModifyDate( new Date() );
               paymentAdjustmentHeaderVOTemp.setStatus( "4" );
               paymentAdjustmentHeaderService.updatePaymentAdjustmentHeader( paymentAdjustmentHeaderVOTemp );
            }

            insertlog( request, paymentAdjustmentHeaderVO, Operate.ROllBACK, null, "rollback_object:" + KANUtil.decodeSelectedIds( paymentAdjustmentHeaderVO.getSelectedIds() ) );
         }

         // ���Selected IDs����Action
         ( ( PaymentAdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).reset();

         // ���ء��˻ء��ɹ����
         success( request, null, "�˻سɹ�", MESSAGE_HEADER );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object_confirm( mapping, form, request, response );
   }

   /**  
    * Issue Object
    *	�޸ĵ���״̬Ϊ����
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward issue_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );

         // �������ID
         final String adjustmentHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "adjustmentHeaderId" ) );

         // ��ʼ��PaymentAdjustmentHeaderVO
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );

         // ��������ֵ
         paymentAdjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         paymentAdjustmentHeaderVO.setModifyDate( new Date() );
         paymentAdjustmentHeaderVO.setStatus( "5" );
         paymentAdjustmentHeaderService.updatePaymentAdjustmentHeader( paymentAdjustmentHeaderVO );

         // ���Selected IDs����Action
         ( ( PaymentAdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).reset();

         // ���ء����š��ɹ����
         success( request, null, "���ųɹ�", MESSAGE_HEADER );

         insertlog( request, paymentAdjustmentHeaderVO, Operate.SUBMIT, null, "issue_object" + paymentAdjustmentHeaderVO.getSelectedIds() );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**  
    * Delete Object
    *	 ���ɾ��н��
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@throws KANException
    */
   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
   }

   /**  
    * Delete ObjectList
    *	 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@throws KANException
    */
   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );
         // ���Action Form
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;
         // ��ʼ��ɾ����¼��
         int deleteCounts = 0;

         // ����ѡ�е�ID
         if ( paymentAdjustmentHeaderVO.getSelectedIds() != null && !paymentAdjustmentHeaderVO.getSelectedIds().trim().isEmpty() )
         {
            // �ָ�
            for ( String selectedId : paymentAdjustmentHeaderVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ��ʼ��ClientVO
                  final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVOTemp = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );

                  // ״̬Ϊ���½��������˻ء�״̬�ſ�ɾ��
                  if ( "1".equals( paymentAdjustmentHeaderVOTemp.getStatus() ) || "4".equals( paymentAdjustmentHeaderVOTemp.getStatus() ) )
                  {
                     deleteCounts++;
                     // ���ýӿ�
                     paymentAdjustmentHeaderVOTemp.setModifyBy( getUserId( request, response ) );
                     paymentAdjustmentHeaderVOTemp.setModifyDate( new Date() );
                     paymentAdjustmentHeaderService.deletePaymentAdjustmentHeader( paymentAdjustmentHeaderVOTemp );
                  }
               }
            }
         }

         // ���Selected IDs����Action
         ( ( PaymentAdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).reset();
         ( ( PaymentAdjustmentHeaderVO ) form ).setAdjustmentHeaderId( "" );

         if ( deleteCounts > 0 )
         {
            // ���ء�ɾ�����ɹ����
            success( request, null, "�ɹ�ɾ�� " + deleteCounts + " ����¼", MESSAGE_HEADER );
            insertlog( request, paymentAdjustmentHeaderVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( paymentAdjustmentHeaderVO.getSelectedIds() ) );
         }
         else
         {
            // ���ء�ɾ�����ɹ����
            warning( request, null, "�����ڷ����޸�������¼��ֻ�С��½������ߡ��˻ء����ݲ��ܱ�ɾ������", MESSAGE_HEADER );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Modify ObjectListStatus
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@throws KANException
    */
   private void modify_objectListStatus( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PaymentAdjustmentHeaderService paymentAdjustmentHeaderService = ( PaymentAdjustmentHeaderService ) getService( "paymentAdjustmentHeaderService" );
         // ���Action Form
         final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVO = ( PaymentAdjustmentHeaderVO ) form;
         // ��ʼ�����������������������Ӱ��Ĺ�Ա����
         int countHeaderId = 0;
         List< EmployeeVO > employees = new ArrayList< EmployeeVO >();

         // ����ѡ�е�ID
         if ( paymentAdjustmentHeaderVO.getSelectedIds() != null && !paymentAdjustmentHeaderVO.getSelectedIds().trim().isEmpty() )
         {
            // �ָ�
            for ( String selectedId : paymentAdjustmentHeaderVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // ��ʼ��ClientVO
                  final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVOTemp = paymentAdjustmentHeaderService.getPaymentAdjustmentHeaderVOByAdjustmentHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );

                  // ���ýӿ�
                  paymentAdjustmentHeaderVOTemp.setModifyBy( getUserId( request, response ) );
                  paymentAdjustmentHeaderVOTemp.setModifyDate( new Date() );

                  // ���ύ����������
                  if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( SUBMIT_OBJECTS ) )
                  {
                     // �½� �� �˻� ״̬���� �ύ
                     if ( paymentAdjustmentHeaderVOTemp.getStatus().equals( "1" ) || paymentAdjustmentHeaderVOTemp.getStatus().equals( "4" ) )
                     {
                        countHeaderId++;
                        paymentAdjustmentHeaderVOTemp.setStatus( "2" );
                        paymentAdjustmentHeaderService.updatePaymentAdjustmentHeader( paymentAdjustmentHeaderVOTemp );
                        fectchEmployeeList( paymentAdjustmentHeaderVOTemp, employees );
                     }
                  }
                  // �����š���������
                  else if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( ISSUE_OBJECTS ) )
                  {
                     // ��׼״̬���Է���
                     if ( paymentAdjustmentHeaderVOTemp.getStatus().equals( "3" ) )
                     {
                        countHeaderId++;
                        paymentAdjustmentHeaderVOTemp.setStatus( "5" );
                        paymentAdjustmentHeaderService.updatePaymentAdjustmentHeader( paymentAdjustmentHeaderVOTemp );
                        fectchEmployeeList( paymentAdjustmentHeaderVOTemp, employees );
                     }
                  }
               }
            }
         }

         // ���ر��
         if ( countHeaderId == 0 )
         {
            warning( request, null, "�����ڷ����޸�������¼������ʧ�ܣ�", MESSAGE_HEADER );
         }
         else
         {
            // ��ʼ����Ա��������
            String employeeNamesString = "";

            // ��Ա���ϰ���������
            if ( employees != null && employees.size() > 1 )
            {
               sortEmployees( employees, request );
            }

            // ���Ӱ���Ա����������3��
            if ( employees.size() < 3 )
            {
               // ������ύ
               if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( SUBMIT_OBJECTS ) )
               {
                  employeeNamesString = getEmployeeNameArray( employees );
                  success( request, null, employeeNamesString.toString() + " ���ϼƣ�" + employees.size() + " �ˣ���" + countHeaderId + "�����������ύ�ɹ���", MESSAGE_HEADER );
               }
               // ����Ƿ���
               else if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( ISSUE_OBJECTS ) )
               {
                  employeeNamesString = getEmployeeNameArray( employees );
                  success( request, null, employeeNamesString.toString() + " ���ϼƣ�" + employees.size() + " �ˣ���" + countHeaderId + "���������ݷ��ųɹ���", MESSAGE_HEADER );
               }
            }
            else
            {
               // ������ύ
               if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( SUBMIT_OBJECTS ) )
               {
                  // ���ǰ����Ա���ļ���
                  final List< EmployeeVO > top3Employees = getTop3EmployeeVOs( employees );
                  employeeNamesString = getEmployeeNameArray( top3Employees );
                  success( request, null, String.valueOf( countHeaderId ) + employeeNamesString.toString() + " ��...���ˣ��ϼƣ�" + employees.size() + " �ˣ���" + countHeaderId
                        + "�����������ύ�ɹ���", MESSAGE_HEADER );
               }
               // ����Ƿ���
               else if ( paymentAdjustmentHeaderVO.getSubAction().equalsIgnoreCase( ISSUE_OBJECTS ) )
               {
                  // ���ǰ����Ա���ļ���
                  final List< EmployeeVO > top3Employees = getTop3EmployeeVOs( employees );
                  employeeNamesString = getEmployeeNameArray( top3Employees );
                  success( request, null, String.valueOf( countHeaderId ) + employeeNamesString.toString() + " ��...���ˣ��ϼƣ�" + employees.size() + " �ˣ���" + countHeaderId
                        + "�����������޸ĳɹ���", MESSAGE_HEADER );
               }
            }
         }

         // ���Selected IDs����Action
         ( ( PaymentAdjustmentHeaderVO ) form ).reset();
         ( ( PaymentAdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( PaymentAdjustmentHeaderVO ) form ).setAdjustmentHeaderId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * GetTop3EmployeeVOs
    *	���ǰ����Ա���ļ���
    *	@param employees
    *	@return
    */
   private List< EmployeeVO > getTop3EmployeeVOs( final List< EmployeeVO > employees )
   {
      List< EmployeeVO > top3Employees = new ArrayList< EmployeeVO >();

      for ( int i = 0; i < 3; i++ )
      {
         top3Employees.add( employees.get( i ) );
      }

      return top3Employees;
   }

   /**  
    * GetEmployeeNameArray
    *	���Ա����������
    *	@param employees
    *	@return
    */
   private String getEmployeeNameArray( final List< EmployeeVO > employees )
   {
      // ��ʼ��Ա����������
      StringBuilder employeeNames = new StringBuilder();

      for ( int i = 0; i < employees.size(); i++ )
      {
         if ( i < ( employees.size() - 1 ) )
         {
            // �������Ӣ����
            if ( KANUtil.filterEmpty( employees.get( i ).getNameEN() ) != null )
            {

               employeeNames.append( employees.get( i ).getNameZH() + "(" + employees.get( i ).getNameEN() + ")�� " );
            }
            else
            {
               employeeNames.append( employees.get( i ).getNameZH() + "�� " );
            }
         }
         else
         {
            // �������Ӣ����
            if ( KANUtil.filterEmpty( employees.get( i ).getNameEN() ) != null )
            {

               employeeNames.append( employees.get( i ).getNameZH() + "(" + employees.get( i ).getNameEN() + ") " );
            }
            else
            {
               employeeNames.append( employees.get( i ).getNameZH() );
            }
         }
      }

      return employeeNames.toString();
   }

   /**  
    * SortEmployees
    *	��Ա���ϰ���������
    *	@param employees
    *	@param request
    */
   private void sortEmployees( final List< EmployeeVO > employees, final HttpServletRequest request )
   {
      Collections.sort( employees, new Comparator< EmployeeVO >()
      {
         @Override
         public int compare( EmployeeVO o1, EmployeeVO o2 )
         {
            // ������������
            if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
            {
               return o1.getNameZH().compareTo( o2.getNameZH() );
            }
            // Ӣ������
            else
            {
               return o1.getNameEN().compareTo( o2.getNameEN() );
            }
         }
      } );
   }

   /**  
    * FectchEmployeeList
    *	����Ա������
    *	@param paymentAdjustmentHeaderVOTemp
    *	@param employees
    */
   private void fectchEmployeeList( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVOTemp, final List< EmployeeVO > employees )
   {
      if ( !isEmployeeExist( paymentAdjustmentHeaderVOTemp, employees ) )
      {
         final EmployeeVO tempEmployeeVO = new EmployeeVO();
         tempEmployeeVO.setEmployeeId( paymentAdjustmentHeaderVOTemp.getEmployeeId() );
         tempEmployeeVO.setNameEN( paymentAdjustmentHeaderVOTemp.getEmployeeNameEN() );
         tempEmployeeVO.setNameZH( paymentAdjustmentHeaderVOTemp.getEmployeeNameZH() );
         employees.add( tempEmployeeVO );
      }
   }

   private boolean isEmployeeExist( final PaymentAdjustmentHeaderVO paymentAdjustmentHeaderVOTemp, final List< EmployeeVO > employees )
   {
      if ( employees != null && employees.size() > 0 )
      {
         for ( EmployeeVO employeeVO : employees )
         {
            if ( employeeVO.getEmployeeId().equals( paymentAdjustmentHeaderVOTemp.getEmployeeId() ) )
            {
               return true;
            }
         }
         return false;
      }
      return false;
   }
}

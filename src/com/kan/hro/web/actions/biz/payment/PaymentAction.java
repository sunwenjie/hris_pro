package com.kan.hro.web.actions.biz.payment;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.payment.PaymentBatchVO;
import com.kan.hro.domain.biz.payment.PaymentDTO;
import com.kan.hro.domain.biz.payment.PaymentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.domain.biz.settlement.BatchTempVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.service.inf.biz.payment.PaymentBatchService;
import com.kan.hro.service.inf.biz.payment.PaymentHeaderService;

public class PaymentAction extends BaseAction
{

   // �����java������
   public static final String javaObjectName = "com.kan.hro.domain.biz.payment.PayslipDTO";

   // Access Action
   public static final String ACCESS_ACTION = "HRO_PAYMENT_BATCH";

   public ActionForward export_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final PaymentBatchService paymentBatchService = ( PaymentBatchService ) getService( "paymentBatchService" );

         // ���Action Form
         final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) form;

         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), ACCESS_ACTION, paymentBatchVO );
         setDataAuth( request, response, paymentBatchVO );

         paymentBatchVO.setOrderId( KANUtil.filterEmpty( paymentBatchVO.getOrderId(), "0" ) );
         // ����Statusֵ������StatusFlag��
         paymentBatchVO.setStatus( getStatusesByStatusFlag( paymentBatchVO.getStatusFlag() ) );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         pagedListHolder.setObject( paymentBatchVO );
         paymentBatchService.getPaymentDTOsByCondition( pagedListHolder );

         // Holder��д��Request����
         request.setAttribute( "pagedListHolder", pagedListHolder );

         return new DownloadFileAction().specialExportList( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * List Object
    *	 ��������б�
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = getPage( request );
         // ����Ƿ�Ajax����
         final String ajax = getAjax( request );
         // ��ʼ��Service�ӿ�
         final PaymentBatchService paymentBatchService = ( PaymentBatchService ) getService( "paymentBatchService" );

         // ��ȡҳ���ǣ���Ԥ���� - preview����̨�� - ��confirm��������ȷ�� �� - submit��
         String statusFlag = request.getParameter( "statusFlag" );
         if ( statusFlag == null )
         {
            statusFlag = PaymentBatchService.STATUS_FLAG_PREVIEW;
         }

         // ����PageFlag��StatusFlag
         ( ( PaymentBatchVO ) form ).setStatusFlag( statusFlag );
         ( ( PaymentBatchVO ) form ).setPageFlag( PaymentBatchService.PAGE_FLAG_BATCH );

         // ���Action Form
         final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) form;

         String accessAction = "HRO_PAYMENT_BATCH";

         if ( statusFlag.equals( PaymentBatchService.STATUS_FLAG_SUBMIT ) )
         {
            accessAction = "HRO_PAYMENT_BATCH_SUBMIT";
         }
         else if ( statusFlag.equals( PaymentBatchService.STATUS_FLAG_ISSUE ) )
         {
            accessAction = "HRO_PAYMENT_BATCH_ISSUE";
         }
         //��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, paymentBatchVO );
         setDataAuth( request, response, paymentBatchVO );

         request.setAttribute( "authAccessAction", accessAction );

         paymentBatchVO.setOrderId( KANUtil.filterEmpty( paymentBatchVO.getOrderId(), "0" ) );
         // ����Statusֵ������StatusFlag��
         paymentBatchVO.setStatus( getStatusesByStatusFlag( paymentBatchVO.getStatusFlag() ) );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder paymentBatchHolder = new PagedListHolder();

         // ���뵱ǰҳ
         paymentBatchHolder.setPage( page );

         // ���û��ָ��������Ĭ�ϰ� BatchId����
         if ( paymentBatchVO.getSortColumn() == null || paymentBatchVO.getSortColumn().isEmpty() )
         {
            paymentBatchVO.setSortColumn( "batchId" );
            paymentBatchVO.setSortOrder( "desc" );
         }

         // ���뵱ǰֵ����
         paymentBatchHolder.setObject( paymentBatchVO );
         // ����ҳ���¼����
         paymentBatchHolder.setPageSize( listPageSize_medium );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         paymentBatchService.getPaymentBatchVOsByCondition( paymentBatchHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( paymentBatchHolder, request );

         // Holder��д��Request����
         request.setAttribute( "paymentBatchHolder", paymentBatchHolder );
         // д��statusFlag
         request.setAttribute( "statusFlag", statusFlag );
         // д��pageFlag
         request.setAttribute( "pageFlag", PaymentBatchService.PAGE_FLAG_BATCH );

         // �����In House��¼��������������
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // ��ʾ������ť
         request.setAttribute( "javaObjectName", javaObjectName );
         showExportButton( mapping, form, request, response );

         // �趨Ȩ��
         setHRFunctionRole( mapping, form, request, response );

         // �����Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listPaymentBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listPaymentBatch" );
   }

   /**
    * �鿴��������
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_batchDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // ��ʼ��Service�ӿ�
      final PaymentBatchService paymentBatchService = ( PaymentBatchService ) getService( "paymentBatchService" );
      // �����������ID
      final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

      // ��ʼ��PaymentBatchVO
      PaymentBatchVO paymentBatchVO = new PaymentBatchVO();
      paymentBatchVO.setBatchId( batchId );
      paymentBatchVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );
      paymentBatchVO.setAccountId( getAccountId( request, response ) );

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
      final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
      paymentHeaderVO.setAccountId( getAccountId( request, response ) );
      paymentHeaderVO.setBatchId( batchId );
      paymentHeaderVO.setStatus( getStatusesByStatusFlag( request.getParameter( "statusFlag" ) ) );

      // ����
      paymentHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
      paymentHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

      // Ĭ������
      if ( request.getParameter( "sortColumn" ) == null || request.getParameter( "sortColumn" ).trim().isEmpty() )
      {
         paymentHeaderVO.setSortColumn( "paymentHeaderId" );
         paymentHeaderVO.setSortOrder( "desc" );
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

      request.setAttribute( "paymentHeaderDTOHolder", paymentHeaderDTOHolder );

      // ����Ƿ�Ajax����
      final String ajax = request.getParameter( "ajax" );

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

      // ���ȫ������״̬���ı��ˣ�����ת����һ��
      if ( paymentHeaderDTOHolder == null || paymentHeaderDTOHolder.getHolderSize() == 0 )
      {
         ( ( PaymentBatchVO ) form ).reset();
         ( ( PaymentBatchVO ) form ).setBatchId( "" );
         request.setAttribute( "paymentBatchForm", ( PaymentBatchVO ) form );
         ( ( PaymentBatchVO ) form ).setSortColumn( null );
         return list_object( mapping, form, request, response );
      }

      return mapping.findForward( "listPaymentHeader" );
   }

   /**  
    * To ObjectNew
    *	 �½�����
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

      // ����Sub Action
      ( ( PaymentBatchVO ) form ).setSubAction( CREATE_OBJECT );

      // �����In House��¼��������������
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         passClientOrders( request, response );
      }

      // ��ת���½�����
      return mapping.findForward( "managePaymentBatch" );
   }

   /**  
    * Add Object
    *	 �������
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2013-12-04
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( true )
         {
            // ��ʼ��Service�ӿ�
            final PaymentBatchService paymentBatchService = ( PaymentBatchService ) getService( "paymentBatchService" );

            // ��ʼ��������
            int rows = 0;

            // ��ȡForm
            final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) form;
            paymentBatchVO.setCreateBy( getUserId( request, response ) );
            paymentBatchVO.setModifyBy( getUserId( request, response ) );
            paymentBatchVO.setOrderId( KANUtil.filterEmpty( paymentBatchVO.getOrderId(), "0" ) );
            // ����ִ�п�ʼʱ��
            paymentBatchVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

            // ��ȡ���ϵĽ�����Ϣ
            final ServiceContractVO serviceContractVO = new ServiceContractVO();
            serviceContractVO.setAccountId( paymentBatchVO.getAccountId() );
            serviceContractVO.setEntityId( paymentBatchVO.getEntityId() );
            serviceContractVO.setBusinessTypeId( paymentBatchVO.getBusinessTypeId() );
            serviceContractVO.setClientId( paymentBatchVO.getClientId() );
            serviceContractVO.setCorpId( paymentBatchVO.getCorpId() );
            serviceContractVO.setOrderId( KANUtil.filterEmpty( paymentBatchVO.getOrderId(), "0" ) );
            serviceContractVO.setEmployeeContractId( paymentBatchVO.getContractId() );
            serviceContractVO.setEmployeeId( paymentBatchVO.getEmployeeId() );
            serviceContractVO.setMonthly( paymentBatchVO.getMonthly() );

            if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
            {
               // ��ʼ��BatchTempVO
               final BatchTempVO batchTempVO = new BatchTempVO();
               batchTempVO.reset( mapping, request );
               batchTempVO.setEntityId( paymentBatchVO.getEntityId() );
               batchTempVO.setBusinessTypeId( paymentBatchVO.getBusinessTypeId() );
               batchTempVO.setOrderId( KANUtil.filterEmpty( paymentBatchVO.getOrderId(), "0" ) );
               batchTempVO.setContractId( paymentBatchVO.getContractId() );
               batchTempVO.setMonthly( paymentBatchVO.getMonthly() );
               batchTempVO.setAccountPeriod( KANUtil.formatDate( new Date(), "yyyy-MM-dd" ) );
               batchTempVO.setContainSalary( BatchTempVO.TRUE );
               batchTempVO.setContainSB( BatchTempVO.TRUE );
               batchTempVO.setContainCB( BatchTempVO.TRUE );
               batchTempVO.setContainOther( BatchTempVO.TRUE );
               batchTempVO.setCreateBy( getUserId( request, response ) );
               batchTempVO.setModifyBy( getUserId( request, response ) );

               // �������㿪ʼʱ������
               batchTempVO.setStartDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );

               // �����Զ���Column
               batchTempVO.setRemark1( saveDefineColumns( request, "" ) );

               // ���ս�������������ȡ���ϵĶ���DTO
               final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
               clientOrderHeaderVO.setAccountId( batchTempVO.getAccountId() );
               clientOrderHeaderVO.setEntityId( batchTempVO.getEntityId() );
               clientOrderHeaderVO.setBusinessTypeId( batchTempVO.getBusinessTypeId() );
               clientOrderHeaderVO.setClientId( batchTempVO.getClientId() );
               clientOrderHeaderVO.setCorpId( batchTempVO.getCorpId() );
               clientOrderHeaderVO.setOrderHeaderId( KANUtil.filterEmpty( paymentBatchVO.getOrderId(), "0" ) );
               clientOrderHeaderVO.setEmployeeContractId( batchTempVO.getContractId() );
               clientOrderHeaderVO.setEmployeeId( paymentBatchVO.getEmployeeId() );
               clientOrderHeaderVO.setMonthly( batchTempVO.getMonthly() );

               //��������Ȩ��
               //String accessAction = "HRO_PAYMENT_BATCH";
               //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, clientOrderHeaderVO );
               setDataAuth( request, response, clientOrderHeaderVO );

               // ����Service�����洢���ݣ���Ҫ����Transaction��
               rows = paymentBatchService.insertPaymentBatchInHouse( paymentBatchVO, batchTempVO, clientOrderHeaderVO, serviceContractVO );
            }
            else
            {
               //��������Ȩ��
               //String accessAction = "HRO_PAYMENT_BATCH";
               //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, serviceContractVO );
               // ����Service�����洢���ݣ���Ҫ����Transaction��
               rows = paymentBatchService.insertPaymentBatch( paymentBatchVO, serviceContractVO );
            }

            if ( rows > 0 )
            {
               // ������ӳɹ����
               success( request, null, "�ɹ��������� " + paymentBatchVO.getBatchId() + " ��" );
               insertlog( request, paymentBatchVO, Operate.ADD, paymentBatchVO.getBatchId(), "���ʽ��㴴��" );
            }
            else
            {
               // ���ؾ�����
               warning( request, null, "����δ������" + ( getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) ? "��Ա" : "Ա��" ) + "�ѱ��������ڱ�δ�ύ������δ�����"
                     + ( getRole( request, null ).equals( KANConstants.ROLE_HR_SERVICE ) ? "����Э��" : "�Ͷ���ͬ" ) + "��Ϣ��������" );
            }
         }
         else
         {
            // ����ʧ�ܱ��
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
         }

         // ���Form����
         ( ( PaymentBatchVO ) form ).reset();
         ( ( PaymentBatchVO ) form ).setBatchId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**  
    * Submit Estimation
    *	н���ύ
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward submit_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡ�� BatchTempVO
         final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) form;
         paymentBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final PaymentBatchService paymentBatchService = ( PaymentBatchService ) getService( "paymentBatchService" );

         // ����Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), ACCESS_ACTION, paymentBatchVO );
         setDataAuth( request, response, paymentBatchVO );

         // �ύ
         final int returnBatchFlag = paymentBatchService.submit( paymentBatchVO );
         insertlog( request, paymentBatchVO, Operate.SUBMIT, paymentBatchVO.getBatchId(), "submit_estimation" );

         // ��������η������޸�
         if ( returnBatchFlag == 0 )
         {
            // ���ò�ѯ����
            ( ( PaymentBatchVO ) form ).setBatchId( null );
            ( ( PaymentBatchVO ) form ).setSortColumn( null );
            paymentBatchVO.setPageFlag( PaymentBatchService.PAGE_FLAG_BATCH );
         }

         // ���Selected IDs����Action
         ( ( PaymentBatchVO ) form ).setSelectedIds( "" );

         // ����pageFlag ��ת
         return forward( paymentBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Issue Estimation
    *	н�ʷ���
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward issue_Actual( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {

         // ��ȡ�� BatchTempVO
         final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) form;
         paymentBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final PaymentBatchService paymentBatchService = ( PaymentBatchService ) getService( "paymentBatchService" );
         // ����Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), ACCESS_ACTION, paymentBatchVO );
         setDataAuth( request, response, paymentBatchVO );

         // �ύ
         final int returnBatchFlag = paymentBatchService.submit( paymentBatchVO );
         insertlog( request, paymentBatchVO, Operate.SUBMIT, paymentBatchVO.getBatchId(), "issue_Actual" );

         // ��������η������޸�
         if ( returnBatchFlag == 0 )
         {
            // ���ò�ѯ����
            ( ( PaymentBatchVO ) form ).setBatchId( null );
            ( ( PaymentBatchVO ) form ).setSortColumn( null );
            paymentBatchVO.setPageFlag( PaymentBatchService.PAGE_FLAG_BATCH );
         }

         // ���Selected IDs����Action
         ( ( PaymentBatchVO ) form ).setSelectedIds( "" );

         // ����pageFlag ��ת
         return forward( paymentBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Rollback Estimation
    *	�˻�
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward rollback_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡ�� BatchTempVO
         final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) form;
         paymentBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final PaymentBatchService paymentBatchService = ( PaymentBatchService ) getService( "paymentBatchService" );

         // �˻�
         final int rollbackBatchFlag = paymentBatchService.rollback( paymentBatchVO, getRole( request, response ) );
         insertlog( request, paymentBatchVO, Operate.ROllBACK, paymentBatchVO.getBatchId(), "rollback_estimation" );

         // ��������η������˻���
         if ( rollbackBatchFlag == 0 )
         {
            // ���ò�ѯ����
            ( ( PaymentBatchVO ) form ).setBatchId( null );
            ( ( PaymentBatchVO ) form ).setSortColumn( null );
            paymentBatchVO.setPageFlag( PaymentBatchService.PAGE_FLAG_BATCH );
         }

         // ���Selected IDs����Action
         ( ( PaymentBatchVO ) form ).setSelectedIds( "" );

         // ����pageFlag ��ת
         return forward( paymentBatchVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Forward
    *	ҳ����ת����
    *	@param pageFlag
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   private ActionForward forward( String pageFlag, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      if ( PaymentBatchService.PAGE_FLAG_HEADER.equalsIgnoreCase( pageFlag ) )
      {
         PaymentHeaderVO paymentHeader = new PaymentHeaderVO();
         paymentHeader.reset();
         paymentHeader.setBatchId( "" );
         paymentHeader.setStatus( getStatusesByStatusFlag( ( ( PaymentBatchVO ) form ).getStatusFlag() ) );
         return new PaymentHeaderAction().list_object( mapping, paymentHeader, request, response );
      }
      else
      {
         return list_object( mapping, form, request, response );
      }

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

}

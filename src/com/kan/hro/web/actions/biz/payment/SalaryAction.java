package com.kan.hro.web.actions.biz.payment;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.tag.AuthConstants;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.payment.CommonBatchVO;
import com.kan.hro.domain.biz.payment.PaymentBatchVO;
import com.kan.hro.domain.biz.payment.SalaryDTO;
import com.kan.hro.domain.biz.payment.SalaryDetailVO;
import com.kan.hro.domain.biz.payment.SalaryHeaderVO;
import com.kan.hro.domain.biz.settlement.BatchTempVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.service.inf.biz.payment.CommonBatchService;
import com.kan.hro.service.inf.biz.payment.PaymentBatchService;
import com.kan.hro.service.inf.biz.payment.SalaryHeaderService;

public class SalaryAction extends BaseAction
{
   public final static String accessAction = "HRO_SALARY_HEADER";

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
         final CommonBatchService salaryBatchService = ( CommonBatchService ) getService( "salaryBatchService" );

         final SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) form;
         final CommonBatchVO commonBatchVO = new CommonBatchVO();
         commonBatchVO.setSortColumn( salaryHeaderVO.getSortColumn() );
         commonBatchVO.setSortOrder( salaryHeaderVO.getSortOrder() );
         commonBatchVO.setSelectedIds( salaryHeaderVO.getSelectedIds() );
         commonBatchVO.setSubAction( salaryHeaderVO.getSubAction() );
         commonBatchVO.setBatchId( salaryHeaderVO.getBatchId() );
         commonBatchVO.setImportExcelName( salaryHeaderVO.getImportExcelName() );
         commonBatchVO.setStatus( salaryHeaderVO.getStatus() );

         // ���õ�ǰ�û�AccountId
         commonBatchVO.setAccountId( getAccountId( request, response ) );

         // ���õ�ǰ��clientId
         commonBatchVO.setClientId( getClientId( request, response ) );
         commonBatchVO.setCorpId( getCorpId( request, response ) );

         // ��������Ȩ��
         //setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, commonBatchVO );
         //commonBatchVO.setInList( KANUtil.convertStringToList( commonBatchVO.getHasIn(), "," ) );
         //commonBatchVO.setNotInList( KANUtil.convertStringToList( commonBatchVO.getNotIn(), "," ) );
         //setDataAuth( request, response, commonBatchVO );
         
         Set< String > rulePositionIds = new HashSet< String >();
         rulePositionIds.add( getPositionId( request, null ) );
         commonBatchVO.setRulePositionIds( rulePositionIds );
         commonBatchVO.setRulePublic( AuthConstants.RULE_UN_PUBLIC );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder salaryBatchHolder = new PagedListHolder();

         // ���뵱ǰҳ
         salaryBatchHolder.setPage( page );

         // ���û��ָ��������Ĭ�ϰ� BatchId����
         if ( commonBatchVO.getSortColumn() == null || commonBatchVO.getSortColumn().isEmpty() )
         {
            commonBatchVO.setSortColumn( "batchId" );
            commonBatchVO.setSortOrder( "desc" );
         }

         // ���뵱ǰֵ����
         salaryBatchHolder.setObject( commonBatchVO );
         // ����ҳ���¼����
         salaryBatchHolder.setPageSize( listPageSize_medium );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         salaryBatchService.getSalaryBatchVOsByCondition( salaryBatchHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( salaryBatchHolder, request );

         // Holder��д��Request����
         request.setAttribute( "salaryBatchHolder", salaryBatchHolder );
         // �����Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listSalaryBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listSalaryBatchBody" );
   }

   public ActionForward to_salaryHeader( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // �����������ID
      final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

      // ��ʼ��Service�ӿ�
      final SalaryHeaderService salaryHeaderService = ( SalaryHeaderService ) getService( "salaryHeaderService" );
      final CommonBatchService salaryBatchService = ( CommonBatchService ) getService( "salaryBatchService" );

      final CommonBatchVO commonBatchVO = salaryBatchService.getCommonBatchVOByBatchId( batchId );

      commonBatchVO.reset( null, request );
      request.setAttribute( "commonBatchVO", commonBatchVO );

      // ��õ�ǰҳ
      final String page = getPage( request );
      // ����Ƿ�Ajax����
      final String ajax = getAjax( request );
      final SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) form;
      salaryHeaderVO.setBatchId( batchId );

      // ���õ�ǰ�û�AccountId
      salaryHeaderVO.setAccountId( getAccountId( request, response ) );

      // ���õ�ǰ��clientId
      salaryHeaderVO.setClientId( getClientId( request, response ) );
      salaryHeaderVO.setCorpId( getCorpId( request, response ) );

      // ��ʼ��PagedListHolder���������÷�ʽ����Service
      final PagedListHolder salaryHeaderHolder = new PagedListHolder();

      // ���뵱ǰҳ
      salaryHeaderHolder.setPage( page );

      // ���û��ָ��������Ĭ�ϰ� BatchId����
      if ( salaryHeaderVO.getSortColumn() == null || salaryHeaderVO.getSortColumn().isEmpty() )
      {
         salaryHeaderVO.setSortColumn( "salaryHeaderId" );
         salaryHeaderVO.setSortOrder( "desc" );
      }

      // ���뵱ǰֵ����
      salaryHeaderHolder.setObject( salaryHeaderVO );
      // ����ҳ���¼����
      salaryHeaderHolder.setPageSize( listPageSize_medium );
      // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
      salaryHeaderService.getSalaryHeaderVOsByCondition( salaryHeaderHolder, true );
      // ˢ��Holder�����ʻ���ֵ
      refreshHolder( salaryHeaderHolder, request );

      salaryHeaderService.getSalaryDTOsByCondition( salaryHeaderHolder, true );

      // Reset PaymentHeaderDTOHolder
      if ( salaryHeaderHolder != null && salaryHeaderHolder.getHolderSize() > 0 )
      {
         final List< Object > salaryDTOOjbects = salaryHeaderHolder.getSource();

         if ( salaryDTOOjbects != null && salaryDTOOjbects.size() > 0 )
         {
            for ( Object salaryDTOOjbect : salaryDTOOjbects )
            {
               final SalaryDTO tempSalaryDTO = ( SalaryDTO ) salaryDTOOjbect;

               // Reset PaymentHeaderVO
               final SalaryHeaderVO tempSalaryHeaderVO = tempSalaryDTO.getSalaryHeaderVO();

               if ( tempSalaryHeaderVO != null )
               {
                  tempSalaryHeaderVO.reset( mapping, request );
               }

               // Reset PaymentDetailVO
               final List< SalaryDetailVO > salaryDetailVOs = tempSalaryDTO.getSalaryDetailVOs();

               if ( salaryDetailVOs != null && salaryDetailVOs.size() > 0 )
               {
                  for ( SalaryDetailVO tempSalaryDetailVO : salaryDetailVOs )
                  {
                     tempSalaryDetailVO.reset( mapping, request );
                     if ( KANUtil.filterEmpty( tempSalaryDetailVO.getItemType() ) == null )
                     {
                        tempSalaryDetailVO.setItemType( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( tempSalaryDetailVO.getItemId() ).getItemType() );
                     }
                  }
               }

            }
         }

      }

      request.setAttribute( "salaryDTOHolder", salaryHeaderHolder );

      // ���ȫ������״̬���ı��ˣ�����ת����һ��
      if ( salaryHeaderHolder == null || salaryHeaderHolder.getHolderSize() == 0 )
      {
         ( ( SalaryHeaderVO ) form ).setSortColumn( null );
         return list_object( mapping, form, request, response );
      }

      // Holder��д��Request����
      request.setAttribute( "salaryHeaderHolder", salaryHeaderHolder );
      if ( new Boolean( ajax ) )
      {
         // д��Role
         request.setAttribute( "role", getRole( request, response ) );
         return mapping.findForward( "listSalaryDetailTable" );
      }

      return mapping.findForward( "listSalaryHeadBody" );
   }

   /**
    * �鿴����
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_salaryDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // �����������ID
      final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) );

      // ��ʼ��Service�ӿ�
      final SalaryHeaderService salaryHeaderService = ( SalaryHeaderService ) getService( "salaryHeaderService" );
      final SalaryHeaderVO salaryHeaderVO = salaryHeaderService.getSalaryHeaderVOByHeaderId( headerId );
      salaryHeaderVO.reset( null, request );
      request.setAttribute( "salaryHeaderForm", salaryHeaderVO );
      // ��ʼ��PagedListHolder
      PagedListHolder salaryDTOHolder = new PagedListHolder();

      // ����
      salaryHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
      salaryHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

      // Ĭ������
      if ( request.getParameter( "sortColumn" ) == null || request.getParameter( "sortColumn" ).trim().isEmpty() )
      {
         salaryHeaderVO.setSortColumn( "salaryHeaderId" );
         salaryHeaderVO.setSortOrder( "desc" );
      }

      // ����ҳ��
      salaryDTOHolder.setPage( getPage( request ) );
      salaryDTOHolder.setObject( salaryHeaderVO );
      // ����ҳ���С
      salaryDTOHolder.setPageSize( listPageSize );

      salaryHeaderService.getSalaryDTOsByCondition( salaryDTOHolder, true );

      // Reset PaymentHeaderDTOHolder
      if ( salaryDTOHolder != null && salaryDTOHolder.getHolderSize() > 0 )
      {
         final List< Object > salaryDTOOjbects = salaryDTOHolder.getSource();

         if ( salaryDTOOjbects != null && salaryDTOOjbects.size() > 0 )
         {
            for ( Object salaryDTOOjbect : salaryDTOOjbects )
            {
               final SalaryDTO tempSalaryDTO = ( SalaryDTO ) salaryDTOOjbect;

               // Reset PaymentHeaderVO
               final SalaryHeaderVO tempSalaryHeaderVO = tempSalaryDTO.getSalaryHeaderVO();

               if ( tempSalaryHeaderVO != null )
               {
                  tempSalaryHeaderVO.reset( mapping, request );
               }

               // Reset PaymentDetailVO
               final List< SalaryDetailVO > salaryDetailVOs = tempSalaryDTO.getSalaryDetailVOs();

               if ( salaryDetailVOs != null && salaryDetailVOs.size() > 0 )
               {
                  for ( SalaryDetailVO tempSalaryDetailVO : salaryDetailVOs )
                  {
                     tempSalaryDetailVO.reset( mapping, request );
                     if ( KANUtil.filterEmpty( tempSalaryDetailVO.getItemType() ) == null )
                     {
                        tempSalaryDetailVO.setItemType( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( tempSalaryDetailVO.getItemId() ).getItemType() );
                     }
                  }
               }

            }
         }
      }

      request.setAttribute( "salaryDTOHolder", salaryDTOHolder );

      // ����Ƿ�Ajax����
      final String ajax = request.getParameter( "ajax" );

      // �����ajax�������ɾ����������ת��Table�������ֶ�Ӧ��jsp
      if ( new Boolean( ajax ) )
      {
         // д��Role
         request.setAttribute( "role", getRole( request, response ) );
         return mapping.findForward( "listSalaryDetailTable" );
      }

      // ���ȫ������״̬���ı��ˣ�����ת����һ��
      if ( salaryDTOHolder == null || salaryDTOHolder.getHolderSize() == 0 )
      {
         ( ( SalaryHeaderVO ) form ).setSortColumn( null );
         return list_object( mapping, form, request, response );
      }

      return mapping.findForward( "listSalaryDetail" );
   }

   /**
    * To ObjectNew �½�����
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
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
    * Add Object �������
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2013-12-04
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
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
               clientOrderHeaderVO.setMonthly( batchTempVO.getMonthly() );

               // ����Service�����洢���ݣ���Ҫ����Transaction��
               rows = paymentBatchService.insertPaymentBatchInHouse( paymentBatchVO, batchTempVO, clientOrderHeaderVO, serviceContractVO );
            }
            else
            {
               // ����Service�����洢���ݣ���Ҫ����Transaction��
               rows = paymentBatchService.insertPaymentBatch( paymentBatchVO, serviceContractVO );
            }

            if ( rows > 0 )
            {
               // ������ӳɹ����
               success( request, null, "�ɹ��������� " + paymentBatchVO.getBatchId() + " ��" );
            }
            else
            {
               // ���ؾ�����
               warning( request, null, "����δ������û�з������������ݣ�" );
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
    * submit_salary
    * 
    * �ύ��״̬Ϊ��׼
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2014-05-20
   public ActionForward submit_salary( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡ�� BatchVO
         final SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) form;

         // ��ʼ��Service�ӿ�
         final SalaryHeaderService salaryHeaderService = ( SalaryHeaderService ) getService( "salaryHeaderService" );

         // �����������ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

         final CommonBatchVO commonBatchVO = new CommonBatchVO();

         if ( KANUtil.filterEmpty( batchId ) != null )
         {
            // ��ʼ��CommonBatchVO
            commonBatchVO.setBatchId( batchId );
         }

         commonBatchVO.setSelectedIds( salaryHeaderVO.getSelectedIds() );
         commonBatchVO.setPageFlag( request.getParameter( "pageFlag" ) );
         commonBatchVO.setSubAction( request.getParameter( "subAction" ) );

         // �ύ����׼
         int row = salaryHeaderService.submit( commonBatchVO );
         insertlog( request, commonBatchVO, Operate.SUBMIT, commonBatchVO.getBatchId(), "submit_salary" );
         ( ( SalaryHeaderVO ) form ).setSelectedIds( "" );

         // ����pageFlag ��ת
         if ( KANUtil.filterEmpty( batchId ) != null && commonBatchVO.getPageFlag().equalsIgnoreCase( SalaryHeaderService.PAGE_FLAG_HEADER ) && row != 1 )
         {
            return to_salaryHeader( mapping, form, request, response );
         }

         // ���Form����
         ( ( SalaryHeaderVO ) form ).reset();
         ( ( SalaryHeaderVO ) form ).setBatchId( "" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**
    * rollback_salary
    * 
    * �˻أ�ֱ��ɾ��
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward rollback_salary( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡ�� BatchVO
         final SalaryHeaderVO salaryHeaderVO = ( SalaryHeaderVO ) form;

         // ��ʼ��Service�ӿ�
         final SalaryHeaderService salaryHeaderService = ( SalaryHeaderService ) getService( "salaryHeaderService" );

         // �����������ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );

         final CommonBatchVO commonBatchVO = new CommonBatchVO();
         if ( KANUtil.filterEmpty( batchId ) != null )
         {
            // ��ʼ��CommonBatchVO
            commonBatchVO.setBatchId( batchId );
         }
         commonBatchVO.setSelectedIds( salaryHeaderVO.getSelectedIds() );
         commonBatchVO.setPageFlag( request.getParameter( "pageFlag" ) );
         commonBatchVO.setSubAction( request.getParameter( "subAction" ) );

         // �˻�
         int row = salaryHeaderService.rollback( commonBatchVO );
         insertlog( request, commonBatchVO, Operate.ROllBACK, commonBatchVO.getBatchId(), "rollback_salary" );
         ( ( SalaryHeaderVO ) form ).setSelectedIds( "" );
         // ����pageFlag ��ת
         if ( KANUtil.filterEmpty( batchId ) != null && commonBatchVO.getPageFlag().equalsIgnoreCase( SalaryHeaderService.PAGE_FLAG_HEADER ) && row != 1 )
         {
            return to_salaryHeader( mapping, form, request, response );
         }

         // ���Form����
         ( ( SalaryHeaderVO ) form ).reset();
         ( ( SalaryHeaderVO ) form ).setBatchId( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
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

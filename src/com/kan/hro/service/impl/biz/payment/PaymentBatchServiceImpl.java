package com.kan.hro.service.impl.biz.payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.message.MessageMailDao;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.domain.message.MessageMailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Mail;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.dao.inf.biz.attendance.TimesheetBatchDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetHeaderDao;
import com.kan.hro.dao.inf.biz.cb.CBBatchDao;
import com.kan.hro.dao.inf.biz.cb.CBDetailDao;
import com.kan.hro.dao.inf.biz.cb.CBHeaderDao;
import com.kan.hro.dao.inf.biz.employee.EmployeeDao;
import com.kan.hro.dao.inf.biz.payment.PaymentBatchDao;
import com.kan.hro.dao.inf.biz.payment.PaymentDetailDao;
import com.kan.hro.dao.inf.biz.payment.PaymentHeaderDao;
import com.kan.hro.dao.inf.biz.payment.SalaryDetailDao;
import com.kan.hro.dao.inf.biz.payment.SalaryHeaderDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentHeaderDao;
import com.kan.hro.dao.inf.biz.sb.SBBatchDao;
import com.kan.hro.dao.inf.biz.sb.SBDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBHeaderDao;
import com.kan.hro.dao.inf.biz.settlement.BatchDao;
import com.kan.hro.dao.inf.biz.settlement.OrderDetailDao;
import com.kan.hro.dao.inf.biz.settlement.OrderHeaderDao;
import com.kan.hro.dao.inf.biz.settlement.ServiceContractDao;
import com.kan.hro.dao.inf.biz.vendor.VendorDao;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.cb.CBBatchVO;
import com.kan.hro.domain.biz.cb.CBDetailVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderDTO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.domain.biz.payment.EMPPaymentDTO;
import com.kan.hro.domain.biz.payment.PaymentBatchVO;
import com.kan.hro.domain.biz.payment.PaymentDTO;
import com.kan.hro.domain.biz.payment.PaymentDetailVO;
import com.kan.hro.domain.biz.payment.PaymentHeaderVO;
import com.kan.hro.domain.biz.payment.SalaryDetailVO;
import com.kan.hro.domain.biz.payment.SalaryHeaderVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentDetailVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.domain.biz.settlement.BatchTempVO;
import com.kan.hro.domain.biz.settlement.OrderDetailVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.payment.PaymentBatchService;
import com.kan.hro.service.inf.biz.settlement.BatchTempService;
import com.kan.hro.service.inf.biz.settlement.ServiceContractService;

public class PaymentBatchServiceImpl extends ContextService implements PaymentBatchService
{
   // ע��PaymentHeaderDao
   private PaymentHeaderDao paymentHeaderDao;

   // ע��PaymentDetailDao
   private PaymentDetailDao paymentDetailDao;

   // ע��BatchDao
   private BatchDao batchDao;

   // ע��ServiceContractDao
   private ServiceContractDao serviceContractDao;

   // ע��OrderHeaderDao
   private OrderHeaderDao orderHeaderDao;

   // ע��OrderDetailDao
   private OrderDetailDao orderDetailDao;

   // ע��ClientOrderHeaderService
   private ClientOrderHeaderService clientOrderHeaderService;

   // ע��BatchTempService
   private BatchTempService batchTempService;

   // ע��ClientOrderHeaderService
   private ServiceContractService serviceContractService;

   // ע��TimesheetBatchDao
   private TimesheetBatchDao timesheetBatchDao;

   // ע��TimesheetHeaderDao
   private TimesheetHeaderDao timesheetHeaderDao;

   // ע��SBBatchDao
   private SBBatchDao sbBatchDao;

   // ע��SBHeaderDao
   private SBHeaderDao sbHeaderDao;

   // ע��SBDetailDao
   private SBDetailDao sbDetailDao;

   // ע��CBBatchDao
   private CBBatchDao cbBatchDao;

   // ע��CBHeaderDao
   private CBHeaderDao cbHeaderDao;

   // ע��CBDetailDao
   private CBDetailDao cbDetailDao;

   // ע��SBAdjustmentHeaderDao
   private SBAdjustmentHeaderDao sbAdjustmentHeaderDao;

   // ע��SBAdjustmentDetailDao
   private SBAdjustmentDetailDao sbAdjustmentDetailDao;

   // ע��EmployeeDao
   private EmployeeDao employeeDao;

   // ע��MessageMailDao
   private MessageMailDao messageMailDao;

   // ע��SalaryHeaderDao
   private SalaryHeaderDao salaryHeaderDao;

   // ע��SalaryDetailDao
   private SalaryDetailDao salaryDetailDao;

   // ע��VendorDao
   private VendorDao vendorDao;

   public PaymentHeaderDao getPaymentHeaderDao()
   {
      return paymentHeaderDao;
   }

   public void setPaymentHeaderDao( PaymentHeaderDao paymentHeaderDao )
   {
      this.paymentHeaderDao = paymentHeaderDao;
   }

   public PaymentDetailDao getPaymentDetailDao()
   {
      return paymentDetailDao;
   }

   public void setPaymentDetailDao( PaymentDetailDao paymentDetailDao )
   {
      this.paymentDetailDao = paymentDetailDao;
   }

   public final ServiceContractDao getServiceContractDao()
   {
      return serviceContractDao;
   }

   public final void setServiceContractDao( ServiceContractDao serviceContractDao )
   {
      this.serviceContractDao = serviceContractDao;
   }

   public final ClientOrderHeaderService getClientOrderHeaderService()
   {
      return clientOrderHeaderService;
   }

   public final void setClientOrderHeaderService( ClientOrderHeaderService clientOrderHeaderService )
   {
      this.clientOrderHeaderService = clientOrderHeaderService;
   }

   public final BatchTempService getBatchTempService()
   {
      return batchTempService;
   }

   public final void setBatchTempService( BatchTempService batchTempService )
   {
      this.batchTempService = batchTempService;
   }

   public final ServiceContractService getServiceContractService()
   {
      return serviceContractService;
   }

   public final void setServiceContractService( ServiceContractService serviceContractService )
   {
      this.serviceContractService = serviceContractService;
   }

   public final BatchDao getBatchDao()
   {
      return batchDao;
   }

   public final void setBatchDao( BatchDao batchDao )
   {
      this.batchDao = batchDao;
   }

   public final OrderHeaderDao getOrderHeaderDao()
   {
      return orderHeaderDao;
   }

   public final void setOrderHeaderDao( OrderHeaderDao orderHeaderDao )
   {
      this.orderHeaderDao = orderHeaderDao;
   }

   public final OrderDetailDao getOrderDetailDao()
   {
      return orderDetailDao;
   }

   public final void setOrderDetailDao( OrderDetailDao orderDetailDao )
   {
      this.orderDetailDao = orderDetailDao;
   }

   public final TimesheetBatchDao getTimesheetBatchDao()
   {
      return timesheetBatchDao;
   }

   public final void setTimesheetBatchDao( TimesheetBatchDao timesheetBatchDao )
   {
      this.timesheetBatchDao = timesheetBatchDao;
   }

   public final TimesheetHeaderDao getTimesheetHeaderDao()
   {
      return timesheetHeaderDao;
   }

   public final void setTimesheetHeaderDao( TimesheetHeaderDao timesheetHeaderDao )
   {
      this.timesheetHeaderDao = timesheetHeaderDao;
   }

   public final SBBatchDao getSbBatchDao()
   {
      return sbBatchDao;
   }

   public final void setSbBatchDao( SBBatchDao sbBatchDao )
   {
      this.sbBatchDao = sbBatchDao;
   }

   public final SBHeaderDao getSbHeaderDao()
   {
      return sbHeaderDao;
   }

   public final void setSbHeaderDao( SBHeaderDao sbHeaderDao )
   {
      this.sbHeaderDao = sbHeaderDao;
   }

   public final SBDetailDao getSbDetailDao()
   {
      return sbDetailDao;
   }

   public final void setSbDetailDao( SBDetailDao sbDetailDao )
   {
      this.sbDetailDao = sbDetailDao;
   }

   public final CBBatchDao getCbBatchDao()
   {
      return cbBatchDao;
   }

   public final void setCbBatchDao( CBBatchDao cbBatchDao )
   {
      this.cbBatchDao = cbBatchDao;
   }

   public final CBHeaderDao getCbHeaderDao()
   {
      return cbHeaderDao;
   }

   public final void setCbHeaderDao( CBHeaderDao cbHeaderDao )
   {
      this.cbHeaderDao = cbHeaderDao;
   }

   public final CBDetailDao getCbDetailDao()
   {
      return cbDetailDao;
   }

   public final void setCbDetailDao( CBDetailDao cbDetailDao )
   {
      this.cbDetailDao = cbDetailDao;
   }

   public final SBAdjustmentHeaderDao getSbAdjustmentHeaderDao()
   {
      return sbAdjustmentHeaderDao;
   }

   public final void setSbAdjustmentHeaderDao( SBAdjustmentHeaderDao sbAdjustmentHeaderDao )
   {
      this.sbAdjustmentHeaderDao = sbAdjustmentHeaderDao;
   }

   public final SBAdjustmentDetailDao getSbAdjustmentDetailDao()
   {
      return sbAdjustmentDetailDao;
   }

   public final void setSbAdjustmentDetailDao( SBAdjustmentDetailDao sbAdjustmentDetailDao )
   {
      this.sbAdjustmentDetailDao = sbAdjustmentDetailDao;
   }

   public EmployeeDao getEmployeeDao()
   {
      return employeeDao;
   }

   public void setEmployeeDao( EmployeeDao employeeDao )
   {
      this.employeeDao = employeeDao;
   }

   public MessageMailDao getMessageMailDao()
   {
      return messageMailDao;
   }

   public void setMessageMailDao( MessageMailDao messageMailDao )
   {
      this.messageMailDao = messageMailDao;
   }

   public final SalaryHeaderDao getSalaryHeaderDao()
   {
      return salaryHeaderDao;
   }

   public final void setSalaryHeaderDao( SalaryHeaderDao salaryHeaderDao )
   {
      this.salaryHeaderDao = salaryHeaderDao;
   }

   public final SalaryDetailDao getSalaryDetailDao()
   {
      return salaryDetailDao;
   }

   public final void setSalaryDetailDao( SalaryDetailDao salaryDetailDao )
   {
      this.salaryDetailDao = salaryDetailDao;
   }

   public final VendorDao getVendorDao()
   {
      return vendorDao;
   }

   public final void setVendorDao( VendorDao vendorDao )
   {
      this.vendorDao = vendorDao;
   }

   @Override
   public PagedListHolder getPaymentBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final PaymentBatchDao paymentBatchDao = ( PaymentBatchDao ) getDao();
      pagedListHolder.setHolderSize( paymentBatchDao.countPaymentBatchVOsByCondition( ( PaymentBatchVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( paymentBatchDao.getPaymentBatchVOsByCondition( ( PaymentBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( paymentBatchDao.getPaymentBatchVOsByCondition( ( PaymentBatchVO ) pagedListHolder.getObject() ) );
      }

      // ����ϼ�ֵ
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         final PaymentBatchVO holderBatchVO = ( PaymentBatchVO ) pagedListHolder.getObject();
         for ( Object paymentBatchVOObject : pagedListHolder.getSource() )
         {
            final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) paymentBatchVOObject;
            paymentBatchVO.setAccountId( holderBatchVO.getAccountId() );
            paymentBatchVO.setCorpId( holderBatchVO.getCorpId() );
            paymentBatchVO.setRulePublic( holderBatchVO.getRulePublic() );
            paymentBatchVO.setRulePrivateIds( holderBatchVO.getRulePrivateIds() );
            paymentBatchVO.setRulePositionIds( holderBatchVO.getRulePositionIds() );
            paymentBatchVO.setRuleBranchIds( holderBatchVO.getRuleBranchIds() );
            paymentBatchVO.setRuleBusinessTypeIds( holderBatchVO.getRuleBusinessTypeIds() );
            paymentBatchVO.setRuleEntityIds( holderBatchVO.getRuleEntityIds() );
            fetchPaymentBatchVO( paymentBatchVO );
         }
      }

      return pagedListHolder;
   }

   /**  
    * FetchPaymentBatchVO
    * ���㹫˾��������ֵ�ϼ�(ָ������)
    * @param pagedListHolder
    * @throws KANException
    */
   private void fetchPaymentBatchVO( final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      final PaymentHeaderVO condPaymentHeaderVO = new PaymentHeaderVO();
      condPaymentHeaderVO.setBatchId( paymentBatchVO.getBatchId() );
      condPaymentHeaderVO.setStatus( paymentBatchVO.getStatus() );
      condPaymentHeaderVO.setCorpId( paymentBatchVO.getCorpId() );
      condPaymentHeaderVO.setAccountId( paymentBatchVO.getAccountId() );
      condPaymentHeaderVO.setRulePublic( paymentBatchVO.getRulePublic() );
      condPaymentHeaderVO.setRulePrivateIds( paymentBatchVO.getRulePrivateIds() );
      condPaymentHeaderVO.setRulePositionIds( paymentBatchVO.getRulePositionIds() );
      condPaymentHeaderVO.setRuleBranchIds( paymentBatchVO.getRuleBranchIds() );
      condPaymentHeaderVO.setRuleBusinessTypeIds( paymentBatchVO.getRuleBusinessTypeIds() );
      condPaymentHeaderVO.setRuleEntityIds( paymentBatchVO.getRuleEntityIds() );
      final List< Object > paymentHeaderVOs = this.paymentHeaderDao.getPaymentHeaderVOsByCondition( condPaymentHeaderVO );
      //final List< Object > paymentDetailVOs = this.paymentDetailDao.getPaymentDetailVOsByBatchVO( paymentBatchVO );
      final List< Object > paymentDetailVOs = new ArrayList< Object >();
      // ��ʼ�������Ĺ�Ա����
      final List< MappingVO > employeeMappingVOs = new ArrayList< MappingVO >();

      if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
      {
         for ( Object paymentHeaderObject : paymentHeaderVOs )
         {
            // ͨ��header�ж�detail Ȩ����header����
            final PaymentDetailVO paymentDetailVO = new PaymentDetailVO();
            paymentDetailVO.setPaymentHeaderId( ( ( PaymentHeaderVO ) paymentHeaderObject ).getPaymentHeaderId() );
            paymentDetailVO.setStatus( paymentBatchVO.getStatus() );
            paymentDetailVO.setRulePublic( paymentBatchVO.getRulePublic() );
            paymentDetailVO.setRulePrivateIds( paymentBatchVO.getRulePrivateIds() );
            paymentDetailVO.setRulePositionIds( paymentBatchVO.getRulePositionIds() );
            paymentDetailVO.setRuleBranchIds( paymentBatchVO.getRuleBranchIds() );
            paymentDetailVO.setRuleBusinessTypeIds( paymentBatchVO.getRuleBusinessTypeIds() );
            paymentDetailVO.setRuleEntityIds( paymentBatchVO.getRuleEntityIds() );
            paymentDetailVOs.addAll( this.paymentDetailDao.getPaymentDetailVOsByCondition( paymentDetailVO ) );
         }
      }

      // ����Ӫ�ճɱ��ϼ�
      if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
      {

         for ( Object paymentDetailVOObject : paymentDetailVOs )
         {
            PaymentDetailVO paymentDetailVO = ( PaymentDetailVO ) paymentDetailVOObject;

            if ( paymentBatchVO.getStatus().equals( paymentDetailVO.getStatus() ) )
            {
               paymentBatchVO.addBillAmountCompany( paymentDetailVO.getBillAmountCompany() );
               paymentBatchVO.addBillAmountPersonal( paymentDetailVO.getBillAmountPersonal() );
               paymentBatchVO.addCostAmountCompany( paymentDetailVO.getCostAmountCompany() );
               paymentBatchVO.addCostAmountPersonal( paymentDetailVO.getCostAmountPersonal() );
            }
         }
      }

      //�Ż����д�� 
      if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
      {
         for ( Object paymentHeaderVOObject : paymentHeaderVOs )
         {
            final PaymentHeaderVO paymentHeaderVO = ( PaymentHeaderVO ) paymentHeaderVOObject;

            if ( paymentBatchVO.getStatus().equals( paymentHeaderVO.getStatus() ) )
            {
               // ��װEmployee���� 
               if ( !isEmployeeVOExist( paymentHeaderVO, employeeMappingVOs ) )
               {
                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( paymentHeaderVO.getEmployeeId() );
                  mappingVO.setMappingValue( paymentHeaderVO.getEmployeeNameZH() );
                  mappingVO.setMappingTemp( paymentHeaderVO.getEmployeeNameEN() );
                  employeeMappingVOs.add( mappingVO );
               }

               // �����˰
               paymentBatchVO.addTaxAmountPersonal( paymentHeaderVO.getTaxAmountPersonal() );
               // ����֧����Ӹ�˰��ֵ
               paymentBatchVO.addCostAmountPersonal( paymentHeaderVO.getTaxAmountPersonal() );
            }

         }
      }
      paymentBatchVO.setEmployees( employeeMappingVOs );
   }

   /**  
    * IsEmployeeVOExist
    * �жϹ�Ա�Ƿ����
    * @param tempPaymentHeaderVO
    * @param employeeMappingVOs
    * @return
    */
   private boolean isEmployeeVOExist( final PaymentHeaderVO PaymentHeaderVO, final List< MappingVO > employeeMappingVOs )
   {
      if ( employeeMappingVOs != null && employeeMappingVOs.size() > 0 )
      {
         for ( MappingVO mappingVO : employeeMappingVOs )
         {
            if ( mappingVO.getMappingId().equals( PaymentHeaderVO.getEmployeeId() ) )
            {
               return true;
            }
         }
      }
      return false;
   }

   /**  
    * CcountAmount
    * ���㹫˾��������ֵ�ϼ�(ָ��PaymentHeaderVO)
    * @param paymentHeaderVO
    * @throws KANException
    */
   private void countAmount( final PaymentHeaderVO paymentHeaderVO ) throws KANException
   {
      // ���PaymentHeaderVO����
      paymentHeaderVO.setBillAmountCompany( "0" );
      paymentHeaderVO.setBillAmountPersonal( "0" );
      paymentHeaderVO.setCostAmountCompany( "0" );
      paymentHeaderVO.setCostAmountPersonal( "0" );

      final List< Object > paymentDetailVOs = this.paymentDetailDao.getPaymentDetailVOsByHeaderId( paymentHeaderVO.getPaymentHeaderId() );

      // ����Ӫ�ճɱ��ϼ�
      if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
      {
         for ( Object paymentDetailVOObject : paymentDetailVOs )
         {
            PaymentDetailVO paymentDetailVO = ( PaymentDetailVO ) paymentDetailVOObject;

            paymentHeaderVO.addBillAmountCompany( paymentDetailVO.getBillAmountCompany() );
            paymentHeaderVO.addBillAmountPersonal( paymentDetailVO.getBillAmountPersonal() );
            paymentHeaderVO.addCostAmountCompany( paymentDetailVO.getCostAmountCompany() );
            paymentHeaderVO.addCostAmountPersonal( paymentDetailVO.getCostAmountPersonal() );
         }
      }

   }

   @Override
   public PaymentBatchVO getPaymentBatchVOByBatchId( final String batchId ) throws KANException
   {
      final PaymentBatchVO paymentBatchVO = ( ( PaymentBatchDao ) getDao() ).getPaymentBatchVOByBatchId( batchId );

      // ����ϼ�
      if ( paymentBatchVO != null )
      {
         fetchPaymentBatchVO( paymentBatchVO );
      }

      return paymentBatchVO;
   }

   @Override
   public int updatePaymentBatch( final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      return ( ( PaymentBatchDao ) getDao() ).updatePaymentBatch( paymentBatchVO );
   }

   @Override
   public int insertPaymentBatch( final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      return ( ( PaymentBatchDao ) getDao() ).insertPaymentBatch( paymentBatchVO );
   }

   @Override
   public List< Object > getPaymentBatchVOsByCondition( final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      final List< Object > paymentBatchVOs = ( ( PaymentBatchDao ) getDao() ).getPaymentBatchVOsByCondition( paymentBatchVO );

      // ����ϼ�
      if ( paymentBatchVOs != null && paymentBatchVOs.size() > 0 )
      {
         for ( Object paymentBatchVOObject : paymentBatchVOs )
         {
            ( ( PaymentBatchVO ) paymentBatchVOObject ).setRulePublic( paymentBatchVO.getRulePublic() );
            ( ( PaymentBatchVO ) paymentBatchVOObject ).setRulePrivateIds( paymentBatchVO.getRulePrivateIds() );
            ( ( PaymentBatchVO ) paymentBatchVOObject ).setRulePositionIds( paymentBatchVO.getRulePositionIds() );
            ( ( PaymentBatchVO ) paymentBatchVOObject ).setRuleBranchIds( paymentBatchVO.getRuleBranchIds() );
            ( ( PaymentBatchVO ) paymentBatchVOObject ).setRuleBusinessTypeIds( paymentBatchVO.getRuleBusinessTypeIds() );
            ( ( PaymentBatchVO ) paymentBatchVOObject ).setRuleEntityIds( paymentBatchVO.getRuleEntityIds() );
            fetchPaymentBatchVO( ( PaymentBatchVO ) paymentBatchVOObject );
         }
      }

      return paymentBatchVOs;
   }

   @Override
   public int rollback( final PaymentBatchVO paymentBatchVO, final String role ) throws KANException
   {
      int rows = 0;

      try
      {
         // ��������
         startTransaction();

         // ��ʼ��ѡ����ID
         final String selectedIds = paymentBatchVO.getSelectedIds();
         // ��ʼ��PageFlag
         final String pageFlag = paymentBatchVO.getPageFlag() != null ? paymentBatchVO.getPageFlag().trim() : "";

         // �����ѡ����
         if ( selectedIds != null && !selectedIds.isEmpty() )
         {
            // �����ѡID Array
            final String[] selectedIdArray = selectedIds.split( "," );

            rows = selectedIdArray.length;

            // ����
            for ( String encodedSelectId : selectedIdArray )
            {

               // ����
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );

               // ��pageFlag�˻�
               if ( pageFlag.equalsIgnoreCase( PaymentBatchService.PAGE_FLAG_BATCH ) )
               {
                  rollbackBatch( selectId, role );
               }
               else if ( pageFlag.equalsIgnoreCase( PaymentBatchService.PAGE_FLAG_HEADER ) )
               {
                  rollbackHeader( selectId, role );
               }
            }

            // �����˻ظ�����
            tryRollbackBatch( paymentBatchVO.getBatchId() );

         }

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   /**  
    * Rollback Batch
    *	�������˻�
    *
    *	@param paymentBatchId
    *	@throws KANException
    */
   private void rollbackBatch( final String paymentBatchId, final String role ) throws KANException
   {
      // ��ʼ��PaymentHeaderVO�б�
      final List< Object > paymentHeaderVOs = this.getPaymentHeaderDao().getPaymentHeaderVOsByBatchId( paymentBatchId );

      if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
      {
         // ����
         for ( Object paymentHeaderVOObject : paymentHeaderVOs )
         {
            // ɾ��PaymentHeaderVO
            rollbackHeader( ( ( PaymentHeaderVO ) paymentHeaderVOObject ).getPaymentHeaderId(), role );
         }
      }

      // �˻�PaymentBatchVO
      ( ( PaymentBatchDao ) getDao() ).deletePaymentBatch( paymentBatchId );
   }

   /**  
    * Rollback Header
    *	������Э���˻�
    *
    *	@param paymentHeaderId
    *	@throws KANException
    */
   private void rollbackHeader( final String paymentHeaderId, final String role ) throws KANException
   {
      // ��ʼ��PaymentDetailVO�б�
      final List< Object > paymentDetailVOs = this.getPaymentDetailDao().getPaymentDetailVOsByHeaderId( paymentHeaderId );

      if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
      {
         // ����
         for ( Object paymentDetailVOObject : paymentDetailVOs )
         {
            // ɾ��PaymentDetailVO
            rollbackDetail( ( ( PaymentDetailVO ) paymentDetailVOObject ).getPaymentDetailId() );
         }
      }

      /** In House - �˻ؽ�����Ϣ - ��ʼ */
      if ( KANUtil.filterEmpty( role ) != null && role.equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         // ��ʼ��PaymentHeaderVO
         final PaymentHeaderVO paymentHeaderVO = this.getPaymentHeaderDao().getPaymentHeaderVOByHeaderId( paymentHeaderId );

         // ��ʼ��OrderDetailVO�б�
         final List< Object > orderDetailVOs = this.getOrderDetailDao().getOrderDetailVOsByContractId( paymentHeaderVO.getOrderContractId() );

         if ( orderDetailVOs != null && orderDetailVOs.size() > 0 )
         {
            for ( Object orderDetailVOObject : orderDetailVOs )
            {
               // ��ʼ��OrderDetailVO
               final OrderDetailVO orderDetailVO = ( ( OrderDetailVO ) orderDetailVOObject );

               /** �籣���籣�������̱��˻ش��� - ��ʼ  */
               if ( KANUtil.filterEmpty( orderDetailVO.getDetailType() ) != null )
               {
                  // �籣�˻����ύ������״̬
                  if ( KANUtil.filterEmpty( orderDetailVO.getDetailType() ).equals( "1" ) )
                  {
                     final SBDetailVO sbDetailVO = this.getSbDetailDao().getSBDetailVOByDetailId( orderDetailVO.getDetailId() );
                     sbDetailVO.setStatus( "4" );
                     this.getSbDetailDao().updateSBDetail( sbDetailVO );

                     final SBHeaderVO sbHeaderVO = this.getSbHeaderDao().getSBHeaderVOByHeaderId( sbDetailVO.getHeaderId() );

                     if ( sbHeaderVO != null && KANUtil.filterEmpty( sbHeaderVO.getStatus() ) != null && KANUtil.filterEmpty( sbHeaderVO.getStatus() ).equals( "5" ) )
                     {
                        sbHeaderVO.setStatus( "4" );
                        this.getSbHeaderDao().updateSBHeader( sbHeaderVO );

                        final SBBatchVO sbBatchVO = this.getSbBatchDao().getSBBatchVOByBatchId( sbHeaderVO.getBatchId() );

                        if ( sbBatchVO != null && KANUtil.filterEmpty( sbBatchVO.getStatus() ) != null && KANUtil.filterEmpty( sbBatchVO.getStatus() ).equals( "5" ) )
                        {
                           sbBatchVO.setStatus( "4" );
                           this.getSbBatchDao().updateSBBatch( sbBatchVO );
                        }
                     }
                  }
                  // �̱��˻����ύ������״̬
                  else if ( KANUtil.filterEmpty( orderDetailVO.getDetailType() ).equals( "2" ) )
                  {
                     final CBDetailVO cbDetailVO = this.getCbDetailDao().getCBDetailVOByDetailId( orderDetailVO.getDetailId() );
                     cbDetailVO.setStatus( "4" );
                     this.getCbDetailDao().updateCBDetail( cbDetailVO );

                     final CBHeaderVO cbHeaderVO = this.getCbHeaderDao().getCBHeaderVOByHeaderId( cbDetailVO.getHeaderId() );

                     if ( cbHeaderVO != null && KANUtil.filterEmpty( cbHeaderVO.getStatus() ) != null && KANUtil.filterEmpty( cbHeaderVO.getStatus() ).equals( "5" ) )
                     {
                        cbHeaderVO.setStatus( "4" );
                        this.getCbHeaderDao().updateCBHeader( cbHeaderVO );

                        final CBBatchVO cbBatchVO = this.getCbBatchDao().getCBBatchVOByBatchId( cbHeaderVO.getBatchId() );

                        if ( cbBatchVO != null && KANUtil.filterEmpty( cbBatchVO.getStatus() ) != null && KANUtil.filterEmpty( cbBatchVO.getStatus() ).equals( "5" ) )
                        {
                           cbBatchVO.setStatus( "4" );
                           this.getCbBatchDao().updateCBBatch( cbBatchVO );
                        }
                     }
                  }
                  // �籣�����˻����ύ������״̬
                  else if ( KANUtil.filterEmpty( orderDetailVO.getDetailType() ).equals( "3" ) )
                  {
                     // ���SBAdjustmentDetailVO
                     final SBAdjustmentDetailVO sbAdjustmentDetailVO = this.getSbAdjustmentDetailDao().getSBAdjustmentDetailVOByAdjustmentDetailId( orderDetailVO.getDetailId() );

                     // ���SBAdjustmentHeaderVO
                     final SBAdjustmentHeaderVO sbAdjustmentHeaderVO = this.getSbAdjustmentHeaderDao().getSBAdjustmentHeaderVOByAdjustmentHeaderId( sbAdjustmentDetailVO.getAdjustmentHeaderId() );
                     sbAdjustmentHeaderVO.setStatus( "3" );
                     this.getSbAdjustmentHeaderDao().updateSBAdjustmentHeader( sbAdjustmentHeaderVO );
                  }
                  // ���ʵ����˻����ύ״̬
                  else if ( KANUtil.filterEmpty( orderDetailVO.getDetailType() ).equals( "4" ) || KANUtil.filterEmpty( orderDetailVO.getDetailType() ).equals( "5" ) )
                  {
                     final List< String > salaryHeaderIds = KANUtil.jasonArrayToStringList( orderDetailVO.getHeaderId() );
                     final String salaryDetailId = orderDetailVO.getDetailId();

                     // ��������ID��Ϊ�յ����
                     if ( salaryHeaderIds != null && salaryHeaderIds.size() > 0 )
                     {
                        for ( String salaryHeaderId : salaryHeaderIds )
                        {
                           final SalaryHeaderVO salaryHeaderVO = this.getSalaryHeaderDao().getSalaryHeaderVOByHeaderId( salaryHeaderId );

                           if ( KANUtil.filterEmpty( salaryHeaderVO.getStatus() ) != null && KANUtil.filterEmpty( salaryHeaderVO.getStatus() ).equals( "3" ) )
                           {
                              salaryHeaderVO.setStatus( "2" );
                              salaryHeaderVO.setModifyDate( new Date() );
                              this.getSalaryHeaderDao().updateSalaryHeader( salaryHeaderVO );

                              final List< Object > salaryDetailVOs = this.getSalaryDetailDao().getSalaryDetailVOsByHeaderId( salaryHeaderVO.getSalaryHeaderId() );

                              if ( salaryDetailVOs != null && salaryDetailVOs.size() > 0 )
                              {
                                 for ( Object salaryDetaillVOObject : salaryDetailVOs )
                                 {
                                    final SalaryDetailVO tempSalaryDetailVO = ( SalaryDetailVO ) salaryDetaillVOObject;
                                    tempSalaryDetailVO.setStatus( "2" );
                                    tempSalaryDetailVO.setModifyDate( new Date() );
                                    this.getSalaryDetailDao().updateSalaryDetail( tempSalaryDetailVO );
                                 }
                              }
                           }
                        }
                     }

                     // �����ӱ�ID��Ϊ�յ����
                     if ( KANUtil.filterEmpty( salaryDetailId ) != null )
                     {
                        // �ӱ������޸�
                        final SalaryDetailVO salaryDetailVO = this.getSalaryDetailDao().getSalaryDetailVOByDetailId( salaryDetailId );
                        salaryDetailVO.setStatus( "2" );
                        salaryDetailVO.setModifyDate( new Date() );
                        this.getSalaryDetailDao().updateSalaryDetail( salaryDetailVO );

                        // ����ж�Salary Detail�Ƿ���ȫ���˻�
                        boolean submited = true;
                        final List< Object > salaryDetailVOs = this.getSalaryDetailDao().getSalaryDetailVOsByHeaderId( salaryDetailVO.getSalaryHeaderId() );

                        if ( salaryDetailVOs != null && salaryDetailVOs.size() > 0 )
                        {
                           for ( Object salaryDetaillVOObject : salaryDetailVOs )
                           {
                              final SalaryDetailVO tempSalaryDetailVO = ( SalaryDetailVO ) salaryDetaillVOObject;

                              if ( KANUtil.filterEmpty( tempSalaryDetailVO.getStatus() ) != null && !KANUtil.filterEmpty( tempSalaryDetailVO.getStatus() ).equals( "2" ) )
                              {
                                 submited = false;
                              }
                           }
                        }

                        if ( submited )
                        {
                           final SalaryHeaderVO salaryHeaderVO = this.getSalaryHeaderDao().getSalaryHeaderVOByHeaderId( salaryDetailVO.getSalaryHeaderId() );

                           if ( KANUtil.filterEmpty( salaryHeaderVO.getStatus() ) != null && KANUtil.filterEmpty( salaryHeaderVO.getStatus() ).equals( "3" ) )
                           {
                              salaryHeaderVO.setStatus( "2" );
                              salaryHeaderVO.setModifyDate( new Date() );
                              this.getSalaryHeaderDao().updateSalaryHeader( salaryHeaderVO );
                           }
                        }
                     }
                  }
               }
               /** �籣���籣�������̱��˻ش��� - ����  */

               // ����ɾ��OrderDetailVO�б�
               this.getOrderDetailDao().deleteOrderDetail( orderDetailVO.getOrderDetailId() );
            }
         }

         /** ���ڱ��˻ش��� - ��ʼ  */
         final ServiceContractVO serviceContractVO = this.getServiceContractDao().getServiceContractVOByContractId( paymentHeaderVO.getOrderContractId() );

         // ���OrderHeaderId
         final String orderHeaderId = serviceContractVO.getOrderHeaderId();

         if ( serviceContractVO != null && KANUtil.filterEmpty( serviceContractVO.getTimesheetId() ) != null )
         {
            // ��ʼ��TimesheetHeaderVO
            final TimesheetHeaderVO timesheetHeaderVO = this.getTimesheetHeaderDao().getTimesheetHeaderVOByHeaderId( serviceContractVO.getTimesheetId() );
            // TimesheetHeaderVO�˻�����׼״̬
            timesheetHeaderVO.setStatus( "3" );
            // ����TimesheetHeaderVO
            this.getTimesheetHeaderDao().updateTimesheetHeader( timesheetHeaderVO );

            // ���TimesheetBatchVO
            final TimesheetBatchVO timesheetBatchVO = this.getTimesheetBatchDao().getTimesheetBatchVOByBatchId( timesheetHeaderVO.getBatchId() );

            if ( timesheetBatchVO != null && KANUtil.filterEmpty( timesheetBatchVO.getStatus() ) != null && KANUtil.filterEmpty( timesheetBatchVO.getStatus() ).equals( "5" ) )
            {
               // TimesheetBatchVO�˻�����׼״̬
               timesheetBatchVO.setStatus( "3" );
               // �˻ؿ�������
               this.getTimesheetBatchDao().updateTimesheetBatch( timesheetBatchVO );
            }
         }
         /** ���ڱ��˻ش��� - ���� */

         // ����ɾ������Contract��Ϣ
         this.getServiceContractDao().deleteServiceContract( paymentHeaderVO.getOrderContractId() );

         // ��ʼ��ServiceContractVO�б�
         final List< Object > serviceContractVOs = this.getServiceContractDao().getServiceContractVOsByOrderHeaderId( orderHeaderId );

         // ���PaymentHeaderVO��ӦOrderHeaderVO�Ѿ�û���¼�ServiceContractVO���ݣ�ɾ��OrderHeaderVO
         if ( serviceContractVOs == null || serviceContractVOs.size() == 0 )
         {
            // ��ʼ��OrderHeaderVO
            final OrderHeaderVO orderHeaderVO = this.getOrderHeaderDao().getOrderHeaderVOByOrderHeaderId( orderHeaderId );

            // ��ȡ��������
            final String batchId = orderHeaderVO.getBatchId();

            // ɾ��OrderHeaderVO
            this.getOrderHeaderDao().deleteOrderHeader( orderHeaderId );

            // ��ʼ��OrderHeaderVO�б�
            final List< Object > orderHeaderVOs = this.getOrderHeaderDao().getOrderHeaderVOsByBatchId( batchId );

            // ���OrderHeaderVO��ӦBatchVO�Ѿ�û���¼�OrderHeaderVO���ݣ�ɾ��BatchVO
            if ( orderHeaderVOs == null || orderHeaderVOs.size() == 0 )
            {
               // ɾ��BatchVO
               this.getBatchDao().deleteBatch( batchId );
            }
         }
      }
      /** In House - �˻ؽ�����Ϣ - ���� */

      // �˻�PaymentHeaderVO
      this.getPaymentHeaderDao().deletePaymentHeader( paymentHeaderId );
   }

   /**  
    * Rollback Detail
    *	����Ŀ�˻�
    *
    *	@param paymentDetailId
    *	@param status
    *	@throws KANException
    */
   private void rollbackDetail( final String paymentDetailId ) throws KANException
   {
      final PaymentDetailVO paymentDetailVO = this.getPaymentDetailDao().getPaymentDetailVOByDetailId( paymentDetailId );

      if ( paymentDetailVO != null && paymentDetailVO.getStatus() != null && paymentDetailVO.getStatus().equals( "1" ) )
      {
         // ɾ��PaymentDetailVO
         this.getPaymentDetailDao().deletePaymentDetail( paymentDetailId );
      }
   }

   @Override
   public int submit( final PaymentBatchVO paymentBatchVO ) throws KANException
   {
      int returnBatchFlag = -1;
      try
      {
         // ��������
         startTransaction();

         // ��ʼ����ѡ��ID����
         final String selectedIds = paymentBatchVO.getSelectedIds();
         // ��ʼ��PageFlag
         final String pageFlag = paymentBatchVO.getPageFlag() != null ? paymentBatchVO.getPageFlag().trim() : "";
         // ��ʼ��SubAction
         final String subAction = paymentBatchVO.getSubAction() != null ? paymentBatchVO.getSubAction().trim() : "";

         // �����ѡ����
         if ( selectedIds != null && !selectedIds.isEmpty() )
         {
            // �����ѡID Array
            final String[] selectedIdArray = selectedIds.split( "," );
            // ��ʼ��״̬�ַ���
            String status = "0";

            // ������ύ
            if ( subAction.equalsIgnoreCase( BaseAction.SUBMIT_OBJECTS ) )
            {
               status = "2";
            }
            // ����Ƿ���
            else if ( subAction.equalsIgnoreCase( BaseAction.ISSUE_OBJECTS ) )
            {
               status = "3";
            }

            // ����
            for ( String encodedSelectId : selectedIdArray )
            {
               // ����
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );

               if ( pageFlag.equalsIgnoreCase( PaymentBatchService.PAGE_FLAG_BATCH ) )
               {
                  paymentBatchVO.setBatchId( KANUtil.encodeStringWithCryptogram( selectId ) );
                  submitBatch( selectId, status, paymentBatchVO.getModifyBy(), paymentBatchVO );
               }
               else if ( pageFlag.equalsIgnoreCase( PaymentBatchService.PAGE_FLAG_HEADER ) )
               {
                  submitHeader( selectId, status, paymentBatchVO.getModifyBy() );
               }

               // ���Ը����丸����״̬
               returnBatchFlag = trySubmitBatch( paymentBatchVO.getBatchId(), status, paymentBatchVO.getModifyBy() );
            }
         }

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }
      return returnBatchFlag;
   }

   /**  
    * Submit Batch
    *	�ύ����
    *
    *	@param batchId
    *	@param status
    *	@param userId
    *	@throws KANException
    */
   private void submitBatch( final String batchId, final String status, final String userId, final PaymentBatchVO condPaymentBatchVO ) throws KANException
   {
      // ��ʼ��PaymentHeaderVO List
      final PaymentHeaderVO tempHeaderVO = new PaymentHeaderVO();
      tempHeaderVO.setBatchId( batchId );
      //      tempHeaderVO.setNotIn( condPaymentBatchVO.getNotIn() );
      //      tempHeaderVO.setHasIn( condPaymentBatchVO.getHasIn() );
      tempHeaderVO.setAccountId( condPaymentBatchVO.getAccountId() );
      tempHeaderVO.setRulePublic( condPaymentBatchVO.getRulePublic() );
      tempHeaderVO.setRulePrivateIds( condPaymentBatchVO.getRulePrivateIds() );
      tempHeaderVO.setRulePositionIds( condPaymentBatchVO.getRulePositionIds() );
      tempHeaderVO.setRuleBranchIds( condPaymentBatchVO.getRuleBranchIds() );
      tempHeaderVO.setRuleBusinessTypeIds( condPaymentBatchVO.getRuleBusinessTypeIds() );
      tempHeaderVO.setRuleEntityIds( condPaymentBatchVO.getRuleEntityIds() );
      tempHeaderVO.setCorpId( condPaymentBatchVO.getCorpId() );
      final List< Object > paymentHeaderVOs = this.getPaymentHeaderDao().getPaymentHeaderVOsByCondition( tempHeaderVO );

      if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
      {
         // ����
         for ( Object paymentHeaderVOObject : paymentHeaderVOs )
         {
            final PaymentHeaderVO paymentHeaderVO = ( PaymentHeaderVO ) paymentHeaderVOObject;
            submitHeader( paymentHeaderVO.getPaymentHeaderId(), status, userId );
         }
      }

      // ���ĸ�����
      final PaymentBatchVO paymentBatchVO = ( ( PaymentBatchDao ) getDao() ).getPaymentBatchVOByBatchId( batchId );
      paymentBatchVO.setStatus( status );
      paymentBatchVO.setModifyBy( userId );
      paymentBatchVO.setModifyDate( new Date() );
      ( ( PaymentBatchDao ) getDao() ).updatePaymentBatch( paymentBatchVO );

   }

   /**  
    * Submit Header
    *	�ύ����Э��
    *
    *	@param headerId
    *	@param status
    *	@param userId
    *	@throws KANException
    */
   private void submitHeader( final String headerId, final String status, final String userId ) throws KANException
   {
      // ��ʼ��PaymentDetailVO List
      final List< Object > paymentDetailVOs = this.getPaymentDetailDao().getPaymentDetailVOsByHeaderId( headerId );

      if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
      {
         // ����
         for ( Object paymentDetailVOObject : paymentDetailVOs )
         {
            final PaymentDetailVO paymentDetailVO = ( PaymentDetailVO ) paymentDetailVOObject;
            submitDetail( paymentDetailVO.getPaymentDetailId(), status, userId );
         }
      }

      // �ύ����Э��
      final PaymentHeaderVO paymentHeaderVO = this.getPaymentHeaderDao().getPaymentHeaderVOByHeaderId( headerId );
      paymentHeaderVO.setStatus( status );
      paymentHeaderVO.setModifyBy( userId );
      paymentHeaderVO.setModifyDate( new Date() );
      this.getPaymentHeaderDao().updatePaymentHeader( paymentHeaderVO );

      // ������Э��������ݱ�ǳ��Ѽ���н��
      final ServiceContractVO serviceContractVO = this.getServiceContractDao().getServiceContractVOByContractId( paymentHeaderVO.getContractId() );
      if ( serviceContractVO != null )
      {
         serviceContractVO.setPaymentFlag( ServiceContractVO.TRUE );
         this.getServiceContractDao().updateServiceContract( serviceContractVO );
      }
      // noticeByEmail( paymentHeaderVO );
   }

   /**  
    * Submit Detail
    *	�ύ��Ŀ
    *
    *	@param detailId
    *	@param status
    *	@param userId
    *	@throws KANException
    */
   private void submitDetail( final String detailId, final String status, final String userId ) throws KANException
   {
      final PaymentDetailVO paymentDetailVO = this.getPaymentDetailDao().getPaymentDetailVOByDetailId( detailId );

      if ( paymentDetailVO != null && paymentDetailVO.getStatus() != null && paymentDetailVO.getStatus().equals( String.valueOf( Integer.parseInt( status ) - 1 ) ) )
      {
         paymentDetailVO.setStatus( status );
         paymentDetailVO.setModifyBy( userId );
         paymentDetailVO.setModifyDate( new Date() );
         this.getPaymentDetailDao().updatePaymentDetail( paymentDetailVO );
      }
   }

   /**  
    * Try Submit Batch
    *	�����ύ���� - �ύ�Ӷ��󵫲�����������Ƿ��ύ�����ʹ��
    *
    *	@param batchId
    *	@param status
    *	@param userId
    *	@return
    *	@throws KANException
    */
   private int trySubmitBatch( final String batchId, final String status, final String userId ) throws KANException
   {
      int submitRows = -1;

      if ( status != null && !status.isEmpty() )
      {
         // ��ʼ��PaymentBatchVO
         final PaymentBatchVO paymentBatchVO = ( ( PaymentBatchDao ) getDao() ).getPaymentBatchVOByBatchId( KANUtil.decodeStringFromAjax( batchId ) );
         // ��ʼ��PaymentHeaderVO�б�
         final List< Object > paymentHeaderVOs = this.getPaymentHeaderDao().getPaymentHeaderVOsByBatchId( KANUtil.decodeStringFromAjax( batchId ) );

         if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
         {
            int headerFlag = 0;

            // ����
            for ( Object paymentHeaderVOObject : paymentHeaderVOs )
            {
               final PaymentHeaderVO paymentHeaderVO = ( PaymentHeaderVO ) paymentHeaderVOObject;

               if ( paymentHeaderVO.getStatus() != null && !paymentHeaderVO.getStatus().isEmpty() && Integer.valueOf( paymentHeaderVO.getStatus() ) >= Integer.valueOf( status ) )
               {
                  headerFlag++;
               }

            }

            // ���н�ʷ�����ȫ������Ҫ���ĵ�״̬���޸����ε�״̬
            if ( headerFlag == paymentHeaderVOs.size() )
            {
               paymentBatchVO.setStatus( status );
               paymentBatchVO.setModifyBy( userId );
               paymentBatchVO.setModifyDate( new Date() );
               updatePaymentBatch( paymentBatchVO );
               submitRows = 0;
            }
         }
         else
         {
            submitRows = 0;
         }
      }

      return submitRows;
   }

   /**  
    * Try Rollback Batch
    *	�����˻����� - �ύ�Ӷ��󵫲�����������Ƿ��˻ص����ʹ��
    *
    * @param batchId
    * @param rollbackBatchFlag
    *	@return
    *	@throws KANException
    */
   private int tryRollbackBatch( final String batchId ) throws KANException
   {
      // ��ʼ��PaymentHeaderVO�б�
      final List< Object > paymentHeaderVOs = this.getPaymentHeaderDao().getPaymentHeaderVOsByBatchId( KANUtil.decodeStringFromAjax( batchId ) );

      if ( paymentHeaderVOs == null || paymentHeaderVOs.size() == 0 )
      {
         return ( ( PaymentBatchDao ) getDao() ).deletePaymentBatch( KANUtil.decodeStringFromAjax( batchId ) );
      }

      return 0;
   }

   @Override
   // Added by Kevin Jin at 2013-12-04
   public int insertPaymentBatch( final PaymentBatchVO paymentBatchVO, final ServiceContractVO serviceContractVO ) throws KANException
   {
      int rows = 0;

      try
      {
         // ��������
         startTransaction();

         rows = insertPaymentBatch_nt( paymentBatchVO, serviceContractVO );

         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   @Override
   // Added by Kevin Jin at 2013-12-29
   public int insertPaymentBatch_nt( final PaymentBatchVO paymentBatchVO, final ServiceContractVO serviceContractVO ) throws KANException
   {
      int rows = 0;

      try
      {
         final List< EMPPaymentDTO > empPaymentDTOs = this.getServiceContractService().getEMPPaymentDTOsByCondition( serviceContractVO );

         // ��������������������
         if ( empPaymentDTOs != null && empPaymentDTOs.size() > 0 )
         {
            for ( EMPPaymentDTO empPaymentDTO : empPaymentDTOs )
            {
               empPaymentDTO.calculatePayment( paymentBatchVO );
            }

            // ����
            for ( EMPPaymentDTO empPaymentDTO : empPaymentDTOs )
            {
               // ���PaymentDTO List
               final List< PaymentDTO > paymentDTOs = empPaymentDTO.getPaymentDTOs();

               if ( paymentDTOs != null && paymentDTOs.size() > 0 )
               {
                  if ( rows == 0 )
                  {
                     // �������
                     insertPaymentBatch( paymentBatchVO );

                     // һ��������ӳɹ�
                     rows = 1;
                  }

                  for ( PaymentDTO paymentDTO : paymentDTOs )
                  {
                     // ���PaymentHeaderVO
                     final PaymentHeaderVO paymentHeaderVO = paymentDTO.getPaymentHeaderVO();

                     if ( paymentHeaderVO != null && KANUtil.filterEmpty( paymentHeaderVO.getPaymentHeaderId() ) == null )
                     {
                        // ����BatchId
                        paymentHeaderVO.setBatchId( paymentBatchVO.getBatchId() );

                        if ( KANUtil.filterEmpty( paymentHeaderVO.getVendorId() ) != null )
                        {
                           // ��ʼ��VendorVO
                           final VendorVO vendorVO = getVendorDao().getVendorVOByVendorId( paymentHeaderVO.getVendorId() );

                           if ( vendorVO != null )
                           {
                              paymentHeaderVO.setVendorNameZH( vendorVO.getNameZH() );
                              paymentHeaderVO.setVendorNameEN( vendorVO.getNameEN() );
                           }
                        }

                        // ����SBHeaderVO
                        this.getPaymentHeaderDao().insertPaymentHeader( paymentHeaderVO );

                        // ���PaymentDetailVO List
                        final List< PaymentDetailVO > paymentDetailVOs = paymentDTO.getPaymentDetailVOs();

                        if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
                        {
                           // ����
                           for ( PaymentDetailVO paymentDetailVO : paymentDetailVOs )
                           {
                              // ����HeaderId
                              paymentDetailVO.setPaymentHeaderId( paymentHeaderVO.getPaymentHeaderId() );
                              // ����PaymentDetailVO
                              this.getPaymentDetailDao().insertPaymentDetail( paymentDetailVO );
                           }
                        }
                     }
                  }
               }
            }
         }

         if ( KANUtil.filterEmpty( paymentBatchVO.getBatchId() ) != null )
         {
            // ����ִ�н���ʱ��
            paymentBatchVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
            paymentBatchVO.setDeleted( PaymentBatchVO.TRUE );
            paymentBatchVO.setStatus( PaymentBatchVO.TRUE );
            // �޸�����
            updatePaymentBatch( paymentBatchVO );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return rows;
   }

   @Override
   // Added by Kevin Jin at 2013-12-29
   public int insertPaymentBatchInHouse( final PaymentBatchVO paymentBatchVO, final BatchTempVO batchTempVO, final ClientOrderHeaderVO clientOrderHeaderVO,
         final ServiceContractVO serviceContractVO ) throws KANException
   {
      int rows = 0;

      try
      {
         // ��������
         startTransaction();

         final List< String > flags = new ArrayList< String >();
         flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_SALARY );
         flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_SB );
         flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_CB );
         flags.add( ClientOrderHeaderService.SETTLEMENT_FLAG_OTHER );

         clientOrderHeaderVO.setSettlementFlags( flags );

         final List< ClientOrderDTO > clientOrderDTOs = this.getClientOrderHeaderService().getClientOrderDTOsByCondition( clientOrderHeaderVO );

         // ������������㶩��
         if ( clientOrderDTOs != null && clientOrderDTOs.size() > 0 )
         {
            for ( ClientOrderDTO clientOrderDTO : clientOrderDTOs )
            {
               clientOrderDTO.calculateSettlement( flags );
            }

            // ����Service�����洢����
            this.getBatchTempService().insertBatchTemp_nt( batchTempVO, clientOrderDTOs );

            // �ύ����
            batchTempVO.setSelectedIds( batchTempVO.getEncodedId() );
            batchTempVO.setPageFlag( BatchTempService.BATCH );
            this.getBatchTempService().submitBatchTemp_nt( batchTempVO );

            // ����Service�����洢����
            rows = insertPaymentBatch_nt( paymentBatchVO, serviceContractVO );
         }
         // �ύ����
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // �ع�����
         rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   // ��ȡ���ʵ��б�
   /* Added by siuxia at 2014-03-14 */
   @Override
   public PagedListHolder getPaymentDTOsByCondition( final PagedListHolder pagedListHolder ) throws KANException
   {
      // ��ʼ��PaymentBatchVO
      final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) pagedListHolder.getObject();
      // �� ȡpageFlag
      final String pageFlag = paymentBatchVO.getPageFlag();
      // ��ʼ�������Ŀ�Ŀ
      final List< ItemVO > itemVOs = new ArrayList< ItemVO >();

      List< Object > source = new ArrayList< Object >();

      // ��Batch��ȡ
      if ( KANUtil.filterEmpty( pageFlag ) != null && pageFlag.trim().equalsIgnoreCase( PAGE_FLAG_BATCH ) )
      {
         // ��ʼ��PaymentBatchVO�б�
         final List< Object > paymentBatchVOs = new ArrayList< Object >();

         // ����ѡ�е�batchId
         if ( KANUtil.filterEmpty( paymentBatchVO.getSelectedIds() ) != null )
         {
            for ( String selectId : paymentBatchVO.getSelectedIds().split( "," ) )
            {
               // װ��PaymentBatchVO
               paymentBatchVOs.add( ( ( PaymentBatchDao ) getDao() ).getPaymentBatchVOByBatchId( KANUtil.decodeStringFromAjax( selectId ) ) );
            }
         }
         else
         {
            // װ��������ЧPaymentBatchVO
            paymentBatchVOs.addAll( ( ( PaymentBatchDao ) getDao() ).getPaymentBatchVOsByCondition( paymentBatchVO ) );
         }

         source = getPaymentDTOsByPaymentBatchVOs( paymentBatchVOs, paymentBatchVO );
      }
      // ��Head��ȡ
      else if ( KANUtil.filterEmpty( pageFlag ) != null && pageFlag.trim().equalsIgnoreCase( PAGE_FLAG_HEADER ) )
      {
         // ��ʼ��PaymentHeaderVO�б�
         final List< Object > paymentHeaderVOs = new ArrayList< Object >();

         // ����ѡ�е�headerId
         if ( KANUtil.filterEmpty( paymentBatchVO.getSelectedIds() ) != null )
         {
            for ( String selectId : paymentBatchVO.getSelectedIds().split( "," ) )
            {
               // װ��PaymentHeaderVO
               paymentHeaderVOs.add( this.getPaymentHeaderDao().getPaymentHeaderVOByHeaderId( KANUtil.decodeStringFromAjax( selectId ) ) );
            }
         }
         else
         {
            final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
            paymentHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( paymentBatchVO.getBatchId() ) );
            paymentHeaderVO.setAccountId( paymentBatchVO.getAccountId() );
            paymentHeaderVO.setCorpId( paymentBatchVO.getCorpId() );

            paymentHeaderVO.setRulePublic( paymentBatchVO.getRulePublic() );
            paymentHeaderVO.setRulePrivateIds( paymentBatchVO.getRulePrivateIds() );
            paymentHeaderVO.setRulePositionIds( paymentBatchVO.getRulePositionIds() );
            paymentHeaderVO.setRuleBranchIds( paymentBatchVO.getRuleBranchIds() );
            paymentHeaderVO.setRuleBusinessTypeIds( paymentBatchVO.getRuleBusinessTypeIds() );
            paymentHeaderVO.setRuleEntityIds( paymentBatchVO.getRuleEntityIds() );

            // ��������װ��PaymentHeaderVO
            paymentHeaderVOs.addAll( this.getPaymentHeaderDao().getPaymentHeaderVOsByCondition( paymentHeaderVO ) );
         }

         source = getPaymentDTOsByPaymentHeaderVOs( paymentHeaderVOs, itemVOs );
      }

      dealPaymentDTOs( source );

      if ( itemVOs != null && itemVOs.size() > 0 )
      {
         pagedListHolder.setAdditionalObject( itemVOs );
      }
      pagedListHolder.setSource( source );

      return pagedListHolder;
   }

   // �����λ�ȡPayslipDTO�б�
   private List< Object > getPaymentDTOsByPaymentBatchVOs( final List< Object > paymentBatchVOs, final PaymentBatchVO paymentBatch ) throws KANException
   {
      // ��ʼ������ֵ
      final List< Object > paymentDTOs = new ArrayList< Object >();

      // ����PaymentBatchVO�б�
      if ( paymentBatchVOs != null && paymentBatchVOs.size() > 0 )
      {
         for ( Object paymentBatchVOObject : paymentBatchVOs )
         {
            // ��ʼ��PaymentBatchVO
            final PaymentBatchVO paymentBatchVO = ( PaymentBatchVO ) paymentBatchVOObject;

            // ��ʼ��PaymentHeaderVO
            final PaymentHeaderVO paymentHeaderVO = new PaymentHeaderVO();
            paymentHeaderVO.setBatchId( paymentBatchVO.getBatchId() );
            paymentHeaderVO.setAccountId( paymentBatchVO.getAccountId() );
            paymentHeaderVO.setCorpId( paymentBatchVO.getCorpId() );
            paymentHeaderVO.setRulePublic( paymentBatch.getRulePublic() );
            paymentHeaderVO.setRulePrivateIds( paymentBatch.getRulePrivateIds() );
            paymentHeaderVO.setRulePositionIds( paymentBatch.getRulePositionIds() );
            paymentHeaderVO.setRuleBranchIds( paymentBatch.getRuleBranchIds() );
            paymentHeaderVO.setRuleBusinessTypeIds( paymentBatch.getRuleBusinessTypeIds() );
            paymentHeaderVO.setRuleEntityIds( paymentBatch.getRuleEntityIds() );

            // ��ȡPaymentHeaderVO�б�
            final List< Object > paymentHeaderVOs = this.getPaymentHeaderDao().getPaymentHeaderVOsByCondition( paymentHeaderVO );

            // ����PaymentHeaderVO�б�
            if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
            {
               paymentDTOs.addAll( getPaymentDTOsByPaymentHeaderVOs( paymentHeaderVOs, null ) );
            }
         }
      }

      return paymentDTOs;
   }

   // ��Head��ȡPayslipDTO�б�
   private List< Object > getPaymentDTOsByPaymentHeaderVOs( final List< Object > paymentHeaderVOs, final List< ItemVO > itemVOs ) throws KANException
   {
      // ��ʼ������ֵ
      final List< Object > paymentDTOs = new ArrayList< Object >();

      // ����PaymentHeaderVO�б�
      if ( paymentHeaderVOs != null && paymentHeaderVOs.size() > 0 )
      {
         for ( Object paymentHeaderVOObject : paymentHeaderVOs )
         {
            // ����ϼ�
            countAmount( ( PaymentHeaderVO ) paymentHeaderVOObject );
            // ��ʼ��PaymentDTO
            final PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPaymentHeaderVO( ( PaymentHeaderVO ) paymentHeaderVOObject );

            // ��ȡPaymentDetailVO�б�
            final List< Object > paymentDetailVOs = this.getPaymentDetailDao().getPaymentDetailVOsByHeaderId( ( ( PaymentHeaderVO ) paymentHeaderVOObject ).getPaymentHeaderId() );

            // ����PaymentDetailVO�б�
            if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
            {
               int count = 0;

               for ( Object paymentDetailVOObject : paymentDetailVOs )
               {
                  final PaymentDetailVO paymentDetailVO = ( PaymentDetailVO ) paymentDetailVOObject;
                  paymentDetailVO.setAccountId( ( ( PaymentHeaderVO ) paymentHeaderVOObject ).getAccountId() );

                  final Map< String, String > currItems = getItems( paymentDTO.getPaymentDetailVOs() );

                  if ( count == 0 || !isExist( currItems, paymentDetailVO.getItemId() ) )
                  {
                     paymentDTO.getPaymentDetailVOs().add( paymentDetailVO );
                  }
                  else
                  {
                     paymentDTO.dealPaymentDetailVO( paymentDetailVO );
                  }

                  count++;

                  // ��ȡ�������п�Ŀ�ļ���
                  if ( itemVOs != null )
                  {
                     fetchItemVOs( itemVOs, paymentDetailVO );
                  }
               }
            }

            paymentDTOs.add( paymentDTO );
         }
      }

      return paymentDTOs;
   }

   private void fetchItemVOs( final List< ItemVO > itemVOs, final PaymentDetailVO paymentDetailVO )
   {
      // �жϿ�Ŀ�Ƿ���ڿ�Ŀ�����У�������������
      if ( itemVOs != null && itemVOs.size() > 0 )
      {
         for ( ItemVO itemVO : itemVOs )
         {
            if ( itemVO.getItemId().equals( paymentDetailVO.getItemId() ) )
            {
               return;
            }
         }
      }

      // ��ʼ������ItemVO
      final ItemVO itemVO = new ItemVO();
      itemVO.setItemId( paymentDetailVO.getItemId() );
      itemVO.setItemType( paymentDetailVO.getItemType() );
      itemVO.setItemNo( paymentDetailVO.getItemNo() );
      itemVOs.add( itemVO );
   }

   // PaymentDTO List����ʹ֮����PaymentDetailVOs��Ŀһ��
   private void dealPaymentDTOs( final List< Object > paymentDTOs )
   {
      if ( paymentDTOs != null && paymentDTOs.size() > 0 )
      {
         final Map< String, String > allItems = new HashMap< String, String >();

         for ( Object paymentDTOObject : paymentDTOs )
         {
            fetchItems( allItems, paymentDTOObject );
         }

         for ( Object paymentDTOObject : paymentDTOs )
         {
            final List< PaymentDetailVO > paymentDetailVOs = ( ( PaymentDTO ) paymentDTOObject ).getPaymentDetailVOs();

            final Map< String, String > currItems = getItems( paymentDetailVOs );

            if ( currItems != null && currItems.size() > 0 )
            {
               for ( Entry< String, String > item : allItems.entrySet() )
               {
                  if ( !isExist( currItems, item.getKey() ) )
                  {
                     final PaymentDetailVO tempDetailVO = new PaymentDetailVO();
                     tempDetailVO.setItemId( item.getKey() );
                     tempDetailVO.setItemType( item.getValue() );

                     ( ( PaymentDTO ) paymentDTOObject ).getPaymentDetailVOs().add( tempDetailVO );
                  }
               }
            }
         }
      }
   }

   // װ��Item����һ��PaymentDTO�е�����PaymentDetailVO��ȡ
   private void fetchItems( final Map< String, String > allItems, final Object paymentDTOObject )
   {
      final PaymentDTO paymentDTO = ( PaymentDTO ) paymentDTOObject;

      if ( paymentDTO != null && paymentDTO.getPaymentDetailVOs() != null && paymentDTO.getPaymentDetailVOs().size() > 0 )
      {
         for ( PaymentDetailVO paymentDetailVO : paymentDTO.getPaymentDetailVOs() )
         {
            if ( allItems.size() == 0 || !isExist( allItems, paymentDetailVO.getItemId() ) )
            {
               allItems.put( paymentDetailVO.getItemId(), paymentDetailVO.getItemType() );
            }
         }
      }
   }

   // �ɼ��Ϸ���һ��Map
   private Map< String, String > getItems( final List< ? > paymentDetailVOs )
   {
      final Map< String, String > items = new HashMap< String, String >();

      if ( paymentDetailVOs != null && paymentDetailVOs.size() > 0 )
      {
         for ( Object paymentDetailVOObject : paymentDetailVOs )
         {
            final PaymentDetailVO paymentDetailVO = ( PaymentDetailVO ) paymentDetailVOObject;
            items.put( paymentDetailVO.getItemId(), paymentDetailVO.getItemType() );
         }
      }

      return items;
   }

   // ����itemId�Ƿ������Map
   private boolean isExist( final Map< String, String > items, final String itemId )
   {
      if ( KANUtil.filterEmpty( itemId ) != null && items.size() > 0 )
      {
         for ( String key : items.keySet() )
         {
            if ( key.equals( itemId ) )
            {
               return true;
            }
         }
      }

      return false;
   }

   // ���ʷ����ʼ�֪ͨ  ֱ�ӷ��͡�������ϵͳ�ʼ���ѯ
   @SuppressWarnings("unused")
   private void noticeByEmail( PaymentHeaderVO paymentHeaderVO )
   {
      try
      {
         List< Object > paymentDetailObjects = paymentDetailDao.getPaymentDetailVOsByHeaderId( paymentHeaderVO.getPaymentHeaderId() );
         final EmployeeVO employeeVO = employeeDao.getEmployeeVOByEmployeeId( paymentHeaderVO.getEmployeeId() );
         if ( employeeVO != null && ( KANUtil.filterEmpty( employeeVO.getEmail1() ) != null || KANUtil.filterEmpty( employeeVO.getEmail2() ) != null ) )
         {
            final StringBuffer sb = new StringBuffer();
            sb.append( employeeVO.getNameZH() ).append( ",��ã����" ).append( paymentHeaderVO.getMonthly() ).append( "���ʵ�����������:<br/>" );
            sb.append( "<table border='1'>" );
            sb.append( "<tr><td>����</td><td>�·�</td>" );
            for ( Object obj : paymentDetailObjects )
            {
               final ItemVO itemVO = KANConstants.getKANAccountConstants( paymentHeaderVO.getAccountId() ).getItemVOByItemId( ( ( PaymentDetailVO ) obj ).getItemId() );
               sb.append( "<td>" ).append( itemVO.getNameZH() ).append( "</td>" );
            }
            sb.append( "<td>��������</td><td>����֧��</td><td>��˰</td><td>ʵ��</td></tr>" );
            sb.append( "<tr>" ).append( "<td>" + employeeVO.getNameZH() + "</td>" );
            sb.append( "<td>" + paymentHeaderVO.getMonthly() + "</td>" );
            for ( Object obj : paymentDetailObjects )
            {
               ( ( PaymentDetailVO ) obj ).setAccountId( paymentHeaderVO.getAccountId() );
               sb.append( "<td>" ).append( ( ( PaymentDetailVO ) obj ).getAmountPersonal() ).append( "</td>" );
            }
            sb.append( "<td>" + paymentHeaderVO.getBillAmountPersonal() + "</td>" );
            sb.append( "<td>" + paymentHeaderVO.getCostAmountPersonal() + "</td>" );
            sb.append( "<td>" + paymentHeaderVO.getTaxAmountPersonal() + "</td>" );
            sb.append( "<td>" + paymentHeaderVO.getAfterTaxSalary() + "</td>" );
            sb.append( "</tr></table>" );

            final MessageMailVO messageMailVO = new MessageMailVO();
            messageMailVO.setSystemId( "1" );
            messageMailVO.setAccountId( paymentHeaderVO.getAccountId() );
            messageMailVO.setCorpId( paymentHeaderVO.getCorpId() );
            messageMailVO.setTitle( paymentHeaderVO.getMonthly() + "�¹��ʵ�" );
            messageMailVO.setContent( sb.toString() );
            messageMailVO.setContentType( "2" );
            messageMailVO.setReception( KANUtil.filterEmpty( employeeVO.getEmail1() ) == null ? employeeVO.getEmail2() : employeeVO.getEmail1() );
            messageMailVO.setCreateBy( "system" );
            messageMailVO.setCreateDate( new Date() );
            new Mail( messageMailVO.getAccountId(), messageMailVO.getReception(), messageMailVO.getTitle(), messageMailVO.getContent() ).send( true );
         }
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }
   }
}

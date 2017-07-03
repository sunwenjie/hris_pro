package com.kan.hro.service.impl.biz.settlement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;

import com.kan.base.core.ContextService;
import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.hro.dao.inf.biz.attendance.TimesheetBatchDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetHeaderDao;
import com.kan.hro.dao.inf.biz.cb.CBBatchDao;
import com.kan.hro.dao.inf.biz.cb.CBDetailDao;
import com.kan.hro.dao.inf.biz.cb.CBHeaderDao;
import com.kan.hro.dao.inf.biz.payment.SalaryDetailDao;
import com.kan.hro.dao.inf.biz.payment.SalaryHeaderDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentHeaderDao;
import com.kan.hro.dao.inf.biz.sb.SBBatchDao;
import com.kan.hro.dao.inf.biz.sb.SBDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBHeaderDao;
import com.kan.hro.dao.inf.biz.settlement.BatchDao;
import com.kan.hro.dao.inf.biz.settlement.BatchTempDao;
import com.kan.hro.dao.inf.biz.settlement.OrderDetailDao;
import com.kan.hro.dao.inf.biz.settlement.OrderDetailTempDao;
import com.kan.hro.dao.inf.biz.settlement.OrderHeaderDao;
import com.kan.hro.dao.inf.biz.settlement.OrderHeaderTempDao;
import com.kan.hro.dao.inf.biz.settlement.ServiceContractDao;
import com.kan.hro.dao.inf.biz.settlement.ServiceContractTempDao;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.cb.CBBatchVO;
import com.kan.hro.domain.biz.cb.CBDetailVO;
import com.kan.hro.domain.biz.cb.CBHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderDTO;
import com.kan.hro.domain.biz.payment.SalaryDetailVO;
import com.kan.hro.domain.biz.payment.SalaryHeaderVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentDetailVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;
import com.kan.hro.domain.biz.sb.SBBatchVO;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.domain.biz.settlement.BatchTempVO;
import com.kan.hro.domain.biz.settlement.BatchVO;
import com.kan.hro.domain.biz.settlement.OrderDetailTempVO;
import com.kan.hro.domain.biz.settlement.OrderDetailVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderTempVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderVO;
import com.kan.hro.domain.biz.settlement.ServiceContractTempVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.domain.biz.settlement.SettlementTempDTO;
import com.kan.hro.service.inf.biz.settlement.BatchTempService;

public class BatchTempServiceImpl extends ContextService implements BatchTempService
{
   // ע��BatchTempDao
   private BatchTempDao batchTempDao;

   // ע��OrderHeaderTempDao
   private OrderHeaderTempDao orderHeaderTempDao;

   // ע��ServiceContractTemp
   private ServiceContractTempDao serviceContractTempDao;

   // ע��OrderDetailTempDao
   private OrderDetailTempDao orderDetailTempDao;

   // ע��BatchDao
   private BatchDao batchDao;

   // ע��OrderHeaderDao
   private OrderHeaderDao orderHeaderDao;

   // ע��ServiceContract
   private ServiceContractDao serviceContractDao;

   // ע��OrderDetailDao
   private OrderDetailDao orderDetailDao;

   // ע��TimesheetBatchDao
   private TimesheetBatchDao timesheetBatchDao;

   // ע��TimesheetHeaderDao
   private TimesheetHeaderDao timesheetHeaderDao;

   // ע��SBHeaderDao
   private SBHeaderDao sbHeaderDao;

   // ע��SBDetailDao
   private SBDetailDao sbDetailDao;

   // ע��CBHeaderDao
   private CBHeaderDao cbHeaderDao;

   // ע��CBDetailDao
   private CBDetailDao cbDetailDao;

   // ע��SBAdjustmentHeaderDao
   private SBAdjustmentHeaderDao sbAdjustmentHeaderDao;

   // ע��SBAdjustmentDetailDao
   private SBAdjustmentDetailDao sbAdjustmentDetailDao;

   // ע��SBBatchDao
   private SBBatchDao sbBatchDao;

   // ע��CBBatchDao
   private CBBatchDao cbBatchDao;

   // ע��SalaryHeaderDao
   private SalaryHeaderDao salaryHeaderDao;

   // ע��SalaryDetailDao
   private SalaryDetailDao salaryDetailDao;

   public final BatchTempDao getBatchTempDao()
   {
      return batchTempDao;
   }

   public final void setBatchTempDao( BatchTempDao batchTempDao )
   {
      this.batchTempDao = batchTempDao;
   }

   public OrderHeaderTempDao getOrderHeaderTempDao()
   {
      return orderHeaderTempDao;
   }

   public void setOrderHeaderTempDao( OrderHeaderTempDao orderHeaderTempDao )
   {
      this.orderHeaderTempDao = orderHeaderTempDao;
   }

   public ServiceContractTempDao getServiceContractTempDao()
   {
      return serviceContractTempDao;
   }

   public void setServiceContractTempDao( ServiceContractTempDao serviceContractTempDao )
   {
      this.serviceContractTempDao = serviceContractTempDao;
   }

   public OrderDetailTempDao getOrderDetailTempDao()
   {
      return orderDetailTempDao;
   }

   public void setOrderDetailTempDao( OrderDetailTempDao orderDetailTempDao )
   {
      this.orderDetailTempDao = orderDetailTempDao;
   }

   public BatchDao getBatchDao()
   {
      return batchDao;
   }

   public void setBatchDao( BatchDao batchDao )
   {
      this.batchDao = batchDao;
   }

   public OrderHeaderDao getOrderHeaderDao()
   {
      return orderHeaderDao;
   }

   public void setOrderHeaderDao( OrderHeaderDao orderHeaderDao )
   {
      this.orderHeaderDao = orderHeaderDao;
   }

   public ServiceContractDao getServiceContractDao()
   {
      return serviceContractDao;
   }

   public void setServiceContractDao( ServiceContractDao serviceContractDao )
   {
      this.serviceContractDao = serviceContractDao;
   }

   public OrderDetailDao getOrderDetailDao()
   {
      return orderDetailDao;
   }

   public void setOrderDetailDao( OrderDetailDao orderDetailDao )
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

   public final SBDetailDao getSbDetailDao()
   {
      return sbDetailDao;
   }

   public final void setSbDetailDao( SBDetailDao sbDetailDao )
   {
      this.sbDetailDao = sbDetailDao;
   }

   public final CBDetailDao getCbDetailDao()
   {
      return cbDetailDao;
   }

   public final void setCbDetailDao( CBDetailDao cbDetailDao )
   {
      this.cbDetailDao = cbDetailDao;
   }

   public final SBAdjustmentDetailDao getSbAdjustmentDetailDao()
   {
      return sbAdjustmentDetailDao;
   }

   public final void setSbAdjustmentDetailDao( SBAdjustmentDetailDao sbAdjustmentDetailDao )
   {
      this.sbAdjustmentDetailDao = sbAdjustmentDetailDao;
   }

   public final SBHeaderDao getSbHeaderDao()
   {
      return sbHeaderDao;
   }

   public final void setSbHeaderDao( SBHeaderDao sbHeaderDao )
   {
      this.sbHeaderDao = sbHeaderDao;
   }

   public final CBHeaderDao getCbHeaderDao()
   {
      return cbHeaderDao;
   }

   public final void setCbHeaderDao( CBHeaderDao cbHeaderDao )
   {
      this.cbHeaderDao = cbHeaderDao;
   }

   public final SBAdjustmentHeaderDao getSbAdjustmentHeaderDao()
   {
      return sbAdjustmentHeaderDao;
   }

   public final void setSbAdjustmentHeaderDao( SBAdjustmentHeaderDao sbAdjustmentHeaderDao )
   {
      this.sbAdjustmentHeaderDao = sbAdjustmentHeaderDao;
   }

   public final SBBatchDao getSbBatchDao()
   {
      return sbBatchDao;
   }

   public final void setSbBatchDao( SBBatchDao sbBatchDao )
   {
      this.sbBatchDao = sbBatchDao;
   }

   public final CBBatchDao getCbBatchDao()
   {
      return cbBatchDao;
   }

   public final void setCbBatchDao( CBBatchDao cbBatchDao )
   {
      this.cbBatchDao = cbBatchDao;
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

   @Override
   public PagedListHolder getBatchTempVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final BatchTempDao batchTempDao = ( BatchTempDao ) getDao();
      pagedListHolder.setHolderSize( batchTempDao.countBatchTempVOsByCondition( ( BatchTempVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( batchTempDao.getBatchTempVOsByCondition( ( BatchTempVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( batchTempDao.getBatchTempVOsByCondition( ( BatchTempVO ) pagedListHolder.getObject() ) );
      }

      // ��Ӱ�����Ա����
      if ( pagedListHolder.getSource() != null && pagedListHolder.getSource().size() > 0 )
      {
         for ( Object batchVOObject : pagedListHolder.getSource() )
         {
            final BatchTempVO batchTempVO = ( BatchTempVO ) batchVOObject;

            fetchSettlementBatchTempVO( batchTempVO );
         }
      }

      return pagedListHolder;
   }

   /**  
    * Fetch Settlement BatchTempVO
    * 
    * @param batchTempVO
    * @throws KANException
    */
   private void fetchSettlementBatchTempVO( final BatchTempVO batchTempVO ) throws KANException
   {
      // ��ʼ�������Ĺ�Ա����
      final List< MappingVO > employees = new ArrayList< MappingVO >();
      // ��ʼ����ѯ����
      final ServiceContractTempVO serviceContractTempTempVO = new ServiceContractTempVO();
      serviceContractTempTempVO.setAccountId( batchTempVO.getAccountId() );
      serviceContractTempTempVO.setBatchId( batchTempVO.getBatchId() );
      final List< Object > serviceContractTempVOs = this.serviceContractTempDao.getServiceContractTempVOsForEmployee( serviceContractTempTempVO );

      if ( serviceContractTempVOs != null && serviceContractTempVOs.size() > 0 )
      {
         for ( Object object : serviceContractTempVOs )
         {
            ServiceContractTempVO tempServiceContractTempVO = ( ServiceContractTempVO ) object;

            if ( !isEmployeeIdExist( tempServiceContractTempVO, employees ) )
            {
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( tempServiceContractTempVO.getEmployeeId() );
               mappingVO.setMappingValue( tempServiceContractTempVO.getEmployeeNameZH() );
               mappingVO.setMappingTemp( tempServiceContractTempVO.getEmployeeNameEN() );
               employees.add( mappingVO );
            }

         }
      }

      batchTempVO.setEmployees( employees );
   }

   /**  
    * IsEmployeeIdExist
    * �жϹ�ԱID�Ƿ����
    * @param tempServiceContractTempVO
    * @param employees
    * @return
    */
   private boolean isEmployeeIdExist( final ServiceContractTempVO tempServiceContractTempVO, final List< MappingVO > employees )
   {
      if ( tempServiceContractTempVO == null || tempServiceContractTempVO.getEmployeeId() == null )
      {
         return false;
      }
      if ( employees != null && employees.size() > 0 )
      {
         for ( MappingVO mappingVO : employees )
         {
            if ( mappingVO.getMappingId().equals( tempServiceContractTempVO.getEmployeeId() ) )
            {
               return true;
            }
         }
      }
      return false;
   }

   @Override
   public BatchTempVO getBatchTempVOByBatchId( final String batchId ) throws KANException
   {
      return ( ( BatchTempDao ) getDao() ).getBatchTempVOByBatchId( batchId );
   }

   @Override
   public int updateBatchTemp( final BatchTempVO batchTempVO ) throws KANException
   {
      return ( ( BatchTempDao ) getDao() ).updateBatchTemp( batchTempVO );
   }

   @Override
   public int insertBatchTemp( final BatchTempVO batchTempVO ) throws KANException
   {
      return ( ( BatchTempDao ) getDao() ).insertBatchTemp( batchTempVO );
   }

   @Override
   public int deleteBatchTemp( final String batchTempId ) throws KANException
   {
      return ( ( BatchTempDao ) getDao() ).deleteBatchTemp( batchTempId );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-10-30
   public int rollbackBatchTemp( final BatchTempVO batchTempVO ) throws KANException
   {
      int rows = 0;

      try
      {
         // ��������
         startTransaction();

         // ��ʼ����ѡ��ID����
         final String selectedIds = batchTempVO.getSelectedIds();

         // ��ʼ��PageFlag
         final String pageFlag = batchTempVO.getPageFlag() != null ? batchTempVO.getPageFlag().trim() : "";

         // �����ѡ����
         if ( selectedIds != null && !selectedIds.isEmpty() )
         {
            // Temp���BatchId
            String batchId = null;
            // ��ʼ����ʶ
            int orderHeaders = 0;
            int serviceContracts = 0;
            int index = 1;

            // �����ѡID Array
            final String[] selectedIdArray = selectedIds.split( "," );

            // ����Id����
            for ( String encodedSelectId : selectedIdArray )
            {
               // ����
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );

               // ������Rollback
               if ( pageFlag.equalsIgnoreCase( BatchTempService.BATCH ) )
               {
                  batchId = selectId;
                  rollbackBatch( selectId );
               }
               else if ( pageFlag.equalsIgnoreCase( BatchTempService.HEADER ) )
               {
                  // ��ʼ��OrderHeaderTempVO
                  final OrderHeaderTempVO orderHeaderTempVO = this.orderHeaderTempDao.getOrderHeaderTempVOByOrderHeaderId( selectId );

                  if ( orderHeaderTempVO != null )
                  {
                     final String tempBatchId = orderHeaderTempVO.getBatchId();
                     batchId = tempBatchId;

                     if ( orderHeaders == 0 )
                     {
                        // ��ȡOrderHeaderTempVO�б�
                        final List< Object > orderHeaderTempVOs = this.orderHeaderTempDao.getOrderHeaderTempVOsByBatchId( tempBatchId );

                        if ( orderHeaderTempVOs != null )
                        {
                           orderHeaders = orderHeaderTempVOs.size();
                        }
                     }

                     rollbackOrderHeader( selectId );

                     if ( index == orderHeaders )
                     {
                        // Try Delete Batch Temp
                        tryDeleteBatchTemp( tempBatchId );
                     }
                  }
               }
               else if ( pageFlag.equalsIgnoreCase( BatchTempService.CONTRACT ) )
               {
                  // ��ʼ��ServiceContractTempVO
                  final ServiceContractTempVO serviceContractTempVO = this.serviceContractTempDao.getServiceContractTempVOByContractId( selectId );

                  if ( serviceContractTempVO != null )
                  {
                     // ��ʼ��OrderHeaderId - Temp
                     final String orderHeaderId = serviceContractTempVO.getOrderHeaderId();

                     if ( batchId == null )
                     {
                        final OrderHeaderTempVO orderHeaderTempVO = this.orderHeaderTempDao.getOrderHeaderTempVOByOrderHeaderId( orderHeaderId );

                        if ( orderHeaderTempVO != null )
                        {
                           batchId = orderHeaderTempVO.getBatchId();
                        }
                     }

                     if ( serviceContracts == 0 )
                     {
                        // ��ȡServiceContractTempVO�б�
                        final List< Object > serviceContractTempVOs = this.serviceContractTempDao.getServiceContractTempVOsByOrderHeaderId( orderHeaderId );

                        if ( serviceContractTempVOs != null )
                        {
                           serviceContracts = serviceContractTempVOs.size();
                        }
                     }

                     rollbackContract( selectId );

                     if ( index == serviceContracts )
                     {
                        // Try Delete Order Header Temp
                        tryDeleteOrderHeaderTemp( orderHeaderId );
                     }
                  }
               }
               else if ( pageFlag.equalsIgnoreCase( BatchTempService.DETAIL ) )
               {
                  // ��ʼ��OrderDetailTempVO
                  final OrderDetailTempVO orderDetailTempVO = this.orderDetailTempDao.getOrderDetailTempVOByOrderDetailId( selectId );

                  if ( orderDetailTempVO != null )
                  {
                     // ��ʼ��ContractId - Temp
                     final String contractId = orderDetailTempVO.getContractId();

                     if ( batchId == null )
                     {
                        final ServiceContractTempVO serviceContractTempVO = this.serviceContractTempDao.getServiceContractTempVOByContractId( contractId );

                        if ( serviceContractTempVO != null )
                        {
                           final OrderHeaderTempVO orderHeaderTempVO = this.orderHeaderTempDao.getOrderHeaderTempVOByOrderHeaderId( serviceContractTempVO.getOrderHeaderId() );

                           if ( orderHeaderTempVO != null )
                           {
                              batchId = orderHeaderTempVO.getBatchId();
                           }
                        }
                     }

                     // ��ȡOrderDetailTempVO�б�
                     final List< Object > orderDetailTempVOs = this.orderDetailTempDao.getOrderDetailTempVOsByContractId( contractId );

                     if ( orderDetailTempVOs != null && orderDetailTempVOs.size() > 0 )
                     {
                        for ( Object object : orderDetailTempVOs )
                        {
                           rollbackOrderDetail( ( ( OrderDetailTempVO ) object ).getOrderDetailId() );
                        }
                     }

                     // Try Delete Service Contract Temp
                     tryDeleteServiceContractTemp( contractId );
                     break;
                  }
               }

               index++;
            }

            // ����OrderHeader��ServiceContract�еĻ���ֵ�������Temp����
            if ( batchId != null )
            {
               // ����δPost������
               recalculateUnPostAmount( batchId );
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

      return rows;
   }

   /**  
    * Rollback Batch 
    *	�˻�����
    *
    *	@param selectId
    *	@throws KANException
    */
   private void rollbackBatch( final String selectId ) throws KANException
   {
      // ���OrderHeaderTempVO List
      final List< Object > orderHeaderTempVOs = this.orderHeaderTempDao.getOrderHeaderTempVOsByBatchId( selectId );

      if ( orderHeaderTempVOs != null && orderHeaderTempVOs.size() > 0 )
      {
         // ����
         for ( Object object : orderHeaderTempVOs )
         {
            final OrderHeaderTempVO orderHeaderTempVO = ( OrderHeaderTempVO ) object;

            rollbackOrderHeader( orderHeaderTempVO.getOrderHeaderId() );
         }
      }

      // ɾ������
      ( ( BatchTempDao ) getDao() ).deleteBatchTemp( selectId );
   }

   /**  
    * Rollback Order Header
    *	�˻ض���
    *
    *	@param selectId
    *	@throws KANException
    */
   private void rollbackOrderHeader( final String selectId ) throws KANException
   {
      // ���ServiceContractTempVO List
      final List< Object > serviceContractTempVOs = this.serviceContractTempDao.getServiceContractTempVOsByOrderHeaderId( selectId );

      if ( serviceContractTempVOs != null && serviceContractTempVOs.size() > 0 )
      {
         // ����
         for ( Object object : serviceContractTempVOs )
         {
            final ServiceContractTempVO serviceContractTempVO = ( ServiceContractTempVO ) object;

            rollbackContract( serviceContractTempVO.getContractId() );
         }
      }

      // ɾ����������
      this.orderHeaderTempDao.deleteOrderHeaderTemp( selectId );
   }

   /**  
    * Rollback Contract
    *	�˻ط���Э��
    *
    *	@param selectId
    *	@throws KANException
    */
   private void rollbackContract( final String selectId ) throws KANException
   {
      // ���OrderDetailTempVO List
      final List< Object > orderDetailTempVOs = this.orderDetailTempDao.getOrderDetailTempVOsByContractId( selectId );

      if ( orderDetailTempVOs != null && orderDetailTempVOs.size() > 0 )
      {
         // ����
         for ( Object object : orderDetailTempVOs )
         {
            final OrderDetailTempVO orderDetailTempVO = ( OrderDetailTempVO ) object;

            // ɾ�������ӱ�
            this.orderDetailTempDao.deleteOrderDetailTemp( orderDetailTempVO.getOrderDetailId() );
         }
      }

      // ɾ������Э��
      this.serviceContractTempDao.deleteServiceContractTemp( selectId );
   }

   /**  
    * Rollback Order Detail
    * �˻ط���Э��
    *
    * @param selectId
    * @throws KANException
    */
   private void rollbackOrderDetail( final String selectId ) throws KANException
   {
      // ɾ�������ӱ�
      this.orderDetailTempDao.deleteOrderDetailTemp( selectId );
   }

   @Override
   public List< Object > getBatchTempVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( BatchTempDao ) getDao() ).getBatchTempVOsByAccountId( accountId );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-10-25
   public int insertBatchTemp( final BatchTempVO batchTempVO, final List< ClientOrderDTO > clientOrderDTOs ) throws KANException
   {
      int rows = 0;

      try
      {
         // ��������
         startTransaction();

         rows = insertBatchTemp_nt( batchTempVO, clientOrderDTOs );

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
   public int insertBatchTemp_nt( final BatchTempVO batchTempVO, final List< ClientOrderDTO > clientOrderDTOs ) throws KANException
   {
      int rows = 0;

      try
      {
         for ( ClientOrderDTO clientOrderDTO : clientOrderDTOs )
         {
            // ������ζ�Ӧ�� SettlementDTO����
            final List< SettlementTempDTO > settlementTempDTOs = clientOrderDTO.getSettlementTempDTOs();
            // ����
            if ( settlementTempDTOs != null && settlementTempDTOs.size() > 0 )
            {
               if ( rows == 0 )
               {
                  // ��������
                  ( ( BatchTempDao ) getDao() ).insertBatchTemp( batchTempVO );

                  // һ��������ӳɹ�
                  rows = 1;
               }

               // ����OrderHeaderVO
               final OrderHeaderTempVO orderHeaderTempVO = clientOrderDTO.getOrderHeaderTempVO();
               // ����OrderHeaderTempVO��BatchId
               orderHeaderTempVO.setBatchId( batchTempVO.getBatchId() );
               // ����OrderHeaderTempVO
               this.getOrderHeaderTempDao().insertOrderHeaderTemp( orderHeaderTempVO );

               for ( SettlementTempDTO settlementTempDTO : settlementTempDTOs )
               {
                  // ���SettlementDTO ��Ӧ�� ServiceContractTempVO �� OrderDetailTempVO����
                  final ServiceContractTempVO serviceContractTempVO = settlementTempDTO.getServiceContractTempVO();
                  // ����ServiceContractTempVO��OrderHeaderId
                  serviceContractTempVO.setOrderHeaderId( orderHeaderTempVO.getOrderHeaderId() );
                  // ����ServiceContractTempVO
                  this.getServiceContractTempDao().insertServiceContractTemp( serviceContractTempVO );

                  // ��÷���Э���Ӧ�Ŀ�Ŀ����
                  final List< OrderDetailTempVO > orderDetailTempVOs = settlementTempDTO.getOrderDetailTempVOs();
                  // ����
                  if ( orderDetailTempVOs != null && orderDetailTempVOs.size() > 0 )
                  {
                     for ( OrderDetailTempVO orderDetailTempVO : orderDetailTempVOs )
                     {
                        // ����OrderDetailTempVO��Ӧ��contractId
                        orderDetailTempVO.setContractId( serviceContractTempVO.getContractId() );
                        this.getOrderDetailTempDao().insertOrderDetailTemp( orderDetailTempVO );
                     }
                  }
               }
            }
         }

         if ( KANUtil.filterEmpty( batchTempVO.getBatchId() ) != null )
         {
            // ����ִ�н���ʱ��
            batchTempVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
            batchTempVO.setDeleted( BatchTempVO.TRUE );
            batchTempVO.setStatus( BatchTempVO.TRUE );
            // �޸�����
            ( ( BatchTempDao ) getDao() ).updateBatchTemp( batchTempVO );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return rows;
   }

   @Override
   // Reviewed by Kevin Jin at 2013-10-29
   public int submitBatchTemp( final BatchTempVO batchTempVO ) throws KANException
   {
      int rows = 0;

      try
      {
         // ��������
         startTransaction();

         rows = submitBatchTemp_nt( batchTempVO );

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
   public int submitBatchTemp_nt( final BatchTempVO batchTempVO ) throws KANException
   {
      int rows = 0;

      try
      {
         // ��ʼ����ѡ��ID����
         final String selectedIds = batchTempVO.getSelectedIds();

         // ��ʼ��PageFlag
         final String pageFlag = batchTempVO.getPageFlag() != null ? batchTempVO.getPageFlag().trim() : "";

         // �����ѡ����
         if ( selectedIds != null && !selectedIds.isEmpty() )
         {
            // �����ѡID Array
            final String[] selectedIdArray = selectedIds.split( "," );

            // Temp���BatchId
            String batchId = null;
            // ��ʼ����ʶ
            int orderHeaders = 0;
            int serviceContracts = 0;
            int index = 1;

            // ����
            for ( String encodedSelectId : selectedIdArray )
            {
               rows = selectedIds.length();

               // ����
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );

               // �������ύ
               if ( pageFlag.equalsIgnoreCase( BatchTempService.BATCH ) )
               {
                  batchId = selectId;
                  submitBatch( selectId, batchTempVO.getModifyBy() );
               }
               // �������ύ
               else if ( pageFlag.equalsIgnoreCase( BatchTempService.HEADER ) )
               {
                  // ��ʼ��OrderHeaderTempVO
                  final OrderHeaderTempVO orderHeaderTempVO = this.getOrderHeaderTempDao().getOrderHeaderTempVOByOrderHeaderId( selectId );

                  if ( orderHeaderTempVO != null )
                  {
                     // ��ʼ��BatchId - Temp
                     final String tempBatchId = orderHeaderTempVO.getBatchId();
                     batchId = tempBatchId;

                     if ( orderHeaders == 0 )
                     {
                        // ��ȡOrderHeaderTempVO�б�
                        final List< Object > orderHeaderTempVOs = this.getOrderHeaderTempDao().getOrderHeaderTempVOsByBatchId( tempBatchId );

                        if ( orderHeaderTempVOs != null )
                        {
                           orderHeaders = orderHeaderTempVOs.size();
                        }
                     }

                     // Prepare Batch
                     prepareBatch( orderHeaderTempVO, batchTempVO.getModifyBy() );

                     submitOrderHeader( orderHeaderTempVO, batchTempVO.getModifyBy() );

                     if ( index == orderHeaders )
                     {
                        // Try Delete Batch Temp
                        tryDeleteBatchTemp( tempBatchId );
                     }
                  }
               }
               // ������Э���ύ
               else if ( pageFlag.equalsIgnoreCase( BatchTempService.CONTRACT ) )
               {
                  // ��ʼ��ServiceContractTempVO
                  final ServiceContractTempVO serviceContractTempVO = this.getServiceContractTempDao().getServiceContractTempVOByContractId( selectId );

                  if ( serviceContractTempVO != null )
                  {
                     // ��ʼ��OrderHeaderId - Temp
                     final String orderHeaderId = serviceContractTempVO.getOrderHeaderId();

                     if ( batchId == null )
                     {
                        final OrderHeaderTempVO orderHeaderTempVO = this.getOrderHeaderTempDao().getOrderHeaderTempVOByOrderHeaderId( orderHeaderId );

                        if ( orderHeaderTempVO != null )
                        {
                           batchId = orderHeaderTempVO.getBatchId();
                        }
                     }

                     if ( serviceContracts == 0 )
                     {
                        // ��ȡServiceContractTempVO�б�
                        final List< Object > serviceContractTempVOs = this.getServiceContractTempDao().getServiceContractTempVOsByOrderHeaderId( orderHeaderId );

                        if ( serviceContractTempVOs != null )
                        {
                           serviceContracts = serviceContractTempVOs.size();
                        }
                     }

                     // Prepare Order Header
                     prepareOrderHeader( serviceContractTempVO, batchTempVO.getModifyBy() );

                     submitContract( serviceContractTempVO, batchTempVO.getModifyBy() );

                     if ( index == serviceContracts )
                     {
                        // Try Delete Order Header Temp
                        tryDeleteOrderHeaderTemp( orderHeaderId );
                     }
                  }
               }
               // ��������ϸ�ύ
               else if ( pageFlag.equalsIgnoreCase( BatchTempService.DETAIL ) )
               {
                  // ��ʼ��OrderDetailTempVO
                  final OrderDetailTempVO orderDetailTempVO = this.getOrderDetailTempDao().getOrderDetailTempVOByOrderDetailId( selectId );

                  if ( orderDetailTempVO != null )
                  {
                     // ��ʼ��ContractId - Temp
                     final String contractId = orderDetailTempVO.getContractId();

                     if ( batchId == null )
                     {
                        final ServiceContractTempVO serviceContractTempVO = this.getServiceContractTempDao().getServiceContractTempVOByContractId( contractId );

                        if ( serviceContractTempVO != null )
                        {
                           final OrderHeaderTempVO orderHeaderTempVO = this.getOrderHeaderTempDao().getOrderHeaderTempVOByOrderHeaderId( serviceContractTempVO.getOrderHeaderId() );

                           if ( orderHeaderTempVO != null )
                           {
                              batchId = orderHeaderTempVO.getBatchId();
                           }
                        }
                     }

                     // ���Ŀ��ڱ��籣���̱����籣������¼״̬Ϊ���ѽ��㡱
                     updateSettlementStatus( contractId, batchTempVO.getModifyBy() );

                     // Prepare Service Contract
                     prepareServiceContract( orderDetailTempVO, batchTempVO.getModifyBy() );

                     // ��ȡOrderDetailTempVO�б�
                     final List< Object > orderDetailTempVOs = this.getOrderDetailTempDao().getOrderDetailTempVOsByContractId( contractId );

                     if ( orderDetailTempVOs != null && orderDetailTempVOs.size() > 0 )
                     {
                        for ( Object object : orderDetailTempVOs )
                        {
                           ( ( OrderDetailTempVO ) object ).setContractId( orderDetailTempVO.getContractId() );
                           submitOrderDetail( ( OrderDetailTempVO ) object, batchTempVO.getModifyBy() );
                        }
                     }

                     // Try Delete Service Contract Temp
                     tryDeleteServiceContractTemp( contractId );
                     break;
                  }
               }

               index++;
            }

            // ����OrderHeader��ServiceContract�еĻ���ֵ��Temp��Ҳ�账��
            if ( batchId != null )
            {
               // ����Post��������
               recalculatePostAmount( batchId );

               // ����δPost������
               recalculateUnPostAmount( batchId );
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return rows;
   }

   // �������ύ
   // Reviewed by Kevin Jin at 2013-10-29
   private void submitBatch( final String selectId, final String userId ) throws KANException
   {
      // ��ȡ��������
      final BatchTempVO batchTempVO = ( ( BatchTempDao ) getDao() ).getBatchTempVOByBatchId( selectId );

      // ��ʼ��BatchVO
      final BatchVO batchVO = new BatchVO();
      // ����BatchVO
      BeanUtils.copyProperties( batchTempVO, batchVO );
      batchVO.setBatchTempId( batchTempVO.getBatchId() );
      batchVO.setCreateBy( userId );
      batchVO.setCreateDate( new Date() );
      batchVO.setModifyBy( userId );
      batchVO.setModifyDate( new Date() );

      // ����BatchVO
      this.batchDao.insertBatch( batchVO );

      // ��ȡ���ζ�ӦOrderHeaderTempVO�б�
      final List< Object > orderHeaderTempVOs = this.orderHeaderTempDao.getOrderHeaderTempVOsByBatchId( selectId );

      if ( orderHeaderTempVOs != null && orderHeaderTempVOs.size() > 0 )
      {
         // ����
         for ( Object object : orderHeaderTempVOs )
         {
            final OrderHeaderTempVO tempOrderHeaderTempVO = ( OrderHeaderTempVO ) object;
            tempOrderHeaderTempVO.setBatchId( batchVO.getBatchId() );

            // �ύOrderHeader
            submitOrderHeader( tempOrderHeaderTempVO, userId );
         }
      }

      // ɾ��BatchTempVO
      this.batchTempDao.deleteBatchTemp( batchTempVO.getBatchId() );
   }

   // ���ݶ����ύ����
   // Reviewed by Kevin Jin at 2013-10-29
   private void submitOrderHeader( final OrderHeaderTempVO orderHeaderTempVO, final String userId ) throws KANException
   {
      // ��ʼ��OrderHeaderVO
      final OrderHeaderVO orderHeaderVO = new OrderHeaderVO();
      // ����OrderHeaderVO
      BeanUtils.copyProperties( orderHeaderTempVO, orderHeaderVO );
      orderHeaderVO.setOrderHeaderTempId( orderHeaderTempVO.getOrderHeaderId() );
      orderHeaderVO.setCreateBy( userId );
      orderHeaderVO.setCreateDate( new Date() );
      orderHeaderVO.setModifyBy( userId );
      orderHeaderVO.setModifyDate( new Date() );

      // ����OrderHeaderVO
      this.orderHeaderDao.insertOrderHeader( orderHeaderVO );

      // ��ȡ������ӦServiceContractTempVO�б�
      final List< Object > serviceContractTempVOs = this.serviceContractTempDao.getServiceContractTempVOsByOrderHeaderId( orderHeaderTempVO.getOrderHeaderId() );

      if ( serviceContractTempVOs != null && serviceContractTempVOs.size() > 0 )
      {
         // ����
         for ( Object object : serviceContractTempVOs )
         {
            final ServiceContractTempVO tempServiceContractTempVO = ( ServiceContractTempVO ) object;
            tempServiceContractTempVO.setOrderHeaderId( orderHeaderVO.getOrderHeaderId() );

            // �ύServiceContractTempVO
            submitContract( tempServiceContractTempVO, userId );
         }
      }

      // ɾ��OrderHeaderTempVO
      this.orderHeaderTempDao.deleteOrderHeaderTemp( orderHeaderTempVO.getOrderHeaderId() );
   }

   // ���ݶ����ύ����
   // Reviewed by Kevin Jin at 2013-10-29
   private void submitContract( final ServiceContractTempVO serviceContractTempVO, final String userId ) throws KANException
   {
      logger.info( "Submit Contract Start - " + serviceContractTempVO.getEmployeeContractId() + " / " + serviceContractTempVO.getEmployeeNameZH() );
      
      // ��ʼ��ServiceContractVO
      final ServiceContractVO serviceContractVO = new ServiceContractVO();
      // ����ServiceContractVO
      BeanUtils.copyProperties( serviceContractTempVO, serviceContractVO );
      serviceContractVO.setContractTempId( serviceContractTempVO.getContractId() );
      serviceContractVO.setCreateBy( userId );
      serviceContractVO.setCreateDate( new Date() );
      serviceContractVO.setModifyBy( userId );
      serviceContractVO.setModifyDate( new Date() );

      // ����ServiceContractVO
      this.serviceContractDao.insertServiceContract( serviceContractVO );

      // ���Ŀ��ڱ��籣���̱����籣������¼״̬Ϊ���ѽ��㡱
      updateSettlementStatus( serviceContractTempVO.getContractId(), userId );

      // ��ȡ����Э���ӦOrderDetailTempVO�б�
      final List< Object > orderDetailTempVOs = this.orderDetailTempDao.getOrderDetailTempVOsByContractId( serviceContractTempVO.getContractId() );

      if ( orderDetailTempVOs != null && orderDetailTempVOs.size() > 0 )
      {
         // ����
         for ( Object object : orderDetailTempVOs )
         {
            final OrderDetailTempVO tempOrderDetailTempVO = ( OrderDetailTempVO ) object;
            tempOrderDetailTempVO.setContractId( serviceContractVO.getContractId() );

            // �ύOrderDetailTempVO
            submitOrderDetail( tempOrderDetailTempVO, userId );
         }
      }

      // ɾ��OrderHeaderTempVO
      this.serviceContractTempDao.deleteServiceContractTemp( serviceContractTempVO.getContractId() );
      
      logger.info( "Submit Contract End - " + serviceContractTempVO.getEmployeeContractId() + " / " + serviceContractTempVO.getEmployeeNameZH() );
   }

   // ���ݶ�����ϸ����
   // Reviewed by Kevin Jin at 2013-10-29
   private void submitOrderDetail( final OrderDetailTempVO orderDetailTempVO, final String userId ) throws KANException
   {
      logger.info( "Submit Order Detail Start - " + orderDetailTempVO.getDetailId() );
      
      // ��ʼ��OrderDetailVO
      final OrderDetailVO orderDetailVO = new OrderDetailVO();
      BeanUtils.copyProperties( orderDetailTempVO, orderDetailVO );
      orderDetailVO.setCreateBy( userId );
      orderDetailVO.setCreateDate( new Date() );
      orderDetailVO.setModifyBy( userId );
      orderDetailVO.setModifyDate( new Date() );

      // ����OrderDetailVO
      this.orderDetailDao.insertOrderDetail( orderDetailVO );

      // ɾ��orderDetailTempVO
      this.orderDetailTempDao.deleteOrderDetailTemp( orderDetailTempVO.getOrderDetailId() );
      
      logger.info( "Submit Order Detail End - " + orderDetailTempVO.getDetailId() );
   }

   // Prepare Batch
   // Reviewed by Kevin Jin at 2013-10-29
   private void prepareBatch( final OrderHeaderTempVO orderHeaderTempVO, final String userId ) throws KANException
   {
      // ��ʼ��BatchVO
      BatchVO batchVO = this.batchDao.getBatchVOByBatchTempId( orderHeaderTempVO.getBatchId() );
      if ( batchVO == null )
      {
         // ��ʼ��BatchTempVO
         final BatchTempVO batchTempVO = this.batchTempDao.getBatchTempVOByBatchId( orderHeaderTempVO.getBatchId() );

         batchVO = new BatchVO();
         // ����BatchVO
         BeanUtils.copyProperties( batchTempVO, batchVO );
         batchVO.setBatchTempId( batchTempVO.getBatchId() );
         batchVO.setCreateBy( userId );
         batchVO.setCreateDate( new Date() );
         batchVO.setModifyBy( userId );
         batchVO.setModifyDate( new Date() );

         // ����BatchVO
         this.batchDao.insertBatch( batchVO );
      }

      orderHeaderTempVO.setBatchId( batchVO.getBatchId() );
   }

   // Try Delete Batch Temp
   // Reviewed by Kevin Jin at 2013-10-29
   private void tryDeleteBatchTemp( final String batchId ) throws KANException
   {
      if ( batchId != null && !batchId.isEmpty() )
      {
         // ɾ��BatchTempVO
         this.batchTempDao.deleteBatchTemp( batchId );
      }
   }

   // Prepare Order Header
   // Reviewed by Kevin Jin at 2013-10-29
   private void prepareOrderHeader( final ServiceContractTempVO serviceContractTempVO, final String userId ) throws KANException
   {
      // ��ʼ��OrderHeaderVO
      OrderHeaderVO orderHeaderVO = this.orderHeaderDao.getOrderHeaderVOByOrderHeaderTempId( serviceContractTempVO.getOrderHeaderId() );

      if ( orderHeaderVO == null )
      {
         // ��ʼ��OrderHeaderTempVO
         final OrderHeaderTempVO orderHeaderTempVO = this.orderHeaderTempDao.getOrderHeaderTempVOByOrderHeaderId( serviceContractTempVO.getOrderHeaderId() );

         // Prepare Batch
         prepareBatch( orderHeaderTempVO, userId );

         orderHeaderVO = new OrderHeaderVO();
         // ����BatchVO
         BeanUtils.copyProperties( orderHeaderTempVO, orderHeaderVO );
         orderHeaderVO.setOrderHeaderTempId( orderHeaderTempVO.getOrderHeaderId() );
         orderHeaderVO.setCreateBy( userId );
         orderHeaderVO.setCreateDate( new Date() );
         orderHeaderVO.setModifyBy( userId );
         orderHeaderVO.setModifyDate( new Date() );

         // ����OrderHeaderVO
         this.orderHeaderDao.insertOrderHeader( orderHeaderVO );
      }

      serviceContractTempVO.setOrderHeaderId( orderHeaderVO.getOrderHeaderId() );
   }

   // Try Delete Order Header Temp
   // Reviewed by Kevin Jin at 2013-10-29
   private void tryDeleteOrderHeaderTemp( final String orderHeaderId ) throws KANException
   {
      if ( orderHeaderId != null && !orderHeaderId.isEmpty() )
      {
         // ��ʼ��OrderHeaderTempVO
         final OrderHeaderTempVO orderHeaderTempVO = this.orderHeaderTempDao.getOrderHeaderTempVOByOrderHeaderId( orderHeaderId );
         if ( orderHeaderTempVO != null )
         {
            final List< Object > orderHeaderTempVOs = this.orderHeaderTempDao.getOrderHeaderTempVOsByBatchId( orderHeaderTempVO.getBatchId() );

            if ( orderHeaderTempVOs != null && orderHeaderTempVOs.size() == 1 )
            {
               tryDeleteBatchTemp( orderHeaderTempVO.getBatchId() );
            }
         }

         // ɾ��OrderHeaderTempVO
         this.orderHeaderTempDao.deleteOrderHeaderTemp( orderHeaderId );
      }
   }

   // Prepare Service Contract
   // Reviewed by Kevin Jin at 2013-10-29
   private void prepareServiceContract( final OrderDetailTempVO orderDetailTempVO, final String userId ) throws KANException
   {
      // ��ʼ��ServiceContractVO
      ServiceContractVO serviceContractVO = this.serviceContractDao.getServiceContractVOByContractTempId( orderDetailTempVO.getContractId() );

      if ( serviceContractVO == null )
      {
         // ��ʼ��ServiceContractTempVO
         final ServiceContractTempVO serviceContractTempVO = this.serviceContractTempDao.getServiceContractTempVOByContractId( orderDetailTempVO.getContractId() );

         // Prepare Batch
         prepareOrderHeader( serviceContractTempVO, userId );

         serviceContractVO = new ServiceContractVO();
         // ����BatchVO
         BeanUtils.copyProperties( serviceContractTempVO, serviceContractVO );
         serviceContractVO.setContractTempId( serviceContractTempVO.getContractId() );
         serviceContractVO.setCreateBy( userId );
         serviceContractVO.setCreateDate( new Date() );
         serviceContractVO.setModifyBy( userId );
         serviceContractVO.setModifyDate( new Date() );

         // ����OrderHeaderVO
         this.serviceContractDao.insertServiceContract( serviceContractVO );
      }

      orderDetailTempVO.setContractId( serviceContractVO.getContractId() );
   }

   // Try Delete Service Contract Temp
   // Reviewed by Kevin Jin at 2013-10-29
   private void tryDeleteServiceContractTemp( final String contractId ) throws KANException
   {
      if ( contractId != null && !contractId.isEmpty() )
      {
         // ��ʼ��ServiceContractTempVO
         final ServiceContractTempVO serviceContractTempVO = this.serviceContractTempDao.getServiceContractTempVOByContractId( contractId );
         if ( serviceContractTempVO != null )
         {
            final List< Object > serviceContractTempVOs = this.serviceContractTempDao.getServiceContractTempVOsByOrderHeaderId( serviceContractTempVO.getOrderHeaderId() );

            if ( serviceContractTempVOs != null && serviceContractTempVOs.size() == 1 )
            {
               tryDeleteOrderHeaderTemp( serviceContractTempVO.getOrderHeaderId() );
            }
         }

         // ɾ��OrderHeaderTempVO
         this.serviceContractTempDao.deleteServiceContractTemp( contractId );
      }
   }

   // Reviewed by Kevin Jin at 2013-10-30
   private void recalculatePostAmount( final String batchId ) throws KANException
   {
      final BatchVO batchVO = this.batchDao.getBatchVOByBatchTempId( batchId );

      if ( batchVO != null )
      {
         final List< Object > orderHeaderVOs = this.orderHeaderDao.getOrderHeaderVOsByBatchId( batchVO.getBatchId() );

         if ( orderHeaderVOs != null && orderHeaderVOs.size() > 0 )
         {
            for ( Object orderHeaderObject : orderHeaderVOs )
            {
               // Order Header Amount
               double billAmountCompany_OrderHeader = 0;
               double billAmountPersonal_OrderHeader = 0;
               double costAmountCompany_OrderHeader = 0;
               double costAmountPersonal_OrderHeader = 0;

               final OrderHeaderVO orderHeaderVO = ( OrderHeaderVO ) orderHeaderObject;

               final List< Object > serviceContractVOs = this.serviceContractDao.getServiceContractVOsByOrderHeaderId( orderHeaderVO.getOrderHeaderId() );

               if ( serviceContractVOs != null && serviceContractVOs.size() > 0 )
               {
                  for ( Object serviceContractObject : serviceContractVOs )
                  {
                     // Contract Service Amount
                     double billAmountCompany_Contract = 0;
                     double billAmountPersonal_Contract = 0;
                     double costAmountCompany_Contract = 0;
                     double costAmountPersonal_Contract = 0;

                     final ServiceContractVO serviceContractVO = ( ServiceContractVO ) serviceContractObject;

                     final List< Object > orderDetailVOs = this.orderDetailDao.getOrderDetailVOsByContractId( serviceContractVO.getContractId() );

                     if ( orderDetailVOs != null && orderDetailVOs.size() > 0 )
                     {
                        for ( Object orderDetailObject : orderDetailVOs )
                        {
                           final OrderDetailVO orderDetailVO = ( OrderDetailVO ) orderDetailObject;

                           if ( orderDetailVO.getBillAmountCompany() != null && !orderDetailVO.getBillAmountCompany().isEmpty() )
                           {
                              billAmountCompany_Contract = billAmountCompany_Contract + Double.valueOf( orderDetailVO.getBillAmountCompany() );
                           }

                           if ( orderDetailVO.getBillAmountPersonal() != null && !orderDetailVO.getBillAmountPersonal().isEmpty() )
                           {
                              billAmountPersonal_Contract = billAmountPersonal_Contract + Double.valueOf( orderDetailVO.getBillAmountPersonal() );
                           }

                           if ( orderDetailVO.getCostAmountCompany() != null && !orderDetailVO.getCostAmountCompany().isEmpty() )
                           {
                              costAmountCompany_Contract = costAmountCompany_Contract + Double.valueOf( orderDetailVO.getCostAmountCompany() );
                           }

                           if ( orderDetailVO.getCostAmountPersonal() != null && !orderDetailVO.getCostAmountPersonal().isEmpty() )
                           {
                              costAmountPersonal_Contract = costAmountPersonal_Contract + Double.valueOf( orderDetailVO.getCostAmountPersonal() );
                           }
                        }
                     }

                     billAmountCompany_OrderHeader = billAmountCompany_OrderHeader + billAmountCompany_Contract;
                     billAmountPersonal_OrderHeader = billAmountPersonal_OrderHeader + billAmountPersonal_Contract;
                     costAmountCompany_OrderHeader = costAmountCompany_OrderHeader + costAmountCompany_Contract;
                     costAmountPersonal_OrderHeader = costAmountPersonal_OrderHeader + costAmountPersonal_Contract;

                     serviceContractVO.setBillAmountCompany( String.valueOf( billAmountCompany_Contract ) );
                     serviceContractVO.setBillAmountPersonal( String.valueOf( billAmountPersonal_Contract ) );
                     serviceContractVO.setCostAmountCompany( String.valueOf( costAmountCompany_Contract ) );
                     serviceContractVO.setCostAmountPersonal( String.valueOf( costAmountPersonal_Contract ) );
                     this.serviceContractDao.updateServiceContract( serviceContractVO );
                  }
               }

               orderHeaderVO.setBillAmountCompany( String.valueOf( billAmountCompany_OrderHeader ) );
               orderHeaderVO.setBillAmountPersonal( String.valueOf( billAmountPersonal_OrderHeader ) );
               orderHeaderVO.setCostAmountCompany( String.valueOf( costAmountCompany_OrderHeader ) );
               orderHeaderVO.setCostAmountPersonal( String.valueOf( costAmountPersonal_OrderHeader ) );
               this.orderHeaderDao.updateOrderHeader( orderHeaderVO );
            }
         }
      }
   }

   // Reviewed by Kevin Jin at 2013-10-30
   private void recalculateUnPostAmount( final String batchId ) throws KANException
   {
      final BatchTempVO batchTempVO = this.getBatchTempDao().getBatchTempVOByBatchId( batchId );

      if ( batchTempVO != null )
      {
         final List< Object > orderHeaderTempVOs = this.getOrderHeaderTempDao().getOrderHeaderTempVOsByBatchId( batchTempVO.getBatchId() );

         if ( orderHeaderTempVOs != null && orderHeaderTempVOs.size() > 0 )
         {
            for ( Object orderHeaderTempObject : orderHeaderTempVOs )
            {
               // Order Header Amount
               double billAmountCompany_OrderHeader = 0;
               double billAmountPersonal_OrderHeader = 0;
               double costAmountCompany_OrderHeader = 0;
               double costAmountPersonal_OrderHeader = 0;

               final OrderHeaderTempVO orderHeaderTempVO = ( OrderHeaderTempVO ) orderHeaderTempObject;

               final List< Object > serviceContractTempVOs = this.serviceContractTempDao.getServiceContractTempVOsByOrderHeaderId( orderHeaderTempVO.getOrderHeaderId() );

               if ( serviceContractTempVOs != null && serviceContractTempVOs.size() > 0 )
               {
                  for ( Object serviceContractTempObject : serviceContractTempVOs )
                  {
                     // Contract Service Amount
                     double billAmountCompany_Contract = 0;
                     double billAmountPersonal_Contract = 0;
                     double costAmountCompany_Contract = 0;
                     double costAmountPersonal_Contract = 0;

                     final ServiceContractTempVO serviceContractTempVO = ( ServiceContractTempVO ) serviceContractTempObject;

                     final List< Object > orderDetailTempVOs = this.orderDetailTempDao.getOrderDetailTempVOsByContractId( serviceContractTempVO.getContractId() );

                     if ( orderDetailTempVOs != null && orderDetailTempVOs.size() > 0 )
                     {
                        for ( Object orderDetailTempObject : orderDetailTempVOs )
                        {
                           final OrderDetailTempVO orderDetailTempVO = ( OrderDetailTempVO ) orderDetailTempObject;

                           if ( orderDetailTempVO.getBillAmountCompany() != null && !orderDetailTempVO.getBillAmountCompany().isEmpty() )
                           {
                              billAmountCompany_Contract = billAmountCompany_Contract + Double.valueOf( orderDetailTempVO.getBillAmountCompany() );
                           }

                           if ( orderDetailTempVO.getBillAmountPersonal() != null && !orderDetailTempVO.getBillAmountPersonal().isEmpty() )
                           {
                              billAmountPersonal_Contract = billAmountPersonal_Contract + Double.valueOf( orderDetailTempVO.getBillAmountPersonal() );
                           }

                           if ( orderDetailTempVO.getCostAmountCompany() != null && !orderDetailTempVO.getCostAmountCompany().isEmpty() )
                           {
                              costAmountCompany_Contract = costAmountCompany_Contract + Double.valueOf( orderDetailTempVO.getCostAmountCompany() );
                           }

                           if ( orderDetailTempVO.getCostAmountPersonal() != null && !orderDetailTempVO.getCostAmountPersonal().isEmpty() )
                           {
                              costAmountPersonal_Contract = costAmountPersonal_Contract + Double.valueOf( orderDetailTempVO.getCostAmountPersonal() );
                           }
                        }
                     }

                     billAmountCompany_OrderHeader = billAmountCompany_OrderHeader + billAmountCompany_Contract;
                     billAmountPersonal_OrderHeader = billAmountPersonal_OrderHeader + billAmountPersonal_Contract;
                     costAmountCompany_OrderHeader = costAmountCompany_OrderHeader + costAmountCompany_Contract;
                     costAmountPersonal_OrderHeader = costAmountPersonal_OrderHeader + costAmountPersonal_Contract;

                     serviceContractTempVO.setBillAmountCompany( String.valueOf( billAmountCompany_Contract ) );
                     serviceContractTempVO.setBillAmountPersonal( String.valueOf( billAmountPersonal_Contract ) );
                     serviceContractTempVO.setCostAmountCompany( String.valueOf( costAmountCompany_Contract ) );
                     serviceContractTempVO.setCostAmountPersonal( String.valueOf( costAmountPersonal_Contract ) );
                     this.serviceContractTempDao.updateServiceContractTemp( serviceContractTempVO );
                  }
               }

               orderHeaderTempVO.setBillAmountCompany( String.valueOf( billAmountCompany_OrderHeader ) );
               orderHeaderTempVO.setBillAmountPersonal( String.valueOf( billAmountPersonal_OrderHeader ) );
               orderHeaderTempVO.setCostAmountCompany( String.valueOf( costAmountCompany_OrderHeader ) );
               orderHeaderTempVO.setCostAmountPersonal( String.valueOf( costAmountPersonal_OrderHeader ) );
               this.orderHeaderTempDao.updateOrderHeaderTemp( orderHeaderTempVO );
            }
         }
      }
   }

   // Reviewed by Kevin Jin at 2013-10-30
   private void updateSettlementStatus( final String contractId, final String userId ) throws KANException
   {
      final ServiceContractTempVO serviceContractTempVO = this.getServiceContractTempDao().getServiceContractTempVOByContractId( contractId );

      if ( serviceContractTempVO == null )
      {
         return;
      }

      // ��ȡOrderHeaderTempVO
      final OrderHeaderTempVO orderHeaderTempVO = this.getOrderHeaderTempDao().getOrderHeaderTempVOByOrderHeaderId( serviceContractTempVO.getOrderHeaderId() );

      if ( orderHeaderTempVO != null )
      {
         // ��ȡBatchTempVO
         final BatchTempVO batchTempVO = this.getBatchTempDao().getBatchTempVOByBatchId( orderHeaderTempVO.getBatchId() );

         if ( batchTempVO != null )
         {
            // ���ڱ�״̬����
            if ( batchTempVO.getContainSalary() != null && batchTempVO.getContainSalary().trim().equals( "1" ) )
            {
               // ��ʼ��TimesheetHeaderVO
               final TimesheetHeaderVO timesheetHeaderVO = this.getTimesheetHeaderDao().getTimesheetHeaderVOByHeaderId( serviceContractTempVO.getTimesheetId() );

               // ����TimesheetΪ���ѽ��㡱״̬ 
               if ( timesheetHeaderVO != null )
               {
                  timesheetHeaderVO.setStatus( "5" );
                  timesheetHeaderVO.setModifyBy( userId );
                  timesheetHeaderVO.setModifyDate( new Date() );
                  this.getTimesheetHeaderDao().updateTimesheetHeader( timesheetHeaderVO );

                  // ��ȡTimesheetHeaderVO�б�
                  final List< Object > timesheetHeaderVOs = this.getTimesheetHeaderDao().getTimesheetHeaderVOsByBatchId( timesheetHeaderVO.getBatchId() );

                  // ��ʼ����� - �Ƿ��ѽ���
                  boolean settled = true;

                  if ( timesheetHeaderVOs != null && timesheetHeaderVOs.size() > 0 )
                  {
                     for ( Object timesheetHeaderVOObject : timesheetHeaderVOs )
                     {
                        final TimesheetHeaderVO tempTimesheetHeaderVO = ( TimesheetHeaderVO ) timesheetHeaderVOObject;

                        if ( KANUtil.filterEmpty( tempTimesheetHeaderVO.getStatus() ) == null || !KANUtil.filterEmpty( tempTimesheetHeaderVO.getStatus() ).equals( "5" ) )
                        {
                           settled = false;

                           break;
                        }
                     }
                  }

                  // ����Timessheet Batch��״̬
                  if ( settled )
                  {
                     // ��ʼ��TimesheetBatchVO
                     final TimesheetBatchVO timesheetBatchVO = this.getTimesheetBatchDao().getTimesheetBatchVOByBatchId( timesheetHeaderVO.getBatchId() );

                     // ����Timesheet BatchΪ�ѽ���
                     timesheetBatchVO.setStatus( "5" );

                     // ����
                     this.getTimesheetBatchDao().updateTimesheetBatch( timesheetBatchVO );
                  }
               }
            }

            // ��ȡOrderDetailTempVO List
            final List< Object > orderDetailTempVOs = this.getOrderDetailTempDao().getOrderDetailTempVOsByContractId( serviceContractTempVO.getContractId() );

            if ( orderDetailTempVOs != null && orderDetailTempVOs.size() > 0 )
            {
               // ��ʼ��
               final List< String > sbBatchIds = new ArrayList< String >();
               final List< String > cbBatchIds = new ArrayList< String >();
               final List< String > sbAdjustmentHeaderIds = new ArrayList< String >();

               int sbDetailIndex = 0;
               int cbDetailIndex = 0;

               for ( Object object : orderDetailTempVOs )
               {
                  final OrderDetailTempVO orderDetailTempVO = ( OrderDetailTempVO ) object;

                  if ( orderDetailTempVO != null && orderDetailTempVO.getDetailType() != null && !orderDetailTempVO.getDetailType().isEmpty() )
                  {
                     // �籣״̬����
                     if ( orderDetailTempVO.getDetailType().trim().equals( "1" ) )
                     {
                        // ���κ��籣
                        if ( batchTempVO.getContainSB() != null && batchTempVO.getContainSB().trim().equals( "1" ) )
                        {
                           final SBDetailVO sbDetailVO = this.getSbDetailDao().getSBDetailVOByDetailId( orderDetailTempVO.getDetailId() );
                           // ״̬��5���ѽ���
                           sbDetailVO.setStatus( "5" );
                           sbDetailVO.setModifyBy( userId );
                           sbDetailVO.setModifyDate( new Date() );
                           this.getSbDetailDao().updateSBDetail( sbDetailVO );

                           final SBHeaderVO sbHeaderVO = this.getSbHeaderDao().getSBHeaderVOByHeaderId( sbDetailVO.getHeaderId() );

                           if ( sbHeaderVO != null && !sbBatchIds.contains( sbHeaderVO.getBatchId() ) )
                           {
                              sbBatchIds.add( sbHeaderVO.getBatchId() );
                           }

                           sbDetailIndex++;
                        }
                     }
                     // �̱�״̬����
                     else if ( orderDetailTempVO.getDetailType().trim().equals( "2" ) )
                     {
                        // ���κ��̱�
                        if ( batchTempVO.getContainCB() != null && batchTempVO.getContainCB().trim().equals( "1" ) )
                        {
                           final CBDetailVO cbDetailVO = this.getCbDetailDao().getCBDetailVOByDetailId( orderDetailTempVO.getDetailId() );
                           // ״̬��5���ѽ���
                           cbDetailVO.setStatus( "5" );
                           cbDetailVO.setModifyBy( userId );
                           cbDetailVO.setModifyDate( new Date() );
                           this.getCbDetailDao().updateCBDetail( cbDetailVO );

                           final CBHeaderVO cbHeaderVO = this.getCbHeaderDao().getCBHeaderVOByHeaderId( cbDetailVO.getHeaderId() );

                           if ( cbHeaderVO != null && !cbBatchIds.contains( cbHeaderVO.getBatchId() ) )
                           {
                              cbBatchIds.add( cbHeaderVO.getBatchId() );
                           }

                           cbDetailIndex++;
                        }
                     }
                     // �籣����״̬����
                     else if ( orderDetailTempVO.getDetailType().trim().equals( "3" ) )
                     {
                        // ���κ��籣
                        if ( batchTempVO.getContainSB() != null && batchTempVO.getContainSB().trim().equals( "1" ) )
                        {
                           final SBAdjustmentDetailVO sbAdjustmentDetailVO = this.getSbAdjustmentDetailDao().getSBAdjustmentDetailVOByAdjustmentDetailId( orderDetailTempVO.getDetailId() );

                           if ( !sbAdjustmentHeaderIds.contains( sbAdjustmentDetailVO.getAdjustmentHeaderId() ) )
                           {
                              sbAdjustmentHeaderIds.add( sbAdjustmentDetailVO.getAdjustmentHeaderId() );
                           }
                        }
                     }
                     // ���ʵ���״̬����
                     else if ( orderDetailTempVO.getDetailType().trim().equals( "4" ) || orderDetailTempVO.getDetailType().trim().equals( "5" ) )
                     {
                        final List< String > salaryHeaderIds = KANUtil.jasonArrayToStringList( orderDetailTempVO.getHeaderId() );
                        final String salaryDetailId = orderDetailTempVO.getDetailId();

                        // ��������ID��Ϊ�յ����
                        if ( salaryHeaderIds != null && salaryHeaderIds.size() > 0 )
                        {
                           for ( String salaryHeaderId : salaryHeaderIds )
                           {
                              final SalaryHeaderVO salaryHeaderVO = this.getSalaryHeaderDao().getSalaryHeaderVOByHeaderId( salaryHeaderId );

                              if ( KANUtil.filterEmpty( salaryHeaderVO.getStatus() ) != null && KANUtil.filterEmpty( salaryHeaderVO.getStatus() ).equals( "2" ) )
                              {
                                 salaryHeaderVO.setStatus( "3" );
                                 salaryHeaderVO.setModifyBy( userId );
                                 salaryHeaderVO.setModifyDate( new Date() );
                                 this.getSalaryHeaderDao().updateSalaryHeader( salaryHeaderVO );

                                 final List< Object > salaryDetailVOs = this.getSalaryDetailDao().getSalaryDetailVOsByHeaderId( salaryHeaderVO.getSalaryHeaderId() );

                                 if ( salaryDetailVOs != null && salaryDetailVOs.size() > 0 )
                                 {
                                    for ( Object salaryDetaillVOObject : salaryDetailVOs )
                                    {
                                       final SalaryDetailVO tempSalaryDetailVO = ( SalaryDetailVO ) salaryDetaillVOObject;
                                       tempSalaryDetailVO.setStatus( "3" );
                                       tempSalaryDetailVO.setModifyBy( userId );
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
                           salaryDetailVO.setStatus( "3" );
                           salaryDetailVO.setModifyBy( userId );
                           salaryDetailVO.setModifyDate( new Date() );
                           this.getSalaryDetailDao().updateSalaryDetail( salaryDetailVO );

                           // ����ж�Salary Detail�Ƿ���ȫ���ύ
                           boolean submited = true;
                           final List< Object > salaryDetailVOs = this.getSalaryDetailDao().getSalaryDetailVOsByHeaderId( salaryDetailVO.getSalaryHeaderId() );

                           if ( salaryDetailVOs != null && salaryDetailVOs.size() > 0 )
                           {
                              for ( Object salaryDetaillVOObject : salaryDetailVOs )
                              {
                                 final SalaryDetailVO tempSalaryDetailVO = ( SalaryDetailVO ) salaryDetaillVOObject;

                                 if ( KANUtil.filterEmpty( tempSalaryDetailVO.getStatus() ) != null && !KANUtil.filterEmpty( tempSalaryDetailVO.getStatus() ).equals( "3" ) )
                                 {
                                    submited = false;
                                 }
                              }
                           }

                           if ( submited )
                           {
                              final SalaryHeaderVO salaryHeaderVO = this.getSalaryHeaderDao().getSalaryHeaderVOByHeaderId( salaryDetailVO.getSalaryHeaderId() );

                              if ( KANUtil.filterEmpty( salaryHeaderVO.getStatus() ) != null && KANUtil.filterEmpty( salaryHeaderVO.getStatus() ).equals( "2" ) )
                              {
                                 salaryHeaderVO.setStatus( "3" );
                                 salaryHeaderVO.setModifyBy( userId );
                                 salaryHeaderVO.setModifyDate( new Date() );
                                 this.getSalaryHeaderDao().updateSalaryHeader( salaryHeaderVO );
                              }
                           }
                        }
                     }
                  }
               }

               /**
                *  �����籣����״̬
                */
               if ( sbBatchIds != null && sbBatchIds.size() > 0 )
               {
                  for ( String sbBatchId : sbBatchIds )
                  {
                     final SBBatchVO sbBatchVO = this.getSbBatchDao().getSBBatchVOByBatchId( sbBatchId );

                     // ��ʼ��SBHeaderVO�б�
                     final List< Object > sbHeaderVOs = this.getSbHeaderDao().getSBHeaderVOsByBatchId( sbBatchId );

                     if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
                     {
                        int sbHeaderIndex = 0;
                        int closedHeader = 0;

                        for ( Object sbHeaderVOObject : sbHeaderVOs )
                        {
                           final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) sbHeaderVOObject;

                           if ( sbHeaderVO != null && sbHeaderVO.getStatus() != null )
                           {
                              if ( sbHeaderVO.getStatus().trim().equals( "5" ) )
                              {
                                 closedHeader++;
                              }
                              else
                              {
                                 int closedDetail = 0;
                                 // ��ʼ��SBDetailVO�б�
                                 final List< Object > sbDetailVOs = this.getSbDetailDao().getSBDetailVOsByHeaderId( sbHeaderVO );

                                 if ( sbDetailVOs != null && sbDetailVOs.size() > 0 )
                                 {
                                    for ( Object sbDetailVOObject : sbDetailVOs )
                                    {
                                       final SBDetailVO sbDetailVO = ( SBDetailVO ) sbDetailVOObject;

                                       if ( sbDetailVO != null && sbDetailVO.getStatus() != null && sbDetailVO.getStatus().trim().equals( "5" ) )
                                       {
                                          closedDetail++;
                                       }
                                    }

                                    if ( sbDetailVOs.size() <= closedDetail + sbDetailIndex )
                                    {
                                       sbHeaderVO.setStatus( "5" );
                                       sbHeaderVO.setModifyBy( userId );
                                       sbHeaderVO.setModifyDate( new Date() );
                                       this.getSbHeaderDao().updateSBHeader( sbHeaderVO );

                                       sbHeaderIndex++;
                                    }
                                 }
                              }
                           }
                        }

                        if ( sbHeaderVOs.size() <= closedHeader + sbHeaderIndex )
                        {
                           sbBatchVO.setStatus( "5" );
                           sbBatchVO.setModifyBy( userId );
                           sbBatchVO.setModifyDate( new Date() );
                           this.getSbBatchDao().updateSBBatch( sbBatchVO );
                        }
                     }
                  }
               }

               /**
                *  �����̱�����״̬
                */
               if ( cbBatchIds != null && cbBatchIds.size() > 0 )
               {
                  for ( String cbBatchId : cbBatchIds )
                  {
                     final CBBatchVO cbBatchVO = this.getCbBatchDao().getCBBatchVOByBatchId( cbBatchId );

                     // ��ʼ��CBHeaderVO�б�
                     final List< Object > cbHeaderVOs = this.getCbHeaderDao().getCBHeaderVOsByBatchId( cbBatchId );

                     if ( cbHeaderVOs != null && cbHeaderVOs.size() > 0 )
                     {
                        int cbHeaderIndex = 0;
                        int closedHeader = 0;

                        for ( Object cbHeaderVOObject : cbHeaderVOs )
                        {
                           final CBHeaderVO cbHeaderVO = ( CBHeaderVO ) cbHeaderVOObject;

                           if ( cbHeaderVO != null && cbHeaderVO.getStatus() != null )
                           {
                              if ( cbHeaderVO.getStatus().trim().equals( "5" ) )
                              {
                                 closedHeader++;
                              }
                              else
                              {
                                 int closedDetail = 0;
                                 // ��ʼ��CBDetailVO�б�
                                 final List< Object > cbDetailVOs = this.getCbDetailDao().getCBDetailVOsByHeaderId( cbHeaderVO.getHeaderId() );

                                 if ( cbDetailVOs != null && cbDetailVOs.size() > 0 )
                                 {
                                    for ( Object cbDetailVOObject : cbDetailVOs )
                                    {
                                       final CBDetailVO cbDetailVO = ( CBDetailVO ) cbDetailVOObject;

                                       if ( cbDetailVO != null && cbDetailVO.getStatus() != null && cbDetailVO.getStatus().trim().equals( "5" ) )
                                       {
                                          closedDetail++;
                                       }
                                    }

                                    if ( cbDetailVOs.size() <= closedDetail + cbDetailIndex )
                                    {
                                       cbHeaderVO.setStatus( "5" );
                                       cbHeaderVO.setModifyBy( userId );
                                       cbHeaderVO.setModifyDate( new Date() );
                                       this.getCbHeaderDao().updateCBHeader( cbHeaderVO );

                                       cbHeaderIndex++;
                                    }
                                 }
                              }
                           }
                        }

                        if ( cbHeaderVOs.size() <= closedHeader + cbHeaderIndex )
                        {
                           cbBatchVO.setStatus( "5" );
                           cbBatchVO.setModifyBy( userId );
                           cbBatchVO.setModifyDate( new Date() );
                           this.getCbBatchDao().updateCBBatch( cbBatchVO );
                        }
                     }
                  }
               }

               /**
                *  �����籣��������״̬
                */
               if ( sbAdjustmentHeaderIds != null && sbAdjustmentHeaderIds.size() > 0 )
               {
                  for ( String adjustmentHeaderId : sbAdjustmentHeaderIds )
                  {
                     final SBAdjustmentHeaderVO adjustmentHeaderVO = this.getSbAdjustmentHeaderDao().getSBAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );
                     adjustmentHeaderVO.setStatus( "5" );
                     adjustmentHeaderVO.setModifyBy( userId );
                     adjustmentHeaderVO.setModifyDate( new Date() );
                     this.getSbAdjustmentHeaderDao().updateSBAdjustmentHeader( adjustmentHeaderVO );
                  }
               }
            }
         }
      }
   }
}

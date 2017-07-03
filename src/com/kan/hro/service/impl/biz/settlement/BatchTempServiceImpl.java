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
   // 注入BatchTempDao
   private BatchTempDao batchTempDao;

   // 注入OrderHeaderTempDao
   private OrderHeaderTempDao orderHeaderTempDao;

   // 注入ServiceContractTemp
   private ServiceContractTempDao serviceContractTempDao;

   // 注入OrderDetailTempDao
   private OrderDetailTempDao orderDetailTempDao;

   // 注入BatchDao
   private BatchDao batchDao;

   // 注入OrderHeaderDao
   private OrderHeaderDao orderHeaderDao;

   // 注入ServiceContract
   private ServiceContractDao serviceContractDao;

   // 注入OrderDetailDao
   private OrderDetailDao orderDetailDao;

   // 注入TimesheetBatchDao
   private TimesheetBatchDao timesheetBatchDao;

   // 注入TimesheetHeaderDao
   private TimesheetHeaderDao timesheetHeaderDao;

   // 注入SBHeaderDao
   private SBHeaderDao sbHeaderDao;

   // 注入SBDetailDao
   private SBDetailDao sbDetailDao;

   // 注入CBHeaderDao
   private CBHeaderDao cbHeaderDao;

   // 注入CBDetailDao
   private CBDetailDao cbDetailDao;

   // 注入SBAdjustmentHeaderDao
   private SBAdjustmentHeaderDao sbAdjustmentHeaderDao;

   // 注入SBAdjustmentDetailDao
   private SBAdjustmentDetailDao sbAdjustmentDetailDao;

   // 注入SBBatchDao
   private SBBatchDao sbBatchDao;

   // 注入CBBatchDao
   private CBBatchDao cbBatchDao;

   // 注入SalaryHeaderDao
   private SalaryHeaderDao salaryHeaderDao;

   // 注入SalaryDetailDao
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

      // 添加包含雇员人数
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
      // 初始化包含的雇员集合
      final List< MappingVO > employees = new ArrayList< MappingVO >();
      // 初始化查询对象
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
    * 判断雇员ID是否存在
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
         // 开启事务
         startTransaction();

         // 初始化勾选的ID数组
         final String selectedIds = batchTempVO.getSelectedIds();

         // 初始化PageFlag
         final String pageFlag = batchTempVO.getPageFlag() != null ? batchTempVO.getPageFlag().trim() : "";

         // 如果有选择项
         if ( selectedIds != null && !selectedIds.isEmpty() )
         {
            // Temp表的BatchId
            String batchId = null;
            // 初始化标识
            int orderHeaders = 0;
            int serviceContracts = 0;
            int index = 1;

            // 获得所选ID Array
            final String[] selectedIdArray = selectedIds.split( "," );

            // 遍历Id数组
            for ( String encodedSelectId : selectedIdArray )
            {
               // 解密
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );

               // 按批次Rollback
               if ( pageFlag.equalsIgnoreCase( BatchTempService.BATCH ) )
               {
                  batchId = selectId;
                  rollbackBatch( selectId );
               }
               else if ( pageFlag.equalsIgnoreCase( BatchTempService.HEADER ) )
               {
                  // 初始化OrderHeaderTempVO
                  final OrderHeaderTempVO orderHeaderTempVO = this.orderHeaderTempDao.getOrderHeaderTempVOByOrderHeaderId( selectId );

                  if ( orderHeaderTempVO != null )
                  {
                     final String tempBatchId = orderHeaderTempVO.getBatchId();
                     batchId = tempBatchId;

                     if ( orderHeaders == 0 )
                     {
                        // 获取OrderHeaderTempVO列表
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
                  // 初始化ServiceContractTempVO
                  final ServiceContractTempVO serviceContractTempVO = this.serviceContractTempDao.getServiceContractTempVOByContractId( selectId );

                  if ( serviceContractTempVO != null )
                  {
                     // 初始化OrderHeaderId - Temp
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
                        // 获取ServiceContractTempVO列表
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
                  // 初始化OrderDetailTempVO
                  final OrderDetailTempVO orderDetailTempVO = this.orderDetailTempDao.getOrderDetailTempVOByOrderDetailId( selectId );

                  if ( orderDetailTempVO != null )
                  {
                     // 初始化ContractId - Temp
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

                     // 获取OrderDetailTempVO列表
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

            // 重算OrderHeader和ServiceContract中的汇总值（仅针对Temp表处理）
            if ( batchId != null )
            {
               // 处理未Post的数据
               recalculateUnPostAmount( batchId );
            }
         }

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         rollbackTransaction();
         throw new KANException( e );
      }

      return rows;
   }

   /**  
    * Rollback Batch 
    *	退回批次
    *
    *	@param selectId
    *	@throws KANException
    */
   private void rollbackBatch( final String selectId ) throws KANException
   {
      // 获得OrderHeaderTempVO List
      final List< Object > orderHeaderTempVOs = this.orderHeaderTempDao.getOrderHeaderTempVOsByBatchId( selectId );

      if ( orderHeaderTempVOs != null && orderHeaderTempVOs.size() > 0 )
      {
         // 遍历
         for ( Object object : orderHeaderTempVOs )
         {
            final OrderHeaderTempVO orderHeaderTempVO = ( OrderHeaderTempVO ) object;

            rollbackOrderHeader( orderHeaderTempVO.getOrderHeaderId() );
         }
      }

      // 删除批次
      ( ( BatchTempDao ) getDao() ).deleteBatchTemp( selectId );
   }

   /**  
    * Rollback Order Header
    *	退回订单
    *
    *	@param selectId
    *	@throws KANException
    */
   private void rollbackOrderHeader( final String selectId ) throws KANException
   {
      // 获得ServiceContractTempVO List
      final List< Object > serviceContractTempVOs = this.serviceContractTempDao.getServiceContractTempVOsByOrderHeaderId( selectId );

      if ( serviceContractTempVOs != null && serviceContractTempVOs.size() > 0 )
      {
         // 遍历
         for ( Object object : serviceContractTempVOs )
         {
            final ServiceContractTempVO serviceContractTempVO = ( ServiceContractTempVO ) object;

            rollbackContract( serviceContractTempVO.getContractId() );
         }
      }

      // 删除订单主表
      this.orderHeaderTempDao.deleteOrderHeaderTemp( selectId );
   }

   /**  
    * Rollback Contract
    *	退回服务协议
    *
    *	@param selectId
    *	@throws KANException
    */
   private void rollbackContract( final String selectId ) throws KANException
   {
      // 获得OrderDetailTempVO List
      final List< Object > orderDetailTempVOs = this.orderDetailTempDao.getOrderDetailTempVOsByContractId( selectId );

      if ( orderDetailTempVOs != null && orderDetailTempVOs.size() > 0 )
      {
         // 遍历
         for ( Object object : orderDetailTempVOs )
         {
            final OrderDetailTempVO orderDetailTempVO = ( OrderDetailTempVO ) object;

            // 删除订单从表
            this.orderDetailTempDao.deleteOrderDetailTemp( orderDetailTempVO.getOrderDetailId() );
         }
      }

      // 删除服务协议
      this.serviceContractTempDao.deleteServiceContractTemp( selectId );
   }

   /**  
    * Rollback Order Detail
    * 退回服务协议
    *
    * @param selectId
    * @throws KANException
    */
   private void rollbackOrderDetail( final String selectId ) throws KANException
   {
      // 删除订单从表
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
         // 开启事务
         startTransaction();

         rows = insertBatchTemp_nt( batchTempVO, clientOrderDTOs );

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
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
            // 获得批次对应的 SettlementDTO集合
            final List< SettlementTempDTO > settlementTempDTOs = clientOrderDTO.getSettlementTempDTOs();
            // 遍历
            if ( settlementTempDTOs != null && settlementTempDTOs.size() > 0 )
            {
               if ( rows == 0 )
               {
                  // 插入批次
                  ( ( BatchTempDao ) getDao() ).insertBatchTemp( batchTempVO );

                  // 一个批次添加成功
                  rows = 1;
               }

               // 插入OrderHeaderVO
               final OrderHeaderTempVO orderHeaderTempVO = clientOrderDTO.getOrderHeaderTempVO();
               // 设置OrderHeaderTempVO的BatchId
               orderHeaderTempVO.setBatchId( batchTempVO.getBatchId() );
               // 插入OrderHeaderTempVO
               this.getOrderHeaderTempDao().insertOrderHeaderTemp( orderHeaderTempVO );

               for ( SettlementTempDTO settlementTempDTO : settlementTempDTOs )
               {
                  // 获得SettlementDTO 对应的 ServiceContractTempVO 和 OrderDetailTempVO集合
                  final ServiceContractTempVO serviceContractTempVO = settlementTempDTO.getServiceContractTempVO();
                  // 设置ServiceContractTempVO的OrderHeaderId
                  serviceContractTempVO.setOrderHeaderId( orderHeaderTempVO.getOrderHeaderId() );
                  // 插入ServiceContractTempVO
                  this.getServiceContractTempDao().insertServiceContractTemp( serviceContractTempVO );

                  // 获得服务协议对应的科目集合
                  final List< OrderDetailTempVO > orderDetailTempVOs = settlementTempDTO.getOrderDetailTempVOs();
                  // 遍历
                  if ( orderDetailTempVOs != null && orderDetailTempVOs.size() > 0 )
                  {
                     for ( OrderDetailTempVO orderDetailTempVO : orderDetailTempVOs )
                     {
                        // 设置OrderDetailTempVO对应的contractId
                        orderDetailTempVO.setContractId( serviceContractTempVO.getContractId() );
                        this.getOrderDetailTempDao().insertOrderDetailTemp( orderDetailTempVO );
                     }
                  }
               }
            }
         }

         if ( KANUtil.filterEmpty( batchTempVO.getBatchId() ) != null )
         {
            // 批次执行结束时间
            batchTempVO.setEndDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd HH:mm:ss" ) );
            batchTempVO.setDeleted( BatchTempVO.TRUE );
            batchTempVO.setStatus( BatchTempVO.TRUE );
            // 修改批次
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
         // 开启事务
         startTransaction();

         rows = submitBatchTemp_nt( batchTempVO );

         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
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
         // 初始化勾选的ID数组
         final String selectedIds = batchTempVO.getSelectedIds();

         // 初始化PageFlag
         final String pageFlag = batchTempVO.getPageFlag() != null ? batchTempVO.getPageFlag().trim() : "";

         // 如果有选择项
         if ( selectedIds != null && !selectedIds.isEmpty() )
         {
            // 获得所选ID Array
            final String[] selectedIdArray = selectedIds.split( "," );

            // Temp表的BatchId
            String batchId = null;
            // 初始化标识
            int orderHeaders = 0;
            int serviceContracts = 0;
            int index = 1;

            // 遍历
            for ( String encodedSelectId : selectedIdArray )
            {
               rows = selectedIds.length();

               // 解密
               final String selectId = KANUtil.decodeStringFromAjax( encodedSelectId );

               // 按批次提交
               if ( pageFlag.equalsIgnoreCase( BatchTempService.BATCH ) )
               {
                  batchId = selectId;
                  submitBatch( selectId, batchTempVO.getModifyBy() );
               }
               // 按订单提交
               else if ( pageFlag.equalsIgnoreCase( BatchTempService.HEADER ) )
               {
                  // 初始化OrderHeaderTempVO
                  final OrderHeaderTempVO orderHeaderTempVO = this.getOrderHeaderTempDao().getOrderHeaderTempVOByOrderHeaderId( selectId );

                  if ( orderHeaderTempVO != null )
                  {
                     // 初始化BatchId - Temp
                     final String tempBatchId = orderHeaderTempVO.getBatchId();
                     batchId = tempBatchId;

                     if ( orderHeaders == 0 )
                     {
                        // 获取OrderHeaderTempVO列表
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
               // 按服务协议提交
               else if ( pageFlag.equalsIgnoreCase( BatchTempService.CONTRACT ) )
               {
                  // 初始化ServiceContractTempVO
                  final ServiceContractTempVO serviceContractTempVO = this.getServiceContractTempDao().getServiceContractTempVOByContractId( selectId );

                  if ( serviceContractTempVO != null )
                  {
                     // 初始化OrderHeaderId - Temp
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
                        // 获取ServiceContractTempVO列表
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
               // 按订单明细提交
               else if ( pageFlag.equalsIgnoreCase( BatchTempService.DETAIL ) )
               {
                  // 初始化OrderDetailTempVO
                  final OrderDetailTempVO orderDetailTempVO = this.getOrderDetailTempDao().getOrderDetailTempVOByOrderDetailId( selectId );

                  if ( orderDetailTempVO != null )
                  {
                     // 初始化ContractId - Temp
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

                     // 更改考勤表、社保、商保、社保调整记录状态为“已结算”
                     updateSettlementStatus( contractId, batchTempVO.getModifyBy() );

                     // Prepare Service Contract
                     prepareServiceContract( orderDetailTempVO, batchTempVO.getModifyBy() );

                     // 获取OrderDetailTempVO列表
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

            // 重算OrderHeader和ServiceContract中的汇总值（Temp表也需处理）
            if ( batchId != null )
            {
               // 处理Post掉的数据
               recalculatePostAmount( batchId );

               // 处理未Post的数据
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

   // 按批次提交
   // Reviewed by Kevin Jin at 2013-10-29
   private void submitBatch( final String selectId, final String userId ) throws KANException
   {
      // 获取主键对象
      final BatchTempVO batchTempVO = ( ( BatchTempDao ) getDao() ).getBatchTempVOByBatchId( selectId );

      // 初始化BatchVO
      final BatchVO batchVO = new BatchVO();
      // 复制BatchVO
      BeanUtils.copyProperties( batchTempVO, batchVO );
      batchVO.setBatchTempId( batchTempVO.getBatchId() );
      batchVO.setCreateBy( userId );
      batchVO.setCreateDate( new Date() );
      batchVO.setModifyBy( userId );
      batchVO.setModifyDate( new Date() );

      // 插入BatchVO
      this.batchDao.insertBatch( batchVO );

      // 获取批次对应OrderHeaderTempVO列表
      final List< Object > orderHeaderTempVOs = this.orderHeaderTempDao.getOrderHeaderTempVOsByBatchId( selectId );

      if ( orderHeaderTempVOs != null && orderHeaderTempVOs.size() > 0 )
      {
         // 遍历
         for ( Object object : orderHeaderTempVOs )
         {
            final OrderHeaderTempVO tempOrderHeaderTempVO = ( OrderHeaderTempVO ) object;
            tempOrderHeaderTempVO.setBatchId( batchVO.getBatchId() );

            // 提交OrderHeader
            submitOrderHeader( tempOrderHeaderTempVO, userId );
         }
      }

      // 删除BatchTempVO
      this.batchTempDao.deleteBatchTemp( batchTempVO.getBatchId() );
   }

   // 根据订单提交结算
   // Reviewed by Kevin Jin at 2013-10-29
   private void submitOrderHeader( final OrderHeaderTempVO orderHeaderTempVO, final String userId ) throws KANException
   {
      // 初始化OrderHeaderVO
      final OrderHeaderVO orderHeaderVO = new OrderHeaderVO();
      // 复制OrderHeaderVO
      BeanUtils.copyProperties( orderHeaderTempVO, orderHeaderVO );
      orderHeaderVO.setOrderHeaderTempId( orderHeaderTempVO.getOrderHeaderId() );
      orderHeaderVO.setCreateBy( userId );
      orderHeaderVO.setCreateDate( new Date() );
      orderHeaderVO.setModifyBy( userId );
      orderHeaderVO.setModifyDate( new Date() );

      // 插入OrderHeaderVO
      this.orderHeaderDao.insertOrderHeader( orderHeaderVO );

      // 获取订单对应ServiceContractTempVO列表
      final List< Object > serviceContractTempVOs = this.serviceContractTempDao.getServiceContractTempVOsByOrderHeaderId( orderHeaderTempVO.getOrderHeaderId() );

      if ( serviceContractTempVOs != null && serviceContractTempVOs.size() > 0 )
      {
         // 遍历
         for ( Object object : serviceContractTempVOs )
         {
            final ServiceContractTempVO tempServiceContractTempVO = ( ServiceContractTempVO ) object;
            tempServiceContractTempVO.setOrderHeaderId( orderHeaderVO.getOrderHeaderId() );

            // 提交ServiceContractTempVO
            submitContract( tempServiceContractTempVO, userId );
         }
      }

      // 删除OrderHeaderTempVO
      this.orderHeaderTempDao.deleteOrderHeaderTemp( orderHeaderTempVO.getOrderHeaderId() );
   }

   // 根据订单提交结算
   // Reviewed by Kevin Jin at 2013-10-29
   private void submitContract( final ServiceContractTempVO serviceContractTempVO, final String userId ) throws KANException
   {
      logger.info( "Submit Contract Start - " + serviceContractTempVO.getEmployeeContractId() + " / " + serviceContractTempVO.getEmployeeNameZH() );
      
      // 初始化ServiceContractVO
      final ServiceContractVO serviceContractVO = new ServiceContractVO();
      // 复制ServiceContractVO
      BeanUtils.copyProperties( serviceContractTempVO, serviceContractVO );
      serviceContractVO.setContractTempId( serviceContractTempVO.getContractId() );
      serviceContractVO.setCreateBy( userId );
      serviceContractVO.setCreateDate( new Date() );
      serviceContractVO.setModifyBy( userId );
      serviceContractVO.setModifyDate( new Date() );

      // 插入ServiceContractVO
      this.serviceContractDao.insertServiceContract( serviceContractVO );

      // 更改考勤表、社保、商保、社保调整记录状态为“已结算”
      updateSettlementStatus( serviceContractTempVO.getContractId(), userId );

      // 获取服务协议对应OrderDetailTempVO列表
      final List< Object > orderDetailTempVOs = this.orderDetailTempDao.getOrderDetailTempVOsByContractId( serviceContractTempVO.getContractId() );

      if ( orderDetailTempVOs != null && orderDetailTempVOs.size() > 0 )
      {
         // 遍历
         for ( Object object : orderDetailTempVOs )
         {
            final OrderDetailTempVO tempOrderDetailTempVO = ( OrderDetailTempVO ) object;
            tempOrderDetailTempVO.setContractId( serviceContractVO.getContractId() );

            // 提交OrderDetailTempVO
            submitOrderDetail( tempOrderDetailTempVO, userId );
         }
      }

      // 删除OrderHeaderTempVO
      this.serviceContractTempDao.deleteServiceContractTemp( serviceContractTempVO.getContractId() );
      
      logger.info( "Submit Contract End - " + serviceContractTempVO.getEmployeeContractId() + " / " + serviceContractTempVO.getEmployeeNameZH() );
   }

   // 根据订单明细结算
   // Reviewed by Kevin Jin at 2013-10-29
   private void submitOrderDetail( final OrderDetailTempVO orderDetailTempVO, final String userId ) throws KANException
   {
      logger.info( "Submit Order Detail Start - " + orderDetailTempVO.getDetailId() );
      
      // 初始化OrderDetailVO
      final OrderDetailVO orderDetailVO = new OrderDetailVO();
      BeanUtils.copyProperties( orderDetailTempVO, orderDetailVO );
      orderDetailVO.setCreateBy( userId );
      orderDetailVO.setCreateDate( new Date() );
      orderDetailVO.setModifyBy( userId );
      orderDetailVO.setModifyDate( new Date() );

      // 插入OrderDetailVO
      this.orderDetailDao.insertOrderDetail( orderDetailVO );

      // 删除orderDetailTempVO
      this.orderDetailTempDao.deleteOrderDetailTemp( orderDetailTempVO.getOrderDetailId() );
      
      logger.info( "Submit Order Detail End - " + orderDetailTempVO.getDetailId() );
   }

   // Prepare Batch
   // Reviewed by Kevin Jin at 2013-10-29
   private void prepareBatch( final OrderHeaderTempVO orderHeaderTempVO, final String userId ) throws KANException
   {
      // 初始化BatchVO
      BatchVO batchVO = this.batchDao.getBatchVOByBatchTempId( orderHeaderTempVO.getBatchId() );
      if ( batchVO == null )
      {
         // 初始化BatchTempVO
         final BatchTempVO batchTempVO = this.batchTempDao.getBatchTempVOByBatchId( orderHeaderTempVO.getBatchId() );

         batchVO = new BatchVO();
         // 复制BatchVO
         BeanUtils.copyProperties( batchTempVO, batchVO );
         batchVO.setBatchTempId( batchTempVO.getBatchId() );
         batchVO.setCreateBy( userId );
         batchVO.setCreateDate( new Date() );
         batchVO.setModifyBy( userId );
         batchVO.setModifyDate( new Date() );

         // 插入BatchVO
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
         // 删除BatchTempVO
         this.batchTempDao.deleteBatchTemp( batchId );
      }
   }

   // Prepare Order Header
   // Reviewed by Kevin Jin at 2013-10-29
   private void prepareOrderHeader( final ServiceContractTempVO serviceContractTempVO, final String userId ) throws KANException
   {
      // 初始化OrderHeaderVO
      OrderHeaderVO orderHeaderVO = this.orderHeaderDao.getOrderHeaderVOByOrderHeaderTempId( serviceContractTempVO.getOrderHeaderId() );

      if ( orderHeaderVO == null )
      {
         // 初始化OrderHeaderTempVO
         final OrderHeaderTempVO orderHeaderTempVO = this.orderHeaderTempDao.getOrderHeaderTempVOByOrderHeaderId( serviceContractTempVO.getOrderHeaderId() );

         // Prepare Batch
         prepareBatch( orderHeaderTempVO, userId );

         orderHeaderVO = new OrderHeaderVO();
         // 复制BatchVO
         BeanUtils.copyProperties( orderHeaderTempVO, orderHeaderVO );
         orderHeaderVO.setOrderHeaderTempId( orderHeaderTempVO.getOrderHeaderId() );
         orderHeaderVO.setCreateBy( userId );
         orderHeaderVO.setCreateDate( new Date() );
         orderHeaderVO.setModifyBy( userId );
         orderHeaderVO.setModifyDate( new Date() );

         // 插入OrderHeaderVO
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
         // 初始化OrderHeaderTempVO
         final OrderHeaderTempVO orderHeaderTempVO = this.orderHeaderTempDao.getOrderHeaderTempVOByOrderHeaderId( orderHeaderId );
         if ( orderHeaderTempVO != null )
         {
            final List< Object > orderHeaderTempVOs = this.orderHeaderTempDao.getOrderHeaderTempVOsByBatchId( orderHeaderTempVO.getBatchId() );

            if ( orderHeaderTempVOs != null && orderHeaderTempVOs.size() == 1 )
            {
               tryDeleteBatchTemp( orderHeaderTempVO.getBatchId() );
            }
         }

         // 删除OrderHeaderTempVO
         this.orderHeaderTempDao.deleteOrderHeaderTemp( orderHeaderId );
      }
   }

   // Prepare Service Contract
   // Reviewed by Kevin Jin at 2013-10-29
   private void prepareServiceContract( final OrderDetailTempVO orderDetailTempVO, final String userId ) throws KANException
   {
      // 初始化ServiceContractVO
      ServiceContractVO serviceContractVO = this.serviceContractDao.getServiceContractVOByContractTempId( orderDetailTempVO.getContractId() );

      if ( serviceContractVO == null )
      {
         // 初始化ServiceContractTempVO
         final ServiceContractTempVO serviceContractTempVO = this.serviceContractTempDao.getServiceContractTempVOByContractId( orderDetailTempVO.getContractId() );

         // Prepare Batch
         prepareOrderHeader( serviceContractTempVO, userId );

         serviceContractVO = new ServiceContractVO();
         // 复制BatchVO
         BeanUtils.copyProperties( serviceContractTempVO, serviceContractVO );
         serviceContractVO.setContractTempId( serviceContractTempVO.getContractId() );
         serviceContractVO.setCreateBy( userId );
         serviceContractVO.setCreateDate( new Date() );
         serviceContractVO.setModifyBy( userId );
         serviceContractVO.setModifyDate( new Date() );

         // 插入OrderHeaderVO
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
         // 初始化ServiceContractTempVO
         final ServiceContractTempVO serviceContractTempVO = this.serviceContractTempDao.getServiceContractTempVOByContractId( contractId );
         if ( serviceContractTempVO != null )
         {
            final List< Object > serviceContractTempVOs = this.serviceContractTempDao.getServiceContractTempVOsByOrderHeaderId( serviceContractTempVO.getOrderHeaderId() );

            if ( serviceContractTempVOs != null && serviceContractTempVOs.size() == 1 )
            {
               tryDeleteOrderHeaderTemp( serviceContractTempVO.getOrderHeaderId() );
            }
         }

         // 删除OrderHeaderTempVO
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

      // 获取OrderHeaderTempVO
      final OrderHeaderTempVO orderHeaderTempVO = this.getOrderHeaderTempDao().getOrderHeaderTempVOByOrderHeaderId( serviceContractTempVO.getOrderHeaderId() );

      if ( orderHeaderTempVO != null )
      {
         // 获取BatchTempVO
         final BatchTempVO batchTempVO = this.getBatchTempDao().getBatchTempVOByBatchId( orderHeaderTempVO.getBatchId() );

         if ( batchTempVO != null )
         {
            // 考勤表状态处理
            if ( batchTempVO.getContainSalary() != null && batchTempVO.getContainSalary().trim().equals( "1" ) )
            {
               // 初始化TimesheetHeaderVO
               final TimesheetHeaderVO timesheetHeaderVO = this.getTimesheetHeaderDao().getTimesheetHeaderVOByHeaderId( serviceContractTempVO.getTimesheetId() );

               // 设置Timesheet为“已结算”状态 
               if ( timesheetHeaderVO != null )
               {
                  timesheetHeaderVO.setStatus( "5" );
                  timesheetHeaderVO.setModifyBy( userId );
                  timesheetHeaderVO.setModifyDate( new Date() );
                  this.getTimesheetHeaderDao().updateTimesheetHeader( timesheetHeaderVO );

                  // 获取TimesheetHeaderVO列表
                  final List< Object > timesheetHeaderVOs = this.getTimesheetHeaderDao().getTimesheetHeaderVOsByBatchId( timesheetHeaderVO.getBatchId() );

                  // 初始化标记 - 是否已结算
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

                  // 更改Timessheet Batch的状态
                  if ( settled )
                  {
                     // 初始化TimesheetBatchVO
                     final TimesheetBatchVO timesheetBatchVO = this.getTimesheetBatchDao().getTimesheetBatchVOByBatchId( timesheetHeaderVO.getBatchId() );

                     // 设置Timesheet Batch为已结算
                     timesheetBatchVO.setStatus( "5" );

                     // 更改
                     this.getTimesheetBatchDao().updateTimesheetBatch( timesheetBatchVO );
                  }
               }
            }

            // 获取OrderDetailTempVO List
            final List< Object > orderDetailTempVOs = this.getOrderDetailTempDao().getOrderDetailTempVOsByContractId( serviceContractTempVO.getContractId() );

            if ( orderDetailTempVOs != null && orderDetailTempVOs.size() > 0 )
            {
               // 初始化
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
                     // 社保状态处理
                     if ( orderDetailTempVO.getDetailType().trim().equals( "1" ) )
                     {
                        // 批次含社保
                        if ( batchTempVO.getContainSB() != null && batchTempVO.getContainSB().trim().equals( "1" ) )
                        {
                           final SBDetailVO sbDetailVO = this.getSbDetailDao().getSBDetailVOByDetailId( orderDetailTempVO.getDetailId() );
                           // 状态“5”已结算
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
                     // 商保状态处理
                     else if ( orderDetailTempVO.getDetailType().trim().equals( "2" ) )
                     {
                        // 批次含商保
                        if ( batchTempVO.getContainCB() != null && batchTempVO.getContainCB().trim().equals( "1" ) )
                        {
                           final CBDetailVO cbDetailVO = this.getCbDetailDao().getCBDetailVOByDetailId( orderDetailTempVO.getDetailId() );
                           // 状态“5”已结算
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
                     // 社保调整状态处理
                     else if ( orderDetailTempVO.getDetailType().trim().equals( "3" ) )
                     {
                        // 批次含社保
                        if ( batchTempVO.getContainSB() != null && batchTempVO.getContainSB().trim().equals( "1" ) )
                        {
                           final SBAdjustmentDetailVO sbAdjustmentDetailVO = this.getSbAdjustmentDetailDao().getSBAdjustmentDetailVOByAdjustmentDetailId( orderDetailTempVO.getDetailId() );

                           if ( !sbAdjustmentHeaderIds.contains( sbAdjustmentDetailVO.getAdjustmentHeaderId() ) )
                           {
                              sbAdjustmentHeaderIds.add( sbAdjustmentDetailVO.getAdjustmentHeaderId() );
                           }
                        }
                     }
                     // 工资导入状态处理
                     else if ( orderDetailTempVO.getDetailType().trim().equals( "4" ) || orderDetailTempVO.getDetailType().trim().equals( "5" ) )
                     {
                        final List< String > salaryHeaderIds = KANUtil.jasonArrayToStringList( orderDetailTempVO.getHeaderId() );
                        final String salaryDetailId = orderDetailTempVO.getDetailId();

                        // 关联主表ID不为空的情况
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

                        // 关联从表ID不为空的情况
                        if ( KANUtil.filterEmpty( salaryDetailId ) != null )
                        {
                           // 从表数据修改
                           final SalaryDetailVO salaryDetailVO = this.getSalaryDetailDao().getSalaryDetailVOByDetailId( salaryDetailId );
                           salaryDetailVO.setStatus( "3" );
                           salaryDetailVO.setModifyBy( userId );
                           salaryDetailVO.setModifyDate( new Date() );
                           this.getSalaryDetailDao().updateSalaryDetail( salaryDetailVO );

                           // 标记判断Salary Detail是否已全部提交
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
                *  处理社保数据状态
                */
               if ( sbBatchIds != null && sbBatchIds.size() > 0 )
               {
                  for ( String sbBatchId : sbBatchIds )
                  {
                     final SBBatchVO sbBatchVO = this.getSbBatchDao().getSBBatchVOByBatchId( sbBatchId );

                     // 初始化SBHeaderVO列表
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
                                 // 初始化SBDetailVO列表
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
                *  处理商保数据状态
                */
               if ( cbBatchIds != null && cbBatchIds.size() > 0 )
               {
                  for ( String cbBatchId : cbBatchIds )
                  {
                     final CBBatchVO cbBatchVO = this.getCbBatchDao().getCBBatchVOByBatchId( cbBatchId );

                     // 初始化CBHeaderVO列表
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
                                 // 初始化CBDetailVO列表
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
                *  处理社保调整数据状态
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

package com.kan.hro.service.impl.biz.settlement;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.SettlementAdjustmentImportBatchDao;
import com.kan.hro.dao.inf.biz.settlement.SettlementAdjustmentImportDetailDao;
import com.kan.hro.dao.inf.biz.settlement.SettlementAdjustmentImportHeaderDao;
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportBatchVO;
import com.kan.hro.service.inf.biz.settlement.SettlementAdjustmentImportBatchService;

public class SettlementAdjustmentImportBatchServiceImpl extends ContextService implements SettlementAdjustmentImportBatchService
{

   private SettlementAdjustmentImportBatchDao settlementAdjustmentImportBatchDao;
   
   private SettlementAdjustmentImportHeaderDao settlementAdjustmentImportHeaderDao;
   
   private SettlementAdjustmentImportDetailDao settlementAdjustmentImportDetailDao;
   
   private CommonBatchDao commonBatchDao;

   @Override
   public PagedListHolder getSettlementAdjustmentImportBatchVOsByCondition( PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      pagedListHolder.setHolderSize( settlementAdjustmentImportBatchDao.countSettlementAdjustmentImportBatchVOsByCondition( ( SettlementAdjustmentImportBatchVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( settlementAdjustmentImportBatchDao.getSettlementAdjustmentImportBatchVOsByCondition( ( SettlementAdjustmentImportBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( settlementAdjustmentImportBatchDao.getSettlementAdjustmentImportBatchVOsByCondition( ( SettlementAdjustmentImportBatchVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public SettlementAdjustmentImportBatchVO getSettlementAdjustmentImportBatchById( String batchId ) throws KANException
   {
      return settlementAdjustmentImportBatchDao.getSettlementAdjustmentImportBatchVOByBatchId( batchId );
   }

   @Override
   public int updateSettlementAdjustmentImportBatch( final SettlementAdjustmentImportBatchVO settlementAdjustmentImportBatchVO ) throws KANException
   {
      int i = 0;
      try
      {
         // 开启事务
         this.startTransaction();
         i = i + settlementAdjustmentImportHeaderDao.insertSettlementAdjustmentHeaderTempToHeader( settlementAdjustmentImportBatchVO.getBatchId() );
         i = i + settlementAdjustmentImportDetailDao.insertSettlementAdjustmentDetailTempToDetail( settlementAdjustmentImportBatchVO.getBatchId() );
         settlementAdjustmentImportHeaderDao.updateHeaderStatus(settlementAdjustmentImportBatchVO.getBatchId());
         settlementAdjustmentImportBatchDao.updateBathStatus( settlementAdjustmentImportBatchVO );
         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return i;
   }

   @Override
   public int backBatch( final SettlementAdjustmentImportBatchVO settlementAdjustmentImportBatchVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         settlementAdjustmentImportDetailDao.deleteSettlementAdjustmentImportDetailTempByBatchId( settlementAdjustmentImportBatchVO.getBatchId() );
         settlementAdjustmentImportHeaderDao.deleteSettlementAdjustmentImportHeaderTempByBatchId( settlementAdjustmentImportBatchVO.getBatchId() );
         commonBatchDao.deleteCommonBatch( settlementAdjustmentImportBatchVO.getBatchId() );
         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   public SettlementAdjustmentImportBatchDao getSettlementAdjustmentImportBatchDao()
   {
      return settlementAdjustmentImportBatchDao;
   }

   public void setSettlementAdjustmentImportBatchDao( SettlementAdjustmentImportBatchDao settlementAdjustmentImportBatchDao )
   {
      this.settlementAdjustmentImportBatchDao = settlementAdjustmentImportBatchDao;
   }

   public SettlementAdjustmentImportHeaderDao getSettlementAdjustmentImportHeaderDao()
   {
      return settlementAdjustmentImportHeaderDao;
   }

   public void setSettlementAdjustmentImportHeaderDao( SettlementAdjustmentImportHeaderDao settlementAdjustmentImportHeaderDao )
   {
      this.settlementAdjustmentImportHeaderDao = settlementAdjustmentImportHeaderDao;
   }

   public SettlementAdjustmentImportDetailDao getSettlementAdjustmentImportDetailDao()
   {
      return settlementAdjustmentImportDetailDao;
   }

   public void setSettlementAdjustmentImportDetailDao( SettlementAdjustmentImportDetailDao settlementAdjustmentImportDetailDao )
   {
      this.settlementAdjustmentImportDetailDao = settlementAdjustmentImportDetailDao;
   }

   public CommonBatchDao getCommonBatchDao()
   {
      return commonBatchDao;
   }

   public void setCommonBatchDao( CommonBatchDao commonBatchDao )
   {
      this.commonBatchDao = commonBatchDao;
   }
}

package com.kan.hro.service.impl.biz.settlement;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.settlement.SettlementAdjustmentImportBatchDao;
import com.kan.hro.dao.inf.biz.settlement.SettlementAdjustmentImportDetailDao;
import com.kan.hro.dao.inf.biz.settlement.SettlementAdjustmentImportHeaderDao;
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportHeaderVO;
import com.kan.hro.service.inf.biz.settlement.SettlementAdjustmentImportHeaderService;

public class SettlementAdjustmentImportHeaderServiceImpl extends ContextService implements SettlementAdjustmentImportHeaderService
{
   private SettlementAdjustmentImportBatchDao settlementAdjustmentImportBatchDao;
   
   private SettlementAdjustmentImportHeaderDao settlementAdjustmentImportHeaderDao;
   
   private SettlementAdjustmentImportDetailDao settlementAdjustmentImportDetailDao;
   
   private CommonBatchDao commonBatchDao;

   @Override
   public PagedListHolder getSettlementAdjustmentImportHeaderVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      pagedListHolder.setHolderSize( settlementAdjustmentImportHeaderDao.countSettlementAdjustmentImportHeaderVOsByCondition( ( SettlementAdjustmentImportHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( settlementAdjustmentImportHeaderDao.getSettlementAdjustmentImportHeaderVOsByCondition( ( SettlementAdjustmentImportHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( settlementAdjustmentImportHeaderDao.getSettlementAdjustmentImportHeaderVOsByCondition( ( SettlementAdjustmentImportHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public int backUpRecord( String[] ids, String batchId ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         settlementAdjustmentImportDetailDao.deleteDetailTempRecord( ids );
         settlementAdjustmentImportHeaderDao.deleteHeaderTempRecord( ids );
         int count = settlementAdjustmentImportHeaderDao.getHeaderCountByBatchId( batchId );
         if ( count == 0 )
         {
            commonBatchDao.deleteCommonBatch( batchId );
         }
         // 提交事务
         this.commitTransaction();
         return count;
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }
   }
   
   @Override
   public SettlementAdjustmentImportHeaderVO getSettlementAdjustmentImportHeaderVOsById( final String headerId,final String accountId ) throws KANException
   {
      return settlementAdjustmentImportHeaderDao.getSettlementAdjustmentImportHeaderVOsById( headerId ,accountId);
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

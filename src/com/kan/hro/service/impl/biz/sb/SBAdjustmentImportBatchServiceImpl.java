package com.kan.hro.service.impl.biz.sb;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentImportBatchDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentImportDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentImportHeaderDao;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportBatchVO;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentImportBatchService;

public class SBAdjustmentImportBatchServiceImpl extends ContextService implements SBAdjustmentImportBatchService
{

   private SBAdjustmentImportBatchDao sbAdjustmentImportBatchDao;
   
   private SBAdjustmentImportHeaderDao sbAdjustmentImportHeaderDao;
   
   private SBAdjustmentImportDetailDao sbAdjustmentImportDetailDao;
   
   private CommonBatchDao commonBatchDao;

   @Override
   public PagedListHolder getSBAdjustmentImportBatchVOsByCondition( PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      pagedListHolder.setHolderSize( sbAdjustmentImportBatchDao.countSBAdjustmentImportBatchVOsByCondition( ( SBAdjustmentImportBatchVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( sbAdjustmentImportBatchDao.getSBAdjustmentImportBatchVOsByCondition( ( SBAdjustmentImportBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbAdjustmentImportBatchDao.getSBAdjustmentImportBatchVOsByCondition( ( SBAdjustmentImportBatchVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public SBAdjustmentImportBatchVO getSBAdjustmentImportBatchById( String batchId ) throws KANException
   {
      return sbAdjustmentImportBatchDao.getSBAdjustmentImportBatchVOByBatchId( batchId );
   }

   @Override
   public int updateSBAdjustmentImportBatch( final SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO ) throws KANException
   {
      int i = 0;
      try
      {
         // 开启事务
         this.startTransaction();
         i = i + sbAdjustmentImportHeaderDao.insertSBAdjustmentHeaderTempToHeader( sbAdjustmentImportBatchVO.getBatchId() );
         i = i + sbAdjustmentImportDetailDao.insertSBAdjustmentDetailTempToDetail( sbAdjustmentImportBatchVO.getBatchId() );
         sbAdjustmentImportHeaderDao.updateHeaderStatus(sbAdjustmentImportBatchVO.getBatchId());
         sbAdjustmentImportBatchDao.updateBathStatus( sbAdjustmentImportBatchVO );
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
   public int backBatch( final SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         sbAdjustmentImportDetailDao.deleteSBAdjustmentImportDetailTempByBatchId( sbAdjustmentImportBatchVO.getBatchId() );
         sbAdjustmentImportHeaderDao.deleteSBAdjustmentImportHeaderTempByBatchId( sbAdjustmentImportBatchVO.getBatchId() );
         commonBatchDao.deleteCommonBatch( sbAdjustmentImportBatchVO.getBatchId() );
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

   public SBAdjustmentImportBatchDao getSbAdjustmentImportBatchDao()
   {
      return sbAdjustmentImportBatchDao;
   }

   public void setSbAdjustmentImportBatchDao( SBAdjustmentImportBatchDao sbAdjustmentImportBatchDao )
   {
      this.sbAdjustmentImportBatchDao = sbAdjustmentImportBatchDao;
   }

   public SBAdjustmentImportHeaderDao getSbAdjustmentImportHeaderDao()
   {
      return sbAdjustmentImportHeaderDao;
   }

   public void setSbAdjustmentImportHeaderDao( SBAdjustmentImportHeaderDao sbAdjustmentImportHeaderDao )
   {
      this.sbAdjustmentImportHeaderDao = sbAdjustmentImportHeaderDao;
   }

   public SBAdjustmentImportDetailDao getSbAdjustmentImportDetailDao()
   {
      return sbAdjustmentImportDetailDao;
   }

   public void setSbAdjustmentImportDetailDao( SBAdjustmentImportDetailDao sbAdjustmentImportDetailDao )
   {
      this.sbAdjustmentImportDetailDao = sbAdjustmentImportDetailDao;
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

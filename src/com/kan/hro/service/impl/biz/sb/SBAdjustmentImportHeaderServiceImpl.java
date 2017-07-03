package com.kan.hro.service.impl.biz.sb;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.common.CommonBatchDao;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentImportBatchDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentImportDetailDao;
import com.kan.hro.dao.inf.biz.sb.SBAdjustmentImportHeaderDao;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportHeaderVO;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentImportHeaderService;

public class SBAdjustmentImportHeaderServiceImpl extends ContextService implements SBAdjustmentImportHeaderService
{
   private SBAdjustmentImportBatchDao sbAdjustmentImportBatchDao;

   private SBAdjustmentImportHeaderDao sbAdjustmentImportHeaderDao;

   private SBAdjustmentImportDetailDao sbAdjustmentImportDetailDao;

   private CommonBatchDao commonBatchDao;

   @Override
   public PagedListHolder getSBAdjustmentImportHeaderVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      pagedListHolder.setHolderSize( sbAdjustmentImportHeaderDao.countSBAdjustmentImportHeaderVOsByCondition( ( SBAdjustmentImportHeaderVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( sbAdjustmentImportHeaderDao.getSBAdjustmentImportHeaderVOsByCondition( ( SBAdjustmentImportHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( sbAdjustmentImportHeaderDao.getSBAdjustmentImportHeaderVOsByCondition( ( SBAdjustmentImportHeaderVO ) pagedListHolder.getObject() ) );
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
         sbAdjustmentImportDetailDao.deleteDetailTempRecord( ids );
         sbAdjustmentImportHeaderDao.deleteHeaderTempRecord( ids );
         int count = sbAdjustmentImportHeaderDao.getHeaderCountByBatchId( batchId );
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
   public SBAdjustmentImportHeaderVO getSBAdjustmentImportHeaderVOsById( final String headerId, final String accountId ) throws KANException
   {
      return sbAdjustmentImportHeaderDao.getSBAdjustmentImportHeaderVOsById( headerId, accountId );
   }

   public SBAdjustmentImportHeaderDao getSbAdjustmentImportHeaderDao()
   {
      return sbAdjustmentImportHeaderDao;
   }

   public void setSbAdjustmentImportHeaderDao( SBAdjustmentImportHeaderDao sbAdjustmentImportHeaderDao )
   {
      this.sbAdjustmentImportHeaderDao = sbAdjustmentImportHeaderDao;
   }

   public SBAdjustmentImportBatchDao getSbAdjustmentImportBatchDao()
   {
      return sbAdjustmentImportBatchDao;
   }

   public void setSbAdjustmentImportBatchDao( SBAdjustmentImportBatchDao sbAdjustmentImportBatchDao )
   {
      this.sbAdjustmentImportBatchDao = sbAdjustmentImportBatchDao;
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

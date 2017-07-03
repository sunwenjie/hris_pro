package com.kan.hro.service.impl.biz.attendance;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.OTImportBatchDao;
import com.kan.hro.dao.inf.biz.attendance.OTImportHeaderDao;
import com.kan.hro.domain.biz.attendance.OTImportHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.service.inf.biz.attendance.OTImportBatchService;

public class OTImportBatchServiceImpl extends ContextService implements OTImportBatchService
{

   private OTImportHeaderDao otImportHeaderDao;

   @Override
   public PagedListHolder getTimesheetBatchVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final OTImportBatchDao otImportBatchDao = ( OTImportBatchDao ) getDao();
      pagedListHolder.setHolderSize( otImportBatchDao.countTimesheetBatchVOsByCondition( ( TimesheetBatchVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( otImportBatchDao.getTimesheetBatchVOsByCondition( ( TimesheetBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( otImportBatchDao.getTimesheetBatchVOsByCondition( ( TimesheetBatchVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public TimesheetBatchVO getTimesheetBatchVOByBatchId( final String batchId ) throws KANException
   {
      return ( ( OTImportBatchDao ) getDao() ).getTimesheetBatchVOByBatchId( batchId );
   }

   @Override
   public int updateBatch( TimesheetBatchVO submitObject ) throws KANException
   {
      if ( submitObject == null || StringUtils.isBlank( submitObject.getBatchId() ) )
      {
         return 0;
      }

      int rows = 0;

      rows = ( ( OTImportBatchDao ) getDao() ).countOTVOsByBatchId( submitObject.getBatchId() );

      if ( rows == 0 )
      {
         try
         {
            // 开启事务
            this.startTransaction();

            // 盛竞特例 Add by siuvan 2015-03-20
            if ( "100045".equals( submitObject.getAccountId() ) )
            {
               otImportHeaderDao.insertOTImportHeaderToOTHeader_shengjoy( submitObject.getBatchId() );
               otImportHeaderDao.insertOTDetailtempToOTDetail_shengjoy( submitObject.getBatchId() );
            }
            else
            {
               //批量提交优化 modify by steven 2012-06-12
               otImportHeaderDao.insertOTImportHeaderToOTHeader( submitObject.getBatchId() );
               otImportHeaderDao.insertOTDetailtempToOTDetail( submitObject.getBatchId() );
            }

            ( ( OTImportBatchDao ) getDao() ).updateBathStatus( submitObject );

            // 更改header状态
            final List< Object > otImportHeaderVOs = this.getOtImportHeaderDao().getOTImportHeaderVOsByBatchId( submitObject.getBatchId() );
            if ( otImportHeaderVOs != null && otImportHeaderVOs.size() > 0 )
            {
               for ( Object otImpoerHeaderVOObject : otImportHeaderVOs )
               {
                  ( ( OTImportHeaderVO ) otImpoerHeaderVOObject ).setStatus( "2" );
                  this.getOtImportHeaderDao().updateOTImportHeader( ( ( OTImportHeaderVO ) otImpoerHeaderVOObject ) );
               }
            }

            // 提交事务
            this.commitTransaction();
         }
         catch ( final Exception e )
         {
            // 回滚事务
            this.rollbackTransaction();
            throw new KANException( e );
         }
      }

      return rows;
   }

   @Override
   public int backBatch( TimesheetBatchVO submitObject ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         ( ( OTImportBatchDao ) getDao() ).deleteOTImportDetailTempByBatchId( submitObject.getBatchId() );
         ( ( OTImportBatchDao ) getDao() ).deleteOTImportHeaderTempByBatchId( submitObject.getBatchId() );
         ( ( OTImportBatchDao ) getDao() ).deleteCommonBatchById( submitObject.getBatchId() );
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

   public OTImportHeaderDao getOtImportHeaderDao()
   {
      return otImportHeaderDao;
   }

   public void setOtImportHeaderDao( OTImportHeaderDao otImportHeaderDao )
   {
      this.otImportHeaderDao = otImportHeaderDao;
   }

}

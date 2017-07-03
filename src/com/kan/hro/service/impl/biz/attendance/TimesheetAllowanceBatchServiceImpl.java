package com.kan.hro.service.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.TimesheetAllowanceBatchDao;
import com.kan.hro.dao.inf.biz.attendance.TimesheetAllowanceDao;
import com.kan.hro.domain.biz.attendance.TimesheetAllowanceVO;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetAllowanceBatchService;

public class TimesheetAllowanceBatchServiceImpl extends ContextService implements TimesheetAllowanceBatchService
{
   //
   private TimesheetAllowanceDao timesheetAllowanceDao;

   @Override
   public PagedListHolder getTimesheetBatchVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final TimesheetAllowanceBatchDao timesheetAllowanceBatchDao = ( TimesheetAllowanceBatchDao ) getDao();
      pagedListHolder.setHolderSize( timesheetAllowanceBatchDao.countTimesheetBatchVOsByCondition( ( TimesheetBatchVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( timesheetAllowanceBatchDao.getTimesheetBatchVOsByCondition( ( TimesheetBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( timesheetAllowanceBatchDao.getTimesheetBatchVOsByCondition( ( TimesheetBatchVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public TimesheetBatchVO getTimesheetBatchVOByBatchId( final String batchId ) throws KANException
   {
      return ( ( TimesheetAllowanceBatchDao ) getDao() ).getTimesheetBatchVOByBatchId( batchId );
   }

   @Override
   public int updateBatch( TimesheetBatchVO submitObject ) throws KANException
   {

      try
      {
         // 开启事务
         //修改
         this.startTransaction();
         ( ( TimesheetAllowanceBatchDao ) getDao() ).updateAllowanceBaseByBatch( submitObject );
         //新增 - 优化
         TimesheetAllowanceVO timesheetAllowanceVO = new TimesheetAllowanceVO();
         timesheetAllowanceVO.setStatus( TimesheetAllowanceVO.TRUE );
         timesheetAllowanceVO.setBatchId( submitObject.getBatchId() );
         final List< Object > timesheetAllowanceVOList = timesheetAllowanceDao.getTimesheetAllowanceTempVOsByCondition( timesheetAllowanceVO );
         for ( Object timesheetAllowanceVOObject : timesheetAllowanceVOList )
         {
            timesheetAllowanceVO = ( TimesheetAllowanceVO ) timesheetAllowanceVOObject;
            timesheetAllowanceDao.insertTimesheetAllowance( timesheetAllowanceVO );
         }

         ( ( TimesheetAllowanceBatchDao ) getDao() ).updateBathStatus( submitObject );
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

   @Override
   public int backBatch( TimesheetBatchVO submitObject ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         ( ( TimesheetAllowanceBatchDao ) getDao() ).deleteAllowanceTempByBatchId( submitObject.getBatchId() );
         ( ( TimesheetAllowanceBatchDao ) getDao() ).deleteTimeSheetHeaderTempByBatchId( submitObject.getBatchId() );
         ( ( TimesheetAllowanceBatchDao ) getDao() ).deleteCommonBatchById( submitObject.getBatchId() );
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

   public TimesheetAllowanceDao getTimesheetAllowanceDao()
   {
      return timesheetAllowanceDao;
   }

   public void setTimesheetAllowanceDao( TimesheetAllowanceDao timesheetAllowanceDao )
   {
      this.timesheetAllowanceDao = timesheetAllowanceDao;
   }

}

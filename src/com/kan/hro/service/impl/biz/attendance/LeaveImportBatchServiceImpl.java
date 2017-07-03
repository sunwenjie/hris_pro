package com.kan.hro.service.impl.biz.attendance;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.LeaveImportBatchDao;
import com.kan.hro.dao.inf.biz.attendance.LeaveImportHeaderDao;
import com.kan.hro.domain.biz.attendance.LeaveImportHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.service.inf.biz.attendance.LeaveImportBatchService;

public class LeaveImportBatchServiceImpl extends ContextService implements LeaveImportBatchService
{

   private LeaveImportHeaderDao leaveImportHeaderDao;

   @Override
   public PagedListHolder getTimesheetBatchVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final LeaveImportBatchDao leaveImportBatchDao = ( LeaveImportBatchDao ) getDao();
      pagedListHolder.setHolderSize( leaveImportBatchDao.countTimesheetBatchVOsByCondition( ( TimesheetBatchVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( leaveImportBatchDao.getTimesheetBatchVOsByCondition( ( TimesheetBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( leaveImportBatchDao.getTimesheetBatchVOsByCondition( ( TimesheetBatchVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public TimesheetBatchVO getTimesheetBatchVOByBatchId( final String batchId ) throws KANException
   {
      return ( ( LeaveImportBatchDao ) getDao() ).getTimesheetBatchVOByBatchId( batchId );
   }

   @Override
   // Modify by siuvan@2014-09-17
   public List< String > updateBatch( TimesheetBatchVO submitObject ) throws KANException
   {
      final List< String > leaveHeaderIds = new ArrayList< String >();

      // 和Leave有无时间重叠
      leaveHeaderIds.addAll( ( ( LeaveImportBatchDao ) getDao() ).countLeaveVOsByBatchId( submitObject.getBatchId() ) );

      if ( leaveHeaderIds == null || leaveHeaderIds.size() == 0 )
      {
         try
         {
            // 开启事务
            this.startTransaction();

            // 将temp数据更新至Leave
            this.leaveImportHeaderDao.insertLeaveImportHeaderToLeaveHeader( submitObject.getBatchId() );
            this.leaveImportHeaderDao.insertLeaveDetailtempToLeaveDetail( submitObject.getBatchId() );

            ( ( LeaveImportBatchDao ) getDao() ).updateBathStatus( submitObject );

            // 更改header状态
            final List< Object > leaveImportHeaderVOs = this.getLeaveImportHeaderDao().getLeaveImportHeaderVOsByBatchId( submitObject.getBatchId() );
            if ( leaveImportHeaderVOs != null && leaveImportHeaderVOs.size() > 0 )
            {
               for ( Object leaveImpoerHeaderVOObject : leaveImportHeaderVOs )
               {
                  ( ( LeaveImportHeaderVO ) leaveImpoerHeaderVOObject ).setStatus( "2" );
                  this.getLeaveImportHeaderDao().updateLeaveImportHeader( ( ( LeaveImportHeaderVO ) leaveImpoerHeaderVOObject ) );
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

      return leaveHeaderIds;
   }

   @Override
   public int backBatch( TimesheetBatchVO submitObject ) throws KANException
   {
      try
      {
         // 开启事务
         this.startTransaction();
         ( ( LeaveImportBatchDao ) getDao() ).deleteLeaveImportDetailTempByBatchId( submitObject.getBatchId() );
         ( ( LeaveImportBatchDao ) getDao() ).deleteLeaveImportHeaderTempByBatchId( submitObject.getBatchId() );
         ( ( LeaveImportBatchDao ) getDao() ).deleteCommonBatchById( submitObject.getBatchId() );
         // 提交事务
         this.commitTransaction();
      }
      catch ( final Exception e )
      {
         // 回滚事务
         this.rollbackTransaction();
         throw new KANException( e );
      }

      return 1;
   }

   public LeaveImportHeaderDao getLeaveImportHeaderDao()
   {
      return leaveImportHeaderDao;
   }

   public void setLeaveImportHeaderDao( LeaveImportHeaderDao leaveImportHeaderDao )
   {
      this.leaveImportHeaderDao = leaveImportHeaderDao;
   }

}

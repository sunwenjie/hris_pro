package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;

public interface LeaveImportBatchDao
{
   public abstract int countTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO ) throws KANException;

   public abstract List< Object > getTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO ) throws KANException;

   public abstract List< Object > getTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO, final RowBounds rowBounds ) throws KANException;

   public abstract TimesheetBatchVO getTimesheetBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract void deleteLeaveImportDetailTempByBatchId( String batchId ) throws KANException;

   public abstract void deleteLeaveImportHeaderTempByBatchId( String batchId ) throws KANException;

   public abstract void deleteCommonBatchById( String batchId ) throws KANException;

   public abstract void updateBathStatus( TimesheetBatchVO submitObject );

   public abstract List< String > countLeaveVOsByBatchId( String batchId ) throws KANException;

}

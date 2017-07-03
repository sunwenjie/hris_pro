package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;

public interface OTImportBatchDao
{
   public abstract int countTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO ) throws KANException;

   public abstract List< Object > getTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO ) throws KANException;

   public abstract List< Object > getTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO, final RowBounds rowBounds ) throws KANException;

   public abstract TimesheetBatchVO getTimesheetBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract void deleteOTImportDetailTempByBatchId( final String batchId ) throws KANException;

   public abstract void deleteOTImportHeaderTempByBatchId( final String batchId ) throws KANException;

   public abstract void deleteCommonBatchById( final String batchId ) throws KANException;

   public abstract void updateBathStatus( final TimesheetBatchVO submitObject );

   public abstract int countOTVOsByBatchId( final String batchId ) throws KANException;

   public abstract int getBatchCountByHeaderId( final String batchId );

}

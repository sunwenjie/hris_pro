package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;

public interface TimesheetBatchDao
{
   public abstract int countTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO ) throws KANException;

   public abstract List< Object > getTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO ) throws KANException;

   public abstract List< Object > getTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO, final RowBounds rowBounds ) throws KANException;

   public abstract TimesheetBatchVO getTimesheetBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int insertTimesheetBatch( final TimesheetBatchVO timesheetBatchVO ) throws KANException;

   public abstract int updateTimesheetBatch( final TimesheetBatchVO timesheetBatchVO ) throws KANException;

   public abstract int deleteTimesheetBatch( final String batchId ) throws KANException;

   public abstract TimesheetBatchVO getTimesheetBatchVOByHeaderIds( final String headerIds ) throws KANException;
}

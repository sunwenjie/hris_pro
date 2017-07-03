package com.kan.hro.service.inf.biz.attendance;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;

public interface TimesheetBatchService
{
   public abstract PagedListHolder getTimesheetBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract TimesheetBatchVO getTimesheetBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int generateTimesheet( final TimesheetBatchVO timesheetBatchVO ) throws KANException;

   public abstract int submit_batch( final TimesheetBatchVO timesheetBatchVO ) throws KANException;

   public abstract TimesheetBatchVO getTimesheetBatchVOByHeaderIds( final String headerIds ) throws KANException;

   public abstract TimesheetBatchVO getTimesheetBatchVOByTimesheetBatchVO( TimesheetBatchVO condition ) throws KANException;
}

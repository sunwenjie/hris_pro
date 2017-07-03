package com.kan.hro.service.inf.biz.attendance;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;

public interface TimesheetAllowanceBatchService
{
   public abstract PagedListHolder getTimesheetBatchVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract TimesheetBatchVO getTimesheetBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int updateBatch(TimesheetBatchVO submitObject)throws KANException;

   public abstract int backBatch(TimesheetBatchVO submitObject)throws KANException;

}

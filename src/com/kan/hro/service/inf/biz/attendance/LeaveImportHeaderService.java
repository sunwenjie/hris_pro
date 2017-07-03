package com.kan.hro.service.inf.biz.attendance;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.LeaveImportHeaderVO;

public interface LeaveImportHeaderService
{
   public abstract PagedListHolder getLeaveImportHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract void updateLeaveImportBase( final String allowanceId, final String base ) throws KANException;

   public abstract int backHeader( final LeaveImportHeaderVO leaveImportHeaderVO ) throws KANException;

   public abstract List< Object > getLeaveImportHeaderVOsByBatchId( final String batchId ) throws KANException;

}

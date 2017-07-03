package com.kan.hro.service.inf.biz.attendance;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface TimesheetAllowanceHeaderService
{
   public abstract PagedListHolder getTimesheetHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract void updateAllowanceBase(String allowanceId, String base) throws KANException;

   public abstract int[] backTimeSheetAllowanceTemp(String[] allowanceIdsArray, String batchId) throws KANException;
}

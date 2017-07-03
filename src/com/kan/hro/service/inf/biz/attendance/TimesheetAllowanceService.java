package com.kan.hro.service.inf.biz.attendance;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.TimesheetAllowanceVO;

public interface TimesheetAllowanceService
{
   public abstract PagedListHolder getTimesheetAllowanceVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract TimesheetAllowanceVO getTimesheetAllowanceVOByAllowanceId( final String allowanceId ) throws KANException;

   public abstract int insertTimesheetAllowance( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException;

   public abstract int updateTimesheetAllowance( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException;

   public abstract int deleteTimesheetAllowance( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException;
}

package com.kan.hro.service.inf.biz.attendance;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.TimesheetDetailVO;

public interface TimesheetDetailService
{
   public abstract PagedListHolder getTimesheetDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract TimesheetDetailVO getTimesheetDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertTimesheetDetail( final TimesheetDetailVO timesheetDetailVO ) throws KANException;

   public abstract int updateTimesheetDetail( final TimesheetDetailVO timesheetDetailVO ) throws KANException;

   public abstract int deleteTimesheetDetail( final TimesheetDetailVO timesheetDetailVO ) throws KANException;

   public abstract List< Object > getTimesheetDetailVOsByHeaderId( final String headerId ) throws KANException;
}

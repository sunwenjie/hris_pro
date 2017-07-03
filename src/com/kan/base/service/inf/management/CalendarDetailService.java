package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.CalendarDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface CalendarDetailService
{
   public abstract PagedListHolder getCalendarDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract CalendarDetailVO getCalendarDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertCalendarDetail( final CalendarDetailVO calendarDetailVO ) throws KANException;

   public abstract int updateCalendarDetail( final CalendarDetailVO calendarDetailVO ) throws KANException;

   public abstract int deleteCalendarDetail( final CalendarDetailVO calendarDetailVO ) throws KANException;

   public abstract List< Object > getAvailableCalendarDetailVOs( final CalendarDetailVO calendarDetailVO ) throws KANException;
}

package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.CalendarDTO;
import com.kan.base.domain.management.CalendarHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface CalendarHeaderService
{
   public abstract PagedListHolder getCalendarHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract CalendarHeaderVO getCalendarHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertCalendarHeader( final CalendarHeaderVO calendarHeaderVO ) throws KANException;

   public abstract int updateCalendarHeader( final CalendarHeaderVO calendarHeaderVO ) throws KANException;

   public abstract int deleteCalendarHeader( final CalendarHeaderVO calendarHeaderVO ) throws KANException;

   public abstract List< Object > getAvailableCalendarHeaderVOs( final CalendarHeaderVO calendarHeaderVO ) throws KANException;

   public abstract List< CalendarDTO > getCalendarDTOsByAccountId( final String accountId ) throws KANException;
}

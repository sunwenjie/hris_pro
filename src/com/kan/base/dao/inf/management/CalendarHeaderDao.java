package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.CalendarHeaderVO;
import com.kan.base.util.KANException;

public interface CalendarHeaderDao
{
   public abstract int countCalendarHeaderVOsByCondition( final CalendarHeaderVO calendarHeaderVO ) throws KANException;

   public abstract List< Object > getCalendarHeaderVOsByCondition( final CalendarHeaderVO calendarHeaderVO ) throws KANException;

   public abstract List< Object > getCalendarHeaderVOsByCondition( final CalendarHeaderVO calendarHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract CalendarHeaderVO getCalendarHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertCalendarHeader( final CalendarHeaderVO calendarHeaderVO ) throws KANException;

   public abstract int updateCalendarHeader( final CalendarHeaderVO calendarHeaderVO ) throws KANException;

   public abstract int deleteCalendarHeader( final CalendarHeaderVO calendarHeaderVO ) throws KANException;

   public abstract List< Object > getCalendarHeaderVOsByAccountId( final String accountId ) throws KANException;
}

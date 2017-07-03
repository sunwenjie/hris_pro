package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.CalendarDetailVO;
import com.kan.base.util.KANException;

public interface CalendarDetailDao
{
   public abstract int countCalendarDetailVOsByCondition( final CalendarDetailVO calendarDetailVO ) throws KANException;

   public abstract List< Object > getCalendarDetailVOsByCondition( final CalendarDetailVO calendarDetailVO ) throws KANException;

   public abstract List< Object > getCalendarDetailVOsByCondition( final CalendarDetailVO calendarDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract CalendarDetailVO getCalendarDetailVOByDetailId( final String headerId ) throws KANException;

   public abstract int insertCalendarDetail( final CalendarDetailVO calendarDetailVO ) throws KANException;

   public abstract int updateCalendarDetail( final CalendarDetailVO calendarDetailVO ) throws KANException;

   public abstract int deleteCalendarDetail( final CalendarDetailVO calendarDetailVO ) throws KANException;

   public abstract List< Object > getCalendarDetailVOsByHeaderId( final String headerId ) throws KANException;
}

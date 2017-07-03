package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.CalendarHeaderDao;
import com.kan.base.domain.management.CalendarHeaderVO;
import com.kan.base.util.KANException;

public class CalendarHeaderDaoImpl extends Context implements CalendarHeaderDao
{

   @Override
   public int countCalendarHeaderVOsByCondition( final CalendarHeaderVO calendarHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countCalendarHeaderVOsByCondition", calendarHeaderVO );
   }

   @Override
   public List< Object > getCalendarHeaderVOsByCondition( final CalendarHeaderVO calendarHeaderVO ) throws KANException
   {
      return selectList( "getCalendarHeaderVOsByCondition", calendarHeaderVO );
   }

   @Override
   public List< Object > getCalendarHeaderVOsByCondition( final CalendarHeaderVO calendarHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getCalendarHeaderVOsByCondition", calendarHeaderVO, rowBounds );
   }

   @Override
   public CalendarHeaderVO getCalendarHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( CalendarHeaderVO ) select( "getCalendarHeaderVOByHeaderId", headerId );
   }

   @Override
   public int insertCalendarHeader( final CalendarHeaderVO calendarHeaderVO ) throws KANException
   {
      return insert( "insertCalendarHeader", calendarHeaderVO );
   }

   @Override
   public int updateCalendarHeader( final CalendarHeaderVO calendarHeaderVO ) throws KANException
   {
      return update( "updateCalendarHeader", calendarHeaderVO );
   }

   @Override
   public int deleteCalendarHeader( final CalendarHeaderVO calendarHeaderVO ) throws KANException
   {
      return delete( "deleteCalendarHeader", calendarHeaderVO );
   }

   @Override
   public List< Object > getCalendarHeaderVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getCalendarHeaderVOsByAccountId", accountId );
   }

}

package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.CalendarDetailDao;
import com.kan.base.domain.management.CalendarDetailVO;
import com.kan.base.util.KANException;

public class CalendarDetailDaoImpl extends Context implements CalendarDetailDao
{

   @Override
   public int countCalendarDetailVOsByCondition( final CalendarDetailVO calendarDetailVO ) throws KANException
   {
      return ( Integer ) select( "countCalendarDetailVOsByCondition", calendarDetailVO );
   }

   @Override
   public List< Object > getCalendarDetailVOsByCondition( final CalendarDetailVO calendarDetailVO ) throws KANException
   {
      return selectList( "getCalendarDetailVOsByCondition", calendarDetailVO );
   }

   @Override
   public List< Object > getCalendarDetailVOsByCondition( final CalendarDetailVO calendarDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getCalendarDetailVOsByCondition", calendarDetailVO, rowBounds );
   }

   @Override
   public CalendarDetailVO getCalendarDetailVOByDetailId( final String headerId ) throws KANException
   {
      return ( CalendarDetailVO ) select( "getCalendarDetailVOByDetailId", headerId );
   }

   @Override
   public int insertCalendarDetail( final CalendarDetailVO calendarDetailVO ) throws KANException
   {
      return insert( "insertCalendarDetail", calendarDetailVO );
   }

   @Override
   public int updateCalendarDetail( final CalendarDetailVO calendarDetailVO ) throws KANException
   {
      return update( "updateCalendarDetail", calendarDetailVO );
   }

   @Override
   public int deleteCalendarDetail( final CalendarDetailVO calendarDetailVO ) throws KANException
   {
      return delete( "deleteCalendarDetail", calendarDetailVO );
   }

   @Override
   public List< Object > getCalendarDetailVOsByHeaderId( final String headerId ) throws KANException
   {
      return selectList( "getCalendarDetailVOsByHeaderId", headerId );
   }

}

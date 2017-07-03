package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.CalendarDetailDao;
import com.kan.base.domain.management.CalendarDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.CalendarDetailService;
import com.kan.base.util.KANException;

public class CalendarDetailServiceImpl extends ContextService implements CalendarDetailService
{

   @Override
   public PagedListHolder getCalendarDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final CalendarDetailDao calendarDetailDao = ( CalendarDetailDao ) getDao();
      pagedListHolder.setHolderSize( calendarDetailDao.countCalendarDetailVOsByCondition( ( CalendarDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( calendarDetailDao.getCalendarDetailVOsByCondition( ( CalendarDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( calendarDetailDao.getCalendarDetailVOsByCondition( ( CalendarDetailVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public CalendarDetailVO getCalendarDetailVOByDetailId( final String headerId ) throws KANException
   {
      return ( ( CalendarDetailDao ) getDao() ).getCalendarDetailVOByDetailId( headerId );
   }

   @Override
   public int insertCalendarDetail( final CalendarDetailVO calendarDetailVO ) throws KANException
   {
      return ( ( CalendarDetailDao ) getDao() ).insertCalendarDetail( calendarDetailVO );
   }

   @Override
   public int updateCalendarDetail( final CalendarDetailVO calendarDetailVO ) throws KANException
   {
      return ( ( CalendarDetailDao ) getDao() ).updateCalendarDetail( calendarDetailVO );
   }

   @Override
   public int deleteCalendarDetail( final CalendarDetailVO calendarDetailVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      calendarDetailVO.setDeleted( CalendarDetailVO.FALSE );
      return ( ( CalendarDetailDao ) getDao() ).updateCalendarDetail( calendarDetailVO );
   }

   @Override
   public List< Object > getAvailableCalendarDetailVOs( final CalendarDetailVO calendarDetailVO ) throws KANException
   {
      calendarDetailVO.setStatus( CalendarDetailVO.TRUE );
      return ( ( CalendarDetailDao ) getDao() ).getCalendarDetailVOsByCondition( calendarDetailVO );
   }
}

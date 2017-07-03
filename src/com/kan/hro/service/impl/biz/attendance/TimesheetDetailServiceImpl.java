package com.kan.hro.service.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.TimesheetDetailDao;
import com.kan.hro.domain.biz.attendance.TimesheetDetailVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetDetailService;

public class TimesheetDetailServiceImpl extends ContextService implements TimesheetDetailService
{

   @Override
   public PagedListHolder getTimesheetDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final TimesheetDetailDao timesheetDetailDao = ( TimesheetDetailDao ) getDao();
      pagedListHolder.setHolderSize( timesheetDetailDao.countTimesheetDetailVOsByCondition( ( TimesheetDetailVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( timesheetDetailDao.getTimesheetDetailVOsByCondition( ( TimesheetDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( timesheetDetailDao.getTimesheetDetailVOsByCondition( ( TimesheetDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public TimesheetDetailVO getTimesheetDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( ( TimesheetDetailDao ) getDao() ).getTimesheetDetailVOByDetailId( detailId );
   }

   @Override
   public int insertTimesheetDetail( final TimesheetDetailVO timesheetDetailVO ) throws KANException
   {
      return ( ( TimesheetDetailDao ) getDao() ).insertTimesheetDetail( timesheetDetailVO );
   }

   @Override
   public int updateTimesheetDetail( final TimesheetDetailVO timesheetDetailVO ) throws KANException
   {
      return ( ( TimesheetDetailDao ) getDao() ).updateTimesheetDetail( timesheetDetailVO );
   }

   @Override
   public int deleteTimesheetDetail( final TimesheetDetailVO timesheetDetailVO ) throws KANException
   {
      final TimesheetDetailVO modifyObject = ( ( TimesheetDetailDao ) getDao() ).getTimesheetDetailVOByDetailId( timesheetDetailVO.getDetailId() );
      modifyObject.setDeleted( TimesheetDetailVO.FALSE );
      return ( ( TimesheetDetailDao ) getDao() ).updateTimesheetDetail( modifyObject );
   }

   @Override
   public List< Object > getTimesheetDetailVOsByHeaderId( String headerId ) throws KANException
   {
      return ( ( TimesheetDetailDao ) getDao() ).getTimesheetDetailVOsByHeaderId( headerId );
   }
}

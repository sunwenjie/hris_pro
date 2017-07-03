package com.kan.hro.service.impl.biz.attendance;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.TimesheetAllowanceDao;
import com.kan.hro.domain.biz.attendance.TimesheetAllowanceVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetAllowanceService;

public class TimesheetAllowanceServiceImpl extends ContextService implements TimesheetAllowanceService
{

   @Override
   public PagedListHolder getTimesheetAllowanceVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final TimesheetAllowanceDao timesheetAllowanceDao = ( TimesheetAllowanceDao ) getDao();
      pagedListHolder.setHolderSize( timesheetAllowanceDao.countTimesheetAllowanceVOsByCondition( ( TimesheetAllowanceVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( timesheetAllowanceDao.getTimesheetAllowanceVOsByCondition( ( TimesheetAllowanceVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( timesheetAllowanceDao.getTimesheetAllowanceVOsByCondition( ( TimesheetAllowanceVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public TimesheetAllowanceVO getTimesheetAllowanceVOByAllowanceId( final String allowanceId ) throws KANException
   {
      return ( ( TimesheetAllowanceDao ) getDao() ).getTimesheetAllowanceVOByAllowanceId( allowanceId );
   }

   @Override
   public int insertTimesheetAllowance( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException
   {
      return ( ( TimesheetAllowanceDao ) getDao() ).insertTimesheetAllowance( timesheetAllowanceVO );
   }

   @Override
   public int updateTimesheetAllowance( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException
   {
      return ( ( TimesheetAllowanceDao ) getDao() ).updateTimesheetAllowance( timesheetAllowanceVO );
   }

   @Override
   public int deleteTimesheetAllowance( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException
   {
      final TimesheetAllowanceVO modifyObject = ( ( TimesheetAllowanceDao ) getDao() ).getTimesheetAllowanceVOByAllowanceId( timesheetAllowanceVO.getAllowanceId() );
      modifyObject.setDeleted( TimesheetAllowanceVO.FALSE );
      return ( ( TimesheetAllowanceDao ) getDao() ).updateTimesheetAllowance( modifyObject );
   }

}

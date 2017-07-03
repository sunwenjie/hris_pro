package com.kan.hro.service.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.LeaveDetailDao;
import com.kan.hro.domain.biz.attendance.LeaveDetailVO;
import com.kan.hro.service.inf.biz.attendance.LeaveDetailService;

public class LeaveDetailServiceImpl extends ContextService implements LeaveDetailService
{

   @Override
   public PagedListHolder getLeaveDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final LeaveDetailDao leaveDetailDao = ( LeaveDetailDao ) getDao();
      pagedListHolder.setHolderSize( leaveDetailDao.countLeaveDetailVOsByCondition( ( LeaveDetailVO ) pagedListHolder.getObject() ) );
     
      if ( isPaged )
      {
         pagedListHolder.setSource( leaveDetailDao.getLeaveDetailVOsByCondition( ( LeaveDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( leaveDetailDao.getLeaveDetailVOsByCondition( ( LeaveDetailVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public LeaveDetailVO getLeaveDetailVOByLeaveDetailId( final String leaveDetailId ) throws KANException
   {
      return ( ( LeaveDetailDao ) getDao() ).getLeaveDetailVOByLeaveDetailId( leaveDetailId );
   }

   @Override
   public int insertLeaveDetail( final LeaveDetailVO leaveDetailVO ) throws KANException
   {
      return ( ( LeaveDetailDao ) getDao() ).insertLeaveDetail( leaveDetailVO );
   }

   @Override
   public int updateLeaveDetail( final LeaveDetailVO leaveDetailVO ) throws KANException
   {
      return ( ( LeaveDetailDao ) getDao() ).updateLeaveDetail( leaveDetailVO );
   }

   @Override
   public int deleteLeaveDetail( final LeaveDetailVO leaveDetailVO ) throws KANException
   {
      return ( ( LeaveDetailDao ) getDao() ).deleteLeaveDetail( leaveDetailVO );
   }

   @Override
   public List< Object > getLeaveDetailVOsByLeaveHeaderId( final String leaveHeaderId ) throws KANException
   {
      return ( ( LeaveDetailDao ) getDao() ).getLeaveDetailVOsByLeaveHeaderId( leaveHeaderId );
   }

}

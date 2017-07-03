package com.kan.hro.service.inf.biz.attendance;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.LeaveDetailVO;

public interface LeaveDetailService
{
   public abstract PagedListHolder getLeaveDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract LeaveDetailVO getLeaveDetailVOByLeaveDetailId( final String leaveDetailId ) throws KANException;

   public abstract int insertLeaveDetail( final LeaveDetailVO leaveDetailVO ) throws KANException;

   public abstract int updateLeaveDetail( final LeaveDetailVO leaveDetailVO ) throws KANException;

   public abstract int deleteLeaveDetail( final LeaveDetailVO leaveDetailVO ) throws KANException;
   
   public abstract List< Object > getLeaveDetailVOsByLeaveHeaderId( final String leaveHeaderId ) throws KANException;
}

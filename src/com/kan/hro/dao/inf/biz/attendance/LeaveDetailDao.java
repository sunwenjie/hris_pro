package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.LeaveDetailVO;

public interface LeaveDetailDao
{
   public abstract int countLeaveDetailVOsByCondition( final LeaveDetailVO leaveDetailVO ) throws KANException;

   public abstract List< Object > getLeaveDetailVOsByCondition( final LeaveDetailVO leaveDetailVO ) throws KANException;

   public abstract List< Object > getLeaveDetailVOsByCondition( final LeaveDetailVO leaveDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract LeaveDetailVO getLeaveDetailVOByLeaveDetailId( final String leaveDetailId ) throws KANException;

   public abstract int insertLeaveDetail( final LeaveDetailVO leaveDetailVO ) throws KANException;

   public abstract int updateLeaveDetail( final LeaveDetailVO leaveDetailVO ) throws KANException;

   public abstract int deleteLeaveDetail( final LeaveDetailVO leaveDetailVO ) throws KANException;

   public abstract List< Object > getLeaveDetailVOsByLeaveHeaderId( final String leaveHeaderId ) throws KANException;

   public abstract int sumLeaveHoursByTimesheetId( final String timesheetId ) throws KANException;

   public abstract int insertLeaveDetailtemp( final LeaveDetailVO leaveDetailVO ) throws KANException;

   public abstract List< Object > getLeaveDetailVOsByTimesheetId( final String timesheetId ) throws KANException;
}

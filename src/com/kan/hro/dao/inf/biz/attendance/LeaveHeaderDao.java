package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;

public interface LeaveHeaderDao
{
   public abstract int countLeaveHeaderVOsByCondition( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract List< Object > getLeaveHeaderVOsByCondition( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract List< Object > getLeaveHeaderVOsByCondition( final LeaveHeaderVO leaveHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract LeaveHeaderVO getLeaveHeaderVOByLeaveHeaderId( final String leaveHeaderId ) throws KANException;

   public abstract int insertLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int updateLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int deleteLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int count_leaveUnread( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int read_Leave( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract List< Object > exportLeaveDetailByCondition( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int sumLegalLeaveHoursByCondition( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract int sumBenefitLeaveHoursByCondition( final LeaveHeaderVO leaveHeaderVO ) throws KANException;

   public abstract String getMapLeaveDetailsByCondition( final LeaveHeaderVO leaveHeaderVO ) throws KANException;
}

package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.LeaveDetailDao;
import com.kan.hro.domain.biz.attendance.LeaveDetailVO;

public class LeaveDetailDaoImpl extends Context implements LeaveDetailDao
{

   @Override
   public int countLeaveDetailVOsByCondition( final LeaveDetailVO leaveDetailVO ) throws KANException
   {
      return ( Integer ) select( "countLeaveDetailVOsByCondition", leaveDetailVO );
   }

   @Override
   public List< Object > getLeaveDetailVOsByCondition( final LeaveDetailVO leaveDetailVO ) throws KANException
   {
      return selectList( "getLeaveDetailVOsByCondition", leaveDetailVO );
   }

   @Override
   public List< Object > getLeaveDetailVOsByCondition( final LeaveDetailVO leaveDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getLeaveDetailVOsByCondition", leaveDetailVO, rowBounds );
   }

   @Override
   public LeaveDetailVO getLeaveDetailVOByLeaveDetailId( final String leaveDetailId ) throws KANException
   {
      return ( LeaveDetailVO ) select( "getLeaveDetailVOByLeaveDetailId", leaveDetailId );
   }

   @Override
   public int insertLeaveDetail( final LeaveDetailVO leaveDetailVO ) throws KANException
   {
      return insert( "insertLeaveDetail", leaveDetailVO );
   }

   @Override
   public int updateLeaveDetail( final LeaveDetailVO leaveDetailVO ) throws KANException
   {
      return update( "updateLeaveDetail", leaveDetailVO );
   }

   @Override
   public int deleteLeaveDetail( final LeaveDetailVO leaveDetailVO ) throws KANException
   {
      return delete( "deleteLeaveDetail", leaveDetailVO.getLeaveDetailId() );
   }

   @Override
   public List< Object > getLeaveDetailVOsByLeaveHeaderId( final String leaveHeaderId ) throws KANException
   {
      return selectList( "getLeaveDetailVOsByLeaveHeaderId", leaveHeaderId );
   }

   @Override
   public int sumLeaveHoursByTimesheetId( final String timesheetId ) throws KANException
   {
      return ( Integer ) select( "sumLeaveHoursByTimesheetId", timesheetId );
   }

   @Override
   public int insertLeaveDetailtemp( final LeaveDetailVO leaveDetailVO ) throws KANException
   {
      return insert( "insertLeaveDetailtemp", leaveDetailVO );
   }

   @Override
   public List< Object > getLeaveDetailVOsByTimesheetId( final String timesheetId ) throws KANException
   {
      return selectList( "getLeaveDetailVOsByTimesheetId", timesheetId );
   }

}

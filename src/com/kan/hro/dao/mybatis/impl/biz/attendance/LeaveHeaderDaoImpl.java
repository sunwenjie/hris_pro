package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.LeaveHeaderDao;
import com.kan.hro.domain.biz.attendance.LeaveHeaderVO;

public class LeaveHeaderDaoImpl extends Context implements LeaveHeaderDao
{

   @Override
   public int countLeaveHeaderVOsByCondition( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countLeaveHeaderVOsByCondition", leaveHeaderVO );
   }

   @Override
   public List< Object > getLeaveHeaderVOsByCondition( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      return selectList( "getLeaveHeaderVOsByCondition", leaveHeaderVO );
   }

   @Override
   public List< Object > getLeaveHeaderVOsByCondition( final LeaveHeaderVO leaveHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getLeaveHeaderVOsByCondition", leaveHeaderVO, rowBounds );
   }

   @Override
   public LeaveHeaderVO getLeaveHeaderVOByLeaveHeaderId( final String leaveHeaderId ) throws KANException
   {
      return ( LeaveHeaderVO ) select( "getLeaveHeaderVOByLeaveHeaderId", leaveHeaderId );
   }

   @Override
   public int insertLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      leaveHeaderVO.setEstimateBenefitHours( leaveHeaderVO.getEstimateBenefitHours().trim() );
      return insert( "insertLeaveHeader", leaveHeaderVO );
   }

   @Override
   public int updateLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      return update( "updateLeaveHeader", leaveHeaderVO );
   }

   @Override
   public int deleteLeaveHeader( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      return delete( "deleteLeaveHeader", leaveHeaderVO );
   }

   @Override
   public int count_leaveUnread( LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      return ( Integer ) select( "count_leaveUnread", leaveHeaderVO );
   }

   @Override
   public int read_Leave( LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      return update( "read_Leave", leaveHeaderVO );
   }

   @Override
   public List< Object > exportLeaveDetailByCondition( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      return selectList( "exportLeaveDetailByCondition", leaveHeaderVO );
   }

   @Override
   public int sumLegalLeaveHoursByCondition( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      return ( Integer ) select( "sumLegalLeaveHoursByCondition", leaveHeaderVO );
   }

   @Override
   public int sumBenefitLeaveHoursByCondition( final LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      return ( Integer ) select( "sumBenefitLeaveHoursByCondition", leaveHeaderVO );
   }

   @Override
   public String getMapLeaveDetailsByCondition( LeaveHeaderVO leaveHeaderVO ) throws KANException
   {
      return ( String ) select( "getMapLeaveDetailsByCondition", leaveHeaderVO );
   }

}

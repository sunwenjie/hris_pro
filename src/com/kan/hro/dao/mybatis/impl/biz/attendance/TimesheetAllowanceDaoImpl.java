package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.TimesheetAllowanceDao;
import com.kan.hro.domain.biz.attendance.TimesheetAllowanceVO;

public class TimesheetAllowanceDaoImpl extends Context implements TimesheetAllowanceDao
{

   @Override
   public int countTimesheetAllowanceVOsByCondition( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException
   {
      return ( Integer ) select( "countTimesheetAllowanceVOsByCondition", timesheetAllowanceVO );
   }

   @Override
   public List< Object > getTimesheetAllowanceVOsByCondition( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException
   {
      return selectList( "getTimesheetAllowanceVOsByCondition", timesheetAllowanceVO );
   }

   @Override
   public List< Object > getTimesheetAllowanceVOsByCondition( final TimesheetAllowanceVO timesheetAllowanceVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getTimesheetAllowanceVOsByCondition", timesheetAllowanceVO, rowBounds );
   }

   @Override
   public TimesheetAllowanceVO getTimesheetAllowanceVOByAllowanceId( final String allowanceId ) throws KANException
   {
      return ( TimesheetAllowanceVO ) select( "getTimesheetAllowanceVOByAllowanceId", allowanceId );
   }

   @Override
   public int insertTimesheetAllowance( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException
   {
      return insert( "insertTimesheetAllowance", timesheetAllowanceVO );
   }

   @Override
   public int updateTimesheetAllowance( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException
   {
      return update( "updateTimesheetAllowance", timesheetAllowanceVO );
   }

   @Override
   public int deleteTimesheetAllowance( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException
   {
      return delete( "deleteTimesheetAllowance", timesheetAllowanceVO );
   }

   @Override
   public List< Object > getTimesheetAllowanceVOsByHeaderId( final String headerId ) throws KANException
   {
      return selectList( "getTimesheetAllowanceVOsByHeaderId", headerId );
   }
   
   @Override
   public List< Object > getTimesheetAllowanceTempVOsByCondition( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException
   {
      return selectList( "getTimesheetAllowanceTempVOsByCondition", timesheetAllowanceVO );
   }

}

package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.TimesheetDetailDao;
import com.kan.hro.domain.biz.attendance.TimesheetDetailVO;

public class TimesheetDetailDaoImpl extends Context implements TimesheetDetailDao
{

   @Override
   public int countTimesheetDetailVOsByCondition( final TimesheetDetailVO timesheetDetailVO ) throws KANException
   {
      return ( Integer ) select( "countTimesheetDetailVOsByCondition", timesheetDetailVO );
   }

   @Override
   public List< Object > getTimesheetDetailVOsByCondition( final TimesheetDetailVO timesheetDetailVO ) throws KANException
   {
      return selectList( "getTimesheetDetailVOsByCondition", timesheetDetailVO );
   }

   @Override
   public List< Object > getTimesheetDetailVOsByCondition( final TimesheetDetailVO timesheetDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getTimesheetDetailVOsByCondition", timesheetDetailVO, rowBounds );
   }

   @Override
   public TimesheetDetailVO getTimesheetDetailVOByDetailId( final String detailId ) throws KANException
   {
      return ( TimesheetDetailVO ) select( "getTimesheetDetailVOByDetailId", detailId );
   }

   @Override
   public int insertTimesheetDetail( final TimesheetDetailVO timesheetDetailVO ) throws KANException
   {
      return insert( "insertTimesheetDetail", timesheetDetailVO );
   }

   @Override
   public int updateTimesheetDetail( final TimesheetDetailVO timesheetDetailVO ) throws KANException
   {
      return update( "updateTimesheetDetail", timesheetDetailVO );
   }

   @Override
   public int deleteTimesheetDetail( final TimesheetDetailVO timesheetDetailVO ) throws KANException
   {
      return delete( "deleteTimesheetDetail", timesheetDetailVO );
   }

   @Override
   public List< Object > getTimesheetDetailVOsByHeaderId( String headerId ) throws KANException
   {
      return selectList( "getTimesheetDetailVOsByHeaderId", headerId );
   }
   
   @Override
   public List< Object > getTimesheetDetailVOsForReportByHeaderIds( List<String> selectedIds ) throws KANException
   {
      return selectList( "getTimesheetDetailVOsForReportByHeaderIds", selectedIds );
   }
   
   @Override
   public List< Object > getTimesheetDetailDaysForReportByHeaderIds(  List<String> selectedIds) throws KANException
   {
      return selectList( "getTimesheetDetailDaysByHeaderIds", selectedIds );
   }

}

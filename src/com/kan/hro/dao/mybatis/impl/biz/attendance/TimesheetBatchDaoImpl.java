package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.TimesheetBatchDao;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;

public class TimesheetBatchDaoImpl extends Context implements TimesheetBatchDao
{

   @Override
   public int countTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      return ( Integer ) select( "countTimesheetBatchVOsByCondition", timesheetBatchVO );
   }

   @Override
   public List< Object > getTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      return selectList( "getTimesheetBatchVOsByCondition", timesheetBatchVO );
   }

   @Override
   public List< Object > getTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getTimesheetBatchVOsByCondition", timesheetBatchVO, rowBounds );
   }

   @Override
   public TimesheetBatchVO getTimesheetBatchVOByBatchId( final String batchId ) throws KANException
   {
      return ( TimesheetBatchVO ) select( "getTimesheetBatchVOByBatchId", batchId );
   }

   @Override
   public int insertTimesheetBatch( final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      return insert( "insertTimesheetBatch", timesheetBatchVO );
   }

   @Override
   public int updateTimesheetBatch( final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      return update( "updateTimesheetBatch", timesheetBatchVO );
   }

   @Override
   public int deleteTimesheetBatch( final String batchId ) throws KANException
   {
      return delete( "deleteTimesheetBatch", batchId );
   }

   @Override
   public TimesheetBatchVO getTimesheetBatchVOByHeaderIds( String headerIds ) throws KANException
   {
      return ( TimesheetBatchVO ) select( "getTimesheetBatchVOByHeaderIds", headerIds );
   }

}

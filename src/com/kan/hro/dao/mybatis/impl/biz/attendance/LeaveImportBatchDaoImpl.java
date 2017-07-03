package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.LeaveImportBatchDao;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;

public class LeaveImportBatchDaoImpl extends Context implements LeaveImportBatchDao
{

   @Override
   public int countTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      return ( Integer ) select( "countLeaveImportBatchVOsByCondition", timesheetBatchVO );
   }

   @Override
   public List< Object > getTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      return selectList( "getLeaveImportBatchVOsByCondition", timesheetBatchVO );
   }

   @Override
   public List< Object > getTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getLeaveImportBatchVOsByCondition", timesheetBatchVO, rowBounds );
   }

   @Override
   public TimesheetBatchVO getTimesheetBatchVOByBatchId( final String batchId ) throws KANException
   {
      return ( TimesheetBatchVO ) select( "getLeaveImportBatchVOByBatchId", batchId );
   }

   @Override
   public void deleteLeaveImportDetailTempByBatchId( String batchId ) throws KANException
   {
      delete( "deleteLeaveImportDetailTempByBatchId", batchId );
   }

   @Override
   public void deleteLeaveImportHeaderTempByBatchId( String batchId ) throws KANException
   {
      delete( "deleteLeaveImportHeaderTempByBatchId", batchId );
   }

   @Override
   public void deleteCommonBatchById( String batchId ) throws KANException
   {
      delete( "deleteLeaveImportCommonBatchById", batchId );
   }

   @Override
   public void updateBathStatus( TimesheetBatchVO submitObject )
   {
      update( "updateLeaveImportBathStatus", submitObject );
   }

   @Override
   public List< String > countLeaveVOsByBatchId( String batchId ) throws KANException
   {
      final List< String > resultList = new ArrayList< String >();
      final List< Object > lists = selectList( "countLeaveImportVOsByCondition", batchId );
      if ( lists != null && lists.size() > 0 )
      {
         for ( Object o : lists )
         {
            resultList.add( ( String ) o );
         }
      }

      return resultList;
   }

}

package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.OTImportBatchDao;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;

public class OTImportBatchDaoImpl extends Context implements OTImportBatchDao
{

   @Override
   public int countTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      return ( Integer ) select( "countOTImportBatchVOsByCondition", timesheetBatchVO );
   }

   @Override
   public List< Object > getTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO ) throws KANException
   {
      return selectList( "getOTImportBatchVOsByCondition", timesheetBatchVO );
   }

   @Override
   public List< Object > getTimesheetBatchVOsByCondition( final TimesheetBatchVO timesheetBatchVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getOTImportBatchVOsByCondition", timesheetBatchVO, rowBounds );
   }

   @Override
   public TimesheetBatchVO getTimesheetBatchVOByBatchId( final String batchId ) throws KANException
   {
      return ( TimesheetBatchVO ) select( "getOTImportBatchVOByBatchId", batchId );
   }

   @Override
   public void deleteOTImportDetailTempByBatchId( final String batchId ) throws KANException
   {
      delete( "deleteOTImportDetailTempByBatchId", batchId );
   }

   @Override
   public void deleteOTImportHeaderTempByBatchId( final String batchId ) throws KANException
   {
      delete( "deleteOTImportHeaderTempByBatchId", batchId );
   }

   @Override
   public void deleteCommonBatchById( final String batchId ) throws KANException
   {
      delete( "deleteOTImportCommonBatchById", batchId );
   }

   @Override
   public void updateBathStatus( final TimesheetBatchVO submitObject )
   {
      update( "updateOTImportBathStatus", submitObject );
   }

   @Override
   public int countOTVOsByBatchId( final String batchId ) throws KANException
   {
      return ( Integer ) select( "countOTImportVOsByCondition", batchId );
   }

   @Override
   public int getBatchCountByHeaderId( final String batchId )
   {
      return ( Integer ) select( "getBatchCount", batchId );
   }

}

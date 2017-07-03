package com.kan.hro.dao.mybatis.impl.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.attendance.TimesheetHeaderDao;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetReportExportVO;

public class TimesheetHeaderDaoImpl extends Context implements TimesheetHeaderDao
{

   @Override
   public int countTimesheetHeaderVOsByCondition( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countTimesheetHeaderVOsByCondition", timesheetHeaderVO );
   }

   @Override
   public List< Object > getTimesheetHeaderVOsByCondition( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      return selectList( "getTimesheetHeaderVOsByCondition", timesheetHeaderVO );
   }

   @Override
   public List< Object > getTimesheetHeaderVOsByCondition( final TimesheetHeaderVO timesheetHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getTimesheetHeaderVOsByCondition", timesheetHeaderVO, rowBounds );
   }

   @Override
   public TimesheetHeaderVO getTimesheetHeaderVOByHeaderId( final String headerId ) throws KANException
   {
      return ( TimesheetHeaderVO ) select( "getTimesheetHeaderVOByHeaderId", headerId );
   }

   @Override
   public int insertTimesheetHeader( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      return insert( "insertTimesheetHeader", timesheetHeaderVO );
   }

   @Override
   public int updateTimesheetHeader( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      return update( "updateTimesheetHeader", timesheetHeaderVO );
   }

   @Override
   public int deleteTimesheetHeader( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      return delete( "deleteTimesheetHeader", timesheetHeaderVO );
   }

   @Override
   public List< Object > getTimesheetHeaderVOsByBatchId( final String batchId ) throws KANException
   {
      return selectList( "getTimesheetHeaderVOsByBatchId", batchId );
   }

   @Override
   public List< Object > getTimesheetHeaderVOsForReportByCondition( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      return selectList( "getTimesheetHeaderVOsForReportByCondition", timesheetHeaderVO );
   }

   @Override
   public List< Object > getTimesheetHeaderVOsForReportByCondition( final TimesheetHeaderVO timesheetHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getTimesheetHeaderVOsForReportByCondition", timesheetHeaderVO, rowBounds );
   }

   @Override
   public int countTimesheetReportExportVOsByCondition( TimesheetReportExportVO timesheetReportExportVO )
   {
      //      TimesheetHeaderVO timesheetHeaderVO = new TimesheetHeaderVO();
      return ( Integer ) select( "countTimesheetReportExportVOsByCondition", timesheetReportExportVO );
   }

   @Override
   public List< Object > getTimesheetReportExportVOsByCondition( TimesheetReportExportVO timesheetReportExportVO, RowBounds rowBounds )
   {
      return selectList( "getTimesheetReportExportVOsByCondition", timesheetReportExportVO, rowBounds );
   }

   @Override
   public List< Object > getTimesheetReportExportVOsByCondition( TimesheetReportExportVO timesheetReportExportVO )
   {
      return selectList( "getTimesheetReportExportVOsByCondition", timesheetReportExportVO );
   }

   @Override
   public TimesheetHeaderVO getTotalFullHoursAndWorkHoursOfYear( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException
   {
      return ( TimesheetHeaderVO ) select( "getTotalFullHoursAndWorkHoursOfYear", timesheetHeaderVO );
   }

   @Override
   public int countAVGTimesheetReportExportVOsByCondition( final TimesheetReportExportVO timesheetReportExportVO ) throws KANException
   {
      return ( Integer ) select( "countAVGTimesheetReportExportVOsByCondition", timesheetReportExportVO );
   }

   @Override
   public List< Object > getAVGTimesheetReportExportVOsByCondition( final TimesheetReportExportVO timesheetReportExportVO ) throws KANException
   {
      return selectList( "getAVGTimesheetReportExportVOsByCondition", timesheetReportExportVO );
   }

   @Override
   public List< Object > getAVGTimesheetReportExportVOsByCondition( final TimesheetReportExportVO timesheetReportExportVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getAVGTimesheetReportExportVOsByCondition", timesheetReportExportVO, rowBounds );
   }

}

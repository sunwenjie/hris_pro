package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetReportExportVO;

public interface TimesheetHeaderDao
{
   public abstract int countTimesheetHeaderVOsByCondition( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract List< Object > getTimesheetHeaderVOsByCondition( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract List< Object > getTimesheetHeaderVOsByCondition( final TimesheetHeaderVO timesheetHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract TimesheetHeaderVO getTimesheetHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertTimesheetHeader( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract int updateTimesheetHeader( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract int deleteTimesheetHeader( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract List< Object > getTimesheetHeaderVOsByBatchId( final String batchId ) throws KANException;

   public abstract int countTimesheetReportExportVOsByCondition( TimesheetReportExportVO timesheetReportExportVO );

   public abstract List< Object > getTimesheetReportExportVOsByCondition( TimesheetReportExportVO object, RowBounds rowBounds );

   public abstract List< Object > getTimesheetReportExportVOsByCondition( TimesheetReportExportVO timesheetReportExportVO );

   public abstract List< Object > getTimesheetHeaderVOsForReportByCondition( TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract List< Object > getTimesheetHeaderVOsForReportByCondition( TimesheetHeaderVO object, RowBounds rowBounds ) throws KANException;

   public abstract TimesheetHeaderVO getTotalFullHoursAndWorkHoursOfYear( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract int countAVGTimesheetReportExportVOsByCondition( final TimesheetReportExportVO timesheetReportExportVO ) throws KANException;

   public abstract List< Object > getAVGTimesheetReportExportVOsByCondition( final TimesheetReportExportVO timesheetReportExportVO ) throws KANException;

   public abstract List< Object > getAVGTimesheetReportExportVOsByCondition( final TimesheetReportExportVO timesheetReportExportVO, final RowBounds rowBounds ) throws KANException;
}

package com.kan.hro.service.inf.biz.attendance;

import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.TimesheetBatchVO;
import com.kan.hro.domain.biz.attendance.TimesheetDTO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.attendance.TimesheetReportExportVO;

public interface TimesheetHeaderService
{
   public abstract PagedListHolder getTimesheetHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract TimesheetHeaderVO getTimesheetHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertTimesheetHeader( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract int insertTimesheetDTO( final List< TimesheetDTO > timesheetDTOs, final TimesheetBatchVO timesheetBatchVO ) throws KANException;

   public abstract int insertTimesheetDTO( final List< TimesheetDTO > timesheetDTOs ) throws KANException;

   public abstract int updateTimesheetHeader( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract int deleteTimesheetHeader( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract List< TimesheetDTO > getTimesheetDTOsByCondition( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;
   
   public abstract int generateTimesheet() throws KANException;

   public abstract int generateTimesheet( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract int generateTimesheet( final TimesheetHeaderVO timesheetHeaderVO, final TimesheetBatchVO timesheetBatchVO ) throws KANException;

   public abstract int submitTimesheetHeader( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract int submit_header( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract List< Object > getTimesheetDetailDaysForReportByHeaderIds( final Object object ) throws KANException;

   public abstract List< Object > getTimesheetDetailVOsForReportByHeaderIds( final Object object ) throws KANException;

   public abstract XSSFWorkbook timeSheetReport( final List< Object > listTimesheetDatys, final List< Object > listTimesheetDetailVOs,
         final PagedListHolder timesheetHeaderlHolder, final TimesheetReportExportVO timesheetHeaderVO ) throws KANException;

   public abstract PagedListHolder getTimesheetReportExportVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged );

   public abstract TimesheetHeaderVO getTotalFullHoursAndWorkHoursOfYear( final String employeeId ) throws KANException;

   public abstract List< Object > getTimesheetHeaderVOsByCondition( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract PagedListHolder getAVGTimesheetReportExportVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

}

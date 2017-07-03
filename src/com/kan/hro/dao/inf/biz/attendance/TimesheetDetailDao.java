package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.TimesheetDetailVO;

public interface TimesheetDetailDao
{
   public abstract int countTimesheetDetailVOsByCondition( final TimesheetDetailVO timesheetDetailVO ) throws KANException;

   public abstract List< Object > getTimesheetDetailVOsByCondition( final TimesheetDetailVO timesheetDetailVO ) throws KANException;

   public abstract List< Object > getTimesheetDetailVOsByCondition( final TimesheetDetailVO timesheetDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract TimesheetDetailVO getTimesheetDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertTimesheetDetail( final TimesheetDetailVO timesheetDetailVO ) throws KANException;

   public abstract int updateTimesheetDetail( final TimesheetDetailVO timesheetDetailVO ) throws KANException;

   public abstract int deleteTimesheetDetail( final TimesheetDetailVO timesheetDetailVO ) throws KANException;

   public abstract List< Object > getTimesheetDetailVOsByHeaderId( final String headerId ) throws KANException;

   public abstract List< Object > getTimesheetDetailVOsForReportByHeaderIds( List< String > selectedIds ) throws KANException;
   
   public abstract List< Object > getTimesheetDetailDaysForReportByHeaderIds( List< String > selectedIds ) throws KANException;
}

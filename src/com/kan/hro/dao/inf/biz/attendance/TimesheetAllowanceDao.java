package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.TimesheetAllowanceVO;

public interface TimesheetAllowanceDao
{
   public abstract int countTimesheetAllowanceVOsByCondition( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException;

   public abstract List< Object > getTimesheetAllowanceVOsByCondition( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException;

   public abstract List< Object > getTimesheetAllowanceVOsByCondition( final TimesheetAllowanceVO timesheetAllowanceVO, final RowBounds rowBounds ) throws KANException;

   public abstract TimesheetAllowanceVO getTimesheetAllowanceVOByAllowanceId( final String allowanceId ) throws KANException;

   public abstract int insertTimesheetAllowance( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException;

   public abstract int updateTimesheetAllowance( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException;

   public abstract int deleteTimesheetAllowance( final TimesheetAllowanceVO timesheetAllowanceVO ) throws KANException;

   public abstract List< Object > getTimesheetAllowanceVOsByHeaderId( final String headerId ) throws KANException;

   public abstract List< Object > getTimesheetAllowanceTempVOsByCondition( final TimesheetAllowanceVO timesheetAllowanceVO) throws KANException;
}

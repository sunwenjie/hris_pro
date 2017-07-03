package com.kan.hro.dao.inf.biz.attendance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;

public interface TimesheetAllowanceHeaderDao
{
   public abstract int countTimesheetHeaderVOsByCondition( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract List< Object > getTimesheetHeaderVOsByCondition( final TimesheetHeaderVO timesheetHeaderVO ) throws KANException;

   public abstract List< Object > getTimesheetHeaderVOsByCondition( final TimesheetHeaderVO timesheetHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract void updateAllowanceBase(String allowanceId, String base);

   public abstract int deleteAllowanceTempByAllowanceIds(List<String> allowanceIds)throws KANException;

   public abstract void deleteEmptyAllowanceHeaderTempBybatchId(String batchId)throws KANException;

   public abstract int getHeaderCountByBatchId(String batchId);
}

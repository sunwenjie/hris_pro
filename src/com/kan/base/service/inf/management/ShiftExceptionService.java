package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.ShiftExceptionVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ShiftExceptionService
{
   public abstract PagedListHolder getShiftExceptionVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ShiftExceptionVO getShiftExceptionVOByExceptionId( final String exceptionId ) throws KANException;

   public abstract int insertShiftException( final ShiftExceptionVO shiftExceptionVO ) throws KANException;

   public abstract int updateShiftException( final ShiftExceptionVO shiftExceptionVO ) throws KANException;

   public abstract int deleteShiftExceptionl( final ShiftExceptionVO shiftExceptionVO ) throws KANException;

   public abstract List< Object > getAvailableShiftExceptions( final ShiftExceptionVO shiftExceptionVO ) throws KANException;
}

package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.ShiftExceptionVO;
import com.kan.base.util.KANException;

public interface ShiftExceptionDao
{
   public abstract int countShiftExceptionVOsByCondition( final ShiftExceptionVO shiftExceptionVO ) throws KANException;

   public abstract List< Object > getShiftExceptionVOsByCondition( final ShiftExceptionVO shiftExceptionVO ) throws KANException;

   public abstract List< Object > getShiftExceptionVOsByCondition( final ShiftExceptionVO shiftExceptionVO, final RowBounds rowBounds ) throws KANException;

   public abstract ShiftExceptionVO getShiftExceptionVOByExceptionId( final String exceptionId ) throws KANException;

   public abstract int insertShiftException( final ShiftExceptionVO shiftExceptionVO ) throws KANException;

   public abstract int updateShiftException( final ShiftExceptionVO shiftExceptionVO ) throws KANException;

   public abstract int deleteShiftException( final ShiftExceptionVO shiftExceptionVO ) throws KANException;

   public abstract List< Object > getShiftExceptionVOsByHeaderId( final String headerId ) throws KANException;
}

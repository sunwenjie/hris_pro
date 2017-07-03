package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.ShiftDetailVO;
import com.kan.base.util.KANException;

public interface ShiftDetailDao
{
   public abstract int countShiftDetailVOsByCondition( final ShiftDetailVO shiftDetailVO ) throws KANException;

   public abstract List< Object > getShiftDetailVOsByCondition( final ShiftDetailVO shiftDetailVO ) throws KANException;

   public abstract List< Object > getShiftDetailVOsByCondition( final ShiftDetailVO shiftDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract ShiftDetailVO getShiftDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertShiftDetail( final ShiftDetailVO shiftDetailVO ) throws KANException;

   public abstract int updateShiftDetail( final ShiftDetailVO shiftDetailVO ) throws KANException;

   public abstract int deleteShiftDetail( final ShiftDetailVO shiftDetailVO ) throws KANException;

   public abstract List< Object > getShiftDetailVOsByHeaderId( final String headerId ) throws KANException;
}

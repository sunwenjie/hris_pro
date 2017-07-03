package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.ShiftHeaderVO;
import com.kan.base.util.KANException;

public interface ShiftHeaderDao
{
   public abstract int countShiftHeaderVOsByCondition( final ShiftHeaderVO shiftHeaderVO ) throws KANException;

   public abstract List< Object > getShiftHeaderVOsByCondition( final ShiftHeaderVO shiftHeaderVO ) throws KANException;

   public abstract List< Object > getShiftHeaderVOsByCondition( final ShiftHeaderVO shiftHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract ShiftHeaderVO getShiftHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertShiftHeader( final ShiftHeaderVO shiftHeaderVO ) throws KANException;

   public abstract int updateShiftHeader( final ShiftHeaderVO shiftHeaderVO ) throws KANException;

   public abstract int deleteShiftHeader( final ShiftHeaderVO shiftHeaderVO ) throws KANException;

   public abstract List< Object > getShiftHeaderVOsByAccountId( final String accountId ) throws KANException;
}

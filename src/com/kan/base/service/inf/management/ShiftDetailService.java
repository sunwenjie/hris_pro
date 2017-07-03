package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.ShiftDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ShiftDetailService
{
   public abstract PagedListHolder getShiftDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ShiftDetailVO getShiftDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertShiftDetail( final ShiftDetailVO shiftDetailVO ) throws KANException;

   public abstract int updateShiftDetail( final ShiftDetailVO shiftDetailVO ) throws KANException;

   public abstract int deleteShiftDetail( final ShiftDetailVO shiftDetailVO ) throws KANException;

   public abstract List< Object > getAvailableShiftDetailVOs( final ShiftDetailVO shiftDetailVO ) throws KANException;
}

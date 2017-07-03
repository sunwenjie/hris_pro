package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.ShiftDTO;
import com.kan.base.domain.management.ShiftHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ShiftHeaderService
{
   public abstract PagedListHolder getShiftHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ShiftHeaderVO getShiftHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertShiftHeader( final ShiftHeaderVO shiftHeaderVO ) throws KANException;

   public abstract int updateShiftHeader( final ShiftHeaderVO shiftHeaderVO ) throws KANException;

   public abstract int deleteShiftHeader( final ShiftHeaderVO shiftHeaderVO ) throws KANException;

   public abstract List< Object > getAvailableShiftHeaderVOs( final ShiftHeaderVO shiftHeaderVO ) throws KANException;

   public abstract List< ShiftDTO > getShiftDTOsByAccountId( final String accountId ) throws KANException;
}

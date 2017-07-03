package com.kan.hro.service.inf.biz.settlement;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;

public interface AdjustmentHeaderService
{
   public abstract PagedListHolder getAdjustmentHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract AdjustmentHeaderVO getAdjustmentHeaderVOByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException;

   public abstract int updateAdjustmentHeader( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException;

   public abstract int insertAdjustmentHeader( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException;

   public abstract int deleteAdjustmentHeader( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException;
   
   public abstract int submitAdjustmentHeader( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException;
   
   public abstract List< Object > getAdjustmentHeaderVOsByAccountId( final String accountId )throws KANException;
}

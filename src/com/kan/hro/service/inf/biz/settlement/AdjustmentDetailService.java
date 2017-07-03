package com.kan.hro.service.inf.biz.settlement;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.AdjustmentDetailVO;

public interface AdjustmentDetailService
{
   public abstract PagedListHolder getAdjustmentDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract AdjustmentDetailVO getAdjustmentDetailVOByAdjustmentDetailId( final String adjustmentDetailId ) throws KANException;

   public abstract int updateAdjustmentDetail( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException;

   public abstract int insertAdjustmentDetail( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException;

   public abstract int deleteAdjustmentDetail( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException;
}

package com.kan.hro.service.inf.biz.sb;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBAdjustmentDetailVO;

public interface SBAdjustmentDetailService
{
   public abstract PagedListHolder getSBAdjustmentDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SBAdjustmentDetailVO getSBAdjustmentDetailVOByAdjustmentDetailId( final String adjustmentDetailId ) throws KANException;

   public abstract int updateSBAdjustmentDetail( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException;

   public abstract int insertSBAdjustmentDetail( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException;

   public abstract int deleteSBAdjustmentDetail( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException;

   public abstract List< Object > getSBAdjustmentDetailVOsByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException;
}

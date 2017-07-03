package com.kan.hro.dao.inf.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.AdjustmentDetailVO;

public interface AdjustmentDetailDao
{
   public abstract int countAdjustmentDetailVOsByCondition(final AdjustmentDetailVO adjustmentDetailVO) throws KANException ; 
   
   public abstract List< Object > getAdjustmentDetailVOsByCondition( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException;

   public abstract List< Object > getAdjustmentDetailVOsByCondition( final AdjustmentDetailVO adjustmentDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract AdjustmentDetailVO getAdjustmentDetailVOByAdjustmentDetailId( final String adjustmentDetailId ) throws KANException;
   
   public abstract List< Object > getAdjustmentDetailVOsByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException;

   public abstract int insertAdjustmentDetail( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException;

   public abstract int updateAdjustmentDetail( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException;

   public abstract int deleteAdjustmentDetail( final AdjustmentDetailVO adjustmentDetailVO ) throws KANException;
}

package com.kan.hro.dao.inf.biz.settlement;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;

public interface AdjustmentHeaderDao
{
   public abstract int countAdjustmentHeaderVOsByCondition(final AdjustmentHeaderVO adjustmentHeaderVO) throws KANException ; 
   
   public abstract List< Object > getAdjustmentHeaderVOsByCondition( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException;

   public abstract List< Object > getAdjustmentHeaderVOsByCondition( final AdjustmentHeaderVO adjustmentHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getPaymentAdjustmentHeaderVOsByCondition( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException;
   
   public abstract AdjustmentHeaderVO getAdjustmentHeaderVOByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException;
   
   public abstract List< Object > getAdjustmentHeaderVOsByAccountId( final String accountId ) throws KANException;

   public abstract int insertAdjustmentHeader( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException;

   public abstract int updateAdjustmentHeader( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException;

   public abstract int deleteAdjustmentHeader( final AdjustmentHeaderVO adjustmentHeaderVO ) throws KANException;
   
}

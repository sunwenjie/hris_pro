package com.kan.hro.dao.inf.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBAdjustmentDetailVO;

public interface SBAdjustmentDetailDao
{
   public abstract int countSBAdjustmentDetailVOsByCondition( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException;

   public abstract List< Object > getSBAdjustmentDetailVOsByCondition( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException;

   public abstract List< Object > getSBAdjustmentDetailVOsByCondition( final SBAdjustmentDetailVO sbAdjustmentDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract SBAdjustmentDetailVO getSBAdjustmentDetailVOByAdjustmentDetailId( final String adjustmentDetailId ) throws KANException;

   public abstract List< Object > getSBAdjustmentDetailVOsByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException;

   public abstract int insertSBAdjustmentDetail( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException;

   public abstract int updateSBAdjustmentDetail( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException;

   public abstract int deleteSBAdjustmentDetail( final SBAdjustmentDetailVO sbAdjustmentDetailVO ) throws KANException;

}

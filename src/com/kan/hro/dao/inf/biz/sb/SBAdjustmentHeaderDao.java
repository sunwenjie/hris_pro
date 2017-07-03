package com.kan.hro.dao.inf.biz.sb;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.sb.SBAdjustmentHeaderVO;

public interface SBAdjustmentHeaderDao
{
   public abstract int countSBAdjustmentHeaderVOsByCondition( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException;

   public abstract List< Object > getSBAdjustmentHeaderVOsByCondition( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException;

   public abstract List< Object > getSBAdjustmentHeaderVOsByCondition( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract SBAdjustmentHeaderVO getSBAdjustmentHeaderVOByAdjustmentHeaderId( final String adjustmentHeaderId ) throws KANException;

   public abstract List< Object > getSBAdjustmentHeaderVOsByAccountId( final String accountId ) throws KANException;

   public abstract int insertSBAdjustmentHeader( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException;

   public abstract int updateSBAdjustmentHeader( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException;

   public abstract int deleteSBAdjustmentHeader( final SBAdjustmentHeaderVO sbAdjustmentHeaderVO ) throws KANException;

}

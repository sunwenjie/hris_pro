package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.IncomeTaxRangeDetailVO;
import com.kan.base.util.KANException;

public interface IncomeTaxRangeDetailDao
{
   public abstract int countIncomeTaxRangeDetailVOsByCondition( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException;

   public abstract List< Object > getIncomeTaxRangeDetailVOsByCondition( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException;

   public abstract List< Object > getIncomeTaxRangeDetailVOsByCondition( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO, final RowBounds rowBounds ) throws KANException;

   public abstract IncomeTaxRangeDetailVO getIncomeTaxRangeDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int insertIncomeTaxRangeDetail( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException;

   public abstract int updateIncomeTaxRangeDetail( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException;

   public abstract int deleteIncomeTaxRangeDetail( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException;

}

package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.IncomeTaxRangeHeaderVO;
import com.kan.base.util.KANException;

public interface IncomeTaxRangeHeaderDao
{
   public abstract int countIncomeTaxRangeHeaderVOsByCondition( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException;

   public abstract List< Object > getIncomeTaxRangeHeaderVOsByCondition( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException;

   public abstract List< Object > getIncomeTaxRangeHeaderVOsByCondition( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract IncomeTaxRangeHeaderVO getIncomeTaxRangeHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int insertIncomeTaxRangeHeader( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException;

   public abstract int updateIncomeTaxRangeHeader( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException;

   public abstract int deleteIncomeTaxRangeHeader( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException;

}

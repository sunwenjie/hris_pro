package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.IncomeTaxBaseVO;
import com.kan.base.util.KANException;

public interface IncomeTaxBaseDao
{
   public abstract int countIncomeTaxBaseVOsByCondition( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException;

   public abstract List< Object > getIncomeTaxBaseVOsByCondition( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException;

   public abstract List< Object > getIncomeTaxBaseVOsByCondition( final IncomeTaxBaseVO incomeTaxBaseVO, final RowBounds rowBounds ) throws KANException;

   public abstract IncomeTaxBaseVO getIncomeTaxBaseVOByBaseId( final String baseId ) throws KANException;

   public abstract int insertIncomeTaxBase( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException;

   public abstract int updateIncomeTaxBase( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException;

   public abstract int deleteIncomeTaxBase( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException;
}

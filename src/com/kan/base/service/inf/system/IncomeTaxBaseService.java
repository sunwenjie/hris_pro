package com.kan.base.service.inf.system;

import java.util.List;

import com.kan.base.domain.system.IncomeTaxBaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface IncomeTaxBaseService
{
   public abstract List< Object > getIncomeTaxBaseVOsByCondition( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException;

   public abstract PagedListHolder getIncomeTaxBaseVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract IncomeTaxBaseVO getIncomeTaxBaseVOByBaseId( final String baseId ) throws KANException;

   public abstract int insertIncomeTaxBase( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException;

   public abstract int updateIncomeTaxBase( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException;

   public abstract int deleteIncomeTaxBase( final IncomeTaxBaseVO incomeTaxBaseVO ) throws KANException;
}

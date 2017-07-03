package com.kan.base.service.inf.system;

import java.util.List;

import com.kan.base.domain.system.IncomeTaxRangeDTO;
import com.kan.base.domain.system.IncomeTaxRangeHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface IncomeTaxRangeHeaderService
{
   public abstract List< Object > getIncomeTaxRangeHeaderVOsByCondition( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException;
   
   public abstract PagedListHolder getIncomeTaxRangeHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract IncomeTaxRangeHeaderVO getIncomeTaxRangeHeaderVOByHeaderId( final String headerId ) throws KANException;

   public abstract int updateIncomeTaxRangeHeader( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException;

   public abstract int insertIncomeTaxRangeHeader( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException;

   public abstract void deleteIncomeTaxRangeHeader( final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO ) throws KANException;

   public abstract List< IncomeTaxRangeDTO > getIncomeTaxRangeDTOs() throws KANException;
}

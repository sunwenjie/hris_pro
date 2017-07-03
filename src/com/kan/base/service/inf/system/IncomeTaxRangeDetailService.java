package com.kan.base.service.inf.system;

import com.kan.base.domain.system.IncomeTaxRangeDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface IncomeTaxRangeDetailService
{
   public abstract PagedListHolder getIncomeTaxRangeDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract IncomeTaxRangeDetailVO getIncomeTaxRangeDetailVOByDetailId( final String detailId ) throws KANException;

   public abstract int updateIncomeTaxRangeDetail( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException;

   public abstract int insertIncomeTaxRangeDetail( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException;

   public abstract void deleteIncomeTaxRangeDetail( final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO ) throws KANException;

}

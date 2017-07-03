package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.TaxVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface TaxService
{
   public abstract PagedListHolder getTaxVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract TaxVO getTaxVOByTaxId( final String taxId ) throws KANException;

   public abstract int insertTax( final TaxVO taxVO ) throws KANException;

   public abstract int updateTax( final TaxVO taxVO ) throws KANException;

   public abstract int deleteTax( final TaxVO taxVO ) throws KANException;

   public abstract List< Object > getTaxVOsByAccountId( final String aCCOUNT_ID ) throws KANException;

   public abstract List< Object > getTaxVOsByTaxVO( final TaxVO taxVO ) throws KANException;
}

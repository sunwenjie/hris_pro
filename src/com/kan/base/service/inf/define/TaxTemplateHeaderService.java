package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.TaxTemplateDTO;
import com.kan.base.domain.define.TaxTemplateHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface TaxTemplateHeaderService
{
   public abstract PagedListHolder getTaxTemplateHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract TaxTemplateHeaderVO getTaxTemplateHeaderVOByTemplateHeaderId( final String templateHeaderId ) throws KANException;

   public abstract int insertTaxTemplateHeader( final TaxTemplateHeaderVO taxTemplateHeaderVO ) throws KANException;

   public abstract int updateTaxTemplateHeader( final TaxTemplateHeaderVO taxTemplateHeaderVO ) throws KANException;

   public abstract int deleteTaxTemplateHeader( final TaxTemplateHeaderVO taxTemplateHeaderVO ) throws KANException;

   public abstract List< TaxTemplateDTO > getTaxTemplateDTOsByAccountId( final String accountId ) throws KANException;

}

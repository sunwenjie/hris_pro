package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.TaxTemplateDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface TaxTemplateDetailService
{
   public abstract PagedListHolder getTaxTemplateDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract TaxTemplateDetailVO getTaxTemplateDetailVOByTemplateDetailId( final String templateDetailId ) throws KANException;

   public abstract int insertTaxTemplateDetail( final TaxTemplateDetailVO taxTemplateDetailVO ) throws KANException;

   public abstract int updateTaxTemplateDetail( final TaxTemplateDetailVO taxTemplateDetailVO ) throws KANException;

   public abstract int deleteTaxTemplateDetail( final TaxTemplateDetailVO taxTemplateDetailVO ) throws KANException;

   public abstract List< Object > getTaxTemplateDetailVOsByTemplateHeaderId( final String templateHeaderId ) throws KANException;
}

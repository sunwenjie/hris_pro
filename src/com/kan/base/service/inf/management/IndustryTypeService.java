package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.IndustryTypeVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface IndustryTypeService
{
   public abstract PagedListHolder getIndustryTypeVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract IndustryTypeVO getIndustryTypeVOByIndustryTypeId( final String industryTypeId ) throws KANException;

   public abstract int insertIndustryType( final IndustryTypeVO industryTypeVO ) throws KANException;

   public abstract int updateIndustryType( final IndustryTypeVO industryTypeVO ) throws KANException;

   public abstract int deleteIndustryType( final IndustryTypeVO industryTypeVO ) throws KANException;

   public abstract List< Object > getIndustryTypeVOsByAccountId( final String accountId ) throws KANException;

   public abstract List< IndustryTypeVO > getIndustryTypeVOsByIndustryTypeVO( final IndustryTypeVO industryTypeVO ) throws KANException;
}

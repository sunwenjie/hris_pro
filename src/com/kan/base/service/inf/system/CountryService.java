package com.kan.base.service.inf.system;

import java.util.List;

import com.kan.base.domain.system.CountryVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface CountryService
{
   public abstract PagedListHolder getCountryVOByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract CountryVO getCountryVOByCountryId( final int countryId ) throws KANException;

   public abstract int insertCountry( final CountryVO countryVO ) throws KANException;

   public abstract int updateCountry( final CountryVO countryVO ) throws KANException;

   public abstract int deleteCountry( final CountryVO countryVO ) throws KANException;
   
   public abstract List< Object > getCountryVOs( ) throws KANException;
   
}

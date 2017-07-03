package com.kan.base.service.inf.system;

import java.util.List;

import com.kan.base.domain.system.CityVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface CityService
{
   public abstract PagedListHolder getCityVOByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;
   
   public abstract CityVO getCityVOByCityId(final int cityId) throws KANException;

   public abstract int insertCity( final CityVO cityVO ) throws KANException;

   public abstract int updateCity( final CityVO cityVO ) throws KANException;

   public abstract int deleteCity( final CityVO cityVO ) throws KANException;
   
   public abstract List< Object > getCityVOsByProvinceId(final int provinceId) throws KANException;
   
}

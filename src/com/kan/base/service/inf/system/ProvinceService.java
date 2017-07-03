package com.kan.base.service.inf.system;


import java.util.List;

import com.kan.base.domain.system.ProvinceVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ProvinceService
{
   public abstract PagedListHolder getProvinceVOByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ProvinceVO getProvinceVOByProvinceId( final int provinceId ) throws KANException;

   public abstract int insertProvince( final ProvinceVO provinceVO ) throws KANException;

   public abstract int updateProvince( final ProvinceVO provinceVO ) throws KANException;

   public abstract int deleteProvince( final ProvinceVO provinceVO ) throws KANException;
   
   public abstract List< Object > getProvinceVOsByCountryId( final int countryId) throws KANException;
   
}

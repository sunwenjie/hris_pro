package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.CountryVO;
import com.kan.base.util.KANException;

public interface CountryDao
{
                       
   public abstract int countCountryVOByCondition(final CountryVO countryVO) throws KANException ; 
   
   public abstract List< Object > getCountryVOByCondition( final CountryVO countryVO ) throws KANException;

   public abstract List< Object > getCountryVOByCondition( final CountryVO countryVO, RowBounds rowBounds ) throws KANException;

   public abstract CountryVO getCountryVOByCountryId( final int cityId ) throws KANException;

   public abstract int insertCountry( final CountryVO countryVO ) throws KANException;

   public abstract int updateCountry( final CountryVO countryVO ) throws KANException;

   public abstract int deleteCountry( final CountryVO countryVO ) throws KANException;
   
   public abstract List< Object > getCountryVOs() throws KANException; 

}

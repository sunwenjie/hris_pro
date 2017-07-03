package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.CityVO;
import com.kan.base.util.KANException;

public interface CityDao
{
   public abstract int countCityVOByCondition(final CityVO cityVO) throws KANException ; 
   
   public abstract List< Object > getCityVOByCondition( final CityVO cityVO ) throws KANException;

   public abstract List< Object > getCityVOByCondition( final CityVO cityVO, RowBounds rowBounds ) throws KANException;

   public abstract CityVO getCityVOByCityId( final int countryId ) throws KANException;

   public abstract int insertCity( final CityVO cityVO ) throws KANException;

   public abstract int updateCity( final CityVO cityVO ) throws KANException;

   public abstract int deleteCity( final CityVO cityVO ) throws KANException;
   
   public abstract List< Object > getCityVOsByProvinceId( final int provinceId ) throws KANException;
}

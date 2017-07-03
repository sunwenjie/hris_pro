package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.CityDao;
import com.kan.base.domain.system.CityVO;
import com.kan.base.util.KANException;

public class CityDaoImpl extends Context implements CityDao
{

   @Override
   public int countCityVOByCondition( final CityVO cityVO ) throws KANException
   {

      return ( Integer ) select( "countCityVOByCondition", cityVO );
   }

   @Override
   public List< Object > getCityVOByCondition( final CityVO cityVO ) throws KANException
   {

      return selectList( "getCityVOByCondition", cityVO );
   }

   @Override
   public List< Object > getCityVOByCondition( final CityVO cityVO, final RowBounds rowBounds ) throws KANException
   {

      return selectList( "getCityVOByCondition", cityVO, rowBounds );
   }

   @Override
   public CityVO getCityVOByCityId( final int cityId ) throws KANException
   {

      return ( CityVO ) select( "getCityVOByCityId", cityId );
   }

   @Override
   public int insertCity( final CityVO cityVO ) throws KANException
   {

      return insert( "insertCity", cityVO );
   }

   @Override
   public int updateCity( final CityVO cityVO ) throws KANException
   {

      return update( "updateCity", cityVO );
   }

   @Override
   public int deleteCity( CityVO cityVO ) throws KANException
   {

      return delete( "deleteCity", cityVO );
   }

   @Override
   public List< Object > getCityVOsByProvinceId( int provinceId ) throws KANException
   {
      return selectList( "getCityVOsByProvinceId", provinceId );
   }

}

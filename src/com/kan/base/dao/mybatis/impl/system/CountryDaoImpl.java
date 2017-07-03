package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.CountryDao;
import com.kan.base.domain.system.CountryVO;
import com.kan.base.util.KANException;

public class CountryDaoImpl extends Context implements CountryDao
{

   @Override
   public int countCountryVOByCondition( final CountryVO countryVO ) throws KANException
   {

      return ( Integer ) select( "countCountryVOByCondition", countryVO );
   }

   @Override
   public List< Object > getCountryVOByCondition( final CountryVO countryVO ) throws KANException
   {

      return selectList( "getCountryVOByCondition", countryVO );
   }

   @Override
   public List< Object > getCountryVOByCondition( final CountryVO countryVO, RowBounds rowBounds ) throws KANException
   {

      return selectList( "getCountryVOByCondition", countryVO, rowBounds );
   }

   @Override
   public CountryVO getCountryVOByCountryId( final int countryId ) throws KANException
   {

      return ( CountryVO ) select( "getCountryVOByCountryId", countryId );
   }

   @Override
   public int insertCountry( final CountryVO countryVO ) throws KANException
   {

      return insert( "insertCountry", countryVO );
   }

   @Override
   public int updateCountry( final CountryVO countryVO ) throws KANException
   {

      return update( "updateCountry", countryVO );
   }

   @Override
   public int deleteCountry( final CountryVO countryVO ) throws KANException
   {

      return delete( "deleteCountry", countryVO );
   }

   @Override
   public List< Object > getCountryVOs() throws KANException
   {
      return selectList( "getCountryVOs", null );
   }

}

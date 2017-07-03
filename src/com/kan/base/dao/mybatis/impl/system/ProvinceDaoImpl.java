package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.ProvinceDao;
import com.kan.base.domain.system.ProvinceVO;
import com.kan.base.util.KANException;

public class ProvinceDaoImpl extends Context implements ProvinceDao
{

   @Override
   public int countProvinceVOByCondition( final ProvinceVO provinceVO ) throws KANException
   {

      return ( Integer ) select( "countProvinceVOByCondition", provinceVO );
   }

   @Override
   public List< Object > getProvinceVOByCondition( final ProvinceVO provinceVO ) throws KANException
   {

      return selectList( "getProvinceVOByCondition", provinceVO );
   }

   @Override
   public List< Object > getProvinceVOByCondition( final ProvinceVO provinceVO, RowBounds rowBounds ) throws KANException
   {

      return selectList( "getProvinceVOByCondition", provinceVO, rowBounds );
   }

  
   @Override
   public ProvinceVO getProvinceVOByProvinceId(final int provinceId ) throws KANException
   {
      // TODO Auto-generated method stub
      return ( ProvinceVO ) select( "getProvinceVOByProvinceId", provinceId );
   }

   @Override
   public int insertProvince( final ProvinceVO provinceVO ) throws KANException
   {

      return insert( "insertProvince", provinceVO );
   }

   @Override
   public int updateProvince( final ProvinceVO provinceVO ) throws KANException
   {

      return update( "updateProvince", provinceVO );
   }

   @Override
   public int deleteProvince( final ProvinceVO provinceVO ) throws KANException
   {

      return delete( "deleteProvince", provinceVO );
   }

   @Override
   public List< Object > getProvinceVOsByCountryId( int countryId ) throws KANException
   {
      return selectList( "getProvinceVOsByCountryId", countryId );
   }

}

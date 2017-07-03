package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.ProvinceVO;
import com.kan.base.util.KANException;

public interface ProvinceDao
{
   public abstract int countProvinceVOByCondition( final ProvinceVO provinceVO ) throws KANException;

   public abstract List< Object > getProvinceVOByCondition( final ProvinceVO provinceVO ) throws KANException;

   public abstract List< Object > getProvinceVOByCondition( final ProvinceVO provinceVO,final RowBounds rowBounds) throws KANException;

   public abstract ProvinceVO getProvinceVOByProvinceId( final int provinceId ) throws KANException;
   
   public abstract int insertProvince( final ProvinceVO provinceVO ) throws KANException;

   public abstract int updateProvince( final ProvinceVO provinceVO ) throws KANException;

   public abstract int deleteProvince( final ProvinceVO provinceVO ) throws KANException;
   
   public abstract List< Object > getProvinceVOsByCountryId( final int countryId ) throws KANException;

}

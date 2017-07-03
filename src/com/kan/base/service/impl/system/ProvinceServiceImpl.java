package com.kan.base.service.impl.system;


import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.CityDao;
import com.kan.base.dao.inf.system.ProvinceDao;
import com.kan.base.domain.system.CityVO;
import com.kan.base.domain.system.ProvinceVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.ProvinceService;
import com.kan.base.util.KANException;

public class ProvinceServiceImpl extends ContextService implements ProvinceService
{

   private CityDao cityDao;

   public CityDao getCityDao()
   {
      return cityDao;
   }

   public void setCityDao( CityDao cityDao )
   {
      this.cityDao = cityDao;
   }

   @Override
   public PagedListHolder getProvinceVOByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final ProvinceDao provinceDao = ( ProvinceDao ) getDao();
      pagedListHolder.setHolderSize( provinceDao.countProvinceVOByCondition( ( ( ProvinceVO ) pagedListHolder.getObject() ) ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( provinceDao.getProvinceVOByCondition( ( ProvinceVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( provinceDao.getProvinceVOByCondition( ( ProvinceVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public int insertProvince( ProvinceVO provinceVO ) throws KANException
   {

      return ( ( ProvinceDao ) getDao() ).insertProvince( provinceVO );
   }

   @Override
   public ProvinceVO getProvinceVOByProvinceId( int provinceId ) throws KANException
   {

      return ( ProvinceVO ) ( ( ProvinceDao ) getDao() ).getProvinceVOByProvinceId( provinceId );
   }

   @Override
   public int updateProvince( ProvinceVO provinceVO ) throws KANException
   {

      return ( ( ProvinceDao ) getDao() ).updateProvince( provinceVO );
   }

   @Override
   public int deleteProvince( ProvinceVO provinceVO ) throws KANException
   {
      try
      {
         if ( provinceVO.getCountryId() != null && !provinceVO.getCountryId().trim().equals( "" ) )
         {
            startTransaction();

            CityVO cityVO = new CityVO();
            cityVO.setProvinceId( provinceVO.getProvinceId() );

            for ( Object objCityVO : this.cityDao.getCityVOByCondition( cityVO ) )
            {
               ( ( CityVO ) objCityVO ).setModifyBy( provinceVO.getModifyBy() );
               ( ( CityVO ) objCityVO ).setModifyDate( provinceVO.getModifyDate() );

               this.cityDao.deleteCity( ( ( CityVO ) objCityVO ) );
            }         
            ( ( ProvinceDao ) getDao() ).deleteProvince( provinceVO );
            commitTransaction();
         }

      }
      catch ( final Exception e )
      {
         rollbackTransaction();
         throw new KANException( e );
      }

      return 0;
   }

   @Override
   public List< Object > getProvinceVOsByCountryId( int countryId ) throws KANException
   {
      return ( ( ProvinceDao ) getDao() ).getProvinceVOsByCountryId( countryId );
   }

}

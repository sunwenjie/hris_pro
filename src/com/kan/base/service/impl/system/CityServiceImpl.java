package com.kan.base.service.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.CityDao;
import com.kan.base.domain.system.CityVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.CityService;
import com.kan.base.util.KANException;

public class CityServiceImpl extends ContextService implements CityService
{

   @Override
   public PagedListHolder getCityVOByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final CityDao cityDao = ( CityDao ) getDao();
      pagedListHolder.setHolderSize( cityDao.countCityVOByCondition( ( CityVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( cityDao.getCityVOByCondition( ( CityVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize()
               + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( cityDao.getCityVOByCondition( ( CityVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }
   
  

   @Override
   public CityVO getCityVOByCityId( int cityId ) throws KANException
   {
     
      return ( ( CityDao ) getDao() ).getCityVOByCityId( cityId );
   }



   @Override
   public int insertCity( final CityVO cityVO ) throws KANException
   {

      return ( ( CityDao ) getDao() ).insertCity( cityVO );
   }

   @Override
   public int updateCity( final CityVO cityVO ) throws KANException
   {

      return ( ( CityDao ) getDao() ).updateCity( cityVO );
   }

   @Override
   public int deleteCity( final CityVO cityVO ) throws KANException
   {

      return ( ( CityDao ) getDao() ).deleteCity( cityVO );
   }



   @Override
   public List< Object > getCityVOsByProvinceId( int provinceId ) throws KANException
   {
      return ( ( CityDao ) getDao() ).getCityVOsByProvinceId( provinceId );
   }

}

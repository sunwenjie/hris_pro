package com.kan.base.service.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.CityDao;
import com.kan.base.dao.inf.system.CountryDao;
import com.kan.base.dao.inf.system.ProvinceDao;
import com.kan.base.domain.system.CityVO;
import com.kan.base.domain.system.CountryVO;
import com.kan.base.domain.system.ProvinceVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.CountryService;
import com.kan.base.util.KANException;

public class CountryServiceImpl extends ContextService implements CountryService
{
   private ProvinceDao provinceDao;

   private CityDao cityDao;

   public ProvinceDao getProvinceDao()
   {
      return provinceDao;
   }

   public void setProvinceDao( ProvinceDao provinceDao )
   {
      this.provinceDao = provinceDao;
   }

   public CityDao getCityDao()
   {
      return cityDao;
   }

   public void setCityDao( CityDao cityDao )
   {
      this.cityDao = cityDao;
   }

   @Override
   public PagedListHolder getCountryVOByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final CountryDao countryDao = ( CountryDao ) getDao();
      pagedListHolder.setHolderSize( countryDao.countCountryVOByCondition( ( CountryVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( countryDao.getCountryVOByCondition( ( CountryVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( countryDao.getCountryVOByCondition( ( CountryVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public CountryVO getCountryVOByCountryId( int countryId ) throws KANException
   {
      return ( ( CountryDao ) getDao() ).getCountryVOByCountryId( countryId );
   }

   @Override
   public int insertCountry( CountryVO countryVO ) throws KANException
   {
      return ( ( CountryDao ) getDao() ).insertCountry( countryVO );
   }

   @Override
   public int updateCountry( CountryVO countryVO ) throws KANException
   {
      return ( ( CountryDao ) getDao() ).updateCountry( countryVO );
   }

   @Override
   public int deleteCountry( CountryVO countryVO ) throws KANException
   {
      // 删除国家关联到其他表（国家——省份——城市）
      // 步骤：在Application.xml中相应的Service中注入相应的DAO Setter注入
      try
      {
         if ( countryVO != null && countryVO.getCountryId() != null && !countryVO.getCountryId().trim().equals( "" ) )
         {
            //开启一个事务
            startTransaction();
            //实例化一个ProvinceVO(省份)对象
            final ProvinceVO provinceVO = new ProvinceVO();
            //设定ProvinceVO的CountryId(也就是找到该国家的所有省份)
            provinceVO.setCountryId( countryVO.getCountryId() );
            //迭代  取出所有省份
            CityVO cityVO;
            for ( Object objectProviceVO : this.provinceDao.getProvinceVOByCondition( provinceVO ) )
            {
               //实例化一个CityVO(城市)对象
               cityVO = new CityVO();
               //设定CityVO的ProvinceId(也就是找到该省份的所有的城市)
               cityVO.setProvinceId( ( ( ProvinceVO ) objectProviceVO ).getProvinceId() );
               //迭代 取出所有城市
               for ( Object objectCityVO : this.cityDao.getCityVOByCondition( cityVO ) )
               {
                  ( ( CityVO ) objectCityVO ).setModifyBy( countryVO.getModifyBy() );
                  ( ( CityVO ) objectCityVO ).setModifyDate( countryVO.getModifyDate() );
                  //逐个删除城市
                  this.cityDao.deleteCity( ( ( CityVO ) objectCityVO ) );
               }
               ( ( ProvinceVO ) objectProviceVO ).setModifyBy( countryVO.getModifyBy() );
               ( ( ProvinceVO ) objectProviceVO ).setModifyDate( countryVO.getModifyDate() );

               //逐个删除省份
               this.provinceDao.deleteProvince( ( ProvinceVO ) objectProviceVO );
            }
            //最后删除国家
            ( ( CountryDao ) getDao() ).deleteCountry( countryVO );
            //这时候提交事务
            commitTransaction();
         }
      }
      catch ( final Exception e )
      {
         //如果有异常则回滚
         rollbackTransaction();
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public List< Object > getCountryVOs() throws KANException
   {
      return ( ( CountryDao ) getDao() ).getCountryVOs();
   }

}

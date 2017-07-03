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
      // ɾ�����ҹ��������������ҡ���ʡ�ݡ������У�
      // ���裺��Application.xml����Ӧ��Service��ע����Ӧ��DAO Setterע��
      try
      {
         if ( countryVO != null && countryVO.getCountryId() != null && !countryVO.getCountryId().trim().equals( "" ) )
         {
            //����һ������
            startTransaction();
            //ʵ����һ��ProvinceVO(ʡ��)����
            final ProvinceVO provinceVO = new ProvinceVO();
            //�趨ProvinceVO��CountryId(Ҳ�����ҵ��ù��ҵ�����ʡ��)
            provinceVO.setCountryId( countryVO.getCountryId() );
            //����  ȡ������ʡ��
            CityVO cityVO;
            for ( Object objectProviceVO : this.provinceDao.getProvinceVOByCondition( provinceVO ) )
            {
               //ʵ����һ��CityVO(����)����
               cityVO = new CityVO();
               //�趨CityVO��ProvinceId(Ҳ�����ҵ���ʡ�ݵ����еĳ���)
               cityVO.setProvinceId( ( ( ProvinceVO ) objectProviceVO ).getProvinceId() );
               //���� ȡ�����г���
               for ( Object objectCityVO : this.cityDao.getCityVOByCondition( cityVO ) )
               {
                  ( ( CityVO ) objectCityVO ).setModifyBy( countryVO.getModifyBy() );
                  ( ( CityVO ) objectCityVO ).setModifyDate( countryVO.getModifyDate() );
                  //���ɾ������
                  this.cityDao.deleteCity( ( ( CityVO ) objectCityVO ) );
               }
               ( ( ProvinceVO ) objectProviceVO ).setModifyBy( countryVO.getModifyBy() );
               ( ( ProvinceVO ) objectProviceVO ).setModifyDate( countryVO.getModifyDate() );

               //���ɾ��ʡ��
               this.provinceDao.deleteProvince( ( ProvinceVO ) objectProviceVO );
            }
            //���ɾ������
            ( ( CountryDao ) getDao() ).deleteCountry( countryVO );
            //��ʱ���ύ����
            commitTransaction();
         }
      }
      catch ( final Exception e )
      {
         //������쳣��ع�
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

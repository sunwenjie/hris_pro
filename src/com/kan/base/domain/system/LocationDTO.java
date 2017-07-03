package com.kan.base.domain.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.kan.base.domain.MappingVO;

public class LocationDTO implements Serializable
{

   /**
    * Serial Version UID
    */
   public static final String COUNTRY_ID = "1";

   private static final long serialVersionUID = 3665510050334451790L;

   private Map< String, CountryDTO > countryDTOs = new TreeMap< String, CountryDTO >();

   /**
    * For Application
    */

   public CityVO getCityVO( final String cityId, final String localeLanguage )
   {
      if ( cityId != null && !cityId.trim().equals( "" ) )
      {
         for ( Map.Entry< String, CountryDTO > countryDTOMap : this.countryDTOs.entrySet() )
         {
            for ( Map.Entry< String, ProvinceDTO > provinceDTOMap : countryDTOMap.getValue().getProvinceDTOs().entrySet() )

            {
               for ( Map.Entry< String, CityVO > cityVOMap : provinceDTOMap.getValue().getCityVOs().entrySet() )
               {
                  if ( cityId.trim().equalsIgnoreCase( cityVOMap.getValue().getCityId() ) )
                  {
                     return cityVOMap.getValue();
                  }
               }
            }
         }
      }

      return null;
   }

   public String getCityName( final String cityId, final String localeLanguage )
   {
      if ( cityId != null && !cityId.trim().equals( "" ) )
      {
         for ( Map.Entry< String, CountryDTO > countryDTOMap : this.countryDTOs.entrySet() )
         {
            for ( Map.Entry< String, ProvinceDTO > provinceDTOMap : countryDTOMap.getValue().getProvinceDTOs().entrySet() )

            {
               for ( Map.Entry< String, CityVO > cityVOMap : provinceDTOMap.getValue().getCityVOs().entrySet() )
               {
                  if ( cityId.trim().equalsIgnoreCase( cityVOMap.getValue().getCityId() ) )
                  {
                     if ( localeLanguage.equalsIgnoreCase( "ZH" ) )
                     {
                        return cityVOMap.getValue().getCityNameZH();
                     }
                     else
                     {
                        return cityVOMap.getValue().getCityNameEN();
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   public String getNativePlace( final String cityId, final String localeLanguage )
   {
      if ( cityId != null && !cityId.trim().equals( "" ) )
      {
         for ( Map.Entry< String, CountryDTO > countryDTOMap : this.countryDTOs.entrySet() )
         {
            for ( Map.Entry< String, ProvinceDTO > provinceDTOMap : countryDTOMap.getValue().getProvinceDTOs().entrySet() )
            {
               for ( Map.Entry< String, CityVO > cityVOMap : provinceDTOMap.getValue().getCityVOs().entrySet() )
               {
                  if ( cityId.trim().equalsIgnoreCase( cityVOMap.getValue().getCityId() ) )
                  {
                     if ( localeLanguage.equalsIgnoreCase( "ZH" ) )
                     {
                        return provinceDTOMap.getValue().getProvinceVO().getProvinceNameZH();
                     }
                     else
                     {
                        return provinceDTOMap.getValue().getProvinceVO().getProvinceNameEN();
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   // 获取所有 Country
   public List< MappingVO > getCountries( final String localeLanguage )
   {
      List< MappingVO > countries = new ArrayList< MappingVO >();

      for ( Map.Entry< String, CountryDTO > countryDTOMap : this.countryDTOs.entrySet() )
      {
         CountryDTO countryDTO = ( CountryDTO ) countryDTOMap.getValue();
         MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( countryDTO.getCountryVO().getCountryId() );

         if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
         {
            mappingVO.setMappingValue( countryDTO.getCountryVO().getCountryNameZH() );
         }
         else if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "EN" ) )
         {
            mappingVO.setMappingValue( countryDTO.getCountryVO().getCountryNameEN() );
         }
         else
         {
            mappingVO.setMappingValue( "" );
         }
         countries.add( mappingVO );
      }
      return countries;
   }

   // 根据CountryId 获取 Province
   public List< MappingVO > getProvinces( final String localeLanguage )
   {
      List< MappingVO > provinces = new ArrayList< MappingVO >();
      //获取 countryId 对应的  Map< String, ProvinceDTO > 
      CountryDTO countryDTO = this.countryDTOs.get( COUNTRY_ID );

      for ( Map.Entry< String, ProvinceDTO > provinceDTOMap : countryDTO.getProvinceDTOs().entrySet() )
      {
         ProvinceDTO provinceDTO = ( ProvinceDTO ) provinceDTOMap.getValue();
         MappingVO mappingVO = new MappingVO();
         mappingVO.setMappingId( provinceDTO.getProvinceVO().getProvinceId() );

         if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
         {
            mappingVO.setMappingValue( provinceDTO.getProvinceVO().getProvinceNameZH() );
         }
         else if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "EN" ) )
         {
            mappingVO.setMappingValue( provinceDTO.getProvinceVO().getProvinceNameEN() );
         }
         else
         {
            mappingVO.setMappingValue( "" );
         }
         provinces.add( mappingVO );
      }

      return provinces;
   }

   // 根据ProvinceId 获取 City
   public List< MappingVO > getCities( final String provinceId, final String localeLanguage )
   {
      final List< MappingVO > cities = new ArrayList< MappingVO >();
      // Get Country DTO
      final ProvinceDTO provinceDTO = this.countryDTOs.get( COUNTRY_ID ).getProvinceDTOs().get( provinceId );

      if ( provinceDTO != null && provinceDTO.getCityVOs() != null && provinceDTO.getCityVOs().size() > 0 )
      {
         for ( Map.Entry< String, CityVO > cityVOMap : provinceDTO.getCityVOs().entrySet() )
         {
            CityVO cityVO = ( CityVO ) cityVOMap.getValue();
            MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( cityVO.getCityId() );
            if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "ZH" ) )
            {
               mappingVO.setMappingValue( cityVO.getCityNameZH() );
            }
            else if ( localeLanguage != null && localeLanguage.trim().equalsIgnoreCase( "EN" ) )
            {
               mappingVO.setMappingValue( cityVO.getCityNameEN() );
            }
            else
            {
               mappingVO.setMappingValue( "" );
            }
            cities.add( mappingVO );
         }
      }

      return cities;
   }

   public Map< String, CountryDTO > getCountryDTOs()
   {
      return countryDTOs;
   }

   public void setCountryDTOs( Map< String, CountryDTO > countryDTOs )
   {
      this.countryDTOs = countryDTOs;
   }

}

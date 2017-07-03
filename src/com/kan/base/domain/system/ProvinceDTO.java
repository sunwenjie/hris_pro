package com.kan.base.domain.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kan.base.domain.MappingVO;

public class ProvinceDTO implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 7003103160048598788L;
   
   private ProvinceVO provinceVO = new ProvinceVO();
   
   private Map<String, CityVO> cityVOs = new HashMap<String, CityVO>();
   
   /**
    * For Application 
    */
   public List<MappingVO> getCitysByProvinceId( String provinceId ){
      List<MappingVO> cityViewsMap = new ArrayList<MappingVO>();
      MappingVO mappingVOTemp = new MappingVO();
      mappingVOTemp.setMappingId( "0" );
      mappingVOTemp.setMappingValue( "«Î—°‘Ò" );
      mappingVOTemp.setMappingTemp( "please select" );
      cityViewsMap.add( mappingVOTemp );
      return cityViewsMap;
   }

   public ProvinceVO getProvinceVO()
   {
      return provinceVO;
   }

   public void setProvinceVO( ProvinceVO provinceVO )
   {
      this.provinceVO = provinceVO;
   }

   public Map< String, CityVO > getCityVOs()
   {
      return cityVOs;
   }

   public void setCityVOs( Map< String, CityVO > cityVOs )
   {
      this.cityVOs = cityVOs;
   }

}

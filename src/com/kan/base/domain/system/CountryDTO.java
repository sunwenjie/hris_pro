package com.kan.base.domain.system;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class CountryDTO implements Serializable
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -1716719207798393744L;

   private CountryVO countryVO = new CountryVO();

   private Map< String, ProvinceDTO > provinceDTOs = new TreeMap< String, ProvinceDTO >();

   /**
    * For Application 
    */
   public CountryVO getCountryVO()
   {
      return countryVO;
   }

   public void setCountryVO( CountryVO countryVO )
   {
      this.countryVO = countryVO;
   }

   public Map< String, ProvinceDTO > getProvinceDTOs()
   {
      return provinceDTOs;
   }

   public void setProvinceDTOs( Map< String, ProvinceDTO > provinceDTOs )
   {
      this.provinceDTOs = provinceDTOs;
   }

}

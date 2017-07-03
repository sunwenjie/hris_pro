package com.kan.base.domain.system;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class CityVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -4548171488247679081L;

   private String cityId;

   private String provinceId;

   private String cityNameZH;

   private String cityNameEN;

   private String cityCode;

   private String cityISO3;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   public String getCityId()
   {
      return cityId;
   }

   public void setCityId( String cityId )
   {
      this.cityId = cityId;
   }

   public String getProvinceId()
   {
      return provinceId;
   }

   public void setProvinceId( String provinceId )
   {
      this.provinceId = provinceId;
   }

   public String getCityNameZH()
   {
      return cityNameZH;
   }

   public void setCityNameZH( String cityNameZH )
   {
      this.cityNameZH = cityNameZH;
   }

   public String getCityNameEN()
   {
      return cityNameEN;
   }

   public void setCityNameEN( String cityNameEN )
   {
      this.cityNameEN = cityNameEN;
   }

   public String getCityCode()
   {
      return cityCode;
   }

   public void setCityCode( String cityCode )
   {
      this.cityCode = cityCode;
   }

   public String getCityISO3()
   {
      return cityISO3;
   }

   public void setCityISO3( String cityISO3 )
   {
      this.cityISO3 = cityISO3;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( cityId == null || cityId.trim().equals( "" ) )
      {
         return "";
      }
      
      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( cityId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.cityNameZH = "";
      this.cityNameEN = "";
      this.cityCode = "";
      this.cityISO3 = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final CityVO cityVO = ( CityVO ) object;
      this.cityNameZH = cityVO.getCityNameZH();
      this.cityNameEN = cityVO.getCityNameEN();
      this.cityCode = cityVO.getCityCode();
      this.cityISO3 = cityVO.getCityISO3();
      super.setStatus( cityVO.getStatus() );
      super.setModifyDate( new Date() );
   }

}

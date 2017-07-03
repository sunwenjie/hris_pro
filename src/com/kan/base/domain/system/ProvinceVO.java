package com.kan.base.domain.system;

import java.net.URLEncoder;
import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class ProvinceVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -196786208861880769L;

   private String provinceId;

   private String countryId;

   private String provinceNameZH;

   private String provinceNameEN;

   public String getProvinceId()
   {
      return provinceId;
   }

   public void setProvinceId( String provinceId )
   {
      this.provinceId = provinceId;
   }

   public String getCountryId()
   {
      return countryId;
   }

   public void setCountryId( String countryId )
   {
      this.countryId = countryId;
   }

   public String getProvinceNameZH()
   {
      return provinceNameZH;
   }

   public void setProvinceNameZH( String provinceNameZH )
   {
      this.provinceNameZH = provinceNameZH;
   }

   public String getProvinceNameEN()
   {
      return provinceNameEN;
   }

   public void setProvinceNameEN( String provinceNameEN )
   {
      this.provinceNameEN = provinceNameEN;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( provinceId == null || provinceId.trim().equals( "" ) )
      {
         return "";
      }
      
      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( provinceId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public void reset() throws KANException
   {
      this.provinceNameZH = "";
      this.provinceNameEN = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final ProvinceVO provinceVO = ( ProvinceVO ) object;
      this.provinceNameZH = provinceVO.getProvinceNameZH();
      this.provinceNameEN = provinceVO.getProvinceNameEN();
      super.setStatus( provinceVO.getStatus() );
      super.setModifyDate( new Date() );
   }

}

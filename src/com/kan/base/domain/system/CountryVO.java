package com.kan.base.domain.system;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class CountryVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -783101600118230083L;

   /**
    * For DB
    */
   private String countryId;

   private String countryNumber;

   private String countryCode;

   private String countryNameZH;

   private String countryNameEN;

   private String countryISO3;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public void update( final Object object )
   {
      final CountryVO countryVO = ( CountryVO ) object;
      this.countryNumber = countryVO.getCountryNumber();
      this.countryCode = countryVO.getCountryCode();
      this.countryNameZH = countryVO.getCountryNameZH();
      this.countryNameEN = countryVO.getCountryNameEN();
      this.countryISO3 = countryVO.getCountryISO3();
      super.setStatus( countryVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset()
   {
      this.countryNumber = "";
      this.countryCode = "";
      this.countryNameZH = "";
      this.countryNameEN = "";
      this.countryISO3 = "";
      super.setStatus( "0" );
   }

   public String getCountryId()
   {
      return countryId;
   }

   public void setCountryId( String countryId )
   {
      this.countryId = countryId;
   }

   public String getCountryNumber()
   {
      return countryNumber;
   }

   public void setCountryNumber( String countryNumber )
   {
      this.countryNumber = countryNumber;
   }

   public String getCountryCode()
   {
      return countryCode;
   }

   public void setCountryCode( String countryCode )
   {
      this.countryCode = countryCode;
   }

   public String getCountryNameZH()
   {
      return countryNameZH;
   }

   public void setCountryNameZH( String countryNameZH )
   {
      this.countryNameZH = countryNameZH;
   }

   public String getCountryNameEN()
   {
      return countryNameEN;
   }

   public void setCountryNameEN( String countryNameEN )
   {
      this.countryNameEN = countryNameEN;
   }

   public String getCountryISO3()
   {
      return countryISO3;
   }

   public void setCountryISO3( String countryISO3 )
   {
      this.countryISO3 = countryISO3;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( countryId == null || countryId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( countryId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

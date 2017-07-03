package com.kan.base.domain.management;

import java.net.URLEncoder;
import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class LanguageVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 1064192724793059232L;

   /**
    * For DB
    */
   private String languageId;

   private String nameZH;

   private String nameEN;

   private String description;

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.description = "";
      super.setStatus( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final LanguageVO languageVO = ( LanguageVO ) object;
      this.nameZH = languageVO.getNameZH();
      this.nameEN = languageVO.getNameEN();
      this.description = languageVO.getDescription();
      super.setStatus( languageVO.getStatus() );
      super.setModifyBy( languageVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public String getLanguageId()
   {
      return languageId;
   }

   public void setLanguageId( String languageId )
   {
      this.languageId = languageId;
   }

   public String getNameZH()
   {
      return nameZH;
   }

   public void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( languageId == null || languageId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( languageId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

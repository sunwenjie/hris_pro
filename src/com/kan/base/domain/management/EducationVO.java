package com.kan.base.domain.management;

import java.net.URLEncoder;
import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class EducationVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -4457099390840428258L;

   /**
    * For DB
    */
   private String educationId;

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
      final EducationVO educationVO = ( EducationVO ) object;
      this.nameZH = educationVO.getNameZH();
      this.nameEN = educationVO.getNameEN();
      this.description = educationVO.getDescription();
      super.setStatus( educationVO.getStatus() );
      super.setModifyBy( educationVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public String getEducationId()
   {
      return educationId;
   }

   public void setEducationId( String educationId )
   {
      this.educationId = educationId;
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
      if ( educationId == null || educationId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( educationId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

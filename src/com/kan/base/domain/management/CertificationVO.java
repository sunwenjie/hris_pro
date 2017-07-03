package com.kan.base.domain.management;

import java.net.URLEncoder;
import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class CertificationVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 4256215130503671103L;

   /**
    * For DB
    */

   private String certificationId;

   private String nameZH;

   private String nameEN;

   private String description;

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final CertificationVO certificationVO = ( CertificationVO ) object;
      this.nameZH = certificationVO.getNameZH();
      this.nameEN = certificationVO.getNameEN();
      super.setStatus( certificationVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getCertificationId()
   {
      return certificationId;
   }

   public void setCertificationId( String certificationId )
   {
      this.certificationId = certificationId;
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
      if ( certificationId == null || certificationId.trim().equals( "" ) )
      {
         return "";
      }
      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( certificationId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

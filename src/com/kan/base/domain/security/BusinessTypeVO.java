package com.kan.base.domain.security;

import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class BusinessTypeVO extends BaseVO
{

   /**  
    * Serial Version UID
    */
   private static final long serialVersionUID = -7725139722168131235L;

   /**
    * For DB
    */
   private String businessTypeId;

   private String nameZH;

   private String nameEN;

   private String description;

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final BusinessTypeVO businessTypeVO = ( BusinessTypeVO ) object;
      this.nameZH = businessTypeVO.getNameZH();
      this.nameEN = businessTypeVO.getNameEN();
      this.description = businessTypeVO.getDescription();
      super.setStatus( businessTypeVO.getStatus() );
      super.setModifyDate( new Date() );
   }

   public String getBusinessTypeId()
   {
      return businessTypeId;
   }

   public void setBusinessTypeId( String businessTypeId )
   {
      this.businessTypeId = businessTypeId;
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
      return encodedField( businessTypeId );
   }

}

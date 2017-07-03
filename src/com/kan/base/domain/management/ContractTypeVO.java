package com.kan.base.domain.management;

import java.net.URLEncoder;
import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

public class ContractTypeVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 7555073941009846417L;

   /**
    * For DB
    */
   private String typeId;

   private String accountId;

   private String nameZH;

   private String nameEN;

   private String description;

   @Override
   public void reset() throws KANException
   {
      this.accountId = "";
      this.nameZH = "";
      this.nameEN = "";
      this.description = "";
      super.setStatus( "" );
   }

   @Override
   public void update( final Object object ) throws KANException
   {
      final ContractTypeVO contractTypeVO = ( ContractTypeVO ) object;
      this.nameZH = contractTypeVO.getNameZH();
      this.nameEN = contractTypeVO.getNameEN();
      this.description = contractTypeVO.getDescription();
      super.setStatus( contractTypeVO.getStatus() );
      super.setModifyBy( contractTypeVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public String getTypeId()
   {
      return typeId;
   }

   public void setTypeId( String typeId )
   {
      this.typeId = typeId;
   }

   public String getAccountId()
   {
      return accountId;
   }

   public void setAccountId( String accountId )
   {
      this.accountId = accountId;
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
      if ( typeId == null || typeId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( typeId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

package com.kan.base.domain.security;

import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class OrgShootVO extends BaseVO
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -7062021385045151920L;

   private String shootId;

   //中文名
   private String nameZH;

   //英文名
   private String nameEN;

   //快照类型 1：部门 2:职位
   private String shootType;

   //快照二进制数据
   private byte[] shootData;

   // 描述
   private String description;

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( this.shootId );
   }

   @Override
   public void reset() throws KANException
   {
      this.nameZH = "";
      this.nameEN = "";
      this.shootType = "0";
      this.shootData = null;
      this.description = "";
      super.setStatus( "0" );
   }

   @Override
   public void update( Object object ) throws KANException
   {
      final OrgShootVO orgShoot = ( OrgShootVO ) object;
      this.nameZH = orgShoot.getNameZH();
      this.nameEN = orgShoot.getNameEN();
      this.shootType = orgShoot.getShootType();
      this.shootData = orgShoot.getShootData();
      this.description = orgShoot.getDescription();
      super.setStatus( orgShoot.getStatus() );
      super.setModifyDate( new Date() );

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

   public String getShootType()
   {
      return shootType;
   }

   public void setShootType( String shootType )
   {
      this.shootType = shootType;
   }

   public byte[] getShootData()
   {
      return shootData;
   }

   public void setShootData( byte[] shootData )
   {
      this.shootData = shootData;
   }

   public String getDescription()
   {
      return description;
   }

   public void setDescription( String description )
   {
      this.description = description;
   }

   public String getShootId()
   {
      return shootId;
   }

   public void setShootId( String shootId )
   {
      this.shootId = shootId;
   }

}

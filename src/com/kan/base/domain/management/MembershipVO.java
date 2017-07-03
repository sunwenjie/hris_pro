package com.kan.base.domain.management;

import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.KANException;

public class MembershipVO extends BaseVO
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 8680559574862584351L;

   /**
    * For DB
    */

   private String membershipId;

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
      final MembershipVO membershipVO = ( MembershipVO ) object;
      this.nameZH = membershipVO.getNameZH();
      this.nameEN = membershipVO.getNameEN();
      this.description = membershipVO.getDescription();
      super.setStatus( membershipVO.getStatus() );
      super.setModifyBy( membershipVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   public String getMembershipId()
   {
      return membershipId;
   }

   public void setMembershipId( String membershipId )
   {
      this.membershipId = membershipId;
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
      return encodedField( membershipId );
   }

}

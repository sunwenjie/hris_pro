package com.kan.base.domain.security;

import java.io.Serializable;

public class StaffBaseView implements Serializable
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -1750563306417330326L;

   private String id;

   private String userId;

   private String staffNo;

   private String nameZH;

   private String nameEN;

   private String remark1;

   /**
    * For Application
    */
   private String name;

   public String getId()
   {
      return id;
   }

   public void setId( String id )
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName( String name )
   {
      this.name = name;
   }

   public String getUserId()
   {
      return userId;
   }

   public void setUserId( String userId )
   {
      this.userId = userId;
   }

   public String getStaffNo()
   {
      return staffNo;
   }

   public void setStaffNo( String staffNo )
   {
      this.staffNo = staffNo;
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

   public String getRemark1()
   {
      return remark1;
   }

   public void setRemark1( String remark1 )
   {
      this.remark1 = remark1;
   }

}

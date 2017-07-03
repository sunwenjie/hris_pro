package com.kan.hro.domain.biz.vendor;

import java.io.Serializable;

public class VendorBaseView implements Serializable
{

   /**  
    * Serial Version UID
    */

   private static final long serialVersionUID = 6613936597915319766L;

   private String id;

   private String name;

   private String vendorId;

   private String nameZH;

   private String nameEN;

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

   public String getVendorId()
   {
      return vendorId;
   }

   public void setVendorId( String vendorId )
   {
      this.vendorId = vendorId;
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

}

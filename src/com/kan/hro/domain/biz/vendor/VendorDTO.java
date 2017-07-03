package com.kan.hro.domain.biz.vendor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VendorDTO implements Serializable
{

   /**
    *  Serial Version UID
    */
   private static final long serialVersionUID = -4439869597098425365L;

   // VendorVO
   private VendorVO vendorVO = new VendorVO();

   // VendorContactVO List
   private List< VendorContactVO > vendorContactVOs = new ArrayList< VendorContactVO >();

   // VendorServiceVO List
   private List< VendorServiceVO > vendorServiceVOs = new ArrayList< VendorServiceVO >();

   public VendorVO getVendorVO()
   {
      return vendorVO;
   }

   public void setVendorVO( VendorVO vendorVO )
   {
      this.vendorVO = vendorVO;
   }

   public List< VendorContactVO > getVendorContactVOs()
   {
      return vendorContactVOs;
   }

   public void setVendorContactVOs( List< VendorContactVO > vendorContactVOs )
   {
      this.vendorContactVOs = vendorContactVOs;
   }

   public List< VendorServiceVO > getVendorServiceVOs()
   {
      return vendorServiceVOs;
   }

   public void setVendorServiceVOs( List< VendorServiceVO > vendorServiceVOs )
   {
      this.vendorServiceVOs = vendorServiceVOs;
   }

}

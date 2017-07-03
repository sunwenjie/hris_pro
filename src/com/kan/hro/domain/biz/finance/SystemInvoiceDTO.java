package com.kan.hro.domain.biz.finance;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SystemInvoiceDTO implements Serializable
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 3135397933324901421L;
  
   private SystemInvoiceHeaderVO systemInvoiceHeaderVO;
  
   private List<SystemInvoiceDetailVO> systemInvoiceDetailVOs=new ArrayList<SystemInvoiceDetailVO>();

   public SystemInvoiceHeaderVO getSystemInvoiceHeaderVO()
   {
      return systemInvoiceHeaderVO;
   }

   public void setSystemInvoiceHeaderVO( SystemInvoiceHeaderVO systemInvoiceHeaderVO )
   {
      this.systemInvoiceHeaderVO = systemInvoiceHeaderVO;
   }

   public List< SystemInvoiceDetailVO > getSystemInvoiceDetailVOs()
   {
      return systemInvoiceDetailVOs;
   }

   public void setSystemInvoiceDetailVOs( List< SystemInvoiceDetailVO > systemInvoiceDetailVOs )
   {
      this.systemInvoiceDetailVOs = systemInvoiceDetailVOs;
   }


  
   
   
}

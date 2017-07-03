package com.kan.hro.domain.biz.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientDTO implements Serializable
{

   /**  
   * Serial Version UID
   */
   private static final long serialVersionUID = 4890805502130235836L;

   // 当前的ClientVO
   private ClientVO clientVO = new ClientVO();

   // 当前ClientVO对应的ClientContactVO List
   private List< ClientContactVO > clientContactVOs = new ArrayList< ClientContactVO >();

   // 当前ClientVO对应的ClientInvoiceVO List
   private List< ClientInvoiceVO > clientInvoiceVOs = new ArrayList< ClientInvoiceVO >();

   // 当前ClientVO对应的ClientContractVO List
   private List< ClientContractVO > clientContractVOs = new ArrayList< ClientContractVO >();

   public ClientVO getClientVO()
   {
      return clientVO;
   }

   public void setClientVO( ClientVO clientVO )
   {
      this.clientVO = clientVO;
   }

   public List< ClientContactVO > getClientContactVOs()
   {
      return clientContactVOs;
   }

   public void setClientContactVOs( List< ClientContactVO > clientContactVOs )
   {
      this.clientContactVOs = clientContactVOs;
   }

   public List< ClientInvoiceVO > getClientInvoiceVOs()
   {
      return clientInvoiceVOs;
   }

   public void setClientInvoiceVOs( List< ClientInvoiceVO > clientInvoiceVOs )
   {
      this.clientInvoiceVOs = clientInvoiceVOs;
   }

   public List< ClientContractVO > getClientContractVOs()
   {
      return clientContractVOs;
   }

   public void setClientContractVOs( List< ClientContractVO > clientContractVOs )
   {
      this.clientContractVOs = clientContractVOs;
   }

}

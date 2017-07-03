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

   // ��ǰ��ClientVO
   private ClientVO clientVO = new ClientVO();

   // ��ǰClientVO��Ӧ��ClientContactVO List
   private List< ClientContactVO > clientContactVOs = new ArrayList< ClientContactVO >();

   // ��ǰClientVO��Ӧ��ClientInvoiceVO List
   private List< ClientInvoiceVO > clientInvoiceVOs = new ArrayList< ClientInvoiceVO >();

   // ��ǰClientVO��Ӧ��ClientContractVO List
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

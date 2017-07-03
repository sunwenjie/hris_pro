package com.kan.hro.domain.biz.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientOrderHeaderDTO implements Serializable
{

   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = -239231778680814217L;

   // Order Header
   private ClientOrderHeaderVO clientOrderHeaderVO;

   // Order Header Rule - VO
   private List< ClientOrderHeaderRuleVO > clientOrderHeaderRuleVOs = new ArrayList< ClientOrderHeaderRuleVO >();

   public ClientOrderHeaderVO getClientOrderHeaderVO()
   {
      return clientOrderHeaderVO;
   }

   public void setClientOrderHeaderVO( ClientOrderHeaderVO clientOrderHeaderVO )
   {
      this.clientOrderHeaderVO = clientOrderHeaderVO;
   }

   public List< ClientOrderHeaderRuleVO > getClientOrderHeaderRuleVOs()
   {
      return clientOrderHeaderRuleVOs;
   }

   public void setClientOrderHeaderRuleVOs( List< ClientOrderHeaderRuleVO > clientOrderHeaderRuleVOs )
   {
      this.clientOrderHeaderRuleVOs = clientOrderHeaderRuleVOs;
   }

}

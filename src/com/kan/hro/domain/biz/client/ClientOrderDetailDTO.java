package com.kan.hro.domain.biz.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientOrderDetailDTO implements Serializable
{

   /**  
    * Serial Version UID  
    */
   private static final long serialVersionUID = -1946406764401277029L;

   // Order Detail
   private ClientOrderDetailVO clientOrderDetailVO;

   // Order Detail Rule - VO
   private List< ClientOrderDetailRuleVO > clientOrderDetailRuleVOs = new ArrayList< ClientOrderDetailRuleVO >();

   // Order Detail Rule - VO
   private List< ClientOrderDetailSBRuleVO > clientOrderDetailSBRuleVOs = new ArrayList< ClientOrderDetailSBRuleVO >();

   public ClientOrderDetailVO getClientOrderDetailVO()
   {
      return clientOrderDetailVO;
   }

   public void setClientOrderDetailVO( ClientOrderDetailVO clientOrderDetailVO )
   {
      this.clientOrderDetailVO = clientOrderDetailVO;
   }

   public List< ClientOrderDetailRuleVO > getClientOrderDetailRuleVOs()
   {
      return clientOrderDetailRuleVOs;
   }

   public void setClientOrderDetailRuleVOs( List< ClientOrderDetailRuleVO > clientOrderDetailRuleVOs )
   {
      this.clientOrderDetailRuleVOs = clientOrderDetailRuleVOs;
   }

   public final List< ClientOrderDetailSBRuleVO > getClientOrderDetailSBRuleVOs()
   {
      return clientOrderDetailSBRuleVOs;
   }

   public final void setClientOrderDetailSBRuleVOs( List< ClientOrderDetailSBRuleVO > clientOrderDetailSBRuleVOs )
   {
      this.clientOrderDetailSBRuleVOs = clientOrderDetailSBRuleVOs;
   }

}

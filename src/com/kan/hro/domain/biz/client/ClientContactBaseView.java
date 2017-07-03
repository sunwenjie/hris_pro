package com.kan.hro.domain.biz.client;

import com.kan.base.domain.BaseView;

public class ClientContactBaseView extends BaseView
{
   
   /**  
   * serialVersionUID
   *  
   */  
   
   private static final long serialVersionUID = 6617173683403841665L;

   private String accountId;
   
   private String conditions;
   
   private String nameZH;
   
   private String nameEN;
   
   private String clientContactId;

   public String getAccountId()
   {
      return accountId;
   }

   public void setAccountId( String accountId )
   {
      this.accountId = accountId;
   }

   public String getConditions()
   {
      return conditions;
   }

   public void setConditions( String conditions )
   {
      this.conditions = conditions;
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

   public String getClientContactId()
   {
      return clientContactId;
   }

   public void setClientContactId( String clientContactId )
   {
      this.clientContactId = clientContactId;
   }
   
   
   
}

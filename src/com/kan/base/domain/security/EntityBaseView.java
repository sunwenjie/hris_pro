package com.kan.base.domain.security;

import com.kan.base.domain.BaseView;

public class EntityBaseView extends BaseView
{
   
   /**  
   * serialVersionUID
   *  
   */  
   
   private static final long serialVersionUID = -4950201864024388206L;

   private String accountId;

   public String getAccountId()
   {
      return accountId;
   }

   public void setAccountId( String accountId )
   {
      this.accountId = accountId;
   }
   
}

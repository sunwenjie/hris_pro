package com.kan.base.domain.security;

import com.kan.base.domain.BaseView;

public class BusinessTypeBaseView extends BaseView
{
   
   /**  
   * serialVersionUID
   *  
   */  
   
   private static final long serialVersionUID = 7498585222212939174L;
   
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

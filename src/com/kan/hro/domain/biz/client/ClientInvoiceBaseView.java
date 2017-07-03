package com.kan.hro.domain.biz.client;

import com.kan.base.domain.BaseView;

public class ClientInvoiceBaseView extends BaseView
{
   
   /**  
   * serialVersionUID
   *  
   */  
   
   private static final long serialVersionUID = -6068189169597930526L;
   // ÕË»§ID
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

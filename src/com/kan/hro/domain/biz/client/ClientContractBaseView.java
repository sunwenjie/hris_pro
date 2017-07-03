package com.kan.hro.domain.biz.client;

import com.kan.base.domain.BaseView;

public class ClientContractBaseView extends BaseView
{
   
   /**  
   * serialVersionUID
   *  
   */  
   
   private static final long serialVersionUID = 1074549668030047302L;
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

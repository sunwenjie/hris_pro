package com.kan.base.domain.management;

import java.io.Serializable;

public class ItemBaseView implements Serializable
{

   /**
    * Serial Version UID
    */

   private static final long serialVersionUID = 8752126388751030342L;

   private String id;

   private String name;

   private String accountId;

   public String getId()
   {
      return id;
   }

   public void setId( String id )
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName( String name )
   {
      this.name = name;
   }

   public String getAccountId()
   {
      return accountId;
   }

   public void setAccountId( String accountId )
   {
      this.accountId = accountId;
   }

}

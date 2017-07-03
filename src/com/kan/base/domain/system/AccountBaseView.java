/*
 * Created on 2013-5-13 TODO To change the template for this generated file go
 * to Window - Preferences - Java - Code Style - Code Templates
 */
package com.kan.base.domain.system;

import java.io.Serializable;

/**
 * @author Kevin Jin
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class AccountBaseView implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -3059334367806042611L;

   private String id;

   private String entityName;

   /**
    * For Application
    */
   private String name;

   public AccountBaseView()
   {

   }

   public AccountBaseView( String id, String entityName, String name )
   {
      this.id = id;
      this.entityName = entityName;
      this.name = name;
   }

   public String getId()
   {
      return id;
   }

   public void setId( String id )
   {
      this.id = id;
   }

   public String getEntityName()
   {
      return entityName;
   }

   public void setEntityName( String entityName )
   {
      this.entityName = entityName;
   }

   public String getName()
   {
      return name;
   }

   public void setName( String name )
   {
      this.name = name;
   }
   
}
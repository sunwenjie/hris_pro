/*
 * Created on 2013-8-19
 */
package com.kan.base.domain.system;

import java.io.Serializable;

/**
 * @author Kevin Jin
 */
public class ModuleBaseView implements Serializable
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -3059334367806042611L;

   private String id;

   private String name;

   public ModuleBaseView()
   {

   }

   public ModuleBaseView( final String id, final String name )
   {
      this.id = id;
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

   public String getName()
   {
      return name;
   }

   public void setName( String name )
   {
      this.name = name;
   }

}
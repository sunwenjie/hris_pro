package com.kan.base.domain.security;

import java.io.Serializable;

public class PositionBaseView implements Serializable
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 6555051995805723754L;

   /**
    * For Application
    */
   private String id;

   private String name;
   
   private String parentId;

   public String getParentId() {
	return parentId;
   }
	
   public void setParentId(String parentId) {
		this.parentId = parentId;
   }

   public PositionBaseView()
   {

   }

   public PositionBaseView( final String id, final String name )
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

package com.kan.base.domain;

import java.io.Serializable;

public class BaseDTO implements Serializable
{

   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 481693597436032903L;

   public String sortColumn;

   public String sortOrder = "asc";

   public BaseDTO()
   {
   }

   public String getSortColumn()
   {
      return sortColumn;
   }

   public void setSortColumn( String sortColumn )
   {
      this.sortColumn = sortColumn;
   }

   public String getSortOrder()
   {
      return sortOrder;
   }

   public void setSortOrder( final String sortOrder )
   {
      if ( sortOrder != null && ( sortOrder.trim().equalsIgnoreCase( "asc" ) || sortOrder.trim().equalsIgnoreCase( "desc" ) ) )
      {
         this.sortOrder = sortOrder;
      }
   }

}

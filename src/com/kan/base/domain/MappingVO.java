/*
 * Created on 2006-4-17 TODO To change the template for this generated file go
 * to Window - Preferences - Java - Code Style - Code Templates
 */
package com.kan.base.domain;

import java.io.Serializable;

/**
 * @author Kevin Jin
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class MappingVO implements Serializable
{

   private static final long serialVersionUID = 862685991270885365L;

   private String mappingId;

   private String mappingValue;

   private String mappingTemp;

   private String mappingStatus;
   
   private String optionStyle;

   public MappingVO()
   {
      super();
   }

   public MappingVO( final String mappingId )
   {
      this.mappingId = mappingId;
   }

   public MappingVO( final String mappingId, final String mappingValue )
   {
      this.mappingId = mappingId;
      this.mappingValue = mappingValue;
   }

   public MappingVO( final String mappingId, final String mappingValue, final String mappingTemp )
   {
      this.mappingId = mappingId;
      this.mappingValue = mappingValue;
      this.mappingTemp = mappingTemp;
   }

   /**
    * @return Returns the mappingValue.
    */
   public String getMappingValue()
   {
      return mappingValue;
   }

   /**
    * @param mappingValue
    *            The mappingValue to set.
    */
   public void setMappingValue( final String mappingValue )
   {
      this.mappingValue = mappingValue;
   }

   /**
    * @return Returns the mappingId.
    */
   public String getMappingId()
   {
      return mappingId;
   }

   /**
    * @param mappingId
    *            The mappingId to set.
    */
   public void setMappingId( final String mappingId )
   {
      this.mappingId = mappingId;
   }

   /**
    * @return Returns the mappingTemp.
    */
   public String getMappingTemp()
   {
      return mappingTemp;
   }

   /**
    * @param mappingTemp
    *            The mappingTemp to set.
    */
   public void setMappingTemp( final String mappingTemp )
   {
      this.mappingTemp = mappingTemp;
   }

   @Override
   public int hashCode()
   {
      final int prime = 31;
      int result = 1;
      result = prime * result + ( ( mappingId == null ) ? 0 : mappingId.hashCode() );
      result = prime * result + ( ( mappingTemp == null ) ? 0 : mappingTemp.hashCode() );
      result = prime * result + ( ( mappingValue == null ) ? 0 : mappingValue.hashCode() );
      return result;
   }

   @Override
   public boolean equals( Object obj )
   {
      if ( this == obj )
         return true;
      if ( obj == null )
         return false;
      if ( getClass() != obj.getClass() )
         return false;
      MappingVO other = ( MappingVO ) obj;
      if ( mappingId == null )
      {
         if ( other.mappingId != null )
            return false;
      }
      else if ( !mappingId.equals( other.mappingId ) )
         return false;
      if ( mappingTemp == null )
      {
         if ( other.mappingTemp != null )
            return false;
      }
      else if ( !mappingTemp.equals( other.mappingTemp ) )
         return false;
      if ( mappingValue == null )
      {
         if ( other.mappingValue != null )
            return false;
      }
      else if ( !mappingValue.equals( other.mappingValue ) )
         return false;
      return true;
   }

   public String getMappingStatus()
   {
      return mappingStatus;
   }

   public void setMappingStatus( String mappingStatus )
   {
      this.mappingStatus = mappingStatus;
   }

   public String getOptionStyle()
   {
      return optionStyle;
   }

   public void setOptionStyle( String optionStyle )
   {
      this.optionStyle = optionStyle;
   }

}
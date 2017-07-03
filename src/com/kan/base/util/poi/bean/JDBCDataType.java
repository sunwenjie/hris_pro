package com.kan.base.util.poi.bean;

public enum JDBCDataType
{
   NUMBER, INT, FLOAT, DOUBLE, DATE, CHAR, VARCHAR, TEXT, BLOB;

   public JDBCDataType getValueByName( final String name )
   {
      final String upperName = name.toUpperCase();
      if ( "NUMBER".equals( upperName ) )
      {
         return NUMBER;
      }
      else if ( "INT".equals( upperName ) )
      {
         return INT;
      }
      else if ( "FLOAT".equals( upperName ) )
      {
         return FLOAT;
      }
      else if ( "DOUBLE".equals( upperName ) )
      {
         return DOUBLE;
      }
      else if ( "DATE".equals( upperName ) )
      {
         return DATE;
      }
      else if ( "CHAR".equals( upperName ) )
      {
         return CHAR;
      }
      else if ( "VARCHAR".equals( upperName ) )
      {
         return VARCHAR;
      }
      else if ( "TEXT".equals( upperName ) )
      {
         return TEXT;
      }
      else if ( "BLOB".equals( upperName ) )
      {
         return BLOB;
      }
      else
      {
         return null;
      }
   }
}

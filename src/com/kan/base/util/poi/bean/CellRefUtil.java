package com.kan.base.util.poi.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.util.CellReference;

public class CellRefUtil
{
   
   private static final Pattern pattern = Pattern.compile( "^([a-zA-Z]+)(\\d+)$" );
   
   public static String[] toArray( String cellRef )
   {
      if(cellRef==null){
         return null;
      }
      String[] ret = null;
      Matcher matcher = pattern.matcher( cellRef );
      if ( matcher.find() )
      {
         ret = new String[ 2 ];
         ret[ 0 ] = matcher.group( 1 );
         ret[ 1 ] = matcher.group( 2 );
      }
      return ret;
   }

   public static int columnToValue( String cellRowStr )
   {
      if ( cellRowStr == null )
      {
         return -1;
      }
      int column = -1;
      for ( int i = 0; i < cellRowStr.length(); ++i )
      {
         int c = cellRowStr.charAt( i );
         column = ( column + 1 ) * 26 + c - 'A';
      }
      return column;
   }

   public static String cellRefAdd( String cellRef, int row, int column )
   {
      String ret = null;
      String[] array = toArray( cellRef );
      int row1 = 0;
      int col1 = 0;
      if ( array == null )
      {
         row1 = row +1;
         col1 = column;
         ret = CellReference.convertNumToColString( col1 )+row1;
      }else{
         //org.apache.poi.ss.util.CellReference
        if(column!=0){
           col1 = CellReference.convertColStringToIndex( array[0] ) +column ;
           if(row!=0){
              row1 = Integer.parseInt( array[1] )+row;
              ret = CellReference.convertNumToColString( col1 )+row1;
           }else{
              ret = CellReference.convertNumToColString( col1 )+array[1];
           }
        }else{
           if(row!=0){
              row1 = Integer.parseInt( array[1] )+row;
              ret = array[0]+row1;
           }else{
              ret = cellRef;
           }
        }
      }

      return ret;
   }

   public static void main( String[] args )
   {
      System.out.println(cellRefAdd(null,1,1));
      System.out.println(cellRefAdd("AB14",1,0));
      System.out.println(columnToValue("A"));
   }

   public static int nameToColumn( String rowStr )
   {
      String name;
      int firstDigit = -1;
      for ( int c = 0; c < rowStr.length(); ++c )
      {
         if ( Character.isDigit( rowStr.charAt( c ) ) )
         {
            firstDigit = c;
            break;
         }
      }

      name = rowStr.substring( 0, firstDigit );

      int column = -1;
      for ( int i = 0; i < name.length(); ++i )
      {
         int c = name.charAt( i );
         column = ( column + 1 ) * 26 + c - 'A';
      }
      return column;
   }
}

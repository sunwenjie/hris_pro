/*
 * Created on 2013-6-6
 */
package com.kan.base.util;

/**
 * @author Kevin
 */
public class RandomUtil
{
   private static String letters[] = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
         "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6",
         "7", "8", "9" };

   private static String numbers[] = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" };

   // 获得随机字符和数字串，按照规定位数
   public static String getRandomString( final int length )
   {
      String randomString = "";

      for ( int i = 0; i < length; i++ )
      {
         double residue = getRandom( length ) % 62;

         if ( residue >= 0 )
         {
            randomString = randomString.concat( letters[ ( int ) residue ] );
         }
      }

      return randomString;
   }

   // 获得随机数字串，按照规定位数
   public static String getRandomNumber( final int length )
   {
      String randomNumber = "";

      for ( int i = 0; i < length; i++ )
      {
         double residue = getRandom( length ) % 9;

         if ( residue >= 0 )
         {
            randomNumber = randomNumber.concat( numbers[ ( int ) residue ] );
         }
      }

      return randomNumber;
   }

   // 获得Random值，按照规定的位数
   public static double getRandom( final int length )
   {
      double random = ( double ) ( Math.pow( 10, length ) * Math.random() );
      if ( random < Math.pow( 10, length - 1 ) )
      {
         return getRandom( length );
      }
      else
      {
         return random;
      }
   }

   public static int getCharIndex( final String codeChar )
   {
      if ( KANUtil.filterEmpty( codeChar ) != null )
      {
         if ( codeChar.equals( "A" ) )
         {
            return 1;
         }
         else if ( codeChar.equals( "B" ) )
         {
            return 2;
         }
         else if ( codeChar.equals( "C" ) )
         {
            return 3;
         }
         else if ( codeChar.equals( "D" ) )
         {
            return 4;
         }
         else if ( codeChar.equals( "E" ) )
         {
            return 5;
         }
         else if ( codeChar.equals( "F" ) )
         {
            return 6;
         }
         else if ( codeChar.equals( "G" ) )
         {
            return 7;
         }
         else if ( codeChar.equals( "H" ) )
         {
            return 8;
         }
         else if ( codeChar.equals( "I" ) )
         {
            return 9;
         }
         else if ( codeChar.equals( "J" ) )
         {
            return 10;
         }
         else if ( codeChar.equals( "K" ) )
         {
            return 11;
         }
         else if ( codeChar.equals( "L" ) )
         {
            return 12;
         }
         else if ( codeChar.equals( "M" ) )
         {
            return 1;
         }
         else if ( codeChar.equals( "N" ) )
         {
            return 2;
         }
         else if ( codeChar.equals( "O" ) )
         {
            return 3;
         }
         else if ( codeChar.equals( "P" ) )
         {
            return 4;
         }
         else if ( codeChar.equals( "Q" ) )
         {
            return 5;
         }
         else if ( codeChar.equals( "R" ) )
         {
            return 6;
         }
         else if ( codeChar.equals( "S" ) )
         {
            return 7;
         }
         else if ( codeChar.equals( "T" ) )
         {
            return 8;
         }
         else if ( codeChar.equals( "U" ) )
         {
            return 9;
         }
         else if ( codeChar.equals( "V" ) )
         {
            return 10;
         }
         else if ( codeChar.equals( "W" ) )
         {
            return 11;
         }
         else if ( codeChar.equals( "X" ) )
         {
            return 12;
         }
         else if ( codeChar.equals( "Y" ) )
         {
            return 1;
         }
         else if ( codeChar.equals( "Z" ) )
         {
            return 2;
         }
         else if ( codeChar.equals( "a" ) )
         {
            return 3;
         }
         else if ( codeChar.equals( "b" ) )
         {
            return 4;
         }
         else if ( codeChar.equals( "c" ) )
         {
            return 5;
         }
         else if ( codeChar.equals( "d" ) )
         {
            return 6;
         }
         else if ( codeChar.equals( "e" ) )
         {
            return 7;
         }
         else if ( codeChar.equals( "f" ) )
         {
            return 8;
         }
         else if ( codeChar.equals( "g" ) )
         {
            return 9;
         }
         else if ( codeChar.equals( "h" ) )
         {
            return 10;
         }
         else if ( codeChar.equals( "i" ) )
         {
            return 11;
         }
         else if ( codeChar.equals( "j" ) )
         {
            return 12;
         }
         else if ( codeChar.equals( "k" ) )
         {
            return 1;
         }
         else if ( codeChar.equals( "l" ) )
         {
            return 2;
         }
         else if ( codeChar.equals( "m" ) )
         {
            return 3;
         }
         else if ( codeChar.equals( "n" ) )
         {
            return 4;
         }
         else if ( codeChar.equals( "o" ) )
         {
            return 5;
         }
         else if ( codeChar.equals( "p" ) )
         {
            return 6;
         }
         else if ( codeChar.equals( "q" ) )
         {
            return 7;
         }
         else if ( codeChar.equals( "r" ) )
         {
            return 8;
         }
         else if ( codeChar.equals( "s" ) )
         {
            return 9;
         }
         else if ( codeChar.equals( "t" ) )
         {
            return 10;
         }
         else if ( codeChar.equals( "u" ) )
         {
            return 11;
         }
         else if ( codeChar.equals( "v" ) )
         {
            return 12;
         }
         else if ( codeChar.equals( "w" ) )
         {
            return 1;
         }
         else if ( codeChar.equals( "x" ) )
         {
            return 2;
         }
         else if ( codeChar.equals( "y" ) )
         {
            return 3;
         }
         else if ( codeChar.equals( "z" ) )
         {
            return 4;
         }
         else if ( codeChar.equals( "0" ) )
         {
            return 5;
         }
         else if ( codeChar.equals( "1" ) )
         {
            return 6;
         }
         else if ( codeChar.equals( "2" ) )
         {
            return 7;
         }
         else if ( codeChar.equals( "3" ) )
         {
            return 8;
         }
         else if ( codeChar.equals( "4" ) )
         {
            return 9;
         }
         else if ( codeChar.equals( "5" ) )
         {
            return 10;
         }
         else if ( codeChar.equals( "6" ) )
         {
            return 11;
         }
         else if ( codeChar.equals( "7" ) )
         {
            return 12;
         }
         else if ( codeChar.equals( "8" ) )
         {
            return 1;
         }
         else if ( codeChar.equals( "9" ) )
         {
            return 2;
         }
      }

      return 0;
   }

}
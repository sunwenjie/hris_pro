package com.kan.base.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PassWordUtil
{

   private static String[] lowLetter = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
         "z" };

   private static String[] capLetter = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
         "Z" };

   private static String[] num = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

   private static String[] symbol = new String[] { "_", "." };

   /**
    * �Զ���������. ��Сд,����,����,����8λ
    * һλ��д,һλСд,һ������,5������
    */
   public static String randomPassWord()
   {
      String pwd = "";
      // 1��Сд��ĸ
      int lowLetterIndex = ( int ) Math.floor( capLetter.length * Math.random() );
      pwd += lowLetter[ lowLetterIndex ];
      // 1����д��ĸ
      int capLetterIndex = ( int ) Math.floor( lowLetter.length * Math.random() );
      pwd += capLetter[ capLetterIndex ];
      // 1������
      int symbolIndex = ( int ) Math.floor( symbol.length * Math.random() );
      pwd += symbol[ symbolIndex ];
      // 5������
      for ( int i = 0; i < 5; i++ )
      {
         int numIndex = ( int ) Math.floor( num.length * Math.random() );
         pwd += num[ numIndex ];
      }
      pwd = randomOrder( pwd );
      return pwd;
   }

   private static String randomOrder( String str )
   {
      String result = "";
      final List< String > strList = new ArrayList< String >();
      for ( char c : str.toCharArray() )
      {
         strList.add( String.valueOf( c ) );
      }
      int length = strList.size();
      for ( int i = 0; i < length; i++ )
      {
         int tmpIndex = ( int ) Math.floor( strList.size() * Math.random() );
         result += strList.get( tmpIndex );
         strList.remove( tmpIndex );
      }
      return result;
   }

   public static void main( String[] args ) throws KANException, UnsupportedEncodingException
   {
      for ( int i = 0; i < 50; i++ )
      {
         System.out.println( randomPassWord() );
      }
   }
}

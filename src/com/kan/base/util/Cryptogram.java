/*
 * Created on 2005-12-20
 */

package com.kan.base.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author Kevin Jin
 */

public class Cryptogram
{
   final static int offset = 3;

   /**
    * Encode a string using Base64 encoding. Used when storing passwords as cookies.
    *
    * This is weak encoding in that anyone can use the decodeString routine to
    * reverse the encoding.
    *
    * @param str
    * @return String
    * @throws KANException
    */
   public static String encodeString( final String sourceString ) throws KANException
   {
      if ( sourceString != null && !sourceString.trim().equals( "" ) )
      {
         final BASE64Encoder encoder = new BASE64Encoder();
         String encodedString = new String( encoder.encode( sourceString.getBytes() ) );

         if ( encodedString != null )
         {
            //去掉回车符换行符
            Pattern p = Pattern.compile( "\\s*|\t|\r|\n" );
            Matcher m = p.matcher( encodedString );
            encodedString = m.replaceAll( "" );
         }
         if ( encodedString != null && encodedString.length() < 6 )
         {
            encodedString = "***" + encodedString;
         }

         final StringBuffer offsetString = new StringBuffer();
         offsetString.append( encodedString.substring( offset * 1, offset * 2 ) );
         offsetString.append( encodedString.substring( offset * 0, offset * 1 ) );
         offsetString.append( encodedString.substring( offset * 2 ) );

         return offsetString.toString().trim();
      }
      else
      {
         return "";
      }

   }

   /**
    * Decode a string using Base64 encoding.
    *
    * @param str
    * @return String
    * @throws IOException
    */
   public static String decodeString( final String sourceString ) throws KANException
   {
      if ( sourceString != null && !sourceString.trim().equals( "" ) )
      {
         final StringBuffer offsetString = new StringBuffer();
         offsetString.append( sourceString.substring( offset * 1, offset * 2 ) );
         offsetString.append( sourceString.substring( offset * 0, offset * 1 ) );
         offsetString.append( sourceString.substring( offset * 2 ) );

         final BASE64Decoder dec = new BASE64Decoder();
         String value;
         try
         {
            value = new String( dec.decodeBuffer( offsetString.toString().replace( "***", "" ) ) );
         }
         catch ( IOException e )
         {
            throw new KANException( e );
         }

         return value;
      }
      else
      {
         return "";
      }
   }

   // 对数字进行增量加密
   public static String encodeNumber( final String publicCode, final String sourceNumber ) throws KANException
   {
      if ( KANUtil.filterEmpty( sourceNumber ) != null )
      {
         String incrementNumber = "";

         for ( int i = 0; i < KANConstants.PRIVATE_CODE.length(); i++ )
         {
            incrementNumber = incrementNumber
                  + publicCode.substring( RandomUtil.getCharIndex( KANConstants.PRIVATE_CODE.substring( i, i + 1 ) ) - 1, RandomUtil.getCharIndex( KANConstants.PRIVATE_CODE.substring( i, i + 1 ) ) );
         }

         final BigDecimal sourceBigDecimal = new BigDecimal( sourceNumber );

         return String.valueOf( sourceBigDecimal.add( new BigDecimal( incrementNumber ) ).toString() );
      }

      return sourceNumber;
   }

   // 对数字进行增量解密
   public static String decodeNumber( final String publicCode, final String sourceNumber ) throws KANException
   {
      if ( KANUtil.filterEmpty( sourceNumber ) != null )
      {
         String decrementNumber = "";

         for ( int i = 0; i < KANConstants.PRIVATE_CODE.length(); i++ )
         {
            decrementNumber = decrementNumber
                  + publicCode.substring( RandomUtil.getCharIndex( KANConstants.PRIVATE_CODE.substring( i, i + 1 ) ) - 1, RandomUtil.getCharIndex( KANConstants.PRIVATE_CODE.substring( i, i + 1 ) ) );
         }

         final BigDecimal sourceBigDecimal = new BigDecimal( sourceNumber );

         return String.valueOf( sourceBigDecimal.subtract( new BigDecimal( decrementNumber ) ).doubleValue() );
      }

      return sourceNumber;
   }

   /**
    * 获取增量
    * @param publicCode
    * @return
    * @throws KANException
    */
   public static String getIncrement( final String publicCode ) throws KANException
   {
      return encodeNumber( publicCode, "0" );
   }

   /**
    * 这个传得是employeeId
    * employeeId.length = 9
    * 这里需要>=62位
    * @param str
    * @return
    */
   public static String getPublicCode( String employeeId )
   {
      String tmpEmployeeId = "";
      final char[] employeeIdArrs = employeeId.toCharArray();
      for ( int i = employeeIdArrs.length - 1; i >= 0; i-- )
      {
         tmpEmployeeId += ( employeeIdArrs[ i ] - 48 );
      }

      String publicCode = "";
      if ( KANUtil.filterEmpty( employeeId ) != null )
      {
         Long tmpVal = Long.parseLong( tmpEmployeeId ) * Long.parseLong( tmpEmployeeId );
         publicCode = String.valueOf( tmpVal > 100000000000l ? tmpVal : ( 100000000000l + tmpVal ) );
      }
      return publicCode;
   }

   // 针对劳动合同年薪字段加密
   public static String decodeAnnualRemuneration( final String remark1, final String employeeId )
   {
      try
      {
         final JSONObject jsonObject = JSONObject.fromObject( remark1 );
         if ( jsonObject.containsKey( "AnnualRemuneration" ) )
         {
            String objValue = ( String ) jsonObject.get( "AnnualRemuneration" );
            jsonObject.put( "AnnualRemuneration", decodeNumber( Cryptogram.getPublicCode( employeeId ), objValue ) );
         }

         return jsonObject.toString();
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }

      return "";
   }

   // 针对劳动合同年薪字段解密
   public static String encodeAnnualRemuneration( final String remark1, final String employeeId )
   {
      try
      {
         final JSONObject jsonObject = JSONObject.fromObject( remark1 );
         if ( jsonObject.containsKey( "AnnualRemuneration" ) )
         {
            String objValue = ( String ) jsonObject.get( "AnnualRemuneration" );
            jsonObject.put( "AnnualRemuneration", encodeNumber( Cryptogram.getPublicCode( employeeId ), objValue ) );
         }

         return jsonObject.toString();
      }
      catch ( Exception e )
      {
         e.printStackTrace();
      }

      return "";
   }

}
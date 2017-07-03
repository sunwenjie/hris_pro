package com.kan.base.util.Encrypt;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 加密解密工具类
 */
public class Encrypt
{

   private static final Log log = LogFactory.getLog( Encrypt.class );

   //去掉回车符换行符
   private static Pattern p = Pattern.compile( "\\s*|\t|\r|\n" );

   /***
    * 加密密钥，24字节的密钥
    */
   //   final static byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58, ( byte ) 0x88, 0x09, 0x40, 0x38, 0x74, 0x25, ( byte ) 0x99, 0x21, ( byte ) 0xCB, ( byte ) 0xDD, 0x58, 0x66, 0x77, 0x22,
   //         0x74, ( byte ) 0x98, 0x30, 0x40, 0x36, ( byte ) 0xE2 }; 

   private static final String PASSWORD_CRYPT_KEY = "2015iclick@forhrisjackiorisiuvan85221806117";
   private static final byte[] keyBytes = new byte[ 24 ];

   static
   {
      build3DesKey( keyBytes, PASSWORD_CRYPT_KEY );
   }

   /**
    * 生成密钥
    * @param keyStr
    * @return
    * @throws UnsupportedEncodingException
    */
   public static void build3DesKey( byte[] keyBytes, String keyStr )
   {
      //      byte[] key = new byte[ 24 ]; // 声明一个24位的字节数组，默认里面都是0
      byte[] temp;
      try
      {
         temp = keyStr.getBytes( "UTF-8" );

         /*
          * 执行数组拷贝 System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
          */
         if ( 24 > temp.length )
         {
            // 如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
            System.arraycopy( temp, 0, keyBytes, 0, temp.length );
         }
         else
         {
            // 如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
            System.arraycopy( temp, 0, keyBytes, 0, keyBytes.length );
         }
      }
      catch ( UnsupportedEncodingException e )
      {
         e.printStackTrace();
      }
   }

   /**
    * 加密方法提供3des加密，并且base64编码
    * @param key 24字节的密钥
    * @param str 明文
    * @return
    */
   public static String DataEncrypt( byte[] key, String str )
   {
      String encrypt = null;
      try
      {
         byte[] ret = ThreeDes.encryptMode( key, str.getBytes( "UTF-8" ) );

         encrypt = new String( Base64Util.encode( ret ) );

      }
      catch ( Exception e )
      {
         System.out.print( e );
         encrypt = str;
      }
      return encrypt;
   }

   /***
    * 解密方法，先解密base64,在按3des解密
    * @param key 24字节的密钥
    * @param str 密文
    * @return
    */
   public static String DataDecrypt( byte[] key, String str )
   {
      String decrypt = null;
      try
      {
         byte[] ret = ThreeDes.decryptMode( key, Base64Util.decode( str ) );
         decrypt = new String( ret, "UTF-8" );
      }
      catch ( Exception e )
      {
         System.out.print( e );
         decrypt = str;
      }
      return decrypt;
   }

   /***
    * 使用默认密钥，3des编码,base64编码，url编码
    * @param str 明文
    * @return 密文
    */
   public static String encodeUrl( String str )
   {

      String encoded = DataEncrypt( keyBytes, str );
      try
      {
         encoded = URLEncoder.encode( encoded, "utf-8" );
      }
      catch ( UnsupportedEncodingException e )
      {

      }
      return encoded;

   }

   public static String encodeUrl( byte[] key, String str )
   {

      String encoded = DataEncrypt( key, str );
      try
      {
         encoded = URLEncoder.encode( encoded, "utf-8" );
      }
      catch ( UnsupportedEncodingException e )
      {

      }
      return encoded;

   }

   /**
    * 使用默认密钥解密方法，url解码，base64解码，3des解码
    * @param str 密文
    * @return 明文
    */
   public static String decodeUrl( String str )
   {

      String encoded = "";
      try
      {
         encoded = URLDecoder.decode( str, "utf-8" );
      }
      catch ( UnsupportedEncodingException e )
      {

      }
      encoded = DataDecrypt( keyBytes, encoded );
      return encoded;

   }

   public static String decodeUrl( byte[] key, String str )
   {

      String encoded = "";
      try
      {
         encoded = URLDecoder.decode( str, "utf-8" );
      }
      catch ( UnsupportedEncodingException e )
      {

      }
      encoded = DataDecrypt( key, encoded );
      return encoded;

   }

   /**
    * @param args
    */
   public static void main( String[] args )
   {
      String str = "Y7J6dDMkd0fj";
      System.out.println( str );
      str = Encrypt.encodeUrl( str );
      System.out.println( str );
      str = Encrypt.decodeUrl( str );
      System.out.println( str );
      System.out.println();
   }

}
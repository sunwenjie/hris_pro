package com.kan.base.util.Encrypt;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ���ܽ��ܹ�����
 */
public class Encrypt
{

   private static final Log log = LogFactory.getLog( Encrypt.class );

   //ȥ���س������з�
   private static Pattern p = Pattern.compile( "\\s*|\t|\r|\n" );

   /***
    * ������Կ��24�ֽڵ���Կ
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
    * ������Կ
    * @param keyStr
    * @return
    * @throws UnsupportedEncodingException
    */
   public static void build3DesKey( byte[] keyBytes, String keyStr )
   {
      //      byte[] key = new byte[ 24 ]; // ����һ��24λ���ֽ����飬Ĭ�����涼��0
      byte[] temp;
      try
      {
         temp = keyStr.getBytes( "UTF-8" );

         /*
          * ִ�����鿽�� System.arraycopy(Դ���飬��Դ�������￪ʼ������Ŀ�����飬��������λ)
          */
         if ( 24 > temp.length )
         {
            // ���temp����24λ���򿽱�temp�����������ȵ����ݵ�key������
            System.arraycopy( temp, 0, keyBytes, 0, temp.length );
         }
         else
         {
            // ���temp����24λ���򿽱�temp����24�����ȵ����ݵ�key������
            System.arraycopy( temp, 0, keyBytes, 0, keyBytes.length );
         }
      }
      catch ( UnsupportedEncodingException e )
      {
         e.printStackTrace();
      }
   }

   /**
    * ���ܷ����ṩ3des���ܣ�����base64����
    * @param key 24�ֽڵ���Կ
    * @param str ����
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
    * ���ܷ������Ƚ���base64,�ڰ�3des����
    * @param key 24�ֽڵ���Կ
    * @param str ����
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
    * ʹ��Ĭ����Կ��3des����,base64���룬url����
    * @param str ����
    * @return ����
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
    * ʹ��Ĭ����Կ���ܷ�����url���룬base64���룬3des����
    * @param str ����
    * @return ����
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
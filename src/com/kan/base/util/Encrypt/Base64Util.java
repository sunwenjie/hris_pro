package com.kan.base.util.Encrypt;

/**
 * base64���빤����
 */
public class Base64Util
{

   /**
    *  �� s ���� BASE64 ����
    * @param s
    * @return
    */
   public static String encode( byte[] s )
   {
      if ( s == null )
         return null;
      return ( new sun.misc.BASE64Encoder() ).encode( s );
   }

   /**
    *  �� s ���� BASE64 ����
    * @param s
    * @return
    */
   public static String encode( String s )
   {

      if ( s == null )
         return null;
      return encode( s.getBytes() );
   }

   /**�� BASE64 ������ַ��� s ���н���
    * 
    * @param s
    * @return
    */
   public static byte[] decode( String s )
   {
      if ( s == null )
         return null;
      sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
      try
      {
         byte[] b = decoder.decodeBuffer( s );
         return b;
      }
      catch ( Exception e )
      {
         return null;
      }
   }
}

package com.kan.base.util;

import javax.servlet.http.HttpServletRequest;

import com.danga.MemCached.MemCachedClient;
import com.kan.base.core.ServiceLocator;

public class CachedUtil
{

   static MemCachedClient memCachedClient = null;

   static String TOKEN = "KJ";

   static
   {
      memCachedClient = ( MemCachedClient ) ServiceLocator.getService( "memCachedClient" );
   }

   // д�뻺�����expiry - ������ӣ�Ĭ��30���ӣ�
   public static void set( final HttpServletRequest request, final String key, final Object value )
   {
      if ( key != null && !key.trim().equals( "" ) && value != null )
      {
         set( request, key, value, 30 * 60 );
      }
   }

   // д�뻺�����
   public static void set( final HttpServletRequest request, final String key, final Object value, final int expiry )
   {
      if ( key != null && !key.trim().equals( "" ) && value != null )
      {
         memCachedClient.set( key, value, expiry * 60 );
      }
   }

   // ��ȡ�������
   public static Object get( final HttpServletRequest request, final String key )
   {
      Object object = null;

      if ( key != null && !key.trim().equals( "" ) )
      {
         try
         {
            object = memCachedClient.get( key );
         }
         catch ( final Exception e )
         {
         }
      }

      return object;
   }

   // ɾ���������
   public static void delete( final HttpServletRequest request, final String key )
   {
      if ( key != null && !key.trim().equals( "" ) )
      {
         memCachedClient.delete( key );
      }
   }

   // ��黺������Ƿ����
   public static boolean exists( final HttpServletRequest request, final String key )
   {
      if ( key == null || key.trim().equals( "" ) )
      {
         return false;
      }
      else
      {
         return memCachedClient.get( key ) != null;
      }
   }

}

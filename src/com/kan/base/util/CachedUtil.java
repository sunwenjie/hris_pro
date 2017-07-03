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

   // 写入缓存对象，expiry - 缓存分钟（默认30分钟）
   public static void set( final HttpServletRequest request, final String key, final Object value )
   {
      if ( key != null && !key.trim().equals( "" ) && value != null )
      {
         set( request, key, value, 30 * 60 );
      }
   }

   // 写入缓存对象
   public static void set( final HttpServletRequest request, final String key, final Object value, final int expiry )
   {
      if ( key != null && !key.trim().equals( "" ) && value != null )
      {
         memCachedClient.set( key, value, expiry * 60 );
      }
   }

   // 获取缓存对象
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

   // 删除缓存对象
   public static void delete( final HttpServletRequest request, final String key )
   {
      if ( key != null && !key.trim().equals( "" ) )
      {
         memCachedClient.delete( key );
      }
   }

   // 检查缓存对象是否存在
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

package com.kan.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时获取微信access_token的线程
 * 
 */
public class TokenThread implements Runnable
{
   private static Logger log = LoggerFactory.getLogger( TokenThread.class );
   public static AccessToken accessToken = null;

   public void run()
   {
      while ( true )
      {
         try
         {
            accessToken = WXUtil.getAccessToken();
            if ( null != accessToken )
            {
               log.info( "获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getToken() );
               System.out.println( "获取access_token成功，有效时长{" + accessToken.getExpiresIn() + "}秒 token:{" + accessToken.getToken() + "}" );
               // 休眠7000秒
               Thread.sleep( ( accessToken.getExpiresIn() - 200 ) * 1000 );
            }
            else
            {
               // 如果access_token为null，60秒后再获取
               Thread.sleep( 60 * 1000 );
            }
         }
         catch ( InterruptedException e )
         {
            try
            {
               Thread.sleep( 60 * 1000 );
            }
            catch ( InterruptedException e1 )
            {
               log.error( "{}", e1 );
            }
            log.error( "{}", e );
         }
      }
   }
}

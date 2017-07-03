package com.kan.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ��ʱ��ȡ΢��access_token���߳�
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
               log.info( "��ȡaccess_token�ɹ�����Чʱ��{}�� token:{}", accessToken.getExpiresIn(), accessToken.getToken() );
               System.out.println( "��ȡaccess_token�ɹ�����Чʱ��{" + accessToken.getExpiresIn() + "}�� token:{" + accessToken.getToken() + "}" );
               // ����7000��
               Thread.sleep( ( accessToken.getExpiresIn() - 200 ) * 1000 );
            }
            else
            {
               // ���access_tokenΪnull��60����ٻ�ȡ
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

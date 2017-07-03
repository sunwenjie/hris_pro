package com.kan.base.util;

import java.util.Hashtable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kan.base.domain.system.SMSConfigVO;

/**
 * @author Kevin Jin
 */

public class SMS
{
   protected Log logger = LogFactory.getLog( getClass() );

   // 短信发送设置
   private String smsConfig;

   // 短信发送者的AcountId
   private String accountId;

   // 收件人地址 
   private String phoneNumber;

   // 短信内容
   private String content;

   // 短信服务器地址
   private String serverHost;

   // 短信服务器端口
   private String serverPort;

   // 短信发送用户名
   private String username;

   // 短信发送密码
   private String password;

   // 短信发送延时
   private String sendTime;

   // 短信发送类型
   private String sendType;

   public SMS( final String accountId, final String phoneNumber, final String content ) throws KANException
   {
      this.accountId = accountId;
      this.phoneNumber = phoneNumber;
      this.content = content;
      this.smsConfig = KANConstants.getKANAccountConstants( this.accountId ).OPTIONS_SMS_CONFIG;

      // 如果账户已经配置短信网关
      if ( this.smsConfig != null && !this.smsConfig.trim().equals( "0" ) )
      {
         // 初始化账户设定的短信对象
         final SMSConfigVO smsConfigVO = KANConstants.getSMSConfigVOByConfigId( this.smsConfig );

         // 初始化短信发送参数
         if ( smsConfigVO != null )
         {
            this.serverHost = smsConfigVO.getServerHost();
            this.serverPort = smsConfigVO.getServerPort();
            this.username = smsConfigVO.getUsername();
            this.password = Cryptogram.decodeString( smsConfigVO.getPassword() );
            this.sendTime = smsConfigVO.getSendTime();
            this.sendType = smsConfigVO.getSendType();
         }
      }
   }

   /**   
    * 发送短信 - 基本方法    
    * @throws KANException 
    */
   public boolean send() throws KANException
   {
      try
      {
         // 使用时代互联的短信网关发送
         if ( this.smsConfig != null && this.smsConfig.trim().equals( "1" ) )
         {
            final Hashtable< String, String > configTable = new Hashtable< String, String >();
            configTable.put( "VCPSERVER", this.serverHost );
            configTable.put( "VCPSVPORT", this.serverPort );
            configTable.put( "VCPUSERID", this.username );
            configTable.put( "VCPPASSWD", this.password );

            // 创建第三方短信发送对象
            final com.todaynic.client.mobile.SMS sender = new com.todaynic.client.mobile.SMS( configTable );
            // 发送短信
            sender.sendSMS( this.phoneNumber, this.content, this.sendTime, this.sendType );
            final String code = sender.getCode();
            final String recmsg = sender.getMsg();

            logger.info( "Account: " + this.accountId + " " + recmsg + ". [" + code + "]" );

            // 状态码2000表示短信发送成功
            if ( code != null && code.trim().equals( "2000" ) )
            {
               return true;
            }
            else
            {
               return false;
            }
         }
         else
         {
            logger.info( "Account: " + this.accountId + " hasn't configed SMS getway." );
            return false;
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

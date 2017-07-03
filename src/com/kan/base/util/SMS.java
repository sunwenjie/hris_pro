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

   // ���ŷ�������
   private String smsConfig;

   // ���ŷ����ߵ�AcountId
   private String accountId;

   // �ռ��˵�ַ 
   private String phoneNumber;

   // ��������
   private String content;

   // ���ŷ�������ַ
   private String serverHost;

   // ���ŷ������˿�
   private String serverPort;

   // ���ŷ����û���
   private String username;

   // ���ŷ�������
   private String password;

   // ���ŷ�����ʱ
   private String sendTime;

   // ���ŷ�������
   private String sendType;

   public SMS( final String accountId, final String phoneNumber, final String content ) throws KANException
   {
      this.accountId = accountId;
      this.phoneNumber = phoneNumber;
      this.content = content;
      this.smsConfig = KANConstants.getKANAccountConstants( this.accountId ).OPTIONS_SMS_CONFIG;

      // ����˻��Ѿ����ö�������
      if ( this.smsConfig != null && !this.smsConfig.trim().equals( "0" ) )
      {
         // ��ʼ���˻��趨�Ķ��Ŷ���
         final SMSConfigVO smsConfigVO = KANConstants.getSMSConfigVOByConfigId( this.smsConfig );

         // ��ʼ�����ŷ��Ͳ���
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
    * ���Ͷ��� - ��������    
    * @throws KANException 
    */
   public boolean send() throws KANException
   {
      try
      {
         // ʹ��ʱ�������Ķ������ط���
         if ( this.smsConfig != null && this.smsConfig.trim().equals( "1" ) )
         {
            final Hashtable< String, String > configTable = new Hashtable< String, String >();
            configTable.put( "VCPSERVER", this.serverHost );
            configTable.put( "VCPSVPORT", this.serverPort );
            configTable.put( "VCPUSERID", this.username );
            configTable.put( "VCPPASSWD", this.password );

            // �������������ŷ��Ͷ���
            final com.todaynic.client.mobile.SMS sender = new com.todaynic.client.mobile.SMS( configTable );
            // ���Ͷ���
            sender.sendSMS( this.phoneNumber, this.content, this.sendTime, this.sendType );
            final String code = sender.getCode();
            final String recmsg = sender.getMsg();

            logger.info( "Account: " + this.accountId + " " + recmsg + ". [" + code + "]" );

            // ״̬��2000��ʾ���ŷ��ͳɹ�
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

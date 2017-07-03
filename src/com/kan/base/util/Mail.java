package com.kan.base.util;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import org.apache.commons.lang3.StringUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * @author Kevin Jin
 */

public class Mail
{
   // 邮件发送的Acount
   private String accountId;

   // 发件人地址
   private String from;

   // 收件人地址 
   private String to;

   // 邮件标题
   private String subject;

   // 邮件内容
   private String content;

   // 是否以方式发送邮件
   private boolean isHtml = false;

   private String attachment;

   public Mail( final String accountId, final String to, final String parameters[] )
   {
      this.accountId = accountId;
      this.to = to;

      if ( parameters != null && parameters.length >= 2 )
      {
         this.subject = parameters[ 0 ];
         this.content = parameters[ 1 ];
      }

      if ( KANConstants.getKANAccountConstants( this.accountId ).MAIL_TYPE.equalsIgnoreCase( "IND" ) )
      {
         this.from = KANConstants.getKANAccountConstants( this.accountId ).OPTIONS_MAIL_SHOW_NAME + " <" + KANConstants.getKANAccountConstants( this.accountId ).MAIL_SENT_AS + ">";
      }
      else if ( KANConstants.getKANAccountConstants( this.accountId ).MAIL_TYPE.equalsIgnoreCase( "KAN" ) )
      {
         this.from = KANConstants.getKANAccountConstants( this.accountId ).MAIL_ACCOUNT_NAME + KANConstants.MAIL_POSTFIX;
      }
   }

   public Mail( final String accountId, final String to, final String subject, final String content )
   {
      this.accountId = accountId;
      this.to = to;
      this.subject = subject;
      this.content = content;

      if ( KANConstants.getKANAccountConstants( this.accountId ).MAIL_TYPE.equalsIgnoreCase( "IND" ) )
      {
         this.from = KANConstants.getKANAccountConstants( this.accountId ).OPTIONS_MAIL_SHOW_NAME + " <" + KANConstants.getKANAccountConstants( this.accountId ).MAIL_SENT_AS + ">";
      }
      else if ( KANConstants.getKANAccountConstants( this.accountId ).MAIL_TYPE.equalsIgnoreCase( "KAN" ) )
      {
         this.from = KANConstants.getKANAccountConstants( this.accountId ).MAIL_ACCOUNT_NAME + KANConstants.MAIL_POSTFIX;
      }
   }

   public Mail( final String accountId, final String to, final String subject, final String content, String attachment )
   {
      this.accountId = accountId;
      this.to = to;
      this.subject = subject;
      this.content = content;

      if ( KANConstants.getKANAccountConstants( this.accountId ).MAIL_TYPE.equalsIgnoreCase( "IND" ) )
      {
         this.from = KANConstants.getKANAccountConstants( this.accountId ).OPTIONS_MAIL_SHOW_NAME + " <" + KANConstants.getKANAccountConstants( this.accountId ).MAIL_SENT_AS + ">";
      }
      else if ( KANConstants.getKANAccountConstants( this.accountId ).MAIL_TYPE.equalsIgnoreCase( "KAN" ) )
      {
         this.from = KANConstants.getKANAccountConstants( this.accountId ).MAIL_ACCOUNT_NAME + KANConstants.MAIL_POSTFIX;
      }
      this.attachment = attachment;
   }

   public void sendA() throws KANException
   {
      final Properties props = new Properties();
      // 设置发送邮件的邮件服务器的属性
      props.put( "mail.smtp.host", KANConstants.getKANAccountConstants( this.accountId ).MAIL_SMTP_HOST );
      // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
      props.put( "mail.smtp.auth", "true" );
      props.put("mail.smtp.starttls.enable", "true");
      // 用设置好的Properties对象构建一个Session
      final Session session = Session.getDefaultInstance( props );
      // 有了这句便可以在发送邮件的过程中在Console处显示过程信息
      session.setDebug( true );

      // 用Session为参数定义消息对象
      final MimeMessage message = new MimeMessage( session );
      try
      {
         // 加载发件人地址
         message.setFrom( new InternetAddress( from ) );
         // 加载收件人地址
         message.addRecipient( Message.RecipientType.TO, new InternetAddress( to ) );
         // 加载标题
         message.setSubject( subject );
         // 向Multipart对象中添加邮件的各个部分内容，包括文本内容和附件
         Multipart multipart = new MimeMultipart();

         // 设置邮件的文本内容
         BodyPart contentPart = new MimeBodyPart();
         contentPart.setText( this.content );
         multipart.addBodyPart( contentPart );

         // 添加附件
         //BodyPart messageBodyPart = new MimeBodyPart();
         //DataSource source = new FileDataSource(affix);
         // 添加附件的内容
         //messageBodyPart.setDataHandler(new DataHandler(source));
         // 添加附件的标题
         // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
         //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
         //messageBodyPart.setFileName("=?GBK?B?"+ enc.encode(affixName.getBytes()) + "?=");
         //multipart.addBodyPart(messageBodyPart);

         // 将Multipart对象放到MimeMessage中
         message.setContent( multipart );
         // 保存邮件
         message.saveChanges();
         // 发送邮件
         Transport transport = session.getTransport( "smtp" );
         // 连接服务器的邮箱
         transport.connect( KANConstants.getKANAccountConstants( this.accountId ).MAIL_SMTP_HOST, KANConstants.getKANAccountConstants( this.accountId ).MAIL_USERNAME, KANConstants.getKANAccountConstants( this.accountId ).MAIL_PASSWORD );
         // 把邮件发送出去
         transport.sendMessage( message, message.getAllRecipients() );
         transport.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**   
    * 发送邮件 - 基本方法    
    * @throws KANException 
    */
   public boolean send() throws KANException
   {
      // 判断是否需要身份认证    
      KANAuthenticator authenticator = null;

      final Properties props = new Properties();
      // 设置发送邮件的邮件服务器的属性
      props.put( "mail.smtp.host", KANConstants.getKANAccountConstants( this.accountId ).MAIL_SMTP_HOST );
      // 需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
      props.put( "mail.smtp.auth", new Boolean( KANConstants.getKANAccountConstants( this.accountId ).MAIL_SMTP_AUTH_TYPE ).toString() );
      props.put("mail.smtp.starttls.enable", "true");

      if ( KANConstants.getKANAccountConstants( this.accountId ).MAIL_SMTP_AUTH_TYPE )
      {
         // 如果需要身份认证，则创建一个密码验证器    
         authenticator = new KANAuthenticator( KANConstants.getKANAccountConstants( this.accountId ).MAIL_USERNAME, KANConstants.getKANAccountConstants( this.accountId ).MAIL_PASSWORD );
      }
      // 根据邮件会话属性和密码验证器构造一个发送邮件的session    
      final Session session = Session.getDefaultInstance( props, authenticator );
      session.setDebug( false );

      try
      {
         // 根据Session创建一个邮件消息    
         final Message message = new MimeMessage( session );
         // 创建邮件发送者地址    
         final Address fromAddress = new InternetAddress( from );
         // 设置邮件消息的发送者    
         message.setFrom( fromAddress );
         // 创建邮件的接收者地址，并设置到邮件消息中    
         final Address toAddress = new InternetAddress( to );
         message.setRecipient( Message.RecipientType.TO, toAddress );
         // 设置邮件消息的主题    
         message.setSubject( subject );
         // 设置邮件消息发送的时间    
         message.setSentDate( new Date() );

         if ( isHtml || StringUtils.isNotBlank( attachment ) )
         {
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象    
            final Multipart multipart = new MimeMultipart( "mixed" );
            // 创建一个包含HTML内容的MimeBodyPart    
            final BodyPart bodyPart = new MimeBodyPart();
            // 设置HTML内容    
            bodyPart.setContent( content, "text/html; charset=utf-8" );
            multipart.addBodyPart( bodyPart );
            // 设置附件
            if ( StringUtils.isNotBlank( attachment ) )
            {
               final String[] attachments = KANUtil.jasonArrayToStringArray( attachment );
               if ( attachments != null && attachments.length > 0 )
               {
                  for ( String tmpAttr : attachments )
                  {
                     final BodyPart tempbodyPart = new MimeBodyPart();
                     // 设置HTML内容    
                     tempbodyPart.setContent( content, "text/html; charset=utf-8" );
                     multipart.addBodyPart( tempbodyPart );
                     String[] names = tmpAttr.split( "/" );
                     tempbodyPart.setFileName( names[ names.length - 1 ] );
                     final SmbFile remoteFile = getSmbFile( tmpAttr );
                     if ( remoteFile != null )
                     {
                        File file = new File( "/usr/file/tmp/"+UUID.randomUUID().toString() );
                        final InputStream ins = new BufferedInputStream( new SmbFileInputStream( remoteFile ) );
                        OutputStream os = new FileOutputStream( file );
                        int bytesRead = 0;
                        byte[] buffer = new byte[ 8192 ];
                        while ( ( bytesRead = ins.read( buffer, 0, 8192 ) ) != -1 )
                        {
                           os.write( buffer, 0, bytesRead );
                        }
                        os.close();
                        ins.close();
                        DataSource dataSource = new FileDataSource( file );
                        DataHandler dataHandler = new DataHandler( dataSource );
                        tempbodyPart.setDataHandler( dataHandler );
                     }
                  }
               }
            }
            // 将MiniMultipart对象设置为邮件内容    
            message.setContent( multipart );
         }
         else
         {
            // 设置邮件消息的主要内容    
            message.setText( content );
         }

         // 发送邮件    
         Transport.send( message );
         return true;
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   private SmbFile getSmbFile( String fileString )
   {
      // Share Folder Username, Password, IP, and Port
      final String username = KANConstants.getKANAccountConstants( KANConstants.DEFAULT_ACCOUNTID ).SHAREFOLDER_USERNAME;
      final String password = KANConstants.getKANAccountConstants( KANConstants.DEFAULT_ACCOUNTID ).SHAREFOLDER_PASSWORD;
      final String host = KANConstants.getKANAccountConstants( KANConstants.DEFAULT_ACCOUNTID ).SHAREFOLDER_HOST;
      final String port = KANConstants.getKANAccountConstants( KANConstants.DEFAULT_ACCOUNTID ).SHAREFOLDER_PORT;

      // 远程文件字符串
      String remoteFileString = fileString;
      if ( username != null && !username.trim().equals( "" ) && port != null && !port.trim().equals( "" ) )
      {
         remoteFileString = "smb://" + username + ":" + password + "@" + host + ":" + port + remoteFileString;
      }
      else if ( username != null && !username.trim().equals( "" ) )
      {
         remoteFileString = "smb://" + username + ":" + password + "@" + host + remoteFileString;
      }
      else if ( port != null && !port.trim().equals( "" ) )
      {
         remoteFileString = "smb://" + host + ":" + port + remoteFileString;
      }
      else
      {
         remoteFileString = "smb://" + host + remoteFileString;
      }

      // 远程文件
      try
      {
         return new SmbFile( remoteFileString );
      }
      catch ( MalformedURLException e )
      {
         return null;
      }
   }

   /**   
    * 发送邮件 - 可设置邮件内容类型
    */
   public boolean send( final boolean isHtml ) throws KANException
   {
      this.isHtml = isHtml;
      return this.send();
   }

}

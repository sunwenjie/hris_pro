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
   // �ʼ����͵�Acount
   private String accountId;

   // �����˵�ַ
   private String from;

   // �ռ��˵�ַ 
   private String to;

   // �ʼ�����
   private String subject;

   // �ʼ�����
   private String content;

   // �Ƿ��Է�ʽ�����ʼ�
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
      // ���÷����ʼ����ʼ�������������
      props.put( "mail.smtp.host", KANConstants.getKANAccountConstants( this.accountId ).MAIL_SMTP_HOST );
      // ��Ҫ������Ȩ��Ҳ�����л����������У�飬��������ͨ����֤��һ��Ҫ����һ����
      props.put( "mail.smtp.auth", "true" );
      props.put("mail.smtp.starttls.enable", "true");
      // �����úõ�Properties���󹹽�һ��Session
      final Session session = Session.getDefaultInstance( props );
      // ������������ڷ����ʼ��Ĺ�������Console����ʾ������Ϣ
      session.setDebug( true );

      // ��SessionΪ����������Ϣ����
      final MimeMessage message = new MimeMessage( session );
      try
      {
         // ���ط����˵�ַ
         message.setFrom( new InternetAddress( from ) );
         // �����ռ��˵�ַ
         message.addRecipient( Message.RecipientType.TO, new InternetAddress( to ) );
         // ���ر���
         message.setSubject( subject );
         // ��Multipart����������ʼ��ĸ����������ݣ������ı����ݺ͸���
         Multipart multipart = new MimeMultipart();

         // �����ʼ����ı�����
         BodyPart contentPart = new MimeBodyPart();
         contentPart.setText( this.content );
         multipart.addBodyPart( contentPart );

         // ��Ӹ���
         //BodyPart messageBodyPart = new MimeBodyPart();
         //DataSource source = new FileDataSource(affix);
         // ��Ӹ���������
         //messageBodyPart.setDataHandler(new DataHandler(source));
         // ��Ӹ����ı���
         // �������Ҫ��ͨ�������Base64�����ת�����Ա�֤������ĸ����������ڷ���ʱ����������
         //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
         //messageBodyPart.setFileName("=?GBK?B?"+ enc.encode(affixName.getBytes()) + "?=");
         //multipart.addBodyPart(messageBodyPart);

         // ��Multipart����ŵ�MimeMessage��
         message.setContent( multipart );
         // �����ʼ�
         message.saveChanges();
         // �����ʼ�
         Transport transport = session.getTransport( "smtp" );
         // ���ӷ�����������
         transport.connect( KANConstants.getKANAccountConstants( this.accountId ).MAIL_SMTP_HOST, KANConstants.getKANAccountConstants( this.accountId ).MAIL_USERNAME, KANConstants.getKANAccountConstants( this.accountId ).MAIL_PASSWORD );
         // ���ʼ����ͳ�ȥ
         transport.sendMessage( message, message.getAllRecipients() );
         transport.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**   
    * �����ʼ� - ��������    
    * @throws KANException 
    */
   public boolean send() throws KANException
   {
      // �ж��Ƿ���Ҫ�����֤    
      KANAuthenticator authenticator = null;

      final Properties props = new Properties();
      // ���÷����ʼ����ʼ�������������
      props.put( "mail.smtp.host", KANConstants.getKANAccountConstants( this.accountId ).MAIL_SMTP_HOST );
      // ��Ҫ������Ȩ��Ҳ�����л����������У�飬��������ͨ����֤��һ��Ҫ����һ����
      props.put( "mail.smtp.auth", new Boolean( KANConstants.getKANAccountConstants( this.accountId ).MAIL_SMTP_AUTH_TYPE ).toString() );
      props.put("mail.smtp.starttls.enable", "true");

      if ( KANConstants.getKANAccountConstants( this.accountId ).MAIL_SMTP_AUTH_TYPE )
      {
         // �����Ҫ�����֤���򴴽�һ��������֤��    
         authenticator = new KANAuthenticator( KANConstants.getKANAccountConstants( this.accountId ).MAIL_USERNAME, KANConstants.getKANAccountConstants( this.accountId ).MAIL_PASSWORD );
      }
      // �����ʼ��Ự���Ժ�������֤������һ�������ʼ���session    
      final Session session = Session.getDefaultInstance( props, authenticator );
      session.setDebug( false );

      try
      {
         // ����Session����һ���ʼ���Ϣ    
         final Message message = new MimeMessage( session );
         // �����ʼ������ߵ�ַ    
         final Address fromAddress = new InternetAddress( from );
         // �����ʼ���Ϣ�ķ�����    
         message.setFrom( fromAddress );
         // �����ʼ��Ľ����ߵ�ַ�������õ��ʼ���Ϣ��    
         final Address toAddress = new InternetAddress( to );
         message.setRecipient( Message.RecipientType.TO, toAddress );
         // �����ʼ���Ϣ������    
         message.setSubject( subject );
         // �����ʼ���Ϣ���͵�ʱ��    
         message.setSentDate( new Date() );

         if ( isHtml || StringUtils.isNotBlank( attachment ) )
         {
            // MiniMultipart����һ�������࣬����MimeBodyPart���͵Ķ���    
            final Multipart multipart = new MimeMultipart( "mixed" );
            // ����һ������HTML���ݵ�MimeBodyPart    
            final BodyPart bodyPart = new MimeBodyPart();
            // ����HTML����    
            bodyPart.setContent( content, "text/html; charset=utf-8" );
            multipart.addBodyPart( bodyPart );
            // ���ø���
            if ( StringUtils.isNotBlank( attachment ) )
            {
               final String[] attachments = KANUtil.jasonArrayToStringArray( attachment );
               if ( attachments != null && attachments.length > 0 )
               {
                  for ( String tmpAttr : attachments )
                  {
                     final BodyPart tempbodyPart = new MimeBodyPart();
                     // ����HTML����    
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
            // ��MiniMultipart��������Ϊ�ʼ�����    
            message.setContent( multipart );
         }
         else
         {
            // �����ʼ���Ϣ����Ҫ����    
            message.setText( content );
         }

         // �����ʼ�    
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

      // Զ���ļ��ַ���
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

      // Զ���ļ�
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
    * �����ʼ� - �������ʼ���������
    */
   public boolean send( final boolean isHtml ) throws KANException
   {
      this.isHtml = isHtml;
      return this.send();
   }

}

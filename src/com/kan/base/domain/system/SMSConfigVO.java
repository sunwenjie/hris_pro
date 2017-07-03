package com.kan.base.domain.system;

import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

/**
 * @author Kevin Jin 
 */
public class SMSConfigVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = 1569361917208574346L;

   /**
    * For DB
    */
   private String configId;

   private String nameZH;

   private String nameEN;

   private String serverHost;

   private String serverPort;

   private String username;

   private String password;

   private float price;

   private String sendTime;

   private String sendType;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      super.reset( mapping, request );
   }

   @Override
   public void update( final Object object )
   {
      final SMSConfigVO smsConfigVO = ( SMSConfigVO ) object;
      this.nameZH = smsConfigVO.getNameZH();
      this.nameEN = smsConfigVO.getNameEN();
      this.serverHost = smsConfigVO.getServerHost();
      this.serverPort = smsConfigVO.getServerPort();
      this.username = smsConfigVO.getUsername();
      this.password = smsConfigVO.getPassword();
      this.price = smsConfigVO.getPrice();
      this.sendTime = smsConfigVO.getSendTime();
      this.sendType = smsConfigVO.getSendType();
      super.setStatus( smsConfigVO.getStatus() );
      super.setModifyBy( smsConfigVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset()
   {
      this.nameZH = "";
      this.nameEN = "";
      this.serverHost = "";
      this.serverPort = "";
      this.username = "";
      this.password = "";
      this.price = 0;
      this.sendTime = "";
      this.sendType = "";
      super.setStatus( "0" );
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( configId == null || configId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( configId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public String getConfigId()
   {
      return configId;
   }

   public void setConfigId( String configId )
   {
      this.configId = configId;
   }

   public String getNameZH()
   {
      return nameZH;
   }

   public void setNameZH( String nameZH )
   {
      this.nameZH = nameZH;
   }

   public String getNameEN()
   {
      return nameEN;
   }

   public void setNameEN( String nameEN )
   {
      this.nameEN = nameEN;
   }

   public String getServerHost()
   {
      return serverHost;
   }

   public void setServerHost( String serverHost )
   {
      this.serverHost = serverHost;
   }

   public String getServerPort()
   {
      return serverPort;
   }

   public void setServerPort( String serverPort )
   {
      this.serverPort = serverPort;
   }

   public String getUsername()
   {
      return username;
   }

   public void setUsername( String username )
   {
      this.username = username;
   }

   public String getPassword()
   {
      return password;
   }

   public void setPassword( String password )
   {
      this.password = password;
   }

   public String getSendTime()
   {
      return sendTime;
   }

   public void setSendTime( String sendTime )
   {
      this.sendTime = sendTime;
   }

   public String getSendType()
   {
      return sendType;
   }

   public void setSendType( String sendType )
   {
      this.sendType = sendType;
   }

   public float getPrice()
   {
      return price;
   }

   public void setPrice( float price )
   {
      this.price = price;
   }

}

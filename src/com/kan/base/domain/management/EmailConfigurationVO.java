/*
 * Created on 2012-05-07 TODO To change the template for this generated file go
 * to Window - Preferences - Java - Code Style - Code Templates
 */
package com.kan.base.domain.management;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;

/**
 * @author Kevin Jin
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class EmailConfigurationVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -1552070218064078462L;

   /**
    * For DB
    */
   private String configurationId;

   private String showName;

   private String mailType;

   private String sentAs;

   private String accountName;

   private String smtpHost;

   private String smtpPort = "25";

   private String username;

   private String password;

   private String smtpAuthType;

   private String smtpSecurityType;

   private String pop3Host;

   private String pop3Port = "110";

   /**
    * For Application
    */
   private String configPop3;

   private String sendTestMail;

   private String testMailAddress;
   @JsonIgnore
   private List< MappingVO > mailTypes;
   @JsonIgnore
   private List< MappingVO > smtpAuthTypes;
   @JsonIgnore
   private List< MappingVO > smtpSecurityTypes;

   public void reset( final ActionMapping mapping, final HttpServletRequest request )
   {
      this.mailTypes = KANUtil.getMappings( request.getLocale(), "email.configuration.type" );
      this.smtpAuthTypes = KANUtil.getMappings( request.getLocale(), "email.configuration.smtp.auth.type" );
      this.smtpSecurityTypes = KANUtil.getMappings( request.getLocale(), "email.configuration.smtp.security.type" );
      super.reset( mapping, request );
   }

   public String getDecodeMailType()
   {
      return decodeField( mailType, mailTypes );
   }

   public String getDecodeSmtpAuthType()
   {
      return decodeField( smtpAuthType, smtpAuthTypes );
   }

   public String getDecodeSmtpSecurityType()
   {
      return decodeField( smtpSecurityType, smtpSecurityTypes );
   }

   @Override
   public void update( final Object object )
   {
      final EmailConfigurationVO emailConfigurationVO = ( EmailConfigurationVO ) object;
      this.showName = emailConfigurationVO.getShowName();
      this.mailType = emailConfigurationVO.getMailType();
      this.sentAs = emailConfigurationVO.getSentAs();
      this.accountName = emailConfigurationVO.getAccountName();
      this.smtpHost = emailConfigurationVO.getSmtpHost();
      this.smtpPort = emailConfigurationVO.getSmtpPort();
      this.username = emailConfigurationVO.getUsername();
      this.password = emailConfigurationVO.getPassword();
      this.smtpAuthType = emailConfigurationVO.getSmtpAuthType();
      this.smtpSecurityType = emailConfigurationVO.getSmtpSecurityType();
      this.pop3Host = emailConfigurationVO.getPop3Host();
      this.pop3Port = emailConfigurationVO.getPop3Port();
      super.setModifyBy( emailConfigurationVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset() throws KANException
   {
      this.showName = "";
      this.mailType = "0";
      this.sentAs = "";
      this.accountName = "";
      this.smtpHost = "";
      this.smtpPort = "25";
      this.username = "";
      this.password = "";
      this.smtpAuthType = "0";
      this.smtpSecurityType = "0";
      this.pop3Host = "";
      this.pop3Port = "110";
      super.setStatus( "0" );
   }

   public String getConfigurationId()
   {
      return configurationId;
   }

   public void setConfigurationId( String configurationId )
   {
      this.configurationId = configurationId;
   }

   public String getShowName()
   {
      return showName;
   }

   public void setShowName( String showName )
   {
      this.showName = showName;
   }

   public String getMailType()
   {
      return mailType;
   }

   public void setMailType( String mailType )
   {
      this.mailType = mailType;
   }

   public String getSentAs()
   {
      return sentAs;
   }

   public void setSentAs( String sentAs )
   {
      this.sentAs = sentAs;
   }

   public String getSmtpHost()
   {
      return smtpHost;
   }

   public void setSmtpHost( String smtpHost )
   {
      this.smtpHost = smtpHost;
   }

   public String getSmtpPort()
   {
      return smtpPort;
   }

   public void setSmtpPort( String smtpPort )
   {
      this.smtpPort = smtpPort;
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

   public String getSmtpAuthType()
   {
      return smtpAuthType;
   }

   public void setSmtpAuthType( String smtpAuthType )
   {
      this.smtpAuthType = smtpAuthType;
   }

   public String getSmtpSecurityType()
   {
      return smtpSecurityType;
   }

   public void setSmtpSecurityType( String smtpSecurityType )
   {
      this.smtpSecurityType = smtpSecurityType;
   }

   public String getPop3Host()
   {
      return pop3Host;
   }

   public void setPop3Host( String pop3Host )
   {
      this.pop3Host = pop3Host;
   }

   public String getPop3Port()
   {
      return pop3Port;
   }

   public void setPop3Port( String pop3Port )
   {
      this.pop3Port = pop3Port;
   }

   public String getAccountName()
   {
      return accountName;
   }

   public void setAccountName( String accountName )
   {
      this.accountName = accountName;
   }

   public String getSendTestMail()
   {
      return sendTestMail;
   }

   public void setSendTestMail( String sendTestMail )
   {
      this.sendTestMail = sendTestMail;
   }

   public String getTestMailAddress()
   {
      return testMailAddress;
   }

   public void setTestMailAddress( String testMailAddress )
   {
      this.testMailAddress = testMailAddress;
   }

   public List< MappingVO > getMailTypes()
   {
      return mailTypes;
   }

   public void setMailTypes( List< MappingVO > mailTypes )
   {
      this.mailTypes = mailTypes;
   }

   public List< MappingVO > getSmtpAuthTypes()
   {
      return smtpAuthTypes;
   }

   public void setSmtpAuthTypes( List< MappingVO > smtpAuthTypes )
   {
      this.smtpAuthTypes = smtpAuthTypes;
   }

   public List< MappingVO > getSmtpSecurityTypes()
   {
      return smtpSecurityTypes;
   }

   public void setSmtpSecurityTypes( List< MappingVO > smtpSecurityTypes )
   {
      this.smtpSecurityTypes = smtpSecurityTypes;
   }

   public String getConfigPop3()
   {
      return configPop3;
   }

   public void setConfigPop3( String configPop3 )
   {
      this.configPop3 = configPop3;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      return encodedField( configurationId );
   }

}
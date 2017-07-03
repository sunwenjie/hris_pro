/*
 * Created on 2012-05-08
 */
package com.kan.base.domain.management;

import java.net.URLEncoder;
import java.util.Date;

import com.kan.base.domain.BaseVO;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;

/**
 * @author Kevin Jin
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ShareFolderConfigurationVO extends BaseVO
{
   /**
    * Serial Version UID
    */
   private static final long serialVersionUID = -8146420582668717735L;

   /**
    * For DB
    */
   private String configurationId;

   private String host;

   private String port;

   private String username;

   private String password;

   private String directory;

   /**
    * For Application
    */
   private String testFTP;

   @Override
   public void update( final Object object )
   {
      final ShareFolderConfigurationVO ftpConfigurationVO = ( ShareFolderConfigurationVO ) object;
      this.host = ftpConfigurationVO.getHost();
      this.port = ftpConfigurationVO.getPort();
      this.username = ftpConfigurationVO.getUsername();
      this.password = ftpConfigurationVO.getPassword();
      this.directory = ftpConfigurationVO.getDirectory();
      super.setModifyBy( ftpConfigurationVO.getModifyBy() );
      super.setModifyDate( new Date() );
   }

   @Override
   public void reset()
   {
      this.host = "";
      this.port = "";
      this.username = "";
      this.password = "";
      this.directory = "/";
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

   public String getHost()
   {
      return host;
   }

   public void setHost( String host )
   {
      this.host = host;
   }

   public String getPort()
   {
      return port;
   }

   public void setPort( String port )
   {
      this.port = port;
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

   public String getDirectory()
   {
      if ( directory == null || directory.equals( "" ) )
      {
         return "/";
      }
      else
      {
         if ( !directory.startsWith( "/" ) )
         {
            directory = "/" + directory;
         }
         if ( !directory.endsWith( "/" ) )
         {
            directory = directory + "/";
         }

         return directory;
      }
   }

   public void setDirectory( String directory )
   {
      this.directory = directory;
   }

   public String getTestFTP()
   {
      return testFTP;
   }

   public void setTestFTP( String testFTP )
   {
      this.testFTP = testFTP;
   }

   @Override
   public String getEncodedId() throws KANException
   {
      if ( configurationId == null || configurationId.trim().equals( "" ) )
      {
         return "";
      }

      try
      {
         return URLEncoder.encode( Cryptogram.encodeString( configurationId ), "UTF-8" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
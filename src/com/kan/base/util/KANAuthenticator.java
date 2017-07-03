package com.kan.base.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author Kevin
 */
public class KANAuthenticator extends Authenticator
{
   String userName = null;

   String password = null;

   public KANAuthenticator()
   {
      super();
   }

   public KANAuthenticator( final String username, final String password )
   {
      this.userName = username;
      this.password = password;
   }

   protected PasswordAuthentication getPasswordAuthentication()
   {
      return new PasswordAuthentication( userName, password );
   }
}
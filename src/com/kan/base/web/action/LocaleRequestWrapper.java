package com.kan.base.web.action;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author Kevin
 */
public class LocaleRequestWrapper extends HttpServletRequestWrapper
{
   private String language;

   private String counrty;

   public LocaleRequestWrapper( HttpServletRequest request )
   {
      super( request );
   }

   // Overwrite
   public Enumeration< Locale > getLocales()
   {
      Vector< Locale > v = new Vector< Locale >( 1 );
      v.add( getLocale() );
      return v.elements();
   }

   // Overwrite
   public Locale getLocale()
   {
      return new Locale( this.language, this.counrty );
   }

   public String getLanguage()
   {
      return language;
   }

   public void setLanguage( String language )
   {
      this.language = language;
   }

   public String getCounrty()
   {
      return counrty;
   }

   public void setCounrty( String counrty )
   {
      this.counrty = counrty;
   }

}

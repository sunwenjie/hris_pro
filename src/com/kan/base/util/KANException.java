/*
 * Created on 2007-3-16
 */
package com.kan.base.util;

import java.io.PrintStream;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kevin
 */
public class KANException extends Exception
{

   private static final long serialVersionUID = -2470896902498702871L;

   private Throwable nestedThrowable = null;

   /**
    * Constuctor of KANException this constructor will redirect to the page
    * listing error message first and then print the stack trace
    * 
    * @param message
    *            the error message in the Message Resource file
    * @param e
    *            the nested exception
    * @param request
    *            the HttpServletRequest of the Action whose method throws the
    *            WARException
    * @param response
    *            the HttpServletResponse of the Action whose method throws the
    *            WARExcepion
    */
   public KANException( final String message, final Throwable e, final HttpServletRequest request, final HttpServletResponse response )
   {
      super( message );
      nestedThrowable = e;
      RequestDispatcher requestDispatcher = request.getRequestDispatcher( "error.do?error=" + message );

      try
      {
         requestDispatcher.forward( request, response );
      }
      catch ( Exception ex )
      {
      }
   }

   public KANException( final String message, final HttpServletRequest request, final HttpServletResponse response )
   {
      super( message );
      RequestDispatcher requestDispatcher = request.getRequestDispatcher( "error.do?error=" + message );

      try
      {
         requestDispatcher.forward( request, response );
      }
      catch ( Exception e )
      {
      }
   }

   public KANException( final String message )
   {
      super( message );
   }

   public KANException( final Throwable e )
   {
      super( e.getMessage() );
      nestedThrowable = e;
   }

   public KANException( final String message, final Throwable e )
   {
      super( message );
      nestedThrowable = e;
   }

   public Throwable getCause()
   {
      return this.nestedThrowable;
   }

   public void printStackTrace()
   {
      if ( nestedThrowable != null )
      {
         nestedThrowable.printStackTrace();
      }
      super.printStackTrace();
   }

   public void printStackTrace( final PrintStream s )
   {
      if ( nestedThrowable != null )
      {
         nestedThrowable.printStackTrace( s );
      }
      super.printStackTrace( s );
   }

   public void printStackTrace( final PrintWriter s )
   {
      if ( nestedThrowable != null )
      {
         nestedThrowable.printStackTrace( s );
      }
      super.printStackTrace( s );
   }

}
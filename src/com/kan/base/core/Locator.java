package com.kan.base.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Kevin Jin
 *
 */
public class Locator
{

   private final static ApplicationContext ctx;

   static
   {
      ctx = new ClassPathXmlApplicationContext( "KAN-HRO-INF/applicationContext.xml" );
   }

   public static Object getObject( final String name )
   {
      return ctx.getBean( name );
   }

   public static Object getObject( final String name, final ApplicationContext iCtx )
   {
      return iCtx.getBean( name );
   }

   private Locator()
   {
   }

}

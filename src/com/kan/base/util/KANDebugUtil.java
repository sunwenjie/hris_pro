package com.kan.base.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

public class KANDebugUtil
{
   public static void debugRequestParameter( final HttpServletRequest request )
   {

      if ( request == null )
      {
         System.out.println( "【request为空】" );
         return;
      }
      StringBuilder sb = new StringBuilder();
      sb.append( "####ActonInfo:【RequestURI=" ).append( request.getRequestURI() ).append( ", proc=" ).append( request.getParameter( "proc" ) );
      if ( request.getParameter( "ajax" ) != null )
      {
         sb.append( ", ajax=" ).append( request.getParameter( "ajax" ) );
      }
      sb.append( "】\n" );
      sb.append( "####request:{" );
      Map< String, String[] > mapParametes = request.getParameterMap();

      for ( Map.Entry< String, String[] > p : mapParametes.entrySet() )
      {
         sb.append( "【" ).append( p.getKey() ).append( "=" ).append( strArrayToString( p.getValue() ) ).append( "】\n" );

      }
      sb.append( "}" );
      System.out.println( sb.toString() );
   }

   public static String strArrayToString( String[] str )
   {
      if ( str == null || str.length == 0 )
      {
         return "null[]";
      }
      StringBuilder sb = new StringBuilder();
      if ( str.length == 1 )
      {
         return str[ 0 ];
      }
      else if ( str.length > 1 )
      {

         sb.append( "[" ).append( str[ 0 ] );
         for ( int i = 1; i < str.length; i++ )
         {
            sb.append( "," ).append( str[ i ] );
         }
         sb.append( "]" );
      }

      return sb.toString();
   }

   /**
    * 方法写的不好，只能获得本类的属性，继承的没有得到
    * @param form
    */
   public static void debugActionFrom( final ActionForm form )
   {
      StringBuilder sb = new StringBuilder();
      Class< ? extends ActionForm > clazz = form.getClass();
      sb.append( "###" ).append( clazz.getSimpleName() ).append( ":【" );
      Field[] fields = form.getClass().getDeclaredFields();//获得属性
      for ( Field field : fields )
      {
         PropertyDescriptor pd;
         try
         {
            pd = new PropertyDescriptor( field.getName(), clazz );
            Method getMethod = pd.getReadMethod();//获得get方法
            Object o = getMethod.invoke( form );//执行get方法返回一个Object
            sb.append( field.getName() ).append( "=" ).append( o == null ? "[null]" : o.toString() ).append( ", " );
         }
         catch ( IntrospectionException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         catch ( IllegalAccessException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         catch ( IllegalArgumentException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         catch ( InvocationTargetException e )
         {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }

      }
      sb.append( "】" );
      System.out.println( sb.toString() );
   }

   public static void debugBean()
   {

   }
}

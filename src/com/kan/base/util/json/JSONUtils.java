package com.kan.base.util.json;

import java.util.HashMap;
import java.util.Map;

import com.kan.base.util.KANUtil;

import net.sf.json.JSONObject;

public class JSONUtils
{
   // 空格字符_zh
   public static final String SPACE_CHAR_ZH = "空";

   // 空格字符_en
   public static final String SPACE_CHAR_EN = "Empty";

   // Map中Value存放不同处的分隔符
   public static final String DIFF_SPLIT_STR = ">>>";

   // 不同处资源文件key的前缀
   public static final String KEYS_PREFIX_ARRAY[] = new String[] { "emp", "ct", "pc", "sd" };

   // toString
   public static String toString( final Map< Object, String > diffMap, final int prefixIndex, final String langguage )
   {
      final StringBuffer rs = new StringBuffer();
      for ( Object key : diffMap.keySet() )
      {
         String columnName = "";
         if ( "zh".equals( langguage ) )
         {
            columnName = KANUtil.filterEmpty( KANUtil.getPropertiesValue( "/KAN-HRO-INF/keys.log_zh.properties", KEYS_PREFIX_ARRAY[ prefixIndex ] + "." + key.toString() ) );
         }
         else
         {
            columnName = KANUtil.filterEmpty( KANUtil.getPropertiesValue( "/KAN-HRO-INF/keys.log_en.properties", KEYS_PREFIX_ARRAY[ prefixIndex ] + "." + key.toString() ) );
         }

         if ( columnName != null && diffMap.get( key ) != null )
         {
            String array[] = diffMap.get( key ).split( DIFF_SPLIT_STR );

            if ( rs.toString().trim().equals( "" ) )
            {
               if ( "zh".equals( langguage ) )
               {
                  rs.append( columnName + "：" + array[ 0 ] + " → " + array[ 1 ] );
               }
               else
               {
                  rs.append( columnName + ": " + array[ 0 ] + " → " + array[ 1 ] );
               }
            }
            else
            {
               if ( "zh".equals( langguage ) )
               {
                  rs.append( "<br/>" + columnName + "：" + array[ 0 ] + " → " + array[ 1 ] );
               }
               else
               {
                  rs.append( "<br/>" + columnName + ": " + array[ 0 ] + " → " + array[ 1 ] );
               }
            }
         }
      }

      return rs.toString();
   }

   // 比较两个JSON对象的不同处，返回Map
   public static Map< Object, String > compareToDifferent( JSONObject beforeJSON, JSONObject afterJSON, String language )
   {
      final Map< Object, String > diffMap = new HashMap< Object, String >();

      if ( beforeJSON != null && afterJSON != null )
      {
         // 遍历更新后的对象，找出从无到有，从A到B
         for ( Object key : afterJSON.keySet() )
         {
            if ( "servletWrapper".equals( key.toString() ) )
               continue;

            // 从无到有
            if ( beforeJSON.get( key ) == null )
            {
               diffMap.put( key, ( "zh".equalsIgnoreCase( language ) ? SPACE_CHAR_ZH : SPACE_CHAR_EN ) + DIFF_SPLIT_STR + replaceEmpty( afterJSON.get( key ), language ) );
            }
            // 从A到B
            else if ( beforeJSON.get( key ) != null && afterJSON.get( key ) != null
                  && !replaceEmpty( beforeJSON.get( key ), language ).equals( replaceEmpty( afterJSON.get( key ), language ) ) )
            {
               if ( !diffMap.containsKey( key ) )
               {
                  diffMap.put( key, replaceEmpty( beforeJSON.get( key ), language ) + DIFF_SPLIT_STR + replaceEmpty( afterJSON.get( key ), language ) );
               }
            }
         }

         // 遍历更新前的对象，找出从有到无，从B到A
         for ( Object key : beforeJSON.keySet() )
         {
            if ( "servletWrapper".equals( key.toString() ) )
               continue;

            // 从有到无
            if ( afterJSON.get( key ) == null )
            {
               diffMap.put( key, replaceEmpty( beforeJSON.get( key ), language ) + DIFF_SPLIT_STR + ( "zh".equalsIgnoreCase( language ) ? SPACE_CHAR_ZH : SPACE_CHAR_EN ) );
            }
            // 从B到A
            else if ( beforeJSON.get( key ) != null && afterJSON.get( key ) != null
                  && !replaceEmpty( beforeJSON.get( key ), language ).equals( replaceEmpty( afterJSON.get( key ), language ) ) )
            {
               if ( !diffMap.containsKey( key ) )
               {
                  diffMap.put( key, replaceEmpty( beforeJSON.get( key ), language ) + DIFF_SPLIT_STR + replaceEmpty( afterJSON.get( key ), language ) );
               }
            }
         }

         // 如果改了自定义字段
         if ( diffMap.get( "remark1" ) != null )
         {
            final String changeContent = diffMap.get( "remark1" ).toString();
            JSONObject j1 = JSONObject.fromObject( changeContent.split( DIFF_SPLIT_STR )[ 0 ] );
            JSONObject j2 = JSONObject.fromObject( changeContent.split( DIFF_SPLIT_STR )[ 1 ] );
            // 删除remark1改动处
            diffMap.remove( "remark1" );

            // remark1是JSON格式，正好递归调用
            final Map< Object, String > diffMapDefine = compareToDifferent( j1, j2, language );
            // 找出remark1不同处，放入Map
            diffMap.putAll( diffMapDefine );
         }
      }

      return diffMap;
   }

   // 替换空字符为空格字符
   public static String replaceEmpty( final Object object, final String language )
   {
      if ( object == null || ( object instanceof String && "".equals( object.toString().trim() ) ) )
      {
         return ( "zh".equalsIgnoreCase( language ) ? SPACE_CHAR_ZH : SPACE_CHAR_EN );
      }
      else if ( object instanceof String )
      {
         return "".equals( object.toString().trim() ) ? ( "zh".equalsIgnoreCase( language ) ? SPACE_CHAR_ZH : SPACE_CHAR_EN ) : object.toString();
      }
      else
      {
         return ( "zh".equalsIgnoreCase( language ) ? SPACE_CHAR_ZH : SPACE_CHAR_EN );
      }
   }

   // 判断两个对象是否不同字符
   public static boolean isDifferent( final Object o1, final Object o2 )
   {
      if ( o1 == null || o2 == null )
         return true;

      if ( o1 instanceof String && o2 instanceof String && !o1.toString().equals( o2.toString() ) )
         return true;

      return false;
   }
}

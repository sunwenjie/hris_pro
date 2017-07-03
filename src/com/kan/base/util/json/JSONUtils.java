package com.kan.base.util.json;

import java.util.HashMap;
import java.util.Map;

import com.kan.base.util.KANUtil;

import net.sf.json.JSONObject;

public class JSONUtils
{
   // �ո��ַ�_zh
   public static final String SPACE_CHAR_ZH = "��";

   // �ո��ַ�_en
   public static final String SPACE_CHAR_EN = "Empty";

   // Map��Value��Ų�ͬ���ķָ���
   public static final String DIFF_SPLIT_STR = ">>>";

   // ��ͬ����Դ�ļ�key��ǰ׺
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
                  rs.append( columnName + "��" + array[ 0 ] + " �� " + array[ 1 ] );
               }
               else
               {
                  rs.append( columnName + ": " + array[ 0 ] + " �� " + array[ 1 ] );
               }
            }
            else
            {
               if ( "zh".equals( langguage ) )
               {
                  rs.append( "<br/>" + columnName + "��" + array[ 0 ] + " �� " + array[ 1 ] );
               }
               else
               {
                  rs.append( "<br/>" + columnName + ": " + array[ 0 ] + " �� " + array[ 1 ] );
               }
            }
         }
      }

      return rs.toString();
   }

   // �Ƚ�����JSON����Ĳ�ͬ��������Map
   public static Map< Object, String > compareToDifferent( JSONObject beforeJSON, JSONObject afterJSON, String language )
   {
      final Map< Object, String > diffMap = new HashMap< Object, String >();

      if ( beforeJSON != null && afterJSON != null )
      {
         // �������º�Ķ����ҳ����޵��У���A��B
         for ( Object key : afterJSON.keySet() )
         {
            if ( "servletWrapper".equals( key.toString() ) )
               continue;

            // ���޵���
            if ( beforeJSON.get( key ) == null )
            {
               diffMap.put( key, ( "zh".equalsIgnoreCase( language ) ? SPACE_CHAR_ZH : SPACE_CHAR_EN ) + DIFF_SPLIT_STR + replaceEmpty( afterJSON.get( key ), language ) );
            }
            // ��A��B
            else if ( beforeJSON.get( key ) != null && afterJSON.get( key ) != null
                  && !replaceEmpty( beforeJSON.get( key ), language ).equals( replaceEmpty( afterJSON.get( key ), language ) ) )
            {
               if ( !diffMap.containsKey( key ) )
               {
                  diffMap.put( key, replaceEmpty( beforeJSON.get( key ), language ) + DIFF_SPLIT_STR + replaceEmpty( afterJSON.get( key ), language ) );
               }
            }
         }

         // ��������ǰ�Ķ����ҳ����е��ޣ���B��A
         for ( Object key : beforeJSON.keySet() )
         {
            if ( "servletWrapper".equals( key.toString() ) )
               continue;

            // ���е���
            if ( afterJSON.get( key ) == null )
            {
               diffMap.put( key, replaceEmpty( beforeJSON.get( key ), language ) + DIFF_SPLIT_STR + ( "zh".equalsIgnoreCase( language ) ? SPACE_CHAR_ZH : SPACE_CHAR_EN ) );
            }
            // ��B��A
            else if ( beforeJSON.get( key ) != null && afterJSON.get( key ) != null
                  && !replaceEmpty( beforeJSON.get( key ), language ).equals( replaceEmpty( afterJSON.get( key ), language ) ) )
            {
               if ( !diffMap.containsKey( key ) )
               {
                  diffMap.put( key, replaceEmpty( beforeJSON.get( key ), language ) + DIFF_SPLIT_STR + replaceEmpty( afterJSON.get( key ), language ) );
               }
            }
         }

         // ��������Զ����ֶ�
         if ( diffMap.get( "remark1" ) != null )
         {
            final String changeContent = diffMap.get( "remark1" ).toString();
            JSONObject j1 = JSONObject.fromObject( changeContent.split( DIFF_SPLIT_STR )[ 0 ] );
            JSONObject j2 = JSONObject.fromObject( changeContent.split( DIFF_SPLIT_STR )[ 1 ] );
            // ɾ��remark1�Ķ���
            diffMap.remove( "remark1" );

            // remark1��JSON��ʽ�����õݹ����
            final Map< Object, String > diffMapDefine = compareToDifferent( j1, j2, language );
            // �ҳ�remark1��ͬ��������Map
            diffMap.putAll( diffMapDefine );
         }
      }

      return diffMap;
   }

   // �滻���ַ�Ϊ�ո��ַ�
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

   // �ж����������Ƿ�ͬ�ַ�
   public static boolean isDifferent( final Object o1, final Object o2 )
   {
      if ( o1 == null || o2 == null )
         return true;

      if ( o1 instanceof String && o2 instanceof String && !o1.toString().equals( o2.toString() ) )
         return true;

      return false;
   }
}

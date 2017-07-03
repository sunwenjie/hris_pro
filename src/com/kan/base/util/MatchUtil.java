/*
 * Created on 2013-8-21
 */
package com.kan.base.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.kan.base.domain.system.ConstantVO;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientContractPropertyVO;
import com.kan.hro.domain.biz.employee.EmployeeContractPropertyVO;

/**
 * @author Kevin Jin
 */
public class MatchUtil
{
   public static int FLAG_GET_CONTENT = 1;

   public static int FLAG_GET_CONTENT_HIGHLIGHT = 2;

   public static int FLAG_GET_CONTENT_WITH_INPUT = 3;

   public static int FLAG_GET_PROPERTIES = 4;

   public static int FLAG_GET_CONTENT_WITH_VALUE = 5;

   public static String generateContent( final String content, final List< ConstantVO > constantVOs, final List< Object > objects, final HttpServletRequest request )
         throws KANException
   {
      return generateContent( content, constantVOs, objects, request, FLAG_GET_CONTENT_WITH_INPUT, null );
   }

   public static String generateContent( final String content, final List< ConstantVO > constantVOs, final List< Object > objects, final HttpServletRequest request,
         final int flag, final String logo ) throws KANException
   {
      return generateContent( content, constantVOs, objects, request, flag, logo, false, null );
   }

   public static String generateContent( final String content, final List< ConstantVO > constantVOs, final List< Object > objects, final HttpServletRequest request,
         final int flag, final String logo, final boolean noHtml, final String appendString ) throws KANException
   {
      // 初始化返回字符串
      final StringBuffer rs = new StringBuffer();

      if ( content != null && !content.trim().equals( "" ) && constantVOs != null && constantVOs.size() > 0 )
      {
         // 初始位移
         int beginIndex = 0;
         int endIndex = 0;

         // 循环直到从位移开始匹配不到字符串
         while ( content.indexOf( "${", endIndex ) >= 0 )
         {
            // 获取下一个匹配到的开始位移
            beginIndex = content.indexOf( "${", endIndex );
            // 非参数部分先堆入StringBuffer
            rs.append( content.substring( endIndex, beginIndex ) );
            // 获取下一个匹配到的结束位移
            endIndex = content.indexOf( "}", beginIndex ) + 1;

            // 如果位移合法
            if ( beginIndex >= 0 && endIndex >= 0 && beginIndex < endIndex )
            {
               // 获得参数信息
               final String[] constantArray = content.substring( beginIndex, endIndex ).replace( "${", "" ).replace( "}", "" ).split( "_" );

               // 如果参数格式合法
               if ( constantArray != null && constantArray.length == 3 )
               {
                  final ConstantVO constantVO = getConstantVOByConstantId( constantVOs, constantArray[ 1 ] );

                  // 生成id_name

                  String id_name = "";
                  if ( constantVO != null )
                  {
                     id_name = constantVO.getPropertyName() + "_" + constantArray[ 1 ] + "_" + constantArray[ 2 ];
                  }
                  rs.append( toConstantValue( constantVO, objects, id_name, request, flag ) );
               }
            }
         }

         rs.append( content.substring( endIndex ) );
      }
      // 非参数部分先堆入StringBuffer
      String logoString = "";
      if ( StringUtils.isNotBlank( logo ) )
      {
         logoString = "<img width='160px' height='38px' src='" + logo + "' border='0'/>";
      }

      if ( StringUtils.isNotBlank( appendString ) )
      {
         rs.append( appendString );
      }

      if ( !noHtml )
      {
         rs.insert( 0, "<html class='has-js' lang='en'><head><meta http-equiv='content-type' content='text/html; charset=" + KANConstants.WKHTMLTOPDF_HTML_CHARSET
               + "'></head><body>" + logoString + "<div style='margin:auto;width:794px'><div style='padding-left:40px;padding-right:40px;'>" );
         rs.append( "</div></div>" );
      }
      return rs.toString();
   }

   public static List< ConstantVO > fetchProperties( final String content, final List< ConstantVO > constantVOs, final HttpServletRequest request, final List< Object > objects,
         final int flag ) throws KANException
   {
      // 初始化返回参数列表
      final List< ConstantVO > returnConstantVOs = new ArrayList< ConstantVO >();

      if ( content != null && !content.trim().equals( "" ) && constantVOs != null && constantVOs.size() > 0 )
      {
         // 初始位移
         int beginIndex = 0;
         int endIndex = 0;

         // 循环直到从位移开始匹配不到字符串
         while ( content.indexOf( "${", endIndex ) >= 0 )
         {
            // 获取下一个匹配到的开始位移
            beginIndex = content.indexOf( "${", endIndex );
            // 获取下一个匹配到的结束位移
            endIndex = content.indexOf( "}", beginIndex ) + 1;

            // 如果位移合法
            if ( beginIndex >= 0 && endIndex >= 0 && beginIndex < endIndex )
            {
               // 获得参数信息
               final String[] constantArray = content.substring( beginIndex, endIndex ).replace( "${", "" ).replace( "}", "" ).split( "_" );

               // 如果参数格式合法
               if ( constantArray != null && constantArray.length == 3 )
               {
                  final ConstantVO constantVO = getConstantVOByConstantId( constantVOs, constantArray[ 1 ] );

                  if ( constantVO != null )
                  {
                     // 生成id_name
                     final String id_name = constantVO.getPropertyName() + "_" + constantArray[ 1 ] + "_" + constantArray[ 2 ];

                     String value = "";

                     if ( flag == FLAG_GET_PROPERTIES )
                     {
                        value = toConstantValue( constantVO, null, id_name, request, flag );
                     }
                     else if ( flag == FLAG_GET_CONTENT_WITH_VALUE )
                     {
                        value = toConstantValue( constantVO, objects, id_name, request, flag );
                     }

                     final ConstantVO tempConstantVO = new ConstantVO();
                     tempConstantVO.update( constantVO );
                     tempConstantVO.setConstantId( constantVO.getConstantId() );
                     tempConstantVO.setPropertyName( id_name );
                     tempConstantVO.setContent( value );
                     returnConstantVOs.add( tempConstantVO );
                  }
               }
            }
         }
      }

      return returnConstantVOs;
   }

   // 转译参数对应的值
   private static String toConstantValue( final ConstantVO constantVO, final List< Object > objects, final String id_name, final HttpServletRequest request, final int flag )
         throws KANException
   {
      // 初始化返回值
      String returnValue = "";

      String lang = "zh";
      if ( request != null )
      {
         lang = request.getLocale().getLanguage();
      }

      // 如果存在参数
      if ( constantVO != null )
      {
         if ( constantVO.getCharacterType() != null )
         {
            // 如果是系统常量或账户常量
            if ( constantVO.getCharacterType().trim().equals( "1" ) || constantVO.getCharacterType().trim().equals( "2" ) )
            {
               // 参数默认内容不为空
               if ( constantVO.getContent() != null && !constantVO.getContent().trim().equals( "" ) )
               {
                  // 如果是定义的系统变量
                  if ( constantVO.getContent().startsWith( "CONT:" ) )
                  {
                     // 获取当前日期和时间
                     if ( constantVO.getContent().endsWith( ":now" ) )
                     {
                        returnValue = new SimpleDateFormat( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).OPTIONS_DATE_FORMAT + " "
                              + KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).OPTIONS_TIME_FORMAT ).format( new Date() );
                     }
                     // 获取当前日期
                     else if ( constantVO.getContent().endsWith( ":nowdate" ) )
                     {
                        returnValue = new SimpleDateFormat( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).OPTIONS_DATE_FORMAT ).format( new Date() );
                     }
                     // 获取当前时间
                     else if ( constantVO.getContent().endsWith( ":nowtime" ) )
                     {
                        returnValue = new SimpleDateFormat( KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).OPTIONS_TIME_FORMAT ).format( new Date() );
                     }
                  }
                  else
                  {
                     returnValue = constantVO.getContent();
                  }
               }
            }
            // 如果是动态变量
            else if ( constantVO.getCharacterType().trim().equals( "3" ) )
            {
               if ( flag == FLAG_GET_CONTENT )
               {
                  if ( objects != null )
                  {
                     for ( Object object : objects )
                     {
                        if ( constantVO.getScopeType().equals( "2" ) )
                        {
                           final ClientContractPropertyVO clientContractPropertyVO = ( ClientContractPropertyVO ) object;

                           if ( clientContractPropertyVO.getPropertyName() != null && clientContractPropertyVO.getPropertyName().trim().equals( id_name ) )
                           {
                              returnValue = clientContractPropertyVO.getPropertyValue();
                           }
                        }
                        else
                        {
                           final EmployeeContractPropertyVO employeeContractPropertyVO = ( EmployeeContractPropertyVO ) object;

                           if ( employeeContractPropertyVO.getPropertyName() != null && employeeContractPropertyVO.getPropertyName().trim().equals( id_name ) )
                           {
                              returnValue = employeeContractPropertyVO.getPropertyValue();
                           }
                        }
                     }
                  }
               }
               else if ( flag == FLAG_GET_CONTENT_HIGHLIGHT )
               {
                  if ( objects != null )
                  {
                     for ( Object object : objects )
                     {
                        final ClientContractPropertyVO clientContractPropertyVO = ( ClientContractPropertyVO ) object;

                        if ( clientContractPropertyVO.getPropertyName() != null && clientContractPropertyVO.getPropertyName().trim().equals( id_name ) )
                        {
                           returnValue = "<span style=\"background: #ff0099;\">" + clientContractPropertyVO.getPropertyValue() + "</span>";
                        }
                     }
                  }
               }
               else if ( flag == FLAG_GET_CONTENT_WITH_INPUT )
               {
                  final String value = getValueFromObjects( objects, constantVO.getPropertyName(), lang );

                  // 初始化className
                  String className = "";
                  if ( constantVO.getLengthType() != null )
                  {
                     if ( constantVO.getLengthType().trim().equals( "1" ) )
                     {
                        className = "small";
                     }
                     else if ( constantVO.getLengthType().trim().equals( "3" ) )
                     {
                        className = "extend";
                     }
                  }

                  // 初始化Readonly
                  String readonly = "";
                  if ( value != null && !value.trim().equals( "" ) )
                  {
                     readonly = " readonly=\"readonly\" ";
                  }
                  if ( constantVO.getCharacterType().trim().equals( "3" ) )
                  {
                     if ( StringUtils.equals( constantVO.getValueType(), "4" ) )
                     {
                        returnValue = "<textarea class=\"" + className + "\" id=\"" + id_name + "\" name=\"" + id_name + "\" " + readonly + " ></textarea>";
                     }
                     else
                     {
                        returnValue = "<input type=\"text\" id=\"" + id_name + "\" name=\"" + id_name + "\" class=\"" + className + "\" value=\"" + value + "\" " + readonly
                              + " />";
                     }
                  }
               }
               else if ( flag == FLAG_GET_PROPERTIES )
               {
                  returnValue = request.getParameter( id_name );
               }
               else if ( flag == FLAG_GET_CONTENT_WITH_VALUE )
               {
                  returnValue = getValueFromObjects( objects, constantVO.getPropertyName(), lang );
               }
            }
         }
      }

      return returnValue;
   }

   // 按照ConstantId获取对应的ConstantVO
   private static ConstantVO getConstantVOByConstantId( final List< ConstantVO > constantVOs, final String constantId )
   {
      if ( constantVOs != null )
      {
         for ( ConstantVO constantVO : constantVOs )
         {
            if ( constantVO.getConstantId() != null && constantVO.getConstantId().trim().equals( constantId ) )
            {
               return constantVO;
            }
         }
      }

      return null;
   }

   // 参照参数名获取对应的值
   private static String getValueFromObjects( final List< Object > objects, final String propertyName, final String localeLanguage )
   {

      if ( objects != null )
      {
         for ( Object object : objects )
         {
            Object value = null;

            try
            {
               value = KANUtil.getValue( object, propertyName, localeLanguage );
            }
            catch ( final Exception e )
            {
               // 取不到，默认不处理
            }

            if ( value == null )
            {
               try
               {
                  value = KANUtil.getValue( object, propertyName );
               }
               catch ( final Exception e )
               {
                  // 取不到，默认不处理
               }
            }

            if ( value != null )
            {
               return value.toString();
            }
         }
      }

      return "";
   }

   public static String generateMessageContent( final String content, final Map< String, Object > parameters )
   {
      // 初始化返回字符串
      StringBuffer rs = new StringBuffer();

      if ( content != null && !content.trim().equals( "" ) && parameters != null && parameters.size() > 0 )
      {
         // 初始位移
         int beginIndex = 0, endIndex = 0, beginDeleteIndex = 0, endDeleteIndex = 0;

         // 循环直到从位移开始匹配不到字符串
         while ( content.indexOf( "${", endIndex ) >= 0 )
         {
            // 获取下一个匹配到的开始位移
            beginIndex = content.indexOf( "${", endIndex );
            // 非参数部分先堆入StringBuffer
            rs.append( content.substring( endIndex, beginIndex ) );
            // 获取下一个匹配到的结束位移
            endIndex = content.indexOf( "}", beginIndex ) + 1;

            // 如果位移合法
            if ( beginIndex >= 0 && endIndex >= 0 && beginIndex < endIndex )
            {
               // 获得参数信息
               final String constant = content.substring( beginIndex, endIndex ).replace( "${", "" ).replace( "}", "" );

               // 如果参数格式合法
               if ( constant != null )
               {
                  rs.append( parameters.get( constant ) == null ? "" : parameters.get( constant ) );
               }
            }
         }

         // 如果位移合法
         if ( beginIndex >= 0 && endIndex >= 0 && beginIndex < endIndex )
         {
            // 非参数部分先堆入StringBuffer
            rs.append( content.substring( endIndex, content.length() ) );
         }

         if ( parameters.get( "if_delete" ) != null && true == new Boolean( parameters.get( "if_delete" ).toString() ) )
         {
            while ( rs.indexOf( "$#", endDeleteIndex ) >= 0 )
            {
               // 获取下一个匹配到的开始位移
               beginDeleteIndex = rs.indexOf( "$#" );
               // 获取下一个匹配到的结束位移
               endDeleteIndex = rs.indexOf( "#$" ) + 2;
               // 如果位移合法
               if ( beginDeleteIndex >= 0 && endDeleteIndex >= 0 && beginDeleteIndex < endDeleteIndex )
               {
                  rs = new StringBuffer( rs.toString().replace( rs.substring( beginDeleteIndex, endDeleteIndex ), "" ) );
               }
            }
         }

      }

      return rs.toString().replaceAll( "$#", "" ).replace( "#$", "" );
   }

   // 替换参数，生成消息内容
   public static String generateMessageContent( final String content, final List< ConstantVO > constantVOs, final List< Object > objects, final boolean noHtml )
   {
      // 初始化返回字符串
      final StringBuffer rs = new StringBuffer();

      if ( content != null && !content.trim().equals( "" ) && constantVOs != null && constantVOs.size() > 0 )
      {
         // 初始位移
         int beginIndex = 0;
         int endIndex = 0;

         // 循环直到从位移开始匹配不到字符串
         while ( content.indexOf( "${", endIndex ) >= 0 )
         {
            // 获取下一个匹配到的开始位移
            beginIndex = content.indexOf( "${", endIndex );
            // 非参数部分先堆入StringBuffer
            rs.append( content.substring( endIndex, beginIndex ) );
            // 获取下一个匹配到的结束位移
            endIndex = content.indexOf( "}", beginIndex ) + 1;

            // 如果位移合法
            if ( beginIndex >= 0 && endIndex >= 0 && beginIndex < endIndex )
            {
               // 获得参数信息
               final String[] constantArray = content.substring( beginIndex, endIndex ).replace( "${", "" ).replace( "}", "" ).split( "_" );

               // 如果参数格式合法
               if ( constantArray != null && constantArray.length == 3 )
               {
                  final ConstantVO constantVO = getConstantVOByConstantId( constantVOs, constantArray[ 1 ] );

                  rs.append( getConstantValueFromObjects( objects, constantVO.getPropertyName() ) );
               }
            }
         }
      }

      if ( !noHtml )
      {
         rs.insert( 0, "<html class='has-js' lang='en'><head><meta http-equiv='content-type' content='text/html; charset=" + KANConstants.WKHTMLTOPDF_HTML_CHARSET
               + "'></head><body><div style='margin:auto;width:794px'><div style='padding-left:40px;padding-right:40px;'>" );
         rs.append( "</div></div>" );
      }

      return rs.toString();
   }

   public static final String[] TEMPLATE_COMMON_PROPERTY_ARRAY = new String[] { "messageSubjectZH", "messageSubjectEN", "logonAddress", "approvalZH", "approvalEN", "rejectZH",
         "rejectEN", "auditHisroryZH", "auditHisroryEN", "remarkZH", "remarkEN" };

   // 参照参数名获取对应的值
   @SuppressWarnings("unchecked")
   private static String getConstantValueFromObjects( final List< Object > objects, final String propertyName )
   {
      // 如果是普通参数
      if ( ArrayUtils.contains( TEMPLATE_COMMON_PROPERTY_ARRAY, propertyName ) )
      {
         for ( Object object : objects )
         {
            if ( object instanceof Map && ( ( Map< String, String > ) object ).get( propertyName ) != null )
            {
               return ( ( Map< String, String > ) object ).get( propertyName ).toString();
            }
         }
      }
      else
      {
         for ( Object object : objects )
         {
            if ( object instanceof Map )
            {
               continue;
            }

            Object value = null;

            if ( value == null )
            {
               try
               {
                  value = KANUtil.getValue( object, propertyName );
               }
               catch ( final Exception e )
               {
                  // 取不到，默认不处理
               }
            }

            if ( value != null )
            {
               return value.toString();
            }
         }
      }

      return "";
   }
}

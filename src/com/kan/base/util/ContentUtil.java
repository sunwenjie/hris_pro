package com.kan.base.util;

import org.apache.commons.lang3.StringUtils;

import com.kan.base.domain.security.UserVO;
import com.kan.base.web.action.BaseAction;

/**
 * 
 * @author Kevin Jin @2014-04-28
 *
 */
public class ContentUtil
{
   // 用户创建时发送邮件 - 获取内容
   public static String[] getMailContent_UserCreate( final Object[] objects ) throws KANException
   {
      // 初始化邮件标题和内容数组
      final String[] contents = new String[] { "", "" };

      final String role = KANUtil.filterEmpty( getValue( objects, "role" ) );
      final StringBuffer messageB = new StringBuffer();
      final StringBuffer titleB = new StringBuffer();
      final String logonAction;
      if ( role != null && role.equals( KANConstants.ROLE_HR_SERVICE ) )
      {
         logonAction = "logon.do";
      }
      else if ( role != null && role.equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         logonAction = "logoni.do";
      }
      else if ( role != null && role.equals( KANConstants.ROLE_EMPLOYEE ) )
      {
         logonAction = "logoni.do";
      }
      else if ( role != null && role.equals( KANConstants.ROLE_CLIENT ) )
      {
         logonAction = "logonc.do";
      }
      else
      {
         logonAction = "";
      }
      if ( objects != null && objects.length > 0 )
      {
         titleB.append( BaseAction.getTitle( role ) );
         titleB.append( "用户名密码重置成功 （ " );
         titleB.append( getValue( objects, "nameZH" ) );
         titleB.append( ( getValue( objects, "nameEN" ) != null ? " - " + getValue( objects, "nameEN" ) : "" ) + "）" );
         contents[ 0 ] = titleB.toString();

         final String domain = KANConstants.DOMAIN.toLowerCase().indexOf( KANConstants.HTTP ) > 0 ? KANConstants.DOMAIN : ( KANConstants.HTTP + KANConstants.DOMAIN );
         final String accountName = getValue( objects, "accountName" );
         //         if ( StringUtils.isNotBlank( accountName ) )
         //         {
         //            messageB.append( "账户：" + accountName + "<br>" );
         //         }
         messageB.append( "用户名：" + getValue( objects, "username" ) + "<br>" );
         // messageB.append( "密码：" + Cryptogram.decodeString( getValue( objects, "password" ) ) );
         messageB.append( "密码：" + getValue( objects, "password" ) );

         //messageB.append( "登录地址： <a href=\"" + domain + "/" + KANConstants.PROJECT_NAME + "/" + logonAction + "\">" + BaseAction.getTitle( role ) + "</a>" );
         contents[ 1 ] = messageB.toString();
      }
      else
      {
         contents[ 0 ] = "无标题";
         contents[ 1 ] = BaseAction.getTitle( role ) + "向您的问候！";
      }

      return contents;
   }

   // 供应商用户创建时发送邮件 - 获取内容
   public static String[] getMailContent_UserCreate_Vendor( final Object[] objects ) throws KANException
   {
      // 初始化邮件标题和内容数组
      final String[] contents = new String[] { "", "" };
      final StringBuffer messageB = new StringBuffer();
      final StringBuffer titleB = new StringBuffer();
      final String role = KANUtil.filterEmpty( getValue( objects, "role" ) );
      if ( objects != null && objects.length > 0 )
      {
         titleB.append( BaseAction.getTitle( role ) );
         titleB.append( "供应商用户名密码重置成功 （ " );
         titleB.append( getValue( objects, "username" ) );
         titleB.append( "） " );
         contents[ 0 ] = titleB.toString();

         final String domain = KANConstants.DOMAIN.toLowerCase().indexOf( KANConstants.HTTP ) > 0 ? KANConstants.DOMAIN : ( KANConstants.HTTP + KANConstants.DOMAIN );
         final String vendorId = getValue( objects, "vendorId" );
         if ( StringUtils.isNotBlank( vendorId ) )
         {
            messageB.append( "供应商ID：" + vendorId + "<br>" );
         }
         messageB.append( "用户名：" + getValue( objects, "username" ) + "<br>" );
         messageB.append( "密码：" + Cryptogram.decodeString( getValue( objects, "password" ) ) + "<br>" );
         messageB.append( "登录地址： <a href=\"" + domain + "/" + KANConstants.PROJECT_NAME + "/" + "logonv.do" + "\">" + BaseAction.getTitle( role ) + "</a>" );
         contents[ 1 ] = messageB.toString();
      }
      else
      {
         contents[ 0 ] = "无标题";
         contents[ 1 ] = BaseAction.getTitle( getValue( objects, "role" ) ) + "向您的问候！";
      }

      return contents;
   }

   // 雇员用户创建时发送邮件 - 获取内容
   public static String[] getMailContent_UserCreate_Employee( final Object[] objects ) throws KANException
   {
      // 初始化邮件标题和内容数组
      final String[] contents = new String[] { "", "" };
      final String role = KANUtil.filterEmpty( getValue( objects, "role" ) );
      final String logonAction = "logone.do";
      final StringBuffer messageB = new StringBuffer();
      final StringBuffer titleB = new StringBuffer();
      if ( objects != null && objects.length > 0 )
      {
         titleB.append( BaseAction.getTitle( role ) );
         titleB.append( "雇员用户名密码重置成功 （ " );
         titleB.append( getValue( objects, "nameZH" ) );
         titleB.append( ( getValue( objects, "nameEN" ) != null ? " - " + getValue( objects, "nameEN" ) : "" ) + "）" );
         contents[ 0 ] = titleB.toString();

         final String domain = KANConstants.DOMAIN.toLowerCase().indexOf( KANConstants.HTTP ) > 0 ? KANConstants.DOMAIN : ( KANConstants.HTTP + KANConstants.DOMAIN );
         final String accountName = getValue( objects, "accountName" );
         //         if ( StringUtils.isNotBlank( accountName ) )
         //         {
         //            messageB.append( "账户：" + accountName + "<br>" );
         //         }
         messageB.append( "用户名：" + getValue( objects, "username" ) + "<br>" );
         messageB.append( "密码：" + Cryptogram.decodeString( getValue( objects, "password" ) ) + "<br>" );
         //不需要地址
         //messageB.append( "登录地址： <a href=\"" + domain + "/" + KANConstants.PROJECT_NAME + "/" + logonAction + "\">" + BaseAction.getTitle( role ) + "</a>" );
         contents[ 1 ] = messageB.toString();
      }
      else
      {
         contents[ 0 ] = "无标题";
         contents[ 1 ] = BaseAction.getTitle( role ) + "向您的问候！";
      }

      return contents;
   }

   // 社保批准发送邮件给供应商，通知操作 - 获取内容
   public static String[] getMailContent_SBNotice_Vendor( final Object[] objects ) throws KANException
   {
      // 初始化邮件标题和内容数组
      final String[] contents = new String[] { "", "" };

      if ( objects != null && objects.length > 0 )
      {
         contents[ 0 ] = "社保操作通知";
         contents[ 1 ] = getValue( objects, "nameZH" ) + "，\n您好！贵公司有社保参保数据等待处理，请登录系统查看。\n" + BaseAction.getTitle( getValue( objects, "role" ) );
      }
      else
      {
         contents[ 0 ] = "无标题";
         contents[ 1 ] = BaseAction.getTitle( getValue( objects, "role" ) ) + "向您的问候！";
      }

      return contents;
   }

   private static String getValue( final Object[] objects, final String field )
   {
      Object value = "";

      try
      {
         if ( objects != null && objects.length > 0 )
         {
            for ( Object object : objects )
            {
               value = KANUtil.getValue( object, field );

               if ( KANUtil.filterEmpty( value ) != null )
               {
                  break;
               }
            }
         }
      }
      catch ( KANException e )
      {
         //e.printStackTrace();
         // 这里是查找. 可以无视报错
      }

      return ( String ) value;
   }

   public static String getMailContent_forgetPassword( UserVO userVO )
   {
      String url = "https://hris.i-click.com/securityAction.do?proc=toResetPassword&validCode=" + userVO.getRemark5();
      StringBuffer result = new StringBuffer();
      result.append( "<html class='has-js' lang='en'><head><meta http-equiv='content-type' content='text/html; charset=utf-8'></head><body><div style='margin:auto;width:794px'><div style='padding-left:40px;padding-right:40px;'><p><p>" );
      result.append( userVO.getUsername() + ",您好:</p><p>此邮箱关联的HRIS账号启动了忘记密码。点击以下链接以继续。<br/><br/><a href=\"" );
      result.append( url );
      result.append( "\">重置密码</a><br/><br/>" );
      result.append( "如果重置密码链接被安全策略阻止,请复制以下链接到浏览器打开重置页面:<br/>" );
      result.append( url + "<br/><br/>" );
      result.append( "此链接将在电子邮件发出三天后过期。<br/><br/>" );
      result.append( "如果您并未提交此请求，可能是其他用户无意中输入了您的电子邮件地址，请忽略此邮件，您的帐户仍然安全。</p>" );
      result.append( "<hr /><p>Dear " + userVO.getUsername()
            + ":</p><p>A HRIS account associated with this email to start the forgotten password. Click the following link to continue.<br/><br/><a href=\"" );
      result.append( url );
      result.append( "\">Reset password</a><br/><br/>" );
      result.append( "If the reset password link is blocked by security policy, please copy the following link to the browser to open the reset page:<br/>" );
      result.append( url + "<br/><br/>" );
      result.append( "This link will expire in three days。<br/><br/>" );
      result.append( "If you do not submit this request, it may be that other users have no intention of entering your email address, please ignore this message, your account is still safe.</p>" );
      return result.toString();
   }
}

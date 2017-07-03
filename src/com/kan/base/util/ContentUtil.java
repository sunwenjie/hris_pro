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
   // �û�����ʱ�����ʼ� - ��ȡ����
   public static String[] getMailContent_UserCreate( final Object[] objects ) throws KANException
   {
      // ��ʼ���ʼ��������������
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
         titleB.append( "�û����������óɹ� �� " );
         titleB.append( getValue( objects, "nameZH" ) );
         titleB.append( ( getValue( objects, "nameEN" ) != null ? " - " + getValue( objects, "nameEN" ) : "" ) + "��" );
         contents[ 0 ] = titleB.toString();

         final String domain = KANConstants.DOMAIN.toLowerCase().indexOf( KANConstants.HTTP ) > 0 ? KANConstants.DOMAIN : ( KANConstants.HTTP + KANConstants.DOMAIN );
         final String accountName = getValue( objects, "accountName" );
         //         if ( StringUtils.isNotBlank( accountName ) )
         //         {
         //            messageB.append( "�˻���" + accountName + "<br>" );
         //         }
         messageB.append( "�û�����" + getValue( objects, "username" ) + "<br>" );
         // messageB.append( "���룺" + Cryptogram.decodeString( getValue( objects, "password" ) ) );
         messageB.append( "���룺" + getValue( objects, "password" ) );

         //messageB.append( "��¼��ַ�� <a href=\"" + domain + "/" + KANConstants.PROJECT_NAME + "/" + logonAction + "\">" + BaseAction.getTitle( role ) + "</a>" );
         contents[ 1 ] = messageB.toString();
      }
      else
      {
         contents[ 0 ] = "�ޱ���";
         contents[ 1 ] = BaseAction.getTitle( role ) + "�������ʺ�";
      }

      return contents;
   }

   // ��Ӧ���û�����ʱ�����ʼ� - ��ȡ����
   public static String[] getMailContent_UserCreate_Vendor( final Object[] objects ) throws KANException
   {
      // ��ʼ���ʼ��������������
      final String[] contents = new String[] { "", "" };
      final StringBuffer messageB = new StringBuffer();
      final StringBuffer titleB = new StringBuffer();
      final String role = KANUtil.filterEmpty( getValue( objects, "role" ) );
      if ( objects != null && objects.length > 0 )
      {
         titleB.append( BaseAction.getTitle( role ) );
         titleB.append( "��Ӧ���û����������óɹ� �� " );
         titleB.append( getValue( objects, "username" ) );
         titleB.append( "�� " );
         contents[ 0 ] = titleB.toString();

         final String domain = KANConstants.DOMAIN.toLowerCase().indexOf( KANConstants.HTTP ) > 0 ? KANConstants.DOMAIN : ( KANConstants.HTTP + KANConstants.DOMAIN );
         final String vendorId = getValue( objects, "vendorId" );
         if ( StringUtils.isNotBlank( vendorId ) )
         {
            messageB.append( "��Ӧ��ID��" + vendorId + "<br>" );
         }
         messageB.append( "�û�����" + getValue( objects, "username" ) + "<br>" );
         messageB.append( "���룺" + Cryptogram.decodeString( getValue( objects, "password" ) ) + "<br>" );
         messageB.append( "��¼��ַ�� <a href=\"" + domain + "/" + KANConstants.PROJECT_NAME + "/" + "logonv.do" + "\">" + BaseAction.getTitle( role ) + "</a>" );
         contents[ 1 ] = messageB.toString();
      }
      else
      {
         contents[ 0 ] = "�ޱ���";
         contents[ 1 ] = BaseAction.getTitle( getValue( objects, "role" ) ) + "�������ʺ�";
      }

      return contents;
   }

   // ��Ա�û�����ʱ�����ʼ� - ��ȡ����
   public static String[] getMailContent_UserCreate_Employee( final Object[] objects ) throws KANException
   {
      // ��ʼ���ʼ��������������
      final String[] contents = new String[] { "", "" };
      final String role = KANUtil.filterEmpty( getValue( objects, "role" ) );
      final String logonAction = "logone.do";
      final StringBuffer messageB = new StringBuffer();
      final StringBuffer titleB = new StringBuffer();
      if ( objects != null && objects.length > 0 )
      {
         titleB.append( BaseAction.getTitle( role ) );
         titleB.append( "��Ա�û����������óɹ� �� " );
         titleB.append( getValue( objects, "nameZH" ) );
         titleB.append( ( getValue( objects, "nameEN" ) != null ? " - " + getValue( objects, "nameEN" ) : "" ) + "��" );
         contents[ 0 ] = titleB.toString();

         final String domain = KANConstants.DOMAIN.toLowerCase().indexOf( KANConstants.HTTP ) > 0 ? KANConstants.DOMAIN : ( KANConstants.HTTP + KANConstants.DOMAIN );
         final String accountName = getValue( objects, "accountName" );
         //         if ( StringUtils.isNotBlank( accountName ) )
         //         {
         //            messageB.append( "�˻���" + accountName + "<br>" );
         //         }
         messageB.append( "�û�����" + getValue( objects, "username" ) + "<br>" );
         messageB.append( "���룺" + Cryptogram.decodeString( getValue( objects, "password" ) ) + "<br>" );
         //����Ҫ��ַ
         //messageB.append( "��¼��ַ�� <a href=\"" + domain + "/" + KANConstants.PROJECT_NAME + "/" + logonAction + "\">" + BaseAction.getTitle( role ) + "</a>" );
         contents[ 1 ] = messageB.toString();
      }
      else
      {
         contents[ 0 ] = "�ޱ���";
         contents[ 1 ] = BaseAction.getTitle( role ) + "�������ʺ�";
      }

      return contents;
   }

   // �籣��׼�����ʼ�����Ӧ�̣�֪ͨ���� - ��ȡ����
   public static String[] getMailContent_SBNotice_Vendor( final Object[] objects ) throws KANException
   {
      // ��ʼ���ʼ��������������
      final String[] contents = new String[] { "", "" };

      if ( objects != null && objects.length > 0 )
      {
         contents[ 0 ] = "�籣����֪ͨ";
         contents[ 1 ] = getValue( objects, "nameZH" ) + "��\n���ã���˾���籣�α����ݵȴ��������¼ϵͳ�鿴��\n" + BaseAction.getTitle( getValue( objects, "role" ) );
      }
      else
      {
         contents[ 0 ] = "�ޱ���";
         contents[ 1 ] = BaseAction.getTitle( getValue( objects, "role" ) ) + "�������ʺ�";
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
         // �����ǲ���. �������ӱ���
      }

      return ( String ) value;
   }

   public static String getMailContent_forgetPassword( UserVO userVO )
   {
      String url = "https://hris.i-click.com/securityAction.do?proc=toResetPassword&validCode=" + userVO.getRemark5();
      StringBuffer result = new StringBuffer();
      result.append( "<html class='has-js' lang='en'><head><meta http-equiv='content-type' content='text/html; charset=utf-8'></head><body><div style='margin:auto;width:794px'><div style='padding-left:40px;padding-right:40px;'><p><p>" );
      result.append( userVO.getUsername() + ",����:</p><p>�����������HRIS�˺��������������롣������������Լ�����<br/><br/><a href=\"" );
      result.append( url );
      result.append( "\">��������</a><br/><br/>" );
      result.append( "��������������ӱ���ȫ������ֹ,�븴���������ӵ������������ҳ��:<br/>" );
      result.append( url + "<br/><br/>" );
      result.append( "�����ӽ��ڵ����ʼ������������ڡ�<br/><br/>" );
      result.append( "�������δ�ύ�����󣬿����������û����������������ĵ����ʼ���ַ������Դ��ʼ��������ʻ���Ȼ��ȫ��</p>" );
      result.append( "<hr /><p>Dear " + userVO.getUsername()
            + ":</p><p>A HRIS account associated with this email to start the forgotten password. Click the following link to continue.<br/><br/><a href=\"" );
      result.append( url );
      result.append( "\">Reset password</a><br/><br/>" );
      result.append( "If the reset password link is blocked by security policy, please copy the following link to the browser to open the reset page:<br/>" );
      result.append( url + "<br/><br/>" );
      result.append( "This link will expire in three days��<br/><br/>" );
      result.append( "If you do not submit this request, it may be that other users have no intention of entering your email address, please ignore this message, your account is still safe.</p>" );
      return result.toString();
   }
}

package com.kan.base.web.actions;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.kan.base.domain.system.AccountVO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.service.inf.system.AccountService;
import com.kan.base.service.inf.system.ModuleService;
import com.kan.base.util.CachedUtil;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.RandomUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientUserVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.client.ClientUserService;

public class ClientSecurityAction extends BaseAction
{
   /**  
    * Logon InVendor
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward logon_inClient( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��ActionErrors
         final ActionErrors errors = new ActionErrors();
         // ��ʼ����¼�Ƿ����
         Boolean failedFlag = false;

         // ��ʼ��Service
         final ClientUserService clientUserService = ( ClientUserService ) getService( "clientUserService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final AccountService accountService = ( AccountService ) getService( "accountService" );
         final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );

         // ��ʼ��Form
         ClientUserVO clientUserVO = ( ClientUserVO ) form;

         //ֱ������accountId��¼
         AccountVO accountVO = null;
         if ( StringUtils.isNotEmpty( StringUtils.trim( clientUserVO.getAccountId() ) ) )
         {
            accountVO = accountService.getAccountVOByAccountId( clientUserVO.getAccountId() );
            if ( accountVO != null )
            {
               clientUserVO.setAccountId( accountVO.getAccountId() );
               clientUserVO.setAccountName( accountVO.getNameCN() );
            }
         }
         else if ( StringUtils.isNotEmpty( StringUtils.trim( clientUserVO.getAccountName() ) ) )
         {
            if ( checkNumeric( StringUtils.trim( clientUserVO.getAccountName() ) ) )
            {
               accountVO = accountService.getAccountVOByAccountId( clientUserVO.getAccountName().trim() );
               if ( accountVO != null )
               {
                  clientUserVO.setAccountId( accountVO.getAccountId() );
                  clientUserVO.setAccountName( accountVO.getNameCN() );
               }
            }
            else
            {
               accountVO = accountService.getAccountVOByAccountName( clientUserVO.getAccountName().trim() );
               if ( accountVO != null )
               {
                  clientUserVO.setAccountId( accountVO.getAccountId() );
                  clientUserVO.setAccountName( accountVO.getNameCN() );
               }
            }
         }

         // �ж��˻������Ƿ���Ч
         if ( accountVO == null || ( accountVO != null && !"1".equals( accountVO.getDeleted() ) ) )
         {
            errors.add( "LoginAccountError", new ActionMessage( "error.security.account" ) );
            addErrors( request, errors );
            ( ( ClientUserVO ) form ).reset();
            ( ( ClientUserVO ) form ).setPassword( "" );
            return mapping.findForward( "logon_inClient" );
         }

         final ClientUserVO loginClientUserVO = clientUserService.getClientUserByName( clientUserVO );

         // �ж��û����Ƿ���Ч
         if ( loginClientUserVO == null )
         {
            errors.add( "LoginUsernameError", new ActionMessage( "error.security.username" ) );
            failedFlag = true;
         }
         else
         {
            // �ж��û��������Ƿ���Ч
            if ( !KANUtil.decodeString( loginClientUserVO.getPassword() ).equals( clientUserVO.getPassword() ) )
            {
               errors.add( "LoginPasswordError", new ActionMessage( "error.security.password" ) );
               failedFlag = true;
            }

            // �жϵ�¼IP�Ƿ���Ч
            if ( KANUtil.filterEmpty( loginClientUserVO.getBindIP() ) != null && !loginClientUserVO.getBindIP().trim().contains( getIPAddress( request ) ) )
            {
               errors.add( "LoginBindipError", new ActionMessage( "error.security.bindip" ) );
               failedFlag = true;
            }
         }

         // �����¼������ת����¼ҳ��
         if ( failedFlag )
         {
            addErrors( request, errors );
            ( ( ClientUserVO ) form ).reset();
            ( ( ClientUserVO ) form ).setPassword( "" );

            return mapping.findForward( "logon_inClient" );
         }
         ClientVO clientVO = clientService.getClientVOByClientId( loginClientUserVO.getClientId() );
         loginClientUserVO.setClientName( clientVO.getClientName() );

         // ��ʼ����¼���󣨹��ʻ���
         loginClientUserVO.reset( mapping, request );

         // �����û���¼��Ϣ
         loginClientUserVO.setCbPersistentCookie( clientUserVO.getCbPersistentCookie() );
         // �����û���¼���к�
         loginClientUserVO.setUserToken( RandomUtil.getRandomString( 16 ) );
         // �����û���ɫ
         loginClientUserVO.setRole( KANConstants.ROLE_CLIENT );
         // ���������¼ʱ��
         loginClientUserVO.setLastLogin( KANUtil.formatDate( new Date(), null ) );
         // ���������¼IP��ַ
         loginClientUserVO.setLastLoginIP( getIPAddress( request ) );
         // ����ϵͳID
         loginClientUserVO.setSystemId( KANConstants.SYS_HR_SERVICE );

         loginClientUserVO.setModifyDate( new Date() );

         clientUserService.updateClientUser( loginClientUserVO );
         
         setClientModule( request, moduleService, clientVO, KANConstants.ROLE_CLIENT );

         clientUserVO.setClientUserId( loginClientUserVO.getClientUserId() );

         // ����Request Attribute
         request.setAttribute( "accountId", loginClientUserVO.getAccountId() );
         request.setAttribute( "accountName", clientUserVO.getAccountName() );
         request.setAttribute( "entityName", accountVO.getEntityName() );
         request.setAttribute( "role", KANConstants.ROLE_CLIENT );
         request.setAttribute( "clientId", loginClientUserVO.getClientId() );
         request.setAttribute( "userId", loginClientUserVO.getClientUserId() );
         request.setAttribute( "username", loginClientUserVO.getUsername() );
         request.setAttribute( "clientName", loginClientUserVO.getClientName() );
         request.setAttribute( "userToken", loginClientUserVO.getUserToken() );
         this.saveUserToClient( response, request, loginClientUserVO );
         this.saveClientUserToCookie( response, request, clientUserVO, AUTO_CLIENT_USER );
         ( ( ClientUserVO ) form ).reset();
         return mapping.findForward( "index" );
      }
      catch ( Exception e )
      {
         throw new KANException( KANUtil.getProperty( request.getLocale(), "exception.SecurityAction.logon" ), e, request, response );
      }
   }

   /**
    *  Change Password
    *  
    *  @param mapping
    *  @param form
    *  @param request
    *  @param response
    *  @return
    *  @throws KANException
     */
   // Reviewed by Kevin Jin at 2014-04-15
   public ActionForward change_password( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
          throws KANException
    {
       try
       {
          // ��ȡPassword
          final String oldPassword = request.getParameter( "oldPassword" );
          final String newPassword = request.getParameter( "newPassword" );

          if ( KANUtil.filterEmpty( oldPassword ) != null && KANUtil.filterEmpty( newPassword ) != null && KANUtil.filterEmpty( getClientUserId( request, response ) ) != null )
          {
             // ��ʼ��Service�ӿ�
             final ClientUserService clientUserService = ( ClientUserService ) getService( "clientUserService" );

             // ��ȡUserVO
             final ClientUserVO clientUserVO = clientUserService.getClientUserVOByUserId( getClientUserId( request, response ) );

             // ��֤������
             if ( Cryptogram.encodeString( oldPassword ).equals( clientUserVO.getPassword() ) )
             {
                clientUserVO.setPassword( Cryptogram.encodeString( newPassword ) );
                clientUserService.updateClientUser( clientUserVO );
                request.setAttribute( "MESSAGE", "�޸ĳɹ��������µ�¼��" );
             }
             else
             {
                request.setAttribute( "MESSAGE", "�޸�ʧ�ܣ����ʵ���ĵ�ǰ�����Ƿ���ȷ��" );
             }
          }
       }
       catch ( Exception e )
       {
          throw new KANException( e );
       }

       return new SecurityAction().logout( mapping, form, request, response );
    }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   /**
    * 
    * 
    * @author : Ian.huang
    * @date   : 2014-6-30
    * @param  : @param mapping
    * @param  : @param form
    * @param  : @param request
    * @param  : @param response
    * @param  : @return
    * @param  : @throws KANException
    * @return : ActionForward
    */
   public ActionForward autoLogon( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      JSONObject jsonObject = new JSONObject();
      ClientUserVO clientUserVO = new ClientUserVO();
      try
      {
         final ClientUserService clientUserService = ( ClientUserService ) getService( "clientUserService" );
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         Cookie[] cookies = request.getCookies();
         String[] cooks = null;
         String username = null;
         String clientUserId = null;
         String password = null;
         String accountId = null;
         String accountName = null;
         boolean flag = false;
         if ( cookies != null )
         {
            for ( Cookie cook : cookies )
            {
               String cookieName = cook.getName();
               if ( !cookieName.equalsIgnoreCase( AUTO_CLIENT_USER ) )
               {
                  continue;
               }
               String cookieValue = cook.getValue();
               cooks = cookieValue.split( "_" );
               if ( cooks.length == 5 )
               {
                  clientUserId = java.net.URLDecoder.decode( cooks[ 0 ], "gbk" );
                  username = java.net.URLDecoder.decode( cooks[ 1 ], "gbk" );
                  password = java.net.URLDecoder.decode( cooks[ 2 ], "gbk" );
                  accountId = java.net.URLDecoder.decode( cooks[ 3 ], "gbk" );
                  accountName = java.net.URLDecoder.decode( cooks[ 4 ], "gbk" );
               }
            }
         }
         if ( username != null && password != null && clientUserId != null )
         {
            clientUserVO.setAccountId( accountId );
            clientUserVO.setAccountName( accountName );
            clientUserVO.setClientUserId( clientUserId );
            clientUserVO.setUsername( username );
            clientUserVO.setPassword( password );

            // �жϵ�¼��Ӧ���û��Ƿ���Ч
            final ClientUserVO loginClientUserVO = clientUserService.getClientUserVOByUserId( clientUserId );

            // �ж��û����Ƿ���Ч
            if ( loginClientUserVO == null || ( loginClientUserVO != null && !"1".equals( loginClientUserVO.getDeleted() ) ) )
            {
               flag = false;
               removeAutoLogonToCookie( response, request, AUTO_CLIENT_USER );
            }
            else
            {
               jsonObject = JSONObject.fromObject( clientUserVO );
               flag = true;
               // �ж��û��������Ƿ���Ч
               if ( !KANUtil.decodeString( loginClientUserVO.getPassword() ).equals( password ) )
               {
                  flag = false;
                  removeAutoLogonToCookie( response, request, AUTO_CLIENT_USER );
               }

               // �жϵ�¼IP�Ƿ���Ч
               if ( loginClientUserVO.getBindIP() != null && !loginClientUserVO.getBindIP().trim().equalsIgnoreCase( "" ) )
               {
                  if ( !loginClientUserVO.getBindIP().trim().contains( getIPAddress( request ) ) )
                  {
                     flag = false;
                     removeAutoLogonToCookie( response, request, AUTO_CLIENT_USER );
                  }
               }
            }
            jsonObject.put( "success", flag );
            out.println( jsonObject != null ? jsonObject.toString() : "" );
            out.flush();
            out.close();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "" );
   }

   private static boolean checkNumeric( String str )
   {
      if ( str == null || str.equals( "" ) )
      {
         return false;
      }
      Pattern pattern = Pattern.compile( "[0-9]*" );
      return pattern.matcher( str ).matches();
   }

   private void saveClientUserToCookie( final HttpServletResponse response, final HttpServletRequest request, final ClientUserVO clientUserVO, final String type )
         throws KANException
   {
      try
      {
         if ( clientUserVO.getCbPersistentCookie() != null && clientUserVO.getCbPersistentCookie().equals( "on" ) )
         {
            // Json�ֽ��������ͻ���
            String cookieUser = clientUserVO.getClientUserId() + "_" + clientUserVO.getUsername() + "_" + clientUserVO.getPassword() + "_" + clientUserVO.getAccountId() + "_"
                  + clientUserVO.getAccountName();

            final Cookie cookie = new Cookie( type, URLEncoder.encode( cookieUser, "gbk" ) );
            cookie.setMaxAge( 15 * 24 * 60 * 60 );
            response.addCookie( cookie );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   private void saveUserToClient( final HttpServletResponse response, final HttpServletRequest request, final ClientUserVO clientUserVO ) throws KANException
   {
      try
      {
         // Json�ֽ��������ͻ���
         String cookieUser = JSONObject.fromObject( clientUserVO, ClientUserVO.USERVO_JSONCONFIG ).toString();
         JSONObject cookieUser_JSON = JSONObject.fromObject( cookieUser );

         request.setAttribute( COOKIE_USER_JSON, cookieUser_JSON );
         final Cookie cookie = new Cookie( COOKIE_USER, URLEncoder.encode( cookieUser, "gbk" ) );
         response.addCookie( cookie );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public static String getClientUserId( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final JSONObject jsonObject = getUserFromClient( request, response );
      return jsonObject != null ? ( String ) jsonObject.get( "clientUserId" ) : ( String ) request.getAttribute( "userId" );
   }

   public static String getClientId( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final JSONObject jsonObject = getUserFromClient( request, response );
      return jsonObject != null ? ( String ) jsonObject.get( "clientId" ) : ( String ) request.getAttribute( "clientId" );
   }
   
   private void setClientModule( final HttpServletRequest request, final ModuleService moduleService, final ClientVO clientVO, final String role ) throws KANException
   {
      List< ModuleVO > listModuleVO = moduleService.getClientModuleVOs( clientVO.getAccountId(), clientVO.getClientId(), role );
      CachedUtil.set( request, "CLIENT_MODULE_" + clientVO.getClientId(), listModuleVO );
   }
}
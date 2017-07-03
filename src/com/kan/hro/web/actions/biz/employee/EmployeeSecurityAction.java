/*
 * Created on 2007-1-11
 */
package com.kan.hro.web.actions.biz.employee;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.kan.base.domain.security.UserVO;
import com.kan.base.domain.system.AccountVO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.service.inf.security.UserService;
import com.kan.base.service.inf.system.AccountService;
import com.kan.base.service.inf.system.ModuleService;
import com.kan.base.util.CachedUtil;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Mail;
import com.kan.base.util.PassWordUtil;
import com.kan.base.util.RandomUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.SecurityAction;
import com.kan.base.web.actions.system.AccountAction;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeUserVO;
import com.kan.hro.domain.biz.employee.EmployeeVO;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.employee.EmployeeUserService;

/**
 * @author steven
 */
public class EmployeeSecurityAction extends BaseAction
{

   /**  
    * Logon
    *	HRO ��Ա��¼
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward logon( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service
         final EmployeeUserService employeeUserService = ( EmployeeUserService ) getService( "employeeUserService" );
         final AccountService accountService = ( AccountService ) getService( "accountService" );
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );
         final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );

         // ��ʼ��Form
         EmployeeUserVO employeeUserVO = ( EmployeeUserVO ) form;

         //ֱ������accountId��¼
         if ( StringUtils.isNotBlank( employeeUserVO.getAccountName() ) && checkNumeric( employeeUserVO.getAccountName().trim() ) )
         {
            AccountVO tempAccountVO = accountService.getAccountVOByAccountId( employeeUserVO.getAccountName().trim() );
            employeeUserVO.setAccountName( tempAccountVO.getNameCN() );
            employeeUserVO.setAccountId( tempAccountVO.getAccountId() );
         }
         else if ( employeeUserVO.getAccountId() == null || employeeUserVO.getAccountId().equals( "" ) )
         {
            AccountVO accountVO = accountService.getAccountVOByAccountName( employeeUserVO.getAccountName().trim() );
            if ( accountVO != null )
            {
               employeeUserVO.setAccountId( accountVO.getAccountId() );
               employeeUserVO.setAccountName( accountVO.getNameCN() );
            }
         }

         final EmployeeUserVO loginEmployeeUserVO = employeeUserService.login( employeeUserVO );

         // ��ʼ��ActionErrors
         final ActionErrors errors = new ActionErrors();
         // ��ʼ����¼�Ƿ����
         Boolean failedFlag = false;

         // �ж��˻������Ƿ���Ч
         if ( !employeeUserVO.getUsername().equalsIgnoreCase( "super" ) )
         {
            // ��ʼ����¼AccountVO
            final AccountVO tempAccountVO = accountService.getAccountVOByAccountId( employeeUserVO.getAccountId() );

            if ( tempAccountVO == null )
            {
               errors.add( "LoginAccountError", new ActionMessage( "error.security.account" ) );
               failedFlag = true;
               addErrors( request, errors );
               ( ( UserVO ) form ).reset();
               ( ( UserVO ) form ).setPassword( "" );

               return mapping.findForward( "logon" );
            }
         }

         // �ж��û����Ƿ���Ч
         if ( loginEmployeeUserVO == null )
         {
            errors.add( "LoginUsernameError", new ActionMessage( "error.security.username" ) );
            failedFlag = true;
         }
         else
         {
            // �ж��û��������Ƿ���Ч
            if ( !KANUtil.decodeString( loginEmployeeUserVO.getPassword() ).equals( employeeUserVO.getPassword() ) )
            {
               errors.add( "LoginPasswordError", new ActionMessage( "error.security.password" ) );
               failedFlag = true;
            }

            if ( StringUtils.isNotBlank( loginEmployeeUserVO.getAccountId() ) && !( loginEmployeeUserVO.getAccountId().trim() ).equals( "1" )
                  && !( loginEmployeeUserVO.getAccountId().trim() ).equals( employeeUserVO.getAccountId() ) )
            {
               errors.add( "LoginAccountError", new ActionMessage( "error.security.account" ) );
               failedFlag = true;
            }

            // �жϵ�¼IP�Ƿ���Ч
            if ( StringUtils.isNotBlank( loginEmployeeUserVO.getBindIP() ) && !loginEmployeeUserVO.getBindIP().trim().contains( getIPAddress( request ) ) )
            {
               errors.add( "LoginBindipError", new ActionMessage( "error.security.bindip" ) );
               failedFlag = true;
            }
         }

         // �����¼������ת����¼ҳ��
         if ( failedFlag )
         {
            addErrors( request, errors );
            ( ( EmployeeUserVO ) form ).reset();
            ( ( EmployeeUserVO ) form ).setPassword( "" );

            return mapping.findForward( "logonEmployee" );
         }
         else if ( loginEmployeeUserVO != null )
         {
            // ��ʼ����¼���󣨹��ʻ���
            loginEmployeeUserVO.reset( mapping, request );
         }

         // ����û���Ӧ��Account��Ϣ
         final AccountVO accountVO = accountService.getAccountVOByAccountId( loginEmployeeUserVO.getAccountId() );
         final List< Object > contractList = employeeContractService.getEmployeeContractVOsByEmployeeId( loginEmployeeUserVO.getEmployeeId() );
         String clientId = null;
         if ( contractList != null && contractList.size() > 0 )
         {
            clientId = ( ( EmployeeContractVO ) ( contractList.get( 0 ) ) ).getClientId();
            loginEmployeeUserVO.setClientId( clientId );
         }
         // �����û���˾����
         loginEmployeeUserVO.setEntityName( accountVO != null ? accountVO.getEntityName() : "" );
         // �Ƿ��Զ���¼
         //         loginEmployeeUserVO.setCbPersistentCookie( employeeUserVO.getCbPersistentCookie() );
         // �����û���¼���к�
         loginEmployeeUserVO.setUserToken( RandomUtil.getRandomString( 16 ) );
         // �����û���ɫ
         loginEmployeeUserVO.setRole( KANConstants.ROLE_EMPLOYEE );
         // ���������¼ʱ��
         loginEmployeeUserVO.setLastLogin( new Date() );
         // ���������¼IP��ַ
         loginEmployeeUserVO.setLastLoginIP( getIPAddress( request ) );
         // ����ϵͳID
         loginEmployeeUserVO.setSystemId( KANConstants.SYS_HR_SERVICE );
         loginEmployeeUserVO.setModifyDate( new Date() );
         loginEmployeeUserVO.setUserId( loginEmployeeUserVO.getEmployeeId() );
         employeeUserService.updateEmployeeUser( loginEmployeeUserVO );
         ClientVO clientVO = new ClientVO();
         clientVO.setAccountId( accountVO.getAccountId() );
         clientVO.setClientId( clientId );
         setClientModule( request, moduleService, clientVO, KANConstants.ROLE_EMPLOYEE );
         // ����Request Attribute
         request.setAttribute( "accountId", loginEmployeeUserVO.getAccountId() );
         request.setAttribute( "employeeId", loginEmployeeUserVO.getEmployeeId() );
         request.setAttribute( "employeeUserId", loginEmployeeUserVO.getEmployeeUserId() );
         request.setAttribute( "userId", loginEmployeeUserVO.getEmployeeId() );
         request.setAttribute( "username", loginEmployeeUserVO.getUsername() );
         request.setAttribute( "entityName", loginEmployeeUserVO.getEntityName() );
         request.setAttribute( "userToken", loginEmployeeUserVO.getUserToken() );
         request.setAttribute( "role", loginEmployeeUserVO.getRole() );
         request.setAttribute( "systemId", KANConstants.SYS_HR_SERVICE );
         request.setAttribute( "clientId", loginEmployeeUserVO.getClientId() );

         // �����ƽ̨�˻���¼��Super��
         if ( KANUtil.filterEmpty( loginEmployeeUserVO.getAccountId() ).equalsIgnoreCase( "1" ) )
         {
            // ��UserVO����д��ͻ�Cookie
            this.saveEmployeeUserToClient( response, request, loginEmployeeUserVO );
            super.saveUserToClient( response, request, loginEmployeeUserVO );
            //            this.saveEmployeeUserToCookie( response, request, employeeUserVO ,super.AUTO_LOGON_USER);
            return new AccountAction().list_object( mapping, new AccountVO(), request, response );
         }
         // �����һ���û���¼
         else
         {
            // ��UserVO����д��ͻ�Cookie
            this.saveEmployeeUserToClient( response, request, loginEmployeeUserVO );
            super.saveUserToClient( response, request, loginEmployeeUserVO );
            ( ( EmployeeUserVO ) form ).reset();

            EmployeeVO employeeVO = new EmployeeVO();
            return new EmployeeAction().to_objectModify( mapping, employeeVO, request, response );
         }
      }
      catch ( Exception e )
      {
         throw new KANException( KANUtil.getProperty( request.getLocale(), "exception.EmployeeSecurityAction.logon" ), e, request, response );
      }
   }

   public void saveEmployeeUserToClient( final HttpServletResponse response, final HttpServletRequest request, final EmployeeUserVO employeeUserVO ) throws KANException
   {
      try
      {
         // Json�ֽ��������ͻ���
         String cookieUser = JSONObject.fromObject( employeeUserVO, EmployeeUserVO.EMPLOYEEUSERVO_JSONCONFIG ).toString();
         JSONObject cookieUser_JSON = JSONObject.fromObject( cookieUser );

         request.setAttribute( COOKIE_EMPLOYEE_USER_JSON, cookieUser_JSON );
         final Cookie cookie = new Cookie( COOKIE_EMPLOYEE_USER, URLEncoder.encode( cookieUser, "gbk" ) );
         response.addCookie( cookie );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // Get property from Cookie object
   public static String getPropertyFromEmployeeUserCookie( final HttpServletRequest request, final HttpServletResponse response, final String propertyName ) throws KANException
   {
      final JSONObject jsonObject = getEmployeeUserFromClient( request, response );
      return jsonObject != null ? ( String ) jsonObject.get( propertyName ) : ( String ) request.getAttribute( propertyName );
   }

   public static String getEmployeeId( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      return getPropertyFromEmployeeUserCookie( request, response, "employeeId" );
   }

   public static String getRole( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      return getPropertyFromEmployeeUserCookie( request, response, "role" );
   }

   public static JSONObject getEmployeeUserFromClient( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         JSONObject cookieUser = ( JSONObject ) request.getAttribute( COOKIE_EMPLOYEE_USER_JSON );
         if ( cookieUser != null && KANUtil.filterEmpty( cookieUser.getString( "accountId" ) ) != null )
         {
            return cookieUser;
         }
         else
         {
            // �ӿͻ��˻�ȡJson�ֽ�����ת����User����
            final Cookie cookies[] = request.getCookies();

            if ( cookies != null )
            {
               for ( Cookie cookie : cookies )
               {
                  if ( cookie.getName().equalsIgnoreCase( COOKIE_EMPLOYEE_USER ) )
                  {
                     if ( cookie.getValue() != null && !cookie.getValue().trim().equalsIgnoreCase( "" ) )
                     {
                        JSONObject cookieUser_JSON = JSONObject.fromObject( URLDecoder.decode( cookie.getValue(), "gbk" ) );
                        if ( KANUtil.filterEmpty( cookieUser_JSON.getString( "accountId" ) ) != null )
                        {
                           request.setAttribute( COOKIE_EMPLOYEE_USER_JSON, cookieUser_JSON );
                           return cookieUser_JSON;
                        }
                        else
                        {
                           return null;
                        }
                     }
                     else
                     {
                        return null;
                     }
                  }
               }
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   public ActionForward to_index( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return mapping.findForward( "index" );
   }

   /**
   * 	Change Password
   *	
   *	@param mapping
   *	@param form
   *	@param request
   *	@param response
   *	@return
   *	@throws KANException
    */
   // Reviewed by steven at 2014-04-15
   public ActionForward change_password( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ȡPassword
         final String oldPassword = request.getParameter( "oldPassword" );
         final String newPassword = request.getParameter( "newPassword" );

         if ( KANUtil.filterEmpty( oldPassword ) != null && KANUtil.filterEmpty( newPassword ) != null && KANUtil.filterEmpty( getUserId( request, response ) ) != null )
         {
            // ��ʼ��Service�ӿ�
            // ��ʼ��Service
            final EmployeeUserService employeeUserService = ( EmployeeUserService ) getService( "employeeUserService" );

            // ��ȡUserVO
            final EmployeeUserVO employeeUserVO = employeeUserService.getEmployeeUserByEmployeeId( getUserId( request, response ) );

            // ��֤������
            if ( Cryptogram.encodeString( oldPassword ).equals( employeeUserVO.getPassword() ) )
            {
               employeeUserVO.setPassword( Cryptogram.encodeString( newPassword ) );
               employeeUserService.updateEmployeeUser( employeeUserVO );
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

      return ( new SecurityAction() ).logout( mapping, form, request, response );
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
    *  �ַ����Ƿ��ת������
    * 
    * @author : steven.wang
    * @date   : 2014-6-24
    * @param  : @param str
    * @param  : @return
    * @return : boolean
    */
   public static boolean checkNumeric( String str )
   {
      if ( str == null || str.equals( "" ) )
      {
         return false;
      }
      Pattern pattern = Pattern.compile( "[0-9]*" );
      return pattern.matcher( str ).matches();
   }

   /**
    * �Զ���¼����cookie
    * 
    * @author : steven.wang
    * @date   : 2014-6-30
    * @param  : @param response
    * @param  : @param request
    * @param  : @param employeeUserVO
    * @param  : @param type
    * @param  : @throws KANException
    * @return : void
    */
   private void saveEmployeeUserToCookie( final HttpServletResponse response, final HttpServletRequest request, final EmployeeUserVO employeeUserVO, final String type )
         throws KANException
   {
      try
      {
         if ( employeeUserVO.getCbPersistentCookie() != null && employeeUserVO.getCbPersistentCookie().equals( "on" ) )
         {
            // Json�ֽ��������ͻ���
            String cookieUser = employeeUserVO.getAccountId() + "_" + employeeUserVO.getAccountName() + "_" + employeeUserVO.getEmployeeId() + "_"
                  + employeeUserVO.getEmployeeUserId() + "_" + employeeUserVO.getUsername() + "_" + employeeUserVO.getPassword() + "_" + employeeUserVO.getCorpId();
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

   /**
    * 
    * 
    * @author : steven.wang
    * @date   : 2014-7-8
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
      UserVO employeeUserVO = new UserVO();
      try
      {
         final UserService userService = ( UserService ) getService( "userService" );
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();
         String type = request.getParameter( "type" );
         if ( type == null || type == "" )
         {
            jsonObject.put( "success", false );
            return mapping.findForward( "" );
         }
         Cookie[] cookies = request.getCookies();
         String[] cooks = null;
         String userName = null;
         String password = null;
         String accountId = null;
         String accountName = null;
         String corpId = null;
         boolean flag = false;
         if ( cookies != null )
         {
            for ( Cookie cook : cookies )
            {
               String cookieName = cook.getName();
               if ( !cookieName.equalsIgnoreCase( type ) )
               {
                  continue;
               }
               String cookieValue = cook.getValue();
               cooks = cookieValue.split( "_" );
               if ( cooks.length == 5 )
               {
                  accountId = java.net.URLDecoder.decode( cooks[ 0 ], "gbk" );
                  userName = java.net.URLDecoder.decode( cooks[ 2 ], "gbk" );
                  accountName = java.net.URLDecoder.decode( cooks[ 1 ], "gbk" );
                  password = java.net.URLDecoder.decode( cooks[ 3 ], "gbk" );
                  corpId = java.net.URLDecoder.decode( cooks[ 4 ], "gbk" );
               }
            }
         }
         if ( userName != null && password != null && accountId != null )
         {
            employeeUserVO.setAccountId( accountId );
            employeeUserVO.setAccountName( accountName );
            employeeUserVO.setUsername( userName );
            employeeUserVO.setPassword( password );
            employeeUserVO.setCorpId( corpId );
            if ( type.equals( AUTO_LOGON_USER ) )
            {
               final UserVO loginUserVO = userService.login( employeeUserVO );
               if ( loginUserVO != null )
               {
                  jsonObject = JSONObject.fromObject( employeeUserVO );
                  flag = true;
                  // �ж��û��������Ƿ���Ч
                  if ( !KANUtil.decodeString( loginUserVO.getPassword() ).equals( employeeUserVO.getPassword() ) )
                  {
                     flag = false;
                     removeAutoLogonToCookie( response, request, AUTO_LOGON_USER );
                  }

                  if ( KANUtil.filterEmpty( loginUserVO.getAccountId() ) != null && !KANUtil.filterEmpty( loginUserVO.getAccountId() ).equals( "1" )
                        && !KANUtil.filterEmpty( loginUserVO.getAccountId() ).equals( employeeUserVO.getAccountId() ) )
                  {
                     flag = false;
                     removeAutoLogonToCookie( response, request, AUTO_LOGON_USER );
                  }

                  // �жϵ�¼IP�Ƿ���Ч
                  if ( KANUtil.filterEmpty( loginUserVO.getBindIP() ) != null && !loginUserVO.getBindIP().trim().contains( getIPAddress( request ) ) )
                  {
                     flag = false;
                     removeAutoLogonToCookie( response, request, AUTO_LOGON_USER );
                  }

               }
               else
               {
                  flag = false;
                  removeAutoLogonToCookie( response, request, type );
               }
            }
            else if ( type.equals( AUTO_LOGONI_USER ) )
            {
               final UserVO loginUserVO = userService.login_inHouse( employeeUserVO );
               if ( loginUserVO != null )
               {
                  jsonObject = JSONObject.fromObject( employeeUserVO );
                  flag = true;
                  // �ж��û��������Ƿ���Ч
                  if ( !KANUtil.decodeString( loginUserVO.getPassword() ).equals( employeeUserVO.getPassword() ) )
                  {
                     flag = false;
                     removeAutoLogonToCookie( response, request, AUTO_LOGONI_USER );
                  }
                  // �жϵ�¼IP�Ƿ���Ч
                  if ( KANUtil.filterEmpty( loginUserVO.getBindIP() ) != null && !loginUserVO.getBindIP().trim().contains( getIPAddress( request ) ) )
                  {
                     flag = false;
                     removeAutoLogonToCookie( response, request, AUTO_LOGONI_USER );
                  }

               }
               else
               {
                  flag = false;
                  removeAutoLogonToCookie( response, request, AUTO_LOGONI_USER );
               }
            }
            jsonObject.put( "success", flag );
            // Send to client
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

   // ��� �Ƿ���ְλ/�û����Ƿ����
   public ActionForward check_object_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final EmployeeUserService employeeUserService = ( EmployeeUserService ) getService( "employeeUserService" );
         // ��ȡ�����Username
         final String username = URLDecoder.decode( URLDecoder.decode( request.getParameter( "username" ), "UTF-8" ), "UTF-8" );
         // ��ȡ�����employeeId
         final String employeeId = request.getParameter( "employeeId" );

         // ��ʼ����������
         final EmployeeUserVO employeeUserVO = new EmployeeUserVO();
         employeeUserVO.setUsername( username );
         employeeUserVO.setEmployeeId( employeeId );

         // ��ѯ���ݿ��Ƿ��Ѵ��ڴ˶���
         boolean employeeUserExist = employeeUserService.checkUsernameExist( employeeUserVO );

         if ( employeeUserExist )
         {
            out.println( "&#8226; �û��� " + username + " �Ѿ�����" );
         }

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax���ã�����Forward
      return mapping.findForward( "" );
   }

   // ��������
   public ActionForward reset_password_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final EmployeeUserService employeeUserService = ( EmployeeUserService ) getService( "employeeUserService" );
         // ��õ�ǰ����
         final String employeeId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "employeeId" ), "UTF-8" ) );

         // ��ȡ��ʷ�û�����
         EmployeeUserVO employeeUserVO = null;
         if ( StringUtils.isNotBlank( employeeId ) )
         {
            employeeUserVO = employeeUserService.getEmployeeUserByEmployeeId( employeeId );
         }

         // ����û���ΪNULL�������û�����
         if ( employeeUserVO != null )
         {
            employeeUserVO.setPassword( Cryptogram.encodeString( PassWordUtil.randomPassWord()  ) );
            employeeUserVO.setModifyBy( getUserId( request, response ) );
            employeeUserVO.setModifyDate( new Date() );
            employeeUserService.updateEmployeeUser( employeeUserVO );

            final String email = request.getParameter( "email" );

            if ( KANUtil.filterEmpty( email ) != null )
            {
               // �������������ʼ�
               // Todo - Kevin
               final String domain = KANConstants.DOMAIN.toLowerCase().indexOf( KANConstants.HTTP ) > 0 ? ( KANConstants.DOMAIN + "/" + KANConstants.PROJECT_NAME ) : ( KANConstants.HTTP
                     + KANConstants.DOMAIN + "/" + KANConstants.PROJECT_NAME );
               final String url = domain + "/logone.do";
               String accountName = BaseAction.getPropertyFromCookie( request, response, "accountName" );
               new Mail( employeeUserVO.getAccountId(), email, BaseAction.getTitle( request, response ) + "�������óɹ� ", "�˻���" + accountName + "<br>�û�����"
                     + employeeUserVO.getUsername() + "<br>���룺" + Cryptogram.decodeString( employeeUserVO.getPassword() ) + "<br>��¼��ַ�� <a href=\"" + url + "\">"
                     + BaseAction.getTitle( request, response ) + "</a>" ).send( true );

               out.println( "<div class=\"message success fadable\">��������ɹ����ѷ��������䣺" + email
                     + "<a onclick=\"$('div.fadable').remove();\" class=\"messageCloseButton\">&nbsp;</a></div>" );
            }
            else
            {
               out.println( "<div class=\"message warning fadable\">��������ɹ����޷���������˾���䡣<a onclick=\"$('div.fadable').remove();\" class=\"messageCloseButton\">&nbsp;</a></div>" );
            }
         }
         else
         {
            out.println( "<div class=\"message error fadable\">�����û������ڡ�<a onclick=\"$('div.fadable').remove();\" class=\"messageCloseButton\">&nbsp;</a></div>" );
         }

         out.println( "<script type=\"text/javascript\">messageWrapperFada();</script>" );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax���ã�����Forward
      return mapping.findForward( "" );
   }

   private void setClientModule( final HttpServletRequest request, final ModuleService moduleService, final ClientVO clientVO, final String role ) throws KANException
   {
      List< ModuleVO > listModuleVO = moduleService.getClientModuleVOs( clientVO.getAccountId(), clientVO.getClientId(), role );
      CachedUtil.set( request, "EMPLOYEE_CLIENT_MODULE_" + clientVO.getClientId(), listModuleVO );
   }
}
package com.kan.base.web.actions;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.RandomUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.vendor.VendorContactVO;
import com.kan.hro.domain.biz.vendor.VendorUserVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.vendor.VendorContactService;
import com.kan.hro.service.inf.biz.vendor.VendorService;
import com.kan.hro.service.inf.biz.vendor.VendorUserService;
import com.kan.hro.web.actions.biz.vendor.VendorAction;

public class VendorSecurityAction extends BaseAction
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
   public ActionForward logon_inVendor( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final VendorUserService vendorUserService = ( VendorUserService ) getService( "vendorUserService" );
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
         VendorUserVO vendorUserVO = ( VendorUserVO ) form;
         final ActionErrors errors = new ActionErrors();
         // 初始化登录是否错误
         Boolean loginErrorFlag = false;

         // 判断供应商输入是否有效
         final VendorVO tempVendorVO = vendorService.getVendorVOByVendorId( vendorUserVO.getVendorId() );

         if ( tempVendorVO == null )
         {
            errors.add( "LoginVendorError", new ActionMessage( "error.security.vendor" ) );
            loginErrorFlag = true;
            addErrors( request, errors );
            ( ( VendorUserVO ) form ).reset();
            ( ( VendorUserVO ) form ).setPassword( "" );
            return mapping.findForward( "logon_inVendor" );
         }

         VendorContactVO vendorContactVO = new VendorContactVO();
         vendorContactVO.setVendorId( vendorUserVO.getVendorId() );
         vendorContactVO.setBizMobile( vendorUserVO.getUsername().trim() );
         vendorContactVO.setBizEmail( vendorUserVO.getUsername().trim() );
         vendorContactVO.setBizPhone( vendorUserVO.getUsername().trim() );
         vendorContactVO.setPersonalEmail( vendorUserVO.getUsername().trim() );
         vendorContactVO.setPersonalMobile( vendorUserVO.getUsername().trim() );
         vendorContactVO.setPersonalPhone( vendorUserVO.getUsername().trim() );
         vendorContactVO.setOtherPhone( vendorUserVO.getUsername().trim() );
         List< Object > vendorContactList = vendorContactService.vendorLogon( vendorContactVO );
         if ( vendorContactList.size() == 1 )
         {
            VendorContactVO temp = ( VendorContactVO ) vendorContactList.get( 0 );
            vendorUserVO.setUsername( temp.getNameZH() );
         }

         // 判断登录供应商用户是否有效
         final VendorUserVO loginVendorUserVO = vendorUserService.login_inVendor( vendorUserVO );
         // 判断用户名是否有效
         if ( loginVendorUserVO == null )
         {
            errors.add( "LoginUsernameError", new ActionMessage( "error.security.username" ) );
            loginErrorFlag = true;
         }
         else
         {
            // 判断用户名密码是否有效
            if ( !KANUtil.decodeString( loginVendorUserVO.getPassword() ).equals( vendorUserVO.getPassword() ) )
            {
               errors.add( "LoginPasswordError", new ActionMessage( "error.security.password" ) );
               loginErrorFlag = true;
            }

            // 判断登录IP是否有效
            if ( loginVendorUserVO.getBindIP() != null && !loginVendorUserVO.getBindIP().trim().equalsIgnoreCase( "" ) )
            {
               if ( !loginVendorUserVO.getBindIP().trim().contains( getIPAddress( request ) ) )
               {
                  errors.add( "LoginBindipError", new ActionMessage( "error.security.bindip" ) );
                  loginErrorFlag = true;
               }
            }
         }

         // 如果有用户名无效跳转到登录页面
         if ( loginErrorFlag )
         {
            addErrors( request, errors );
            ( ( VendorUserVO ) form ).setBindIP( "" );
            ( ( VendorUserVO ) form ).setStatus( "0" );
            ( ( VendorUserVO ) form ).setPassword( "" );
            return mapping.findForward( "logon_inVendor" );
         }
         else if ( loginVendorUserVO != null )
         {
            // 初始化登录对象（国际化）
            loginVendorUserVO.reset( mapping, request );
         }
         this.saveVendorUserToCookie( response, request, vendorUserVO, AUTO_VENDOR_USER );
         // 设置用户登录序列号
         loginVendorUserVO.setVendorUserToken( RandomUtil.getRandomString( 16 ) );

         // 设置Request Attribute
         request.setAttribute( "accountId", loginVendorUserVO.getAccountId() );
         request.setAttribute( "role", KANConstants.ROLE_VENDOR );
         request.setAttribute( "logonInVendor", "true" );
         request.setAttribute( "vendorId", loginVendorUserVO.getVendorId() );
         request.setAttribute( "userId", loginVendorUserVO.getVendorUserId() );
         request.setAttribute( "username", loginVendorUserVO.getUsername() );
         request.setAttribute( "vendorName", loginVendorUserVO.getVendorName() );
         request.setAttribute( "userToken", loginVendorUserVO.getVendorUserToken() );
         loginVendorUserVO.setRole( KANConstants.ROLE_VENDOR );
         this.saveUserToClient( response, request, loginVendorUserVO );

         // 更新用户登录信息
         loginVendorUserVO.setLastLogin( new Date() );
         loginVendorUserVO.setLastLoginIP( getIPAddress( request ) );
         loginVendorUserVO.setModifyDate( new Date() );
         vendorUserService.updateVendorUser( loginVendorUserVO );

         ( ( VendorUserVO ) form ).reset();

         return new VendorAction().to_objectModify_inVendor( mapping, form, request, response );
      }
      catch ( Exception e )
      {
         throw new KANException( KANUtil.getProperty( request.getLocale(), "exception.SecurityAction.logon" ), e, request, response );
      }
   }

   public ActionForward logout( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      removeObjectFromClient( request, response, COOKIE_USER );
      removeObjectFromClient( request, response, COOKIE_SECURITY );

      return mapping.findForward( "logon_inVendor" );
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
    * 自动登录保存cookie
    * 
    * @author : Ian.huang
    * @date   : 2014-6-30
    * @param  : @param response
    * @param  : @param request
    * @param  : @param userVO
    * @param  : @param type
    * @param  : @throws KANException
    * @return : void
    */
   private void saveVendorUserToCookie( final HttpServletResponse response, final HttpServletRequest request, final VendorUserVO vendorUserVO, final String type )
         throws KANException
   {
      try
      {
         if ( vendorUserVO.getCbPersistentCookie() != null && vendorUserVO.getCbPersistentCookie().equals( "on" ) )
         {
            // Json字节流存至客户端
            String cookieUser = vendorUserVO.getVendorId() + "_" + vendorUserVO.getUsername() + "_" + vendorUserVO.getPassword();

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
      VendorUserVO vendorUserVO = new VendorUserVO();
      try
      {
         final VendorUserService vendorUserService = ( VendorUserService ) getService( "vendorUserService" );
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         Cookie[] cookies = request.getCookies();
         String[] cooks = null;
         String username = null;
         String vendorId = null;
         String password = null;
         boolean flag = false;
         if ( cookies != null )
         {
            for ( Cookie cook : cookies )
            {
               String cookieName = cook.getName();
               if ( !cookieName.equalsIgnoreCase( AUTO_VENDOR_USER ) )
               {
                  continue;
               }
               String cookieValue = cook.getValue();
               cooks = cookieValue.split( "_" );
               if ( cooks.length == 3 )
               {
                  vendorId = java.net.URLDecoder.decode( cooks[ 0 ], "gbk" );
                  username = java.net.URLDecoder.decode( cooks[ 1 ], "gbk" );
                  password = java.net.URLDecoder.decode( cooks[ 2 ], "gbk" );
               }
            }
         }
         if ( username != null && password != null && vendorId != null )
         {
            vendorUserVO.setVendorId( vendorId );
            vendorUserVO.setUsername( username );
            vendorUserVO.setPassword( password );
            // 判断登录供应商用户是否有效
            final VendorUserVO loginVendorUserVO = vendorUserService.login_inVendor( vendorUserVO );

            // 判断用户名是否有效
            if ( loginVendorUserVO == null )
            {
               flag = false;
               removeAutoLogonToCookie( response, request, AUTO_VENDOR_USER );
            }
            else
            {
               jsonObject = JSONObject.fromObject( vendorUserVO );
               flag = true;
               // 判断用户名密码是否有效
               if ( !KANUtil.decodeString( loginVendorUserVO.getPassword() ).equals( vendorUserVO.getPassword() ) )
               {
                  flag = false;
                  removeAutoLogonToCookie( response, request, AUTO_VENDOR_USER );
               }

               // 判断登录IP是否有效
               if ( loginVendorUserVO.getBindIP() != null && !loginVendorUserVO.getBindIP().trim().equalsIgnoreCase( "" ) )
               {
                  if ( !loginVendorUserVO.getBindIP().trim().contains( getIPAddress( request ) ) )
                  {
                     flag = false;
                     removeAutoLogonToCookie( response, request, AUTO_VENDOR_USER );
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
}
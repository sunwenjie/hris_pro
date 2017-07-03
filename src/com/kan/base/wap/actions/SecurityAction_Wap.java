/*
 * Created on 2007-1-11
 */
package com.kan.base.wap.actions;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.kan.base.domain.security.UserVO;
import com.kan.base.domain.system.AccountVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.UserService;
import com.kan.base.service.inf.system.AccountService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.RandomUtil;
import com.kan.base.web.action.BaseAction;

/**
 * @author iori
 */
public class SecurityAction_Wap extends BaseAction
{

   /**
    * json数据返回格式
    * {success:"true",msg:"xxx",other:xxx}
    */
   public ActionForward logon( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
       StringBuffer returnJson = new StringBuffer();
        
      try
      {
         response.setHeader("Content-Type", "application/plain;charset=UTF-8");
         final PrintWriter out = response.getWriter();
         final UserService userService = ( UserService ) getService( "userService" );
         final AccountService accountService = ( AccountService ) getService( "accountService" );
         final UserVO userVO = ( UserVO ) form;
         final UserVO loginUserVO = userService.login( userVO );
         final ActionErrors errors = new ActionErrors();

         if ( loginUserVO == null )
         {
            errors.add( "LoginError", new ActionMessage( "error.security.username" ) );
            addErrors( request, errors );
            out.print( "{\"success\":\"false\",\"msg\":\"logon error\"}" );
            return null;
         }

         if ( !Cryptogram.decodeString( loginUserVO.getPassword() ).equals( userVO.getPassword() ) )
         {
            errors.add( "LoginError", new ActionMessage( "error.security.password" ) );
            addErrors( request, errors );
            out.print( "{\"success\":\"false\",\"msg\":\"logon error\"}" );
            return null;
         }

         if ( loginUserVO.getAccountId() != null && !loginUserVO.getAccountId().trim().equalsIgnoreCase( "1" )
               && !loginUserVO.getAccountId().trim().equalsIgnoreCase( userVO.getAccountId() ) )
         {
            errors.add( "LoginError", new ActionMessage( "error.security.account" ) );
            addErrors( request, errors );
            out.print( "{\"success\":\"false\",\"msg\":\"logon error\"}" );
            return null;
         }

         if ( loginUserVO.getBindIP() != null && !loginUserVO.getBindIP().trim().equalsIgnoreCase( "" ) )
         {
            if ( !loginUserVO.getBindIP().trim().contains( getIPAddress( request ) ) )
            {
               errors.add( "LoginError", new ActionMessage( "error.security.bindip" ) );
               addErrors( request, errors );
               out.print( "{\"success\":\"false\",\"msg\":\"logon error\"}" );
               return null;
            }
         }

         // 获得用户对应的Account信息
         final AccountVO accountVO = accountService.getAccountVOByAccountId( loginUserVO.getAccountId() );
         // 设置用户公司名称
         loginUserVO.setEntityName( accountVO != null ? accountVO.getEntityName() : "" );
         // 设置用户登录序列号
         loginUserVO.setUserToken( RandomUtil.getRandomString( 16 ) );
         // 设置cookie
         String cookie = this.saveUserToAndroidClient( response, loginUserVO );

         // 设置返回json
         returnJson.append( "{\"success\":\"success\",\"msg\":{" );

         returnJson.append( "\"accountId\":" ).append( "\"" ).append( loginUserVO.getAccountId() ).append( "\"," );
         returnJson.append( "\"userId\":" ).append( "\"" ).append( loginUserVO.getUserId() ).append( "\"," );
         returnJson.append( "\"staffId\":" ).append( "\"" ).append( loginUserVO.getStaffId() ).append( "\"," );
         returnJson.append( "\"username\":" ).append( "\"" ).append( loginUserVO.getUsername() ).append( "\"," );
         returnJson.append( "\"entityName\":" ).append( "\"" ).append( loginUserVO.getEntityName() ).append( "\"," );
         returnJson.append( "\"userToken\":" ).append( "\"" ).append( loginUserVO.getUserToken() ).append( "\"" );
         //this.saveUserToClient( response, loginUserVO );
         // TODO complete the function cookie on android
         returnJson.append( "}" );
         
         // 更新用户登录信息
         loginUserVO.setLastLogin( new Date() );
         loginUserVO.setLastLoginIP( getIPAddress( request ) );
         loginUserVO.setModifyDate( new Date() );
         userService.updateUser( loginUserVO );

         // 如果是平台账户登录（Super）
         if ( loginUserVO.getAccountId().trim().equalsIgnoreCase( "1" ) )
         {
            //return new AccountAction().list_object( mapping, new AccountVO(), request, response );
         }
         // 如果是一般用户登录
         else
         {
            ( ( UserVO ) form ).reset();
            //return new UserAction().list_object( mapping, form, request, response );

         }
         returnJson.append( ",\"other\":\""+cookie +"\"");
         returnJson.append( "}" );
         out.print( returnJson.toString() );
         return null;
      }
      catch ( Exception e )
      {
         throw new KANException( KANUtil.getProperty( request.getLocale(), "exception.SecurityAction.logon" ), e, request, response );
      }

   }

   public ActionForward to_index( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return mapping.findForward( "index" );
   }

   public ActionForward logout( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      removeObjectFromClient( request, response, COOKIE_USER );
      removeObjectFromClient( request, response, COOKIE_SECURITY );

      return mapping.findForward( "logon" );
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
    * this method copy from UserAction.list_object(..)
    * for the method this.logon(..) only
    */
   public PagedListHolder get_users_list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      PagedListHolder userHolder = null;
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final UserService userService = ( UserService ) getService( "userService" );
         // 获得Action Form
         final UserVO userVO = ( UserVO ) form;

         // 如果子Action是删除用户列表
         if ( userVO.getSubAction() != null && userVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( userVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         userHolder = new PagedListHolder();

         // 传入当前页
         userHolder.setPage( page );
         // 传入当前值对象
         userHolder.setObject( userVO );
         // 设置页面记录条数
         userHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         userService.getUserVOsByCondition( userHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( userHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "userHolder", userHolder );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            //return mapping.findForward( "listUserTable" );
            return null;
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      //return mapping.findForward( "listUser" );
      return userHolder;
   }
   
   public String saveUserToAndroidClient( final HttpServletResponse response, final UserVO userVO ) throws KANException
   {
      String res = "";
      try
      {
         // Json字节流存至客户端
         
         res = COOKIE_USER+"="+URLEncoder.encode( JSONObject.fromObject( userVO, UserVO.USERVO_JSONCONFIG ).toString(), "GBK" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return res;
   }
   
}
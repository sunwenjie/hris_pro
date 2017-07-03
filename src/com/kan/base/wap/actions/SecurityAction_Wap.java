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
    * json���ݷ��ظ�ʽ
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

         // ����û���Ӧ��Account��Ϣ
         final AccountVO accountVO = accountService.getAccountVOByAccountId( loginUserVO.getAccountId() );
         // �����û���˾����
         loginUserVO.setEntityName( accountVO != null ? accountVO.getEntityName() : "" );
         // �����û���¼���к�
         loginUserVO.setUserToken( RandomUtil.getRandomString( 16 ) );
         // ����cookie
         String cookie = this.saveUserToAndroidClient( response, loginUserVO );

         // ���÷���json
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
         
         // �����û���¼��Ϣ
         loginUserVO.setLastLogin( new Date() );
         loginUserVO.setLastLoginIP( getIPAddress( request ) );
         loginUserVO.setModifyDate( new Date() );
         userService.updateUser( loginUserVO );

         // �����ƽ̨�˻���¼��Super��
         if ( loginUserVO.getAccountId().trim().equalsIgnoreCase( "1" ) )
         {
            //return new AccountAction().list_object( mapping, new AccountVO(), request, response );
         }
         // �����һ���û���¼
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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final UserService userService = ( UserService ) getService( "userService" );
         // ���Action Form
         final UserVO userVO = ( UserVO ) form;

         // �����Action��ɾ���û��б�
         if ( userVO.getSubAction() != null && userVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ���û��б��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( userVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         userHolder = new PagedListHolder();

         // ���뵱ǰҳ
         userHolder.setPage( page );
         // ���뵱ǰֵ����
         userHolder.setObject( userVO );
         // ����ҳ���¼����
         userHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         userService.getUserVOsByCondition( userHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( userHolder, request );

         // Holder��д��Request����
         request.setAttribute( "userHolder", userHolder );
         // ����ǵ����򷵻�Render���ɵ��ֽ���
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���Table JSP
            //return mapping.findForward( "listUserTable" );
            return null;
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      //return mapping.findForward( "listUser" );
      return userHolder;
   }
   
   public String saveUserToAndroidClient( final HttpServletResponse response, final UserVO userVO ) throws KANException
   {
      String res = "";
      try
      {
         // Json�ֽ��������ͻ���
         
         res = COOKIE_USER+"="+URLEncoder.encode( JSONObject.fromObject( userVO, UserVO.USERVO_JSONCONFIG ).toString(), "GBK" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return res;
   }
   
}
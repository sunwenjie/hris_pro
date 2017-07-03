/*
 * Created on 2003-04-12
 */
package com.kan.base.wap.actions.security;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.service.inf.security.UserService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.Mail;
import com.kan.base.util.PassWordUtil;
import com.kan.base.util.RandomUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.security.UserRender;

/**
 * @author iori
 */
public class UserAction_Wap extends BaseAction
{

   /**
    * List Users
    * 
    */
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
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
         PagedListHolder userHolder = new PagedListHolder();

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
            return mapping.findForward( "listUserTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listUser" );
   }

   /**
    * To user modify page
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final UserService userService = ( UserService ) getService( "userService" );
         // 获得当前主键
         String userId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "userId" ), "UTF-8" ) );
         // 获得主键对应对象
         UserVO userVO = userService.getUserVOByUserId( userId );
         // 刷新对象，初始化对象列表及国际化
         userVO.reset( null, request );
         userVO.setStaffName( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffNameByStaffId( userVO.getStaffId() ) );
         userVO.setPassword( "" );
         userVO.setSubAction( VIEW_OBJECT );

         // 传回ActionForm对象到前端
         request.setAttribute( "userForm", userVO );

         // 获得修改历史
         final List< Object > historyUserVOs = userService.getHistoryUserVOsByUserId( userId );
         // 传回历史修改记录列表到前端
         request.setAttribute( "historyUserVOs", historyUserVOs );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageUser" );
   }

   /**
    * To user new page
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( UserVO ) form ).setStatus( UserVO.TRUE );
      ( ( UserVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageUser" );
   }

   /**
    * Add user
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final UserService userService = ( UserService ) getService( "userService" );
            // 获得ActionForm
            final UserVO userVO = ( UserVO ) form;
            // 将密码文件加密后存入数据库
            if ( userVO.getPassword() != null && !userVO.getPassword().trim().equals( "" ) )
            {
               userVO.setPassword( Cryptogram.encodeString( userVO.getPassword() ) );
            }
            userVO.setAccountId( getAccountId( request, response ) );
            userVO.setCreateBy( getUserId( request, response ) );
            userVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            userService.insertUser( userVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }
         // 清空Form条件
         ( ( UserVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify user
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final UserService userService = ( UserService ) getService( "userService" );
            // 获得当前主键
            final String userId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "userId" ), "UTF-8" ) );
            // 获得当前是否需要修改密码
            final String changePassword = request.getParameter( "changePassword" );
            // 获得主键对应对象
            final UserVO userVO = userService.getUserVOByUserId( userId );
            // 打上历史修改时间戳?????
            //userVO.setHisTitle( getHisTitle( userVO, request ) );
            // 值对象复制
            if ( changePassword != null && changePassword.trim().equalsIgnoreCase( "on" ) )
            {
               userVO.update( ( UserVO ) form, true );
               // 将密码文件加密后存入数据库
               userVO.setPassword( Cryptogram.encodeString( userVO.getPassword() ) );
            }
            else
            {
               userVO.update( ( UserVO ) form, false );
            }
            userVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            userService.updateUser( userVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
         // 清空Form条件
         ( ( UserVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete user
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final UserService userService = ( UserService ) getService( "userService" );
         final UserVO userVO = new UserVO();
         // 获得当前主键
         String userId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "userId" ), "UTF-8" ) );

         // 删除主键对应对象
         userVO.setUserId( userId );
         userVO.setModifyBy( getUserId( request, response ) );
         userService.deleteUser( userVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete user list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final UserService userService = ( UserService ) getService( "userService" );
         // 获得Action Form
         UserVO userVO = ( UserVO ) form;
         // 存在选中的ID
         if ( userVO.getSelectedIds() != null && !userVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : userVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               userVO.setUserId( selectedId );
               userVO.setModifyBy( getUserId( request, response ) );
               userService.deleteUser( userVO );
            }
         }

         // 清除Selected IDs和子Action
         userVO.setSelectedIds( "" );
         userVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 检查用户名是否存在
   public ActionForward check_object_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service接口
         final UserService userService = ( UserService ) getService( "userService" );
         // 获取传入的Username
         final String username = request.getParameter( "username" );

         // 初始化参数对象
         final UserVO userVO = new UserVO();
         userVO.setAccountId( getAccountId( request, response ) );
         userVO.setUsername( username );

         // 查询数据库是否已存在此对象
         final UserVO existUserVO = userService.login( userVO );

         if ( existUserVO != null )
         {
            out.println( "&#8226; 用户名 " + username + " 已经存在" );
         }

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用，无需Forward
      return mapping.findForward( "" );
   }

   // 获取历史更改记录
   public ActionForward get_history_object_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service接口
         final UserService userService = ( UserService ) getService( "userService" );
         // 获取传入的HistoryId
         final String historyId = request.getParameter( "historyId" );

         // 获取历史用户对象
         final UserVO historyUserVO = userService.getHistoryUserVOByHistoryId( historyId );

         if ( historyUserVO != null )
         {
            // 设置账户ID
            historyUserVO.setAccountId( getAccountId( request, response ) );
            // 初始化对象
            historyUserVO.reset( mapping, request );
            // 调用Render生成HTML
            out.println( UserRender.getHistoryUserVO( request.getLocale(), historyUserVO ) );
         }

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用，无需Forward
      return mapping.findForward( "" );
   }

   // 重设密码
   public ActionForward reset_password_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service接口
         final UserService userService = ( UserService ) getService( "userService" );
         final StaffService staffService = ( StaffService ) getService( "staffService" );
         // 获得当前主键
         final String staffId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "staffId" ), "UTF-8" ) );

         // 获取历史用户对象
         UserVO userVO = null;
         if ( staffId != null && !staffId.trim().equals( "" ) )
         {
            userVO = userService.getUserVOByStaffId( staffId );
         }

         // 如果用户不为NULL，更改用户密码
         if ( userVO != null )
         {
            userVO.setPassword( Cryptogram.encodeString( PassWordUtil.randomPassWord()  ) );
            userVO.setModifyBy( getUserId( request, response ) );
            userVO.setModifyDate( new Date() );
            userService.updateUser( userVO );

            final StaffVO staffVO = staffService.getStaffVOByStaffId( userVO.getStaffId() );

            if ( staffVO != null && staffVO.getBizEmail() != null && !staffVO.getBizEmail().equals( "" ) )
            {
               // 发送密码重设邮件
               // Todo - Kevin
               new Mail( staffVO.getAccountId(), staffVO.getBizEmail(), "云派密码设置成功 （ " + staffVO.getNameZH() + " - " + staffVO.getNameEN() + "）", "用户名：" + userVO.getUsername()
                     + "<br>密码：" + Cryptogram.decodeString( userVO.getPassword() ) + "<br>登录地址： <a href=\"http://www.kanpower.com.cn\">Kanpower 云派</a>" ).send( true );

               out.println( "<div class=\"message success fadable\">密码重设成功！已发送至邮箱：" + staffVO.getBizEmail()
                     + "<a onclick=\"$('div.fadable').remove();\" class=\"messageCloseButton\">&nbsp;</a></div>" );
            }
            else
            {
               out.println( "<div class=\"message warning fadable\">密码重设成功！无法发送至公司邮箱。<a onclick=\"$('div.fadable').remove();\" class=\"messageCloseButton\">&nbsp;</a></div>" );
            }
         }
         else
         {
            out.println( "<div class=\"message error fadable\">出错！用户不存在。<a onclick=\"$('div.fadable').remove();\" class=\"messageCloseButton\">&nbsp;</a></div>" );
         }

         out.println( "<script type=\"text/javascript\">messageWrapperFada();</script>" );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用，无需Forward
      return mapping.findForward( "" );
   }
}
/*
 * Created on 2003-04-12
 */
package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Mail;
import com.kan.base.util.PassWordUtil;
import com.kan.base.util.RandomUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientContactVO;
import com.kan.hro.domain.biz.client.ClientUserVO;
import com.kan.hro.service.inf.biz.client.ClientContactService;
import com.kan.hro.service.inf.biz.client.ClientUserService;

public class ClientUserAction extends BaseAction
{

   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final ClientUserService clientUserService = ( ClientUserService ) getService( "clientUserService" );
         // 获得Action Form
         final ClientUserVO clientContactVO = ( ClientUserVO ) form;

         // 如果子Action是删除用户列表
         if ( clientContactVO.getSubAction() != null && clientContactVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( clientContactVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder clientContactHolder = new PagedListHolder();

         // 传入当前页
         clientContactHolder.setPage( page );
         // 传入当前值对象
         clientContactHolder.setObject( clientContactVO );
         // 设置页面记录条数
         clientContactHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         clientUserService.getClientUserVOsByCondition( clientContactHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( clientContactHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "clientContactHolder", clientContactHolder );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listClientUserTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listClientUser" );
   }

   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final ClientUserService clientUserService = ( ClientUserService ) getService( "clientUserService" );
         // 获得当前主键
         String clientContactId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "clientContactId" ), "UTF-8" ) );
         // 获得主键对应对象
         ClientUserVO clientContactVO = clientUserService.getClientUserVOByUserId( clientContactId );
         // 刷新对象，初始化对象列表及国际化
         clientContactVO.reset( null, request );
         //clientContactVO.setStaffName( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffNameByStaffId( clientContactVO.getUserId() ) );
         clientContactVO.setPassword( "" );
         clientContactVO.setSubAction( VIEW_OBJECT );

         // 传回ActionForm对象到前端
         request.setAttribute( "clientContactForm", clientContactVO );

         //         // 获得修改历史
         //         final List< Object > historyClientUserVOs = clientUserService.getHistoryClientUserVOsByUserId( clientContactId );
         //         // 传回历史修改记录列表到前端
         //         request.setAttribute( "historyClientUserVOs", historyClientUserVOs );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageUser" );
   }

   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( ClientUserVO ) form ).setStatus( ClientUserVO.TRUE );
      ( ( ClientUserVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageUser" );
   }

   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ClientUserService clientUserService = ( ClientUserService ) getService( "clientUserService" );
            // 获得ActionForm
            final ClientUserVO clientContactVO = ( ClientUserVO ) form;
            // 将密码文件加密后存入数据库
            if ( clientContactVO.getPassword() != null && !clientContactVO.getPassword().trim().equals( "" ) )
            {
               clientContactVO.setPassword( Cryptogram.encodeString( clientContactVO.getPassword() ) );
            }
            clientContactVO.setAccountId( getAccountId( request, response ) );
            clientContactVO.setCreateBy( getUserId( request, response ) );
            clientContactVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            clientUserService.insertClientUser( clientContactVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }
         // 清空Form条件
         ( ( ClientUserVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      return null;
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
      //      try
      //      {
      //         // 初始化Service接口
      //         final ClientUserService clientUserService = ( ClientUserService ) getService( "clientUserService" );
      //         final ClientUserVO clientContactVO = new ClientUserVO();
      //         // 获得当前主键
      //         String userId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "userId" ), "UTF-8" ) );
      //
      //         // 删除主键对应对象
      //         clientContactVO.setUserId( userId );
      //         clientContactVO.setModifyBy( getUserId( request, response ) );
      //         clientUserService.deleteUser( clientContactVO );
      //      }
      //      catch ( final Exception e )
      //      {
      //         throw new KANException( e );
      //      }
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
         // 获取传入的Username
         final String username = request.getParameter( "username" );
         // 获取客户ID
         final String clientId = request.getParameter( "clientId" );
         // 初始化Service接口
         final ClientUserService clientUserService = ( ClientUserService ) getService( "clientUserService" );
         // 初始化ClientUserVO
         final ClientUserVO clientUserVO = new ClientUserVO();

         if ( KANUtil.filterEmpty( clientId ) == null )
         {
            out.println( "&#8226; 请先选择客户；" );
         }
         else
         {
            clientUserVO.setClientId( clientId );
            clientUserVO.setUsername( username );

            if ( clientUserService.isExistByCondition( clientUserVO ) )
            {
               out.println( "&#8226; 用户名 " + username + " 已经存在；" );
            }
         }
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用，无需Forward
      return null;
   }

   // 获取历史更改记录
   public ActionForward get_history_object_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
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
         final ClientUserService clientUserService = ( ClientUserService ) getService( "clientUserService" );
         // 初始化Service接口
         final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );
         // 获得当前主键
         final String staffId = request.getParameter( "staffId" );

         // 获取历史用户对象
         ClientUserVO userVO = null;
         if ( staffId != null && !staffId.trim().equals( "" ) )
         {
            userVO = clientUserService.getClientUserVOByUserId( staffId );
         }

         // 如果用户不为NULL，更改用户密码
         if ( userVO != null )
         {
            ClientContactVO clientContactVO = clientContactService.getClientContactVOByClientContactId( userVO.getClientContactId() );
            userVO.setPassword( Cryptogram.encodeString( PassWordUtil.randomPassWord()  ) );
            userVO.setModifyBy( getUserId( request, response ) );
            userVO.setModifyDate( new Date() );
            clientUserService.updateClientUser( userVO );
            userVO.setRole( KANConstants.ROLE_CLIENT );
            final String domain = KANConstants.DOMAIN.toLowerCase().indexOf( KANConstants.HTTP ) > 0 ? ( KANConstants.DOMAIN + "/" + KANConstants.PROJECT_NAME )
                  : ( KANConstants.HTTP + KANConstants.DOMAIN + "/" + KANConstants.PROJECT_NAME );
            final String url = domain + "/logonc.do";
            String accountName = BaseAction.getPropertyFromCookie( request, response, "accountName" );
            new Mail( clientContactVO.getAccountId(), clientContactVO.getBizEmail(), BaseAction.getTitle( request, response ) + "密码设置成功 ", "账户：" + accountName + "<br>用户名："
                  + clientContactVO.getUsername() + "<br>密码：" + Cryptogram.decodeString( userVO.getPassword() ) + "<br>登录地址： <a href=\"" + url + "\">"
                  + BaseAction.getTitle( request, response ) + "</a>" ).send( true );

            out.println( "<div class=\"message success fadable\">密码重设成功！已发送至邮箱：" + clientContactVO.getBizEmail()
                  + "<a onclick=\"$('div.fadable').remove();\" class=\"messageCloseButton\">&nbsp;</a></div>" );

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
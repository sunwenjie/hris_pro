package com.kan.hro.web.actions.biz.vendor;

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
import com.kan.hro.domain.biz.vendor.VendorContactVO;
import com.kan.hro.domain.biz.vendor.VendorUserVO;
import com.kan.hro.service.inf.biz.vendor.VendorContactService;
import com.kan.hro.service.inf.biz.vendor.VendorUserService;

public class VendorUserAction extends BaseAction
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
         final VendorUserService vendorUserService = ( VendorUserService ) getService( "vendorUserService" );
         // 获得Action Form
         final VendorUserVO vendorUserVO = ( VendorUserVO ) form;

         // 如果子Action是删除用户列表
         if ( vendorUserVO.getSubAction() != null && vendorUserVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( vendorUserVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder vendorUserHolder = new PagedListHolder();

         // 传入当前页
         vendorUserHolder.setPage( page );
         // 传入当前值对象
         vendorUserHolder.setObject( vendorUserVO );
         // 设置页面记录条数
         vendorUserHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         vendorUserService.getVendorUserVOsByCondition( vendorUserHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( vendorUserHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "vendorUserHolder", vendorUserHolder );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listVendorUserTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listVendorUser" );
   }

   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final VendorUserService vendorUserService = ( VendorUserService ) getService( "vendorUserService" );
         // 获得当前主键
         String vendorUserId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "vendorUserId" ), "UTF-8" ) );
         // 获得主键对应对象
         VendorUserVO vendorUserVO = vendorUserService.getVendorUserVOByVendorUserId( vendorUserId );
         // 刷新对象，初始化对象列表及国际化
         vendorUserVO.reset( null, request );
         //vendorUserVO.setStaffName( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getStaffNameByStaffId( vendorUserVO.getUserId() ) );
         vendorUserVO.setPassword( "" );
         vendorUserVO.setSubAction( VIEW_OBJECT );

         // 传回ActionForm对象到前端
         request.setAttribute( "vendorUserForm", vendorUserVO );

         //         // 获得修改历史
         //         final List< Object > historyVendorUserVOs = vendorUserService.getHistoryVendorUserVOsByUserId( vendorUserId );
         //         // 传回历史修改记录列表到前端
         //         request.setAttribute( "historyVendorUserVOs", historyVendorUserVOs );
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
      ( ( VendorUserVO ) form ).setStatus( VendorUserVO.TRUE );
      ( ( VendorUserVO ) form ).setSubAction( CREATE_OBJECT );

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
            final VendorUserService vendorUserService = ( VendorUserService ) getService( "vendorUserService" );
            // 获得ActionForm
            final VendorUserVO vendorUserVO = ( VendorUserVO ) form;
            // 将密码文件加密后存入数据库
            if ( vendorUserVO.getPassword() != null && !vendorUserVO.getPassword().trim().equals( "" ) )
            {
               vendorUserVO.setPassword( Cryptogram.encodeString( vendorUserVO.getPassword() ) );
            }
            vendorUserVO.setAccountId( getAccountId( request, response ) );
            vendorUserVO.setCreateBy( getUserId( request, response ) );
            vendorUserVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            vendorUserService.insertVendorUser( vendorUserVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }
         // 清空Form条件
         ( ( VendorUserVO ) form ).reset();
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
      // No Use
      return null;
   }

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
         final VendorUserService vendorUserService = ( VendorUserService ) getService( "vendorUserService" );
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
         // 获得当前主键
         final String vendorContactId = KANUtil.decodeStringFromAjax( request.getParameter( "vendorContactId" ) );
         final VendorContactVO vendorContactVO = vendorContactService.getVendorContactVOByVendorContactId( vendorContactId );

         // 获取历史用户对象
         final VendorUserVO tempVendorUserVO = new VendorUserVO();
         VendorUserVO vendorUserVO = new VendorUserVO();
         tempVendorUserVO.setUsername( vendorContactVO.getUsername() );
         tempVendorUserVO.setAccountId( vendorContactVO.getAccountId() );

         if ( vendorContactId != null && !vendorContactId.trim().equals( "" ) && vendorContactVO.getUsername() != null )
         {
            vendorUserVO = vendorUserService.getVendorUserVOByUsername( tempVendorUserVO );
         }

         // 如果用户不为NULL，更改用户密码
         if ( vendorUserVO != null )
         {
            vendorUserVO.setPassword( Cryptogram.encodeString( PassWordUtil.randomPassWord()  ) );
            vendorUserVO.setModifyBy( getUserId( request, response ) );
            vendorUserVO.setModifyDate( new Date() );
            vendorUserService.updateVendorUser( vendorUserVO );

            final String email = request.getParameter( "email" );

            if ( KANUtil.filterEmpty( email ) != null )
            {
               // 发送密码重设邮件
               final String domain = KANConstants.DOMAIN.toLowerCase().indexOf( KANConstants.HTTP ) > 0 ? KANConstants.DOMAIN : ( KANConstants.HTTP + KANConstants.DOMAIN );
               new Mail( vendorContactVO.getAccountId(), email, BaseAction.getTitle( request, response ) + "密码设置成功 （ " + vendorContactVO.getNameZH() + " - " + vendorContactVO.getNameEN() + "）", "供应商ID：" + vendorUserVO.getVendorId() 
                     + "<br>用户名："
                     + vendorUserVO.getUsername()
                     + "<br>密码："
                     + Cryptogram.decodeString( vendorUserVO.getPassword() )
                     + "<br>登录地址： <a href=\""
                     + domain + "/" + KANConstants.PROJECT_NAME + "/" + "logonv.do"
                     + "\">"
                     + BaseAction.getTitle( request, response ) + "</a>" ).send( true );

               out.println( "<div class=\"message success fadable\">密码重设成功！已发送至邮箱：" + email
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
      // No Use
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
      // No Use
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
         final VendorUserService vendorUserService = ( VendorUserService ) getService( "vendorUserService" );
         // 获取传入的Username
         final String username = request.getParameter( "username" );

         // 初始化参数对象
         final VendorUserVO vendorUserVO = new VendorUserVO();
         vendorUserVO.setAccountId( getAccountId( request, response ) );
         vendorUserVO.setUsername( username );

         // 查询数据库是否已存在此对象
         final VendorUserVO existVendorUserVO = vendorUserService.getVendorUserVOByUsername( vendorUserVO );

         if ( existVendorUserVO != null )
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

}
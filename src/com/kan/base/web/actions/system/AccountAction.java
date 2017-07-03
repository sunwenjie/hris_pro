/*
 * Created on 2013-05-13
 */
package com.kan.base.web.actions.system;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.OptionsVO;
import com.kan.base.domain.system.AccountVO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.AccountService;
import com.kan.base.service.inf.system.HRMAccountService;
import com.kan.base.service.inf.system.HROAccountService;
import com.kan.base.service.inf.system.ModuleService;
import com.kan.base.service.inf.system.PlatFromAccountService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

/**
 * @author Kevin Jin
 */
public class AccountAction extends BaseAction
{

   public static String accessAction = "HRO_SYS_ACCOUNT";

   // 1:HRO##2:HRM##3:PLATFORM
   private final String TYPE_HRO = "1";

   private final String TYPE_HRM = "2";

   private final String TYPE_PLATFORM = "3";

   /**
    * List AccountBaseViews by Jason format
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Account Service
         final AccountService accountService = ( AccountService ) getService( "accountService" );

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( accountService.getAccountBaseViews() );

         // Send to client
         out.println( array.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "" );
   }

   /**
    * List Accounts
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
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
         final AccountService accountService = ( AccountService ) getService( "accountService" );
         // 获得Action Form
         final AccountVO accountVO = ( AccountVO ) form;

         // 如果子Action是删除用户列表
         if ( accountVO != null && accountVO.getSubAction() != null && accountVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果子Action是激活用户列表
         else if ( accountVO != null && accountVO.getSubAction() != null && accountVO.getSubAction().equalsIgnoreCase( ACTIVE_OBJECTS ) )
         {
            // 调用激活用户列表的Action
            active_objectList( mapping, form, request, response );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder accountHolder = new PagedListHolder();

         // 传入当前页
         accountHolder.setPage( page );
         // 传入当前值对象
         accountHolder.setObject( accountVO );
         // 设置页面记录条数
         accountHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         accountService.getAccountVOsByCondition( accountHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( accountHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "accountHolder", accountHolder );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listAccountTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listAccount" );
   }

   /**
    * To account modify page
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
         final AccountService accountService = ( AccountService ) getService( "accountService" );
         // 获得当前主键
         String accountId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "accountId" ), "UTF-8" ) );
         // 获得主键对应对象
         AccountVO accountVO = accountService.getAccountVOByAccountId( accountId );
         // Checkbox处理
         accountVO.setCanAdvBizEmail( accountVO.getCanAdvBizEmail() != null && accountVO.getCanAdvBizEmail().equals( AccountVO.TRUE ) ? "on" : "" );
         accountVO.setCanAdvPersonalEmail( accountVO.getCanAdvPersonalEmail() != null && accountVO.getCanAdvPersonalEmail().equals( AccountVO.TRUE ) ? "on" : "" );
         accountVO.setCanAdvBizSMS( accountVO.getCanAdvBizSMS() != null && accountVO.getCanAdvBizSMS().equals( AccountVO.TRUE ) ? "on" : "" );
         accountVO.setCanAdvPersonalSMS( accountVO.getCanAdvPersonalSMS() != null && accountVO.getCanAdvPersonalSMS().equals( AccountVO.TRUE ) ? "on" : "" );
         // 刷新对象，初始化对象列表及国际化
         accountVO.reset( null, request );

         // 如果City Id，则填充Province Id
         if ( accountVO.getCityId() != null && !accountVO.getCityId().trim().equals( "" ) && !accountVO.getCityId().trim().equals( "0" ) )
         {
            accountVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( accountVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            accountVO.setCityIdTemp( accountVO.getCityId() );
         }

         accountVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "accountForm", accountVO );
         // 系统模块树
         request.setAttribute( "selectedModules", getselectedModules( accountId ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageAccount" );
   }

   
   // 初始化Module常量
   private List< ModuleVO > getselectedModules( final String accountId ) throws KANException
   {
      try
      {
         // Clear First
         final List< ModuleVO > tempModuleVOs = new ArrayList< ModuleVO >();
         final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
         // 初始化Module VO
         final List< Object > moduleVOs = moduleService.getAccountModuleVOsByAccountId( accountId );

         if ( moduleVOs != null )
         {
            // 遍历
            for ( Object moduleVOObject : moduleVOs )
            {
               tempModuleVOs.add( ( ModuleVO ) moduleVOObject );
            }
         }

         return tempModuleVOs;
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * To account modify page part
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify_internal( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final AccountService accountService = ( AccountService ) getService( "accountService" );
         // 获得当前主键
         String accountId = BaseAction.getAccountId( request, response );
         // 获得主键对应对象
         AccountVO accountVO = accountService.getAccountVOByAccountId( accountId );
         // 刷新对象，初始化对象列表及国际化
         accountVO.reset( null, request );

         // 如果City Id，则填充Province Id
         if ( accountVO.getCityId() != null && !accountVO.getCityId().trim().equals( "" ) && !accountVO.getCityId().trim().equals( "0" ) )
         {
            accountVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( accountVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            accountVO.setCityIdTemp( accountVO.getCityId() );
         }

         accountVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "accountForm", accountVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageAccountInternal" );
   }

   /**
    * To account new page
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
      ( ( AccountVO ) form ).setStatus( AccountVO.TRUE );
      ( ( AccountVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageAccount" );
   }

   /**
    * Add account
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
            final AccountService accountService = ( AccountService ) getService( "accountService" );
            // 获得ActionForm
            final AccountVO accountVO = ( AccountVO ) form;
            // Checkbox处理
            if ( accountVO.getCanAdvBizEmail() != null && accountVO.getCanAdvBizEmail().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvBizEmail( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvBizEmail( OptionsVO.FALSE );
            }

            if ( accountVO.getCanAdvBizSMS() != null && accountVO.getCanAdvBizSMS().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvBizSMS( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvBizSMS( OptionsVO.FALSE );
            }

            if ( accountVO.getCanAdvPersonalEmail() != null && accountVO.getCanAdvPersonalEmail().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvPersonalEmail( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvPersonalEmail( OptionsVO.FALSE );
            }

            if ( accountVO.getCanAdvPersonalSMS() != null && accountVO.getCanAdvPersonalSMS().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvPersonalSMS( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvPersonalSMS( OptionsVO.FALSE );
            }

            // 设置初始化信息
            accountVO.setInitialized( AccountVO.FALSE );
            // 获取登录用户
            accountVO.setCreateBy( getUserId( request, response ) );
            accountVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            accountService.insertAccount( accountVO );
            // 初始化常量持久对象
            constantsInit( "initModule", accountVO.getAccountId() );
            constantsInit( "initStaff", accountVO.getAccountId() );

            // 返回添加成功标记
            success( request );
         }

         // 清空Form条件
         ( ( AccountVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify account part
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_internal( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final AccountService accountService = ( AccountService ) getService( "accountService" );
            // 获得当前主键
            final String accountId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "accountId" ), "UTF-8" ) );
            // 获得主键对应对象
            final AccountVO accountVO = accountService.getAccountVOByAccountId( accountId );

            // 装载界面传值
            accountVO.update( form, true );
            // 照片
            final String[] imageFileArray = request.getParameterValues( "imageFileArray" );
            if ( imageFileArray == null )
            {
               accountVO.setImageFile( null );
            }
            else
            {
               String imageFileString = "";
               for ( String s : imageFileArray )
               {
                  imageFileString += s;
                  imageFileString += "##";
               }
               accountVO.setImageFile( imageFileString.length() > 0 ? imageFileString.substring( 0, imageFileString.length() - 2 ) : null );
            }
            // 获取登录用户
            accountVO.setModifyBy( getUserId( request, response ) );
            // 修改对象
            accountService.updateAccount( accountVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // 清空Form条件
         ( ( AccountVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return to_objectModify_internal( mapping, form, request, response );
   }

   /**
    * Modify account
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
            final AccountService accountService = ( AccountService ) getService( "accountService" );
            // 获得当前主键
            final String accountId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "accountId" ), "UTF-8" ) );
            // 获得主键对应对象
            final AccountVO accountVO = accountService.getAccountVOByAccountId( accountId );

            // 装载界面传值
            accountVO.update( form );
            // Checkbox处理
            if ( accountVO.getCanAdvBizEmail() != null && accountVO.getCanAdvBizEmail().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvBizEmail( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvBizEmail( OptionsVO.FALSE );
            }

            if ( accountVO.getCanAdvBizSMS() != null && accountVO.getCanAdvBizSMS().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvBizSMS( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvBizSMS( OptionsVO.FALSE );
            }

            if ( accountVO.getCanAdvPersonalEmail() != null && accountVO.getCanAdvPersonalEmail().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvPersonalEmail( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvPersonalEmail( OptionsVO.FALSE );
            }

            if ( accountVO.getCanAdvPersonalSMS() != null && accountVO.getCanAdvPersonalSMS().equalsIgnoreCase( "on" ) )
            {
               accountVO.setCanAdvPersonalSMS( OptionsVO.TRUE );
            }
            else
            {
               accountVO.setCanAdvPersonalSMS( OptionsVO.FALSE );
            }
            // 获取登录用户
            accountVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            accountService.updateAccount( accountVO );
            // 初始化常量持久对象
            constantsInit( "initModule", accountId );
            constantsInit( "initStaff", accountId );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // 清空Form条件
         ( ( AccountVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete account
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
         final AccountService accountService = ( AccountService ) getService( "accountService" );
         final AccountVO accountVO = new AccountVO();
         // 获得当前主键
         String accountId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "accountId" ), "UTF-8" ) );

         // 删除主键对应对象
         accountVO.setAccountId( accountId );
         accountVO.setModifyBy( getUserId( request, response ) );
         accountService.deleteAccount( accountVO );

         // 初始化常量持久对象
         constantsInit( "initModule", accountVO.getAccountId() );
         constantsInit( "initStaff", accountVO.getAccountId() );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete account list
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
         final AccountService accountService = ( AccountService ) getService( "accountService" );
         // 获得Action Form
         final AccountVO accountVO = ( AccountVO ) form;
         // 存在选中的ID
         if ( accountVO.getSelectedIds() != null && !accountVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : accountVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               accountVO.setAccountId( selectedId );
               accountVO.setModifyBy( getUserId( request, response ) );
               accountService.deleteAccount( accountVO );
            }
         }

         // 初始化常量持久对象
         constantsInit( "initModule", accountVO.getAccountId() );
         constantsInit( "initStaff", accountVO.getAccountId() );

         // 清除Selected IDs和子Action
         accountVO.setSelectedIds( "" );
         accountVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Active Account List
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void active_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final AccountService accountService = ( AccountService ) getService( "accountService" );

         final HROAccountService hroAccountService = ( HROAccountService ) getService( "hroAccountService" );
         final HRMAccountService hrmAccountService = ( HRMAccountService ) getService( "hrmAccountService" );
         final PlatFromAccountService platFromAccountService = ( PlatFromAccountService ) getService( "platFormAccountService" );
         // 获得Action Form
         AccountVO accountVO = ( AccountVO ) form;
         // 存在选中的ID
         if ( accountVO.getSelectedIds() != null && !accountVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : accountVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               accountVO.setAccountId( selectedId );
               accountVO.setCreateBy( getUserId( request, response ) );
               accountVO.setModifyBy( getUserId( request, response ) );

               final AccountVO targetAccountVO = accountService.getAccountVOByAccountId( accountVO.getAccountId() );
               if ( targetAccountVO.getAccountType().equals( TYPE_HRO ) )
               {
                  hroAccountService.initAccount( targetAccountVO );
               }
               else if ( targetAccountVO.getAccountType().equals( TYPE_HRM ) )
               {
                  hrmAccountService.initAccount( targetAccountVO );
               }
               else if ( targetAccountVO.getAccountType().equals( TYPE_PLATFORM ) )
               {
                  platFromAccountService.initAccount( targetAccountVO );
               }
            }
         }
         // 清除Selected IDs和子Action
         accountVO.setSelectedIds( "" );
         accountVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
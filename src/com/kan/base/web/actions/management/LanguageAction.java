package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.LanguageVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.LanguageService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class LanguageAction extends BaseAction
{
   public final static String accessAction = "HRO_EMPLOYEE_LANGUAGES";

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final LanguageService languageService = ( LanguageService ) getService( "languageService" );
         // 获得Action Form
         final LanguageVO languageVO = ( LanguageVO ) form;
         // 需要设置当前用户AccountId
         languageVO.setAccountId( getAccountId( request, response ) );
         // 调用删除方法
         if ( languageVO.getSubAction() != null && languageVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder languageHolder = new PagedListHolder();
         // 传入当前页
         languageHolder.setPage( page );
         // 传入当前值对象
         languageHolder.setObject( languageVO );
         // 设置页面记录条数
         languageHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         languageService.getLanguageVOsByCondition( languageHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( languageHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "languageHolder", languageHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listLanguageTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listLanguage" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );
      // 设置Sub Action
      ( ( LanguageVO ) form ).setStatus( LanguageVO.TRUE );
      ( ( LanguageVO ) form ).setSubAction( CREATE_OBJECT );
      // 跳转到新建界面  
      return mapping.findForward( "manageLanguage" );
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final LanguageService languageService = ( LanguageService ) getService( "languageService" );

            // 获得当前FORM
            final LanguageVO languageVO = ( LanguageVO ) form;
            languageVO.setCreateBy( getUserId( request, response ) );
            languageVO.setModifyBy( getUserId( request, response ) );
            languageVO.setAccountId( getAccountId( request, response ) );
            languageService.insertLanguage( languageVO );

            // 初始化常量持久对象
            constantsInit( "initLanguage", getAccountId( request, response ) );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, languageVO, Operate.ADD, languageVO.getLanguageId(), null );
         }
         else
         {
            // 清空FORM
            ( ( LanguageVO ) form ).reset();
            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         // 清空Action Form
         ( ( LanguageVO ) form ).reset();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final LanguageService languageService = ( LanguageService ) getService( "languageService" );
         // 主键获取需解码
         String languageId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( languageId ) == null )
         {
            languageId = ( ( LanguageVO ) form ).getLanguageId();
         }
         // 获得LanguageVO对象
         LanguageVO languageVO = languageService.getLanguageVOByLanguageId( languageId );
         languageVO.reset( null, request );
         // 区分Add和Update
         languageVO.setSubAction( VIEW_OBJECT );
         // 将LanguageVO传入request对象
         request.setAttribute( "languageForm", languageVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageLanguage" );
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final LanguageService languageService = ( LanguageService ) getService( "languageService" );

            // 主键获取需解码
            final String entityId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
            // 获取LanguageVO对象
            final LanguageVO languageVO = languageService.getLanguageVOByLanguageId( entityId );
            // 装载界面传值
            languageVO.update( ( LanguageVO ) form );
            // 获取登录用户
            languageVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            languageService.updateLanguage( languageVO );

            // 初始化常量持久对象
            constantsInit( "initLanguage", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, languageVO, Operate.MODIFY, languageVO.getLanguageId(), null );
         }

         // 清空Action Form
         ( ( LanguageVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );

      }
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final LanguageService languageService = ( LanguageService ) getService( "languageService" );

         // 获得Action Form
         final LanguageVO languageVO = ( LanguageVO ) form;
         // 存在选中的ID
         if ( languageVO.getSelectedIds() != null && !languageVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : languageVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               languageVO.setLanguageId( selectedId );
               languageVO.setModifyBy( getUserId( request, response ) );
               languageVO.setAccountId( getAccountId( request, response ) );
               languageService.deleteLanguage( languageVO );
            }

            // 初始化常量持久对象
            constantsInit( "initLanguage", getAccountId( request, response ) );
            insertlog( request, languageVO, Operate.DELETE, null, languageVO.getSelectedIds() );
         }

         // 清除Selected IDs和子Action
         languageVO.setSelectedIds( "" );
         languageVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

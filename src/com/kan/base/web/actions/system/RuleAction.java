/*
 * Created on 2013-05-28
 */
package com.kan.base.web.actions.system;

import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.RuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.RuleService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.system.RuleRender;

/**
 * @author Kevin Jin
 */
public class RuleAction extends BaseAction
{

   /**
    * List Rules
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
         final RuleService ruleService = ( RuleService ) getService( "ruleService" );
         // 获得Action Form
         final RuleVO ruleVO = ( RuleVO ) form;

         // 如果子Action是删除用户列表
         if ( ruleVO != null && ruleVO.getSubAction() != null && ruleVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( ruleVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder ruleHolder = new PagedListHolder();

         // 传入当前页
         ruleHolder.setPage( page );
         // 传入当前值对象
         ruleHolder.setObject( ruleVO );
         // 设置页面记录条数
         ruleHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         ruleService.getRuleVOsByCondition( ruleHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( ruleHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "ruleHolder", ruleHolder );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listRuleTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listRule" );
   }

   /**
    * To rule modify page
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
         final RuleService ruleService = ( RuleService ) getService( "ruleService" );
         // 获得当前主键
         final String ruleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "ruleId" ), "GBK" ) );
         // 获得主键对应对象
         RuleVO ruleVO = ruleService.getRuleVOByRuleId( ruleId );
         // 刷新对象，初始化对象列表及国际化
         ruleVO.reset( null, request );

         ruleVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "ruleForm", ruleVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageRule" );
   }

   /**
    * To rule new page
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
      ( ( RuleVO ) form ).setStatus( RuleVO.TRUE );
      ( ( RuleVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageRule" );
   }

   /**
    * Add rule
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
            final RuleService ruleService = ( RuleService ) getService( "ruleService" );
            // 获得ActionForm
            final RuleVO ruleVO = ( RuleVO ) form;

            // 获取登录用户
            ruleVO.setCreateBy( getUserId( request, response ) );
            ruleVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            ruleService.insertRule( ruleVO );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }
         // 清空form
         ( ( RuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify rule
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
            final RuleService ruleService = ( RuleService ) getService( "ruleService" );
            // 获得当前主键
            final String ruleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "ruleId" ), "GBK" ) );
            // 获得主键对应对象
            final RuleVO ruleVO = ruleService.getRuleVOByRuleId( ruleId );

            // 装载界面传值
            ruleVO.update( ( RuleVO ) form );

            // 获取登录用户
            ruleVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            ruleService.updateRule( ruleVO );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
         //清空form
         ( ( RuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete rule
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
         final RuleService ruleService = ( RuleService ) getService( "ruleService" );
         final RuleVO ruleVO = new RuleVO();
         // 获得当前主键
         String ruleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "ruleId" ), "GBK" ) );

         // 删除主键对应对象
         ruleVO.setRuleId( ruleId );
         ruleVO.setModifyBy( getUserId( request, response ) );
         ruleService.deleteRule( ruleVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete rule list
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
         final RuleService ruleService = ( RuleService ) getService( "ruleService" );
         // 获得Action Form
         RuleVO ruleVO = ( RuleVO ) form;
         // 存在选中的ID
         if ( ruleVO.getSelectedIds() != null && !ruleVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : ruleVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               ruleVO.setRuleId( selectedId );
               ruleVO.setModifyBy( getUserId( request, response ) );
               ruleService.deleteRule( ruleVO );
            }
         }
         // 清除Selected IDs和子Action
         ruleVO.setSelectedIds( "" );
         ruleVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * list_object_html_thinking_management
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_html_select( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获得ModuleId
         final String moduleId = request.getParameter( "moduleId" );

         // 获得RuleType
         final String ruleType = request.getParameter( "ruleType" );

         // Render调用
         out.println( RuleRender.getRuleSelectByModuleId( request.getLocale(), ruleType, moduleId ) );
         out.flush();
         out.close();

         // Ajax调用无跳转
         return mapping.findForward( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
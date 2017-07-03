/*
 * Created on 2013-05-28
 */
package com.kan.base.web.actions.system;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.ModuleDTO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.domain.system.RightVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.ModuleService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.system.ModuleRender;
import com.kan.base.web.renders.system.RightRender;

/**
 * @author Kevin Jin
 */
public class ModuleAction extends BaseAction
{
   /**
    * To Account Module Modify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_accountModuleModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         return mapping.findForward( "accountSetting" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   /**
    * Modify Account Module
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_accountModule_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );

         // 获取前端传过来的ModuleVO
         final ModuleVO moduleVO = ( ModuleVO ) form;

         // 获取当前选中的moduleId
         final String[] moduleIdArray = moduleVO.getModuleIdArray();

         // 获取要修改的moduleVO对应的moduleId
         final String moduleId = moduleIdArray[ 0 ];

         if ( moduleId != null && !"".equals( moduleId ) )
         {
            // moduleVO设置moduleId
            moduleVO.setModuleId( moduleId );

            // 获取登录用户
            moduleVO.setModifyBy( getUserId( request, response ) );

            // 转成Array对象存储数据库
            moduleVO.setRightIds( KANUtil.toJasonArray( moduleVO.getRightIdArray() ) );
            moduleVO.setRuleIds( KANUtil.toJasonArray( moduleVO.getRuleIdArray() ) );

            // 修改对象
            moduleService.updateAccountModuleRelation( moduleVO );
         }

         // 返回编辑成功标记
         success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

         constants.initModule();

         // 清除Form中缓存的数据，返会List重新搜索
         ( ( ModuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用,无需跳转页面
      return mapping.findForward( "" );
   }

   /**
    * List Modules
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
         final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
         // 获得Action Form
         final ModuleVO moduleVO = ( ModuleVO ) form;

         // 如果子Action是删除用户列表
         if ( moduleVO != null && moduleVO.getSubAction() != null && moduleVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序、翻页或导出操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( moduleVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder moduleHolder = new PagedListHolder();

         // 传入当前页
         moduleHolder.setPage( page );
         // 传入当前值对象
         moduleHolder.setObject( moduleVO );
         // 设置页面记录条数
         moduleHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         moduleService.getModuleVOsByCondition( moduleHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( moduleHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "moduleHolder", moduleHolder );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listModuleTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listModule" );
   }

   /**
    * To Module Modify
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
         final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
         // 获得当前主键
         String moduleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "moduleId" ), "GBK" ) );
         // 获得主键对应对象
         final ModuleVO moduleVO = moduleService.getModuleVOByModuleId( moduleId );
         // 刷新对象，初始化对象列表及国际化
         moduleVO.reset( null, request );
         moduleVO.setSubAction( VIEW_OBJECT );
         // 缓存至Request中
         request.setAttribute( "moduleForm", moduleVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageModule" );
   }

   /**
    * To Module Create
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
      ( ( ModuleVO ) form ).setStatus( ModuleVO.TRUE );
      ( ( ModuleVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageModule" );
   }

   /**
    * Add Module
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
            final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // 获得ActionForm
            final ModuleVO moduleVO = ( ModuleVO ) form;

            // 获取登录用户
            moduleVO.setCreateBy( getUserId( request, response ) );
            moduleVO.setModifyBy( getUserId( request, response ) );
            // 转成Array对象存储数据库
            moduleVO.setRightIds( KANUtil.toJasonArray( moduleVO.getRightIdArray() ) );
            moduleVO.setRuleIds( KANUtil.toJasonArray( moduleVO.getRuleIdArray() ) );
            // 新建对象
            moduleService.insertModule( moduleVO );

            // 重新加载Module
            constants.initModule();

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }

         // 清除Form中缓存的数据，返会List重新搜索
         ( ( ModuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify Module
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
            final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // 获得当前主键
            final String moduleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "moduleId" ), "GBK" ) );
            // 获得主键对应对象
            final ModuleVO moduleVO = moduleService.getModuleVOByModuleId( moduleId );

            // 装载界面传值
            moduleVO.update( ( ModuleVO ) form );

            // 获取登录用户
            moduleVO.setModifyBy( getUserId( request, response ) );
            // 转成Array对象存储数据库
            moduleVO.setRightIds( KANUtil.toJasonArray( moduleVO.getRightIdArray() ) );
            moduleVO.setRuleIds( KANUtil.toJasonArray( moduleVO.getRuleIdArray() ) );

            // 修改对象
            moduleService.updateModule( moduleVO );

            // 重新加载Module
            constants.initModule();

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // 清除Form中缓存的数据，返会List重新搜索
         ( ( ModuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete Module
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
         final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );

         final ModuleVO moduleVO = new ModuleVO();
         // 获得当前主键
         String moduleId = request.getParameter( "moduleId" );

         // 删除主键对应对象
         moduleVO.setModuleId( moduleId );
         moduleVO.setModifyBy( getUserId( request, response ) );
         moduleService.deleteModule( moduleVO );

         // 重新加载Module
         constants.initModule();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Module List
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
         final ModuleService moduleService = ( ModuleService ) getService( "moduleService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );

         // 获得Action Form
         ModuleVO moduleVO = ( ModuleVO ) form;
         // 存在选中的ID
         if ( moduleVO.getSelectedIds() != null && !moduleVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : moduleVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               moduleVO.setModuleId( selectedId );
               moduleVO.setModifyBy( getUserId( request, response ) );
               moduleService.deleteModule( moduleVO );
            }
         }
         // 清除Selected IDs和子Action
         moduleVO.setSelectedIds( "" );
         moduleVO.setSubAction( "" );

         // 重新加载Module
         constants.initModule();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Authority Combo HTML for Position
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_authority_combo_position_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获得PositionId和ModuleId
         String positionId = request.getParameter( "positionId" );
         final String moduleId = request.getParameter( "moduleId" );

         // PostionId解码
         if ( positionId != null && !positionId.trim().equals( "" ) )
         {
            positionId = Cryptogram.decodeString( URLDecoder.decode( positionId, "UTF-8" ) );
         }

         if ( moduleId != null && !moduleId.trim().equals( "" ) )
         {
            out.println( ModuleRender.getAuthorityComboByPositionId( request, positionId, moduleId ) );
         }

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

   /**
    * List Authority Combo HTML for Group
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_authority_combo_group_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获得GroupId和ModuleId
         String groupId = request.getParameter( "groupId" );
         final String moduleId = request.getParameter( "moduleId" );

         // GroupId解码
         if ( groupId != null && !groupId.trim().equals( "" ) )
         {
            groupId = Cryptogram.decodeString( URLDecoder.decode( groupId, "UTF-8" ) );
         }

         if ( moduleId != null && !moduleId.trim().equals( "" ) )
         {
            out.println( ModuleRender.getAuthorityComboByGroupId( request, groupId, moduleId ) );
         }

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

   /**
    * List Authority Combo for Account
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_authority_combo_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获得ModuleId，用以判断是否按照员工显示树
         final String moduleId = request.getParameter( "moduleId" );

         if ( moduleId != null && !moduleId.trim().equals( "" ) )
         {
            out.println( ModuleRender.getAuthorityComboByAccountId( request, moduleId ) );
         }

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

   /**
    * List ModuleBaseViews by Jason format
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

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( KANConstants.MODULE_BASEVIEW );

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

   public ActionForward list_Module_rightIds_html_checkBox( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         String moduleId = request.getParameter( "moduleId" );

         ModuleDTO moduleDTO = KANConstants.getModuleDTOByModuleId( moduleId );

         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         final PrintWriter out = response.getWriter();
         if ( moduleDTO != null )
         {
            List< RightVO > rightVOs = moduleDTO.getModuleVO().getRightVOs();
            if ( rightVOs != null )
            {
               final String checkBoxName = request.getParameter( "checkBoxName" );
               final String rightIds = request.getParameter( "rightIds" );
               final String selectRightIds[] = KANUtil.jasonArrayToStringArray( rightIds );

               // Render调用
               // 屏蔽掉查看，列表等权限
               //final String exceptRightIds[] = { "1", "2", "3", "4", "5" };
               out.println( RightRender.getRightHorizontalMultipleChoice( request, rightVOs, checkBoxName, selectRightIds ) );
            }
            else
            {
               out.println( "<font color=red >没有可选的权限！</font>" );
            }
         }
         else
         {
            out.println( "<font color=red >未找到对应模块！</font>" );
         }
         out.flush();
         out.close();
         // Ajax调用无跳转
         return null;

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
/*
 * Created on 2013-05-28
 */
package com.kan.base.web.actions.system;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.RightVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.RightService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

/**
 * @author Kevin Jin
 */
public class RightAction extends BaseAction
{

   /**
    * List Rights
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
         final RightService rightService = ( RightService ) getService( "rightService" );
         // 获得Action Form
         final RightVO rightVO = ( RightVO ) form;

         // 如果子Action是删除用户列表
         if ( rightVO != null && rightVO.getSubAction() != null && rightVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( rightVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder rightHolder = new PagedListHolder();

         // 传入当前页
         rightHolder.setPage( page );
         // 传入当前值对象
         rightHolder.setObject( rightVO );
         // 设置页面记录条数
         rightHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         rightService.getRightVOsByCondition( rightHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( rightHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "rightHolder", rightHolder );
         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listRightTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listRight" );
   }

   /**
    * To right modify page
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
         final RightService rightService = ( RightService ) getService( "rightService" );
         // 获得当前主键
         final String rightId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "rightId" ), "GBK" ) );
         // 获得主键对应对象
         RightVO rightVO = rightService.getRightVOByRightId( rightId );
         // 刷新对象，初始化对象列表及国际化
         rightVO.reset( null, request );

         rightVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "rightForm", rightVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageRight" );
   }

   /**
    * To right new page
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
      ( ( RightVO ) form ).setStatus( RightVO.TRUE );
      ( ( RightVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageRight" );
   }

   /**
    * Add right
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
            final RightService rightService = ( RightService ) getService( "rightService" );
            // 获得ActionForm
            final RightVO rightVO = ( RightVO ) form;

            // 获取登录用户
            rightVO.setCreateBy( getUserId( request, response ) );
            rightVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            rightService.insertRight( rightVO );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }
         // 清空form
         ( ( RightVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify right
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
            final RightService rightService = ( RightService ) getService( "rightService" );
            // 获得当前主键
            final String rightId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "rightId" ), "GBK" ) );
            // 获得主键对应对象
            final RightVO rightVO = rightService.getRightVOByRightId( rightId );

            // 装载界面传值
            rightVO.update( ( RightVO ) form );

            // 获取登录用户
            rightVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            rightService.updateRight( rightVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
         //清空form
         ( ( RightVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete right
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
         final RightService rightService = ( RightService ) getService( "rightService" );
         final RightVO rightVO = new RightVO();
         // 获得当前主键
         String rightId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "rightId" ), "GBK" ) );

         // 删除主键对应对象
         rightVO.setRightId( rightId );
         rightVO.setModifyBy( getUserId( request, response ) );
         rightService.deleteRight( rightVO );

         // 清除Selected IDs和子Action
         rightVO.setSelectedIds( "" );
         rightVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete right list
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
         final RightService rightService = ( RightService ) getService( "rightService" );
         // 获得Action Form
         RightVO rightVO = ( RightVO ) form;
         // 存在选中的ID
         if ( rightVO.getSelectedIds() != null && !rightVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : rightVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               rightVO.setRightId( selectedId );
               rightVO.setModifyBy( getUserId( request, response ) );
               rightService.deleteRight( rightVO );
            }
         }
         // 清除Selected IDs和子Action
         rightVO.setSelectedIds( "" );
         rightVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
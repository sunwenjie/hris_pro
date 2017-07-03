package com.kan.base.web.actions.system;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.ConstantService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

public class ConstantAction extends BaseAction
{

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final ConstantService constantService = ( ConstantService ) getService( "constantService" );
         // 获得Action Form
         final ConstantVO constantVO = ( ConstantVO ) form;
         // 需要设置当前用户AccountId
         constantVO.setAccountId( getAccountId( request, response ) );

         // 调用删除方法
         if ( constantVO.getSubAction() != null && constantVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( constantVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder constantHolder = new PagedListHolder();
         // 传入当前页
         constantHolder.setPage( page );
         // 传入当前值对象
         constantHolder.setObject( constantVO );
         // 设置页面记录条数
         constantHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         constantService.getConstantVOsByCondition( constantHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( constantHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "constantHolder", constantHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Constant JSP
            return mapping.findForward( "listConstantTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listConstant" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( ConstantVO ) form ).setStatus( BaseVO.TRUE );
      ( ( ConstantVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面  
      return mapping.findForward( "manageConstant" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final ConstantService constantService = ( ConstantService ) getService( "constantService" );

            // 获得当前FORM
            final ConstantVO constantVO = ( ConstantVO ) form;
            constantVO.setCreateBy( getUserId( request, response ) );
            constantVO.setModifyBy( getUserId( request, response ) );
            constantVO.setAccountId( getAccountId( request, response ) );
            constantService.insertConstant( constantVO );

            // 初始化常量持久对象
            constantsInit( "initConstant", getAccountId( request, response ) );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }

         // 清空Action Form
         ( ( ConstantVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final ConstantService constantService = ( ConstantService ) getService( "constantService" );
         // 主键获取需解码
         final String constantId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "constantId" ), "UTF-8" ) );
         // 获得ConstantVO对象
         final ConstantVO constantVO = constantService.getConstantVOByConstantId( constantId );
         // 区分Add和Update
         constantVO.setSubAction( BaseAction.VIEW_OBJECT );
         constantVO.reset( null, request );
         // 将ConstantVO传入request对象
         request.setAttribute( "constantForm", constantVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageConstant" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final ConstantService constantService = ( ConstantService ) getService( "constantService" );

            // 主键获取需解码
            final String constantId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "constantId" ), "UTF-8" ) );
            // 获取ConstantVO对象
            final ConstantVO constantVO = constantService.getConstantVOByConstantId( constantId );
            // 装载界面传值
            constantVO.update( ( ConstantVO ) form );
            // 获取登录用户
            constantVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            constantService.updateConstant( constantVO );

            // 初始化常量持久对象
            constantsInit( "initConstant", getAccountId( request, response ) );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // 清空Action Form
         ( ( ConstantVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final ConstantService constantService = ( ConstantService ) getService( "constantService" );

         // 获得Action Form
         final ConstantVO constantVO = ( ConstantVO ) form;
         // 存在选中的ID
         if ( constantVO.getSelectedIds() != null && !constantVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : constantVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               constantVO.setConstantId( selectedId );
               constantVO.setAccountId( getAccountId( request, response ) );
               constantVO.setModifyBy( getUserId( request, response ) );
               constantService.deleteConstant( constantVO );
            }
         }

         // 初始化常量持久对象
         constantsInit( "initConstant", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         constantVO.setSelectedIds( "" );
         constantVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

package com.kan.base.web.actions.management;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ItemGroupVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ItemGroupService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ItemGroupAction extends BaseAction
{

   public final static String accessAction = "HRO_MGT_ITEM_GROUP";

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
         final ItemGroupService itemGroupService = ( ItemGroupService ) getService( "itemGroupService" );
         // 获得Action Form
         final ItemGroupVO itemGroupVO = ( ItemGroupVO ) form;
         // 需要设置当前用户AccountId
         itemGroupVO.setAccountId( getAccountId( request, response ) );
         // 调用删除方法
         if ( itemGroupVO.getSubAction() != null && itemGroupVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( itemGroupVO );
         }
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder itemGroupHolder = new PagedListHolder();
         // 传入当前页
         itemGroupHolder.setPage( page );
         // 传入当前值对象
         itemGroupHolder.setObject( itemGroupVO );
         // 设置页面记录条数
         itemGroupHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         itemGroupService.getItemGroupVOsByCondition( itemGroupHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( itemGroupHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "itemGroupHolder", itemGroupHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            return mapping.findForward( "listItemGroupTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listItemGroup" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( ItemGroupVO ) form ).setListMerge( ItemGroupVO.FALSE );
      ( ( ItemGroupVO ) form ).setReportMerge( ItemGroupVO.FALSE );
      ( ( ItemGroupVO ) form ).setInvoiceMerge( ItemGroupVO.FALSE );
      ( ( ItemGroupVO ) form ).setStatus( ItemGroupVO.TRUE );
      ( ( ItemGroupVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面  
      return mapping.findForward( "manageItemGroup" );
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
            final ItemGroupService itemGroupService = ( ItemGroupService ) getService( "itemGroupService" );
            // 获得当前FORM
            final ItemGroupVO itemGroupVO = ( ItemGroupVO ) form;
            itemGroupVO.setCreateBy( getUserId( request, response ) );
            itemGroupVO.setModifyBy( getUserId( request, response ) );
            itemGroupVO.setAccountId( getAccountId( request, response ) );
            itemGroupService.insertItemGroup( itemGroupVO );

            // 初始化常量持久对象
            constantsInit( "initItemGroup", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, itemGroupVO, Operate.ADD, itemGroupVO.getItemGroupId(), null );
         }
         // 清空Form
         ( ( ItemGroupVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final ItemGroupService itemGroupService = ( ItemGroupService ) getService( "itemGroupService" );
         // 主键获取需解码
         final String itemGroupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
         // 获得ItemGroupVO对象
         final ItemGroupVO itemGroupVO = itemGroupService.getItemGroupVOByItemGroupId( itemGroupId );
         // 区分Add和Update
         itemGroupVO.setSubAction( VIEW_OBJECT );
         itemGroupVO.reset( null, request );
         // 将ItemGroupVO传入request对象
         request.setAttribute( "itemGroupForm", itemGroupVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageItemGroup" );
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
            final ItemGroupService itemGroupService = ( ItemGroupService ) getService( "itemGroupService" );
            // 主键获取需解码
            final String itemGroupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
            // 获取ItemGroupVO对象
            final ItemGroupVO itemGroupVO = itemGroupService.getItemGroupVOByItemGroupId( itemGroupId );
            // 装载界面传值
            itemGroupVO.update( ( ItemGroupVO ) form );
            // 获取登录用户
            itemGroupVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            itemGroupService.updateItemGroup( itemGroupVO );

            // 初始化常量持久对象
            constantsInit( "initItemGroup", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, itemGroupVO, Operate.MODIFY, itemGroupVO.getItemGroupId(), null );
         }
         // 清空Form
         ( ( ItemGroupVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
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
         final ItemGroupService itemGroupService = ( ItemGroupService ) getService( "itemGroupService" );
         // 获得Action Form
         final ItemGroupVO itemGroupVO = ( ItemGroupVO ) form;
         // 存在选中的ID
         if ( itemGroupVO.getSelectedIds() != null && !itemGroupVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : itemGroupVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               itemGroupVO.setItemGroupId( selectedId );
               itemGroupVO.setAccountId( getAccountId( request, response ) );
               itemGroupVO.setModifyBy( getUserId( request, response ) );
               itemGroupService.deleteItemGroup( itemGroupVO );
            }

            // 初始化常量持久对象
            constantsInit( "initItemGroup", getAccountId( request, response ) );

            insertlog( request, itemGroupVO, Operate.DELETE, null, itemGroupVO.getSelectedIds() );
         }
         // 清除Selected IDs和子Action
         itemGroupVO.setSelectedIds( "" );
         itemGroupVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

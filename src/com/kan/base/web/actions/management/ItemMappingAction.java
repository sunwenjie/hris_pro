package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ItemMappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ItemMappingService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ItemMappingAction extends BaseAction
{
   public static final String accessAction = "HRO_MGT_ITEM_MAPPING";

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
         final ItemMappingService itemMappingService = ( ItemMappingService ) getService( "itemMappingService" );
         // 获得Action Form
         final ItemMappingVO itemMappingVO = ( ItemMappingVO ) form;
         // 需要设置当前用户AccountId
         itemMappingVO.setAccountId( getAccountId( request, response ) );
         // 调用删除方法
         if ( itemMappingVO.getSubAction() != null && itemMappingVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( itemMappingVO );
         }
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder itemMappingHolder = new PagedListHolder();
         // 传入当前页
         itemMappingHolder.setPage( page );
         // 传入当前值对象
         itemMappingHolder.setObject( itemMappingVO );
         // 设置页面记录条数
         itemMappingHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         itemMappingService.getItemMappingVOsByCondition( itemMappingHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( itemMappingHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "itemMappingHolder", itemMappingHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            return mapping.findForward( "listItemMappingTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listItemMapping" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( ItemMappingVO ) form ).setStatus( ItemMappingVO.TRUE );
      ( ( ItemMappingVO ) form ).setSubAction( CREATE_OBJECT );
      // 跳转到新建界面  
      return mapping.findForward( "manageItemMapping" );
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
            final ItemMappingService itemMappingService = ( ItemMappingService ) getService( "itemMappingService" );
            // 获得当前FORM
            final ItemMappingVO itemMappingVO = ( ItemMappingVO ) form;
            itemMappingVO.setCreateBy( getUserId( request, response ) );
            itemMappingVO.setModifyBy( getUserId( request, response ) );
            itemMappingVO.setAccountId( getAccountId( request, response ) );
            itemMappingService.insertItemMapping( itemMappingVO );

            // 初始化常量持久对象
            constantsInit( "initItemMapping", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, itemMappingVO, Operate.ADD, itemMappingVO.getMappingId(), null );
         }

         // 清空Form
         ( ( ItemMappingVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   public ActionForward add_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         final String itemId = request.getParameter( "itemId" );

         final String entityId = request.getParameter( "entityId" );

         final String businessTypeId = request.getParameter( "businessTypeId" );
         // 获得当前账户所有ItemMapping
         final List< ItemMappingVO > itemMappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).ITEM_MAPPING_VO;
         Integer flag = 1;
         if ( itemMappingVOs != null && itemMappingVOs.size() > 0 )
         {
            for ( Object itemMappingVOObject : itemMappingVOs )
            {
               if ( ( ( ItemMappingVO ) itemMappingVOObject ).getItemId().equals( itemId ) )
               {
                  if ( ( ( ItemMappingVO ) itemMappingVOObject ).getEntityId().equals( entityId ) )
                  {
                     if ( ( ( ItemMappingVO ) itemMappingVOObject ).getBusinessTypeId().equals( businessTypeId ) )
                     {
                        flag = 2;
                     }
                  }
               }
            }
         }
         // AJAX调用
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         final PrintWriter out = response.getWriter();
         out.println( "<input type=\"hidden\" name=\"isFeasible\" id=\"isFeasible\" value=\"" + flag + "\"/>" );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
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
         final ItemMappingService itemMappingService = ( ItemMappingService ) getService( "itemMappingService" );
         // 主键获取需解码
         final String mappingId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "mappingId" ), "UTF-8" ) );
         // 获得ItemMappingVO对象                                                                                          
         final ItemMappingVO itemMappingVO = itemMappingService.getItemMappingVOByMappingId( mappingId );
         // 区分Add和Update
         itemMappingVO.setSubAction( VIEW_OBJECT );
         itemMappingVO.reset( null, request );
         // 将ItemMappingVO传入request对象
         request.setAttribute( "itemMappingForm", itemMappingVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageItemMapping" );
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
            final ItemMappingService itemMappingService = ( ItemMappingService ) getService( "itemMappingService" );
            // 主键获取需解码
            final String mappingId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "itemMappingId" ), "UTF-8" ) );
            // 获取ItemMappingVO对象
            final ItemMappingVO itemMappingVO = itemMappingService.getItemMappingVOByMappingId( mappingId );
            // 装载界面传值
            itemMappingVO.update( ( ItemMappingVO ) form );
            // 获取登录用户
            itemMappingVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            itemMappingService.updateItemMapping( itemMappingVO );

            // 初始化常量持久对象
            constantsInit( "initItemMapping", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, itemMappingVO, Operate.MODIFY, itemMappingVO.getMappingId(), null );
         }
         // 清空Form
         ( ( ItemMappingVO ) form ).reset();
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
         final ItemMappingService itemMappingService = ( ItemMappingService ) getService( "itemMappingService" );
         // 获得Action Form
         final ItemMappingVO itemMappingVO = ( ItemMappingVO ) form;
         // 存在选中的ID
         if ( itemMappingVO.getSelectedIds() != null && !itemMappingVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : itemMappingVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               itemMappingVO.setMappingId( selectedId );
               itemMappingVO.setAccountId( getAccountId( request, response ) );
               itemMappingVO.setModifyBy( getUserId( request, response ) );
               itemMappingService.deleteItemMapping( itemMappingVO );
            }

            // 初始化常量持久对象
            constantsInit( "initItemMapping", getAccountId( request, response ) );

            insertlog( request, itemMappingVO, Operate.DELETE, null, itemMappingVO.getSelectedIds() );
         }
         // 清除Selected IDs和子Action
         itemMappingVO.setSelectedIds( "" );
         itemMappingVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

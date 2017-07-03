package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ItemService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ItemAction extends BaseAction
{
   public final static String accessAction = "HRO_MGT_ITEM";

   /**
    * Get Object Json
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2014-05-21
   public ActionForward get_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取ItemId
         final String itemId = request.getParameter( "itemId" );

         // 获取ItemVO
         final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( itemId );

         // 初始化 JSONObject
         final JSONObject jsonObject = JSONObject.fromObject( itemVO );

         // Send to front
         out.println( jsonObject.toString() );
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

   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 科目Type 0：所有；1：不包括社保
         final String type = request.getParameter( "type" );
         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         // 获取ItemVO List
         final List< ItemVO > itemVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOsByType( type );

         if ( itemVOs != null && itemVOs.size() > 0 )
         {
            for ( ItemVO itemVO : itemVOs )
            {
               JSONObject jsonObject = new JSONObject();
               jsonObject.put( "id", itemVO.getItemId() );
               jsonObject.put( "name", itemVO.getNameZH() + " - " + itemVO.getNameEN() );
               jsonObject.put( "accountId", itemVO.getAccountId() );
               array.add( jsonObject );
            }
         }

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
         final ItemService itemService = ( ItemService ) getService( "itemService" );
         // 获得Action Form
         final ItemVO itemVO = ( ItemVO ) form;
         // 需要设置当前用户AccountId
         itemVO.setAccountId( getAccountId( request, response ) );
         // 调用删除方法
         if ( itemVO.getSubAction() != null && itemVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( itemVO );
         }
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder itemHolder = new PagedListHolder();
         // 传入当前页
         itemHolder.setPage( page );
         // 传入当前值对象
         itemHolder.setObject( itemVO );
         // 设置页面记录条数
         itemHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         itemService.getItemVOsByCondition( itemHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( itemHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "itemHolder", itemHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Item JSP
            request.setAttribute( "accountId", getAccountId( request, null ) );
            return mapping.findForward( "listItemTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listItem" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( ItemVO ) form ).setStatus( ItemVO.TRUE );
      ( ( ItemVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( ItemVO ) form ).setBillRatePersonal( "0" );
      ( ( ItemVO ) form ).setBillRateCompany( "0" );
      ( ( ItemVO ) form ).setCostRatePersonal( "0" );
      ( ( ItemVO ) form ).setCostRateCompany( "0" );
      // 跳转到新建界面  
      return mapping.findForward( "manageItem" );
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
            final ItemService itemService = ( ItemService ) getService( "itemService" );
            // 获得当前FORM
            final ItemVO itemVO = ( ItemVO ) form;
            itemVO.setCreateBy( getUserId( request, response ) );
            itemVO.setModifyBy( getUserId( request, response ) );
            itemVO.setAccountId( getAccountId( request, response ) );
            int returnVal = itemService.insertItem( itemVO );

            // 初始化常量持久对象
            constantsInit( "initItem", getAccountId( request, response ) );

            if ( returnVal > 1 )
            {
               constantsInit( "initTable", getAccountId( request, response ) );
               constantsInit( "initListHeader", getAccountId( request, response ) );
               constantsInit( "initImportHeader", getAccountId( request, response ) );
            }

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, itemVO, Operate.ADD, itemVO.getItemId(), null );
         }

         // 清空Form
         ( ( ItemVO ) form ).reset();
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
         final ItemService itemService = ( ItemService ) getService( "itemService" );
         // 主键获取需解码
         final String itemId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // 获得ItemVO对象
         final ItemVO itemVO = itemService.getItemVOByItemId( itemId );
         // 区分Add和Update
         itemVO.setSubAction( VIEW_OBJECT );
         // 刷新国际化
         itemVO.reset( null, request );
         // 将ItemVO传入request对象
         request.setAttribute( "itemForm", itemVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageItem" );
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
            final ItemService itemService = ( ItemService ) getService( "itemService" );

            // 主键获取需解码
            final String itemId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "encodedId" ), "UTF-8" ) );
            // 获取ItemVO对象
            final ItemVO itemVO = itemService.getItemVOByItemId( itemId );
            // 装载界面传值
            itemVO.update( ( ItemVO ) form );
            itemVO.setIsCascade( ( ( ItemVO ) form ).getIsCascade() );
            // 获取登录用户
            itemVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            int returnVal = itemService.updateItem( itemVO );

            // 初始化常量持久对象
            constantsInit( "initItem", getAccountId( request, response ) );

            if ( returnVal > 1 )
            {
               constantsInit( "initTable", getAccountId( request, response ) );
               constantsInit( "initListHeader", getAccountId( request, response ) );
               constantsInit( "initImportHeader", getAccountId( request, response ) );
            }

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, itemVO, Operate.MODIFY, itemVO.getItemId(), null );
         }
         // 清空Form
         ( ( ItemVO ) form ).reset();
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
         final ItemService itemService = ( ItemService ) getService( "itemService" );
         // 获得Action Form
         final ItemVO itemVO = ( ItemVO ) form;

         // 存在选中的ID
         if ( itemVO.getSelectedIds() != null && !itemVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : itemVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               itemVO.setItemId( selectedId );
               itemVO.setAccountId( getAccountId( request, response ) );
               itemVO.setModifyBy( getUserId( request, response ) );
               itemService.deleteItem( itemVO );
            }

            insertlog( request, itemVO, Operate.DELETE, null, itemVO.getSelectedIds() );
         }

         // 初始化常量持久对象
         constantsInit( "initItem", getAccountId( request, response ) );

         // 清除Selected IDs和子Action
         itemVO.setSelectedIds( "" );
         itemVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final StringBuffer sb = new StringBuffer();
         final boolean isZH = "zh".equalsIgnoreCase( request.getLocale().getLanguage() ) ? true : false;
         final String id = request.getParameter( "id" );
         final String itemId = request.getParameter( "itemId" );
         List< ItemVO > itemVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).ITEM_VO;
         String selected = "";
         for ( ItemVO itemVO : itemVOs )
         {
            if ( KANUtil.filterEmpty( id ) != null && KANUtil.filterEmpty( itemId ) != null && itemId.equals( itemVO.getItemId() ) )
            {
               selected = "selected";
            }
            final String itemName = isZH ? itemVO.getNameZH() : itemVO.getNameEN();
            sb.append( "<option " + selected + " id='option_itemId_" + itemVO.getItemId() + "' value='" + itemVO.getItemId() + "'>" + itemName + "</option>" );
         }
         out.print( sb.toString() );
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
    * 验证元素名是否存在
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward checkNameExist( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final String _name = request.getParameter( "name" );
         final String name = KANUtil.filterEmpty( _name ) == null ? "" : URLDecoder.decode( URLDecoder.decode( _name, "UTF-8" ), "UTF-8" );
         List< ItemVO > itemVOs = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).ITEM_VO;
         boolean exist = false;
         if ( KANUtil.filterEmpty( name.trim() ) != null )
         {
            for ( ItemVO item : itemVOs )
            {
               if ( name.equalsIgnoreCase( item.getNameZH().trim() ) || name.equalsIgnoreCase( item.getNameEN().trim() ) )
               {
                  exist = true;
                  break;
               }
            }
         }
         out.print( exist );
         // Send to front
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
}

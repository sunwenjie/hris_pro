package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.TableDTO;
import com.kan.base.domain.define.TableVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.TableService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;

public class TableAction extends BaseAction
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
         // 获得Action Form
         final TableVO tableVO = ( TableVO ) form;

         // 如果没有指定排序则默认按 tableIndex排序
         if ( tableVO.getSortColumn() == null || tableVO.getSortColumn().isEmpty() )
         {
            tableVO.setSortColumn( "moduleType,tableIndex" );
            tableVO.setSortOrder( "asc" );
         }
         
         // 调用删除方法
         if ( tableVO.getSubAction() != null && tableVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( tableVO );
         }

         // 初始化Service接口
         final TableService tableService = ( TableService ) getService( "tableService" );
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder tablePagedListHolder = new PagedListHolder();
         // 传入当前页
         tablePagedListHolder.setPage( page );
         // 传入当前值对象
         tablePagedListHolder.setObject( tableVO );
         // 设置页面记录条数
         tablePagedListHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         tableVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         tableService.getTableVOsByCondition( tablePagedListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( tablePagedListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "tablePagedListHolder", tablePagedListHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax 调用，重新传入AccountId
            request.setAttribute( "accountId", getAccountId( request, response ) );
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listTableTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listTable" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( TableVO ) form ).setStatus( TableVO.TRUE );
      ( ( TableVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( TableVO ) form ).setTableIndex( "100" );
      ( ( TableVO ) form ).setTableType( TableVO.TRUE );
      ( ( TableVO ) form ).setRole( TableVO.TRUE );

      // 跳转到新建界面
      return mapping.findForward( "manageTable" );
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
            final TableService tableService = ( TableService ) getService( "tableService" );
            // 获得当前FORM
            final TableVO tableVO = ( TableVO ) form;
            tableVO.setCreateBy( getUserId( request, response ) );
            tableVO.setModifyBy( getUserId( request, response ) );
            tableVO.setAccountId( getAccountId( request, response ) );

            // Checkbox处理
            tableVO.setCanManager( ( tableVO.getCanManager() != null && tableVO.getCanManager().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanList( ( tableVO.getCanList() != null && tableVO.getCanList().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanSearch( ( tableVO.getCanSearch() != null && tableVO.getCanSearch().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanImport( ( tableVO.getCanImport() != null && tableVO.getCanImport().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanReport( ( tableVO.getCanReport() != null && tableVO.getCanManager().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );

            // 添加TableVO
            tableService.insertTable( tableVO );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }

         // 清空Action Form
         ( ( TableVO ) form ).reset();
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
         final TableService tableService = ( TableService ) getService( "tableService" );
         // 主键获取需解码
         final String tableId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "tableId" ), "UTF-8" ) );
         // 获得TableVO对象
         final TableVO tableVO = tableService.getTableVOByTableId( tableId );
         // Checkbox处理
         tableVO.setCanManager( ( tableVO.getCanManager() != null && tableVO.getCanManager().equalsIgnoreCase( TableVO.TRUE ) ) ? "on" : "" );
         tableVO.setCanList( ( tableVO.getCanList() != null && tableVO.getCanList().equalsIgnoreCase( TableVO.TRUE ) ) ? "on" : "" );
         tableVO.setCanSearch( ( tableVO.getCanSearch() != null && tableVO.getCanSearch().equalsIgnoreCase( TableVO.TRUE ) ) ? "on" : "" );
         tableVO.setCanImport( ( tableVO.getCanImport() != null && tableVO.getCanImport().equalsIgnoreCase( TableVO.TRUE ) ) ? "on" : "" );
         tableVO.setCanReport( ( tableVO.getCanReport() != null && tableVO.getCanReport().equalsIgnoreCase( TableVO.TRUE ) ) ? "on" : "" );
         // 区分Add和Update
         tableVO.setSubAction( "viewObject" );
         tableVO.reset( null, request );
         // 将TableVO传入request对象
         request.setAttribute( "tableForm", tableVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageTable" );
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
            final TableService tableService = ( TableService ) getService( "tableService" );
            // 主键获取需解码
            final String tableId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "tableId" ), "GBK" ) );
            // 获取TableVO对象
            final TableVO tableVO = tableService.getTableVOByTableId( tableId );
            // 装载界面传值
            tableVO.update( ( TableVO ) form );
            // Checkbox处理
            tableVO.setCanManager( ( tableVO.getCanManager() != null && tableVO.getCanManager().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanList( ( tableVO.getCanList() != null && tableVO.getCanList().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanSearch( ( tableVO.getCanSearch() != null && tableVO.getCanSearch().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanImport( ( tableVO.getCanImport() != null && tableVO.getCanImport().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            tableVO.setCanReport( ( tableVO.getCanReport() != null && tableVO.getCanReport().equalsIgnoreCase( "on" ) ) ? TableVO.TRUE : TableVO.FALSE );
            // 获取登录用户
            tableVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            tableService.updateTable( tableVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // 清空Form
         ( ( TableVO ) form ).reset();
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
         final TableService tableService = ( TableService ) getService( "tableService" );
         // 获得Action Form
         final TableVO tableVO = ( TableVO ) form;
         // 存在选中的ID
         if ( tableVO.getSelectedIds() != null && !tableVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : tableVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               tableVO.setTableId( selectedId );
               tableVO.setModifyBy( getUserId( request, response ) );
               tableVO.setAccountId( getAccountId( request, response ) );
               tableService.deleteTable( tableVO );
            }
         }
         // 清除Selected IDs和子Action
         tableVO.setSelectedIds( "" );
         tableVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   //* Add by siuxia 2014-01-20 *//
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

         // 获取tableId
         final String tableId = request.getParameter( "tableId" );

         // 获取TableDTO
         final TableDTO tableDTO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( tableId );

         // 初始化JSONObject
         final JSONObject jsonObject = new JSONObject();

         if ( tableDTO != null && tableDTO.getTableVO() != null )
         {
            jsonObject.put( "success", "true" );
            jsonObject.put( "nameZH", tableDTO.getTableVO().getNameZH() );
            jsonObject.put( "nameEN", tableDTO.getTableVO().getNameEN() );
         }
         else
         {
            jsonObject.put( "success", "false" );
         }

         // Send to client
         out.println( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

}
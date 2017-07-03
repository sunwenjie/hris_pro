package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.ColumnVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ColumnService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ColumnAction extends BaseAction
{

   public static String accessAction = "HRO_DEF_COLUMN";

   @Override
   // Code reviewed by Kevin Jin @ 2013-07-02
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final ColumnService columnService = ( ColumnService ) getService( "columnService" );

         // 获得Action Form
         final ColumnVO columnVO = ( ColumnVO ) form;

         // 调用删除方法
         if ( columnVO.getSubAction() != null && columnVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         else
         {
            decodedObject( columnVO );
         }

         // 如果没有指定排序则默认按employeeId排序
         if ( columnVO.getSortColumn() == null || columnVO.getSortColumn().isEmpty() )
         {
            columnVO.setSortColumn( "columnIndex" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder columnHolder = new PagedListHolder();
         // 传入当前页
         columnHolder.setPage( page );
         // 传入当前值对象
         columnHolder.setObject( columnVO );
         // 设置页面记录条数
         columnHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         columnVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         columnService.getColumnVOsByCondition( columnHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( columnHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "columnHolder", columnHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listColumnTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listColumn" );
   }

   @Override
   // Code reviewed by Kevin Jin @ 2013-07-02
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 避免重复提交
      this.saveToken( request );

      // 初始化Action Form属性
      ( ( ColumnVO ) form ).setStatus( ColumnVO.TRUE );
      ( ( ColumnVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( ColumnVO ) form ).setColumnIndex( "100" );
      ( ( ColumnVO ) form ).setValueType( "2" );
      ( ( ColumnVO ) form ).setInputType( "1" );
      ( ( ColumnVO ) form ).setDisplayType( "1" );
      ( ( ColumnVO ) form ).setIsDBColumn( "2" );
      ( ( ColumnVO ) form ).setEditable( ColumnVO.TRUE );
      ( ( ColumnVO ) form ).setIsRequired( ColumnVO.FALSE );
      ( ( ColumnVO ) form ).setValidateLengthMin( "0" );
      ( ( ColumnVO ) form ).setValidateLengthMax( "0" );
      ( ( ColumnVO ) form ).setValidateRangeMin( "0" );
      ( ( ColumnVO ) form ).setValidateRangeMax( "0" );
      ( ( ColumnVO ) form ).setUseThinking( ColumnVO.FALSE );

      // 跳转到新建界面
      return mapping.findForward( "manageColumn" );
   }

   @Override
   // Code reviewed by Kevin Jin @ 2013-07-02
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 判断是否为重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final ColumnService columnService = ( ColumnService ) getService( "columnService" );

            // 获得当前FORM
            final ColumnVO columnVO = ( ColumnVO ) form;

            columnVO.setCreateBy( getUserId( request, response ) );
            columnVO.setModifyBy( getUserId( request, response ) );
            columnVO.setAccountId( getAccountId( request, response ) );

            columnVO.setCanImport( ( columnVO.getCanImport() != null && columnVO.getCanImport().equalsIgnoreCase( "on" ) ) ? ColumnVO.TRUE : ColumnVO.FALSE );

            columnService.insertColumn( columnVO );

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, columnVO, Operate.ADD, columnVO.getColumnId(), null );
         }
         else
         {
            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            // 清空Action Form
            ( ( ColumnVO ) form ).reset();

            // 跳转到列表界面
            return list_object( mapping, form, request, response );
         }

         return to_objectModify( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   // Code reviewed by Kevin Jin @ 2013-07-02
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设定记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final ColumnService columnService = ( ColumnService ) getService( "columnService" );
         // 主键获取需解码
         String columnId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( columnId ) == null )
         {
            columnId = ( ( ColumnVO ) form ).getColumnId();
         }
         // 获得ColumnVO对象
         final ColumnVO columnVO = columnService.getColumnVOByColumnId( columnId );

         columnVO.setCanImport( ( columnVO.getCanImport() != null && columnVO.getCanImport().equalsIgnoreCase( ColumnVO.TRUE ) ) ? "on" : "" );

         // 设置SubAction
         columnVO.setSubAction( "viewObject" );
         columnVO.reset( null, request );
         // 将ColumnVO传入request对象
         request.setAttribute( "columnForm", columnVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageColumn" );
   }

   @Override
   // Code reviewed by Kevin Jin @ 2013-07-02
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交            
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final ColumnService columnService = ( ColumnService ) getService( "columnService" );

            // 主键解码
            final String columnId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得ColumnVO对象
            final ColumnVO columnVO = columnService.getColumnVOByColumnId( columnId );
            // 装载界面传值
            columnVO.update( ( ColumnVO ) form );
            // 获取登录用户
            columnVO.setModifyBy( getUserId( request, response ) );

            columnVO.setCanImport( ( columnVO.getCanImport() != null && columnVO.getCanImport().equalsIgnoreCase( "on" ) ) ? ColumnVO.TRUE : ColumnVO.FALSE );

            // 调用修改方法
            columnService.updateColumn( columnVO );

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, columnVO, Operate.MODIFY, columnVO.getColumnId(), null );
         }

         // 清空Action Form
         ( ( ColumnVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   // Code reviewed by Kevin Jin @ 2013-07-02
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final ColumnService columnService = ( ColumnService ) getService( "columnService" );

         // 获得Action Form
         ColumnVO columnVO = ( ColumnVO ) form;
         // 存在选中的ID
         if ( columnVO.getSelectedIds() != null && !columnVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : columnVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               final ColumnVO tempColumnVO = columnService.getColumnVOByColumnId( selectedId );
               tempColumnVO.setModifyBy( getUserId( request, response ) );
               tempColumnVO.setModifyDate( new Date() );
               // 调用删除接口
               columnService.deleteColumn( tempColumnVO );
            }

            insertlog( request, columnVO, Operate.DELETE, null, columnVO.getSelectedIds() );
            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
         }

         // 清除Selected IDs和子Action
         columnVO.setSelectedIds( "" );
         columnVO.setSubAction( "" );
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

         // 获取columnId
         final String columnId = request.getParameter( "columnId" );

         // 初始化Service
         final ColumnService columnService = ( ColumnService ) getService( "columnService" );

         // 获取ColumnVO
         final ColumnVO columnVO = columnService.getColumnVOByColumnId( columnId );

         // 初始化JSONObject
         final JSONObject jsonObject = new JSONObject();

         if ( columnVO != null )
         {
            jsonObject.put( "success", "true" );
            jsonObject.putAll( JSONObject.fromObject( columnVO ) );
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

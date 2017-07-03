package com.kan.base.web.actions.define;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.domain.define.SearchDetailVO;
import com.kan.base.domain.define.SearchHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.SearchHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class SearchHeaderAction extends BaseAction
{
   public static String accessAction = "HRO_DEFINE_SEARCH";

   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 初始化TableId
         final String tableId = request.getParameter( "tableId" );
         // 按照TableId初始化下拉选项
         final List< MappingVO > mappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSearchHeadersByTableId( tableId, KANUtil.filterEmpty( getCorpId( request, null ) ), request.getLocale().getLanguage() );

         if ( mappingVOs != null )
         {
            mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "searchId", "" ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "" );
   }

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
         final SearchHeaderVO searchHeaderVO = ( SearchHeaderVO ) form;
         // 处理subAction
         dealSubAction( searchHeaderVO, mapping, form, request, response );
         // 初始化Service接口
         final SearchHeaderService searchHeaderService = ( SearchHeaderService ) getService( "searchHeaderService" );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder searchHeaderHolder = new PagedListHolder();
         // 传入当前页
         searchHeaderHolder.setPage( page );
         // 传入当前值对象
         searchHeaderHolder.setObject( searchHeaderVO );
         // 设置页面记录条数
         searchHeaderHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         searchHeaderVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         searchHeaderService.getSearchHeaderVOsByCondition( searchHeaderHolder, true );
         refreshHolder( searchHeaderHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "searchHeaderHolder", searchHeaderHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listSearchHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listSearchHeader" );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-09
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( SearchHeaderVO ) form ).setStatus( SearchHeaderVO.TRUE );
      ( ( SearchHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageSearchHeader" );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-09
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化SearchDetailVO
         final SearchDetailVO searchDetailVO = new SearchDetailVO();

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final SearchHeaderService searchHeaderService = ( SearchHeaderService ) getService( "searchHeaderService" );

            // 获得当前FORM
            final SearchHeaderVO searchHeaderVO = ( SearchHeaderVO ) form;

            // Checkbox处理
            if ( searchHeaderVO.getUseJavaObject() != null && searchHeaderVO.getUseJavaObject().equalsIgnoreCase( "on" ) )
            {
               searchHeaderVO.setUseJavaObject( ListHeaderVO.TRUE );
            }
            else
            {
               searchHeaderVO.setUseJavaObject( ListHeaderVO.FALSE );
            }

            searchHeaderVO.setCreateBy( getUserId( request, response ) );
            searchHeaderVO.setModifyBy( getUserId( request, response ) );
            searchHeaderVO.setAccountId( getAccountId( request, response ) );
            searchHeaderService.insertSearchHeader( searchHeaderVO );

            searchDetailVO.setSearchHeaderId( searchHeaderVO.getSearchHeaderId() );

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initSearchHeader", getAccountId( request, response ) );

            // 返回编辑成功的标记 
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            insertlog( request, searchHeaderVO, Operate.ADD, searchHeaderVO.getSearchHeaderId(), null );
         }
         else
         {
            // 清空form
            ( ( SearchHeaderVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return new SearchDetailAction().list_object( mapping, searchDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   // Code reviewed by Kevin at 2013-07-09
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final SearchHeaderService searchHeaderService = ( SearchHeaderService ) getService( "searchHeaderService" );

            // 获取主键 - 需解码
            final String searchHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得SearchHeaderVO对象
            final SearchHeaderVO searchHeaderVO = searchHeaderService.getSearchHeaderVOBySearchHeaderId( searchHeaderId );
            // 装载界面传值
            searchHeaderVO.update( ( SearchHeaderVO ) form );

            // Checkbox处理
            if ( searchHeaderVO.getUseJavaObject() != null && searchHeaderVO.getUseJavaObject().equalsIgnoreCase( "on" ) )
            {
               searchHeaderVO.setUseJavaObject( ListHeaderVO.TRUE );
            }
            else
            {
               searchHeaderVO.setUseJavaObject( ListHeaderVO.FALSE );
            }

            // 获取登录用户
            searchHeaderVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            searchHeaderService.updateSearchHeader( searchHeaderVO );

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initSearchHeader", getAccountId( request, response ) );

            // 返回编辑成功的标记 
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, searchHeaderVO, Operate.MODIFY, searchHeaderVO.getSearchHeaderId(), null );
         }

         // 清空Action Form
         ( ( SearchHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return new SearchDetailAction().list_object( mapping, new SearchDetailVO(), request, response );
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
         final SearchHeaderService searchHeaderService = ( SearchHeaderService ) getService( "searchHeaderService" );

         // 获得Action Form
         final SearchHeaderVO searchHeaderVO = ( SearchHeaderVO ) form;

         // 存在选中的ID
         if ( searchHeaderVO.getSelectedIds() != null && !searchHeaderVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : searchHeaderVO.getSelectedIds().split( "," ) )
            {
               // 根据ID获取对应的searchHeaderVO
               final SearchHeaderVO searchHeaderVOForDel = searchHeaderService.getSearchHeaderVOBySearchHeaderId( selectedId );
               searchHeaderVOForDel.setModifyBy( getUserId( request, response ) );
               searchHeaderVOForDel.setModifyDate( new Date() );
               searchHeaderService.deleteSearchHeader( searchHeaderVOForDel );
            }

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initSearchHeader", getAccountId( request, response ) );

            insertlog( request, searchHeaderVO, Operate.DELETE, null, searchHeaderVO.getSelectedIds() );
         }

         // 清除Selected IDs和子Action
         searchHeaderVO.setSelectedIds( "" );
         searchHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

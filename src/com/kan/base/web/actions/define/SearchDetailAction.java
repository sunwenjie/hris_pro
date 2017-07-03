package com.kan.base.web.actions.define;

import java.net.URLDecoder;
import java.util.ArrayList;
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
import com.kan.base.service.inf.define.SearchDetailService;
import com.kan.base.service.inf.define.SearchHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class SearchDetailAction extends BaseAction
{
   public static String accessAction = "HRO_DEFINE_SEARCH";

   @Override
   // Code reviewed by Kevin Jin at 2013-07-09
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         //获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 获得Action Form
         final SearchDetailVO searchDetailVO = ( SearchDetailVO ) form;

         // 调用删除方法
         if ( searchDetailVO.getSubAction() != null && searchDetailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // 初始化Service接口
         final SearchDetailService searchDetailService = ( SearchDetailService ) getService( "searchDetailService" );
         final SearchHeaderService searchHeaderService = ( SearchHeaderService ) getService( "searchHeaderService" );

         // 获得当下主键  
         String searchHeaderId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( searchHeaderId ) == null )
         {
            searchHeaderId = searchDetailVO.getSearchHeaderId();
         }
         else
         {
            searchHeaderId = KANUtil.decodeStringFromAjax( searchHeaderId );
         }

         // 获取SerachDetailVO
         final SearchHeaderVO searchHeaderVO = searchHeaderService.getSearchHeaderVOBySearchHeaderId( searchHeaderId );
         searchHeaderVO.setSubAction( VIEW_OBJECT );
         searchHeaderVO.reset( null, request );
         // 设置Checkbox
         searchHeaderVO.setUseJavaObject( searchHeaderVO.getUseJavaObject() != null && searchHeaderVO.getUseJavaObject().equals( ListHeaderVO.TRUE ) ? "on" : "" );
         // 传入request对象
         request.setAttribute( "searchHeaderForm", searchHeaderVO );

         // 根据SearchHeaderId查找，得到SearchDetailVO集合
         searchDetailVO.setSearchHeaderId( searchHeaderId );
         // 如果没有指定排序则默认按 列表字段顺序排序
         if ( searchDetailVO.getSortColumn() == null || searchDetailVO.getSortColumn().isEmpty() )
         {
            searchDetailVO.setSortColumn( "columnIndex" );
         }

         //此处分页代码
         final PagedListHolder searchDetailHolder = new PagedListHolder();
         // 传入当前页
         searchDetailHolder.setPage( page );
         // 传入当前值对象
         searchDetailHolder.setObject( searchDetailVO );
         // 设置页面记录条数
         searchDetailHolder.setPageSize( listPageSize_large );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         searchDetailService.getSearchDetailVOsByCondition( searchDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( searchDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "searchDetailHolder", searchDetailHolder );

         // 初始化字段
         // 初始化字段MappingVO List
         final List< MappingVO > columns = new ArrayList< MappingVO >();

         // 不是启用JAVA对象，加载table字段
         if ( KANUtil.filterEmpty( searchHeaderVO.getTableId(), "0" ) != null )
         {
            columns.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( searchHeaderVO.getTableId() ).getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ), true ) );

            // 添加不关联选项
            columns.add( 1, new MappingVO( "1", KANUtil.getProperty( request.getLocale(), "def.column.no.relation" ) ) );
         }
         // 添加空的下拉选项，即“请选择”
         columns.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         searchDetailVO.setColumns( columns );
         searchDetailVO.setSubAction( "" );
         searchDetailVO.setSearchHeaderId( searchHeaderId );
         searchDetailVO.setColumnIndex( "0" );
         searchDetailVO.setFontSize( "13" );
         searchDetailVO.setUseThinking( SearchDetailVO.FALSE );
         searchDetailVO.setDisplay( SearchDetailVO.TRUE );
         searchDetailVO.setStatus( SearchDetailVO.TRUE );
         searchDetailVO.reset( null, request );

         columnIsExist( searchDetailVO.getColumns(), searchHeaderId, searchDetailService );

         // SearchDetail写入Request对象
         request.setAttribute( "searchDetailForm", searchDetailVO );

         // 如果是AJAX调用，则直接传值给table JSP页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listSearchDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listSearchDetail" );
   }

   private void columnIsExist( final List< MappingVO > columns, final String searchHeaderId, final SearchDetailService searchDetailService ) throws KANException
   {
      if ( columns != null && columns.size() > 0 )
      {
         SearchDetailVO searchDetailVO = null;
         for ( MappingVO column : columns )
         {
            searchDetailVO = new SearchDetailVO();
            searchDetailVO.setSearchHeaderId( searchHeaderId );
            searchDetailVO.setColumnId( column.getMappingId() );

            final List< Object > objects = searchDetailService.getSearchDetailVOsByCondition( searchDetailVO );
            column.setMappingStatus( ( objects != null && objects.size() > 0 ) ? "2" : "1" );
         }
      }
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-09
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service 接口
            final SearchDetailService searchDetailService = ( SearchDetailService ) getService( "searchDetailService" );

            // 获得SearchHeaderId
            final String searchHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得当前form
            final SearchDetailVO searchDetailVO = ( SearchDetailVO ) form;
            // 初始化SearchDetailVO对象
            searchDetailVO.setSearchHeaderId( searchHeaderId );
            searchDetailVO.setCreateBy( getUserId( request, response ) );
            searchDetailVO.setModifyBy( getUserId( request, response ) );
            searchDetailVO.setAccountId( getAccountId( request, response ) );
            searchDetailService.insertSearchDetail( searchDetailVO );

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initSearchHeader", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, searchDetailVO, Operate.ADD, searchDetailVO.getSearchDetailId(), null );
         }

         // 清空Action Form
         ( ( SearchDetailVO ) form ).reset();
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
      // no use
      return null;
   }

   // Code reviewed by Kevin Jin at 2013-07-09
   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final SearchDetailService searchDetailService = ( SearchDetailService ) getService( "searchDetailService" );
         final SearchHeaderService searchHeaderService = ( SearchHeaderService ) getService( "searchHeaderService" );

         // 获取主键 - 需解码
         final String searchDetailId = KANUtil.decodeString( request.getParameter( "searchDetailId" ) );

         // 获取SearchDetailVO对象
         final SearchDetailVO searchDetailVO = searchDetailService.getSearchDetailVOBySearchDetailId( searchDetailId );

         // 获取SearchHeaderVO对象
         final SearchHeaderVO searchHeaderVO = searchHeaderService.getSearchHeaderVOBySearchHeaderId( searchDetailVO.getSearchHeaderId() );

         // 国际化传值
         searchDetailVO.reset( null, request );

         // 初始化字段MappingVO List
         final List< MappingVO > columns = new ArrayList< MappingVO >();

         // 不是启用JAVA对象，加载table字段
         if ( KANUtil.filterEmpty( searchHeaderVO.getTableId(), "0" ) != null )
         {
            columns.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( searchHeaderVO.getTableId() ).getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ), true ) );

            // 添加不关联选项
            columns.add( 1, new MappingVO( "1", KANUtil.getProperty( request.getLocale(), "def.column.no.relation" ) ) );
         }
         // 添加空的下拉选项，即“请选择”
         columns.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         searchDetailVO.setColumns( columns );

         // 设置SubAction
         searchDetailVO.setSubAction( VIEW_OBJECT );

         // 写入Request
         request.setAttribute( "searchHeaderForm", searchHeaderVO );
         request.setAttribute( "searchDetailForm", searchDetailVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // AJAX调用跳转FORM页面
      return mapping.findForward( "manageSearchDetailForm" );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-09
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final SearchDetailService searchDetailService = ( SearchDetailService ) getService( "searchDetailService" );

            // 获取主键 - 需解码
            final String searchDetailId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "searchDetailId" ), "UTF-8" ) );
            // 获得SearchDetailVO对象
            final SearchDetailVO searchDetailVO = searchDetailService.getSearchDetailVOBySearchDetailId( searchDetailId );
            // 装载界面传值
            searchDetailVO.update( ( SearchDetailVO ) form );
            // 获取登录用户
            searchDetailVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            searchDetailService.updateSearchDetail( searchDetailVO );

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initSearchHeader", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, searchDetailVO, Operate.MODIFY, searchDetailVO.getSearchDetailId(), null );
         }

         // 清空Form
         ( ( SearchDetailVO ) form ).reset();
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
   // Code reviewed by Kevin Jin at 2013-07-09
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final SearchDetailService searchDetailService = ( SearchDetailService ) getService( "searchDetailService" );

         // 获得当前form
         SearchDetailVO searchDetailVO = ( SearchDetailVO ) form;
         // 存在选中的ID
         if ( searchDetailVO.getSelectedIds() != null && !searchDetailVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : searchDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               final SearchDetailVO tempSearchDetailVO = searchDetailService.getSearchDetailVOBySearchDetailId( selectedId );
               tempSearchDetailVO.setModifyBy( getUserId( request, response ) );
               tempSearchDetailVO.setModifyDate( new Date() );
               // 调用删除接口
               searchDetailService.deleteSearchDetail( tempSearchDetailVO );
            }

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initSearchHeader", getAccountId( request, response ) );

            insertlog( request, searchDetailVO, Operate.DELETE, null, searchDetailVO.getSelectedIds() );
         }

         // 清除Selected IDs和子Action
         searchDetailVO.setSelectedIds( "" );
         searchDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

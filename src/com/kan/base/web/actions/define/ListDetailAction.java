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

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ListDetailService;
import com.kan.base.service.inf.define.ListHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ListDetailAction extends BaseAction
{

   public static String accessAction = "HRO_DEFINE_LIST";

   @Override
   // Code reviewed by Kevin Jin at 2013-07-08
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否AJAX调用
         final String ajax = request.getParameter( "ajax" );

         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 获得Action Form
         final ListDetailVO listDetailVO = ( ListDetailVO ) form;

         // 如果子Action是删除
         if ( listDetailVO.getSubAction() != null && listDetailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }

         // 初始化Service接口
         final ListDetailService listDetailService = ( ListDetailService ) getService( "listDetailService" );
         final ListHeaderService listHeaderService = ( ListHeaderService ) getService( "listHeaderService" );

         // 获得主表主键
         String listHeaderId = request.getParameter( "listHeaderId" );
         if ( KANUtil.filterEmpty( listHeaderId ) == null )
         {
            listHeaderId = listDetailVO.getListHeaderId();
         }
         else
         {
            listHeaderId = KANUtil.decodeStringFromAjax( listHeaderId );
         }

         // 根据主键获得 ListHeaderVO对象
         final ListHeaderVO listHeaderVO = listHeaderService.getListHeaderVOByListHeaderId( listHeaderId );
         listHeaderVO.setSubAction( VIEW_OBJECT );
         listHeaderVO.reset( null, request );
         // 设置分页字段的Checkbox
         listHeaderVO.setUsePagination( listHeaderVO.getUsePagination() != null && listHeaderVO.getUsePagination().equals( ListHeaderVO.TRUE ) ? "on" : "" );
         listHeaderVO.setUseJavaObject( listHeaderVO.getUseJavaObject() != null && listHeaderVO.getUseJavaObject().equals( ListHeaderVO.TRUE ) ? "on" : "" );
         // 传入request对象
         request.setAttribute( "listHeaderForm", listHeaderVO );

         // 根据外键查找，得到ListDetailVO集合
         listDetailVO.setListHeaderId( listHeaderId );
         // 如果没有指定排序则默认按 列表字段顺序排序
         if ( listDetailVO.getSortColumn() == null || listDetailVO.getSortColumn().isEmpty() )
         {
            listDetailVO.setSortColumn( "columnIndex" );
         }

         // 此处分页代码
         final PagedListHolder listDetailHolder = new PagedListHolder();
         // 传入当前页
         listDetailHolder.setPage( page );
         // 传入当前值对象
         listDetailHolder.setObject( listDetailVO );
         // 设置页面记录条数
         listDetailHolder.setPageSize( listPageSize_large );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         listDetailService.getListDetailVOsByCondition( listDetailHolder, true );

         // 初始化字段
         final List< MappingVO > columns = new ArrayList< MappingVO >();

         // 加载table字段
         if ( KANUtil.filterEmpty( listHeaderVO.getTableId(), "0" ) != null )
         {
            columns.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( listHeaderVO.getTableId() ).getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ), true ) );

            // 添加不关联选项
            columns.add( 0, new MappingVO( "1", KANUtil.getProperty( request.getLocale(), "def.column.no.relation" ) ) );
         }
         // 添加空的下拉选项，即“请选择”
         columns.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         listDetailVO.setColumns( columns );
         listDetailVO.setSubAction( "" );
         listDetailVO.setListHeaderId( listHeaderId );
         listDetailVO.setColumnWidthType( "1" );
         listDetailVO.setFontSize( "13" );
         listDetailVO.setIsDecoded( ListDetailVO.FALSE );
         listDetailVO.setIsLinked( ListDetailVO.FALSE );
         listDetailVO.setAlign( "1" );
         listDetailVO.setSort( ListDetailVO.TRUE );
         listDetailVO.setDisplay( ListDetailVO.TRUE );
         listDetailVO.setStatus( ListDetailVO.TRUE );

         listDetailVO.reset( null, request );

         columnIsExist( listDetailVO.getColumns(), listHeaderId, listDetailService );

         // ListDetail写入Request对象
         request.setAttribute( "listDetailForm", listDetailVO );

         // 初始化列表中ListDetail的Columns
         for ( Object listDetailVOObject : listDetailHolder.getSource() )
         {
            ( ( ListDetailVO ) listDetailVOObject ).setColumns( columns );
         }
         // 刷新Holder，国际化传值
         refreshHolder( listDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "listDetailHolder", listDetailHolder );

         // 如果是AJAX调用，则直接传值给table JSP页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listListDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listListDetail" );
   }

   private void columnIsExist( final List< MappingVO > columns, final String listHeaderId, final ListDetailService listDetailService ) throws KANException
   {
      if ( columns != null && columns.size() > 0 )
      {
         ListDetailVO listDetailVO = null;
         for ( MappingVO column : columns )
         {
            listDetailVO = new ListDetailVO();
            listDetailVO.setListHeaderId( listHeaderId );
            listDetailVO.setColumnId( column.getMappingId() );

            final List< Object > objects = listDetailService.getListDetailVOsByCondition( listDetailVO );
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
   // Code reviewed by Kevin Jin at 2013-07-08
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            //初始化Service 接口
            final ListDetailService listDetailService = ( ListDetailService ) getService( "listDetailService" );

            // 获得ListHeaderId
            final String listHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "listHeaderId" ), "UTF-8" ) );
            // 获得当前Form
            final ListDetailVO listDetailVO = ( ListDetailVO ) form;
            // 初始化ListDetailVO对象
            listDetailVO.setListHeaderId( listHeaderId );
            listDetailVO.setCreateBy( getUserId( request, response ) );
            listDetailVO.setModifyBy( getUserId( request, response ) );
            listDetailVO.setAccountId( getAccountId( request, response ) );
            listDetailService.insertListDetail( listDetailVO );

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initListHeader", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            insertlog( request, listDetailVO, Operate.ADD, listDetailVO.getListDetailId(), null );
         }

         // 清空Action Form
         ( ( ListDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-08
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
   }

   // Code reviewed by Kevin Jin at 2013-07-08
   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final ListDetailService listDetailService = ( ListDetailService ) getService( "listDetailService" );
         final ListHeaderService listHeaderService = ( ListHeaderService ) getService( "listHeaderService" );

         // 获取主键 - 需解码
         final String listDetailId = KANUtil.decodeString( request.getParameter( "listDetailId" ) );

         // 获取ListDetailVO
         final ListDetailVO listDetailVO = listDetailService.getListDetailVOByListDetailId( listDetailId );

         // 获取ListHeaderVO
         final ListHeaderVO listHeaderVO = listHeaderService.getListHeaderVOByListHeaderId( listDetailVO.getListHeaderId() );

         // 国际化传值
         listDetailVO.reset( null, request );

         // 初始化字段
         final List< MappingVO > columns = new ArrayList< MappingVO >();

         // 加载table字段
         if ( KANUtil.filterEmpty( listHeaderVO.getTableId(), "0" ) != null )
         {
            columns.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTableDTOByTableId( listHeaderVO.getTableId() ).getColumns( request.getLocale().getLanguage(), KANUtil.filterEmpty( getCorpId( request, null ) ), true ) );

            // 添加不关联选项
            columns.add( 1, new MappingVO( "1", KANUtil.getProperty( request.getLocale(), "def.column.no.relation" ) ) );
         }
         // 添加空的下拉选项，即“请选择”
         columns.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         listDetailVO.setColumns( columns );

         // 设置SubAction
         listDetailVO.setSubAction( VIEW_OBJECT );

         // 传入request对象
         request.setAttribute( "listDetailForm", listDetailVO );
         request.setAttribute( "listHeaderForm", listHeaderVO );

         // AJAX调用跳转FORM页面
         return mapping.findForward( "manageListDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-02
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ListDetailService listDetailService = ( ListDetailService ) getService( "listDetailService" );

            // 获取主键 - 需解码
            final String listDetailId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "listDetailId" ), "UTF-8" ) );
            // 获得ListDetailVO对象
            final ListDetailVO listDetailVO = listDetailService.getListDetailVOByListDetailId( listDetailId );
            // 装载界面传值
            listDetailVO.update( ( ListDetailVO ) form );
            // 获取登录用户
            listDetailVO.setModifyBy( getUserId( request, response ) );
            // 调用修改接口
            listDetailService.updateListDetail( listDetailVO );

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initListHeader", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            insertlog( request, listDetailVO, Operate.MODIFY, listDetailVO.getListDetailId(), null );
         }

         // 清空Form
         ( ( ListDetailVO ) form ).reset();
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
   // Code reviewed by Kevin Jin at 2013-07-02
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final ListDetailService listDetailService = ( ListDetailService ) getService( "listDetailService" );

         // 获得当前form
         ListDetailVO listDetailVO = ( ListDetailVO ) form;
         // 存在选中的ID
         if ( listDetailVO.getSelectedIds() != null && !listDetailVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : listDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               final ListDetailVO tempListDetailVO = listDetailService.getListDetailVOByListDetailId( selectedId );
               tempListDetailVO.setModifyBy( getUserId( request, response ) );
               tempListDetailVO.setModifyDate( new Date() );
               // 调用删除接口
               listDetailService.deleteListDetail( tempListDetailVO );
            }

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initListHeader", getAccountId( request, response ) );

            insertlog( request, listDetailVO, Operate.DELETE, null, listDetailVO.getSelectedIds() );
         }

         // 清除Selected IDs和子Action
         listDetailVO.setSelectedIds( "" );
         listDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 快速设置ListDetailVO的columnIndex
   /* Add by siuvan @2014-7-24 */
   public void quick_column_index( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final ListDetailService listDetailService = ( ListDetailService ) getService( "listDetailService" );
         // 获取新的列表顺序
         final String newColumnIndex = request.getParameter( "newColumnIndex" );

         // 如果存在社保单这样的特殊列表
         int addIndex = 0;

         if ( KANUtil.filterEmpty( newColumnIndex ) != null )
         {
            final String[] newIndexArray = newColumnIndex.split( "," );
            if ( newIndexArray != null && newIndexArray.length > 0 )
            {
               for ( String s : newIndexArray )
               {
                  if ( s.split( "_" ).length == 2 )
                  {
                     String listDetailId = s.split( "_" )[ 0 ];
                     String columnIndex = s.split( "_" )[ 1 ];
                     final ListDetailVO listDetailVO = listDetailService.getListDetailVOByListDetailId( listDetailId );
                     if ( listDetailVO != null )
                     {
                        listDetailVO.setColumnIndex( listDetailVO.isSBItem() ? columnIndex : String.valueOf( Integer.valueOf( columnIndex ) + addIndex ) );
                        listDetailVO.setModifyBy( getUserId( request, null ) );
                        listDetailVO.setModifyDate( new Date() );

                        listDetailService.updateListDetail( listDetailVO );

                        // 如果是社保科目，需同时修改对应“个人”的columnIndex
                        if ( listDetailVO.isSBItem() )
                        {
                           addIndex = 20;

                           final ListDetailVO searchListDetailVO = new ListDetailVO();
                           searchListDetailVO.setListHeaderId( listDetailVO.getListHeaderId() );
                           searchListDetailVO.setPropertyName( listDetailVO.getPropertyName().replace( "c", "p" ) );
                           searchListDetailVO.setStatus( BaseVO.TRUE );

                           final List< Object > listDetailVOs = listDetailService.getListDetailVOsByCondition( searchListDetailVO );
                           if ( listDetailVOs != null && listDetailVOs.size() > 0 )
                           {
                              for ( Object vo : listDetailVOs )
                              {
                                 ( ( ListDetailVO ) vo ).setColumnIndex( String.valueOf( Integer.valueOf( columnIndex ) + addIndex ) );
                                 ( ( ListDetailVO ) vo ).setModifyBy( getUserId( request, null ) );
                                 ( ( ListDetailVO ) vo ).setModifyDate( new Date() );

                                 listDetailService.updateListDetail( ( ( ListDetailVO ) vo ) );
                              }
                           }
                        }
                     }
                  }
               }
            }

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initListHeader", getAccountId( request, response ) );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
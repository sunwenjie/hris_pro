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
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.domain.define.TableDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ListHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.renders.util.ListRender;

public class ListHeaderAction extends BaseAction
{
   public static String accessAction = "HRO_DEFINE_LIST";

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
         final ListHeaderVO listHeaderVO = ( ListHeaderVO ) form;
         // 处理subAction
         dealSubAction( listHeaderVO, mapping, form, request, response );
         // 初始化Service接口
         final ListHeaderService listHeaderService = ( ListHeaderService ) getService( "listHeaderService" );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder listHeaderHolder = new PagedListHolder();
         // 传入当前页
         listHeaderHolder.setPage( page );
         // 传入当前值对象
         listHeaderHolder.setObject( listHeaderVO );
         // 设置页面记录条数
         listHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         listHeaderService.getListHeaderVOsByCondition( listHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( listHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "listHeaderHolder", listHeaderHolder );
         // Ajax调用，直接传值给table jsp页面
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listListHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listListHeader" );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-08
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 找到当前AccountId的所有TableDTO
      final List< TableDTO > tableDTOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).TABLE_DTO;

      // 和系统定义的Table比较，如果Table已创建过List，则移除。
      if ( ( ( ListHeaderVO ) form ).getTables() != null && ( ( ListHeaderVO ) form ).getTables().size() > 0 && tableDTOs != null && tableDTOs.size() > 0 )
      {
         for ( int i = ( ( ListHeaderVO ) form ).getTables().size() - 1; i > 0; i-- )
         {
            for ( TableDTO tableDTO : tableDTOs )
            {
               // 初始化ListDTO
               final ListDTO listDTO = tableDTO.getListDTO( BaseAction.getAccountId( request, null ), BaseAction.getCorpId( request, null ) );

               if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getTableId() != null
                     && ( ( ListHeaderVO ) form ).getTables().get( i ).getMappingId().equals( listDTO.getListHeaderVO().getTableId() ) )
               {
                  if ( !"118".equals( listDTO.getListHeaderVO().getTableId() ) )
                  {
                     ( ( ListHeaderVO ) form ).getTables().remove( i );
                  }

                  break;
               }
            }
         }
      }

      final List< MappingVO > parents = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getLists( getLocale( request ).getLanguage(), true, getCorpId( request, null ) );
      if ( parents != null )
      {
         parents.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
      }

      ( ( ListHeaderVO ) form ).setParents( parents );
      // 设置初始化字段
      ( ( ListHeaderVO ) form ).setStatus( ListHeaderVO.TRUE );
      ( ( ListHeaderVO ) form ).setIsSearchFirst( ListHeaderVO.FALSE );
      ( ( ListHeaderVO ) form ).setUsePagination( "on" );
      ( ( ListHeaderVO ) form ).setPageSize( String.valueOf( this.listPageSize ) );
      ( ( ListHeaderVO ) form ).setLoadPages( String.valueOf( this.loadPages ) );
      ( ( ListHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageListHeader" );
   }

   @Override
   // Code reviewed by Kevin Jin at 2013-07-08
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化 ListDetailVO
         final ListDetailVO listDetailVO = new ListDetailVO();

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final ListHeaderService listHeaderService = ( ListHeaderService ) getService( "listHeaderService" );

            // 获得当前FORM
            final ListHeaderVO listHeaderVO = ( ListHeaderVO ) form;

            // Checkbox处理
            if ( listHeaderVO.getUsePagination() != null && listHeaderVO.getUsePagination().equalsIgnoreCase( "on" ) )
            {
               listHeaderVO.setUsePagination( ListHeaderVO.TRUE );
            }
            else
            {
               listHeaderVO.setUsePagination( ListHeaderVO.FALSE );
            }

            if ( listHeaderVO.getUseJavaObject() != null && listHeaderVO.getUseJavaObject().equalsIgnoreCase( "on" ) )
            {
               listHeaderVO.setUseJavaObject( ListHeaderVO.TRUE );
            }
            else
            {
               listHeaderVO.setUseJavaObject( ListHeaderVO.FALSE );
            }

            // 获取登录用户及账户
            listHeaderVO.setCreateBy( getUserId( request, response ) );
            listHeaderVO.setModifyBy( getUserId( request, response ) );
            listHeaderVO.setAccountId( getAccountId( request, response ) );
            listHeaderService.insertListHeader( listHeaderVO );

            listDetailVO.setListHeaderId( listHeaderVO.getListHeaderId() );

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initListHeader", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, listHeaderVO, Operate.ADD, listHeaderVO.getListHeaderId(), null );
         }
         else
         {
            // 清空form
            ( ( ListHeaderVO ) form ).reset();

            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return new ListDetailAction().list_object( mapping, listDetailVO, request, response );
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
   // Code reviewed by Kevin Jin at 2013-07-08
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final ListHeaderService listHeaderService = ( ListHeaderService ) getService( "listHeaderService" );

            // 获得主键
            final String listHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "listHeaderId" ), "UTF-8" ) );
            // 获得ListHeaderVO对象
            final ListHeaderVO listHeaderVO = listHeaderService.getListHeaderVOByListHeaderId( listHeaderId );

            // 装载界面传值
            listHeaderVO.update( ( ListHeaderVO ) form );
            // Checkbox处理
            if ( listHeaderVO.getUsePagination() != null && listHeaderVO.getUsePagination().equalsIgnoreCase( "on" ) )
            {
               listHeaderVO.setUsePagination( ListHeaderVO.TRUE );
            }
            else
            {
               listHeaderVO.setUsePagination( ListHeaderVO.FALSE );
            }

            if ( listHeaderVO.getUseJavaObject() != null && listHeaderVO.getUseJavaObject().equalsIgnoreCase( "on" ) )
            {
               listHeaderVO.setUseJavaObject( ListHeaderVO.TRUE );
            }
            else
            {
               listHeaderVO.setUseJavaObject( ListHeaderVO.FALSE );
            }

            // 获取登录用户
            listHeaderVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            listHeaderService.updateListHeader( listHeaderVO );

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initListHeader", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            insertlog( request, listHeaderVO, Operate.MODIFY, listHeaderVO.getListHeaderId(), null );
         }

         // 清空Form
         ( ( ListHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return new ListDetailAction().list_object( mapping, new ListDetailVO(), request, response );
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
         final ListHeaderService listHeaderService = ( ListHeaderService ) getService( "listHeaderService" );

         // 获得Action Form
         ListHeaderVO listHeaderVO = ( ListHeaderVO ) form;

         // 存在选中的ID
         if ( listHeaderVO.getSelectedIds() != null && !listHeaderVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : listHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               final ListHeaderVO tempListHeaderVO = listHeaderService.getListHeaderVOByListHeaderId( selectedId );
               // 调用删除接口
               tempListHeaderVO.setModifyBy( getUserId( request, response ) );
               tempListHeaderVO.setModifyDate( new Date() );
               // 调用删除接口
               listHeaderService.deleteListHeader( tempListHeaderVO );
            }

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initListHeader", getAccountId( request, response ) );

            insertlog( request, listHeaderVO, Operate.DELETE, null, listHeaderVO.getListHeaderId() );
         }

         // 清除Selected IDs和子Action
         listHeaderVO.setSelectedIds( "" );
         listHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public void load_popup_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取listHeaderId
         final String listHeaderId = request.getParameter( "listHeaderId" );
         // 获取ListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getListDTOByListHeaderId( listHeaderId );

         // 初始化StringBuffer
         final StringBuffer rs = new StringBuffer();

         if ( listDTO != null )
         {
            rs.append( ListRender.generateQuickColumnIndexPopup( request, listHeaderId, "listHeaderAction.do?proc=list_object" ) );
         }

         // Send to client
         out.println( rs.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

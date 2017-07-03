package com.kan.base.web.actions.define;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.ColumnGroupVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.define.ColumnGroupService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ColumnGroupAction extends BaseAction
{

   public static String accessAction = "HRO_DEFINE_COLUMNGROUP";

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
         final ColumnGroupVO columnGroupVO = ( ColumnGroupVO ) form;

         dealSubAction( columnGroupVO, mapping, form, request, response );

         // 初始化Service接口
         final ColumnGroupService columnGroupService = ( ColumnGroupService ) getService( "columnGroupService" );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder columnGroupHolder = new PagedListHolder();
         // 传入当前页
         columnGroupHolder.setPage( page );
         // 传入当前值对象
         columnGroupHolder.setObject( columnGroupVO );
         // 设置页面记录条数
         columnGroupHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         columnGroupVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         columnGroupService.getColumnGroupVOsByCondition( columnGroupHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( columnGroupHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "columnGroupHolder", columnGroupHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回Table JSP
            return mapping.findForward( "listColumnGroupTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listColumnGroup" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( ColumnGroupVO ) form ).setUseName( ColumnGroupVO.FALSE );
      ( ( ColumnGroupVO ) form ).setUseBorder( ColumnGroupVO.FALSE );
      ( ( ColumnGroupVO ) form ).setUsePadding( ColumnGroupVO.FALSE );
      ( ( ColumnGroupVO ) form ).setUseMargin( ColumnGroupVO.FALSE );
      ( ( ColumnGroupVO ) form ).setIsFlexable( ColumnGroupVO.FALSE );
      ( ( ColumnGroupVO ) form ).setIsDisplayed( ColumnGroupVO.TRUE );
      ( ( ColumnGroupVO ) form ).setStatus( ColumnGroupVO.TRUE );
      ( ( ColumnGroupVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageColumnGroup" );
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
            final ColumnGroupService columnGroupService = ( ColumnGroupService ) getService( "columnGroupService" );

            // 获得当前FORM
            final ColumnGroupVO columnGroupVO = ( ColumnGroupVO ) form;
            columnGroupVO.setAccountId( getAccountId( request, response ) );
            columnGroupVO.setCreateBy( getUserId( request, response ) );
            columnGroupVO.setModifyBy( getUserId( request, response ) );
            columnGroupService.insertColumnGroup( columnGroupVO );

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initColumnGroup", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, columnGroupVO, Operate.ADD, columnGroupVO.getGroupId(), null );
         }
         else
         {
            // 清空form
            ( ( ColumnGroupVO ) form ).reset();
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
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
         final ColumnGroupService columnGroupService = ( ColumnGroupService ) getService( "columnGroupService" );
         // 主键获取需解码
         String groupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( KANUtil.filterEmpty( groupId ) == null )
         {
            groupId = ( ( ColumnGroupVO ) form ).getGroupId();
         }

         // 获得    ColumnGroupVO对象
         final ColumnGroupVO columnGroupVO = columnGroupService.getColumnGroupVOByGroupId( groupId );
         // 区分Add和update
         columnGroupVO.setSubAction( "viewObject" );
         columnGroupVO.reset( null, request );

         // 将ColumnGroupVO传入request对象
         request.setAttribute( "columnGroupForm", columnGroupVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageColumnGroup" );
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
            final ColumnGroupService columnGroupService = ( ColumnGroupService ) getService( "columnGroupService" );

            // 获取主键需解码 
            final String groupId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // 获取ColumnGroupVO
            final ColumnGroupVO columnGroupVO = columnGroupService.getColumnGroupVOByGroupId( groupId );
            // 装载界面传值
            columnGroupVO.update( ( ColumnGroupVO ) form );
            // 获取登录用户
            columnGroupVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            columnGroupService.updateColumnGroup( columnGroupVO );

            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initColumnGroup", getAccountId( request, response ) );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, columnGroupVO, Operate.MODIFY, columnGroupVO.getGroupId(), null );
         }

         // 清空Form
         ( ( ColumnGroupVO ) form ).reset();
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
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final ColumnGroupService columnGroupService = ( ColumnGroupService ) getService( "columnGroupService" );

         // 获得Action Form
         final ColumnGroupVO columnGroupVO = ( ColumnGroupVO ) form;

         // 存在选中的ID
         if ( columnGroupVO.getSelectedIds() != null && !columnGroupVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : columnGroupVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               columnGroupVO.setGroupId( selectedId );
               columnGroupVO.setModifyBy( getUserId( request, response ) );
               columnGroupVO.setAccountId( getAccountId( request, response ) );
               // 标记删除
               columnGroupService.deleteColumnGroup( columnGroupVO );
            }

            insertlog( request, columnGroupVO, Operate.DELETE, null, columnGroupVO.getSelectedIds() );
            // 初始化常量持久对象
            constantsInit( "initTable", getAccountId( request, response ) );
            constantsInit( "initColumnGroup", getAccountId( request, response ) );
         }

         // 清除Selected IDs和子Action
         columnGroupVO.setSelectedIds( "" );
         columnGroupVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}

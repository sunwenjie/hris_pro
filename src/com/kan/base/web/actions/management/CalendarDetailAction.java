package com.kan.base.web.actions.management;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.CalendarDetailVO;
import com.kan.base.domain.management.CalendarHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.CalendarDetailService;
import com.kan.base.service.inf.management.CalendarHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class CalendarDetailAction extends BaseAction
{
   public static final String accessAction = "HRO_ATTENDANCE_CALENDAR";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 获得当前页
         final String page = request.getParameter( "page" );

         // 如果不是Ajax调用，设置Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // 获得Action Form
         final CalendarDetailVO calendarDetailVO = ( CalendarDetailVO ) form;

         // 处理SubAction
         dealSubAction( calendarDetailVO, mapping, form, request, response );

         // 初始化Service接口
         final CalendarDetailService calendarDetailService = ( CalendarDetailService ) getService( "calendarDetailService" );
         final CalendarHeaderService calendarHeaderService = ( CalendarHeaderService ) getService( "calendarHeaderService" );

         // 获得主表主键
         String headerId = request.getParameter( "headerId" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = calendarDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // 获得主表对象
         final CalendarHeaderVO calendarHeaderVO = calendarHeaderService.getCalendarHeaderVOByHeaderId( headerId );
         // 刷新国际化
         calendarHeaderVO.reset( null, request );
         // 区分修改添加
         calendarHeaderVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "calendarHeaderForm", calendarHeaderVO );
         calendarDetailVO.setHeaderId( headerId );

         // 如果没有指定排序则默认按 日期排序
         if ( calendarDetailVO.getSortColumn() == null || calendarDetailVO.getSortColumn().isEmpty() )
         {
            calendarDetailVO.setSortColumn( "day" );
            calendarDetailVO.setSortOrder( "desc" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder calendarDetailHolder = new PagedListHolder();
         // 传入当前页
         calendarDetailHolder.setPage( page );
         // 传入当前值对象
         calendarDetailHolder.setObject( calendarDetailVO );
         // 设置页面记录条数
         calendarDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         calendarDetailService.getCalendarDetailVOsByCondition( calendarDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( calendarDetailHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "calendarDetailHolder", calendarDetailHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // Ajax Table调用，直接传回CalendarDetail JSP
            return mapping.findForward( "listCalendarDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listCalendarDetail" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // No Use
      return null;
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
            final CalendarDetailService calendarDetailService = ( CalendarDetailService ) getService( "calendarDetailService" );
            // 获得当前FORM
            final CalendarDetailVO calendarDetailVO = ( CalendarDetailVO ) form;
            // 获得主表ID
            final String headerId = KANUtil.decodeString( request.getParameter( "headerId" ) );
            calendarDetailVO.setHeaderId( headerId );
            calendarDetailVO.setCreateBy( getUserId( request, response ) );
            calendarDetailVO.setModifyBy( getUserId( request, response ) );
            calendarDetailVO.setAccountId( getAccountId( request, response ) );
            calendarDetailService.insertCalendarDetail( calendarDetailVO );

            // 重新加载常量中的CalendarHeader
            constantsInit( "initCalendarHeader", getAccountId( request, response ) );

            // 返回保存成功的标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );
            insertlog( request, calendarDetailVO, Operate.ADD, calendarDetailVO.getDetailId(), null );
         }

         // 清空Form
         ( ( CalendarDetailVO ) form ).reset();
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
      // No Use
      return null;
   }

   public ActionForward to_objectModify_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化Service接口
         final CalendarDetailService calendarDetailService = ( CalendarDetailService ) getService( "calendarDetailService" );
         final CalendarHeaderService calendarHeaderService = ( CalendarHeaderService ) getService( "calendarHeaderService" );
         // 主键主表ID
         final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
         // 获得CalendarDetailVO对象
         final CalendarDetailVO calendarDetailVO = calendarDetailService.getCalendarDetailVOByDetailId( detailId );
         // 获得CalendarHeaderVO对象
         final CalendarHeaderVO calendarHeaderVO = calendarHeaderService.getCalendarHeaderVOByHeaderId( calendarDetailVO.getHeaderId() );
         // 国际化传值
         calendarDetailVO.reset( null, request );
         // 区分修改添加
         calendarDetailVO.setSubAction( VIEW_OBJECT );
         calendarDetailVO.setStatus( CalendarHeaderVO.TRUE );
         // 传入request对象
         request.setAttribute( "calendarHeaderForm", calendarHeaderVO );
         request.setAttribute( "calendarDetailForm", calendarDetailVO );

         // Ajax Form调用，直接传回Form JSP
         return mapping.findForward( "manageCalendarDetailForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
            // 初始化Service接口
            final CalendarDetailService calendarDetailService = ( CalendarDetailService ) getService( "calendarDetailService" );

            // 主键获取需解码
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            // 获取主键对象
            final CalendarDetailVO calendarDetailVO = calendarDetailService.getCalendarDetailVOByDetailId( detailId );
            // 装载界面传值
            calendarDetailVO.update( ( CalendarDetailVO ) form );
            // 获取登录用户
            calendarDetailVO.setModifyBy( getUserId( request, response ) );
            // 调用修改接口
            calendarDetailService.updateCalendarDetail( calendarDetailVO );

            // 重新加载常量中的CalendarHeader
            constantsInit( "initCalendarHeader", getAccountId( request, response ) );

            // 返回编辑成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
            insertlog( request, calendarDetailVO, Operate.MODIFY, calendarDetailVO.getDetailId(), null );
         }

         // 清空Form
         ( ( CalendarDetailVO ) form ).reset();
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
         final CalendarDetailService calendarDetailService = ( CalendarDetailService ) getService( "calendarDetailService" );

         // 获得当前form
         CalendarDetailVO calendarDetailVO = ( CalendarDetailVO ) form;
         // 存在选中的ID
         if ( calendarDetailVO.getSelectedIds() != null && !calendarDetailVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, calendarDetailVO, Operate.DELETE, null, calendarDetailVO.getSelectedIds() );
            // 分割
            for ( String selectedId : calendarDetailVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               calendarDetailVO = calendarDetailService.getCalendarDetailVOByDetailId( selectedId );
               calendarDetailVO.setModifyBy( getUserId( request, response ) );
               calendarDetailVO.setModifyDate( new Date() );
               // 调用删除接口
               calendarDetailService.deleteCalendarDetail( calendarDetailVO );
            }
         }
         // 清除Selected IDs和子Action
         calendarDetailVO.setSelectedIds( "" );
         calendarDetailVO.setSubAction( "" );

         // 重新加载常量中的CalendarHeader
         constantsInit( "initCalendarHeader", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}
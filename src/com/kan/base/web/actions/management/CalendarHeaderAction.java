package com.kan.base.web.actions.management;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.management.CalendarDTO;
import com.kan.base.domain.management.CalendarDetailVO;
import com.kan.base.domain.management.CalendarHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.CalendarHeaderService;
import com.kan.base.util.ChineseCalendar;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class CalendarHeaderAction extends BaseAction
{
   public static final String accessAction = "HRO_ATTENDANCE_CALENDAR";

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
         final CalendarHeaderService calendarHeaderService = ( CalendarHeaderService ) getService( "calendarHeaderService" );
         // 获得Action Form
         final CalendarHeaderVO calendarHeaderVO = ( CalendarHeaderVO ) form;

         // 调用删除方法
         if ( calendarHeaderVO.getSubAction() != null && calendarHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( calendarHeaderVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder calendarHeaderHolder = new PagedListHolder();
         // 传入当前页
         calendarHeaderHolder.setPage( page );
         // 传入当前值对象
         calendarHeaderHolder.setObject( calendarHeaderVO );
         // 设置页面记录条数
         calendarHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         calendarHeaderService.getCalendarHeaderVOsByCondition( calendarHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( calendarHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "calendarHeaderHolder", calendarHeaderHolder );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "accountId", getAccountId( request, null ) );
            // Ajax Table调用，直接传回CalendarHeader JSP
            return mapping.findForward( "listCalendarHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listCalendarHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加需设定一个记号，防止重复提交
      this.saveToken( request );

      // 设置Sub Action
      ( ( CalendarHeaderVO ) form ).setStatus( BaseVO.TRUE );
      ( ( CalendarHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面  
      return mapping.findForward( "manageCalendarHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化CalendarDetailVO
         final CalendarDetailVO calendarDetailVO = new CalendarDetailVO();

         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final CalendarHeaderService calendarHeaderService = ( CalendarHeaderService ) getService( "calendarHeaderService" );
            // 获得当前FORM
            final CalendarHeaderVO calendarHeaderVO = ( CalendarHeaderVO ) form;
            calendarHeaderVO.setCreateBy( getUserId( request, response ) );
            calendarHeaderVO.setModifyBy( getUserId( request, response ) );
            calendarHeaderVO.setAccountId( getAccountId( request, response ) );
            calendarHeaderService.insertCalendarHeader( calendarHeaderVO );

            calendarDetailVO.setHeaderId( calendarHeaderVO.getHeaderId() );

            // 重新加载到缓存中
            constantsInit( "initCalendarHeader", getAccountId( request, response ) );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
            insertlog( request, calendarHeaderVO, Operate.ADD, calendarHeaderVO.getHeaderId(), null );
         }
         else
         {
            // 清空Action Form
            ( ( CalendarHeaderVO ) form ).reset();
            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return new CalendarDetailAction().list_object( mapping, calendarDetailVO, request, response );
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
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final CalendarHeaderService calendarHeaderService = ( CalendarHeaderService ) getService( "calendarHeaderService" );

            // 主键获取需解码
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "headerId" ), "UTF-8" ) );
            // 获取CalendarHeaderVO对象
            final CalendarHeaderVO calendarHeaderVO = calendarHeaderService.getCalendarHeaderVOByHeaderId( headerId );
            // 装载界面传值
            calendarHeaderVO.update( ( CalendarHeaderVO ) form );
            // 获取登录用户
            calendarHeaderVO.setModifyBy( getUserId( request, response ) );
            // 调用修改方法
            calendarHeaderService.updateCalendarHeader( calendarHeaderVO );

            // 重新加载到缓存中
            constantsInit( "initCalendarHeader", getAccountId( request, response ) );

            // 返回编辑成功的标记
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
            insertlog( request, calendarHeaderVO, Operate.MODIFY, calendarHeaderVO.getHeaderId(), null );
         }

         // 清空Action Form
         ( ( CalendarHeaderVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return new CalendarDetailAction().list_object( mapping, new CalendarDetailVO(), request, response );
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
         final CalendarHeaderService calendarHeaderService = ( CalendarHeaderService ) getService( "calendarHeaderService" );

         // 获得Action Form
         CalendarHeaderVO calendarHeaderVO = ( CalendarHeaderVO ) form;
         // 存在选中的ID
         if ( KANUtil.filterEmpty( calendarHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, calendarHeaderVO, Operate.DELETE, null, calendarHeaderVO.getSelectedIds() );
            // 分割
            for ( String selectedId : calendarHeaderVO.getSelectedIds().split( "," ) )
            {
               // 获取需要删除的对象
               calendarHeaderVO = calendarHeaderService.getCalendarHeaderVOByHeaderId( selectedId );
               calendarHeaderVO.setHeaderId( selectedId );
               calendarHeaderVO.setModifyBy( getUserId( request, response ) );
               calendarHeaderVO.setModifyDate( new Date() );
               calendarHeaderService.deleteCalendarHeader( calendarHeaderVO );
            }
         }
         // 清除Selected IDs和子Action
         calendarHeaderVO.setSelectedIds( "" );
         calendarHeaderVO.setSubAction( "" );

         // 重新加载到缓存中
         constantsInit( "initCalendarHeader", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward generateCalendar_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {

      // 获取日历年份
      final String year = request.getParameter( "year" );

      // 获取日历月份
      final String month = request.getParameter( "month" );

      // 初始化CalendarDTO 列表
      final List< CalendarDTO > calendarDTOs = new ArrayList< CalendarDTO >();

      // 当前日历第一天
      final Calendar tempCalendar = Calendar.getInstance();
      tempCalendar.set( Integer.valueOf( year ), Integer.valueOf( month ) - 1, 1 );

      // 当前日历起始
      final Calendar startCalendar = Calendar.getInstance();
      startCalendar.set( Integer.valueOf( year ), Integer.valueOf( month ) - 1, 1 );

      // 当前日历截止
      final Calendar endCalendar = Calendar.getInstance();
      endCalendar.set( Integer.valueOf( year ), Integer.valueOf( month ) - 1, tempCalendar.getActualMaximum( Calendar.DAY_OF_MONTH ) );

      // 初始化CalendarDTO
      CalendarDTO calendarDTO = new CalendarDTO();

      // 迭代装载CalendarDTO
      while ( tempCalendar.getTimeInMillis() <= endCalendar.getTimeInMillis() )
      {
         ChineseCalendar chineseCalendar = new ChineseCalendar( tempCalendar );
         // 初始化CalendarDetailVO
         final CalendarDetailVO calendarDetailVO = new CalendarDetailVO();
         calendarDetailVO.setMonthType( "2" );
         calendarDetailVO.setDetailId( String.valueOf( tempCalendar.get( Calendar.DATE ) ) );
         calendarDetailVO.setWeek( String.valueOf( tempCalendar.get( Calendar.DAY_OF_WEEK ) - 1 ) );
         calendarDetailVO.setDayType( ( ( calendarDetailVO.getWeek().equals( "6" ) || calendarDetailVO.getWeek().equals( "0" ) ) ? "2" : "1" ) );
         calendarDetailVO.setNameZH( chineseCalendar.getDay() );

         final CalendarDetailVO targetCalendarDetailVO = getCalendarDetailVO( request, KANUtil.formatDate( tempCalendar.getTime(), "yyyy-MM-dd" ) );

         if ( targetCalendarDetailVO != null )
         {
            calendarDetailVO.setDayType( targetCalendarDetailVO.getDayType() );
            calendarDetailVO.setNameZH( targetCalendarDetailVO.getNameZH() );
         }

         calendarDTO.getCalendarDetailVOs().add( calendarDetailVO );
         // 逢周末换行
         if ( tempCalendar.get( Calendar.DAY_OF_WEEK ) - 1 == 0 )
         {
            calendarDTOs.add( calendarDTO );
            calendarDTO = new CalendarDTO();
         }

         tempCalendar.add( Calendar.DATE, 1 );
      }

      if ( calendarDTO.getCalendarDetailVOs().size() > 0 )
      {
         calendarDTOs.add( calendarDTO );
      }

      if ( calendarDTOs.size() > 0 )
      {
         final List< CalendarDetailVO > firstWeek = calendarDTOs.get( 0 ).getCalendarDetailVOs();
         final List< CalendarDetailVO > lastWeek = calendarDTOs.get( calendarDTOs.size() - 1 ).getCalendarDetailVOs();

         int idx = 0;
         while ( firstWeek.size() < 7 )
         {
            final CalendarDetailVO calendarDetailVO = new CalendarDetailVO();
            startCalendar.add( Calendar.DAY_OF_MONTH, -( 7 - firstWeek.size() ) );
            calendarDetailVO.setMonthType( "1" );
            calendarDetailVO.setDetailId( String.valueOf( startCalendar.get( Calendar.DATE ) ) );
            calendarDetailVO.setWeek( String.valueOf( startCalendar.get( Calendar.DAY_OF_WEEK ) - 1 ) );
            calendarDetailVO.setDayType( ( ( calendarDetailVO.getWeek().equals( "6" ) || calendarDetailVO.getWeek().equals( "0" ) ) ? "2" : "1" ) );
            ChineseCalendar chineseCalendar = new ChineseCalendar( startCalendar );
            calendarDetailVO.setNameZH( chineseCalendar.getDay() );
            firstWeek.add( idx, calendarDetailVO );
            startCalendar.set( Integer.valueOf( year ), Integer.valueOf( month ) - 1, 1 );
            idx++;
         }

         while ( lastWeek.size() < 7 )
         {
            final CalendarDetailVO calendarDetailVO = new CalendarDetailVO();
            endCalendar.add( Calendar.DAY_OF_MONTH, 1 );
            calendarDetailVO.setMonthType( "3" );
            calendarDetailVO.setDetailId( String.valueOf( endCalendar.get( Calendar.DATE ) ) );
            calendarDetailVO.setWeek( String.valueOf( endCalendar.get( Calendar.DAY_OF_WEEK ) - 1 ) );
            calendarDetailVO.setDayType( ( ( calendarDetailVO.getWeek().equals( "6" ) || calendarDetailVO.getWeek().equals( "0" ) ) ? "2" : "1" ) );
            ChineseCalendar chineseCalendar = new ChineseCalendar( endCalendar );
            calendarDetailVO.setNameZH( chineseCalendar.getDay() );
            lastWeek.add( calendarDetailVO );
         }
      }

      request.setAttribute( "calendarDTOs", calendarDTOs );

      return mapping.findForward( "calendarTable" );
   }

   public CalendarDetailVO getCalendarDetailVO( final HttpServletRequest request, final String day ) throws KANException
   {
      final String headerId = KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) );

      final CalendarDTO calendarDTO = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getCalendarDTOByHeaderId( headerId );

      if ( calendarDTO != null && calendarDTO.getCalendarDetailVOs() != null && calendarDTO.getCalendarDetailVOs().size() > 0 )
      {
         for ( CalendarDetailVO calendarDetailVO : calendarDTO.getCalendarDetailVOs() )
         {
            if ( KANUtil.filterEmpty( day ) != null && KANUtil.filterEmpty( calendarDetailVO.getDay() ) != null && calendarDetailVO.getDay().equals( day ) )
            {
               return calendarDetailVO;
            }
         }
      }

      return null;
   }

   public static void get_date_detail( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取年、月、日
         final String year = request.getParameter( "year" );
         final String month = request.getParameter( "month" );
         final String day = request.getParameter( "day" );

         // 创建一个日期
         final Date date = KANUtil.createDate( year + "-" + month + "-" + day );

         // 初始化ChineseCalendar
         final ChineseCalendar chineseCalendar = new ChineseCalendar( date );

         // 初始化
         final Calendar startCalendar = Calendar.getInstance();
         startCalendar.setTime( date );

         int week = startCalendar.get( Calendar.DAY_OF_WEEK ) - 1;

         // 初始化StringBuffer
         final StringBuffer sb = new StringBuffer( "&nbsp;" );
         sb.append( "&nbsp;农历" + chineseCalendar.toString() + "&nbsp;&nbsp;" + "星期" + ( ( week - 1 ) == -1 ? "日" : ChineseCalendar.chineseNumber[ week - 1 ] ) );

         // Send to client
         out.println( sb.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   public static void main( String[] args )
   {
      // 当前日历截止
      final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
      final Calendar endCalendar = Calendar.getInstance();
      endCalendar.setTime( new Date() );

      endCalendar.add( Calendar.DAY_OF_MONTH, -1 );
      System.out.println( sdf.format( endCalendar.getTime() ) );
   }
}

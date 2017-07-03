package com.kan.hro.wap.actions.biz.attendance;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.attendance.TimesheetDetailVO;
import com.kan.hro.domain.biz.attendance.TimesheetHeaderVO;
import com.kan.hro.domain.biz.employee.EmployeeContractSalaryVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetDetailService;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractSalaryService;

public class TimesheetHeaderAction_Wap extends BaseAction
{

   // 当前Action对应的Access Action
   private static String accessAction = "HRO_BIZ_TIMESHEET_HEADER";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 初始化Service接口
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );
         // 获得Action Form
         final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;
         // 需要设置当前用户AccountId
         timesheetHeaderVO.setAccountId( getAccountId( request, response ) );
         // 获得SubAction
         final String subAction = getSubAction( form );
         // 处理SubAction
         dealSubAction( timesheetHeaderVO, mapping, form, request, response );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder timesheetHeaderPagedListHolder = new PagedListHolder();

         // 默认月份降序
         if ( !( new Boolean( request.getParameter( "ajax" ) ) ) )
         {
            timesheetHeaderVO.setSortOrder( "desc" );
            timesheetHeaderVO.setSortColumn( "a.monthly" );
         }

         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 传入当前页
            timesheetHeaderPagedListHolder.setPage( page );
            // 传入当前值对象
            timesheetHeaderPagedListHolder.setObject( timesheetHeaderVO );
            // 设置页面记录条数
            timesheetHeaderPagedListHolder.setPageSize( getPageSize( request, accessAction ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            timesheetHeaderService.getTimesheetHeaderVOsByCondition( timesheetHeaderPagedListHolder, isPaged( request, accessAction ) );
            // 刷新Holder，国际化传值
            refreshHolder( timesheetHeaderPagedListHolder, request );
         }
         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", timesheetHeaderPagedListHolder );

         // 处理Return
         return dealReturn( accessAction, "listTimesheetHeader", mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 处理Return
         return dealReturn( null, "generateTimesheetHeader", mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

            // 获取Form
            final TimesheetHeaderVO timesheetHeaderVO = ( TimesheetHeaderVO ) form;
            timesheetHeaderVO.setCreateBy( getUserId( request, null ) );
            timesheetHeaderVO.setModifyBy( getUserId( request, null ) );

            // 生成Timesheet
            timesheetHeaderService.generateTimesheet( timesheetHeaderVO );
         }
         else
         {
            // 返回失败标记
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 处理Return
      return list_object( mapping, form, request, response );
   }

   /**
    * 根据 雇员的ID ，年月查询出考勤。
    *
    */
   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         this.saveToken( request );
         String headerId = "";
         // 获取条件数据
         final String employeeId = request.getParameter( "employeeId" );
         final String year = request.getParameter( "year" );
         final String month = request.getParameter( "month" );
         final TimesheetHeaderVO timesheetHeaderVOCond = new TimesheetHeaderVO();
         timesheetHeaderVOCond.setEmployeeId( employeeId );
         timesheetHeaderVOCond.setMonthly( year + "/" + month );
         timesheetHeaderVOCond.setAccountId( getAccountId( request, response ) );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder timesheetHeaderPagedListHolder = new PagedListHolder();
         timesheetHeaderPagedListHolder.setObject( timesheetHeaderVOCond );
         // 初始化Service接口
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

         // 获取当前的 考勤
         timesheetHeaderService.getTimesheetHeaderVOsByCondition( timesheetHeaderPagedListHolder, false );

         // 刷新Holder，国际化传值
         refreshHolder( timesheetHeaderPagedListHolder, request );

         // 获取当前考勤对象
         List< Object > timeSheetHeaderVOs = ( List< Object > ) timesheetHeaderPagedListHolder.getSource();
         if ( timeSheetHeaderVOs != null && timeSheetHeaderVOs.size() > 0 )
         {
            // 如果不为空，则获取当前的headerId;
            headerId = ( ( TimesheetHeaderVO ) timeSheetHeaderVOs.get( timeSheetHeaderVOs.size() - 1 ) ).getHeaderId();
         }
         final EmployeeContractSalaryService employeeContractSalaryService = ( EmployeeContractSalaryService ) getService( "employeeContractSalaryService" );

         // 获得TimesheetHeaderVO
         TimesheetHeaderVO timesheetHeaderVO = null;
         boolean byHour = false;
         if ( headerId != null && !"".equals( headerId ) )
         {
            timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( headerId );
            timesheetHeaderVO.setSubAction( VIEW_OBJECT );
            timesheetHeaderVO.reset( null, request );
            // 获取服务协议列表
            final List< Object > employeeContractSalaryVOs = employeeContractSalaryService.getEmployeeContractSalaryVOsByContractId( timesheetHeaderVO.getContractId() );

            // 雇员服务协议>薪酬方案 - 基本工资是否按小时算
            if ( employeeContractSalaryVOs != null && employeeContractSalaryVOs.size() > 0 )
            {
               for ( Object employeeContractSalaryVOObject : employeeContractSalaryVOs )
               {
                  final EmployeeContractSalaryVO employeeContractSalaryVO = ( EmployeeContractSalaryVO ) employeeContractSalaryVOObject;
                  if ( employeeContractSalaryVO.getItemId().equals( "1" ) && employeeContractSalaryVO.getSalaryType().equals( "2" ) )
                  {
                     byHour = true;
                     break;
                  }
               }
            }
         }
         // 传给前端
         response.setHeader( "Content-Type", "application/json;charset=UTF-8" );
         final PrintWriter out = response.getWriter();
         StringBuffer returnJson = new StringBuffer();
         returnJson.append( "{\"success\":\"success\",\"msg\":" );
         returnJson.append( JSONObject.fromObject( timesheetHeaderVO == null ? "{}" : timesheetHeaderVO ).toString() );
         returnJson.append( "," ).append( "\"other\":{" );
         if ( byHour )
         {
            returnJson.append( "\"byHour\":\"true\"" );
         }
         else
         {
            returnJson.append( "\"byHour\":\"false\"" );
         }
         returnJson.append( "}}" );
         out.print( returnJson.toString() );
         out.flush();
         out.close();

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转页面
      return null;
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );
            // 主键获取需解码
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得TimesheetHeaderVO对象
            final TimesheetHeaderVO timesheetHeaderVO = timesheetHeaderService.getTimesheetHeaderVOByHeaderId( headerId );
            timesheetHeaderVO.setTotalWorkHours( ( ( TimesheetHeaderVO ) form ).getTotalWorkHours() );
            timesheetHeaderVO.setWorkHoursArray( ( ( TimesheetHeaderVO ) form ).getWorkHoursArray() );
            timesheetHeaderVO.setBaseArray( ( ( TimesheetHeaderVO ) form ).getBaseArray() );
            // 获取登录用户
            timesheetHeaderVO.setModifyBy( getUserId( request, response ) );
            timesheetHeaderVO.setModifyDate( new Date() );
            // 调用修改方法
            timesheetHeaderService.updateTimesheetHeader( timesheetHeaderVO );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
         // 清空FORM
         ( ( TimesheetHeaderVO ) form ).reset();
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
      // No Use
   }

   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final TimesheetDetailService timesheetDetailService = ( TimesheetDetailService ) getService( "timesheetDetailService" );
         // ID获取需解码
         String headerId = request.getParameter( "headerId" );

         if ( headerId != null && !"".equals( headerId ) )
         {
            final PagedListHolder timesheetDetailPagedListHolder = new PagedListHolder();
            // 初始化考勤详情查询条件
            final TimesheetDetailVO timesheetDetailVO = new TimesheetDetailVO();
            timesheetDetailVO.setHeaderId( headerId );
            timesheetDetailVO.setStatus( BaseVO.TRUE );
            timesheetDetailVO.setSortColumn( ( ( TimesheetHeaderVO ) form ).getSortColumn() );
            timesheetDetailVO.setSortOrder( ( ( TimesheetHeaderVO ) form ).getSortOrder() );
            timesheetDetailPagedListHolder.setObject( timesheetDetailVO );

            timesheetDetailService.getTimesheetDetailVOsByCondition( timesheetDetailPagedListHolder, false );
            // 刷新Holder，国际化传值
            refreshHolder( timesheetDetailPagedListHolder, request );
            // 传给前端
            response.setHeader( "Content-Type", "application/json;charset=UTF-8" );
            final PrintWriter out = response.getWriter();
            StringBuffer returnJson = new StringBuffer();
            returnJson.append( "{\"success\":\"success\",\"msg\":" );
            returnJson.append( JSONArray.fromObject( timesheetDetailPagedListHolder.getSource() == null ? "{}" : timesheetDetailDateFormat( timesheetDetailPagedListHolder ) ).toString() );
            returnJson.append( "," ).append( "\"other\":" );
            returnJson.append( timesheetDetailPagedListHolder.getSource() == null ? ""
                  : JSONArray.fromObject( ( ( TimesheetDetailVO ) timesheetDetailPagedListHolder.getSource().get( 0 ) ).getDayTypies() ) );
            returnJson.append( "}" );
            out.print( returnJson.toString() );
            out.flush();
            out.close();
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return null;
   }

   public static final String[] WEEK = { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

   public static String getWeek( final String date ) throws KANException
   {
      try
      {
         if ( date != null && !"".equals( date ) )
         {
            final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime( sdf.parse( date ) );

            return WEEK[ calendar.get( Calendar.DAY_OF_WEEK ) - 1 ];
         }
      }
      catch ( final Exception e )
      {
         new KANException( e );
      }
      return null;
   }

   public static int getTimeGap( final String time1, final String time2 ) throws KANException
   {
      try
      {
         if ( time1 != null && time2 != null && !time1.equals( "" ) && !time2.equals( "" ) )
         {
            final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
            final Calendar calendar = Calendar.getInstance();
            calendar.setTime( sdf.parse( time1 ) );
            long timeOne = calendar.getTimeInMillis();
            calendar.setTime( sdf.parse( time2 ) );
            long timeTwo = calendar.getTimeInMillis();
            return ( int ) ( ( timeTwo - timeOne ) / ( 1000 * 60 * 60 * 24 ) );
         }
      }
      catch ( final Exception e )
      {
         new KANException( e );
      }
      return 0;
   }

   public static void main( String[] args )
   {
      final Calendar calendar = Calendar.getInstance();
      calendar.add( Calendar.MONTH, +2 );
      System.out.println( calendar.getTime().getTime() );
   }

   public static List< TimesheetDetailLite > timesheetDetailDateFormat( final PagedListHolder timesheetDetailPagedListHolder )
   {
      final List< Object > timeSheetDetailObjects = timesheetDetailPagedListHolder.getSource();
      // List 保存考勤部分列
      final List< TimesheetDetailLite > timesheetDetailLites = new ArrayList< TimesheetDetailLite >();
      // 临时存放考勤
      TimesheetDetailVO timesheetDetailVO = null;
      // 临时存放 考勤部分列
      TimesheetHeaderAction_Wap.TimesheetDetailLite timesheetDetailLite = null;
      for ( Object object : timeSheetDetailObjects )
      {
         timesheetDetailVO = ( TimesheetDetailVO ) object;
         timesheetDetailLite = new TimesheetDetailLite();
         timesheetDetailLite.setDay( timesheetDetailVO.getDay() );
         timesheetDetailLite.setDayType( timesheetDetailVO.getDayType() );
         timesheetDetailLite.setFullHours( timesheetDetailVO.getFullHours() );
         timesheetDetailLite.setWorkHours( timesheetDetailVO.getWorkHours() );
         timesheetDetailLite.setHeaderId( timesheetDetailVO.getHeaderId() );
         timesheetDetailLite.setDetailId( timesheetDetailVO.getDetailId() );
         timesheetDetailLites.add( timesheetDetailLite );
      }
      return timesheetDetailLites;
   }

   /**
    *  这个内部类是用来装考勤的部分列
    */
   public static class TimesheetDetailLite
   {
      private String day;
      private String dayType;
      private String fullHours;
      private String workHours;
      private String headerId;
      private String detailId;

      public TimesheetDetailLite()
      {
         super();
         // TODO Auto-generated constructor stub
      }

      public String getDay()
      {
         return day;
      }

      public void setDay( String day )
      {
         this.day = day;
      }

      public String getDayType()
      {
         return dayType;
      }

      public void setDayType( String dayType )
      {
         this.dayType = dayType;
      }

      public String getFullHours()
      {
         return fullHours;
      }

      public void setFullHours( String fullHours )
      {
         this.fullHours = fullHours;
      }

      public String getWorkHours()
      {
         return workHours;
      }

      public void setWorkHours( String workHours )
      {
         this.workHours = workHours;
      }

      public String getHeaderId()
      {
         return headerId;
      }

      public void setHeaderId( String headerId )
      {
         this.headerId = headerId;
      }

      public String getDetailId()
      {
         return detailId;
      }

      public void setDetailId( String detailId )
      {
         this.detailId = detailId;
      }

   }
}

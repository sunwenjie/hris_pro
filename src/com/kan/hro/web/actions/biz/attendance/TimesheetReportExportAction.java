package com.kan.hro.web.actions.biz.attendance;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.attendance.TimesheetDetailVO;
import com.kan.hro.domain.biz.attendance.TimesheetReportExportVO;
import com.kan.hro.service.inf.biz.attendance.TimesheetHeaderService;

public class TimesheetReportExportAction extends BaseAction
{
   // 当前Action对应的Access Action
   public static final String accessAction = "HRO_BIZ_ATTENDANCE_TIMESHEET_REPORT_EXPORT";

   // 平均出勤天数报表
   public static final String ACCESS_ACITON = "HRO_BIZ_ATTENDANCE_AVG_REPORT";

   @Override
   // 考勤表报表，生成SQL灵活查出所有（包含super的item和accountId的item）科目
   /** Modify by siuvan 2015-01-05 **/
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );
         // 获得Action Form
         final TimesheetReportExportVO timesheetReportExportVO = ( TimesheetReportExportVO ) form;

         // 处理数据权限
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            setDataAuth( request, response, timesheetReportExportVO );
         }

         // 如果没有指定排序则默认按 batchId排序
         if ( timesheetReportExportVO.getSortColumn() == null || timesheetReportExportVO.getSortColumn().isEmpty() )
         {
            timesheetReportExportVO.setSortOrder( "desc" );
            timesheetReportExportVO.setSortColumn( "a.headerId" );
         }

         if ( timesheetReportExportVO.getMonthly() == null || timesheetReportExportVO.getMonthly().isEmpty() )
         {
            timesheetReportExportVO.setMonthly( new SimpleDateFormat( "yyyy/MM" ).format( new Date() ) );
         }
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            timesheetReportExportVO.setClientId( BaseAction.getClientId( request, response ) );
         }

         // 处理subAction
         dealSubAction( timesheetReportExportVO, mapping, form, request, response );

         // 灵活查询请假、加班科目
         final List< MappingVO > leaveItems = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getLeaveItems( request.getLocale().getLanguage(), getCorpId( request, null ) );
         final List< MappingVO > otItems = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getOtItems( request.getLocale().getLanguage(), getCorpId( request, null ) );
         timesheetReportExportVO.setResultJSON( generateSQLString( leaveItems, otItems ) );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // 传入当前页
         pagedListHolder.setPage( page );
         // 传入当前值对象
         pagedListHolder.setObject( timesheetReportExportVO );
         // 设置页面记录条数
         pagedListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         timesheetHeaderService.getTimesheetReportExportVOsByCondition( pagedListHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         String selectedIds = "";
         // 遍历
         for ( Object object : pagedListHolder.getSource() )
         {
            TimesheetReportExportVO timesheetHeader = ( TimesheetReportExportVO ) object;
            selectedIds = selectedIds + timesheetHeader.getHeaderId() + ",";
         }

         List< Object > listTimesheetDatys = new ArrayList< Object >();
         List< Object > listTimesheetDetailVOs = new ArrayList< Object >();
         DateFormat fmt = new SimpleDateFormat( "yyyy-MM-dd" );
         String[] weekDaysName = { KANUtil.getProperty( getLocale( request ), "ckb.sun" ), KANUtil.getProperty( getLocale( request ), "ckb.mon" ),
               KANUtil.getProperty( getLocale( request ), "ckb.tue" ), KANUtil.getProperty( getLocale( request ), "ckb.wed" ),
               KANUtil.getProperty( getLocale( request ), "ckb.thu" ), KANUtil.getProperty( getLocale( request ), "ckb.fri" ),
               KANUtil.getProperty( getLocale( request ), "ckb.sat" ) };
         timesheetReportExportVO.setWeekDaysName( weekDaysName );

         if ( selectedIds.length() > 1 )
         {
            timesheetReportExportVO.setSelectedIds( selectedIds.substring( 0, selectedIds.length() - 1 ) );
            listTimesheetDatys = timesheetHeaderService.getTimesheetDetailDaysForReportByHeaderIds( timesheetReportExportVO );
            listTimesheetDetailVOs = timesheetHeaderService.getTimesheetDetailVOsForReportByHeaderIds( timesheetReportExportVO );

            // 遍历 回填
            for ( Object object : pagedListHolder.getSource() )
            {
               TimesheetReportExportVO timesheetHeader = ( TimesheetReportExportVO ) object;
               for ( Object objectdays : listTimesheetDatys )
               {

                  boolean on = true;
                  TimesheetDetailVO timesheetdays = ( TimesheetDetailVO ) objectdays;
                  if ( timesheetdays.getDayType().equals( "3" ) )
                  {
                     timesheetdays.setRemark1( "法" );
                  }
                  else
                  {

                     Date date = fmt.parse( timesheetdays.getDay() );

                     Calendar calendar = Calendar.getInstance();
                     calendar.setTime( date );
                     int intWeek = calendar.get( Calendar.DAY_OF_WEEK ) - 1;
                     timesheetdays.setRemark1( weekDaysName[ intWeek ] );

                  }

                  for ( Object objectDetail : listTimesheetDetailVOs )
                  {
                     TimesheetDetailVO timesheetDetailVO = ( TimesheetDetailVO ) objectDetail;
                     if ( timesheetHeader.getHeaderId().equals( timesheetDetailVO.getHeaderId() ) && timesheetdays.getDay().equals( timesheetDetailVO.getDay() ) )
                     {
                        timesheetHeader.getDetailList().add( timesheetDetailVO );
                        on = false;
                        continue;
                     }

                  }
                  if ( on )
                  {
                     timesheetHeader.getDetailList().add( objectdays );
                  }
               }
            }
         }

         // 刷新国际化
         refreshHolder( pagedListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "timesheetReportExportHolder", pagedListHolder );
         request.setAttribute( "listTimesheetDatys", listTimesheetDatys );
         request.setAttribute( "listTimesheetDetailVOs", listTimesheetDetailVOs );

         // 如果是In House登录，设置帐套数据
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // Ajax调用，直接返回table页面
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               final XSSFWorkbook workbook = timesheetHeaderService.timeSheetReport( listTimesheetDatys, listTimesheetDetailVOs, pagedListHolder, timesheetReportExportVO );
               // 导出文件
               new DownloadFileAction().download( response, workbook, "xx.xlsx" );
               return null;
            }
            else
            {
               request.setAttribute( "role", getRole( request, response ) );
               return mapping.findForward( "listTimesheetReportExportTable" );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listTimesheetReportExport" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   /**
    * 导出导入模板
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return ActionForward
    * @throws KANException 
    */
   public ActionForward exportReport( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

         // 初始化PagedListHolder
         final PagedListHolder timesheetHeaderlHolder = new PagedListHolder();

         // 初始化考勤详情查询条件
         final TimesheetReportExportVO timesheetHeaderVO = ( TimesheetReportExportVO ) form;
         if ( BaseAction.getRole( request, response ).equals( KANConstants.ROLE_CLIENT ) )
         {
            timesheetHeaderVO.setClientId( BaseAction.getClientId( request, response ) );
         }
         timesheetHeaderlHolder.setObject( timesheetHeaderVO );
         timesheetHeaderService.getTimesheetReportExportVOsByCondition( timesheetHeaderlHolder, false );
         String selectedIds = "";
         if ( timesheetHeaderlHolder.getSource() != null )
         {
            // 遍历
            for ( Object object : timesheetHeaderlHolder.getSource() )
            {
               TimesheetReportExportVO timesheetHeader = ( TimesheetReportExportVO ) object;
               /*timesheetHeader.reset( mapping, request );*/
               selectedIds = selectedIds + timesheetHeader.getHeaderId() + ",";
            }

            List< Object > listTimesheetDatys = new ArrayList< Object >();
            List< Object > listTimesheetDetailVOs = new ArrayList< Object >();

            if ( selectedIds.length() > 1 )
            {
               timesheetHeaderVO.setSelectedIds( selectedIds.substring( 0, selectedIds.length() - 1 ) );
               listTimesheetDatys = timesheetHeaderService.getTimesheetDetailDaysForReportByHeaderIds( timesheetHeaderVO );
               listTimesheetDetailVOs = timesheetHeaderService.getTimesheetDetailVOsForReportByHeaderIds( timesheetHeaderVO );
            }
            final XSSFWorkbook workbook = timesheetHeaderService.timeSheetReport( listTimesheetDatys, listTimesheetDetailVOs, timesheetHeaderlHolder, timesheetHeaderVO );
            // 初始化OutputStream
            final OutputStream os = response.getOutputStream();

            // 设置返回文件下载
            response.setContentType( "application/x-msdownload" );

            // 解决文件中文名下载问题
            response.setHeader( "Content-Disposition", "attachment;filename=" + new String( URLDecoder.decode( "考勤表.xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) );

            // Excel文件写入OutputStream
            workbook.write( os );

            // 输出OutputStream
            os.flush();
            //关闭流  
            os.close();

         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "" );
   }

   public ActionForward list_object_avg_report( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 获得Action Form
         final TimesheetReportExportVO timesheetReportExportVO = ( TimesheetReportExportVO ) form;
         if ( KANUtil.filterEmpty( timesheetReportExportVO.getMonthlyBegin() ) == null || KANUtil.filterEmpty( timesheetReportExportVO.getMonthlyEnd() ) == null )
         {
            timesheetReportExportVO.setMonthlyBegin( KANUtil.getMonthly( new Date() ) );
            timesheetReportExportVO.setMonthlyEnd( KANUtil.getMonthly( new Date() ) );
         }
         // 初始化Service接口
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );
         // 搜索内容需要解码。
         decodedObject( timesheetReportExportVO );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder timesheetReportExportHolder = new PagedListHolder();
         // 传入当前页
         timesheetReportExportHolder.setPage( page );
         // 传入当前值对象
         setDataAuth( request, response, timesheetReportExportVO );
         timesheetReportExportHolder.setObject( timesheetReportExportVO );
         // 设置页面记录条数
         timesheetReportExportHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         timesheetHeaderService.getAVGTimesheetReportExportVOsByCondition( timesheetReportExportHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         // 刷新Holder，国际化传值
         refreshHolder( timesheetReportExportHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "timesheetReportExportHolder", timesheetReportExportHolder );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               request.setAttribute( "holderName", "timesheetReportExportHolder" );
               request.setAttribute( "fileName", request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "平均出勤天数报表" : "AVG Attendance Days Report" );
               request.setAttribute( "nameZHArray", timesheetReportExportVO.getTitleNameList().split( "," ) );
               request.setAttribute( "nameSysArray", timesheetReportExportVO.getTitleIdList().split( "," ) );
               // 导出文件
               return new DownloadFileAction().commonExportList( mapping, form, request, response, true );
            }
            else
            {
               request.setAttribute( "role", BaseAction.getRole( request, response ) );
               // Ajax Table调用，直接传回Item JSP
               return mapping.findForward( "listAVGTimesheetReportExportTable" );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // 跳转JSP页面
      return mapping.findForward( "listAVGTimesheetReportExport" );
   }

   private String generateSQLString( final List< MappingVO > leaveItems, final List< MappingVO > otItems )
   {
      final StringBuffer sqlStr = new StringBuffer();
      if ( ( leaveItems != null && leaveItems.size() > 0 ) || ( otItems != null && otItems.size() > 0 ) )
      {
         sqlStr.append( ", CONCAT('{'," );
         int count = 0;
         for ( MappingVO leaveItem : leaveItems )
         {
            count++;
            if ( leaveItem.getMappingId().equals( "60" ) )
               continue;
            sqlStr.append( leaveItem.getMappingId() + ",':'," );
            sqlStr.append( "( SELECT CASE WHEN(ISNULL(SUM(e.estimateBenefitHours + e.estimateLegalHours))) THEN 0 ELSE SUM(e.estimateBenefitHours + e.estimateLegalHours) END " );
            sqlStr.append( "FROM Hro_Biz_Attendance_Leave_Detail AS e WHERE e.timesheetId = a.headerId AND e.itemId = " + leaveItem.getMappingId() + " AND e.deleted = 1 )" );

            if ( count != leaveItems.size() || ( otItems != null && otItems.size() > 0 ) )
               sqlStr.append( ",','," );
         }
         count = 0;
         for ( MappingVO otItem : otItems )
         {
            count++;
            sqlStr.append( otItem.getMappingId() + ",':'," );
            sqlStr.append( "( SELECT CASE WHEN(ISNULL(SUM(f.estimateHours))) THEN 0 ELSE SUM(f.estimateHours) END " );
            sqlStr.append( "FROM Hro_Biz_Attendance_OT_Detail AS f WHERE f.timesheetId = a.headerId AND f.itemId = " + otItem.getMappingId() + " AND f.deleted = 1 )" );

            if ( count != otItems.size() )
               sqlStr.append( ",','," );
         }
         sqlStr.append( ",'}') AS resultJSON " );
      }
      return sqlStr.toString();
   }
}

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
   // ��ǰAction��Ӧ��Access Action
   public static final String accessAction = "HRO_BIZ_ATTENDANCE_TIMESHEET_REPORT_EXPORT";

   // ƽ��������������
   public static final String ACCESS_ACITON = "HRO_BIZ_ATTENDANCE_AVG_REPORT";

   @Override
   // ���ڱ�������SQL��������У�����super��item��accountId��item����Ŀ
   /** Modify by siuvan 2015-01-05 **/
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );
         // ���Action Form
         final TimesheetReportExportVO timesheetReportExportVO = ( TimesheetReportExportVO ) form;

         // ��������Ȩ��
         if ( KANConstants.ROLE_IN_HOUSE.equals( BaseAction.getRole( request, response ) ) || KANConstants.ROLE_HR_SERVICE.equals( BaseAction.getRole( request, response ) ) )
         {
            setDataAuth( request, response, timesheetReportExportVO );
         }

         // ���û��ָ��������Ĭ�ϰ� batchId����
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

         // ����subAction
         dealSubAction( timesheetReportExportVO, mapping, form, request, response );

         // ����ѯ��١��Ӱ��Ŀ
         final List< MappingVO > leaveItems = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getLeaveItems( request.getLocale().getLanguage(), getCorpId( request, null ) );
         final List< MappingVO > otItems = KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getOtItems( request.getLocale().getLanguage(), getCorpId( request, null ) );
         timesheetReportExportVO.setResultJSON( generateSQLString( leaveItems, otItems ) );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder pagedListHolder = new PagedListHolder();
         // ���뵱ǰҳ
         pagedListHolder.setPage( page );
         // ���뵱ǰֵ����
         pagedListHolder.setObject( timesheetReportExportVO );
         // ����ҳ���¼����
         pagedListHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         timesheetHeaderService.getTimesheetReportExportVOsByCondition( pagedListHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         String selectedIds = "";
         // ����
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

            // ���� ����
            for ( Object object : pagedListHolder.getSource() )
            {
               TimesheetReportExportVO timesheetHeader = ( TimesheetReportExportVO ) object;
               for ( Object objectdays : listTimesheetDatys )
               {

                  boolean on = true;
                  TimesheetDetailVO timesheetdays = ( TimesheetDetailVO ) objectdays;
                  if ( timesheetdays.getDayType().equals( "3" ) )
                  {
                     timesheetdays.setRemark1( "��" );
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

         // ˢ�¹��ʻ�
         refreshHolder( pagedListHolder, request );
         // Holder��д��Request����
         request.setAttribute( "timesheetReportExportHolder", pagedListHolder );
         request.setAttribute( "listTimesheetDatys", listTimesheetDatys );
         request.setAttribute( "listTimesheetDetailVOs", listTimesheetDetailVOs );

         // �����In House��¼��������������
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // Ajax���ã�ֱ�ӷ���tableҳ��
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               final XSSFWorkbook workbook = timesheetHeaderService.timeSheetReport( listTimesheetDatys, listTimesheetDetailVOs, pagedListHolder, timesheetReportExportVO );
               // �����ļ�
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
      // ��תJSPҳ��
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
    * ��������ģ��
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
         // ��ʼ��Service�ӿ�
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );

         // ��ʼ��PagedListHolder
         final PagedListHolder timesheetHeaderlHolder = new PagedListHolder();

         // ��ʼ�����������ѯ����
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
            // ����
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
            // ��ʼ��OutputStream
            final OutputStream os = response.getOutputStream();

            // ���÷����ļ�����
            response.setContentType( "application/x-msdownload" );

            // ����ļ���������������
            response.setHeader( "Content-Disposition", "attachment;filename=" + new String( URLDecoder.decode( "���ڱ�.xlsx", "UTF-8" ).getBytes(), "iso-8859-1" ) );

            // Excel�ļ�д��OutputStream
            workbook.write( os );

            // ���OutputStream
            os.flush();
            //�ر���  
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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ���Action Form
         final TimesheetReportExportVO timesheetReportExportVO = ( TimesheetReportExportVO ) form;
         if ( KANUtil.filterEmpty( timesheetReportExportVO.getMonthlyBegin() ) == null || KANUtil.filterEmpty( timesheetReportExportVO.getMonthlyEnd() ) == null )
         {
            timesheetReportExportVO.setMonthlyBegin( KANUtil.getMonthly( new Date() ) );
            timesheetReportExportVO.setMonthlyEnd( KANUtil.getMonthly( new Date() ) );
         }
         // ��ʼ��Service�ӿ�
         final TimesheetHeaderService timesheetHeaderService = ( TimesheetHeaderService ) getService( "timesheetHeaderService" );
         // ����������Ҫ���롣
         decodedObject( timesheetReportExportVO );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder timesheetReportExportHolder = new PagedListHolder();
         // ���뵱ǰҳ
         timesheetReportExportHolder.setPage( page );
         // ���뵱ǰֵ����
         setDataAuth( request, response, timesheetReportExportVO );
         timesheetReportExportHolder.setObject( timesheetReportExportVO );
         // ����ҳ���¼����
         timesheetReportExportHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         timesheetHeaderService.getAVGTimesheetReportExportVOsByCondition( timesheetReportExportHolder, ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true ) );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( timesheetReportExportHolder, request );
         // Holder��д��Request����
         request.setAttribute( "timesheetReportExportHolder", timesheetReportExportHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            if ( getSubAction( form ).equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               request.setAttribute( "holderName", "timesheetReportExportHolder" );
               request.setAttribute( "fileName", request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? "ƽ��������������" : "AVG Attendance Days Report" );
               request.setAttribute( "nameZHArray", timesheetReportExportVO.getTitleNameList().split( "," ) );
               request.setAttribute( "nameSysArray", timesheetReportExportVO.getTitleIdList().split( "," ) );
               // �����ļ�
               return new DownloadFileAction().commonExportList( mapping, form, request, response, true );
            }
            else
            {
               request.setAttribute( "role", BaseAction.getRole( request, response ) );
               // Ajax Table���ã�ֱ�Ӵ���Item JSP
               return mapping.findForward( "listAVGTimesheetReportExportTable" );
            }
         }
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
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

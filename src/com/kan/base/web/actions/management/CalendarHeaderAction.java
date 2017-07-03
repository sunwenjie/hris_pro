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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final CalendarHeaderService calendarHeaderService = ( CalendarHeaderService ) getService( "calendarHeaderService" );
         // ���Action Form
         final CalendarHeaderVO calendarHeaderVO = ( CalendarHeaderVO ) form;

         // ����ɾ������
         if ( calendarHeaderVO.getSubAction() != null && calendarHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( calendarHeaderVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder calendarHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         calendarHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         calendarHeaderHolder.setObject( calendarHeaderVO );
         // ����ҳ���¼����
         calendarHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         calendarHeaderService.getCalendarHeaderVOsByCondition( calendarHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( calendarHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "calendarHeaderHolder", calendarHeaderHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            request.setAttribute( "accountId", getAccountId( request, null ) );
            // Ajax Table���ã�ֱ�Ӵ���CalendarHeader JSP
            return mapping.findForward( "listCalendarHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listCalendarHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( CalendarHeaderVO ) form ).setStatus( BaseVO.TRUE );
      ( ( CalendarHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����  
      return mapping.findForward( "manageCalendarHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��CalendarDetailVO
         final CalendarDetailVO calendarDetailVO = new CalendarDetailVO();

         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final CalendarHeaderService calendarHeaderService = ( CalendarHeaderService ) getService( "calendarHeaderService" );
            // ��õ�ǰFORM
            final CalendarHeaderVO calendarHeaderVO = ( CalendarHeaderVO ) form;
            calendarHeaderVO.setCreateBy( getUserId( request, response ) );
            calendarHeaderVO.setModifyBy( getUserId( request, response ) );
            calendarHeaderVO.setAccountId( getAccountId( request, response ) );
            calendarHeaderService.insertCalendarHeader( calendarHeaderVO );

            calendarDetailVO.setHeaderId( calendarHeaderVO.getHeaderId() );

            // ���¼��ص�������
            constantsInit( "initCalendarHeader", getAccountId( request, response ) );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
            insertlog( request, calendarHeaderVO, Operate.ADD, calendarHeaderVO.getHeaderId(), null );
         }
         else
         {
            // ���Action Form
            ( ( CalendarHeaderVO ) form ).reset();
            // ��������ظ��ύ�ľ���
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
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final CalendarHeaderService calendarHeaderService = ( CalendarHeaderService ) getService( "calendarHeaderService" );

            // ������ȡ�����
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "headerId" ), "UTF-8" ) );
            // ��ȡCalendarHeaderVO����
            final CalendarHeaderVO calendarHeaderVO = calendarHeaderService.getCalendarHeaderVOByHeaderId( headerId );
            // װ�ؽ��洫ֵ
            calendarHeaderVO.update( ( CalendarHeaderVO ) form );
            // ��ȡ��¼�û�
            calendarHeaderVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            calendarHeaderService.updateCalendarHeader( calendarHeaderVO );

            // ���¼��ص�������
            constantsInit( "initCalendarHeader", getAccountId( request, response ) );

            // ���ر༭�ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
            insertlog( request, calendarHeaderVO, Operate.MODIFY, calendarHeaderVO.getHeaderId(), null );
         }

         // ���Action Form
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
         // ��ʼ��Service�ӿ�
         final CalendarHeaderService calendarHeaderService = ( CalendarHeaderService ) getService( "calendarHeaderService" );

         // ���Action Form
         CalendarHeaderVO calendarHeaderVO = ( CalendarHeaderVO ) form;
         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( calendarHeaderVO.getSelectedIds() ) != null )
         {
            insertlog( request, calendarHeaderVO, Operate.DELETE, null, calendarHeaderVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : calendarHeaderVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               calendarHeaderVO = calendarHeaderService.getCalendarHeaderVOByHeaderId( selectedId );
               calendarHeaderVO.setHeaderId( selectedId );
               calendarHeaderVO.setModifyBy( getUserId( request, response ) );
               calendarHeaderVO.setModifyDate( new Date() );
               calendarHeaderService.deleteCalendarHeader( calendarHeaderVO );
            }
         }
         // ���Selected IDs����Action
         calendarHeaderVO.setSelectedIds( "" );
         calendarHeaderVO.setSubAction( "" );

         // ���¼��ص�������
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

      // ��ȡ�������
      final String year = request.getParameter( "year" );

      // ��ȡ�����·�
      final String month = request.getParameter( "month" );

      // ��ʼ��CalendarDTO �б�
      final List< CalendarDTO > calendarDTOs = new ArrayList< CalendarDTO >();

      // ��ǰ������һ��
      final Calendar tempCalendar = Calendar.getInstance();
      tempCalendar.set( Integer.valueOf( year ), Integer.valueOf( month ) - 1, 1 );

      // ��ǰ������ʼ
      final Calendar startCalendar = Calendar.getInstance();
      startCalendar.set( Integer.valueOf( year ), Integer.valueOf( month ) - 1, 1 );

      // ��ǰ������ֹ
      final Calendar endCalendar = Calendar.getInstance();
      endCalendar.set( Integer.valueOf( year ), Integer.valueOf( month ) - 1, tempCalendar.getActualMaximum( Calendar.DAY_OF_MONTH ) );

      // ��ʼ��CalendarDTO
      CalendarDTO calendarDTO = new CalendarDTO();

      // ����װ��CalendarDTO
      while ( tempCalendar.getTimeInMillis() <= endCalendar.getTimeInMillis() )
      {
         ChineseCalendar chineseCalendar = new ChineseCalendar( tempCalendar );
         // ��ʼ��CalendarDetailVO
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
         // ����ĩ����
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
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ȡ�ꡢ�¡���
         final String year = request.getParameter( "year" );
         final String month = request.getParameter( "month" );
         final String day = request.getParameter( "day" );

         // ����һ������
         final Date date = KANUtil.createDate( year + "-" + month + "-" + day );

         // ��ʼ��ChineseCalendar
         final ChineseCalendar chineseCalendar = new ChineseCalendar( date );

         // ��ʼ��
         final Calendar startCalendar = Calendar.getInstance();
         startCalendar.setTime( date );

         int week = startCalendar.get( Calendar.DAY_OF_WEEK ) - 1;

         // ��ʼ��StringBuffer
         final StringBuffer sb = new StringBuffer( "&nbsp;" );
         sb.append( "&nbsp;ũ��" + chineseCalendar.toString() + "&nbsp;&nbsp;" + "����" + ( ( week - 1 ) == -1 ? "��" : ChineseCalendar.chineseNumber[ week - 1 ] ) );

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
      // ��ǰ������ֹ
      final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
      final Calendar endCalendar = Calendar.getInstance();
      endCalendar.setTime( new Date() );

      endCalendar.add( Calendar.DAY_OF_MONTH, -1 );
      System.out.println( sdf.format( endCalendar.getTime() ) );
   }
}

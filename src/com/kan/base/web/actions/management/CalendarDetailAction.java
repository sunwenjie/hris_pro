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
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // �������Ajax���ã�����Token
         if ( !new Boolean( ajax ) )
         {
            this.saveToken( request );
         }

         // ���Action Form
         final CalendarDetailVO calendarDetailVO = ( CalendarDetailVO ) form;

         // ����SubAction
         dealSubAction( calendarDetailVO, mapping, form, request, response );

         // ��ʼ��Service�ӿ�
         final CalendarDetailService calendarDetailService = ( CalendarDetailService ) getService( "calendarDetailService" );
         final CalendarHeaderService calendarHeaderService = ( CalendarHeaderService ) getService( "calendarHeaderService" );

         // �����������
         String headerId = request.getParameter( "headerId" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = calendarDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ����������
         final CalendarHeaderVO calendarHeaderVO = calendarHeaderService.getCalendarHeaderVOByHeaderId( headerId );
         // ˢ�¹��ʻ�
         calendarHeaderVO.reset( null, request );
         // �����޸����
         calendarHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "calendarHeaderForm", calendarHeaderVO );
         calendarDetailVO.setHeaderId( headerId );

         // ���û��ָ��������Ĭ�ϰ� ��������
         if ( calendarDetailVO.getSortColumn() == null || calendarDetailVO.getSortColumn().isEmpty() )
         {
            calendarDetailVO.setSortColumn( "day" );
            calendarDetailVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder calendarDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         calendarDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         calendarDetailHolder.setObject( calendarDetailVO );
         // ����ҳ���¼����
         calendarDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         calendarDetailService.getCalendarDetailVOsByCondition( calendarDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( calendarDetailHolder, request );

         // Holder��д��Request����
         request.setAttribute( "calendarDetailHolder", calendarDetailHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���CalendarDetail JSP
            return mapping.findForward( "listCalendarDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
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
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final CalendarDetailService calendarDetailService = ( CalendarDetailService ) getService( "calendarDetailService" );
            // ��õ�ǰFORM
            final CalendarDetailVO calendarDetailVO = ( CalendarDetailVO ) form;
            // �������ID
            final String headerId = KANUtil.decodeString( request.getParameter( "headerId" ) );
            calendarDetailVO.setHeaderId( headerId );
            calendarDetailVO.setCreateBy( getUserId( request, response ) );
            calendarDetailVO.setModifyBy( getUserId( request, response ) );
            calendarDetailVO.setAccountId( getAccountId( request, response ) );
            calendarDetailService.insertCalendarDetail( calendarDetailVO );

            // ���¼��س����е�CalendarHeader
            constantsInit( "initCalendarHeader", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );
            insertlog( request, calendarDetailVO, Operate.ADD, calendarDetailVO.getDetailId(), null );
         }

         // ���Form
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
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final CalendarDetailService calendarDetailService = ( CalendarDetailService ) getService( "calendarDetailService" );
         final CalendarHeaderService calendarHeaderService = ( CalendarHeaderService ) getService( "calendarHeaderService" );
         // ��������ID
         final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
         // ���CalendarDetailVO����
         final CalendarDetailVO calendarDetailVO = calendarDetailService.getCalendarDetailVOByDetailId( detailId );
         // ���CalendarHeaderVO����
         final CalendarHeaderVO calendarHeaderVO = calendarHeaderService.getCalendarHeaderVOByHeaderId( calendarDetailVO.getHeaderId() );
         // ���ʻ���ֵ
         calendarDetailVO.reset( null, request );
         // �����޸����
         calendarDetailVO.setSubAction( VIEW_OBJECT );
         calendarDetailVO.setStatus( CalendarHeaderVO.TRUE );
         // ����request����
         request.setAttribute( "calendarHeaderForm", calendarHeaderVO );
         request.setAttribute( "calendarDetailForm", calendarDetailVO );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
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
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final CalendarDetailService calendarDetailService = ( CalendarDetailService ) getService( "calendarDetailService" );

            // ������ȡ�����
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            // ��ȡ��������
            final CalendarDetailVO calendarDetailVO = calendarDetailService.getCalendarDetailVOByDetailId( detailId );
            // װ�ؽ��洫ֵ
            calendarDetailVO.update( ( CalendarDetailVO ) form );
            // ��ȡ��¼�û�
            calendarDetailVO.setModifyBy( getUserId( request, response ) );
            // �����޸Ľӿ�
            calendarDetailService.updateCalendarDetail( calendarDetailVO );

            // ���¼��س����е�CalendarHeader
            constantsInit( "initCalendarHeader", getAccountId( request, response ) );

            // ���ر༭�ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
            insertlog( request, calendarDetailVO, Operate.MODIFY, calendarDetailVO.getDetailId(), null );
         }

         // ���Form
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
         // ��ʼ��Service�ӿ�
         final CalendarDetailService calendarDetailService = ( CalendarDetailService ) getService( "calendarDetailService" );

         // ��õ�ǰform
         CalendarDetailVO calendarDetailVO = ( CalendarDetailVO ) form;
         // ����ѡ�е�ID
         if ( calendarDetailVO.getSelectedIds() != null && !calendarDetailVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, calendarDetailVO, Operate.DELETE, null, calendarDetailVO.getSelectedIds() );
            // �ָ�
            for ( String selectedId : calendarDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               calendarDetailVO = calendarDetailService.getCalendarDetailVOByDetailId( selectedId );
               calendarDetailVO.setModifyBy( getUserId( request, response ) );
               calendarDetailVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               calendarDetailService.deleteCalendarDetail( calendarDetailVO );
            }
         }
         // ���Selected IDs����Action
         calendarDetailVO.setSelectedIds( "" );
         calendarDetailVO.setSubAction( "" );

         // ���¼��س����е�CalendarHeader
         constantsInit( "initCalendarHeader", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}
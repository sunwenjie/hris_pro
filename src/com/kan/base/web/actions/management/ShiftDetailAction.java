package com.kan.base.web.actions.management;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ShiftDetailVO;
import com.kan.base.domain.management.ShiftExceptionVO;
import com.kan.base.domain.management.ShiftHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ShiftDetailService;
import com.kan.base.service.inf.management.ShiftHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

public class ShiftDetailAction extends BaseAction
{
   public static final String accessAction = "HRO_ATTENDANCE_SHIFT";

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ���Action Form
         final ShiftDetailVO shiftDetailVO = ( ShiftDetailVO ) form;

         // ����subAction
         dealSubAction( shiftDetailVO, mapping, form, request, response );

         // ��ʼ��Service�ӿ�
         final ShiftDetailService shiftDetailService = ( ShiftDetailService ) getService( "shiftDetailService" );
         final ShiftHeaderService shiftHeaderService = ( ShiftHeaderService ) getService( "shiftHeaderService" );

         // �����������
         String headerId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( headerId ) != null )
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }
         else
         {
            headerId = ( ( ShiftDetailVO ) form ).getHeaderId();
         }

         // ����������
         final ShiftHeaderVO shiftHeaderVO = shiftHeaderService.getShiftHeaderVOByHeaderId( headerId );
         // ˢ�¹��ʻ�
         shiftHeaderVO.reset( null, request );
         // �����޸����
         shiftHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "shiftHeaderForm", shiftHeaderVO );

         shiftDetailVO.setHeaderId( headerId );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder shiftDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         shiftDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         shiftDetailHolder.setObject( shiftDetailVO );
         // ����ҳ���¼����
         shiftDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         shiftDetailService.getShiftDetailVOsByCondition( shiftDetailHolder, false );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( shiftDetailHolder, request );

         // Holder��д��Request����
         request.setAttribute( "shiftDetailHolder", shiftDetailHolder );

         int maxPeriod = 0;
         int minPeriod = 48;
         if ( shiftDetailHolder != null && shiftDetailHolder.getSource() != null && shiftDetailHolder.getSource().size() > 0 )
         {
            for ( Object shiftDetailObject : shiftDetailHolder.getSource() )
            {
               final ShiftDetailVO shiftDetail = ( ShiftDetailVO ) shiftDetailObject;
               if ( KANUtil.filterEmpty( shiftDetail.getShiftPeriod() ) != null )
               {
                  String[] periodArray = KANUtil.jasonArrayToStringArray( shiftDetail.getShiftPeriod() );
                  if ( Integer.valueOf( periodArray[ 0 ] ) < minPeriod )
                  {
                     minPeriod = Integer.valueOf( periodArray[ 0 ] );
                  }

                  if ( Integer.valueOf( periodArray[ periodArray.length - 1 ] ) > maxPeriod )
                  {
                     maxPeriod = Integer.valueOf( periodArray[ periodArray.length - 1 ] );
                  }
               }
            }
         }

         request.setAttribute( "minPeriod", minPeriod );
         request.setAttribute( "maxPeriod", maxPeriod );

         // ��ʼ��ShiftExceptionVO
         final ShiftExceptionVO shiftExceptionVO = new ShiftExceptionVO();
         shiftExceptionVO.setHeaderId( headerId );
         shiftExceptionVO.setExceptionType( "2" );
         shiftExceptionVO.setStatus( "1" );
         shiftExceptionVO.reset( null, request );
         request.setAttribute( "shiftExceptionForm", shiftExceptionVO );

         //* ����Shift Exception Info
         new ShiftExceptionAction().list_object( mapping, shiftExceptionVO, request, response );

         // ���subAction
         ( ( ShiftDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listShiftDetail" );
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
            final ShiftDetailService shiftDetailService = ( ShiftDetailService ) getService( "shiftDetailService" );
            // ��õ�ǰFORM
            final ShiftDetailVO shiftDetailVO = ( ShiftDetailVO ) form;

            // �������ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            shiftDetailVO.setHeaderId( headerId );
            shiftDetailVO.setCreateBy( getUserId( request, response ) );
            shiftDetailVO.setModifyBy( getUserId( request, response ) );
            shiftDetailVO.setAccountId( getAccountId( request, response ) );

            shiftDetailService.insertShiftDetail( shiftDetailVO );

            // ���¼��ص�������
            constantsInit( "initShiftHeader", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );
            insertlog( request, shiftDetailVO, Operate.ADD, shiftDetailVO.getDetailId(), null );
         }

         // ���Form
         ( ( ShiftDetailVO ) form ).reset();
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
         final ShiftDetailService shiftDetailService = ( ShiftDetailService ) getService( "shiftDetailService" );
         final ShiftHeaderService shiftHeaderService = ( ShiftHeaderService ) getService( "shiftHeaderService" );

         // ��������ID
         final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );

         // ���ShiftDetailVO����
         final ShiftDetailVO shiftDetailVO = shiftDetailService.getShiftDetailVOByDetailId( detailId );

         // ���ShiftHeaderVO����
         final ShiftHeaderVO shiftHeaderVO = shiftHeaderService.getShiftHeaderVOByHeaderId( shiftDetailVO.getHeaderId() );

         // ���ʻ���ֵ
         shiftDetailVO.reset( null, request );

         // �����޸����
         shiftDetailVO.setSubAction( VIEW_OBJECT );

         // ����request����
         request.setAttribute( "shiftHeaderForm", shiftHeaderVO );
         request.setAttribute( "shiftDetailForm", shiftDetailVO );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
         return mapping.findForward( "manageShiftDetailForm" );
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
            final ShiftDetailService shiftDetailService = ( ShiftDetailService ) getService( "shiftDetailService" );
            // ������ȡ�����
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            // ��ȡ��������
            final ShiftDetailVO shiftDetailVO = shiftDetailService.getShiftDetailVOByDetailId( detailId );
            // װ�ؽ��洫ֵ
            shiftDetailVO.update( ( ShiftDetailVO ) form );
            // ��ȡ��¼�û�
            shiftDetailVO.setModifyBy( getUserId( request, response ) );
            // �����޸Ľӿ�
            shiftDetailService.updateShiftDetail( shiftDetailVO );

            // ���¼��ص�������
            constantsInit( "initShiftHeader", getAccountId( request, response ) );

            // ���ر༭�ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
            insertlog( request, shiftDetailVO, Operate.MODIFY, shiftDetailVO.getDetailId(), null );
         }

         // ���Form
         ( ( ShiftDetailVO ) form ).reset();
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
         final ShiftDetailService shiftDetailService = ( ShiftDetailService ) getService( "shiftDetailService" );
         // ��õ�ǰform
         ShiftDetailVO shiftDetailVO = ( ShiftDetailVO ) form;
         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( shiftDetailVO.getSelectedIds() ) != null )
         {
            insertlog( request, shiftDetailVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( shiftDetailVO.getSelectedIds() ) );
            // �ָ�
            for ( String selectedId : shiftDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               shiftDetailVO = shiftDetailService.getShiftDetailVOByDetailId( KANUtil.decodeStringFromAjax( selectedId ) );
               shiftDetailVO.setModifyBy( getUserId( request, response ) );
               shiftDetailVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               shiftDetailService.deleteShiftDetail( shiftDetailVO );
            }

            // ���¼��ص�������
            constantsInit( "initShiftHeader", getAccountId( request, response ) );
         }
         // ���Selected IDs����Action
         shiftDetailVO.setSelectedIds( "" );
         shiftDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}
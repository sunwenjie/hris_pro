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
import com.kan.base.service.inf.management.ShiftExceptionService;
import com.kan.base.service.inf.management.ShiftHeaderService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class ShiftExceptionAction extends BaseAction
{

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ���Action Form
         final ShiftExceptionVO shiftExceptionVO = ( ShiftExceptionVO ) form;

         // ����SubAction
         dealSubAction( shiftExceptionVO, mapping, form, request, response );

         // ��ʼ��Service�ӿ�
         final ShiftExceptionService shiftExceptionService = ( ShiftExceptionService ) getService( "shiftExceptionService" );

         // �����������
         String headerId = request.getParameter( "headerId" );
         if ( KANUtil.filterEmpty( headerId ) == null )
         {
            headerId = shiftExceptionVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         shiftExceptionVO.setHeaderId( headerId );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder shiftExceptionHolder = new PagedListHolder();

         // ���뵱ǰҳ
         shiftExceptionHolder.setPage( page );
         // ���뵱ǰֵ����
         shiftExceptionHolder.setObject( shiftExceptionVO );
         // ����ҳ���¼����
         shiftExceptionHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         shiftExceptionService.getShiftExceptionVOsByCondition( shiftExceptionHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( shiftExceptionHolder, request );

         // Holder��д��Request����
         request.setAttribute( "shiftExceptionHolder", shiftExceptionHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listShiftExceptionTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
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
      try
      {
         // ��ʼ��ShiftDet
         final ShiftDetailVO shiftDetailVO = new ShiftDetailVO();

         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final ShiftExceptionService shiftExceptionService = ( ShiftExceptionService ) getService( "shiftExceptionService" );
            // ��õ�ǰFORM
            final ShiftExceptionVO shiftExceptionVO = ( ShiftExceptionVO ) form;

            // �������ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            shiftDetailVO.setHeaderId( headerId );
            shiftExceptionVO.setHeaderId( headerId );
            shiftExceptionVO.setCreateBy( getUserId( request, response ) );
            shiftExceptionVO.setModifyBy( getUserId( request, response ) );
            shiftExceptionVO.setAccountId( getAccountId( request, response ) );

            shiftExceptionService.insertShiftException( shiftExceptionVO );

            // ���¼��ص�������
            constantsInit( "initShiftHeader", getAccountId( request, response ) );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, "MESSAGE_EXCEPTION" );
         }

         // ���Form
         ( ( ShiftExceptionVO ) form ).reset();

         // ��ת�Ű�����
         return new ShiftDetailAction().list_object( mapping, shiftDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
      return null;
   }

   public ActionForward to_objectModify_ajax( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���üǺţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final ShiftExceptionService shiftExceptionService = ( ShiftExceptionService ) getService( "shiftExceptionService" );
         final ShiftHeaderService shiftHeaderService = ( ShiftHeaderService ) getService( "shiftHeaderService" );

         // ��������ID
         final String exceptionId = KANUtil.decodeString( request.getParameter( "exceptionId" ) );

         // ���ShiftExceptionVO����
         final ShiftExceptionVO shiftExceptionVO = shiftExceptionService.getShiftExceptionVOByExceptionId( exceptionId );

         // ���ShiftHeaderVO����
         final ShiftHeaderVO shiftHeaderVO = shiftHeaderService.getShiftHeaderVOByHeaderId( shiftExceptionVO.getHeaderId() );

         // ���ʻ���ֵ
         shiftExceptionVO.reset( null, request );

         // �����޸����
         shiftExceptionVO.setSubAction( VIEW_OBJECT );

         // ����request����
         request.setAttribute( "shiftHeaderForm", shiftHeaderVO );
         request.setAttribute( "shiftExceptionForm", shiftExceptionVO );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
         return mapping.findForward( "manageShiftExceptionForm" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��ShiftDet
         final ShiftDetailVO shiftDetailVO = new ShiftDetailVO();

         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final ShiftExceptionService shiftExceptionService = ( ShiftExceptionService ) getService( "shiftExceptionService" );
            // ������ȡ�����
            final String exceptionId = KANUtil.decodeString( request.getParameter( "exceptionId" ) );
            // ��ȡShiftExceptionVO
            final ShiftExceptionVO shiftExceptionVO = shiftExceptionService.getShiftExceptionVOByExceptionId( exceptionId );
            // װ�ؽ��洫ֵ
            shiftExceptionVO.update( ( ShiftExceptionVO ) form );
            // ��ȡ��¼�û�
            shiftExceptionVO.setModifyBy( getUserId( request, response ) );
            // �����޸Ľӿ�
            shiftExceptionService.updateShiftException( shiftExceptionVO );

            shiftDetailVO.setHeaderId( shiftExceptionVO.getHeaderId() );

            // ���¼��ص�������
            constantsInit( "initShiftHeader", getAccountId( request, response ) );

            // ���ر༭�ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, "MESSAGE_EXCEPTION" );
         }

         // ���Form
         ( ( ShiftExceptionVO ) form ).reset();

         // ��ת�Ű�����
         return new ShiftDetailAction().list_object( mapping, shiftDetailVO, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final ShiftExceptionService shiftExceptionService = ( ShiftExceptionService ) getService( "shiftExceptionService" );
         // ��õ�ǰform
         ShiftExceptionVO shiftExceptionVO = ( ShiftExceptionVO ) form;
         // ����ѡ�е�ID
         if ( KANUtil.filterEmpty( shiftExceptionVO.getSelectedIds() ) != null )
         {
            // �ָ�
            for ( String selectedId : shiftExceptionVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               shiftExceptionVO = shiftExceptionService.getShiftExceptionVOByExceptionId( KANUtil.decodeStringFromAjax( selectedId ) );
               shiftExceptionVO.setModifyBy( getUserId( request, response ) );
               shiftExceptionVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               shiftExceptionService.deleteShiftExceptionl( shiftExceptionVO );
            }

            // ���¼��ص�������
            constantsInit( "initShiftHeader", getAccountId( request, response ) );
         }
         // ���Selected IDs����Action
         shiftExceptionVO.setSelectedIds( "" );
         shiftExceptionVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

}

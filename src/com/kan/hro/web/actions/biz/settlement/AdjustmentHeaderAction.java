package com.kan.hro.web.actions.biz.settlement;

import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.settlement.AdjustmentDetailVO;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;
import com.kan.hro.service.inf.biz.settlement.AdjustmentHeaderService;

public class AdjustmentHeaderAction extends BaseAction
{
	public final static String accessAction = "HRO_SETTLE_ADJUSTMENT_HEADER";
   /**
    * �˵�����
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
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
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );

         // ���Action Form
         final AdjustmentHeaderVO adjustmentHeaderVO = ( AdjustmentHeaderVO ) form;
         
         String accessAction ="HRO_SETTLE_ADJUSTMENT_HEADER";
         //��������Ȩ��
         setAuthPositionIds(BaseAction.getAccountId(request, response), BaseAction.getUserVOFromClient(request, response), accessAction,adjustmentHeaderVO);
         
         adjustmentHeaderVO.setOrderId( KANUtil.filterEmpty( adjustmentHeaderVO.getOrderId(), "0" ) );

         // ����ɾ������
         if ( adjustmentHeaderVO.getSubAction() != null && adjustmentHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( adjustmentHeaderVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder adjustmentHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         adjustmentHeaderHolder.setPage( page );

         // ���û��ָ��������Ĭ�ϰ� adjustmentHeaderId����
         if ( adjustmentHeaderVO.getSortColumn() == null || adjustmentHeaderVO.getSortColumn().isEmpty() )
         {
            adjustmentHeaderVO.setSortColumn( "adjustmentHeaderId" );
            adjustmentHeaderVO.setSortOrder( "desc" );
         }

         // ���뵱ǰֵ����
         adjustmentHeaderHolder.setObject( adjustmentHeaderVO );
         // ����ҳ���¼����
         adjustmentHeaderHolder.setPageSize( listPageSize );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         adjustmentHeaderService.getAdjustmentHeaderVOsByCondition( adjustmentHeaderHolder, true );
         refreshHolder( adjustmentHeaderHolder, request );
         // Holder��д��Request����
         request.setAttribute( "adjustmentHeaderHolder", adjustmentHeaderHolder );

         // �����In House��¼��������������
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listAdjustmentHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listAdjustmentHeader" );
   }

   /**
    * ����ȷ��
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_confirm( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );

         // ���Action Form
         final AdjustmentHeaderVO adjustmentHeaderVO = ( AdjustmentHeaderVO ) form;
         
         String accessAction ="HRO_SETTLE_ADJUSTMENT_HEADER_CONFIRM";
         //��������Ȩ��
         setAuthPositionIds(BaseAction.getAccountId(request, response), BaseAction.getUserVOFromClient(request, response), accessAction,adjustmentHeaderVO);
         
         adjustmentHeaderVO.setOrderId( KANUtil.filterEmpty( adjustmentHeaderVO.getOrderId(), "0" ) );

         // ����ȷ�ϣ�ֻ��ѯ������ˡ�״̬������
         adjustmentHeaderVO.setStatus( "2" );

         // ����ɾ������
         if ( adjustmentHeaderVO.getSubAction() != null && adjustmentHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( adjustmentHeaderVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder adjustmentHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         adjustmentHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         adjustmentHeaderHolder.setObject( adjustmentHeaderVO );
         // ����ҳ���¼����
         adjustmentHeaderHolder.setPageSize( listPageSize );

         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         adjustmentHeaderService.getAdjustmentHeaderVOsByCondition( adjustmentHeaderHolder, true );
         refreshHolder( adjustmentHeaderHolder, request );
         // Holder��д��Request����
         request.setAttribute( "adjustmentHeaderHolder", adjustmentHeaderHolder );

         // �����In House��¼��������������
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            passClientOrders( request, response );
         }

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listAdjustmentHeaderConfirmTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listAdjustmentHeaderConfirm" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ����Sub Action
      ( ( AdjustmentHeaderVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( AdjustmentHeaderVO ) form ).setAdjustmentDate( KANUtil.formatDate( new Date(), "yyyy-MM-dd" ) );
      ( ( AdjustmentHeaderVO ) form ).setBillAmountPersonal( "0" );
      ( ( AdjustmentHeaderVO ) form ).setBillAmountCompany( "0" );
      ( ( AdjustmentHeaderVO ) form ).setCostAmountPersonal( "0" );
      ( ( AdjustmentHeaderVO ) form ).setCostAmountCompany( "0" );
      ( ( AdjustmentHeaderVO ) form ).setStatus( "1" );

      request.setAttribute( "adjustmentDetailHolder", new PagedListHolder() );

      // �����In House��¼��������������
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         passClientOrders( request, response );
      }

      // ��ת���½�����
      return mapping.findForward( "manageAdjustmentHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��form����
         final AdjustmentDetailVO adjustmentDetailVO = new AdjustmentDetailVO();
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );
            // ��õ�ǰFORM
            final AdjustmentHeaderVO adjustmentHeaderVO = ( AdjustmentHeaderVO ) form;
            // �趨��ǰ�û�
            adjustmentHeaderVO.setAccountId( getAccountId( request, response ) );
            adjustmentHeaderVO.setCreateBy( getUserId( request, response ) );
            adjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
            // ������ӷ���
            int result = adjustmentHeaderService.insertAdjustmentHeader( adjustmentHeaderVO );
            adjustmentDetailVO.setAdjustmentHeaderId( adjustmentHeaderVO.getAdjustmentHeaderId() );

            if ( result == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT, null, MESSAGE_HEADER );
            }
            else
            {
               success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );
            }
         }
         else
         {
            // ���FORM
            ( ( AdjustmentHeaderVO ) form ).reset();

            // ��������ظ��ύ�ľ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }

         return new AdjustmentDetailAction().list_object( mapping, adjustmentDetailVO, request, response );
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
         final String flag = request.getParameter( "flag" );
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );
            // ������ȡ�����
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ��ȡSBAdjustmentHeaderVO����
            final AdjustmentHeaderVO adjustmentHeaderVO = adjustmentHeaderService.getAdjustmentHeaderVOByAdjustmentHeaderId( headerId );
            // ��ȡSubAction
            final String subAction = request.getParameter( "subAction" );
            // װ�ؽ��洫ֵ
            adjustmentHeaderVO.update( ( AdjustmentHeaderVO ) form );
            // ��ȡ��¼�û�
            adjustmentHeaderVO.setModifyBy( getUserId( request, response ) );

            // ������ύ
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               if ( adjustmentHeaderService.submitAdjustmentHeader( adjustmentHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT, null, MESSAGE_HEADER );
               }
               else
               {
                  // ���ر���ɹ��ı��
                  success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
                  ;
               }
            }
            else
            {
               adjustmentHeaderService.updateAdjustmentHeader( adjustmentHeaderVO );
               // ���ر���ɹ��ı��
               success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );
            }
         }
         // ���Action Form
         ( ( AdjustmentHeaderVO ) form ).reset();
         ( ( AdjustmentHeaderVO ) form ).setClientNameZH( "" );
         ( ( AdjustmentHeaderVO ) form ).setClientNameEN( "" );
         if ( KANUtil.filterEmpty( flag ) != null && flag.equals( "confirm" ) )
         {
            return list_object_confirm( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return new AdjustmentDetailAction().list_object( mapping, new AdjustmentDetailVO(), request, response );
   }

   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );
         // ������������
         final String adjustmentHeaderId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // ���LeaveVO����
         final AdjustmentHeaderVO adjustmentHeaderVO = adjustmentHeaderService.getAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentHeaderId );
         // ��ȡ��¼�û�
         adjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         adjustmentHeaderVO.setLocale( request.getLocale() );

         if ( adjustmentHeaderService.submitAdjustmentHeader( adjustmentHeaderVO ) == -1 )
         {
            success( request, MESSAGE_TYPE_SUBMIT );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   public ActionForward approve_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );

         final AdjustmentHeaderVO adjustmentHeaderVO = ( AdjustmentHeaderVO ) form;
         adjustmentHeaderVO.setStatus( "3" );
         adjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         adjustmentHeaderService.updateAdjustmentHeader( adjustmentHeaderVO );

         // ���FORM
         ( ( AdjustmentHeaderVO ) form ).reset();
         ( ( AdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( AdjustmentHeaderVO ) form ).setSelectedIds( "" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object_confirm( mapping, form, request, response );
   }

   public ActionForward rollback_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );

         final AdjustmentHeaderVO adjustmentHeaderVO = ( AdjustmentHeaderVO ) form;
         adjustmentHeaderVO.setStatus( "4" );
         adjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
         adjustmentHeaderService.updateAdjustmentHeader( adjustmentHeaderVO );

         // ���FORM
         ( ( AdjustmentHeaderVO ) form ).reset();
         ( ( AdjustmentHeaderVO ) form ).setSubAction( "" );
         ( ( AdjustmentHeaderVO ) form ).setSelectedIds( "" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object_confirm( mapping, form, request, response );
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
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );
         // ���Action Form
         AdjustmentHeaderVO adjustmentHeaderVO = ( AdjustmentHeaderVO ) form;
         // ����ѡ�е�ID
         if ( adjustmentHeaderVO.getSelectedIds() != null && !adjustmentHeaderVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : adjustmentHeaderVO.getSelectedIds().split( "," ) )
            {
               // ���ɾ������
               adjustmentHeaderVO = adjustmentHeaderService.getAdjustmentHeaderVOByAdjustmentHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );
               adjustmentHeaderVO.setModifyBy( getUserId( request, response ) );
               adjustmentHeaderVO.setModifyDate( new Date() );
               adjustmentHeaderService.deleteAdjustmentHeader( adjustmentHeaderVO );
            }
         }

         // ���Selected IDs����Action
         ( ( AdjustmentHeaderVO ) form ).setSelectedIds( "" );
         ( ( AdjustmentHeaderVO ) form ).setSubAction( SEARCH_OBJECT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

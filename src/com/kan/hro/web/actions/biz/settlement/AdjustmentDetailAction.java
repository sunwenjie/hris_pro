package com.kan.hro.web.actions.biz.settlement;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.settlement.AdjustmentDetailVO;
import com.kan.hro.domain.biz.settlement.AdjustmentHeaderVO;
import com.kan.hro.service.inf.biz.settlement.AdjustmentDetailService;
import com.kan.hro.service.inf.biz.settlement.AdjustmentHeaderService;

public class AdjustmentDetailAction extends BaseAction
{
	public final static String accessAction = "HRO_SETTLE_ADJUSTMENT_DETAIL";
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
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��õ�ǰҳ
         final String page = getPage( request );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );
         final AdjustmentDetailService adjustmentDetailService = ( AdjustmentDetailService ) getService( "adjustmentDetailService" );

         // �����������
         String headerId = request.getParameter( "id" );
         if ( headerId == null || "".equals( headerId ) )
         {
            headerId = ( ( AdjustmentDetailVO ) form ).getAdjustmentHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ����������
         final AdjustmentHeaderVO adjustmentHeaderVO = adjustmentHeaderService.getAdjustmentHeaderVOByAdjustmentHeaderId( headerId );

         // ˢ�¹��ʻ�
         adjustmentHeaderVO.reset( null, request );
         // �����޸����
         adjustmentHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "adjustmentHeaderForm", adjustmentHeaderVO );
         // ���Action Form
         final AdjustmentDetailVO adjustmentDetailVO = ( AdjustmentDetailVO ) form;
         adjustmentDetailVO.setAdjustmentHeaderId( headerId );
         // ����SubAction
         dealSubAction( adjustmentDetailVO, mapping, form, request, response );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder adjustmentDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         adjustmentDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         adjustmentDetailHolder.setObject( adjustmentDetailVO );
         // ����ҳ���¼����
         adjustmentDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         adjustmentDetailService.getAdjustmentDetailVOsByCondition( adjustmentDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( adjustmentDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "adjustmentDetailHolder", adjustmentDetailHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���listAdjustmentDetailTable JSP
            return mapping.findForward( "listAdjustmentDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listAdjustmentDetail" );
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
   public ActionForward list_object_confirm( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��õ�ǰҳ
         final String page = getPage( request );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );
         final AdjustmentDetailService adjustmentDetailService = ( AdjustmentDetailService ) getService( "adjustmentDetailService" );

         // �����������
         String headerId = request.getParameter( "id" );
         if ( headerId == null || "".equals( headerId ) )
         {
            headerId = ( ( AdjustmentDetailVO ) form ).getAdjustmentHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ����������
         final AdjustmentHeaderVO adjustmentHeaderVO = adjustmentHeaderService.getAdjustmentHeaderVOByAdjustmentHeaderId( headerId );

         // ˢ�¹��ʻ�
         adjustmentHeaderVO.reset( null, request );
         // �����޸����
         adjustmentHeaderVO.setSubAction( VIEW_OBJECT );
         // д��request����
         request.setAttribute( "adjustmentHeaderForm", adjustmentHeaderVO );
         // ���Action Form
         final AdjustmentDetailVO adjustmentDetailVO = ( AdjustmentDetailVO ) form;
         adjustmentDetailVO.setAdjustmentHeaderId( headerId );
         // ����SubAction
         dealSubAction( adjustmentDetailVO, mapping, form, request, response );
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder adjustmentDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         adjustmentDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         adjustmentDetailHolder.setObject( adjustmentDetailVO );
         // ����ҳ���¼����
         adjustmentDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         adjustmentDetailService.getAdjustmentDetailVOsByCondition( adjustmentDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( adjustmentDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "adjustmentDetailHolder", adjustmentDetailHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���listAdjustmentDetailTable JSP
            return mapping.findForward( "listAdjustmentDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listAdjustmentDetailConfrim" );
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
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final AdjustmentDetailService adjustmentDetailService = ( AdjustmentDetailService ) getService( "adjustmentDetailService" );
            // ��õ�ǰFORM
            final AdjustmentDetailVO adjustmentDetailVO = ( AdjustmentDetailVO ) form;
            // �������ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            adjustmentDetailVO.setAdjustmentHeaderId( headerId );
            adjustmentDetailVO.setCreateBy( getUserId( request, response ) );
            adjustmentDetailVO.setModifyBy( getUserId( request, response ) );
            adjustmentDetailVO.setAccountId( getAccountId( request, response ) );
            adjustmentDetailService.insertAdjustmentDetail( adjustmentDetailVO );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );
         }

         // ���Form
         ( ( AdjustmentDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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
         final AdjustmentDetailService adjustmentDetailService = ( AdjustmentDetailService ) getService( "adjustmentDetailService" );
         final AdjustmentHeaderService adjustmentHeaderService = ( AdjustmentHeaderService ) getService( "adjustmentHeaderService" );
         // ��������ID
         final String detailId = KANUtil.decodeString( request.getParameter( "id" ) );
         // ���AdjustmentDetailVO����
         final AdjustmentDetailVO adjustmentDetailVO = adjustmentDetailService.getAdjustmentDetailVOByAdjustmentDetailId( detailId );
         // ���AdjustmentHeaderVO����
         final AdjustmentHeaderVO adjustmentHeaderVO = adjustmentHeaderService.getAdjustmentHeaderVOByAdjustmentHeaderId( adjustmentDetailVO.getAdjustmentHeaderId() );
         // ���ʻ���ֵ
         adjustmentDetailVO.reset( null, request );
         // �����޸����
         adjustmentDetailVO.setSubAction( VIEW_OBJECT );
         adjustmentDetailVO.setStatus( AdjustmentDetailVO.TRUE );
         // ����request����
         request.setAttribute( "adjustmentHeaderForm", adjustmentHeaderVO );
         request.setAttribute( "adjustmentDetailForm", adjustmentDetailVO );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
         return mapping.findForward( "manageAdjustmentDetailForm" );
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
         // �жϷ�ֹ�ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�AdjustmentDetailService
            final AdjustmentDetailService adjustmentDetailService = ( AdjustmentDetailService ) getService( "adjustmentDetailService" );
            // ������ȡ�����
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            // ��ȡ��������
            final AdjustmentDetailVO adjustmentDetailVO = adjustmentDetailService.getAdjustmentDetailVOByAdjustmentDetailId( detailId );
            // װ�ؽ��洫ֵ
            adjustmentDetailVO.update( ( AdjustmentDetailVO ) form );
            // ��ȡ��¼�û�
            adjustmentDetailVO.setModifyBy( getUserId( request, response ) );
            // �����޸Ľӿ�
            adjustmentDetailService.updateAdjustmentDetail( adjustmentDetailVO );
            // ���ر༭�ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );
         }

         // ���Form
         ( ( AdjustmentDetailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return list_object( mapping, form, request, response );
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
         final AdjustmentDetailService adjustmentDetailService = ( AdjustmentDetailService ) getService( "adjustmentDetailService" );
         // ��õ�ǰform
         AdjustmentDetailVO adjustmentDetailVO = ( AdjustmentDetailVO ) form;
         // ����ѡ�е�ID
         if ( adjustmentDetailVO.getSelectedIds() != null && !adjustmentDetailVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : adjustmentDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               adjustmentDetailVO = adjustmentDetailService.getAdjustmentDetailVOByAdjustmentDetailId( KANUtil.decodeStringFromAjax( selectedId ) );
               adjustmentDetailVO.setModifyBy( getUserId( request, response ) );
               adjustmentDetailVO.setModifyDate( new Date() );
               // ����ɾ���ӿ�
               adjustmentDetailService.deleteAdjustmentDetail( adjustmentDetailVO );
            }
         }
         // ���Selected IDs����Action
         adjustmentDetailVO.setSelectedIds( "" );
         adjustmentDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

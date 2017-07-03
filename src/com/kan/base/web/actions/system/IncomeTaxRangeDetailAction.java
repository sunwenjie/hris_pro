package com.kan.base.web.actions.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.system.IncomeTaxRangeDetailVO;
import com.kan.base.domain.system.IncomeTaxRangeHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.IncomeTaxRangeDetailService;
import com.kan.base.service.inf.system.IncomeTaxRangeHeaderService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class IncomeTaxRangeDetailAction extends BaseAction
{

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ���Action Form
         final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = ( IncomeTaxRangeDetailVO ) form;

         // ����subAction
         dealSubAction( incomeTaxRangeDetailVO, mapping, form, request, response );

         // �����������
         String headerId = request.getParameter( "id" );
         if ( headerId == null || headerId.trim().isEmpty() )
         {
            headerId = incomeTaxRangeDetailVO.getHeaderId();
         }
         else
         {
            headerId = KANUtil.decodeStringFromAjax( headerId );
         }

         // ��ʼ��Service�ӿ�
         final IncomeTaxRangeHeaderService incomeTaxRangeHeaderService = ( IncomeTaxRangeHeaderService ) getService( "incomeTaxRangeHeaderService" );
         final IncomeTaxRangeDetailService incomeTaxRangeDetailService = ( IncomeTaxRangeDetailService ) getService( "incomeTaxRangeDetailService" );

         // ����������
         final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = incomeTaxRangeHeaderService.getIncomeTaxRangeHeaderVOByHeaderId( headerId );

         incomeTaxRangeHeaderVO.reset( null, request );
         incomeTaxRangeHeaderVO.setSubAction( VIEW_OBJECT );
         incomeTaxRangeHeaderVO.setIsDefault( ( incomeTaxRangeHeaderVO.getIsDefault() != null && incomeTaxRangeHeaderVO.getIsDefault().equalsIgnoreCase( "1" ) ) ? "on" : "" );

         // д��request����
         request.setAttribute( "incomeTaxRangeHeaderForm", incomeTaxRangeHeaderVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder incomeTaxRangeDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         incomeTaxRangeDetailHolder.setPage( page );
         // ��������ID
         incomeTaxRangeDetailVO.setHeaderId( headerId );
         // ���뵱ǰֵ����
         incomeTaxRangeDetailHolder.setObject( incomeTaxRangeDetailVO );
         // ����ҳ���¼����
         incomeTaxRangeDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         incomeTaxRangeDetailService.getIncomeTaxRangeDetailVOsByCondition( incomeTaxRangeDetailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( incomeTaxRangeDetailHolder, request );

         // Holder��д��Request����
         request.setAttribute( "incomeTaxRangeDetailHolder", incomeTaxRangeDetailHolder );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���SocialBenetifDetail JSP
            return mapping.findForward( "listIncomeTaxRangeDetailTable" );
         }
         ( ( IncomeTaxRangeDetailVO ) form ).setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // ��תJSPҳ��
      return mapping.findForward( "listIncomeTaxRangeDetail" );
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
            final IncomeTaxRangeDetailService incomeTaxRangeDetailService = ( IncomeTaxRangeDetailService ) getService( "incomeTaxRangeDetailService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );
            // ��õ�ǰFORM
            final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = ( IncomeTaxRangeDetailVO ) form;
            // �������ID
            final String headerId = KANUtil.decodeString( request.getParameter( "id" ) );
            incomeTaxRangeDetailVO.setHeaderId( headerId );
            incomeTaxRangeDetailVO.setCreateBy( getUserId( request, response ) );
            incomeTaxRangeDetailVO.setModifyBy( getUserId( request, response ) );
            incomeTaxRangeDetailVO.setAccountId( getAccountId( request, response ) );
            incomeTaxRangeDetailService.insertIncomeTaxRangeDetail( incomeTaxRangeDetailVO );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_DETAIL );

            // ˢ�³���
            constants.initIncomeTaxRange();
         }

         // ���Form
         ( ( IncomeTaxRangeDetailVO ) form ).reset();
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
         // ��ʼ��HeaderService�ӿ�
         final IncomeTaxRangeDetailService incomeTaxRangeDetailService = ( IncomeTaxRangeDetailService ) getService( "incomeTaxRangeDetailService" );
         final IncomeTaxRangeHeaderService incomeTaxRangeHeaderService = ( IncomeTaxRangeHeaderService ) getService( "incomeTaxRangeHeaderService" );
         // ��������ID
         final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
         // ���IncomeTaxRangeDetailVO����
         final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = incomeTaxRangeDetailService.getIncomeTaxRangeDetailVOByDetailId( detailId );
         // ���IncomeTaxRangeHeaderVO����
         final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = incomeTaxRangeHeaderService.getIncomeTaxRangeHeaderVOByHeaderId( incomeTaxRangeDetailVO.getHeaderId() );
         // ���ʻ���ֵ
         incomeTaxRangeDetailVO.reset( null, request );
         // �����޸����
         incomeTaxRangeDetailVO.setSubAction( BaseAction.VIEW_OBJECT );
         incomeTaxRangeDetailVO.setStatus( IncomeTaxRangeHeaderVO.TRUE );
         // ����request����
         request.setAttribute( "incomeTaxRangeHeaderForm", incomeTaxRangeHeaderVO );
         request.setAttribute( "incomeTaxRangeDetailForm", incomeTaxRangeDetailVO );

         // Ajax Form���ã�ֱ�Ӵ���Form JSP
         return mapping.findForward( "manageIncomeTaxRangeDetailForm" );
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
            // ��ʼ�� Service�ӿ�
            final IncomeTaxRangeDetailService incomeTaxRangeDetailService = ( IncomeTaxRangeDetailService ) getService( "incomeTaxRangeDetailService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );
            // �ӱ�ID
            final String detailId = KANUtil.decodeString( request.getParameter( "detailId" ) );
            // ��ȡIncomeTaxRangeDetailVO����
            final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = incomeTaxRangeDetailService.getIncomeTaxRangeDetailVOByDetailId( detailId );
            // װ�ؽ��洫ֵ
            incomeTaxRangeDetailVO.update( ( IncomeTaxRangeDetailVO ) form );
            // ��ȡ��¼�û�
            incomeTaxRangeDetailVO.setModifyBy( getUserId( request, response ) );
            // �����޸ķ���
            incomeTaxRangeDetailService.updateIncomeTaxRangeDetail( incomeTaxRangeDetailVO );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_DETAIL );

            // ˢ�³���
            constants.initIncomeTaxRange();
         }
         // ���Form
         ( ( IncomeTaxRangeDetailVO ) form ).reset();
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
         final IncomeTaxRangeDetailService incomeTaxRangeDetailService = ( IncomeTaxRangeDetailService ) getService( "incomeTaxRangeDetailService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );
         // ���Action Form
         IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = ( IncomeTaxRangeDetailVO ) form;
         // ����ѡ�е�ID
         if ( incomeTaxRangeDetailVO.getSelectedIds() != null && !incomeTaxRangeDetailVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : incomeTaxRangeDetailVO.getSelectedIds().split( "," ) )
            {
               // ��ȡ��Ҫɾ���Ķ���
               incomeTaxRangeDetailVO = incomeTaxRangeDetailService.getIncomeTaxRangeDetailVOByDetailId( selectedId );
               // ����ɾ���ӿ�
               incomeTaxRangeDetailVO.setDetailId( selectedId );
               incomeTaxRangeDetailVO.setAccountId( getAccountId( request, response ) );
               incomeTaxRangeDetailVO.setModifyBy( getUserId( request, response ) );
               incomeTaxRangeDetailService.deleteIncomeTaxRangeDetail( incomeTaxRangeDetailVO );
            }

            // ˢ�³���
            constants.initIncomeTaxRange();
         }

         // ���Selected IDs����Action
         incomeTaxRangeDetailVO.setSelectedIds( "" );
         incomeTaxRangeDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

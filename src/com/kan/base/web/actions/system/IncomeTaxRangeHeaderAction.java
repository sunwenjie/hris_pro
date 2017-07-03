package com.kan.base.web.actions.system;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.system.IncomeTaxRangeDetailVO;
import com.kan.base.domain.system.IncomeTaxRangeHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.IncomeTaxRangeHeaderService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class IncomeTaxRangeHeaderAction extends BaseAction
{

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
         final IncomeTaxRangeHeaderService incomeTaxRangeHeaderService = ( IncomeTaxRangeHeaderService ) getService( "incomeTaxRangeHeaderService" );
         // ���Action Form
         final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = ( IncomeTaxRangeHeaderVO ) form;

         // ����subAction
         dealSubAction( incomeTaxRangeHeaderVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder incomeTaxRangeHeaderHolder = new PagedListHolder();
         // ���뵱ǰҳ
         incomeTaxRangeHeaderHolder.setPage( page );
         // ���뵱ǰֵ����
         incomeTaxRangeHeaderHolder.setObject( incomeTaxRangeHeaderVO );
         // ����ҳ���¼����
         incomeTaxRangeHeaderHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         incomeTaxRangeHeaderService.getIncomeTaxRangeHeaderVOsByCondition( incomeTaxRangeHeaderHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( incomeTaxRangeHeaderHolder, request );

         // Holder��д��Request����
         request.setAttribute( "incomeTaxRangeHeaderHolder", incomeTaxRangeHeaderHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���IncomeTaxRangeHeader JSP
            return mapping.findForward( "listIncomeTaxRangeHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listIncomeTaxRangeHeader" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( IncomeTaxRangeHeaderVO ) form ).setStatus( IncomeTaxRangeHeaderVO.TRUE );
      ( ( IncomeTaxRangeHeaderVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����  
      return mapping.findForward( "manageIncomeTaxRangeHeader" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         final IncomeTaxRangeDetailVO incomeTaxRangeDetailVO = new IncomeTaxRangeDetailVO();
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ�� Service�ӿ�
            final IncomeTaxRangeHeaderService incomeTaxRangeHeaderService = ( IncomeTaxRangeHeaderService ) getService( "incomeTaxRangeHeaderService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // ��õ�ǰFORM
            final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = ( IncomeTaxRangeHeaderVO ) form;
            // Checkbox����
            incomeTaxRangeHeaderVO.setIsDefault( ( incomeTaxRangeHeaderVO.getIsDefault() != null && incomeTaxRangeHeaderVO.getIsDefault().equalsIgnoreCase( "on" ) ) ? BaseVO.TRUE
                  : BaseVO.FALSE );
            incomeTaxRangeHeaderVO.setCreateBy( getUserId( request, response ) );
            incomeTaxRangeHeaderVO.setModifyBy( getUserId( request, response ) );
            incomeTaxRangeHeaderService.insertIncomeTaxRangeHeader( incomeTaxRangeHeaderVO );
            incomeTaxRangeDetailVO.setHeaderId( ( ( IncomeTaxRangeHeaderVO ) form ).getHeaderId() );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_ADD, null, MESSAGE_HEADER );

            // ˢ�³���
            constants.initIncomeTaxRange();
         }
         else
         {
            // �����ظ���ǵı��
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );
            // ���Form
            ( ( IncomeTaxRangeHeaderVO ) form ).reset();

            return list_object( mapping, form, request, response );
         }

         return new IncomeTaxRangeDetailAction().list_object( mapping, incomeTaxRangeDetailVO, request, response );
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
            final IncomeTaxRangeHeaderService incomeTaxRangeHeaderService = ( IncomeTaxRangeHeaderService ) getService( "incomeTaxRangeHeaderService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // ������ȡ�����
            final String headerId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ��ȡIncomeTaxRangeHeaderVO����
            final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = incomeTaxRangeHeaderService.getIncomeTaxRangeHeaderVOByHeaderId( headerId );
            // װ�ؽ��洫ֵ
            incomeTaxRangeHeaderVO.update( ( IncomeTaxRangeHeaderVO ) form );
            // ��ȡ��¼�û�
            incomeTaxRangeHeaderVO.setModifyBy( getUserId( request, response ) );
            // Checkbox����
            incomeTaxRangeHeaderVO.setIsDefault( ( incomeTaxRangeHeaderVO.getIsDefault() != null && incomeTaxRangeHeaderVO.getIsDefault().equalsIgnoreCase( "on" ) ) ? BaseVO.TRUE
                  : BaseVO.FALSE );
            // �����޸ķ���
            incomeTaxRangeHeaderService.updateIncomeTaxRangeHeader( incomeTaxRangeHeaderVO );

            // ���ر���ɹ��ı��
            success( request, MESSAGE_TYPE_UPDATE, null, MESSAGE_HEADER );

            // ˢ�³���
            constants.initIncomeTaxRange();
         }
         // ���Form
         ( ( IncomeTaxRangeHeaderVO ) form ).reset();

         return new IncomeTaxRangeDetailAction().list_object( mapping, new IncomeTaxRangeDetailVO(), request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
         final IncomeTaxRangeHeaderService incomeTaxRangeHeaderService = ( IncomeTaxRangeHeaderService ) getService( "incomeTaxRangeHeaderService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );
         // ���Action Form
         IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = ( IncomeTaxRangeHeaderVO ) form;
         // ����ѡ�е�ID
         if ( incomeTaxRangeHeaderVO.getSelectedIds() != null && !incomeTaxRangeHeaderVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : incomeTaxRangeHeaderVO.getSelectedIds().split( "," ) )
            {
               // ���Ҫɾ���Ķ���
               incomeTaxRangeHeaderVO = incomeTaxRangeHeaderService.getIncomeTaxRangeHeaderVOByHeaderId( selectedId );
               // ����ɾ���ӿ�
               incomeTaxRangeHeaderVO.setHeaderId( selectedId );
               incomeTaxRangeHeaderVO.setAccountId( getAccountId( request, response ) );
               incomeTaxRangeHeaderVO.setModifyBy( getUserId( request, response ) );
               incomeTaxRangeHeaderService.deleteIncomeTaxRangeHeader( incomeTaxRangeHeaderVO );
            }

            // ˢ�³���
            constants.initIncomeTaxRange();
         }

         // ���Selected IDs����Action
         incomeTaxRangeHeaderVO.setSelectedIds( "" );
         incomeTaxRangeHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

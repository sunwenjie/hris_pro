package com.kan.base.web.actions.system;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.BaseVO;
import com.kan.base.domain.system.IncomeTaxBaseVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.IncomeTaxBaseService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;

public class IncomeTaxBaseAction extends BaseAction
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
         final IncomeTaxBaseService incomeTaxBaseService = ( IncomeTaxBaseService ) getService( "incomeTaxBaseService" );
         // ���Action Form
         final IncomeTaxBaseVO incomeTaxBaseVO = ( IncomeTaxBaseVO ) form;

         // ����subAction
         dealSubAction( incomeTaxBaseVO, mapping, form, request, response );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder incomeTaxBaseHolder = new PagedListHolder();
         // ���뵱ǰҳ
         incomeTaxBaseHolder.setPage( page );
         // ���뵱ǰֵ����
         incomeTaxBaseHolder.setObject( incomeTaxBaseVO );
         // ����ҳ���¼����
         incomeTaxBaseHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         incomeTaxBaseService.getIncomeTaxBaseVOsByCondition( incomeTaxBaseHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( incomeTaxBaseHolder, request );

         // Holder��д��Request����
         request.setAttribute( "incomeTaxBaseHolder", incomeTaxBaseHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // Ajax Table���ã�ֱ�Ӵ���IncomeTaxBase JSP
            return mapping.findForward( "listIncomeTaxBaseTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listIncomeTaxBase" );
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ������趨һ���Ǻţ���ֹ�ظ��ύ
      this.saveToken( request );

      // ����Sub Action
      ( ( IncomeTaxBaseVO ) form ).setStatus( IncomeTaxBaseVO.TRUE );
      ( ( IncomeTaxBaseVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����  
      return mapping.findForward( "manageIncomeTaxBase" );
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
            final IncomeTaxBaseService incomeTaxBaseService = ( IncomeTaxBaseService ) getService( "incomeTaxBaseService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // ��õ�ǰFORM
            final IncomeTaxBaseVO incomeTaxBaseVO = ( IncomeTaxBaseVO ) form;
            // Checkbox����
            incomeTaxBaseVO.setIsDefault( ( incomeTaxBaseVO.getIsDefault() != null && incomeTaxBaseVO.getIsDefault().equalsIgnoreCase( "on" ) ) ? BaseVO.TRUE : BaseVO.FALSE );
            incomeTaxBaseVO.setCreateBy( getUserId( request, response ) );
            incomeTaxBaseVO.setModifyBy( getUserId( request, response ) );
            incomeTaxBaseService.insertIncomeTaxBase( incomeTaxBaseVO );

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            // ˢ�³���
            constants.initIncomeTaxBase();
         }
         else
         {
            // �����ظ��ύ���
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            // ���Form
            ( ( IncomeTaxBaseVO ) form ).reset();

            return list_object( mapping, form, request, response );
         }

         return to_objectModify( mapping, form, request, response );
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
      try
      {
         // ������趨һ���Ǻţ���ֹ�ظ��ύ
         this.saveToken( request );
         // ��ʼ��Service�ӿ�
         final IncomeTaxBaseService incomeTaxBaseService = ( IncomeTaxBaseService ) getService( "incomeTaxBaseService" );

         // ������ȡ
         String baseId = request.getParameter( "id" );
         if ( baseId == null || baseId.trim().isEmpty() )
         {
            baseId = ( ( IncomeTaxBaseVO ) form ).getBaseId();
         }
         else
         {
            baseId = Cryptogram.decodeString( URLDecoder.decode( baseId, "UTF-8" ) );
         }

         // ���IncomeTaxBaseVO����
         final IncomeTaxBaseVO incomeTaxBaseVO = incomeTaxBaseService.getIncomeTaxBaseVOByBaseId( baseId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         incomeTaxBaseVO.reset( null, request );
         // �����޸����
         incomeTaxBaseVO.setSubAction( VIEW_OBJECT );
         // Checkbox����
         incomeTaxBaseVO.setIsDefault( ( incomeTaxBaseVO.getIsDefault() != null && incomeTaxBaseVO.getIsDefault().equalsIgnoreCase( BaseVO.TRUE ) ) ? "on" : "" );
         // д��request����
         request.setAttribute( "incomeTaxBaseForm", incomeTaxBaseVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageIncomeTaxBase" );
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
            final IncomeTaxBaseService incomeTaxBaseService = ( IncomeTaxBaseService ) getService( "incomeTaxBaseService" );
            final KANConstants constants = ( KANConstants ) getService( "constants" );

            // ������ȡ�����
            final String baseId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ��ȡIncomeTaxBaseVO����
            final IncomeTaxBaseVO incomeTaxBaseVO = incomeTaxBaseService.getIncomeTaxBaseVOByBaseId( baseId );
            // װ�ؽ��洫ֵ
            incomeTaxBaseVO.update( ( IncomeTaxBaseVO ) form );
            // ��ȡ��¼�û�
            incomeTaxBaseVO.setModifyBy( getUserId( request, response ) );
            // Checkbox����
            incomeTaxBaseVO.setIsDefault( ( incomeTaxBaseVO.getIsDefault() != null && incomeTaxBaseVO.getIsDefault().equalsIgnoreCase( "on" ) ) ? BaseVO.TRUE : BaseVO.FALSE );
            // �����޸ķ���
            incomeTaxBaseService.updateIncomeTaxBase( incomeTaxBaseVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            // ˢ�³���
            constants.initIncomeTaxBase();
         }
         // ���Form
         ( ( IncomeTaxBaseVO ) form ).reset();

         return to_objectModify( mapping, form, request, response );
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
         final IncomeTaxBaseService incomeTaxBaseService = ( IncomeTaxBaseService ) getService( "incomeTaxBaseService" );
         final KANConstants constants = ( KANConstants ) getService( "constants" );

         // ���Action Form
         IncomeTaxBaseVO incomeTaxBaseVO = ( IncomeTaxBaseVO ) form;
         // ����ѡ�е�ID
         if ( incomeTaxBaseVO.getSelectedIds() != null && !incomeTaxBaseVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : incomeTaxBaseVO.getSelectedIds().split( "," ) )
            {
               // ���Ҫɾ���Ķ���
               incomeTaxBaseVO = incomeTaxBaseService.getIncomeTaxBaseVOByBaseId( selectedId );
               // ����ɾ���ӿ�
               incomeTaxBaseVO.setBaseId( selectedId );
               incomeTaxBaseVO.setAccountId( getAccountId( request, response ) );
               incomeTaxBaseVO.setModifyBy( getUserId( request, response ) );
               incomeTaxBaseService.deleteIncomeTaxBase( incomeTaxBaseVO );
            }

            // ˢ�³���
            constants.initIncomeTaxBase();
         }

         // ���Selected IDs����Action
         incomeTaxBaseVO.setSelectedIds( "" );
         incomeTaxBaseVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

/*
 * Created on 2013-05-13
 */
package com.kan.base.web.actions.message;

import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.message.MessageTemplateVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.message.MessageTemplateService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

/**
 * @author Jixiang.hu
 */
public class MessageTemplateAction extends BaseAction
{

   public static String accessAction = "HRO_MESSAGE_TEMPLATE";

   /**
    * List MessageTemplate
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final MessageTemplateService messageTemplateService = ( MessageTemplateService ) getService( "messageTemplateService" );
         // ���Action Form
         final MessageTemplateVO messageTemplateVO = ( MessageTemplateVO ) form;

         // �����Action��ɾ����Ϣģ��
         if ( messageTemplateVO != null && messageTemplateVO.getSubAction() != null && messageTemplateVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // ����ɾ����Ϣģ���Action
            delete_objectList( mapping, form, request, response );
         }

         // ���û��ָ��������Ĭ�ϰ�employeeId����
         if ( messageTemplateVO.getSortColumn() == null || messageTemplateVO.getSortColumn().isEmpty() )
         {
            messageTemplateVO.setSortColumn( "templateId" );
            messageTemplateVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder messageTemplateHolder = new PagedListHolder();

         // ���뵱ǰҳ
         messageTemplateHolder.setPage( page );
         // ���뵱ǰֵ����
         messageTemplateHolder.setObject( messageTemplateVO );
         // ����ҳ���¼����
         messageTemplateHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         messageTemplateService.getMessageTemplateVOsByCondition( messageTemplateHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( messageTemplateHolder, request );

         request.setAttribute( "messageTemplateHolder", messageTemplateHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listMessageTemplateTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listMessageTemplate" );
   }

   /**
    * To messageTemplate modify page
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ���ҳ��Token
         this.saveToken( request );

         // ��ʼ��Service�ӿ�
         final MessageTemplateService messageTemplateService = ( MessageTemplateService ) getService( "messageTemplateService" );
         // ��õ�ǰ����
         String templateId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( templateId ) != null )
         {
            templateId = Cryptogram.decodeString( URLDecoder.decode( templateId, "UTF-8" ) );
         }
         else
         {
            templateId = ( ( MessageTemplateVO ) form ).getTemplateId();
         }
         // ���������Ӧ����
         MessageTemplateVO messageTemplateVO = messageTemplateService.getMessageTemplateVOByTemplateId( templateId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         messageTemplateVO.reset( null, request );

         messageTemplateVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "messageTemplateForm", messageTemplateVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageMessageTemplate" );
   }

   /**
    * To messageTemplate new page
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ����Sub Action
      ( ( MessageTemplateVO ) form ).setContentType( "1" );
      ( ( MessageTemplateVO ) form ).setStatus( MessageTemplateVO.TRUE );
      ( ( MessageTemplateVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageMessageTemplate" );
   }

   /**
    * Add messageTemplate
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // ���ActionForm
      final MessageTemplateVO messageTemplateVO = ( MessageTemplateVO ) form;

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final MessageTemplateService messageTemplateService = ( MessageTemplateService ) getService( "messageTemplateService" );
            // ��ȡ��¼�û�  - ���ô����û�id
            messageTemplateVO.setCreateBy( getUserId( request, response ) );
            // ��ȡ��¼�û��˻�  �����˻�id
            messageTemplateVO.setAccountId( getAccountId( request, response ) );
            messageTemplateVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            messageTemplateService.insertMessageTemplate( messageTemplateVO );
            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );
            insertlog( request, messageTemplateVO, Operate.ADD, messageTemplateVO.getTemplateId(), null );
         }

         // ���Form����
         ( ( MessageTemplateVO ) form ).reset();

         constantsInit( "initMessageTemplate", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, messageTemplateVO, request, response );
   }

   /**
    * Modify messageTemplate
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final MessageTemplateService messageTemplateService = ( MessageTemplateService ) getService( "messageTemplateService" );
            // ��õ�ǰ����
            final String templateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // ���������Ӧ����
            final MessageTemplateVO messageTemplateVO = messageTemplateService.getMessageTemplateVOByTemplateId( templateId );
            // װ�ؽ��洫ֵ
            messageTemplateVO.update( ( MessageTemplateVO ) form );
            // ��ȡ��¼�û� �����޸��˻�id
            messageTemplateVO.setModifyBy( getUserId( request, response ) );

            // �޸Ķ���
            messageTemplateService.updateMessageTemplate( messageTemplateVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, messageTemplateVO, Operate.MODIFY, messageTemplateVO.getTemplateId(), null );
         }

         constantsInit( "initMessageTemplate", getAccountId( request, response ) );
         // ���Form����
         ( ( MessageTemplateVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify messageTemplate
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward getMessageTemplateContent_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // ��ʼ��PrintWrite����
         final PrintWriter out = response.getWriter();

         // ��ʼ��Service�ӿ�
         final MessageTemplateService messageTemplateService = ( MessageTemplateService ) getService( "messageTemplateService" );
         // ��õ�ǰ����
         final String templateId = request.getParameter( "templateId" );
         // ���������Ӧ����
         final MessageTemplateVO messageTemplateVO = messageTemplateService.getMessageTemplateVOByTemplateId( templateId );

         out.println( KANUtil.NoHTML( messageTemplateVO.getContent() ) );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   /**
    * Delete messageTemplate
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final MessageTemplateService messageTemplateService = ( MessageTemplateService ) getService( "messageTemplateService" );
         // ��õ�ǰ����
         final String templateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateId" ), "UTF-8" ) );

         // ɾ��������Ӧ����
         messageTemplateService.deleteMessageTemplate( templateId );

         insertlog( request, form, Operate.DELETE, templateId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete messageTemplate list
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final MessageTemplateService messageTemplateService = ( MessageTemplateService ) getService( "messageTemplateService" );
         // ���Action Form
         MessageTemplateVO messageTemplateVO = ( MessageTemplateVO ) form;
         // ����ѡ�е�ID
         if ( messageTemplateVO.getSelectedIds() != null && !messageTemplateVO.getSelectedIds().equals( "" ) )
         {
            // �ָ�
            for ( String selectedId : messageTemplateVO.getSelectedIds().split( "," ) )
            {
               // ����ɾ���ӿ�
               messageTemplateService.deleteMessageTemplate( selectedId );
            }

            insertlog( request, messageTemplateVO, Operate.DELETE, null, messageTemplateVO.getSelectedIds() );
         }
         // ���Selected IDs����Action
         messageTemplateVO.setSelectedIds( "" );
         messageTemplateVO.setSubAction( "" );

         constantsInit( "initMessageTemplate", getAccountId( request, response ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}
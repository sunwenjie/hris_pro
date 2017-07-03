/*
 * Created on 2013-05-07
 */
package com.kan.base.web.actions.message;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.message.MessageSmsVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.message.MessageSmsService;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

/**
 * @author Jixiang Hu
 */
public class MessageSmsAction extends BaseAction
{

   public static String accessAction = "HRO_MESSAGE_SMS";

   /**
    * List messageSms
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
         final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
         // ���Action Form
         final MessageSmsVO messageSmsVO = ( MessageSmsVO ) form;

         boolean actionIsNotNull = messageSmsVO != null && messageSmsVO.getSubAction() != null;
         // �����Action��ɾ������
         boolean isDelete = actionIsNotNull && messageSmsVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS );
         boolean isCancleSend = actionIsNotNull && messageSmsVO.getSubAction().equalsIgnoreCase( "cancelSend" );
         boolean isContinueSend = actionIsNotNull && messageSmsVO.getSubAction().equalsIgnoreCase( "continueSend" );
         if ( isDelete )
         {
            // ����ɾ�����ŵ�Action
            delete_objectList( mapping, form, request, response );
         }
         else if ( isCancleSend )
         {
            // ����ȡ�����Ͷ��ŵ�Action
            sendCancel( mapping, form, request, response );
         }
         else if ( isContinueSend )
         {
            // ���ü��������ŵ�Action
            sendContinue( mapping, form, request, response );

         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder messageSmsHolder = new PagedListHolder();
         // ���뵱ǰҳ
         messageSmsHolder.setPage( page );
         // ���뵱ǰֵ����
         messageSmsHolder.setObject( messageSmsVO );
         // ����ҳ���¼����
         messageSmsHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         messageSmsService.getMessageSmsVOsByCondition( messageSmsHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( messageSmsHolder, request );

         request.setAttribute( "messageSmsHolder", messageSmsHolder );

         // �����ajax�������ɾ����������ת��Table�������ֶ�Ӧ��jsp
         if ( new Boolean( ajax ) || isDelete )
         {
            return mapping.findForward( "listMessageSmsTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listMessageSms" );
   }

   /**
    * To messageSms modify page
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
         final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
         // ��õ�ǰ����
         String smsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "smsId" ), "GBK" ) );
         // ���������Ӧ����
         MessageSmsVO messageSmsVO = messageSmsService.getMessageSmsVOBySmsId( smsId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         messageSmsVO.reset( null, request );

         messageSmsVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "messageSmsForm", messageSmsVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageMessageSms" );
   }

   /**
    * To messageSms new page
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
      ( ( MessageSmsVO ) form ).setStatus( MessageSmsVO.TRUE );
      ( ( MessageSmsVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageMessageSms" );
   }

   /**
    * Add messageSms
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

      try
      {
         // �����ظ��ύ
         if ( this.isTokenValid( request, true ) )
         {
            // ��ʼ��Service�ӿ�
            final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
            // ���ActionForm
            final MessageSmsVO messageSmsVO = ( MessageSmsVO ) form;
            // ��ȡ��¼�û�  - ���ô����û�id
            messageSmsVO.setCreateBy( getUserId( request, response ) );
            messageSmsVO.setAccountId( getAccountId( request, response ) );
            // ����systemId
            messageSmsVO.setSystemId( KANConstants.SYSTEM_ID );
            messageSmsVO.setModifyBy( getUserId( request, response ) );
            // �½�����
            final String[] userIds = request.getParameterValues( "userIds" );
            final String[] positionIds = request.getParameterValues( "positionIds" );
            final String[] groupIds = request.getParameterValues( "groupIds" );
            final String[] branchIds = request.getParameterValues( "branchIds" );
            final String[] extendAccounts = request.getParameterValues( "extends" );
            final String[] positionGradeIds = request.getParameterValues( "positionGradeIds" );
            // ��ʼ��staffService
            final StaffService staffService = ( StaffService ) getService( "staffService" );
            final List< Object > staffVOObjects = staffService.getStaffVOsByIds( getAccountId( request, response ), userIds, positionIds, groupIds, branchIds, positionGradeIds );
            final List< String > smses = new ArrayList< String >();
            StaffVO staffVO = null;
            for ( Object obj : staffVOObjects )
            {
               staffVO = ( StaffVO ) obj;
               if ( KANUtil.filterEmpty( staffVO.getBizMobile() ) != null )
               {
                  smses.add( staffVO.getBizMobile() );
               }
               else if ( KANUtil.filterEmpty( staffVO.getBizPhone() ) != null )
               {
                  smses.add( staffVO.getBizPhone() );
               }
            }
            // ����û��ֶ�����
            if ( extendAccounts != null && extendAccounts.length > 0 )
            {
               for ( String sms : extendAccounts )
               {
                  smses.add( sms );
               }
            }
            // ȥ�ظ�
            final List< String > distinctSmses = KANUtil.getDistinctList( smses );
            for ( String sms : distinctSmses )
            {
               messageSmsVO.setReception( sms );
               messageSmsService.insertMessageSms( messageSmsVO );
            }

            // ������ӳɹ����
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, messageSmsVO, Operate.ADD, messageSmsVO.getSmsId(), null );
         }
         // ���Form����
         ( ( MessageSmsVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify messageSms
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
            final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
            // ��õ�ǰ����
            final String smsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "smsId" ), "GBK" ) );
            // ���������Ӧ����
            final MessageSmsVO messageSmsVO = messageSmsService.getMessageSmsVOBySmsId( smsId );
            MessageSmsVO formVO = ( MessageSmsVO ) form;
            // Ŀ��״̬�͵�ǰ״̬��ת�� �߼��ж�
            String status = messageSmsVO.getStatus();
            String targetStatus = formVO.getStatus();
            if ( "4".equals( targetStatus ) && "1".equals( status ) )
            {
               messageSmsVO.setStatus( "4" );
            }
            else if ( "1".equals( targetStatus ) && "4".equals( status ) )
            {
               messageSmsVO.setStatus( "1" );
            }
            // װ�ؽ��洫ֵ
            messageSmsVO.update( ( MessageSmsVO ) form );
            // ��ȡ��¼�û� �����޸��˻�id
            messageSmsVO.setModifyBy( getUserId( request, response ) );
            // ����systemId
            messageSmsVO.setSystemId( KANConstants.SYSTEM_ID );
            // �޸Ķ���
            final String[] smses = request.getParameterValues( "smses" );
            for ( String sms : smses )
            {
               messageSmsVO.setReception( sms );
               messageSmsService.updateMessageSms( messageSmsVO );
            }

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, messageSmsVO, Operate.MODIFY, messageSmsVO.getSmsId(), null );
         }

         // ���Form����
         ( ( MessageSmsVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete messageSms
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
         final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
         // ��õ�ǰ����
         final String smsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "smsId" ), "GBK" ) );

         // ɾ��������Ӧ����
         messageSmsService.deleteMessageSmsBySmsId( getUserId( request, response ), smsId );

         insertlog( request, form, Operate.DELETE, smsId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete messageSms list
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
         final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
         // ���Action Form
         MessageSmsVO messageSmsVO = ( MessageSmsVO ) form;
         // ����ѡ�е�ID
         if ( messageSmsVO.getSelectedIds() != null && !messageSmsVO.getSelectedIds().equals( "" ) )
         {
            // ����ɾ���ӿ�
            messageSmsService.deleteMessageSmsBySmsId( getUserId( request, response ), messageSmsVO.getSelectedIds().split( "," ) );

            insertlog( request, form, Operate.DELETE, null, messageSmsVO.getSelectedIds() );
         }
         // ���Selected IDs����Action
         messageSmsVO.setSelectedIds( "" );
         messageSmsVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Modify messageSms
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void sendCancel( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
         // ��õ�ǰ����
         final String smsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "smsId" ), "GBK" ) );
         // ���������Ӧ����
         final MessageSmsVO messageSmsVO = messageSmsService.getMessageSmsVOBySmsId( smsId );
         // Ŀ��״̬�͵�ǰ״̬��ת�� �߼��ж�
         if ( "1".equals( messageSmsVO.getStatus() ) )
         {
            // ���ö���״̬Ϊ����ͣ����
            messageSmsVO.setStatus( "4" );
         }

         // ��ȡ��¼�û� �����޸��˻�id
         messageSmsVO.setModifyBy( getUserId( request, response ) );
         // �޸Ķ���
         messageSmsService.updateMessageSms( messageSmsVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   /**
    * Modify messageSms
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void sendContinue( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
         // ��õ�ǰ����
         final String smsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "smsId" ), "GBK" ) );
         // ���������Ӧ����
         final MessageSmsVO messageSmsVO = messageSmsService.getMessageSmsVOBySmsId( smsId );
         if ( "4".equals( messageSmsVO.getStatus() ) )
         {
            // ���ö���״̬Ϊ��������
            messageSmsVO.setStatus( "1" );
         }
         // ��ȡ��¼�û� �����޸��˻�id
         messageSmsVO.setModifyBy( getUserId( request, response ) );
         // �޸Ķ���
         messageSmsService.updateMessageSms( messageSmsVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   /**
    * To messageSms new page
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward load_sysMsgInfoCount( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // ���ҳ��Token
      this.saveToken( request );

      // ����Sub Action
      ( ( MessageSmsVO ) form ).setStatus( MessageSmsVO.TRUE );
      ( ( MessageSmsVO ) form ).setSubAction( CREATE_OBJECT );

      // ��ת���½�����
      return mapping.findForward( "manageMessageSms" );
   }

}
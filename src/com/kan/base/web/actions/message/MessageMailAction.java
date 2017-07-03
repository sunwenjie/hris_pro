/*
 * Created on 2013-05-07
 */
package com.kan.base.web.actions.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.message.MessageMailVO;
import com.kan.base.domain.message.MessageRegularVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.message.MessageMailService;
import com.kan.base.service.inf.message.MessageRegularService;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;

/**
 * @author Jixiang Hu
 */
public class MessageMailAction extends BaseAction
{

   public static String accessAction = "HRO_MESSAGE_MAIL";

   /**
    * List messageMail
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
         final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
         // ���Action Form
         final MessageMailVO messageMailVO = ( MessageMailVO ) form;

         boolean actionIsNotNull = messageMailVO != null && messageMailVO.getSubAction() != null;
         // �����Action��ɾ���ʼ�
         boolean isDelete = actionIsNotNull && messageMailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS );
         boolean isCancleSend = actionIsNotNull && messageMailVO.getSubAction().equalsIgnoreCase( "cancelSend" );
         boolean isContinueSend = actionIsNotNull && messageMailVO.getSubAction().equalsIgnoreCase( "continueSend" );
         if ( isDelete )
         {
            // ����ɾ���ʼ���Action
            delete_objectList( mapping, form, request, response );
         }
         else if ( isCancleSend )
         {
            // ����ȡ�������ʼ���Action
            sendCancel( mapping, form, request, response );
         }
         else if ( isContinueSend )
         {
            // ���ü������ʼ���Action
            sendContinue( mapping, form, request, response );

         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( messageMailVO );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder messageMailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         messageMailHolder.setPage( page );
         // ���뵱ǰֵ����
         messageMailHolder.setObject( messageMailVO );
         // ����ҳ���¼����
         messageMailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         messageMailService.getMessageMailVOsByCondition( messageMailHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( messageMailHolder, request );

         request.setAttribute( "messageMailHolder", messageMailHolder );

         // �����ajax�������ɾ����������ת��Table�������ֶ�Ӧ��jsp
         if ( new Boolean( ajax ) || isDelete )
         {
            return mapping.findForward( "listMessageMailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listMessageMail" );
   }

   /**
    * To messageMail modify page
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
         final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
         // ��õ�ǰ����
         String mailId = KANUtil.decodeStringFromAjax( request.getParameter( "mailId" ) );
         // ���������Ӧ����
         MessageMailVO messageMailVO = messageMailService.getMessageMailVOByMailId( mailId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         messageMailVO.reset( null, request );

         messageMailVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "messageMailForm", messageMailVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageMessageMail" );
   }

   /**
    * To messageMail new page
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
      ( ( MessageMailVO ) form ).setStatus( MessageMailVO.TRUE );
      ( ( MessageMailVO ) form ).setSubAction( CREATE_OBJECT );
      // Ĭ�Ϻ���ʽ
      ( ( MessageMailVO ) form ).setContentType( "2" );

      // ��ת���½�����
      return mapping.findForward( "manageMessageMail" );
   }

   /**
    * Add messageMail
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
            // ���ActionForm
            final MessageMailVO messageMailVO = ( MessageMailVO ) form;
            // ��ȡ��¼�û�  - ���ô����û�id
            messageMailVO.setCreateBy( getUserId( request, response ) );
            messageMailVO.setAccountId( getAccountId( request, response ) );
            // ����systemId
            messageMailVO.setSystemId( getSystemId( request, response ) );
            messageMailVO.setModifyBy( getUserId( request, response ) );
            final String sendType = messageMailVO.getSendType();
            // ��������ģʽ
            if ( "1".equals( sendType ) )
            {
               sendNow( messageMailVO, request );
            }
            else if ( "2".equals( sendType ) )
            {
               sendDelayed( messageMailVO, request );
            }
            else if ( "3".equals( sendType ) )
            {
               sendCircle( messageMailVO, request );
            }

            insertlog( request, messageMailVO, Operate.ADD, messageMailVO.getMailId(), null );
         }

         // ���Form����
         ( ( MessageMailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify messageMail
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
            final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
            // ��õ�ǰ����
            final String mailId = KANUtil.decodeStringFromAjax( request.getParameter( "mailId" ) );
            // ���������Ӧ����
            final MessageMailVO messageMailVO = messageMailService.getMessageMailVOByMailId( mailId );
            MessageMailVO formVO = ( MessageMailVO ) form;

            MessageRegularVO messageRegularVO = creatRegular( formVO, request );
            if ( messageRegularVO != null )
            {
               final MessageRegularService messageRegularService = ( MessageRegularService ) getService( "messageRegularService" );
               messageRegularVO.setAccountId( getAccountId( request, null ) );
               messageRegularVO.setSystemId( getSystemId( request, response ) );
               messageRegularVO.setClientId( getUserId( request, null ) );
               messageRegularVO.setCorpId( getUserId( request, null ) );
               messageRegularVO.setCreateBy( getUserId( request, null ) );
               messageRegularVO.setCreateDate( new Date() );
               messageRegularVO.setModifyBy( getUserId( request, null ) );
               messageRegularVO.setModifyDate( new Date() );
               messageRegularService.insertMessageRegular( messageRegularVO );
               messageMailVO.setRegularId( messageRegularVO.getRegularId() );
            }

            // Ŀ��״̬�͵�ǰ״̬��ת�� �߼��ж�
            String status = messageMailVO.getStatus();
            String targetStatus = formVO.getStatus();
            if ( "4".equals( targetStatus ) && "1".equals( status ) )
            {
               messageMailVO.setStatus( "4" );
            }
            else if ( "1".equals( targetStatus ) && "4".equals( status ) )
            {
               messageMailVO.setStatus( "1" );
            }
            // װ�ؽ��洫ֵ
            messageMailVO.update( formVO );
            // ��ȡ��¼�û� �����޸��˻�id
            messageMailVO.setModifyBy( getUserId( request, response ) );
            // ����systemId
            messageMailVO.setSystemId( getSystemId( request, response ) );
            // �޸Ķ���
            messageMailService.updateMessageMail( messageMailVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, messageMailVO, Operate.MODIFY, messageMailVO.getMailId(), null );
         }
         else
         {
            log.debug( "###########�ظ��ύ" );
         }

         // ���Form����
         ( ( MessageMailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify messageMail
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
         final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
         // ��õ�ǰ����
         final String mailId = KANUtil.decodeStringFromAjax( request.getParameter( "mailId" ) );
         // ���������Ӧ����
         final MessageMailVO messageMailVO = messageMailService.getMessageMailVOByMailId( mailId );
         // Ŀ��״̬�͵�ǰ״̬��ת�� �߼��ж�
         if ( "1".equals( messageMailVO.getStatus() ) )
         {
            // �����ʼ�״̬Ϊ����ͣ����
            messageMailVO.setStatus( "4" );
         }

         // ��ȡ��¼�û� �����޸��˻�id
         messageMailVO.setModifyBy( getUserId( request, response ) );
         // �޸Ķ���
         messageMailService.updateMessageMail( messageMailVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   /**
    * Modify messageMail
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
         final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
         // ��õ�ǰ����
         final String mailId = KANUtil.decodeStringFromAjax( request.getParameter( "mailId" ) );
         // ���������Ӧ����
         final MessageMailVO messageMailVO = messageMailService.getMessageMailVOByMailId( mailId );
         if ( "4".equals( messageMailVO.getStatus() ) )
         {
            // �����ʼ�״̬Ϊ��������
            messageMailVO.setStatus( "1" );
         }
         // ��ȡ��¼�û� �����޸��˻�id
         messageMailVO.setModifyBy( getUserId( request, response ) );
         // �޸Ķ���
         messageMailService.updateMessageMail( messageMailVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   /**
    * Delete messageMail
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
         final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
         // ��õ�ǰ����
         final String mailId = KANUtil.decodeStringFromAjax( request.getParameter( "mailId" ) );

         // ɾ��������Ӧ����
         messageMailService.deleteMessageMailByMailId( getUserId( request, response ), mailId );

         insertlog( request, form, Operate.DELETE, mailId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete messageMail list
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
         final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
         // ���Action Form
         MessageMailVO messageMailVO = ( MessageMailVO ) form;
         // ����ѡ�е�ID
         if ( messageMailVO.getSelectedIds() != null && !messageMailVO.getSelectedIds().equals( "" ) )
         {
            messageMailService.deleteMessageMailByMailId( getUserId( request, response ), messageMailVO.getSelectedIds().split( "," ) );

            insertlog( request, form, Operate.DELETE, null, messageMailVO.getSelectedIds() );
         }

         // ���Selected IDs����Action
         messageMailVO.setSelectedIds( "" );
         messageMailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // ��������
   protected void sendNow( final MessageMailVO messageMailVO, final HttpServletRequest request ) throws KANException
   {
      // ��ʼ��Service�ӿ�
      final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
      messageMailVO.setStatus( "1" );

      // �½�����
      final String[] userIds = request.getParameterValues( "userIds" );
      final String[] positionIds = request.getParameterValues( "positionIds" );
      final String[] groupIds = request.getParameterValues( "groupIds" );
      final String[] branchIds = request.getParameterValues( "branchIds" );
      final String[] extendAccounts = request.getParameterValues( "extends" );
      final String[] positionGradeIds = request.getParameterValues( "positionGradeIds" );
      // ��ʼ��staffService
      final StaffService staffService = ( StaffService ) getService( "staffService" );
      final List< Object > staffVOObjects = staffService.getStaffVOsByIds( getAccountId( request, null ), userIds, positionIds, groupIds, branchIds, positionGradeIds );
      final List< String > emails = new ArrayList< String >();
      StaffVO staffVO = null;
      for ( Object obj : staffVOObjects )
      {
         staffVO = ( StaffVO ) obj;
         if ( KANUtil.filterEmpty( staffVO.getBizEmail() ) != null )
         {
            emails.add( staffVO.getBizEmail() );
         }
         else if ( KANUtil.filterEmpty( staffVO.getPersonalEmail() ) != null )
         {
            emails.add( staffVO.getPersonalEmail() );
         }
      }
      // ����û��ֶ�����
      if ( extendAccounts != null && extendAccounts.length > 0 )
      {
         for ( String email : extendAccounts )
         {
            emails.add( email );
         }
      }
      // ȥ�ظ�
      final List< String > distinctEmails = KANUtil.getDistinctList( emails );
      for ( String email : distinctEmails )
      {
         messageMailVO.setReception( email );
         messageMailService.insertMessageMail( messageMailVO, getAccountId( request, null ) );
      }

      // ������ӳɹ����
      success( request, MESSAGE_TYPE_ADD );
   }

   // ��ʱ����
   protected void sendDelayed( final MessageMailVO messageMailVO, final HttpServletRequest request ) throws KANException
   {
      //  �ȷŵ��ݸ���5�����˹涨ʱ���Զ��޸�״̬Ϊ1
      // ��ʼ��Service�ӿ�
      final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
      final String[] toSendTimes = request.getParameterValues( "toSendTimes" );

      // �½�����
      final String[] userIds = request.getParameterValues( "userIds" );
      final String[] positionIds = request.getParameterValues( "positionIds" );
      final String[] groupIds = request.getParameterValues( "groupIds" );
      final String[] branchIds = request.getParameterValues( "branchIds" );
      final String[] extendAccounts = request.getParameterValues( "extends" );
      final String[] positionGradeIds = request.getParameterValues( "positionGradeIds" );
      // ��ʼ��staffService
      final StaffService staffService = ( StaffService ) getService( "staffService" );
      final List< Object > staffVOObjects = staffService.getStaffVOsByIds( getAccountId( request, null ), userIds, positionIds, groupIds, branchIds, positionGradeIds );
      final List< String > emails = new ArrayList< String >();
      StaffVO staffVO = null;
      for ( Object obj : staffVOObjects )
      {
         staffVO = ( StaffVO ) obj;
         if ( KANUtil.filterEmpty( staffVO.getBizEmail() ) != null )
         {
            emails.add( staffVO.getBizEmail() );
         }
         else if ( KANUtil.filterEmpty( staffVO.getPersonalEmail() ) != null )
         {
            emails.add( staffVO.getPersonalEmail() );
         }
      }
      // ����û��ֶ�����
      if ( extendAccounts != null && extendAccounts.length > 0 )
      {
         for ( String email : extendAccounts )
         {
            emails.add( email );
         }
      }
      // ȥ�ظ�
      final List< String > distinctEmails = KANUtil.getDistinctList( emails );
      for ( String email : distinctEmails )
      {
         messageMailVO.setReception( email );
         for ( String toSendTime : toSendTimes )
         {
            messageMailVO.setToSendTime( toSendTime );
            //  ��ӵ��ݸ���
            messageMailService.insertDraftBoxMessageMail( messageMailVO, getAccountId( request, null ) );
         }
      }

      // ������ӳɹ����
      success( request, MESSAGE_TYPE_ADD );
   }

   // ���ڷ���
   protected void sendCircle( final MessageMailVO messageMailVO, final HttpServletRequest request ) throws Exception
   {
      // ��������
      MessageRegularVO messageRegularVO = creatRegular( messageMailVO, request );

      if ( messageRegularVO != null )
      {
         final MessageRegularService messageRegularService = ( MessageRegularService ) getService( "messageRegularService" );
         messageRegularVO.setAccountId( getAccountId( request, null ) );
         messageRegularVO.setClientId( getUserId( request, null ) );
         messageRegularVO.setCorpId( getUserId( request, null ) );
         messageRegularVO.setSystemId( getSystemId( request, null ) );
         messageRegularVO.setCreateBy( getUserId( request, null ) );
         messageRegularVO.setCreateDate( new Date() );
         messageRegularVO.setModifyBy( getUserId( request, null ) );
         messageRegularVO.setModifyDate( new Date() );
         messageRegularService.insertMessageRegular( messageRegularVO );
         messageMailVO.setRegularId( messageRegularVO.getRegularId() );
      }

      // ��ʼ��Service�ӿ�
      final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
      // ����Ŀ�ʼʱ����ǲݸ�Ĵ�����
      final String toSendTime = messageRegularVO.getStartDateTime();

      // �½�����
      final String[] userIds = request.getParameterValues( "userIds" );
      final String[] positionIds = request.getParameterValues( "positionIds" );
      final String[] groupIds = request.getParameterValues( "groupIds" );
      final String[] branchIds = request.getParameterValues( "branchIds" );
      final String[] extendAccounts = request.getParameterValues( "extends" );
      final String[] positionGradeIds = request.getParameterValues( "positionGradeIds" );
      // ��ʼ��staffService
      final StaffService staffService = ( StaffService ) getService( "staffService" );
      final List< Object > staffVOObjects = staffService.getStaffVOsByIds( getAccountId( request, null ), userIds, positionIds, groupIds, branchIds, positionGradeIds );
      final List< String > emails = new ArrayList< String >();
      StaffVO staffVO = null;
      for ( Object obj : staffVOObjects )
      {
         staffVO = ( StaffVO ) obj;
         if ( KANUtil.filterEmpty( staffVO.getBizEmail() ) != null )
         {
            emails.add( staffVO.getBizEmail() );
         }
         else if ( KANUtil.filterEmpty( staffVO.getPersonalEmail() ) != null )
         {
            emails.add( staffVO.getPersonalEmail() );
         }
      }
      // ����û��ֶ�����
      if ( extendAccounts != null && extendAccounts.length > 0 )
      {
         for ( String email : extendAccounts )
         {
            emails.add( email );
         }
      }
      // ȥ�ظ�
      final List< String > distinctEmails = KANUtil.getDistinctList( emails );
      for ( String email : distinctEmails )
      {
         messageMailVO.setReception( email );
         messageMailVO.setToSendTime( toSendTime );
         // ��ӵ��ݸ���
         messageMailService.insertDraftBoxMessageMail( messageMailVO, getAccountId( request, null ) );
      }

      // ������ӳɹ����
      success( request, MESSAGE_TYPE_ADD );

   }

   // ��������
   protected MessageRegularVO creatRegular( final MessageMailVO messageMailVO, final HttpServletRequest request ) throws Exception
   {
      final String repeatType = messageMailVO.getRepeatType();
      // ��������
      MessageRegularVO messageRegularVO = null;
      // ѡ����ѭ�����ͣ�����Ϊ��д�κ��������򵱳���������
      if ( "1".equals( repeatType ) || "2".equals( repeatType ) || "3".equals( repeatType ) )
      {
         messageRegularVO = new MessageRegularVO();
         messageRegularVO.setStartDateTime( messageMailVO.getStartDateTime() );
         messageRegularVO.setEndDateTime( messageMailVO.getEndDateTime() );
         messageRegularVO.setRepeatType( messageMailVO.getRepeatType() );
      }

      // ����
      if ( "1".equals( repeatType ) )
      {
         final String period = request.getParameter( "period_day" );
         messageRegularVO.setPeriod( KANUtil.filterEmpty( period ) );
      }

      // ����
      else if ( "2".equals( repeatType ) )
      {
         final String period = request.getParameter( "period_week" );
         messageRegularVO.setPeriod( KANUtil.filterEmpty( period ) );
         final String[] weekPeriods = request.getParameterValues( "chk_weekPeriod" );
         final int[] weekPeriod = new int[] { 0, 0, 0, 0, 0, 0, 0 };
         for ( int i = 0; i < weekPeriods.length; i++ )
         {
            int index = Integer.parseInt( weekPeriods[ i ] ) - 1;
            weekPeriod[ index ] = 1;
         }
         String weekPeriodStr = "";
         for ( int i : weekPeriod )
         {
            weekPeriodStr += i;
         }
         messageRegularVO.setWeekPeriod( weekPeriodStr );
      }
      // ����
      else if ( "3".equals( repeatType ) )
      {
         final String period = request.getParameter( "period_month" );
         messageRegularVO.setPeriod( KANUtil.filterEmpty( period ) );
         messageRegularVO.setAdditionalPeriod( messageMailVO.getAdditionalPeriod() );
      }
      else
      {
         sendNow( messageMailVO, request );
      }
      return messageRegularVO;
   }
}
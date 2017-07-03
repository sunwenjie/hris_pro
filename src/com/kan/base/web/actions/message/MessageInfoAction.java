/*
 * Created on 2013-05-07
 */
package com.kan.base.web.actions.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.message.MessageInfoVO;
import com.kan.base.domain.security.StaffVO;
import com.kan.base.domain.security.UserVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.message.MessageInfoService;
import com.kan.base.service.inf.security.StaffService;
import com.kan.base.service.inf.security.UserService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.web.actions.biz.employee.EmployeeSecurityAction;

/**
 * @author Jixiang Hu
 */
public class MessageInfoAction extends BaseAction
{
   public static String accessAction = "HRO_MESSAGE_INFO";

   /**
    * List messageInfo
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
         final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
         // ���Action Form
         final MessageInfoVO messageInfoVO = ( MessageInfoVO ) form;
         // ���ò�ѯ�Լ����½�����Ϣ
         messageInfoVO.setCreateBy( getUserId( request, response ) );

         // �����Action��ɾ��ϵͳ��Ϣ
         boolean isDelete = messageInfoVO != null && messageInfoVO.getSubAction() != null && messageInfoVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS );
         if ( isDelete )
         {
            // ����ɾ��ϵͳ��Ϣ��Action
            delete_objectList( mapping, form, request, response );
         }
         // ���SubActionΪ�գ�ͨ������������������ҳ������Ajax�ύ������������Ҫ���롣
         else
         {
            decodedObject( messageInfoVO );
         }

         if ( getRole( request, null ).equals( KANConstants.ROLE_EMPLOYEE ) )
         {
            messageInfoVO.setRole( KANConstants.ROLE_EMPLOYEE );
            messageInfoVO.setReception( EmployeeSecurityAction.getEmployeeId( request, response ) );
         }
         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder messageInfoHolder = new PagedListHolder();
         // ���뵱ǰҳ
         messageInfoHolder.setPage( page );
         // ���뵱ǰֵ����
         messageInfoHolder.setObject( messageInfoVO );
         // ����ҳ���¼����
         messageInfoHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         messageInfoService.getMessageInfoVOsByCondition( messageInfoHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( messageInfoHolder, request );

         request.setAttribute( "messageInfoHolder", messageInfoHolder );

         // �����ajax�������ɾ����������ת��Table�������ֶ�Ӧ��jsp
         if ( new Boolean( ajax ) || isDelete )
         {
            return mapping.findForward( "listMessageInfoTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listMessageInfo" );
   }

   /**
    * List messageInfo
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_receive( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );
         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );
         // ��ʼ��Service�ӿ�
         final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
         // ���Action Form
         final MessageInfoVO messageInfoVO = ( MessageInfoVO ) form;
         // ����reception
         messageInfoVO.setAccountId( getAccountId( request, response ) );
         // ���ò�ѯ��ϢΪ�Լ����ܵ�ϵͳ��Ϣ
         messageInfoVO.setReception( getUserId( request, response ) );
         // ������Ϣ role add by steven 
         messageInfoVO.setRole( super.getRole( request, response ) );
         // �����Action��ɾ��ϵͳ��Ϣ
         boolean isDelete = messageInfoVO != null && messageInfoVO.getSubAction() != null && messageInfoVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS );
         if ( isDelete )
         {
            // ����ɾ��ϵͳ��Ϣ��Action
            delete_objectList( mapping, form, request, response );
         }
         // �����Action�Ǳ�Ϊ�Ѷ�ϵͳ��Ϣ
         boolean isSetAllReaded = messageInfoVO != null && messageInfoVO.getSubAction() != null && messageInfoVO.getSubAction().equalsIgnoreCase( "setAllReadedObjects" );
         if ( isSetAllReaded )
         {
            // ���ñ�Ϊ�Ѷ�ϵͳ��Ϣ��Action
            setAllReaded_objectList( mapping, form, request, response );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         PagedListHolder messageInfoHolder = new PagedListHolder();
         // ���뵱ǰҳ
         messageInfoHolder.setPage( page );
         // ���뵱ǰֵ����
         messageInfoHolder.setObject( messageInfoVO );
         // ����ҳ���¼����
         messageInfoHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         messageInfoService.getMessageInfoVOsByCondition( messageInfoHolder, true );
         // ˢ��Holder�����ʻ���ֵ
         refreshHolder( messageInfoHolder, request );

         request.setAttribute( "messageInfoHolder", messageInfoHolder );

         // �����ajax�������ɾ����������ת��Table�������ֶ�Ӧ��jsp
         if ( new Boolean( ajax ) || isDelete )
         {
            return mapping.findForward( "listReceiveMessageInfoTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return mapping.findForward( "listReceiveMessageInfo" );
   }

   /**
    * To messageInfo modify page
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
         final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
         // ��õ�ǰ����
         String infoId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "infoId" ), "GBK" ) );
         // ���������Ӧ����
         MessageInfoVO messageInfoVO = messageInfoService.getMessageInfoVOByInfoId( infoId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         messageInfoVO.reset( null, request );

         // δ����Ϣ  �����ߴ򿪴���Ϣ����Ϣ״̬��Ϊ�Ѷ�
         if ( "2".equals( messageInfoVO.getStatus() ) && getUserId( request, response ).equals( messageInfoVO.getReception() ) )
         {
            // ������Ϣ�Ѷ�
            messageInfoVO.setStatus( "3" );
            messageInfoVO.setReceptionStatus( "3" );
            messageInfoService.updateMessageInfo( messageInfoVO );
         }

         messageInfoVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "messageInfoForm", messageInfoVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageMessageInfo" );
   }

   /**
    * To messageInfo �鿴ϵͳ��Ϣ���� page
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_messageInfoRead( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {

         // ��ʼ��Service�ӿ�
         final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
         // ��õ�ǰ����
         String infoId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "infoId" ), "GBK" ) );
         // ���������Ӧ����
         MessageInfoVO messageInfoVO = messageInfoService.getMessageInfoVOByInfoId( infoId );
         // ˢ�¶��󣬳�ʼ�������б����ʻ�
         messageInfoVO.reset( null, request );
         final String userId = getUserId( request, response );
         final String accountId = getAccountId( request, response );
         // �жϣ�ֻ���Ǳ��˲鿴�Լ�����Ϣ
         if ( userId.equals( messageInfoVO.getReception() ) && accountId.equals( messageInfoVO.getAccountId() ) )
         {
            // ������Ϣ�Ѷ�
            messageInfoVO.setStatus( "3" );
            messageInfoVO.setReceptionStatus( "3" );
            messageInfoService.updateMessageInfo( messageInfoVO );
            messageInfoVO.setSubAction( VIEW_OBJECT );

            request.setAttribute( "messageInfoForm", messageInfoVO );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���༭����
      return mapping.findForward( "manageMessageInfo" );
   }

   /**
    * To messageInfo new page
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
      ( ( MessageInfoVO ) form ).setInfoId( null );
      ( ( MessageInfoVO ) form ).setStatus( MessageInfoVO.TRUE );
      ( ( MessageInfoVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( MessageInfoVO ) form ).setExpiredTime( KANUtil.formatDate( KANUtil.getDate( new Date(), 0, 0, 3 ), "yyyy-MM-dd" ) );

      // ��ת���½�����
      return mapping.findForward( "manageMessageInfo" );
   }

   /**
    * Add messageInfo
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
            final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
            // ���ActionForm
            final MessageInfoVO messageInfoVO = ( MessageInfoVO ) form;
            // ��ȡ��¼�û�  - ���ô����û�id
            messageInfoVO.setCreateBy( getUserId( request, response ) );
            // ��ȡ��¼�û��˻�  �����˻�id
            final String accountId = getAccountId( request, response );
            messageInfoVO.setAccountId( getAccountId( request, response ) );

            final String expiredTime = request.getParameter( "expiredTime" );
            messageInfoVO.setExpiredTime( expiredTime );
            // ����systemId
            messageInfoVO.setSystemId( KANConstants.SYSTEM_ID );
            messageInfoVO.setModifyBy( getUserId( request, response ) );
            // ״̬�ж�  ���� 1���½����� 2�����沢����״̬���Ķ�Ĭ��Ϊ 1���½��� 
            final String status = messageInfoVO.getStatus();
            if ( !"1".equals( status ) )
            {
               if ( "2".equals( status ) )
               {
                  messageInfoVO.setStatus( "2" );
                  messageInfoVO.setReceptionStatus( "2" );
               }
               else
               {
                  messageInfoVO.setStatus( "1" );
               }
            }

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
            final List< String > infos = new ArrayList< String >();
            StaffVO staffVO = null;
            for ( Object obj : staffVOObjects )
            {
               staffVO = ( StaffVO ) obj;
               infos.add( staffVO.getUserId() );
            }
            // ����û��ֶ�����
            if ( extendAccounts != null && extendAccounts.length > 0 )
            {
               for ( String info : extendAccounts )
               {
                  infos.add( info );
               }
            }
            // ȥ�ظ�
            final List< String > distinctInfos = KANUtil.getDistinctList( infos );

            for ( String userId : distinctInfos )
            {
               messageInfoVO.setReception( userId );
               messageInfoService.insertMessageInfo( messageInfoVO, accountId );
            }
            // ������ӳɹ����
            success( request );

            insertlog( request, messageInfoVO, Operate.ADD, messageInfoVO.getInfoId(), null );
         }
         // ���Form����
         ( ( MessageInfoVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Modify messageInfo
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
            final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
            // ��õ�ǰ����
            final String infoId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "infoId" ), "GBK" ) );
            // ���������Ӧ����
            final MessageInfoVO messageInfoVO = messageInfoService.getMessageInfoVOByInfoId( infoId );
            final MessageInfoVO toDeletedVO = messageInfoVO;
            // װ�ؽ��洫ֵ
            messageInfoVO.update( ( MessageInfoVO ) form );
            // ��ȡ��¼�û� �����޸��˻�id
            messageInfoVO.setModifyBy( getUserId( request, response ) );
            // ����systemId
            messageInfoVO.setSystemId( KANConstants.SYSTEM_ID );
            String status = messageInfoVO.getStatus();
            String targetStatus = ( ( MessageInfoVO ) form ).getStatus();

            // ״̬�ж�
            if ( "2".equals( targetStatus ) && "1".equals( status ) )
            {
               messageInfoVO.setStatus( "2" );
               messageInfoVO.setReceptionStatus( "2" );
            }

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
            final List< String > infos = new ArrayList< String >();
            StaffVO staffVO = null;
            for ( Object obj : staffVOObjects )
            {
               staffVO = ( StaffVO ) obj;
               infos.add( staffVO.getUserId() );
            }
            // ����û��ֶ�����
            if ( extendAccounts != null && extendAccounts.length > 0 )
            {
               for ( String info : extendAccounts )
               {
                  infos.add( info );
               }
            }
            // ȥ�ظ�
            final List< String > distinctInfos = KANUtil.getDistinctList( infos );

            for ( String userId : distinctInfos )
            {
               messageInfoVO.setReception( userId );
               messageInfoService.insertMessageInfo( messageInfoVO, getAccountId( request, response ) );
            }

            //ɾ��������
            messageInfoService.deleteMessageInfo( toDeletedVO );

            // ���ر༭�ɹ����
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, messageInfoVO, Operate.MODIFY, messageInfoVO.getInfoId(), null );
         }

         // ���Form����
         ( ( MessageInfoVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��ת���б����
      return list_object( mapping, form, request, response );
   }

   /**
    * Delete messageInfo
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
         final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
         // ��õ�ǰ����
         final String infoId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "infoId" ), "GBK" ) );

         messageInfoService.deleteMessageInfoByInfoId( getUserId( request, response ), infoId );

         insertlog( request, form, Operate.DELETE, infoId, null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete messageInfo list
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
         final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
         // ���Action Form
         MessageInfoVO messageInfoVO = ( MessageInfoVO ) form;
         // ����ѡ�е�ID
         if ( messageInfoVO.getSelectedIds() != null && !messageInfoVO.getSelectedIds().equals( "" ) )
         {
            messageInfoService.deleteMessageInfoByInfoId( getUserId( request, response ), messageInfoVO.getSelectedIds().split( "," ) );

            insertlog( request, form, Operate.DELETE, null, messageInfoVO.getSelectedIds() );
         }
         // ���Selected IDs����Action
         messageInfoVO.setSelectedIds( "" );
         messageInfoVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   private void setAllReaded_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // ��ʼ��Service�ӿ�
         final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
         // ���Action Form
         MessageInfoVO messageInfoVO = ( MessageInfoVO ) form;
         // ����ѡ�е�ID
         if ( messageInfoVO.getSelectedIds() != null && !messageInfoVO.getSelectedIds().equals( "" ) )
         {
            messageInfoService.setMessageInfosReaded( getUserId( request, response ), messageInfoVO.getSelectedIds().split( "," ) );
         }
         // ���Selected IDs����Action
         messageInfoVO.setSelectedIds( "" );
         messageInfoVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward get_notReadCount( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException, IOException
   {
      response.setContentType( "text/html" );
      response.setCharacterEncoding( "GBK" );
      PrintWriter out = response.getWriter();
      final String accoutId = getAccountId( request, response );
      final String userId = getUserId( request, response );
      // ��ʼ��Service�ӿ�
      final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
      if ( KANConstants.ROLE_EMPLOYEE.equals( getRole( request, response ) ) )
      {
         if ( accoutId != null && userId != null && !userId.isEmpty() && !accoutId.isEmpty() )
         {
            // ���Action Form
            MessageInfoVO messageInfoVO = ( MessageInfoVO ) form;
            messageInfoVO.setAccountId( accoutId );
            messageInfoVO.setReception( userId );
            messageInfoVO.setRole( KANConstants.ROLE_EMPLOYEE );
            int count = messageInfoService.getNotReadCount( messageInfoVO );

            final JSONArray jsonArray = new JSONArray();
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put( "accountId", "" );
            jsonObject.put( "count", count > 999 ? "999+" : count );
            jsonArray.add( jsonObject );
            out.print( jsonArray.toString() );
            out.flush();
            out.close();
         }
      }
      else
      {
         final UserService userService = ( UserService ) getService( "userService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         final UserVO userVO = userService.getUserVOByUserId( userId );
         String[] userIds = null;
         if(userVO!=null && KANUtil.filterEmpty(userVO.getUserIds())!=null){
           userIds = KANUtil.jasonArrayToStringArray( userVO.getUserIds() );
         }
         final List< String > userIdList = new ArrayList< String >();
         if ( KANUtil.filterEmpty( userIds ) != null && userIds.length > 0 )
         {
            for ( String s : userIds )
            {
               userIdList.add( s );
            }
         }
         userIdList.add( userId );
         for ( int i = 0; i < userIdList.size(); i++ )
         {
            for ( int j = i + 1; j < userIdList.size(); j++ )
            {
               if ( Double.parseDouble( userIdList.get( i ) ) > Double.parseDouble( userIdList.get( j ) ) )
               {
                  String temp = userIdList.get( i );
                  userIdList.set( i, userIdList.get( j ) );
                  userIdList.set( j, temp );
               }
            }
         }
         if ( accoutId != null && userId != null && !userId.isEmpty() && !accoutId.isEmpty() )
         {
            final JSONArray jsonArray = new JSONArray();
            for ( String userIdStr : userIdList )
            {
               final JSONObject jsonObject = new JSONObject();
               final UserVO tempUserVO = userService.getUserVOByUserId( userIdStr );
               final ClientVO clientVO = clientService.getClientVOByCorpId( tempUserVO.getCorpId() );
               // ���Action Form
               MessageInfoVO messageInfoVO = ( MessageInfoVO ) form;
               messageInfoVO.setAccountId( accoutId );
               messageInfoVO.setReception( userId );
               int count = messageInfoService.getNotReadCount( messageInfoVO );
               jsonObject.put( "accountId", clientVO == null ? "" : clientVO.getNameZH() );
               jsonObject.put( "count", count );
               jsonArray.add( jsonObject );
            }

            out.print( jsonArray.toString() );
            out.flush();
            out.close();
         }
      }
      return null;
   }

   public ActionForward get_allCount( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException, IOException
   {
      // ��ʼ��Service�ӿ�
      final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
      final String accoutId = getAccountId( request, response );
      final String userId = getUserId( request, response );
      if ( accoutId != null && userId != null && !userId.isEmpty() && !accoutId.isEmpty() )
      {
         // ���Action Form
         MessageInfoVO messageInfoVO = ( MessageInfoVO ) form;
         messageInfoVO.setAccountId( accoutId );
         messageInfoVO.setReception( userId );
         int count = messageInfoService.getAllCount( messageInfoVO );

         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         PrintWriter out = response.getWriter();
         out.print( count > 999 ? "999+" : count );
         out.flush();
         out.close();
      }
      return null;
   }

}
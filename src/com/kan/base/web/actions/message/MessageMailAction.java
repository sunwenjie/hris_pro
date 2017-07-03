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
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
         // 获得Action Form
         final MessageMailVO messageMailVO = ( MessageMailVO ) form;

         boolean actionIsNotNull = messageMailVO != null && messageMailVO.getSubAction() != null;
         // 如果子Action是删除邮件
         boolean isDelete = actionIsNotNull && messageMailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS );
         boolean isCancleSend = actionIsNotNull && messageMailVO.getSubAction().equalsIgnoreCase( "cancelSend" );
         boolean isContinueSend = actionIsNotNull && messageMailVO.getSubAction().equalsIgnoreCase( "continueSend" );
         if ( isDelete )
         {
            // 调用删除邮件的Action
            delete_objectList( mapping, form, request, response );
         }
         else if ( isCancleSend )
         {
            // 调用取消发送邮件的Action
            sendCancel( mapping, form, request, response );
         }
         else if ( isContinueSend )
         {
            // 调用继续发邮件的Action
            sendContinue( mapping, form, request, response );

         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( messageMailVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder messageMailHolder = new PagedListHolder();
         // 传入当前页
         messageMailHolder.setPage( page );
         // 传入当前值对象
         messageMailHolder.setObject( messageMailVO );
         // 设置页面记录条数
         messageMailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         messageMailService.getMessageMailVOsByCondition( messageMailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( messageMailHolder, request );

         request.setAttribute( "messageMailHolder", messageMailHolder );

         // 如果是ajax请求或者删除操作则跳转到Table公共部分对应的jsp
         if ( new Boolean( ajax ) || isDelete )
         {
            return mapping.findForward( "listMessageMailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
         // 获得当前主键
         String mailId = KANUtil.decodeStringFromAjax( request.getParameter( "mailId" ) );
         // 获得主键对应对象
         MessageMailVO messageMailVO = messageMailService.getMessageMailVOByMailId( mailId );
         // 刷新对象，初始化对象列表及国际化
         messageMailVO.reset( null, request );

         messageMailVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "messageMailForm", messageMailVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
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
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( MessageMailVO ) form ).setStatus( MessageMailVO.TRUE );
      ( ( MessageMailVO ) form ).setSubAction( CREATE_OBJECT );
      // 默认含样式
      ( ( MessageMailVO ) form ).setContentType( "2" );

      // 跳转到新建界面
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 获得ActionForm
            final MessageMailVO messageMailVO = ( MessageMailVO ) form;
            // 获取登录用户  - 设置创建用户id
            messageMailVO.setCreateBy( getUserId( request, response ) );
            messageMailVO.setAccountId( getAccountId( request, response ) );
            // 设置systemId
            messageMailVO.setSystemId( getSystemId( request, response ) );
            messageMailVO.setModifyBy( getUserId( request, response ) );
            final String sendType = messageMailVO.getSendType();
            // 立即发送模式
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

         // 清空Form条件
         ( ( MessageMailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
            // 获得当前主键
            final String mailId = KANUtil.decodeStringFromAjax( request.getParameter( "mailId" ) );
            // 获得主键对应对象
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

            // 目标状态和当前状态的转换 逻辑判断
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
            // 装载界面传值
            messageMailVO.update( formVO );
            // 获取登录用户 设置修改账户id
            messageMailVO.setModifyBy( getUserId( request, response ) );
            // 设置systemId
            messageMailVO.setSystemId( getSystemId( request, response ) );
            // 修改对象
            messageMailService.updateMessageMail( messageMailVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, messageMailVO, Operate.MODIFY, messageMailVO.getMailId(), null );
         }
         else
         {
            log.debug( "###########重复提交" );
         }

         // 清空Form条件
         ( ( MessageMailVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 初始化Service接口
         final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
         // 获得当前主键
         final String mailId = KANUtil.decodeStringFromAjax( request.getParameter( "mailId" ) );
         // 获得主键对应对象
         final MessageMailVO messageMailVO = messageMailService.getMessageMailVOByMailId( mailId );
         // 目标状态和当前状态的转换 逻辑判断
         if ( "1".equals( messageMailVO.getStatus() ) )
         {
            // 设置邮件状态为：暂停发送
            messageMailVO.setStatus( "4" );
         }

         // 获取登录用户 设置修改账户id
         messageMailVO.setModifyBy( getUserId( request, response ) );
         // 修改对象
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
         // 初始化Service接口
         final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
         // 获得当前主键
         final String mailId = KANUtil.decodeStringFromAjax( request.getParameter( "mailId" ) );
         // 获得主键对应对象
         final MessageMailVO messageMailVO = messageMailService.getMessageMailVOByMailId( mailId );
         if ( "4".equals( messageMailVO.getStatus() ) )
         {
            // 设置邮件状态为：待发送
            messageMailVO.setStatus( "1" );
         }
         // 获取登录用户 设置修改账户id
         messageMailVO.setModifyBy( getUserId( request, response ) );
         // 修改对象
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
         // 初始化Service接口
         final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
         // 获得当前主键
         final String mailId = KANUtil.decodeStringFromAjax( request.getParameter( "mailId" ) );

         // 删除主键对应对象
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
         // 初始化Service接口
         final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
         // 获得Action Form
         MessageMailVO messageMailVO = ( MessageMailVO ) form;
         // 存在选中的ID
         if ( messageMailVO.getSelectedIds() != null && !messageMailVO.getSelectedIds().equals( "" ) )
         {
            messageMailService.deleteMessageMailByMailId( getUserId( request, response ), messageMailVO.getSelectedIds().split( "," ) );

            insertlog( request, form, Operate.DELETE, null, messageMailVO.getSelectedIds() );
         }

         // 清除Selected IDs和子Action
         messageMailVO.setSelectedIds( "" );
         messageMailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 立即发送
   protected void sendNow( final MessageMailVO messageMailVO, final HttpServletRequest request ) throws KANException
   {
      // 初始化Service接口
      final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
      messageMailVO.setStatus( "1" );

      // 新建对象
      final String[] userIds = request.getParameterValues( "userIds" );
      final String[] positionIds = request.getParameterValues( "positionIds" );
      final String[] groupIds = request.getParameterValues( "groupIds" );
      final String[] branchIds = request.getParameterValues( "branchIds" );
      final String[] extendAccounts = request.getParameterValues( "extends" );
      final String[] positionGradeIds = request.getParameterValues( "positionGradeIds" );
      // 初始化staffService
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
      // 添加用户手动输入
      if ( extendAccounts != null && extendAccounts.length > 0 )
      {
         for ( String email : extendAccounts )
         {
            emails.add( email );
         }
      }
      // 去重复
      final List< String > distinctEmails = KANUtil.getDistinctList( emails );
      for ( String email : distinctEmails )
      {
         messageMailVO.setReception( email );
         messageMailService.insertMessageMail( messageMailVO, getAccountId( request, null ) );
      }

      // 返回添加成功标记
      success( request, MESSAGE_TYPE_ADD );
   }

   // 定时发送
   protected void sendDelayed( final MessageMailVO messageMailVO, final HttpServletRequest request ) throws KANException
   {
      //  先放到草稿箱5。到了规定时间自动修改状态为1
      // 初始化Service接口
      final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
      final String[] toSendTimes = request.getParameterValues( "toSendTimes" );

      // 新建对象
      final String[] userIds = request.getParameterValues( "userIds" );
      final String[] positionIds = request.getParameterValues( "positionIds" );
      final String[] groupIds = request.getParameterValues( "groupIds" );
      final String[] branchIds = request.getParameterValues( "branchIds" );
      final String[] extendAccounts = request.getParameterValues( "extends" );
      final String[] positionGradeIds = request.getParameterValues( "positionGradeIds" );
      // 初始化staffService
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
      // 添加用户手动输入
      if ( extendAccounts != null && extendAccounts.length > 0 )
      {
         for ( String email : extendAccounts )
         {
            emails.add( email );
         }
      }
      // 去重复
      final List< String > distinctEmails = KANUtil.getDistinctList( emails );
      for ( String email : distinctEmails )
      {
         messageMailVO.setReception( email );
         for ( String toSendTime : toSendTimes )
         {
            messageMailVO.setToSendTime( toSendTime );
            //  添加到草稿箱
            messageMailService.insertDraftBoxMessageMail( messageMailVO, getAccountId( request, null ) );
         }
      }

      // 返回添加成功标记
      success( request, MESSAGE_TYPE_ADD );
   }

   // 周期发送
   protected void sendCircle( final MessageMailVO messageMailVO, final HttpServletRequest request ) throws Exception
   {
      // 创建规则
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

      // 初始化Service接口
      final MessageMailService messageMailService = ( MessageMailService ) getService( "messageMailService" );
      // 规则的开始时间就是草稿的待发送
      final String toSendTime = messageRegularVO.getStartDateTime();

      // 新建对象
      final String[] userIds = request.getParameterValues( "userIds" );
      final String[] positionIds = request.getParameterValues( "positionIds" );
      final String[] groupIds = request.getParameterValues( "groupIds" );
      final String[] branchIds = request.getParameterValues( "branchIds" );
      final String[] extendAccounts = request.getParameterValues( "extends" );
      final String[] positionGradeIds = request.getParameterValues( "positionGradeIds" );
      // 初始化staffService
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
      // 添加用户手动输入
      if ( extendAccounts != null && extendAccounts.length > 0 )
      {
         for ( String email : extendAccounts )
         {
            emails.add( email );
         }
      }
      // 去重复
      final List< String > distinctEmails = KANUtil.getDistinctList( emails );
      for ( String email : distinctEmails )
      {
         messageMailVO.setReception( email );
         messageMailVO.setToSendTime( toSendTime );
         // 添加到草稿箱
         messageMailService.insertDraftBoxMessageMail( messageMailVO, getAccountId( request, null ) );
      }

      // 返回添加成功标记
      success( request, MESSAGE_TYPE_ADD );

   }

   // 创建规则
   protected MessageRegularVO creatRegular( final MessageMailVO messageMailVO, final HttpServletRequest request ) throws Exception
   {
      final String repeatType = messageMailVO.getRepeatType();
      // 创建规则
      MessageRegularVO messageRegularVO = null;
      // 选择是循环发送，但是为填写任何条件，则当成立即发送
      if ( "1".equals( repeatType ) || "2".equals( repeatType ) || "3".equals( repeatType ) )
      {
         messageRegularVO = new MessageRegularVO();
         messageRegularVO.setStartDateTime( messageMailVO.getStartDateTime() );
         messageRegularVO.setEndDateTime( messageMailVO.getEndDateTime() );
         messageRegularVO.setRepeatType( messageMailVO.getRepeatType() );
      }

      // 按天
      if ( "1".equals( repeatType ) )
      {
         final String period = request.getParameter( "period_day" );
         messageRegularVO.setPeriod( KANUtil.filterEmpty( period ) );
      }

      // 按周
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
      // 按月
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
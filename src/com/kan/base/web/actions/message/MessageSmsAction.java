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
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
         // 获得Action Form
         final MessageSmsVO messageSmsVO = ( MessageSmsVO ) form;

         boolean actionIsNotNull = messageSmsVO != null && messageSmsVO.getSubAction() != null;
         // 如果子Action是删除短信
         boolean isDelete = actionIsNotNull && messageSmsVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS );
         boolean isCancleSend = actionIsNotNull && messageSmsVO.getSubAction().equalsIgnoreCase( "cancelSend" );
         boolean isContinueSend = actionIsNotNull && messageSmsVO.getSubAction().equalsIgnoreCase( "continueSend" );
         if ( isDelete )
         {
            // 调用删除短信的Action
            delete_objectList( mapping, form, request, response );
         }
         else if ( isCancleSend )
         {
            // 调用取消发送短信的Action
            sendCancel( mapping, form, request, response );
         }
         else if ( isContinueSend )
         {
            // 调用继续发短信的Action
            sendContinue( mapping, form, request, response );

         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder messageSmsHolder = new PagedListHolder();
         // 传入当前页
         messageSmsHolder.setPage( page );
         // 传入当前值对象
         messageSmsHolder.setObject( messageSmsVO );
         // 设置页面记录条数
         messageSmsHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         messageSmsService.getMessageSmsVOsByCondition( messageSmsHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( messageSmsHolder, request );

         request.setAttribute( "messageSmsHolder", messageSmsHolder );

         // 如果是ajax请求或者删除操作则跳转到Table公共部分对应的jsp
         if ( new Boolean( ajax ) || isDelete )
         {
            return mapping.findForward( "listMessageSmsTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
         // 获得当前主键
         String smsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "smsId" ), "GBK" ) );
         // 获得主键对应对象
         MessageSmsVO messageSmsVO = messageSmsService.getMessageSmsVOBySmsId( smsId );
         // 刷新对象，初始化对象列表及国际化
         messageSmsVO.reset( null, request );

         messageSmsVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "messageSmsForm", messageSmsVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
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
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( MessageSmsVO ) form ).setStatus( MessageSmsVO.TRUE );
      ( ( MessageSmsVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
            // 获得ActionForm
            final MessageSmsVO messageSmsVO = ( MessageSmsVO ) form;
            // 获取登录用户  - 设置创建用户id
            messageSmsVO.setCreateBy( getUserId( request, response ) );
            messageSmsVO.setAccountId( getAccountId( request, response ) );
            // 设置systemId
            messageSmsVO.setSystemId( KANConstants.SYSTEM_ID );
            messageSmsVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            final String[] userIds = request.getParameterValues( "userIds" );
            final String[] positionIds = request.getParameterValues( "positionIds" );
            final String[] groupIds = request.getParameterValues( "groupIds" );
            final String[] branchIds = request.getParameterValues( "branchIds" );
            final String[] extendAccounts = request.getParameterValues( "extends" );
            final String[] positionGradeIds = request.getParameterValues( "positionGradeIds" );
            // 初始化staffService
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
            // 添加用户手动输入
            if ( extendAccounts != null && extendAccounts.length > 0 )
            {
               for ( String sms : extendAccounts )
               {
                  smses.add( sms );
               }
            }
            // 去重复
            final List< String > distinctSmses = KANUtil.getDistinctList( smses );
            for ( String sms : distinctSmses )
            {
               messageSmsVO.setReception( sms );
               messageSmsService.insertMessageSms( messageSmsVO );
            }

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, messageSmsVO, Operate.ADD, messageSmsVO.getSmsId(), null );
         }
         // 清空Form条件
         ( ( MessageSmsVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
            // 获得当前主键
            final String smsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "smsId" ), "GBK" ) );
            // 获得主键对应对象
            final MessageSmsVO messageSmsVO = messageSmsService.getMessageSmsVOBySmsId( smsId );
            MessageSmsVO formVO = ( MessageSmsVO ) form;
            // 目标状态和当前状态的转换 逻辑判断
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
            // 装载界面传值
            messageSmsVO.update( ( MessageSmsVO ) form );
            // 获取登录用户 设置修改账户id
            messageSmsVO.setModifyBy( getUserId( request, response ) );
            // 设置systemId
            messageSmsVO.setSystemId( KANConstants.SYSTEM_ID );
            // 修改对象
            final String[] smses = request.getParameterValues( "smses" );
            for ( String sms : smses )
            {
               messageSmsVO.setReception( sms );
               messageSmsService.updateMessageSms( messageSmsVO );
            }

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, messageSmsVO, Operate.MODIFY, messageSmsVO.getSmsId(), null );
         }

         // 清空Form条件
         ( ( MessageSmsVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 初始化Service接口
         final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
         // 获得当前主键
         final String smsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "smsId" ), "GBK" ) );

         // 删除主键对应对象
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
         // 初始化Service接口
         final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
         // 获得Action Form
         MessageSmsVO messageSmsVO = ( MessageSmsVO ) form;
         // 存在选中的ID
         if ( messageSmsVO.getSelectedIds() != null && !messageSmsVO.getSelectedIds().equals( "" ) )
         {
            // 调用删除接口
            messageSmsService.deleteMessageSmsBySmsId( getUserId( request, response ), messageSmsVO.getSelectedIds().split( "," ) );

            insertlog( request, form, Operate.DELETE, null, messageSmsVO.getSelectedIds() );
         }
         // 清除Selected IDs和子Action
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
         // 初始化Service接口
         final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
         // 获得当前主键
         final String smsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "smsId" ), "GBK" ) );
         // 获得主键对应对象
         final MessageSmsVO messageSmsVO = messageSmsService.getMessageSmsVOBySmsId( smsId );
         // 目标状态和当前状态的转换 逻辑判断
         if ( "1".equals( messageSmsVO.getStatus() ) )
         {
            // 设置短信状态为：暂停发送
            messageSmsVO.setStatus( "4" );
         }

         // 获取登录用户 设置修改账户id
         messageSmsVO.setModifyBy( getUserId( request, response ) );
         // 修改对象
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
         // 初始化Service接口
         final MessageSmsService messageSmsService = ( MessageSmsService ) getService( "messageSmsService" );
         // 获得当前主键
         final String smsId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "smsId" ), "GBK" ) );
         // 获得主键对应对象
         final MessageSmsVO messageSmsVO = messageSmsService.getMessageSmsVOBySmsId( smsId );
         if ( "4".equals( messageSmsVO.getStatus() ) )
         {
            // 设置短信状态为：待发送
            messageSmsVO.setStatus( "1" );
         }
         // 获取登录用户 设置修改账户id
         messageSmsVO.setModifyBy( getUserId( request, response ) );
         // 修改对象
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
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( MessageSmsVO ) form ).setStatus( MessageSmsVO.TRUE );
      ( ( MessageSmsVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
      return mapping.findForward( "manageMessageSms" );
   }

}
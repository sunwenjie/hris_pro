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
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
         // 获得Action Form
         final MessageInfoVO messageInfoVO = ( MessageInfoVO ) form;
         // 设置查询自己的新建的信息
         messageInfoVO.setCreateBy( getUserId( request, response ) );

         // 如果子Action是删除系统消息
         boolean isDelete = messageInfoVO != null && messageInfoVO.getSubAction() != null && messageInfoVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS );
         if ( isDelete )
         {
            // 调用删除系统消息的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( messageInfoVO );
         }

         if ( getRole( request, null ).equals( KANConstants.ROLE_EMPLOYEE ) )
         {
            messageInfoVO.setRole( KANConstants.ROLE_EMPLOYEE );
            messageInfoVO.setReception( EmployeeSecurityAction.getEmployeeId( request, response ) );
         }
         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder messageInfoHolder = new PagedListHolder();
         // 传入当前页
         messageInfoHolder.setPage( page );
         // 传入当前值对象
         messageInfoHolder.setObject( messageInfoVO );
         // 设置页面记录条数
         messageInfoHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         messageInfoService.getMessageInfoVOsByCondition( messageInfoHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( messageInfoHolder, request );

         request.setAttribute( "messageInfoHolder", messageInfoHolder );

         // 如果是ajax请求或者删除操作则跳转到Table公共部分对应的jsp
         if ( new Boolean( ajax ) || isDelete )
         {
            return mapping.findForward( "listMessageInfoTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
         // 获得Action Form
         final MessageInfoVO messageInfoVO = ( MessageInfoVO ) form;
         // 设置reception
         messageInfoVO.setAccountId( getAccountId( request, response ) );
         // 设置查询信息为自己接受的系统消息
         messageInfoVO.setReception( getUserId( request, response ) );
         // 设置消息 role add by steven 
         messageInfoVO.setRole( super.getRole( request, response ) );
         // 如果子Action是删除系统消息
         boolean isDelete = messageInfoVO != null && messageInfoVO.getSubAction() != null && messageInfoVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS );
         if ( isDelete )
         {
            // 调用删除系统消息的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果子Action是标为已读系统消息
         boolean isSetAllReaded = messageInfoVO != null && messageInfoVO.getSubAction() != null && messageInfoVO.getSubAction().equalsIgnoreCase( "setAllReadedObjects" );
         if ( isSetAllReaded )
         {
            // 调用标为已读系统消息的Action
            setAllReaded_objectList( mapping, form, request, response );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder messageInfoHolder = new PagedListHolder();
         // 传入当前页
         messageInfoHolder.setPage( page );
         // 传入当前值对象
         messageInfoHolder.setObject( messageInfoVO );
         // 设置页面记录条数
         messageInfoHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         messageInfoService.getMessageInfoVOsByCondition( messageInfoHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( messageInfoHolder, request );

         request.setAttribute( "messageInfoHolder", messageInfoHolder );

         // 如果是ajax请求或者删除操作则跳转到Table公共部分对应的jsp
         if ( new Boolean( ajax ) || isDelete )
         {
            return mapping.findForward( "listReceiveMessageInfoTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 添加页面Token
         this.saveToken( request );
         // 初始化Service接口
         final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
         // 获得当前主键
         String infoId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "infoId" ), "GBK" ) );
         // 获得主键对应对象
         MessageInfoVO messageInfoVO = messageInfoService.getMessageInfoVOByInfoId( infoId );
         // 刷新对象，初始化对象列表及国际化
         messageInfoVO.reset( null, request );

         // 未读消息  接收者打开此消息则将消息状态改为已读
         if ( "2".equals( messageInfoVO.getStatus() ) && getUserId( request, response ).equals( messageInfoVO.getReception() ) )
         {
            // 设置消息已读
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

      // 跳转到编辑界面
      return mapping.findForward( "manageMessageInfo" );
   }

   /**
    * To messageInfo 查看系统消息详情 page
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

         // 初始化Service接口
         final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
         // 获得当前主键
         String infoId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "infoId" ), "GBK" ) );
         // 获得主键对应对象
         MessageInfoVO messageInfoVO = messageInfoService.getMessageInfoVOByInfoId( infoId );
         // 刷新对象，初始化对象列表及国际化
         messageInfoVO.reset( null, request );
         final String userId = getUserId( request, response );
         final String accountId = getAccountId( request, response );
         // 判断，只能是本人查看自己的消息
         if ( userId.equals( messageInfoVO.getReception() ) && accountId.equals( messageInfoVO.getAccountId() ) )
         {
            // 设置消息已读
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

      // 跳转到编辑界面
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
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( MessageInfoVO ) form ).setInfoId( null );
      ( ( MessageInfoVO ) form ).setStatus( MessageInfoVO.TRUE );
      ( ( MessageInfoVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( MessageInfoVO ) form ).setExpiredTime( KANUtil.formatDate( KANUtil.getDate( new Date(), 0, 0, 3 ), "yyyy-MM-dd" ) );

      // 跳转到新建界面
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
            // 获得ActionForm
            final MessageInfoVO messageInfoVO = ( MessageInfoVO ) form;
            // 获取登录用户  - 设置创建用户id
            messageInfoVO.setCreateBy( getUserId( request, response ) );
            // 获取登录用户账户  设置账户id
            final String accountId = getAccountId( request, response );
            messageInfoVO.setAccountId( getAccountId( request, response ) );

            final String expiredTime = request.getParameter( "expiredTime" );
            messageInfoVO.setExpiredTime( expiredTime );
            // 设置systemId
            messageInfoVO.setSystemId( KANConstants.SYSTEM_ID );
            messageInfoVO.setModifyBy( getUserId( request, response ) );
            // 状态判断  不是 1（新建）或 2（保存并发送状态）的都默认为 1（新建） 
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
            final List< String > infos = new ArrayList< String >();
            StaffVO staffVO = null;
            for ( Object obj : staffVOObjects )
            {
               staffVO = ( StaffVO ) obj;
               infos.add( staffVO.getUserId() );
            }
            // 添加用户手动输入
            if ( extendAccounts != null && extendAccounts.length > 0 )
            {
               for ( String info : extendAccounts )
               {
                  infos.add( info );
               }
            }
            // 去重复
            final List< String > distinctInfos = KANUtil.getDistinctList( infos );

            for ( String userId : distinctInfos )
            {
               messageInfoVO.setReception( userId );
               messageInfoService.insertMessageInfo( messageInfoVO, accountId );
            }
            // 返回添加成功标记
            success( request );

            insertlog( request, messageInfoVO, Operate.ADD, messageInfoVO.getInfoId(), null );
         }
         // 清空Form条件
         ( ( MessageInfoVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
            // 获得当前主键
            final String infoId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "infoId" ), "GBK" ) );
            // 获得主键对应对象
            final MessageInfoVO messageInfoVO = messageInfoService.getMessageInfoVOByInfoId( infoId );
            final MessageInfoVO toDeletedVO = messageInfoVO;
            // 装载界面传值
            messageInfoVO.update( ( MessageInfoVO ) form );
            // 获取登录用户 设置修改账户id
            messageInfoVO.setModifyBy( getUserId( request, response ) );
            // 设置systemId
            messageInfoVO.setSystemId( KANConstants.SYSTEM_ID );
            String status = messageInfoVO.getStatus();
            String targetStatus = ( ( MessageInfoVO ) form ).getStatus();

            // 状态判断
            if ( "2".equals( targetStatus ) && "1".equals( status ) )
            {
               messageInfoVO.setStatus( "2" );
               messageInfoVO.setReceptionStatus( "2" );
            }

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
            final List< String > infos = new ArrayList< String >();
            StaffVO staffVO = null;
            for ( Object obj : staffVOObjects )
            {
               staffVO = ( StaffVO ) obj;
               infos.add( staffVO.getUserId() );
            }
            // 添加用户手动输入
            if ( extendAccounts != null && extendAccounts.length > 0 )
            {
               for ( String info : extendAccounts )
               {
                  infos.add( info );
               }
            }
            // 去重复
            final List< String > distinctInfos = KANUtil.getDistinctList( infos );

            for ( String userId : distinctInfos )
            {
               messageInfoVO.setReception( userId );
               messageInfoService.insertMessageInfo( messageInfoVO, getAccountId( request, response ) );
            }

            //删除老数据
            messageInfoService.deleteMessageInfo( toDeletedVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, messageInfoVO, Operate.MODIFY, messageInfoVO.getInfoId(), null );
         }

         // 清空Form条件
         ( ( MessageInfoVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 初始化Service接口
         final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
         // 获得当前主键
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
         // 初始化Service接口
         final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
         // 获得Action Form
         MessageInfoVO messageInfoVO = ( MessageInfoVO ) form;
         // 存在选中的ID
         if ( messageInfoVO.getSelectedIds() != null && !messageInfoVO.getSelectedIds().equals( "" ) )
         {
            messageInfoService.deleteMessageInfoByInfoId( getUserId( request, response ), messageInfoVO.getSelectedIds().split( "," ) );

            insertlog( request, form, Operate.DELETE, null, messageInfoVO.getSelectedIds() );
         }
         // 清除Selected IDs和子Action
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
         // 初始化Service接口
         final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
         // 获得Action Form
         MessageInfoVO messageInfoVO = ( MessageInfoVO ) form;
         // 存在选中的ID
         if ( messageInfoVO.getSelectedIds() != null && !messageInfoVO.getSelectedIds().equals( "" ) )
         {
            messageInfoService.setMessageInfosReaded( getUserId( request, response ), messageInfoVO.getSelectedIds().split( "," ) );
         }
         // 清除Selected IDs和子Action
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
      // 初始化Service接口
      final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
      if ( KANConstants.ROLE_EMPLOYEE.equals( getRole( request, response ) ) )
      {
         if ( accoutId != null && userId != null && !userId.isEmpty() && !accoutId.isEmpty() )
         {
            // 获得Action Form
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
               // 获得Action Form
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
      // 初始化Service接口
      final MessageInfoService messageInfoService = ( MessageInfoService ) getService( "messageInfoService" );
      final String accoutId = getAccountId( request, response );
      final String userId = getUserId( request, response );
      if ( accoutId != null && userId != null && !userId.isEmpty() && !accoutId.isEmpty() )
      {
         // 获得Action Form
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
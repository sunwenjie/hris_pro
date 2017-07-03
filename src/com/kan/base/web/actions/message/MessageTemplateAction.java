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
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 初始化Service接口
         final MessageTemplateService messageTemplateService = ( MessageTemplateService ) getService( "messageTemplateService" );
         // 获得Action Form
         final MessageTemplateVO messageTemplateVO = ( MessageTemplateVO ) form;

         // 如果子Action是删除信息模板
         if ( messageTemplateVO != null && messageTemplateVO.getSubAction() != null && messageTemplateVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 调用删除信息模板的Action
            delete_objectList( mapping, form, request, response );
         }

         // 如果没有指定排序则默认按employeeId排序
         if ( messageTemplateVO.getSortColumn() == null || messageTemplateVO.getSortColumn().isEmpty() )
         {
            messageTemplateVO.setSortColumn( "templateId" );
            messageTemplateVO.setSortOrder( "desc" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder messageTemplateHolder = new PagedListHolder();

         // 传入当前页
         messageTemplateHolder.setPage( page );
         // 传入当前值对象
         messageTemplateHolder.setObject( messageTemplateVO );
         // 设置页面记录条数
         messageTemplateHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         messageTemplateService.getMessageTemplateVOsByCondition( messageTemplateHolder, true );
         // 刷新Holder，国际化传值
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

      // 跳转到列表界面
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
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final MessageTemplateService messageTemplateService = ( MessageTemplateService ) getService( "messageTemplateService" );
         // 获得当前主键
         String templateId = request.getParameter( "id" );
         if ( KANUtil.filterEmpty( templateId ) != null )
         {
            templateId = Cryptogram.decodeString( URLDecoder.decode( templateId, "UTF-8" ) );
         }
         else
         {
            templateId = ( ( MessageTemplateVO ) form ).getTemplateId();
         }
         // 获得主键对应对象
         MessageTemplateVO messageTemplateVO = messageTemplateService.getMessageTemplateVOByTemplateId( templateId );
         // 刷新对象，初始化对象列表及国际化
         messageTemplateVO.reset( null, request );

         messageTemplateVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "messageTemplateForm", messageTemplateVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
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
      // 添加页面Token
      this.saveToken( request );

      // 设置Sub Action
      ( ( MessageTemplateVO ) form ).setContentType( "1" );
      ( ( MessageTemplateVO ) form ).setStatus( MessageTemplateVO.TRUE );
      ( ( MessageTemplateVO ) form ).setSubAction( CREATE_OBJECT );

      // 跳转到新建界面
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
      // 获得ActionForm
      final MessageTemplateVO messageTemplateVO = ( MessageTemplateVO ) form;

      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final MessageTemplateService messageTemplateService = ( MessageTemplateService ) getService( "messageTemplateService" );
            // 获取登录用户  - 设置创建用户id
            messageTemplateVO.setCreateBy( getUserId( request, response ) );
            // 获取登录用户账户  设置账户id
            messageTemplateVO.setAccountId( getAccountId( request, response ) );
            messageTemplateVO.setModifyBy( getUserId( request, response ) );
            // 新建对象
            messageTemplateService.insertMessageTemplate( messageTemplateVO );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
            insertlog( request, messageTemplateVO, Operate.ADD, messageTemplateVO.getTemplateId(), null );
         }

         // 清空Form条件
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
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final MessageTemplateService messageTemplateService = ( MessageTemplateService ) getService( "messageTemplateService" );
            // 获得当前主键
            final String templateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final MessageTemplateVO messageTemplateVO = messageTemplateService.getMessageTemplateVOByTemplateId( templateId );
            // 装载界面传值
            messageTemplateVO.update( ( MessageTemplateVO ) form );
            // 获取登录用户 设置修改账户id
            messageTemplateVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            messageTemplateService.updateMessageTemplate( messageTemplateVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, messageTemplateVO, Operate.MODIFY, messageTemplateVO.getTemplateId(), null );
         }

         constantsInit( "initMessageTemplate", getAccountId( request, response ) );
         // 清空Form条件
         ( ( MessageTemplateVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service接口
         final MessageTemplateService messageTemplateService = ( MessageTemplateService ) getService( "messageTemplateService" );
         // 获得当前主键
         final String templateId = request.getParameter( "templateId" );
         // 获得主键对应对象
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
         // 初始化Service接口
         final MessageTemplateService messageTemplateService = ( MessageTemplateService ) getService( "messageTemplateService" );
         // 获得当前主键
         final String templateId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "templateId" ), "UTF-8" ) );

         // 删除主键对应对象
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
         // 初始化Service接口
         final MessageTemplateService messageTemplateService = ( MessageTemplateService ) getService( "messageTemplateService" );
         // 获得Action Form
         MessageTemplateVO messageTemplateVO = ( MessageTemplateVO ) form;
         // 存在选中的ID
         if ( messageTemplateVO.getSelectedIds() != null && !messageTemplateVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : messageTemplateVO.getSelectedIds().split( "," ) )
            {
               // 调用删除接口
               messageTemplateService.deleteMessageTemplate( selectedId );
            }

            insertlog( request, messageTemplateVO, Operate.DELETE, null, messageTemplateVO.getSelectedIds() );
         }
         // 清除Selected IDs和子Action
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
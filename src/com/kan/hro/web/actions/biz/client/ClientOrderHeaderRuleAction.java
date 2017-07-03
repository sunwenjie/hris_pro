package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientOrderHeaderRuleVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderRuleService;

public class ClientOrderHeaderRuleAction extends BaseAction
{
   public static String accessAction = "HRO_BIZ_CLIENT_ORDER_HEADER_RULE";

   /**
    * List Object
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
         final ClientOrderHeaderRuleService clientOrderHeaderRuleService = ( ClientOrderHeaderRuleService ) getService( "clientOrderHeaderRuleService" );
         // 获得Action Form
         final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO = ( ClientOrderHeaderRuleVO ) form;
         clientOrderHeaderRuleVO.setAccountId( getAccountId( request, response ) );

         // 如果子Action是删除用户列表
         if ( clientOrderHeaderRuleVO.getSubAction() != null && clientOrderHeaderRuleVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( clientOrderHeaderRuleVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // 传入当前页
         pageListHolder.setPage( page );
         // 传入当前值对象
         pageListHolder.setObject( clientOrderHeaderRuleVO );
         // 设置页面记录条数
         pageListHolder.setPageSize( listPageSize );
         // 获得SubAction
         String subAction = "";

         // 如果子SubAction不为空
         if ( clientOrderHeaderRuleVO.getSubAction() != null )
         {
            subAction = clientOrderHeaderRuleVO.getSubAction().trim();
         }

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         clientOrderHeaderRuleService.getClientOrderHeaderRuleVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
         // 刷新Holder，国际化传值
         refreshHolder( pageListHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", pageListHolder );

         // 如果是调用则返回Render生成的字节流
         if ( new Boolean( ajax ) )
         {
            if ( subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
            {
               return new DownloadFileAction().exportList( mapping, form, request, response );
            }
            else
            {
               // Config the response
               response.setContentType( "text/html" );
               response.setCharacterEncoding( "UTF-8" );
               // 初始化PrintWrite对象
               final PrintWriter out = response.getWriter();

               // Send to client
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_HEADER_RULE" ) );
               out.flush();
               out.close();
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "listClientOrderHeaderRule" );
   }

   /**
    * To Object New
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-21
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 获得父对象主键Id
      final String orderHeaderId = KANUtil.decodeString( request.getParameter( "orderHeaderId" ) );

      ( ( ClientOrderHeaderRuleVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( ClientOrderHeaderRuleVO ) form ).setOrderHeaderId( orderHeaderId );
      ( ( ClientOrderHeaderRuleVO ) form ).setStatus( ClientOrderHeaderRuleVO.TRUE );
      // 国际化
      ( ( ClientOrderHeaderRuleVO ) form ).reset( null, request );

      return mapping.findForward( "manageClientOrderHeaderRule" );
   }

   /**
    * To Object Modify
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-10-21
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final ClientOrderHeaderRuleService clientOrderHeaderRuleService = ( ClientOrderHeaderRuleService ) getService( "clientOrderHeaderRuleService" );

         // 获得当前主键
         String orderHeaderRuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( orderHeaderRuleId == null || orderHeaderRuleId.trim().isEmpty() )
         {
            orderHeaderRuleId = ( ( ClientOrderHeaderRuleVO ) form ).getOrderHeaderRuleId();
         }

         // 获得当前主键对应对象
         final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO = clientOrderHeaderRuleService.getClientOrderHeaderRuleVOByClientOrderHeaderRuleId( orderHeaderRuleId );

         // 刷新VO对象，初始化对象列表及国际化
         clientOrderHeaderRuleVO.reset( null, request );
         // 设置Sub Action
         clientOrderHeaderRuleVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientOrderHeaderRuleForm", clientOrderHeaderRuleVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageClientOrderHeaderRule" );
   }

   /**  
    * Add Object
    *	 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {

      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ClientOrderHeaderRuleService clientOrderHeaderRuleService = ( ClientOrderHeaderRuleService ) getService( "clientOrderHeaderRuleService" );

            // 获得ActionForm
            final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO = ( ClientOrderHeaderRuleVO ) form;
            // 获取登录用户
            clientOrderHeaderRuleVO.setCreateBy( getUserId( request, response ) );
            clientOrderHeaderRuleVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            clientOrderHeaderRuleVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // 新建对象
            clientOrderHeaderRuleService.insertClientOrderHeaderRule( clientOrderHeaderRuleVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }

         // 清空Form条件
         ( ( ClientOrderHeaderRuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ClientOrderHeaderRuleService clientOrderHeaderRuleService = ( ClientOrderHeaderRuleService ) getService( "clientOrderHeaderRuleService" );
            // 获得当前主键
            final String orderHeaderRuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO = clientOrderHeaderRuleService.getClientOrderHeaderRuleVOByClientOrderHeaderRuleId( orderHeaderRuleId );
            // 获取登录用户
            clientOrderHeaderRuleVO.update( ( ClientOrderHeaderRuleVO ) form );
            // 保存自定义Column
            clientOrderHeaderRuleVO.setRemark1( saveDefineColumns( request, accessAction ) );
            clientOrderHeaderRuleVO.setModifyBy( getUserId( request, response ) );
            // 修改对象
            clientOrderHeaderRuleService.updateClientOrderHeaderRule( clientOrderHeaderRuleVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // 清空Form条件
         ( ( ClientOrderHeaderRuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // no use
   }

   /**
    * Delete Object Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-10-19
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final ClientOrderHeaderRuleService clientOrderHeaderRuleService = ( ClientOrderHeaderRuleService ) getService( "clientOrderHeaderRuleService" );

         // 获取主键
         final String clientOrderHeaderRuleId = KANUtil.decodeStringFromAjax( request.getParameter( "clientOrderHeaderRuleId" ) );

         // 根据主键获得对应VO
         final ClientOrderHeaderRuleVO clientOrderHeaderRuleVO = clientOrderHeaderRuleService.getClientOrderHeaderRuleVOByClientOrderHeaderRuleId( clientOrderHeaderRuleId );
         clientOrderHeaderRuleVO.setModifyBy( getUserId( request, response ) );
         clientOrderHeaderRuleVO.setModifyDate( new Date() );

         // 调用删除接口
         final long rows = clientOrderHeaderRuleService.deleteClientOrderHeaderRule( clientOrderHeaderRuleVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
         }
         else
         {
            deleteFailedAjax( out, null );
         }

         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * Delete Object List
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
         final ClientOrderHeaderRuleService clientOrderHeaderRuleService = ( ClientOrderHeaderRuleService ) getService( "clientOrderHeaderRuleService" );
         // 获得Action Form
         ClientOrderHeaderRuleVO clientOrderHeaderRuleVO = ( ClientOrderHeaderRuleVO ) form;

         // 存在选中的ID
         if ( clientOrderHeaderRuleVO.getSelectedIds() != null && !clientOrderHeaderRuleVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : clientOrderHeaderRuleVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // 主键转码
                  final String clientOrderHeaderRuleId = KANUtil.decodeStringFromAjax( selectedId );
                  // 根据主键获得对应VO
                  final ClientOrderHeaderRuleVO clientOrderHeaderRuleVOForDel = clientOrderHeaderRuleService.getClientOrderHeaderRuleVOByClientOrderHeaderRuleId( clientOrderHeaderRuleId );
                  clientOrderHeaderRuleVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderHeaderRuleVOForDel.setModifyDate( new Date() );
                  // 调用删除接口
                  clientOrderHeaderRuleService.deleteClientOrderHeaderRule( clientOrderHeaderRuleVOForDel );
               }
            }
         }

         // 清除Selected IDs和子Action
         clientOrderHeaderRuleVO.setSelectedIds( "" );
         clientOrderHeaderRuleVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

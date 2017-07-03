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
import com.kan.hro.domain.biz.client.ClientOrderDetailSBRuleVO;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailSBRuleService;

/**  
 * 项目名称：HRO_V1  
 * 类名称：ClientOrderDetailRuleAction  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-8-19  
 */
public class ClientOrderDetailSBRuleAction extends BaseAction
{

   /**
    * List client Order Header
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
         final ClientOrderDetailSBRuleService clientOrderDetailSBRuleService = ( ClientOrderDetailSBRuleService ) getService( "clientOrderDetailSBRuleService" );
         // 获得Action Form
         final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO = ( ClientOrderDetailSBRuleVO ) form;
         clientOrderDetailSBRuleVO.setAccountId( getAccountId( request, response ) );

         // 如果子Action是删除用户列表
         if ( clientOrderDetailSBRuleVO.getSubAction() != null && clientOrderDetailSBRuleVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( clientOrderDetailSBRuleVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // 传入当前页
         pageListHolder.setPage( page );
         // 传入当前值对象
         pageListHolder.setObject( clientOrderDetailSBRuleVO );
         // 设置页面记录条数
         pageListHolder.setPageSize( listPageSize );
         // 获得SubAction
         String subAction = "";

         // 如果子SubAction不为空
         if ( clientOrderDetailSBRuleVO.getSubAction() != null )
         {
            subAction = clientOrderDetailSBRuleVO.getSubAction().trim();
         }

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         clientOrderDetailSBRuleService.getClientOrderDetailSBRuleVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_DETAIL_SB_RULE" ) );
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
      return mapping.findForward( "listClientOrderDetailSBRule" );
   }

   /**
    * To object New
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Code review by Kevin Jin at 2013-10-21
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 设置Sub Action
         ( ( ClientOrderDetailSBRuleVO ) form ).setSubAction( CREATE_OBJECT );
         // 获得父节点主键
         final String orderDetailId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "orderDetailId" ), "UTF-8" ) );

         // 如果OrderDetailId 存在
         if ( orderDetailId != null && !orderDetailId.isEmpty() )
         {
            ( ( ClientOrderDetailSBRuleVO ) form ).setOrderDetailId( orderDetailId );
         }

         ( ( ClientOrderDetailSBRuleVO ) form ).setStatus( ClientOrderDetailSBRuleVO.TRUE );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到新建界面
      return mapping.findForward( "manageClientOrderDetailSBRule" );
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
   // Code review by Kevin Jin at 2013-10-21
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final ClientOrderDetailSBRuleService clientOrderDetailSBRuleService = ( ClientOrderDetailSBRuleService ) getService( "clientOrderDetailSBRuleService" );

         // 获得当前主键
         String sbRuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( sbRuleId == null || sbRuleId.trim().isEmpty() )
         {
            sbRuleId = ( ( ClientOrderDetailSBRuleVO ) form ).getSbRuleId();
         }

         // 获得ClientOrderDetailRuleVO
         final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO = clientOrderDetailSBRuleService.getClientOrderDetailSBRuleVOByClientOrderDetailSBRuleId( sbRuleId );
         // 刷新VO对象，初始化对象列表及国际化
         clientOrderDetailSBRuleVO.reset( null, request );
         clientOrderDetailSBRuleVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientOrderDetailSBRuleForm", clientOrderDetailSBRuleVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageClientOrderDetailSBRule" );
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
            final ClientOrderDetailSBRuleService clientOrderDetailSBRuleService = ( ClientOrderDetailSBRuleService ) getService( "clientOrderDetailSBRuleService" );

            // 获得ActionForm
            final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO = ( ClientOrderDetailSBRuleVO ) form;
            // 获取登录用户
            clientOrderDetailSBRuleVO.setCreateBy( getUserId( request, response ) );
            clientOrderDetailSBRuleVO.setModifyBy( getUserId( request, response ) );

            // 新建对象
            clientOrderDetailSBRuleService.insertClientOrderDetailSBRule( clientOrderDetailSBRuleVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }

         // 清空Form条件
         ( ( ClientOrderDetailSBRuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
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
            final ClientOrderDetailSBRuleService clientOrderDetailSBRuleService = ( ClientOrderDetailSBRuleService ) getService( "clientOrderDetailSBRuleService" );

            // 获得当前主键
            final String sbRuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // 获得主键对应对象
            final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO = clientOrderDetailSBRuleService.getClientOrderDetailSBRuleVOByClientOrderDetailSBRuleId( sbRuleId );

            // 获取登录用户
            clientOrderDetailSBRuleVO.update( ( ClientOrderDetailSBRuleVO ) form );
            clientOrderDetailSBRuleVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            clientOrderDetailSBRuleService.updateClientOrderDetailSBRule( clientOrderDetailSBRuleVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // 清空Form条件
         ( ( ClientOrderDetailSBRuleVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
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
   // Added by Kevin Jin at 2013-10-20
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final ClientOrderDetailSBRuleService clientOrderDetailSBRuleService = ( ClientOrderDetailSBRuleService ) getService( "clientOrderDetailSBRuleService" );

         // 获取主键
         final String sbRuleId = KANUtil.decodeStringFromAjax( request.getParameter( "sbRuleId" ) );

         // 根据主键获得对应VO
         final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO = clientOrderDetailSBRuleService.getClientOrderDetailSBRuleVOByClientOrderDetailSBRuleId( sbRuleId );
         clientOrderDetailSBRuleVO.setModifyBy( getUserId( request, response ) );
         clientOrderDetailSBRuleVO.setModifyDate( new Date() );

         // 调用删除接口
         final long rows = clientOrderDetailSBRuleService.deleteClientOrderDetailSBRule( clientOrderDetailSBRuleVO );

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
         final ClientOrderDetailSBRuleService clientOrderDetailSBRuleService = ( ClientOrderDetailSBRuleService ) getService( "clientOrderDetailSBRuleService" );
         // 获得Action Form
         ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO = ( ClientOrderDetailSBRuleVO ) form;

         // 存在选中的ID
         if ( clientOrderDetailSBRuleVO.getSelectedIds() != null && !clientOrderDetailSBRuleVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : clientOrderDetailSBRuleVO.getSelectedIds().split( "," ) )
            {

               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // 主键转码
                  final String sbRuleId = KANUtil.decodeStringFromAjax( selectedId );
                  // 根据主键获得对应VO
                  final ClientOrderDetailSBRuleVO tempSBRuleVO = clientOrderDetailSBRuleService.getClientOrderDetailSBRuleVOByClientOrderDetailSBRuleId( sbRuleId );
                  tempSBRuleVO.setModifyBy( getUserId( request, response ) );
                  tempSBRuleVO.setModifyDate( new Date() );
                  // 调用删除接口
                  clientOrderDetailSBRuleService.deleteClientOrderDetailSBRule( tempSBRuleVO );
               }

            }
         }

         // 清除Selected IDs和子Action
         clientOrderDetailSBRuleVO.setSelectedIds( "" );
         clientOrderDetailSBRuleVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

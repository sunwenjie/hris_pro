package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientOrderDetailRuleVO;
import com.kan.hro.domain.biz.client.ClientOrderDetailVO;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailRuleService;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailService;

/**  
 * 项目名称：HRO_V1  
 * 类名称：ClientOrderDetailRuleAction  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-8-19  
 */
public class ClientOrderDetailRuleAction extends BaseAction
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
         final ClientOrderDetailRuleService clientOrderDetailRuleService = ( ClientOrderDetailRuleService ) getService( "clientOrderDetailRuleService" );
         // 获得Action Form
         final ClientOrderDetailRuleVO clientOrderDetailRuleVO = ( ClientOrderDetailRuleVO ) form;
         clientOrderDetailRuleVO.setAccountId( getAccountId( request, response ) );

         // 如果子Action是删除用户列表
         if ( clientOrderDetailRuleVO.getSubAction() != null && clientOrderDetailRuleVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( clientOrderDetailRuleVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // 传入当前页
         pageListHolder.setPage( page );
         // 传入当前值对象
         pageListHolder.setObject( clientOrderDetailRuleVO );
         // 设置页面记录条数
         pageListHolder.setPageSize( listPageSize );
         // 获得SubAction
         String subAction = "";

         // 如果子SubAction不为空
         if ( clientOrderDetailRuleVO.getSubAction() != null )
         {
            subAction = clientOrderDetailRuleVO.getSubAction().trim();
         }

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         clientOrderDetailRuleService.getClientOrderDetailRuleVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_DETAIL_RULE" ) );
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
      return mapping.findForward( "listClientOrderDetailRule" );
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

         // 初始化Service接口
         final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );

         // 设置Sub Action
         ( ( ClientOrderDetailRuleVO ) form ).setSubAction( CREATE_OBJECT );
         // 获得父节点主键
         final String orderDetailId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "orderDetailId" ), "UTF-8" ) );

         // 如果OrderDetailId 存在
         if ( orderDetailId != null && !orderDetailId.isEmpty() )
         {
            ( ( ClientOrderDetailRuleVO ) form ).setOrderDetailId( orderDetailId );
            // 获得父节点主键对应对象
            final ClientOrderDetailVO clientOrderDetailVO = clientOrderDetailService.getClientOrderDetailVOByClientOrderDetailId( orderDetailId );

            // 获得当前ItemVO
            final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( clientOrderDetailVO.getItemId() );

            // ItemVO需写入Request对象
            request.setAttribute( "itemVO", itemVO );
         }

         ( ( ClientOrderDetailRuleVO ) form ).setStatus( ClientOrderDetailRuleVO.TRUE );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到新建界面
      return mapping.findForward( "manageClientOrderDetailRule" );
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
         final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
         final ClientOrderDetailRuleService clientOrderDetailRuleService = ( ClientOrderDetailRuleService ) getService( "clientOrderDetailRuleService" );

         // 获得当前主键
         String orderDetailRuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( orderDetailRuleId == null || orderDetailRuleId.trim().isEmpty() )
         {
            orderDetailRuleId = ( ( ClientOrderDetailRuleVO ) form ).getOrderDetailRuleId();
         }

         // 获得ClientOrderDetailRuleVO
         final ClientOrderDetailRuleVO clientOrderDetailRuleVO = clientOrderDetailRuleService.getClientOrderDetailRuleVOByClientOrderDetailRuleId( orderDetailRuleId );
         // 刷新VO对象，初始化对象列表及国际化
         clientOrderDetailRuleVO.reset( null, request );
         clientOrderDetailRuleVO.setSubAction( VIEW_OBJECT );

         // 获得父节点对象
         final ClientOrderDetailVO clientOrderDetailVO = clientOrderDetailService.getClientOrderDetailVOByClientOrderDetailId( clientOrderDetailRuleVO.getOrderDetailId() );

         // 获得当前ItemVO
         final ItemVO itemVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getItemVOByItemId( clientOrderDetailVO.getItemId() );

         // ItemVO和ActionFrom需写入Request对象
         request.setAttribute( "itemVO", itemVO );
         request.setAttribute( "clientOrderDetailRuleForm", clientOrderDetailRuleVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageClientOrderDetailRule" );
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
            final ClientOrderDetailRuleService clientOrderDetailRuleService = ( ClientOrderDetailRuleService ) getService( "clientOrderDetailRuleService" );

            // 获得ActionForm
            final ClientOrderDetailRuleVO clientOrderDetailRuleVO = ( ClientOrderDetailRuleVO ) form;
            // 获取登录用户
            clientOrderDetailRuleVO.setCreateBy( getUserId( request, response ) );
            clientOrderDetailRuleVO.setModifyBy( getUserId( request, response ) );

            // 新建对象
            clientOrderDetailRuleService.insertClientOrderDetailRule( clientOrderDetailRuleVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }

         // 清空Form条件
         ( ( ClientOrderDetailRuleVO ) form ).reset();
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
            final ClientOrderDetailRuleService clientOrderDetailRuleService = ( ClientOrderDetailRuleService ) getService( "clientOrderDetailRuleService" );

            // 获得当前主键
            final String orderDetailRuleId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // 获得主键对应对象
            final ClientOrderDetailRuleVO clientOrderDetailRuleVO = clientOrderDetailRuleService.getClientOrderDetailRuleVOByClientOrderDetailRuleId( orderDetailRuleId );

            // 获取登录用户
            clientOrderDetailRuleVO.update( ( ClientOrderDetailRuleVO ) form );
            clientOrderDetailRuleVO.setModifyBy( getUserId( request, response ) );

            // 修改对象
            clientOrderDetailRuleService.updateClientOrderDetailRule( clientOrderDetailRuleVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // 清空Form条件
         ( ( ClientOrderDetailRuleVO ) form ).reset();
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
         final ClientOrderDetailRuleService clientOrderDetailRuleService = ( ClientOrderDetailRuleService ) getService( "clientOrderDetailRuleService" );

         // 获取主键
         final String clientOrderDetailRuleId = KANUtil.decodeStringFromAjax( request.getParameter( "clientOrderDetailRuleId" ) );

         // 根据主键获得对应VO
         final ClientOrderDetailRuleVO clientOrderDetailRuleVO = clientOrderDetailRuleService.getClientOrderDetailRuleVOByClientOrderDetailRuleId( clientOrderDetailRuleId );
         clientOrderDetailRuleVO.setModifyBy( getUserId( request, response ) );
         clientOrderDetailRuleVO.setModifyDate( new Date() );

         // 调用删除接口
         final long rows = clientOrderDetailRuleService.deleteClientOrderDetailRule( clientOrderDetailRuleVO );

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
         final ClientOrderDetailRuleService clientOrderDetailRuleService = ( ClientOrderDetailRuleService ) getService( "clientOrderDetailRuleService" );
         // 获得Action Form
         ClientOrderDetailRuleVO clientOrderDetailRuleVO = ( ClientOrderDetailRuleVO ) form;

         // 存在选中的ID
         if ( clientOrderDetailRuleVO.getSelectedIds() != null && !clientOrderDetailRuleVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : clientOrderDetailRuleVO.getSelectedIds().split( "," ) )
            {

               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // 主键转码
                  final String clientOrderDetailRuleId = KANUtil.decodeStringFromAjax( selectedId );
                  // 根据主键获得对应VO
                  final ClientOrderDetailRuleVO clientOrderDetailRuleVOForDel = clientOrderDetailRuleService.getClientOrderDetailRuleVOByClientOrderDetailRuleId( clientOrderDetailRuleId );
                  clientOrderDetailRuleVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderDetailRuleVOForDel.setModifyDate( new Date() );
                  // 调用删除接口
                  clientOrderDetailRuleService.deleteClientOrderDetailRule( clientOrderDetailRuleVOForDel );
               }

            }
         }

         // 清除Selected IDs和子Action
         clientOrderDetailRuleVO.setSelectedIds( "" );
         clientOrderDetailRuleVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

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
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderOTVO;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderOTService;

/**  
 * 项目名称：HRO_V1  
 * 类名称：ClientOrderOTAction  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-8-19  
 */
public class ClientOrderOTAction extends BaseAction
{

   // 当前Action对应的Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT_ORDER_OT";
   public final static String accessAction_in_house = "HRO_BIZ_CLIENT_ORDER_OT_IN_HOUSE";

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
         final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );
         // 获得Action Form
         final ClientOrderOTVO clientOrderOTVO = ( ClientOrderOTVO ) form;
         clientOrderOTVO.setAccountId( getAccountId( request, response ) );

         // 如果子Action是删除用户列表
         if ( clientOrderOTVO.getSubAction() != null && clientOrderOTVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( clientOrderOTVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // 传入当前页
         pageListHolder.setPage( page );
         // 传入当前值对象
         pageListHolder.setObject( clientOrderOTVO );
         // 设置页面记录条数
         pageListHolder.setPageSize( listPageSize );
         // 获得SubAction
         String subAction = "";

         // 如果子SubAction不为空
         if ( clientOrderOTVO.getSubAction() != null )
         {
            subAction = clientOrderOTVO.getSubAction().trim();
         }

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         clientOrderOTService.getClientOrderOTVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_OT" ) );
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
      return mapping.findForward( "listClientOrderOT" );
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
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 初始化Service接口
      final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

      // 获得Order Header Id
      final String orderHeaderId = KANUtil.decodeString( request.getParameter( "orderHeaderId" ) );

      // 获得ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

      ( ( ClientOrderOTVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( ClientOrderOTVO ) form ).setOrderHeaderId( orderHeaderId );
      ( ( ClientOrderOTVO ) form ).setBase( "0.00" );
      ( ( ClientOrderOTVO ) form ).setDiscount( "100.00" );
      ( ( ClientOrderOTVO ) form ).setMultiple( "1" );
      ( ( ClientOrderOTVO ) form ).setResultCap( "0.00" );
      ( ( ClientOrderOTVO ) form ).setResultFloor( "0.00" );
      ( ( ClientOrderOTVO ) form ).setStartDate( clientOrderHeaderVO.getStartDate() );
      ( ( ClientOrderOTVO ) form ).setEndDate( clientOrderHeaderVO.getEndDate() );
      ( ( ClientOrderOTVO ) form ).setStatus( ClientOrderOTVO.TRUE );
      ( ( ClientOrderOTVO ) form ).reset( null, request );

      // Attribute Setting
      request.setAttribute( "clientOrderHeaderVO", clientOrderHeaderVO );

      // 跳转到新建界面
      return mapping.findForward( "manageClientOrderOT" );
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
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );

         // 获得当前主键
         String orderOTId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( orderOTId == null || orderOTId.trim().isEmpty() )
         {
            orderOTId = ( ( ClientOrderOTVO ) form ).getOrderOTId();
         }

         // 根据主键查找对应的clientOrderOTVO
         final ClientOrderOTVO clientOrderOTVO = clientOrderOTService.getClientOrderOTVOByClientOrderOTId( orderOTId );

         // 刷新VO对象，初始化对象列表及国际化
         clientOrderOTVO.reset( null, request );
         // 设置Sub Action
         clientOrderOTVO.setSubAction( VIEW_OBJECT );

         // Attribute Setting
         request.setAttribute( "clientOrderHeaderVO", clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( clientOrderOTVO.getOrderHeaderId() ) );
         request.setAttribute( "clientOrderOTForm", clientOrderOTVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageClientOrderOT" );
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
            final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );

            // 获得ActionForm
            final ClientOrderOTVO clientOrderOTVO = ( ClientOrderOTVO ) form;
            // 获取登录用户
            clientOrderOTVO.setCreateBy( getUserId( request, response ) );
            clientOrderOTVO.setModifyBy( getUserId( request, response ) );

            // 新建对象
            clientOrderOTService.insertClientOrderOT( clientOrderOTVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, clientOrderOTVO, Operate.ADD, clientOrderOTVO.getOrderOTId(), null );
         }

         // 清空Form条件
         ( ( ClientOrderOTVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查询界面
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
            final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );

            // 获得当前主键
            final String orderOTId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

            // 获得主键对应对象
            final ClientOrderOTVO clientOrderOTVO = clientOrderOTService.getClientOrderOTVOByClientOrderOTId( orderOTId );

            // 获取登录用户
            clientOrderOTVO.update( ( ClientOrderOTVO ) form );
            clientOrderOTVO.setModifyBy( getUserId( request, response ) );
            clientOrderOTVO.reset( mapping, request );

            // 修改对象
            clientOrderOTService.updateClientOrderOT( clientOrderOTVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, clientOrderOTVO, Operate.MODIFY, clientOrderOTVO.getOrderOTId(), null );
         }

         // 清空Form条件
         ( ( ClientOrderOTVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查询界面
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
         final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );

         // 获取主键
         final String clientOrderOTId = KANUtil.decodeStringFromAjax( request.getParameter( "clientOrderOTId" ) );

         // 根据主键获得对应VO
         final ClientOrderOTVO clientOrderOTVO = clientOrderOTService.getClientOrderOTVOByClientOrderOTId( clientOrderOTId );
         clientOrderOTVO.setModifyBy( getUserId( request, response ) );
         clientOrderOTVO.setModifyDate( new Date() );

         // 调用删除接口
         final long rows = clientOrderOTService.deleteClientOrderOT( clientOrderOTVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, clientOrderOTVO, Operate.DELETE, clientOrderOTVO.getOrderOTId(), "ajax delete" );
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
         final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );
         // 获得Action Form
         ClientOrderOTVO clientOrderOTVO = ( ClientOrderOTVO ) form;

         // 存在选中的ID
         if ( clientOrderOTVO.getSelectedIds() != null && !clientOrderOTVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : clientOrderOTVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // 主键转码
                  final String clientOrderOTId = KANUtil.decodeStringFromAjax( selectedId );
                  // 根据主键获得对应VO
                  final ClientOrderOTVO clientOrderOTVOForDel = clientOrderOTService.getClientOrderOTVOByClientOrderOTId( clientOrderOTId );
                  clientOrderOTVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderOTVOForDel.setModifyDate( new Date() );
                  // 调用删除接口
                  clientOrderOTService.deleteClientOrderOT( clientOrderOTVOForDel );
               }

               insertlog( request, clientOrderOTVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( clientOrderOTVO.getSelectedIds() ) );
            }
         }

         // 清除Selected IDs和子Action
         clientOrderOTVO.setSelectedIds( "" );
         clientOrderOTVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

}

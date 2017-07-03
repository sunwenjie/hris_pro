package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

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
import com.kan.hro.domain.biz.client.ClientOrderLeaveVO;
import com.kan.hro.service.inf.biz.client.ClientOrderLeaveService;

/**  
 * 项目名称：HRO_V1  
 * 类名称：ClientOrderLeaveAction  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-8-19  
 */
public class ClientOrderLeaveAction extends BaseAction
{

   // 当前Action对应的Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT_ORDER_LEAVE";
   public final static String accessAction_in_house = "HRO_BIZ_CLIENT_ORDER_LEAVE_IN_HOUSE";

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
         final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );
         // 获得Action Form
         final ClientOrderLeaveVO clientOrderLeaveVO = ( ClientOrderLeaveVO ) form;
         clientOrderLeaveVO.setAccountId( getAccountId( request, response ) );

         // 如果子Action是删除用户列表
         if ( clientOrderLeaveVO.getSubAction() != null && clientOrderLeaveVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( clientOrderLeaveVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // 传入当前页
         pageListHolder.setPage( page );
         // 传入当前值对象
         pageListHolder.setObject( clientOrderLeaveVO );
         // 设置页面记录条数
         pageListHolder.setPageSize( listPageSize );
         // 获得SubAction
         String subAction = "";

         // 如果子SubAction不为空
         if ( clientOrderLeaveVO.getSubAction() != null )
         {
            subAction = clientOrderLeaveVO.getSubAction().trim();
         }

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         clientOrderLeaveService.getClientOrderLeaveVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_LEAVE" ) );
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
      return mapping.findForward( "listClientOrderLeave" );
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

      // 获得OrderHeaderId
      final String orderHeaderId = KANUtil.decodeString( request.getParameter( "orderHeaderId" ) );

      ( ( ClientOrderLeaveVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( ClientOrderLeaveVO ) form ).setOrderHeaderId( orderHeaderId );
      ( ( ClientOrderLeaveVO ) form ).setLegalQuantity( "0" );
      ( ( ClientOrderLeaveVO ) form ).setBenefitQuantity( "0" );
      ( ( ClientOrderLeaveVO ) form ).setCycle( "1" );
      ( ( ClientOrderLeaveVO ) form ).setProbationUsing( ClientOrderLeaveVO.TRUE );
      ( ( ClientOrderLeaveVO ) form ).setDelayUsing( ClientOrderLeaveVO.FALSE );
      ( ( ClientOrderLeaveVO ) form ).setStatus( ClientOrderLeaveVO.TRUE );

      // 国际化
      ( ( ClientOrderLeaveVO ) form ).reset( null, request );

      // 跳转到新建界面
      return mapping.findForward( "manageClientOrderLeave" );
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
         final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );

         // 获得当前主键
         String orderLeaveId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( orderLeaveId == null || orderLeaveId.trim().isEmpty() )
         {
            orderLeaveId = ( ( ClientOrderLeaveVO ) form ).getOrderLeaveId();
         }

         // 获得ClientOrderLeaveVO
         final ClientOrderLeaveVO clientOrderLeaveVO = clientOrderLeaveService.getClientOrderLeaveVOByClientOrderLeaveId( orderLeaveId );

         // 刷新VO对象，初始化对象列表及国际化
         clientOrderLeaveVO.reset( null, request );
         // 设置Sub Action
         clientOrderLeaveVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientOrderLeaveForm", clientOrderLeaveVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageClientOrderLeave" );
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
            final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );

            // 获得ActionForm
            final ClientOrderLeaveVO clientOrderLeaveVO = ( ClientOrderLeaveVO ) form;
            // 获取登录用户
            clientOrderLeaveVO.setCreateBy( getUserId( request, response ) );
            clientOrderLeaveVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            clientOrderLeaveVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // 新建对象
            clientOrderLeaveService.insertClientOrderLeave( clientOrderLeaveVO );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, clientOrderLeaveVO, Operate.ADD, clientOrderLeaveVO.getOrderLeaveId(), null );
         }

         // 清空Form条件
         ( ( ClientOrderLeaveVO ) form ).reset();
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
            final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );
            // 获得当前主键
            final String orderLeaveId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final ClientOrderLeaveVO clientOrderLeaveVO = clientOrderLeaveService.getClientOrderLeaveVOByClientOrderLeaveId( orderLeaveId );
            // 获取登录用户
            clientOrderLeaveVO.update( ( ClientOrderLeaveVO ) form );
            clientOrderLeaveVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            clientOrderLeaveVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // 修改对象
            clientOrderLeaveService.updateClientOrderLeave( clientOrderLeaveVO );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, clientOrderLeaveVO, Operate.MODIFY, clientOrderLeaveVO.getOrderLeaveId(), null );
         }

         // 清空Form条件
         ( ( ClientOrderLeaveVO ) form ).reset();
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
         final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );

         // 获取主键
         final String clientOrderLeaveId = KANUtil.decodeStringFromAjax( request.getParameter( "clientOrderLeaveId" ) );

         // 根据主键获得对应VO
         final ClientOrderLeaveVO clientOrderLeaveVO = clientOrderLeaveService.getClientOrderLeaveVOByClientOrderLeaveId( clientOrderLeaveId );
         clientOrderLeaveVO.setModifyBy( getUserId( request, response ) );
         clientOrderLeaveVO.setModifyDate( new Date() );

         // 调用删除接口
         final long rows = clientOrderLeaveService.deleteClientOrderLeave( clientOrderLeaveVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, clientOrderLeaveVO, Operate.DELETE, clientOrderLeaveVO.getOrderLeaveId(), "ajax delete" );
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
         final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );
         // 获得Action Form
         ClientOrderLeaveVO clientOrderLeaveVO = ( ClientOrderLeaveVO ) form;

         // 存在选中的ID
         if ( clientOrderLeaveVO.getSelectedIds() != null && !clientOrderLeaveVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : clientOrderLeaveVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // 主键转码
                  final String clientOrderLeaveId = KANUtil.decodeStringFromAjax( selectedId );
                  // 根据主键获得对应VO
                  final ClientOrderLeaveVO clientOrderLeaveVOForDel = clientOrderLeaveService.getClientOrderLeaveVOByClientOrderLeaveId( clientOrderLeaveId );
                  clientOrderLeaveVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderLeaveVOForDel.setModifyDate( new Date() );
                  // 调用删除接口
                  clientOrderLeaveService.deleteClientOrderLeave( clientOrderLeaveVOForDel );
               }
            }

            insertlog( request, clientOrderLeaveVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( clientOrderLeaveVO.getSelectedIds() ) );
         }

         // 清除Selected IDs和子Action
         clientOrderLeaveVO.setSelectedIds( "" );
         clientOrderLeaveVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // 校验休假设置不能重复
   private boolean checkClientOrderLeave( final List< Object > clientOrderLeaveVOs, final String itemId, final String year, final String orderLeaveId )
   {
      boolean flag = true;
      if ( clientOrderLeaveVOs != null && clientOrderLeaveVOs.size() > 0 )
      {
         for ( Object o : clientOrderLeaveVOs )
         {
            final ClientOrderLeaveVO tempClientOrderLeaveVO = ( ClientOrderLeaveVO ) o;
            if ( orderLeaveId != null && tempClientOrderLeaveVO.getOrderLeaveId().equals( orderLeaveId ) )
               continue;

            // 如果是年假
            if ( "41".equals( itemId ) )
            {
               if ( tempClientOrderLeaveVO.getItemId().equals( itemId ) && tempClientOrderLeaveVO.getYear().equals( year ) )
               {
                  flag = false;
                  break;
               }
            }
            else
            {
               if ( tempClientOrderLeaveVO.getItemId().equals( itemId ) )
               {
                  flag = false;
                  break;
               }
            }
         }
      }

      return flag;
   }

   public void checkClientOrderLeave_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
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
         final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );

         final String orderHeaderId = request.getParameter( "orderHeaderId" );
         final String itemId = request.getParameter( "itemId" );
         final String year = request.getParameter( "year" );
         final String orderLeaveId = request.getParameter( "orderLeaveId" );

         final List< Object > clientOrderLeaveVOs = clientOrderLeaveService.getClientOrderLeaveVOsByOrderHeaderId( orderHeaderId );

         boolean exist = checkClientOrderLeave( clientOrderLeaveVOs, itemId, year, orderLeaveId );

         out.print( exist ? "1" : "2" );
         out.flush();
         out.close();
      }
      catch ( Exception e )
      {
         throw new KANException( e );
      }
   }

}

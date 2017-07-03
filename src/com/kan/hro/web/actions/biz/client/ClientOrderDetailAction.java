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
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientOrderDetailSBRuleVO;
import com.kan.hro.domain.biz.client.ClientOrderDetailVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailRuleService;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailSBRuleService;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;

/**  
 * 项目名称：HRO_V1  
 * 类名称：ClientOrderDetailAction  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-8-19  
 */
public class ClientOrderDetailAction extends BaseAction
{
   public static String accessAction = "HRO_BIZ_CLIENT_ORDER_DETAIL";

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
         final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
         // 获得Action Form
         final ClientOrderDetailVO clientOrderDetailVO = ( ClientOrderDetailVO ) form;
         clientOrderDetailVO.setAccountId( getAccountId( request, response ) );

         // 如果子Action是删除用户列表
         if ( clientOrderDetailVO.getSubAction() != null && clientOrderDetailVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( clientOrderDetailVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // 传入当前页
         pageListHolder.setPage( page );
         // 传入当前值对象
         pageListHolder.setObject( clientOrderDetailVO );
         // 设置页面记录条数
         pageListHolder.setPageSize( listPageSize );
         // 获得SubAction
         String subAction = "";

         // 如果子SubAction不为空
         if ( clientOrderDetailVO.getSubAction() != null )
         {
            subAction = clientOrderDetailVO.getSubAction().trim();
         }

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         clientOrderDetailService.getClientOrderDetailVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_DETAIL" ) );
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
      return mapping.findForward( "listClientOrderDetail" );
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

      // 获得OrderHeaderId
      final String orderHeaderId = KANUtil.decodeString( request.getParameter( "orderHeaderId" ) );
      // 获得ClientOrderHeaderVO
      final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

      ( ( ClientOrderDetailVO ) form ).setOrderHeaderId( orderHeaderId );
      ( ( ClientOrderDetailVO ) form ).setPackageType( "1" );
      ( ( ClientOrderDetailVO ) form ).setDivideType( "1" );
      ( ( ClientOrderDetailVO ) form ).setCalculateType( "4" );
      ( ( ClientOrderDetailVO ) form ).setBase( "0.00" );
      ( ( ClientOrderDetailVO ) form ).setResultCap( "0.00" );
      ( ( ClientOrderDetailVO ) form ).setResultFloor( "0.00" );
      ( ( ClientOrderDetailVO ) form ).setCycle( "1" );
      ( ( ClientOrderDetailVO ) form ).setStartDate( clientOrderHeaderVO.getStartDate() );
      ( ( ClientOrderDetailVO ) form ).setEndDate( clientOrderHeaderVO.getEndDate() );
      ( ( ClientOrderDetailVO ) form ).setStatus( ClientOrderDetailVO.TRUE );
      request.setAttribute( "clientOrderHeaderVO", clientOrderHeaderVO );
      // 设置Sub Action
      ( ( ClientOrderDetailVO ) form ).setSubAction( CREATE_OBJECT );

      // 国际化
      ( ( ClientOrderDetailVO ) form ).reset( null, request );

      // 跳转到新建界面
      return mapping.findForward( "manageClientOrderDetail" );
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
         final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         // 获得当前主键
         String orderDetailId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( orderDetailId == null || orderDetailId.trim().isEmpty() )
         {
            orderDetailId = ( ( ClientOrderDetailVO ) form ).getOrderDetailId();
         }

         // 获得ClientOrderDetailVO
         final ClientOrderDetailVO clientOrderDetailVO = clientOrderDetailService.getClientOrderDetailVOByClientOrderDetailId( orderDetailId );
         // 刷新VO对象，初始化对象列表及国际化
         clientOrderDetailVO.reset( null, request );
         clientOrderDetailVO.setSubAction( VIEW_OBJECT );
         // 获取header
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( clientOrderDetailVO.getOrderHeaderId() );
         request.setAttribute( "clientOrderDetailForm", clientOrderDetailVO );
         request.setAttribute( "clientOrderHeaderVO", clientOrderHeaderVO );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageClientOrderDetail" );
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
            final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
            // 获得ActionForm
            final ClientOrderDetailVO clientOrderDetailVO = ( ClientOrderDetailVO ) form;
            // 获取登录用户
            clientOrderDetailVO.setCreateBy( getUserId( request, response ) );
            clientOrderDetailVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            clientOrderDetailVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // 新建对象
            clientOrderDetailService.insertClientOrderDetail( clientOrderDetailVO );

            // 判断是否需要转向
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // 生成转向地址
               forwardURL = forwardURL + ( ( ClientOrderDetailVO ) form ).getEncodedId();
               request.getRequestDispatcher( forwardURL ).forward( request, response );

               return null;
            }

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }

         // 清空Form条件
         ( ( ClientOrderDetailVO ) form ).reset();
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
   // Reviewed by Kevin Jin at 2013-10-20
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
            // 获得当前主键
            final String clientOrderDetailId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final ClientOrderDetailVO clientOrderDetailVO = clientOrderDetailService.getClientOrderDetailVOByClientOrderDetailId( clientOrderDetailId );
            clientOrderDetailVO.reset( null, request );
            // 获取登录用户
            clientOrderDetailVO.update( ( ClientOrderDetailVO ) form );
            // 保存自定义Column
            clientOrderDetailVO.setRemark1( saveDefineColumns( request, accessAction ) );
            clientOrderDetailVO.setModifyBy( getUserId( request, response ) );
            // 修改对象
            clientOrderDetailService.updateClientOrderDetail( clientOrderDetailVO );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }

         // 清空Form条件
         ( ( ClientOrderDetailVO ) form ).reset();
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
   // Added by Kevin Jin at 2013-10-19
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );

         // 获取主键
         final String clientOrderDetailId = KANUtil.decodeStringFromAjax( request.getParameter( "clientOrderDetailId" ) );

         // 根据主键获得对应VO
         final ClientOrderDetailVO clientOrderDetailVO = clientOrderDetailService.getClientOrderDetailVOByClientOrderDetailId( clientOrderDetailId );
         clientOrderDetailVO.reset( mapping, request );
         clientOrderDetailVO.setModifyBy( getUserId( request, response ) );
         clientOrderDetailVO.setModifyDate( new Date() );

         // 调用删除接口
         final long rows = clientOrderDetailService.deleteClientOrderDetail( clientOrderDetailVO );

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
         final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
         // 获得Action Form
         ClientOrderDetailVO clientOrderDetailVO = ( ClientOrderDetailVO ) form;

         // 存在选中的ID
         if ( clientOrderDetailVO.getSelectedIds() != null && !clientOrderDetailVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : clientOrderDetailVO.getSelectedIds().split( "," ) )
            {

               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // 主键转码
                  final String clientOrderDetailId = KANUtil.decodeStringFromAjax( selectedId );
                  // 根据主键获得对应VO
                  final ClientOrderDetailVO clientOrderDetailVOForDel = clientOrderDetailService.getClientOrderDetailVOByClientOrderDetailId( clientOrderDetailId );
                  clientOrderDetailVO.reset( mapping, request );
                  clientOrderDetailVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderDetailVOForDel.setModifyDate( new Date() );
                  // 调用删除接口
                  clientOrderDetailService.deleteClientOrderDetail( clientOrderDetailVOForDel );
               }

            }
         }

         // 清除Selected IDs和子Action
         clientOrderDetailVO.setSelectedIds( "" );
         clientOrderDetailVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获取未解码 Client Order Detail Id
         final String decodeClientOrderDetailId = request.getParameter( "orderDetailId" );
         // 初始化clientOrderDetailId
         String clientOrderDetailId = "";

         if ( decodeClientOrderDetailId != null && !decodeClientOrderDetailId.trim().equals( "" ) )
         {
            clientOrderDetailId = KANUtil.decodeString( decodeClientOrderDetailId );

            // 初始化Service接口
            final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
            // 根据主键查找对应的clientOrderDetailVO
            final ClientOrderDetailVO clientOrderDetailVO = clientOrderDetailService.getClientOrderDetailVOByClientOrderDetailId( clientOrderDetailId );

            // 刷新VO对象，初始化对象列表及国际化
            clientOrderDetailVO.reset( null, request );
            request.setAttribute( "clientOrderDetailForm", clientOrderDetailVO );
         }
         else
         {
            request.setAttribute( "clientOrderDetailForm", form );
         }

         // 初始化Client Order Detail Rule接口
         final ClientOrderDetailRuleService clientOrderDetailRuleService = ( ClientOrderDetailRuleService ) getService( "clientOrderDetailRuleService" );

         final List< Object > clientOrderDetailRuleList = clientOrderDetailRuleService.getClientOrderDetailRuleVOsByClientOrderDetailId( clientOrderDetailId );

         if ( clientOrderDetailRuleList != null && clientOrderDetailRuleList.size() > 0 )
         {
            for ( Object obj : clientOrderDetailRuleList )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         //  加载紧主表规则
         request.setAttribute( "clientOrderDetailRuleList", clientOrderDetailRuleList );
         // 发送数组长度
         request.setAttribute( "clientOrderDetailRuleListSize", ( clientOrderDetailRuleList == null ) ? ( "0" ) : ( clientOrderDetailRuleList.size() ) );

         final ClientOrderDetailSBRuleService clientOrderDetailSBRuleService = ( ClientOrderDetailSBRuleService ) getService( "clientOrderDetailSBRuleService" );

         final List< Object > clientOrderDetailSBRuleList = clientOrderDetailSBRuleService.getClientOrderDetailSBRuleVOsByClientOrderDetailId( clientOrderDetailId );

         if ( clientOrderDetailSBRuleList != null && clientOrderDetailSBRuleList.size() > 0 )
         {
            for ( Object obj : clientOrderDetailSBRuleList )
            {
               final ClientOrderDetailSBRuleVO clientOrderDetailSBRuleVO = ( ClientOrderDetailSBRuleVO ) obj;
               clientOrderDetailSBRuleVO.setSbRuleTypes( KANUtil.getMappings( request.getLocale(), "business.client.order.detail.sb.rule" ) );
               clientOrderDetailSBRuleVO.setSbSolutionIds( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getSocialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) ) );
            }
         }

         // 加载社保补缴规则
         request.setAttribute( "clientOrderDetailSBRuleList", clientOrderDetailSBRuleList );

         request.setAttribute( "clientOrderDetailSBRuleListSize", ( clientOrderDetailSBRuleList == null ) ? ( "0" ) : ( clientOrderDetailSBRuleList.size() ) );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageClientOrderDetailSpecialInfo" );
   }

}

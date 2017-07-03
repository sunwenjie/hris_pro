package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.base.web.renders.util.ListRender;
import com.kan.hro.domain.biz.client.ClientOrderCBVO;
import com.kan.hro.service.inf.biz.client.ClientOrderCBService;

/**  
 * 项目名称：HRO_V1  
 * 类名称：ClientOrderCBAction  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-8-19  
 */
public class ClientOrderCBAction extends BaseAction
{

   // 当前Action对应的Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT_ORDER_CB";
   public final static String accessAction_in_house = "HRO_BIZ_CLIENT_ORDER_CB_IN_HOUSE";

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
         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );
         // 获得Action Form
         final ClientOrderCBVO clientOrderCBVO = ( ClientOrderCBVO ) form;
         clientOrderCBVO.setAccountId( getAccountId( request, response ) );

         // 如果子Action是删除用户列表
         if ( clientOrderCBVO.getSubAction() != null && clientOrderCBVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            //             调用删除用户列表的Action
            delete_objectList( mapping, form, request, response );
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( clientOrderCBVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder pageListHolder = new PagedListHolder();
         // 传入当前页
         pageListHolder.setPage( page );
         // 传入当前值对象
         pageListHolder.setObject( clientOrderCBVO );
         // 设置页面记录条数
         pageListHolder.setPageSize( listPageSize );
         // 获得SubAction
         String subAction = "";

         // 如果子SubAction不为空
         if ( clientOrderCBVO.getSubAction() != null )
         {
            subAction = clientOrderCBVO.getSubAction().trim();
         }

         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         clientOrderCBService.getClientOrderCBVOsByCondition( pageListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : true );
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
               out.println( ListRender.generateListTable( request, "HRO_BIZ_CLIENT_ORDER_SB" ) );
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
      return mapping.findForward( "listClientOrderCB" );
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

      ( ( ClientOrderCBVO ) form ).setOrderHeaderId( orderHeaderId );
      ( ( ClientOrderCBVO ) form ).setFreeShortOfMonth( "2" );
      ( ( ClientOrderCBVO ) form ).setChargeFullMonth( "2" );
      ( ( ClientOrderCBVO ) form ).setSubAction( CREATE_OBJECT );
      ( ( ClientOrderCBVO ) form ).setStatus( "1" );

      // 国际化
      ( ( ClientOrderCBVO ) form ).reset( null, request );

      // 跳转到新建界面
      return mapping.findForward( "manageClientOrderCB" );
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
         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );

         // 获得当前主键
         String orderCBId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );

         if ( orderCBId == null || orderCBId.trim().isEmpty() )
         {
            orderCBId = ( ( ClientOrderCBVO ) form ).getOrderCbId();
         }

         //  获得ClientOrderCBVO
         final ClientOrderCBVO clientOrderCBVO = clientOrderCBService.getClientOrderCBVOByClientOrderCBId( orderCBId );

         // 刷新VO对象，初始化对象列表及国际化
         clientOrderCBVO.reset( null, request );
         // 设置Sub Action
         clientOrderCBVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientOrderCBForm", clientOrderCBVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageClientOrderCB" );
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
            final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );

            // 获得ActionForm
            final ClientOrderCBVO clientOrderCBVO = ( ClientOrderCBVO ) form;
            // 获取登录用户
            clientOrderCBVO.setCreateBy( getUserId( request, response ) );
            clientOrderCBVO.setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            clientOrderCBVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // 新建对象
            clientOrderCBService.insertClientOrderCB( clientOrderCBVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            insertlog( request, clientOrderCBVO, Operate.ADD, clientOrderCBVO.getOrderCbId(), null );
         }

         // 清空Form条件
         ( ( ClientOrderCBVO ) form ).reset();
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
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );
            // 获得当前主键
            final String clientOrderCBId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得主键对应对象
            final ClientOrderCBVO clientOrderCBVO = clientOrderCBService.getClientOrderCBVOByClientOrderCBId( clientOrderCBId );
            // 获取登录用户
            clientOrderCBVO.update( ( ClientOrderCBVO ) form );
            // 保存自定义Column
            clientOrderCBVO.setRemark1( saveDefineColumns( request, accessAction ) );
            clientOrderCBVO.setModifyBy( getUserId( request, response ) );
            // 修改对象
            clientOrderCBService.updateClientOrderCB( clientOrderCBVO );
            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );

            insertlog( request, clientOrderCBVO, Operate.MODIFY, clientOrderCBVO.getOrderCbId(), null );
         }

         // 清空Form条件
         ( ( ClientOrderCBVO ) form ).reset();
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
         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );

         // 获取主键
         final String clientOrderCBId = KANUtil.decodeStringFromAjax( request.getParameter( "clientOrderCBId" ) );

         // 根据主键获得对应VO
         final ClientOrderCBVO clientOrderCBVO = clientOrderCBService.getClientOrderCBVOByClientOrderCBId( clientOrderCBId );
         clientOrderCBVO.setModifyBy( getUserId( request, response ) );
         clientOrderCBVO.setModifyDate( new Date() );

         // 调用删除接口
         final long rows = clientOrderCBService.deleteClientOrderCB( clientOrderCBVO );

         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 返回状态至Ajax
         if ( rows > 0 )
         {
            deleteSuccessAjax( out, null );
            insertlog( request, clientOrderCBVO, Operate.DELETE, clientOrderCBVO.getOrderCbId(), "ajax delete" );
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
         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );
         // 获得Action Form
         ClientOrderCBVO clientOrderCBVO = ( ClientOrderCBVO ) form;

         // 存在选中的ID
         if ( clientOrderCBVO.getSelectedIds() != null && !clientOrderCBVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : clientOrderCBVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // 主键转码
                  final String clientOrderCBId = KANUtil.decodeStringFromAjax( selectedId );
                  // 根据主键获得对应VO
                  final ClientOrderCBVO clientOrderCBVOForDel = clientOrderCBService.getClientOrderCBVOByClientOrderCBId( clientOrderCBId );
                  clientOrderCBVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderCBVOForDel.setModifyDate( new Date() );
                  // 调用删除接口
                  clientOrderCBService.deleteClientOrderCB( clientOrderCBVOForDel );
               }
            }

            insertlog( request, clientOrderCBVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( clientOrderCBVO.getSelectedIds() ) );
         }

         // 清除Selected IDs和子Action
         clientOrderCBVO.setSelectedIds( "" );
         clientOrderCBVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * 
    * 	List Object Options Ajax
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         final String cbSolutionId = request.getParameter( "solutionId" );

         // 初始化
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         // 如果是In House登录
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            mappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getCommercialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) ) );
         }
         // 如果是Hr Service登录
         else
         {
            mappingVOs.addAll( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getCommercialBenefitSolutions( request.getLocale().getLanguage() ) );
         }
         // 添加super 
         mappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getCommercialBenefitSolutions( request.getLocale().getLanguage() ) );

         mappingVOs.add( 0, ( ( ClientOrderCBVO ) form ).getEmptyMappingVO() );
         out.println( KANUtil.getOptionHTML( mappingVOs, "cbSolutionId", cbSolutionId ) );

         // Send to client
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

   public ActionForward get_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获取商报方案
         final String solutionId = request.getParameter( "solutionId" );
         // 获取订单ID
         final String orderId = request.getParameter( "orderId" );

         // 初始化Service
         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );
         final List< Object > clientOrderCBVOs = clientOrderCBService.getClientOrderCBVOsByClientOrderHeaderId( orderId );
         ClientOrderCBVO clientOrderCBVO = null;
         for ( Object cbvoObject : clientOrderCBVOs )
         {
            ClientOrderCBVO cbvo = ( ClientOrderCBVO ) cbvoObject;
            if ( KANUtil.filterEmpty( solutionId ) != null )
            {
               if ( solutionId.equals( cbvo.getCbSolutionId() ) )
               {
                  clientOrderCBVO = cbvo;
                  break;
               }
            }
         }

         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();
         if ( clientOrderCBVO == null )
         {
            jsonObject.put( "success", "false" );
         }
         else
         {
            jsonObject.put( "freeShortOfMonth", KANUtil.filterEmpty( clientOrderCBVO.getFreeShortOfMonth() ) == null ? "0" : clientOrderCBVO.getFreeShortOfMonth() );
            jsonObject.put( "chargeFullMonth", KANUtil.filterEmpty( clientOrderCBVO.getChargeFullMonth() ) == null ? "0" : clientOrderCBVO.getChargeFullMonth() );
            jsonObject.put( "success", "true" );
         }
         // Send to client
         out.println( jsonObject.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return mapping.findForward( "" );
   }

   // 快速创建员工，根据结算规则获取商报.super有商报，没有社保
   public ActionForward list_object_options_byOrderHeaderId_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         final String orderHeaderId = request.getParameter( "orderHeaderId" );

         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );

         // 初始化
         final List< MappingVO > targetMappingVOs = new ArrayList< MappingVO >();
         final List< MappingVO > allMappingVOs = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getCommercialBenefitSolutions( request.getLocale().getLanguage(), getCorpId( request, response ) );

         final List< Object > clientOrderCBVOs = clientOrderCBService.getClientOrderCBVOsByClientOrderHeaderId( orderHeaderId );

         if ( clientOrderCBVOs != null && clientOrderCBVOs.size() > 0 )
         {
            for ( Object clientOrderCBObject : clientOrderCBVOs )
            {
               // 获取结算规则商保
               for ( MappingVO mappingVO : allMappingVOs )
               {
                  if ( mappingVO.getMappingId().equals( ( ( ClientOrderCBVO ) clientOrderCBObject ).getCbSolutionId() ) )
                  {
                     targetMappingVOs.add( mappingVO );
                  }
               }
            }
         }
         else
         {
            // 如果结算规则里面没有商报，则添加账号的所有商保
            targetMappingVOs.addAll( allMappingVOs );
         }
         // 添加super 
         targetMappingVOs.addAll( KANConstants.getKANAccountConstants( "1" ).getCommercialBenefitSolutions( request.getLocale().getLanguage() ) );
         targetMappingVOs.add( 0, ( ( ClientOrderCBVO ) form ).getEmptyMappingVO() );
         out.println( KANUtil.getOptionHTML( targetMappingVOs, "cbSolutionId", null ) );

         // Send to client
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return null;
   }

}

package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
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

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientContactVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.service.inf.biz.client.ClientContactService;
import com.kan.hro.service.inf.biz.client.ClientService;

/**  
 * 项目名称：HRO_V1  
 * 类名称：ClientContactAction  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-8-8  
 */
public class ClientContactAction extends BaseAction
{
   // 当前Action对应的Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT_CONTACT";

   /**  
    * Get Object Json
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
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

         // 初始化 Service
         final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );

         // 获取ClientContactId
         final String clientContactId = request.getParameter( "clientContactId" );

         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();

         // 初始化ClientContactVO
         final ClientContactVO clientContactVO = clientContactService.getClientContactVOByClientContactId( clientContactId );
         if ( clientContactVO != null && clientContactVO.getAccountId() != null && clientContactVO.getAccountId().equals( getAccountId( request, response ) ) )
         {
            clientContactVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( clientContactVO );
            jsonObject.put( "success", "true" );
         }
         else
         {
            jsonObject.put( "success", "false" );
         }

         // Send to front
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

   /**
    * list object json
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-15
   public ActionForward list_object_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化 Service
         final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );

         // 获取当前登录用户的accountId
         final String clientId = KANUtil.decodeString( request.getParameter( "clientId" ) );

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( clientContactService.getClientContactVOsByClientId( clientId ) );

         // Send to clientContactGroup
         out.println( array.toString() );
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

   /**  
    *	List Object Options Ajax
    * 显示联系人下拉框
    * 
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-15
   public ActionForward list_object_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化下拉选项
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // 初始化联系人
         String clientContactId = request.getParameter( "contactId" );

         // 如果ClientId不为空
         if ( request.getParameter( "clientId" ) != null && !request.getParameter( "clientId" ).trim().isEmpty() )
         {
            // 初始化Service接口
            final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );
            final ClientService clientService = ( ClientService ) getService( "clientService" );

            // 获取ClientVO
            final ClientVO clientVO = clientService.getClientVOByClientId( KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) ) );

            // 联系人为空的情况，默认取主要联系人
            if ( clientContactId == null || clientContactId.trim().isEmpty() )
            {
               clientContactId = clientVO.getMainContact();
            }

            final List< Object > clientContactVOs = clientContactService.getClientContactVOsByClientId( KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) ) );

            if ( clientContactVOs != null && clientContactVOs.size() > 0 )
            {
               // 遍历
               for ( Object clientContactVOObject : clientContactVOs )
               {
                  final ClientContactVO tempClientContactVO = ( ClientContactVO ) clientContactVOObject;

                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( tempClientContactVO.getClientContactId() );

                  // 中文
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     mappingVO.setMappingValue( tempClientContactVO.getNameZH() );
                  }
                  else
                  {
                     mappingVO.setMappingValue( tempClientContactVO.getNameEN() );
                  }

                  mappingVOs.add( mappingVO );
               }
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "clientContactId", clientContactId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "" );
   }

   /**
    * List ClientContact
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
         // 初始化Service接口
         final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );
         // 获得Action Form
         final ClientContactVO clientContactVO = ( ClientContactVO ) form;
         // 获得SubAction
         final String subAction = getSubAction( form );

         // 添加自定义搜索内容
         clientContactVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         // 如果没有指定排序则默认按ClientcontactId排序
         if ( clientContactVO.getSortColumn() == null || clientContactVO.getSortColumn().isEmpty() )
         {
            clientContactVO.setSortColumn( "clientContactId" );
            clientContactVO.setSortOrder( "desc" );
         }

         // 处理SubAction
         dealSubAction( clientContactVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder clientContactHolder = new PagedListHolder();

         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 传入当前页
            clientContactHolder.setPage( page );
            // 传入当前值对象
            clientContactHolder.setObject( clientContactVO );
            // 设置页面记录条数
            clientContactHolder.setPageSize( getPageSize( request, accessAction ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            clientContactService.getClientContactVOsByCondition( clientContactHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // 刷新Holder，国际化传值
            refreshHolder( clientContactHolder, request );
         }

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", clientContactHolder );
         // 处理Return
         return dealReturn( accessAction, "listClientContact", mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 设置SubAction 及默认值
         ( ( ClientContactVO ) form ).setSubAction( CREATE_OBJECT );
         ( ( ClientContactVO ) form ).setStatus( ClientContactVO.TRUE );

         // 初始化PositionVO
         final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

         if ( positionVO != null )
         {
            ( ( ClientContactVO ) form ).setBranch( positionVO.getBranchId() );
            ( ( ClientContactVO ) form ).setOwner( positionVO.getPositionId() );
         }

         // 如果Client Id不为空
         if ( request.getParameter( "clientId" ) != null && !request.getParameter( "clientId" ).trim().equals( "" ) )
         {
            // 获取Client Id
            final String clientId = KANUtil.decodeString( request.getParameter( "clientId" ) );
            // 设置Client Id
            ( ( ClientContactVO ) form ).setClientId( clientId );
            // 初始化接口
            final ClientService clientService = ( ClientService ) getService( "clientService" );
            // 根据Client Id 获得对应的ClientVO
            final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
            // 设置Client Name
            ( ( ClientContactVO ) form ).setClientId( clientVO.getClientId() );
            ( ( ClientContactVO ) form ).setClientNameZH( clientVO.getNameZH() );
            ( ( ClientContactVO ) form ).setClientNameEN( clientVO.getNameEN() );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到新建界面
      return mapping.findForward( "manageClientContact" );
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
   // Reviewed by Kevin Jin at 2013-11-13
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );

         // 获得当前主键
         String contactId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( contactId == null || contactId.trim().isEmpty() )
         {
            contactId = ( ( ClientContactVO ) form ).getClientContactId();
         }

         // 获得ClientContactVO
         final ClientContactVO clientContactVO = clientContactService.getClientContactVOByClientContactId( contactId );
         // 刷新对象，初始化对象列表及国际化
         clientContactVO.reset( null, request );

         // 如果存在City Id，则填充Province Id
         if ( KANUtil.filterEmpty( clientContactVO.getCityId(), "0" ) != null )
         {
            clientContactVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( clientContactVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            clientContactVO.setCityIdTemp( clientContactVO.getCityId() );
         }

         clientContactVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientContactForm", clientContactVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageClientContact" );
   }

   /**
    * Add Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-13
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化输入值错误数
            request.setAttribute( "errorCount", 0 );
            // 检查页面输入值
            checkClientId( mapping, form, request, response );

            // 页面跳转控制
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // 初始化Service接口
            final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );

            // 获得ActionForm
            final ClientContactVO clientContactVO = ( ClientContactVO ) form;
            clientContactVO.setCreateBy( getUserId( request, response ) );
            clientContactVO.setModifyBy( getUserId( request, response ) );
            clientContactVO.setAccountName( BaseAction.getPropertyFromCookie( request, response, "accountName" ) );
            // 保存自定义Column
            clientContactVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // 新建对象
            clientContactService.insertClientContact( clientContactVO );

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify clientContact
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-13
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化输入值错误数
            request.setAttribute( "errorCount", 0 );
            // 检查页面输入值
            checkClientId( mapping, form, request, response );

            // 页面跳转控制
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // 初始化Service接口
            final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );

            // 获得 ClientContactVO
            final ClientContactVO clientContactVO = clientContactService.getClientContactVOByClientContactId( KANUtil.decodeString( request.getParameter( "id" ) ) );

            clientContactVO.update( ( ClientContactVO ) form );
            // 保存自定义Column
            clientContactVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // 获取登录用户
            clientContactVO.setModifyBy( getUserId( request, response ) );
            clientContactVO.setModifyDate( new Date() );
            clientContactVO.reset( mapping, request );
            clientContactVO.setAccountName( BaseAction.getPropertyFromCookie( request, response, "accountName" ) );
            // 修改对象
            clientContactService.updateClientContact( clientContactVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Delete clientContact
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
      // No Use
   }

   /**
    * Delete clientContact list
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
         final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );
         // 获得Action Form
         final ClientContactVO clientContactVO = ( ClientContactVO ) form;

         // 存在选中的ID
         if ( clientContactVO.getSelectedIds() != null && !clientContactVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : clientContactVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // 实例化ClientContactVO以删除
                  ClientContactVO tempClientContactVO = clientContactService.getClientContactVOByClientContactId( KANUtil.decodeStringFromAjax( selectedId ) );
                  tempClientContactVO.setModifyBy( getUserId( request, response ) );
                  tempClientContactVO.setModifyDate( new Date() );

                  // 调用删除接口
                  clientContactService.deleteClientContact( tempClientContactVO );
               }
            }
         }

         // 清除Selected IDs和子Action
         clientContactVO.setSelectedIds( "" );
         clientContactVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Delete Object Ajax
    * Tab删除联系人
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @throws KANException
    */
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final ClientContactService clientContactService = ( ClientContactService ) getService( "clientContactService" );

         // 获取主键
         final String contactId = KANUtil.decodeStringFromAjax( request.getParameter( "contactId" ) );

         // 获取ClientContactVO
         final ClientContactVO clientContactVO = clientContactService.getClientContactVOByClientContactId( contactId );
         clientContactVO.setModifyBy( getUserId( request, response ) );
         clientContactVO.setModifyDate( new Date() );

         // 调用删除接口
         final long rows = clientContactService.deleteClientContact( clientContactVO );

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

   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {

         String username = request.getParameter( "username" );
         if ( username != null )
         {
            username = new String( username.getBytes( "ISO8859_1" ), "GBK" );
         }
         final String clientUserId = request.getParameter( "clientUserId" );

         request.setAttribute( "username", username );
         request.setAttribute( "clientUserId", clientUserId );
         // Ajax调用
         return mapping.findForward( "listSpecialInfo" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * To PrePage
    * 页面输入错误返回提交页面
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_prePage( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 添加需设定一个记号，防止重复提交
         this.saveToken( request );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // 跳转到Manage界面   
      return mapping.findForward( "manageClientContact" );
   }

   /**  
    * 检查输入ClientId是否有效
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public void checkClientId( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 初始化Service接口
      final ClientService clientService = ( ClientService ) getService( "clientService" );
      // 获取Form
      final ClientContactVO clientContactVO = ( ClientContactVO ) form;
      // 获得ClientId
      final String clientId = KANUtil.filterEmpty( clientContactVO.getClientId() );

      final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

      if ( clientVO == null )
      {
         request.setAttribute( "clientIdError", "客户ID输入无效！" );
         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );
      }

   }

}

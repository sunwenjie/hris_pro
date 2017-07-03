package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientInvoiceVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.service.inf.biz.client.ClientInvoiceService;
import com.kan.hro.service.inf.biz.client.ClientService;

/**  
 * 项目名称：HRO_V1  
 * 类名称：ClientInvoiceAction  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-8-8  
 */

public class ClientInvoiceAction extends BaseAction
{
   // 当前Action对应的Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT_INVOICE";

   /**  
    * List Object Options Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-05
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

         // 获取InvoiceAddressId
         final String invoiceAddressId = request.getParameter( "invoiceAddressId" );

         // 初始化Service接口
         final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );

         // 获取ClientId
         final String clientId = request.getParameter( "clientId" );

         // 获取ClientInvoiceVO列表
         final List< Object > clientInvoiceVOs = clientInvoiceService.getClientInvoiceVOsByClientId( clientId );

         // 生成下拉框
         if ( clientInvoiceVOs != null && clientInvoiceVOs.size() > 0 )
         {
            for ( Object clientInvoiceVOObject : clientInvoiceVOs )
            {
               final ClientInvoiceVO clientInvoiceVO = ( ClientInvoiceVO ) clientInvoiceVOObject;

               // 初始化MappingVO
               final MappingVO mappingVO = new MappingVO();

               mappingVO.setMappingId( clientInvoiceVO.getClientInvoiceId() );

               // 初始化发票地址
               String invoiceAddress = clientInvoiceVO.getAddress();
               // 中文
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  invoiceAddress = invoiceAddress + "（" + clientInvoiceVO.getNameZH() + "）";
               }
               // 非中文
               else
               {
                  invoiceAddress = invoiceAddress + " (" + clientInvoiceVO.getNameEN() + ")";
               }
               mappingVO.setMappingValue( invoiceAddress );
               mappingVOs.add( mappingVO );
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "invoiceAddressId", invoiceAddressId ) );
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
    * list object json
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
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
         final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );

         // 获取当前登录用户的accountId
         final String accountId = getAccountId( request, response );

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( clientInvoiceService.getClientInvoiceBaseViews( accountId ) );

         // Send to clientInvoiceGroup
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
    * List clientInvoice
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
         final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );
         // 获得Action Form
         final ClientInvoiceVO clientInvoiceVO = ( ClientInvoiceVO ) form;
         // 获得SubAction
         final String subAction = getSubAction( form );

         // 添加自定义搜索内容
         clientInvoiceVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         // 如果没有指定排序则默认按ClientInvoiceId排序
         if ( clientInvoiceVO.getSortColumn() == null || clientInvoiceVO.getSortColumn().isEmpty() )
         {
            clientInvoiceVO.setSortColumn( "clientInvoiceId" );
            clientInvoiceVO.setSortOrder( "desc" );
         }

         // 处理SubAction
         dealSubAction( clientInvoiceVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder clientInvoiceHolder = new PagedListHolder();

         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 传入当前页
            clientInvoiceHolder.setPage( page );
            // 传入当前值对象
            clientInvoiceHolder.setObject( clientInvoiceVO );
            // 设置页面记录条数
            clientInvoiceHolder.setPageSize( getPageSize( request, accessAction ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            clientInvoiceService.getClientInvoiceVOsByCondition( clientInvoiceHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // 刷新Holder，国际化传值
            refreshHolder( clientInvoiceHolder, request );
         }

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", clientInvoiceHolder );
         // 处理Return
         return dealReturn( accessAction, "listClientInvoice", mapping, form, request, response );
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

         // 如果Client Id不为空
         if ( request.getParameter( "clientId" ) != null && !request.getParameter( "clientId" ).trim().equals( "" ) )
         {
            // 获取Client Id
            final String clientId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "clientId" ), "UTF-8" ) );
            // 初始化ClientInvoiceVO
            ( ( ClientInvoiceVO ) form ).reset();
            // 设置Client Id
            ( ( ClientInvoiceVO ) form ).setClientId( clientId );
            // 初始化接口
            final ClientService clientService = ( ClientService ) getService( "clientService" );
            // 根据Client Id 获得对应的ClientVO
            final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
            // 设置Client Name
            ( ( ClientInvoiceVO ) form ).setClientId( clientVO.getClientId() );
            ( ( ClientInvoiceVO ) form ).setClientNameZH( clientVO.getNameZH() );
            ( ( ClientInvoiceVO ) form ).setClientNameEN( clientVO.getNameEN() );
            // 设置法务实体
            ( ( ClientInvoiceVO ) form ).setLegalEntity( clientVO.getLegalEntity() );
            // 设置法务台头
            if ( getLocale( request ).getLanguage().equalsIgnoreCase( "zh" ) )
            {
               ( ( ClientInvoiceVO ) form ).setTitle( clientVO.getNameZH() );
            }
            else
            {
               ( ( ClientInvoiceVO ) form ).setTitle( clientVO.getNameEN() );
            }
            ( ( ClientInvoiceVO ) form ).setTitle( clientVO.getNameZH() );
         }

         // 设置SubAction 及默认值
         ( ( ClientInvoiceVO ) form ).setSubAction( CREATE_OBJECT );
         ( ( ClientInvoiceVO ) form ).setStatus( ClientInvoiceVO.TRUE );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到新建界面
      return mapping.findForward( "manageClientInvoice" );
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
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );

         // 初始化Service接口
         final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );

         // 获得当前主键
         String invoiceId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( invoiceId == null || invoiceId.trim().isEmpty() )
         {
            invoiceId = ( ( ClientInvoiceVO ) form ).getClientInvoiceId();
         }

         // 获得ClientInvoiceVO
         final ClientInvoiceVO clientInvoiceVO = clientInvoiceService.getClientInvoiceVOByClientInvoiceId( invoiceId );
         // 刷新对象，初始化对象列表及国际化
         clientInvoiceVO.reset( null, request );

         // 如果存在City Id，则填充Province Id
         if ( KANUtil.filterEmpty( clientInvoiceVO.getCityId(), "0" ) != null )
         {
            clientInvoiceVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( clientInvoiceVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            clientInvoiceVO.setCityIdTemp( clientInvoiceVO.getCityId() );
         }

         clientInvoiceVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientInvoiceForm", clientInvoiceVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageClientInvoice" );
   }

   /**
    * Add clientInvoice
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-05
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
            final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );

            // 获得ActionForm
            final ClientInvoiceVO clientInvoiceVO = ( ClientInvoiceVO ) form;
            clientInvoiceVO.setCreateBy( getUserId( request, response ) );
            clientInvoiceVO.setModifyBy( getUserId( request, response ) );

            // 保存自定义Column
            clientInvoiceVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // 新建对象
            clientInvoiceService.insertClientInvoice( clientInvoiceVO );

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
    * Modify Client Invoice
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-05
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
            final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );

            // 获得主键对应对象
            final ClientInvoiceVO clientInvoiceVO = clientInvoiceService.getClientInvoiceVOByClientInvoiceId( KANUtil.decodeString( request.getParameter( "id" ) ) );

            // 获取登录用户
            clientInvoiceVO.update( ( ClientInvoiceVO ) form );
            // 保存自定义Column
            clientInvoiceVO.setRemark1( saveDefineColumns( request, accessAction ) );

            clientInvoiceVO.setModifyBy( getUserId( request, response ) );
            clientInvoiceVO.setModifyDate( new Date() );
            clientInvoiceVO.reset( mapping, request );

            // 修改对象
            clientInvoiceService.updateClientInvoice( clientInvoiceVO );

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
    * Delete clientInvoice
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
    * Delete clientInvoice list
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
         final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );
         // 获得Action Form
         final ClientInvoiceVO clientInvoiceVO = ( ClientInvoiceVO ) form;

         // 存在选中的ID
         if ( clientInvoiceVO.getSelectedIds() != null && !clientInvoiceVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : clientInvoiceVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // 实例化ClientInvoiceVO
                  final ClientInvoiceVO tempClientInvoiceVO = clientInvoiceService.getClientInvoiceVOByClientInvoiceId( KANUtil.decodeStringFromAjax( selectedId ) );
                  tempClientInvoiceVO.setModifyBy( getUserId( request, response ) );
                  tempClientInvoiceVO.setModifyDate( new Date() );

                  // 调用删除接口
                  clientInvoiceService.deleteClientInvoice( tempClientInvoiceVO );
               }
            }
         }

         // 清除Selected IDs和子Action
         clientInvoiceVO.setSelectedIds( "" );
         clientInvoiceVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Delete Object Ajax
    * Tab删除账单地址
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
         final ClientInvoiceService clientInvoiceService = ( ClientInvoiceService ) getService( "clientInvoiceService" );

         // 获取主键
         final String invoiceId = KANUtil.decodeStringFromAjax( request.getParameter( "invoiceId" ) );

         // 获取ClientInvoiceVO
         final ClientInvoiceVO clientInvoiceVO = clientInvoiceService.getClientInvoiceVOByClientInvoiceId( invoiceId );
         clientInvoiceVO.setModifyBy( getUserId( request, response ) );
         clientInvoiceVO.setModifyDate( new Date() );

         // 调用删除接口
         final long rows = clientInvoiceService.deleteClientInvoice( clientInvoiceVO );

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
      return mapping.findForward( "manageClientInvoice" );
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
      final ClientInvoiceVO clientInvoiceVO = ( ClientInvoiceVO ) form;
      // 获得ClientId
      final String clientId = KANUtil.filterEmpty( clientInvoiceVO.getClientId() );

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

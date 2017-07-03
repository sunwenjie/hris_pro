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
import com.kan.base.domain.system.CityVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientContractVO;
import com.kan.hro.domain.biz.client.ClientDTO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.service.inf.biz.client.ClientContractService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientService;

/**  
 *   
 * 项目名称：HRO_V1  
 * 类名称：ClientAction  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-8-8  
 *   
 */
public class ClientAction extends BaseAction
{
   // 当前Action对应的Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT";

   /**
    * Get Object Json
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-05
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
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // 获取ClientId
         final String clientId = request.getParameter( "clientId" );

         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();

         // 初始化ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
         if ( clientVO != null && clientVO.getAccountId() != null && clientVO.getAccountId().equals( getAccountId( request, response ) ) )
         {
            clientVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( clientVO );
            jsonObject.put( "success", "true" );

            // 初始化订单绑定合同
            boolean orderBindContract = false;
            if ( clientVO.getOrderBindContract() != null && clientVO.getOrderBindContract().trim().equals( "1" ) )
            {
               orderBindContract = true;
            }
            else
            {
               orderBindContract = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_ORDER_BIND_CONTRACT;
            }

            jsonObject.put( "orderBindContract", orderBindContract );
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
    * List Object Json ForFullView
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_object_json_forFullView( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
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
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // 获取当前登录用户的accountId
         final String accountId = getAccountId( request, response );

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( clientService.getClientFullViews( accountId ) );

         // Send to front
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
    * List Object Ajax
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 初始化Service接口
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // 获得Action Form 
         final ClientVO clientVO = ( ClientVO ) form;

         //处理数据权限
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, clientVO );

         // 解码
         decodedObject( clientVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder clientHolder = new PagedListHolder();

         // 传入当前页
         clientHolder.setPage( page );
         // 传入当前值对象
         clientHolder.setObject( clientVO );
         // 设置页面记录条数
         clientHolder.setPageSize( listPageSize_popup );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         clientService.getClientVOsByCondition( clientHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( clientHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "clientHolder", clientHolder );

         // Ajax Table调用，直接传回JSP
         return mapping.findForward( "popupTable" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

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
         // 初始化Service接口
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // 获得Action Form
         final ClientVO clientVO = ( ClientVO ) form;

         //处理数据权限
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, clientVO );

         // 获得SubAction
         final String subAction = getSubAction( form );

         // 添加自定义搜索内容
         clientVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         // 如果没有指定排序则默认按 ClientId排序
         if ( clientVO.getSortColumn() == null || clientVO.getSortColumn().isEmpty() )
         {
            clientVO.setSortColumn( "clientId" );
            clientVO.setSortOrder( "desc" );
         }

         // 处理SubAction
         dealSubAction( clientVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder clientHolder = new PagedListHolder();

         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 传入当前页
            clientHolder.setPage( page );
            // 传入当前值对象
            clientHolder.setObject( clientVO );
            // 设置页面记录条数
            clientHolder.setPageSize( getPageSize( request, accessAction ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            clientService.getClientVOsByCondition( clientHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // 刷新Holder，国际化传值
            refreshHolder( clientHolder, request );
         }

         request.setAttribute( "pagedListHolder", clientHolder );

         // 处理Return
         return dealReturn( accessAction, "listClient", mapping, form, request, response );
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
   // Reviewed by Kevin Jin at 2013-10-11
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );
      // 页面Reset
      ( ( ClientVO ) form ).reset();
      // 设置Sub Action
      ( ( ClientVO ) form ).setSubAction( CREATE_OBJECT );

      // 设置新建页面默认值
      ( ( ClientVO ) form ).setStatus( ClientVO.TRUE );
      ( ( ClientVO ) form ).setOrderBindContract( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_ORDER_BIND_CONTRACT ? "1" : "2" );
      ( ( ClientVO ) form ).setSbGenerateCondition( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_SB_GENERATE_CONDITION );
      ( ( ClientVO ) form ).setCbGenerateCondition( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_CB_GENERATE_CONDITION );
      ( ( ClientVO ) form ).setSettlementCondition( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_SETTLEMENT_GENERATE_CONDITION );
      ( ( ClientVO ) form ).setSbGenerateConditionSC( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_SB_GENERATE_CONDITION_SC );
      ( ( ClientVO ) form ).setCbGenerateConditionSC( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_CB_GENERATE_CONDITION_SC );
      ( ( ClientVO ) form ).setSettlementConditionSC( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_SETTLEMENT_GENERATE_CONDITION_SC );

      // 判断是否从集团页面添加
      final String encodedGroupId = request.getParameter( "groupId" );

      // 如果是从集团页面添加
      if ( encodedGroupId != null && !encodedGroupId.trim().isEmpty() )
      {
         // 设置ClientVO 的 groupId
         ( ( ClientVO ) form ).setGroupId( KANUtil.decodeString( encodedGroupId ) );
      }

      // 初始化PositionVO
      final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

      // 设置默认“所属部门”、“所属人”
      if ( positionVO != null )
      {
         ( ( ClientVO ) form ).setBranch( positionVO.getBranchId() );
         ( ( ClientVO ) form ).setOwner( positionVO.getPositionId() );
      }

      // 跳转到新建界面
      return mapping.findForward( "manageClient" );
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
   // Reviewed by Kevin Jin at 2013-10-13
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );
         // 初始化Service接口
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // 获得当前主键
         String clientId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( clientId == null || clientId.trim().isEmpty() )
         {
            clientId = ( ( ClientVO ) form ).getClientId();
         }

         // 获得ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
         // 刷新对象，初始化对象列表及国际化
         clientVO.reset( mapping, request );

         // 如果存在City Id，则填充Province Id
         if ( KANUtil.filterEmpty( clientVO.getCityId(), "0" ) != null )
         {
            // 获取CityVO
            final CityVO cityVO = KANConstants.LOCATION_DTO.getCityVO( clientVO.getCityId(), request.getLocale().getLanguage() );

            clientVO.setProvinceId( cityVO != null ? cityVO.getProvinceId() : "0" );
            clientVO.setCityIdTemp( clientVO.getCityId() );
         }

         //  获取当前的商务合同。如果有商务合同的状态为归档。则财务编码不能修改
         ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
         final List< Object > clientContractObjects = clientContractService.getClientContractVOsByClientId( clientId );
         boolean isFiling = false;
         for ( Object obj : clientContractObjects )
         {
            if ( "6".equals( ( ( ClientContractVO ) obj ).getStatus() ) )
            {
               isFiling = true;
               break;
            }
         }
         request.setAttribute( "isFiling", isFiling );
         clientVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientForm", clientVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageClient" );
   }

   /**  
    * To_ObjectModify_Internal
    *	Inhouse客户信息维护
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward to_objectModify_internal( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加页面Token
         this.saveToken( request );
         // 初始化Service接口
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // 获得当前主键
         String corpId = getCorpId( request, response );

         // 获得ClientVO
         final ClientVO clientVO = clientService.getClientVOByCorpId( corpId );
         // 刷新对象，初始化对象列表及国际化
         clientVO.reset( null, request );

         // 如果存在City Id，则填充Province Id
         if ( KANUtil.filterEmpty( clientVO.getCityId(), "0" ) != null )
         {
            // 获取CityVO
            final CityVO cityVO = KANConstants.LOCATION_DTO.getCityVO( clientVO.getCityId(), request.getLocale().getLanguage() );

            clientVO.setProvinceId( cityVO != null ? cityVO.getProvinceId() : "0" );
            clientVO.setCityIdTemp( clientVO.getCityId() );
         }

         clientVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientForm", clientVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      return mapping.findForward( "manageClientInternal" );
   }

   /**
    * Add Client
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
            // 初始化Service接口
            final ClientService clientService = ( ClientService ) getService( "clientService" );
            // 获得ActionForm
            final ClientVO clientVO = ( ClientVO ) form;
            // 获取登录用户
            clientVO.setCreateBy( getUserId( request, response ) );
            clientVO.setModifyBy( getUserId( request, response ) );

            // 保存自定义Column
            clientVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // 手机模块权限
            final String[] mobileModuleRightIds = request.getParameterValues( "checkBox_mobileModuleRightIds" );

            if ( mobileModuleRightIds != null && mobileModuleRightIds.length > 0 )
            {
               clientVO.setMobileModuleRightIds( KANUtil.toJasonArray( mobileModuleRightIds, "," ) );
            }

            // 新建对象
            clientVO.setModuleIdArray( request.getParameterValues( "moduleIdArray" ) );
            clientVO.setEmployeeModuleIdArray( request.getParameterValues( "employeeModuleIdArray" ) );
            clientService.insertClient( clientVO );
            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );

            // 判断是否需要转向
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // 生成转向地址
               forwardURL = forwardURL + ( ( ClientVO ) form ).getEncodedId();
               request.getRequestDispatcher( forwardURL ).forward( request, response );

               return null;
            }
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
    * Copy Object
    *	复制客户信息
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward copy_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( KANUtil.filterEmpty( request.getParameter( "clientId" ) ) != null )
         {
            // 初始化Service接口
            final ClientService clientService = ( ClientService ) getService( "clientService" );
            // 获取数据库中原有对象
            final String clientId = KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) );
            final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

            ( ( ClientVO ) form ).setClientId( "" );
            ( ( ClientVO ) form ).update( clientVO );
            // “状态”为新建
            ( ( ClientVO ) form ).setStatus( "1" );
            // 获取登录用户
            ( ( ClientVO ) form ).setCreateBy( getUserId( request, response ) );
            ( ( ClientVO ) form ).setModifyBy( getUserId( request, response ) );
            // 保存自定义Column
            ( ( ClientVO ) form ).setRemark1( saveDefineColumns( request, accessAction ) );

            // 手机模块权限
            final String[] mobileModuleRightIds = request.getParameterValues( "checkBox_mobileModuleRightIds" );
            if ( mobileModuleRightIds != null && mobileModuleRightIds.length > 0 )
            {
               ( ( ClientVO ) form ).setMobileModuleRightIds( KANUtil.toJasonArray( mobileModuleRightIds, "," ) );
            }

            // 新建对象
            clientService.insertClient( ( ( ClientVO ) form ) );

            // 返回添加成功标记
            success( request, null, "复制信息成功", MESSAGE_HEADER );

            // 判断是否需要转向
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // 生成转向地址
               forwardURL = forwardURL + ( ( ClientVO ) form ).getEncodedId();
               request.getRequestDispatcher( forwardURL ).forward( request, response );
            }

         }
         else
         {
            // TODO 添加跳转
            error( request, null, "复制信息不成功！", MESSAGE_HEADER );
            return null;
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
    * Modify Client
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
            // 初始化Service接口
            final ClientService clientService = ( ClientService ) getService( "clientService" );

            // 获取SubAction
            final String subAction = request.getParameter( "subAction" );

            // 获得当前主键
            String clientId = KANUtil.decodeString( request.getParameter( "id" ) );

            // 获得ClientVO
            final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

            clientVO.update( ( ClientVO ) form );
            // 保存自定义Column
            clientVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // 照片
            final String[] imageFileArray = request.getParameterValues( "imageFileArray" );
            String imageFileString = "";

            if ( imageFileArray != null && imageFileArray.length > 0 )
            {
               for ( String s : imageFileArray )
               {
                  imageFileString += s;
                  imageFileString += "##";
               }
               clientVO.setImageFile( imageFileString.length() > 0 ? imageFileString.substring( 0, imageFileString.length() - 2 ) : null );
            }
            // 手机模块权限
            final String[] mobileModuleRightIds = request.getParameterValues( "checkBox_mobileModuleRightIds" );
            if ( mobileModuleRightIds != null && mobileModuleRightIds.length > 0 )
            {
               clientVO.setMobileModuleRightIds( KANUtil.toJasonArray( mobileModuleRightIds, "," ) );
            }

            clientVO.setModifyBy( getUserId( request, response ) );
            clientVO.reset( mapping, request );

            // 保存自定义Column
            clientVO.setRemark1( saveDefineColumns( request, accessAction ) );
            clientVO.setModuleIdArray( request.getParameterValues( "moduleIdArray" ) );
            clientVO.setEmployeeModuleIdArray( request.getParameterValues( "employeeModuleIdArray" ) );

            // 如果是客户提交
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               if ( clientService.submitClient( clientVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
               }
            }
            else
            {
               clientService.updateClient( clientVO );
               success( request, MESSAGE_TYPE_UPDATE );
            }
            // 初始化常量持久对象
            constantsInit( "initOptions", getAccountId( request, response ) );
            insertlog( request, clientVO, Operate.MODIFY, clientVO.getClientId(), "企业信息" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 如果是客户登录则直接跳转到客户信息管理页面
      if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, response ) ) )
      {
         return to_objectModify_internal( mapping, form, request, response );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   /**  
    * Modify Object Ajax
    *	ajax修改客户所属集团
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward modify_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service接口
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // 获得当前主键
         String clientId = request.getParameter( "clientId" );
         // 获得集团ID
         final String groupId = request.getParameter( "groupId" );
         // 获得ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

         if ( clientVO != null )
         {
            // 更新集团ID
            clientVO.setGroupId( groupId );
            clientVO.setModifyBy( getUserId( request, response ) );
            clientVO.reset( mapping, request );
            // 更新数据库
            clientService.updateClient( clientVO );
            jsonObject.put( "clientVO", clientVO );
            jsonObject.put( "success", "true" );
         }
         else
         {
            // 客户ID无效
            jsonObject.put( "success", "invalidClientId" );
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
    * Submit Client
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-13
   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // 获得主键需解码
         final String clientId = KANUtil.decodeString( request.getParameter( "id" ) );

         // 获得ClientVO对象
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

         // 设置数据
         clientVO.setModifyBy( getUserId( request, response ) );
         clientVO.setModifyDate( new Date() );
         clientVO.reset( null, request );
         clientVO.setModuleIdArray( request.getParameterValues( "moduleIdArray" ) );
         clientVO.setEmployeeModuleIdArray( request.getParameterValues( "employeeModuleIdArray" ) );
         if ( clientService.submitClient( clientVO ) == -1 )
         {
            success( request, MESSAGE_TYPE_SUBMIT );
         }
         else
         {
            success( request, MESSAGE_TYPE_UPDATE );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**
    * Delete Client
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
    * Delete Client List
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-13
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // 获得Action Form
         final ClientVO clientVO = ( ClientVO ) form;

         // 存在选中的ID
         if ( clientVO.getSelectedIds() != null && !clientVO.getSelectedIds().trim().isEmpty() )
         {
            // 分割
            for ( String selectedId : clientVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // 初始化ClientVO
                  final ClientVO tempClientVO = clientService.getClientVOByClientId( KANUtil.decodeStringFromAjax( selectedId ) );

                  // 调用删除接口
                  tempClientVO.setModifyBy( getUserId( request, response ) );
                  tempClientVO.setModifyDate( new Date() );
                  clientService.deleteClient( tempClientVO );
               }
            }
         }

         // 清除Selected IDs和子Action
         clientVO.setSelectedIds( "" );
         clientVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Special Info HTML
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // 初始化设置Tab Number
         int clientContactCount = 0;
         int clientInvoiceCount = 0;
         int clientContractCount = 0;
         int clientOrderHeaderCount = 0;

         if ( request.getParameter( "clientId" ) != null && !request.getParameter( "clientId" ).trim().isEmpty() )
         {
            // 获得当前主键
            final String clientId = KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) );

            // 实例化ClientVO作为查找ClientDTO的条件
            ClientVO clientVO = new ClientVO();
            clientVO.setAccountId( getAccountId( request, response ) );
            clientVO.setClientId( clientId );

            // 根据主键查找对应的ClientDTO
            final ClientDTO clientDTO = clientService.getClientDTOByClientVO( clientVO );

            if ( clientDTO.getClientVO() != null )
            {
               // 刷新VO对象，初始化对象列表及国际化
               clientDTO.getClientVO().reset( null, request );
               clientDTO.getClientVO().setSubAction( VIEW_OBJECT );
            }

            // 添加Client Contact Count用于Tab Number显示
            clientContactCount = clientDTO.getClientContactVOs().size();

            // 添加Client Invoice Count用于Tab Number显示
            clientInvoiceCount = clientDTO.getClientInvoiceVOs().size();

            // 添加Client Contract Count用于Tab Number显示
            clientContractCount = clientDTO.getClientContractVOs().size();

            // 初始化 Service
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

            // 新建订单用于查询
            final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
            clientOrderHeaderVO.setClientId( clientId );
            clientOrderHeaderVO.setAccountId( getAccountId( request, response ) );

            // 获得客户对应的订单列表
            final List< Object > clientOrderHeaderVOs = clientOrderHeaderService.getClientOrderHeaderVOsByCondition( clientOrderHeaderVO );
            clientOrderHeaderCount = clientOrderHeaderVOs.size();

            request.setAttribute( "clientForm", clientDTO.getClientVO() );
            request.setAttribute( "clientOrderHeaderVOs", clientOrderHeaderVOs );
            request.setAttribute( "clientDTO", clientDTO );
         }

         request.setAttribute( "clientContactCount", clientContactCount );
         request.setAttribute( "clientOrderHeaderCount", clientOrderHeaderCount );
         request.setAttribute( "clientInvoiceCount", clientInvoiceCount );
         request.setAttribute( "clientContractCount", clientContractCount );

         // Ajax调用
         return mapping.findForward( "managerSpecialInfo" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

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
   // Added by Kevin Jin at 2013-11-25
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

         // 获取ClientId
         final String clientId = request.getParameter( "clientId" );

         // 获得雇员ID
         final String employeeId = request.getParameter( "employeeId" );

         // 初始化Service接口
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // 获得ClientVO列表
         final List< Object > clientVOs = clientService.getClientVOsByEmployeeId( employeeId );

         // 初始化MappingVO列表
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, ( ( ClientVO ) form ).getEmptyMappingVO() );

         if ( clientVOs != null && clientVOs.size() > 0 )
         {
            for ( Object clientVOObject : clientVOs )
            {
               final ClientVO clientVO = ( ClientVO ) clientVOObject;
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( clientVO.getClientId() );

               // 如果是中文环境
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingValue( clientVO.getNameZH() );
               }
               // 如果是英文环境
               else
               {
                  mappingVO.setMappingValue( clientVO.getNameEN() );
               }

               mappingVOs.add( mappingVO );
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "clientId", clientId ) );
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
    * Delete Object Ajax
    *	集团Ajax删除客户
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@throws KANException
    */
   public void delete_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // 获取主键
         //         final String clientId = KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) );
         final String clientId = request.getParameter( "clientId" );

         // 获取ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
         clientVO.setModifyBy( getUserId( request, response ) );
         clientVO.setModifyDate( new Date() );

         // 调用删除接口
         final long rows = clientService.delClientAndGroupRelationByClientId( clientVO );

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

   public ActionForward list_options_ajax_byAccountId( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
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
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         final PagedListHolder pagedListHolder = new PagedListHolder();

         final ClientVO clientVO = new ClientVO();

         clientVO.setAccountId( getAccountId( request, response ) );

         pagedListHolder.setObject( clientVO );

         clientService.getClientVOsByCondition( pagedListHolder, false );

         // 初始化下拉选项
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         MappingVO mappingVO = null;

         ClientVO tempClientVO = null;
         if ( pagedListHolder != null && pagedListHolder.getSource().size() > 0 )
         {
            for ( Object object : pagedListHolder.getSource() )
            {
               mappingVO = new MappingVO();
               tempClientVO = ( ClientVO ) object;
               // 如果是中文环境
               if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
               {
                  mappingVO.setMappingId( tempClientVO.getClientId() );
                  mappingVO.setMappingValue( tempClientVO.getClientName() );
                  mappingVOs.add( mappingVO );
               }
               else if ( request.getLocale().getLanguage().equalsIgnoreCase( "EN" ) )
               {
                  mappingVO.setMappingId( tempClientVO.getClientId() );
                  mappingVO.setMappingValue( tempClientVO.getClientName() );
                  mappingVOs.add( mappingVO );
               }
            }
         }

         // Send to client
         final String clientId = request.getParameter( "clientId" );
         out.println( KANUtil.getOptionHTML( mappingVOs, "clientId", KANUtil.filterEmpty( clientId ) == null ? "0" : clientId ) );
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

   public ActionForward load_image_file( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化 Service
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // Send to client
         final String clientId = request.getParameter( "clientId" );
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
         clientVO.setSubAction( request.getParameter( "subAction" ) );
         clientVO.reset( null, request );
         request.setAttribute( "clientVO", clientVO );
         return mapping.findForward( "imageFileExtend" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   // for 结算单header
   public ActionForward getClientVOAndOrderVOForOrderBillDetailHeader( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request,
         final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化 Service
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // 初始化 Service
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // 获取ClientId
         final String clientId = KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) );
         final String orderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderId" ) );
         final String monthly = KANUtil.decodeStringFromAjax( request.getParameter( "monthly" ) );

         // 初始化ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderId );
         clientVO.reset( null, request );
         clientOrderHeaderVO.reset( null, request );
         clientOrderHeaderVO.setMonthly( monthly );
         request.setAttribute( "clientVO", clientVO );
         request.setAttribute( "clientOrderHeaderVO", clientOrderHeaderVO );
         return mapping.findForward( "orderBillDetailHeader" );

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }
}

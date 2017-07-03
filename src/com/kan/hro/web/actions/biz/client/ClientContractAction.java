package com.kan.hro.web.actions.biz.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.MappingVO;
import com.kan.base.domain.management.BusinessContractTemplateVO;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.system.ConstantVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.BusinessContractTemplateService;
import com.kan.base.util.HTMLParseUtil;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.MatchUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientContractPropertyVO;
import com.kan.hro.domain.biz.client.ClientContractVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.service.inf.biz.client.ClientContractPropertyService;
import com.kan.hro.service.inf.biz.client.ClientContractService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientService;

/**  
 * 项目名称：HRO_V1  
 * 类名称：ClientContractAction  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-8-8  
 */
public class ClientContractAction extends BaseAction
{
   // 当前Action对应的Access Action
   public final static String accessAction = "HRO_BIZ_CLIENT_CONTRACT";

   /**  
    * Get Object Ajax
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward get_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 初始化Service接口
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );

         // 获取ContractId
         final String contractId = request.getParameter( "contractId" );

         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();

         // 获取ClientContractVO
         final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );
         if ( clientContractVO != null && clientContractVO.getAccountId() != null && clientContractVO.getAccountId().equals( getAccountId( request, response ) ) )
         {
            clientContractVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( clientContractVO );
            String finalEndDate = getFinalEndDate( clientContractVO );
            jsonObject.put( "finalEndDate", finalEndDate );
            jsonObject.put( "success", "true" );
         }
         else
         {
            jsonObject.put( "success", "false" );
         }

         // Send to client
         out.println( jsonObject != null ? jsonObject.toString() : "" );
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
    * List Object Options Ajax
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
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

         // 初始化Service接口
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );

         // 标记请求来自于（Order/Contract）
         final String flag = request.getParameter( "flag" );

         // 获取ClientId
         final String clientId = request.getParameter( "clientId" );
         // 获取ClientContractVO列表
         final List< Object > clientContractVOs = clientContractService.getClientContractVOsByClientId( clientId );

         // 获取ContractId
         final String contractId = request.getParameter( "contractId" );

         if ( flag != null && !flag.trim().isEmpty() && clientContractVOs != null && clientContractVOs.size() > 0 )
         {
            // 遍历
            for ( Object object : clientContractVOs )
            {
               final ClientContractVO clientContractVO = ( ClientContractVO ) object;
               boolean target = false;

               if ( flag.trim().equalsIgnoreCase( "order" ) )
               {
                  if ( clientContractVO.getStatus() != null
                        && ( clientContractVO.getStatus().trim().equals( "3" ) || clientContractVO.getStatus().trim().equals( "5" ) || clientContractVO.getStatus().trim().equals( "6" ) ) )
                  {
                     target = true;
                  }
               }
               else if ( flag.trim().equalsIgnoreCase( "contract" ) )
               {
                  if ( clientContractVO.getStatus() != null && clientContractVO.getStatus().trim().equals( "6" ) )
                  {
                     target = true;
                  }
               }

               if ( target )
               {
                  // 初始化MappingVO
                  final MappingVO mappingVO = new MappingVO();
                  // 初始化MappingValue
                  String mappingValue = clientContractVO.getContractId();
                  if ( KANUtil.filterEmpty( clientContractVO.getContractNo() ) != null )
                  {
                     mappingValue = mappingValue + "/" + clientContractVO.getContractNo();
                  }

                  mappingVO.setMappingId( clientContractVO.getContractId() );
                  // 中文
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     mappingValue = mappingValue + " - " + clientContractVO.getNameZH();
                  }
                  // 非中文
                  else
                  {
                     if ( KANUtil.filterEmpty( clientContractVO.getNameEN() ) != null )
                     {
                        mappingValue = mappingValue + " - " + clientContractVO.getNameEN();
                     }
                  }
                  mappingVO.setMappingValue( mappingValue );
                  mappingVOs.add( mappingVO );
               }
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "contractId", contractId ) );
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
    * List Object Json
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-06
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
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );

         // 获取当前登录用户的accountId
         final String accountId = getAccountId( request, response );

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( clientContractService.getClientContractBaseViews( accountId ) );

         // Send to clientContractGroup
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
    * List Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2013-11-06
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = getPage( request );
         // 初始化Service接口
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
         // 获得Action Form
         final ClientContractVO clientContractVO = ( ClientContractVO ) form;

         //处理数据权限
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, clientContractVO );

         // 获得SubAction
         final String subAction = getSubAction( form );

         // 添加自定义搜索内容
         clientContractVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         // 如果没有指定排序则默认按 ContractId排序
         if ( clientContractVO.getSortColumn() == null || clientContractVO.getSortColumn().isEmpty() )
         {
            clientContractVO.setSortColumn( "contractId" );
            clientContractVO.setSortOrder( "desc" );
         }

         // 处理SubAction
         dealSubAction( clientContractVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder clientContractHolder = new PagedListHolder();

         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 传入当前页
            clientContractHolder.setPage( page );
            // 传入当前值对象
            clientContractHolder.setObject( clientContractVO );
            // 设置页面记录条数
            clientContractHolder.setPageSize( getPageSize( request, accessAction ) );

            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            clientContractService.getClientContractVOsByCondition( clientContractHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // 刷新Holder，国际化传值
            refreshHolder( clientContractHolder, request );
         }

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", clientContractHolder );

         // 处理Return
         return dealReturn( accessAction, "listClientContract", mapping, form, request, response );
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
   @Override
   // Reviewed by Kevin Jin at 2013-11-06
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 添加需设定一个记号，防止重复提交
         this.saveToken( request );

         // 初始化Entity Mapping列表
         final List< MappingVO > entities = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getEntities( request.getLocale().getLanguage() );

         // 初始化Business Type Mapping列表
         final List< MappingVO > businessTypes = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getBusinessTypes( request.getLocale().getLanguage() );

         // 初始化PositionVO
         final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

         // 获得ActionForm
         final ClientContractVO clientContractVO = ( ClientContractVO ) form;
         // 默认设置
         clientContractVO.setSubAction( CREATE_OBJECT );

         if ( entities != null && entities.size() == 1 )
         {
            clientContractVO.setEntityId( ( ( MappingVO ) entities.get( 0 ) ).getMappingId() );

            // 初始化
            final EntityVO entityVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getEntityVOByEntityId( ( ( MappingVO ) entities.get( 0 ) ).getMappingId() );
            clientContractVO.setBusinessTypeId( entityVO.getBizType() );
         }

         if ( businessTypes != null && businessTypes.size() == 1 )
         {
            clientContractVO.setBusinessTypeId( ( ( MappingVO ) businessTypes.get( 0 ) ).getMappingId() );
         }

         // 商务合同开始日期默认当天
         clientContractVO.setStartDate( KANUtil.formatDate( KANUtil.createDate( null ), "yyyy-MM-dd" ) );
         // 商务合同结束日期默认三年
         clientContractVO.setEndDate( KANUtil.formatDate( KANUtil.getDate( KANUtil.createDate( null ), 3, 0, -1 ), "yyyy-MM-dd" ) );
         clientContractVO.setStatus( ClientContractVO.TRUE );

         if ( positionVO != null )
         {
            clientContractVO.setBranch( positionVO.getBranchId() );
            clientContractVO.setOwner( positionVO.getPositionId() );
         }

         // 如果Client Id不为空
         if ( KANUtil.filterEmpty( request.getParameter( "clientId" ) ) != null )
         {
            // 获取Client Id
            final String clientId = KANUtil.decodeString( request.getParameter( "clientId" ) );
            // 设置Client Id
            ( ( ClientContractVO ) form ).setClientId( clientId );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到新建界面   
      return mapping.findForward( "manageClientContract" );
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
   @Override
   // Reviewed by Kevin Jin at 2013-11-06
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

            // 初始化 Service接口
            final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
            final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );

            // 获得当前FORM
            final ClientContractVO clientContractVO = ( ClientContractVO ) form;
            clientContractVO.setCreateBy( getUserId( request, response ) );
            clientContractVO.setModifyBy( getUserId( request, response ) );

            if ( clientContractVO.getTemplateId() != null && !"0".equals( clientContractVO.getTemplateId() ) )
            {
               BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( clientContractVO.getTemplateId() );
               clientContractVO.setContent( businessContractTemplateVO == null ? "" : businessContractTemplateVO.getContent() );
            }

            // 调用插入方法
            clientContractService.insertClientContract( clientContractVO );

            // 判断是否需要转向
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // 生成转向地址
               forwardURL = forwardURL + ( ( ClientContractVO ) form ).getEncodedId();
               request.getRequestDispatcher( forwardURL ).forward( request, response );

               return null;
            }

            // 返回添加成功标记
            success( request, MESSAGE_TYPE_ADD );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   @Override
   // Reviewed by Kevin Jin at 2013-11-06
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );
         // 初始化 Service接口
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
         // 主键获取需解码
         String contractId = KANUtil.decodeString( request.getParameter( "id" ) );

         if ( KANUtil.filterEmpty( contractId ) == null )
         {
            contractId = ( ( ClientContractVO ) form ).getContractId();
         }

         // 获得ClientContractVO对象                                                                                          
         final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );
         clientContractVO.reset( null, request );

         // 区分Add和Update
         clientContractVO.setSubAction( VIEW_OBJECT );

         // 将ClientContractVO传入request对象
         request.setAttribute( "clientContractForm", clientContractVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "manageClientContract" );
   }

   /**
    * Modify Object
    * 第一个界面“保存”按钮触发
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   // Reviewed by Kevin Jin at 2013-11-06
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
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

            // 初始化 Service接口
            final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
            final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
            // 主键获取需解码
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
            // 获取ClientContractVO对象
            final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );

            // 装载界面传值
            clientContractVO.update( ( ClientContractVO ) form );
            // 获取登录用户
            clientContractVO.setModifyBy( getUserId( request, response ) );

            if ( clientContractVO.getTemplateId() != null && !"0".equals( clientContractVO.getTemplateId() ) )
            {
               BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( clientContractVO.getTemplateId() );
               clientContractVO.setContent( businessContractTemplateVO == null ? "" : businessContractTemplateVO.getContent() );
            }

            // 调用修改方法
            clientContractService.updateClientContract( clientContractVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Modify Object Step 2
    * 第二个界面“保存”、“提交”按钮触发
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward modify_object_step2( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化输入值错误数
            request.setAttribute( "errorCount", 0 );

            // 页面跳转控制
            if ( request.getAttribute( "errorCount" ) != null && ( ( Integer ) request.getAttribute( "errorCount" ) != 0 ) )
            {
               return to_prePage( mapping, form, request, response );
            }

            // 初始化 Service接口
            final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
            final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );

            // 获取SubAction
            final String subAction = request.getParameter( "subAction" );

            // 主键获取需解码
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );

            // 获取ClientContractVO对象
            final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );

            // 获取合同模板信息
            final BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( clientContractVO.getTemplateId() );
            final String content = businessContractTemplateVO.getContent();

            // 装载界面传值
            clientContractVO.setContent( content );
            // 更新登录用户及修改时间
            clientContractVO.setModifyBy( getUserId( request, response ) );
            clientContractVO.setModifyDate( new Date() );

            final List< ConstantVO > constantVOs = MatchUtil.fetchProperties( content, KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getConstantVOsByScopeType( "2" ), request, null, MatchUtil.FLAG_GET_PROPERTIES );

            // 初始化Rows
            int rows = 0;

            // 调用修改方法
            rows = clientContractService.updateClientContract( clientContractVO, constantVOs );

            // 如果是合同提交 - 默认状态为“审批”
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               clientContractVO.reset( null, request );
               rows = clientContractService.submitClientContract( clientContractVO );
            }

            // 返回提示消息
            if ( rows == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
            }
         }

         // 清空Form
         ( ( ClientContractVO ) form ).reset();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   /**
    * Submit Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by siuvan.xia 2014-06-23
   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );

         // 获得当前主键
         final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );

         // 获得主键对应对象
         final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );

         List< Object > objects = getClientContractPDFVos( clientContractVO, request );
         final List< ConstantVO > constantVOs = MatchUtil.fetchProperties( clientContractVO.getContent(), KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getConstantVOsByScopeType( "2" ), request, objects, MatchUtil.FLAG_GET_CONTENT_WITH_VALUE );

         // 设置数据
         clientContractVO.setModifyBy( getUserId( request, response ) );
         clientContractVO.setModifyDate( new Date() );
         clientContractVO.reset( null, request );
         clientContractVO.setConstantVOs( constantVOs );
         clientContractService.submitClientContract( clientContractVO );
         success( request, MESSAGE_TYPE_SUBMIT );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Chop Object
    * 第二个界面“盖章”按钮触发
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-11
   public ActionForward chop_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
            // 主键获取需解码
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
            // 获取ClientContractVO对象
            final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );

            clientContractVO.setStatus( "5" );
            clientContractVO.setModifyBy( getUserId( request, response ) );
            clientContractVO.setModifyDate( new Date() );

            // 调用修改方法
            clientContractService.chopClientContract( clientContractVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Archive Object
    * 第二个界面“归档”按钮触发
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-11
   public ActionForward archive_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 判断防止重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化 Service接口
            final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
            // 主键获取需解码
            final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
            // 获取ClientContractVO对象
            final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );

            clientContractVO.setStatus( "6" );
            clientContractVO.setModifyBy( getUserId( request, response ) );
            clientContractVO.setModifyDate( new Date() );

            // 调用修改方法
            clientContractService.updateClientContract( clientContractVO );

            // 返回编辑成功标记
            success( request, MESSAGE_TYPE_UPDATE );
         }
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
   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
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
   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
         // 获得Action Form
         final ClientContractVO clientContractVO = ( ClientContractVO ) form;

         // 存在选中的ID
         if ( clientContractVO.getSelectedIds() != null && !clientContractVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : clientContractVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // 主键转码
                  final String contractId = KANUtil.decodeStringFromAjax( selectedId );
                  // 根据主键获得对应VO
                  final ClientContractVO clientContractVOForDel = clientContractService.getClientContractVOByContractId( contractId );
                  clientContractVOForDel.setModifyBy( getUserId( request, response ) );
                  clientContractVOForDel.setModifyDate( new Date() );
                  clientContractService.deleteClientContract( clientContractVOForDel );
               }
            }
         }

         // 清除Selected IDs和子Action
         clientContractVO.setSelectedIds( "" );
         clientContractVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Delete Object Ajax
    * Tab删除商务合同
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
         // 初始化Row
         long rows = 0;

         // 初始化Service接口
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );

         // 获取主键
         final String contractId = KANUtil.decodeStringFromAjax( request.getParameter( "contractId" ) );

         // 根据主键获得对应VO
         final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );
         clientContractVO.setModifyBy( getUserId( request, response ) );
         clientContractVO.setModifyDate( new Date() );

         // 特殊状态不能删除
         if ( clientContractVO.getStatus() != null
               && ( clientContractVO.getStatus().trim().equals( "3" ) || clientContractVO.getStatus().trim().equals( "5" ) || clientContractVO.getStatus().trim().equals( "6" ) ) )
         {
            rows = 0;
         }
         else
         {
            // 调用删除接口
            rows = clientContractService.deleteClientContract( clientContractVO );
         }

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
   * List Special Info HTML
   * 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
   * @throws KANException
   */
   // Reviewed by Kevin Jin at 2013-11-08
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // 获得客户合同编号
         final String contractId = KANUtil.decodeString( request.getParameter( "clientContractId" ) );

         // 初始化Tab Number
         int attachmentCount = 0;
         int clientOrderHeaderCount = 0;

         if ( contractId != null && !contractId.trim().isEmpty() )
         {
            // 获得ClientContractVO
            final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );
            // 刷新VO对象，初始化对象列表及国际化
            clientContractVO.reset( null, request );
            clientContractVO.setSubAction( VIEW_OBJECT );

            request.setAttribute( "clientContractForm", clientContractVO );

            // 获取Tab标签Number
            if ( clientContractVO.getAttachmentArray() != null )
            {
               attachmentCount = clientContractVO.getAttachmentArray().length;
            }

            // 初始化ClientOrderHeaderVO
            final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
            clientOrderHeaderVO.setContractId( contractId );
            clientOrderHeaderVO.setClientId( clientContractVO.getClientId() );
            clientOrderHeaderVO.setAccountId( getAccountId( request, response ) );
            // 获得ClientOrderHeaderVO列表
            final List< Object > clientOrderHeaderVOs = clientOrderHeaderService.getClientOrderHeaderVOsByCondition( clientOrderHeaderVO );

            // 获取Tab标签Number
            if ( clientOrderHeaderVOs != null )
            {
               clientOrderHeaderCount = clientOrderHeaderVOs.size();
            }

            request.setAttribute( "clientOrderHeaderVOs", clientOrderHeaderVOs );
         }

         request.setAttribute( "clientOrderHeaderCount", clientOrderHeaderCount );
         request.setAttribute( "attachmentCount", attachmentCount );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "manageClientContractSpecialInfo" );
   }

   /**  
    * Generate Contract
    *	
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward generate_contract( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设置记号，防止重复提交
         this.saveToken( request );

         // 初始化 Service接口
         final BusinessContractTemplateService businessContractTemplateService = ( BusinessContractTemplateService ) getService( "businessContractTemplateService" );
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );

         // 如果是修改,获取商务合同主键Id
         final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );

         // 获得界面传值
         ClientContractVO clientContractVO = ( ClientContractVO ) form;
         BusinessContractTemplateVO businessContractTemplateVO = businessContractTemplateService.getBusinessContractTemplateVOByBusinessContractTemplateId( clientContractVO.getTemplateId() );

         if ( contractId == null || contractId.trim().isEmpty() )
         {
            // 如果是新增
            clientContractVO.setStatus( "1" );
            clientContractVO.setContent( businessContractTemplateVO == null ? "" : businessContractTemplateVO.getContent() );
            clientContractService.insertClientContract( clientContractVO );
         }
         else
         {
            // 如果是修改，获得主键对应对象
            final ClientContractVO clientContractVOFromDB = clientContractService.getClientContractVOByContractId( contractId );

            // 装载界面传值
            clientContractVOFromDB.update( clientContractVO );
            // 获取登录用户
            clientContractVOFromDB.setModifyBy( getUserId( request, response ) );
            clientContractVOFromDB.setContent( businessContractTemplateVO == null ? "" : businessContractTemplateVO.getContent() );
            // 调用修改方法
            clientContractService.updateClientContract( clientContractVOFromDB );
         }

         if ( clientContractVO.getTemplateId() != null && !clientContractVO.getTemplateId().trim().equals( "0" ) )
         {
            // 获取合同模板信息
            final String content = businessContractTemplateVO == null ? "" : businessContractTemplateVO.getContent();

            // 初始化Object List
            //            final List< Object > objects = new ArrayList< Object >();
            //            ClientVO clientVo = clientService.getClientVOByClientIdForPdf( clientContractVO.getClientId() );
            //            clientVo.setContactAddress( clientVo.getAddress() );
            //            if ( clientVo.getRemark1() != null && clientVo.getRemark1().contains( "registeredAddress" ) )
            //            {
            //               String[] remarks = clientVo.getRemark1().replaceAll( "\"", "" ).replace( "{", "" ).replace( "}", "" ).split( "," );
            //               for ( String remark : remarks )
            //               {
            //                  if ( remark.contains( "registeredAddress" ) )
            //                  {
            //                     clientVo.setRegisteredAddress( remark.replace( "registeredAddress:", "" ) );
            //                     break;
            //                  }
            //               }
            //            }
            //
            //            if ( StringUtils.isEmpty( clientVo.getContactWay() ) )
            //            {
            //               clientVo.setContactWay( clientVo.getPhone() );
            //            }
            //            objects.add( clientVo );
            //            objects.add( KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getEntityVOByEntityId( clientContractVO.getEntityId() ) );
            //            objects.add( clientContractVO );

            List< Object > objects = getClientContractPDFVos( clientContractVO, request );

            // 设置ClientContractVO的Content
            clientContractVO.setContent( MatchUtil.generateContent( content, KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getConstantVOsByScopeType( "2" ), objects, request ) );
         }

         request.setAttribute( "clientContractForm", clientContractVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "generateContract" );
   }

   /**  
    * Export Contract PDF
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-09
   public ActionForward export_contract_pdf( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化 Service接口
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
         final ClientContractPropertyService clientContractPropertyService = ( ClientContractPropertyService ) getService( "clientContractPropertyService" );
         // 获取商务合同主键
         final String contractId = KANUtil.decodeString( request.getParameter( "id" ) );
         // 获取商务合同对象
         final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );

         // 初始化文件名
         String fileName = ".pdf";
         if ( request.getLocale().getLanguage().trim().equalsIgnoreCase( "ZH" ) )
         {
            fileName = clientContractVO.getNameZH() + fileName;
         }
         else
         {
            fileName = clientContractVO.getNameEN() + fileName;
         }

         // 下载商务合同PDF版本
         String htmlContent = MatchUtil.generateContent( clientContractVO.getContent(), KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getConstantVOsByScopeType( "2" ), clientContractPropertyService.getClientContractPropertyVOsByContractId( clientContractVO.getContractId() ), request, MatchUtil.FLAG_GET_CONTENT,null );
         //new DownloadFileAction().download( response, HTMLParseUtil.htmlToPDF( htmlContent, clientContractVO.getContractId(), KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_LOGO_FILE ), fileName );
         new DownloadFileAction().download( response, HTMLParseUtil.htmlParsePDF( htmlContent, clientContractVO.getContractId(), KANConstants.getKANAccountConstants( getAccountId( request, response ) ).OPTIONS_LOGO_FILE ), fileName );
      }
      catch ( final Exception e )
      {
         if ( StringUtils.contains( e.getMessage(), "行号" ) )
         {
            error( request, null, e.getMessage() );
         }
         else
         {
            throw new KANException( e );
         }
      }

      // Ajax调用
      return mapping.findForward( "" );
   }

   /**  
    * To PrePage
    *	页面输入错误返回提交页面
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
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
      return mapping.findForward( "manageClientContract" );
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
      final ClientContractVO clientContractVO = ( ClientContractVO ) form;
      // 获得ClientId
      final String clientId = KANUtil.filterEmpty( clientContractVO.getClientId() );

      final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

      if ( clientVO == null )
      {
         request.setAttribute( "clientIdError", "客户ID输入无效！" );
         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );
      }

   }

   private String getFinalEndDate( final ClientContractVO clientContractVO ) throws Exception
   {
      String finalEndDate = clientContractVO.getEndDate();
      final String startDate = clientContractVO.getStartDate() + " 00:00:00";
      final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
      final SimpleDateFormat sdf2 = new SimpleDateFormat( "yyyy-MM-dd" );
      final Date sDate = sdf.parse( startDate );
      Calendar calendar = Calendar.getInstance();
      if ( KANUtil.filterEmpty( finalEndDate ) != null )
      {
         final Date eDate = sdf.parse( clientContractVO.getEndDate() + " 23:59:59" );
         calendar.setTime( sDate );
         calendar.add( Calendar.YEAR, 3 );
         //如果订单时间差在3年内，按照订单算
         if ( calendar.getTime().getTime() > eDate.getTime() )
         {
            finalEndDate = clientContractVO.getEndDate();
         }
         else
         {
            // 否则算3年
            calendar.setTime( KANUtil.getDateAfterMonth( sDate, 36 ) );
            calendar.add( Calendar.DAY_OF_MONTH, -1 );
            finalEndDate = sdf2.format( calendar.getTime() );
         }
      }
      else
      {
         // 开时间三年后
         calendar.setTime( KANUtil.getDateAfterMonth( sDate, 36 ) );
         calendar.add( Calendar.DAY_OF_MONTH, -1 );
         finalEndDate = sdf2.format( calendar.getTime() );
      }
      return finalEndDate;
   }

   public void setInputValueForPage( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      response.setContentType( "application/json;charset=UTF-8" );
      response.setCharacterEncoding( "UTF-8" );
      String clientContractId = request.getParameter( "contractId" );
      final ClientContractPropertyService clientContractPropertyService = ( ClientContractPropertyService ) getService( "clientContractPropertyService" );
      List< Object > list = clientContractPropertyService.getClientContractPropertyVOsByContractId( KANUtil.decodeString( clientContractId ) );
      List< Map< String, String > > listReturn = new ArrayList< Map< String, String > >();
      for ( Object object : list )
      {
         Map< String, String > map = new HashMap< String, String >();
         map.put( "id", ( ( ClientContractPropertyVO ) object ).getPropertyName() );
         map.put( "value", ( ( ClientContractPropertyVO ) object ).getPropertyValue() );
         listReturn.add( map );
      }
      JSONArray json = JSONArray.fromObject( listReturn );
      try
      {
         response.getWriter().write( json.toString() );
         response.getWriter().flush();
      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }
   }

   public void getArchiveClientContractCount( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // 初始化 Service
      final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
      int count = clientContractService.getArchiveClientContractCount( request.getParameter( "clientId" ) );
      try
      {
         final PrintWriter out = response.getWriter();
         out.print( count + "" );
         out.flush();
         out.close();
      }
      catch ( IOException e )
      {
         e.printStackTrace();
      }
   }

   public List< Object > getClientContractPDFVos( final ClientContractVO clientContractVO, final HttpServletRequest request ) throws KANException
   {
      // 初始化Object List
      final List< Object > objects = new ArrayList< Object >();
      final ClientService clientService = ( ClientService ) getService( "clientService" );
      ClientVO clientVo = clientService.getClientVOByClientIdForPdf( clientContractVO.getClientId() );
      clientVo.setContactAddress( clientVo.getAddress() );

      if ( clientVo.getRemark1() != null && clientVo.getRemark1().contains( "registeredAddress" ) )
      {
         String[] remarks = clientVo.getRemark1().replaceAll( "\"", "" ).replace( "{", "" ).replace( "}", "" ).split( "," );
         for ( String remark : remarks )
         {
            if ( remark.contains( "registeredAddress" ) )
            {
               clientVo.setRegisteredAddress( remark.replace( "registeredAddress:", "" ) );
               break;
            }
         }
      }

      if ( StringUtils.isEmpty( clientVo.getContactWay() ) )
      {
         clientVo.setContactWay( clientVo.getPhone() );
      }

      objects.add( clientVo );
      objects.add( KANConstants.getKANAccountConstants( getAccountId( request, null ) ).getEntityVOByEntityId( clientContractVO.getEntityId() ) );
      objects.add( clientContractVO );

      return objects;
   }

}

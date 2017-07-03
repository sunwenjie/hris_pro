package com.kan.hro.web.actions.biz.client;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.system.IncomeTaxBaseVO;
import com.kan.base.domain.system.IncomeTaxRangeHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.IncomeTaxBaseService;
import com.kan.base.service.inf.system.IncomeTaxRangeHeaderService;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.client.ClientContractVO;
import com.kan.hro.domain.biz.client.ClientOrderHeaderVO;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.employee.EmployeeContractVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.client.ClientContractService;
import com.kan.hro.service.inf.biz.client.ClientOrderCBService;
import com.kan.hro.service.inf.biz.client.ClientOrderDetailService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderRuleService;
import com.kan.hro.service.inf.biz.client.ClientOrderHeaderService;
import com.kan.hro.service.inf.biz.client.ClientOrderLeaveService;
import com.kan.hro.service.inf.biz.client.ClientOrderOTService;
import com.kan.hro.service.inf.biz.client.ClientOrderOtherService;
import com.kan.hro.service.inf.biz.client.ClientOrderSBService;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.employee.EmployeeContractService;
import com.kan.hro.service.inf.biz.vendor.VendorService;

/**  
 * 项目名称：HRO_V1  
 * 类名称：ClientOrderHeaderAction  
 * 类描述：  
 * 创建人：Jack  
 * 创建时间：2013-8-19  
 */
public class ClientOrderHeaderAction extends BaseAction
{

   public static String getAccessActionForWorkFlow()
   {
      return "HRO_BIZ_CLIENT_ORDER_HEADER";
   }

   // 当前Action对应的Access Action
   public static String getAccessAction( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      if ( ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) ) )
      {
         return "HRO_BIZ_CLIENT_ORDER_HEADER";
      }
      else
      {
         return "HRO_BIZ_CLIENT_ORDER_HEADER_IN_HOUSE";
      }
   }

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
   // Added by Kevin Jin at 2013-11-15
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
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // 获取OrderHeaderId
         final String orderHeaderId = request.getParameter( "orderHeaderId" );

         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();

         // 初始化ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

         if ( clientOrderHeaderVO != null && clientOrderHeaderVO.getAccountId() != null && clientOrderHeaderVO.getAccountId().equals( getAccountId( request, response ) ) )
         {
            clientOrderHeaderVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( clientOrderHeaderVO );
            String finalEndDate = getFinalEndDate( clientOrderHeaderVO );
            jsonObject.put( "finalEndDate", finalEndDate );
            jsonObject.put( "EndDate", clientOrderHeaderVO.getContractPeriod() != null && !"0".equals( clientOrderHeaderVO.getContractPeriod() ) ? KANUtil.formatDate( KANUtil.getDate( new Date(), Integer.valueOf( clientOrderHeaderVO.getContractPeriod() ), 0, -1 ), "yyyy-MM-dd" )
                  : null );

            //试用期结算日期
            jsonObject.put( "probationEndDate", clientOrderHeaderVO.getProbationMonth() != null && !"0".equals( clientOrderHeaderVO.getProbationMonth() ) ? KANUtil.formatDate( KANUtil.getDate( new Date(), 0, Integer.valueOf( clientOrderHeaderVO.getProbationMonth() ), 0 ), "yyyy-MM-dd" )
                  : null );
            jsonObject.put( "probationMonth", clientOrderHeaderVO.getProbationMonth() );

            jsonObject.put( "contractPeriod", clientOrderHeaderVO.getContractPeriod() );

            //            if ( StringUtils.isNotBlank( clientOrderHeaderVO.getProbationMonth() ) )
            //            {
            //               jsonObject.put( "probationEndDate",  KANUtil.formatDate( KANUtil.getDate( new Date(),0, Integer.valueOf( clientOrderHeaderVO.getProbationMonth() ), 0 ), "yyyy-MM-dd" ) );
            ////               employeeContractVO.setProbationEndDate( KANUtil.formatDate( KANUtil.getDate( new Date(),0, Integer.valueOf( clientOrderHeaderVO.getProbationMonth() ), 0 ), "yyyy-MM-dd" ) );
            //            }
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
    * List Object Ajax
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-11-15
   public ActionForward list_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 初始化Service接口
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         // 获得Action Form 
         final ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;

         //处理数据权限
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), getAccessAction( request, response ), clientOrderHeaderVO );

         // 解码
         decodedObject( clientOrderHeaderVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder orderHolder = new PagedListHolder();

         // 传入当前页
         orderHolder.setPage( page );
         // 传入当前值对象
         orderHolder.setObject( clientOrderHeaderVO );
         // 设置页面记录条数
         orderHolder.setPageSize( listPageSize_popup );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         clientOrderHeaderService.getClientOrderHeaderVOsByCondition( orderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( orderHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "orderHolder", orderHolder );

         // Ajax Table调用，直接传回JSP
         return mapping.findForward( "popupTable" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * List Client Order Header
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-04
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final String accessAction = getAccessAction( request, response );

      try
      {
         // 获得当前页
         final String page = getPage( request );
         // 初始化Service接口
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         // 获得Action Form
         final ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;

         //处理数据权限
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, clientOrderHeaderVO );

         // 获得SubAction
         final String subAction = getSubAction( form );
         // 添加自定义搜索内容
         clientOrderHeaderVO.setRemark1( generateDefineListSearches( request, accessAction ) );

         // 如果没有指定排序则默认按OrderHeaderId排序
         if ( clientOrderHeaderVO.getSortColumn() == null || clientOrderHeaderVO.getSortColumn().isEmpty() )
         {
            clientOrderHeaderVO.setSortColumn( "orderHeaderId" );
            clientOrderHeaderVO.setSortOrder( "desc" );
         }

         // 如果是客户端登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            clientOrderHeaderVO.setCorpId( getCorpId( request, null ) );
         }

         // 处理SubAction
         dealSubAction( clientOrderHeaderVO, mapping, form, request, response );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder clientOrderHeaderHolder = new PagedListHolder();

         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 传入当前页
            clientOrderHeaderHolder.setPage( page );
            // 传入当前值对象
            clientOrderHeaderHolder.setObject( clientOrderHeaderVO );
            // 设置页面记录条数
            clientOrderHeaderHolder.setPageSize( getPageSize( request, accessAction ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            clientOrderHeaderService.getClientOrderHeaderVOsByCondition( clientOrderHeaderHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false
                  : isPaged( request, accessAction ) );
            // 刷新Holder，国际化传值
            refreshHolder( clientOrderHeaderHolder, request );
         }

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", clientOrderHeaderHolder );

         // 处理Return
         if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
         {
            return dealReturn( accessAction, "listClientOrderHeader", mapping, form, request, response );
         }
         else
         {
            return dealReturn( accessAction, "listClientOrderHeaderInHouse", mapping, form, request, response );
         }

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
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 添加页面Token
      this.saveToken( request );

      // 初始化Entity Mapping列表
      final List< MappingVO > entities = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getEntities( request.getLocale().getLanguage() );

      // 初始化Business Type Mapping列表
      final List< MappingVO > businessTypes = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getBusinessTypes( request.getLocale().getLanguage() );

      // 初始化PositionVO
      final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

      // 获得ActionForm
      final ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;
      // 默认设置
      clientOrderHeaderVO.setSubAction( CREATE_OBJECT );

      if ( entities != null && entities.size() == 1 )
      {
         clientOrderHeaderVO.setEntityId( ( ( MappingVO ) entities.get( 0 ) ).getMappingId() );

         // 初始化
         final EntityVO entityVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getEntityVOByEntityId( ( ( MappingVO ) entities.get( 0 ) ).getMappingId() );
         clientOrderHeaderVO.setBusinessTypeId( entityVO.getBizType() );
      }

      if ( businessTypes != null && businessTypes.size() == 1 )
      {
         clientOrderHeaderVO.setBusinessTypeId( ( ( MappingVO ) businessTypes.get( 0 ) ).getMappingId() );
      }

      clientOrderHeaderVO.setStartDate( KANUtil.formatDate( KANUtil.createDate( null ), "yyyy-MM-dd" ) );
      //货币类型默认为人民币
      clientOrderHeaderVO.setCurrency( "CN" );
      //合同期限默认为3年
      clientOrderHeaderVO.setContractPeriod( "3" );
      clientOrderHeaderVO.setLocked( ClientOrderHeaderVO.FALSE );
      clientOrderHeaderVO.setStatus( ClientOrderHeaderVO.TRUE );
      if ( positionVO != null )
      {
         clientOrderHeaderVO.setBranch( positionVO.getBranchId() );
         clientOrderHeaderVO.setOwner( positionVO.getPositionId() );
      }

      // 如果是通过 Client界面添加
      if ( KANUtil.filterEmpty( request.getParameter( "clientId" ) ) != null )
      {
         // 初始化Service接口
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // 获取ClientId
         final String clientId = KANUtil.decodeString( request.getParameter( "clientId" ) );
         // 获取ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientId );
         // 国际化
         clientVO.reset( null, request );

         // 设置ClientOrderHeaderVO 相关参数
         clientOrderHeaderVO.setClientId( clientId );
         clientOrderHeaderVO.setClientNameZH( clientVO.getNameZH() );
         clientOrderHeaderVO.setClientNameEN( clientVO.getNameEN() );
         clientOrderHeaderVO.setEntityId( clientVO.getLegalEntity() );

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

         request.setAttribute( "orderBindContract", orderBindContract );
      }

      // 如果是通过 ClientContract界面添加
      if ( KANUtil.filterEmpty( request.getParameter( "contractId" ) ) != null )
      {
         // 初始化Service接口
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final ClientContractService clientContractService = ( ClientContractService ) getService( "clientContractService" );
         // 获取ContractId
         final String contractId = KANUtil.decodeString( request.getParameter( "contractId" ) );
         // 获得ClientContractVO
         final ClientContractVO clientContractVO = clientContractService.getClientContractVOByContractId( contractId );
         // 国际化
         clientContractVO.reset( null, request );

         // 获得ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientContractVO.getClientId() );

         // 设置ClientOrderHeaderVO 相关参数
         clientOrderHeaderVO.setClientId( clientContractVO.getClientId() );
         clientOrderHeaderVO.setClientNameZH( clientVO.getNameZH() );
         clientOrderHeaderVO.setClientNameEN( clientVO.getNameEN() );
         clientOrderHeaderVO.setContractId( contractId );
         clientOrderHeaderVO.setEntityId( clientVO.getLegalEntity() );
         clientOrderHeaderVO.setStartDate( clientContractVO.getStartDate() );
         // 订单默认结束日期设置（商务合同结束日期非空则取商务合同结束日期，反之为空）
         //KANUtil.formatDate( KANUtil.getDate( KANUtil.createDate( null ), 3, 0, -1 ), "yyyy-MM-dd" )
         clientOrderHeaderVO.setEndDate( KANUtil.filterEmpty( clientContractVO.getEndDate() ) != null ? clientContractVO.getEndDate() : null );

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

         request.setAttribute( "orderBindContract", orderBindContract );
      }

      // 如果是从客户端登录
      if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
      {
         clientOrderHeaderVO.setCorpId( getCorpId( request, null ) );
      }

      // 跳转到新建界面
      if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
      {
         return mapping.findForward( "manageClientOrderHeader" );
      }
      else
      {
         return mapping.findForward( "manageClientOrderHeaderInHouse" );
      }
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
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );

         // 获得当前主键
         String orderHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );
         if ( orderHeaderId == null || orderHeaderId.trim().isEmpty() )
         {
            orderHeaderId = ( ( ClientOrderHeaderVO ) form ).getOrderHeaderId();
         }

         // 根据主键查找对应的clientOrderHeaderVO
         ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

         if ( clientOrderHeaderVO == null )
         {
            clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;
         }

         if ( "0".equals( clientOrderHeaderVO.getContractPeriod() ) )
         {
            clientOrderHeaderVO.setContractPeriod( "3" );
         }

         // 刷新VO对象，初始化对象列表及国际化
         clientOrderHeaderVO.reset( null, request );
         clientOrderHeaderVO.setSubAction( VIEW_OBJECT );

         request.setAttribute( "clientOrderHeaderForm", clientOrderHeaderVO );

         // 初始化ClientVO
         final ClientVO clientVO = clientService.getClientVOByClientId( clientOrderHeaderVO.getClientId() );

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

         request.setAttribute( "orderBindContract", orderBindContract );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到编辑界面
      if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
      {
         return mapping.findForward( "manageClientOrderHeader" );
      }
      else
      {
         return mapping.findForward( "manageClientOrderHeaderInHouse" );
      }

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
   // Reviewed by Kevin Jin at 2013-11-13
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final String accessAction = getAccessAction( request, response );
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
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
            // 获得ActionForm
            final ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;
            // 获取登录用户
            clientOrderHeaderVO.setCreateBy( getUserId( request, response ) );
            clientOrderHeaderVO.setModifyBy( getUserId( request, response ) );
            // 不折算科目
            final String[] excludeDivideItemIds = request.getParameterValues( "checkBox_excludeDivideItemIds" );
            if ( excludeDivideItemIds != null && excludeDivideItemIds.length > 0 )
            {
               clientOrderHeaderVO.setExcludeDivideItemIds( KANUtil.toJasonArray( excludeDivideItemIds, "," ) );
            }
            // 保存合同到期提醒
            final String[] noticeExpires = request.getParameterValues( "noticeExpire" );
            if ( noticeExpires != null && noticeExpires.length > 0 )
            {
               clientOrderHeaderVO.setNoticeExpire( KANUtil.toJasonArray( noticeExpires, "," ) );
            }
            // 保存试用到期提醒
            final String[] noticeProbationExpires = request.getParameterValues( "noticeProbationExpire" );
            if ( noticeProbationExpires != null && noticeProbationExpires.length > 0 )
            {
               clientOrderHeaderVO.setNoticeProbationExpire( KANUtil.toJasonArray( noticeProbationExpires, "," ) );
            }
            // 保存退休到期提醒
            final String[] noticeRetires = request.getParameterValues( "noticeRetire" );
            if ( noticeRetires != null && noticeRetires.length > 0 )
            {
               clientOrderHeaderVO.setNoticeRetire( KANUtil.toJasonArray( noticeRetires, "," ) );
            }
            // 保存自定义Column
            clientOrderHeaderVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // TODO 后续需要更正，临时措施（社保公积金拆分后修改）
            clientOrderHeaderVO.setFundMonth( clientOrderHeaderVO.getSbMonth() );

            // TODO 后续需要移除，临时措施（不满月可以有不同的折算方式）
            clientOrderHeaderVO.setDivideTypeIncomplete( clientOrderHeaderVO.getDivideType() );

            // 新建对象
            clientOrderHeaderService.insertClientOrderHeader( clientOrderHeaderVO );

            // 判断是否需要转向
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // 生成转向地址
               forwardURL = forwardURL + clientOrderHeaderVO.getEncodedId();
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
   // Reviewed by Kevin Jin at 2013-11-05
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      final String accessAction = getAccessAction( request, response );
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
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

            // 获取SubAction
            final String subAction = request.getParameter( "subAction" );

            // 获得当前主键
            final String orderHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );

            // 获得ClientOrderHeaderVO
            final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

            // 设置数据
            clientOrderHeaderVO.update( ( ClientOrderHeaderVO ) form );
            // 保存自定义Column
            clientOrderHeaderVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // 不折算科目
            final String[] excludeDivideItemIds = request.getParameterValues( "checkBox_excludeDivideItemIds" );
            if ( excludeDivideItemIds != null && excludeDivideItemIds.length > 0 )
            {
               clientOrderHeaderVO.setExcludeDivideItemIds( KANUtil.toJasonArray( excludeDivideItemIds, "," ) );
            }
            clientOrderHeaderVO.setModifyBy( getUserId( request, response ) );
            clientOrderHeaderVO.reset( mapping, request );
            // 保存合同到期提醒
            final String[] noticeExpires = request.getParameterValues( "noticeExpire" );
            if ( noticeExpires != null && noticeExpires.length > 0 )
            {
               clientOrderHeaderVO.setNoticeExpire( KANUtil.toJasonArray( noticeExpires, "," ) );
            }
            // 保存试用到期提醒
            final String[] noticeProbationExpires = request.getParameterValues( "noticeProbationExpire" );
            if ( noticeProbationExpires != null && noticeProbationExpires.length > 0 )
            {
               clientOrderHeaderVO.setNoticeProbationExpire( KANUtil.toJasonArray( noticeProbationExpires, "," ) );
            }
            // 保存退休到期提醒
            final String[] noticeRetires = request.getParameterValues( "noticeRetire" );
            if ( noticeRetires != null && noticeRetires.length > 0 )
            {
               clientOrderHeaderVO.setNoticeRetire( KANUtil.toJasonArray( noticeRetires, "," ) );
            }
            // 香港社保到期提醒
            final String[] noticeHKSBs = request.getParameterValues( "noticeHKSB" );
            if ( noticeHKSBs != null && noticeHKSBs.length > 0 )
            {
               clientOrderHeaderVO.setNoticeHKSB( KANUtil.toJasonArray( noticeHKSBs, "," ) );
            }

            // 保存自定义Column
            clientOrderHeaderVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // 如果是订单提交
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               clientOrderHeaderVO.reset( mapping, request );
               if ( clientOrderHeaderService.submitClientOrderHeader( clientOrderHeaderVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, clientOrderHeaderVO, Operate.SUBMIT, clientOrderHeaderVO.getOrderHeaderId(), null );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, clientOrderHeaderVO, Operate.MODIFY, clientOrderHeaderVO.getOrderHeaderId(), null );
               }
            }
            else
            {
               clientOrderHeaderService.updateClientOrderHeader( clientOrderHeaderVO );
               // 判断是否需要转向
               String forwardURL = request.getParameter( "forwardURL" );
               if ( forwardURL != null && !forwardURL.trim().isEmpty() )
               {
                  // 生成转向地址
                  request.getRequestDispatcher( forwardURL ).forward( request, response );

                  return null;
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, clientOrderHeaderVO, Operate.MODIFY, clientOrderHeaderVO.getOrderHeaderId(), null );
               }
            }
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return to_objectModify( mapping, form, request, response );
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
   // Reviewed by Kevin Jin at 2013-11-12
   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // 获得当前主键
         final String orderHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );

         // 获得主键对应对象
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

         // 设置数据
         clientOrderHeaderVO.setModifyBy( getUserId( request, response ) );
         clientOrderHeaderVO.setModifyDate( new Date() );
         clientOrderHeaderVO.reset( null, request );
         clientOrderHeaderService.submitClientOrderHeader( clientOrderHeaderVO );
         success( request, MESSAGE_TYPE_SUBMIT );
         insertlog( request, clientOrderHeaderVO, Operate.SUBMIT, clientOrderHeaderVO.getOrderHeaderId(), null );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return list_object( mapping, form, request, response );
   }

   /**
    * Cancel Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-12
   public ActionForward cancel_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

            // 获得当前主键
            final String orderHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );

            // 获得主键对应对象
            final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

            // 设置数据
            clientOrderHeaderVO.setStatus( "8" );
            clientOrderHeaderVO.setModifyBy( getUserId( request, response ) );
            clientOrderHeaderVO.setModifyDate( new Date() );

            clientOrderHeaderService.updateClientOrderHeader( clientOrderHeaderVO );
            success( request, MESSAGE_TYPE_UPDATE );
            insertlog( request, clientOrderHeaderVO, Operate.MODIFY, clientOrderHeaderVO.getOrderHeaderId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return to_objectModify( mapping, form, request, response );
   }

   /**
    * Stop Object
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Reviewed by Kevin Jin at 2013-11-12
   public ActionForward stop_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 避免重复提交
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

            // 获得当前主键
            final String orderHeaderId = KANUtil.decodeString( request.getParameter( "id" ) );

            // 获得主键对应对象
            final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

            // 设置数据
            clientOrderHeaderVO.setStatus( "7" );
            clientOrderHeaderVO.setModifyBy( getUserId( request, response ) );
            clientOrderHeaderVO.setModifyDate( new Date() );

            clientOrderHeaderService.updateClientOrderHeader( clientOrderHeaderVO );
            success( request, MESSAGE_TYPE_UPDATE );
            insertlog( request, clientOrderHeaderVO, Operate.MODIFY, clientOrderHeaderVO.getOrderHeaderId(), null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
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
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         // 获得Action Form
         ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;

         // 存在选中的ID
         if ( clientOrderHeaderVO.getSelectedIds() != null && !clientOrderHeaderVO.getSelectedIds().equals( "" ) )
         {
            // 分割
            for ( String selectedId : clientOrderHeaderVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.trim().equals( "null" ) )
               {
                  // 主键转码
                  final String clientOrderHeaderId = KANUtil.decodeStringFromAjax( selectedId );
                  // 根据主键获得对应VO
                  final ClientOrderHeaderVO clientOrderHeaderVOForDel = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( clientOrderHeaderId );
                  clientOrderHeaderVOForDel.setModifyBy( getUserId( request, response ) );
                  clientOrderHeaderVOForDel.setModifyDate( new Date() );
                  // 调用删除接口
                  clientOrderHeaderService.deleteClientOrderHeader( clientOrderHeaderVOForDel );
               }
            }

            insertlog( request, clientOrderHeaderVO, Operate.MODIFY, null, KANUtil.decodeSelectedIds( clientOrderHeaderVO.getSelectedIds() ) );
         }

         // 清除Selected IDs和子Action
         clientOrderHeaderVO.setSelectedIds( "" );
         clientOrderHeaderVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Delete Object Ajax
    * Tab删除订单
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
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // 获取主键
         final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderHeaderId" ) );

         // 获取ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );
         clientOrderHeaderVO.setModifyBy( getUserId( request, response ) );
         clientOrderHeaderVO.setModifyDate( new Date() );

         // 特殊状态不能删除
         if ( clientOrderHeaderVO.getStatus() != null
               && ( clientOrderHeaderVO.getStatus().trim().equals( "3" ) || clientOrderHeaderVO.getStatus().trim().equals( "5" )
                     || clientOrderHeaderVO.getStatus().trim().equals( "6" ) || clientOrderHeaderVO.getStatus().trim().equals( "7" ) ) )
         {
            rows = 0;
         }
         else
         {
            // 调用删除接口
            rows = clientOrderHeaderService.deleteClientOrderHeader( clientOrderHeaderVO );
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
    * Copy Object
    * 拷贝 订单/派送协议
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward copy_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( KANUtil.filterEmpty( request.getParameter( "orderHeaderId" ) ) != null )
         {
            // 初始化Service接口
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
            // 获取数据库中原有对象
            final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderHeaderId" ) );
            final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

            ( ( ClientOrderHeaderVO ) form ).setOrderHeaderId( orderHeaderId );
            ( ( ClientOrderHeaderVO ) form ).update( clientOrderHeaderVO );
            ( ( ClientOrderHeaderVO ) form ).setClientId( clientOrderHeaderVO.getClientId() );
            // “状态”为新建
            ( ( ClientOrderHeaderVO ) form ).setStatus( "1" );
            // 获取登录用户
            ( ( ClientOrderHeaderVO ) form ).setCreateBy( getUserId( request, response ) );
            ( ( ClientOrderHeaderVO ) form ).setModifyBy( getUserId( request, response ) );
            ( ( ClientOrderHeaderVO ) form ).setCreateDate( new Date() );
            ( ( ClientOrderHeaderVO ) form ).setModifyDate( new Date() );

            // 保存自定义Column
            ( ( ClientOrderHeaderVO ) form ).setRemark1( saveDefineColumns( request, getAccessAction( request, response ) ) );

            // 新建对象
            if ( clientOrderHeaderService.copyClientOrderHeader( ( ClientOrderHeaderVO ) form ) > 0 )
            {
               // 返回添加成功标记
               success( request, null, "复制信息成功！", MESSAGE_HEADER );
            }
            else
            {
               error( request, null, "复制信息不成功！", MESSAGE_HEADER );
            }

         }
         else
         {
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

   // Reviewed by Kevin Jin at 2013-11-04
   public ActionForward list_special_info_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化OrderHeaderId
         final String orderHeaderId = KANUtil.decodeString( request.getParameter( "orderHeaderId" ) );

         // 初始化ClientOrderHeaderVO
         ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;

         if ( orderHeaderId != null && !orderHeaderId.trim().isEmpty() )
         {
            // 初始化Service接口
            final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

            // 获取ClientOrderHeaderVO
            clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

            // 刷新对象，初始化对象列表及国际化
            if ( clientOrderHeaderVO != null )
            {
               clientOrderHeaderVO.reset( null, request );
            }
         }
         else
         {
            // 加班Tab初始化设置
            clientOrderHeaderVO.setApplyOTFirst( "1" );
            clientOrderHeaderVO.setOtLimitByDay( "0" );
            clientOrderHeaderVO.setOtLimitByMonth( "0" );
            clientOrderHeaderVO.setWorkdayOTItemId( "21" );
            clientOrderHeaderVO.setWeekendOTItemId( "22" );
            clientOrderHeaderVO.setHolidayOTItemId( "23" );

            // 结算Tab初始化设置
            clientOrderHeaderVO.setInvoiceType( "2" );
            clientOrderHeaderVO.setSalaryMonth( "1" );
            clientOrderHeaderVO.setSbMonth( "1" );
            clientOrderHeaderVO.setCbMonth( "1" );
            clientOrderHeaderVO.setPersonalSBBurden( "2" );

            // 薪酬Tab初始化设置
            clientOrderHeaderVO.setCircleStartDay( "1" );
            clientOrderHeaderVO.setCircleEndDay( "31" );
            clientOrderHeaderVO.setAttendanceCheckType( "2" );
            clientOrderHeaderVO.setAttendanceGenerate( "1" );
            clientOrderHeaderVO.setCalendarId( "1" );
            clientOrderHeaderVO.setShiftId( "1" );
            clientOrderHeaderVO.setApproveType( "2" );
            clientOrderHeaderVO.setSalaryType( "1" );
            clientOrderHeaderVO.setDivideType( "2" );

            // 如果clientOrderHeaderVO的个税起征点无值
            if ( KANUtil.filterEmpty( clientOrderHeaderVO.getIncomeTaxBaseId() ) == null )
            {
               // 设置clientOrderHeaderVO的默认个税起征点
               if ( clientOrderHeaderVO.getIncomeTaxBases() != null && clientOrderHeaderVO.getIncomeTaxBases().size() > 0 )
               {
                  // 如果只有一条记录
                  if ( clientOrderHeaderVO.getIncomeTaxBases().size() == 2 )
                  {
                     clientOrderHeaderVO.setIncomeTaxBaseId( clientOrderHeaderVO.getIncomeTaxBases().get( 1 ).getMappingId() );
                  }
                  //  多条记录取第一条设置为"默认"的记录
                  else
                  {
                     for ( MappingVO incometaxBaseMappingVO : clientOrderHeaderVO.getIncomeTaxBases() )
                     {
                        // 初始化Service接口
                        final IncomeTaxBaseService incomeTaxBaseService = ( IncomeTaxBaseService ) getService( "incomeTaxBaseService" );
                        final IncomeTaxBaseVO incomeTaxBaseVO = incomeTaxBaseService.getIncomeTaxBaseVOByBaseId( incometaxBaseMappingVO.getMappingId() );

                        if ( incomeTaxBaseVO != null && incomeTaxBaseVO.getIsDefault() != null && incomeTaxBaseVO.getIsDefault().equals( "1" ) )
                        {
                           clientOrderHeaderVO.setIncomeTaxBaseId( incometaxBaseMappingVO.getMappingId() );
                           break;
                        }

                     }
                  }
               }
            }

            // 如果clientOrderHeaderVO的个税起征点无值
            if ( KANUtil.filterEmpty( clientOrderHeaderVO.getIncomeTaxRangeHeaderId() ) == null )
            {
               // 设置clientOrderHeaderVO的默认个税税率
               if ( clientOrderHeaderVO.getIncomeTaxRangeHeaders() != null && clientOrderHeaderVO.getIncomeTaxRangeHeaders().size() > 0 )
               {
                  // 如果只有一条记录
                  if ( clientOrderHeaderVO.getIncomeTaxRangeHeaders().size() == 2 )
                  {
                     clientOrderHeaderVO.setIncomeTaxRangeHeaderId( clientOrderHeaderVO.getIncomeTaxRangeHeaders().get( 1 ).getMappingId() );
                  }
                  //  多条记录取第一条设置为"默认"的记录
                  else
                  {
                     for ( MappingVO incomeTaxRangeHeaderMappingVO : clientOrderHeaderVO.getIncomeTaxRangeHeaders() )
                     {
                        // 初始化Service接口
                        final IncomeTaxRangeHeaderService incomeTaxRangeHeaderService = ( IncomeTaxRangeHeaderService ) getService( "incomeTaxRangeHeaderService" );
                        final IncomeTaxRangeHeaderVO incomeTaxRangeHeaderVO = incomeTaxRangeHeaderService.getIncomeTaxRangeHeaderVOByHeaderId( incomeTaxRangeHeaderMappingVO.getMappingId() );

                        if ( incomeTaxRangeHeaderVO != null && incomeTaxRangeHeaderVO.getIsDefault() != null && incomeTaxRangeHeaderVO.getIsDefault().equals( "1" ) )
                        {
                           clientOrderHeaderVO.setIncomeTaxRangeHeaderId( incomeTaxRangeHeaderMappingVO.getMappingId() );
                           break;
                        }

                     }
                  }
               }
            }
         }

         // 初始化PagedListHolder
         final PagedListHolder serviceContractHolder = new PagedListHolder();

         // 装载派送信息Holder数据
         fetchServiceContractHolder( request, response, serviceContractHolder, orderHeaderId, true );

         // 初始化Service接口
         final ClientOrderHeaderRuleService clientOrderHeaderRuleService = ( ClientOrderHeaderRuleService ) getService( "clientOrderHeaderRuleService" );
         final List< Object > clientOrderHeaderRuleVOs = clientOrderHeaderRuleService.getClientOrderHeaderRuleVOsByClientOrderHeaderId( orderHeaderId );
         if ( clientOrderHeaderRuleVOs != null && clientOrderHeaderRuleVOs.size() > 0 )
         {
            for ( Object obj : clientOrderHeaderRuleVOs )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         // 初始化Service接口
         final ClientOrderDetailService clientOrderDetailService = ( ClientOrderDetailService ) getService( "clientOrderDetailService" );
         final List< Object > clientOrderDetailVOs = clientOrderDetailService.getClientOrderDetailVOsByClientOrderHeaderId( orderHeaderId );
         if ( clientOrderDetailVOs != null && clientOrderDetailVOs.size() > 0 )
         {
            for ( Object obj : clientOrderDetailVOs )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         // 初始化Service接口
         final ClientOrderSBService clientOrderSBService = ( ClientOrderSBService ) getService( "clientOrderSBService" );
         final List< Object > clientOrderSBVOs = clientOrderSBService.getClientOrderSBVOsByClientOrderHeaderId( orderHeaderId );
         if ( clientOrderSBVOs != null && clientOrderSBVOs.size() > 0 )
         {
            for ( Object obj : clientOrderSBVOs )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         // 初始化Service接口
         final ClientOrderCBService clientOrderCBService = ( ClientOrderCBService ) getService( "clientOrderCBService" );
         final List< Object > clientOrderCBVOs = clientOrderCBService.getClientOrderCBVOsByClientOrderHeaderId( orderHeaderId );
         if ( clientOrderCBVOs != null && clientOrderCBVOs.size() > 0 )
         {
            for ( Object obj : clientOrderCBVOs )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         // 初始化Service接口
         final ClientOrderLeaveService clientOrderLeaveService = ( ClientOrderLeaveService ) getService( "clientOrderLeaveService" );
         final List< Object > clientOrderLeaveVOs = clientOrderLeaveService.getClientOrderLeaveVOsByOrderHeaderId( orderHeaderId );
         if ( clientOrderLeaveVOs != null && clientOrderLeaveVOs.size() > 0 )
         {
            for ( Object obj : clientOrderLeaveVOs )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         // 初始化Service接口
         final ClientOrderOTService clientOrderOTService = ( ClientOrderOTService ) getService( "clientOrderOTService" );
         final List< Object > clientOrderOTVOs = clientOrderOTService.getClientOrderOTVOsByClientOrderHeaderId( orderHeaderId );
         if ( clientOrderOTVOs != null && clientOrderOTVOs.size() > 0 )
         {
            for ( Object obj : clientOrderOTVOs )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         // 初始化Service接口
         final ClientOrderOtherService clientOrderOtherService = ( ClientOrderOtherService ) getService( "clientOrderOtherService" );
         final List< Object > clientOrderOtherVOs = clientOrderOtherService.getClientOrderOtherVOsByOrderHeaderId( orderHeaderId );
         if ( clientOrderOtherVOs != null && clientOrderOtherVOs.size() > 0 )
         {
            for ( Object obj : clientOrderOtherVOs )
            {
               ( ( ActionForm ) obj ).reset( null, request );
            }
         }

         // 薪酬供应商
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         final List< String > headerIds = new ArrayList< String >();
         for ( Object obj : clientOrderSBVOs )
         {
            headerIds.add( ( ( ClientOrderSBVO ) obj ).getSbSolutionId() );
         }

         final SocialBenefitSolutionHeaderVO condSBSolutionHeaderVO = new SocialBenefitSolutionHeaderVO();
         condSBSolutionHeaderVO.setAccountId( getAccountId( request, response ) );
         condSBSolutionHeaderVO.setCorpId( getCorpId( request, response ) );
         condSBSolutionHeaderVO.setHeaderIds( headerIds );
         // 3 代缴社保
         condSBSolutionHeaderVO.setServiceIds( "3" );
         final List< Object > vendorVOs = vendorService.getVendorVOsBySBSolutionHeaderVO( condSBSolutionHeaderVO );
         List< MappingVO > salaryVendors = new ArrayList< MappingVO >();
         // 添加请选择
         salaryVendors.add( clientOrderHeaderVO.getEmptyMappingVO() );
         for ( Object vendorObject : vendorVOs )
         {
            final VendorVO tempVendorVO = ( VendorVO ) vendorObject;
            final MappingVO tempMappingVO = new MappingVO();
            tempMappingVO.setMappingId( tempVendorVO.getVendorId() );
            tempMappingVO.setMappingValue( "zh".equalsIgnoreCase( getLocale( request ).getLanguage() ) ? tempVendorVO.getNameZH() : tempVendorVO.getNameEN() );
            salaryVendors.add( tempMappingVO );
         }
         clientOrderHeaderVO.setSalaryVendors( salaryVendors );

         // ID
         request.setAttribute( "orderId", orderHeaderId );

         // Form
         clientOrderHeaderVO.setSubAction( request.getParameter( "subAction" ) );
         request.setAttribute( "clientOrderHeaderForm", clientOrderHeaderVO );

         // List
         request.setAttribute( "serviceContractHolder", serviceContractHolder );
         request.setAttribute( "clientOrderHeaderRuleVOs", clientOrderHeaderRuleVOs );
         request.setAttribute( "clientOrderDetailVOs", clientOrderDetailVOs );
         request.setAttribute( "clientOrderSBVOs", clientOrderSBVOs );
         request.setAttribute( "clientOrderCBVOs", clientOrderCBVOs );
         request.setAttribute( "clientOrderLeaveVOs", clientOrderLeaveVOs );
         request.setAttribute( "clientOrderOTVOs", clientOrderOTVOs );
         request.setAttribute( "clientOrderOtherVOs", clientOrderOtherVOs );

         // Tab Number
         request.setAttribute( "numberOfEmployee", ( serviceContractHolder == null ) ? ( "0" ) : ( serviceContractHolder.getHolderSize() ) );
         request.setAttribute( "numberOfClientOrderHeaderRule", ( clientOrderHeaderRuleVOs == null ) ? ( "0" ) : ( clientOrderHeaderRuleVOs.size() ) );
         request.setAttribute( "numberOfClientOrderServiceFee", ( clientOrderDetailVOs == null ) ? ( "0" ) : ( clientOrderDetailVOs.size() ) );
         request.setAttribute( "numberOfClientOrderSB", ( clientOrderSBVOs == null ) ? ( "0" ) : ( clientOrderSBVOs.size() ) );
         request.setAttribute( "numberOfClientOrderCB", ( clientOrderCBVOs == null ) ? ( "0" ) : ( clientOrderCBVOs.size() ) );
         request.setAttribute( "numberOfClientOrderLeave", ( clientOrderLeaveVOs == null ) ? ( "0" ) : ( clientOrderLeaveVOs.size() ) );
         request.setAttribute( "numberOfClientOrderOT", ( clientOrderOTVOs == null ) ? ( "0" ) : ( clientOrderOTVOs.size() ) );
         request.setAttribute( "numberOfClientOrderOther", ( clientOrderOtherVOs == null ) ? ( "0" ) : ( clientOrderOtherVOs.size() ) );
         request.setAttribute( "numberOfAttachment", ( clientOrderHeaderVO == null || clientOrderHeaderVO.getAttachmentArray() == null ) ? ( "0" )
               : ( clientOrderHeaderVO.getAttachmentArray().length ) );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      final String comeForm = request.getParameter( "comeFrom" );
      if ( KANUtil.filterEmpty( comeForm ) != null && comeForm.equals( "workflow" ) )
      {
         return mapping.findForward( "workflowClientOrderHeaderSpecialInfo" );
      }

      // 处理Return
      if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
      {
         return mapping.findForward( "manageClientOrderHeaderSpecialInfo" );
      }
      else
      {
         return mapping.findForward( "manageClientOrderHeaderSpecialInfoInHouse" );
      }

   }

   // 装载Tab派送协议Holder
   // Add by siuvan.xia @ 2014-07-08
   private void fetchServiceContractHolder( final HttpServletRequest request, final HttpServletResponse response, final PagedListHolder serviceContractHolder,
         final String orderHeaderId, final boolean isPaged ) throws KANException
   {
      // 新建情况不查询服务协议
      if ( KANUtil.filterEmpty( orderHeaderId ) != null )
      {
         // 初始化Service接口
         final EmployeeContractService employeeContractService = ( EmployeeContractService ) getService( "employeeContractService" );

         // 初始化EmployeeContractVO
         final EmployeeContractVO employeeContractVO = new EmployeeContractVO();
         employeeContractVO.setAccountId( getAccountId( request, response ) );
         employeeContractVO.setCorpId( getCorpId( request, response ) );
         employeeContractVO.setOrderId( orderHeaderId );
         employeeContractVO.setFlag( "2" );
         employeeContractVO.setSortColumn( "contractId" );
         employeeContractVO.setSortOrder( "desc" );

         // 传入当前值对象
         serviceContractHolder.setObject( employeeContractVO );
         // 设置页面记录条数
         serviceContractHolder.setPageSize( this.listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         employeeContractService.getEmployeeContractVOsByCondition( serviceContractHolder, isPaged );
         // 刷新Holder，国际化传值
         refreshHolder( serviceContractHolder, request );
      }

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
   public ActionForward list_object_options_json( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
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
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         // 获取clientId
         final String clientId = request.getParameter( "clientId" );

         final List< Object > list = clientOrderHeaderService.getClientOrderHeaderBaseViewsByClientId( clientId );

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( list );
         // Send to clientOrderHeaderGroup
         out.println( array.toString() );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return null;
   }

   /**  
    * list_applyOTFirst_options_ajax
    *	生成“加班需要申请”下拉框
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_applyOTFirst_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 获得applyOTFirst 的值
         final String applyOTFirst = request.getParameter( "applyOTFirst" );
         // 初始化下拉选项
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs = KANUtil.getMappings( request.getLocale(), "flag" );
         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "applyOTFirst", applyOTFirst ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return null;
   }

   /**  
    * list_item_options_ajax
    *	生成账户对应“科目”下拉框
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_item_options_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         // 获得ItemId 的值
         final String itemId = request.getParameter( "itemId" );
         // 获得加班科目类型
         final String itemType = request.getParameter( "itemType" );
         // 初始化下拉选项
         List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         // 获得当前AccountId
         final String accountId = getAccountId( request, response );
         // 获得当前张辉对应的加班科目组
         mappingVOs = KANConstants.getKANAccountConstants( accountId ).getOtItems( request.getLocale().getLanguage() );
         // 添加“请选择”选项
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );
         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, itemType, itemId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return null;
   }

   /**
    * 根据客户ID获取客户订单下拉列表
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   // Added by Kevin Jin at 2013-12-13
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

         // 初始化 Service
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );

         // 获取OrderId
         final String orderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderId" ) );

         // 获取ClientId
         String corpId = KANUtil.decodeStringFromAjax( request.getParameter( "clientId" ) );

         if ( KANConstants.ROLE_IN_HOUSE.equals( getRole( request, null ) ) )
         {
            corpId = getCorpId( request, response );
         }

         // 初始化ClientOrderHeaderVO
         final ClientOrderHeaderVO clientOrderHeaderVO = new ClientOrderHeaderVO();
         clientOrderHeaderVO.setCorpId( corpId );
         clientOrderHeaderVO.setAccountId( getAccountId( request, response ) );
         clientOrderHeaderVO.setStatus( "3, 5" );

         // 获得登录客户对应的所有帐套信息
         final List< Object > clientOrderHeaderVOs = clientOrderHeaderService.getClientOrderHeaderVOsByCondition( clientOrderHeaderVO );

         // 初始化下拉选项
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();
         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         if ( clientOrderHeaderVOs != null && clientOrderHeaderVOs.size() > 0 )
         {
            for ( Object clientOrderHeaderVOObject : clientOrderHeaderVOs )
            {
               // 初始化ClientOrderHeaderVO
               final ClientOrderHeaderVO tempClientOrderHeaderVO = ( ClientOrderHeaderVO ) clientOrderHeaderVOObject;

               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( tempClientOrderHeaderVO.getOrderHeaderId() );
               mappingVO.setMappingValue( tempClientOrderHeaderVO.getOrderHeaderId() );

               // 带上描述信息
               if ( tempClientOrderHeaderVO.getDescription() != null && !tempClientOrderHeaderVO.getDescription().trim().isEmpty() )
               {
                  mappingVO.setMappingValue( tempClientOrderHeaderVO.getOrderHeaderId() + " - " + tempClientOrderHeaderVO.getDescription() );
               }
               else
               {
                  mappingVO.setMappingValue( tempClientOrderHeaderVO.getOrderHeaderId() );
               }

               mappingVOs.add( mappingVO );
            }
         }

         out.println( KANUtil.getOptionHTML( mappingVOs, "orderId", orderId ) );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到列表界面
      return null;
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
      if ( getRole( request, response ).equals( KANConstants.ROLE_HR_SERVICE ) )
      {
         return mapping.findForward( "manageClientOrderHeader" );
      }
      else
      {
         return mapping.findForward( "manageClientOrderHeaderInHouse" );
      }

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
      final ClientOrderHeaderVO clientOrderHeaderVO = ( ClientOrderHeaderVO ) form;
      // 获得ClientId
      final String clientId = KANUtil.filterEmpty( clientOrderHeaderVO.getClientId() );

      final ClientVO clientVO = clientService.getClientVOByClientId( clientId );

      if ( clientVO == null )
      {
         request.setAttribute( "clientIdError", "客户ID输入无效！" );
         int errorCount = ( Integer ) request.getAttribute( "errorCount" );
         errorCount += 1;
         request.setAttribute( "errorCount", errorCount );
      }

   }

   public void updateEmployeeSBBaseBySolution( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // Config the response
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "UTF-8" );
         String orderId = request.getParameter( "orderId" );
         String sbSolutionId = request.getParameter( "sbSolutionId" );
         String accountId = BaseAction.getAccountId( request, response );
         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         clientOrderHeaderService.updateEmployeeSBBaseBySolution( orderId, sbSolutionId, accountId );
         addSuccessAjax( out, null );
         insertlog( request, form, Operate.MODIFY, orderId, "应用最低基数>>updateEmployeeSBBaseBySolution(" + orderId + "," + sbSolutionId + "," + accountId + ")" );
         out.flush();
         out.close();
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   private String getFinalEndDate( final ClientOrderHeaderVO clientOrderHeaderVO ) throws Exception
   {
      String finalEndDate = clientOrderHeaderVO.getEndDate();
      final String startDate = clientOrderHeaderVO.getStartDate() + " 00:00:00";
      final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd hh:mm:ss" );
      final SimpleDateFormat sdf2 = new SimpleDateFormat( "yyyy-MM-dd" );
      final Date sDate = sdf.parse( startDate );
      Calendar calendar = Calendar.getInstance();
      if ( KANUtil.filterEmpty( finalEndDate ) != null )
      {
         final Date eDate = sdf.parse( clientOrderHeaderVO.getEndDate() + " 23:59:59" );
         calendar.setTime( sDate );
         calendar.add( Calendar.YEAR, 3 );
         //如果订单时间差在3年内，按照订单算
         if ( calendar.getTime().getTime() > eDate.getTime() )
         {
            finalEndDate = clientOrderHeaderVO.getEndDate();
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

   // 导出派送信息
   // Added by siuvan.xia @ 2014-07-08
   public ActionForward export_service_contract( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化OrderHeaderId
         final String orderHeaderId = KANUtil.decodeString( request.getParameter( "orderHeaderId" ) );

         // 初始化PagedListHolder
         final PagedListHolder pagedListHolder = new PagedListHolder();
         fetchServiceContractHolder( request, response, pagedListHolder, orderHeaderId, false );

         // 初始化导出表头
         final String[] columnSysNames = new String[] { "contractId", "startDate", "endDate", "employeeId", "employeeNo", "employeeNameZH", "employeeNameEN", "certificateNumber",
               "decodeEmployStatus", "decodeStatus" };

         request.setAttribute( "pagedListHolder", pagedListHolder );
         request.setAttribute( "holderName", "pagedListHolder" );
         request.setAttribute( "fileName", ( getRole( request, response ).equals( "1" ) ? "雇员" : "员工" ) );
         request.setAttribute( "nameZHArray", getNameZHArray( request, response ) );
         request.setAttribute( "nameSysArray", columnSysNames );

         // 导出文件
         return new DownloadFileAction().commonExportList( mapping, form, request, response, false );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   // 导出表头
   private String[] getNameZHArray( final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      final String role = getRole( request, response );
      // 初始化导出表头
      final String[] columnZHNames = new String[ 10 ];
      columnZHNames[ 0 ] = ( role.equals( "1" ) ? "派送信息" : "劳动合同" ) + "ID";
      columnZHNames[ 1 ] = "服务开始时间";
      columnZHNames[ 2 ] = "服务结束时间";
      columnZHNames[ 3 ] = ( role.equals( "1" ) ? "雇员" : "员工" ) + "ID";
      columnZHNames[ 4 ] = ( role.equals( "1" ) ? "雇员" : "员工" ) + "编号";
      columnZHNames[ 5 ] = ( role.equals( "1" ) ? "雇员" : "员工" ) + "姓名（中文）";
      columnZHNames[ 6 ] = ( role.equals( "1" ) ? "雇员" : "员工" ) + "姓名（英文）";
      columnZHNames[ 7 ] = "证件号码";
      columnZHNames[ 8 ] = "雇佣状态";
      columnZHNames[ 9 ] = ( role.equals( "1" ) ? "派送信息" : "劳动合同" ) + "状态";
      return columnZHNames;
   }

   // Added by siuvan.xia 2015-02-09
   public ActionForward calculate_annual_leave( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化OrderHeaderId
         final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "id" ) );

         // 初始化 Service
         final ClientOrderHeaderService clientOrderHeaderService = ( ClientOrderHeaderService ) getService( "clientOrderHeaderService" );
         final ClientOrderHeaderVO clientOrderHeaderVO = clientOrderHeaderService.getClientOrderHeaderVOByOrderHeaderId( orderHeaderId );

         int rows = clientOrderHeaderService.calculateEmployeeAnnualLeave( clientOrderHeaderVO );
         if ( rows > 0 )
         {
            success( request, null, KANUtil.getProperty( request.getLocale(), "message.prompt.caculation.success" ).replaceAll( "X", String.valueOf( rows ) ) );
            insertlog( request, form, Operate.MODIFY, orderHeaderId, "计算年假：rows " + rows );
         }
         else
         {
            warning( request, null, KANUtil.getProperty( request.getLocale(), "message.prompt.caculation.failure" ) );
            insertlog( request, form, Operate.MODIFY, orderHeaderId, "计算年假：rows " + rows );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return to_objectModify( mapping, form, request, response );
   }

}

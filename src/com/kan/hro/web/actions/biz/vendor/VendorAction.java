package com.kan.hro.web.actions.biz.vendor;

import java.io.PrintWriter;
import java.net.URLDecoder;
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

import com.kan.base.domain.HistoryVO;
import com.kan.base.domain.MappingVO;
import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.management.SocialBenefitSolutionHeaderVO;
import com.kan.base.domain.security.PositionVO;
import com.kan.base.domain.system.SocialBenefitDTO;
import com.kan.base.domain.workflow.WorkflowActualVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.HistoryService;
import com.kan.base.service.inf.management.SocialBenefitSolutionHeaderService;
import com.kan.base.service.inf.workflow.WorkflowActualService;
import com.kan.base.util.Cryptogram;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.util.Operate;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.domain.biz.vendor.VendorServiceVO;
import com.kan.hro.domain.biz.vendor.VendorVO;
import com.kan.hro.service.inf.biz.sb.SBHeaderService;
import com.kan.hro.service.inf.biz.vendor.VendorContactService;
import com.kan.hro.service.inf.biz.vendor.VendorService;
import com.kan.hro.service.inf.biz.vendor.VendorServiceService;

public class VendorAction extends BaseAction
{
   // 当前Action对应的Access Action
   public static String accessAction = "HRO_BIZ_VENDOR";
   // 当前Action对应的JavaObjectName
   public static String javaObjectName = "com.kan.hro.domain.biz.sb.SBDTO";

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
   // Reviewed by Kevin Jin at 2013-11-21
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

         // 获取供应商vendorId
         final String vendorId = request.getParameter( "vendorId" );

         // 获取社保方案ID
         final String sbSolutionId = request.getParameter( "sbSolutionId" );

         if ( KANUtil.filterEmpty( sbSolutionId, "0" ) != null )
         {
            // 初始化Service接口
            final VendorService vendorService = ( VendorService ) getService( "vendorService" );

            // 根据SBSolutionId获得VendorVO列表
            final List< Object > vendorVOs = vendorService.getVendorVOsBySBHeaderId( sbSolutionId );

            // 生成下拉框
            if ( vendorVOs != null && vendorVOs.size() > 0 )
            {
               for ( Object vendorVOObject : vendorVOs )
               {
                  final VendorVO vendorVO = ( VendorVO ) vendorVOObject;

                  final MappingVO mappingVO = new MappingVO();
                  mappingVO.setMappingId( vendorVO.getVendorId() );
                  // 如果是中文环境
                  if ( request.getLocale().getLanguage().equalsIgnoreCase( "ZH" ) )
                  {
                     mappingVO.setMappingValue( vendorVO.getNameZH() );
                  }
                  // 如果是中文环境
                  else
                  {
                     mappingVO.setMappingValue( vendorVO.getNameEN() );
                  }
                  mappingVOs.add( mappingVO );
               }
            }
         }

         // Send to client
         out.println( KANUtil.getOptionHTML( mappingVOs, "vendorId", vendorId ) );
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

         // 初始化Service
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );

         // 初始化 JSONArray
         final JSONArray array = new JSONArray();
         array.addAll( vendorService.getVendorBaseViewsByAccountId( getAccountId( request, response ) ) );

         // Send to client
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

   @Override
   public ActionForward list_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 初始化Service接口
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // 获得Action Form
         final VendorVO vendorVO = ( VendorVO ) form;

         //处理数据权限
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, vendorVO );

         // 如果没有指定排序则默认按 vendorId排序
         if ( vendorVO.getSortColumn() == null || vendorVO.getSortColumn().isEmpty() )
         {
            vendorVO.setSortColumn( "vendorId" );
            vendorVO.setSortOrder( "desc" );
         }

         // 获得SubAction
         final String subAction = getSubAction( form );
         // 添加自定义搜索内容
         vendorVO.setRemark1( generateDefineListSearches( request, accessAction ) );
         // 处理SubAction
         dealSubAction( vendorVO, mapping, form, request, response );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder vendorPagedListHolder = new PagedListHolder();
         // 如果是搜索优先，那么SubAction必须是Search Object或Delete Objects
         if ( !isSearchFirst( request, accessAction ) || subAction.equalsIgnoreCase( SEARCH_OBJECT ) || subAction.equalsIgnoreCase( DELETE_OBJECTS ) )
         {
            // 传入当前页
            vendorPagedListHolder.setPage( page );
            // 传入当前值对象
            vendorPagedListHolder.setObject( vendorVO );
            // 设置页面记录条数
            vendorPagedListHolder.setPageSize( getPageSize( request, accessAction ) );
            // 调用Service方法，引用对象返回，第二个参数说明是否分页
            vendorService.getVendorVOsByCondition( vendorPagedListHolder, subAction.equalsIgnoreCase( DOWNLOAD_OBJECTS ) ? false : isPaged( request, accessAction ) );
            // 刷新Holder，国际化传值
            refreshHolder( vendorPagedListHolder, request );
         }

         // Holder需写入Request对象
         request.setAttribute( "pagedListHolder", vendorPagedListHolder );
         // 处理Return
         return dealReturn( accessAction, "listVendor", mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   @Override
   public ActionForward to_objectNew( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 避免重复提交
      this.saveToken( request );

      // 初始化PositionVO
      final PositionVO positionVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getPositionVOByPositionId( getPositionId( request, response ) );

      // 获得ActionForm
      final VendorVO vendorVO = ( VendorVO ) form;
      // 设置Sub Action
      vendorVO.setSubAction( BaseAction.CREATE_OBJECT );
      vendorVO.setType( "2" );
      vendorVO.setStatus( VendorVO.TRUE );
      // 设置所属人所属部门
      if ( positionVO != null )
      {
         vendorVO.setBranch( positionVO.getBranchId() );
         vendorVO.setOwner( positionVO.getPositionId() );
      }
      return mapping.findForward( "manageVendor" );
   }

   @Override
   public ActionForward add_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final VendorService vendorService = ( VendorService ) getService( "vendorService" );
            // 获得当前FORM
            final VendorVO vendorVO = ( VendorVO ) form;
            // 设定当前用户
            vendorVO.setAccountId( getAccountId( request, response ) );
            vendorVO.setCreateBy( getUserId( request, response ) );
            vendorVO.setModifyBy( getUserId( request, response ) );
            vendorVO.setDeleted( "1" );

            // 保存自定义Column
            vendorVO.setRemark1( saveDefineColumns( request, accessAction ) );
            // 调用添加方法
            int result = vendorService.insertVendor( vendorVO );

            if ( result == -1 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
               insertlog( request, vendorVO, Operate.SUBMIT, vendorVO.getVendorId(), null );
            }
            else
            {
               success( request, MESSAGE_TYPE_ADD );
               insertlog( request, vendorVO, Operate.ADD, vendorVO.getVendorId(), null );
            }

            // 判断是否需要转向
            String forwardURL = request.getParameter( "forwardURL" );
            if ( forwardURL != null && !forwardURL.trim().isEmpty() )
            {
               // 生成转向地址
               forwardURL = forwardURL + ( ( VendorVO ) form ).getEncodedId();
               request.getRequestDispatcher( forwardURL ).forward( request, response );

               return null;
            }
         }
         else
         {
            // 清空form
            ( ( VendorVO ) form ).reset();
            // 返回添加重复提交的警告
            error( request, null, KANUtil.getProperty( this.getLocale( request ), "message.prompt.duplicate.submission" ) );

            return list_object( mapping, form, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转到查看界面
      return to_objectModify( mapping, form, request, response );
   }

   @Override
   public ActionForward to_objectModify( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设定记号，防止重复提交
         this.saveToken( request );
         // 初始化Service接口
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // 主键获取需解码
         String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( vendorId == null || vendorId.trim().isEmpty() )
         {
            vendorId = ( ( VendorVO ) form ).getVendorId();
         }

         // 获得VendorVO对象
         final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
         // 刷新对象，初始化对象列表及国际化
         vendorVO.reset( null, request );
         // 如果City Id，则填充Province Id
         if ( vendorVO.getCityId() != null && !vendorVO.getCityId().trim().equals( "" ) && !vendorVO.getCityId().trim().equals( "0" ) )
         {
            vendorVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( vendorVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            vendorVO.setCityIdTemp( vendorVO.getCityId() );
         }
         // 区分修改添加
         vendorVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "vendorForm", vendorVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageVendor" );
   }

   /**  
    * To ObjectModify InVendor
    *	供应商登录显示供应商信息
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward to_objectModify_inVendor( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 设定记号，防止重复提交
         this.saveToken( request );
         // 初始化Service接口
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // 获得登录供应商ID
         String vendorId = getVendorId( request, response );

         // 获得VendorVO对象
         final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
         // 刷新对象，初始化对象列表及国际化
         vendorVO.reset( null, request );

         // 如果City Id，则填充Province Id
         if ( vendorVO.getCityId() != null && !vendorVO.getCityId().trim().equals( "" ) && !vendorVO.getCityId().trim().equals( "0" ) )
         {
            vendorVO.setProvinceId( KANConstants.LOCATION_DTO.getCityVO( vendorVO.getCityId(), request.getLocale().getLanguage() ).getProvinceId() );
            vendorVO.setCityIdTemp( vendorVO.getCityId() );
         }

         // 区分修改添加
         vendorVO.setSubAction( VIEW_OBJECT );
         // 写入request对象
         request.setAttribute( "vendorForm", vendorVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "manageVendor" );
   }

   @Override
   public ActionForward modify_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         if ( this.isTokenValid( request, true ) )
         {
            // 初始化Service接口
            final VendorService vendorService = ( VendorService ) getService( "vendorService" );
            // 主键获取需解码
            final String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
            // 获得VendorVO对象
            final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
            // 获取SubAction
            final String subAction = request.getParameter( "subAction" );
            // 装载界面传值
            vendorVO.update( ( ( VendorVO ) form ) );
            // 获取登录用户
            vendorVO.setModifyBy( getUserId( request, response ) );

            // 保存自定义Column
            vendorVO.setRemark1( saveDefineColumns( request, accessAction ) );

            // 如果是提交
            if ( subAction != null && subAction.trim().equalsIgnoreCase( SUBMIT_OBJECT ) )
            {
               vendorVO.reset( mapping, request );
               if ( vendorService.submitVendor( vendorVO ) == -1 )
               {
                  success( request, MESSAGE_TYPE_SUBMIT );
                  insertlog( request, vendorVO, Operate.SUBMIT, vendorVO.getVendorId(), null );
               }
               else
               {
                  success( request, MESSAGE_TYPE_UPDATE );
                  insertlog( request, vendorVO, Operate.MODIFY, vendorVO.getVendorId(), null );
               }
            }
            else
            {
               vendorService.updateVendor( vendorVO );
               success( request, MESSAGE_TYPE_UPDATE );
               insertlog( request, vendorVO, Operate.MODIFY, vendorVO.getVendorId(), null );
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

   @Override
   protected void delete_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      // No Use
   }

   @Override
   protected void delete_objectList( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // 获得Action Form
         VendorVO vendorVO = ( VendorVO ) form;
         if ( vendorVO.getSelectedIds() != null && !vendorVO.getSelectedIds().equals( "" ) )
         {
            insertlog( request, vendorVO, Operate.DELETE, null, KANUtil.decodeSelectedIds( vendorVO.getSelectedIds() ) );
            // 分割
            for ( String selectedId : vendorVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  vendorVO = vendorService.getVendorVOByVendorId( KANUtil.decodeStringFromAjax( selectedId ) );
                  vendorVO.setModifyBy( getUserId( request, response ) );
                  vendorVO.setModifyDate( new Date() );
                  // 调用删除接口
                  vendorService.deleteVendor( vendorVO );
               }
            }
         }
         // 清除Selected IDs和子Action
         vendorVO.setSelectedIds( "" );
         vendorVO.setSubAction( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   public ActionForward submit_object( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 初始化Service接口
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // 获得主键需解码
         final String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         // 获得LeaveVO对象
         final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
         // 获取登录用户
         vendorVO.setModifyBy( getUserId( request, response ) );
         vendorVO.setLocale( request.getLocale() );

         // 获得Action Form
         vendorVO.reset( mapping, request );

         if ( vendorService.submitVendor( vendorVO ) == -1 )
         {
            success( request, MESSAGE_TYPE_SUBMIT );
            insertlog( request, vendorVO, Operate.SUBMIT, vendorId, null );
         }
         else
         {
            success( request, MESSAGE_TYPE_UPDATE );
            insertlog( request, vendorVO, Operate.MODIFY, vendorId, null );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return list_object( mapping, form, request, response );
   }

   public ActionForward to_workflowView( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 主键获取需解码
         final String vendorId = Cryptogram.decodeString( URLDecoder.decode( request.getParameter( "id" ), "UTF-8" ) );
         if ( vendorId != null && !vendorId.trim().isEmpty() )
         {
            // 初始化Service接口
            final VendorService vendorService = ( VendorService ) getService( "vendorService" );
            // 获得VendorVO对象
            final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
            // 刷新对象，初始化对象列表及国际化
            vendorVO.reset( null, request );
            // 写入request对象
            request.setAttribute( "vendorVO", vendorVO );
         }
         final String workflowId = request.getParameter( "workflowId" );

         WorkflowActualService workflowActualService = ( WorkflowActualService ) getService( "workflowActualService" );

         WorkflowActualVO workflowActualVO = workflowActualService.getWorkflowActualVOByWorkflowId( workflowId );

         request.setAttribute( "workflowActualVO", workflowActualVO );

         if ( workflowId != null && !workflowId.trim().isEmpty() )
         {
            HistoryService historyService = ( HistoryService ) getService( "historyService" );
            HistoryVO historyVO = historyService.getHistoryVOByWorkflowId( workflowId );
            String passObjStr = historyVO.getPassObject();
            if ( passObjStr != null && !passObjStr.trim().isEmpty() )
            {
               final VendorVO vendorVO = ( VendorVO ) JSONObject.toBean( JSONObject.fromObject( passObjStr ), VendorVO.class );
               request.setAttribute( "passObject", vendorVO );
            }

         }

         return mapping.findForward( "workflowVendorView" );
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
         // 获取ListDTO
         final ListDTO listDTO = KANConstants.getKANAccountConstants( BaseAction.getAccountId( request, null ) ).getListDTOByJavaObjectName( javaObjectName, KANUtil.filterEmpty( getCorpId( request, null ) ) );

         // 判断列表是否需要添加导出功能
         if ( listDTO != null && listDTO.getListHeaderVO() != null && listDTO.getListHeaderVO().getExportExcel() != null
               && listDTO.getListHeaderVO().getExportExcel().trim().equals( "1" ) )
         {
            request.setAttribute( "isExportExcel", "1" );
         }

         final String ajax = request.getParameter( "ajax" );
         // 初始化设置Tab Number
         int vendorServiceCount = 0;
         int vendorContactCount = 0;
         int attachmentCount = 0;
         // ID获取需解码
         String vendorId = "";
         if ( new Boolean( ajax ) )
         {
            vendorId = Cryptogram.decodeString( URLDecoder.decode( URLDecoder.decode( request.getParameter( "vendorId" ), "UTF-8" ), "UTF-8" ) );
         }
         else
         {
            vendorId = request.getParameter( "vendorId" );
         }
         // 初始化employeeContractService接口
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         final VendorServiceService vendorServiceService = ( VendorServiceService ) getService( "vendorServiceService" );
         final VendorContactService vendorContactService = ( VendorContactService ) getService( "vendorContactService" );
         if ( vendorId != null && !vendorId.equals( "" ) )
         {
            final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
            vendorVO.reset( null, request );
            attachmentCount = vendorVO.getAttachmentArray().length;

            final PagedListHolder pagedListHolder = new PagedListHolder();
            final VendorServiceVO vendorServiceVO = new VendorServiceVO();
            vendorServiceVO.setVendorId( vendorId );
            vendorServiceVO.setSortOrder( "ASC" );
            vendorServiceVO.setSortColumn( "cityId,sbHeaderId" );
            pagedListHolder.setObject( vendorServiceVO );
            vendorServiceService.getVendorServiceVOsByCondition( pagedListHolder, false );
            // 刷新Holder，国际化传值
            refreshHolder( pagedListHolder, request );
            vendorServiceCount = pagedListHolder.getHolderSize();

            // 获得供应商联系人
            final List< Object > vendorContactVOs = vendorContactService.getVendorContactVOsByVendorId( vendorId );
            //  加载供应商Info
            request.setAttribute( "vendorForm", vendorVO );
            request.setAttribute( "pagedListHolder", pagedListHolder );
            request.setAttribute( "vendorContactVOs", vendorContactVOs );
            request.setAttribute( "accountId", getAccountId( request, null ) );
            request.setAttribute( "username", getUsername( request, null ) );
            vendorContactCount = vendorContactVOs.size();
         }

         request.setAttribute( "listVendorServiceCount", vendorServiceCount );
         request.setAttribute( "listVendorContactCount", vendorContactCount );
         request.setAttribute( "attachmentCount", attachmentCount );

         // 初始化Service接口
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );

         // 初始化查询对象SBHeaderVO
         final SBHeaderVO sbHeaderVO = new SBHeaderVO();

         // 设置相关属性
         sbHeaderVO.setVendorId( vendorId );
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         setDataAuth( request, response, sbHeaderVO );

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbHeaderVO.setClientId( getClientId( request, response ) );
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
            // 发送帐套列表
            passClientOrders( request, response );
         }

         decodedObject( sbHeaderVO );

         // 国际化
         sbHeaderVO.reset( mapping, request );
         request.setAttribute( "sbHeaderForm", sbHeaderVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sbHeaderHolder = new PagedListHolder();
         // 传入当前页
         sbHeaderHolder.setPage( getPage( request ) );
         // 传入排序相关字段
         sbHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 如果没有指定排序则默认按月份排序
         if ( sbHeaderVO.getSortColumn() == null || sbHeaderVO.getSortColumn().isEmpty() )
         {
            sbHeaderVO.setSortColumn( "monthly" );
            sbHeaderVO.setSortOrder( "desc" );
         }

         // 传入当前值对象
         sbHeaderHolder.setObject( sbHeaderVO );
         // 设置页面记录条数
         sbHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页(查询供应商对应社保方案集合)
         sbHeaderService.getVendorSBHeaderVOsByCondition( sbHeaderHolder, true );
         refreshHolder( sbHeaderHolder, request );

         // 写入Role
         request.setAttribute( "role", getRole( request, response ) );
         // Holder需写入Request对象
         request.setAttribute( "sbHeaderHolder", sbHeaderHolder );
         request.setAttribute( "listVendorPaymentCount", sbHeaderHolder.getSource().size() );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "listSpecialInfo" );
   }

   public ActionForward getSBMappingVOsByCityId_html( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         response.setContentType( "text/html" );
         response.setCharacterEncoding( "GBK" );

         // 初始化PrintWrite对象
         final PrintWriter out = response.getWriter();

         // 获得城市ID
         final String cityId = request.getParameter( "cityId" );

         // 初始化Service
         final SocialBenefitSolutionHeaderService socialBenefitSolutionHeaderService = ( SocialBenefitSolutionHeaderService ) getService( "socialBenefitSolutionHeaderService" );

         // 初始化MappingVO
         final List< MappingVO > mappingVOs = new ArrayList< MappingVO >();

         mappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

         // 获取SocialBenefitDTO列表
         final List< SocialBenefitDTO > socialBenefitDTOs = KANConstants.SOCIAL_BENEFIT_DTO;

         // 初始化SocialbenefitSolutionHeaderVO列表
         List< Object > socialBenefitSolutionHeaderVOs = null;

         // 存在SocialBenefitDTO列表
         if ( socialBenefitDTOs != null && socialBenefitDTOs.size() > 0 )
         {
            for ( SocialBenefitDTO tempSocialBenefitDTO : socialBenefitDTOs )
            {
               // 存在当前城市社保
               if ( tempSocialBenefitDTO.getSocialBenefitHeaderVO().getCityId().equals( cityId ) )
               {
                  final SocialBenefitSolutionHeaderVO tempVO = new SocialBenefitSolutionHeaderVO();
                  tempVO.setSysHeaderId( tempSocialBenefitDTO.getSocialBenefitHeaderVO().getHeaderId() );
                  tempVO.setAccountId( getAccountId( request, response ) );
                  socialBenefitSolutionHeaderVOs = socialBenefitSolutionHeaderService.getSocialBenefitSolutionHeaderVOsBySysSBHeaderVO( tempVO );
                  break;
               }
            }
         }

         // 存在SocialbenefitSolutionHeaderVO列表
         if ( socialBenefitSolutionHeaderVOs != null && socialBenefitSolutionHeaderVOs.size() > 0 )
         {
            for ( Object o : socialBenefitSolutionHeaderVOs )
            {
               // 初始化SocialBenefitSolutionHeaderVO
               final SocialBenefitSolutionHeaderVO temp = ( SocialBenefitSolutionHeaderVO ) o;

               // 初始化MappingVO
               final MappingVO mappingVO = new MappingVO();
               mappingVO.setMappingId( temp.getHeaderId() );
               mappingVO.setMappingValue( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) ? temp.getNameZH() : temp.getNameEN() );

               if ( KANUtil.filterEmpty( getCorpId( request, response ) ) == null )
               {
                  if ( KANUtil.filterEmpty( temp.getCorpId() ) == null )
                  {
                     mappingVOs.add( mappingVO );
                  }

               }
               else
               {
                  if ( KANUtil.filterEmpty( temp.getCorpId() ) != null && temp.getCorpId().equals( getCorpId( request, response ) ) )
                  {
                     mappingVOs.add( mappingVO );
                  }
               }
            }
         }

         out.println( KANUtil.getSelectHTML( mappingVOs, "manageVendorService_sbHeaderId", "manageVendorService_sbHeaderId", null, null, null ) );
         out.flush();
         out.close();
         return mapping.findForward( "" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * List Object Ajax
    * 供应商弹出模态框
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward list_object_ajax( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 初始化Service接口
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // 获得Action Form 
         final VendorVO vendorVO = ( VendorVO ) form;

         //处理数据权限
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, vendorVO );

         // 解码
         decodedObject( vendorVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder vendorHolder = new PagedListHolder();

         // 传入当前页
         vendorHolder.setPage( page );
         // 传入当前值对象
         vendorHolder.setObject( vendorVO );
         // 设置页面记录条数
         vendorHolder.setPageSize( listPageSize_popup );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         vendorService.getVendorVOsByCondition( vendorHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( vendorHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "vendorHolder", vendorHolder );

         // Ajax Table调用，直接传回 JSP
         return mapping.findForward( "popupTable" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
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
         // 初始化 Service
         final VendorService vendorService = ( VendorService ) getService( "vendorService" );
         // 获取vendorId
         final String vendorId = request.getParameter( "vendorId" );

         // 初始化 JSONObject
         JSONObject jsonObject = new JSONObject();

         // 初始化VendorVO
         final VendorVO vendorVO = vendorService.getVendorVOByVendorId( vendorId );
         if ( vendorVO != null && vendorVO.getAccountId() != null && vendorVO.getAccountId().equals( getAccountId( request, response ) ) )
         {
            vendorVO.reset( mapping, request );
            jsonObject = JSONObject.fromObject( vendorVO );
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

   public List< MappingVO > list_option( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      // 初始化返回值
      final List< MappingVO > vendorMappingVOs = new ArrayList< MappingVO >();
      vendorMappingVOs.add( 0, KANUtil.getEmptyMappingVO( request.getLocale() ) );

      // 初始化 Service
      final VendorService vendorService = ( VendorService ) getService( "vendorService" );

      // 初始化VendorVO
      final VendorVO vendorVO = new VendorVO();
      vendorVO.setAccountId( getAccountId( request, response ) );
      vendorVO.setCorpId( getCorpId( request, response ) );
      // 批准状态
      vendorVO.setStatus( "3" );

      // 获取VendorVO列表
      final List< Object > vendors = vendorService.getVendorVOsByCondition( vendorVO );

      // 存在VendorVO列表
      if ( vendors != null && vendors.size() > 0 )
      {
         for ( Object vendorVOObject : vendors )
         {
            final MappingVO mappingVO = new MappingVO();
            mappingVO.setMappingId( ( ( VendorVO ) vendorVOObject ).getVendorId() );

            if ( request.getLocale().getLanguage().equalsIgnoreCase( "zh" ) )
            {
               mappingVO.setMappingValue( ( ( VendorVO ) vendorVOObject ).getNameZH() );
            }
            else
            {
               mappingVO.setMappingValue( ( ( VendorVO ) vendorVOObject ).getNameEN() );
            }

            vendorMappingVOs.add( mappingVO );
         }
      }

      return vendorMappingVOs;
   }

   public ActionForward list_service_evaluate( final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response )
         throws KANException
   {
      return mapping.findForward( "listServiceEvaluate" );
   }
}

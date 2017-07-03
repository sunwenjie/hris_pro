package com.kan.hro.web.actions.biz.vendor;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.define.ListDTO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.base.web.actions.util.DownloadFileAction;
import com.kan.hro.domain.biz.sb.SBDetailVO;
import com.kan.hro.domain.biz.sb.SBHeaderVO;
import com.kan.hro.service.inf.biz.sb.SBBatchService;
import com.kan.hro.service.inf.biz.sb.SBDetailService;
import com.kan.hro.service.inf.biz.sb.SBHeaderService;

/**   
 * 类名称：SBAction  
 * 类描述：社保操作
 * 创建人：Kevin  
 * 创建时间：2013-9-13  
 */
public class VendorPaymentAction extends BaseAction
{

   // 当前Action对应的JavaObjectName
   public static String javaObjectName = "com.kan.hro.domain.biz.sb.SBDTO";
   
   public static String accessAction = "HRO_BIZ_VENDOR_PAYMENT";

   /**
    * List Estimation
    * 
    *	显示供应商社保对账单列表
    *
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward list_estimation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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

         // 初始化Service接口
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         // 设置pageFlag
         ( ( SBHeaderVO ) form ).setPageFlag( SBHeaderService.PAGE_FLAG_VENDOR );

         // 获得Action Form
         final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) form;
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         setDataAuth( request, response, sbHeaderVO );

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbHeaderVO.setClientId( getClientId( request, response ) );
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
         }

         decodedObject( sbHeaderVO );
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sbHeaderHolder = new PagedListHolder();
         // 传入当前页
         sbHeaderHolder.setPage( getPage( request ) );

         // 如果没有指定排序则默认按月份排序
         if ( sbHeaderVO.getSortColumn() == null || sbHeaderVO.getSortColumn().isEmpty() )
         {
            sbHeaderVO.setSortColumn( "monthly" );
            sbHeaderVO.setSortOrder( "desc" );
         }

         // 添加供应商下拉框
         sbHeaderVO.setVendors( new VendorAction().list_option( mapping, form, request, response ) );

         // 传入当前值对象
         sbHeaderHolder.setObject( sbHeaderVO );

         // 如果是导出
         if ( sbHeaderVO.getSubAction() != null && sbHeaderVO.getSubAction().equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
         {
            // 调用Service方法，引用对象返回
            sbHeaderService.getSBDTOsByCondition( sbHeaderHolder );
            // Holder需写入Request对象
            request.setAttribute( "pagedListHolder", sbHeaderHolder );
            return new DownloadFileAction().specialExportList( mapping, form, request, response );
         }

         // 设置页面记录条数
         sbHeaderHolder.setPageSize( listPageSize_medium );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         sbHeaderService.getAmountVendorSBHeaderVOsByCondition( sbHeaderHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( sbHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "sbHeaderHolder", sbHeaderHolder );
         // 写入pageFlag
         request.setAttribute( "pageFlag", SBBatchService.PAGE_FLAG_BATCH );

         // 如果是Ajax请求
         if ( new Boolean( getAjax( request ) ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listVendorPaymentTable" );
         }

         return mapping.findForward( "listVendorPayment" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * List Object Ajax
    *	ajax获取供应商对应对账单
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
         // 初始化Service接口
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );

         // 获得Action Form
         final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) form;
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         setDataAuth( request, response, sbHeaderVO );
         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
            // 发送帐套列表
            passClientOrders( request, response );
         }

         // 设置相关属性
         sbHeaderVO.setVendorId( KANUtil.decodeStringFromAjax( request.getParameter( "vendorId" ) ) );
         sbHeaderVO.setAccountId( getAccountId( request, response ) );

         decodedObject( sbHeaderVO );
         // 国际化
         sbHeaderVO.reset( mapping, request );
         request.setAttribute( "sbHeaderForm", sbHeaderVO );

         if ( sbHeaderVO.getSubAction() != null )
         {
            // 调用删除方法
            if ( sbHeaderVO.getSubAction().equalsIgnoreCase( DELETE_OBJECTS ) )
            {
               delete_objectList( mapping, form, request, response );
            }
            else if ( sbHeaderVO.getSubAction().equalsIgnoreCase( CONFIRM_OBJECTS ) )
            {
               modify_objectListStatus( mapping, form, request, response );
            }
         }
         // 如果SubAction为空，通常是搜索，点击排序或翻页操作。Ajax提交的搜索内容需要解码。
         else
         {
            decodedObject( sbHeaderVO );
         }

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

         // 如果是导出
         if ( sbHeaderVO.getSubAction() != null && sbHeaderVO.getSubAction().equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
         {

            // 如果是供应商登录则限制导出状态只能为“批准”状态数据
            if ( getRole( request, response ) != null && getRole( request, response ).equals( KANConstants.ROLE_VENDOR ) )
            {
               ( ( SBHeaderVO ) sbHeaderHolder.getObject() ).setAdditionalStatus( "2" );
            }

            // 调用Service方法，引用对象返回
            sbHeaderService.getSBDTOsByCondition( sbHeaderHolder );
            // Holder需写入Request对象
            request.setAttribute( "pagedListHolder", sbHeaderHolder );
            return new DownloadFileAction().specialExportList( mapping, form, request, response );
         }

         // 设置页面记录条数
         sbHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页(查询供应商某个月份的所有社保方案)
         sbHeaderService.getVendorSBHeaderVOsByCondition( sbHeaderHolder, true );
         refreshHolder( sbHeaderHolder, request );

         // 写入Role
         request.setAttribute( "role", getRole( request, response ) );
         // Holder需写入Request对象
         request.setAttribute( "sbHeaderHolder", sbHeaderHolder );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return mapping.findForward( "listHeaderTableForTab" );
   }

   /**  
    * Modify ObjectListStatus
    *	Ajax修改供应商对账单状态
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    * @throws KANException 
    */
   private void modify_objectListStatus( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取的 SBHeaderVO
         final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) form;
         sbHeaderVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );

         // 提交至批准
         sbHeaderService.submit( sbHeaderVO );

         // 存在选中的ID
         if ( sbHeaderVO.getSelectedIds() != null && !sbHeaderVO.getSelectedIds().trim().isEmpty() )
         {
            // 分割
            for ( String selectedId : sbHeaderVO.getSelectedIds().split( "," ) )
            {
               if ( selectedId != null && !selectedId.equals( "null" ) )
               {
                  // 初始化ClientVO
                  final SBHeaderVO tempSBHeaderVO = sbHeaderService.getSBHeaderVOByHeaderId( KANUtil.decodeStringFromAjax( selectedId ) );

                  // 调用接口
                  tempSBHeaderVO.setModifyBy( getUserId( request, response ) );
                  tempSBHeaderVO.setModifyDate( new Date() );

                  // 修改SBHeaderVO状态为“确认”
                  if ( sbHeaderVO.getSubAction().equalsIgnoreCase( CONFIRM_OBJECTS ) )
                  {
                     sbHeaderService.submit( sbHeaderVO );
                  }

               }
            }
         }

         // 重置选中项和SubAction
         ( ( SBHeaderVO ) form ).setSelectedIds( "" );
         ( ( SBHeaderVO ) form ).setSubAction( SEARCH_OBJECT );

         // “确认成功”
         success( request, null, "确认成功" );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**
    * To Vendor Detail
    * 
    * 显示单个供应商指定月份的社保明细
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_vendorDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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

         // 初始化Service接口
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );

         // 初始化查询对象SBHeaderVO
         final SBHeaderVO sbHeaderVO = new SBHeaderVO();

         // 设置相关属性
         sbHeaderVO.setVendorId( KANUtil.decodeString( request.getParameter( "vendorId" ) ) );
         sbHeaderVO.setMonthly( request.getParameter( "monthly" ) );
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         sbHeaderVO.setPageFlag( SBHeaderService.PAGE_FLAG_HEADER );

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
            // 发送帐套列表
            passClientOrders( request, response );
         }

         decodedObject( sbHeaderVO );
         setDataAuth( request, response, sbHeaderVO );
         // 获得指定供应商对应月份SBHeaderVO信息
         final List< Object > amountVendorPaymentHeaderVOs = sbHeaderService.getAmountVendorSBHeaderVOsByCondition( sbHeaderVO );
         // 初始化发送对象
         SBHeaderVO vendorPaymentHeaderVO = new SBHeaderVO();

         if ( amountVendorPaymentHeaderVOs != null && amountVendorPaymentHeaderVOs.size() > 0 )
         {
            vendorPaymentHeaderVO = ( SBHeaderVO ) amountVendorPaymentHeaderVOs.get( 0 );
         }

         // 刷新VO对象，初始化对象列表及国际化
         if ( vendorPaymentHeaderVO != null )
         {
            vendorPaymentHeaderVO.reset( null, request );
         }

         request.setAttribute( "vendorPaymentHeaderVO", vendorPaymentHeaderVO );

         // 如果是搜索或导出查询更新搜索条件
         if ( ( ( SBHeaderVO ) form ).getSubAction() != null
               && ( ( ( SBHeaderVO ) form ).getSubAction().equalsIgnoreCase( SEARCH_OBJECT ) || ( ( SBHeaderVO ) form ).getSubAction().equalsIgnoreCase( DOWNLOAD_OBJECTS ) ) )
         {
            sbHeaderVO.update( ( SBHeaderVO ) form );

            // 供应商ID重新设置
            sbHeaderVO.setVendorId( KANUtil.decodeString( request.getParameter( "vendorId" ) ) );
         }

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

         // 如果是导出
         if ( sbHeaderVO.getSubAction() != null && sbHeaderVO.getSubAction().equalsIgnoreCase( DOWNLOAD_OBJECTS ) )
         {
            // 调用Service方法，引用对象返回
            sbHeaderService.getSBDTOsByCondition( sbHeaderHolder );
            // Holder需写入Request对象
            request.setAttribute( "pagedListHolder", sbHeaderHolder );
            return new DownloadFileAction().specialExportList( mapping, form, request, response );
         }

         // 设置页面记录条数
         sbHeaderHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页(查询供应商某个月份的所有社保方案)
         sbHeaderService.getVendorSBHeaderVOsByCondition( sbHeaderHolder, true );
         refreshHolder( sbHeaderHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "sbHeaderHolder", sbHeaderHolder );

         // Ajax调用
         if ( new Boolean( getAjax( request ) ) )
         {
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listHeader" );
   }

   /**
    * To SBDetail
    * 
    * 显示单个供应商指定月份指定社保方案数据明细
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward to_sbDetail( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
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

         // 初始化Service接口
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );
         final SBDetailService sbDetailService = ( SBDetailService ) getService( "sbDetailService" );

         // 初始化查询对象SBHeaderVO
         SBHeaderVO sbHeaderVO = new SBHeaderVO();

         // 设置相关属性
         sbHeaderVO.setVendorId( KANUtil.decodeString( request.getParameter( "vendorId" ) ) );
         sbHeaderVO.setMonthly( request.getParameter( "monthly" ) );
         sbHeaderVO.setAccountId( getAccountId( request, response ) );
         sbHeaderVO.setPageFlag( SBHeaderService.PAGE_FLAG_HEADER );
         sbHeaderVO.setAdditionalStatus( request.getParameter( "additionalStatus" ) );

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbHeaderVO.setCorpId( getCorpId( request, response ) );
            // 发送帐套列表
            passClientOrders( request, response );
         }

         decodedObject( sbHeaderVO );
         setDataAuth( request, response, sbHeaderVO );
         // 获得指定供应商对应月份SBHeaderVO信息
         final List< Object > vendorPaymentHeaderVOs = sbHeaderService.getAmountVendorSBHeaderVOsByCondition( sbHeaderVO );
         // 初始化发送对象 - 供应商信息
         SBHeaderVO vendorPaymentHeaderVO = new SBHeaderVO();

         if ( vendorPaymentHeaderVOs != null && vendorPaymentHeaderVOs.size() > 0 )
         {
            vendorPaymentHeaderVO = ( SBHeaderVO ) vendorPaymentHeaderVOs.get( 0 );
         }

         // 刷新VO对象，初始化对象列表及国际化
         if ( vendorPaymentHeaderVO != null )
         {
            vendorPaymentHeaderVO.reset( null, request );
         }

         request.setAttribute( "vendorPaymentHeaderVO", vendorPaymentHeaderVO );

         // 获得SBHeader ID
         final String headerId = ( KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) ) );
         // 设置SBHeader ID
         sbHeaderVO.setHeaderId( headerId );

         // 初始化发送对象 - 社保方案信息
         SBHeaderVO tempSBHeaderVO = new SBHeaderVO();
         setDataAuth( request, response, tempSBHeaderVO );
         final List< Object > sbHeaderVOs = sbHeaderService.getVendorSBHeaderVOsByCondition( sbHeaderVO );

         // 获得供应商对应月份单条社保方案信息
         if ( sbHeaderVOs != null && sbHeaderVOs.size() > 0 )
         {
            tempSBHeaderVO = ( SBHeaderVO ) sbHeaderVOs.get( 0 );
         }
         // 刷新VO对象，初始化对象列表及国际化
         tempSBHeaderVO.reset( mapping, request );
         request.setAttribute( "sbHeaderVO", tempSBHeaderVO );

         // 如果是搜索查询更新搜索条件
         if ( ( ( SBHeaderVO ) form ).getSubAction() != null && ( ( SBHeaderVO ) form ).getSubAction().equalsIgnoreCase( SEARCH_OBJECT ) )
         {
            sbHeaderVO.update( ( SBHeaderVO ) form );

            // SBHeader ID 和 SBBatch ID 设置解码值
            sbHeaderVO.setBatchId( KANUtil.decodeString( request.getParameter( "batchId" ) ) );
            sbHeaderVO.setHeaderId( KANUtil.decodeString( request.getParameter( "headerId" ) ) );
         }

         // 国际化
         sbHeaderVO.reset( mapping, request );
         request.setAttribute( "sbHeaderForm", sbHeaderVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sbDetailHolder = new PagedListHolder();
         // 传入当前页
         sbDetailHolder.setPage( getPage( request ) );

         // 初始化SBDetailVO
         final SBDetailVO sbDetailVO = new SBDetailVO();
         sbDetailVO.setHeaderId( sbHeaderVO.getHeaderId() );
         sbDetailVO.setStatus( sbHeaderVO.getAdditionalStatus() );

         // 传入排序相关字段
         sbDetailVO.setSortColumn( request.getParameter( "sortColumn" ) );
         sbDetailVO.setSortOrder( request.getParameter( "sortOrder" ) );
         sbDetailVO.setAccountId( getAccountId( request, response ) );

         // 如果是In House登录填入Client ID
         if ( getRole( request, response ).equals( KANConstants.ROLE_IN_HOUSE ) )
         {
            sbDetailVO.setCorpId( getCorpId( request, response ) );
         }

         // 如果没有指定排序则默认按科目ID排序
         if ( sbDetailVO.getSortColumn() == null || sbDetailVO.getSortColumn().isEmpty() )
         {
            sbDetailVO.setSortColumn( "itemId" );
         }

         // 传入当前值对象
         sbDetailHolder.setObject( sbDetailVO );
         // 设置页面记录条数
         sbDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         sbDetailService.getSBDetailVOsByCondition( sbDetailHolder, true );
         refreshHolder( sbDetailHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "sbDetailHolder", sbDetailHolder );

         if ( new Boolean( getAjax( request ) ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listDetailTable" );
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      return mapping.findForward( "listDetail" );
   }

   /**  
    * Submit_Confirmation
    *	确认社保方案
    *	@param mapping
    *	@param form
    *	@param request
    *	@param response
    *	@return
    *	@throws KANException
    */
   public ActionForward submit_confirmation( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获取的 SBHeaderVO
         final SBHeaderVO sbHeaderVO = ( SBHeaderVO ) form;
         sbHeaderVO.setModifyBy( getUserId( request, response ) );

         // 初始化Service接口
         final SBHeaderService sbHeaderService = ( SBHeaderService ) getService( "sbHeaderService" );

         // 提交至批准
         sbHeaderService.submit( sbHeaderVO );

         // 重置选中项和SubAction
         ( ( SBHeaderVO ) form ).setSelectedIds( "" );
         ( ( SBHeaderVO ) form ).setSubAction( SEARCH_OBJECT );

         // “确认成功”
         success( request, null, "确认成功" );
         // 根据pageFlag 跳转
         return forward( sbHeaderVO.getPageFlag(), mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
   }

   /**  
    * Forward
    * 根据Page Flag跳转
    *
    * @param pageFlag
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   private ActionForward forward( String pageFlag, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      if ( pageFlag.equalsIgnoreCase( SBHeaderService.PAGE_FLAG_HEADER ) )
      {
         return to_vendorDetail( mapping, form, request, response );
      }
      else if ( pageFlag.equalsIgnoreCase( SBHeaderService.PAGE_FLAG_DETAIL ) )
      {
         return to_sbDetail( mapping, form, request, response );
      }
      else
      {
         return list_estimation( mapping, form, request, response );
      }
   }

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

}

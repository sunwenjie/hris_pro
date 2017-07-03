package com.kan.hro.web.actions.biz.settlement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.domain.management.TaxVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANConstants;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.settlement.BatchVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderVO;
import com.kan.hro.domain.biz.settlement.ServiceContractVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.settlement.BatchService;
import com.kan.hro.service.inf.biz.settlement.BatchTempService;
import com.kan.hro.service.inf.biz.settlement.OrderHeaderService;
import com.kan.hro.service.inf.biz.settlement.ServiceContractService;

public class SettlementContractAction extends BaseAction
{
   /**
    * to_HeaderDetail
    * 
    * 显示批次、订单相关信息及服务协议列表
    * 需要显示当前服订单的汇总数
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final BatchService batchService = ( BatchService ) getService( "batchService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderService orderHeaderService = ( OrderHeaderService ) getService( "orderHeaderService" );
         final ServiceContractService serviceContractService = ( ServiceContractService ) getService( "serviceContractService" );

         // 获得批次ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得批次对象
         final BatchVO batchVO = batchService.getBatchVOByBatchId( batchId );

         // 设置页面的PageFlag
         batchVO.setPageFlag( BatchTempService.CONTRACT );
         // 初始化对象及国际化
         batchVO.reset( null, request );
         request.setAttribute( "batchForm", batchVO );

         // 如果批次是按客户结算的，装载客户对象
         if ( batchVO.getClientId() != null && !batchVO.getClientId().trim().equals( "" ) )
         {
            final ClientVO clientVO = clientService.getClientVOByClientId( batchVO.getClientId() );
            clientVO.reset( null, request );
            request.setAttribute( "clientVO", clientVO );
         }

         // 获得订单流水ID
         final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderHeaderId" ) );
         // 获得订单流水对象
         final OrderHeaderVO orderHeaderVO = orderHeaderService.getOrderHeaderVOByOrderHeaderId( orderHeaderId );
         // 初始化对象及国际化
         orderHeaderVO.reset( null, request );
         request.setAttribute( "orderHeaderVO", orderHeaderVO );

         // 如果订单存在TaxId，装载Tax对象
         if ( orderHeaderVO.getTaxId() != null && !orderHeaderVO.getTaxId().trim().equals( "" ) )
         {
            final TaxVO taxVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxVOByTaxId( orderHeaderVO.getTaxId() );
            taxVO.reset( null, request );
            request.setAttribute( "taxVO", taxVO );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder contractHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 传入当前页
         contractHolder.setPage( page );
         // 初始化查询对象
         final ServiceContractVO serviceContractVO = ( ServiceContractVO ) form;

         if ( ajax != null && ajax.equals( "true" ) )
         {
            decodedObject( serviceContractVO );
         }
         // 设置相关属性值
         serviceContractVO.setOrderHeaderId( orderHeaderId );
         serviceContractVO.setBatchId( orderHeaderVO.getBatchId() );

         // 传入排序相关字段
         serviceContractVO.setSortColumn( request.getParameter( "sortColumn" ) );
         serviceContractVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 默认按 订单流水ID排序
         if ( serviceContractVO.getSortColumn() == null || serviceContractVO.getSortColumn().trim().equals( "" ) )
         {
            serviceContractVO.setSortColumn( "orderHeaderId" );
            serviceContractVO.setSortOrder( "desc" );
         }

         // 传入当前值对象
         contractHolder.setObject( serviceContractVO );
         // 设置页面记录条数
         contractHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         serviceContractVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         serviceContractService.getServiceContractVOsByCondition( contractHolder, true );
         refreshHolder( contractHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "contractHolder", contractHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listContractTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "listContract" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // no use

   }

}

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
import com.kan.hro.domain.biz.settlement.BatchTempVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderTempVO;
import com.kan.hro.domain.biz.settlement.ServiceContractTempVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.settlement.BatchTempService;
import com.kan.hro.service.inf.biz.settlement.OrderHeaderTempService;
import com.kan.hro.service.inf.biz.settlement.ServiceContractTempService;

public class SettlementContractTempAction extends BaseAction
{

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderTempService orderHeaderTempService = ( OrderHeaderTempService ) getService( "orderHeaderTempService" );
         final ServiceContractTempService serviceContractTempService = ( ServiceContractTempService ) getService( "serviceContractTempService" );

         // 获得批次ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得批次对象
         final BatchTempVO batchTempVO = batchTempService.getBatchTempVOByBatchId( batchId );

         if ( batchTempVO == null )
         {
            BatchTempVO batchTempVOs = new BatchTempVO();
            batchTempVOs.reset();
            return new SettlementTempAction().to_batchDetail( mapping, batchTempVOs, request, response );
         }
         // 设置页面的PageFlag
         batchTempVO.setPageFlag( BatchTempService.CONTRACT );
         // 初始化对象及国际化
         batchTempVO.reset( null, request );
         request.setAttribute( "batchTempForm", batchTempVO );

         // 如果批次是按客户结算的，装载客户对象
         if ( batchTempVO.getClientId() != null && !batchTempVO.getClientId().trim().equals( "" ) )
         {
            final ClientVO clientVO = clientService.getClientVOByClientId( batchTempVO.getClientId() );
            clientVO.reset( null, request );
            request.setAttribute( "clientVO", clientVO );
         }

         // 获得订单流水ID
         final String orderHeaderId = KANUtil.decodeStringFromAjax( request.getParameter( "orderHeaderId" ) );
         // 获得订单流水对象
         final OrderHeaderTempVO orderHeaderTempVO = orderHeaderTempService.getOrderHeaderTempVOByOrderHeaderId( orderHeaderId );

         if ( orderHeaderTempVO != null )
         {
            // 初始化对象及国际化
            orderHeaderTempVO.reset( null, request );
            request.setAttribute( "orderHeaderTempVO", orderHeaderTempVO );
            // 如果订单存在TaxId，装载Tax对象
            if ( orderHeaderTempVO.getTaxId() != null && !orderHeaderTempVO.getTaxId().trim().equals( "" ) )
            {
               final TaxVO taxVO = KANConstants.getKANAccountConstants( getAccountId( request, response ) ).getTaxVOByTaxId( orderHeaderTempVO.getTaxId() );
               taxVO.reset( null, request );
               request.setAttribute( "taxVO", taxVO );
            }
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder contractTempHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 传入当前页
         contractTempHolder.setPage( page );
         // 初始化查询对象
         final ServiceContractTempVO serviceContractTempVO = ( ServiceContractTempVO ) form;

         if ( ajax != null && ajax.equals( "true" ) )
         {
            decodedObject( serviceContractTempVO );
         }
         // 设置相关属性值
         serviceContractTempVO.setOrderHeaderId( orderHeaderId );
         serviceContractTempVO.setBatchId( orderHeaderTempVO.getBatchId() );
         // 传入排序相关字段
         serviceContractTempVO.setSortColumn( request.getParameter( "sortColumn" ) );
         serviceContractTempVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 默认按 订单流水ID排序
         if ( serviceContractTempVO.getSortColumn() == null || serviceContractTempVO.getSortColumn().trim().equals( "" ) )
         {
            serviceContractTempVO.setSortColumn( "orderHeaderId" );
            serviceContractTempVO.setSortOrder( "desc" );
         }

         // 传入当前值对象
         contractTempHolder.setObject( serviceContractTempVO );
         // 设置页面记录条数
         contractTempHolder.setPageSize( listPageSize );
         // 刷新Holder，国际化传值
         serviceContractTempVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         serviceContractTempService.getServiceContractTempVOsByCondition( contractTempHolder, true );
         refreshHolder( contractTempHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "contractTempHolder", contractTempHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listContractTempTable" );
         }

         if ( contractTempHolder == null || contractTempHolder.getHolderSize() == 0 )
         {
            OrderHeaderTempVO orderHeaderTemp = new OrderHeaderTempVO();
            orderHeaderTemp.reset();
            orderHeaderTemp.setBatchId( "" );
            return new SettlementHeaderTempAction().list_object( mapping, orderHeaderTemp, request, response );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "listContractTemp" );
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

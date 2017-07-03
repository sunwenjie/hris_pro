package com.kan.hro.web.actions.biz.settlement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.client.ClientVO;
import com.kan.hro.domain.biz.settlement.BatchVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.settlement.BatchService;
import com.kan.hro.service.inf.biz.settlement.BatchTempService;
import com.kan.hro.service.inf.biz.settlement.OrderHeaderService;

public class SettlementHeaderAction extends BaseAction
{
   public final static String accessAction = "HRO_SETTLE_ORDER_BATCH";

   /**
    * To Batch Detail
    * 
    * 显示批次及订单列表
    * 需要显示当前批次的汇总数
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

         // 获得当前主键（批次信息）
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得当前主键对象
         final BatchVO batchVO = batchService.getBatchVOByBatchId( batchId );

         // 设置页面的PageFlag
         batchVO.setPageFlag( BatchTempService.HEADER );

         // 刷新VO对象，初始化对象列表及国际化
         batchVO.reset( null, request );
         request.setAttribute( "batchForm", batchVO );

         // 如果批次是按客户结算的，装载客户对象
         if ( batchVO.getClientId() != null && !batchVO.getClientId().trim().equals( "" ) )
         {
            final ClientVO clientVO = clientService.getClientVOByClientId( batchVO.getClientId() );
            clientVO.reset( null, request );
            request.setAttribute( "clientVO", clientVO );
         }

         /**
          * 获取订单列表
          */
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder headerListHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 传入当前页
         headerListHolder.setPage( page );
         // 初始化查询对象
         OrderHeaderVO orderHeaderVO = ( OrderHeaderVO ) form;
         //处理数据权限
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), SettlementAction.accessAction, orderHeaderVO );

         if ( ajax != null && ajax.equals( "true" ) )
         {
            decodedObject( orderHeaderVO );
         }
         // 设置相关属性值
         orderHeaderVO.setBatchId( batchId );
         orderHeaderVO.setCorpId( getCorpId( request, response ) );
         orderHeaderVO.setAccountId( getAccountId( request, response ) );
         // 传入排序相关字段
         orderHeaderVO.setSortColumn( request.getParameter( "sortColumn" ) );
         orderHeaderVO.setSortOrder( request.getParameter( "sortOrder" ) );

         // 默认按 BatchId排序
         if ( orderHeaderVO.getSortColumn() == null || orderHeaderVO.getSortColumn().trim().equals( "" ) )
         {
            orderHeaderVO.setSortColumn( "batchId" );
         }

         // 传入当前值对象
         headerListHolder.setObject( orderHeaderVO );
         // 设置页面记录条数
         headerListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         orderHeaderService.getOrderHeaderVOsByCondition( headerListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( headerListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "headerListHolder", headerListHolder );
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "listHeader" );
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

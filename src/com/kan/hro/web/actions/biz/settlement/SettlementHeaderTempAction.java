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
import com.kan.hro.domain.biz.settlement.BatchTempVO;
import com.kan.hro.domain.biz.settlement.OrderHeaderTempVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.settlement.BatchTempService;
import com.kan.hro.service.inf.biz.settlement.OrderHeaderTempService;

public class SettlementHeaderTempAction extends BaseAction
{
    public final static String accessAction = "HRO_SETTLE_ORDER_BATCH_TEMP";
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
         final BatchTempService batchTempService = ( BatchTempService ) getService( "batchTempService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         final OrderHeaderTempService orderHeaderTempService = ( OrderHeaderTempService ) getService( "orderHeaderTempService" );

         // 获得当前主键（批次信息）
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得当前主键对象
         final BatchTempVO batchTempVO = batchTempService.getBatchTempVOByBatchId( batchId );

         if ( batchTempVO != null )
         {
            // 设置页面的PageFlag
            batchTempVO.setPageFlag( BatchTempService.HEADER );
            // 刷新VO对象，初始化对象列表及国际化
            batchTempVO.reset( null, request );
            request.setAttribute( "batchTempForm", batchTempVO );

            // 如果批次是按客户结算的，装载客户对象
            if ( batchTempVO.getClientId() != null && !batchTempVO.getClientId().trim().equals( "" ) )
            {
               final ClientVO clientVO = clientService.getClientVOByClientId( batchTempVO.getClientId() );
               clientVO.reset( null, request );
               request.setAttribute( "clientVO", clientVO );
            }
         }

         /**
          * 获取订单列表
          */
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder headerTempListHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         // 传入当前页
         headerTempListHolder.setPage( page );
         // 初始化查询对象
         OrderHeaderTempVO orderHeaderTempVO = ( OrderHeaderTempVO ) form;
         
         //处理数据权限
         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), SettlementTempAction.accessAction, orderHeaderTempVO );
         
         if(ajax!=null&&ajax.equals( "true" )){
            decodedObject( orderHeaderTempVO );
         }
         // 设置相关属性值
         orderHeaderTempVO.setBatchId( batchId );
         orderHeaderTempVO.setCorpId( getCorpId( request, response ) );
         orderHeaderTempVO.setAccountId( getAccountId( request, response ) );
         // 传入排序相关字段
         orderHeaderTempVO.setSortColumn( request.getParameter( "sortColumn" ) );
         orderHeaderTempVO.setSortOrder( request.getParameter( "sortOrder" ) );
       
         // 默认按 BatchId排序
         if ( orderHeaderTempVO.getSortColumn() == null || orderHeaderTempVO.getSortColumn().trim().equals( "" ) )
         {
            orderHeaderTempVO.setSortColumn( "batchId" );
         }

         // 传入当前值对象
         headerTempListHolder.setObject( orderHeaderTempVO );
         // 设置页面记录条数
         headerTempListHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         orderHeaderTempService.getOrderHeaderTempVOsByCondition( headerTempListHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( headerTempListHolder, request );

         // Holder需写入Request对象
         request.setAttribute( "headerTempListHolder", headerTempListHolder );

         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listHeaderTempTable" );
         }

         final String boolSearchHeader=request.getParameter( "searchHeader" );

         if ( headerTempListHolder == null || headerTempListHolder.getHolderSize() == 0 )
         {
            if ( boolSearchHeader == null || !boolSearchHeader.equals( "true" ) )
            {
               BatchTempVO batchTemp = new BatchTempVO();
               batchTemp.reset( null, request );
               batchTemp.setBatchId( "" );
               request.setAttribute( "messageInfo", true );
               return new SettlementTempAction().list_estimation( mapping, batchTemp, request, response );
            }
         }

      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // Ajax调用
      return mapping.findForward( "listHeaderTemp" );
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

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
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportDetailVO;
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportHeaderVO;
import com.kan.hro.service.inf.biz.settlement.SettlementAdjustmentImportDetailService;
import com.kan.hro.service.inf.biz.settlement.SettlementAdjustmentImportHeaderService;

public class SettlementAdjustmentImportDetailAction extends BaseAction
{

   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final SettlementAdjustmentImportDetailService settlementAdjustmentImportDetailService = ( SettlementAdjustmentImportDetailService ) getService( "settlementAdjustmentImportDetailService" );
         final SettlementAdjustmentImportHeaderService settlementAdjustmentImportHeaderService = ( SettlementAdjustmentImportHeaderService ) getService( "settlementAdjustmentImportHeaderService" );
         // 获得Action Form
         final SettlementAdjustmentImportDetailVO settlementAdjustmentImportDetailVO = ( SettlementAdjustmentImportDetailVO ) form;
         settlementAdjustmentImportDetailVO.setAdjustmentHeaderId( KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) ) );

         // 如果没有指定排序则默认按 adjustmentHeaderId排序
         if ( settlementAdjustmentImportDetailVO.getSortColumn() == null || settlementAdjustmentImportDetailVO.getSortColumn().isEmpty() )
         {
            settlementAdjustmentImportDetailVO.setSortColumn( "a.adjustmentDetailId" );
            settlementAdjustmentImportDetailVO.setSortOrder( "desc" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder settlementAdjustmentImprotDetailHolder = new PagedListHolder();
         // 传入当前页
         settlementAdjustmentImprotDetailHolder.setPage( page );
         // 传入当前值对象
         settlementAdjustmentImprotDetailHolder.setObject( settlementAdjustmentImportDetailVO );
         // 设置页面记录条数
         settlementAdjustmentImprotDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         settlementAdjustmentImportDetailService.getSettlementAdjustmentImportDetailVOsByCondition( settlementAdjustmentImprotDetailHolder, true );
         refreshHolder( settlementAdjustmentImprotDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "settlementAdjustmentImportDetailHolder", settlementAdjustmentImprotDetailHolder );
         SettlementAdjustmentImportHeaderVO settlementAdjustmentImportHeaderVO = settlementAdjustmentImportHeaderService.getSettlementAdjustmentImportHeaderVOsById( KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) ) ,getAccountId( request, response ));
         settlementAdjustmentImportHeaderVO.reset( mapping, request );
         request.setAttribute( "settlementAdjustmentImportHeaderForm", settlementAdjustmentImportHeaderVO );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listSettlementAdjustmentImportDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listSettlementAdjustmentImportDetail" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {

   }

}

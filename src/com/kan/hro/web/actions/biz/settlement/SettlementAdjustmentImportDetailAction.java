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
         // ��õ�ǰҳ
         final String page = request.getParameter( "page" );

         // ����Ƿ�Ajax����
         final String ajax = request.getParameter( "ajax" );

         // ��ʼ��Service�ӿ�
         final SettlementAdjustmentImportDetailService settlementAdjustmentImportDetailService = ( SettlementAdjustmentImportDetailService ) getService( "settlementAdjustmentImportDetailService" );
         final SettlementAdjustmentImportHeaderService settlementAdjustmentImportHeaderService = ( SettlementAdjustmentImportHeaderService ) getService( "settlementAdjustmentImportHeaderService" );
         // ���Action Form
         final SettlementAdjustmentImportDetailVO settlementAdjustmentImportDetailVO = ( SettlementAdjustmentImportDetailVO ) form;
         settlementAdjustmentImportDetailVO.setAdjustmentHeaderId( KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) ) );

         // ���û��ָ��������Ĭ�ϰ� adjustmentHeaderId����
         if ( settlementAdjustmentImportDetailVO.getSortColumn() == null || settlementAdjustmentImportDetailVO.getSortColumn().isEmpty() )
         {
            settlementAdjustmentImportDetailVO.setSortColumn( "a.adjustmentDetailId" );
            settlementAdjustmentImportDetailVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder settlementAdjustmentImprotDetailHolder = new PagedListHolder();
         // ���뵱ǰҳ
         settlementAdjustmentImprotDetailHolder.setPage( page );
         // ���뵱ǰֵ����
         settlementAdjustmentImprotDetailHolder.setObject( settlementAdjustmentImportDetailVO );
         // ����ҳ���¼����
         settlementAdjustmentImprotDetailHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         settlementAdjustmentImportDetailService.getSettlementAdjustmentImportDetailVOsByCondition( settlementAdjustmentImprotDetailHolder, true );
         refreshHolder( settlementAdjustmentImprotDetailHolder, request );
         // Holder��д��Request����
         request.setAttribute( "settlementAdjustmentImportDetailHolder", settlementAdjustmentImprotDetailHolder );
         SettlementAdjustmentImportHeaderVO settlementAdjustmentImportHeaderVO = settlementAdjustmentImportHeaderService.getSettlementAdjustmentImportHeaderVOsById( KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) ) ,getAccountId( request, response ));
         settlementAdjustmentImportHeaderVO.reset( mapping, request );
         request.setAttribute( "settlementAdjustmentImportHeaderForm", settlementAdjustmentImportHeaderVO );
         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listSettlementAdjustmentImportDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
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

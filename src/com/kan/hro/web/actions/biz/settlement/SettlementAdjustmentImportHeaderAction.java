package com.kan.hro.web.actions.biz.settlement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportBatchVO;
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportHeaderVO;
import com.kan.hro.service.inf.biz.settlement.SettlementAdjustmentImportBatchService;
import com.kan.hro.service.inf.biz.settlement.SettlementAdjustmentImportHeaderService;

public class SettlementAdjustmentImportHeaderAction extends BaseAction
{
   public static String accessAction = "HRO_SETTLE_ADJUSTMENT_HEADER";

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
         final SettlementAdjustmentImportHeaderService settlementAdjustmentImportHeaderService = ( SettlementAdjustmentImportHeaderService ) getService( "settlementAdjustmentImportHeaderService" );
         final SettlementAdjustmentImportBatchService settlementAdjustmentImportBatchService = ( SettlementAdjustmentImportBatchService ) getService( "settlementAdjustmentImportBatchService" );

         // ���Action Form
         final SettlementAdjustmentImportHeaderVO settlementAdjustmentImportHeaderVO = ( SettlementAdjustmentImportHeaderVO ) form;
         settlementAdjustmentImportHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) ) );

         // ���û��ָ��������Ĭ�ϰ� adjustmentHeaderId����
         if ( settlementAdjustmentImportHeaderVO.getSortColumn() == null || settlementAdjustmentImportHeaderVO.getSortColumn().isEmpty() )
         {
            settlementAdjustmentImportHeaderVO.setSortColumn( "a.adjustmentHeaderId" );
            settlementAdjustmentImportHeaderVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder settlementAdjustmentImprotBatchHolder = new PagedListHolder();
         // ���뵱ǰҳ
         settlementAdjustmentImprotBatchHolder.setPage( page );
         // ���뵱ǰֵ����
         settlementAdjustmentImprotBatchHolder.setObject( settlementAdjustmentImportHeaderVO );
         // ����ҳ���¼����
         settlementAdjustmentImprotBatchHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         settlementAdjustmentImportHeaderService.getSettlementAdjustmentImportHeaderVOsByCondition( settlementAdjustmentImprotBatchHolder, true );
         refreshHolder( settlementAdjustmentImprotBatchHolder, request );
         // Holder��д��Request����
         request.setAttribute( "settlementAdjustmentImportHeaderHolder", settlementAdjustmentImprotBatchHolder );

         SettlementAdjustmentImportBatchVO settlementAdjustmentImportBatchVO = settlementAdjustmentImportBatchService.getSettlementAdjustmentImportBatchById( KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) ) );
         request.setAttribute( "settlementAdjustmentImportBatchVO", settlementAdjustmentImportBatchVO );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listSettlementAdjustmentImportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listSettlementAdjustmentImportHeader" );
   }

   public ActionForward backUpRecord( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      final SettlementAdjustmentImportHeaderService settlementAdjustmentImportHeaderService = ( SettlementAdjustmentImportHeaderService ) getService( "settlementAdjustmentImportHeaderService" );
      String idsStr = request.getParameter( "selectedIds" );
      String[] ids = null;
      if(StringUtils.isNotBlank( idsStr)){
         ids= idsStr.split(",");
      }
      
      String batchId = request.getParameter( "batchId" );
      int count = settlementAdjustmentImportHeaderService.backUpRecord( ids, KANUtil.decodeStringFromAjax( batchId ) );
      if ( count == 0 )
      {
         SettlementAdjustmentImportBatchVO settlementAdjustmentImportBatchVO = new SettlementAdjustmentImportBatchVO();
         settlementAdjustmentImportBatchVO.reset( mapping, request );
         settlementAdjustmentImportBatchVO.setBatchId( batchId );
         return new SettlementAdjustmentImportBatchAction().list_object( mapping, settlementAdjustmentImportBatchVO, request, response );
      }
      else
      {
         final SettlementAdjustmentImportHeaderVO settlementAdjustmentImportHeaderVO = ( SettlementAdjustmentImportHeaderVO ) form;
         settlementAdjustmentImportHeaderVO.setBatchId( batchId );
         return list_object( mapping, settlementAdjustmentImportHeaderVO, request, response );
      }
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

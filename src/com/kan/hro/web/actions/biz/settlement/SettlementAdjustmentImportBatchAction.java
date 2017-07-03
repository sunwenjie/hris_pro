package com.kan.hro.web.actions.biz.settlement;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.settlement.SettlementAdjustmentImportBatchVO;
import com.kan.hro.service.inf.biz.settlement.SettlementAdjustmentImportBatchService;

public class SettlementAdjustmentImportBatchAction extends BaseAction
{
   public static String accessAction = "HRO_SETTLEMENT_ADJUSTMENT_IMPORT_BATCH";

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
         final SettlementAdjustmentImportBatchService settlementAdjustmentImportBatchService = ( SettlementAdjustmentImportBatchService ) getService( "settlementAdjustmentImportBatchService" );

         // ���Action Form
         final SettlementAdjustmentImportBatchVO settlementAdjustmentImportBatchVO = ( SettlementAdjustmentImportBatchVO ) form;

         // ���û��ָ��������Ĭ�ϰ� adjustmentHeaderId����
         if ( settlementAdjustmentImportBatchVO.getSortColumn() == null || settlementAdjustmentImportBatchVO.getSortColumn().isEmpty() )
         {
            settlementAdjustmentImportBatchVO.setSortColumn( "a.batchId" );
            settlementAdjustmentImportBatchVO.setSortOrder( "desc" );
         }

         //��������Ȩ��
         //         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, settlementAdjustmentImportBatchVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder settlementAdjustmentImprotBatchHolder = new PagedListHolder();
         // ���뵱ǰҳ
         settlementAdjustmentImprotBatchHolder.setPage( page );
         // ���뵱ǰֵ����
         settlementAdjustmentImprotBatchHolder.setObject( settlementAdjustmentImportBatchVO );
         // ����ҳ���¼����
         settlementAdjustmentImprotBatchHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         settlementAdjustmentImportBatchService.getSettlementAdjustmentImportBatchVOsByCondition( settlementAdjustmentImprotBatchHolder, true );
         refreshHolder( settlementAdjustmentImprotBatchHolder, request );
         // Holder��д��Request����
         request.setAttribute( "settlementAdjustmentImportBatchHolder", settlementAdjustmentImprotBatchHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listSettlementAdjustmentImportBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listSettlementAdjustmentImportBatch" );
   }

   public ActionForward submit_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final SettlementAdjustmentImportBatchVO settlementAdjustmentImportBatchVO = ( SettlementAdjustmentImportBatchVO ) form;
         settlementAdjustmentImportBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final SettlementAdjustmentImportBatchService settlementAdjustmentImportBatchService = ( SettlementAdjustmentImportBatchService ) getService( "settlementAdjustmentImportBatchService" );

         // ��ù�ѡID
         final String batchIds = settlementAdjustmentImportBatchVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( String encodedSelectId : selectedIdArray )
            {
               final SettlementAdjustmentImportBatchVO submitObject = settlementAdjustmentImportBatchService.getSettlementAdjustmentImportBatchById( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );
               submitObject.setModifyDate( new Date() );
               rows = rows + settlementAdjustmentImportBatchService.updateSettlementAdjustmentImportBatch( submitObject );
            }

            if ( rows == 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE );
            }

         }

         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

   }

   /**
    * �˻�,����ɾ��temp��
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws KANException
    */
   public ActionForward back_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final SettlementAdjustmentImportBatchVO settlementAdjustmentImportBatchVO = ( SettlementAdjustmentImportBatchVO ) form;
         settlementAdjustmentImportBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final SettlementAdjustmentImportBatchService settlementAdjustmentImportBatchService = ( SettlementAdjustmentImportBatchService ) getService( "settlementAdjustmentImportBatchService" );

         // ��ù�ѡID
         final String batchIds = settlementAdjustmentImportBatchVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( String encodedSelectId : selectedIdArray )
            {
               final SettlementAdjustmentImportBatchVO submitObject = settlementAdjustmentImportBatchService.getSettlementAdjustmentImportBatchById( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );

               rows = rows + settlementAdjustmentImportBatchService.backBatch( submitObject );
            }

            if ( rows < 0 )
            {
               success( request, MESSAGE_TYPE_SUBMIT );
            }
            else
            {
               success( request, MESSAGE_TYPE_UPDATE, "�˻سɹ�!" );
            }
         }
         return list_object( mapping, form, request, response );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
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

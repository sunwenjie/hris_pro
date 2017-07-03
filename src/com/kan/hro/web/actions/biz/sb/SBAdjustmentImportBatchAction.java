package com.kan.hro.web.actions.biz.sb;

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
import com.kan.hro.domain.biz.sb.SBAdjustmentImportBatchVO;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentImportBatchService;

public class SBAdjustmentImportBatchAction extends BaseAction
{
   public static String accessAction = "HRO_SB_ADJUSTMENT_IMPORT_BATCH";

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
         final SBAdjustmentImportBatchService sbAdjustmentImportBatchService = ( SBAdjustmentImportBatchService ) getService( "sbAdjustmentImportBatchService" );

         // ���Action Form
         final SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO = ( SBAdjustmentImportBatchVO ) form;

         // ���û��ָ��������Ĭ�ϰ� adjustmentHeaderId����
         if ( sbAdjustmentImportBatchVO.getSortColumn() == null || sbAdjustmentImportBatchVO.getSortColumn().isEmpty() )
         {
            sbAdjustmentImportBatchVO.setSortColumn( "a.batchId" );
            sbAdjustmentImportBatchVO.setSortOrder( "desc" );
         }

         //��������Ȩ��
         //         setAuthPositionIds( BaseAction.getAccountId( request, response ), BaseAction.getUserVOFromClient( request, response ), accessAction, sbAdjustmentImportBatchVO );

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbAdjustmentImprotBatchHolder = new PagedListHolder();
         // ���뵱ǰҳ
         sbAdjustmentImprotBatchHolder.setPage( page );
         // ���뵱ǰֵ����
         sbAdjustmentImprotBatchHolder.setObject( sbAdjustmentImportBatchVO );
         // ����ҳ���¼����
         sbAdjustmentImprotBatchHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbAdjustmentImportBatchService.getSBAdjustmentImportBatchVOsByCondition( sbAdjustmentImprotBatchHolder, true );
         refreshHolder( sbAdjustmentImprotBatchHolder, request );
         // Holder��д��Request����
         request.setAttribute( "sbAdjustmentImportBatchHolder", sbAdjustmentImprotBatchHolder );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listSBAdjustmentImportBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listSBAdjustmentImportBatch" );
   }

   public ActionForward submit_batch( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // ��ȡActionForm
         final SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO = ( SBAdjustmentImportBatchVO ) form;
         sbAdjustmentImportBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final SBAdjustmentImportBatchService sbAdjustmentImportBatchService = ( SBAdjustmentImportBatchService ) getService( "sbAdjustmentImportBatchService" );

         // ��ù�ѡID
         final String batchIds = sbAdjustmentImportBatchVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( String encodedSelectId : selectedIdArray )
            {
               final SBAdjustmentImportBatchVO submitObject = sbAdjustmentImportBatchService.getSBAdjustmentImportBatchById( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );
               submitObject.setModifyDate( new Date() );
               rows = rows + sbAdjustmentImportBatchService.updateSBAdjustmentImportBatch( submitObject );
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
         final SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO = ( SBAdjustmentImportBatchVO ) form;
         sbAdjustmentImportBatchVO.setModifyBy( getUserId( request, response ) );

         // ��ʼ��Service�ӿ�
         final SBAdjustmentImportBatchService sbAdjustmentImportBatchService = ( SBAdjustmentImportBatchService ) getService( "sbAdjustmentImportBatchService" );

         // ��ù�ѡID
         final String batchIds = sbAdjustmentImportBatchVO.getSelectedIds();

         // ���ڹ�ѡID
         if ( KANUtil.filterEmpty( batchIds ) != null )
         {
            // �ָ�ѡ����
            final String[] selectedIdArray = batchIds.split( "," );

            int rows = 0;
            // ����selectedIds �����޸�
            for ( String encodedSelectId : selectedIdArray )
            {
               final SBAdjustmentImportBatchVO submitObject = sbAdjustmentImportBatchService.getSBAdjustmentImportBatchById( KANUtil.decodeStringFromAjax( encodedSelectId ) );
               submitObject.reset( null, request );
               submitObject.setModifyBy( getUserId( request, null ) );

               rows = rows + sbAdjustmentImportBatchService.backBatch( submitObject );
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

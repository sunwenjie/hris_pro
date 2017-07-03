package com.kan.hro.web.actions.biz.sb;

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
import com.kan.hro.domain.biz.sb.SBAdjustmentImportBatchVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportHeaderVO;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentImportBatchService;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentImportHeaderService;

public class SBAdjustmentImportHeaderAction extends BaseAction
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
         final SBAdjustmentImportHeaderService sbAdjustmentImportHeaderService = ( SBAdjustmentImportHeaderService ) getService( "sbAdjustmentImportHeaderService" );
         final SBAdjustmentImportBatchService sbAdjustmentImportBatchService = ( SBAdjustmentImportBatchService ) getService( "sbAdjustmentImportBatchService" );

         // ���Action Form
         final SBAdjustmentImportHeaderVO sbAdjustmentImportHeaderVO = ( SBAdjustmentImportHeaderVO ) form;
         sbAdjustmentImportHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) ) );

         // ���û��ָ��������Ĭ�ϰ� adjustmentHeaderId����
         if ( sbAdjustmentImportHeaderVO.getSortColumn() == null || sbAdjustmentImportHeaderVO.getSortColumn().isEmpty() )
         {
            sbAdjustmentImportHeaderVO.setSortColumn( "a.adjustmentHeaderId" );
            sbAdjustmentImportHeaderVO.setSortOrder( "desc" );
         }

         // ��ʼ��PagedListHolder���������÷�ʽ����Service
         final PagedListHolder sbAdjustmentImprotBatchHolder = new PagedListHolder();
         // ���뵱ǰҳ
         sbAdjustmentImprotBatchHolder.setPage( page );
         // ���뵱ǰֵ����
         sbAdjustmentImprotBatchHolder.setObject( sbAdjustmentImportHeaderVO );
         // ����ҳ���¼����
         sbAdjustmentImprotBatchHolder.setPageSize( listPageSize );
         // ����Service���������ö��󷵻أ��ڶ�������˵���Ƿ��ҳ
         sbAdjustmentImportHeaderService.getSBAdjustmentImportHeaderVOsByCondition( sbAdjustmentImprotBatchHolder, true );
         refreshHolder( sbAdjustmentImprotBatchHolder, request );
         // Holder��д��Request����
         request.setAttribute( "sbAdjustmentImportHeaderHolder", sbAdjustmentImprotBatchHolder );

         SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO = sbAdjustmentImportBatchService.getSBAdjustmentImportBatchById( KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) ) );
         request.setAttribute( "sbAdjustmentImportBatchVO", sbAdjustmentImportBatchVO );

         // Ajax����
         if ( new Boolean( ajax ) )
         {
            // д��Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listSBAdjustmentImportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // ��תJSPҳ��
      return mapping.findForward( "listSBAdjustmentImportHeader" );
   }

   public ActionForward backUpRecord( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      final SBAdjustmentImportHeaderService sbAdjustmentImportHeaderService = ( SBAdjustmentImportHeaderService ) getService( "sbAdjustmentImportHeaderService" );
      String ids = request.getParameter( "selectedIds" );
      if ( StringUtils.isNotEmpty( ids ) )
      {
         String batchId = request.getParameter( "batchId" );
         int count = sbAdjustmentImportHeaderService.backUpRecord( ids.split( "," ), KANUtil.decodeStringFromAjax( batchId ) );
         if ( count == 0 )
         {
            SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO = new SBAdjustmentImportBatchVO();
            sbAdjustmentImportBatchVO.reset( mapping, request );
            return new SBAdjustmentImportBatchAction().list_object( mapping, sbAdjustmentImportBatchVO, request, response );
         }
         else
         {
            final SBAdjustmentImportHeaderVO sbAdjustmentImportHeaderVO = ( SBAdjustmentImportHeaderVO ) form;
            sbAdjustmentImportHeaderVO.setBatchId( batchId );
            sbAdjustmentImportHeaderVO.setSelectedIds( "" );
            return list_object( mapping, sbAdjustmentImportHeaderVO, request, response );
         }
      }
      else
      {
         SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO = new SBAdjustmentImportBatchVO();
         sbAdjustmentImportBatchVO.reset( mapping, request );
         return new SBAdjustmentImportBatchAction().list_object( mapping, sbAdjustmentImportBatchVO, request, response );
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

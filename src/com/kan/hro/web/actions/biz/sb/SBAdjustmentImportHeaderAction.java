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
         // 获得当前页
         final String page = request.getParameter( "page" );

         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );

         // 初始化Service接口
         final SBAdjustmentImportHeaderService sbAdjustmentImportHeaderService = ( SBAdjustmentImportHeaderService ) getService( "sbAdjustmentImportHeaderService" );
         final SBAdjustmentImportBatchService sbAdjustmentImportBatchService = ( SBAdjustmentImportBatchService ) getService( "sbAdjustmentImportBatchService" );

         // 获得Action Form
         final SBAdjustmentImportHeaderVO sbAdjustmentImportHeaderVO = ( SBAdjustmentImportHeaderVO ) form;
         sbAdjustmentImportHeaderVO.setBatchId( KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) ) );

         // 如果没有指定排序则默认按 adjustmentHeaderId排序
         if ( sbAdjustmentImportHeaderVO.getSortColumn() == null || sbAdjustmentImportHeaderVO.getSortColumn().isEmpty() )
         {
            sbAdjustmentImportHeaderVO.setSortColumn( "a.adjustmentHeaderId" );
            sbAdjustmentImportHeaderVO.setSortOrder( "desc" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sbAdjustmentImprotBatchHolder = new PagedListHolder();
         // 传入当前页
         sbAdjustmentImprotBatchHolder.setPage( page );
         // 传入当前值对象
         sbAdjustmentImprotBatchHolder.setObject( sbAdjustmentImportHeaderVO );
         // 设置页面记录条数
         sbAdjustmentImprotBatchHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         sbAdjustmentImportHeaderService.getSBAdjustmentImportHeaderVOsByCondition( sbAdjustmentImprotBatchHolder, true );
         refreshHolder( sbAdjustmentImprotBatchHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "sbAdjustmentImportHeaderHolder", sbAdjustmentImprotBatchHolder );

         SBAdjustmentImportBatchVO sbAdjustmentImportBatchVO = sbAdjustmentImportBatchService.getSBAdjustmentImportBatchById( KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) ) );
         request.setAttribute( "sbAdjustmentImportBatchVO", sbAdjustmentImportBatchVO );

         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listSBAdjustmentImportHeaderTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
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

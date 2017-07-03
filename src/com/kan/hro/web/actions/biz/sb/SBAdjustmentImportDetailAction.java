package com.kan.hro.web.actions.biz.sb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.util.KANUtil;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportDetailVO;
import com.kan.hro.domain.biz.sb.SBAdjustmentImportHeaderVO;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentImportDetailService;
import com.kan.hro.service.inf.biz.sb.SBAdjustmentImportHeaderService;

public class SBAdjustmentImportDetailAction extends BaseAction
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
         final SBAdjustmentImportDetailService sbAdjustmentImportDetailService = ( SBAdjustmentImportDetailService ) getService( "sbAdjustmentImportDetailService" );
         final SBAdjustmentImportHeaderService sbAdjustmentImportHeaderService = ( SBAdjustmentImportHeaderService ) getService( "sbAdjustmentImportHeaderService" );
         // 获得Action Form
         final SBAdjustmentImportDetailVO sbAdjustmentImportDetailVO = ( SBAdjustmentImportDetailVO ) form;
         sbAdjustmentImportDetailVO.setAdjustmentHeaderId( KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) ) );

         // 如果没有指定排序则默认按 adjustmentHeaderId排序
         if ( sbAdjustmentImportDetailVO.getSortColumn() == null || sbAdjustmentImportDetailVO.getSortColumn().isEmpty() )
         {
            sbAdjustmentImportDetailVO.setSortColumn( "a.adjustmentDetailId" );
            sbAdjustmentImportDetailVO.setSortOrder( "desc" );
         }

         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder sbAdjustmentImprotDetailHolder = new PagedListHolder();
         // 传入当前页
         sbAdjustmentImprotDetailHolder.setPage( page );
         // 传入当前值对象
         sbAdjustmentImprotDetailHolder.setObject( sbAdjustmentImportDetailVO );
         // 设置页面记录条数
         sbAdjustmentImprotDetailHolder.setPageSize( listPageSize );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         sbAdjustmentImportDetailService.getSBAdjustmentImportDetailVOsByCondition( sbAdjustmentImprotDetailHolder, true );
         refreshHolder( sbAdjustmentImprotDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "sbAdjustmentImportDetailHolder", sbAdjustmentImprotDetailHolder );
         SBAdjustmentImportHeaderVO sbAdjustmentImportHeaderVO = sbAdjustmentImportHeaderService.getSBAdjustmentImportHeaderVOsById( KANUtil.decodeStringFromAjax( request.getParameter( "headerId" ) ) ,getAccountId( request, response ));
         sbAdjustmentImportHeaderVO.reset( mapping, request );
         request.setAttribute( "sbAdjustmentImportHeaderForm", sbAdjustmentImportHeaderVO );
         // Ajax调用
         if ( new Boolean( ajax ) )
         {
            // 写入Role
            request.setAttribute( "role", getRole( request, response ) );
            return mapping.findForward( "listSBAdjustmentImportDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }

      // 跳转JSP页面
      return mapping.findForward( "listSBAdjustmentImportDetail" );
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

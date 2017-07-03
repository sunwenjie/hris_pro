package com.kan.hro.web.actions.biz.finance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.base.web.action.BaseAction;
import com.kan.hro.domain.biz.finance.SystemInvoiceBatchVO;
import com.kan.hro.service.inf.biz.finance.SystemInvoiceBatchService;


public class SystemInvoiceAction extends BaseAction
{
   /**
    * 显示系统发票批次列表
    */
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         
         final String pageFlag=request.getParameter( "pageFlag" );
         // 初始化Service接口
         final SystemInvoiceBatchService systemInvoiceBatchService = ( SystemInvoiceBatchService ) getService( "systemInvoiceBatchService" );

         // 获得Action Form
         final SystemInvoiceBatchVO systemInvoiceBatchVO = ( SystemInvoiceBatchVO ) form;
         // 需要设置当前用户AccountId
         systemInvoiceBatchVO.setAccountId( getAccountId( request, response ) );
         systemInvoiceBatchVO.setCorpId( getCorpId( request, response ) );
         decodedObject( systemInvoiceBatchVO );
         
         // 初始化PagedListHolder，用于引用方式调用Service
         final PagedListHolder batchHolder = new PagedListHolder();
         // 传入当前页
         batchHolder.setPage( page );

         // 如果没有指定排序则默认按 batchId排序
         if ( systemInvoiceBatchVO.getSortColumn() == null || systemInvoiceBatchVO.getSortColumn().trim().equals( "" ) )
         {
            systemInvoiceBatchVO.setSortColumn( "batchId" );
            systemInvoiceBatchVO.setSortOrder( "desc" );
         }

         // 传入当前值对象
         batchHolder.setObject( systemInvoiceBatchVO );
         // 设置页面记录条数
         batchHolder.setPageSize( listPageSize_medium );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         systemInvoiceBatchService.getInvoiceBatchVOsByBatch( batchHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( batchHolder, request );
         
         // Holder需写入Request对象
         request.setAttribute( "batchHolder", batchHolder );
         // 写入pageFlag
         request.setAttribute( "pageFlag", pageFlag );
         // 如果是ajax请求
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listBatchTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      //批次列表
      return mapping.findForward( "listBatch" );
   }

   @Override
   public ActionForward to_objectNew( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward add_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward to_objectModify( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   public ActionForward modify_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
      return null;
   }

   @Override
   protected void delete_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

   @Override
   protected void delete_objectList( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      // No use
   }

}

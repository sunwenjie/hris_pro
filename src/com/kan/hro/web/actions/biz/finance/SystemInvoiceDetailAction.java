package com.kan.hro.web.actions.biz.finance;

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
import com.kan.hro.domain.biz.finance.SystemInvoiceBatchVO;
import com.kan.hro.domain.biz.finance.SystemInvoiceDetailVO;
import com.kan.hro.domain.biz.finance.SystemInvoiceHeaderVO;
import com.kan.hro.service.inf.biz.client.ClientService;
import com.kan.hro.service.inf.biz.finance.SystemInvoiceBatchService;
import com.kan.hro.service.inf.biz.finance.SystemInvoiceDetailService;
import com.kan.hro.service.inf.biz.finance.SystemInvoiceHeaderService;

/**
 * 
 * @author : ian.huang
 *	@Date   : 2014-5-13
 */
public class SystemInvoiceDetailAction extends BaseAction
{
   
   @Override
   public ActionForward list_object( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws KANException
   {
      try
      {
         // 初始化Service接口
         final SystemInvoiceBatchService systemInvoiceBatchService = ( SystemInvoiceBatchService ) getService( "systemInvoiceBatchService" );
         final SystemInvoiceHeaderService systemInvoiceHeaderService = ( SystemInvoiceHeaderService ) getService( "systemInvoiceHeaderService" );
         final SystemInvoiceDetailService systemInvoiceDetailService = ( SystemInvoiceDetailService ) getService( "systemInvoiceDetailService" );
         final ClientService clientService = ( ClientService ) getService( "clientService" );
         // 获得批次ID
         final String batchId = KANUtil.decodeStringFromAjax( request.getParameter( "batchId" ) );
         // 获得批次对象
         final SystemInvoiceBatchVO systemInvoiceBatchVO = systemInvoiceBatchService.getInvoiceBatchVOByBatchId( batchId );

         // 初始化对象及国际化
         systemInvoiceBatchVO.reset( null, request );
         
         request.setAttribute( "batchForm", systemInvoiceBatchVO );

         // 如果批次是按客户结算的，装载客户对象
         if ( systemInvoiceBatchVO.getClientId() != null && !systemInvoiceBatchVO.getClientId().trim().equals( "" ) )
         {
            final ClientVO clientVO = clientService.getClientVOByClientId( systemInvoiceBatchVO.getClientId() );
            clientVO.reset( null, request );
            request.setAttribute( "clientVO", clientVO );
         }

         // 获得订单流水ID
         final String invoiceId = KANUtil.decodeStringFromAjax( request.getParameter( "invoiceId" ) );
         // 获得订单流水对象
         SystemInvoiceHeaderVO  invoiceHeaderVO=new SystemInvoiceHeaderVO();
         invoiceHeaderVO.setInvoiceId( invoiceId );
         invoiceHeaderVO.setCorpId( getCorpId( request, response ) );
         invoiceHeaderVO.setAccountId( getAccountId( request, response ) );
         invoiceHeaderVO.setExtendInvoiceId( invoiceId );
         
         final SystemInvoiceHeaderVO systemInvoiceHeaderVO = systemInvoiceHeaderService.getSystemInvoiceHeaderByInvoiceId( invoiceHeaderVO );

         // 初始化对象及国际化
         systemInvoiceHeaderVO.reset( null, request );
         request.setAttribute( "HeaderForm", systemInvoiceHeaderVO );

         // 初始化PagedListHolder，用于引用方式调用Service
         PagedListHolder listDetailHolder = new PagedListHolder();
         // 获得当前页
         final String page = request.getParameter( "page" );
         // 获得是否Ajax调用
         final String ajax = request.getParameter( "ajax" );
         
         final String pageFlag = request.getParameter( "pageFlag" );
         // 传入当前页
         listDetailHolder.setPage( page );
         // 初始化查询对象
         final SystemInvoiceDetailVO systemInvoiceDetailVO = (SystemInvoiceDetailVO)form;
         
         // 设置相关属性值
         systemInvoiceDetailVO.setInvoiceId( invoiceId );
         // 传入排序相关字段
         systemInvoiceDetailVO.setSortColumn( request.getParameter( "sortColumn" ) );
         systemInvoiceDetailVO.setSortOrder( request.getParameter( "sortOrder" ) );
         
         // 默认按 订单流水ID排序
         if ( systemInvoiceDetailVO.getSortColumn() == null || systemInvoiceDetailVO.getSortColumn().trim().equals( "" ) )
         {
            systemInvoiceDetailVO.setSortColumn( "invoiceDetailId" );
            systemInvoiceDetailVO.setSortOrder( "desc" );
         }
         // 传入当前值对象
         listDetailHolder.setObject( systemInvoiceDetailVO );
         // 设置页面记录条数
         listDetailHolder.setPageSize( listPageSize );
         systemInvoiceDetailVO.reset( null, request );
         // 调用Service方法，引用对象返回，第二个参数说明是否分页
         systemInvoiceDetailService.getInvoiceDetailVOsByheaderId( listDetailHolder, true );
         // 刷新Holder，国际化传值
         refreshHolder( listDetailHolder, request );
         // Holder需写入Request对象
         request.setAttribute( "listDetailHolder", listDetailHolder );
         //写入页面设置
         request.setAttribute( "pageFlag", pageFlag );
         
         if ( new Boolean( ajax ) )
         {
            return mapping.findForward( "listDetailTable" );
         }
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      // Ajax调用
      return mapping.findForward( "listDetail" );
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

package com.kan.hro.service.inf.biz.finance;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.finance.SystemInvoiceDetailVO;

public interface SystemInvoiceDetailService
{
   // Page Flag常量（页面跳转用）
   public static String BATCH = "Preview";

   public static String HEADER = "Split";

   public static String DETAAIL = "Merge";
   public abstract PagedListHolder getInvoiceDetailVOsByheaderId( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SystemInvoiceDetailVO getInvoiceDetailVOByOrderDetailId( final String orderDetailId ) throws KANException;
   
   public abstract int updateInvoiceDetail( final SystemInvoiceDetailVO systemInvoiceDetailVO ) throws KANException;

   public abstract int insertInvoiceDetail( final SystemInvoiceDetailVO systemInvoiceDetailVO ) throws KANException;

   public abstract int deleteInvoiceDetail( final String invoiceDetailId ) throws KANException;

}

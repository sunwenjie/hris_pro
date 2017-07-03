package com.kan.hro.service.inf.biz.finance;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.finance.SystemInvoiceBatchVO;

public interface SystemInvoiceBatchService
{
   // Page Flag常量（页面跳转用）
   public static String BATCH = "Preview";

   public static String HEADER = "Split";

   public static String DETAAIL = "Merge";

   public abstract PagedListHolder getInvoiceBatchVOsByBatch( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SystemInvoiceBatchVO getInvoiceBatchVOByBatchId( final String batchId ) throws KANException;
   
   public abstract int updateInvoiceBatch( final SystemInvoiceBatchVO systemInvoiceBatchVO ) throws KANException;

   public abstract int insertInvoiceBatch( final SystemInvoiceBatchVO systemInvoiceBatchVO ) throws KANException;

   public abstract int deleteInvoiceBatch( final String batchId ) throws KANException;
   
}

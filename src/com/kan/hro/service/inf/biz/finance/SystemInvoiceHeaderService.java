package com.kan.hro.service.inf.biz.finance;

import java.util.List;

import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.finance.SystemInvoiceHeaderVO;

public interface SystemInvoiceHeaderService
{
   // Page Flag常量（页面跳转用）
   public static String BATCH = "Preview";

   public static String HEADER = "Split";

   public static String DETAAIL = "Merge";
   public abstract PagedListHolder getInvoiceHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract int updateInvoiceHeader( final SystemInvoiceHeaderVO  financeSystemInvoiceHeaderVO) throws KANException;

   public abstract int insertInvoiceHeader( final SystemInvoiceHeaderVO financeSystemInvoiceHeaderVO ) throws KANException;

   public abstract int deleteInvoiceHeader( final String invoiceId ) throws KANException;

   public abstract List< Object > getInvoiceHeaderVOsByBatchId( final String batchId ) throws KANException;
   
   public abstract SystemInvoiceHeaderVO getSystemInvoiceHeaderByInvoiceId(final SystemInvoiceHeaderVO financeSystemInvoiceHeaderVO) throws KANException;
   
   public abstract PagedListHolder getSubSystemInvoiceHeaderByHeaderId( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;
   
   public abstract PagedListHolder getComSystemInvoiceHeaderById( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;
   
   public abstract SystemInvoiceHeaderVO getSystemInvoiceHeaderById(final SystemInvoiceHeaderVO systemInvoiceHeaderVO) throws KANException;
   
   public abstract int getMaxInvoiceId() throws KANException;
}

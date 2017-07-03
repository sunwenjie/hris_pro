package com.kan.hro.dao.inf.biz.finance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.finance.SystemInvoiceDetailVO;

public interface SystemInvoiceDetailDao
{
   public abstract int countSystemInvoiceDetailVOsByHeaderId( final SystemInvoiceDetailVO systemInvoiceDetailVO ) throws KANException;

   public abstract List< Object > getSystemInvoiceDetailVOsByHeaderId( final SystemInvoiceDetailVO systemInvoiceDetailVO ) throws KANException;

   public abstract List< Object > getSystemInvoiceDetailVOsByHeaderId( final SystemInvoiceDetailVO systemInvoiceDetailVO, RowBounds rowBounds ) throws KANException;

   public abstract SystemInvoiceDetailVO getSystemInvoiceDetailVOByOrderDetailId( final String invoiceDetailId ) throws KANException;

   public abstract int insertSystemInvoiceDetail( final SystemInvoiceDetailVO systemInvoiceDetailVO ) throws KANException;

   public abstract int updateSystemInvoiceDetail( final SystemInvoiceDetailVO systemInvoiceDetailVO ) throws KANException;

   public abstract int deleteSystemInvoiceDetail( final String invoiceDetailId ) throws KANException;

}

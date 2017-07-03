package com.kan.hro.dao.inf.biz.finance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.finance.SystemInvoiceBatchVO;

public interface SystemInvoiceBatchDao
{
   public abstract int countSystemInvoiceBatchVOsByBatch( final SystemInvoiceBatchVO systemInvoicebatchVO ) throws KANException;

   public abstract List< Object > getSystemInvoiceBatchVOsByBatch( final SystemInvoiceBatchVO systemInvoicebatchVO ) throws KANException;

   public abstract List< Object > getSystemInvoiceBatchVOsByBatch( final SystemInvoiceBatchVO systemInvoicebatchVO, RowBounds rowBounds ) throws KANException;

   public abstract SystemInvoiceBatchVO getSystemInvoiceBatchVOByBatchId( final String batchId ) throws KANException;

   public abstract int insertSystemInvoiceBatch( final SystemInvoiceBatchVO systemInvoiceBatchVO ) throws KANException;

   public abstract int updateSystemInvoiceBatch( final SystemInvoiceBatchVO systemInvoiceBatchVO ) throws KANException;

   public abstract int deleteSystemInvoiceBatch( final String batchId ) throws KANException;

}

package com.kan.hro.dao.inf.biz.finance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.util.KANException;
import com.kan.hro.domain.biz.finance.SystemInvoiceHeaderVO;

public interface SystemInvoiceHeaderDao
{
   public abstract int countSystemInvoiceHeaderVOsByCondition( final SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException;

   public abstract List< Object > getSystemInvoiceHeaderVOsByCondition( final SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException;

   public abstract List< Object > getSystemInvoiceHeaderVOsByCondition( final SystemInvoiceHeaderVO systemInvoiceHeaderVO, RowBounds rowBounds ) throws KANException;

   public abstract List< Object > getSystemInvoiceHeaderVOsByBatchId( final String batchId ) throws KANException;

   public abstract int insertSystemInvoiceHeader( final SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException;

   public abstract int updateSystemInvoiceHeader( final SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException;

   public abstract int deleteSystemInvoiceHeader( final String invoiceId ) throws KANException;
                                         
   public abstract SystemInvoiceHeaderVO getSystemInvoiceHeaderByInvoiceId(final SystemInvoiceHeaderVO systemInvoiceHeaderVO)throws KANException;
   
   public abstract List<Object> getSubSystemInvoiceHeaderByHeaderId(final SystemInvoiceHeaderVO systemInvoiceHeaderVO, RowBounds rowBounds )throws KANException;
   
   public abstract List<Object> getSubSystemInvoiceHeaderByHeaderId(final SystemInvoiceHeaderVO systemInvoiceHeaderVO)throws KANException;
   
   public abstract int countSubSystemInvoiceHeaderByHeaderId(final SystemInvoiceHeaderVO systemInvoiceHeaderVO)throws KANException;

   public abstract List<Object> getComSystemInvoiceHeaderById(final SystemInvoiceHeaderVO systemInvoiceHeaderVO, RowBounds rowBounds )throws KANException;
   
   public abstract List<Object> getComSystemInvoiceHeaderById(final SystemInvoiceHeaderVO systemInvoiceHeaderVO)throws KANException;
   
   public abstract int countComSystemInvoiceHeaderById(final SystemInvoiceHeaderVO systemInvoiceHeaderVO)throws KANException;

   public abstract SystemInvoiceHeaderVO getSystemInvoiceHeaderById(final SystemInvoiceHeaderVO systemInvoiceHeaderVO) throws KANException;

   public abstract int getMaxInvoiceId()throws KANException;
}

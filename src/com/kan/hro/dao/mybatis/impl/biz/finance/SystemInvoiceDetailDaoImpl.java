package com.kan.hro.dao.mybatis.impl.biz.finance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.finance.SystemInvoiceDetailDao;
import com.kan.hro.domain.biz.finance.SystemInvoiceDetailVO;

public class SystemInvoiceDetailDaoImpl extends Context implements SystemInvoiceDetailDao
{

   @Override
   public int countSystemInvoiceDetailVOsByHeaderId(final SystemInvoiceDetailVO systemInvoiceDetailVO ) throws KANException
   {
      return (Integer)select( "countInvoiceDetailVOsByHeaderId", systemInvoiceDetailVO );
   }

   @Override
   public List< Object > getSystemInvoiceDetailVOsByHeaderId(final SystemInvoiceDetailVO systemInvoiceDetailVO ) throws KANException
   {
      return selectList( "getSystemInvoiceDetailVOsByHeaderId", systemInvoiceDetailVO );
   }

   @Override
   public List< Object > getSystemInvoiceDetailVOsByHeaderId(final SystemInvoiceDetailVO systemInvoiceDetailVO,final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSystemInvoiceDetailVOsByHeaderId", systemInvoiceDetailVO, rowBounds );
   }

   @Override
   public SystemInvoiceDetailVO getSystemInvoiceDetailVOByOrderDetailId(final String invoiceDetailId ) throws KANException
   {
      return (SystemInvoiceDetailVO)select( "getSystemInvoiceDetailVOByinvoiceDetailId", invoiceDetailId );
   }

   @Override
   public int insertSystemInvoiceDetail(final SystemInvoiceDetailVO systemInvoiceDetailVO ) throws KANException
   {
      return insert( "insertSystemInvoiceDetail", systemInvoiceDetailVO );
   }

   @Override
   public int updateSystemInvoiceDetail(final SystemInvoiceDetailVO systemInvoiceDetailVO ) throws KANException
   {
      return update( "updateSystemInvoiceDetail", systemInvoiceDetailVO );
   }

   @Override
   public int deleteSystemInvoiceDetail(final String invoiceDetailId ) throws KANException
   {
      return delete( "deleteSystemInvoiceDetail", invoiceDetailId );
   }

 
}

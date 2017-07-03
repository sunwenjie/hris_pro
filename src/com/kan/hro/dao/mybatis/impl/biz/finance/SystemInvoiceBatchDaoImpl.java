package com.kan.hro.dao.mybatis.impl.biz.finance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.finance.SystemInvoiceBatchDao;
import com.kan.hro.domain.biz.finance.SystemInvoiceBatchVO;

public class SystemInvoiceBatchDaoImpl extends Context implements SystemInvoiceBatchDao
{

   @Override
   public int countSystemInvoiceBatchVOsByBatch(final SystemInvoiceBatchVO systemInvoicebatchVO ) throws KANException
   {  
      return (Integer)select( "countSystemInvoiceBatchVOsByBatch", systemInvoicebatchVO );
   }

   @Override
   public List< Object > getSystemInvoiceBatchVOsByBatch(final SystemInvoiceBatchVO systemInvoicebatchVO ) throws KANException
   {
      return selectList( "getSystemInvoiceBatchVOsByBatch", systemInvoicebatchVO );
   }

   @Override
   public List< Object > getSystemInvoiceBatchVOsByBatch(final SystemInvoiceBatchVO systemInvoicebatchVO,final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSystemInvoiceBatchVOsByBatch", systemInvoicebatchVO ,rowBounds);
   }

   @Override
   public SystemInvoiceBatchVO getSystemInvoiceBatchVOByBatchId(final String batchId ) throws KANException
   {
      return (SystemInvoiceBatchVO)select( "getSystemInvoiceBatchVOByBatchId", batchId );
   }


   @Override
   public int updateSystemInvoiceBatch(final SystemInvoiceBatchVO systemInvoiceBatchVO ) throws KANException
   {
      return update( "updateSystemInvoiceBatch", systemInvoiceBatchVO );
   }

   @Override
   public int deleteSystemInvoiceBatch(final String batchId ) throws KANException
   {
      return delete( "deleteSystemInvoiceBatch", batchId );
   }

   @Override
   public int insertSystemInvoiceBatch(final SystemInvoiceBatchVO systemInvoiceBatchVO ) throws KANException
   {
      return insert( "insertSystemInvoiceBatch", systemInvoiceBatchVO );
   }


}

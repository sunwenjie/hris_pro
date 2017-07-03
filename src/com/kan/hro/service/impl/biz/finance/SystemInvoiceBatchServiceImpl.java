package com.kan.hro.service.impl.biz.finance;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.finance.SystemInvoiceBatchDao;
import com.kan.hro.domain.biz.finance.SystemInvoiceBatchVO;
import com.kan.hro.service.inf.biz.finance.SystemInvoiceBatchService;

public class SystemInvoiceBatchServiceImpl extends ContextService implements SystemInvoiceBatchService
{

   @Override
   public PagedListHolder getInvoiceBatchVOsByBatch( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final SystemInvoiceBatchDao systemInvoiceBatchDao = ( SystemInvoiceBatchDao ) getDao();
      pagedListHolder.setHolderSize( systemInvoiceBatchDao.countSystemInvoiceBatchVOsByBatch( ( SystemInvoiceBatchVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
         
      {
         pagedListHolder.setSource( systemInvoiceBatchDao.getSystemInvoiceBatchVOsByBatch( ( SystemInvoiceBatchVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( systemInvoiceBatchDao.getSystemInvoiceBatchVOsByBatch( ( SystemInvoiceBatchVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public SystemInvoiceBatchVO getInvoiceBatchVOByBatchId( final String batchId ) throws KANException
   {
      return ( ( SystemInvoiceBatchDao ) getDao() ).getSystemInvoiceBatchVOByBatchId( batchId );
   }

   @Override
   public int updateInvoiceBatch( final SystemInvoiceBatchVO systemInvoiceBatchVO ) throws KANException
   {
      return ( ( SystemInvoiceBatchDao ) getDao() ).updateSystemInvoiceBatch( systemInvoiceBatchVO );
   }

   @Override
   public int insertInvoiceBatch( final SystemInvoiceBatchVO systemInvoiceBatchVO ) throws KANException
   {
      return ( ( SystemInvoiceBatchDao ) getDao() ).insertSystemInvoiceBatch( systemInvoiceBatchVO );
   }

   @Override
   public int deleteInvoiceBatch( final String batchId ) throws KANException
   {
      return ( ( SystemInvoiceBatchDao ) getDao() ).deleteSystemInvoiceBatch( batchId );
   }
}

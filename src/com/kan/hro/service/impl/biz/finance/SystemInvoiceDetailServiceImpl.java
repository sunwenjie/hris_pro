package com.kan.hro.service.impl.biz.finance;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.finance.SystemInvoiceDetailDao;
import com.kan.hro.domain.biz.finance.SystemInvoiceDetailVO;
import com.kan.hro.service.inf.biz.finance.SystemInvoiceDetailService;

public class SystemInvoiceDetailServiceImpl extends ContextService implements SystemInvoiceDetailService
{

   @Override
   public PagedListHolder getInvoiceDetailVOsByheaderId( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final SystemInvoiceDetailDao systemInvoiceDetailDao = ( SystemInvoiceDetailDao ) getDao();
    pagedListHolder.setHolderSize( systemInvoiceDetailDao.countSystemInvoiceDetailVOsByHeaderId( ( SystemInvoiceDetailVO ) pagedListHolder.getObject() ) );

    if ( isPaged )
    {
       pagedListHolder.setSource( systemInvoiceDetailDao.getSystemInvoiceDetailVOsByHeaderId( ( SystemInvoiceDetailVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
             * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
    }
    else
    {
       pagedListHolder.setSource( systemInvoiceDetailDao.getSystemInvoiceDetailVOsByHeaderId( ( SystemInvoiceDetailVO ) pagedListHolder.getObject() ) );
    }

    return pagedListHolder;
   }

   @Override
   public SystemInvoiceDetailVO getInvoiceDetailVOByOrderDetailId( String invoiceDetailId ) throws KANException
   {
      return (( SystemInvoiceDetailDao ) getDao()).getSystemInvoiceDetailVOByOrderDetailId( invoiceDetailId );
   }

   @Override
   public int updateInvoiceDetail( SystemInvoiceDetailVO systemInvoiceDetailVO ) throws KANException
   {
      return (( SystemInvoiceDetailDao ) getDao()).updateSystemInvoiceDetail( systemInvoiceDetailVO );
   }

   @Override
   public int insertInvoiceDetail( SystemInvoiceDetailVO systemInvoiceDetailVO ) throws KANException
   {
      return (( SystemInvoiceDetailDao ) getDao()).insertSystemInvoiceDetail( systemInvoiceDetailVO );
   }

   @Override
   public int deleteInvoiceDetail( String invoiceDetailId ) throws KANException
   {
      return (( SystemInvoiceDetailDao ) getDao()).deleteSystemInvoiceDetail( invoiceDetailId );
   }
   
}

package com.kan.hro.service.impl.biz.finance;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.finance.SystemInvoiceHeaderDao;
import com.kan.hro.domain.biz.finance.SystemInvoiceHeaderVO;
import com.kan.hro.service.inf.biz.finance.SystemInvoiceHeaderService;

public class SystemInvoiceHeaderServiceImpl extends ContextService implements SystemInvoiceHeaderService
{

   @Override
   public PagedListHolder getInvoiceHeaderVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
    final SystemInvoiceHeaderDao systemInvoiceHeaderDao = ( SystemInvoiceHeaderDao ) getDao();
    pagedListHolder.setHolderSize( systemInvoiceHeaderDao.countSystemInvoiceHeaderVOsByCondition( ( SystemInvoiceHeaderVO ) pagedListHolder.getObject() ) );

    if ( isPaged )
    {
       pagedListHolder.setSource( systemInvoiceHeaderDao.getSystemInvoiceHeaderVOsByCondition( ( SystemInvoiceHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
             * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
    }
    else
    {
       pagedListHolder.setSource( systemInvoiceHeaderDao.getSystemInvoiceHeaderVOsByCondition( ( SystemInvoiceHeaderVO ) pagedListHolder.getObject() ) );
    }

    return pagedListHolder;
   }


   @Override
   public int updateInvoiceHeader( SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException
   {
      return (( SystemInvoiceHeaderDao ) getDao()).updateSystemInvoiceHeader( systemInvoiceHeaderVO );
   }

   @Override
   public int insertInvoiceHeader( SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException
   {
      return (( SystemInvoiceHeaderDao ) getDao()).insertSystemInvoiceHeader( systemInvoiceHeaderVO );
   }

   @Override
   public int deleteInvoiceHeader( String invoiceId ) throws KANException
   {
      return (( SystemInvoiceHeaderDao ) getDao()).deleteSystemInvoiceHeader( invoiceId );
   }

   @Override
   public List< Object > getInvoiceHeaderVOsByBatchId( String batchId ) throws KANException
   {
      return (( SystemInvoiceHeaderDao ) getDao()).getSystemInvoiceHeaderVOsByBatchId( batchId );
   }


   @Override
   public SystemInvoiceHeaderVO getSystemInvoiceHeaderByInvoiceId(final SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException
   {
      
      return (SystemInvoiceHeaderVO)(( SystemInvoiceHeaderDao ) getDao()).getSystemInvoiceHeaderByInvoiceId( systemInvoiceHeaderVO );
   }
   

   @Override
   public PagedListHolder getSubSystemInvoiceHeaderByHeaderId( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final SystemInvoiceHeaderDao systemInvoiceHeaderDao = ( SystemInvoiceHeaderDao ) getDao();
      pagedListHolder.setHolderSize( systemInvoiceHeaderDao.countSubSystemInvoiceHeaderByHeaderId( ( SystemInvoiceHeaderVO )pagedListHolder.getObject()) );

      if ( isPaged )
      {
         pagedListHolder.setSource( systemInvoiceHeaderDao.getSubSystemInvoiceHeaderByHeaderId( ( SystemInvoiceHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( systemInvoiceHeaderDao.getSubSystemInvoiceHeaderByHeaderId( ( SystemInvoiceHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }
   
   
   @Override
   public PagedListHolder getComSystemInvoiceHeaderById( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final SystemInvoiceHeaderDao systemInvoiceHeaderDao = ( SystemInvoiceHeaderDao ) getDao();
      pagedListHolder.setHolderSize(systemInvoiceHeaderDao.countComSystemInvoiceHeaderById( (SystemInvoiceHeaderVO) pagedListHolder.getObject())  );

      if ( isPaged )
      {
         pagedListHolder.setSource( systemInvoiceHeaderDao.getComSystemInvoiceHeaderById( ( SystemInvoiceHeaderVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( systemInvoiceHeaderDao.getComSystemInvoiceHeaderById( ( SystemInvoiceHeaderVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }


   @Override
   public SystemInvoiceHeaderVO getSystemInvoiceHeaderById( SystemInvoiceHeaderVO systemInvoiceHeaderVO ) throws KANException
   {
      return (SystemInvoiceHeaderVO)(( SystemInvoiceHeaderDao ) getDao()).getSystemInvoiceHeaderById( systemInvoiceHeaderVO );
   }


   @Override
   public int getMaxInvoiceId() throws KANException
   {
     return (( SystemInvoiceHeaderDao ) getDao()).getMaxInvoiceId();
   }
   
}

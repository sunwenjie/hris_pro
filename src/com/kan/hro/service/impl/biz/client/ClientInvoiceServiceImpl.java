package com.kan.hro.service.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientInvoiceDao;
import com.kan.hro.domain.biz.client.ClientInvoiceVO;
import com.kan.hro.service.inf.biz.client.ClientInvoiceService;

public class ClientInvoiceServiceImpl extends ContextService implements ClientInvoiceService
{

   @Override
   public PagedListHolder getClientInvoiceVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ClientInvoiceDao clientInvoiceDao = ( ClientInvoiceDao ) getDao();
      pagedListHolder.setHolderSize( clientInvoiceDao.countClientInvoiceVOsByCondition( ( ClientInvoiceVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientInvoiceDao.getClientInvoiceVOsByCondition( ( ClientInvoiceVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientInvoiceDao.getClientInvoiceVOsByCondition( ( ClientInvoiceVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ClientInvoiceVO getClientInvoiceVOByClientInvoiceId( final String clientInvoiceId ) throws KANException
   {
      return ( ( ClientInvoiceDao ) getDao() ).getClientInvoiceVOByClientInvoiceId( clientInvoiceId );
   }

   @Override
   public int updateClientInvoice( final ClientInvoiceVO clientInvoiceVO ) throws KANException
   {
      return ( ( ClientInvoiceDao ) getDao() ).updateClientInvoice( clientInvoiceVO );
   }

   @Override
   public int insertClientInvoice( final ClientInvoiceVO clientInvoiceVO ) throws KANException
   {
      return ( ( ClientInvoiceDao ) getDao() ).insertClientInvoice( clientInvoiceVO );
   }

   @Override
   public int deleteClientInvoice( final ClientInvoiceVO clientInvoiceVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ýclientInvoiceVO
      clientInvoiceVO.setDeleted( ClientInvoiceVO.FALSE );
      return ( ( ClientInvoiceDao ) getDao() ).updateClientInvoice( clientInvoiceVO );
   }

   @Override
   public List< Object > getClientInvoiceBaseViews( final String accountId ) throws KANException
   {
      return ( ( ClientInvoiceDao ) getDao() ).getClientInvoiceBaseViews( accountId );
   }

   @Override
   public List< Object > getClientInvoiceVOsByClientId( final String clientId ) throws KANException
   {
      return ( ( ClientInvoiceDao ) getDao() ).getClientInvoiceVOsByClientId( clientId );
   }

}

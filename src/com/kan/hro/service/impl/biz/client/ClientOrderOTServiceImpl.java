package com.kan.hro.service.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderOTDao;
import com.kan.hro.domain.biz.client.ClientOrderOTVO;
import com.kan.hro.service.inf.biz.client.ClientOrderOTService;

public class ClientOrderOTServiceImpl extends ContextService implements ClientOrderOTService
{

   @Override
   public PagedListHolder getClientOrderOTVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ClientOrderOTDao clientOrderOTDao = ( ClientOrderOTDao ) getDao();
      pagedListHolder.setHolderSize( clientOrderOTDao.countClientOrderOTVOsByCondition( ( ClientOrderOTVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientOrderOTDao.getClientOrderOTVOsByCondition( ( ClientOrderOTVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientOrderOTDao.getClientOrderOTVOsByCondition( ( ClientOrderOTVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ClientOrderOTVO getClientOrderOTVOByClientOrderOTId( final String clientOrderOTId ) throws KANException
   {
      return ( ( ClientOrderOTDao ) getDao() ).getClientOrderOTVOByClientOrderOTId( clientOrderOTId );
   }

   @Override
   public int updateClientOrderOT( final ClientOrderOTVO clientOrderOTVO ) throws KANException
   {
      try
      {
         ( ( ClientOrderOTDao ) getDao() ).updateClientOrderOT( clientOrderOTVO );
      }
      catch ( final Exception e )
      {
         throw new KANException( e );
      }
      return 0;
   }

   @Override
   public int insertClientOrderOT( final ClientOrderOTVO clientOrderOTVO ) throws KANException
   {
      return ( ( ClientOrderOTDao ) getDao() ).insertClientOrderOT( clientOrderOTVO );
   }

   @Override
   public int deleteClientOrderOT( final ClientOrderOTVO clientOrderOTVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ýclientOrderOTVO
      clientOrderOTVO.setDeleted( ClientOrderOTVO.FALSE );
      return ( ( ClientOrderOTDao ) getDao() ).updateClientOrderOT( clientOrderOTVO );
   }

   @Override
   public List< Object > getClientOrderOTVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException
   {
      return ( ( ClientOrderOTDao ) getDao() ).getClientOrderOTVOsByClientOrderHeaderId( clientOrderHeaderId );
   }

}

package com.kan.hro.service.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientContractPropertyDao;
import com.kan.hro.domain.biz.client.ClientContractPropertyVO;
import com.kan.hro.service.inf.biz.client.ClientContractPropertyService;

public class ClientContractPropertyServiceImpl extends ContextService implements ClientContractPropertyService
{

   @Override
   public PagedListHolder getClientContractPropertyVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ClientContractPropertyDao clientContractPropertyDao = ( ClientContractPropertyDao ) getDao();
      pagedListHolder.setHolderSize( clientContractPropertyDao.countClientContractPropertyVOsByCondition( ( ClientContractPropertyVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientContractPropertyDao.getClientContractPropertyVOsByCondition( ( ClientContractPropertyVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientContractPropertyDao.getClientContractPropertyVOsByCondition( ( ClientContractPropertyVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ClientContractPropertyVO getClientContractPropertyVOByClientContractPropertyId( final String clientContractPropertyId ) throws KANException
   {
      return ( ( ClientContractPropertyDao ) getDao() ).getClientContractPropertyVOByClientContractPropertyId( clientContractPropertyId );
   }

   @Override
   public int updateClientContractProperty( final ClientContractPropertyVO clientContractPropertyVO ) throws KANException
   {
      return ( ( ClientContractPropertyDao ) getDao() ).updateClientContractProperty( clientContractPropertyVO );
   }

   @Override
   public int insertClientContractProperty( final ClientContractPropertyVO clientContractPropertyVO ) throws KANException
   {
      return ( ( ClientContractPropertyDao ) getDao() ).insertClientContractProperty( clientContractPropertyVO );
   }

   @Override
   public int deleteClientContractProperty( final ClientContractPropertyVO clientContractPropertyVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ýclientContractPropertyVO
      clientContractPropertyVO.setDeleted( ClientContractPropertyVO.FALSE );
      return ( ( ClientContractPropertyDao ) getDao() ).updateClientContractProperty( clientContractPropertyVO );
   }

   @Override
   public List< Object > getClientContractPropertyVOsByContractId( final String ContractId ) throws KANException
   {
      return ( ( ClientContractPropertyDao ) getDao() ).getClientContractPropertyVOsByContractId( ContractId );
   }

}

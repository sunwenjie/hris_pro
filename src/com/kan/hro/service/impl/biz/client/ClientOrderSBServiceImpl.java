package com.kan.hro.service.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderSBDao;
import com.kan.hro.domain.biz.client.ClientOrderSBVO;
import com.kan.hro.service.inf.biz.client.ClientOrderSBService;

public class ClientOrderSBServiceImpl extends ContextService implements ClientOrderSBService
{
   @Override
   public PagedListHolder getClientOrderSBVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ClientOrderSBDao clientOrderSBDao = ( ClientOrderSBDao ) getDao();
      pagedListHolder.setHolderSize( clientOrderSBDao.countClientOrderSBVOsByCondition( ( ClientOrderSBVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientOrderSBDao.getClientOrderSBVOsByCondition( ( ClientOrderSBVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientOrderSBDao.getClientOrderSBVOsByCondition( ( ClientOrderSBVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ClientOrderSBVO getClientOrderSBVOByClientOrderSBId( final String clientOrderSBId ) throws KANException
   {
      return ( ( ClientOrderSBDao ) getDao() ).getClientOrderSBVOByClientOrderSBId( clientOrderSBId );
   }

   @Override
   public int updateClientOrderSB( final ClientOrderSBVO clientOrderSBVO ) throws KANException
   {
      return ( ( ClientOrderSBDao ) getDao() ).updateClientOrderSB( clientOrderSBVO );
   }

   @Override
   public int insertClientOrderSB( final ClientOrderSBVO clientOrderSBVO ) throws KANException
   {
      return ( ( ClientOrderSBDao ) getDao() ).insertClientOrderSB( clientOrderSBVO );
   }

   @Override
   public int deleteClientOrderSB( final ClientOrderSBVO clientOrderSBVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ýClientOrderSBVO
      clientOrderSBVO.setDeleted( ClientOrderSBVO.FALSE );
      return ( ( ClientOrderSBDao ) getDao() ).updateClientOrderSB( clientOrderSBVO );
   }

   @Override
   public List< Object > getClientOrderSBVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException
   {
      return ( ( ClientOrderSBDao ) getDao() ).getClientOrderSBVOsByClientOrderHeaderId( clientOrderHeaderId );
   }

   @Override
   public List< Object > getClientOrderSBVOsByEmployeeContractId( final String employeeContractId ) throws KANException
   {
      return ( ( ClientOrderSBDao ) getDao() ).getClientOrderSBVOsByEmployeeContractId( employeeContractId );
   }

}

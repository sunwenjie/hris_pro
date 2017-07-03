package com.kan.hro.service.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderCBDao;
import com.kan.hro.domain.biz.client.ClientOrderCBVO;
import com.kan.hro.service.inf.biz.client.ClientOrderCBService;

public class ClientOrderCBServiceImpl extends ContextService implements ClientOrderCBService
{

   @Override
   public PagedListHolder getClientOrderCBVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final ClientOrderCBDao clientOrderCBDao = ( ClientOrderCBDao ) getDao();
      pagedListHolder.setHolderSize( clientOrderCBDao.countClientOrderCBVOsByCondition( ( ClientOrderCBVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientOrderCBDao.getClientOrderCBVOsByCondition( ( ClientOrderCBVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientOrderCBDao.getClientOrderCBVOsByCondition( ( ClientOrderCBVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ClientOrderCBVO getClientOrderCBVOByClientOrderCBId( String clientOrderCBId ) throws KANException
   {
      return ( ( ClientOrderCBDao ) getDao() ).getClientOrderCBVOByClientOrderCBId( clientOrderCBId );
   }

   @Override
   public int updateClientOrderCB( ClientOrderCBVO clientOrderCBVO ) throws KANException
   {
      return ( ( ClientOrderCBDao ) getDao() ).updateClientOrderCB( clientOrderCBVO );
   }

   @Override
   public int insertClientOrderCB( ClientOrderCBVO clientOrderCBVO ) throws KANException
   {
      return ( ( ClientOrderCBDao ) getDao() ).insertClientOrderCB( clientOrderCBVO );
   }

   @Override
   public int deleteClientOrderCB( ClientOrderCBVO clientOrderCBVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ýclientOrderCBVO
      clientOrderCBVO.setDeleted( ClientOrderCBVO.FALSE );
      return ( ( ClientOrderCBDao ) getDao() ).updateClientOrderCB( clientOrderCBVO );
   }

   @Override
   public List< Object > getClientOrderCBVOsByClientOrderHeaderId( final String clientOrderHeaderId ) throws KANException
   {
      return ( ( ClientOrderCBDao ) getDao() ).getClientOrderCBVOsByClientOrderHeaderId( clientOrderHeaderId );
   }

   @Override
   public List< Object > getClientOrderCBVOsByEmployeeContractId( final String employeeContractId ) throws KANException
   {
      return ( ( ClientOrderCBDao ) getDao() ).getClientOrderCBVOsByEmployeeContractId( employeeContractId );
   }

}

package com.kan.hro.service.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientOrderLeaveDao;
import com.kan.hro.domain.biz.client.ClientOrderLeaveVO;
import com.kan.hro.service.inf.biz.client.ClientOrderLeaveService;

public class ClientOrderLeaveServiceImpl extends ContextService implements ClientOrderLeaveService
{

   @Override
   public PagedListHolder getClientOrderLeaveVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ClientOrderLeaveDao clientOrderLeaveDao = ( ClientOrderLeaveDao ) getDao();
      pagedListHolder.setHolderSize( clientOrderLeaveDao.countClientOrderLeaveVOsByCondition( ( ClientOrderLeaveVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( clientOrderLeaveDao.getClientOrderLeaveVOsByCondition( ( ClientOrderLeaveVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientOrderLeaveDao.getClientOrderLeaveVOsByCondition( ( ClientOrderLeaveVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ClientOrderLeaveVO getClientOrderLeaveVOByClientOrderLeaveId( final String clientOrderLeaveId ) throws KANException
   {
      return ( ( ClientOrderLeaveDao ) getDao() ).getClientOrderLeaveVOByClientOrderLeaveId( clientOrderLeaveId );
   }

   @Override
   public int updateClientOrderLeave( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException
   {
      return ( ( ClientOrderLeaveDao ) getDao() ).updateClientOrderLeave( clientOrderLeaveVO );
   }

   @Override
   public int insertClientOrderLeave( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException
   {
      return ( ( ClientOrderLeaveDao ) getDao() ).insertClientOrderLeave( clientOrderLeaveVO );
   }

   @Override
   public int deleteClientOrderLeave( final ClientOrderLeaveVO clientOrderLeaveVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ýclientOrderLeaveVO
      clientOrderLeaveVO.setDeleted( ClientOrderLeaveVO.FALSE );
      return ( ( ClientOrderLeaveDao ) getDao() ).updateClientOrderLeave( clientOrderLeaveVO );
   }

   @Override
   public List< Object > getClientOrderLeaveVOsByOrderHeaderId( final String orderHeaderId ) throws KANException
   {
      return ( ( ClientOrderLeaveDao ) getDao() ).getClientOrderLeaveVOsByOrderHeaderId( orderHeaderId );
   }

}

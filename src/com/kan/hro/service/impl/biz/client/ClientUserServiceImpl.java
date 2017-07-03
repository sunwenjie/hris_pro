package com.kan.hro.service.impl.biz.client;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;
import com.kan.hro.dao.inf.biz.client.ClientUserDao;
import com.kan.hro.domain.biz.client.ClientUserVO;
import com.kan.hro.service.inf.biz.client.ClientUserService;
import com.kan.hro.service.inf.cp.biz.client.CPClientUserService;

public class ClientUserServiceImpl extends ContextService implements ClientUserService, CPClientUserService
{

   @Override
   public PagedListHolder getClientUserVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ClientUserDao clientUserDao = ( ClientUserDao ) getDao();
      pagedListHolder.setHolderSize( clientUserDao.countClientUserVOsByCondition( ( ClientUserVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( clientUserDao.getClientUserVOsByCondition( ( ClientUserVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( clientUserDao.getClientUserVOsByCondition( ( ClientUserVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public ClientUserVO getClientUserVOByUserId( final String userId ) throws KANException
   {
      return ( ( ClientUserDao ) getDao() ).getClientUserVOByUserId( userId );
   }

   @Override
   public int insertClientUser( final ClientUserVO clientUserVO ) throws KANException
   {
      return ( ( ClientUserDao ) getDao() ).insertClientUser( clientUserVO );
   }

   @Override
   public int updateClientUser( final ClientUserVO clientUserVO ) throws KANException
   {
      return ( ( ClientUserDao ) getDao() ).updateClientUser( clientUserVO );
   }

   @Override
   public int deleteClientUser( final ClientUserVO clientUserVO ) throws KANException
   {
      return ( ( ClientUserDao ) getDao() ).deleteClientUser( clientUserVO );
   }

   @Override
   public List< Object > getClientUserVOByCondition( final ClientUserVO clientUserVO ) throws KANException
   {
      return ( ( ClientUserDao ) getDao() ).getClientUserVOsByCondition( clientUserVO );
   }

   @Override
   public boolean isExistByCondition( final ClientUserVO clientUserVO ) throws KANException
   {
      final List< Object > list = getClientUserVOByCondition( clientUserVO );
      return list != null && list.size() > 0;
   }

   @Override
   public ClientUserVO getClientUserByName( ClientUserVO clientUserVO )
   {
      return ( ( ClientUserDao ) getDao() ).getClientUserByName( clientUserVO );
   }
}

package com.kan.base.service.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.RightDao;
import com.kan.base.domain.system.RightVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.RightService;
import com.kan.base.util.KANException;

public class RightServiceImpl extends ContextService implements RightService
{

   @Override
   public PagedListHolder getRightVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final RightDao rightDao = ( RightDao ) getDao();
      pagedListHolder.setHolderSize( rightDao.countRightVOsByCondition( ( RightVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( rightDao.getRightVOsByCondition( ( RightVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( rightDao.getRightVOsByCondition( ( RightVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public RightVO getRightVOByRightId( final String rightId ) throws KANException
   {
      return ( ( RightDao ) getDao() ).getRightVOByRightId( rightId );
   }

   @Override
   public int updateRight( final RightVO rightVO ) throws KANException
   {
      return ( ( RightDao ) getDao() ).updateRight( rightVO );
   }

   @Override
   public int insertRight( final RightVO rightVO ) throws KANException
   {
      return ( ( RightDao ) getDao() ).insertRight( rightVO );

   }

   @Override
   public void deleteRight( final RightVO rightVO ) throws KANException
   {
      ( ( RightDao ) getDao() ).deleteRight( rightVO );
   }

   @Override
   public List< Object > getRightVOs() throws KANException
   {
      return ( ( RightDao ) getDao() ).getRightVOs();
   }

}

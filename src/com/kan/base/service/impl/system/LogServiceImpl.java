package com.kan.base.service.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.system.LogDao;
import com.kan.base.domain.system.LogVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.system.LogService;
import com.kan.base.util.KANException;

public class LogServiceImpl extends ContextService implements LogService
{

   @Override
   public int insertLog( LogVO logVO ) throws KANException
   {
      final int rows = ( ( LogDao ) getDao() ).insertLog( logVO );

      return rows;
   }

   @Override
   public PagedListHolder getLogVOsByCondition( PagedListHolder pagedListHolder, boolean isPaged ) throws KANException
   {
      final LogDao logDao = ( LogDao ) getDao();
      pagedListHolder.setHolderSize( logDao.countLogVOsByCondition( ( LogVO ) pagedListHolder.getObject() ) );

      if ( isPaged )
      {
         pagedListHolder.setSource( logDao.getLogVOsByCondition( ( LogVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage() * pagedListHolder.getPageSize()
               + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( logDao.getLogVOsByCondition( ( LogVO ) pagedListHolder.getObject() ) );
      }

      return pagedListHolder;
   }

   @Override
   public LogVO getLogVOById( String id ) throws KANException
   {
      return ( ( LogDao ) getDao() ).getLogVOById( id );
   }

   @Override
   public List< Object > getLogModules() throws KANException
   {
      return ( ( LogDao ) getDao() ).getLogModules();
   }

   @Override
   public LogVO getPreLog( final LogVO logVO ) throws KANException
   {
      return ( ( LogDao ) getDao() ).getPreLog( logVO );
   }

}

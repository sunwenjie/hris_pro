package com.kan.base.service.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.security.EntityDao;
import com.kan.base.domain.security.EntityVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.security.EntityService;
import com.kan.base.util.KANException;

public class EntityServiceImpl extends ContextService implements EntityService
{

   @Override
   public PagedListHolder getEntityVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final EntityDao entityDao = ( EntityDao ) getDao();
      pagedListHolder.setHolderSize( entityDao.countEntityVOsByCondition( ( EntityVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( entityDao.getEntityVOsByCondition( ( EntityVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( entityDao.getEntityVOsByCondition( ( EntityVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public EntityVO getEntityVOByEntityId( final String entityId ) throws KANException
   {
      return ( ( EntityDao ) getDao() ).getEntityVOByEntityId( entityId );
   }

   @Override
   public int insertEntity( final EntityVO entityVO ) throws KANException
   {
      return ( ( EntityDao ) getDao() ).insertEntity( entityVO );
   }

   @Override
   public int updateEntity( final EntityVO entityVO ) throws KANException
   {
      return ( ( EntityDao ) getDao() ).updateEntity( entityVO );
   }

   @Override
   public int deleteEntity( final EntityVO entityVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final EntityVO modifyObject = ( ( EntityDao ) getDao() ).getEntityVOByEntityId( entityVO.getEntityId() );
      modifyObject.setDeleted( EntityVO.FALSE );
      return ( ( EntityDao ) getDao() ).updateEntity( modifyObject );
   }

   @Override
   public List< Object > getEntityVOsByAccountId( final String accountId ) throws KANException
   {
      return ( ( EntityDao ) getDao() ).getEntityVOsByAccountId( accountId );
   }

   @Override
   public List< Object > getEntityBaseViews( final String accountId ) throws KANException
   {
      return ( ( EntityDao ) getDao() ).getEntityBaseViews( accountId );
   }

}

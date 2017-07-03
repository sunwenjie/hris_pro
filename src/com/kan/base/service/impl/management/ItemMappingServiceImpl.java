package com.kan.base.service.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.ContextService;
import com.kan.base.dao.inf.management.ItemMappingDao;
import com.kan.base.domain.management.ItemMappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.service.inf.management.ItemMappingService;
import com.kan.base.util.KANException;

public class ItemMappingServiceImpl extends ContextService implements ItemMappingService
{

   @Override
   public PagedListHolder getItemMappingVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException
   {
      final ItemMappingDao itemMappingDao = ( ItemMappingDao ) getDao();
      pagedListHolder.setHolderSize( itemMappingDao.countItemMappingVOsByCondition( ( ItemMappingVO ) pagedListHolder.getObject() ) );
      if ( isPaged )
      {
         pagedListHolder.setSource( itemMappingDao.getItemMappingVOsByCondition( ( ItemMappingVO ) pagedListHolder.getObject(), new RowBounds( pagedListHolder.getPage()
               * pagedListHolder.getPageSize() + 1, pagedListHolder.getPageSize() ) ) );
      }
      else
      {
         pagedListHolder.setSource( itemMappingDao.getItemMappingVOsByCondition( ( ItemMappingVO ) pagedListHolder.getObject() ) );
      }
      return pagedListHolder;
   }

   @Override
   public ItemMappingVO getItemMappingVOByMappingId( final String mappingId ) throws KANException
   {
      return ( ( ItemMappingDao ) getDao() ).getItemMappingVOByMappingId( mappingId );
   }

   @Override
   public int insertItemMapping( final ItemMappingVO itemMappingVO ) throws KANException
   {
      return ( ( ItemMappingDao ) getDao() ).insertItemMapping( itemMappingVO );
   }

   @Override
   public int updateItemMapping( final ItemMappingVO itemMappingVO ) throws KANException
   {
      return ( ( ItemMappingDao ) getDao() ).updateItemMapping( itemMappingVO );
   }

   @Override
   public int deleteItemMapping( final ItemMappingVO itemMappingVO ) throws KANException
   {
      // ±ê¼ÇÉ¾³ý
      final ItemMappingVO modifyObject = ( ( ItemMappingDao ) getDao() ).getItemMappingVOByMappingId( itemMappingVO.getMappingId() );
      modifyObject.setDeleted( ItemMappingVO.FALSE );
      return ( ( ItemMappingDao ) getDao() ).updateItemMapping( modifyObject );
   }

   @Override
   public List< Object > getItemMappingVOsByAccountId( final String accountId ) throws KANException
   {
      return( ( ItemMappingDao ) getDao() ).getItemMappingVOsByAccountId( accountId );
   }
}

package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.ItemMappingVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ItemMappingService
{
   public abstract PagedListHolder getItemMappingVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ItemMappingVO getItemMappingVOByMappingId( final String mappingId ) throws KANException;

   public abstract int insertItemMapping( final ItemMappingVO itemMappingVO ) throws KANException;

   public abstract int updateItemMapping( final ItemMappingVO itemMappingVO ) throws KANException;

   public abstract int deleteItemMapping( final ItemMappingVO itemMappingVO ) throws KANException;
   
   public abstract List< Object > getItemMappingVOsByAccountId( final String accountId ) throws KANException;
}

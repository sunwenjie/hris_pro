package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.ItemGroupDTO;
import com.kan.base.domain.management.ItemGroupVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ItemGroupService
{
   public abstract PagedListHolder getItemGroupVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ItemGroupVO getItemGroupVOByItemGroupId( final String itemGroupId ) throws KANException;

   public abstract int insertItemGroup( final ItemGroupVO itemGroupVO ) throws KANException;

   public abstract int updateItemGroup( final ItemGroupVO itemGroupVO ) throws KANException;

   public abstract int deleteItemGroup( final ItemGroupVO itemGroupVO ) throws KANException;
   
   public abstract List< ItemGroupDTO > getItemGroupDTOsByAccountId( final String accountId ) throws KANException;
}

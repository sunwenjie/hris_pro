package com.kan.base.service.inf.management;

import java.util.List;

import com.kan.base.domain.management.ItemVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ItemService
{
   public abstract PagedListHolder getItemVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ItemVO getItemVOByItemId( final String itemId ) throws KANException;

   public abstract int insertItem( final ItemVO itemVO ) throws KANException;

   public abstract int updateItem( final ItemVO itemVO ) throws KANException;

   public abstract int deleteItem( final ItemVO itemVO ) throws KANException;
   
   public abstract List< Object > getItemVOsByAccountId( final String accountId ) throws KANException;
   
   public abstract List< Object > getItemBaseViewsByAccountId( final String accountId ) throws KANException;
}

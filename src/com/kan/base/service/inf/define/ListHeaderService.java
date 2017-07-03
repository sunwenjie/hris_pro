package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.ListDTO;
import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ListHeaderService
{
   public abstract PagedListHolder getListHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ListHeaderVO getListHeaderVOByListHeaderId( final String listHeaderId ) throws KANException;

   public abstract int insertListHeader( final ListHeaderVO listHeaderVO ) throws KANException;

   public abstract int updateListHeader( final ListHeaderVO listHeaderVO ) throws KANException;

   public abstract int deleteListHeader( final ListHeaderVO listHeaderVO ) throws KANException;

   public abstract List< ListDTO > getListDTOsByAccountId( final String accountId ) throws KANException;
}

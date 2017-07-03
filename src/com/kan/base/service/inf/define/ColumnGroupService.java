package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.ColumnGroupVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ColumnGroupService
{
   public abstract PagedListHolder getColumnGroupVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ColumnGroupVO getColumnGroupVOByGroupId( final String groupId ) throws KANException;

   public abstract int insertColumnGroup( final ColumnGroupVO columnGroupVO ) throws KANException;

   public abstract int updateColumnGroup( final ColumnGroupVO columnGroupVO ) throws KANException;

   public abstract int deleteColumnGroup( final ColumnGroupVO columnGroupVO ) throws KANException;

   public abstract List< Object > getColumnGroupVOsByAccountId( final String accountId ) throws KANException;
}
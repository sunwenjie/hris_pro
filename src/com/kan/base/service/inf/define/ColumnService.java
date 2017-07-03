package com.kan.base.service.inf.define;

import com.kan.base.domain.define.ColumnVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ColumnService
{
   public abstract PagedListHolder getColumnVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ColumnVO getColumnVOByColumnId( final String columnId ) throws KANException;

   public abstract int insertColumn( final ColumnVO columnVO ) throws KANException;

   public abstract int updateColumn( final ColumnVO columnVO ) throws KANException;

   public abstract int deleteColumn( final ColumnVO columnVO ) throws KANException;
}

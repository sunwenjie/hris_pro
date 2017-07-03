package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.ListDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ListDetailService
{
   public abstract PagedListHolder getListDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ListDetailVO getListDetailVOByListDetailId( final String listDetailId ) throws KANException;

   public abstract int insertListDetail( final ListDetailVO listDetailVO ) throws KANException;

   public abstract int updateListDetail( final ListDetailVO listDetailVO ) throws KANException;

   public abstract int deleteListDetail( final ListDetailVO listDetailVO ) throws KANException;

   public abstract List< Object > getListDetailVOsByListHeaderId( final String listHeaderId ) throws KANException;
   public abstract List< Object > getListDetailVOsByCondition( final ListDetailVO listDetailVO ) throws KANException;
}

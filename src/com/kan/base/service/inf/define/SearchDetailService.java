package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.SearchDetailVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface SearchDetailService
{
   public abstract PagedListHolder getSearchDetailVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SearchDetailVO getSearchDetailVOBySearchDetailId( final String searchDetailId ) throws KANException;

   public abstract int insertSearchDetail( final SearchDetailVO searchDetailVO ) throws KANException;

   public abstract int updateSearchDetail( final SearchDetailVO searchDetailVO ) throws KANException;

   public abstract int deleteSearchDetail( final SearchDetailVO searchDetailVO ) throws KANException;

   public abstract List< Object > getSearchDetailVOsBySearchHeaderId( final String searchHeaderId ) throws KANException;

   public abstract List< Object > getSearchDetailVOsByCondition( final SearchDetailVO searchDetailVO ) throws KANException;
}

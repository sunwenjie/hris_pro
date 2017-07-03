package com.kan.base.service.inf.define;

import java.util.List;

import com.kan.base.domain.define.SearchDTO;
import com.kan.base.domain.define.SearchHeaderVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface SearchHeaderService
{
   public abstract PagedListHolder getSearchHeaderVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract SearchHeaderVO getSearchHeaderVOBySearchHeaderId( final String searchHeaderId ) throws KANException;

   public abstract int insertSearchHeader( final SearchHeaderVO searchHeaderVO ) throws KANException;

   public abstract int updateSearchHeader( final SearchHeaderVO searchHeaderVO ) throws KANException;

   public abstract int deleteSearchHeader( final SearchHeaderVO searchHeaderVO ) throws KANException; 
   
   public abstract List< SearchDTO > getSearchDTOsByAccountId( final String accountId ) throws KANException;
}

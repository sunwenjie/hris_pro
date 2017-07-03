package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.SearchHeaderVO;
import com.kan.base.util.KANException;

public interface SearchHeaderDao
{
   public abstract int countSearchHeaderVOsByCondition( final SearchHeaderVO searchHeaderVO ) throws KANException;

   public abstract List< Object > getSearchHeaderVOsByCondition( final SearchHeaderVO searchHeaderVO ) throws KANException;

   public abstract List< Object > getSearchHeaderVOsByCondition( final SearchHeaderVO searchHeaderVO, final RowBounds rowBounds ) throws KANException;

   public abstract SearchHeaderVO getSearchHeaderVOBySearchHeaderId( final String searchHeaderId ) throws KANException;

   public abstract int insertSearchHeader( final SearchHeaderVO searchHeaderVO ) throws KANException;

   public abstract int updateSearchHeader( final SearchHeaderVO searchHeaderVO ) throws KANException;

   public abstract int deleteSearchHeader( final String searchHeaderId ) throws KANException;

   public abstract List< Object > getSearchHeaderVOsByAccountId( final String accountId ) throws KANException;
}

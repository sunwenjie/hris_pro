package com.kan.base.dao.inf.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.define.SearchDetailVO;
import com.kan.base.util.KANException;

public interface SearchDetailDao
{
   public abstract int countSearchDetailVOsByCondition( final SearchDetailVO searchDetailVO ) throws KANException;

   public abstract List< Object > getSearchDetailVOsByCondition( final SearchDetailVO searchDetailVO ) throws KANException;

   public abstract List< Object > getSearchDetailVOsByCondition( final SearchDetailVO searchDetailVO, RowBounds rowBounds ) throws KANException;

   public abstract SearchDetailVO getSearchDetailVOBySearchDetailId( final String searchDetailId ) throws KANException;

   public abstract int insertSearchDetail( final SearchDetailVO searchDetailVO ) throws KANException;

   public abstract int updateSearchDetail( final SearchDetailVO searchDetailVO ) throws KANException;

   public abstract int deleteSearchDetail( final String searchDetailId ) throws KANException;

   public abstract List< Object > getSearchDetailVOsBySearchHeaderId( final String searchHeaderId ) throws KANException;
}

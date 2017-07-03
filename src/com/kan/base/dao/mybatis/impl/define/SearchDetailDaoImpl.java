package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.SearchDetailDao;
import com.kan.base.domain.define.SearchDetailVO;
import com.kan.base.util.KANException;

public class SearchDetailDaoImpl extends Context implements SearchDetailDao
{

   @Override
   public int countSearchDetailVOsByCondition( final SearchDetailVO searchDetailVO ) throws KANException
   {
      return ( Integer ) select( "countSearchDetailVOsByCondition", searchDetailVO );
   }

   @Override
   public List< Object > getSearchDetailVOsByCondition( final SearchDetailVO searchDetailVO ) throws KANException
   {
      return selectList( "getSearchDetailVOsByCondition", searchDetailVO );
   }

   @Override
   public List< Object > getSearchDetailVOsByCondition( final SearchDetailVO searchDetailVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSearchDetailVOsByCondition", searchDetailVO, rowBounds );
   }

   @Override
   public SearchDetailVO getSearchDetailVOBySearchDetailId( final String searchDetailId ) throws KANException
   {
      return ( SearchDetailVO ) select( "getSearchDetailVOBySearchDetailId", searchDetailId );
   }

   @Override
   public int insertSearchDetail( final SearchDetailVO searchDetailVO ) throws KANException
   {
      return insert( "insertSearchDetail", searchDetailVO );
   }

   @Override
   public int updateSearchDetail( final SearchDetailVO searchDetailVO ) throws KANException
   {
      return update( "updateSearchDetail", searchDetailVO );
   }

   @Override
   public int deleteSearchDetail( final String searchDetailId ) throws KANException
   {
      return delete( "deleteSearchDetail", searchDetailId );
   }

   @Override
   public List< Object > getSearchDetailVOsBySearchHeaderId( final String searchHeaderId ) throws KANException
   {
      return selectList( "getSearchDetailVOsBySearchHeaderId", searchHeaderId );
   }

}

package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.SearchHeaderDao;
import com.kan.base.domain.define.SearchHeaderVO;
import com.kan.base.util.KANException;

public class SearchHeaderDaoImpl extends Context implements SearchHeaderDao
{

   @Override
   public int countSearchHeaderVOsByCondition( final SearchHeaderVO searchHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countSearchHeaderVOsByCondition", searchHeaderVO );
   }

   @Override
   public List< Object > getSearchHeaderVOsByCondition( final SearchHeaderVO searchHeaderVO ) throws KANException
   {
      return selectList( "getSearchHeaderVOsByCondition", searchHeaderVO );
   }

   @Override
   public List< Object > getSearchHeaderVOsByCondition( final SearchHeaderVO searchHeaderVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getSearchHeaderVOsByCondition", searchHeaderVO, rowBounds );
   }

   @Override
   public SearchHeaderVO getSearchHeaderVOBySearchHeaderId( final String searchHeaderId ) throws KANException
   {
      return ( SearchHeaderVO ) select( "getSearchHeaderVOBySearchHeaderId", searchHeaderId );
   }

   @Override
   public int insertSearchHeader( final SearchHeaderVO searchHeaderVO ) throws KANException
   {
      return insert( "insertSearchHeader", searchHeaderVO );
   }

   @Override
   public int updateSearchHeader( final SearchHeaderVO searchHeaderVO ) throws KANException
   {
      return update( "updateSearchHeader", searchHeaderVO );
   }

   @Override
   public int deleteSearchHeader( final String searchHeaderId ) throws KANException
   {
      return delete( "deleteSearchHeader", searchHeaderId );
   }

   @Override
   public List< Object > getSearchHeaderVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getSearchHeaderVOsByAccountId", accountId );
   }

}

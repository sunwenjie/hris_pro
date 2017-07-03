package com.kan.base.dao.mybatis.impl.define;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.define.ListHeaderDao;
import com.kan.base.domain.define.ListHeaderVO;
import com.kan.base.util.KANException;

public class ListHeaderDaoImpl extends Context implements ListHeaderDao
{

   @Override
   public int countListHeaderVOsByCondition( final ListHeaderVO listHeaderVO ) throws KANException
   {
      return ( Integer ) select( "countListHeaderVOsByCondition", listHeaderVO );
   }

   @Override
   public List< Object > getListHeaderVOsByCondition( final ListHeaderVO listHeaderVO ) throws KANException
   {
      return selectList( "getListHeaderVOsByCondition", listHeaderVO );
   }

   @Override
   public List< Object > getListHeaderVOsByCondition( final ListHeaderVO listHeaderVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getListHeaderVOsByCondition", listHeaderVO, rowBounds );
   }

   @Override
   public ListHeaderVO getListHeaderVOByListHeaderId( final String listHeaderId ) throws KANException
   {
      return ( ListHeaderVO ) select( "getListHeaderVOByListHeaderId", listHeaderId );
   }

   @Override
   public int insertListHeader( final ListHeaderVO listHeaderVO ) throws KANException
   {
      return insert( "insertListHeader", listHeaderVO );
   }

   @Override
   public int updateListHeader( final ListHeaderVO listHeaderVO ) throws KANException
   {
      return update( "updateListHeader", listHeaderVO );
   }

   @Override
   public int deleteListHeader( final String listHeaderId ) throws KANException
   {
      return delete( "deleteListHeader", listHeaderId );
   }

   @Override
   public List< Object > getListHeaderVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getListHeaderVOsByAccountId", accountId );
   }

   @Override
   public List< Object > getAccountListHeaderVOsByCondition( ListHeaderVO listHeaderVO ) throws KANException
   {
      return selectList( "getAccountListHeaderVOsByCondition", listHeaderVO );
   }

   @Override
   public List< Object > getListHeaderVOsByJavaObjcet( ListHeaderVO listHeaderVO ) throws KANException
   {
      return selectList( "getListHeaderVOsByJavaObjcet", listHeaderVO );
   }
}

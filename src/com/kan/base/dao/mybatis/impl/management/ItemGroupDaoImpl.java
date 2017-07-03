package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.ItemGroupDao;
import com.kan.base.domain.management.ItemGroupVO;
import com.kan.base.util.KANException;

public class ItemGroupDaoImpl extends Context implements ItemGroupDao
{

   @Override
   public int countItemGroupVOsByCondition( final ItemGroupVO itemGroupVO ) throws KANException
   {
      return ( Integer ) select( "countItemGroupVOsByCondition", itemGroupVO );
   }

   @Override
   public List< Object > getItemGroupVOsByCondition( final ItemGroupVO itemGroupVO ) throws KANException
   {
      return selectList( "getItemGroupVOsByCondition", itemGroupVO );
   }

   @Override
   public List< Object > getItemGroupVOsByCondition( final ItemGroupVO itemGroupVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getItemGroupVOsByCondition", itemGroupVO, rowBounds );
   }

   @Override
   public ItemGroupVO getItemGroupVOByItemGroupId( final String itemGroupId ) throws KANException
   {
      return ( ItemGroupVO ) select( "getItemGroupVOByItemGroupId", itemGroupId );
   }

   @Override
   public int insertItemGroup( final ItemGroupVO itemGroupVO ) throws KANException
   {
      return insert( "insertItemGroup", itemGroupVO );
   }

   @Override
   public int updateItemGroup( final ItemGroupVO itemGroupVO ) throws KANException
   {
      return update( "updateItemGroup", itemGroupVO );
   }

   @Override
   public int deleteItemGroup( final ItemGroupVO itemGroupVO ) throws KANException
   {
      return delete( "deleteItemGroup", itemGroupVO );
   }

   @Override
   public List< Object > getItemGroupVOsByAccountId( String accountId ) throws KANException
   {
      return selectList( "getItemGroupVOsByAccountId", accountId );
   }

}

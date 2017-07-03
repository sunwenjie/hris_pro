package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.ItemDao;
import com.kan.base.domain.management.ItemVO;
import com.kan.base.util.KANException;

public class ItemDaoImpl extends Context implements ItemDao
{

   @Override
   public int countItemVOsByCondition( final ItemVO itemVO ) throws KANException
   {
      return ( Integer ) select( "countItemVOsByCondition", itemVO );
   }

   @Override
   public List< Object > getItemVOsByCondition( final ItemVO itemVO ) throws KANException
   {
      return selectList( "getItemVOsByCondition", itemVO );
   }

   @Override
   public List< Object > getItemVOsByCondition( final ItemVO itemVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getItemVOsByCondition", itemVO, rowBounds );
   }

   @Override
   public ItemVO getItemVOByItemId( final String itemId ) throws KANException
   {
      return ( ItemVO ) select( "getItemVOByItemId", itemId );
   }

   @Override
   public int insertItem( final ItemVO itemVO ) throws KANException
   {
      return insert( "insertItem", itemVO );
   }

   @Override
   public int updateItem( final ItemVO itemVO ) throws KANException
   {
      return update( "updateItem", itemVO );
   }

   @Override
   public int deleteItem( final ItemVO itemVO ) throws KANException
   {
      return delete( "deleteItem", itemVO );
   }

   @Override
   public List< Object > getItemVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getItemVOsByAccountId", accountId );
   }

   @Override
   public List< Object > getItemBaseViewsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getItemBaseViewsByAccountId", accountId );
   }

}

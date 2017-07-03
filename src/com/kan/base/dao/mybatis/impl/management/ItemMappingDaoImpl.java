package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.ItemMappingDao;
import com.kan.base.domain.management.ItemMappingVO;
import com.kan.base.util.KANException;

public class ItemMappingDaoImpl extends Context implements ItemMappingDao
{

   @Override
   public int countItemMappingVOsByCondition( final ItemMappingVO itemMappingVO ) throws KANException
   {
      return ( Integer ) select( "countItemMappingVOsByCondition", itemMappingVO );
   }

   @Override
   public List< Object > getItemMappingVOsByCondition( final ItemMappingVO itemMappingVO ) throws KANException
   {
      return selectList( "getItemMappingVOsByCondition", itemMappingVO );
   }

   @Override
   public List< Object > getItemMappingVOsByCondition( final ItemMappingVO itemMappingVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getItemMappingVOsByCondition", itemMappingVO, rowBounds );
   }

   @Override
   public ItemMappingVO getItemMappingVOByMappingId( final String mappingId ) throws KANException
   {
      return ( ItemMappingVO ) select( "getItemMappingVOByMappingId", mappingId );
   }

   @Override
   public int insertItemMapping( final ItemMappingVO itemMappingVO ) throws KANException
   {
      return insert( "insertItemMapping", itemMappingVO );
   }

   @Override
   public int updateItemMapping( final ItemMappingVO itemMappingVO ) throws KANException
   {
      return update( "updateItemMapping", itemMappingVO );
   }

   @Override
   public int deleteItemMapping( final ItemMappingVO itemMappingVO ) throws KANException
   {
      return delete( "deleteItemMapping", itemMappingVO );
   }

   @Override
   public List< Object > getItemMappingVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getItemMappingVOsByAccountId", accountId );
   }

}

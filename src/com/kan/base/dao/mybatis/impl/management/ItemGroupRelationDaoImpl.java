package com.kan.base.dao.mybatis.impl.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.management.ItemGroupRelationDao;
import com.kan.base.domain.management.ItemGroupRelationVO;
import com.kan.base.util.KANException;

public class ItemGroupRelationDaoImpl extends Context implements ItemGroupRelationDao
{

   @Override
   public int countItemGroupRelationVOsByCondition( final ItemGroupRelationVO itemGroupRelationVO ) throws KANException
   {
      return ( Integer ) select( "countItemGroupRelationVOsByCondition", itemGroupRelationVO );
   }

   @Override
   public List< Object > getItemGroupRelationVOsByCondition( final ItemGroupRelationVO itemGroupRelationVO ) throws KANException
   {
      return selectList( "getItemGroupRelationVOsByCondition", itemGroupRelationVO );
   }

   @Override
   public List< Object > getItemGroupRelationVOsByCondition( final ItemGroupRelationVO itemGroupRelationVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getItemGroupRelationVOsByCondition", itemGroupRelationVO, rowBounds );
   }

   @Override
   public ItemGroupRelationVO getItemGroupRelationVOByRelationId( final String relationId ) throws KANException
   {
      return ( ItemGroupRelationVO ) select( "getItemGroupRelationVOByRelationId", relationId );

   }

   @Override
   public int insertItemGroupRelation( final ItemGroupRelationVO itemGroupRelationVO ) throws KANException
   {
      return insert( "insertItemGroupRelation", itemGroupRelationVO );
   }

   @Override
   public int updateItemGroupRelation( final ItemGroupRelationVO itemGroupRelationVO ) throws KANException
   {
      return update( "updateItemGroupRelation", itemGroupRelationVO );
   }

   @Override
   public int deleteItemGroupRelation( final ItemGroupRelationVO itemGroupRelationVO ) throws KANException
   {
      return delete( "deleteItemGroupRelation", itemGroupRelationVO );
   }

   @Override
   public List< Object > getItemGroupRelationVOsByItemGroupId( final String itemGroupId ) throws KANException
   {
      return selectList( "getItemGroupRelationVOsByItemGroupId", itemGroupId );
   }

}

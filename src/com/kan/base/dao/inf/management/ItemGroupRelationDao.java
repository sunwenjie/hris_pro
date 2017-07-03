package com.kan.base.dao.inf.management;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.management.ItemGroupRelationVO;
import com.kan.base.util.KANException;

public interface ItemGroupRelationDao
{
   public abstract int countItemGroupRelationVOsByCondition( final ItemGroupRelationVO itemGroupRelationVO ) throws KANException;

   public abstract List< Object > getItemGroupRelationVOsByCondition( final ItemGroupRelationVO itemGroupRelationVO ) throws KANException;

   public abstract List< Object > getItemGroupRelationVOsByCondition( final ItemGroupRelationVO itemGroupRelationVO, final RowBounds rowBounds ) throws KANException;

   public abstract ItemGroupRelationVO getItemGroupRelationVOByRelationId( final String relationId ) throws KANException;

   public abstract int insertItemGroupRelation( final ItemGroupRelationVO itemGroupRelationVO ) throws KANException;

   public abstract int updateItemGroupRelation( final ItemGroupRelationVO itemGroupRelationVO ) throws KANException;

   public abstract int deleteItemGroupRelation( final ItemGroupRelationVO itemGroupRelationVO ) throws KANException;

   public abstract List< Object > getItemGroupRelationVOsByItemGroupId( final String itemGroupId ) throws KANException;
}

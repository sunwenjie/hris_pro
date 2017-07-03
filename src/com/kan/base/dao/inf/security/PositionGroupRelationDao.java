package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.PositionGroupRelationVO;
import com.kan.base.util.KANException;

public interface PositionGroupRelationDao
{
   public abstract int countPositionGroupRelationVOsByCondition( final PositionGroupRelationVO positionGroupRelationVO ) throws KANException;

   public abstract List< Object > getPositionGroupRelationVOsByCondition( final PositionGroupRelationVO positionGroupRelationVO ) throws KANException;

   public abstract List< Object > getPositionGroupRelationVOsByCondition( final PositionGroupRelationVO positionGroupRelationVO, RowBounds rowBounds ) throws KANException;

   public abstract PositionGroupRelationVO getPositionGroupRelationVOByPositionGroupRelationId( final String positionGroupRelationId ) throws KANException;

   public abstract int insertPositionGroupRelation( final PositionGroupRelationVO positionGroupRelationVO ) throws KANException;

   public abstract int updatePositionGroupRelation( final PositionGroupRelationVO positionGroupRelationVO ) throws KANException;

   public abstract int deletePositionGroupRelationByGroupId( final String groupId ) throws KANException;

   public abstract int deletePositionGroupRelationByPositionId( final String positionId ) throws KANException;

   public abstract List< Object > getPositionGroupRelationVOsByGroupId( final String groupId ) throws KANException;

   public abstract List< Object > getPositionGroupRelationVOsByPositionId( final String positionId ) throws KANException;

   public int countPositionGroupRelationVOsByGroupId( final String groupId ) throws KANException;

}

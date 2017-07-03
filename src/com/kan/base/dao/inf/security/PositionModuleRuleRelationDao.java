package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.PositionModuleRuleRelationVO;
import com.kan.base.util.KANException;

public interface PositionModuleRuleRelationDao
{
   public abstract int countPositionModuleRuleRelationVOsByCondition( final PositionModuleRuleRelationVO positionModuleRuleRelationVO ) throws KANException;

   public abstract List< Object > getPositionModuleRuleRelationVOsByCondition( final PositionModuleRuleRelationVO positionModuleRuleRelationVO ) throws KANException;

   public abstract List< Object > getPositionModuleRuleRelationVOsByCondition( final PositionModuleRuleRelationVO positionModuleRuleRelationVO, RowBounds rowBounds )
         throws KANException;

   public abstract PositionModuleRuleRelationVO getPositionModuleRuleRelationVOByPositionModuleRuleRelationId( final String positionStaffRelationId ) throws KANException;

   public abstract int insertPositionModuleRuleRelation( final PositionModuleRuleRelationVO positionModuleRuleRelationVO ) throws KANException;

   public abstract int updatePositionModuleRuleRelation( final PositionModuleRuleRelationVO positionModuleRuleRelationVO ) throws KANException;

   public abstract int deletePositionModuleRuleRelationByCondition( final PositionModuleRuleRelationVO positionModuleRuleRelationVO ) throws KANException;

   public abstract List< Object > getPositionModuleRuleRelationVOsByPositionId( final String positionId ) throws KANException;

}

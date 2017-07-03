package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.GroupModuleRuleRelationVO;
import com.kan.base.util.KANException;

public interface GroupModuleRuleRelationDao
{
   public abstract int countGroupModuleRuleRelationVOsByCondition(final GroupModuleRuleRelationVO groupModuleRuleRelationVO) throws KANException ; 
   
   public abstract List< Object > getGroupModuleRuleRelationVOsByCondition( final GroupModuleRuleRelationVO groupModuleRuleRelationVO ) throws KANException;

   public abstract List< Object > getGroupModuleRuleRelationVOsByCondition( final GroupModuleRuleRelationVO groupModuleRuleRelationVO, RowBounds rowBounds ) throws KANException;

   public abstract GroupModuleRuleRelationVO getGroupModuleRuleRelationVOByRelationId( final String relationId ) throws KANException;

   public abstract int insertGroupModuleRuleRelation( final GroupModuleRuleRelationVO groupModuleRuleRelationVO ) throws KANException;

   public abstract int updateGroupModuleRuleRelation( final GroupModuleRuleRelationVO groupModuleRuleRelationVO ) throws KANException;

   public abstract int deleteGroupModuleRuleRelationByCondition( final GroupModuleRuleRelationVO groupModuleRuleRelationVO ) throws KANException;

   public abstract List< Object > getGroupModuleRuleRelationVOsByGroupId( final String groupId ) throws KANException;
   
   public abstract int deleteGroupModuleRuleRelationByGroupId( final GroupModuleRuleRelationVO groupModuleRuleRelationVO ) throws KANException;
}

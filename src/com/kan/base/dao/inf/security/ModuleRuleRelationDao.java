package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.ModuleRuleRelationVO;
import com.kan.base.util.KANException;

public interface ModuleRuleRelationDao
{
   public abstract int countModuleRuleRelationVOsByCondition( final ModuleRuleRelationVO moduleRuleRelationVO ) throws KANException;

   public abstract List< Object > getModuleRuleRelationVOsByCondition( final ModuleRuleRelationVO moduleRuleRelationVO ) throws KANException;

   public abstract List< Object > getModuleRuleRelationVOsByCondition( final ModuleRuleRelationVO moduleRuleRelationVO, RowBounds rowBounds ) throws KANException;

   public abstract ModuleRuleRelationVO getModuleRuleRelationVOByModuleRuleRelationId( final String moduleRuleRelationId ) throws KANException;

   public abstract int insertModuleRuleRelation( final ModuleRuleRelationVO moduleRuleRelationVO ) throws KANException;

   public abstract int updateModuleRuleRelation( final ModuleRuleRelationVO moduleRuleRelationVO ) throws KANException;

   public abstract int deleteModuleRuleRelationByCondition( final ModuleRuleRelationVO moduleRuleRelationVO ) throws KANException;

   public abstract List< Object > getModuleRuleRelationVOsByAccountId( final String accountId ) throws KANException;

}

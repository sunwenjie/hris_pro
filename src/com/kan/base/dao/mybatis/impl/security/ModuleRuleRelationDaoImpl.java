package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.ModuleRuleRelationDao;
import com.kan.base.domain.security.ModuleRuleRelationVO;
import com.kan.base.util.KANException;

public class ModuleRuleRelationDaoImpl extends Context implements ModuleRuleRelationDao
{

   @Override
   public int countModuleRuleRelationVOsByCondition( final ModuleRuleRelationVO moduleRuleRelationVO ) throws KANException
   {
      return ( Integer ) select( "countModuleRuleRelationVOsByCondition", moduleRuleRelationVO );
   }

   @Override
   public List< Object > getModuleRuleRelationVOsByCondition( final ModuleRuleRelationVO moduleRuleRelationVO ) throws KANException
   {
      return selectList( "getModuleRuleRelationVOsByCondition", moduleRuleRelationVO );
   }

   @Override
   public List< Object > getModuleRuleRelationVOsByCondition( final ModuleRuleRelationVO moduleRuleRelationVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getModuleRuleRelationVOsByCondition", moduleRuleRelationVO, rowBounds );
   }

   @Override
   public ModuleRuleRelationVO getModuleRuleRelationVOByModuleRuleRelationId( final String moduleRuleRelationId ) throws KANException
   {
      return ( ModuleRuleRelationVO ) select( "getModuleRuleRelationVOByModuleRuleRelationId", moduleRuleRelationId );
   }

   @Override
   public int updateModuleRuleRelation( final ModuleRuleRelationVO moduleRuleRelationVO ) throws KANException
   {
      return update( "updateModuleRuleRelation", moduleRuleRelationVO );
   }

   @Override
   public int insertModuleRuleRelation( final ModuleRuleRelationVO moduleRuleRelationVO ) throws KANException
   {
      return insert( "insertModuleRuleRelation", moduleRuleRelationVO );
   }

   @Override
   public int deleteModuleRuleRelationByCondition( final ModuleRuleRelationVO moduleRuleRelationVO ) throws KANException
   {
      return delete( "deleteModuleRuleRelationByCondition", moduleRuleRelationVO );
   }

   @Override
   public List< Object > getModuleRuleRelationVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getModuleRuleRelationVOsByAccountId", accountId );
   }

}

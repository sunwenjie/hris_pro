package com.kan.base.dao.mybatis.impl.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.security.ModuleRightRelationDao;
import com.kan.base.domain.security.ModuleRightRelationVO;
import com.kan.base.util.KANException;

public class ModuleRightRelationDaoImpl extends Context implements ModuleRightRelationDao
{

   @Override
   public int countModuleRightRelationVOsByCondition( final ModuleRightRelationVO moduleRightRelationVO ) throws KANException
   {
      return ( Integer ) select( "countModuleRightRelationVOsByCondition", moduleRightRelationVO );
   }

   @Override
   public List< Object > getModuleRightRelationVOsByCondition( final ModuleRightRelationVO moduleRightRelationVO ) throws KANException
   {
      return selectList( "getModuleRightRelationVOsByCondition", moduleRightRelationVO );
   }

   @Override
   public List< Object > getModuleRightRelationVOsByCondition( final ModuleRightRelationVO moduleRightRelationVO, final RowBounds rowBounds ) throws KANException
   {
      return selectList( "getModuleRightRelationVOsByCondition", moduleRightRelationVO, rowBounds );
   }

   @Override
   public ModuleRightRelationVO getModuleRightRelationVOByModuleRightRelationId( final String moduleRightRelationId ) throws KANException
   {
      return ( ModuleRightRelationVO ) select( "getModuleRightRelationVOByModuleRightRelationId", moduleRightRelationId );
   }

   @Override
   public int updateModuleRightRelation( final ModuleRightRelationVO moduleRightRelationVO ) throws KANException
   {
      return update( "updateModuleRightRelation", moduleRightRelationVO );
   }

   @Override
   public int insertModuleRightRelation( final ModuleRightRelationVO moduleRightRelationVO ) throws KANException
   {
      return insert( "insertModuleRightRelation", moduleRightRelationVO );
   }

   @Override
   public int deleteModuleRightRelationByCondition( final ModuleRightRelationVO moduleRightRelationVO ) throws KANException
   {
      return delete( "deleteModuleRightRelationByCondition", moduleRightRelationVO );
   }

   @Override
   public List< Object > getModuleRightRelationVOsByAccountId( final String accountId ) throws KANException
   {
      return selectList( "getModuleRightRelationVOsByAccountId", accountId );
   }

}

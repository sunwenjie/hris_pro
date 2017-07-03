package com.kan.base.dao.inf.security;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.security.ModuleRightRelationVO;
import com.kan.base.util.KANException;

public interface ModuleRightRelationDao
{
   public abstract int countModuleRightRelationVOsByCondition( final ModuleRightRelationVO moduleRightRelationVO ) throws KANException;

   public abstract List< Object > getModuleRightRelationVOsByCondition( final ModuleRightRelationVO moduleRightRelationVO ) throws KANException;

   public abstract List< Object > getModuleRightRelationVOsByCondition( final ModuleRightRelationVO moduleRightRelationVO, RowBounds rowBounds ) throws KANException;

   public abstract ModuleRightRelationVO getModuleRightRelationVOByModuleRightRelationId( final String moduleRightRelationId ) throws KANException;

   public abstract int insertModuleRightRelation( final ModuleRightRelationVO moduleRightRelationVO ) throws KANException;

   public abstract int updateModuleRightRelation( final ModuleRightRelationVO moduleRightRelationVO ) throws KANException;

   public abstract int deleteModuleRightRelationByCondition( final ModuleRightRelationVO moduleRightRelationVO ) throws KANException;

   public abstract List< Object > getModuleRightRelationVOsByAccountId( final String accountId ) throws KANException;

}

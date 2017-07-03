package com.kan.base.dao.inf.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.domain.system.ModuleVO;
import com.kan.base.util.KANException;

public interface ModuleDao
{

   public abstract int countModuleVOsByCondition( final ModuleVO moduleVO ) throws KANException;

   public abstract List< Object > getModuleVOsByCondition( final ModuleVO moduleVO ) throws KANException;

   public abstract List< Object > getModuleVOsByCondition( final ModuleVO moduleVO, RowBounds rowBounds ) throws KANException;

   public abstract ModuleVO getModuleVOByModuleId( final String moduleId ) throws KANException;

   public abstract int updateModule( final ModuleVO moduleVO ) throws KANException;

   public abstract int insertModule( final ModuleVO moduleVO ) throws KANException;

   public abstract int deleteModule( final ModuleVO moduleVO ) throws KANException;

   public abstract List< Object > getLevelOneModuleVOs() throws KANException;

   public abstract List< Object > getModuleVOsByParentModuleId( final String parentModuleId ) throws KANException;

   public abstract List< Object > getModuleVOsByConditionFromAccount( final ModuleVO moduleVO ) throws KANException;

   public abstract List< Object > getModuleVOsByConditionFromPosition( final ModuleVO moduleVO ) throws KANException;

   public abstract List< Object > getModuleVOsByConditionFromGroup( final ModuleVO moduleVO ) throws KANException;

   public abstract List< Object > getActiveModuleBaseViews() throws KANException;

   public abstract List< Object > getModuleVOsByModuleVO( final ModuleVO moduleVO );

   public abstract void deleteClientModule( final ModuleVO moduleVO ) throws KANException;

   public abstract void deleteClientModuleByClient( final ModuleVO moduleVO ) throws KANException;

   public abstract void insertClientModule( final ModuleVO moduleVO ) throws KANException;

   public abstract List< Object > getClientModuleVOs( final String accountId, final String clientId, final String role ) throws KANException;

   public abstract List< Object > getClientModuleVOsByParentModuleId( final String parentModuleId )throws KANException;

   public abstract List< Object > getEmployeeModuleVOsByParentModuleId( final String parentModuleId )throws KANException;
}
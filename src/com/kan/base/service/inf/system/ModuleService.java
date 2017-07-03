package com.kan.base.service.inf.system;

import java.util.List;

import com.kan.base.domain.system.AccountModuleDTO;
import com.kan.base.domain.system.ModuleDTO;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.page.PagedListHolder;
import com.kan.base.util.KANException;

public interface ModuleService
{

   public abstract PagedListHolder getModuleVOsByCondition( final PagedListHolder pagedListHolder, final boolean isPaged ) throws KANException;

   public abstract ModuleVO getModuleVOByModuleId( final String moduleId ) throws KANException;

   public abstract int updateModule( final ModuleVO moduleVO ) throws KANException;

   public abstract int insertModule( final ModuleVO moduleVO ) throws KANException;

   public abstract void deleteModule( final ModuleVO moduleVO ) throws KANException;

   public abstract List< Object > getModuleVOsByParentModuleId( final String parentModuleId ) throws KANException;

   public abstract List< ModuleDTO > getModuleDTOs() throws KANException;
   
   public abstract int updateAccountModuleRelation( final ModuleVO moduleVO ) throws KANException;

   public abstract List< AccountModuleDTO > getAccountModuleDTOsByAccountId( final String accountId ) throws KANException;
   
   public abstract List< Object > getAccountModuleVOsByAccountId( final String accountId ) throws KANException;
   
   public abstract List< Object > getActiveModuleBaseViews() throws KANException;
   public abstract List< ModuleDTO > getClientModuleDTOs() throws KANException;
   
   public abstract List< ModuleDTO > getEmployeeModuleDTOs() throws KANException;
   public abstract void updateAccountClientModuleRelation( final ModuleVO moduleVO ) throws KANException;
   public abstract List< ModuleVO > getClientModuleVOs( final String accountId, final String clientId,final String role ) throws KANException;
}

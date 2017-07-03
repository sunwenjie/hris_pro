package com.kan.base.dao.mybatis.impl.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.ModuleDao;
import com.kan.base.domain.system.ModuleVO;
import com.kan.base.util.KANException;

public class ModuleDaoImpl extends Context implements ModuleDao
{

   @Override
   public int countModuleVOsByCondition( ModuleVO moduleVO ) throws KANException
   {
      return ( Integer ) select( "countModuleVOsByCondition", moduleVO );
   }

   @Override
   public List< Object > getModuleVOsByCondition( ModuleVO moduleVO ) throws KANException
   {
      return selectList( "getModuleVOsByCondition", moduleVO );
   }

   @Override
   public List< Object > getModuleVOsByCondition( ModuleVO moduleVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getModuleVOsByCondition", moduleVO, rowBounds );
   }

   @Override
   public ModuleVO getModuleVOByModuleId( String moduleId ) throws KANException
   {
      return ( ModuleVO ) select( "getModuleVOByModuleId", moduleId );
   }

   @Override
   public int updateModule( ModuleVO moduleVO ) throws KANException
   {
      return insert( "updateModule", moduleVO );
   }

   @Override
   public int insertModule( ModuleVO moduleVO ) throws KANException
   {
      return insert( "insertModule", moduleVO );
   }

   @Override
   public int deleteModule( ModuleVO moduleVO ) throws KANException
   {
      return delete( "deleteModule", moduleVO );
   }

   @Override
   public List< Object > getLevelOneModuleVOs() throws KANException
   {
      return selectList( "getLevelOneModuleVOs", null );
   }

   @Override
   public List< Object > getModuleVOsByParentModuleId( final String parentModuleId ) throws KANException
   {
      return selectList( "getModuleVOsByParentModuleId", parentModuleId );
   }

   @Override
   public List< Object > getModuleVOsByConditionFromAccount( final ModuleVO moduleVO ) throws KANException
   {
      return selectList( "getModuleVOsByConditionFromAccount", moduleVO );
   }

   @Override
   public List< Object > getModuleVOsByConditionFromPosition( final ModuleVO moduleVO ) throws KANException
   {
      return selectList( "getModuleVOsByConditionFromPosition", moduleVO );
   }

   @Override
   public List< Object > getModuleVOsByConditionFromGroup( final ModuleVO moduleVO ) throws KANException
   {
      return selectList( "getModuleVOsByConditionFromGroup", moduleVO );
   }

   @Override
   public List< Object > getActiveModuleBaseViews() throws KANException
   {
      return selectList( "getActiveModuleBaseViews", null );
   }

   @Override
   public List< Object > getModuleVOsByModuleVO( ModuleVO moduleVO )
   {
      return selectList( "getModuleVOsByModuleVO", moduleVO );
   }

   @Override
   public void deleteClientModule( final ModuleVO moduleVO ) throws KANException
   {
      delete( "deleteClientModule", moduleVO );
   }

   @Override
   public void deleteClientModuleByClient( final ModuleVO moduleVO ) throws KANException
   {
      delete( "deleteClientModuleByClient", moduleVO );
   }

   @Override
   public void insertClientModule( final ModuleVO moduleVO ) throws KANException
   {
      insert( "insertClientModule", moduleVO );
   }

   @Override
   public List< Object > getClientModuleVOs( String accountId, final String clientId ,final String role) throws KANException
   {
      Map<String,String> args = new HashMap< String, String >();
      args.put( "accountId", accountId );
      args.put( "clientId", clientId );
      args.put( "roleId", role );
      return selectList( "getClientModuleVOs", args );
   }

   @Override
   public List< Object > getClientModuleVOsByParentModuleId( String parentModuleId ) throws KANException
   {
      return selectList( "getClientModuleVOsByParentModuleId", parentModuleId );
   }

   @Override
   public List< Object > getEmployeeModuleVOsByParentModuleId( String parentModuleId ) throws KANException
   {
      return selectList( "getEmployeeModuleVOsByParentModuleId", parentModuleId );
   }
}
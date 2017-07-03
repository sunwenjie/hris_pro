package com.kan.base.dao.mybatis.impl.system;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.kan.base.core.Context;
import com.kan.base.dao.inf.system.WorkflowModuleDao;
import com.kan.base.domain.system.WorkflowModuleVO;
import com.kan.base.util.KANException;

public class WorkflowModuleDaoImpl extends Context implements WorkflowModuleDao
{

   @Override
   public int countWorkflowModuleVOsByCondition( final WorkflowModuleVO workflowModuleVO ) throws KANException
   {
      return ( Integer ) select( "countWorkflowModuleVOsByCondition", workflowModuleVO );
   }

   @Override
   public List< Object > getWorkflowModuleVOsByCondition( final WorkflowModuleVO workflowModuleVO ) throws KANException
   {
      return selectList( "getWorkflowModuleVOsByCondition", workflowModuleVO );
   }

   @Override
   public List< Object > getWorkflowModuleVOsByCondition( final WorkflowModuleVO workflowModuleVO, RowBounds rowBounds ) throws KANException
   {
      return selectList( "getWorkflowModuleVOsByCondition", workflowModuleVO, rowBounds );
   }

   @Override
   public int updateWorkflowModule( final WorkflowModuleVO workflowModuleVO ) throws KANException
   {
      return insert( "updateWorkflowModule", workflowModuleVO );
   }

   @Override
   public int insertWorkflowModule( final WorkflowModuleVO workflowModuleVO ) throws KANException
   {
      return insert( "insertWorkflowModule", workflowModuleVO );
   }

   @Override
   public int deleteWorkflowModule( final String moduleId ) throws KANException
   {
      return delete( "deleteWorkflowModule", moduleId );
   }

   @Override
   public WorkflowModuleVO getWorkflowModuleVOByWorkflowModuleId( final String moduleId ) throws KANException
   {
      return ( WorkflowModuleVO ) select( "getWorkflowModuleVOByModuleId", moduleId );
   }

   @Override
   public List< Object > listWorkflowModuleVO() throws KANException
   {
      return selectList( "listWorkflowModuleVO", null );
   }

   @Override
   public List< Object > listUseWorkflowModuleByAccountId( final String accountId ) throws KANException
   {
      return selectList( "listUseWorkflowModuleByAccountId", accountId );
   }

   @Override
   public List< Object > getNotUseWorkflowModules() throws KANException
   {
      return selectList( "getNotUseWorkflowModule", null );
   }

}